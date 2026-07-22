terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
    local = {
      source  = "hashicorp/local"
      version = "~> 2.0"
    }
  }
}

provider "docker" {}

resource "docker_network" "emploi_du_temps_network" {
  name = "emploi-du-temps-network"
}

resource "docker_volume" "pgdata" {
  name = "emploi-du-temps-pgdata"
}

resource "docker_image" "postgres" {
  name = "postgres:16"
}

resource "docker_container" "postgres" {
  name  = "pg-emploi-du-temps"
  image = docker_image.postgres.image_id

  networks_advanced {
    name = docker_network.emploi_du_temps_network.name
  }

  env = [
    "POSTGRES_USER=postgres",
    "POSTGRES_PASSWORD=postgres",
    "POSTGRES_DB=emploi_du_temps"
  ]

  ports {
    internal = 5432
    external = 5432
  }

  volumes {
    volume_name    = docker_volume.pgdata.name
    container_path = "/var/lib/postgresql/data"
  }
}

resource "docker_image" "app" {
  name         = "emploi-du-temps-app:terraform"
  keep_locally = true
}

resource "docker_container" "app" {
  name  = "emploi-du-temps-app-tf"
  image = docker_image.app.image_id

  networks_advanced {
    name = docker_network.emploi_du_temps_network.name
  }

  env = [
    "SPRING_DATASOURCE_URL=jdbc:postgresql://pg-emploi-du-temps:5432/emploi_du_temps",
    "SPRING_DATASOURCE_USERNAME=postgres",
    "SPRING_DATASOURCE_PASSWORD=postgres"
  ]

  ports {
    internal = 8080
    external = 8080
  }

  depends_on = [docker_container.postgres]
}

# Configuration Prometheus (scrape config)
resource "local_file" "prometheus_config" {
  filename = "${path.module}/prometheus.yml"
  content  = <<-EOT
    global:
      scrape_interval: 5s

    scrape_configs:
      - job_name: 'emploi-du-temps-app'
        metrics_path: '/actuator/prometheus'
        static_configs:
          - targets: ['emploi-du-temps-app-tf:8080']
  EOT
}

resource "docker_image" "prometheus" {
  name = "prom/prometheus:latest"
}

resource "docker_container" "prometheus" {
  name  = "prometheus"
  image = docker_image.prometheus.image_id

  networks_advanced {
    name = docker_network.emploi_du_temps_network.name
  }

  ports {
    internal = 9090
    external = 9090
  }

  volumes {
    host_path      = abspath(local_file.prometheus_config.filename)
    container_path = "/etc/prometheus/prometheus.yml"
    read_only      = true
  }

  depends_on = [docker_container.app]
}

resource "docker_image" "grafana" {
  name = "grafana/grafana:latest"
}

resource "docker_container" "grafana" {
  name  = "grafana"
  image = docker_image.grafana.image_id

  networks_advanced {
    name = docker_network.emploi_du_temps_network.name
  }

  env = [
    "GF_SECURITY_ADMIN_PASSWORD=admin"
  ]

  ports {
    internal = 3000
    external = 3000
  }

  depends_on = [docker_container.prometheus]
}
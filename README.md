# Proyecto Base Implementando Clean Architecture

## Que necesita para ejecutar el proyecto
1. Tener instalado el **JDK de Java 17** o superior
2. Tener instalado **Gradle 8.1.1** o superior

## Guía de ejecución

#### Guía para deployment en Docker
1. Clonar el proyecto
2. Abrir una terminal CMD y ubicarse en la raíz del proyecto con el **Dockerfile**
4. Ejecutar los siguientes comandos:

   ``` docker build -f Dockerfile -t bank/enterprise:latest .```

   ```docker run -d --name enterprise -p 8081:8081 bank/enterprise:latest```

#### Documentación OpenAPI con Swagger 3.0
http://localhost:8081/enterprise/swagger-ui/index.html

## Para consumir los servicios con postman
[Bank.postman_collection.json](bank-enterprise%2Fsrc%2Fmain%2Fresources%2FBank.postman_collection.json)
![postman1.png](bank-enterprise%2Fsrc%2Fmain%2Fresources%2Fpostman1.png)
![postman2.png](bank-enterprise%2Fsrc%2Fmain%2Fresources%2Fpostman2.png)
## Diagrama
![Diagrama.png](bank-enterprise%2Fsrc%2Fmain%2Fresources%2FDiagrama.png)

## Cobertura del código
![Coverage.png](bank-enterprise%2Fsrc%2Fmain%2Fresources%2FCoverage.png)
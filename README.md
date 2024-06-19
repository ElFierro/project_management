# SISTEMA DE GESTIÓN DE PROYECTOS AMBIENTALES 

## APIS 📋 

#### MANAGER

Este microservicio se encarga de:
- Crear, consultar, actualizar y eliminar proyectos, tareas y recursos

## TECNOLOGÍAS UTILIZADAS 📋

#### FRAMEWORK

- Spring boot - versión 3.3.0

#### ENTORNO DE DESARROLLO

- JDK versión 17

#### MANEJADOR DE DEPENDENCIAS

- Maven

#### DEPENDENCIAS

- SpringBoot DevTools
- mysql-connector
- Spring web
- Lombok
- Starter mail

#### EMPAQUETADO

- Jar

#### BASE DE DATOS

- MySql

## INSTALACIÓN 🔧

#### CLONAR EL RESPOSITORIO

- Rama Master

```
https://github.com/ElFierro/project_management.git
```

#### INSTALAR MAVEN EN EL PROYECTO

Lo puede hacer con el comando:

```
mvn install
```

Asegúrese que se generaron los archivos **jar** en cada proyecto en la carpeta **target**. 

#### EJECUTE EL ARCHIVO DOCKER-COMPOSE

En la caperta **\Api** esta el archivo **Docker-compose** ejecutamos en una **terminal** el siguiente comando para crear el contenedor:

```
Docker-compose up --build -d
```

#### PROBAR PETICIONES

En la carpeta **pruebas** encontrara una coleccion json de postman.

## Autores ✒️

- Cristian Fierro

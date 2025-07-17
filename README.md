# Patient Management System

A backend system for managing patients, billing, and analytics, built with **Java 21** and **Spring Boot** in a microservices architecture. Developed as a portfolio project to demonstrate backend development, service communication, and cloud deployment.

## Architecture

The system consists of 5 services:

* **Patient Service**: REST API with DTO validation, OpenAPI docs, Kafka producer, PostgreSQL.
* **Billing Service**: gRPC client to Patient Service.
* **Analytics Service**: Kafka consumer, gRPC client to Patient Service.
* **Auth Service**: REST API for user registration/login (JWT), PostgreSQL.
* **API Gateway**: Routes requests and integrates authentication.

Each service has its own **Dockerfile** and runs as a container.

## Deployment

Infrastructure is defined via **CloudFormation** and deployed using **LocalStack**:

* VPC
* ECS (Fargate) for services
* RDS for databases
* MSK (Kafka)
* ALB as the entry point

## Testing

* Basic integration tests using **RestAssured**.

## Technologies

* Java 21, Spring Boot
* REST, gRPC, Kafka
* PostgreSQL
* Docker (individual Dockerfiles per service)
* LocalStack, AWS ECS/RDS/MSK
* JWT, OpenAPI, RestAssured

## Notes

This is a **learning project**, not production-ready.

## Credits

This project is based on the excellent course:
[Production-Ready Spring Boot Microservices](https://codejackal.com/courses/production-ready-spring-boot-microservices/) by CodeJackal.

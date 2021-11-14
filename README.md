# deliverybackend

A Spring Boot Application providing a rest interface to list all orderable pizzas and their detail informations.

To make the interface restful it is splitted into one operation responding with a list of pizzas (`/delivery/pizza`) and one operation which provides a set of detail informations to one pizza from this list (`/delivery/pizza/{id}`). To link both services Spring Boot HATEOAS is used.

The pizza's data are stored in a [mongo database] (https://github.com/dennisgassner/deliverymongo).
The application is secured by OAuth, so that the [pre-configured keycloak container](https://github.com/dennisgassner/deliveryauth) is necessary. 

To create a docker image usable in the [Pizza Scenario](https://github.com/dennisgassner/pizza-delivery) just build it via the simple way described by [Spring Boot](https://spring.io/guides/topicals/spring-boot-docker/), so docker build `--build-arg JAR_FILE=target/*.jar -t orgdennis/deliverybackend .`






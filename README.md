The idea here is to generate the back-end of a system for restaurants, here I will abstract myself from certain conditions, as we will not have the front-end layer here, I'm elaborating a micro-service architecture, so the front-end in theory could be any channel, like a website , an app, and more.



This service can be accessed in several ways, you could put it in the cloud, host on a server, in many ways using the concept of containers, it doesn't matter, each one must choose their best form and option for their business.


Initially in this step, I chose an architecture using an queue.


A queue is an ordered collection of items where the addition of new items happens at one end, called the “rear,” and the removal of existing items occurs at the other end, commonly called the “front.” As an element enters the queue it starts at the rear and makes its way toward the front, waiting until that time when it is the next element to be removed.


The most recently added item in the queue must wait at the end of the collection. The item that has been in the collection the longest is at the front. This ordering principle is sometimes called FIFO, first-in first-out. It is also known as “first-come first-served.”


In essence this is not necessary, I could save it directly in a database, doing this I will have to access this queue by other means and then save them in the database, I already have a complete example of how to do this, the architecture must be designed according to the needs of each individual, several situations can be found and reasons for that, there is no what we can call universal architecture.


Focusing on coding, I chose to use the Java language and the Spring Boot framework, the version of Java I chose was 8 and I use the Ubuntu 20.04 LTS operating system, and used maven.


Spring itself already has examples of how to structure your project and even a launcher which you can use at any time, if you want to generate a new project you can start with a pattern adopted by it, if you want to change that pattern you can also do it this, I particularly for this model followed a standard and example of coding already used by them, hence my option.

https://www.springboottutorial.com/spring-boot-projects-with-code-examples

https://start.spring.io/


So let's imagine a service for restaurants, what a restaurant needs, it has its menu, it needs to place an order, it then needs to register its customers to have its control, imagine if it is a chain of restaurants then it needs the registration of its stores, so just to exemplify I divided it into four micro-services for these purposes:


- Maintenance of your stores, your restaurants

- Maintenance of your customers

- Maintenance of your orders

- Maintenance of your products, your menu


To run my example, we need RabbitMQ installed on the machine, I only needed three commands on mine:


wget -O- https://dl.bintray.com/rabbitmq/Keys/rabbitmq-release-signing-key.asc | sudo apt-key add -

wget -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | sudo apt-key add -


sudo apt update

sudo apt -y install rabbitmq-server



In these two links you can go deeper and look for more details about it:



https://www.rabbitmq.com/install-debian.html#apt



https://computingforgeeks.com/how-to-install-latest-rabbitmq-server-on-ubuntu-18-04-lts/



Here I tried to abstract myself from discussions and things like that, I chose and followed an exemplified architecture, I didn't use clean architecture and I didn't worry about details as clean architecture isn't just a MVC with an adapter?


Code and be happy, everyone wants things in their own way and sometimes you don't know why and even less if it's necessary, you can suggest, but always who will hit the hammer will not be you.


These links could help you to run my project:



http://www.appsdeveloperblog.com/run-spring-boot-app-from-a-command-line/



https://spring.io/guides/gs/spring-boot/


After you can test it using POSTMAN:


https://www.guru99.com/postman-tutorial.html


Here's an example of my post:



http://localhost:8080/restaurants/commands/sign-up


{

    "email": "test@test.com"

}



As for this example I used a relationship, then you can save the addresses as long as you use the correct id:


http://localhost:8080/restaurants/ here you put the id /addresses/commands/add


I already have a lot of examples using docker, but I will add in the future.
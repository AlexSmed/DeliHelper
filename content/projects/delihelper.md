---
title: "DeliHelper"
date: 2026-10-29
draft: false
summary: "A Java-based application that helps restaurant and deli employees manage recipes, routines, important information, and product allergy checks."
---

# DeliHelper

**GitHub repository:** [AlexSmed/DeliHelper](https://github.com/AlexSmed/DeliHelper)

## Project Description

DeliHelper is an application designed to make everyday work easier for restaurant and deli employees. It provides a place to store recipes, important information, and routines, helping employees find the information they need to carry out their tasks.

The application also integrates Google's Gemini AI to check products for potential allergens, helping employees identify allergy-related concerns. 
its setup so it only works with labels on products as it to difficult to know wich ingredients are in a pile of random non labeled items

## Technologies Used

- **Language:** Java
- **Web framework:** Javalin
- **Persistence:** JPA / Hibernate
- **Database:** PostgreSQL
- **Build tool:** Maven
- **Authentication:** JSON Web Tokens (JWT)
-  **DockerHub:** Automatic container updates

### Architecture

The application uses a layered architecture to separate responsibilities:

- **Controller layer:** Handles REST endpoints and incoming requests.
- **Service layer:** Contains the application's business logic.
- **Repository layer:** Handles database access.

## Features

- Centralized storage for recipes and important information.
- Access to routines and procedures for restaurant and deli employees.
- Product allergy checks using Gemini AI.
- Persistent data storage using PostgreSQL.
- JWT-based authentication.

## My Contribution

I developed DeliHelper myself

**Sickness** : i was sick during the time we learned about Secuity and Deployment.   
but i just read the homework and did the Work, that would be gone through in class at home
(Optional section)

Because i missed the Security week, i had to learn how to make the JWT work, i did that by watching the provided video,
from the teachers and searched google too, if i was unsure.
(Learned i shall not get sick again :/ )


## What I Learned

Working on this project helped me get more hands-on experience with Java backend development, REST APIs, and databases. I also learned more 
about how to structure an application by keeping the controllers, business logic, and database operations separate, which makes the code easier to 
manage and update.

I also worked with JWT authentication and learned how to integrate an AI service into an application. One of the most interesting parts was building 
the allergy-checking feature, as it made me think more carefully about how information is presented to users, especially when it comes to something 
as important as food safety.


## Source Code

[View DeliHelper on GitHub](https://github.com/AlexSmed/DeliHelper)


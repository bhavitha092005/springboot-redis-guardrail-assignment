
# Backend Engineering Assignment - Core API & Guardrails

## Project Overview

This project is a Spring Boot backend microservice developed for the Backend Engineering Assignment.

It acts as a central API system with Redis-powered guardrails, PostgreSQL persistence, and scheduled notification batching.

The application is designed to safely handle concurrent bot interactions using Redis atomic operations while keeping the service stateless.

---
__________________________________________________________________________________________________________________________________________________________________
## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Redis (tested locally using Memurai on Windows)
- Maven
- Spring Tool Suite (STS)
- Postman

---
__________________________________________________________________________________________________________________________________________________________________
## Postman Collection

The exported Postman collection is included in the repository.

File:

Backend-Assignment-Postman-Collection.json

How to use:

1. Open Postman
2. Click Import
3. Select the JSON file
4. Run the saved requests

Included requests:

- Create Post
- Get Post
- Like Post
- Add Human Comment
- Add Bot Comment
- Get Virality Score
- Cooldown Test
- Depth Limit Test

__________________________________________________________________________________________________________________________________________________________________
## Features Implemented

# Phase 1 - Core API & Database Setup

Entities created:

- User
- Bot
- Post
- Comment

REST APIs:

- `POST /api/posts` → Create new post
- `POST /api/posts/{postId}/comments` → Add comment
- `POST /api/posts/{postId}/like` → Like post
- `GET /api/posts/{postId}` → Get post details
- `GET /api/redis/virality/{postId}` → Check virality score

---

# Phase 2 - Redis Virality Engine & Atomic Locks

## Real-Time Virality Score

Stored in Redis using key:

```text id="i8p6mt"
post:{id}:virality_score

Scoring Rules:

Bot Reply = +1
Human Like = +20
Human Comment = +50
Guardrails
Horizontal Cap

Redis Key:

post:{id}:bot_count
Maximum 100 bot replies allowed per post
Additional requests return HTTP 429 Too Many Requests
Vertical Cap
Comment thread depth cannot exceed 20
Cooldown Cap

Redis Key:

cooldown:bot_{botId}:human_{userId}
Same bot cannot interact with same human more than once in 10 minutes
Phase 3 - Notification Engine
Smart Notification Throttling

When a bot interacts with a user's post:

If notification sent within last 15 minutes:

Store notification in Redis List:

user:{id}:pending_notifs
Else:

Immediate notification logged to console.

Scheduler

Runs every 5 minutes using Spring Scheduler.

Tasks performed:

Scan pending notifications
Summarize interactions
Clear pending queue

Example:

Summarized Push Notification: Bot 3 replied to your post and 2 others interacted with your posts.
Thread Safety / Concurrency Handling

Redis atomic operations were used:

INCR
SETNX
TTL Expiration

These ensure:

Accurate bot counters under concurrent requests
Safe cooldown enforcement
Stateless distributed counters
Stateless Architecture

No runtime counters or state stored in Java memory.

Not used:

HashMap
static variables
in-memory counters

All temporary state is stored in Redis.

Project Structure
src/main/java/com/assignment/

controller/
service/
repository/
entity/
dto/
config/
scheduler/
exception/
How to Run Locally
Prerequisites
Java 17
PostgreSQL
Redis
Create Database
CREATE DATABASE assignment_db;
__________________________________________________________________________________________________________________________________________________________________
Configure application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/assignment_db
spring.datasource.username=postgres
spring.datasource.password=your_password


spring.data.redis.host=localhost
spring.data.redis.port=6379
Run Application
mvn spring-boot:run

or run from STS.
__________________________________________________________________________________________________________________________________________________________________
Docker Compose

A docker-compose.yml file is included to quickly start:

PostgreSQL
Redis

Run:

docker compose up -d
Sample API Requests
Create Post

POST /api/posts

{
  "authorId": 1,
  "authorType": "USER",
  "content": "My first post"
}
Add Bot Comment

POST /api/posts/1/comments

{
  "authorId": 2,
  "authorType": "BOT",
  "content": "Nice post",
  "depthLevel": 1
}
Deliverables Included
Spring Boot source code
PostgreSQL + Redis configuration
docker-compose.yml
Postman Collection JSON
README.md
Notes

This project was developed and tested locally on Windows using PostgreSQL and Memurai (Redis-compatible server).

Docker Compose is included for easier reviewer setup.

# 🚀 Backend Engineering Assignment  
## Core API & Guardrails System

A Spring Boot backend microservice built to handle high-volume interactions using **PostgreSQL**, **Redis**, and **thread-safe guardrails**.

This project demonstrates backend fundamentals including:

- REST API development  
- Redis atomic counters & locks  
- Rate limiting / cooldown logic  
- Scheduled batch notifications  
- Stateless architecture  
- Production-style backend structure  

---

# 📌 Tech Stack

| Layer | Technology |
|------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Distributed State | Redis |
| Build Tool | Maven |
| Testing Tool | Postman |
| IDE | Spring Tool Suite (STS) |

---

# 📂 Project Structure

```text
src/main/java/com/assignment/

├── controller
├── service
├── repository
├── entity
├── dto
├── config
├── scheduler
└── exception
✅ Features Implemented
🔹 Phase 1 — Core API & Database
Entities
User
Bot
Post
Comment
REST APIs
Method	Endpoint	Description
POST	/api/posts	Create new post
GET	/api/posts/{id}	Fetch post
POST	/api/posts/{id}/comments	Add comment
POST	/api/posts/{id}/like	Like post
🔹 Phase 2 — Redis Virality Engine

Real-time virality score stored in Redis.

Redis Key
post:{id}:virality_score
Scoring Rules
Action	Score
Bot Reply	+1
Human Like	+20
Human Comment	+50
🔹 Phase 2 — Atomic Guardrails
1️⃣ Horizontal Cap

Maximum 100 bot replies per post.

Redis Key:

post:{id}:bot_count
100th request allowed
101st request blocked with HTTP 429
2️⃣ Vertical Cap

Maximum thread depth:

20

If exceeded:

HTTP 400 Bad Request
3️⃣ Cooldown Cap

Same bot cannot interact with same human more than once in 10 minutes.

Redis Key:

cooldown:bot_{botId}:human_{userId}

Blocked requests return:

HTTP 429 Too Many Requests
🔹 Phase 3 — Notification Engine

To prevent spam notifications:

If user received notification in last 15 minutes:

Store pending notifications in Redis List:

user:{id}:pending_notifs
Else:

Immediate push notification logged to console.

⏰ Scheduler

Runs every 5 minutes using Spring Scheduler.

Tasks:
Scan pending notifications
Summarize bot interactions
Clear notification queue
Example Output
Summarized Push Notification:
Bot 3 replied to your post and 2 others interacted with your posts.
🔐 Concurrency & Thread Safety

Redis atomic commands used:

INCR
SETNX
TTL Expiry
Ensures:

✅ Accurate counters under concurrent traffic
✅ Safe cooldown locks
✅ Race-condition resistant bot caps

🌐 Stateless Architecture

No runtime counters stored in Java memory.

Not used:

HashMap
static counters
local in-memory locks

All temporary state handled in Redis.

⚠️ Error Handling

Custom exception handling implemented using:

ApiException
GlobalExceptionHandler
Status Codes
Status	Meaning
400	Invalid depth level
404	Post not found
429	Rate limited / Bot cap exceeded
🧪 Postman Collection

Included in project root:

Backend-Assignment-Postman-Collection.json
Contains Tested Requests
Create Post
Get Post
Like Post
Human Comment
Bot Comment
Virality Check
Cooldown Test
Depth Limit Test
⚙️ How to Run Locally
Prerequisites
Java 17
PostgreSQL
Redis
Create Database
CREATE DATABASE assignment_db;
Configure application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/assignment_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.data.redis.host=localhost
spring.data.redis.port=6379
Run Application
mvn spring-boot:run

or run directly from STS.

🐳 Docker Support

Included:

docker-compose.yml

Starts:

PostgreSQL
Redis

Run:

docker compose up -d
📦 Deliverables Included

✅ Spring Boot Source Code
✅ PostgreSQL + Redis Integration
✅ docker-compose.yml
✅ Postman Collection
✅ README.md

📝 Notes

This project was developed and tested locally on Windows using:

PostgreSQL
Memurai (Redis-compatible server)

Docker Compose is included for reviewer convenience.

👩‍💻 Author

Bhavitha Pala

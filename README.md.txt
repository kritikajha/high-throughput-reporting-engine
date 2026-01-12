# High-Throughput Asynchronous Reporting Engine
![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green) ![AWS](https://img.shields.io/badge/AWS-Certified-blue)

## 📌 Overview
An enterprise-grade asynchronous engine designed to handle heavy data-reporting tasks without blocking the main application thread. This system was engineered to solve the "Synchronous Bottleneck" in financial reporting modules.

## 🛠️ The Problem (The Challenge)
In the legacy system, report generation was a synchronous process. 
* **Timeouts:** Long-running queries (~2 hours) caused API timeouts.
* **Resource Exhaustion:** Simultaneous requests led to High CPU and Memory usage (OOM errors).
* **Poor UX:** Users had to wait with a loading screen until the report was ready.

## 🚀 The Solution (Engineering Wins)
I re-architected the system into an **Event-Driven Architecture**:

1. **Decoupled Processing:** Used **ActiveMQ** to move report requests into a background queue. The API now returns a `202 Accepted` status immediately.
2. **Multithreaded Execution:** Implemented a `ThreadPoolTaskExecutor` to process multiple reports in parallel while controlling the maximum number of concurrent threads to prevent database overloading.
3. **Database Performance:** Identified and optimized slow-running SQL via **Oracle AWR reports**, reducing the raw data retrieval time by 60%.
4. **Reliability:** Integrated a "Retry-with-Delay" mechanism using Dead Letter Queues (DLQ) for failed report generations.

## 📈 Impact
* **Performance:** Reduced total processing time from **120 minutes to 30 minutes**.
* **Scalability:** System can now handle 5x more concurrent users without crashing.
* **Cost:** Reduced AWS compute overhead by optimizing thread lifecycle.

## 🏗️ High-Level Design
[User] -> [Spring Boot API] -> [ActiveMQ Queue] -> [Worker Threads] -> [Oracle DB] -> [S3 Storage]

---
*Note: This repository is a technical case study and simulation of the architectural principles used in production.*
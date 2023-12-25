# Social Media
## Project Description
In the project simulating a social media platform, a RestFul API-based structure was implemented, 
adhering to a micro services architecture. Spring Framework components such as Spring Boot, Web, 
Security, and Data JPA were integrated to establish a layered structure, ensuring that each service is 
designed to perform specific functionalities. The project's dependencies were managed using Gradle, and 
it was made testable through Docker containers. Leveraging Docker's flexibility and portability advantages, 
the project's independence and self-sufficiency were reinforced.
## Project Structure
### Technologies
* Java SE 17
* Spring Framework
  * Spring Boot
  * Spring Cloud
  * Spring Security
  * Spring Data JPA
* Database
  * PostgreSQL
  * MongoDB
* Data Storage and Processing
  * Caching: Redis
  * Message Broker: RabbitMQ
  * Search Engine: Elastic Search
* Containerization and Distribution
  * Kubernetes
  * Docker
  * GitHub
### Software Architecture and Development Approaches
* Microservice Architecture
* REST API
* Layered Architecture
## Installation Steps
1. Install Docker
   * [Windows](https://docs.docker.com/desktop/install/windows-install/)
   * [Mac](https://docs.docker.com/desktop/install/mac-install/)
   * [Linux](https://docs.docker.com/desktop/install/linux-install/)
2. Download the project from GitHub and extract it into a folder
3. Open the terminal within the folder and enter the command
    * ```shell
      docker-compose up -d
      ```
## User Guide
# API - Gerenciador de Estoque

API para gerenciamento de estoque com categorias, produtos, movimentações e relatórios financeiros e físicos.

## Diagrama de entidades (ER)

<img width="936" height="191" alt="diagrama-de-classes" src="https://github.com/user-attachments/assets/1a97be15-da0b-4e56-bfe9-bce7c87dbe55" />

## Tecnologias utilizadas

Java 17

Spring Boot

JPA / Hibernate

PostgreSQL

Maven

Docker e Docker Compose

AWS

## Instruções rápidas para rodar:

### Pela Web (AWS)

http://3.85.245.28:8080/api/

### Com Docker

docker compose up --build
A aplicação inicia em http://localhost:8080

### Sem Docker

mvn clean package
java -jar target/*.jar

## Endpoints

Base: /api

### Categorias (/api/categories)

GET / -> lista categorias  
GET /{id} -> busca categoria por id  
POST / -> cria categoria  
Exemplo: {"name": "Enlatados", "size": "MEDIUM", "packaging": "CAN"}  
PUT /{id} -> atualiza categoria  
DELETE /{id} -> remove categoria

### Produtos (/api/products)

GET / -> lista produtos  
GET /{id} -> busca produto por id  
POST / -> cria produto  
Exemplo: {"name": "Milho", "unitPrice": 5.99, "unit": "un", "quantityInStock": 100, "minQuantity": 10, "maxQuantity": 200, "categoryId": 1}  
PUT /{id} -> atualiza produto  
DELETE /{id} -> remove produto  
PATCH /price-adjustment -> reajusta preço de todos os produtos  
Exemplo: {"percentage": 10.0}

### Movimentos (/api/movements)

GET / -> lista movimentações  
POST / -> registra movimentação  
Exemplo: {"productId": 1, "quantityMoved": 10, "movementType": "EXIT"}

### Reports (/api/reports)

GET /price-list -> lista de preços  
GET /balance -> balanço físico e financeiro  
GET /below-min-stock -> produtos abaixo do estoque mínimo  
GET /count-by-category -> contagem por categoria  
GET /most-moved -> produtos mais movimentados

## Contribuidores

David Camargo Rech 10724112255
Gabriel Martins Meira 10724113373
Leonardo Schmitt 10724112322
Leonardo Sousa Vargas 10724111015
Mateus Almeida Santos 10724112341

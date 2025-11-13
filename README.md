# API - Gerenciador de Estoque

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

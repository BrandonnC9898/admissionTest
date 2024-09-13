# Test cases:

## Create

curl --location 'localhost:8080/api/medications' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Testmed3",
    "description": "lorem alkmelasjdlfem al lkjasdñlifjñlasd ijñlejñoiajldkjf ljasidf",
    "price": 10.0,
    "expirationDate": "2026-01-01",
    "categoryName": "Analgesics"
}'

## Get By Category
curl --location 'localhost:8080/api/medications/category/Analgesics?expiration-after=2024-09-20'

## Get By Id
curl --location 'localhost:8080/api/medications/1'

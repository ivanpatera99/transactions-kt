# Transactions serivice

This project is a test project to track transactions.

## Setup

First build the docker image with the following command:
```
docker build -t transactions .
```
Then simply run the application with:
```
docker run -p 8080:8080 transactions
```
## Usage

There are three exposed endpoints. 

1. Put a new transaction:
```
curl --location --request PUT 'localhost:8080/transactions/{transactionId}' \
--header 'Content-Type: application/json' \
--data '{
    "amount": 400,
    "type": "test",
    "parentId": 10 (optional)
}'
```
it returns 200:
```
{
    "msg": "transaction saved successfully"
}
```

and returns 404/409 depending on your error case:
```
{
    "error": "PARENT_TXN_NOT_FOUND"
}
```

2. Get by type
```
curl --location 'localhost:8080/transactions/types/{type}'
```
Where `{type}` represents a string. This endpoint returns a list of transactions related to the provided type. If no transaction has been registered yet for the type it returns an empty list.
Ex:
```
{
    "transactions": [
        9
    ]
}
```

3. Get transaction sum:
```
curl --location 'localhost:8080/transactions/sum/{transactionId}'
```
This endpoint returns the sum of all the children related to the provided `transactionId`.
Ex:
```
{
    "sum": 40.0
}
```
It also returns a 404 if the provided transactionId is not found:
```
{
    "error": "TXN_NOT_FOUND"
}
```

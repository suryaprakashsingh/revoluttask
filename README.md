# revoluttask

# Endpoints
http://localhost:8080/api/v1/accounts   POST - Add accounts
Sample JSON {"accountNum": "1234", "balance": 4000}  

http://localhost:8080/api/v1/transfers   POST - Transfer funds
Sample JSON  {
	"from" : "123",
	"to" : "1234",
	"amount": 100
}


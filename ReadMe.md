purpose:

readingIsGoog is It is a book sales application where customers can register, scann books (multiple books can be scanned at once), 
order, monitor the details of these orders and monitor the order statistics of the customers.

used technologies:

	-	mongoDB "version": "5.0.6"
	-	jdk 11
	-	spring boot
	-	mapStruct
	-	spring-security
	-	junit
	-	lombok
	-	docker 
	-	maven
	-	postman
	-	restfull


functional requirements:

	-	paging
	-	autorization for request  ( could not be completed ) 
	-	unit test 
	-	clean code
	-	restful endpoints
	-	input validations

capabilties:

	-	customer registration
	-	book scann
	-	give an order
    -	view order detail
    -	view customer's monthly order statistics
	-	list all orders of given customer ( paging )
	-	list orders in specific time interval



1) customer registration:

	Customer can register with an form:
	-	userName  ->   user name must be uniq, so have to choose one which is unregistered 
	-	mailAddress -> customer's e-mail address. It must be in the correct format. For example: orkunya@gmail.com
	-	firstName --> customer's first name
	-	lastName -> customer's last name
	-	address -> customer's address. does not have a specific format
	-	phoneNumber -> customer's phone number. It must be in the correct format. For example: 5542481020


	example:

input:
{
    "userName" : "orkunya3",
    "mailAddress" : "orku@gmail.com",
    "firstName" : "Burak", 
	"lastName" : "Yasar",
	"address" : "Bahcelievler",
	"phoneNumber" : "5548844410"
}

response:
{
    "userName": "orkunya3",
    "responseMessage": "Customer registered successful.",
    "responseCode": "REGISTERED"
}


2) book scann:

One or more than one Book can be scanned at once. Scanning form:

	-	publicId ->  book's public Id
	-	name  ->  book's name. for ex: Harry Potter
	-	author -> author of book
	-	numberOf -> specified  how many books will be scanned
	-	price -> price of one book. If scanning more than one book at once, you must give price of one book also.
	
 

	example:

input:
{
    "publicId" : "200",
    "name" : "Ucurtmayı Vurmasınlar",
    "author" : "Burak",
    "numberOf" : 4,
    "price" : 50.50
}

response:
{
    "name": "Ucurtmayı Vurmasınlar",
    "publicId": "200",
    "responseMessage": "4 book(s) scanned. Total stock: 10",
    "responseCode": "SCANNED"
}

3) give an order:

customer can place an order if there is enough stock.  
	-	Customer can not place an order which is les tane one book.
	-	if any order is not suitable ( its mean ther is not enough stock) at once, that will be cancelled.
order form:

	-	createdUserName -> customer's user name
	-	orderedBookList: ->  order detail. it is an array. it can be more than one order at once.
		-	bookPublicId  -> book public id for order
		-	numberOfBooksOrdered -> how many books orderer

examle:

input:
{
    "createdUserName" : "orkunya3",
    "orderedBookList" : [{
        "bookPublicId" : 200,
        "numberOfBooksOrdered" : 2
    }]
}

response:
{
    "id": "0f7aa2c5-445f-49e2-b4e1-899213083413",
    "createdUserName": "orkunya3",
    "responseMessage": "Your order has been placed. No: 0f7aa2c5-445f-49e2-b4e1-899213083413",
    "responseCode": "ORDERED"
}

4)  view order detail

customer view order detail. Form:

	-	id -> The public id of the book to be viewed.

example:

input:
{
    "id" : "d632c11a-0602-474a-902f-5171a2316384"
}

response:
{
    "id": "d632c11a-0602-474a-902f-5171a2316384",
    "createdUserName": "100",
    "numberOfBookList": [
        {
            "bookPublicId": "100",
            "numberOfBooksOrdered": 1,
            "bookPrice": 0.0
        }
    ],
    "orderTime": "2022-04-03T19:26:33.782",
    "yearNum": 0
}

5) view customer's monthly order statistics


It can be views customer's monthly order statistics. it list total order count, total book count and total purchased amount per month.
Form:

	-	yearNum -> specified which year's statistics
	-	userName -> specified which customer's statistics

example:


input:
{
    "yearNum" : 2022,
    "userName" : "orkunya3"
}

response:
[
    {
        "monthName": "January",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "February",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "March",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "April",
        "totalOrderCount": 1,
        "totalBookCount": 2,
        "totalPurchasedAmount": 101.0
    },
    {
        "monthName": "May",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "June",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "July",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "August",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "September",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "October",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "November",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    },
    {
        "monthName": "December",
        "totalOrderCount": 0,
        "totalBookCount": 0,
        "totalPurchasedAmount": 0.0
    }
]
6) list all orders of given customer

All orders of a given customer can be listed. Here the pagination method was applied. 
Form:


	-	userName -> customer's user name
	-	pageNumber -> Specifies which page number you want.
	-	pageSize -> Specifies which page size you want.

Example:

input:
{
    "userName" : "100",
    "pageNumber" : 1,
    "pageSize" :  2
}

response:
[
    {
        "id": "073197ca-337e-4762-97cd-149894b619f1",
        "createdUserName": "100",
        "numberOfBookList": [
            {
                "bookPublicId": "100",
                "numberOfBooksOrdered": 1,
                "bookPrice": 0.0
            }
        ],
        "orderTime": "2022-04-03T21:15:10.444",
        "yearNum": 2022
    }
]


7) list orders in specific time interval

All orders of a given specific time interval.
Form:

	-	startDate -> begining date of specific time
	-	endDate -> ending date of specific time

example:

input:
{
    "startDate" : "2021-04-03T19:26:33.782",
    "endDate" : "2023-04-03T19:26:33.782"
}

response:

[
    {
        "id": "d632c11a-0602-474a-902f-5171a2316384",
        "createdUserName": "100",
        "numberOfBookList": [
            {
                "bookPublicId": "100",
                "numberOfBooksOrdered": 1,
                "bookPrice": 0.0
            }
        ],
        "orderTime": "2022-04-03T19:26:33.782",
        "yearNum": 0
    },
    {
        "id": "bbcc5cce-b2bc-44ec-b714-1b5b535f85ea",
        "createdUserName": "100",
        "numberOfBookList": [
            {
                "bookPublicId": "100",
                "numberOfBooksOrdered": 1,
                "bookPrice": 0.0
            }
        ],
        "orderTime": "2022-04-03T20:55:44.481",
        "yearNum": 0
    },
    {
        "id": "073197ca-337e-4762-97cd-149894b619f1",
        "createdUserName": "100",
        "numberOfBookList": [
            {
                "bookPublicId": "100",
                "numberOfBooksOrdered": 1,
                "bookPrice": 0.0
            }
        ],
        "orderTime": "2022-04-03T21:15:10.444",
        "yearNum": 2022
    },
    {
        "id": "c5c4becd-4065-499f-a997-922d51b2e515",
        "createdUserName": "burk",
        "numberOfBookList": [
            {
                "bookPublicId": "100",
                "numberOfBooksOrdered": 1,
                "bookPrice": 50.5
            }
        ],
        "orderTime": "2022-04-04T20:16:38.348",
        "yearNum": 2022
    },
    {
        "id": "065cdad9-b512-41ab-9be1-72c00aaa505b",
        "createdUserName": "burk",
        "numberOfBookList": [
            {
                "bookPublicId": "200",
                "numberOfBooksOrdered": 1,
                "bookPrice": 50.5
            }
        ],
        "orderTime": "2022-04-04T20:17:16.841",
        "yearNum": 2022
    },
    {
        "id": "418bdd29-7ac6-4cb8-aab9-3793cee7644a",
        "createdUserName": "burk",
        "numberOfBookList": [
            {
                "bookPublicId": "200",
                "numberOfBooksOrdered": 2,
                "bookPrice": 50.5
            }
        ],
        "orderTime": "2022-04-04T20:17:48.96",
        "yearNum": 2022
    }
]
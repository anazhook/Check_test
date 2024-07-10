This program implements the functionality of forming a receipt in a store.
There is a list of available products in the "products.csv" file. It contains information about each product (its description, price, wether or not it has a wholesale discount).
The store has a discount program. The list of available discount cards and tge percentage of the discount is stored in the "discountCards.csv" file in the resource folder.
If a product is wholesale, in the event of the customer buying five amd more of them a 10% discount is offered.
The input line looks like this:
id-quantity discountCard=xxxx balanceDebitCard=xxxx
Where id is identification number of the product according to the resource file, quantity is number of products of that type, discountCard is the number of the discount card, which is an optional parameter that offers a specific discount (according to the file, or 2% if the number doesn't coinside with any on the file), balanceDebitCard is the maximum amount of money available for spending (if the total exeeds the balance, an error occurrs).
The program shows current local date and time, a list of bought items with description, quantity, price per piece, total and discount.
Then it calculates the total price, total discount and total price with discount.
It then shows the information about the discount card if such was provided (card number and percentage of the discount).
The result is shown on the console and in the "result.csv" file.

# The Ledger Co
The following was discovered as part of building this project:

The Ledger Co., a marketplace for banks to lend money to borrowers and receive payments for the loans. It works on the following logic:

* The interest for the loan is calculated by I = P*N*R where P is the principal amount, N is the number of years and R is the rate of interest. 
* The total amount to repay will be A = P + I The amount should be paid back monthly in the form of EMIs. 
* The borrowers can also pay a lump sum (that is, an amount more than their monthly EMI). In such a case, the lump sum will be deducted from the total amount (A) which can reduce the number of EMIs. This doesn’t affect the EMI amount unless the remaining total amount is less than the EMI. 
* All transactions happen through The Ledger Co. 

The system is designed to find out how much amount a user has paid the bank and how many EMIs are remaining.

The program takes the following as input:
1. The bank name, borrower name, principal, interest and term.
2. Lump sum payments if any.
3. Given the bank name, borrower name, and EMI number, the program should print the total amount paid by the user (including the EMI number mentioned) and the remaining number of EMIs.

The output returned by the application is:
1. Amount paid so far, and number of EMIs remaining for the user with the bank

### Input Commands
There are 3 input commands defined to separate out the actions. 
* LOAN
* PAYMENT
* BALANCE

### Assumptions
1. Repayments will be paid every month as EMIs until the total amount is recovered.
2. Lump sum amounts can be paid at any point of time before the end of tenure.
3. The EMI amount will be always ceiled to the nearest integer. For example 86.676767 can be ceiled to 87 and 100.10 to 101.
4. The no of EMIs should be ceiled to the nearest whole number. For example 23.79 will be ceiled to 24 and 23.1 will also be ceiled to 24.
5. If the last EMI is more than the remaining amount to pay, then it should be adjusted to match the amount remaining to pay. E.g. if the remaining amount to pay is 150 and your EMI is 160, then the last EMI amount paid should be 150.
6. The total amount remaining at any EMI number should always include the EMIs paid and lump sum payments until that number. For example if there was a lump sum payment after EMI number 10, then the balance command for EMI number 10 should include the lump sum payment as well.

## Gradle command to trigger the application
**_./gradlew bootRun --args=<path-to-txt-file>_**
* e.g.  ./gradlew bootRun --args=/Users/Neha/Desktop/WS/ledger-co/src/test/resources/input_testdata_1.txt


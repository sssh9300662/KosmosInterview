# KosmosInterview
Kosmos Interview

This sample code is used to handle the following virtual currency transaction:<br>
1. Save cash
2. Deduct cash
3. Cancel transaction

I use command pattern to complete this transaction module. The business flow is described as follows:
1. Client get transaction commad from PCashCloseCommandFactory
2. Client execuete command by required parameters.
3. Command executes transaction logic


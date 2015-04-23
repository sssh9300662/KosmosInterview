# KosmosInterview
Kosmos Interview

This sample code is used to handle the following virtual currency transaction:<br>
1. Save cash<br>
2. Deduct cash<br>
3. Cancel transaction<br>

I use command pattern to complete this transaction module. The business flow is described as follows:<br>
1. Client get transaction commad from PCashCloseCommandFactory<br>
2. Client execuete command by required parameters.<br>
3. Command executes transaction logic<br>


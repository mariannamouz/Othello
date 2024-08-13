# Othello
Play Othello against the computer

Implemented a program in which the user can play a game of Othello against the computer.
To determine the computer's moves , we use the MiniMax algorithm with a-b pruning.
In the beggining of the game , the user will choose the maximum search depth for the MiniMax algorithm.
Then , he/she will also choose whether he will play first.
The program rejects moves that violate the rules of the game. 
If the player whose turn it is to play cannot place a new checker anywhere without violating the 
rules, you automatically display a relevant message and the other player plays.
Also after each move you show the state of the game.

To compile and start executing the program, run the main method
which is located in the Main.java file

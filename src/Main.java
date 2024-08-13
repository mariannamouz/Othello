import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int depth;
		int players_color;
		Board board = new Board();

		System.out.println("Which color do you choose? (Consider that black playes first!) (Answer with : 0 for Black / 1 for White)");
		players_color = s.nextInt();

		System.out.println("What would you like the maximum search depth be?");
		depth = s.nextInt();

		// put -1 as default color
		Player human = new Player(-1,depth);
		Player computer = new Player(-1,depth);

		if (players_color == 0) {
			human.setColor(0);
			computer.setColor(1);
		} else {
			human.setColor(1);
			computer.setColor(0);
		}

		board.printBoard();

		// while loop
		while(!board.isTerminal())
		{
			// it's the human's turn to play
			if (board.getNextPlayer() == human.getColor())
			{
				System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("\nIt's your turn!");

				if (board.possibleMoves(human.getColor()) > 0) { // if the player can make valid moves
					System.out.println("\nEnter X,Y coordinations:");
					System.out.print("X:");
					// x = given x - 1
					int x = s.nextInt()-1;

					System.out.print("Y:");
					// y = given y - 1
					int y = s.nextInt()-1;

					Move newMove = new Move(human.getColor(), x, y);

					// while the player chooses "out of bounds" coords OR chooses a not valid move
					while(((x<0 || x>7) || (y<0 || y>7)) || !board.isMoveValid(newMove))
					{
						System.out.println("\nINVALID MOVE");
						System.out.println("Please enter again X,Y coordinations:");
						System.out.print("X:");
						// x = given x - 1
						x = s.nextInt()-1;
	
						System.out.print("Y:");
						// y = given y - 1
						y = s.nextInt()-1;
	
						newMove = new Move(human.getColor(), x, y);
					}
					// we are out of the while loop which means that the player chose a valid move
					board.makeMove(newMove);
					System.out.println("\nBOARD:");
					board.printBoard();
				} 
				else 
				{
					System.out.println("But you can't make any valid move at the moment");
					// give your turn to AI
					board.setNextPlayer(computer.getColor());
				}

			} 
			// it's the computer's turn to play
			else
			{
				// if the computer can make valid moves
				if (board.possibleMoves(computer.getColor()) > 0) {
					System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("\nIt's AI's turn!");
					System.out.println("\nCalculating move...");
					Move comp_move = computer.MiniMax(board);
					System.out.println("Move: "+(comp_move.getX()+1)+", "+(comp_move.getY()+1));
					board.makeMove(comp_move);
					System.out.println("\nBOARD:");
					board.printBoard();
				} 
				else 
				{
					System.out.println("But it can't make any valid move at the moment");
					// give your turn to human
					board.setNextPlayer(human.getColor());
				}
			}
		}

		System.out.println("\nSo the game is over!");
		System.out.println("The score is:");
		System.out.println("You: " + board.getScore(human.getColor()));
		System.out.println("Computer: " + board.getScore(computer.getColor()));
		
		if (board.getScore(human.getColor()) > board.getScore(computer.getColor())) {
			System.out.println("Congatulations! You are the winner!");
		} else if (board.getScore(human.getColor()) < board.getScore(computer.getColor())) {
			System.out.println("Computer won");
		} else {
			System.out.println("It's a tie");
		}
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		s.close();
	}
}
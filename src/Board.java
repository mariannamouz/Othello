import java.util.ArrayList;

public class Board {
    
	/* The next player
     * Initialized with 0 because black starts
     */
    private int nextPlayer=0;

    // move that led to this board
	private Move lastMove = new Move();

    //create a board with 64 available moves/disks
    private int[][] board = new int[8][8];

    // Default Constructor
    Board()
    {
        for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.board[i][j] = -1;
			}
		}
        /* Othello initialization
         * 0 = Black
         * 1 = White
         */
        board[3][3]=0;
        board[3][4]=1;
        board[4][3]=1;
        board[4][4]=0;
    }

    // Copy constructor
    Board(Board board)
    {
        this.nextPlayer = board.getNextPlayer();
		this.lastMove = board.getLastMove();
        this.board = new int[8][8];

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                this.board[i][j] = board.board[i][j];
            }
        }
    }

    // This function is called, each time a new disk is put on the board
    public void makeMove(Move move)
    {
        // no need to check if the move is valid, we checked in main

        // update board table
        board[move.getX()][move.getY()] = move.getColor();

        // OUT-FLANK
        // for x, from x-1 to x+1
        for(int i=move.getX()-1; i<=move.getX()+1; i++)
        {
            // for y, from y-1 to y+1
            for(int j=move.getY()-1; j<=move.getY()+1; j++)
            {
                // if we are inside the board
                if(i>=0 && i<=7 && j>=0 && j<=7)
                {
                    // if there is a non empty square with a different color disk
                    if(board[i][j]!=-1 && board[i][j]!=move.getColor())
                    {
                        // create a neighbor
                        Move neighbor = new Move(board[i][j], i, j);

                        // direction of x,y = neighbor's x,y (i,j) - move's x,y
                        int dir_x = i-move.getX();
                        int dir_y = j-move.getY();

                        // keep the opposite color neighbors in an ArrayList
                        ArrayList<Move> neighbors = new ArrayList<Move>();

                        // follow the line and out-flank
                        while(true)
                        {
                            // add a copy of the neighbor move to ArrayList
                            neighbors.add(new Move(neighbor.getColor(), neighbor.getX(), neighbor.getY()));

                            // update neighbor's x,y
                            neighbor.setX(neighbor.getX()+dir_x);
                            neighbor.setY(neighbor.getY()+dir_y);

                            // if you reach "out of bounds", exit while
                            if(neighbor.getX()==-1 || neighbor.getX()==8 || neighbor.getY()==-1 || neighbor.getY()==8)
                                break;

                            // update neighbor's color
                            neighbor.setColor(board[neighbor.getX()][neighbor.getY()]);
                            // if you find an empty square, exit while
                            if(neighbor.getColor()==-1)
                                break;
                            // if you find same color, then go backwards and out-flank
                            if(neighbor.getColor()==move.getColor())
                            {
                                for(Move nei : neighbors)
                                {;
                                    //out-flank
                                    board[nei.getX()][nei.getY()]=move.getColor();
                                }
                                // exit while
                                break;
                            }
                            // else, if you keep on finding same color, keep on searching
                        }
                    }
                }
            }   
        }

        // update lastMove
        this.lastMove = move;
        
        // update nextPlayer
        if(getNextPlayer()==0)
        {
            setNextPlayer(1);
        }
        else
        {
            setNextPlayer(0);
        }    
    }

    /* Checks if a move is valid, according to Othello's rules:
     * 1. There must be no disks on the given square
     * 2. The disk must out-flank one or more of the enemie's disks
     */
    public boolean isMoveValid(Move move){

        // if there is no disk(move) on this position (if board's disk's color = -1)
        if(board[move.getX()] [move.getY()]==-1)
        {
            // for x, from x-1 to x+1
            for(int i=move.getX()-1; i<=move.getX()+1; i++)
            {
                // for y, from y-1 to y+1
                for(int j=move.getY()-1; j<=move.getY()+1; j++)
                {
                    // if we are inside the board
                    if(i>=0 && i<=7 && j>=0 && j<=7)
                    {
                        // if there is at least one disk of different color then
                        if(board[i][j]!=-1 && board[i][j]!=move.getColor())
                        {
                            // create a neighbor
                            Move neighbor = new Move(board[i][j], i, j);

                            // direction of x,y = neighbor's x,y (i,j) - move's x,y
                            int dir_x = i-move.getX();
                            int dir_y = j-move.getY();

                            // search if there is at least one straight (horizontal, vertical, or diagonal) occupied line
                            // between the given disk and another same color disk 
                            while(true)
                            {
                                // neighbor's new coords = old + (x or y's) direction 
                                neighbor.setX(neighbor.getX()+dir_x);
                                neighbor.setY(neighbor.getY()+dir_y);

                                // if you reach "out of bounds", exit while
                                if(neighbor.getX()==-1 || neighbor.getX()==8 || neighbor.getY()==-1 || neighbor.getY()==8)
                                    break;
                                
                                // if you are inside the board, update neighbor's color
                                neighbor.setColor(board[neighbor.getX()][neighbor.getY()]);
                                // if you find an empty square, exit while
                                if(neighbor.getColor()==-1)
                                    break;
                                // if you find same color, then finish searching
                                if(neighbor.getColor()==move.getColor())
                                    return true;
                                // else, keep on searching
                            }
                        }
                    }
                }   
            }
            return false;
        }
        return false;
    }

    // Returns the number of possible moves, the given color has 
    public int possibleMoves(int color) { 
		// p holds the number of possible moves
        int p = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
                // if the position is empty
				if (board[i][j] == -1)
                {
					Move move = new Move(color,i, j);
                    // if a move made in this position is valid
					if (isMoveValid(move))
                    {
                        // increase valid moves
						p++;
					}
				}
			}
		}
        return p;
	}

    /* Returns an ArrayList (children) with all the available moves for a color
     * Any empty square is an available move (child)
     */
    public ArrayList<Board> getChildren(int color)
    {
        ArrayList<Board> children = new ArrayList<>();
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                Move move = new Move(color, x, y);
                if(isMoveValid(move))
                {
                    Board child = new Board(this);
                    child.makeMove(move);
                    children.add(child);
                }
            }
        }
        return children;
    }

    /* Returns true if board is full or there are no valid moves left
     * we want there to be at least one empty square and one valid move
    */
    public boolean isTerminal()
    {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

                Move black_move = new Move(0,i, j);
                Move white_move = new Move(1,i, j);

                // if the move is valid for the black or the white player;
                if (isMoveValid(black_move) || isMoveValid(white_move))
                { 
                    return false;
                }
			}
		}
        // if there is no valid move, it is terminal
        return true;
    }

    /* Evaluation function
     * which uses the 3 factors below, to calculate and return the heuristic value of a position
     * 1. Total number of disks
     * 2. Total number of captured corners 
     * 3. Total number of disks at the boarders 
     */
    public int evaluate()
    {
        int black = 0; // total number of black disks
        int white = 0; // total number of white disks
        int black_corners = 0; // total number of black disks that are in corners
        int white_corners = 0; // total number of white disks that are in corners
        int black_boarder = 0; // total number of black disks that are in the boarders,
                               // but not in the corners of the board
        int white_boarder = 0; // total number of black disks that are in the boarders,
                               // but not in the corners of the board

        // count the number of black and white discs
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) {
                    black++;
                } else if (board[i][j] == 1) {
                    white++;
                }
            }
        }
        
        // count number of black or white discs that are in the corners of the board
        if (board[0][0] == 0) {
            black_corners++;
        } else if (board[0][0] == 1) {
            white_corners++;
        }
        if (board[7][0] == 0) {
            black_corners++;
        } else if (board[7][0] == 1) {
            white_corners++;
        }
        if (board[0][7] == 0) {
            black_corners++;
        } else if (board[0][7] == 1) {
            white_corners++;
        }
        if (board[7][7] == 0) {
            black_corners++;
        } else if (board[7][7] == 1) {
            white_corners++;
        }

        // count the number of black or white discs that are in the borders , but not in
        // the corners of the board
        for (int i = 1; i < 7; i++) {
            if (board[0][i] == 0) {
                black_boarder++;
            } else if (board[0][i] == 1) {
                white_boarder++;
            }
        }
        for (int i = 1; i < 7; i++) {
            if (board[i][0] == 0) {
                black_boarder++;
            } else if (board[i][0] == 1) {
                white_boarder++;
            }
        }
        for (int i = 1; i < 7; i++) {
            if (board[7][i] == 0) {
                black_boarder++;
            } else if (board[7][i] == 1) {
                white_boarder++;
            }
        }
        for (int i = 1; i < 7; i++) {
            if (board[i][7] == 0) {
                black_boarder++;
            } else if (board[i][7] == 1) {
                white_boarder++;
            }
        }

        int f1 = black - white;
        int f2 = black_corners - white_corners;
        int f3 = black_boarder - white_boarder;

        return f1 + (3*f2) + (2*f3);
    }

    /*Prints the current Board
    * X = Black
    * O = White
    * _ = nothing
    */
    public void printBoard(){
        System.out.print("\n  Y 1 2 3 4 5 6 7 8");
        System.out.print("\nX  _________________");
        System.out.print("\n");
        for(int i=0; i<8;i++)
        {
            System.out.print((i+1)+" | ");
            for(int j=0; j<8;j++)
            {
                if(board[i][j]==0)
                    System.out.print("X ");
                else if(board[i][j]==1)
                    System.out.print("O ");
                else
                    System.out.print("_ ");
            }
            System.out.print("| "+(i+1));
            System.out.print("\n");
        }
        System.out.print("   _________________");
        System.out.print("\n");
        System.out.print("    1 2 3 4 5 6 7 8");
        System.out.print("\n\n");
        System.out.println("Blacks: "+getScore(0)+"  /  Whites: "+getScore(1));
    }

    // Returns the number of the requested color disks
    public int getScore(int color){
        int num=0;
        for (int i = 0; i < 8; i++) { 
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == color) { 
                    num++;
                }
            }
        }
        return num;
    }


    // Getters & Setters
    public int getNextPlayer() {
        return nextPlayer;
    }
    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
    public Move getLastMove() {
        return lastMove;
    }
}

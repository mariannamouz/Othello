import java.util.ArrayList;

public class Player{

    // color: 0 for black, 1 for white
    private int color;
    // if player is AI, set maximum depth for the minimax alf
    private int maxDepth;

    // Person Player constructor
    public Player(int color)
    {
        this.color = color;
    }

    // AI Player constructor
    public Player(int color, int maxDepth)
    {
        this.color = color;
        this.maxDepth = maxDepth;
    }

    public Move MiniMax(Board board)
    {
        // if blacks plays then call Max
        if(getColor()==0)
        {
            // copy board and pass it as an argument, with initialized zero depth
            return Max(new Board(board), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        }
        // if white plays, call Min
        else
        {
            return Min(new Board(board), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        }
    }


    // we call Max & Min, one after the other, until maxDepth is reached or terminal node

    public Move Max(Board board,int a, int b, int depth)
    {
        // if the position is terminal or we have reached the maximum given depth 
        if(board.isTerminal() || depth == getMaxDepth())
        {
            // return the last move, after evaluating the current state(board)
            return new Move(board.getLastMove().getColor(), board.getLastMove().getX(), board.getLastMove().getY(), board.evaluate());
        }

        // Max is called by black (color: 0)
        ArrayList<Board> children = board.getChildren(0);

        // Initialize MaxMove with minimum value
        Move maxMove = new Move(a);

        for(Board child: children)
        {
            // we call Min for each child
            Move minMove = Min(child, a, b, depth+1);

            // find the greatest minMove
            if(minMove.getValue() > maxMove.getValue())
            {
                maxMove.setColor(child.getLastMove().getColor());
                maxMove.setX(child.getLastMove().getX());
                maxMove.setY(child.getLastMove().getY());
                maxMove.setValue(minMove.getValue());
            }
            // if the evaluate gives the same value, we 50/50 choose one of the two moves
            else if(minMove.getValue()== maxMove.getValue() && Math.random() < .5f)
            {
                maxMove.setColor(child.getLastMove().getColor());
                maxMove.setX(child.getLastMove().getX());
                maxMove.setY(child.getLastMove().getY());
                maxMove.setValue(minMove.getValue());
            }

            //AB Pruning
            a = Integer.max(a, minMove.getValue());
            if(b <= a)
            {
                break;
            }

        }
        return maxMove;
    }

    // Similarly
    public Move Min(Board board, int a, int b, int depth)
    {
        if(board.isTerminal() || depth == getMaxDepth())
        {
            return new Move(board.getLastMove().getColor(), board.getLastMove().getX(), board.getLastMove().getY(), board.evaluate());
        }

        ArrayList<Board> children = board.getChildren(1);

        Move minMove = new Move(b);

        for(Board child: children)
        {
            Move maxMove = Max(child, a, b, depth+1);

            if(maxMove.getValue() < minMove.getValue())
            {
                minMove.setColor(child.getLastMove().getColor());
                minMove.setX(child.getLastMove().getX());
                minMove.setY(child.getLastMove().getY());
                minMove.setValue(maxMove.getValue());
            }
            else if(maxMove.getValue()== minMove.getValue() && Math.random() < .5f)
            {
                minMove.setColor(child.getLastMove().getColor());
                minMove.setX(child.getLastMove().getX());
                minMove.setY(child.getLastMove().getY());
                minMove.setValue(maxMove.getValue());
            }

            //AB Pruning
            b = Integer.min(b, maxMove.getValue());
            if(b <= a)
            {
                break;
            }

        }
        return minMove;
    }

    // Getters & Setters
    public int getColor() 
    {
        return color;
    }
    public void setColor(int color)
    {
        this.color = color;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

}


/* A class describing a move in the board
 * Every produced child corresponds to a move, a new disk added to the board
 */
public class Move {

    private int color;
    private int x,y;
    private int value;

    // Default Constructor (no real move)
    Move()
    {
        this.color = -1;
        this.x = -1;
        this.y = -1;
    }

    public Move(int value) {
        this.value = value;
    }

    // Constructor
    public Move(int color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    // Constructor with value
    public Move(int color, int x, int y, int value) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.value = value;
    }

    // Setters & Getters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

}

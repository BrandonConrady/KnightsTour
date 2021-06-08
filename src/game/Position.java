package game;

/*
game.Position object stores a location.
x represents the horizontal component
y represents the vertical component
Origin, (0,0), is at the top left.
Increasing x moves you right
Increasing y moves you down
 */
public class Position implements ChessParameters {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equalTo(Position p) {
        return this.getX() == p.getX() && this.getY() == p.getY();
    }

    ///getters and setters///

    //x
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    //y
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    ///methods for location name

    //chess square name
    public String squareName() {
        if(ChessParameters.onBoard(this)) {
            return COL_NAMES[getX()] + ROW_NAMES[getY()];
        }
        else {
            return "";
        }
    }

    //toString() override
    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
}

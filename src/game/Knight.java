package game;

import java.util.ArrayList;

public class Knight implements KnightBehavior {

    private Position position;

    public Knight() {
        System.out.println("[Knight]: Initializing Knight...");
        this.position = null;
    }

    //moves piece to Position p on KTBoard b
    public void move(KTBoard b, Position p) {
        if(getPosition() == null) {
            System.out.println("[Knight]: Placing Piece at: " + p.squareName());
        }
        else {
            System.out.println("[Knight]: Moving from " + getPosition().squareName() + " to " + p.squareName());
        }

        this.setPosition(p);
        b.getMoveList().add(p);
        b.getVisitedSquare()[p.getX()][p.getY()] = true;
        b.updateHeuristic();

        System.out.println("[Knight]: Move " + b.getMoveList().size() + "/64");
    }

    public boolean validMove(KTBoard b, Position p) {
        boolean valid = false;

        for(Position move: validMoves(b)) {
            if(move.equalTo(p)) {
                valid = true;
            }
        }

        return valid;
    }

    //returns list of positions piece can move to from current position on KTBoard b
    public ArrayList<Position> validMoves(KTBoard b) {
        return KnightBehavior.knightMoves(b, this.getPosition());
    }

    //returns list of positions piece can move to from a given Position p on KTBoard b
    public ArrayList<Position> validMoves(KTBoard b, Position p) {
        return KnightBehavior.knightMoves(b, p);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

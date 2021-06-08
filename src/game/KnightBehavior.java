package game;

import java.util.ArrayList;

public interface KnightBehavior extends ChessParameters {

    //shift values for knight move
    int [] COL_SHIFT = {-2,-1, 1, 2, 2, 1,-1,-2}; //change in x
    int [] ROW_SHIFT = { 1, 2, 2, 1,-1,-2,-2,-1}; //change in y

    //returns list of on-board Knight moves from a given position
    static ArrayList<Position> knightMoves(KTBoard b, Position p) {
        ArrayList<Position> moves = new ArrayList<>();

        if(ChessParameters.onBoard(p)) { //returns empty list if starting position isn't on the board
            for(int i = 0; i < BOARD_SIZE; i++) {
                Position temp = new Position(p.getX() + COL_SHIFT[i], p.getY() + ROW_SHIFT[i]);

                if(ChessParameters.onBoard(temp) && !b.getVisitedSquare()[temp.getX()][temp.getY()]) {
                    moves.add(temp);
                }
            }
        }

        return moves;
    }
}

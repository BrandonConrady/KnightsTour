package game;

import java.util.ArrayList;

public interface ChessParameters {

    int BOARD_SIZE = 8; //size in number of rows/cols (since its square these are equal)
    int BOARD_SIZE_PX = 480; //size but in pixels, this is just a default and should not interfere with resizing the frame

    //names for rows and cols based on the position values as inputs
    String [] ROW_NAMES = {"8", "7", "6", "5", "4", "3", "2", "1"}; //inputs y value
    String [] COL_NAMES = {"a", "b", "c", "d", "e", "f", "g", "h"}; //inputs x value

    static boolean onBoard(Position p) {
        return p.getX() >= 0 && p.getY() >= 0 && p.getX() < BOARD_SIZE && p.getY() < BOARD_SIZE;
    }
}

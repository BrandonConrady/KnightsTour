package run.tests;

import game.ChessParameters;
import game.Position;

/*
Prints names of all squares, as well as tests the case where a position out of bounds is chosen for the squareName() method.
 */
public class SquareNameTest implements ChessParameters {
    public static void main(String[] args) {
        System.out.println("Square Name Test:");

        for(int i = 0; i <= BOARD_SIZE; i++) {
            String line = "";

            for(int j = 0; j <= BOARD_SIZE; j++) {
                Position p = new Position(i,j);

                line += " " + p + " " + p.squareName() + " ";
            }

            System.out.println(line);
        }
    }
}

package run.tests;

import game.ChessParameters;
import game.KTBoard;
import game.Position;

public class KTBoardPrintTests implements ChessParameters {
    public static void main(String[] args) {
        KTBoard board = new KTBoard();

        board.printVisitedSquares();
        board.printHeuristic();

        board.placeKnight(new Position(0,0));


    }
}

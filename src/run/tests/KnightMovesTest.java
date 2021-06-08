package run.tests;

import game.KTBoard;
import game.Position;

public class KnightMovesTest {
    public static void main(String[] args) {
        KTBoard board = new KTBoard();
        board.placeKnight(new Position(0,0));

        board.printHeuristicMoves(board.getKnight().getPosition());
        board.printHeuristic();
        board.printVisitedSquares();

        board.getKnight().move(board, new Position(1,2));

        board.printHeuristicMoves(board.getKnight().getPosition());
        board.printHeuristic();
        board.printVisitedSquares();
    }
}

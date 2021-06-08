package game;

import java.util.ArrayList;

public class KTBoard implements ChessParameters {

    private Knight knight;
    private ArrayList<Position> moveList;

    //for these arrays, x will correspond to the first index,
    //  and y will correspond to the second index
    private boolean [][] visitedSquare;
    private int [][] heuristic;
    //i.e [position.getX()][position.getY()]

    public KTBoard() {
        System.out.println("[Model]: Begin Setup");

        knight = new Knight();
        moveList = new ArrayList<>();
        visitedSquare = new boolean[BOARD_SIZE][BOARD_SIZE];
        heuristic = new int[BOARD_SIZE][BOARD_SIZE];

        initializeBoard();

        System.out.println("[Model]: Setup Complete!");
    }

    public void initializeBoard() {
        System.out.println("[Model]: Initializing Board Values...");

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                visitedSquare[i][j] = false;
                heuristic[i][j] = knight.validMoves(this, new Position(i,j)).size();
            }
        }
    }

    //updates heuristics for current move
    public void updateHeuristic() {
        for(Position move: knight.validMoves(this)) {
            heuristic[move.getX()][move.getY()] = knight.validMoves(this, new Position(move.getX(), move.getY())).size();
        }
    }

    //alternative method to update heuristic for use in undoMove()
    public void updateHeuristic(Position p) {
        for(Position move: knight.validMoves(this, p)) {
            heuristic[move.getX()][move.getY()] = knight.validMoves(this, new Position(move.getX(), move.getY())).size();
        }
    }

    public void resetGame() {
        moveList.clear();
        knight.setPosition(null);
        initializeBoard();
    }

    public void undoMove() {
        if(moveList.size() > 1) {
            int lastIndex = moveList.size() - 1;
            Position temp = moveList.get(lastIndex);
            visitedSquare[temp.getX()][temp.getY()] = false;
            moveList.remove(lastIndex); //removes last move from list
            knight.setPosition(moveList.get(moveList.size() - 1)); //the move we are reverting to is now the last move in the list

            updateHeuristic();
            updateHeuristic(temp);
        }
    }

    public void placeKnight(Position p) {
        knight.move(this, p);
    }

    public String getMoveListText() {
        System.out.println("[Model]: Calculating Text for Move List...");

        if(moveList.size() == 0) {
            return "Make a move...";
        }
        else {
            String text = "Move " + moveList.size() + "\n";

            for(int i = 0; i < moveList.size(); i++) {
                text += (i + 1) + ": " + moveList.get(i).squareName() + "\n";
            }

            return text;
        }
    }

    ///print methods///

    //prints whether or not the square has been visited, with T = visited, F = not visited
    public void printVisitedSquares() {
        System.out.println("[Model]: Printing Visited Value for each Square to Console...");

        for(int i = 0; i < BOARD_SIZE; i++) {
            String line = "";

            for(int j = 0; j < BOARD_SIZE; j++) {
                line += (visitedSquare[j][i] ? "T" : "F") + (j == BOARD_SIZE - 1 ? "" : ",");
            }

            System.out.println(line);
        }

        System.out.println();
    }

    //prints the move list where the number is the sequence in which the moves were made
    public void printMoveList() {
        System.out.println("[Model]: Printing Move History to Console...");

        for(int i = 0; i < BOARD_SIZE; i++) {
            String line = "";

            for(int j = 0; j < BOARD_SIZE; j++) {
                if(moveList.size() > 0) {
                    for(int k = 0; k < moveList.size(); k++) {
                        if(moveList.get(k).equalTo(new Position(j,i))) {
                            line += k;
                        }
                        else {
                            line += "_";
                        }
                    }
                }
                else {
                    line += "_";
                }

                line += (j == BOARD_SIZE - 1 ? "" : ",");
            }

            System.out.println(line);
        }

        System.out.println();
    }

    //prints heuristic value for each square - i.e how many possible moves could be made from any given point
    public void printHeuristic() {
        System.out.println("[Model]: Printing Heuristic Values for Board to Console...");

        for(int i = 0; i < BOARD_SIZE; i++) {
            String line = "";

            for(int j = 0; j < BOARD_SIZE; j++) {
                line += heuristic[j][i] + (j == BOARD_SIZE - 1 ? "" : ",");
            }

            System.out.println(line);
        }

        System.out.println();
    }

    //prints a list of all positions a knight could move to from a specified starting position
    public void printPossibleMoves(Position p) {
        System.out.println("[Model]: Printing Possible Moves to Console...");

        ArrayList<Position> moves = knight.validMoves(this, p);
        System.out.println("Possible moves: " + moves.size());
        if(moves.size() > 0) {
            String line = "";

            for(Position move: moves) {
                line += move.squareName() + ", ";
            }

            line = line.substring(0,line.length() - 2);
            line += "\n";
            System.out.println(line);
        }
    }

    //prints a list of all positions a knight could move to from a specified starting position with heuristic score
    public void printHeuristicMoves(Position p) {
        System.out.println("[Model]: Printing Possible Moves w/ Heuristic Values to Console...");

        ArrayList<Position> moves = knight.validMoves(this, p);
        System.out.println("Possible moves: " + moves.size());
        if(moves.size() > 0) {
            String line = "";

            for(Position move: moves) {
                line += move.squareName() + " (" + heuristic[move.getX()][move.getY()] + ")" + ", ";
            }

            line = line.substring(0,line.length() - 2);
            line += "\n";
            System.out.println(line);
        }
    }

    public Knight getKnight() {
        return knight;
    }

    public void setKnight(Knight knight) {
        this.knight = knight;
    }

    public boolean[][] getVisitedSquare() {
        return visitedSquare;
    }

    public void setVisitedSquare(boolean[][] visitedSquare) {
        this.visitedSquare = visitedSquare;
    }

    public ArrayList<Position> getMoveList() {
        return moveList;
    }

    public void setMoveList(ArrayList<Position> moveList) {
        this.moveList = moveList;
    }
}

package gui;

import game.ChessParameters;
import game.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GBoardDisplay extends JPanel implements ChessParameters {

    private final String knightPath = "/assets/pieces/wn-transparent.png";
    private final ImageIcon knightIcon;
    private int width, height;
    private Point piecePt;

    private boolean [][] visited;
    private ArrayList<Position> possibleMoves;

    public GBoardDisplay() {
        System.out.println("[Board]: Initializing Board Display...");

        possibleMoves = new ArrayList<>();
        initializeVisited();

        knightIcon = new ImageIcon(getClass().getResource(knightPath));
        this.setPreferredSize(new Dimension(BOARD_SIZE_PX,BOARD_SIZE_PX));
        this.setMinimumSize(new Dimension(BOARD_SIZE_PX,BOARD_SIZE_PX));

        System.out.println("[Board]: Drawing Board...");
        this.repaint();
    }

    public void initializeVisited() {
        visited = new boolean[BOARD_SIZE][BOARD_SIZE];

        for(boolean[] row: visited) {
            Arrays.fill(row, false);
        }
    }

    public void paint(Graphics g) {
        fillSquares(g);
        drawLines(g);

        if(possibleMoves.size() > 0) {
            highlightPossibleMoves(g);
        }

        if(piecePt != null) {
            drawPiece(g);
        }
    }

    public void drawLines(Graphics g) {
        width = this.getWidth();
        height = this.getHeight();
        g.setColor(Color.BLACK);

        for(int i = 0; i <= BOARD_SIZE; i++) {
            int dw = i != BOARD_SIZE ? (width / BOARD_SIZE) * i : width;
            int dh = i != BOARD_SIZE ? (height / BOARD_SIZE) * i : height;

            g.drawLine(0,dh,width,dh);
            g.drawLine(dw,0,dw,height);
        }
    }

    public void fillSquares(Graphics g) {
        width = this.getWidth();
        height = this.getHeight();

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                int dw = width / BOARD_SIZE;
                int dh = height / BOARD_SIZE;

                Color c = (visited[i][j] ? Color.GRAY : (i % 2 == j % 2 ? Color.WHITE : Color.BLACK));

                g.setColor(c);
                g.fillRect(i * dw, j * dh, dw, dh);
            }
        }
    }

    public void drawPiece(Graphics g) {
        g.drawImage(knightIcon.getImage(), (int) piecePt.getX(), (int) piecePt.getY(), this);
    }

    public void highlightPossibleMoves(Graphics g) {
        int squareWidth = this.getWidth() / BOARD_SIZE;
        int squareHeight = this.getHeight() / BOARD_SIZE;

        for(Position p: possibleMoves) {
            g.setColor(Color.GREEN);
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            g.drawRect((p.getX() * squareWidth) + 2, (p.getY() * squareHeight) + 2, squareWidth - 2, squareHeight - 2);
        }
    }

    public Position getSquare(Point mp) {
        return new Position((int) mp.getX() * BOARD_SIZE / this.getWidth(), (int) mp.getY() * BOARD_SIZE / this.getHeight());
    }

    //returns top left point for piece icon to be centered within current square
    public Point snapInPlace(Point mp) {
        Position pos = getSquare(mp);
        return new Point((pos.getX() * this.getWidth() / BOARD_SIZE) + 5, (pos.getY() * this.getHeight() / BOARD_SIZE) + 5);
    }

    public Point snapInPlace(Position pos) {
        return new Point((pos.getX() * this.getWidth() / BOARD_SIZE) + 5, (pos.getY() * this.getHeight() / BOARD_SIZE) + 5);
    }

    public Point getPiecePt() {
        return piecePt;
    }

    public void setPiecePt(Point piecePt) {
        this.piecePt = piecePt;
    }

    public boolean[][] getVisited() {
        return visited;
    }

    public void setVisited(boolean[][] visited) {
        this.visited = visited;
    }

    public ArrayList<Position> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<Position> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}

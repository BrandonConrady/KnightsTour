package gui;

import game.KTBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KTGraphicsController {

    private KTBoard b;
    private JFrame v;
    private GBoardDisplay display;
    private JPanel sidePanel, buttonPanel;

    private JButton undoButton, resetButton;
    private JTextArea moveListArea;
    private JScrollPane moveListScroller;
    private ImageIcon icon, resetIcon, undoIcon;

    private Point previous, current;

    public KTGraphicsController(KTBoard model) {
        System.out.println("[ViewController]: Begin setup");

        this.b = model;
        this.v = new JFrame();

        iconSetUp();
        setUpGUI();

        System.out.println("[ViewController]: Setup complete!");
    }

    //pulls images from assets folder into imageicons for use in GUI
    public void iconSetUp() {
        System.out.println("[View]: Loading Icons...");

        icon = new ImageIcon(getClass().getResource("/assets/icons/knight_icon.png"));
        resetIcon = scaledImage("/assets/icons/reset.png", 30, 30);
        undoIcon = scaledImage("/assets/icons/previous.png", 30, 30);
    }

    public ImageIcon scaledImage(String path, int newWidth, int newHeight) {
        ImageIcon temp = new ImageIcon(getClass().getResource(path));
        Image img = temp.getImage();
        Image scaled = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public void setUpGUI() {
        System.out.println("[View]: Initializing GUI...");

        v.setTitle("Knight's Tour");
        v.setIconImage(icon.getImage());
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        v.setLayout(new BorderLayout(10,10));

        setUpComponents();
        addComponents();
        addListeners();

        v.setVisible(true);
        v.pack();
    }

    public void setUpComponents() {
        System.out.println("[View]: Initializing Components...");

        display = new GBoardDisplay();

        moveListArea = new JTextArea(8,8);
        moveListArea.setEditable(false);
        moveListArea.setText(b.getMoveListText());
        moveListArea.setDisabledTextColor(Color.BLACK);

        moveListScroller = new JScrollPane(moveListArea);
        moveListScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        moveListScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        moveListScroller.setPreferredSize(new Dimension(100, 400));

        undoButton = new JButton();
        undoButton.setIcon(undoIcon);
        undoButton.setDisabledIcon(undoIcon);
        undoButton.setPreferredSize(new Dimension(30,30));
        undoButton.setBackground(Color.BLACK);
        undoButton.setEnabled(false);

        resetButton = new JButton();
        resetButton.setIcon(resetIcon);
        resetButton.setDisabledIcon(resetIcon);
        resetButton.setPreferredSize(new Dimension(30,30));
        resetButton.setBackground(Color.BLACK);
        resetButton.setEnabled(false);

        sidePanel = new JPanel(new BorderLayout(0,10));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        buttonPanel = new JPanel(new GridLayout(1,2,10,0));
    }

    public void addComponents() {
        System.out.println("[View]: Adding Components to View...");

        buttonPanel.add(undoButton);
        buttonPanel.add(resetButton);

        sidePanel.add(moveListScroller);
        sidePanel.add(buttonPanel, BorderLayout.PAGE_END);

        v.add(display, BorderLayout.CENTER);
        v.add(sidePanel, BorderLayout.EAST);
    }

    public void addListeners() {
        System.out.println("[Controller]: Adding Listeners to GUI...");

        display.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(current == null) {
                    current = display.snapInPlace(e.getPoint());
                    previous = current;

                    b.placeKnight(display.getSquare(current));
                    updateGUI();
                    display.repaint();
                }
                else {
                    previous = e.getPoint();
                }
            }
        });

        display.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(inBounds(e.getPoint())) {
                    current = e.getPoint();

                    display.getPiecePt().translate(
                            (int) (current.getX() - previous.getX()),
                            (int) (current.getY() - previous.getY())
                    );
                    previous = current;
                    display.setVisited(b.getVisitedSquare());
                    display.repaint();
                }
            }
        });

        display.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(previous != null && current != null) {
                    Point mp = e.getPoint();

                    if(inBounds(mp)) {
                        if(b.getKnight().validMove(b, display.getSquare(mp))) {
                            b.getKnight().move(b, display.getSquare(current));

                            updateGUI();
                            display.repaint();

                            gameOverCheck();
                        }
                        else {
                            display.setPiecePt(display.snapInPlace(b.getKnight().getPosition()));
                            display.repaint();
                        }
                    }
                }
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }

    public void updateGUI() {
        System.out.println("[View]: Updating View...");

        display.setPiecePt(display.snapInPlace(current));
        display.setPossibleMoves(b.getKnight().validMoves(b));
        display.setVisited(b.getVisitedSquare());

        moveListArea.setText(b.getMoveListText());

        undoButton.setEnabled(b.getMoveList().size() > 1);
        resetButton.setEnabled(b.getMoveList().size() > 0);
    }

    //checks if input point is over the 50x50 square shape of the piece icon
    public boolean inBounds(Point p) {
        return p.getX() - display.getPiecePt().getX() >= 0 && p.getX() - display.getPiecePt().getX() <= 50
                && p.getY() - display.getPiecePt().getY() >= 0 && p.getY() - display.getPiecePt().getY() <= 50;
    }

    public void reset() {
        System.out.println("[Controller]: Resetting Game...");

        current = null;
        previous = null;

        b.resetGame();
        resetGUI();
    }

    public void resetGUI() {
        System.out.println("[View]: Resetting GUI...");

        display.setPiecePt(null);
        display.setVisited(b.getVisitedSquare());
        display.getPossibleMoves().clear();
        display.repaint();

        moveListArea.setText(b.getMoveListText());

        undoButton.setEnabled(false);
    }

    public void undo() {
        System.out.println("[Controller]: Undoing Move...");

        b.undoMove();

        updateGUI();
        display.setPiecePt(display.snapInPlace(b.getKnight().getPosition()));
        display.repaint();
    }

    public void gameOverCheck() {
        System.out.println("[Controller]: Checking for Game Over...");

        int validMoveCount = b.getKnight().validMoves(b).size();

        if(validMoveCount == 0) {
            System.out.println("[Controller]: No Moves Left...");
            String message;

            if(b.getMoveList().size() == 64) {
                message = "Tour complete!";
            }
            else {
                message = "No valid moves!";
            }

            System.out.println("[Controller]: Game Over! " + message);
            gameOver(message);
        }
        else {
            System.out.println("[Controller]: Valid Moves: " + validMoveCount);
        }
    }

    public void gameOver(String message) {
        int choice = JOptionPane.showConfirmDialog(v, message + " Try again?", "Game Over", JOptionPane.YES_NO_OPTION);

        if(choice == JOptionPane.YES_OPTION) {
            reset();
        }
    }
}

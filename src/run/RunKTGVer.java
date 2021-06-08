package run;

import game.KTBoard;
import gui.KTGraphicsController;

import javax.swing.*;

/*
Runs version of Knight's Tour using Java Swing Graphics
 */
public class RunKTGVer {
    public static void main(String[] args) {
        System.out.println("[Main]: Program Start");
        KTBoard model = new KTBoard();
        new KTGraphicsController(model);
    }
}

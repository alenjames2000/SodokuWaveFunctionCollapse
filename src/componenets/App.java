package componenets;

import javax.swing.*;

public class App {
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Sodoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add Board
        Board board = new Board();
        frame.add(board);
        frame.addKeyListener(board);

        //Display the window.
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

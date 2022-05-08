package componenets;

import javax.swing.*;

public class SodokuMain {
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Sodoku");
        frame.setSize(640,480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        label.setBounds(300,300,10,10);
        frame.add(label);

        //Display the window.
//        frame.pack();
        frame.setLayout(null);
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

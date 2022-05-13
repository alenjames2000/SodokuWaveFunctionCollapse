package componenets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Board extends JPanel implements ActionListener, KeyListener{
    //Control Refresh Rate
    private final int DELAY = 17;

    //Board Size
    private static final int width = 576;
    private static final int height = 576;

    //Relevant Objects
    private Timer timer;
    private SodukuBoard tiles;

    public Board(){
        //Set background and size
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(255,255,255));

        //Create relevant objects
        tiles = new SodukuBoard(40);

        //Start timer
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Arrays.stream(tiles.getBoard()).flatMap(Arrays::stream).forEach(x -> x.draw(g, this));
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){

    }

    @Override
    public void keyReleased(KeyEvent e){

    }
}

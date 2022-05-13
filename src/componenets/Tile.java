package componenets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Tile {
    //Instance variables
    private static BufferedImage[] images = {
            loadImage("resources/Tile.png"),  //0
            loadImage("resources/1.png"),     //1
            loadImage("resources/2.png"),     //2
            loadImage("resources/3.png"),     //3
            loadImage("resources/4.png"),     //4
            loadImage("resources/5.png"),     //5
            loadImage("resources/6.png"),     //6
            loadImage("resources/7.png"),     //7
            loadImage("resources/8.png"),     //8
            loadImage("resources/9.png"),     //9
            loadImage("resources/1m.png"),    //10
            loadImage("resources/2m.png"),    //11
            loadImage("resources/3m.png"),    //12
            loadImage("resources/4m.png"),    //13
            loadImage("resources/5m.png"),    //14
            loadImage("resources/6m.png"),    //15
            loadImage("resources/7m.png"),    //16
            loadImage("resources/8m.png"),    //17
            loadImage("resources/9m.png"),    //18
    };
    private Point pos;
    private int width = 64;
    private int height = 64;
    int value = 0;
    boolean set = false;
    ArrayList<Integer> possibilites = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));

    //Constructors
    Tile(int x, int y){
        pos = new Point(x, y);
    }

    Tile(int x, int y, int value){
        pos = new Point(x,y);
        this.value = value;
    }

    Tile(Tile tile){
        this.pos = new Point(tile.getPos());
        this.value = tile.getValue();
        this.set = tile.isSet();
        this.possibilites = tile.getPossibilites();
    }

    //Property Modifiers
    public void setValue(int value){
        set = true;
        this.value = value;
    }

    public void resetValue(){
        value = 0;
        set = false;
    }

    public void removePossibility(int toRemove){
        if(!set && value == 0){
            possibilites.remove((Integer)toRemove);
        }
    }

    public void setSetTrue(){
        set = true;
    }

    public void reset(){
        set = false;
        possibilites = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    private static BufferedImage loadImage(String path){
        try{
            return ImageIO.read(new File(path));
        }
        catch(IOException e){
            throw new RuntimeException("Unable to open image: " + path + "\n" + e.getMessage());
        }
    }

    // Property Getters
    public int getValue(){
        return value;
    }

    public ArrayList<Integer> getPossibilites(){
        return possibilites;
    }

    public int getPossibilitySize(){
        return possibilites.size();
    }

    public boolean isSet(){
        return set;
    }

    public Point getPos(){
        return this.pos;
    }

    //Image Methods
    public void draw(Graphics g, ImageObserver observer){
        g.drawImage(images[0], pos.x*width, pos.y*height, observer);
        if(this.set){
            g.drawImage(images[value], pos.x*width, pos.y*height, observer);
        }
        else{
            possibilites.forEach(x -> g.drawImage(images[x+9], pos.x*width, pos.y*height, observer));
        }

    }
}

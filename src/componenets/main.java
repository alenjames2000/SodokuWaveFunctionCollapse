package componenets;

import componenets.SodukuBoard;

public class main {
    public static void main(String args[]){
        SodukuBoard myBoard = new SodukuBoard(new int[]{
                0,4,0,8,0,0,0,0,6,
                0,0,1,0,0,6,0,0,3,
                0,0,6,3,0,9,8,0,0,
                2,5,0,6,0,3,0,0,0,
                0,0,0,0,0,0,0,0,0,
                0,8,7,0,0,0,0,4,0,
                0,0,0,0,9,0,7,0,0,
                0,0,0,0,0,4,0,1,0,
                0,0,0,0,0,2,0,0,5
        });
        myBoard.printBoard();
        myBoard.solve();
        System.out.println();
        myBoard.printBoard();
    }
}

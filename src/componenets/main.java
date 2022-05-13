package componenets;

import componenets.SodukuBoard;

public class main {
    public static void main(String args[]){
        SodukuBoard myBoard = new SodukuBoard(new int[]{
                0,0,0,0,0,8,0,0,0,
                3,0,0,4,2,0,1,0,0,
                0,0,9,5,6,1,0,0,4,
                2,0,6,9,0,0,5,3,1,
                1,0,0,0,0,7,6,0,9,
                9,0,0,6,0,0,0,0,0,
                0,0,0,0,0,0,7,5,3,
                0,0,1,0,3,0,4,0,0,
                6,0,0,0,0,0,2,0,0
        });
        //SodukuBoard myBoard = new SodukuBoard(40)
        myBoard.printBoard();
        myBoard.solve();
        System.out.println();
        myBoard.printBoard();
    }
}

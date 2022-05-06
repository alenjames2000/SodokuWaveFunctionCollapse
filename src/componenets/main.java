package componenets;

import componenets.SodukuBoard;

public class main {
    public static void main(String args[]){
        SodukuBoard myBoard = new SodukuBoard(30);
        myBoard.printBoard();
        myBoard.solve();
        System.out.println();
        myBoard.printBoard();
    }
}

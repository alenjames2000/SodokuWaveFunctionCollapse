package componenets;

import java.util.*;

public class SodukuBoard {
    //Store Board Information
    private Tile[][] board = new Tile[9][9];

    //Nested class to repersent all the tiles
    class Tile{
        //Instance variables
        int value = 0;
        boolean set = false;
        ArrayList<Integer> possibilites = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        //Constructors
        Tile(){

        }

        Tile(int value){
            this.value = value;
        }

        Tile(Tile tile){
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
    }

    //Board Constructors
    SodukuBoard(int remove){
        populateBoard();
        while(createSolution() != 0){
            resetTiles();
        };
        removeNumbers(remove);
    }

    SodukuBoard(int [] boardV) {
        populateBoard(boardV);
    }

    SodukuBoard(SodukuBoard boardV) {
        revert(boardV);
    }

    //Getters
    Tile[][] getBoard(){
        return board;
    }

    //Reverts board to another given board state
    void revert(SodukuBoard boardNew){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                this.board[i][j] = new Tile(boardNew.getBoard()[i][j]);
            }
        }
    }

    //Populate functions which initialize the board with or without values
    void populateBoard(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                board[i][j] = new Tile();
            }
        }
    }

    void populateBoard(int[] boardValue){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                board[i][j] = new Tile(boardValue[i*9+j]);
            }
        }
    }

    // Propagates given choice for the wave function
    int propagte(Integer[] location){
        int toRemove = board[location[0]][location[1]].getValue();
        for(int i=0; i<9; i++){
            board[location[0]][i].removePossibility(toRemove);
            if(board[location[0]][i].getPossibilitySize() == 0){
                return -1;
            }
        }

        for(int i=0; i<9; i++){
            board[i][location[1]].removePossibility(toRemove);
            if(board[i][location[1]].getPossibilitySize() == 0){
                return -1;
            }
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[location[0]/3*3+i][location[1]/3*3+j].removePossibility(toRemove);
                if(board[location[0]/3*3+i][location[1]/3*3+j].getPossibilitySize() == 0){
                    return -1;
                }
            }
        }

        return 0;
    }

    //Get lowest entropy tiles locations from the board
    ArrayList<Integer[]> getLowestEntropy(){
        ArrayList<Integer[]> toReturn = new ArrayList<Integer[]>();
        int lowest = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(!board[i][j].isSet()){
                    if(toReturn.isEmpty()){
                        toReturn.add(new Integer[]{i,j});
                        lowest = board[i][j].getPossibilitySize();
                    }
                    else{
                        if(board[i][j].getPossibilitySize() < lowest){
                            toReturn.clear();
                            toReturn.add(new Integer[]{i,j});
                            lowest = board[i][j].getPossibilitySize();
                        }
                        else if(board[i][j].getPossibilitySize() == lowest){
                            toReturn.add(new Integer[]{i,j});
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    // Recursive function that populates the board with values
    int createSolution(){
        ArrayList<Integer[]> possibilites = getLowestEntropy();
        Collections.shuffle(possibilites);
        SodukuBoard temp = new SodukuBoard(this);
        if(possibilites.size() == 0){
            return 0;
        }
        else {
            for (int i = 0; i < possibilites.size(); i++) {
                for (int k = 0; k < board[possibilites.get(i)[0]][possibilites.get(i)[1]].getPossibilites().size(); k++) {
                    ArrayList<Integer> x = new ArrayList<Integer>(board[possibilites.get(i)[0]][possibilites.get(i)[1]].getPossibilites());
                    Collections.shuffle(x);
                    board[possibilites.get(i)[0]][possibilites.get(i)[1]].setValue(x.get(k));
                    if (propagte(possibilites.get(i)) == -1) {
                        revert(temp);
                    } else {
                        if (createSolution() == 0) {
                            return 0;
                        }
                    }
                }
            }
        }
        revert(temp);
        return -1;
    }

    // Removes values from the board to create a board to solve
    void removeNumbers(int remove){
        Random gen = new Random();
        ArrayList<ArrayList<Integer>> previous = new ArrayList<ArrayList<Integer>>();
        for(int i=0; i<remove; i++){
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(gen.nextInt(board.length));
            temp.add(gen.nextInt(board[0].length));
            if(!previous.contains(temp)){
                previous.add(temp);
                board[temp.get(0)][temp.get(1)].resetValue();
            }
            else{
                i--;
            }
        }
    }

    //Sets all tiles to not sets and resets the possibilites for each tile
    void resetTiles(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                board[i][j].reset();
            }
        }
    }

    //Propagates all values that have been set on a board
    void propagateSet(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j].getValue() != 0){
                    board[i][j].setSetTrue();
                    propagte(new Integer[]{i,j});
                }
            }
        }
    }

    //Solves the current board
    public void solve(){
        int i = 1;
        do{
            resetTiles();
            propagateSet();
            System.out.println(i++);
        }while(createSolution() != 0);
    }

    //Prints the current board
    public void printBoard(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                System.out.print(board[i][j].getValue());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    //Converts the board to an array of ints
    public ArrayList<Integer> toArray(){
        ArrayList<Integer> toReturn = new ArrayList<Integer>();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                toReturn.add(board[i][i].getValue());
            }
        }
        return toReturn;
    }
}

package componenets;

import java.util.*;

public class SodukuBoard {
    private Tile[][] board = new Tile[9][9];

    class Tile{
        int value = 0;
        boolean set = false;
        ArrayList<Integer> possibilites = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        Tile(){

        }

        Tile(Tile tile){
            this.value = tile.getValue();
            this.set = tile.isSet();
            this.possibilites = tile.getPossibilites();
        }

        public void setValue(int value){
            set = true;
            this.value = value;
        }

        public void resetValue(){
            value = 0;
        }

        public void removePossibility(int toRemove){
            if(!set){
                possibilites.remove((Integer)toRemove);
            }
        }

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

        public void setSetTrue(){
            set = true;
        }

        public void reset(){
            set = false;
            possibilites = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        }
    }

    SodukuBoard(int remove){
        populateBoard();
        while(createSolution() != 0){
            resetTiles();
        };
        removeNumbers(remove);
    }

    SodukuBoard(SodukuBoard board) {
        revert(board);
    }

    void revert(SodukuBoard board){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                this.board[i][j] = new Tile(board.getBoard()[i][j]);
            }
        }
    }

    Tile[][] getBoard(){
        return board;
    }

    void populateBoard(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                board[i][j] = new Tile();
            }
        }
    }

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
                    board[possibilites.get(i)[0]][possibilites.get(i)[1]].setValue(board[possibilites.get(i)[0]][possibilites.get(i)[1]].getPossibilites().get(k));
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
        return -1;
    }

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

    void resetTiles(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                board[i][j].reset();
            }
        }
    }

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

    public void solve(){
        do{
            resetTiles();
            propagateSet();
        }while(createSolution() != 0);
    }

    public void printBoard(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                System.out.print(board[i][j].getValue());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

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

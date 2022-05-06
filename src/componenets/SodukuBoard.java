package componenets;

import java.util.*;

public class SodukuBoard {
    private Tile[][] board = new Tile[9][9];

    class Tile{
        int value = 0;
        boolean set = false;
        ArrayList<Integer> possibilites = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        public int setValue(Random gen){
            int poss = possibilites.size();
            if(poss > 0){
                value = possibilites.get(gen.nextInt(poss));
                set = true;
                return 0;
            }
            return -1;
        }

        public void setValue(){
            value = 0;
        }

        public void removePossibility(int toRemove){
            possibilites.remove((Integer)toRemove);
        }

        public int getValue(){
            return value;
        }

        public int getPossibilitySize(){
            return possibilites.size();
        }

        public boolean isSet(){
            return set;
        }
    }

    void populateBoard(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                board[i][j] = new Tile();
            }
        }
    }

    void propagte(Integer[] location){
        int toRemove = board[location[0]][location[1]].getValue();
        for(int i=0; i<9; i++){
            board[location[0]][i].removePossibility(toRemove);
        }

        for(int i=0; i<9; i++){
            board[i][location[1]].removePossibility(toRemove);
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[location[0]/3*3+i][location[1]/3*3+j].removePossibility(toRemove);
            }
        }
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

    void createSolution(){
        boolean created = false;
        while(!created){
            created = true;
            populateBoard();
            Random gen = new Random();
            Integer[] location = {gen.nextInt(board.length), gen.nextInt(board[0].length)};
            board[location[0]][location[1]].setValue(gen);
            propagte(location);
            for(int i=0; i<80; i++){
                ArrayList<Integer[]> possibilites = getLowestEntropy();
                location = possibilites.get(gen.nextInt(possibilites.size()));
                int success = board[location[0]][location[1]].setValue(gen);
                if(success == -1){
                    created = false;
                    break;
                }
                propagte(location);
            }
        }
    }

    void removeNumbers(int remove){
        Random gen = new Random();
        for(int i=0; i<remove; i++){
            Integer[] location = {gen.nextInt(board.length), gen.nextInt(board[0].length)};
            board[location[0]][location[1]].setValue();
        }
    }

    SodukuBoard(int remove){
        createSolution();
        removeNumbers(remove);
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
}
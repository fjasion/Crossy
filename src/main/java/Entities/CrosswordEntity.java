package Entities;

import Containers.Position;
import Enums.Orientation;

import java.util.*;

public class CrosswordEntity {
    final int boardSize;
    Map<ClueEntity,Integer> clues;
    Map<Integer,Character> board;

    public CrosswordEntity(int boardSize){
        this.boardSize = boardSize;
        clues = new HashMap<>();
        board = new HashMap<>();
    }

    public void construct(CrosswordDictionary dictionary,int expectedClues){
        //Random seedGen = new Random();
        //long seed = seedGen.nextLong();
        Random random = new Random(0);
        //System.err.println(seed);
        int cls=0;
        int nextNo=1;
        Orientation currOrientation = Orientation.HORIZONTAL;
        while(cls<expectedClues){
            DictionaryEntity temp = dictionary.getRandom(random.nextLong());
            int tries=0;
            while(tries<200) {
                Position position = Position.randomPosition(random,boardSize,currOrientation);
                if(isNewWordLegal(temp,position)){
                    //System.err.println(temp.getWord() + " " + rx + " " + ry + " " + currOrientation);
                    int localNo = nextNo;
                    if(clues.containsKey(new ClueEntity(temp,position.getRow(),position.getColumn(),currOrientation.returnOpposite()))) {
                        localNo = clues.get(new ClueEntity(temp, position.getRow(), position.getColumn(), currOrientation.returnOpposite()));
                        nextNo--;
                    }
                    nextNo++;
                    clues.put(new ClueEntity(temp,position),localNo);
                    insertIntoBoard(temp,position);
                    currOrientation = currOrientation.returnOpposite();
                    cls++;
                    break;
                }
                tries++;
            }
        }
    }

    private boolean isNewWordLegal(DictionaryEntity entity,Position position){
        int rx = position.getRow();
        int ry = position.getColumn();
        int rr = position.getOrientation() == Orientation.HORIZONTAL?0:1;
        if(clues.containsKey(new ClueEntity(entity,rx,ry,position.getOrientation())))
            return false;
        if(rr==0 && board.containsKey((rx)*boardSize+ry-1))
            return false;
        if(rr==1 && board.containsKey((rx-1)*boardSize+ry))
            return false;

        for(int i=0;i<entity.getWord().length();i++){
            if(rx >= boardSize || ry >= boardSize)
                return false;
            if(board.containsKey(rx* boardSize +ry) && board.get(rx* boardSize +ry) != entity.getWord().charAt(i))
                return false;
            else if(!board.containsKey(rx*boardSize+ry)){
                if(rr==0){
                    if(board.containsKey((rx-1)*boardSize+ry) || board.containsKey((rx+1)*boardSize+ry))
                        return false;
                }
                else{
                    if(board.containsKey((rx)*boardSize+ry-1) || board.containsKey((rx)*boardSize+ry+1))
                        return false;
                }
            }
            if(rr==0)
                ry++;
            else
                rx++;
        }

        if(rr==0 && board.containsKey((rx)*boardSize+ry))
            return false;
        if(rr==1 && board.containsKey((rx)*boardSize+ry))
            return false;

        return true;
    }

    private void insertIntoBoard(DictionaryEntity entity,Position position){
        int rx = position.getRow();
        int ry = position.getColumn();
        for(int i=0;i<entity.getWord().length();i++){
            if(!board.containsKey(rx* boardSize +ry))
                board.put(rx* boardSize +ry,entity.getWord().charAt(i));
            if(position.getOrientation()==Orientation.HORIZONTAL)
                ry++;
            else
                rx++;
        }
    }

    public void printSolvedBoard(){
        for(int i = 0; i< boardSize; i++){
            for(int j = 0; j< boardSize; j++){
                if(board.containsKey(i* boardSize +j))
                    System.out.print(board.get(i* boardSize +j));
                else
                    System.out.print('#');
            }
            System.out.println();
        }
    }

    public void printUnsolvedBoard(){
        for(int i = 0; i< boardSize; i++){
            for(int j = 0; j< boardSize; j++){
                if(board.containsKey(i* boardSize +j))
                    System.out.print('?');
                else
                    System.out.print('#');
            }
            System.out.println();
        }
    }

    public void printCrossword(){
        Map<Integer,Character> map = new HashMap<>(board);
        for (ClueEntity clue:clues.keySet()) {
            map.put(clue.getPosition().getIndex(boardSize),Character.forDigit(clues.get(clue),10));
        }
        for(int i = 0; i< boardSize; i++){
            for(int j = 0; j< boardSize; j++){
                if(map.containsKey(i* boardSize +j)) {
                    if (Character.isDigit(map.get(i * boardSize + j)))
                        System.out.print(map.get(i*boardSize+j));
                    else
                        System.out.print('?');
                }
                else
                    System.out.print('#');
            }
            System.out.println();
        }
        System.out.println("HORIZONTALY: ");
        for (ClueEntity clue:clues.keySet()) {
            if(clue.getPosition().getOrientation()==Orientation.HORIZONTAL){
                System.out.println(clues.get(clue) + ". " + clue.getDefinition());
            }
        }
        System.out.println("VERTICALY: ");
        for (ClueEntity clue:clues.keySet()) {
            if(clue.getPosition().getOrientation()==Orientation.VERTICAL){
                System.out.println(clues.get(clue) + ". " + clue.getDefinition());
            }
        }
    }

    public boolean isValidSolution(HashMap<Integer,Character> map) {
        return this.board.equals(map);
    }

}

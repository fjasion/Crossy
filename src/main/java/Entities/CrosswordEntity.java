package Entities;

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
        Random seedGen = new Random();
        long seed = seedGen.nextLong();
        Random random = new Random(seed);
        System.err.println(seed);
        int cls=0;
        int nextNo=1;
        while(cls<expectedClues){
            DictionaryEntity temp = dictionary.getRandom(random.nextLong());
            int tries=0;
            while(tries<200) {
                int rx = random.nextInt() % boardSize;
                if(rx<0)
                    rx+=boardSize;
                int ry = random.nextInt() % boardSize;
                if(ry<0)
                    ry+=boardSize;
                int rr = cls % 2;
                if(rr<0)
                    rr+=1;
                if(isNewWordLegal(temp,rx,ry,rr)){
                    System.err.println(temp.getWord() + " " + rx + " " + ry + " " + rr);
                    int localNo = nextNo;
                    if(clues.containsKey(new ClueEntity(temp,rx,ry,rr==0?1:0))) {
                        localNo = clues.get(new ClueEntity(temp, rx, ry, rr == 0 ? 1 : 0));
                        nextNo--;
                    }
                    nextNo++;
                    clues.put(new ClueEntity(temp,rx,ry,rr),localNo);
                    insertIntoBoard(temp,rx,ry,rr);
                    cls++;
                    break;
                }
                tries++;
            }
        }
    }

    private boolean isNewWordLegal(DictionaryEntity entity,int rx, int ry, int rr){
        if(clues.containsKey(new ClueEntity(entity,rx,ry,rr)))
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

    private void insertIntoBoard(DictionaryEntity entity,int rx, int ry, int rr){
        for(int i=0;i<entity.getWord().length();i++){
            if(!board.containsKey(rx* boardSize +ry))
                board.put(rx* boardSize +ry,entity.getWord().charAt(i));
            if(rr==0)
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
            map.put(clue.getX()*boardSize+clue.getY(),Character.forDigit(clues.get(clue),10));
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
            if(clue.getR()==0){
                System.out.println(clues.get(clue) + ". " + clue.getDefinition());
            }
        }
        System.out.println("VERTICALY: ");
        for (ClueEntity clue:clues.keySet()) {
            if(clue.getR()==1){
                System.out.println(clues.get(clue) + ". " + clue.getDefinition());
            }
        }
    }
}

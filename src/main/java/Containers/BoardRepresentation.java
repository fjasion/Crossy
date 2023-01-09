package Containers;

import Entities.ClueEntity;
import Enums.Orientation;
import Enums.ReprezentationType;
import Main.CONFIG;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BoardRepresentation {
    private int boardSize;
    private HashMap<Integer,Character> board;
    private ReprezentationType repType;

    public BoardRepresentation(BoardRepresentation br){
        this.boardSize = br.boardSize;
        this.board = new HashMap<>(br.board);
        this.repType = br.repType;
    }
    public BoardRepresentation(Map<ClueEntity,Integer> clues, int boardSize, ReprezentationType repType){
        this.boardSize = boardSize;
        this.repType = repType;
        board = new HashMap<>();
        if(clues != null)
            for(var x:clues.keySet()){
                insert(x,clues.get(x));
            }
    }

    public BoardRepresentation(int boardSize, ReprezentationType repType){
        this(null,boardSize,repType);
    }

    public boolean isInsertLegal(ClueEntity clueEntity){
        int rx = clueEntity.getPosition().getRow();
        int ry = clueEntity.getPosition().getColumn();
        int rr = clueEntity.getPosition().getOrientation() == Orientation.HORIZONTAL?0:1;
        //if(clues.containsKey(new ClueEntity(entity,rx,ry,position.getOrientation()))) //we will need to deal with that later - this is check for two words at the same place
            //return false;
        if(rr==0 && board.containsKey((rx)*boardSize+ry-1))
            return false;
        if(rr==1 && board.containsKey((rx-1)*boardSize+ry))
            return false;

        for(int i=0;i<clueEntity.getWord().length();i++){
            if(rx >= boardSize || ry >= boardSize)
                return false;
            if(board.containsKey(rx* boardSize +ry) && board.get(rx* boardSize +ry) != clueEntity.getWord().charAt(i))
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

    public void insert(int pos,char c){
        board.put(pos,c);
    }
    public void insert(ClueEntity clueEntity, Integer no){
        int rx = clueEntity.getPosition().getRow();
        int ry = clueEntity.getPosition().getColumn();
        for(int i=0;i<clueEntity.getWord().length();i++){
            if(!board.containsKey(rx* boardSize +ry)) {
                if (repType == ReprezentationType.SOLVED)
                    board.put(rx * boardSize + ry, clueEntity.getWord().charAt(i));
                else
                    board.put(rx*boardSize+ry,'?');
            }
            if(clueEntity.getPosition().getOrientation()== Orientation.HORIZONTAL)
                ry++;
            else
                rx++;
        }

        if(repType == ReprezentationType.UNSOLVED && no != null){
            board.put(clueEntity.getPosition().getIndex(boardSize),Character.forDigit(no,10));
        }
    }

    public void insert(ClueEntity clueEntity){
        insert(clueEntity,null);
    }

    public void print(){
        for(int i = 0; i< boardSize; i++){
            for(int j = 0; j< boardSize; j++){
                if(board.containsKey(i* boardSize +j))
                    System.out.print(board.get(i*boardSize+j));
                else
                    System.out.print(CONFIG.FILL_CHARACTER);
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardRepresentation that = (BoardRepresentation) o;
        return boardSize == that.boardSize && Objects.equals(board, that.board);
    }

    public Map<Integer,Character> getCharacterMap(){
        return new HashMap<>(board);
    }
}

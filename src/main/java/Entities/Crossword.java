package Entities;

import Containers.BoardRepresentation;
import Containers.Position;
import Enums.Orientation;
import Enums.ReprezentationType;

import java.io.Serializable;
import java.util.*;

public class Crossword implements Serializable {
    private final int boardSize;
    private HashMap<ClueEntity,Integer> clues;
    //private Map<Integer,Character> board;

    public Crossword(int boardSize){
        this(boardSize,new HashMap<>());
    }

    public Crossword(int boardSize,HashMap<ClueEntity,Integer> clues){
        this.boardSize = boardSize;
        this.clues = clues;
    }

    public void generateFromDictionary(CrosswordDictionary dictionary, int expectedClues){
        Map<Integer,Integer> indexes = new HashMap<>();
        //Map<Integer,Character> board = new HashMap<>();
        Random seedGen = new Random();
        long seed = seedGen.nextLong();
        Random random = new Random(seed);

        BoardRepresentation board = new BoardRepresentation(boardSize,ReprezentationType.SOLVED);

        int cls=0;
        int nextNo=1;
        Orientation currOrientation = Orientation.HORIZONTAL;
        while(cls<expectedClues){
            DictionaryEntity temp = dictionary.getRandom(random.nextLong());
            int tries=0;
            while(tries<200) {
                Position position = Position.randomPosition(random,boardSize,currOrientation);
                ClueEntity tempClue = new ClueEntity(temp,position);
                if(!isClueAtPosition(position) && board.isInsertLegal(tempClue)){
                    int localNo = nextNo;
                    if(indexes.containsKey(position.getIndex(boardSize))) {
                        localNo = indexes.get(position.getIndex(boardSize));
                        nextNo--;
                    }
                    nextNo++;
                    indexes.put(position.getIndex(boardSize),localNo);
                    clues.put(tempClue,localNo);
                    board.insert(tempClue);
                    currOrientation = currOrientation.returnOpposite();
                    cls++;
                    break;
                }
                tries++;
            }
        }
    }

    private boolean isClueAtPosition(Position position){
        for(ClueEntity clue : clues.keySet()){
            if(clue.getPosition().equals(position))
                return true;
        }
        return false;
    }
    public BoardRepresentation getBoardRepresentation(ReprezentationType repType){
        return new BoardRepresentation(clues,boardSize,repType);
    }

    public void printCrossword(){
        getBoardRepresentation(ReprezentationType.UNSOLVED).print();
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

    public boolean isValidSolution(BoardRepresentation boardRepresentation) {
        return this.getBoardRepresentation(ReprezentationType.SOLVED).equals(boardRepresentation);
    }

}

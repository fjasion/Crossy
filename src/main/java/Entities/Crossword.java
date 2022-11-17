package Entities;

import Containers.BoardRepresentation;
import Containers.Position;
import CustomExceptions.GenerationTimeExceeded;
import Enums.Orientation;
import Enums.ReprezentationType;
import Main.CONFIG;

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

    public void generateFromDictionary(CrosswordDictionary dict, int expectedClues) throws GenerationTimeExceeded {
        HashMap<ClueEntity,Integer> prevClues = new HashMap<>(clues);
        clues = new HashMap<>();
        //CrosswordDictionary dictionary = new CrosswordDictionary(dict);
        Map<Integer,Integer> indexes = new HashMap<>();
        Random seedGen = new Random();
        long seed = seedGen.nextLong();
        Random random = new Random(seed);
        BoardRepresentation board = new BoardRepresentation(boardSize,ReprezentationType.SOLVED);
        int generated = tryFillingRandomly(dict,indexes,board,random,expectedClues);
        if(generated == expectedClues)
            return;
        clues = prevClues;
        throw new GenerationTimeExceeded();
    }

    private int tryFillingBrutaly(CrosswordDictionary dictionary, Map<Integer,Integer> indexes, BoardRepresentation board, int expectedClues){
        int cls = clues.size();
        int nextNo = indexes.size();
        for(DictionaryEntity entity:dictionary.getWordEntityList()){
            //System.out.println(entity.getWord());
            for(int i=0;i<boardSize;i++){
                for(int j=0;j<boardSize;j++){
                    for (Orientation orientation:Orientation.values()){
                        Position position = new Position(i,j,orientation);
                        ClueEntity tempClue = new ClueEntity(entity,position);
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
                            cls++;
                            break;
                        }
                        if(cls == expectedClues){
                            System.err.println("Successfully bruted crossword");
                            return cls;
                        }
                    }
                }
            }
        }
        return cls;
    }
    private int tryFillingRandomly(CrosswordDictionary dict, Map<Integer,Integer> ind, BoardRepresentation brd, Random random, int expectedClues){
        int cntr = 0;
        int cls=0;
        CrosswordDictionary dictionary=null;
        Map<Integer,Integer> indexes=null;
        BoardRepresentation board=null;
        while(cntr < CONFIG.MAX_GENERATION_ATTEMPTS){
            dictionary = new CrosswordDictionary(dict);
            indexes = new HashMap<>();
            board = new BoardRepresentation(brd);
            clues = new HashMap<>();
            cls=0;
            int nextNo=1;
            Orientation currOrientation = Orientation.HORIZONTAL;
            int lclCntr = 0;
            while(cls<expectedClues && lclCntr<CONFIG.MAX_LOCAL_GENERATION_ATTEMPTS){
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
                        dictionary.removeWordEntity(temp);
                        currOrientation = currOrientation.returnOpposite();
                        cls++;
                        break;
                    }
                    tries++;
                }
                lclCntr++;
            }
            if(cls == expectedClues) {
                System.err.println("Successfully generated crossword");
                return cls;
            }
            cntr++;
        }
        dict = dictionary;
        ind = indexes;
        brd = board;
        return cls;
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

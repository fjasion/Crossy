package Containers;

import Enums.Orientation;

import java.util.Objects;
import java.util.Random;

public class Position {
    private int row,column;
    private Orientation orientation;
    public Position(int row, int column, Orientation orientation){
        this.row = row;
        this.column = column;
        this.orientation = orientation;
    }

    public Position(){
        this(-1,-1,null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column && orientation == position.orientation;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public Orientation getOrientation() {
        return orientation;
    }
    public int getIndex(int boardSize){
        return row*boardSize+column;
    }
    public static Position randomPosition(Random random, int boardSize, Orientation orientation){
        int row = random.nextInt() % boardSize;
        if(row<0)
            row+=boardSize;
        int column = random.nextInt() % boardSize;
        if(column<0)
            column+=boardSize;
        return new Position(row,column,orientation);
    }
}

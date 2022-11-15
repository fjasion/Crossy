package Containers;

import Enums.Orientation;

import java.util.Objects;

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
}

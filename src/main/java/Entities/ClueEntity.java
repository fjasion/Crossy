package Entities;

import Containers.Position;
import Enums.Orientation;

import java.util.Objects;

public class ClueEntity extends DictionaryEntity{
    private Position position;
    ClueEntity(DictionaryEntity dictionaryEntity,Position position){
        super(dictionaryEntity);
        this.position = position;
    }
    public ClueEntity(DictionaryEntity dictionaryEntity,int row, int column, Orientation orientation){
        super(dictionaryEntity);
        this.position = new Position(row,column,orientation);
    }
    public ClueEntity(){
        this(new DictionaryEntity(),-1,-1,null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClueEntity that = (ClueEntity) o;
        return Objects.equals(position, that.position);
    }

    public Position getPosition() {
        return position;
    }
}

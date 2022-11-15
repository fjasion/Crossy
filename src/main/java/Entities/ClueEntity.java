package Entities;

import java.util.Objects;

public class ClueEntity extends DictionaryEntity{
    public int getX() {
        return x;
    }

    public int getR() {
        return r;
    }

    public int getY() {
        return y;
    }

    private int x,y,r;//x,y of the beginning of the word on the board and rotation 0 - horizontal, 1 - vertical
    public ClueEntity(DictionaryEntity dictionaryEntity,int x, int y, int r){
        super(dictionaryEntity);
        this.x = x;
        this.y = y;
        this.r = r;
    }
    public ClueEntity(){
        this(new DictionaryEntity(),-1,-1,-1);
    }

    public void setCoordinates(int x, int y, int r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClueEntity that = (ClueEntity) o;
        return x == that.x && y == that.y && r == that.r;
    }
}

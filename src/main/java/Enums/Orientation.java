package Enums;

import java.io.Serializable;

public enum Orientation implements Serializable {
    HORIZONTAL,
    VERTICAL;
    public Orientation returnOpposite(){
        if(this == Orientation.HORIZONTAL)
            return Orientation.VERTICAL;
        return Orientation.HORIZONTAL;
    }
}

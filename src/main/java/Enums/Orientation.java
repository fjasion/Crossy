package Enums;

public enum Orientation {
    HORIZONTAL,
    VERTICAL;
    public Orientation returnOpposite(){
        if(this == Orientation.HORIZONTAL)
            return Orientation.VERTICAL;
        return Orientation.HORIZONTAL;
    }
}

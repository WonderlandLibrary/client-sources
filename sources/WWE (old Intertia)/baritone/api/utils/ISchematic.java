package baritone.api.utils;

public interface ISchematic
{
    default boolean inSchematic(final int n, final int n2, final int n3) {
        return n >= 0 && n < this.widthX() && n2 >= 0 && n2 < this.heightY() && n3 >= 0 && n3 < this.lengthZ();
    }
    
    awt desiredState(final int p0, final int p1, final int p2);
    
    int widthX();
    
    int heightY();
    
    int lengthZ();
}

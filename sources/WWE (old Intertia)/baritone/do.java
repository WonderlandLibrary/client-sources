package baritone;

import baritone.api.utils.*;

public final class do implements ISchematic
{
    private final int a;
    private final int b;
    private final int c;
    
    public do(final int a, final int b, final int c) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    @Override
    public final awt desiredState(final int n, final int n2, final int n3) {
        return aox.a.t();
    }
    
    @Override
    public final int widthX() {
        return this.a;
    }
    
    @Override
    public final int heightY() {
        return this.b;
    }
    
    @Override
    public final int lengthZ() {
        return this.c;
    }
}

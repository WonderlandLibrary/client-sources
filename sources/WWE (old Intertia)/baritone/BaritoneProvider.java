package baritone;

import baritone.api.*;
import java.util.*;
import baritone.api.cache.*;

public final class BaritoneProvider implements IBaritoneProvider
{
    private final a a;
    
    public BaritoneProvider() {
        super();
        this.a = new a();
    }
    
    @Override
    public final IBaritone getPrimaryBaritone() {
        return this.a;
    }
    
    @Override
    public final List<IBaritone> getAllBaritones() {
        return (List<IBaritone>)Collections.singletonList(this.a);
    }
    
    @Override
    public final IWorldScanner getWorldScanner() {
        return x.a;
    }
}

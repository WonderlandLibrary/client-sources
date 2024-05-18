package baritone.api.utils;

import baritone.api.pathing.calc.*;
import java.util.*;

public class PathCalculationResult
{
    private final IPath path;
    private final PathCalculationResult$Type type;
    
    public PathCalculationResult(final PathCalculationResult$Type pathCalculationResult$Type) {
        this(pathCalculationResult$Type, null);
    }
    
    public PathCalculationResult(final PathCalculationResult$Type type, final IPath path) {
        super();
        Objects.requireNonNull(type);
        this.path = path;
        this.type = type;
    }
    
    public final Optional<IPath> getPath() {
        return Optional.ofNullable(this.path);
    }
    
    public final PathCalculationResult$Type getType() {
        return this.type;
    }
}

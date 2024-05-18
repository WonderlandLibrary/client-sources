package baritone.api.cache;

import baritone.api.utils.*;
import java.util.*;

public interface IWorldScanner
{
    List<et> scanChunkRadius(final IPlayerContext p0, final List<aow> p1, final int p2, final int p3, final int p4);
    
    List<et> scanChunk(final IPlayerContext p0, final List<aow> p1, final amn p2, final int p3, final int p4);
}

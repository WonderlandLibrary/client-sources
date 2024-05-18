package baritone.api;

import java.util.*;
import baritone.api.cache.*;

public interface IBaritoneProvider
{
    IBaritone getPrimaryBaritone();
    
    List<IBaritone> getAllBaritones();
    
    default IBaritone getBaritoneForPlayer(final bud bud) {
        for (final IBaritone baritone : this.getAllBaritones()) {
            if (bud.equals((Object)baritone.getPlayerContext().player())) {
                return baritone;
            }
        }
        return null;
    }
    
    IWorldScanner getWorldScanner();
}

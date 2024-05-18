package baritone.api.utils;

import baritone.api.behavior.*;
import baritone.api.utils.input.*;

public interface IInputOverrideHandler extends IBehavior
{
    boolean isInputForcedDown(final Input p0);
    
    void setInputForceState(final Input p0, final boolean p1);
    
    void clearAllKeys();
}

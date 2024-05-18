package baritone.api;

import baritone.api.behavior.*;
import baritone.api.process.*;
import baritone.api.cache.*;
import baritone.api.pathing.calc.*;
import baritone.api.utils.*;
import baritone.api.event.listener.*;

public interface IBaritone
{
    void init();
    
    IPathingBehavior getPathingBehavior();
    
    ILookBehavior getLookBehavior();
    
    IFollowProcess getFollowProcess();
    
    IMineProcess getMineProcess();
    
    IBuilderProcess getBuilderProcess();
    
    ICustomGoalProcess getCustomGoalProcess();
    
    IGetToBlockProcess getGetToBlockProcess();
    
    IWorldProvider getWorldProvider();
    
    IPathingControlManager getPathingControlManager();
    
    IInputOverrideHandler getInputOverrideHandler();
    
    IPlayerContext getPlayerContext();
    
    IEventBus getGameEventHandler();
}

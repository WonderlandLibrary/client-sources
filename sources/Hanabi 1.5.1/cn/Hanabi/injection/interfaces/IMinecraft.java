package cn.Hanabi.injection.interfaces;

import net.minecraft.client.resources.*;
import net.minecraft.util.*;

public interface IMinecraft
{
    Session getSession();
    
    void setSession(final Session p0);
    
    LanguageManager getLanguageManager();
    
    Timer getTimer();
    
    void setRightClickDelayTimer(final int p0);
    
    void setClickCounter(final int p0);
    
    void runCrinkMouse();
}

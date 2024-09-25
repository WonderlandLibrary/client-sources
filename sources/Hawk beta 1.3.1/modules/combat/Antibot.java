package eze.modules.combat;

import eze.modules.*;
import eze.util.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.entity.*;
import java.util.*;

public class Antibot extends Module
{
    public float oldSens;
    public Timer timer;
    
    public Antibot() {
        super("Kill invisibles (Antibot)", 0, Category.COMBAT);
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
        this.toggled = false;
    }
    
    @Override
    public void onDisable() {
        this.toggled = true;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            for (final Object entity : this.mc.theWorld.loadedEntityList) {
                if (((Entity)entity).isInvisible() && entity != this.mc.thePlayer) {
                    this.mc.theWorld.removeEntity((Entity)entity);
                }
            }
        }
    }
}

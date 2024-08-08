package me.xatzdevelopments.xatz.client.Unused.superherofx;

import java.util.*;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.*;


public class FXManager
{
    private ArrayList<FX> fxList;
    
    public FXManager() {
        this.fxList = new ArrayList<FX>();
    }
    
    public void onRender() {
        for (final FX fx : this.fxList) {
            fx.onRender();
        }
    }
    
    public void onTick() {
        final Iterator iterator = this.fxList.iterator();
        while (iterator.hasNext()) {
            final FX fx = (FX) iterator.next();
            fx.onTick();
            if (fx.getTicksAlive() > fx.getMaxTicksAlive()) {
                iterator.remove();
            }
        }
    }
    
    public void addTextFx(final EntityLivingBase idk, final String text) {
        this.fxList.add(new TextFX(idk, text));
    }
    
    public void resetFxList() {
    	this.fxList.removeAll(fxList);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.misc;

import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import me.perry.mcdonalds.features.modules.Module;

public class PopCounter extends Module
{
    public static HashMap<String, Integer> TotemPopContainer;
    private static PopCounter INSTANCE;
    
    public PopCounter() {
        super("PopCounter", "Counts other players totem pops.", Category.MISC, true, false, false);
        this.setInstance();
    }
    
    public static PopCounter getInstance() {
        if (PopCounter.INSTANCE == null) {
            PopCounter.INSTANCE = new PopCounter();
        }
        return PopCounter.INSTANCE;
    }
    
    private void setInstance() {
        PopCounter.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        PopCounter.TotemPopContainer.clear();
    }
    
    public void onDeath(final EntityPlayer player) {
        if (!PopCounter.TotemPopContainer.containsKey(player.getName())) {
            return;
        }
        final int l_Count = PopCounter.TotemPopContainer.get(player.getName());
        PopCounter.TotemPopContainer.remove(player.getName());
        if (l_Count == 1) {
            return;
        }
    }
    
    public void onTotemPop(final EntityPlayer player) {
        if (fullNullCheck()) {
            return;
        }
        if (PopCounter.mc.player.equals((Object)player)) {
            return;
        }
        int l_Count = 1;
        if (PopCounter.TotemPopContainer.containsKey(player.getName())) {
            l_Count = PopCounter.TotemPopContainer.get(player.getName());
            PopCounter.TotemPopContainer.put(player.getName(), ++l_Count);
        }
        else {
            PopCounter.TotemPopContainer.put(player.getName(), l_Count);
        }
        if (l_Count == 1) {
            return;
        }
    }
    
    static {
        PopCounter.TotemPopContainer = new HashMap<String, Integer>();
        PopCounter.INSTANCE = new PopCounter();
    }
}

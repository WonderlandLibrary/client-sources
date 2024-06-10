package xyz.gucciclient.modules.mods.combat;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AntiiiiiiBot extends Module
{
    private BooleanValue hypixel;
    private BooleanValue mineplex;
    
    public AntiiiiiiBot() {
        super("AntiBot", 0, Category.COMBAT);
        this.hypixel = new BooleanValue("Hypixel", false);
        this.mineplex = new BooleanValue("Mineplex", false);
        this.addBoolean(this.hypixel);
        this.addBoolean(this.mineplex);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent ev3nt) {
        if (this.hypixel.getState()) {
            for (final Object object : this.mc.theWorld.playerEntities) {
                final EntityPlayer entityPlayer = (EntityPlayer)object;
                if (entityPlayer != null && entityPlayer != this.mc.thePlayer && entityPlayer.getDisplayName().getFormattedText().equalsIgnoreCase(entityPlayer.getName() + "§r") && !this.mc.thePlayer.getDisplayName().getFormattedText().equalsIgnoreCase(this.mc.thePlayer.getName() + "§r")) {
                    this.mc.theWorld.removeEntity((Entity)entityPlayer);
                }
            }
        }
        if (this.mineplex.getState()) {
            for (final Object object : this.mc.theWorld.playerEntities) {
                final EntityPlayer entityPlayer = (EntityPlayer)object;
                if (entityPlayer != null && entityPlayer != this.mc.thePlayer) {
                    if (entityPlayer.getName().startsWith("Body #")) {
                        this.mc.theWorld.removeEntity((Entity)entityPlayer);
                    }
                    if (entityPlayer.getMaxHealth() != 20.0f) {
                        continue;
                    }
                    this.mc.theWorld.removeEntity((Entity)entityPlayer);
                }
            }
        }
    }
}

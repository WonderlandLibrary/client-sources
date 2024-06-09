// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Eagle", description = "Sneaks on the edge of blocks", cat = Category.PLAYER)
public class Eagle extends Module
{
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public Eagle() {
        this.updateEventListener = (e -> this.mc.gameSettings.keyBindSneak.pressed = (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ)).getBlock() == Blocks.air));
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.thePlayer != null && !GameSettings.isKeyDown(this.mc.gameSettings.keyBindSneak)) {
            this.mc.gameSettings.keyBindSneak.pressed = false;
        }
    }
}

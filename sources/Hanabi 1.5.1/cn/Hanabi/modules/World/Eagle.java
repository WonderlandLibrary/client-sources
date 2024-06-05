package cn.Hanabi.modules.World;

import cn.Hanabi.modules.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import cn.Hanabi.events.*;
import net.minecraft.block.*;
import net.minecraft.client.settings.*;
import com.darkmagician6.eventapi.*;

public class Eagle extends Mod
{
    
    
    public Eagle() {
        super("Eagle", Category.WORLD);
    }
    
    public Block getBlock(final BlockPos blockPos) {
        return Eagle.mc.theWorld.getBlockState(blockPos).getBlock();
    }
    
    public Block getBlockUnderPlayer(final EntityPlayer entityPlayer) {
        return this.getBlock(new BlockPos(entityPlayer.posX, entityPlayer.posY - 1.0, entityPlayer.posZ));
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (this.getBlockUnderPlayer((EntityPlayer)Eagle.mc.thePlayer) instanceof BlockAir) {
            if (Eagle.mc.thePlayer.onGround) {
                KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), true);
            }
        }
        else if (Eagle.mc.thePlayer.onGround) {
            KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        }
    }
    
    public void onEnable() {
        Eagle.mc.thePlayer.setSneaking(false);
        super.onEnable();
    }
    
    public void onDisable() {
        KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        super.onDisable();
    }
}

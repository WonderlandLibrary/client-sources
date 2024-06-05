package me.mees.remix.modules.player;

import me.satisfactory.base.module.*;
import org.lwjgl.input.*;
import me.satisfactory.base.events.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import pw.stamina.causam.scan.method.model.*;

public class Eagle extends Module
{
    public Eagle() {
        super("Eagle", 0, Category.PLAYER);
    }
    
    @Override
    public void onDisable() {
        Eagle.mc.rightClickDelayTimer = 4;
        if (!Keyboard.isKeyDown(Eagle.mc.gameSettings.keyBindSneak.getKeyCode())) {
            Eagle.mc.gameSettings.keyBindSneak.pressed = false;
        }
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        if (Eagle.mc.thePlayer != null) {
            Eagle.mc.rightClickDelayTimer = 0;
            if (Eagle.mc.thePlayer.isCollided) {
                Eagle.mc.gameSettings.keyBindSneak.pressed = (Eagle.mc.theWorld.getBlockState(new BlockPos(Eagle.mc.thePlayer.posX, Eagle.mc.thePlayer.posY - 1.0, Eagle.mc.thePlayer.posZ)).getBlock() == Blocks.air);
            }
        }
    }
}

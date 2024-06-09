/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Movement;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class NoWeb
extends Module {
    public NoWeb() {
        super("NoWeb", "NoWeb", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        BlockPos block98 = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
        BlockPos block = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0, Minecraft.thePlayer.posZ);
        if ((NoWeb.mc.theWorld.getBlockState(block98).getBlock() == Blocks.web || NoWeb.mc.theWorld.getBlockState(block).getBlock() == Blocks.web) && NoWeb.mc.gameSettings.keyBindForward.pressed) {
            double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
            double speed = 0.38;
            Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
            Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
        }
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
}


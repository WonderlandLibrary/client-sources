package com.client.glowclient.modules.movement;

import com.client.glowclient.events.*;
import net.minecraft.util.math.*;
import com.client.glowclient.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class LiquidSpeed extends ModuleContainer
{
    public static final NumberValue waterSpeed;
    public static final NumberValue lavaSpeed;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final BlockPos blockPos = new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY + 0.4, Wrapper.mc.player.posZ);
        if (!ModuleManager.M("Freecam").k()) {
            if (Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.LAVA) {
                if (Wrapper.mc.gameSettings.keyBindForward.isKeyDown() || Wrapper.mc.gameSettings.keyBindRight.isKeyDown() || Wrapper.mc.gameSettings.keyBindLeft.isKeyDown() || Wrapper.mc.gameSettings.keyBindBack.isKeyDown()) {
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.motionX *= LiquidSpeed.lavaSpeed.k();
                    final EntityPlayerSP player2 = Wrapper.mc.player;
                    player2.motionZ *= LiquidSpeed.lavaSpeed.k();
                }
                if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                    Wrapper.mc.player.motionY = 0.06;
                }
                if (Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    Wrapper.mc.player.motionY = -0.14;
                }
            }
            if (Wrapper.mc.player.isInWater()) {
                if (Wrapper.mc.gameSettings.keyBindForward.isKeyDown() || Wrapper.mc.gameSettings.keyBindRight.isKeyDown() || Wrapper.mc.gameSettings.keyBindLeft.isKeyDown() || Wrapper.mc.gameSettings.keyBindBack.isKeyDown()) {
                    final EntityPlayerSP player3 = Wrapper.mc.player;
                    player3.motionX *= LiquidSpeed.waterSpeed.k();
                    final EntityPlayerSP player4 = Wrapper.mc.player;
                    player4.motionZ *= LiquidSpeed.waterSpeed.k();
                }
                if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                    final EntityPlayerSP player5 = Wrapper.mc.player;
                    player5.motionY *= LiquidSpeed.waterSpeed.k() / 1.2;
                }
            }
        }
    }
    
    public LiquidSpeed() {
        super(Category.MOVEMENT, "LiquidSpeed", false, -1, "Go faster in lava/water");
    }
    
    static {
        lavaSpeed = ValueFactory.M("LiquidSpeed", "LavaSpeed", "Speed of lava travel", 1.775, 0.01, 1.0, 2.0);
        waterSpeed = ValueFactory.M("LiquidSpeed", "WaterSpeed", "Speed of water travel", 1.1, 0.01, 1.0, 2.0);
    }
    
    @Override
    public String M() {
        return new StringBuilder().insert(0, String.format("W:%.1f,", LiquidSpeed.waterSpeed.k())).append(String.format("L:%.1f", LiquidSpeed.lavaSpeed.k())).toString();
    }
}

/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;

public class AutoJump
extends Module {
    @Override
    public void onEvent(Event Nigga) {
        AutoJump Nigga2;
        if (Nigga instanceof EventUpdate && !Client.ghostMode && Nigga2.mc.thePlayer.onGround && (Nigga2.mc.thePlayer.moveForward != Float.intBitsToFloat(2.12494477E9f ^ 0x7EA8155D) || Nigga2.mc.thePlayer.moveStrafing != Float.intBitsToFloat(2.1191072E9f ^ 0x7E4F024F))) {
            BlockPos Nigga3 = new BlockPos(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY, Nigga2.mc.thePlayer.posZ);
            if (Minecraft.theWorld.getBlockState(Nigga3.offsetUp(2)).getBlock().isFullBlock()) {
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
                Nigga2.mc.thePlayer.jump();
            }
            if (Minecraft.theWorld.getBlockState(Nigga3.offsetSouth()).getBlock().isFullBlock() || Minecraft.theWorld.getBlockState(Nigga3.offsetNorth()).getBlock().isFullBlock() || Minecraft.theWorld.getBlockState(Nigga3.offsetEast()).getBlock().isFullBlock() || Minecraft.theWorld.getBlockState(Nigga3.offsetWest()).getBlock().isFullBlock() || Minecraft.theWorld.getBlockState(Nigga3.offsetNorth().offsetWest()).getBlock().isFullBlock() || Minecraft.theWorld.getBlockState(Nigga3.offsetNorth().offsetEast()).getBlock().isFullBlock() || Minecraft.theWorld.getBlockState(Nigga3.offsetSouth().offsetWest()).getBlock().isFullBlock() || Minecraft.theWorld.getBlockState(Nigga3.offsetSouth().offsetEast()).getBlock().isFullBlock() || !Minecraft.theWorld.getBlockState(Nigga3.offsetDown()).getBlock().isFullBlock() || Minecraft.theWorld.getBlockState(Nigga3.offsetUp()).getBlock().isFullBlock()) {
                Nigga2.mc.thePlayer.jump();
            }
        }
    }

    public AutoJump() {
        super(Qprot0.0("\ubc08\u71de\u8749\ua7eb\u9fd4\u3986\u8c22\ud074"), 0, Module.Category.MOVEMENT);
        AutoJump Nigga;
    }

    public static {
        throw throwable;
    }
}


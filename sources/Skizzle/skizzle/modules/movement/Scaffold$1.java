/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle.modules.movement;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.BlockUtil;
import skizzle.util.Timer;

public class Scaffold$1
extends Module {
    public Timer placeDelay;
    public long lastMS;
    public Timer timer = new Timer();
    public BooleanSetting swingItem;
    public String displayName;
    public NumberSetting extend;
    public BooleanSetting holdItem;

    public Scaffold$1() {
        super(Qprot0.0("\u1894\u71c8\u23da\ua7e2\u3272\u9d12\u8c23\u74e6"), 0, Module.Category.MOVEMENT);
        Scaffold$1 Nigga;
        Nigga.placeDelay = new Timer();
        Nigga.displayName = Qprot0.0("\u1894\u71c8\u23da\ua7e2\u3272\u9d12\u8c23\u74e6");
        Nigga.swingItem = new BooleanSetting(Qprot0.0("\u1894\u71dc\u23d2\ua7ea\u3273\u9d5d\u8c06\u74f6\u5707\u9933"), false);
        Nigga.holdItem = new BooleanSetting(Qprot0.0("\u188f\u71c4\u23d7\ua7e0\u3234\u9d3f\u8c23\u74ed\u5701\u9935"), true);
        Nigga.extend = new NumberSetting(Qprot0.0("\u1882\u71d3\u23cf\ua7e1\u327a\u9d19"), 1.0, 1.0, 8.0, 1.0);
        Nigga.lastMS = System.currentTimeMillis();
        Nigga.addSettings(Nigga.swingItem, Nigga.holdItem, Nigga.extend);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventMotion && !Client.ghostMode && Nigga.isPre()) {
            Scaffold$1 Nigga2;
            EventMotion Nigga3 = (EventMotion)Nigga;
            BlockPos Nigga4 = new BlockPos(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY, Nigga2.mc.thePlayer.posZ);
            BlockPos Nigga5 = new BlockPos(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY, Nigga2.mc.thePlayer.posZ);
            if (Nigga2.mc.thePlayer.moveForward != Float.intBitsToFloat(2.11841165E9f ^ 0x7E44657B) || Nigga2.mc.gameSettings.keyBindJump.getIsKeyPressed() || Nigga2.mc.thePlayer.moveStrafing != Float.intBitsToFloat(2.13166451E9f ^ 0x7F0E9E96)) {
                for (int Nigga6 = 9; Nigga6 > -1; --Nigga6) {
                    Block Nigga7;
                    if (Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga6) == null || !Minecraft.theWorld.isAirBlock(Nigga2.mc.thePlayer.getPosition().offset(EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYaw), 10)) || (Nigga7 = Block.getBlockFromItem(Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga6).getItem())) == null || !Nigga7.isFullBlock()) continue;
                    BlockPos Nigga8 = Nigga2.mc.thePlayer.getPosition();
                    if (Keyboard.isKeyDown((int)Nigga2.mc.gameSettings.keyBindSneak.getKeyCode())) {
                        KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindSneak.getKeyCode(), false);
                        Nigga4 = new BlockPos((double)Nigga4.getX(), (double)Nigga4.getY() - 0.0, (double)Nigga4.getZ());
                    }
                    int Nigga9 = 0;
                    if (Nigga2.mc.thePlayer.moveForward < Float.intBitsToFloat(2.13378406E9f ^ 0x7F2EF5CE)) {
                        Nigga9 = 180;
                    }
                    int Nigga10 = 0;
                    while ((double)Nigga10 < Nigga2.extend.getValue()) {
                        if (Minecraft.theWorld.isAirBlock(Nigga2.mc.thePlayer.getPosition().offset(EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYaw - (float)Nigga9), (int)Nigga2.extend.getValue()).offsetDown())) {
                            if (Nigga2.holdItem.isEnabled()) {
                                Nigga2.mc.thePlayer.inventory.currentItem = Nigga6;
                            }
                            if (Nigga2.swingItem.isEnabled()) {
                                Nigga2.mc.thePlayer.swingItem();
                            }
                            BlockPos Nigga11 = Nigga4.offset(EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYaw - (float)Nigga9), Nigga10).offsetDown();
                            Nigga2.mc.thePlayer.rotationYawHead = Nigga2.mc.thePlayer.rotationPitchHead = Float.intBitsToFloat(-1.07549222E9f ^ 0x7E454A67);
                            Nigga2.mc.thePlayer.rotationPitchHead = BlockUtil.getDirectionToBlock(Nigga11.getX(), Nigga11.getY(), Nigga11.getZ(), EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYaw - (float)Nigga9))[0];
                            Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Nigga4.offset(EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYaw - (float)Nigga9), Nigga10).offsetDown(), EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYaw - (float)Nigga9).getIndex(), Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga6), Float.intBitsToFloat(2.08772557E9f ^ 0x7C7029FF), Float.intBitsToFloat(2.10871398E9f ^ 0x7DB06BC7), Float.intBitsToFloat(2.13859021E9f ^ 0x7F784BFF)));
                        }
                        ++Nigga10;
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {
    }

    public static {
        throw throwable;
    }
}


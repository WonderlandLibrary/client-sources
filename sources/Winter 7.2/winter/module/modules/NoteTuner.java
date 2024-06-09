/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.tools.DocumentationTool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import winter.event.EventListener;
import winter.event.events.PacketEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.other.Timer;

public class NoteTuner
extends Module {
    private BlockPos curBlock;
    private boolean listen;
    private boolean listen1;
    private int findMode;
    private Timer time;
    DocumentationTool.Location l;
    private HashMap<Integer, Integer> map;
    private List<Integer> tuned;

    public NoteTuner() {
        super("Note Tuner", Module.Category.Other, -13970566);
        this.setBind(49);
        this.curBlock = null;
        this.findMode = 0;
        this.time = new Timer();
        this.l = null;
        this.map = new HashMap();
        this.tuned = new ArrayList<Integer>();
    }

    @Override
    public void onEnable() {
        this.map.clear();
        this.tuned.clear();
        this.listen = false;
        this.listen1 = false;
        this.time.reset();
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            int i2 = 0;
            while (i2 < 25) {
                float[] rotations;
                int startX = (int)Math.floor(this.mc.thePlayer.posX) - 2;
                int startY = (int)Math.floor(this.mc.thePlayer.posY) - 1;
                int startZ = (int)Math.floor(this.mc.thePlayer.posZ) - 2;
                int x2 = startX + i2 % 5;
                int z2 = startZ + i2 / 5;
                if (!this.map.containsKey(i2)) {
                    this.curBlock = new BlockPos(x2, startY, z2);
                    rotations = this.getBlockRotations(x2, startY, z2);
                    event.yaw = rotations[0];
                    event.pitch = rotations[1];
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
                    this.findMode = 0;
                } else if (!this.tuned.contains(i2)) {
                    this.curBlock = new BlockPos(x2, startY, z2);
                    rotations = this.getBlockRotations(x2, startY, z2);
                    event.yaw = rotations[0];
                    event.pitch = rotations[1];
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
                    this.findMode = 1;
                }
                ++i2;
            }
        } else if (!event.isPre()) {
            if (this.l != null) {
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
                this.l = null;
            }
            if (this.curBlock != null) {
                if (this.findMode == 0) {
                    if (this.time.hasPassed(200.0f)) {
                        this.mc.thePlayer.swingItem();
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.curBlock, EnumFacing.UP));
                        this.mc.thePlayer.swingItem();
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.curBlock, EnumFacing.UP));
                        this.curBlock = null;
                        this.listen = true;
                        this.time.reset();
                    }
                } else if (this.time.hasPassed(200.0f)) {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.curBlock, 1, this.mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
                    this.mc.thePlayer.swingItem();
                    this.curBlock = null;
                    this.listen1 = true;
                    this.time.reset();
                }
            }
        }
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if (event.isIncoming() && event.getPacket() instanceof S24PacketBlockAction) {
            S24PacketBlockAction packet = (S24PacketBlockAction)event.getPacket();
            int i2 = 0;
            while (i2 < 25) {
                int startX = (int)Math.floor(this.mc.thePlayer.posX) - 2;
                int startY = (int)Math.floor(this.mc.thePlayer.posY) - 1;
                int startZ = (int)Math.floor(this.mc.thePlayer.posZ) - 2;
                int x2 = startX + i2 % 5;
                int z2 = startZ + i2 / 5;
                BlockPos pos = packet.func_179825_a();
                if (pos.getY() == startY && pos.getX() == x2 && pos.getZ() == z2) {
                    this.map.put(i2, S24PacketBlockAction.field_148873_e);
                    if (this.listen) {
                        this.listen = false;
                    }
                    if (this.listen1) {
                        this.listen1 = false;
                    }
                    if (!this.tuned.contains(i2) && i2 == S24PacketBlockAction.field_148873_e) {
                        this.tuned.add(i2);
                        System.out.println(String.valueOf(String.valueOf(i2)) + " tuned" + S24PacketBlockAction.field_148873_e);
                    }
                }
                ++i2;
            }
        }
    }

    public float[] getBlockRotations(double x2, double y2, double z2) {
        double var4 = x2 - this.mc.thePlayer.posX + 0.5;
        double var5 = z2 - this.mc.thePlayer.posZ + 0.5;
        double var6 = y2 - (this.mc.thePlayer.posY + (double)this.mc.thePlayer.getEyeHeight() - 1.0);
        double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        float var8 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[]{var8, (float)(- Math.atan2(var6, var7) * 180.0 / 3.141592653589793)};
    }
}


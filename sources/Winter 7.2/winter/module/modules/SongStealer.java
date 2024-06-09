/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import winter.event.EventListener;
import winter.event.events.PacketEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class SongStealer
extends Module {
    private final File songDirectory;
    private BufferedWriter writer;
    private int delay;
    private boolean written;
    private float instrumentValue;
    private float range;
    private boolean legacy;

    public SongStealer() {
        super("Song Stealer", Module.Category.Other, -13970566);
        this.songDirectory = new File(Minecraft.getMinecraft().mcDataDir + "\\Winter\\songs\\");
        this.written = false;
        this.instrumentValue = -1.0f;
        this.range = 50.0f;
        this.legacy = true;
    }

    @Override
    public void onEnable() {
        try {
            this.writer = new BufferedWriter(new FileWriter(new File(this.songDirectory, String.format("stolen-%s.groovy", new SimpleDateFormat("HH-mm-ss").format(new Date())))));
            this.written = false;
            super.onEnable();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            this.writer.close();
            super.onDisable();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            System.out.println("1");
            if (!this.mc.isSingleplayer()) {
                ++this.delay;
            } else if (!this.mc.isGamePaused() && this.mc.thePlayer != null) {
                ++this.delay;
            }
        }
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if (event.isIncoming() && event.getPacket() instanceof S24PacketBlockAction) {
            S24PacketBlockAction action = (S24PacketBlockAction)event.getPacket();
            double distance = this.mc.thePlayer.getDistanceSq(S24PacketBlockAction.field_179826_a);
            if (distance > (double)(this.range * this.range) && this.range > 0.0f) {
                return;
            }
            try {
                boolean instrumentAllowed = false;
                if (this.delay > 0 && this.written) {
                    this.writer.write("rest " + this.delay);
                    this.writer.newLine();
                    this.writer.newLine();
                }
                if (!(this.legacy || action.getData1() != 1 && action.getData1() != 2 && action.getData1() != 3)) {
                    this.writer.write("drum " + (int)Math.ceil(action.getData2() / 5));
                    this.writer.newLine();
                    this.written = true;
                    this.delay = 0;
                } else {
                    this.writer.write("play " + action.getData2());
                    this.writer.newLine();
                    this.written = true;
                    this.delay = 0;
                }
            }
            catch (IOException instrumentAllowed) {
                // empty catch block
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


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class TotemPopESP
extends Module {
    List<Totempop> popList = new CopyOnWriteArrayList<Totempop>();
    private long time;
    private int color;
    static long staticTime;

    public TotemPopESP() {
        super("TotemPopESP", 0, Module.Category.RENDER);
        this.settings.add(new Settings("TimeMs", 3000.0f, 6000.0f, 800.0f, this));
    }

    @EventTarget
    public void onPacket(EventReceivePacket e) {
        EntityPlayer ent;
        SPacketEntityStatus packet;
        Packet packet2;
        if (this.actived && e.getPacket() != null && (packet2 = e.getPacket()) instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)packet2) != null && packet.getOpCode() == 35 && packet.getEntity(TotemPopESP.mc.world) != null && packet.getEntity(TotemPopESP.mc.world) instanceof EntityPlayer && packet.getEntity(TotemPopESP.mc.world) != null && (ent = (EntityPlayer)packet.getEntity(TotemPopESP.mc.world)) != null && ent.isEntityAlive()) {
            this.popList.add(new Totempop(ent.posX, ent.posY, ent.posZ, ent.limbSwingAmount, ent.rotationYaw, ent.rotationPitch, new EntityOtherPlayerMP(TotemPopESP.mc.world, new GameProfile(UUID.randomUUID(), " " + (int)(Math.random() * 9.99999999E8)))));
        }
    }

    @Override
    public void alwaysRender3DV2() {
        if (!this.actived) {
            return;
        }
        Vec3d fix = new Vec3d(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
        staticTime = System.currentTimeMillis();
        float maxMs = this.currentFloatValue("TimeMs");
        if (this.popList.size() > 0) {
            for (int i = 0; i < this.popList.size(); ++i) {
                if (this.popList.get(i) == null || !((float)this.popList.get(i).getTime() / maxMs >= 1.0f)) continue;
                this.popList.remove(i);
            }
            if (this.popList.size() != 0) {
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glDisable(3008);
                GL11.glBlendFunc(770, 1);
                GL11.glDisable(2929);
                GL11.glDisable(2896);
                GL11.glDisable(3553);
                GlStateManager.translate(-fix.xCoord, -fix.yCoord, -fix.zCoord);
            }
            for (Totempop pops : this.popList) {
                if (pops == null) continue;
                float age = (float)pops.getTime() / maxMs;
                if (pops.player == null) continue;
                pops.limbSwingAmount += (double)0.01f;
                pops.player.limbSwing = (float)pops.limbSwingAmount;
                pops.player.limbSwingAmount = (float)pops.limbSwingAmount;
                if ((double)age < 0.1) {
                    pops.player.rotationYaw = (float)pops.rotationYaw;
                    pops.player.rotationYawHead = (float)pops.rotationYaw;
                    pops.player.renderYawOffset = (float)pops.rotationYaw;
                    pops.player.rotationPitch = (float)pops.rotationPitch;
                    pops.player.rotationPitchHead = (float)pops.rotationPitch;
                }
                RenderUtils.glColor(ColorUtils.swapAlpha(ClientColors.getColor1(), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor1()) * MathUtils.clamp(1.0f - age, 0.0f, 1.0f) / 5.0f));
                if (pops.player == null) continue;
                RenderLivingBase.silentMode = true;
                TotemPopESP.mc.renderManager.doRenderEntityNoShadow(pops.player, pops.x, pops.y + (double)(age * 4.0f), pops.z, pops.player.rotationYaw, 2.0f, true);
                RenderLivingBase.silentMode = false;
            }
            if (this.popList.size() != 0) {
                GL11.glEnable(2896);
                GL11.glEnable(3008);
                GlStateManager.translate(fix.xCoord, fix.yCoord, fix.zCoord);
                GL11.glEnable(3553);
                GlStateManager.resetColor();
                GL11.glEnable(2929);
                GL11.glBlendFunc(770, 771);
                GL11.glPopMatrix();
            }
        }
    }

    public class Totempop {
        EntityOtherPlayerMP player;
        long time = System.currentTimeMillis();
        int color = ClientColors.getColor1();
        double x;
        double y;
        double z;
        double limbSwingAmount;
        double rotationYaw;
        double rotationPitch;

        public Totempop(double x, double y, double z, double limbSwingAmount, double rotationYaw, double rotationPitch, EntityOtherPlayerMP player) {
            this.x = Minecraft.player.posX;
            this.y = Minecraft.player.posY;
            this.z = Minecraft.player.posZ;
            this.x = x;
            this.y = y;
            this.z = z;
            this.limbSwingAmount = limbSwingAmount;
            this.rotationYaw = rotationYaw;
            this.rotationPitch = rotationPitch;
            this.player = player;
        }

        int getColor() {
            return this.color;
        }

        long getTime() {
            return staticTime - this.time;
        }
    }
}


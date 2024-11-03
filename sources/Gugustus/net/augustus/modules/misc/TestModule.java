package net.augustus.modules.misc;

import com.sun.javafx.geom.Vec2d;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventSendPacket;
import net.augustus.events.EventShaderRender;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.PlayerUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestModule extends Module {
    public BooleanValue grimFlyTest = new BooleanValue(69420420, "GrimFly", this , false);
    public BooleanValue flyPredict = new BooleanValue(208736, "FlyPredict", this , false);
    public final ColorSetting color = new ColorSetting(1, "Color", this, new Color(21, 121, 230));
    public BooleanValue blur = new BooleanValue(1337, "Blur", this , false);
    private final HashMap<Packet, Long> packets = new HashMap<>();
    private final java.util.ArrayList<Vec3> positions = new java.util.ArrayList<>();
    private Vec3 start = null;

    public TestModule() {
        super("TestModule", Color.RED, Categorys.MISC);
    }

    @Override
    public void onEnable() {
        start = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }
    @Override
    public void onPreDisable() {
        resetPackets();
    }
    @EventTarget
    public void onEvent3D(EventRender3D eventRender3D) {
        if(flyPredict.getBoolean()) {
            try {
                this.positions.clear();
                float velocity = (float) start.distance(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
                MovingObjectPosition m = null;
                EntityLivingBase thrower = mc.thePlayer;
                //float rotationYaw = thrower.prevRotationYaw + (thrower.rotationYaw - thrower.prevRotationYaw) * mc.getTimer().renderPartialTicks;
                float rotationYaw = (float) calculateYaw(mc.thePlayer.posZ, start.xCoord, start.zCoord, mc.thePlayer.posX);
                //float rotationPitch = thrower.prevRotationPitch + (thrower.rotationPitch - thrower.prevRotationPitch) * mc.getTimer().renderPartialTicks;
                double deltaY = start.yCoord - mc.thePlayer.posY;
                double deltaXZ = Math.sqrt((start.xCoord - mc.thePlayer.posX) * (start.xCoord - mc.thePlayer.posX)
                        + (start.zCoord - mc.thePlayer.posZ) * (start.zCoord - mc.thePlayer.posZ));

                float rotationPitch;

                if (deltaXZ != 0) {
                    double pitchRadians = Math.atan(deltaY / deltaXZ);
                    rotationPitch = (float) -Math.toDegrees(pitchRadians);
                } else {
                    // Handle the case when deltaXZ is zero to prevent division by zero
                    rotationPitch = 0.0f; // Set a default value or handle accordingly based on your requirements
                }
                double posX = thrower.lastTickPosX + (thrower.posX - thrower.lastTickPosX) * (double) mc.getTimer().renderPartialTicks;
                double posY = thrower.lastTickPosY + (double) thrower.getEyeHeight() + (thrower.posY - thrower.lastTickPosY) * (double) mc.getTimer().renderPartialTicks;
                double posZ = thrower.lastTickPosZ + (thrower.posZ - thrower.lastTickPosZ) * (double) mc.getTimer().renderPartialTicks;
                posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
                posY -= 0.1F;
                posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
                float multipicator = 10F;
                double motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * multipicator;
                double motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * multipicator;
                double motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI) * multipicator;
                float inaccuracy = 0.0F;
                Random rand = new Random();
                float ff = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                double x = motionX / (double) ff;
                double y = motionY / (double) ff;
                double z = motionZ / (double) ff;
                x += rand.nextGaussian() * 0.0075F * (double) inaccuracy;
                y += rand.nextGaussian() * 0.0075F * (double) inaccuracy;
                z += rand.nextGaussian() * 0.0075F * (double) inaccuracy;
                x *= velocity;
                y *= velocity;
                z *= velocity;
                motionX = x;
                motionY = y;
                motionZ = z;
                float prevRotationYaw = (float) (MathHelper.func_181159_b(x, z) * 180.0 / Math.PI);
                float prevRotationPitch = (float)(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI);
                boolean b = true;
                int ticksInAir = 0;

                while (b) {
                    if (ticksInAir > 300) {
                        b = false;
                    }

                    ++ticksInAir;
                    Vec3 vec3 = new Vec3(posX, posY, posZ);
                    Vec3 vec31 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
                    MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(vec3, vec31);

                    if (movingobjectposition != null) {
                        b = false;
                    }

                    m = movingobjectposition;
                    posX += motionX / 10;
                    posY += motionY / 10;
                    posZ += motionZ / 10;
                    float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
                    rotationYaw = (float) (MathHelper.func_181159_b(motionX, motionZ) * 180.0 / Math.PI);
                    rotationPitch = (float) (MathHelper.func_181159_b(motionY, f1) * 180.0 / Math.PI);

                    while (rotationPitch - prevRotationPitch < -180.0F) {
                        prevRotationPitch -= 360.0F;
                    }

                    while (rotationPitch - prevRotationPitch >= 180.0F) {
                        prevRotationPitch += 360.0F;
                    }

                    while (rotationYaw - prevRotationYaw < -180.0F) {
                        prevRotationYaw -= 360.0F;
                    }

                    while (rotationYaw - prevRotationYaw >= 180.0F) {
                        prevRotationYaw += 360.0F;
                    }

                    float f2 = 0.99F;
                    float f3 = 0.03F;

                    motionX *= f2;
                    motionY *= f2;
                    motionZ *= f2;
                    motionY -= f3;
                    this.positions.add(new Vec3(posX, posY, posZ));
                }

                if (this.positions.size() > 1) {
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(2848);
                    GL11.glDisable(3553);
                    GlStateManager.disableCull();
                    GL11.glDepthMask(false);
                    GL11.glColor4f(
                            (float) this.color.getColor().getRed() / 255.0F,
                            (float) this.color.getColor().getGreen() / 255.0F,
                            (float) this.color.getColor().getBlue() / 255.0F,
                            0.7F
                    );
                    GL11.glLineWidth((float) 5 / 2.0F);
                    Tessellator tessellator = Tessellator.getInstance();
                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    worldrenderer.begin(3, DefaultVertexFormats.POSITION);

                    for (Vec3 vec3 : this.positions) {
                        worldrenderer.pos(
                                        (double) ((float) vec3.xCoord) - mc.getRenderManager().getRenderPosX(),
                                        (double) ((float) vec3.yCoord) - mc.getRenderManager().getRenderPosY(),
                                        (double) ((float) vec3.zCoord) - mc.getRenderManager().getRenderPosZ()
                                )
                                .endVertex();
                    }

                    tessellator.draw();
                    if (m != null) {
                        GL11.glColor4f(
                                (float) this.color.getColor().getRed() / 255.0F,
                                (float) this.color.getColor().getGreen() / 255.0F,
                                (float) this.color.getColor().getBlue() / 255.0F,
                                0.3F
                        );
                        Vec3 hitVec = m.hitVec;
                        EnumFacing enumFacing1 = m.sideHit;
                        float minX = (float) (hitVec.xCoord - mc.getRenderManager().getRenderPosX());
                        float maxX = (float) (hitVec.xCoord - mc.getRenderManager().getRenderPosX());
                        float minY = (float) (hitVec.yCoord - mc.getRenderManager().getRenderPosY());
                        float maxY = (float) (hitVec.yCoord - mc.getRenderManager().getRenderPosY());
                        float minZ = (float) (hitVec.zCoord - mc.getRenderManager().getRenderPosZ());
                        float maxZ = (float) (hitVec.zCoord - mc.getRenderManager().getRenderPosZ());
                        if (enumFacing1 == EnumFacing.SOUTH) {
                            minX = (float) ((double) minX - 0.4);
                            maxX = (float) ((double) maxX + 0.4);
                            minY = (float) ((double) minY - 0.4);
                            maxY = (float) ((double) maxY + 0.4);
                            maxZ = (float) ((double) maxZ + 0.02);
                            minZ = (float) ((double) minZ + 0.05);
                        } else if (enumFacing1 == EnumFacing.NORTH) {
                            minX = (float) ((double) minX - 0.4);
                            maxX = (float) ((double) maxX + 0.4);
                            minY = (float) ((double) minY - 0.4);
                            maxY = (float) ((double) maxY + 0.4);
                            maxZ = (float) ((double) maxZ - 0.02);
                            minZ = (float) ((double) minZ - 0.05);
                        } else if (enumFacing1 == EnumFacing.EAST) {
                            maxX = (float) ((double) maxX + 0.02);
                            minX = (float) ((double) minX + 0.05);
                            minY = (float) ((double) minY - 0.4);
                            maxY = (float) ((double) maxY + 0.4);
                            minZ = (float) ((double) minZ - 0.4);
                            maxZ = (float) ((double) maxZ + 0.4);
                        } else if (enumFacing1 == EnumFacing.WEST) {
                            maxX = (float) ((double) maxX - 0.02);
                            minX = (float) ((double) minX - 0.05);
                            minY = (float) ((double) minY - 0.4);
                            maxY = (float) ((double) maxY + 0.4);
                            minZ = (float) ((double) minZ - 0.4);
                            maxZ = (float) ((double) maxZ + 0.4);
                        } else if (enumFacing1 == EnumFacing.UP) {
                            minX = (float) ((double) minX - 0.4);
                            maxX = (float) ((double) maxX + 0.4);
                            maxY = (float) ((double) maxY + 0.02);
                            minY = (float) ((double) minY + 0.05);
                            minZ = (float) ((double) minZ - 0.4);
                            maxZ = (float) ((double) maxZ + 0.4);
                        } else if (enumFacing1 == EnumFacing.DOWN) {
                            minX = (float) ((double) minX - 0.4);
                            maxX = (float) ((double) maxX + 0.4);
                            maxY = (float) ((double) maxY - 0.02);
                            minY = (float) ((double) minY - 0.05);
                            minZ = (float) ((double) minZ - 0.4);
                            maxZ = (float) ((double) maxZ + 0.4);
                        }

                        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
                        worldrenderer.pos(minX, minY, minZ).endVertex();
                        worldrenderer.pos(minX, minY, maxZ).endVertex();
                        worldrenderer.pos(minX, maxY, maxZ).endVertex();
                        worldrenderer.pos(minX, maxY, minZ).endVertex();
                        worldrenderer.pos(minX, minY, maxZ).endVertex();
                        worldrenderer.pos(maxX, minY, maxZ).endVertex();
                        worldrenderer.pos(maxX, maxY, maxZ).endVertex();
                        worldrenderer.pos(minX, maxY, maxZ).endVertex();
                        worldrenderer.pos(maxX, minY, maxZ).endVertex();
                        worldrenderer.pos(maxX, minY, minZ).endVertex();
                        worldrenderer.pos(maxX, maxY, minZ).endVertex();
                        worldrenderer.pos(maxX, maxY, maxZ).endVertex();
                        worldrenderer.pos(maxX, minY, minZ).endVertex();
                        worldrenderer.pos(minX, minY, minZ).endVertex();
                        worldrenderer.pos(minX, maxY, minZ).endVertex();
                        worldrenderer.pos(maxX, maxY, minZ).endVertex();
                        worldrenderer.pos(minX, minY, minZ).endVertex();
                        worldrenderer.pos(minX, minY, maxZ).endVertex();
                        worldrenderer.pos(maxX, minY, maxZ).endVertex();
                        worldrenderer.pos(maxX, minY, minZ).endVertex();
                        worldrenderer.pos(minX, maxY, minZ).endVertex();
                        worldrenderer.pos(minX, maxY, maxZ).endVertex();
                        worldrenderer.pos(maxX, maxY, maxZ).endVertex();
                        worldrenderer.pos(maxX, maxY, minZ).endVertex();
                        worldrenderer.endVertex();
                        tessellator.draw();
                        GL11.glLineWidth(2.0F);
                        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
                        worldrenderer.pos(minX, minY, minZ).endVertex();
                        worldrenderer.pos(minX, minY, maxZ).endVertex();
                        worldrenderer.pos(minX, maxY, maxZ).endVertex();
                        worldrenderer.pos(minX, maxY, minZ).endVertex();
                        worldrenderer.pos(minX, minY, minZ).endVertex();
                        worldrenderer.pos(maxX, minY, minZ).endVertex();
                        worldrenderer.pos(maxX, maxY, minZ).endVertex();
                        worldrenderer.pos(maxX, maxY, maxZ).endVertex();
                        worldrenderer.pos(maxX, minY, maxZ).endVertex();
                        worldrenderer.pos(maxX, minY, minZ).endVertex();
                        worldrenderer.pos(maxX, minY, maxZ).endVertex();
                        worldrenderer.pos(minX, minY, maxZ).endVertex();
                        worldrenderer.pos(minX, maxY, maxZ).endVertex();
                        worldrenderer.pos(maxX, maxY, maxZ).endVertex();
                        worldrenderer.pos(maxX, maxY, minZ).endVertex();
                        worldrenderer.pos(minX, maxY, minZ).endVertex();
                        worldrenderer.endVertex();
                        tessellator.draw();
                    }

                    GL11.glLineWidth(1.0F);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDepthMask(true);
                    GlStateManager.enableCull();
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glDisable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(2848);
                }
            } catch (Exception idek) {
                idek.printStackTrace();
            }
        }
    }
    @EventTarget
    public void onPacket(EventSendPacket eventSendPacket) {
        Packet packet = eventSendPacket.getPacket();
        if (this.grimFlyTest.getBoolean()) {
            //PlayerUtil.sendChat("Capture packet " + System.currentTimeMillis());
            packets.put(packet, System.currentTimeMillis());
            eventSendPacket.setCanceled(true);
            checkPackets();
        }
    }
    private void checkPackets() {
        try {
            Packet toremove = null;
            for(Map.Entry<Packet, Long> set : packets.entrySet()) {
                if(set.getValue() + 100 < System.currentTimeMillis()) {
                    PlayerUtil.sendChat("Send packet " + set.getValue());
                    mc.thePlayer.sendQueue.addToSendQueueDirect(set.getKey());
                    toremove = set.getKey();
                }
            }
            if(toremove != null) {
                packets.remove(toremove);
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            //System.err.println("Error SBlink");
        }
    }
    private void resetPackets() {
        try {
            this.packets.keySet().forEach(mc.thePlayer.sendQueue::addToSendQueueDirect);
        } catch (Exception var2) {
            System.err.println("Error Blink");
        }

        this.packets.clear();
    }
    public static double calculateYaw(double startX, double startY, double endX, double endY) {
        double deltaZ = endY - startY;
        double deltaX = endX - startX;

        double yawRadians = Math.atan2(deltaZ, deltaX);

        return Math.toDegrees(yawRadians);
    }

}

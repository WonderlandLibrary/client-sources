/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventRender3D;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.RenderUtil;
import skizzle.util.Timer;

public class ChestESP
extends Module {
    public ModeSetting mode = new ModeSetting(Qprot0.0("\udd83\u71c4\ue6c6\ua7e1"), Qprot0.0("\udd88\u71de\ue6ce\ua7e8"), Qprot0.0("\udd88\u71de\ue6ce\ua7e8"), Qprot0.0("\udd8c\u71c4\ue6da"), Qprot0.0("\udd8d\u71d9\ue6c3\ua7f0\uff78"), Qprot0.0("\udd9d\u71c8\ue6c3\ua7ea"), Qprot0.0("\udd8b\u71d3\ue6d2\ua7e5\uff73\u5810"), Qprot0.0("\udd81\u71de\ue6d6\ua7e8\uff74\u581a\u8c2a"));
    public Timer timer;
    public boolean goingUp;
    public NumberSetting scans = new NumberSetting(Qprot0.0("\udd9d\u71c8\ue6c3\ua7ea\uff6e"), 1.0, 1.0, 3.0, 1.0);
    public float anim = Float.intBitsToFloat(2.13805286E9f ^ 0x7F7018E5);

    @Override
    public void onEvent(Event Nigga) {
        ChestESP Nigga2;
        if ((double)Nigga2.anim < -0.2) {
            Nigga2.anim = Float.intBitsToFloat(2.12610624E9f ^ 0x7EB9CE9F);
        }
        if ((double)Nigga2.anim > 1.1) {
            Nigga2.anim = Float.intBitsToFloat(2.13093197E9f ^ 0x7F0370D8);
        }
        if (Minecraft.theWorld != null) {
            double Nigga3 = Nigga2.timer.getDelay();
            if (Nigga2.timer.hasTimeElapsed((long)1407189179 ^ 0x53E000BAL, true)) {
                Nigga2.anim = Nigga2.goingUp ? (Nigga2.anim += (float)Nigga3 / Float.intBitsToFloat(9.9794285E8f ^ 0x7F016650) * Float.intBitsToFloat(1.05271661E9f ^ 0x7EBF2E49)) : (Nigga2.anim -= (float)Nigga3 / Float.intBitsToFloat(9.9273139E8f ^ 0x7F51E112) * Float.intBitsToFloat(1.0396976E9f ^ 0x7DF886AF));
                if (Nigga2.anim > Float.intBitsToFloat(1.08205837E9f ^ 0x7F1D3141)) {
                    Nigga2.goingUp = false;
                }
                if (Nigga2.anim < Float.intBitsToFloat(2.13338227E9f ^ 0x7F28D490)) {
                    Nigga2.goingUp = true;
                }
            }
        }
        if (Nigga instanceof EventRender3D) {
            for (Object Nigga4 : Minecraft.theWorld.loadedTileEntityList) {
                Object Nigga5;
                float Nigga6;
                double Nigga7;
                double Nigga8;
                Object Nigga9;
                if (!(Nigga4 instanceof TileEntity)) {
                    return;
                }
                GlStateManager.pushMatrix();
                if (Nigga4 instanceof TileEntityChest) {
                    Nigga9 = (TileEntityChest)Nigga4;
                    Color Nigga10 = new Color(Client.RGBColor);
                    BlockPos Nigga11 = ((TileEntityChest)Nigga4).getPos();
                    Nigga8 = (double)Nigga11.getX() - Nigga2.mc.getRenderManager().renderPosX;
                    Nigga7 = (double)Nigga11.getY() - Nigga2.mc.getRenderManager().renderPosY;
                    double Nigga12 = (double)Nigga11.getZ() - Nigga2.mc.getRenderManager().renderPosZ;
                    Nigga6 = (float)Nigga10.getRed() / Float.intBitsToFloat(1.00735866E9f ^ 0x7F7412CE);
                    float Nigga13 = (float)Nigga10.getBlue() / Float.intBitsToFloat(1.00872506E9f ^ 0x7F60EC4C);
                    float Nigga14 = (float)Nigga10.getGreen() / Float.intBitsToFloat(1.01172896E9f ^ 0x7F32C22D);
                    if (((TileEntityChest)Nigga9).getChestType() == 1) {
                        Nigga6 = Float.intBitsToFloat(1.1013961E9f ^ 0x7EE9344D);
                        Nigga13 = Float.intBitsToFloat(2.1179808E9f ^ 0x7E3DD2B7);
                        Nigga14 = Float.intBitsToFloat(1.11744448E9f ^ 0x7DD6155F);
                    }
                    Nigga5 = new AxisAlignedBB(Nigga8 + 0.0, Nigga7, Nigga12 + 0.0, Nigga8 + 0.0, Nigga7 + 0.0, Nigga12 + 0.0);
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd88\u71de\ue6ce\ue261"))) {
                        RenderUtil.drawSolidBlockESP((AxisAlignedBB)Nigga5, Nigga6, Nigga14, Nigga13, Float.intBitsToFloat(1.09122547E9f ^ 0x7F460B6A), 7);
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd8d\u71d9\ue6c3\ue279\u39f1"))) {
                        RenderUtil.drawSolidBlockESP((AxisAlignedBB)Nigga5, Nigga6, Nigga14, Nigga13, Float.intBitsToFloat(1.07629581E9f ^ 0x7F26F8B0), 3);
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd8c\u71c4\ue6da"))) {
                        GL11.glColor4d((double)Nigga6, (double)Nigga14, (double)Nigga13, (double)1.0);
                        GL11.glLineWidth((float)Float.intBitsToFloat(1.06486694E9f ^ 0x7F789461));
                        RenderUtil.drawBoundingBox((AxisAlignedBB)Nigga5, 3);
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd8b\u71d3\ue6d2\ue26c\u39fa\u5810"))) {
                        GlStateManager.pushMatrix();
                        GL11.glColor4d((double)Nigga6, (double)Nigga14, (double)Nigga13, (double)1.0);
                        Nigga5 = new AxisAlignedBB(Nigga8 + 0.0 + (double)Nigga2.anim / 2.5, Nigga7 + (double)Nigga2.anim / 2.5, Nigga12 + 0.0 + (double)Nigga2.anim / 2.5, Nigga8 + 0.0 - (double)Nigga2.anim / 2.5, Nigga7 + 0.0 - (double)Nigga2.anim / 2.5, Nigga12 + 0.0 - (double)Nigga2.anim / 2.5);
                        RenderUtil.drawBoundingBox((AxisAlignedBB)Nigga5, 3);
                        GlStateManager.popMatrix();
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd9d\u71c8\ue6c3\ue263"))) {
                        GL11.glPushMatrix();
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        GL11.glDisable((int)3553);
                        GL11.glEnable((int)2848);
                        GL11.glDisable((int)2929);
                        GL11.glDepthMask((boolean)false);
                        GL11.glColor4d((double)Nigga6, (double)Nigga14, (double)Nigga13, (double)1.0);
                        GL11.glLineWidth((float)Float.intBitsToFloat(1.08602906E9f ^ 0x7F3B7CCB));
                        Tessellator Nigga15 = Tessellator.getInstance();
                        WorldRenderer Nigga16 = Nigga15.getWorldRenderer();
                        Nigga5 = new AxisAlignedBB(Nigga8 + 0.0, Nigga7 + (double)Nigga2.anim, Nigga12 + 0.0, Nigga8 + 0.0, Nigga7 + 0.0, Nigga12 + 0.0);
                        Nigga16.startDrawing(3);
                        Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).minZ);
                        Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).maxZ);
                        Nigga16.addVertex(((AxisAlignedBB)Nigga5).maxX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).maxZ);
                        Nigga16.addVertex(((AxisAlignedBB)Nigga5).maxX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).minZ);
                        Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).minZ);
                        Nigga15.draw();
                        Nigga5 = new AxisAlignedBB(Nigga8 + 0.0 + (double)Nigga2.anim, Nigga7 + 0.0, Nigga12 + 0.0, Nigga8 + 0.0 + (double)Nigga2.anim, Nigga7 + 0.0, Nigga12 + 0.0);
                        if (Nigga2.scans.getValue() > 1.5) {
                            Nigga16.startDrawing(3);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).minZ);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).maxY, ((AxisAlignedBB)Nigga5).minZ);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).maxY, ((AxisAlignedBB)Nigga5).maxZ);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).maxZ);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).minZ);
                            Nigga15.draw();
                        }
                        Nigga5 = new AxisAlignedBB(Nigga8 + 0.0, Nigga7 + 0.0, Nigga12 + 0.0 - (double)Nigga2.anim, Nigga8 + 0.0, Nigga7 + 0.0, Nigga12 + 0.0 - (double)Nigga2.anim);
                        if (Nigga2.scans.getValue() > 2.5) {
                            Nigga16.startDrawing(3);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).maxZ);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).maxY, ((AxisAlignedBB)Nigga5).maxZ);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).maxX, ((AxisAlignedBB)Nigga5).maxY, ((AxisAlignedBB)Nigga5).maxZ);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).maxX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).maxZ);
                            Nigga16.addVertex(((AxisAlignedBB)Nigga5).minX, ((AxisAlignedBB)Nigga5).minY, ((AxisAlignedBB)Nigga5).maxZ);
                            Nigga15.draw();
                        }
                        GL11.glDisable((int)2848);
                        GL11.glEnable((int)3553);
                        GL11.glEnable((int)2929);
                        GL11.glDepthMask((boolean)true);
                        GL11.glDisable((int)3042);
                        GL11.glPopMatrix();
                    }
                }
                if (Nigga4 instanceof TileEntityEnderChest) {
                    TileEntityEnderChest cfr_ignored_0 = (TileEntityEnderChest)Nigga4;
                    new Color(Client.RGBColor);
                    Nigga9 = ((TileEntityEnderChest)Nigga4).getPos();
                    double Nigga17 = (double)((Vec3i)Nigga9).getX() - Nigga2.mc.getRenderManager().renderPosX;
                    Nigga8 = (double)((Vec3i)Nigga9).getY() - Nigga2.mc.getRenderManager().renderPosY;
                    Nigga7 = (double)((Vec3i)Nigga9).getZ() - Nigga2.mc.getRenderManager().renderPosZ;
                    float Nigga18 = Float.intBitsToFloat(1.12337882E9f ^ 0x7F39AAAD);
                    float Nigga19 = Float.intBitsToFloat(1.12187405E9f ^ 0x7F12BC51);
                    Nigga6 = Float.intBitsToFloat(1.12146867E9f ^ 0x7D5840FF);
                    AxisAlignedBB Nigga20 = new AxisAlignedBB(Nigga17 + 0.0, Nigga8, Nigga7 + 0.0, Nigga17 + 0.0, Nigga8 + 0.0, Nigga7 + 0.0);
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd88\u71de\ue6ce\ue261"))) {
                        RenderUtil.drawSolidBlockESP(Nigga20, Nigga18, Nigga6, Nigga19, Float.intBitsToFloat(1.13122534E9f ^ 0x7D21EDFF), 7);
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd8d\u71d9\ue6c3\ue279\u39f1"))) {
                        RenderUtil.drawSolidBlockESP(Nigga20, Nigga18, Nigga6, Nigga19, Float.intBitsToFloat(1.10246054E9f ^ 0x7EB636A3), 3);
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd8c\u71c4\ue6da"))) {
                        GL11.glColor4d((double)Nigga18, (double)Nigga6, (double)Nigga19, (double)1.0);
                        RenderUtil.drawBoundingBox(Nigga20, 3);
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd8b\u71d3\ue6d2\ue26c\u39fa\u5810"))) {
                        GlStateManager.pushMatrix();
                        GL11.glColor4d((double)Nigga18, (double)Nigga6, (double)Nigga19, (double)1.0);
                        Nigga20 = new AxisAlignedBB(Nigga17 + 0.0 + (double)Nigga2.anim / 2.5, Nigga8 + (double)Nigga2.anim / 2.5, Nigga7 + 0.0 + (double)Nigga2.anim / 2.5, Nigga17 + 0.0 - (double)Nigga2.anim / 2.5, Nigga8 + 0.0 - (double)Nigga2.anim / 2.5, Nigga7 + 0.0 - (double)Nigga2.anim / 2.5);
                        RenderUtil.drawBoundingBox(Nigga20, 3);
                        GlStateManager.popMatrix();
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\udd9d\u71c8\ue6c3\ue263"))) {
                        GL11.glPushMatrix();
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        GL11.glDisable((int)3553);
                        GL11.glEnable((int)2848);
                        GL11.glDisable((int)2929);
                        GL11.glDepthMask((boolean)false);
                        GL11.glColor4d((double)Nigga18, (double)Nigga6, (double)Nigga19, (double)1.0);
                        GL11.glLineWidth((float)Float.intBitsToFloat(1.06130067E9f ^ 0x7F0229C9));
                        Tessellator Nigga21 = Tessellator.getInstance();
                        Nigga5 = Nigga21.getWorldRenderer();
                        Nigga20 = new AxisAlignedBB(Nigga17 + 0.0, Nigga8 + (double)Nigga2.anim, Nigga7 + 0.0, Nigga17 + 0.0, Nigga8 + 0.0, Nigga7 + 0.0);
                        ((WorldRenderer)Nigga5).startDrawing(3);
                        ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.minY, Nigga20.minZ);
                        ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.minY, Nigga20.maxZ);
                        ((WorldRenderer)Nigga5).addVertex(Nigga20.maxX, Nigga20.minY, Nigga20.maxZ);
                        ((WorldRenderer)Nigga5).addVertex(Nigga20.maxX, Nigga20.minY, Nigga20.minZ);
                        ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.minY, Nigga20.minZ);
                        Nigga21.draw();
                        Nigga20 = new AxisAlignedBB(Nigga17 + 0.0 + (double)Nigga2.anim, Nigga8 + 0.0, Nigga7 + 0.0, Nigga17 + 0.0 + (double)Nigga2.anim, Nigga8 + 0.0, Nigga7 + 0.0);
                        if (Nigga2.scans.getValue() > 1.5) {
                            ((WorldRenderer)Nigga5).startDrawing(3);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.minY, Nigga20.minZ);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.maxY, Nigga20.minZ);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.maxY, Nigga20.maxZ);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.minY, Nigga20.maxZ);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.minY, Nigga20.minZ);
                            Nigga21.draw();
                        }
                        Nigga20 = new AxisAlignedBB(Nigga17 + 0.0, Nigga8 + 0.0, Nigga7 + 0.0 - (double)Nigga2.anim, Nigga17 + 0.0, Nigga8 + 0.0, Nigga7 + 0.0 - (double)Nigga2.anim);
                        if (Nigga2.scans.getValue() > 2.5) {
                            ((WorldRenderer)Nigga5).startDrawing(3);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.minY, Nigga20.maxZ);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.maxY, Nigga20.maxZ);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.maxX, Nigga20.maxY, Nigga20.maxZ);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.maxX, Nigga20.minY, Nigga20.maxZ);
                            ((WorldRenderer)Nigga5).addVertex(Nigga20.minX, Nigga20.minY, Nigga20.maxZ);
                            Nigga21.draw();
                        }
                        GL11.glDisable((int)2848);
                        GL11.glEnable((int)3553);
                        GL11.glEnable((int)2929);
                        GL11.glDepthMask((boolean)true);
                        GL11.glDisable((int)3042);
                        GL11.glPopMatrix();
                    }
                }
                GlStateManager.popMatrix();
            }
        }
    }

    public ChestESP() {
        super(Qprot0.0("\udd8d\u71c3\ue6c7\ua7f7\uff69\u5831\u8c1c\ub1cb"), 0, Module.Category.RENDER);
        ChestESP Nigga;
        Nigga.timer = new Timer();
        Nigga.addSettings(Nigga.mode, Nigga.scans);
    }

    public static {
        throw throwable;
    }
}


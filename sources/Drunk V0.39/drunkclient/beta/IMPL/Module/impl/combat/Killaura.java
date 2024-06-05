/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.IMPL.Module.impl.combat;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventRender3D;
import drunkclient.beta.API.events.world.EventPostUpdate;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.Module.impl.misc.Teams;
import drunkclient.beta.IMPL.managers.FriendManager;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.AStarCustomPathFinder;
import drunkclient.beta.UTILS.Math.RandomUtil;
import drunkclient.beta.UTILS.Math.RotationUtil;
import drunkclient.beta.UTILS.Math.RotationUtils;
import drunkclient.beta.UTILS.Math.Vec3d;
import drunkclient.beta.UTILS.render.RenderUtil;
import drunkclient.beta.UTILS.world.Timer;
import drunkclient.beta.UTILS.world.TimerUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

public class Killaura
extends Module {
    public Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[])TMode.values(), (Enum)TMode.Switch);
    public Mode<Enum> sortingMode = new Mode("Sorting Mode", "Sorting Mode", (Enum[])SortingMode.values(), (Enum)SortingMode.Health);
    public Mode<Enum> rotationMode = new Mode("Rotation Mode", "Rotation Mode", (Enum[])RMode.values(), (Enum)RMode.Normal);
    public Mode<Enum> autoBlockMode = new Mode("AutoBlock Mode", "AutoBlock Mode", (Enum[])ABMode.values(), (Enum)ABMode.Fake);
    public Mode<Enum> EspMode = new Mode("Esp Mode", "Esp Mode", (Enum[])ESPMode.values(), (Enum)ESPMode.Jello);
    public Mode<Enum> attackMode = new Mode("Attack Mode", "Attack Mode", (Enum[])AttackMode.values(), (Enum)AttackMode.Mc);
    public Option<Boolean> player = new Option<Boolean>("Player", "Player", true);
    public Option<Boolean> animals = new Option<Boolean>("Animals", "Animals", false);
    public Option<Boolean> mobs = new Option<Boolean>("Mobs", "Mobs", false);
    public Option<Boolean> invis = new Option<Boolean>("Invisible", "Invisible", false);
    public Option<Boolean> RenderCircle = new Option<Boolean>("Render Circle", "Render Circle", false);
    public Option<Boolean> wall = new Option<Boolean>("Wall", "Wall", true);
    public Option<Boolean> unSprint = new Option<Boolean>("Un Sprint", "Un Sprint", true);
    public Option<Boolean> Esp = new Option<Boolean>("Esp", "Esp", false);
    public Option<Boolean> vsirange = new Option<Boolean>("Visualize Range", "Visualize Range", false);
    public Option<Boolean> rotation = new Option<Boolean>("Rotation", "Rotation", false);
    public Option<Boolean> silentrotation = new Option<Boolean>("Silent-Rotations", "Silent-Rotations", false);
    public Option<Boolean> autoBlock = new Option<Boolean>("Auto Block", "AutoBlock", false);
    public Option<Boolean> strafe = new Option<Boolean>("Strafe", "Strafe", false);
    public Option<Boolean> randomCps = new Option<Boolean>("Random Cps", "Random Cps", false);
    float yaw = 0.0f;
    float pitch = 0.0f;
    private ArrayList<Vec3> points = new ArrayList();
    public static Numbers<Double> range = new Numbers<Double>("Range", "Range", 4.4, 0.1, 7.0, 0.1);
    public static Numbers<Double> minCps = new Numbers<Double>("Low Cps", "Low Cps", 7.0, 1.0, 20.0, 0.01);
    public static Numbers<Double> mainCps = new Numbers<Double>("Main Cps", "Main Cps", 7.0, 1.0, 20.0, 0.01);
    public static Numbers<Double> maxCps = new Numbers<Double>("Max Cps", "Max Cps", 8.0, 1.0, 20.0, 0.01);
    private final Numbers<Double> switchDelay = new Numbers<Double>("Switch Delay", "Switch Delay", 100.0, 1.0, 1000.0, 1.0);
    private final Numbers<Double> zitterValue = new Numbers<Double>("Zitter Value", "Zitter Value", 3.0, 0.1, 10.0, 0.01);
    public static EntityLivingBase target;
    private List<EntityLivingBase> targetList = new ArrayList<EntityLivingBase>();
    private int targetIndex;
    public static boolean block;
    public TimerUtil switchTimer = new TimerUtil();
    public TimerUtil attackTimer = new TimerUtil();
    public Timer renderTimer = new Timer();
    boolean up = true;
    float height = 0.0f;

    public Killaura() {
        super("KillAura", new String[]{"Killaura", "AutoKill"}, Type.COMBAT, "Automatically attacks entities around you");
        this.addValues(this.mode, this.sortingMode, this.rotationMode, this.autoBlockMode, this.attackMode, range, maxCps, mainCps, minCps, this.switchDelay, this.zitterValue, this.vsirange, this.Esp, this.player, this.animals, this.mobs, this.invis, this.wall, this.randomCps, this.unSprint, this.strafe, this.autoBlock, this.rotation, this.silentrotation);
    }

    @Override
    public void onEnable() {
        target = null;
        this.targetIndex = 0;
        this.points.clear();
        this.targetList.clear();
        this.unBlock();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        target = null;
        this.targetIndex = 0;
        this.targetList.clear();
        this.unBlock();
        super.onDisable();
    }

    @EventHandler
    public void onUpdatePre(EventPreUpdate event) {
        this.getAllTarget();
        this.sortTargets();
        this.slotTargetSwitch();
        this.rotation(event);
        this.setSuffix(this.mode.getModeAsString());
        if (((Boolean)this.unSprint.getValue()).booleanValue() && target != null) {
            Minecraft.thePlayer.setSprinting(false);
        }
        if (!this.attackTimer.hasReached(1000.0 / this.getCps())) return;
        if (target == null) return;
        if (this.isValidEntity(target)) {
            if (this.attackMode.getValue() == AttackMode.Mc) {
                this.MCAttack();
            } else {
                this.Packet1Attack();
            }
        }
        this.attackTimer.reset();
    }

    @EventHandler
    public void onUpdatePost(EventPostUpdate event) {
        if ((Boolean)this.autoBlock.getValue() == false) return;
        if (target == null) {
            this.unBlock();
            return;
        }
        if (this.isHoldingSword() && target != null) {
            this.block();
            return;
        }
        this.unBlock();
    }

    /*
     * Exception decompiling
     */
    public void rotation(EventPreUpdate event) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$FailedRewriteException: Block member is not a case, it's a class org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredComment
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteSwitch(SwitchStringRewriter.java:236)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at the.bytecode.club.bytecodeviewer.decompilers.impl.CFRDecompiler.decompileToZip(CFRDecompiler.java:306)
         *     at the.bytecode.club.bytecodeviewer.resources.ResourceDecompiling.lambda$null$5(ResourceDecompiling.java:159)
         *     at java.lang.Thread.run(Unknown Source)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void MCAttack() {
        Minecraft.thePlayer.swingItem();
        Killaura.mc.playerController.attackEntity(Minecraft.thePlayer, target);
    }

    public void Packet1Attack() {
        Minecraft.thePlayer.swingItem();
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)target, C02PacketUseEntity.Action.ATTACK));
    }

    public void Packet2Attack() {
    }

    private void smoothAim(EventPreUpdate em) {
        double randomYaw = 0.05;
        double randomPitch = 0.05;
        float targetYaw = RotationUtils.getYawChange(this.yaw, Killaura.target.posX + (double)Killaura.randomNumber(1, -1) * randomYaw, Killaura.target.posZ + (double)Killaura.randomNumber(1, -1) * randomYaw);
        float yawFactor = targetYaw / 2.3f;
        em.setYaw(this.yaw + yawFactor);
        this.yaw += yawFactor;
        float targetPitch = RotationUtils.getPitchChange(this.pitch, target, Killaura.target.posY + (double)Killaura.randomNumber(1, -1) * randomPitch);
        float pitchFactor = targetPitch / 2.3f;
        em.setPitch(this.pitch + pitchFactor);
        this.pitch += pitchFactor;
    }

    public static int randomNumber(int max, int min) {
        return Math.round((float)min + (float)Math.random() * (float)(max - min));
    }

    @EventHandler
    public void renderEsp(EventRender3D event) {
        if (((Boolean)this.vsirange.getValue()).booleanValue()) {
            RenderUtil.pre3D();
            GL11.glLineWidth((float)6.0f);
            GL11.glBegin((int)3);
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
            double n = 0.0;
            double d = Minecraft.thePlayer.lastTickPosX + (Minecraft.thePlayer.posX - Minecraft.thePlayer.lastTickPosX) * (double)event.getPartialTicks() + Math.sin(0.0) * (Double)range.getValue();
            mc.getRenderManager();
            double d2 = d - RenderManager.renderPosX;
            double d3 = Minecraft.thePlayer.lastTickPosY + (Minecraft.thePlayer.posY - Minecraft.thePlayer.lastTickPosY) * (double)event.getPartialTicks();
            mc.getRenderManager();
            double d4 = d3 - RenderManager.renderPosY;
            double d5 = Minecraft.thePlayer.lastTickPosZ + (Minecraft.thePlayer.posZ - Minecraft.thePlayer.lastTickPosZ) * (double)event.getPartialTicks() + Math.cos(0.0) * (Double)range.getValue();
            mc.getRenderManager();
            GL11.glVertex3d((double)d2, (double)d4, (double)(d5 - RenderManager.renderPosZ));
            GL11.glEnd();
            GL11.glLineWidth((float)3.0f);
            GL11.glBegin((int)3);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)255.0f);
            double n2 = 0.0;
            double d6 = Minecraft.thePlayer.lastTickPosX + (Minecraft.thePlayer.posX - Minecraft.thePlayer.lastTickPosX) * (double)event.getPartialTicks() + Math.sin(0.0) * (Double)range.getValue();
            mc.getRenderManager();
            double d7 = d6 - RenderManager.renderPosX;
            double d8 = Minecraft.thePlayer.lastTickPosY + (Minecraft.thePlayer.posY - Minecraft.thePlayer.lastTickPosY) * (double)event.getPartialTicks();
            mc.getRenderManager();
            double d9 = d8 - RenderManager.renderPosY;
            double d10 = Minecraft.thePlayer.lastTickPosZ + (Minecraft.thePlayer.posZ - Minecraft.thePlayer.lastTickPosZ) * (double)event.getPartialTicks() + Math.cos(0.0) * (Double)range.getValue();
            mc.getRenderManager();
            GL11.glVertex3d((double)d7, (double)d9, (double)(d10 - RenderManager.renderPosZ));
            GL11.glEnd();
            RenderUtil.post3D();
        }
        if (!((Boolean)this.Esp.getValue()).booleanValue()) {
            if (target == null) return;
            ArrayList<drunkclient.beta.UTILS.Vec3> path = Killaura.findBlinkPath(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, Killaura.target.posX, Killaura.target.posY, Killaura.target.posZ, 12.0);
            if (!this.points.isEmpty()) return;
            return;
        }
        if (!this.EspMode.getModeAsString().equalsIgnoreCase("Jello")) return;
        if (target == null) return;
        if (this.up) {
            if (this.renderTimer.hasElapsed(45L, true)) {
                this.height += 0.1f;
                if (this.height > 2.0f) {
                    this.up = false;
                }
            }
        } else if (this.renderTimer.hasElapsed(45L, true)) {
            this.height -= 0.1f;
            if (this.height <= 0.0f) {
                this.up = true;
            }
        }
        if (!this.up) {
            this.renderCircle(event, this.height, 1.0f);
            this.renderCircle(event, this.height + 0.01f, 0.95f);
            this.renderCircle(event, this.height + 0.02f, 0.9f);
            this.renderCircle(event, this.height + 0.03f, 0.85f);
            this.renderCircle(event, this.height + 0.04f, 0.8f);
            this.renderCircle(event, this.height + 0.05f, 0.75f);
            this.renderCircle(event, this.height + 0.06f, 0.7f);
            this.renderCircle(event, this.height + 0.07f, 0.65f);
            this.renderCircle(event, this.height + 0.08f, 0.6f);
            this.renderCircle(event, this.height + 0.09f, 0.55f);
            this.renderCircle(event, this.height + 0.1f, 0.5f);
            this.renderCircle(event, this.height + 0.11f, 0.45f);
            this.renderCircle(event, this.height + 0.12f, 0.4f);
            this.renderCircle(event, this.height + 0.13f, 0.35f);
            this.renderCircle(event, this.height + 0.14f, 0.3f);
            this.renderCircle(event, this.height + 0.15f, 0.25f);
            this.renderCircle(event, this.height + 0.16f, 0.2f);
            this.renderCircle(event, this.height + 0.17f, 0.15f);
            this.renderCircle(event, this.height + 0.18f, 0.1f);
            this.renderCircle(event, this.height + 0.19f, 0.05f);
            this.renderCircle(event, this.height + 0.2f, 0.03f);
            this.renderCircle(event, this.height + 0.21f, 0.02f);
            this.renderCircle(event, this.height + 0.22f, 0.01f);
            return;
        }
        this.renderCircle(event, this.height, 0.1f);
        this.renderCircle(event, this.height + 0.01f, 0.15f);
        this.renderCircle(event, this.height + 0.02f, 0.2f);
        this.renderCircle(event, this.height + 0.03f, 0.25f);
        this.renderCircle(event, this.height + 0.04f, 0.3f);
        this.renderCircle(event, this.height + 0.05f, 0.35f);
        this.renderCircle(event, this.height + 0.06f, 0.4f);
        this.renderCircle(event, this.height + 0.07f, 0.45f);
        this.renderCircle(event, this.height + 0.08f, 0.5f);
        this.renderCircle(event, this.height + 0.09f, 0.55f);
        this.renderCircle(event, this.height + 0.1f, 0.6f);
        this.renderCircle(event, this.height + 0.11f, 0.65f);
        this.renderCircle(event, this.height + 0.12f, 0.7f);
        this.renderCircle(event, this.height + 0.13f, 0.75f);
        this.renderCircle(event, this.height + 0.14f, 0.8f);
        this.renderCircle(event, this.height + 0.15f, 0.85f);
        this.renderCircle(event, this.height + 0.16f, 0.9f);
        this.renderCircle(event, this.height + 0.17f, 0.95f);
        this.renderCircle(event, this.height + 0.18f, 1.0f);
    }

    public void renderCircle(EventRender3D event, float height, float alpha) {
        EntityLivingBase entity = target;
        double rad = 0.6;
        float points = 90.0f;
        GlStateManager.enableDepth();
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2881);
        GL11.glEnable((int)2832);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glHint((int)3153, (int)4354);
        GL11.glColor4f((float)10.0f, (float)107.0f, (float)204.0f, (float)alpha);
        GL11.glDisable((int)2929);
        GL11.glLineWidth((float)1.3f);
        GL11.glBegin((int)3);
        GlStateManager.color(10.0f, 107.0f, 204.0f, alpha);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.getPartialTicks() - RenderManager.renderPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.getPartialTicks() - RenderManager.renderPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.getPartialTicks() - RenderManager.renderPosZ;
        int i = 0;
        while (true) {
            if (i > 90) {
                GL11.glEnd();
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)2929);
                GL11.glDisable((int)2848);
                GL11.glDisable((int)2881);
                GL11.glDisable((int)2848);
                GL11.glEnable((int)2832);
                GL11.glEnable((int)3553);
                GL11.glPopMatrix();
                return;
            }
            GL11.glColor4f((float)84.0f, (float)95.0f, (float)255.0f, (float)alpha);
            GL11.glVertex3d((double)(x + rad * Math.cos((double)i * (Math.PI * 2) / (double)points)), (double)(y + (double)height), (double)(z + rad * Math.sin((double)i * (Math.PI * 2) / (double)points)));
            ++i;
        }
    }

    public void block() {
        if (Killaura.mc.gameSettings.keyBindUseItem.isPressed()) return;
        if (block) return;
        switch (this.autoBlockMode.getModeAsString()) {
            case "Fake": 
            case "Legit": {
                block = true;
                return;
            }
            case "Normal": {
                block = true;
                Minecraft.thePlayer.setItemInUse(Minecraft.thePlayer.getCurrentEquippedItem(), Minecraft.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                return;
            }
            case "Redesky": {
                if (Killaura.mc.gameSettings.keyBindUseItem.isPressed()) return;
                if (block) return;
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)target, new Vec3((double)RandomUtils.nextInt(50, 50) / 100.0, (double)RandomUtils.nextInt(0, 200) / 100.0, (double)RandomUtils.nextInt(50, 50) / 100.0)));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)target, C02PacketUseEntity.Action.INTERACT));
                block = true;
                return;
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public void unBlock() {
        if (Killaura.block == false) return;
        if (this.autoBlockMode.getValue() == ABMode.Normal) ** GOTO lbl5
        if (this.autoBlockMode.getValue() == ABMode.Legit) {
lbl5:
            // 2 sources

            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        } else if (this.autoBlockMode.getValue() == ABMode.Redesky) {
            Killaura.mc.playerController.syncCurrentPlayItem();
        }
        Killaura.block = false;
    }

    public static float[] rotationsToEntity(EntityLivingBase paramEntityLivingBase) {
        Vec3d vec31 = new Vec3d(Minecraft.thePlayer.posX + Minecraft.thePlayer.motionX, Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() + Minecraft.thePlayer.motionY, Minecraft.thePlayer.posZ + Minecraft.thePlayer.motionZ);
        Vec3d vec32 = new Vec3d(0.0, paramEntityLivingBase.posY + (double)paramEntityLivingBase.getEyeHeight(), 0.0);
        float f = 0.0f;
        while (true) {
            if (!(f < paramEntityLivingBase.getEyeHeight())) {
                double d1 = Math.abs(paramEntityLivingBase.getEntityBoundingBox().maxX - paramEntityLivingBase.getEntityBoundingBox().minX) / 2.0;
                double d2 = Math.abs(paramEntityLivingBase.getEntityBoundingBox().maxZ - paramEntityLivingBase.getEntityBoundingBox().minZ) / 2.0;
                double d3 = paramEntityLivingBase.posX - vec31.xCoord;
                double d4 = vec32.yCoord - vec31.yCoord;
                double d5 = paramEntityLivingBase.posZ - vec31.zCoord;
                return Killaura.applyMouseSensFix((float)Math.toDegrees(Math.atan2(d5, d3)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(d4, Math.hypot(d3, d5)))));
            }
            Vec3d vec3 = new Vec3d(0.0, paramEntityLivingBase.posY + (double)f + paramEntityLivingBase.posY - paramEntityLivingBase.prevPosY, 0.0);
            if (vec3.distanceTo(vec31) < vec32.distanceTo(vec31)) {
                vec32 = vec3;
            }
            f = (float)((double)f + 0.05);
        }
    }

    public double getCps() {
        double d;
        if (((Boolean)this.randomCps.getValue()).booleanValue()) {
            d = RandomUtil.getRandomInRange((Double)minCps.getValue(), (Double)maxCps.getValue());
            return d;
        }
        d = (Double)mainCps.getValue();
        return d;
    }

    public void slotTargetSwitch() {
        if (this.switchTimer.hasReached((Double)this.switchDelay.getValue()) && this.mode.getModeAsString().equals("Switch")) {
            ++this.targetIndex;
            this.switchTimer.reset();
        }
        if (this.targetIndex >= this.targetList.size()) {
            this.targetIndex = 0;
        }
        target = !this.targetList.isEmpty() && this.targetIndex < this.targetList.size() ? this.targetList.get(this.targetIndex) : null;
    }

    private static float[] applyMouseSensFix(float paramFloat1, float paramFloat2) {
        float f1 = Killaura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f2 = f1 * f1 * f1 * 1.2f;
        return new float[]{paramFloat1, paramFloat2};
    }

    private void sortTargets() {
        switch (this.sortingMode.getModeAsString()) {
            case "Angle": {
                this.targetList.sort(Comparator.comparingDouble(RotationUtil::getAngleChange));
                return;
            }
            case "Distance": {
                this.targetList.sort(Comparator.comparingDouble(RotationUtil::getDistanceToEntity));
                return;
            }
            case "Health": {
                this.targetList.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
                return;
            }
        }
    }

    private void getAllTarget() {
        this.targetList.clear();
        Iterator<Entity> iterator = Minecraft.thePlayer.getEntityWorld().loadedEntityList.iterator();
        while (iterator.hasNext()) {
            EntityLivingBase entityLivingBase;
            Entity entity = iterator.next();
            if (!(entity instanceof EntityLivingBase) || !this.isValidEntity(entityLivingBase = (EntityLivingBase)entity)) continue;
            this.targetList.add(entityLivingBase);
        }
    }

    private boolean isValidEntity(EntityLivingBase ent) {
        if (Minecraft.thePlayer.isDead) {
            return false;
        }
        if (!ent.canEntityBeSeen(Minecraft.thePlayer) && !((Boolean)this.wall.getValue()).booleanValue()) {
            return false;
        }
        if (ent instanceof EntityArmorStand) {
            return false;
        }
        if (ent == null) return false;
        if (ent == Minecraft.thePlayer) return false;
        if (ent instanceof EntityPlayer) {
            if ((Boolean)this.player.getValue() == false) return false;
        }
        if (ent instanceof EntityAnimal || ent instanceof EntitySquid) {
            if ((Boolean)this.animals.getValue() == false) return false;
        }
        if (ent instanceof EntityMob || ent instanceof EntityVillager || ent instanceof EntitySnowman || ent instanceof EntityBat) {
            if ((Boolean)this.mobs.getValue() == false) return false;
        }
        if ((double)Minecraft.thePlayer.getDistanceToEntity(ent) > (Double)range.getValue() + 0.4) return false;
        if (!(ent instanceof EntityPlayer) && ent.isDead) {
            if (FriendManager.isFriend(ent.getName())) return false;
        }
        if (ent.isDead) return false;
        if (ent.isInvisible()) {
            if ((Boolean)this.invis.getValue() == false) return false;
        }
        if (Minecraft.thePlayer.isDead) return false;
        if (!(ent instanceof EntityPlayer)) return true;
        if (Teams.isOnSameTeam(ent)) return false;
        return true;
    }

    private boolean isHoldingSword() {
        if (Minecraft.thePlayer.getCurrentEquippedItem() == null) return false;
        if (!(Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) return false;
        return true;
    }

    public static List<drunkclient.beta.UTILS.Vec3> findBlinkPath(double tpX, double tpY, double tpZ) {
        return Killaura.findBlinkPath(tpX, tpY, tpZ, 5.0);
    }

    public static List<drunkclient.beta.UTILS.Vec3> findBlinkPath(double tpX, double tpY, double tpZ, double dist) {
        return Killaura.findBlinkPath(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, tpX, tpY, tpZ, dist);
    }

    private static boolean canPassThrow(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        if (block.getMaterial() == Material.air) return true;
        if (block.getMaterial() == Material.plants) return true;
        if (block.getMaterial() == Material.vine) return true;
        if (block == Blocks.ladder) return true;
        if (block == Blocks.water) return true;
        if (block == Blocks.flowing_water) return true;
        if (block == Blocks.wall_sign) return true;
        if (block == Blocks.standing_sign) return true;
        return false;
    }

    public static ArrayList<drunkclient.beta.UTILS.Vec3> findBlinkPath(double curX, double curY, double curZ, double tpX, double tpY, double tpZ, double dashDistance) {
        drunkclient.beta.UTILS.Vec3 topFrom = new drunkclient.beta.UTILS.Vec3(curX, curY, curZ);
        drunkclient.beta.UTILS.Vec3 to = new drunkclient.beta.UTILS.Vec3(tpX, tpY, tpZ);
        if (!Killaura.canPassThrow(new BlockPos(topFrom))) {
            topFrom = topFrom.addVector(0.0, 1.0, 0.0);
        }
        AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        drunkclient.beta.UTILS.Vec3 lastLoc = null;
        drunkclient.beta.UTILS.Vec3 lastDashLoc = null;
        ArrayList<drunkclient.beta.UTILS.Vec3> path = new ArrayList<drunkclient.beta.UTILS.Vec3>();
        ArrayList<drunkclient.beta.UTILS.Vec3> pathFinderPath = pathfinder.getPath();
        Iterator<drunkclient.beta.UTILS.Vec3> iterator = pathFinderPath.iterator();
        while (iterator.hasNext()) {
            drunkclient.beta.UTILS.Vec3 pathElm = iterator.next();
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > dashDistance * dashDistance) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                    double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                    double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                    double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                    double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                    double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                    int x = (int)smallX;
                    block1: while ((double)x <= bigX) {
                        int y = (int)smallY;
                        while (true) {
                            int z;
                            if ((double)y <= bigY) {
                                z = (int)smallZ;
                            } else {
                                ++x;
                                continue block1;
                            }
                            while ((double)z <= bigZ) {
                                if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break block1;
                                }
                                ++z;
                            }
                            ++y;
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }

    static enum ESPMode {
        Jello,
        Line;

    }

    public static enum AttackMode {
        Mc,
        Packet,
        Redesky;

    }

    public static enum ABMode {
        Fake,
        Normal,
        Legit,
        Redesky;

    }

    public static enum RMode {
        Normal,
        Smooth,
        Zitter,
        AAC,
        Ghostly;

    }

    public static enum SortingMode {
        Angle,
        Distance,
        Health;

    }

    public static enum TMode {
        Single,
        Switch;

    }
}


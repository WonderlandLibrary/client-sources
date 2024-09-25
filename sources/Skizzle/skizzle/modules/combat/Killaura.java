/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.combat;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.EventType;
import skizzle.events.listeners.EventAttack;
import skizzle.events.listeners.EventMotion;
import skizzle.events.listeners.EventRender3D;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.events.listeners.EventRenderPlayer;
import skizzle.events.listeners.EventStrafing;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.AnimationHelper;
import skizzle.util.MoveUtil;
import skizzle.util.RandomHelper;
import skizzle.util.RenderUtil;
import skizzle.util.RotationUtil;
import skizzle.util.Timer;

public class Killaura
extends Module {
    public ModeSetting mode;
    public Timer timer = new Timer();
    public BooleanSetting blockHit;
    public double lastHealth;
    public AnimationHelper targetSmoothAnim;
    public double clickDelay = 0.0;
    public String lastTarget;
    public Timer targetbarTimer;
    public NumberSetting randomYaw;
    public float renderLastYaw;
    public HashSet<EntityLivingBase> illegalEntities;
    public boolean targetEspUp = true;
    public Timer playerbarTimer;
    public AnimationHelper targetAnimAnim;
    public NumberSetting fov;
    public float targetYaw;
    public ModeSetting rotations;
    public NumberSetting motionTracking;
    public BooleanSetting walls;
    public ModeSetting health;
    public EntityLivingBase singleTarget;
    public double targetEspAnim = 0.0;
    public NumberSetting mouseMatLength;
    public boolean changeTargetStuff = false;
    public BooleanSetting targetMenu;
    public double targetAnim;
    public NumberSetting menuScale;
    public EntityLivingBase renderTargeting;
    public float renderYaw;
    public double playerBarAnim;
    public ModeSetting filter;
    public ModeSetting targetMenuM;
    public float[] targetRotations;
    public NumberSetting range;
    public Timer timer2 = new Timer();
    public double targetBarAnim;
    public NumberSetting yawSense;
    public NumberSetting menuX;
    public boolean mineplex = false;
    public Timer espTimer;
    public double targetSmoothHealth;
    public NumberSetting menuY;
    public NumberSetting maxAps;
    public float renderPitch;
    public NumberSetting pitchAccur;
    public NumberSetting minAps;
    public BooleanSetting crits;
    public BooleanSetting rayTrace;
    public float thirdPersonYaw;
    public float sYaw;
    public int mouseMatCurrent = 0;
    public int tocks;
    public BooleanSetting mouseMat;
    public AnimationHelper targetPopUp;
    public BooleanSetting esp;
    public String teamColour = "";
    public float renderLastPitch;
    public NumberSetting pitchSense;
    public BooleanSetting noSwing;
    public EntityLivingBase targeting;

    public Killaura() {
        super(Qprot0.0("\u4823\u71c2\u7370\ua7e8\u63de\ucda7\u8c3d\u2444"), 19, Module.Category.COMBAT);
        Killaura Nigga;
        Nigga.range = new NumberSetting(Qprot0.0("\u483a\u71ca\u7372\ua7e3\u63da"), 4.0, 1.0, 8.0, 0.0);
        Nigga.esp = new BooleanSetting(Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2460\u5731\uc8a5"), true);
        Nigga.targetMenu = new BooleanSetting(Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2468\u5707\uc89b\u478a"), true);
        Nigga.menuX = new NumberSetting(Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2468\u5707\uc89b\u478a\uaf4c\ucaa6"), 440.0, 0.0, 1000.0, 5.0);
        Nigga.menuY = new NumberSetting(Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2468\u5707\uc89b\u478a\uaf4c\ucaa7"), 15.0, 0.0, 1000.0, 5.0);
        Nigga.menuScale = new NumberSetting(Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2468\u5707\uc89b\u478a\uaf4c\ucaad\u724e\u17e7\ud401\u42ec"), 1.0, 0.0, 10.0, 0.0);
        Nigga.minAps = new NumberSetting(Qprot0.0("\u4825\u71c2\u7372\ua7a4\u63fe\ucd82\u8c1c"), 10.0, 1.0, 20.0, 1.0);
        Nigga.maxAps = new NumberSetting(Qprot0.0("\u4825\u71ca\u7364\ua7a4\u63fe\ucd82\u8c1c"), 10.0, 1.0, 20.0, 1.0);
        Nigga.noSwing = new BooleanSetting(Qprot0.0("\u4826\u71c4\u733c\ua7d7\u63c8\ucdbb\u8c21\u2442"), false);
        Nigga.rotations = new ModeSetting(Qprot0.0("\u483a\u71c4\u7368\ua7e5\u63cb\ucdbb\u8c20\u244b\u5711"), Qprot0.0("\u482b\u71c7\u737d\ua7f7\u63cc\ucdbb\u8c2c"), Qprot0.0("\u482b\u71c7\u737d\ua7f7\u63cc\ucdbb\u8c2c"), Qprot0.0("\u4824\u71ce\u737b\ua7ed\u63cb"), Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2476\u5716\uc887\u479e\uaf0a\uca9b"), Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2476\u5716\uc887\u479e\uaf0a\uca9b\u720d\u17b4"), Qprot0.0("\u4826\u71c4\u733c\ua7d6\u63d0\ucda6\u8c2e\u2451\u570b\uc89a\u4791\uaf1f"));
        Nigga.pitchAccur = new NumberSetting(Qprot0.0("\u4838\u71c2\u7368\ua7e7\u63d7\ucdf2\u8c0e\u2446\u5701\uc880\u478d\uaf0d\uca9d\u7254"), 10.0, 1.0, 10.0, 0.0);
        Nigga.filter = new ModeSetting(Qprot0.0("\u482e\u71c2\u7370\ua7f0\u63da\ucda0"), Qprot0.0("\u482c\u71c2\u736f\ua7f0\u63de\ucdbc\u8c2c\u2440"), Qprot0.0("\u482c\u71c2\u736f\ua7f0\u63de\ucdbc\u8c2c\u2440"), Qprot0.0("\u4829\u71c5\u737b\ua7e8\u63da"), Qprot0.0("\u4820\u71ce\u737d\ua7e8\u63cb\ucdba"));
        Nigga.randomYaw = new NumberSetting(Qprot0.0("\u483a\u71ca\u7372\ua7e0\u63d0\ucdbf\u8c6f\u247c\u5703\uc882"), 1.0, 1.0, 20.0, 1.0);
        Nigga.blockHit = new BooleanSetting(Qprot0.0("\u482a\u71c7\u7373\ua7e7\u63d4\ucdf2\u8c07\u244c\u5716"), true);
        Nigga.fov = new NumberSetting(Qprot0.0("\u482e\u71e4\u734a"), 270.0, 10.0, 360.0, 10.0);
        Nigga.mode = new ModeSetting(Qprot0.0("\u4825\u71c4\u7378\ua7e1"), Qprot0.0("\u483b\u71dc\u7375\ua7f0\u63dc\ucdba"), Qprot0.0("\u483b\u71dc\u7375\ua7f0\u63dc\ucdba"), Qprot0.0("\u483b\u71c2\u7372\ua7e3\u63d3\ucdb7"));
        Nigga.crits = new BooleanSetting(Qprot0.0("\u482b\u71d9\u7375\ua7f0\u63d6\ucdb1\u8c2e\u2449\u5711"), false);
        Nigga.rayTrace = new BooleanSetting(Qprot0.0("\u483a\u71ca\u7365\ua7f0\u63cd\ucdb3\u8c2c\u2440"), false);
        Nigga.walls = new BooleanSetting(Qprot0.0("\u4820\u71c2\u7368\ua7a4\u63eb\ucdba\u8c3d\u244a\u5717\uc892\u4797\uaf4c\ucaa9\u724c\u17ea\ud401\u42fa"), false);
        Nigga.health = new ModeSetting(Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2468\u5707\uc89b\u478a\uaf4c\ucab6\u7248\u17e7\ud401\u42fd\ufe6f"), Qprot0.0("\u482a\u71ca\u736e"), Qprot0.0("\u482a\u71ca\u736e"), Qprot0.0("\u4838\u71ce\u736e\ua7e7\u63da\ucdbc\u8c3b"), Qprot0.0("\u4826\u71de\u7371\ua7e6\u63da\ucda0\u8c3c"));
        Nigga.targetMenuM = new ModeSetting(Qprot0.0("\u483c\u71ca\u736e\ua7e3\u63da\ucda6\u8c6f\u2468\u5707\uc89b\u478a\uaf4c\ucab3\u7242\u17e2\ud408"), Qprot0.0("\u483b\u71c2\u7371\ua7f4\u63d3\ucdb7"), Qprot0.0("\u483b\u71c2\u7371\ua7f4\u63d3\ucdb7"), Qprot0.0("\u482b\u71c4\u7371\ua7f4\u63d3\ucdb7\u8c37"));
        Nigga.pitchSense = new NumberSetting(Qprot0.0("\u4838\u71c2\u7368\ua7e7\u63d7\ucdf2\u8c1c\u2440\u570c\uc886\u4796\uaf18\uca97\u725b\u17ef\ud419\u42f0"), 1.0, 0.0, 1.0, 0.0);
        Nigga.yawSense = new NumberSetting(Qprot0.0("\u4831\u71ca\u736b\ua7a4\u63ec\ucdb7\u8c21\u2456\u570b\uc881\u4796\uaf1a\uca97\u7259\u17ff"), 1.0, 0.0, 1.0, 0.0);
        Nigga.motionTracking = new NumberSetting(Qprot0.0("\u483c\u71d9\u737d\ua7e7\u63d4\ucdbb\u8c21\u2442\u5742\uc8a6\u478f\uaf09\uca9b\u7249"), 1.0, 0.0, 2.0, 0.0);
        Nigga.mouseMatLength = new NumberSetting(Qprot0.0("\u4825\u71c4\u7369\ua7f7\u63da\ucdf2\u8c02\u2444\u5716\uc8d5\u47b3\uaf09\uca90\u724a\u17f2\ud405"), 30.0, 0.0, 120.0, 1.0);
        Nigga.mouseMat = new BooleanSetting(Qprot0.0("\u4825\u71c4\u7369\ua7f7\u63da\ucdf2\u8c02\u2444\u5716\uc89d"), false);
        Nigga.thirdPersonYaw = Float.intBitsToFloat(2.10178112E9f ^ 0x7D46A2AF);
        Nigga.espTimer = new Timer();
        Nigga.illegalEntities = new HashSet();
        Nigga.targetbarTimer = new Timer();
        Nigga.playerbarTimer = new Timer();
        Nigga.targetRotations = new float[]{Float.intBitsToFloat(2.13191347E9f ^ 0x7F126AF9), Float.intBitsToFloat(2.13843392E9f ^ 0x7F75E9AD)};
        Nigga.targetSmoothAnim = new AnimationHelper();
        Nigga.targetAnimAnim = new AnimationHelper();
        Nigga.targetPopUp = new AnimationHelper();
        Nigga.addSettings(Nigga.blockHit, Nigga.mode, Nigga.esp, Nigga.targetMenu, Nigga.targetMenuM, Nigga.menuX, Nigga.menuY, Nigga.menuScale, Nigga.health, Nigga.crits, Nigga.fov, Nigga.range, Nigga.minAps, Nigga.maxAps, Nigga.noSwing, Nigga.rotations, Nigga.filter, Nigga.pitchAccur, Nigga.randomYaw, Nigga.rayTrace, Nigga.walls, Nigga.pitchSense, Nigga.yawSense, Nigga.motionTracking, Nigga.mouseMatLength, Nigga.mouseMat);
    }

    public EntityLivingBase singleTarget(EntityLivingBase Nigga, List<EntityLivingBase> Nigga2) {
        Killaura Nigga3;
        if (Nigga3.mode.getMode().equals(Qprot0.0("\u483b\u71c2\u7372\u2e68\ue858\ucdb7"))) {
            if (Nigga3.singleTarget == null) {
                Nigga3.singleTarget = Nigga;
            }
            if (Nigga3.singleTarget.isDead) {
                Nigga3.singleTarget = Nigga;
            }
            if (!Nigga2.contains(Nigga3.singleTarget)) {
                Nigga3.singleTarget = Nigga;
            }
            return Nigga3.singleTarget;
        }
        return Nigga;
    }

    @Override
    public void onEnable() {
        Killaura Nigga;
        Nigga.sYaw = Nigga.mc.thePlayer.rotationYaw;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onDisable() {
        Nigga.targeting = null;
        Nigga.targetPopUp.stage = 0.0;
    }

    public static double lambda$4(EntityLivingBase Nigga) {
        return Nigga.getHealth();
    }

    public double lambda$2(EntityLivingBase Nigga) {
        Killaura Nigga2;
        return Nigga.getDistanceToEntity(Nigga2.mc.thePlayer);
    }

    public float getYaw(EntityLivingBase Nigga, EntityLivingBase Nigga2) {
        Killaura Nigga3;
        return MathHelper.wrapAngleTo180_float(Nigga3.getRotations(Nigga)[0] - MathHelper.wrapAngleTo180_float(Nigga2.rotationYawHead));
    }

    public boolean lambda$1(EntityLivingBase Nigga) {
        Killaura Nigga2;
        return (double)Nigga.getDistanceToEntity(Nigga2.mc.thePlayer) <= Nigga2.range.getValue() && Nigga != Nigga2.mc.thePlayer && !Nigga.isDead && Nigga.getHealth() > Float.intBitsToFloat(2.12782067E9f ^ 0x7ED3F7A9) && ModuleManager.targeting.isTarget(Nigga) && !Nigga.isInvisible();
    }

    public boolean checkSwordItem() {
        Killaura Nigga;
        return Nigga.mc.thePlayer.getHeldItem() != null && Nigga.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }

    public double lambda$3(EntityLivingBase Nigga) {
        Killaura Nigga2;
        return Nigga2.getRotations(Nigga)[0];
    }

    @Override
    public void onEvent(Event Nigga) {
        Killaura Nigga2;
        Event Nigga3;
        if (Nigga instanceof EventStrafing) {
            Nigga3 = (EventStrafing)Nigga;
            if (Nigga2.targeting != null) {
                Nigga2.targetRotations[0] = Nigga2.getRotations(Nigga2.targeting)[0];
                Nigga2.targetRotations[1] = Nigga2.getRotations(Nigga2.targeting)[1];
                if (Nigga2.rotations.getMode().equals(Qprot0.0("\u483c\u71ca\u736e\ue26a\uac53\ucda6\u8c6f\u2476\u129f\u070e\u479e\uaf0a\uca9b")) || Nigga2.rotations.getMode().equals(Qprot0.0("\u483c\u71ca\u736e\ue26a\uac53\ucda6\u8c6f\u2476\u129f\u070e\u479e\uaf0a\uca9b\u3784\ud83d"))) {
                    ((EventStrafing)Nigga3).setX(Nigga2.targetRotations[0]);
                    ((EventStrafing)Nigga3).setZ(Nigga2.targetRotations[0]);
                }
                if (Nigga2.rotations.getMode().equals(Qprot0.0("\u482b\u71c7\u737d\ue27e\uac45\ucdbb\u8c2c")) && !ModuleManager.scaffold.isEnabled() && Nigga2.targeting != null) {
                    Nigga2.mc.thePlayer.setSprinting(false);
                    MoveUtil.applyStrafeToPlayer((EventStrafing)Nigga3, Nigga2.targetRotations[0]);
                }
            }
        }
        if (Nigga instanceof EventRenderPlayer) {
            Nigga3 = (EventRenderPlayer)Nigga;
            if (Nigga2.targeting != null) {
                ((EventRenderPlayer)Nigga3).setPitch((float)RenderUtil.interpolateValue(Nigga2.renderLastPitch, Nigga2.renderPitch, ((EventRenderPlayer)Nigga3).getPartialTicks()));
                ((EventRenderPlayer)Nigga3).setYaw((float)RenderUtil.interpolateValue(Nigga2.renderLastYaw, Nigga2.renderYaw, ((EventRenderPlayer)Nigga3).getPartialTicks()));
                ((EventRenderPlayer)Nigga3).yawChange = Float.intBitsToFloat(2.10182067E9f ^ 0x7D473CCF);
            }
        }
        if (Minecraft.theWorld != null && Nigga2.targeting != null) {
            double Nigga4 = Nigga2.espTimer.getDelay();
            if (Nigga2.espTimer.hasTimeElapsed((long)1395546115 ^ 0x532E5802L, true)) {
                Nigga2.targetEspAnim = Nigga2.targetEspUp ? (Nigga2.targetEspAnim += (double)((float)Nigga4 / Float.intBitsToFloat(9.8850259E8f ^ 0x7E915A49) * Float.intBitsToFloat(1.05965837E9f ^ 0x7F291A6C))) : (Nigga2.targetEspAnim -= (double)((float)Nigga4 / Float.intBitsToFloat(9.8859546E8f ^ 0x7E96C511) * Float.intBitsToFloat(1.0552023E9f ^ 0x7EE51C01)));
                if (Nigga2.targetEspAnim > (double)Nigga2.targeting.getEyeHeight()) {
                    Nigga2.targetEspUp = false;
                }
                if (Nigga2.targetEspAnim < 0.0) {
                    Nigga2.targetEspUp = true;
                }
            }
        }
        if (Nigga instanceof EventRender3D && Nigga2.targeting != null) {
            GL11.glLineWidth((float)Float.intBitsToFloat(1.05585824E9f ^ 0x7EAF1E4D));
            GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            if (RenderUtil.interpolateValue(Nigga2.targeting.lastTickPosY, Nigga2.targeting.posY, ((EventRender3D)Nigga).getPartialTicks()) - Nigga2.mc.getRenderManager().renderPosY + Nigga2.targetEspAnim < RenderUtil.interpolateValue(Nigga2.targeting.lastTickPosY, Nigga2.targeting.posY, ((EventRender3D)Nigga).getPartialTicks()) - Nigga2.mc.getRenderManager().renderPosY) {
                Nigga2.targetEspAnim = 0.0;
            }
            if (RenderUtil.interpolateValue(Nigga2.targeting.lastTickPosY, Nigga2.targeting.posY, ((EventRender3D)Nigga).getPartialTicks()) - Nigga2.mc.getRenderManager().renderPosY + Nigga2.targetEspAnim > RenderUtil.interpolateValue(Nigga2.targeting.lastTickPosY, Nigga2.targeting.posY, ((EventRender3D)Nigga).getPartialTicks()) - Nigga2.mc.getRenderManager().renderPosY + (double)Nigga2.targeting.getEyeHeight() + 1.0) {
                Nigga2.targetEspAnim = 0.0;
            }
            RenderUtil.drawCircle(RenderUtil.interpolateValue(Nigga2.targeting.lastTickPosX, Nigga2.targeting.posX, ((EventRender3D)Nigga).getPartialTicks()) - Nigga2.mc.getRenderManager().renderPosX, RenderUtil.interpolateValue(Nigga2.targeting.lastTickPosY, Nigga2.targeting.posY, ((EventRender3D)Nigga).getPartialTicks()) - Nigga2.mc.getRenderManager().renderPosY + Nigga2.targetEspAnim, RenderUtil.interpolateValue(Nigga2.targeting.lastTickPosZ, Nigga2.targeting.posZ, ((EventRender3D)Nigga).getPartialTicks()) - Nigga2.mc.getRenderManager().renderPosZ, 0.0, 2.0, false);
        }
        if (Nigga instanceof EventUpdate && Nigga2.targeting != null) {
            Nigga2.renderLastPitch = Nigga2.renderPitch;
            Nigga2.renderLastYaw = Nigga2.renderYaw;
            if (Nigga2.rotations.getMode().equals(Qprot0.0("\u4826\u71c4\u733c\ue25f\uac59\ucda6\u8c2e\u2451\u1282\u0713\u4791\uaf1f"))) {
                Nigga2.renderYaw = Nigga2.getRotations(Nigga2.targeting)[0];
                Nigga2.renderPitch = Nigga2.getRotations(Nigga2.targeting)[1];
            }
        }
        if (Nigga instanceof EventRenderGUI && !Client.ghostMode) {
            ScaledResolution Nigga5 = new ScaledResolution(Nigga2.mc, Nigga2.mc.displayWidth, Nigga2.mc.displayHeight);
            double Nigga6 = Nigga2.targetPopUp.getDelay();
            boolean Nigga7 = Nigga2.targetPopUp.hasTimeElapsed((long)289113881 ^ 0x113B8718L, true);
            if (Nigga2.targetPopUp.stage > 150.0) {
                Nigga2.targetPopUp.stage = 150.0;
            }
            if (Nigga2.targeting != null && Nigga2.targetPopUp.stage < 150.0 && Nigga7) {
                Nigga2.targetPopUp.stage += Nigga6 / 2.0;
            }
            if (Nigga2.targeting == null && Nigga2.targetPopUp.stage > 0.0) {
                if (Nigga7) {
                    Nigga2.targetPopUp.stage -= Nigga6 / 2.0;
                }
                if (Nigga2.targetPopUp.stage < 0.0) {
                    Nigga2.targetPopUp.stage = 0.0;
                    Nigga2.renderTargeting = null;
                }
            }
            Nigga2.menuX.setMaximum(Nigga5.getScaledWidth() - 100);
            Nigga2.menuX.setMinimum(5.0);
            Nigga2.menuY.setMaximum(Nigga5.getScaledHeight() - 70);
            Nigga2.menuY.setMinimum(5.0);
            if (Nigga2.targeting != null && ((double)Nigga2.mc.thePlayer.getDistanceToEntity(Nigga2.targeting) > Nigga2.range.getValue() || Nigga2.targeting.isInvisible())) {
                Nigga2.targeting = null;
                Nigga2.changeTargetStuff = true;
            }
            if (Nigga2.renderTargeting != null && Nigga2.targetMenu.isEnabled() && ModuleManager.targeting.isTarget(Nigga2.renderTargeting)) {
                Nigga2.renderTargetGUI(Nigga2.renderTargeting, Nigga2.changeTargetStuff);
                Nigga2.changeTargetStuff = false;
            }
        }
        if (Nigga instanceof EventMotion && !Client.ghostMode) {
            if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u4826\u71c4\u736e\ue260\uac57\ucdbe"))) {
                Nigga2.setSuffix(Nigga2.rotations.getMode());
            } else if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u4826\u71c4\u7368\ue265\uac5f\ucdbc\u8c28"))) {
                Nigga2.setSuffix(Nigga2.name);
            } else if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u483c\u71c4\u7373\ue22d\uac7b\ucda7\u8c2c\u244d"))) {
                Nigga2.setSuffix(Qprot0.0("\u4848\u71e6\u7326") + Nigga2.rotations.getMode() + Qprot0.0("\u4848\u71f9\u7326") + Nigga2.range.getValue() + Qprot0.0("\u4848\u71ea\u7326") + Nigga2.minAps.getValue() + "|" + Nigga2.maxAps.getValue());
            }
            List<EntityLivingBase> Nigga8 = Minecraft.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            EventMotion Nigga9 = (EventMotion)Nigga;
            Nigga8 = Nigga8.stream().filter(Nigga2::lambda$1).collect(Collectors.toList());
            if (Nigga2.filter.getMode().equals(Qprot0.0("\u482c\u71c2\u736f\ue279\uac57\ucdbc\u8c2c\u2440"))) {
                Nigga8.sort(Comparator.comparingDouble(Nigga2::lambda$2));
            }
            if (Nigga2.filter.getMode().equals(Qprot0.0("\u4829\u71c5\u737b\ue261\uac53"))) {
                Nigga8.sort(Comparator.comparingDouble(Nigga2::lambda$3));
            }
            if (Nigga2.filter.getMode().equals(Qprot0.0("\u4820\u71ce\u737d\ue261\uac42\ucdba"))) {
                Nigga8.sort(Comparator.comparingDouble(Killaura::lambda$4));
            }
            if (!Nigga8.isEmpty()) {
                Object Nigga7 = Nigga8.iterator();
                while (Nigga7.hasNext()) {
                    EntityLivingBase cfr_ignored_0 = (EntityLivingBase)Nigga7.next();
                }
                Nigga7 = (EntityLivingBase)Nigga8.get(0);
                Nigga7 = Nigga2.singleTarget((EntityLivingBase)Nigga7, Nigga8);
                boolean Nigga10 = false;
                if (Nigga7 instanceof EntityPlayer) {
                    Nigga10 = ((EntityPlayer)Nigga7).isFake;
                }
                if (!Nigga10 && !((Entity)Nigga7).isInvisible() && Nigga2.isInView((EntityLivingBase)Nigga7) && ((Entity)Nigga7).getDisplayName().getFormattedText().contains(Nigga2.teamColour) && ModuleManager.targeting.isTarget((EntityLivingBase)Nigga7) && !((Entity)Nigga7).getName().toLowerCase().startsWith(Qprot0.0("\u4810\u71ca\u737f"))) {
                    EventAttack Nigga11 = new EventAttack((Entity)Nigga7);
                    Nigga11.setType(EventType.PRE);
                    Client.onEvent(Nigga11);
                    if (Nigga2.targeting != Nigga7) {
                        Nigga2.changeTargetStuff = true;
                    }
                    Nigga2.targeting = Nigga7;
                    Nigga2.renderTargeting = Nigga2.targeting;
                    if (Nigga2.mc.objectMouseOver != null && Nigga2.mc.objectMouseOver.entityHit != Nigga7 && Nigga2.rayTrace.isEnabled() || !Nigga2.rayTrace.isEnabled()) {
                        if (!Nigga2.rotations.getMode().equals(Qprot0.0("\u4826\u71c4\u733c\ue25f\uac59\ucda6\u8c2e\u2451\u1282\u0713\u4791\uaf1f")) && !Nigga2.mode.getMode().equals(Qprot0.0("\u483c\u71ca\u736e\ue26a\uac53\ucda6\u8c6f\u2476\u129f\u070e\u479e\uaf0a\uca9b\u3784\ud83d"))) {
                            if (Nigga2.mode.getMode().equals(Qprot0.0("\u482b\u71c7\u737d\ue27e\uac45\ucdbb\u8c2c"))) {
                                Nigga2.mc.thePlayer.setSprinting(false);
                            }
                            Nigga2.renderYaw = RotationUtil.fixedSensitivity(Nigga2.mc.gameSettings.mouseSensitivity, Nigga2.getRotations((Entity)Nigga7)[0], Nigga2.getRotations((Entity)Nigga7)[1])[0];
                            Nigga2.renderPitch = RotationUtil.fixedSensitivity(Nigga2.mc.gameSettings.mouseSensitivity, Nigga2.getRotations((Entity)Nigga7)[0], Nigga2.getRotations((Entity)Nigga7)[1])[1];
                            Nigga2.targetYaw = Nigga2.renderYaw;
                            Nigga9.setYaw(Nigga2.targetRotations[0]);
                            Nigga9.setPitch(Nigga2.targetRotations[1]);
                        }
                        if (Nigga2.rotations.getMode() == Qprot0.0("\u4824\u71ce\u737b\ue264\uac42")) {
                            Nigga2.renderYaw = RotationUtil.fixedSensitivity(Nigga2.mc.gameSettings.mouseSensitivity, Nigga2.getRotations((Entity)Nigga7)[0], Nigga2.getRotations((Entity)Nigga7)[1])[0];
                            Nigga2.renderPitch = RotationUtil.fixedSensitivity(Nigga2.mc.gameSettings.mouseSensitivity, Nigga2.getRotations((Entity)Nigga7)[0], Nigga2.getRotations((Entity)Nigga7)[1])[1];
                            Nigga2.targetYaw = Nigga2.renderYaw;
                            Nigga2.mc.thePlayer.rotationYaw = Nigga2.renderYaw;
                            Nigga2.mc.thePlayer.rotationPitch = Nigga2.renderPitch;
                        }
                    }
                    if (Nigga2.blockHit.isEnabled() && Nigga2.checkSwordItem() && Nigga2.mc.thePlayer.itemInUse == null) {
                        Nigga2.mc.thePlayer.setItemInUse(Nigga2.mc.thePlayer.inventory.getCurrentItem(), 20);
                    }
                    if (Nigga2.timer.hasTimeElapsed((long)Nigga2.clickDelay, true)) {
                        Nigga2.clickDelay = RandomHelper.randomDouble((int)(1000.0 / Nigga2.minAps.getValue()), (int)(1000.0 / Nigga2.maxAps.getValue()));
                        ((EntityLivingBase)Nigga7).getHealth();
                        Nigga2.mc.thePlayer.swingItem();
                        if (Nigga2.rotations.getMode().equals(Qprot0.0("\u4824\u71ce\u737b\ue264\uac42")) && Nigga2.mc.objectMouseOver.entityHit == Nigga7 || !Nigga2.rotations.getMode().equals(Qprot0.0("\u4824\u71ce\u737b\ue264\uac42"))) {
                            Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)Nigga7, C02PacketUseEntity.Action.ATTACK));
                        }
                        Nigga2.rotations.getMode().equals(Qprot0.0("\u483c\u71ca\u736e\ue26a\uac53\ucda6\u8c6f\u2476\u129f\u070e\u479e\uaf0a\uca9b"));
                        if (Nigga2.crits.isEnabled() && Nigga2.mc.thePlayer.onGround) {
                            Nigga2.mc.thePlayer.jump();
                        }
                        Nigga2.lastHealth = ((EntityLivingBase)Nigga7).getHealth();
                    }
                }
            } else {
                Nigga2.targeting = null;
                Nigga2.lastHealth = 0.0;
                Nigga2.thirdPersonYaw = Nigga2.mc.thePlayer.rotationYaw;
            }
        }
    }

    public void renderTargetGUI(EntityLivingBase Nigga, boolean Nigga2) {
        Killaura Nigga3;
        MinecraftFontRenderer Nigga4 = Client.fontNormal;
        MinecraftFontRenderer Nigga5 = FontUtil.cleantiny;
        MinecraftFontRenderer Nigga6 = FontUtil.cleanmedium;
        int Nigga7 = (int)Nigga3.menuX.getValue();
        int Nigga8 = (int)Nigga3.menuY.getValue();
        int Nigga9 = 140;
        String Nigga10 = Qprot0.0("\u4826\u71c4\u733c\u914b\u514d\ucdb7\u8c22");
        if (Nigga3.mc.thePlayer.getHeldItem() != null) {
            Nigga10 = Nigga3.mc.thePlayer.getHeldItem().getItem().getItemStackDisplayName(Nigga3.mc.thePlayer.getHeldItem());
        }
        if (Nigga9 < Nigga4.getStringWidth(Nigga10) + 70) {
            Nigga9 = Nigga4.getStringWidth(Nigga10) + 70;
        }
        int Nigga11 = Nigga8 + 80;
        int Nigga12 = Nigga7 + Nigga9;
        double Nigga13 = Nigga.getHealth() / Nigga.getMaxHealth();
        GL11.glEnable((int)3042);
        Nigga3.mc.getRenderItem();
        Nigga3.targetSmoothAnim.getDelay();
        if (Nigga3.targetSmoothAnim.hasTimeElapsed((long)158145093 ^ 0x96D1A4FL, true)) {
            Nigga3.targetSmoothHealth -= (Nigga3.targetSmoothHealth - (double)Nigga.getHealth()) / 10.0 + 0.0;
        }
        if (Nigga3.targetSmoothHealth < (double)Nigga.getHealth()) {
            Nigga3.targetSmoothHealth = Nigga.getHealth();
        }
        GL11.glEnable((int)3089);
        RenderUtil.scissor((double)(Nigga7 + Nigga9 / 2) - Nigga3.targetPopUp.stage, (double)(Nigga8 + 40) - Nigga3.targetPopUp.stage, Nigga3.targetPopUp.stage * 2.0, Nigga3.targetPopUp.stage * 1.8);
        RenderUtil.drawRoundedRect(Nigga7, Nigga8, Nigga12, Nigga11, 5.0, -585097184);
        Nigga4.drawString(Nigga10, Nigga12 - Nigga4.getStringWidth(Nigga10) - 10, Nigga8 + 10, -1);
        double Nigga14 = Nigga3.mc.thePlayer.getDistanceToEntity(Nigga);
        Nigga14 = Math.round(Nigga14 * 100.0);
        Nigga5.drawString("" + (Nigga14 /= 100.0), Nigga12 - Nigga5.getStringWidth("" + Nigga14) - 10, Nigga8 + 40, -1);
        double Nigga15 = Math.sqrt(Math.pow(Nigga.posX - Nigga.lastTickPosX, 2.0) + Math.pow(Nigga.posZ - Nigga.lastTickPosZ, 2.0));
        Nigga15 = Math.round(Nigga15 * 100.0);
        Nigga15 /= 10.0;
        Nigga5.drawString(String.valueOf(Nigga15 *= 2.0) + Qprot0.0("\u4848\u71c9\u7333\u9171"), Nigga12 - Nigga5.getStringWidth(String.valueOf(Nigga15) + Qprot0.0("\u4848\u71c9\u7333\u9171")) - 10, Nigga8 + 20, -1);
        Nigga5.drawString(String.valueOf(Client.getPlayerPing(Nigga.getName())) + Qprot0.0("\u4848\u71c6\u736f"), Nigga12 - Nigga5.getStringWidth(String.valueOf(Client.getPlayerPing(Nigga.getName())) + Qprot0.0("\u4848\u71c6\u736f")) - 10, Nigga8 + 30, -1);
        double Nigga16 = Math.round(Nigga3.targetSmoothHealth * 10.0);
        Nigga4.drawString("" + (Nigga16 /= 10.0), Nigga12 - 10 - Nigga4.getStringWidth("" + Nigga16), Nigga11 - 18, -1);
        Nigga6.drawString(String.valueOf(Nigga.onGround ? Qprot0.0("\u4827\u71c5") : Qprot0.0("\u4827\u71cd\u737a")) + Qprot0.0("\u4848\u71ec\u736e\u916d\u514c\ucdbc\u8c2b"), Nigga12 - 50 - Nigga6.getStringWidth(String.valueOf(Nigga.onGround ? Qprot0.0("\u4827\u71c5") : Qprot0.0("\u4827\u71cd\u737a")) + Qprot0.0("\u4848\u71ec\u736e\u916d\u514c\ucdbc\u8c2b")), Nigga8 + 30, -1);
        boolean Nigga17 = false;
        if (Nigga3.mc.thePlayer.getHealth() >= Nigga.getHealth()) {
            Nigga17 = true;
        }
        Nigga6.drawString(Nigga17 ? Qprot0.0("\u483f\u71c2\u7372\u916c\u5150\ucdbc\u8c28") : Qprot0.0("\u4824\u71c4\u736f\u916b\u5157\ucdb5"), Nigga12 - 50 - Nigga6.getStringWidth(Nigga17 ? Qprot0.0("\u483f\u71c2\u7372\u916c\u5150\ucdbc\u8c28") : Qprot0.0("\u4824\u71c4\u736f\u916b\u5157\ucdb5")), Nigga8 + 40, -1);
        RenderUtil.initMask();
        RenderUtil.drawRoundedRect(Nigga7, (double)Nigga11 - 4.5, Nigga3.targetAnim, Nigga11, 4.0, -1);
        RenderUtil.useMask();
        Gui.drawStaticGradientRect(Nigga7, Nigga11 - 5, Nigga7 + Nigga9 / 2, Nigga11, -256, -65536);
        Gui.drawStaticGradientRect(Nigga7 + Nigga9 / 2, Nigga11 - 5, Nigga12, Nigga11, -16711936, -256);
        RenderUtil.disableMask();
        if (Nigga2) {
            Nigga3.targetAnim = Nigga12;
        }
        double Nigga18 = Nigga3.targetAnimAnim.getDelay();
        if (Nigga3.targetAnimAnim.hasTimeElapsed((long)903465018 ^ 0x35D9C830L, true) && Nigga3.targetAnim > (double)Nigga7 + Nigga13 * (double)Nigga9) {
            Nigga3.targetAnim -= Nigga18 / 200.0 + (Nigga3.targetAnim - (double)Nigga7 + Nigga13 * (double)Nigga9) / 100.0;
        }
        if (Nigga3.targetAnim < (double)Nigga7 + Nigga13 * (double)Nigga9) {
            Nigga3.targetAnim = (double)Nigga7 + Nigga13 * (double)Nigga9;
        }
        Nigga.noTag = true;
        GuiInventory.drawEntityOnScreen(Nigga7 + 23, Nigga8 + 65, 28, Nigga.rotationYaw, Nigga.rotationPitch, Nigga);
        Nigga.noTag = false;
        GL11.glDisable((int)3089);
    }

    public float[] getRotations(Entity Nigga) {
        Killaura Nigga2;
        if (Nigga2.rotations.getMode().equals(Qprot0.0("\u4824\u71ce\u737b\uf460\ub246"))) {
            float Nigga3 = Float.intBitsToFloat(1.08880064E9f ^ 0x7F65C78D) - (float)Nigga2.yawSense.getValue();
            float Nigga4 = Float.intBitsToFloat(1.09598426E9f ^ 0x7ED3648F) - (float)Nigga2.pitchSense.getValue();
            float Nigga5 = Float.intBitsToFloat(1.06601952E9f ^ 0x7F2A2AA9) - (float)Nigga2.motionTracking.getValue() * Float.intBitsToFloat(1.03955392E9f ^ 0x7D56558F);
            double Nigga6 = Nigga.posX - Nigga2.mc.thePlayer.posX - Nigga.motionX * (double)Nigga5 - (Nigga2.mc.thePlayer.posX - Nigga2.mc.thePlayer.lastTickPosX) * (double)Nigga5 / 2.0;
            double Nigga7 = Nigga.posZ - Nigga2.mc.thePlayer.posZ - Nigga.motionZ * (double)Nigga5 - (Nigga2.mc.thePlayer.posZ - Nigga2.mc.thePlayer.lastTickPosZ) * (double)Nigga5 / 2.0;
            double Nigga8 = Nigga.posY - 0.0 + (double)Nigga.getEyeHeight();
            double Nigga9 = Nigga2.mc.thePlayer.posY + (double)Nigga2.mc.thePlayer.getEyeHeight() - Nigga8 + Nigga.motionY * (double)Nigga5 + (Nigga2.mc.thePlayer.posY - Nigga2.mc.thePlayer.lastTickPosY) * (double)Nigga5 / 2.0;
            double Nigga10 = MathHelper.sqrt_double(Nigga6 * Nigga6 + Nigga7 * Nigga7);
            float Nigga11 = (float)(Math.atan2(Nigga7, Nigga6) * 180.0 / Math.PI) - Float.intBitsToFloat(1.0068409E9f ^ 0x7EB72C49);
            float Nigga12 = (float)(Math.atan2(Nigga9, Nigga10) * 180.0 / Math.PI);
            float Nigga13 = Nigga11 - Nigga2.mc.thePlayer.rotationYaw;
            float Nigga14 = Nigga12 - Nigga2.mc.thePlayer.rotationPitch;
            Nigga13 = MathHelper.wrapAngleTo180_float(Nigga13);
            Nigga14 = MathHelper.wrapAngleTo180_float(Nigga14);
            Nigga11 -= Nigga13 * Nigga3;
            Nigga12 -= Nigga14 * Nigga4;
            if (Nigga2.mouseMat.isEnabled()) {
                Nigga2.mouseMatCurrent = (int)((float)Nigga2.mouseMatCurrent + (Nigga11 - Nigga2.mc.thePlayer.rotationYaw) / Float.intBitsToFloat(1.07000467E9f ^ 0x7EC6F9AF));
                if ((double)Math.abs(Nigga2.mouseMatCurrent) > Nigga2.mouseMatLength.getValue() * 500.0) {
                    Nigga11 = Nigga2.mc.thePlayer.rotationYaw;
                    Nigga12 = Nigga2.mc.thePlayer.rotationPitch;
                    if ((double)Math.abs(Nigga2.mouseMatCurrent) > Nigga2.mouseMatLength.getValue() * 500.0 + Nigga2.mouseMatLength.getValue() * 125.0) {
                        Nigga2.mouseMatCurrent = 0;
                    }
                }
            }
            return new float[]{Nigga11, Nigga12};
        }
        double Nigga15 = Nigga.posX - Nigga2.mc.thePlayer.posX;
        double Nigga16 = Nigga.posZ - Nigga2.mc.thePlayer.posZ;
        double Nigga17 = Nigga.posY + (double)Nigga.getEyeHeight();
        double Nigga18 = Nigga2.mc.thePlayer.posY + (double)Nigga2.mc.thePlayer.getEyeHeight() - Nigga17;
        double Nigga19 = MathHelper.sqrt_double(Nigga15 * Nigga15 + Nigga16 * Nigga16);
        float Nigga20 = (float)(Math.atan2(Nigga16, Nigga15) * 180.0 / Math.PI) - Float.intBitsToFloat(1.0174825E9f ^ 0x7E118D17);
        float Nigga21 = (float)(Math.atan2(Nigga18, Nigga19) * 180.0 / Math.PI);
        Nigga2.pitchAccur.getValue();
        Random Nigga22 = new Random();
        Nigga22.nextInt((int)Nigga2.randomYaw.getValue());
        Nigga2.randomYaw.getValue();
        return new float[]{Nigga20, Nigga21};
    }

    public boolean isInView(EntityLivingBase Nigga) {
        Killaura Nigga2;
        return (double)Math.abs(MathHelper.wrapAngleTo180_float(Nigga2.getRotations(Nigga)[0] - MathHelper.wrapAngleTo180_float(Nigga2.mc.thePlayer.rotationYawHead))) < Nigga2.fov.getValue() / 2.0 && Client.booleanThingy(!Nigga2.walls.isEnabled(), Nigga2.mc.thePlayer.canBlockBeSeen(new BlockPos(Nigga.posX, Nigga.posY, Nigga.posZ)));
    }
}


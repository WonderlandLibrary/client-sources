package cn.Hanabi.modules.Combat;

import cn.Hanabi.value.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import cn.Hanabi.*;
import net.minecraft.client.renderer.*;
import cn.Hanabi.utils.fontmanager.*;
import java.util.*;
import net.minecraft.entity.*;
import cn.Hanabi.modules.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import javax.vecmath.*;
import ClassSub.*;
import net.minecraft.item.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.network.play.client.*;
import cn.Hanabi.modules.World.*;
import net.minecraft.entity.item.*;
import cn.Hanabi.modules.Player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import java.lang.management.*;
import javax.swing.*;
import java.awt.*;

public class KillAura extends Mod
{
    public static Value<Boolean> autoBlock;
    public Value<Double> hurttime;
    public Value<Double> mistake;
    public static Value<Double> reach;
    public Value<Double> blockReach;
    public Value<Double> cps;
    public Value<Double> turn;
    public Value<String> rotMode;
    public static Value<Double> switchsize;
    public Value<Double> switchDelay;
    public Value<Double> yawDiff;
    public Value<Boolean> attackPlayers;
    public Value<Boolean> attackAnimals;
    public Value<Boolean> attackMobs;
    public Value<Boolean> throughblock;
    public Value<Boolean> rotations;
    public Value<Boolean> autodisable;
    public Value<Boolean> invisible;
    public Value<Boolean> targetHUD;
    public Value<Boolean> esp;
    public Value<Boolean> aac;
    public Value<String> priority;
    public static boolean isBlocking;
    public static ArrayList<EntityLivingBase> targets;
    public Random random;
    public static ArrayList<EntityLivingBase> attacked;
    public boolean needBlock;
    public boolean needUnBlock;
    public int index;
    public static EntityLivingBase target;
    public static EntityLivingBase needHitBot;
    public Class205 switchTimer;
    public Class205 attacktimer;
    public Vector2f lastAngles;
    private Class318 angleUtility;
    AxisAlignedBB axisAlignedBB;
    float shouldAddYaw;
    float[] lastRotation;
    private float rotationYawHead;
    private float[] lastRotations;
    
    
    public KillAura() {
        super("KillAura", Category.COMBAT);
        this.hurttime = new Value<Double>("KillAura_HurtTime", 10.0, 1.0, 10.0, 1.0);
        this.mistake = new Value<Double>("KillAura_Mistakes", 0.0, 0.0, 20.0, 1.0);
        this.blockReach = new Value<Double>("KillAura_BlockRange", 0.5, 0.0, 3.0, 0.1);
        this.cps = new Value<Double>("KillAura_CPS", 10.0, 1.0, 20.0, 1.0);
        this.turn = new Value<Double>("KillAura_TurnHeadSpeed", 15.0, 5.0, 120.0, 1.0);
        this.rotMode = new Value<String>("KillAura", "RotationMode", 0);
        this.switchDelay = new Value<Double>("KillAura_SwitchDelay", 50.0, 0.0, 2000.0, 10.0);
        this.yawDiff = new Value<Double>("KillAura_YawDifference", 15.0, 5.0, 90.0, 1.0);
        this.attackPlayers = new Value<Boolean>("KillAura_Players", true);
        this.attackAnimals = new Value<Boolean>("KillAura_Animals", false);
        this.attackMobs = new Value<Boolean>("KillAura_Mobs", false);
        this.throughblock = new Value<Boolean>("KillAura_ThroughBlock", true);
        this.rotations = new Value<Boolean>("KillAura_HeadRotations", true);
        this.autodisable = new Value<Boolean>("KillAura_AutoDisable", true);
        this.invisible = new Value<Boolean>("KillAura_Invisibles", false);
        this.targetHUD = new Value<Boolean>("KillAura_ShowTarget", true);
        this.esp = new Value<Boolean>("KillAura_ESP", true);
        this.aac = new Value<Boolean>("KillAura_AAC", false);
        this.priority = new Value<String>("KillAura", "Priority", 1);
        this.random = new Random();
        this.needBlock = false;
        this.needUnBlock = false;
        this.switchTimer = new Class205();
        this.attacktimer = new Class205();
        this.lastAngles = new Vector2f(0.0f, 0.0f);
        this.angleUtility = new Class318(110.0f, 120.0f, 30.0f, 40.0f);
        this.lastRotation = new float[] { 0.0f, 0.0f };
        this.setState(true);
        this.priority.mode.add("Angle");
        this.priority.mode.add("Range");
        this.priority.mode.add("Fov");
        this.rotMode.mode.add("Simple");
        this.rotMode.mode.add("Smooth");
        this.rotMode.mode.add("Instant");
        KillAura.attacked = new ArrayList<EntityLivingBase>();
    }
    
    @EventTarget
    public void targetHud(final EventRender2D eventRender2D) {
        if (this.targetHUD.getValueState()) {
            final ScaledResolution scaledResolution = new ScaledResolution(KillAura.mc);
            if (KillAura.target != null) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final UnicodeFontRenderer wqy18 = Hanabi.INSTANCE.fontManager.wqy18;
                wqy18.drawStringWithShadow(KillAura.target.getName(), scaledResolution.getScaledWidth() / 2.0f - wqy18.getStringWidth(KillAura.target.getName()) / 2.0f, scaledResolution.getScaledHeight() / 2.0f - 33.0f, 16777215);
                RenderHelper.enableGUIStandardItemLighting();
                KillAura.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glDepthMask(false);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                for (int n = 0; n < KillAura.target.getMaxHealth() / 2.0f; ++n) {
                    KillAura.mc.ingameGUI.drawTexturedModalRect(scaledResolution.getScaledWidth() / 2 - KillAura.target.getMaxHealth() / 2.0f * 10.0f / 2.0f + n * 10, (float)(scaledResolution.getScaledHeight() / 2 - 20), 16, 0, 9, 9);
                }
                for (int n2 = 0; n2 < KillAura.target.getHealth() / 2.0f; ++n2) {
                    KillAura.mc.ingameGUI.drawTexturedModalRect(scaledResolution.getScaledWidth() / 2 - KillAura.target.getMaxHealth() / 2.0f * 10.0f / 2.0f + n2 * 10, (float)(scaledResolution.getScaledHeight() / 2 - 20), 52, 0, 9, 9);
                }
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GlStateManager.disableBlend();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        if (KillAura.target == null || !this.esp.getValueState()) {
            return;
        }
        for (final EntityLivingBase entityLivingBase : KillAura.targets) {
            KillAura.mc.getRenderManager();
            final double n = entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * Class211.getTimer().renderPartialTicks - ((IRenderManager)KillAura.mc.getRenderManager()).getRenderPosX();
            KillAura.mc.getRenderManager();
            final double n2 = entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * Class211.getTimer().renderPartialTicks - ((IRenderManager)KillAura.mc.getRenderManager()).getRenderPosY();
            KillAura.mc.getRenderManager();
            Class246.drawEntityESP(n, n2, entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * Class211.getTimer().renderPartialTicks - ((IRenderManager)KillAura.mc.getRenderManager()).getRenderPosZ(), entityLivingBase.getEntityBoundingBox().maxX - entityLivingBase.getEntityBoundingBox().minX, entityLivingBase.getEntityBoundingBox().maxY - entityLivingBase.getEntityBoundingBox().minY + 0.25, (entityLivingBase.hurtTime > 1) ? 1.0f : 0.0f, (entityLivingBase.hurtTime > 1) ? 0.0f : 1.0f, 0.0f, 0.2f, (entityLivingBase.hurtTime > 1) ? 1.0f : 0.0f, (entityLivingBase.hurtTime > 1) ? 0.0f : 1.0f, 0.0f, 1.0f, 2.0f);
        }
    }
    
    public static double getRandomDoubleInRange(final double n, final double n2) {
        return (n >= n2) ? n : (new Random().nextDouble() * (n2 - n) + n);
    }
    
    @EventTarget
    public void onReload(final EventWorldChange eventWorldChange) {
        if (this.autodisable.getValueState()) {
            this.set(false);
        }
    }
    
    @EventTarget
    public void onPre(final EventPreMotion eventPreMotion) {
        this.rotationYawHead = KillAura.mc.thePlayer.rotationYawHead;
        this.needBlock = false;
        KillAura.needHitBot = null;
        if (!KillAura.targets.isEmpty() && this.index >= KillAura.targets.size()) {
            this.index = 0;
        }
        for (final EntityLivingBase entityLivingBase : KillAura.targets) {
            if (this.isValidEntity((Entity)entityLivingBase)) {
                continue;
            }
            KillAura.targets.remove(entityLivingBase);
        }
        this.getTarget(eventPreMotion);
        if (KillAura.targets.size() == 0) {
            KillAura.target = null;
        }
        else {
            KillAura.target = KillAura.targets.get(this.index);
            this.axisAlignedBB = null;
            if (KillAura.mc.thePlayer.getDistanceToEntity((Entity)KillAura.target) > KillAura.reach.getValueState()) {
                KillAura.target = KillAura.targets.get(0);
            }
        }
        if (KillAura.target != null) {
            if (KillAura.target.hurtTime == 10 && this.switchTimer.isDelayComplete(this.switchDelay.getValueState()) && KillAura.targets.size() > 1) {
                this.switchTimer.reset();
                ++this.index;
            }
            Math.abs(Math.abs(MathHelper.wrapAngleTo180_float(this.rotationYawHead)) - Math.abs(MathHelper.wrapAngleTo180_float(Class45.getRotations((Entity)KillAura.target)[0])));
            if (KillAura.target.hurtTime == 10 && Class346.abuses < 0) {
                KillAura.mc.theWorld.removeEntity((Entity)KillAura.target);
            }
            if (this.rotations.getValueState() && Class346.abuses > 0) {
                if (this.rotMode.isCurrentMode("Simple")) {
                    if (!this.aac.getValueState()) {
                        final float[] array = getEntityRotations(KillAura.target, this.lastRotations, this.aac.getValueState(), (int)(Object)this.turn.getValueState());
                        this.lastRotations = new float[] { array[0], array[1] };
                        eventPreMotion.setYaw(array[0] + this.randomNumber(-3, 3));
                        eventPreMotion.setPitch(array[1] + this.randomNumber(-3, 3));
                        this.rotationYawHead = eventPreMotion.getYaw();
                    }
                    if (this.aac.getValueState()) {
                        final boolean b = false;
                        final boolean b2 = true;
                        final double n = 0.2;
                        final double n2 = 0.25;
                        if (this.axisAlignedBB == null) {
                            this.axisAlignedBB = new AxisAlignedBB(KillAura.target.posX, KillAura.target.posY, KillAura.target.posZ, KillAura.target.posX + 1.0, KillAura.target.posY + 1.0, KillAura.target.posZ + 1.0);
                        }
                        this.axisAlignedBB = this.axisAlignedBB.offset((KillAura.target.posX - KillAura.target.prevPosX) * getRandomDoubleInRange(n, n2), (KillAura.target.posY - KillAura.target.prevPosY) * getRandomDoubleInRange(n, n2), (KillAura.target.posZ - KillAura.target.prevPosZ) * getRandomDoubleInRange(n, n2));
                        final float[] array2 = Class272.l(new float[] { Class272.ll1[0], Class272.ll1[1] }, Class272.l(Class272.l(this.axisAlignedBB, b && !this.attacktimer.isDelayComplete((long)(this.cps.getValueState() / 2.0))), b2), (float)(Math.random() * (this.turn.getValueState() / 3.0 * 1.2 - this.turn.getValueState() / 3.0 * 0.8) + this.turn.getValueState() / 3.0 * 0.8));
                        if (!ModManager.getModule("Scaffold").isEnabled()) {
                            eventPreMotion.setYaw(array2[0]);
                            this.rotationYawHead = eventPreMotion.getYaw();
                            eventPreMotion.setPitch(array2[1]);
                        }
                    }
                }
                else if (this.rotMode.isCurrentMode("Smooth")) {
                    final Class274 smoothAngle = this.angleUtility.smoothAngle(this.angleUtility.calculateAngle(new Class266<Double>(KillAura.target.getEntityBoundingBox().minX + (KillAura.target.getEntityBoundingBox().maxX - KillAura.target.getEntityBoundingBox().minX) / 2.0, ((KillAura.target instanceof EntityPig || KillAura.target instanceof EntitySpider) ? (KillAura.target.getEntityBoundingBox().minY - KillAura.target.getEyeHeight() * 1.2) : KillAura.target.posY) - ((Math.abs(KillAura.target.posY - KillAura.mc.thePlayer.posY) > 1.8) ? (Math.abs(KillAura.target.posY - KillAura.mc.thePlayer.posY) / Math.abs(KillAura.target.posY - KillAura.mc.thePlayer.posY) / 2.0) : Math.abs(KillAura.target.posY - KillAura.mc.thePlayer.posY)), KillAura.target.getEntityBoundingBox().minZ + (KillAura.target.getEntityBoundingBox().maxZ - KillAura.target.getEntityBoundingBox().minZ) / 2.0), new Class266<Double>(KillAura.mc.thePlayer.getEntityBoundingBox().minX + (KillAura.mc.thePlayer.getEntityBoundingBox().maxX - KillAura.mc.thePlayer.getEntityBoundingBox().minX) / 2.0, KillAura.mc.thePlayer.posY, KillAura.mc.thePlayer.getEntityBoundingBox().minZ + (KillAura.mc.thePlayer.getEntityBoundingBox().maxZ - KillAura.mc.thePlayer.getEntityBoundingBox().minZ) / 2.0)), new Class274(Float.valueOf(this.lastRotation[0]), Float.valueOf(this.lastRotation[1])), (float)(Object)this.turn.getValueState() * 10.0f, (float)(Object)this.turn.getValueState() * 10.0f);
                    final Random random = new Random();
                    eventPreMotion.setYaw(smoothAngle.getYaw() + random.nextInt(10) - 5.0f);
                    eventPreMotion.setPitch(smoothAngle.getPitch() + random.nextInt(3) - 2.0f);
                    this.lastRotation[0] = eventPreMotion.getYaw();
                    this.lastRotation[1] = eventPreMotion.getPitch();
                    this.rotationYawHead = eventPreMotion.getYaw();
                }
                else if (this.rotMode.isCurrentMode("Instant")) {
                    final float[] array3 = Class45.getRotations((Entity)KillAura.target);
                    final Random random2 = new Random();
                    eventPreMotion.setYaw(array3[0] + random2.nextInt(10) - 5.0f);
                    eventPreMotion.setPitch(array3[1] + random2.nextInt(3) - 2.0f);
                    this.rotationYawHead = eventPreMotion.getYaw();
                }
            }
            if (KillAura.target != null && this.aac.getValueState()) {
                this.doAttack();
            }
        }
        else {
            this.lastRotation[0] = KillAura.mc.thePlayer.rotationYaw;
            this.lastAngles.x = KillAura.mc.thePlayer.rotationYaw;
            KillAura.targets.clear();
            if (this.needUnBlock) {
                this.unBlock(true);
            }
        }
    }
    
    private void doBlock(final boolean b) {
        if (b) {
            ((IEntityPlayer)KillAura.mc.thePlayer).setItemInUseCount(KillAura.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
        }
        KillAura.mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(KillAura.mc.thePlayer.inventory.getCurrentItem()));
        this.needUnBlock = true;
    }
    
    private void unBlock(final boolean b) {
        if (b) {
            ((IEntityPlayer)KillAura.mc.thePlayer).setItemInUseCount(0);
        }
        KillAura.mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        this.needUnBlock = false;
    }
    
    public static float[] getEntityRotations(final EntityLivingBase entityLivingBase, final float[] array, final boolean b, final int n) {
        final Class80 class80 = new Class80(b, n);
        final Class209 smoothAngle = class80.smoothAngle(class80.calculateAngle(new Vector3d(entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.getEyeHeight(), entityLivingBase.posZ), new Vector3d(KillAura.mc.thePlayer.posX, KillAura.mc.thePlayer.posY + KillAura.mc.thePlayer.getEyeHeight(), KillAura.mc.thePlayer.posZ)), new Class209(array[0], array[1]));
        return new float[] { KillAura.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(smoothAngle.getYaw() - KillAura.mc.thePlayer.rotationYaw), smoothAngle.getPitch() };
    }
    
    private int randomNumber(final int n, final int n2) {
        return (int)(Math.random() * (n - n2)) + n2;
    }
    
    private void doAttack() {
        if (this.attacktimer.isDelayComplete(1000 / (int)(Object)this.cps.getValueState() + this.random.nextInt(50) - 30 - 20 + this.random.nextInt(50))) {
            boolean b = Hanabi.flag < 0;
            final boolean b2 = KillAura.mc.thePlayer.getDistanceToEntity((Entity)KillAura.target) <= KillAura.reach.getValueState() && Hanabi.flag > 0;
            if (b2) {
                this.attacktimer.reset();
                if (KillAura.target.hurtTime > this.hurttime.getValueState() || this.random.nextInt(100) < (int)(Object)this.mistake.getValueState()) {
                    b = true;
                }
                if (Math.abs(Math.abs(MathHelper.wrapAngleTo180_float(this.rotationYawHead)) - Math.abs(MathHelper.wrapAngleTo180_float(Class45.getRotations((Entity)KillAura.target)[0]))) > this.yawDiff.getValueState() && !ModManager.getModule("Scaffold").isEnabled()) {
                    b = true;
                }
            }
            if (KillAura.mc.thePlayer.isBlocking() || (KillAura.mc.thePlayer.getHeldItem() != null && KillAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && KillAura.autoBlock.getValueState())) {
                this.unBlock(!KillAura.mc.thePlayer.isBlocking() && !KillAura.autoBlock.getValueState() && KillAura.mc.thePlayer.getItemInUseCount() > 0);
            }
            if (b2) {
                this.attack(b);
            }
            this.needBlock = true;
        }
    }
    
    @EventTarget
    public void onPost(final EventPostMotion eventPostMotion) {
        if (KillAura.target != null && !this.aac.getValueState()) {
            this.doAttack();
        }
        if (KillAura.target != null && ((KillAura.mc.thePlayer.getHeldItem() != null && KillAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && KillAura.autoBlock.getValueState()) || KillAura.mc.thePlayer.isBlocking()) && this.needBlock) {
            this.doBlock(true);
        }
    }
    
    @EventTarget
    public void onBlockPacket(final EventPacket eventPacket) {
        final Packet packet = eventPacket.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement && !KillAura.isBlocking) {
            KillAura.isBlocking = true;
        }
        if (packet instanceof C07PacketPlayerDigging && KillAura.isBlocking) {
            KillAura.isBlocking = false;
        }
    }
    
    private void attack(final boolean b) {
        KillAura.mc.thePlayer.swingItem();
        if (!b) {
            this.needBlock = true;
            KillAura.mc.thePlayer.onCriticalHit((Entity)KillAura.target);
            KillAura.mc.thePlayer.onEnchantmentCritical((Entity)KillAura.target);
            final ArrayList<EntityLivingBase> list = new ArrayList<EntityLivingBase>();
            for (final Entity entity : KillAura.mc.theWorld.loadedEntityList) {
                final float abs = Math.abs(Math.abs(MathHelper.wrapAngleTo180_float(this.rotationYawHead)) - Math.abs(MathHelper.wrapAngleTo180_float(Class45.getRotations(entity)[0])));
                if (entity instanceof EntityZombie && entity.isInvisible() && (abs < this.yawDiff.getValueState() || KillAura.mc.thePlayer.getDistanceToEntity((Entity)KillAura.target) < 1.0f) && KillAura.mc.thePlayer.getDistanceToEntity(entity) < KillAura.reach.getValueState() && entity != KillAura.mc.thePlayer) {
                    list.add((EntityLivingBase)entity);
                }
            }
            if (list.size() == 0) {
                list.add(KillAura.target);
            }
            KillAura.needHitBot = list.get(this.random.nextInt(list.size()));
            EventManager.call(new EventAttack((Entity)KillAura.target));
            if (Class346.abuses > 0) {
                KillAura.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity((Entity)(this.aac.getValueState() ? KillAura.needHitBot : KillAura.target), C02PacketUseEntity.Action.ATTACK));
            }
            if (!KillAura.attacked.contains(KillAura.target) && KillAura.target instanceof EntityPlayer) {
                KillAura.attacked.add(KillAura.target);
            }
            KillAura.needHitBot = null;
        }
    }
    
    private void getTarget(final EventPreMotion p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore          6
        //     5: goto            9
        //     8: return         
        //     9: iload           6
        //    11: ifne            8
        //    14: iload           6
        //    16: ifne            8
        //    19: getstatic       cn/Hanabi/modules/Combat/KillAura.switchsize:Lcn/Hanabi/value/Value;
        //    22: invokevirtual   cn/Hanabi/value/Value.getValueState:()Ljava/lang/Object;
        //    25: checkcast       Ljava/lang/Double;
        //    28: invokevirtual   java/lang/Double.intValue:()I
        //    31: istore_2       
        //    32: iload           6
        //    34: ifne            8
        //    37: iload           6
        //    39: ifne            8
        //    42: iload_2        
        //    43: ldc             1
        //    45: if_icmple       87
        //    48: iload           6
        //    50: ifne            87
        //    53: iload           6
        //    55: ifne            8
        //    58: iload           6
        //    60: ifne            8
        //    63: aload_0        
        //    64: ldc_w           "Switch"
        //    67: invokevirtual   cn/Hanabi/modules/Combat/KillAura.setDisplayName:(Ljava/lang/String;)V
        //    70: iload           6
        //    72: ifne            8
        //    75: iload           6
        //    77: ifeq            104
        //    80: aconst_null    
        //    81: athrow         
        //    82: nop            
        //    83: nop            
        //    84: nop            
        //    85: nop            
        //    86: athrow         
        //    87: iload           6
        //    89: ifne            8
        //    92: aload_0        
        //    93: ldc_w           "Single"
        //    96: invokevirtual   cn/Hanabi/modules/Combat/KillAura.setDisplayName:(Ljava/lang/String;)V
        //    99: iload           6
        //   101: ifne            8
        //   104: iload           6
        //   106: ifne            8
        //   109: getstatic       cn/Hanabi/modules/Combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   112: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   115: getfield        net/minecraft/client/multiplayer/WorldClient.loadedEntityList:Ljava/util/List;
        //   118: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   123: astore_3       
        //   124: iload           6
        //   126: ifne            8
        //   129: iload           6
        //   131: ifne            8
        //   134: aload_3        
        //   135: invokeinterface java/util/Iterator.hasNext:()Z
        //   140: ifeq            316
        //   143: iload           6
        //   145: ifne            316
        //   148: iload           6
        //   150: ifne            8
        //   153: aload_3        
        //   154: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   159: checkcast       Lnet/minecraft/entity/Entity;
        //   162: astore          4
        //   164: iload           6
        //   166: ifne            8
        //   169: iload           6
        //   171: ifne            8
        //   174: aload           4
        //   176: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   179: ifeq            257
        //   182: iload           6
        //   184: ifne            257
        //   187: iload           6
        //   189: ifne            8
        //   192: aload_0        
        //   193: aload           4
        //   195: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   198: dup            
        //   199: astore          5
        //   201: invokespecial   cn/Hanabi/modules/Combat/KillAura.isValidEntity:(Lnet/minecraft/entity/Entity;)Z
        //   204: ifeq            257
        //   207: iload           6
        //   209: ifne            257
        //   212: iload           6
        //   214: ifne            8
        //   217: getstatic       cn/Hanabi/modules/Combat/KillAura.targets:Ljava/util/ArrayList;
        //   220: aload           5
        //   222: invokevirtual   java/util/ArrayList.contains:(Ljava/lang/Object;)Z
        //   225: ifne            257
        //   228: iload           6
        //   230: ifne            257
        //   233: iload           6
        //   235: ifne            8
        //   238: iload           6
        //   240: ifne            8
        //   243: getstatic       cn/Hanabi/modules/Combat/KillAura.targets:Ljava/util/ArrayList;
        //   246: aload           5
        //   248: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   251: pop            
        //   252: iload           6
        //   254: ifne            8
        //   257: iload           6
        //   259: ifne            8
        //   262: getstatic       cn/Hanabi/modules/Combat/KillAura.targets:Ljava/util/ArrayList;
        //   265: invokevirtual   java/util/ArrayList.size:()I
        //   268: iload_2        
        //   269: if_icmplt       299
        //   272: iload           6
        //   274: ifne            299
        //   277: iload           6
        //   279: ifne            8
        //   282: iload           6
        //   284: ifne            8
        //   287: iload           6
        //   289: ifeq            316
        //   292: aconst_null    
        //   293: athrow         
        //   294: nop            
        //   295: nop            
        //   296: nop            
        //   297: nop            
        //   298: athrow         
        //   299: iload           6
        //   301: ifne            8
        //   304: iload           6
        //   306: ifeq            129
        //   309: aconst_null    
        //   310: athrow         
        //   311: nop            
        //   312: nop            
        //   313: nop            
        //   314: nop            
        //   315: athrow         
        //   316: iload           6
        //   318: ifne            8
        //   321: aload_0        
        //   322: getfield        cn/Hanabi/modules/Combat/KillAura.priority:Lcn/Hanabi/value/Value;
        //   325: ldc_w           "Range"
        //   328: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //   331: ifeq            365
        //   334: iload           6
        //   336: ifne            365
        //   339: iload           6
        //   341: ifne            8
        //   344: iload           6
        //   346: ifne            8
        //   349: getstatic       cn/Hanabi/modules/Combat/KillAura.targets:Ljava/util/ArrayList;
        //   352: invokedynamic   BootstrapMethod #0, compare:()Ljava/util/Comparator;
        //   357: invokevirtual   java/util/ArrayList.sort:(Ljava/util/Comparator;)V
        //   360: iload           6
        //   362: ifne            8
        //   365: iload           6
        //   367: ifne            8
        //   370: aload_0        
        //   371: getfield        cn/Hanabi/modules/Combat/KillAura.priority:Lcn/Hanabi/value/Value;
        //   374: ldc_w           "Fov"
        //   377: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //   380: ifeq            419
        //   383: iload           6
        //   385: ifne            419
        //   388: iload           6
        //   390: ifne            8
        //   393: iload           6
        //   395: ifne            8
        //   398: getstatic       cn/Hanabi/modules/Combat/KillAura.targets:Ljava/util/ArrayList;
        //   401: invokedynamic   BootstrapMethod #1, applyAsDouble:()Ljava/util/function/ToDoubleFunction;
        //   406: invokeinterface java/util/Comparator.comparingDouble:(Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
        //   411: invokevirtual   java/util/ArrayList.sort:(Ljava/util/Comparator;)V
        //   414: iload           6
        //   416: ifne            8
        //   419: iload           6
        //   421: ifne            8
        //   424: aload_0        
        //   425: getfield        cn/Hanabi/modules/Combat/KillAura.priority:Lcn/Hanabi/value/Value;
        //   428: ldc_w           "Angle"
        //   431: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //   434: ifeq            468
        //   437: iload           6
        //   439: ifne            468
        //   442: iload           6
        //   444: ifne            8
        //   447: iload           6
        //   449: ifne            8
        //   452: getstatic       cn/Hanabi/modules/Combat/KillAura.targets:Ljava/util/ArrayList;
        //   455: invokedynamic   BootstrapMethod #2, compare:()Ljava/util/Comparator;
        //   460: invokevirtual   java/util/ArrayList.sort:(Ljava/util/Comparator;)V
        //   463: iload           6
        //   465: ifne            8
        //   468: iload           6
        //   470: ifne            8
        //   473: return         
        //   474: nop            
        //   475: nop            
        //   476: nop            
        //   477: nop            
        //   478: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @EventTarget
    private void onPacket(final EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof S08PacketPlayerPosLook) {
            final IS08PacketPlayerPosLook is08PacketPlayerPosLook = (IS08PacketPlayerPosLook)eventPacket.getPacket();
            is08PacketPlayerPosLook.setYaw(KillAura.mc.thePlayer.rotationYaw);
            is08PacketPlayerPosLook.setPitch(KillAura.mc.thePlayer.rotationPitch);
        }
        if (!(eventPacket.getPacket() instanceof C03PacketPlayer)) {}
    }
    
    private boolean isValidEntity(final Entity entity) {
        if (entity != null && entity instanceof EntityLivingBase) {
            if (entity.isDead || ((EntityLivingBase)entity).getHealth() <= 0.0f) {
                if (ModManager.getModule("AutoL").isEnabled() && KillAura.attacked.contains(entity)) {
                    KillAura.attacked.remove(entity);
                    final String sb = AutoL.getSB();
                    if (AutoL.wdr.getValueState() && !AutoL.wdred.contains(KillAura.target.getName())) {
                        AutoL.wdred.add(KillAura.target.getName());
                        KillAura.mc.thePlayer.sendChatMessage("/wdr " + KillAura.target.getName() + " ka fly reach nokb jesus ac");
                    }
                    KillAura.mc.thePlayer.sendChatMessage(AutoAbuse.prefix + entity.getName() + " L" + (AutoL.abuse.getValueState() ? (" " + sb) : "") + (AutoL.ad.getValueState() ? " Buy Hanabi at mcheika.com" : ""));
                }
                return false;
            }
            if (KillAura.mc.thePlayer.getDistanceToEntity(entity) < KillAura.reach.getValueState() + this.blockReach.getValueState() && entity != KillAura.mc.thePlayer && !KillAura.mc.thePlayer.isDead && !(entity instanceof EntityArmorStand) && !(entity instanceof EntitySnowman)) {
                if (entity instanceof EntityPlayer && this.attackPlayers.getValueState()) {
                    return entity.ticksExisted >= 30 && (KillAura.mc.thePlayer.canEntityBeSeen(entity) || this.throughblock.getValueState()) && (!entity.isInvisible() || this.invisible.getValueState()) && !AntiBot.isBot(entity) && !Teams.isOnSameTeam(entity);
                }
                if (entity instanceof EntityMob && this.attackMobs.getValueState()) {
                    return !AntiBot.isBot(entity);
                }
                if ((entity instanceof EntityAnimal || entity instanceof EntityVillager) && this.attackAnimals.getValueState()) {
                    return !AntiBot.isBot(entity);
                }
            }
        }
        return false;
    }
    
    public void onEnable() {
        if (ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";")[0].contains("\\lib\\")) {
            this.shouldAddYaw = 0.0f;
            KillAura.attacked = new ArrayList<EntityLivingBase>();
            this.axisAlignedBB = null;
            if (KillAura.mc.thePlayer != null) {
                this.lastRotation[0] = KillAura.mc.thePlayer.rotationYaw;
                this.lastAngles.x = KillAura.mc.thePlayer.rotationYaw;
                this.lastRotations = new float[] { KillAura.mc.thePlayer.rotationYaw, KillAura.mc.thePlayer.rotationPitch };
            }
            this.index = 0;
            super.onEnable();
            return;
        }
        JOptionPane.showMessageDialog(null, "你上你婊子妈的补丁啊", "操你妈啊5555555", 0);
        Runtime.getRuntime().exit(new Random().nextInt(123123));
    Label_0064_Outer:
        while (true) {
            while (true) {
                try {
                    while (true) {
                        JOptionPane.showMessageDialog(null, "�?都不让�?�子�?�?", "操你妈啊5555555", 0);
                        Thread.currentThread();
                        Thread.sleep(10000000L);
                        Thread.sleep(10000000L);
                    }
                }
                catch (InterruptedException ex) {
                    continue Label_0064_Outer;
                }
                continue;
            }
        }
    }
    
    public void onDisable() {
        this.axisAlignedBB = null;
        if (KillAura.mc.thePlayer != null) {
            this.lastRotation[0] = KillAura.mc.thePlayer.rotationYaw;
            this.lastAngles.x = KillAura.mc.thePlayer.rotationYaw;
        }
        KillAura.targets.clear();
        KillAura.target = null;
        ((IEntityPlayer)KillAura.mc.thePlayer).setItemInUseCount(0);
        KillAura.mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        super.onDisable();
    }
    
    private static int lambda$getTarget$2(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        return (int)(KillAura.mc.thePlayer.rotationYaw - Class45.getRotations((Entity)entityLivingBase)[0] - (KillAura.mc.thePlayer.rotationYaw - Class45.getRotations((Entity)entityLivingBase2)[0]));
    }
    
    private static double lambda$getTarget$1(final EntityLivingBase entityLivingBase) {
        return Class45.getDistanceBetweenAngles(KillAura.mc.thePlayer.rotationPitch, Class45.getRotations((Entity)entityLivingBase)[0]);
    }
    
    private static int lambda$getTarget$0(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        return (int)(entityLivingBase.getDistanceToEntity((Entity)KillAura.mc.thePlayer) - entityLivingBase2.getDistanceToEntity((Entity)KillAura.mc.thePlayer));
    }
    
    static {
        KillAura.autoBlock = new Value<Boolean>("KillAura_AutoBlock", true);
        KillAura.reach = new Value<Double>("KillAura_Range", 4.2, 3.0, 6.0, 0.1);
        KillAura.switchsize = new Value<Double>("KillAura_MaxTargets", 1.0, 1.0, 5.0, 1.0);
        KillAura.isBlocking = false;
        KillAura.targets = new ArrayList<EntityLivingBase>();
        KillAura.attacked = new ArrayList<EntityLivingBase>();
        KillAura.target = null;
        KillAura.needHitBot = null;
    }
}

package me.superskidder.lune.modules.combat;

import me.superskidder.lune.Lune;
import me.superskidder.lune.luneautoleak.checks.AntiPatch;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.movement.Scaffold;
import me.superskidder.lune.events.*;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.math.MathUtil;
import me.superskidder.lune.utils.player.MoveUtils;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class KillAura
        extends Mod {
    public static EntityLivingBase target;
    private List<Entity> targets = new ArrayList<>(0);
    private int index;

    private Num<Double> cps = new Num<>("Cps", 10.0, 1.0, 20.0);
    public static Num<Double> range = new Num<>("Reach", 4.2, 1.0, 6.0);
    public static Num<Double> SwitchDelay = new Num<>("SwitchDelay", 800.0, 1.0, 5000.0);
    public static Num<Double> turnSpeed = new Num<Double>("TurnSpeed(percent)", 40.0, 1.0, 100.0);
    public static Num<Double> switchEntitys = new Num<Double>("SwitchEntity", 2.0, 1.0, 5.0);
    public static Num<Double> rotyawdist = new Num<Double>("YawDist", 0.0, -20.0, 20.0);
    public static Num<Double> rotpitchdist = new Num<Double>("PitchDist", 0.0, -20.0, 20.0);

    private Bool<Boolean> autoblock = new Bool<>("AutoBlock", true);
    private static Bool<Boolean> players = new Bool<>("Players", true);
    private static Bool<Boolean> animals = new Bool<>("Animals", true);
    private static Bool<Boolean> mobs = new Bool<>("Mobs", false);
    private static Bool<Boolean> invisibles = new Bool<>("Invisibles", false);
    private static Bool<Boolean> villager = new Bool<>("Villager", false);
    public static Bool<Boolean> esp = new Bool<>("ESP", true);
    private Bool<Boolean> random = new Bool<>("Random", true);
    private Bool<Boolean> targetHud = new Bool<>("TargetHUD", true);
    private Bool<Boolean> hover = new Bool<>("Hover", true);


    private Mode<?> mode = new Mode<>("Mode", AuraMode.values(), AuraMode.Single);
    private Mode<?> rotMode = new Mode<>("RotMode", RotationMode.values(), RotationMode.Normal);
    private Mode<?> attackMode = new Mode<>("RotMode", AttackMode.values(), AttackMode.Packet);

    private Mode<?> switchMode = new Mode<>("SwitchMode", SwitchMode.values(), SwitchMode.Delay);

    private boolean isBlocking;
    private Comparator<Entity> angleComparator = Comparator.comparingDouble(e2 -> e2.getDistanceToEntity(mc.thePlayer));

    private TimerUtil attackTimer = new TimerUtil();

    private TimerUtil switchTimer = new TimerUtil();
    public static float[] lastRotations;
    public static float lastYaw;
    public static float lastPitch;
    public static boolean shouldSetRot;
    public static int kgw;
    public static Entity lastTarget = null;
    private int rotyaw;
    private int rotpitch;

    enum SwitchMode {
        Delay,
        HurtTime
    }

    enum RotationMode {
        Normal,
        AAC
    }

    enum AttackMode {
        Packet,
        Attack,
    }

    public KillAura() {
        super("KillAura", ModCategory.Combat, "Auto attack entities in range");
        this.addValues(this.cps, this.range, this.SwitchDelay, this.turnSpeed, this.switchEntitys, this.autoblock, this.players, this.mobs, this.villager, this.invisibles, this.animals, this.targetHud, this.mode, this.attackMode, this.rotMode, this.esp, this.random, this.switchMode,
                this.rotpitchdist, this.rotyawdist);
    }

    @Override
    public void onDisable() {
        this.target = null;
        this.targets.clear();
        if (this.autoblock.getValue() && this.hasSword() && mc.thePlayer.isBlocking()) {
            this.unBlock();
        }
    }

    @Override
    public void onEnabled() {
        this.target = null;
        this.index = 0;
        this.rotyaw = (int) mc.thePlayer.rotationYaw % 360;
    }

    @EventTarget
    public void onRender(EventRender3D e) {
        if (target == null)
            return;


    }

    @EventTarget
    public void onRender(EventRender2D e) {
        if (AntiPatch.patched || Lune.crack || Lune.username.equals("cracker!")) {
            RenderUtil.blurArea(0, 0, 1920, 1080, 200);
        }
        if (!this.targetHud.getValue())
            return;
        final ScaledResolution scaledResolution = new ScaledResolution(KillAura.mc);
        if (target != null) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            mc.fontRendererObj.drawStringWithShadow(KillAura.target.getName(), scaledResolution.getScaledWidth() / 2.0f - mc.fontRendererObj.getStringWidth(KillAura.target.getName()) / 2.0f, scaledResolution.getScaledHeight() / 2.0f - 33.0f, 16777215);
            RenderHelper.enableGUIStandardItemLighting();
            KillAura.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glDepthMask(false);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            for (int n = 0; n < KillAura.target.getMaxHealth() / 2.0f; ++n) {
                KillAura.mc.ingameGUI.drawTexturedModalRect(scaledResolution.getScaledWidth() / 2 - KillAura.target.getMaxHealth() / 2.0f * 10.0f / 2.0f + n * 10, (float) (scaledResolution.getScaledHeight() / 2 - 20), 16, 0, 9, 9);
            }
            for (int n2 = 0; n2 < KillAura.target.getHealth() / 2.0f; ++n2) {
                KillAura.mc.ingameGUI.drawTexturedModalRect(scaledResolution.getScaledWidth() / 2 - KillAura.target.getMaxHealth() / 2.0f * 10.0f / 2.0f + n2 * 10, (float) (scaledResolution.getScaledHeight() / 2 - 20), 52, 0, 9, 9);
            }
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            RenderHelper.disableStandardItemLighting();
        }


    }

    private boolean hasSword() {
        if (mc.thePlayer.inventory.getCurrentItem() != null) {
            if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void block() {
        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            if (mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem())) {
                mc.getItemRenderer().resetEquippedProgress2();
            }
        }
    }

    private void unBlock() {
        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            mc.playerController.onStoppedUsingItem(mc.thePlayer);
        }
    }

    private boolean shouldAttack() {
        return this.attackTimer.hasReached(1000.0 / (this.cps.getValue().doubleValue() + (random.getValue().booleanValue() ? MathUtil.randomDouble(0.0, 3.5) : 0)));
    }

    @EventTarget
    private void onUpdate(EventPreUpdate event) {
        this.setDisplayName(this.mode.getValue().toString());
        if (ModuleManager.getModByClass(Scaffold.class).getState()) {
            return;
        }

        if(!rotMode.isValid("AAC")){
            mc.thePlayer.setSprinting(false);
        }


        if (target == null && autoblock.getValue().booleanValue() && mc.thePlayer.isBlocking()) {
            if (hasSword()) {
                unBlock();
            }
        }
        if (hasSword() && this.target != null && autoblock.getValue().booleanValue() && !isBlocking) {
            this.block();
        }
        this.targets = getTargets(range.getValue().doubleValue() + 1);

        targets.sort(this.angleComparator);

        if (this.targets.size() > 1 && this.mode.getValue() == AuraMode.Switch
                && this.targets.size() < switchEntitys.getValue().intValue()) {
            if (switchMode.getValue() == SwitchMode.Delay && switchTimer.delay(SwitchDelay.getValue().longValue())) {
                ++this.index;
                switchTimer.reset();
            } else if (switchMode.getValue() == SwitchMode.HurtTime && target != null) {
                if (target.hurtTime != 0 && mc.thePlayer.ticksExisted % 60 == 0) {
                    ++this.index;
                }
            }
        }

        if (mc.thePlayer.ticksExisted % SwitchDelay.getValue().intValue() == 0 && this.targets.size() > 1 && this.mode.getValue() == AuraMode.Single) {

            if (target.getDistanceToEntity(mc.thePlayer) > range.getValue().doubleValue()) {
                ++index;
            } else if (target.isDead) {
                ++index;
            }
        }

        if (target != null) {
            target = null;
        }

        if (!this.targets.isEmpty()) {
            if (this.index >= this.targets.size()) {
                this.index = 0;
            }
            float[] arrf;
            lastTarget = target;
            target = (EntityLivingBase) this.targets.get(this.index);
            mc.thePlayer.rotationYaw = mc.thePlayer.rotationYaw % 360;
            lastYaw = pq(target)[0];
            lastPitch = pq(target)[1];

//            lastYaw = getRotation(target)[0];
//            lastPitch = getRotation(target)[1];
//            event.setYaw((float) (event.getYaw() + (event.getYaw() < lastYaw ? turnSpeed.getValue() / 100 : -turnSpeed.getValue() / 100)));
//            event.setPitch((float) (event.getPitch() + (event.getPitch() < lastPitch ? turnSpeed.getValue() / 100 : -turnSpeed.getValue() / 100)));

            if ((mc.thePlayer.motionX == 0 & mc.thePlayer.motionZ == 0 && rotMode.isValid(RotationMode.AAC.name())) || rotMode.isValid(RotationMode.Normal.name())) {


                rotyaw += rotyaw < lastYaw ? Math.abs(lastYaw - rotyaw) * (turnSpeed.getValue() / 100F) : -Math.abs(lastYaw - rotyaw) * (turnSpeed.getValue() / 100F) * (mc.thePlayer.ticksExisted % 2);


                rotpitch += rotpitch < lastPitch ? Math.abs(lastPitch - rotpitch) * (turnSpeed.getValue() / 100F) : -Math.abs(lastPitch - rotpitch) * (turnSpeed.getValue() / 100F);
                Random random = new Random();
                event.setYaw((float) (rotyaw + random.nextFloat() / 2));
//                mc.thePlayer.rotationYaw = (float) (rotyaw + random.nextFloat() / 2);
                event.setPitch(rotpitch);
//                mc.thePlayer.rotationPitch = rotpitch;


            }

        }
    }

    public float[] pq(EntityLivingBase EntityLivingBase) {
        if (EntityLivingBase != null) {
            double d = mc.thePlayer.posX;
            double d2 = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight();
            double d3 = mc.thePlayer.posZ;
            double d4 = EntityLivingBase.posX;
            double d5 = EntityLivingBase.posY + (double) (EntityLivingBase.height / 2.0f);
            double d6 = EntityLivingBase.posZ;
            double d7 = d - d4;
            double d8 = d2 - d5;
            double d9 = d3 - d6;
            double d10 = Math.sqrt(Math.pow(d7, 2.0) + Math.pow(d9, 2.0));
            float f = (float) ((float) (Math.toDegrees(Math.atan2(d9, d7)) + 90.0) + rotyawdist.getValue() * 2);
            float f2 = (float) ((float) Math.toDegrees(Math.atan2(d10, d8)) + rotpitchdist.getValue());
            return new float[]{(float) ((double) f + (new Random().nextBoolean() ? Math.random() : -Math.random())), (float) ((double) (90.0f - f2) + (new Random().nextBoolean() ? Math.random() : -Math.random()))};
        }
        return null;
    }

    public static float[] d(float[] arrf, float[] arrf2, float f) {
        double d = getAngleDifference(arrf2[0], arrf[0]);
        double d2 = getAngleDifference(arrf2[1], arrf[1]);
        arrf[0] = (float) ((double) arrf[0] + (d > (double) f ? (double) f : (d < (double) (-f) ? (double) (-f) : d)));
        arrf[1] = (float) ((double) arrf[1] + (d2 > (double) f ? (double) f : (d2 < (double) (-f) ? (double) (-f) : d2)));
        return arrf;
    }

    public static double getAngleDifference(double d, double d2) {
        return ((d - d2) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    @EventTarget
    public void onPostUpdate(EventPostUpdate e) {
        this.setDisplayName(this.mode.getModeAsString());
        if (ModuleManager.getModByClass(Scaffold.class).getState()) {
            return;
        }
        if (target != null) {
            if (this.shouldAttack()) {
                if (this.hasSword() && mc.thePlayer.isBlocking() && this.canAttack(this.target)) {
                    unBlock();
                }

                mc.thePlayer.swingItem();
                if (attackMode.isValid(AttackMode.Packet.name())) {

                    mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

                } else if (attackMode.isValid(AttackMode.Attack.name())) {
                    mc.playerController.attackEntity(mc.thePlayer, target);
                }
                this.attackTimer.reset();
            }
            if (!mc.thePlayer.isBlocking() && this.hasSword() && autoblock.getValue().booleanValue()) {
                this.block();
            }
        }
    }

    public List<Entity> getTargets(Double value) {
        List<Entity> ents = new ArrayList<>();
        for (Entity ent : mc.theWorld.loadedEntityList) {
            if (mc.thePlayer.getDistanceToEntity(ent) <= value && canAttack(ent)) {
                if (ents.size() >= switchEntitys.getValue().intValue()) {
                    ents.remove(0);
                }
                ents.add(ent);
            }
        }
        return ents;
        // return mc.theWorld.loadedEntityList.stream().filter(e -> (double) mc.thePlayer.getDistanceToEntity((Entity) e) <= value && canAttack((Entity) e)).collect(Collectors.toList());
    }

    public boolean canAttack(Entity e) {
        if (e == mc.thePlayer) {
            return false;
        }

        if (!e.isEntityAlive()) {
            return false;
        }

        if (Teams.isOnSameTeam(e)) {
            return false;
        }

        if (e instanceof EntityPlayer && players.getValue()) {
            return true;
        }
        if (e instanceof EntityMob || e instanceof EntityBat || e instanceof EntityWaterMob && mobs.getValue()) {
            return true;
        }
        if (e instanceof EntityAnimal && animals.getValue()) {
            return true;
        }
        if (e.isInvisible() && invisibles.getValue() && e instanceof EntityPlayer) {
            return true;
        }
        if (e instanceof EntityVillager && villager.getValue()) {
            return true;
        }

        return false;
    }

    public static float[] getRotation(EntityLivingBase entity) {
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        double diffY = entity.posY + (double) entity.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double dist = MathHelper.sqrt_double((diffX * diffX + diffZ * diffZ));
        float yaw = (float) ((float) (Math.atan2(diffZ, diffX) * 180.0 / 3.1415926) + Lune.flag - 90.0f + rotyawdist.getValue());
        float pitch = (float) ((float) (-(Math.atan2(diffY, dist) * 180.0 / 3.1415926)) + Lune.flag + rotpitchdist.getValue());
        return new float[]{yaw, pitch};
    }

    @EventTarget
    private void onSendPacket(EventPacketSend event) {
        Packet packet = event.getPacket();
        C08PacketPlayerBlockPlacement blockPlacement;
        if (packet instanceof C07PacketPlayerDigging
                && ((C07PacketPlayerDigging) event.getPacket())
                .getStatus().equals((Object) C07PacketPlayerDigging.Action.RELEASE_USE_ITEM)) {
            this.isBlocking = false;
        }
        if (packet instanceof C08PacketPlayerBlockPlacement
                && (blockPlacement = (C08PacketPlayerBlockPlacement) event.getPacket()).getStack() != null
                && blockPlacement.getStack().getItem() instanceof ItemSword
                && blockPlacement.getPosition().equals(new BlockPos(-1, -1, -1))) {
            this.isBlocking = true;
        }
    }

    enum AuraMode {
        Switch,
        Single,
    }
}


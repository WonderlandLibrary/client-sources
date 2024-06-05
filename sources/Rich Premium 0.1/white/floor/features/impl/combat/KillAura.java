package white.floor.features.impl.combat;

import clickgui.ClickGuiScreen;
import clickgui.setting.Setting;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event2D;
import white.floor.event.event.EventPacket;
import white.floor.event.event.EventPreMotionUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.ModulesHelper;
import white.floor.helpers.combat.CountHelper;
import white.floor.helpers.combat.GCDFix;
import white.floor.helpers.combat.RotationHelper;
import white.floor.helpers.combat.RotationSpoofer;
import white.floor.helpers.friend.Friend;
import white.floor.helpers.friend.FriendManager;
import white.floor.helpers.movement.MovementHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;
import white.floor.helpers.render.AnimationHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class KillAura extends Feature {


    public static EntityLivingBase target;
    private double healthBarWidth;
    private double healthBarWidth1;
    private double hudHeight;
    int easingHealth = 0;
    float yaw;
    float pitch;

    private double hurttimeBarWidth;
    private double hurtHeight;

    private float targetHudScale;
    private float targetHudgl;
    private double armBarWidth;
    private double armorBarWidth;

    List<EntityLivingBase> targets;

    public KillAura() {
        super("KillAura", "rage vblobflgbd", 0, Category.COMBAT);
        targets = new ArrayList<EntityLivingBase>();
        ArrayList<String> rotation = new ArrayList<String>();
        rotation.add("Packet");
        rotation.add("Sunrise");
        Main.instance.settingsManager.rSetting(new Setting("kilovura", this));
        Main.instance.settingsManager.rSetting(new Setting("Rotation Mode", this, "Packet", rotation));
        Main.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
        Main.instance.settingsManager.rSetting(new Setting("Yaw Random", this, 4, 0, 5, false));
        Main.instance.settingsManager.rSetting(new Setting("Pre Aim Range", this, 1, 0, 5, false));
        Main.instance.settingsManager.rSetting(new Setting("Aim Predict", this, 2, 0, 5, false));
        Main.instance.settingsManager.rSetting(new Setting("Range", this, 3.4, 3, 7, false));
        Main.instance.settingsManager.rSetting(new Setting("Client Look", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Players", this, true));
        Main.instance.settingsManager.rSetting(new Setting("Mobs", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Invisible", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Walls", this, false));
        Main.instance.settingsManager.rSetting(new Setting("TargetHud", this, false));
        Main.instance.settingsManager.rSetting(new Setting("TargetHudX", this, 360.0, 0.0, 500.0, true));
        Main.instance.settingsManager.rSetting(new Setting("TargetHudY", this, 150.0, 0.0, 170.0, true));
        Main.instance.settingsManager.rSetting(new Setting("ShieldBreak", this, false));
        Main.settingsManager.rSetting(new Setting("ShieldBreakDelay", this, 65, 0, 200, true));
        Main.instance.settingsManager.rSetting(new Setting("ShieldBlock", this, false));
        Main.instance.settingsManager.rSetting(new Setting("OnlyCrits", this, false));

    }

    @EventTarget
    public void onSendPacket(EventPacket event) {
        if (isToggled()) {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                SPacketPlayerPosLook packet1 = (SPacketPlayerPosLook) event.getPacket();
                mc.player.rotationYaw = packet1.getYaw();
                mc.player.rotationPitch = packet1.getPitch();
            }
        }
    }

    @EventTarget
    public void onEventPreMotion(EventPreMotionUpdate mamanooma) {
        if (mc.player.isDead && isToggled()) {
            toggle();
            NotificationPublisher.queue(getName(), "Was disabled due to death.", NotificationType.INFO);
        }

        if (isToggled()) {
            this.setModuleName("KillAura " + ChatFormatting.GRAY + "[Rotation: " + Main.settingsManager.getSettingByName("Rotation Mode").getValString() + "]");

            target = this.getTarget(Main.instance.settingsManager.getSettingByName("Range").getValDouble() + Main.instance.settingsManager.getSettingByName("Pre Aim Range").getValDouble());

            if (target == null || mc.player.isUseMainHand()) return;

            float cdValue;
            if (mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() instanceof ItemAxe)
                cdValue = 1;
            else cdValue = 0.95f;

            if (mc.player.getCooledAttackStrength(0) >= cdValue && mc.player.isEntityAlive()) {
                mc.player.setSprinting(false);

                if (!RotationHelper.isLookingAtEntity(target, yaw)) return;

                if (Main.instance.settingsManager.getSettingByName("OnlyCrits").getValBoolean() && !mc.player.critcheck()) {
                    if (mc.player.fallDistance >= 0.15 || MovementHelper.isBlockAbove()) {
                        if (mc.player.getDistanceToEntity(target) <= Main.instance.settingsManager.getSettingByName("Range").getValDouble()) {
                            mc.playerController.attackEntity(mc.player, target);
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                } else {
                    mc.playerController.attackEntity(mc.player, target);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }


    public static float[] rotats(Entity entity) {
        double diffX = entity.posX - mc.player.posX;
        double diffZ = entity.posZ - mc.player.posZ;
        double diffY = entity.posY + entity.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight()) - 0.4;

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (((Math.atan2(diffZ, diffX) * 180 / Math.PI) - 90)) + CountHelper.nextFloat(-1, 1);
        float yawBody = (float) (((Math.atan2(diffZ, diffX) * 180 / Math.PI) - 90));
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180 / Math.PI)) + CountHelper.nextFloat(-1, 1);
        float pitch2 = (float) (-(Math.atan2(diffY, dist) * 180 / Math.PI));

        mc.player.setSprinting(false);

        yaw = mc.player.prevRotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
        yawBody = mc.player.prevRotationYaw + MathHelper.wrapDegrees(yawBody - mc.player.rotationYaw);
        pitch = mc.player.prevRotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90, 90);
        return new float[]{yaw, pitch, yawBody, pitch2};
    }

    @EventTarget
    public void onRotations(EventPreMotionUpdate event) {
        if (isToggled()) {
            if (target != null) {

                String mode = Main.instance.settingsManager.getSettingByName("Rotation Mode").getValString();
                float[] Rots = rotats(target);
                if (mode.equalsIgnoreCase("Packet")) {
                    Rots = rotats(target);

                }
                if (mode.equalsIgnoreCase("Sunrise")) {
                    Rots = RotationHelper.getRatationsG1(target);
                    pitch = Rots[3];
                }
                event.setYaw(Rots[0]);
                event.setPitch(Rots[1]);
                yaw = Rots[2];
                mc.player.renderYawOffset = Rots[2];
                mc.player.rotationYawHead = Rots[0];
                mc.player.rotationPitchHead = Rots[1];
                if (Main.instance.settingsManager.getSettingByName("Client Look").getValBoolean()) {
                    mc.player.rotationYaw = Rots[0];
                    mc.player.rotationPitch = Rots[1];
                }


                if (Main.instance.settingsManager.getSettingByName("ShieldBreak").getValBoolean()) {
                    if (target instanceof EntityPlayer tp) {
                        if (RotationHelper.isAimAtMe(tp, 80)) {
                            if (tp.isBlocking()) destroyShield();
                        }
                    }
                }
            }
            if (Main.instance.settingsManager.getSettingByName("ShieldBlock").getValBoolean() && (mc.player.getHeldItemOffhand().getItem() instanceof ItemShield)) {
                boolean shield;

                if (mc.player.getCooledAttackStrength(1) < 0.9)
                    shield = true;
                else
                    shield = false;

                mc.gameSettings.keyBindUseItem.pressed = shield;
            }
        }
    }

    private EntityLivingBase getTarget(double range) {
        targets.clear();
        double dist = range;
        EntityLivingBase target = null;
        for (Entity entitys : mc.world.loadedEntityList) {
            EntityLivingBase player;
            Entity entity = entitys;
            if (!(entity instanceof EntityLivingBase) || !canAttack(player = (EntityLivingBase) entity)) continue;
            if (!player.isEntityAlive()) continue;

            double curDist = mc.player.getDistanceToEntity(player);
            if (!(curDist <= dist)) continue;
            dist = curDist;
            target = player;
            targets.add(player);
        }
        return target;
    }

    @EventTarget
    public void e(Event2D e) {
        ModulesHelper.DrawtargetHud();
    }



    public void destroyShield() {
        for (int a = 1; a < 9; a++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(a);
            if (itemStack.getItem() instanceof ItemAxe) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(a));
            }
        }
        mc.playerController.attackEntity(mc.player, target);
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
    }


    public void drawHead(ResourceLocation skin, int width, int height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(width, height, 8.0f, 8.0f, 8, 8, 37, 37, 64.0f, 64.0f);
    }

    public static boolean canAttack(EntityLivingBase player) {
        String mode = Main.instance.settingsManager.getSettingByName("AntiBot Mode").getValString();
        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob
                || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer
                    && !Main.instance.settingsManager.getSettingByName("Players").getValBoolean()) {
                return false;
            }
            if (player instanceof EntityAnimal
                    && !Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Mobs").getValBoolean()) {
                return false;
            }
            if (player instanceof EntityMob
                    && !Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Mobs").getValBoolean()) {
                return false;
            }

            if (player instanceof EntityVillager
                    && !Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Mobs").getValBoolean()) {
                return false;
            }
        }
        if (player.isInvisible() && !Main.instance.settingsManager.getSettingByName("Invisible").getValBoolean()) {
            return false;
        }
        if (player instanceof EntityArmorStand) {
            return false;
        }

        for (Friend friend : FriendManager.friendManager.friendsList) {
            if (!player.getName().equals(friend.getName()))
                continue;
            return false;
        }

        if (Main.featureDirector.getModule(AntiBot.class).isToggled() && mode.equalsIgnoreCase("AfterHit")
                && !AntiBot.entete.contains(player)) {
            return false;
        }

        if (!RotationHelper.canSeeEntityAtFov(player,
                (float) Main.instance.settingsManager.getSettingByName("FOV").getValDouble())
                && !KillAura.canSeeEntityAtFov(player,
                (float) Main.instance.settingsManager.getSettingByName("FOV").getValDouble())) {
            return false;
        }
        if (!range(player, Main.instance.settingsManager.getSettingByName("Range").getValDouble() + Main.instance.settingsManager.getSettingByName("Pre Aim Range").getValDouble())) {
            return false;
        }
        if (!player.canEntityBeSeen(mc.player)) {
            return Main.instance.settingsManager.getSettingByName("Walls").getValBoolean();
        }
        return player != mc.player;
    }

    private static boolean range(EntityLivingBase entity, double range) {
        mc.player.getDistanceToEntity(entity);
        return (double) mc.player.getDistanceToEntity(entity) <= range;
    }

    public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
        double diffX = entityLiving.posX - mc.player.posX;
        double diffZ = entityLiving.posZ - mc.player.posZ;
        float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double d = newYaw;
        double difference = KillAura.angleDifference(d, mc.player.rotationYaw);
        return difference <= (double) scope;
    }

    public static double angleDifference(double a, double b) {
        float yaw360 = (float) (Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        mc.gameSettings.keyBindUseItem.pressed = false;
        target = null;
        this.targetHudScale = 0;
        super.onDisable();
    }
}

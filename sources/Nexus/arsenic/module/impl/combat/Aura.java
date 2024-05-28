package arsenic.module.impl.combat;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventMove;
import arsenic.event.impl.EventRenderWorldLast;
import arsenic.event.impl.EventRunTick;
import arsenic.event.impl.EventSilentRotation;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.EnumProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.module.property.impl.rangeproperty.RangeProperty;
import arsenic.module.property.impl.rangeproperty.RangeValue;
import arsenic.utils.java.ColorUtils;
import arsenic.utils.minecraft.MathUtils;
import arsenic.utils.minecraft.MoveUtil;
import arsenic.utils.minecraft.PlayerUtils;
import arsenic.utils.render.RenderUtils;
import arsenic.utils.rotations.RotationUtils;
import arsenic.utils.timer.MillisTimer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@ModuleInfo(name = "Aura", category = ModuleCategory.Combat)
public class Aura extends Module {
    public final EnumProperty<rotMode> mode = new EnumProperty<>("Mode: ", rotMode.Normal);
    public final EnumProperty<abMode> blockMode = new EnumProperty<>("BlockMode: ", abMode.Vanilla);
    public final EnumProperty<strafeMode> fixMoveMode = new EnumProperty<>("MoveFix: ", strafeMode.None);
    public final DoubleProperty range = new DoubleProperty("Attack Range", new DoubleValue(0, 10, 3, 0.1));
    public final DoubleProperty rangeblock = new DoubleProperty("Block Range", new DoubleValue(0, 10, 3, 0.1));
    public final DoubleProperty rangerot = new DoubleProperty("Rotation Range", new DoubleValue(0, 10, 3, 0.1));
    public final DoubleProperty aps = new DoubleProperty("APS", new DoubleValue(1, 20, 10, 1));
    public final DoubleProperty rpsmax = new DoubleProperty("Max Speed", new DoubleValue(1, 180, 70, 1));
    public final DoubleProperty rpsmin = new DoubleProperty("Min Speed", new DoubleValue(1, 180, 70, 1));
    public final BooleanProperty smarthitselect = new BooleanProperty("Smart HitSelect", false);
    public final BooleanProperty polarfun = new BooleanProperty("Polar Rotation", true);
    public final BooleanProperty randomize = new BooleanProperty("Randomize", true);
    public final BooleanProperty raycast = new BooleanProperty("Raycast", true);
    public final BooleanProperty noGui = new BooleanProperty("Don't hit in gui's", true);
    public final BooleanProperty autoWeapon = new BooleanProperty("Auto Weapon", true);
    public final BooleanProperty targetesp = new BooleanProperty("Target ESP", true);
    @PropertyInfo(reliesOn = "Target ESP", value = "true")
    public final EnumProperty<renderMode> rendermode = new EnumProperty<>("Mode: ", renderMode.Ring);
    private boolean blocking;
    float[] rotations;
    float[] gcd;
    int ogslot;
    MillisTimer attackTimer = new MillisTimer();

    @Override
    protected void onEnable() {
        attackTimer.reset();
        ogslot = mc.thePlayer.inventory.currentItem;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        attackTimer.reset();
        mc.thePlayer.inventory.currentItem = ogslot;
        this.unblock();
    }

    @EventLink
    public final Listener<EventSilentRotation> eventSilentRotationListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        float[] prevRots = new float[]{event.getYaw(), event.getPitch()};
        EntityLivingBase targetrot = PlayerUtils.getClosestPlayerWithin(rangerot.getValue().getInput());
        if (targetrot == null) {
            event.setSpeed(180F);
            return;
        }
        switch (fixMoveMode.getValue()) {
            case Strict: {
                event.setJumpFix(true);
                event.setDoMovementFix(true);
            }
            break;
            case None: {
                event.setJumpFix(false);
                event.setDoMovementFix(false);
            }
        }

        // rotations
        if (mode.getValue() == rotMode.Normal) {
            rotations = RotationUtils.getRotations(mc.thePlayer.getPositionVector(), targetrot.getPositionVector());
        }
        if (mode.getValue() == rotMode.Vestige) {
            rotations = RotationUtils.getRotationsToEntity(targetrot, false);
        }

        if (randomize.getValue()) {
            try {
                rotations[0] += SecureRandom.getInstanceStrong().nextDouble() * 0.1;
                rotations[1] += SecureRandom.getInstanceStrong().nextDouble() * 0.1;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        // fixing rots
        gcd = getGCDRotations(rotations, prevRots);
        if (polarfun.getValue()) {
            // make rots more legit
            Random random = new Random();
            int randomNumber = random.nextInt(5) + 1;
            if (randomNumber == 1) {
                float speed = 10F;
                event.setSpeed(speed);
            }
            if (randomNumber == 5) {
                float speed = 25F;
                event.setSpeed(speed);
            }
            if (randomNumber == 2) {
                float speed = 15F;
                event.setSpeed(speed);
            }
            if (randomNumber != 1 && randomNumber != 5 && randomNumber != 2) {
                float speed = MathUtils.getRandomInRange(MathUtils.getRandomInRange(60F, 80F), MathUtils.getRandomInRange(100F, 150F));
                event.setSpeed(speed);
            }
            // polar fun ends here
        } else {
            float speed = (float) MathUtils.getRandomInRange(rpsmax.getValue().getInput(), rpsmin.getValue().getInput());
            event.setSpeed(speed);
        }
        event.setYaw(gcd[0]);
        event.setPitch(gcd[1]);
    };

    @EventLink
    public final Listener<EventRunTick> eventRunTickListener = event -> {
        EntityPlayer target = PlayerUtils.getClosestPlayerWithin(range.getValue().getInput());
        Entity targetrot = PlayerUtils.getClosestPlayerWithin(rangerot.getValue().getInput());
        Entity blocktarget = PlayerUtils.getClosestPlayerWithin(rangeblock.getValue().getInput());
        if (mc.thePlayer != null && mc.theWorld != null) {
            if (blocktarget != null && !blockMode.getValue().equals(abMode.None)) {
                if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                    switch (blockMode.getValue()) {
                        case Vanilla: {
                            this.block();
                        }
                        break;
                        case Hypixel: {
                            if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                            }
                            if (!mc.gameSettings.keyBindAttack.isKeyDown()) {
                                KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
                            }
                        }
                        break;
                        case Damage:
                            if (mc.thePlayer.hurtTime == MathUtils.getRandomInRange(7, 9)) {
                                this.block();
                            } else {
                                this.unblock();
                            }
                            break;
                    }
                }
            }
        }
        if (noGui.getValue() && mc.currentScreen != null) {
            return;
        }
        if (autoWeapon.getValue() && targetrot != null) {
            for (int slot = 0; slot <= 8; slot++) {
                ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
                if (itemInSlot != null && itemInSlot.getItem() instanceof ItemSword) {
                    if (mc.thePlayer.inventory.currentItem != slot) {
                        mc.thePlayer.inventory.currentItem = slot;
                    }
                }
            }
        }
        if (blocktarget == null) {
            this.unblock();
        }
        if (mc.thePlayer != null && mc.theWorld != null) {
            long delay = (long) MathUtils.getRandomInRange(aps.getValue().getInput() - 3, aps.getValue().getInput() + 3);
            if (attackTimer.hasElapsed((1000 / delay) +
                    (!smarthitselect.getValue() || mc.thePlayer.hurtTime != 0 || (target instanceof EntityLivingBase && target.hurtTime <= 3) ? 0 : MathUtils.getRandomInRange(490, 525)))) {
                if (targetrot != null) {
                    mc.thePlayer.swingItem();

                    if (target != null) {
                        final MovingObjectPosition movingObjectPosition = mc.objectMouseOver;
                        if (raycast.getValue()) {
                            if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                                // random chance to miss
                                Random random = new Random();
                                int randomNumber = random.nextInt(5) + 1;
                                if (polarfun.getValue()) {
                                    if (randomNumber == 1) {

                                    } else {
                                        mc.playerController.attackEntity(mc.thePlayer, movingObjectPosition.entityHit);
                                    }
                                } else {
                                    mc.playerController.attackEntity(mc.thePlayer, movingObjectPosition.entityHit);
                                }
                            }
                        } else {
                            mc.playerController.attackEntity(mc.thePlayer, target);
                        }
                    }
                }
                attackTimer.reset();
            }
        }
    };
    @EventLink
    public final Listener<EventRenderWorldLast> renderWorldLast = e -> {
        Entity targetrot = PlayerUtils.getClosestPlayerWithin(rangerot.getValue().getInput());
        if (targetrot == null) {
            return;
        }
        if (targetesp.getValue()) {
            if (rendermode.getValue() == renderMode.Arrow) {
                RenderUtils.drawBoxAroundEntity(targetrot, 5, 0, 0, ColorUtils.getThemeRainbowColor(4, 0));
            }
            if (rendermode.getValue() == renderMode.Ring) {
                RenderUtils.drawCircle(targetrot, e.partialTicks, 0.55, ColorUtils.getThemeRainbowColor(4, 0), 255);
            }
            if (rendermode.getValue() == renderMode.Tracer) {
                GL11.glPushMatrix();
                final double x = targetrot.lastTickPosX + (targetrot.posX - targetrot.lastTickPosX) * e.partialTicks - mc.getRenderManager().viewerPosX;
                final double y = (targetrot.lastTickPosY + (targetrot.posY - targetrot.lastTickPosY) * e.partialTicks - mc.getRenderManager().viewerPosY) + targetrot.height * 1.1;
                final double z = targetrot.lastTickPosZ + (targetrot.posZ - targetrot.lastTickPosZ) * e.partialTicks - mc.getRenderManager().viewerPosZ;
                final boolean bobbing = mc.gameSettings.viewBobbing;
                mc.gameSettings.viewBobbing = false;
                GlStateManager.disableTexture2D();
                Color color = new Color(80, 255, 0);
                GL11.glColor3d(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
                GL11.glLineWidth(2);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, mc.thePlayer.getEyeHeight(), 0.0);
                GL11.glVertex3d(x, y, z);
                GL11.glEnd();
                GlStateManager.enableTexture2D();
                mc.gameSettings.viewBobbing = bobbing;
                GlStateManager.popMatrix();
            }
        }
    };

    private void block() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        this.blocking = true;
    }

    private void unblock() {
        if (this.blocking) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            this.blocking = false;
        }
    }

    private double Sens() {
        final float sens = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        final float pow = sens * sens * sens * 8.0F;
        return pow * 0.15D;
    }

    private float[] getGCDRotations(final float[] currentRots, final float[] prevRots) {
        final float yawDif = currentRots[0] - prevRots[0];
        final float pitchDif = currentRots[1] - prevRots[1];
        final double gcd = Sens();

        currentRots[0] -= yawDif % gcd;
        currentRots[1] -= pitchDif % gcd;
        return currentRots;
    }

    public enum rotMode {
        Normal,
        Vestige,
        None
    }

    public enum strafeMode {
        Strict,
        None
    }

    public enum abMode {
        Vanilla,
        Damage,
        Hypixel,
        None
    }

    public enum renderMode {
        Ring,
        Tracer,
        Arrow
    }
}
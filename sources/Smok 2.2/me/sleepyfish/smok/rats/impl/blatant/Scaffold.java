package me.sleepyfish.smok.rats.impl.blatant;

import maxstats.weave.event.EventTick;
import maxstats.weave.event.EventRenderTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.GlUtils;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.rats.impl.useless.Spin;
import me.sleepyfish.smok.utils.render.RenderUtils;
import me.sleepyfish.smok.rats.settings.ModeSetting;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.rats.settings.SpaceSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.utils.render.animation.simple.AnimationUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import me.sleepyfish.smok.utils.render.animation.normal.Animation;
import me.sleepyfish.smok.utils.render.animation.normal.Direction;
import net.minecraft.util.Vec3;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.client.gui.ScaledResolution;

// Class from SMok Client by SleepyFish
public class Scaffold extends Rat {

    DoubleSetting blockRange;
    DoubleSetting cpsMin;
    DoubleSetting cpsMax;

    BoolSetting legitTower;
    BoolSetting rayTrace;
    BoolSetting placeChecks;

    ModeSetting<Enum<?>> rotationMode;
    DoubleSetting speedModifier;

    private int lastItem;

    private Timer timer = new Timer();
    private Timer smoothTimer = new Timer();

    private boolean loaded;
    private Animation introAnimation;

    private float hudX;
    private float hudY;

    public Scaffold() {
        super("Scaffold", Rat.Category.Blatant, "Walk with a blunt");
    }

    @Override
    public void setup() {
        this.addSetting(this.blockRange = new DoubleSetting("Y Block Range", 2.6, 1.0, 6.0, 0.2));
        this.addSetting(this.cpsMin = new DoubleSetting("CPS Min", 7.0, 1.0, 20.0, 1.0));
        this.addSetting(this.cpsMax = new DoubleSetting("CPS Max", 11.0, 2.0, 25.0, 1.0));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.legitTower = new BoolSetting("Legit Tower", false));
        this.addSetting(this.rayTrace = new BoolSetting("Raytrace", true));
        this.addSetting(this.placeChecks = new BoolSetting("Place Checks", "Like place on water", true));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.rotationMode = new ModeSetting<>("Rotation Mode", rotationModes.Smooth));
        this.addSetting(this.speedModifier = new DoubleSetting("Smooth speed", "To low value makes you attack before looking at a target", 6.4, 5.0, 10.0, 0.2));
    }

    @Override
    public void onEnableEvent() {
        Smok.inst.rotManager.stopRotate();

        // hud positions
        ScaledResolution sr = new ScaledResolution(mc);
        this.hudX = sr.getScaledWidth() / 2.0F + 40F;
        this.hudY = sr.getScaledHeight() / 2.0F + 20F;

        if (mc.theWorld != null) {
            if (Smok.inst.ratManager.getRatByClass(Aura.class).isEnabled()) {
                Smok.inst.ratManager.getRatByClass(Aura.class).toggle();
            }

            if (mc.getSession().getUsername().startsWith("smellon")) {
                if (Smok.inst.ratManager.getRatByClass(Spin.class).isEnabled()) {
                    Smok.inst.ratManager.getRatByClass(Spin.class).toggle();
                }
            }
        }

        if (this.loaded) {
            this.introAnimation.setDirection(Direction.BACKWARDS);

            if (this.introAnimation.isDone(Direction.BACKWARDS)) {
                this.loaded = false;
            }
        }

        this.lastItem = mc.thePlayer.inventory.currentItem;
        Utils.changeToBlock(false);
    }

    @Override
    public void onDisableEvent() {
        Smok.inst.rotManager.stopRotate();

        mc.thePlayer.inventory.currentItem = this.lastItem;

        if (this.loaded) {
            this.introAnimation.setDirection(Direction.BACKWARDS);

            if (this.introAnimation.isDone(Direction.BACKWARDS)) {
                this.loaded = false;
            }
        }
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (mc.currentScreen == null || ClientUtils.inClickGui()) {
            Utils.BlockData data = null;
            double posY;
            double yDif;

            for (posY = mc.thePlayer.posY - 1.0D; posY > 0.0D; posY--) {
                Utils.BlockData newData = Utils.Combat.getBlockData(new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ));

                if (newData != null) {
                    if (this.placeChecks.isEnabled()) {
                        Block block = Utils.getBlock(newData.pos.getX(), newData.pos.getY(), newData.pos.getZ());

                        if (mc.theWorld.canBlockBePlaced(block, newData.pos, false, newData.face, mc.thePlayer, Utils.getCurrentItem())) {
                            continue;
                        }

                        if (block != Blocks.air && (block == Blocks.lava || block == Blocks.water || block == Blocks.flowing_lava || block == Blocks.flowing_water)) {
                            continue;
                        }
                    }

                    yDif = mc.thePlayer.posY - posY;

                    if (yDif <= this.blockRange.getValue()) {
                        data = newData;
                        break;
                    }
                }
            }

            if (data == null) {
                Smok.inst.rotManager.stopRotate();
                return;
            }

            this.rotateBalls(data);

            if (Utils.holdingBlock()) {
                if (!this.loaded) {
                    this.introAnimation = AnimationUtils.getAnimation();
                    this.loaded = true;
                }

                if (timer.cpsTimer(this.cpsMin.getValueToInt(), this.cpsMax.getValueToInt())) {

                    if (this.legitTower.isEnabled()) {
                        if (!Utils.overAir(1))
                            return;
                    } else {
                        if (!mc.thePlayer.onGround)
                            return;
                    }

                    if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, Utils.getCurrentItem(), data.pos, data.face, new Vec3(data.pos.getX(), data.pos.getY(), data.pos.getZ()))) {
                        mc.thePlayer.swingItem();
                        timer.reset();
                    }
                }
            } else {
                if (!Utils.holdingBlock()) {
                    if (Utils.isMoving()) {
                        if (Utils.changeToBlock(false) == null) {
                            Smok.inst.rotManager.stopRotate();

                            if (this.loaded) {
                                this.introAnimation.setDirection(Direction.BACKWARDS);

                                if (this.introAnimation.isDone(Direction.BACKWARDS)) {
                                    this.loaded = false;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void rotateBalls(Utils.BlockData data) {
        if (mc.currentScreen == null || ClientUtils.inClickGui()) {
            float yaw = mc.thePlayer.rotationYaw;
            float pitch = mc.thePlayer.rotationPitch;

            if (data.pos != null) {
                float[] rotations = Utils.Combat.getBlockRotations(data.pos, data.face);

                if (rotationMode.getMode() == rotationModes.Derp) {
                    yaw = rotations[0];
                    pitch = rotations[1];
                }

                if (rotationMode.getMode() == rotationModes.Smooth) {
                    float serverYaw = Smok.inst.rotManager.yaw;
                    float serverPitch = Smok.inst.rotManager.pitch;

                    yaw = rotations[0];
                    pitch = rotations[1];

                    if (this.smoothTimer.delay(this.speedModifier.getValueToLong() * 5L)) {
                        if (yaw > serverYaw) {
                            ++yaw;
                            this.smoothTimer.reset();
                        }

                        if (yaw < serverYaw) {
                            --yaw;
                            this.smoothTimer.reset();
                        }

                        if (pitch > serverPitch) {
                            ++pitch;
                            this.smoothTimer.reset();
                        }

                        if (pitch < serverPitch) {
                            --pitch;
                            this.smoothTimer.reset();
                        }
                    }
                }

                if (!this.legitTower.isEnabled()) {
                    if (!mc.thePlayer.onGround) {
                        Smok.inst.rotManager.stopRotate();
                        return;
                    }
                }

                if (this.rayTrace.isEnabled()) {
                    Smok.inst.rotManager.rayTracePos = data.pos;
                }

                if (pitch > 90.0F) {
                    ClientUtils.addDebug("scaff: p:" + pitch);
                }

                Smok.inst.rotManager.setRotations(yaw, pitch);
                Smok.inst.rotManager.startRotate();
            }
        }
    }

    @SmokEvent
    public void onRender(EventRenderTick e) {
        if (mc.currentScreen == null || ClientUtils.inClickGui() && Utils.holdingBlock()) {
            GlUtils.startScale(hudX, hudY, 40, 20, (float) introAnimation.getValue());

            if (Gui.renderShadows.isEnabled()) {
                RenderUtils.drawShadow(hudX, hudY, 40, 20, 5);
            }

            RoundedUtils.drawRound(hudX, hudY, 40, 20, 5, ColorUtils.getBackgroundColor(4));

            if (this.loaded) {
                if (Utils.changeToBlockBool(false)) {
                    RenderUtils.ItemRenderer.render(Utils.getCurrentItem(), (int) (hudX + 2), (int) (hudY + 2));
                    FontUtils.rBold22.drawStringWithShadow("" + Utils.getCurrentItem().stackSize, hudX + 22, hudY + 5, ColorUtils.getClientColor(1));
                    ColorUtils.clearColor();
                }
            }

            GlUtils.stopScale();
        }
    }

    public enum rotationModes {
        Derp, Smooth;
    }

}
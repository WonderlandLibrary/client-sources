package club.mega.module.impl.combat;

import club.mega.Mega;
import club.mega.event.impl.*;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.BooleanSetting;
import club.mega.module.setting.impl.ListSetting;
import club.mega.module.setting.impl.NumberSetting;
import club.mega.util.AuraUtil;
import club.mega.util.RayCastUtil;
import club.mega.util.RotationUtil;
import net.minecraft.item.ItemSword;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "KillAura", description = "KillAura", category = Category.COMBAT)
public class KillAura extends Module {

    public final ListSetting mode = new ListSetting("Mode", this, new String[]{"Single", "Switch"});
    public final NumberSetting switchDelay = new NumberSetting("Switch delay", this, 200, 1000, 300, 100, () -> mode.is("switch"));
    public final ListSetting priority = new ListSetting("Priority", this, new String[]{"Health", "Distance"});
    public final ListSetting attackMode = new ListSetting("Attack mode", this, new String[]{"Normal", "Click"});
    public final ListSetting attackOn = new ListSetting("Attack on", this, new String[]{"Tick", "Pre", "Post", "Frame"});
    public final NumberSetting preRange = new NumberSetting("Pre Range", this, 0, 3, 1, 0.1);
    public final BooleanSetting randomizePreRange = new BooleanSetting("Randomize pre Range", this, true, () -> preRange.getAsDouble() != 0);
    public final NumberSetting range = new NumberSetting("Range", this, 3, 7, 4, 0.1);
    public final BooleanSetting randomizeRange = new BooleanSetting("Randomize range", this, false);
    public final NumberSetting maxAPS = new NumberSetting("Max APS", this, 5, 40, 14, 1);
    public final NumberSetting minAPS = new NumberSetting("Min APS", this, 5, 40, 12, 1);
    public final BooleanSetting smoothRotations = new BooleanSetting("Smooth rotations", this, false);
    public final BooleanSetting calculateSmoothRotations = new BooleanSetting("Calculate Speed", this, true, smoothRotations::get);
    public final NumberSetting smoothSpeed = new NumberSetting("Speed", this, 1, 180, 90, 5, () -> smoothRotations.get() && !calculateSmoothRotations.get());
    public final BooleanSetting autoBlock = new BooleanSetting("Auto. block", this, false);
    public final NumberSetting blockRange = new NumberSetting("Block range", this, 2, 10, 4, 0.1, autoBlock::get);
    public final NumberSetting blockChance = new NumberSetting("Block chance", this, 5, 100, 20, 5, autoBlock::get);
    public final NumberSetting minUnBlockTicks = new NumberSetting("Min unblock ticks", this, 1, 20, 8, 1, autoBlock::get);
    public final NumberSetting maxUnBlockTicks = new NumberSetting("Max unblock ticks", this, 1, 20, 13, 1, autoBlock::get);
    public final BooleanSetting fakeBlock = new BooleanSetting("Fake block", this, true);
    public final BooleanSetting ignoreWalls = new BooleanSetting("Ignore walls", this, true);
    public final BooleanSetting legitRotations = new BooleanSetting("Legit rotations", this, false);
    public final BooleanSetting preSwing = new BooleanSetting("Pre swing", this, false);
    public final BooleanSetting random = new BooleanSetting("Randomize rotations", this, true);
    public final BooleanSetting moveFix = new BooleanSetting("MoveFix", this, false);
    public final BooleanSetting velocityFix = new BooleanSetting("VelocityFix", this, false);
    public final BooleanSetting keepSprint = new BooleanSetting("Keep sprint", this, true);
    public final BooleanSetting shopAttack = new BooleanSetting("Shop", this, false, false);
    public final BooleanSetting deathDisable = new BooleanSetting("Disable on death", this, true, false);
    public final BooleanSetting attackDeath = new BooleanSetting("Attack death", this, false);
    public final BooleanSetting player = new BooleanSetting("Players", this, true, false);
    public final BooleanSetting mobs = new BooleanSetting("Mobs", this, true, false);
    public final BooleanSetting villagers = new BooleanSetting("Villagers", this, false, false);

    @Handler
    public final void tick(final EventTick event) {
        AuraUtil.setTargets();

        if ((!MC.thePlayer.isEntityAlive() || MC.theWorld == null && MC.thePlayer != null) && deathDisable.get())
            toggle();

        if (AuraUtil.getTarget() == null) {
            AuraUtil.setPreRange(preRange.getAsFloat());
            AuraUtil.setRange(range.getAsFloat());
            AuraUtil.setRotations(new float[]{MC.thePlayer.rotationYawHead, MC.thePlayer.rotationPitch});
            AuraUtil.setRotationSpeed(calculateSmoothRotations.get() ? 90 : smoothSpeed.getAsFloat());
            AuraUtil.resetCps();
            AuraUtil.forceUnblock();
            return;
        }

        AuraUtil.setPrevRotations(AuraUtil.getRotations());
        if (!legitRotations.get() || (RayCastUtil.getMouseOver(AuraUtil.getRange() + AuraUtil.getPreRange(), AuraUtil.getRotations()[0], AuraUtil.getRotations()[1]) != AuraUtil.getTarget()))
        AuraUtil.setRotations(RotationUtil.getKillAuraRotations(AuraUtil.getTarget(), legitRotations.get()));

        AuraUtil.addBlockTick();
        if (attackOn.is("tick"))
            AuraUtil.attack();
    }

    @Handler
    public final void preTick(final EventPreTick event) {
        if (AuraUtil.getTarget() == null)
            return;

        MC.thePlayer.rotationYawHead = AuraUtil.getRotations()[0];
        MC.thePlayer.renderYawOffset = AuraUtil.getRotations()[0];
        event.setRotations(AuraUtil.getRotations());

        if (attackOn.is("pre"))
            AuraUtil.attack();
    }

    @Handler
    public final void postTick(final EventPostTick event) {
        if (AuraUtil.getTarget() == null)
            return;

        if (attackOn.is("post"))
            AuraUtil.attack();
    }

    @Handler
    public final void render2D(final EventRender2D event) {
        if (AuraUtil.getTarget() == null)
            return;

        if (attackOn.is("frame"))
            AuraUtil.attack();
    }

    @Handler
    public final void look(final EventLook event) {
        if (AuraUtil.getTarget() != null) event.setRotations(AuraUtil.getRotations());
    }

    @Handler
    public final void moveFlying(final EventMoveFlying event) {
        if (AuraUtil.getTarget() == null || !moveFix.get())
            return;

        event.setYaw(AuraUtil.getRotations()[0]);
    }

    @Handler
    public final void velocity(final EventVelocity event) {
        if (AuraUtil.getTarget() == null || !velocityFix.get())
            return;

        event.setYaw(AuraUtil.getRotations()[0]);
    }

    @Handler
    public final void renderPitch(final EventRenderPitch event) {
        if (AuraUtil.getTarget() != null) event.setPitch(AuraUtil.getRotations()[1]);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        AuraUtil.setRotationSpeed(calculateSmoothRotations.get() ? 70 : smoothSpeed.getAsFloat());
        AuraUtil.setPreRange(preRange.getAsFloat());
        AuraUtil.setRange(range.getAsFloat());
        AuraUtil.resetCps();
        AuraUtil.setRange(range.getAsFloat());
        if (MC.thePlayer != null) AuraUtil.setRotations(new float[]{MC.thePlayer.rotationYaw, MC.thePlayer.rotationPitch});
    }

    @Override
    public void onDisable() {
        super.onDisable();
        AuraUtil.forceUnblock();
    }

    public final float[] getRots() {
        return (KillAura.getInstance().isToggled() && AuraUtil.getTarget() != null) ? AuraUtil.getRotations() : new float[] {MC.thePlayer.rotationYaw, MC.thePlayer.rotationPitch};
    }

    public static KillAura getInstance() {
        return Mega.INSTANCE.getModuleManager().getModule(KillAura.class);
    }
}
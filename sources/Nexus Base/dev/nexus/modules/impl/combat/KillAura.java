package dev.nexus.modules.impl.combat;

import dev.nexus.Nexus;
import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventMotionPre;
import dev.nexus.events.impl.EventSilentRotation;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.impl.player.Blink;
import dev.nexus.modules.setting.BooleanSetting;
import dev.nexus.modules.setting.ModeSetting;
import dev.nexus.modules.setting.NumberSetting;
import dev.nexus.utils.client.MSTimer;
import dev.nexus.utils.client.MathUtils;
import dev.nexus.utils.game.CombatUtils;
import dev.nexus.utils.game.PacketUtils;
import dev.nexus.utils.rotation.RotationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KillAura extends Module {
    public final ModeSetting targetMode = new ModeSetting("Target Mode", "Single", "Single", "Switch");
    public final NumberSetting switchDelay = new NumberSetting("Switch Delay", 10, 1000, 150, 10);

    public final BooleanSetting attackTeamMates = new BooleanSetting("Attack TeamMates", false);

    public final NumberSetting maxAPS = new NumberSetting("Max APS", 0, 20, 12, 1);
    public final NumberSetting minAPS = new NumberSetting("Min APS", 0, 20, 10, 2);

    public final NumberSetting findRange = new NumberSetting("Find Range", 0, 6, 4.5, 0.1);
    public final NumberSetting attackRange = new NumberSetting("Attack Range", 0, 6, 3, 0.1);
    public final NumberSetting blockRange = new NumberSetting("Block Range", 0, 6, 4.5, 0.1);

    public final ModeSetting autoBlockMode = new ModeSetting("Autoblock", "Vanilla", "Vanilla", "Watchdog", "Fake");

    public final NumberSetting minYawRandom = new NumberSetting("Min Yaw Random", 0, 3, 1, 0.1);
    public final NumberSetting maxYawRandom = new NumberSetting("Max Yaw Random", 0, 3, 1, 0.1);
    public final NumberSetting minPitchRandom = new NumberSetting("Min Pitch Random", 0, 3, 1, 0.1);
    public final NumberSetting maxPitchRandom = new NumberSetting("Max Pitch Random", 0, 3, 1, 0.1);
    public final NumberSetting minTurnSpeed = new NumberSetting("Min Turn Speed", 0, 180, 120, 10);
    public final NumberSetting maxTurnSpeed = new NumberSetting("Max Turn Speed", 0, 180, 160, 10);

    // Target
    public EntityPlayer target;
    private List<EntityPlayer> targetList = new ArrayList<>();

    // Attacking
    private final MSTimer attackTimer = new MSTimer();

    // Autoblock
    private boolean blinkAB;
    private boolean block;
    private boolean blink;
    private boolean swapped;
    private int serverSlot;
    public boolean renderBlock;

    private boolean rotated;

    public KillAura() {
        super("KillAura", Keyboard.KEY_X, ModuleCategory.COMBAT);
        this.addSettings(targetMode, switchDelay, attackTeamMates, maxAPS, minAPS, findRange, attackRange, blockRange, autoBlockMode, minYawRandom, maxYawRandom, minPitchRandom, maxPitchRandom, minTurnSpeed, maxTurnSpeed);
        switchDelay.setDependency(targetMode, "Switch");
    }

    @EventLink
    public final Listener<EventMotionPre> eventMotionPreListener = event -> {
        if (isNull()) {
            return;
        }
        getTargets();
        if (target == null) {
            unblock();
            return;
        }

        if (!rotated) {
            return;
        }

        if (CombatUtils.isHoldingSword() && CombatUtils.getSqrtDistanceToEntityBox(target) <= blockRange.getPowValue()) {
            switch (autoBlockMode.getMode()) {
                case "Watchdog":
                    renderBlock = true;
                    if (blinkAB) {
                        Nexus.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(true);
                        blink = true;

                        if (serverSlot != mc.thePlayer.inventory.currentItem % 8 + 1) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(serverSlot = mc.thePlayer.inventory.currentItem % 8 + 1));
                            swapped = true;
                            block = false;
                        }

                        blinkAB = false;
                    } else {
                        if (serverSlot != mc.thePlayer.inventory.currentItem) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(serverSlot = mc.thePlayer.inventory.currentItem));
                            swapped = false;
                        }

                        if (CombatUtils.getSqrtDistanceToEntityBox(target) < attackRange.getPowValue()) {
                            if (attackTimer.hasTimeElapsed(getAttackDelay())) {
                                mc.thePlayer.swingItem();
                                mc.playerController.attackEntity(mc.thePlayer, target);
                                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.INTERACT));

                                attackTimer.reset();
                            }
                        }

                        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                        block = true;

                        Nexus.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
                        blink = false;

                        blinkAB = true;
                    }
                    break;

                case "Vanilla":
                    if (!block) {
                        PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                        renderBlock = true;
                        block = true;
                    }
                    break;
                case "Fake":
                    renderBlock = true;
                    break;
            }
        } else {
            unblock();
        }

        if (autoBlockMode.isMode("Watchdog")) {
            return;
        }
        if (attackTimer.hasTimeElapsed(getAttackDelay())) {
            if (CombatUtils.getSqrtDistanceToEntityBox(target) < attackRange.getPowValue()) {
                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(mc.thePlayer, target);
                attackTimer.reset();
            }
        }
    };

    private void unblock() {
        if (block) {
            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            block = false;
        }

        if (blink) {
            Nexus.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
            blink = false;
        }

        if (swapped) {
            if (serverSlot != mc.thePlayer.inventory.currentItem) {
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(serverSlot = mc.thePlayer.inventory.currentItem));
                swapped = false;
            }
        }
        renderBlock = false;
    }

    private long getAttackDelay() {
        double x = maxAPS.getValue();
        double y = minAPS.getValue();
        float finalValue = MathUtils.getRandomInRange((float) x, (float) y) + 6;
        return (long) (1000L / finalValue);
    }

    @EventLink
    public final Listener<EventSilentRotation> eventSilentRotationListener = event -> {
        if (target == null) {
            rotated = false;
            return;
        }
        float[] rotations = RotationUtils.getRotations(target, minYawRandom.getValueFloat(), maxYawRandom.getValueFloat(), minPitchRandom.getValueFloat(), maxPitchRandom.getValueFloat(), minTurnSpeed.getValueFloat(), maxTurnSpeed.getValueFloat());
        event.setSpeed(180f);
        event.setYaw(rotations[0]);
        event.setPitch(rotations[1]);
        rotated = true;
    };

    private final MSTimer sortDelay = new MSTimer();
    private int targetCount;

    private void getTargets() {
        if (targetMode.isMode("Single")) {
            target = CombatUtils.getClosestPlayerWithin(findRange.getValue(), attackTeamMates.getValue());
        }
        if (targetMode.isMode("Switch")) {
            targetList = CombatUtils.getPlayersWithin(findRange.getValue(), attackTeamMates.getValue());

            if (sortDelay.hasTimeElapsed(switchDelay.getValue())) {
                sortDelay.reset();
                targetCount++;
            }

            if (targetCount > targetList.size()) {
                targetCount = 0;
            }

            target = targetList.get(targetCount);
        }
    }

    @Override
    public void onEnable() {
        blinkAB = true;
        block = false;
        blink = false;
        swapped = false;
        renderBlock = false;
        target = null;
        attackTimer.setTime(9999);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        unblock();
        rotated = false;

        target = null;
        super.onDisable();
    }
}

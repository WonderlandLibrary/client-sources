package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.input.KeyboardPressEvent;
import dev.excellent.api.event.impl.input.MouseInputEvent;
import dev.excellent.api.event.impl.player.*;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.AutoPotion;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.client.script.ScriptConstructor;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.player.InvUtil;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.rotation.AuraUtil;
import dev.excellent.impl.util.rotation.GCDUtil;
import dev.excellent.impl.util.rotation.RotationUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.KeyValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import org.joml.Vector2f;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.StreamSupport;

@ModuleInfo(name = "Elytra Helper", description = "Позволяет вам более продвинуто использовать Элитры.", category = Category.PLAYER)
public class ElytraHelper extends Module {

    private final KeyValue fireworkKey = new KeyValue("Феерверк", this, Keyboard.KEY_NONE.keyCode);
    private final KeyValue swapKey = new KeyValue("Свап", this, Keyboard.KEY_NONE.keyCode);
    private final BooleanValue autoFly = new BooleanValue("Авто взлёт", this, true);
    private final BooleanValue autoFirework = new BooleanValue("Авто феерверк", this, true, () -> !autoFly.getValue());
    private final BooleanValue strafe = new BooleanValue("Преследовать таргет", this, false);
    private final NumberValue fireworkDelay = new NumberValue("Время исп феерверка", this, 500, 0, 5000, 50, () -> !strafe.getValue());
    private boolean startFallFlying;
    private ItemStack oldStack = null;
    private int oldStackSlot;
    public Vector2f rotation = new Vector2f();
    private LivingEntity target;
    private final TimerUtil fireworkTimer = TimerUtil.create();
    private final ScriptConstructor scriptConstructor = ScriptConstructor.create();
    private final InvUtil.Hand handUtil = new InvUtil.Hand();
    private long delay;
    private boolean swap;

    @Override
    protected void onEnable() {
        super.onEnable();
        swap = false;
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        swap = false;
        delay = 0;
    }

    private final Listener<PacketEvent> packet = handUtil::onEventPacket;

    private final Listener<UpdateEvent> onUpdate = event -> {
        scriptConstructor.update();
        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
        updateTarget();

        if (autoFly.getValue() && mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == Items.ELYTRA) {
            if (mc.player.isOnGround()) {
                startFallFlying = false;
                mc.gameSettings.keyBindJump.setPressed(false);
                mc.player.jump();
                return;
            }


            if (!mc.player.isElytraFlying() && !startFallFlying && !mc.player.isOnGround() && mc.player.motion.y < 0.0) {
                mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                if (autoFirework.getValue() && !startFallFlying) {
                    mc.player.jumpMovementFactor = 0;
                    if (fireworkTimer.hasReached(fireworkDelay.getValue().doubleValue())) {
                        useFirework();
                        fireworkTimer.reset();
                    }
                }
                startFallFlying = true;
            }
        }
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;

        if (!strafe.getValue()) return;
        if (target != null) setRotation(target);
    };
    private final Listener<MouseInputEvent> onMouseInput = event -> {
        if (swapKey.getValue() == event.getMouseButton() && scriptConstructor.isFinished()) {
            if (PlayerUtil.findItemSlot(Items.ELYTRA) == -1) {
                ChatUtil.addText(TextFormatting.RED + "Не найдена элитра в инвентаре.");
                return;
            }

            swap = true;
            swap();
        } else if (fireworkKey.getValue() == event.getMouseButton()) {
            useFirework();
        }
    };
    private final Listener<KeyboardPressEvent> onKeyboardInput = event -> {
        if (swapKey.getValue() == event.getKeyCode() && scriptConstructor.isFinished()) {
            swap = true;
            swap();
        } else if (fireworkKey.getValue() == event.getKeyCode()) {
            useFirework();
        }
    };

    private int findAndTrowItem(int hbSlot, int invSlot) {
        if (hbSlot != -1) {
            this.handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            if (hbSlot != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            }
            mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            if (hbSlot != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
            }
            mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return hbSlot;
        }
        if (invSlot != -1) {
            handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            mc.playerController.pickItem(invSlot);
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return invSlot;
        }
        return -1;
    }

    private void swap() {
        if (mc.player == null || !swap) return;

        int elytraSlot = PlayerUtil.findItemSlot(Items.ELYTRA);

        if (elytraSlot == -1) {
            ChatUtil.addText(TextFormatting.RED + "Не найдена элитра в инвентаре.");
            swap = false;
            return;
        }

        scriptConstructor.cleanup()
                .addStep(50, () -> {
                    ItemStack itemStack = mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                    if (itemStack.getItem() != Items.ELYTRA) {
                        oldStack = itemStack.copy();
                        oldStackSlot = elytraSlot;
                        PlayerUtil.moveItem(elytraSlot, 6, true);
                    } else if (oldStack != null && oldStackSlot != -2) {
                        PlayerUtil.moveItem(oldStackSlot, 6, true);
                        if (mc.player.inventory.getStackInSlot(oldStackSlot).getItem() != Items.ELYTRA) {
                            mc.playerController.windowClick(0, oldStackSlot, 0, ClickType.PICKUP, mc.player);
                        }
                        oldStack = null;
                    }
                }).addStep(100, () -> swap = false);
    }

    private void useFirework() {
        scriptConstructor.cleanup().addStep(100, () -> {
            if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
            int hbSlot = InvUtil.getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, true);
            int invSlot = InvUtil.getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, false);


            if (invSlot == -1 && hbSlot == -1) {
                return;
            }

            int old = mc.player.inventory.currentItem;

            int slot = findAndTrowItem(hbSlot, invSlot);
            if (slot > 8) {
                mc.playerController.pickItem(slot);
            }
            if (InvUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                mc.player.inventory.currentItem = old;
            }
        });
    }

    public final Listener<MoveInputEvent> onMove = event -> {
        if (swap) {
            event.setForward(0);
            event.setStrafe(0);
        }
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null && aura.shouldAttack()) return;

        if (!strafe.getValue()) return;
        if (target != null && mc.player.isElytraFlying()) {
            MoveUtil.fixMovement(event, AutoPotion.singleton.get().isActive() ? mc.player.rotationYaw : rotation.x);
        }
    };
    public final Listener<JumpEvent> onJump = event -> {
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null && aura.shouldAttack()) return;

        if (!strafe.getValue()) return;
        if (target != null && mc.player.isElytraFlying()) {
            event.setYaw(rotation.x);
        }
    };
    public final Listener<PlayerLookEvent> onLook = event -> {
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null && aura.shouldAttack()) return;

        if (!strafe.getValue()) return;
        if (target != null) {
            setRotation(target);
            event.setRotation(rotation);
        }
    };
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null && aura.shouldAttack()) return;

        if (!strafe.getValue()) return;
        if (target != null && mc.player.isElytraFlying()) {
            event.setYaw(rotation.x);
        }
    };
    private final Listener<MotionEvent> onMotion = event -> {
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null && aura.shouldAttack()) return;

        if (!strafe.getValue()) return;
        if (target == null || AutoPotion.singleton.get().isActive())
            return;

        setRotation(target);
        event.setYaw(rotation.x);
        event.setPitch(rotation.y);
    };

    private final Listener<LookVecEvent> onLookVec = event -> {
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null && aura.shouldAttack()) return;

        if (!strafe.getValue()) return;
        if (target != null && mc.player.isElytraFlying()) {
            event.setYaw(rotation.x);
            event.setPitch(rotation.y);
            mc.player.rotationYawHead = rotation.x;
            mc.player.renderYawOffset = rotation.x;
            mc.player.rotationPitchHead = rotation.y;
        }
    };
    private final Listener<LookVecEvent.Elytra> onLookVecElytra = event -> {
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null && aura.shouldAttack()) return;

        if (!strafe.getValue()) return;
        if (target != null && mc.player.isElytraFlying()) {
            event.setPitch(rotation.y);
        }
    };


    private void setRotation(final LivingEntity base) {
        if (mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != Items.ELYTRA) return;
        KillAura aura = KillAura.singleton.get();
        if (aura.getTarget() != null && aura.shouldAttack()) return;
        if ((target.hurtTime == 0) && fireworkTimer.hasReached(fireworkDelay.getValue().doubleValue()) || (mc.player.getDistance(target) < 10 && fireworkTimer.hasReached(500))) {
            useFirework();
            fireworkTimer.reset();
        }

        Vector3d vec3d = AuraUtil.getVector(base);
        double diffX = vec3d.x;
        double diffY = vec3d.y;
        double diffZ = vec3d.z;

        float[] rotations = {
                (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F,
                (float) -Math.toDegrees(Math.atan2(diffY, Math.hypot(diffX, diffZ)))
        };

        float deltaYaw = MathHelper.wrapDegrees(Mathf.calculateDelta(rotations[0], this.rotation.x));
        float deltaPitch = Mathf.calculateDelta(rotations[1], this.rotation.y);

        float additionYaw = Math.min(Math.max(Math.abs(deltaYaw), 1), 3);
        float additionPitch = Math.min(Math.max(Math.abs(deltaPitch), 1), 3);

        float newYaw = this.rotation.x + (deltaYaw > 0.0f ? additionYaw : -additionYaw) + ThreadLocalRandom.current().nextFloat();
        float newPitch = this.rotation.y + (deltaPitch > 0.0f ? additionPitch : -additionPitch) + ThreadLocalRandom.current().nextFloat();

        float gcd = GCDUtil.getGCDValue();
        newYaw = newYaw - (newYaw - this.rotation.x) % gcd;
        newPitch = newPitch - (newPitch - this.rotation.y) % gcd;

        this.rotation.x = MathHelper.wrapDegrees(RotationUtil.normalize(newYaw));
        this.rotation.y = MathHelper.clamp(newPitch, -89F, 89F);
        mc.player.rotationYaw = this.rotation.x;
        mc.player.rotationPitch = this.rotation.y;
    }


    private void updateTarget() {
        KillAura aura = KillAura.singleton.get();
        if (aura.isEnabled() && aura.getTarget() != null && aura.getTarget() != target) {
            target = aura.getTarget();
        } else {
            if (!mc.player.isElytraFlying() || StreamSupport.stream(mc.world.getAllEntities().spliterator(), true).noneMatch(entity -> entity.equals(target))) {
                target = null;
            }
        }
    }
}

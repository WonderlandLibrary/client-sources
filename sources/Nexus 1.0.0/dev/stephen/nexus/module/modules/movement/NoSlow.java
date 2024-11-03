package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.impl.player.EventMotionPost;
import dev.stephen.nexus.event.impl.player.EventMotionPre;
import dev.stephen.nexus.event.types.TransferOrder;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.combat.KillAura;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.ModeSetting;
import dev.stephen.nexus.utils.mc.PacketUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BowItem;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class NoSlow extends Module {
    public static final BooleanSetting block = new BooleanSetting("Block", true);
    public static final ModeSetting blockMode = new ModeSetting("Block Mode", "Vanilla", "Vanilla", "Grim", "NCP", "Switch", "Double Switch", "Blink");

    public static final BooleanSetting eat = new BooleanSetting("Eat", true);
    public static final ModeSetting eatMode = new ModeSetting("Eat Mode", "Vanilla", "Vanilla", "Watchdog", "Switch", "Double Switch", "Intave", "Polar", "BlocksMC");

    public static final BooleanSetting drink = new BooleanSetting("Drink", true);
    public static final ModeSetting drinkMode = new ModeSetting("Drink Mode", "Vanilla", "Vanilla", "Watchdog", "Switch", "Double Switch", "Intave", "Polar", "BlocksMC");

    public static final BooleanSetting bow = new BooleanSetting("Bow", true);
    public static final ModeSetting bowMode = new ModeSetting("Bow Mode", "Vanilla", "Vanilla", "Watchdog");

    public static final BooleanSetting blockRightClick = new BooleanSetting("BlockRightClick", false);
    public static final BooleanSetting workWithAB = new BooleanSetting("WorkWithAB", false);
    private boolean send;

    //Blink
    public final ArrayList<Packet<?>> packets = new ArrayList<>();
    private int slotChangeTick;
    private boolean blinking;

    public NoSlow() {
        super("NoSlow", "Removes slowdown", 0, ModuleCategory.MOVEMENT);
        this.addSettings(block, blockMode, eat, eatMode, drink, drinkMode, bow, bowMode, blockRightClick, workWithAB);

        blockMode.addDependency(block, true);
        eatMode.addDependency(eat, true);
        drinkMode.addDependency(drink, true);
        bowMode.addDependency(bow, true);
    }

    @Override
    public void onEnable() {
        send = false;

        slotChangeTick = mc.player.age;
        blinking = false;

        packets.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        releasePackets();
        super.onDisable();
    }

    @EventLink
    public final Listener<EventMotionPre> eventMotionPreListener = event -> {
        if (isNull()) {
            blinking = false;
            releasePackets();
            return;
        }

        if (canDoSwordMethod()) {
            switch (blockMode.getMode()) {
                case "Switch":
                    int selectedSlot = mc.player.getInventory().selectedSlot;
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot % 8 + 1));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot));
                    break;
                case "Double Switch":
                    int selectedSlot2 = mc.player.getInventory().selectedSlot;
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2 % 8 + 1));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2 % 7 + 2));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2));
                    break;
                case "Grim":
                    PacketUtils.sendPacketSilently(new PlayerInteractItemC2SPacket(Hand.OFF_HAND, 0, Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch));
                    break;
                case "NCP":
                    PacketUtils.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
                    break;
            }
        }

        if (canDoFoodMethod()) {
            switch (eatMode.getMode()) {
                case "Switch":
                    int selectedSlot = mc.player.getInventory().selectedSlot;
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot % 8 + 1));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot));
                    break;
                case "Double Switch":
                    int selectedSlot2 = mc.player.getInventory().selectedSlot;
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2 % 8 + 1));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2 % 7 + 2));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2));
                    break;
                case "BlocksMC":
                    PacketUtils.sendSequencedPacketSilently(it -> new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(new Vec3d(0, 0, 0), Direction.UP, new BlockPos(-1, -1, -1), false), it));
                    break;
                case "Polar":
                    if (mc.player.getItemUseTime() == 1) {
                        PacketUtils.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.UP));
                    }
                    break;
            }
        }

        if (canDoDrinkMethod()) {
            switch (drinkMode.getMode()) {
                case "Switch":
                    int selectedSlot = mc.player.getInventory().selectedSlot;
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot % 8 + 1));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot));
                    break;

                case "Double Switch":
                    int selectedSlot2 = mc.player.getInventory().selectedSlot;
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2 % 8 + 1));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2 % 7 + 2));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(selectedSlot2));
                    break;

                case "BlocksMC":
                    PacketUtils.sendSequencedPacketSilently(it -> new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(new Vec3d(0, 0, 0), Direction.UP, new BlockPos(-1, -1, -1), false), it));
                    break;

                case "Polar":
                    if (mc.player.getItemUseTime() == 1) {
                        PacketUtils.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.UP));
                    }
                    break;
            }
        }

        if ((canDoFoodMethod() && eatMode.isMode("Watchdog")) || (canDoDrinkMethod() && drinkMode.isMode("Watchdog")) || (canDoBowMethod() && bowMode.isMode("Watchdog"))) {
            doWatchdogMotion(event);
        }

        if ((canDoFoodMethod() && eatMode.isMode("Intave")) || (canDoDrinkMethod() && drinkMode.isMode("Intave"))) {
            if (mc.player.getItemUseTimeLeft() == 0 || mc.player.getItemUseTime() == 1) {
                PacketUtils.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, mc.player.getBlockPos(), Direction.UP));
            }
        }

        if (blockMode.isMode("Blink") && block.getValue()) {
            if (mc.player.getMainHandStack().getItem() instanceof SwordItem && mc.player.isUsingItem() && slotChangeTick != mc.player.age) {
                if (mc.player.getItemUseTime() % 2 == 0) {
                    blinking = false;
                    releasePackets();
                    mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());
                } else {
                    blinking = true;
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket((mc.player.getInventory().selectedSlot + 1) % 8));
                    PacketUtils.sendPacket(new UpdateSelectedSlotC2SPacket(mc.player.getInventory().selectedSlot));
                }
            } else {
                blinking = false;
                releasePackets();
            }
        }
    };

    @EventLink
    public final Listener<EventMotionPost> eventTickPostListener = event -> {
        if (isNull()) {
            return;
        }
        if (canDoSwordMethod()) {
            switch (blockMode.getMode()) {
                case "NCP":
                    mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());
                    break;
            }
        }
    };

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }

        if (blockMode.isMode("Blink") && block.getValue()) {
            if (event.getOrder() == TransferOrder.SEND) {
                if (event.getPacket() instanceof UpdateSelectedSlotC2SPacket) {
                    slotChangeTick = mc.player.age;
                }

                if (blinking) {
                    event.setCancelled(true);
                    packets.add(event.getPacket());
                }
            }
        }

        if ((canDoFoodMethod() && eatMode.isMode("Watchdog")) || (canDoDrinkMethod() && drinkMode.isMode("Watchdog")) || (canDoBowMethod() && bowMode.isMode("Watchdog"))) {
            doWatchdogPacket(event);
        }
    };

    private void doWatchdogPacket(EventPacket eventPacket) {
        Packet<?> packet = eventPacket.getPacket();
        if (packet instanceof PlayerInteractItemC2SPacket) {
            if (PlayerUtil.inAirTicks() < 4) {
                if (mc.player.isOnGround()) {
                    mc.player.setJumping(false);
                    mc.player.jump();
                }
                send = true;
                eventPacket.cancel();
            }
        }
    }

    private void doWatchdogMotion(EventMotionPre eventMotionPre) {
        if (PlayerUtil.inAirTicks() == 4 && send) {
            send = false;
            PacketUtils.sendSequencedPacketSilently(a -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, a, Client.INSTANCE.getRotationManager().yaw, Client.INSTANCE.getRotationManager().pitch));
        } else {
            eventMotionPre.y += 1E-14;
        }
    }

    private void releasePackets() {
        if (isNull()) {
            packets.clear();
        } else {
            if (!packets.isEmpty()) {
                ArrayList<Packet<?>> c = packets;
                for (Packet<?> packet : c) {
                    mc.getNetworkHandler().sendPacket(packet);
                }
                packets.clear();
            }
        }
    }

    public boolean shouldntSlow() {
        return canDoSwordMethodBareBones() || canDoFoodMethod() || canDoDrinkMethod() || canDoBowMethod();
    }

    private boolean canDoSwordMethod() {
        if (Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(KillAura.class).blocking) {
            if (!workWithAB.getValue()) {
                return false;
            }
        }
        return (mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getOffHandStack().getItem() instanceof SwordItem) && block.getValue() && (blockRightClick.getValue() ? mc.options.useKey.isPressed() : mc.player.isUsingItem());
    }

    private boolean canDoSwordMethodBareBones() {
        return (mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getOffHandStack().getItem() instanceof SwordItem) && block.getValue() && mc.player.isUsingItem();
    }

    private boolean holdingSword() {
        return (mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getOffHandStack().getItem() instanceof SwordItem) && block.getValue();
    }

    private boolean canDoFoodMethod() {
        return (mc.player.getMainHandStack().getDefaultComponents().contains(DataComponentTypes.FOOD) || mc.player.getOffHandStack().getDefaultComponents().contains(DataComponentTypes.FOOD)) && eat.getValue() && mc.player.isUsingItem();
    }

    private boolean canDoDrinkMethod() {
        return (mc.player.getMainHandStack().getItem() instanceof PotionItem || mc.player.getOffHandStack().getItem() instanceof PotionItem) && drink.getValue() && mc.player.isUsingItem();
    }

    private boolean canDoBowMethod() {
        return (mc.player.getMainHandStack().getItem() instanceof BowItem || mc.player.getOffHandStack().getItem() instanceof BowItem) && bow.getValue() && mc.player.isUsingItem();
    }
}
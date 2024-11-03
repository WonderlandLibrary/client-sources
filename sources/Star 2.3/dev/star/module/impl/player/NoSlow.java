package dev.star.module.impl.player;

import dev.star.event.impl.network.PacketSendEvent;
import dev.star.event.impl.player.MotionEvent;
import dev.star.event.impl.player.SlowDownEvent;
import dev.star.event.impl.player.UpdateEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.utils.player.MovementUtils;
import dev.star.utils.server.PacketUtils;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Vanilla", "Watchdog");
    private final ModeSetting SwordMode = new ModeSetting("Sword Mode", "Test", "Test", "Test 1", "Test 2");

    private boolean synced;

    public NoSlow() {
        super("NoSlow", Category.PLAYER, "prevent item slowdown");
        this.addSettings(mode, SwordMode);
    }

    @Override
    public void onUpdateEvent(UpdateEvent event) {

        if (mode.is("Watchdog")) {
            if (!mc.thePlayer.isUsingItem() || this.mc.thePlayer.getCurrentEquippedItem().getItem() == null) return;

            if (this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                switch (mode.getMode()) {
                    case "Test":
                        PacketUtils.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                        PacketUtils.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                        break;
                    case "Test 1":
                        if (mc.thePlayer.ticksExisted % 2 == 0)
                            PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(null));
                        break;
                    case "Test 2":
                        PacketUtils.sendPacket(new C02PacketUseEntity());
                        break;
                }
            } else {
                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0, 0, 0));
                }
            }
        }
    }

    @Override
    public void onSlowDownEvent(SlowDownEvent event) {

        event.cancel();

    }

    private  int offGroundTicks = 0;
    private boolean send = false;

    @Override
    public void onMotionEvent(MotionEvent e) {
        this.setSuffix(mode.getMode());
        switch (mode.getMode()) {
            case "Watchdog":


                if (mc.thePlayer.onGround) {
                    offGroundTicks = 0;
                } else {
                    offGroundTicks++;
                }

                final ItemStack item = this.mc.thePlayer.getCurrentEquippedItem();
                if (offGroundTicks == 4 && send) {
                    send = false;
                    PacketUtils.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(
                            new BlockPos(-1, -1, -1),
                            255, item,
                            0, 0, 0
                    ));

                } else if (item != null && mc.thePlayer.isUsingItem()) {
                    e.setY(e.getY() + 1E-14);
                }

                break;

        }
    }

    @Override
    public void onPacketSendEvent(PacketSendEvent event) {

        if (mode.is("Watchdog")) {
            if (event.getPacket() instanceof C08PacketPlayerBlockPlacement && !mc.thePlayer.isUsingItem()) {
                C08PacketPlayerBlockPlacement blockPlacement = (C08PacketPlayerBlockPlacement) event.getPacket();
                if (this.mc.thePlayer.getCurrentEquippedItem() != null && blockPlacement.getPlacedBlockDirection() == 255 && offGroundTicks < 2) {
                    if (mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.thePlayer.jump();
                    }
                    send = true;
                    event.cancel();
                }
            }
        }

    }




}

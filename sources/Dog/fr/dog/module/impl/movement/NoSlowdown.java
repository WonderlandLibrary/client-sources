package fr.dog.module.impl.movement;

import fr.dog.component.impl.packet.BlinkComponent;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketSendEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.event.impl.player.move.SlowDownEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.util.packet.PacketUtil;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class NoSlowdown extends Module {
    public ModeProperty mode = ModeProperty.newInstance("Mode", new String[]{"Watchdog", "Vanilla"}, "Watchdog");
    private final BooleanProperty sword = BooleanProperty.newInstance("Sword", false);
    private final BooleanProperty consumables = BooleanProperty.newInstance("Consumables", true);
    private final BooleanProperty bow = BooleanProperty.newInstance("Bow", true);
    private final BooleanProperty potion = BooleanProperty.newInstance("Potions", false);
    private final BooleanProperty autoJump = BooleanProperty.newInstance("Auto Jump", false, () -> mode.is("Watchdog"));

    private boolean doSend = false;
    private int groundTicks = 0;
    private boolean blinkEnabled = false;

    public NoSlowdown() {
        super("NoSlow", ModuleCategory.MOVEMENT);
        this.registerProperties(sword, consumables, potion, bow, mode, autoJump);
    }

    private boolean isAllowed() {
        if (mc.thePlayer.getHeldItem() != null) {
            if (sword.getValue() && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                return true;
            }
            if (consumables.getValue() && mc.thePlayer.getHeldItem().getItem() instanceof ItemFood) {
                return true;
            }
            if (bow.getValue() && mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
                return true;
            }
            return potion.getValue() && mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion;
        }
        return false;
    }

    @SubscribeEvent
    private void onSlowDown(SlowDownEvent event) {
        if (isAllowed()) {
            event.setForward(1f);
            event.setStrafe(1f);
        }
    }

    @SubscribeEvent
    private void onSendPacket(PacketSendEvent event) {
        this.setSuffix(mode.getValue());
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement c08 && mc.thePlayer.isUsingItem()) {
            if (c08.getPlacedBlockDirection() == 255 && isAllowed()) {
                if (groundTicks < 2 && mc.thePlayer.onGround) {
                    mc.thePlayer.movementInput.jump = false;
                    mc.thePlayer.jump();
                    doSend = true;
                    event.setCancelled(true);
                }
            }
        }
    }

    @SubscribeEvent
    private void onNetworkUpdate(PlayerNetworkTickEvent event) {
        if (mc.thePlayer.onGround) {
            groundTicks = 0;
        } else {
            groundTicks++;
        }

        boolean isUsingItem = mc.thePlayer.isUsingItem();

        if (mode.getValue().equals("Watchdog") && isUsingItem && isAllowed() && mc.thePlayer.onGround && autoJump.getValue()) {
            mc.thePlayer.movementInput.jump = true;
            mc.thePlayer.jump();
        }

        if (mode.getValue().equals("Blink")) {
            if (isUsingItem && isAllowed() && !blinkEnabled) {
                BlinkComponent.onEnable();
                blinkEnabled = true;
            } else if (!isUsingItem && blinkEnabled) {
                BlinkComponent.onDisable();
                blinkEnabled = false;
            }
        }

        if (groundTicks == 2 && doSend) {
            doSend = false;
            PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
        } else if (isUsingItem && isAllowed()) {
            event.setPosY(event.getPosY() + 1E-14);
        }
    }
}

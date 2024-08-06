package club.strifeclient.module.implementations.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.event.implementations.player.SlowdownEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.module.implementations.combat.KillAura;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.ModeSetting;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

import java.util.function.Supplier;

@ModuleInfo(name = "NoSlow", description = "Don't slow down while holding slowing items.", category = Category.MOVEMENT)
public final class NoSlowdown extends Module {

    private final ModeSetting<NoSlowdownMode> modeSetting = new ModeSetting<>("Mode", NoSlowdownMode.WATCHDOG);

    @EventHandler
    private final Listener<SlowdownEvent> slowdownEventListener = e -> e.setCancelled(true);

    private KillAura killAura;

    private boolean shouldNoSlow() {
        return true;
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        switch (modeSetting.getValue()) {
            case WATCHDOG: {
                if (e.isUpdate() && shouldNoSlow() && (mc.thePlayer.ticksExisted % 3 == 0 || mc.thePlayer.getItemInUseCount() < 3) && mc.thePlayer.isUsingItem()) {
                    if ((mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion && ItemPotion.isSplash(mc.thePlayer.getHeldItem().getMetadata())) || mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)
                        return;
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem == 8 ? 0 : mc.thePlayer.inventory.currentItem + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
                break;
            }
        }
    };

    public enum NoSlowdownMode implements SerializableEnum {
        WATCHDOG("Watchdog");

        final String name;

        NoSlowdownMode(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public Supplier<Object> getSuffix() {
        return () -> modeSetting.getValue().getName();
    }
}

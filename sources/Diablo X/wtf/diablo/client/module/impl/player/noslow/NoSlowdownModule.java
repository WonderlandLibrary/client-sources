package wtf.diablo.client.module.impl.player.noslow;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.SlowDownEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.math.MathUtil;

@ModuleMetaData(
        name = "No Slowdown",
        description = "Prevents you from slowing down when using items",
        category = ModuleCategoryEnum.PLAYER
)
public final class NoSlowdownModule extends AbstractModule {

    private final ModeSetting<EnumNoSlowMode> enumNoSlowModeModeSetting = new ModeSetting<>("Mode", EnumNoSlowMode.WATCHDOG);

    public NoSlowdownModule() {
        this.registerSettings(enumNoSlowModeModeSetting);
    }

    @EventHandler
    private final Listener<SlowDownEvent> slowDownEventListener = event -> {
        final int itemLoc = mc.thePlayer.inventory.currentItem;

        if (event.getMode() == SlowDownEvent.Mode.Item) {
            switch (enumNoSlowModeModeSetting.getValue()) {
                case WATCHDOG:
                    if (mc.thePlayer.onGround && mc.thePlayer.isUsingItem() && !mc.thePlayer.isEating() || mc.thePlayer.isBlocking()) {
                        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                            if (event.getEventType() == EventTypeEnum.PRE) {
                                final int currentSlot = mc.thePlayer.inventory.currentItem;
                                final ItemStack heldItem = mc.thePlayer.getHeldItem();

                                mc.getNetHandler().addToSendQueueNoEvent(new C09PacketHeldItemChange(getNewSlot(currentSlot)));
                                mc.getNetHandler().addToSendQueueNoEvent(new C09PacketHeldItemChange(currentSlot));
                                mc.getNetHandler().addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(heldItem));
                            }
                        }
                    }
                    break;
                case GRIM:
                    if(event.getEventType() == EventTypeEnum.PRE) {
                        if (mc.thePlayer.ticksExisted % 2 == 0) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(itemLoc == 1 ? 0 : 1));
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(itemLoc));
                            mc.thePlayer.motionX *= 0.8;
                            mc.thePlayer.motionZ *= 0.8;
                        }
                    }
                    break;
                case AAC4:
                    if(event.getEventType() == EventTypeEnum.PRE) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(itemLoc));
                    }
                    break;
                case AAC5:
                    if (event.getEventType() == EventTypeEnum.POST) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
                    }
                    break;
                case NCP:
                    if (event.getEventType() == EventTypeEnum.POST) {
                        if(mc.thePlayer.isBlocking() && mc.thePlayer.ticksExisted % 10 == 0)
                            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }
                    break;
                default:
                    event.setCancelled(true);
                    break;
            }

            event.setCancelled(true);

        }
    };

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event ->
    {
        this.setSuffix(enumNoSlowModeModeSetting.getValue().getName());
        switch (enumNoSlowModeModeSetting.getValue()) {
            case WATCHDOG:
                if (mc.thePlayer.isEating() && mc.thePlayer.onGround) {
                    event.setPitch(90);
                }
                break;
        }
    };

    private int getNewSlot(final int currentSlot) {
        final int selectedSlot = MathUtil.getRandomInt(0, 8);

        if (selectedSlot == currentSlot) {
            return getNewSlot(currentSlot);
        }

        return selectedSlot;
    }
}

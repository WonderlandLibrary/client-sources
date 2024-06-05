package koks.modules.impl.utilities;

import koks.event.Event;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0BPacketEntityAction;

/**
 * @author avox | lmao | kroko
 * @created on 09.09.2020 : 17:41
 */
public class AntiFlag extends Module {

    public AntiFlag() {
        super("AntiFlag", "Its detect when you flag", Category.UTILITIES);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof PacketEvent) {
            if (((PacketEvent) event).getType() == PacketEvent.Type.RECIVE) {
                PacketEvent e = (PacketEvent) event;
                if (e.getPacket() instanceof C01PacketChatMessage) {
                    String message = ((C01PacketChatMessage) e.getPacket()).getMessage();
                    if (message.contains("It looks like you might be glitching")) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                        mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                        if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                            mc.gameSettings.keyBindUseItem.pressed = true;
                            mc.gameSettings.keyBindUseItem.pressed = false;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
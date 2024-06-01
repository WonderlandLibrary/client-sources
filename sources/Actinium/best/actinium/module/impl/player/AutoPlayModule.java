package best.actinium.module.impl.player;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.input.ClickEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.render.ChatUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S02PacketChat;

@ModuleInfo(
        name = "Auto Play",
        description = "automatically joins another game for you.",
        category = ModuleCategory.PLAYER
)
public class AutoPlayModule extends Module {
    public ModeProperty mode = new ModeProperty("Auto Play Mode",this,new String[] {"mmc","Hypixel"},"mmc");

    @Callback
    public void onMotion(MotionEvent event) {
        setSuffix(mode.getMode());
        if(mode.is("mmc")) {
            for (int i = 0; i < 9; i++) {
                ItemStack stackInSlot = mc.thePlayer.inventory.getStackInSlot(i);

                if (stackInSlot != null && stackInSlot.getItem() == Items.paper) {
                    mc.thePlayer.inventory.currentItem = i;
                    mc.gameSettings.keyBindUseItem.setPressTime(1);
                }
            }
        }
    }

    @Callback
    public void onPacket(PacketEvent event) {
        if(mode.is("Hypixel")) {
            if(event.getPacket() instanceof S02PacketChat) {
                final S02PacketChat wrapper = (S02PacketChat) event.getPacket();
                if(wrapper.getChatComponent().getFormattedText().contains("play again?")) {
                    mc.thePlayer.sendChatMessage("/play solo_insane");
                }
            }
        }
    }
}

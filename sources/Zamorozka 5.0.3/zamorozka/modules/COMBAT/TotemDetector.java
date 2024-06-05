package zamorozka.modules.COMBAT;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotion;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventSendPacket;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ChatUtils;

public class TotemDetector extends Module {

    public static HashMap<String, Integer> popList = new HashMap<>();

	public TotemDetector() {
		super("TotemDetector", 0, Category.COMBAT);
	}

	@EventTarget
	public void onPacket(EventPacket event) {
        if (mc.world == null || mc.player == null) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);
                if(popList == null) {
                    popList = new HashMap<>();
				}
				if (popList.get(entity.getName()) == null) {
                    popList.put(entity.getName(), 1);
                    ChatUtils.printChatprefix(ChatFormatting.WHITE + "Player " + ChatFormatting.RED + entity.getName() + ChatFormatting.WHITE + " has popped " + ChatFormatting.GREEN + "1 totem!");
                } else if(!(popList.get(entity.getName()) == null)) {
                    int popCounter = popList.get(entity.getName());
                    int newPopCounter = popCounter += 1;
                    popList.put(entity.getName(), newPopCounter);
                    ChatUtils.printChatprefix(ChatFormatting.WHITE + "Player " + ChatFormatting.RED + entity.getName() + ChatFormatting.WHITE + " has popped " + ChatFormatting.GREEN +newPopCounter + " totems!");
                }
            }
        }
    }
	
	@EventTarget
	public void onPre(EventPreMotionUpdates event) {
        if (mc.world == null || mc.player == null) {
            return;
        }
       for(EntityPlayer player : mc.world.playerEntities) {
           if(player.getHealth() <= 0) {
               if(popList.containsKey(player.getName())) {
                   ChatUtils.printChatprefix(ChatFormatting.WHITE + "Player " + ChatFormatting.RED + player.getName() + ChatFormatting.WHITE + " died after popped " + ChatFormatting.GREEN +  popList.get(player.getName()) + " totems!");
                   popList.remove(player.getName(), popList.get(player.getName()));
               }
           }
       }
   }
}
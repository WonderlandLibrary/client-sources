package none.module.modules.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.ChatUtil;

public class NameProtect extends Module{

	public NameProtect() {
		super("NameProtect", "NameProtect", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	public static List<String> strings = new ArrayList<>();
	public static String name = "You";
	
	public static String GetName() {
		return name;
	}
	
	public static void setNameProtect(String name) {
		NameProtect.name = name;
	}

	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, EventPacket.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;
            if (em.isPre()) {
                final NetHandlerPlayClient var4 = mc.thePlayer.connection;
                final List players = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(var4.getPlayerInfoMap());
                for (final Object o : players) {
                    final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
                    if (info == null) {
                        break;
                    }
                    if (!strings.contains(info.getGameProfile().getName())) {
                        strings.add(info.getGameProfile().getName());
                    }
                }
                for (Object o : mc.theWorld.getLoadedEntityList()) {
                    if (o instanceof EntityPlayer) {
                        if (!strings.contains(((EntityPlayer) o).getName())) {
                            strings.add(((EntityPlayer) o).getName());
                        }
                    }
                }
            }
            
        }
		if (event instanceof EventPacket) {
            EventPacket ep = (EventPacket) event;
            if (ep.isIncoming() && ep.getPacket() instanceof S02PacketChat) {
                S02PacketChat packet = (S02PacketChat) ep.getPacket();
                if (packet.getChatComponent().getUnformattedText().contains("NoneTK")) {
                }else if (packet.getChatComponent().getUnformattedText().contains(mc.thePlayer.getName())) {
                    String temp = packet.getChatComponent().getFormattedText();
                    ChatUtil.printChat(temp.replaceAll(mc.thePlayer.getName(), "\2479" + NameProtect.GetName() + "\247r"));
                    ep.setCancelled(true);
                } else {
                    String[] list = new String[]{"join", "left", "leave", "leaving", "lobby", "server", "fell", "died", "slain", "burn", "void", "disconnect", "kill", "by", "was", "quit", "blood", "game"};
                    for (String str : list) {
                        if (packet.getChatComponent().getUnformattedText().toLowerCase().contains(str)) {
                            ep.setCancelled(false);
                            break;
                        }
                    }
                }
            }
        }
	}

}

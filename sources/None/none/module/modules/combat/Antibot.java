package none.module.modules.combat;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.util.MathHelper;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventChat;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;

public class Antibot extends Module{

	public Antibot() {
		super("AntiBot", "AntiBot", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	private static String[] modes = {"Hypixel", "Packet", "Mineplex", "Custom"};
	public static ModeValue MODE = new ModeValue("AntiBot-Mode", "Hypixel", modes);
    private BooleanValue DEAD = new BooleanValue("Dead", true);
    private BooleanValue KILLER = new BooleanValue("HypixelKiller", true);
	ArrayList<Entity>entities = new ArrayList<>();
    private TimeHelper timer = new TimeHelper();
    TimeHelper lastRemoved = new TimeHelper();
    
    public static List<EntityPlayer> getInvalid() {
        return invalid;
    }
    
    public static List<String> getInvalidName() {
    	List<String> name = new ArrayList<>();
    	for (EntityPlayer player : invalid) {
    		name.add(player.getCustomNameTag().isEmpty() ? player.getName() : player.getCustomNameTag());
    	}
        return name;
    }
    
    private static List<EntityPlayer> invalid = new ArrayList<>();
    private static List<EntityPlayer> removed = new ArrayList<>();
    
    @Override
    protected void onEnable() {
    	super.onEnable();
    	invalid.clear();
    	removed.clear();
    }
    
    @Override
    protected void onDisable() {
    	super.onDisable();
    	invalid.clear();
    	removed.clear();
      if (mc.getCurrentServerData() != null && (mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") || mc.getCurrentServerData().serverIP.toLowerCase().contains("mineplex"))) {
    	  EventChat.addchatmessage(ChatFormatting.DARK_GREEN + " Antibot was kept enabled for your protection.");
    	  Client.instance.notification.show(new Notification(NotificationType.ERROR, getName(), "was kept enabled for your protection.", 10));
          toggle();
      }
    }
    
	@Override
	@RegisterEvent(events = {EventPacket.class, EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		String currentSetting = MODE.getSelected();
		if (mc.getIntegratedServer() == null) {
            if (mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && !currentSetting.equals("Hypixel")) {
                MODE.setObject("Hypixel");
                EventChat.addchatmessage(ChatFormatting.WHITE + " AntiBot has been set to the proper mode.");
                Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), "has been set to the proper mode.", 10));
            } else if (mc.getCurrentServerData().serverIP.toLowerCase().contains("mineplex") && !currentSetting.equals("Mineplex")) {
            	MODE.setObject("Mineplex");
            	EventChat.addchatmessage(ChatFormatting.WHITE + " AntiBot has been set to the proper mode.");
                Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), "has been set to the proper mode.", 10));
            }
        }
		
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + MODE.getSelected());
		
		if (mc.thePlayer.ticksExisted < 1) {
			invalid.clear();
			removed.clear();
		}
		
        boolean killer = KILLER.getObject();

        if (event instanceof EventPacket) {
            EventPacket ep = (EventPacket) event;
            if (currentSetting.equalsIgnoreCase("Packet")) {
	            if (ep.isIncoming() && ep.getPacket() instanceof S0CPacketSpawnPlayer) {
	                S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) ep.getPacket();
	                double entX = packet.getX() / 32;
	                double entY = packet.getY() / 32;
	                double entZ = packet.getZ() / 32;
	                double posX = mc.thePlayer.posX;
	                double posY = mc.thePlayer.posY;
	                double posZ = mc.thePlayer.posZ;
	                double var7 = posX - entX;
	                double var9 = posY - entY;
	                double var11 = posZ - entZ;
	                float distance = MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
	                if (distance <= 17 && entY > mc.thePlayer.posY + 1 && (mc.thePlayer.posX != entX && mc.thePlayer.posY != entY && mc.thePlayer.posZ != entZ)) {
	                    ep.setCancelled(true);
	                }
	            }
            }
        }
        if (event instanceof EventPreMotionUpdate) {
            EventPreMotionUpdate em = (EventPreMotionUpdate) event;
            
            if (em.isPre()) {
                if (mc.getIntegratedServer() == null) {
                    if (mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && !currentSetting.equals("Hypixel")) {
                        MODE.setObject("Hypixel");
                        EventChat.addchatmessage(ChatFormatting.WHITE + " AntiBot has been set to the proper mode.");
                        Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), "has been set to the proper mode.", 10));
                    } else if (mc.getCurrentServerData().serverIP.toLowerCase().contains("mineplex") && !currentSetting.equals("Mineplex")) {
                    	MODE.setObject("Mineplex");
                    	EventChat.addchatmessage(ChatFormatting.WHITE + " AntiBot has been set to the proper mode.");
                        Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), "has been set to the proper mode.", 10));
                    }
                }
                if (DEAD.getObject())
                    for (Object o : mc.theWorld.loadedEntityList) {
                        if (o instanceof EntityPlayer) {
                            EntityPlayer ent = (EntityPlayer) o;
                            assert ent != mc.thePlayer;
                            if (ent.isPlayerSleeping()) {
                                mc.theWorld.removeEntity(ent);
                            }
                        }
                    }
            }
            /**
             * created by LeakedPvP
             */
            if(currentSetting.equalsIgnoreCase("Mineplex") && mc.thePlayer.ticksExisted > 40)
            for(Object o : mc.theWorld.loadedEntityList){
    			Entity en = (Entity)o;
    			if(en instanceof EntityPlayer && !(en instanceof EntityPlayerSP)){
    				int ticks = en.ticksExisted;
    				double diffY = Math.abs(mc.thePlayer.posY - en.posY);
    				String name = en.getName();
    				String customname = en.getCustomNameTag();
    				if(customname == "" && !invalid.contains((EntityPlayer)en) && ticks < 40){
    					invalid.add((EntityPlayer)en);
    				}
    			}
    		}
            if (em.isPre() && currentSetting.equalsIgnoreCase("hypixel")) {
                //Clears the invalid player list after a second to prevent false positives staying permanent.
            	if(killer){
            		if(!removed.isEmpty()){
            			if(lastRemoved.hasTimeReached(1000)){
            				if(removed.size() == 1){
            					EventChat.addchatmessage(ChatFormatting.GOLD + " Watchdog Killer -> " + removed.size() + " bot has been removed");
            	                Client.instance.notification.show(new Notification(NotificationType.INFO, "Watchdog Killer -> ", removed.size() + " bot has been removed", 10));
            				}else{
            					EventChat.addchatmessage(ChatFormatting.GOLD + " Watchdog Killer -> " + removed.size() + " bot has been removed");
            	                Client.instance.notification.show(new Notification(NotificationType.INFO, "Watchdog Killer -> ", removed.size() + " bot has been removed", 10));
            				}
            				lastRemoved.setLastMS();
            				removed.clear();
            			}
            		}
            	}
                if (!invalid.isEmpty() && timer.hasTimeReached(1000)) {
                    invalid.clear();
                    timer.setLastMS();
                }
                // Loop through entity list
                for (Object o : mc.theWorld.getLoadedEntityList()) {
                    if (o instanceof EntityPlayer) {
                        EntityPlayer ent = (EntityPlayer) o;
                        //Make sure it's not the local player + they are in a worrying distance. Ignore them if they're already invalid.
                        if (ent != mc.thePlayer && !invalid.contains(ent)) {
                            //Handle current mode
                            switch (currentSetting) {
                                case "Hypixel": {
                                	
                                    String formated = ent.getDisplayName().getFormattedText();
                                    String custom = ent.getCustomNameTag();
                                    String name = ent.getName();
                                    
                                    if(ent.isInvisible() && !formated.startsWith("§c") && formated.endsWith("§r") && custom.equals(name)){
                    					double diffX = Math.abs(ent.posX - mc.thePlayer.posX);
                    					double diffY = Math.abs(ent.posY - mc.thePlayer.posY);
                    					double diffZ = Math.abs(ent.posZ - mc.thePlayer.posZ);
                    					double diffH = Math.sqrt(diffX * diffX + diffZ * diffZ);
                    					if(diffY < 13 && diffY > 10 && diffH < 3){
                    						List<EntityPlayer> list = getTabPlayerList();
                    						if(!list.contains(ent)){
                    							if(killer){
                                        			lastRemoved.setLastMS();
                                        			removed.add(ent);
                                              		mc.theWorld.removeEntity(ent);
                                              	}
                                        		invalid.add(ent);
                    						}
                    						
                    					}
                    				}
                                    
                                    if (ent.isInvisible() && ent.posY > mc.thePlayer.posY + 7) {
                                    	List<EntityPlayer> list = getTabPlayerList();
                						if(!list.contains(ent)){
                							if(killer){
                                    			lastRemoved.setLastMS();
                                    			removed.add(ent);
                                          		mc.theWorld.removeEntity(ent);
                                          	}
                                    		invalid.add(ent);
                						}
                                    }
                                    //SHOP BEDWARS
                                    if(!formated.startsWith("§") && formated.endsWith("§r")){
                                    	invalid.add(ent);
                                    }
                                    if(ent.isInvisible()){
                                    	//BOT INVISIBLES IN GAME
                                    	if(!custom.equalsIgnoreCase("") && custom.toLowerCase().contains("§c§c") && name.contains("§c")){
                                    		if(killer){
                                    			lastRemoved.setLastMS();
                                    			removed.add(ent);
                                          		mc.theWorld.removeEntity(ent);
                                          	}
                                    		invalid.add(ent);
                                    	}
                                    }
                                    //WATCHDOG BOT
                                    if(!custom.equalsIgnoreCase("") && custom.toLowerCase().contains("§c") && custom.toLowerCase().contains("§r")){
                                      	if(killer){
                                      		lastRemoved.setLastMS();
                                      		removed.add(ent);
                                      		mc.theWorld.removeEntity(ent);
                                      	}
                                      	invalid.add(ent);
                                      }
                                    
                                    //BOT LOBBY
                                    if(formated.contains("§8[NPC]")){
                                    	invalid.add(ent);
                                    }
                                    if(!formated.contains("§c") && !custom.equalsIgnoreCase("")){

                                    	invalid.add(ent);
                                    }
                                    break;
                                }


                            }
                        }
                    }
                }

            }
        }
	}
	
	public static List<EntityPlayer> getTabPlayerList() {
        final NetHandlerPlayClient var4 = Minecraft.getMinecraft().thePlayer.connection;
        final List<EntityPlayer> list = new ArrayList<>();
        final List players = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(var4.getPlayerInfoMap());
        for (final Object o : players) {
            final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
            if (info == null) {
                continue;
            }
            list.add(Minecraft.getMinecraft().theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return list;
    }

}

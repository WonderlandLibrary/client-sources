package none.module.modules.world;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemSword;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.EventChat;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.ChatUtil;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;

public class Murder extends Module{

	public Murder() {
		super("MurderFinder", "MurderFinder", Category.WORLD, Keyboard.KEY_NONE);
	}
	
	private String[] mode = {"Client-Side", "Server-Side"};
	public ModeValue modes = new ModeValue("Notification-Mode", "Client-Side", mode);
	private BooleanValue checkbow = new BooleanValue("Show-BowPlayer", false);
	
	TimeHelper timer = new TimeHelper();
	private String MESSAGE = "{P} = Murderer";
	
	public ArrayList<String> nameList = new ArrayList<>(); 
	public ArrayList<String> bowList = new ArrayList<>(); 
	
	@Override
	protected void onEnable() {
		super.onEnable();
		nameList.clear();
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		nameList.clear();
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, Event2D.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			if (mc.thePlayer.ticksExisted <= 1 && e.isPre()) {
				nameList.clear();
				bowList.clear();
				timer.setLastMS();
				Client.instance.notification.show(new Notification(NotificationType.INFO, "MurderFinder", " Clear List MurderName.", 3));
			}
			
			for (Object o : mc.theWorld.loadedEntityList) {
				if (o instanceof EntityPlayer && checkbow.getObject()) {
	                EntityPlayer ent = (EntityPlayer) o;
	                if (ent != mc.thePlayer && nameList.contains(ent.getName()) && !ent.isHaveBow) {
	                	ent.isHaveBow = true;
	                }
	                if (ent != mc.thePlayer && ent.getHeldItem() != null && isBow(ent.getHeldItem().getItem()) && !ent.isHaveBow) {
	                	ent.isHaveBow = true;
	                }
	                if (ent.isHaveBow && timer.hasTimeReached(15000)) {
	                	switch (modes.getSelected()) {
		                	case "Client-Side": {
		                		EventChat.addchatmessage("\247d" + ent.getName() + " \2477 Had Bow.!");
		                		bowList.add(ent.getName());
		                		break;
		                	}
	                	}
	                	timer.setLastMS();
	                }
	            }
	            if (o instanceof EntityPlayer) {
	                EntityPlayer ent = (EntityPlayer) o;
	                if (ent != mc.thePlayer && nameList.contains(ent.getName()) && !ent.isMurderer) {
	                	ent.isMurderer = true;
	                }
	                if (ent != mc.thePlayer && ent.getHeldItem() != null && isMurder(ent.getHeldItem().getItem()) && !ent.isMurderer) {
	                	ent.isMurderer = true;
	                }
	                if (ent.isMurderer && timer.hasTimeReached(15000)) {
	                	switch (modes.getSelected()) {
		                	case "Server-Side": {
		                		String customChat = (MESSAGE);
		                		customChat = customChat.replace("{P}", "%s");
		                		ChatUtil.sendChat(String.format(customChat, ent.getName()));
		                		EventChat.addchatmessage("\247d" + ent.getName() + " \2477is the murderer!");
		                		nameList.add(ent.getName());
		                		break;
		                	}
		                	case "Client-Side": {
		                		EventChat.addchatmessage("\247d" + ent.getName() + " \2477is the murderer!");
		                		nameList.add(ent.getName());
		                		break;
		                	}
	                	}
	                	timer.setLastMS();
	                }
	            }
	        }
		}
		
		if (event instanceof Event2D) {
			
		}
	}
	
	public boolean isMurder(Item item){
    	if(item instanceof ItemMap || item.getUnlocalizedName().equalsIgnoreCase("item.ingotGold") ||
    			item instanceof ItemBow || item.getUnlocalizedName().equalsIgnoreCase("item.arrow") ||
    			item.getUnlocalizedName().equalsIgnoreCase("item.potion") ||
    			item.getUnlocalizedName().equalsIgnoreCase("item.paper") ||
    			item.getUnlocalizedName().equalsIgnoreCase("tile.tnt") || 
    			item.getUnlocalizedName().equalsIgnoreCase("item.web") ||
    			item.getUnlocalizedName().equalsIgnoreCase("item.bed") || 
    			item.getUnlocalizedName().equalsIgnoreCase("item.compass") || 
    			item.getUnlocalizedName().equalsIgnoreCase("item.comparator") ||
    			item.getUnlocalizedName().equalsIgnoreCase("item.shovelWood")){
    		return false;
    	}
    	
    	if (item instanceof ItemSword) {
    		return true;
    	}
    	
    	return false;
    }
	
	public boolean isBow(Item item){
    	if (item instanceof ItemBow) {
    		return true;
    	}
    	
    	return false;
    }

}

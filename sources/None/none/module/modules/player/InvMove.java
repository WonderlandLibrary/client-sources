package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import none.clickGui.clickgui;
import none.clickGui.configpanel.ConfigPanel;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;

public class InvMove extends Module{
	
	private String[] modes = {"Normal", "AACP"};
	public ModeValue invmode = new ModeValue("Inv-Mode", "Normal", modes);
	private BooleanValue CARRY = new BooleanValue("Carry", false);
	boolean inInventory = false;
	
	public InvMove() {
		super("InventoryMove", "InventoryMove", Category.PLAYER, Keyboard.KEY_NONE);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
	}

	@Override
	@RegisterEvent(events = {EventPacket.class, EventTick.class, EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		String mode = invmode.getSelected();
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + mode);
		
		if (mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof ConfigPanel || mc.currentScreen instanceof clickgui) {
            return;
        }
		
		if (mc.thePlayer.ticksExisted <= 10) return;
		
        if(event instanceof EventPreMotionUpdate){
        	EventPreMotionUpdate em = (EventPreMotionUpdate)event;
        	if(em.isPre()){
        		
        	}
        }
        if (event instanceof EventTick) {
            if (mc.currentScreen != null) {
    			if(!mode.equalsIgnoreCase("")){
    				KeyBinding[] moveKeys = new KeyBinding[]{
    					mc.gameSettings.keyBindForward,
    					mc.gameSettings.keyBindBack,
    					mc.gameSettings.keyBindLeft,
    					mc.gameSettings.keyBindRight,
    					mc.gameSettings.keyBindJump					
    				};
    				for (KeyBinding bind : moveKeys){
    					KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
    				}
    				if(!inInventory){
    					if(mode.equalsIgnoreCase("AACP")){
                			mc.thePlayer.connection.sendPacket(new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SPRINTING));
                		}
    					inInventory = !inInventory;
    				}
    			}
    			if(mc.currentScreen instanceof GuiInventory){
                if (Keyboard.isKeyDown(200)) {
                    mc.thePlayer.rotationPitch -= 3;
                }
                if (Keyboard.isKeyDown(208)) {
                    mc.thePlayer.rotationPitch += 3;
                }
                if (Keyboard.isKeyDown(203)) {
                    mc.thePlayer.rotationYaw -= 6;
                }
                if (Keyboard.isKeyDown(205)) {
                    mc.thePlayer.rotationYaw += 6;
                }
    			}
            }else{
            	if (Keyboard.isKeyDown(200)) {
                    mc.thePlayer.rotationPitch -= 3;
                }
                if (Keyboard.isKeyDown(208)) {
                    mc.thePlayer.rotationPitch += 3;
                }
                if (Keyboard.isKeyDown(203)) {
                    mc.thePlayer.rotationYaw -= 6;
                }
                if (Keyboard.isKeyDown(205)) {
                    mc.thePlayer.rotationYaw += 6;
                }
            	if(inInventory){
            		if(mode.equalsIgnoreCase("AACP")){
            			mc.thePlayer.connection.sendPacket(new C0BPacketEntityAction(mc.thePlayer, Action.START_SPRINTING));
            		}
					inInventory = !inInventory;
				}
            	KeyBinding[] moveKeys = new KeyBinding[]{
    					mc.gameSettings.keyBindForward,
    					mc.gameSettings.keyBindBack,
    					mc.gameSettings.keyBindLeft,
    					mc.gameSettings.keyBindRight,
    					mc.gameSettings.keyBindJump					
    				};
    				for (KeyBinding bind : moveKeys){
    					KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
    				}
            }
        }
        if (event instanceof EventPacket) {
            EventPacket ep = (EventPacket) event;
            Packet packet = ep.getPacket();
            if(CARRY.getObject())
            if (ep.isOutgoing() && packet instanceof C0DPacketCloseWindow) {
                ep.setCancelled(true);
            }
            if(packet instanceof C0BPacketEntityAction){          	
            	C0BPacketEntityAction p = (C0BPacketEntityAction)packet;
            	if(p.getAction() == Action.START_SPRINTING && inInventory && mode.equalsIgnoreCase("AACP")) {
            		ep.setCancelled(true);
            		mc.thePlayer.setSprinting(false);
            	}
            }
        }
	}

}

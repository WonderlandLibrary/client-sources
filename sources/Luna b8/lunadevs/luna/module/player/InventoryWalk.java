package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;
import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventPacket;
import lunadevs.luna.events.PacketRecieveEvent;
import lunadevs.luna.events.PacketSendEvent;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0DPacketCloseWindow;

public class InventoryWalk extends Module{

	public InventoryWalk() {
		super("InventoryWalk", Keyboard.KEY_B, Category.PLAYER, false);
	}
	
	Integer[] keyBindingMove = { Integer.valueOf(mc.gameSettings.keyBindForward.getKeyCode()), Integer.valueOf(mc.gameSettings.keyBindBack.getKeyCode()), Integer.valueOf(mc.gameSettings.keyBindRight.getKeyCode()), Integer.valueOf(mc.gameSettings.keyBindLeft.getKeyCode()), Integer.valueOf(mc.gameSettings.keyBindJump.getKeyCode()) };
	Integer[] keyBindingLook = { Integer.valueOf(200), Integer.valueOf(208), Integer.valueOf(205), Integer.valueOf(203) };

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		if (!(Minecraft.getMinecraft().currentScreen instanceof GuiChat)){
	      Integer[] arrayOfInteger;
	      int j = (arrayOfInteger = this.keyBindingMove).length;
	      for (int i = 0; i < j; i++)
	      {
	        Integer bindingMove = arrayOfInteger[i];KeyBinding.setKeyBindState(bindingMove.intValue(), Keyboard.isKeyDown(bindingMove.intValue()));
	      }
	      if (mc.currentScreen != null)
	      {
	        if (Keyboard.isKeyDown(this.keyBindingLook[0].intValue())) {
	          mc.thePlayer.rotationPitch -= 5.0F;
	        }
	        if (Keyboard.isKeyDown(this.keyBindingLook[1].intValue())) {
	          mc.thePlayer.rotationPitch += 5.0F;
	        }
	        if (Keyboard.isKeyDown(this.keyBindingLook[2].intValue())) {
	          mc.thePlayer.rotationYaw += 5.0F;
	        }
	        if (Keyboard.isKeyDown(this.keyBindingLook[3].intValue())) {
	          mc.thePlayer.rotationYaw -= 5.0F;
	        }
	      }
		}
	    }
	
	
	@EventTarget
	  public void onPacketRecieve(PacketRecieveEvent event)
	  {
	    if ((event.getPacket() instanceof C0DPacketCloseWindow)){ 
	      event.setCancelled(true);
	    }
	  }
	
	

	@Override
	public void onRender() {
		
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		
	}


	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}

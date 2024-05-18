package Squad.Modules.Render;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventRender3D;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class OutlineEsp extends Module{

	public OutlineEsp() {
		super("ESP", Keyboard.KEY_NONE, 0x88, Category.Render);
		// TODO Auto-generated constructor stub
	}
	
	  
	  public void onEnable()
	  {
	    EventManager.register(this);
	    super.onEnable();
	  }
	  
}
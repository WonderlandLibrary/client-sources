package Squad.Modules.Render;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventRender3D;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntityEnderChest;

public class StorageEsp extends Module{

	public StorageEsp() {
		super("StorageESP", Keyboard.KEY_NONE, 0x88, Category.Render);
		// TODO Auto-generated constructor stub
	}
	
	public void onEnable(){
		EventManager.register(this);
	}
	
	public void onDisable(){
		EventManager.unregister(this);
	}
	
	
	public void setup()
	  {
	    ArrayList<String> modes = new ArrayList();
	    modes.add("Box");
	    Squad.instance.setmgr.rSetting(new Setting("StorageESPMode", this, "Box", modes));
	  }
	
	@EventTarget
	public void onUpdate(EventRender3D e){
		if(Squad.instance.setmgr.getSettingByName("StorageESPMode").getValString().equalsIgnoreCase("Box")){
			   for (Object o : mc.theWorld.loadedTileEntityList) {
			        if ((o instanceof TileEntityEnderChest))
			        {
			        	  TileEntityEnderChest echest = (TileEntityEnderChest)o;
			              GlStateManager.pushMatrix();
			              GlStateManager.translate(echest.getPos().getX() - RenderManager.renderPosX + 0.5D, echest.getPos().getY() - RenderManager.renderPosY + 0.5D, echest.getPos().getZ() - RenderManager.renderPosZ + 0.5D);
			              GlStateManager.rotate(-Minecraft.getMinecraft().renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			              GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
			              GlStateManager.disableDepth();
			              GlStateManager.disableLighting();
			Gui.drawRect(-23.0D, -23.0D, -6.0D, -18.0D, -16777216);
	            Gui.drawRect(23.0D, -23.0D, 6.0D, -18.0D, -16777216);
	            Gui.drawRect(-23.0D, 21.0D, -6.0D, 26.0D, -16777216);
	            Gui.drawRect(23.0D, 21.0D, 6.0D, 26.0D, -16777216);
	            Gui.drawRect(18.0D, 25.0D, 23.0D, 9.0D, -16777216);
	            Gui.drawRect(18.0D, -23.0D, 23.0D, -6.0D, -16777216);
	            Gui.drawRect(-18.0D, 25.0D, -23.0D, 9.0D, -16777216);
	            Gui.drawRect(-18.0D, -23.0D, -23.0D, -6.0D, -16777216);
	            
	            Gui.drawRect(-22.0D, -22.0D, -7.0D, -19.0D, 0x07C050);
	            Gui.drawRect(-22.0D, 22.0D, -7.0D, 25.0D, 0x07C050);
	            Gui.drawRect(19.0D, -22.0D, 22.0D, -7.0D, 0x07C050);
	            Gui.drawRect(-19.0D, 22.0D, -22.0D, 10.0D, 0x07C050);
	            
	            Gui.drawRect(22.0D, 22.0D, 7.0D, 25.0D, 0x07C050);
	            Gui.drawRect(22.0D, -22.0D, 7.0D, -19.0D, 0x07C050);
	            Gui.drawRect(19.0D, 22.0D, 22.0D, 10.0D, 0x07C050);
	            Gui.drawRect(-19.0D, -22.0D, -22.0D, -7.0D, 0x07C050);
	          }
	          GlStateManager.enableDepth();
	          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	          GlStateManager.popMatrix();

	        }
}
	}
}


	        



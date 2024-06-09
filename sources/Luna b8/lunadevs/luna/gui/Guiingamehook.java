package lunadevs.luna.gui;

import java.awt.Color;

import org.lwjgl.opengl.Display;

import com.zCore.Core.zCore;

import lunadevs.luna.main.Luna;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.manage.TabGuiManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.config.GommeHD;
import lunadevs.luna.module.config.Hypixel;
import lunadevs.luna.module.config.Mineplex;
import lunadevs.luna.module.movement.Scaffold;
import lunadevs.luna.utils.Comparator;
import lunadevs.luna.utils.RenderHelper;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;

public class Guiingamehook extends GuiIngame{
	
	public Guiingamehook(Minecraft mcIn) {
		super(mcIn);
	}
	 public void func_175180_a(float p_175180_1_){
		 super.func_175180_a(p_175180_1_);
		 this.renderClientName();
		 this.renderClientVersion();
		 this.renderCords();
		 this.renderCurrentScaffold();
		 this.renderConfig();
		 this.renderMods();
		 //this.renderChatOverlay();
		 TabGuiManager.init();
		 TabGuiManager.render();
	 }
	 
	  public void renderConfig(){
	    	 if(ModuleManager.findMod(Hypixel.class).isEnabled){
	    		 zCore.drawRect(0, 207, 109, 228, Integer.MIN_VALUE);
	        	 Luna.fontRenderer.drawStringWithShadow("§fX: " + (int)zCore.p().posX + " Y: " + (int)zCore.p().posY + " Z: " + (int)zCore.p().posZ, 2, 207, 0);
	             Luna.fontRenderer.drawStringWithShadow("§fConfig: Hypixel", 2, 217, 0);
	             RenderHelper.drawRect(107.0F, 207.0F, 109.0F, 228.0F, 0xFF1aff1a);
	         
	     }else if(ModuleManager.findMod(Mineplex.class).isEnabled){
	    	 zCore.drawRect(0, 207, 109, 228, Integer.MIN_VALUE);
	    	 Luna.fontRenderer.drawStringWithShadow("§fX: " + (int)zCore.p().posX + " Y: " + (int)zCore.p().posY + " Z: " + (int)zCore.p().posZ, 2, 207, 0);
	         Luna.fontRenderer.drawStringWithShadow("§fConfig: Mineplex", 2, 217, 0);
	         RenderHelper.drawRect(107.0F, 207.0F, 109.0F, 228.0F, 0xFF1aff1a);
	         
	     }else if(ModuleManager.findMod(GommeHD.class).isEnabled){
	    	 zCore.drawRect(0, 207, 109, 228, Integer.MIN_VALUE);
	    	 Luna.fontRenderer.drawStringWithShadow("§fX: " + (int)zCore.p().posX + " Y: " + (int)zCore.p().posY + " Z: " + (int)zCore.p().posZ, 2, 207, 0);
	         Luna.fontRenderer.drawStringWithShadow("§fConfig: GommeHD", 2, 217, 0);
	         RenderHelper.drawRect(107.0F, 207.0F, 109.0F, 228.0F, 0xFF1aff1a);
	         
	     }
	     }
	 
     public void renderClientName(){
         Luna.fontRenderer50.drawStringWithShadow(Luna.CLIENT_NAME, 3, -2, 0xFF9931FF);
     }
  
  public void renderClientVersion(){
         Luna.fontRenderer.drawStringWithShadow("b"+Luna.CLIENT_BUILD, 55, 6, 0xFFFFFFFF);
  }
     public void renderCords(){
    	 if(!(zCore.mc().currentScreen instanceof GuiChat)){
         Luna.fontRenderer.drawStringWithShadow("XYZ: " + String.valueOf(Math.round(Luna.mc.thePlayer.posX) + " " +  Math.round(Luna.mc.thePlayer.posY) + " " + Math.round(Luna.mc.thePlayer.posZ) + " "), 5, GuiMainMenu.height-10, 0x7200ff);
     }}
     public void renderChatOverlay(){
    	 if((zCore.mc().currentScreen instanceof GuiChat)){
        Luna.fontRendererGUI.drawStringWithShadow("Enter message...", 5, GuiMainMenu.height-13, getRainbow(6000, -15 * 14));
     }}
     public void renderCurrentScaffold(){
    	 if(ModuleManager.findMod(Scaffold.class).isEnabled()){
    	 ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Display.getWidth(), Display.getHeight());
         Luna.fontRenderer.drawStringWithShadow(String.valueOf(Scaffold.blockcount), sr.getScaledWidth() / 2 - (Luna.fontRenderer.getStringWidth(String.valueOf(Scaffold.blockcount))) / 2, sr.getScaledHeight() / 2 - 15, 0xFF9931FF);
     }}
     public void renderMods(){
		 ScaledResolution sr = new ScaledResolution(zCore.mc(), zCore.mc().displayWidth, zCore.mc().displayHeight);
		int y = 18;
		int mheight = 10 * y + 1;
		for (Module m : Luna.moduleManager.getModules()) {
			final boolean setTransition = false;
			if (m.getTransition() > 0) {
				m.setTransition(m.getTransition() - 1);
			}
			if (!m.isEnabled) continue;
			if (m.value == true){
				//zCore.drawRect(Luna.mc.displayWidth / 2 + 15 - (Luna.fontRenderer).getStringWidth(m.getName()), mheight - 1, sr.getScaledWidth(), mheight + zCore.getFontRenderer().FONT_HEIGHT, Integer.MAX_VALUE);
				Luna.fontRendererArrayList.drawStringWithShadow(m.name +  " §7" + m.getValue(), ((Luna.mc.displayWidth / 2 + 15) - (Luna.fontRenderer).getStringWidth(m.getName() + m.getValue())) - 21 + m.getTransition(), y - 15, RenderUtils.getRainbow(6000, +15 * y) /**getRainbow(6000, -15 * 4)*/);
			//Original: Luna.fontRenderer.drawStringWithShadow(m.name + "\2477[" + m.getValue() + "]", ((Luna.mc.displayWidth / 2 + 15) - (Luna.fontRenderer).getStringWidth(m.getName() + m.getValue())) - 21 + m.getTransition(), y - 15, 0xFF7200ff);
			}else if (m.value == false){
			//Original: Luna.fontRenderer.drawStringWithShadow(m.name, ((Luna.mc.displayWidth / 2 + 15) - (Luna.fontRenderer).getStringWidth(m.getName()))  - 19 +  m.getTransition(), y - 15, 0xFF7200ff);
				Luna.fontRendererArrayList.drawStringWithShadow(m.name, ((Luna.mc.displayWidth / 2 + 15) - (Luna.fontRenderer).getStringWidth(m.getName()))  - 19 +  m.getTransition(), y - 15,  RenderUtils.getRainbow(6000, +15 * y) /**getRainbow(6000, -15 * 4)*/);
			}
				y += Luna.fontRendererArrayList.getStringHeight(m.getName() + m.getValue());
			}
		ModuleManager.mods.sort(new Comparator());
		}
		
		private int getRainbow(int speed, int offset) {
	        float hue = (System.currentTimeMillis() + offset) % speed;
	        hue /= speed;
	        return Color.getHSBColor(hue, 1f, 1f).getRGB();
		}
	
	 public static Color fade(long offset, float fade)
	  {
		 float hue = (float) (System.nanoTime() + offset) / 1.0E10F % 1.0F;
			long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
			Color c = new Color((int) color);
			 return new Color(c.getRed()/255.0F*fade, c.getGreen()/255.0F*fade, c.getBlue()/255.0F*fade, c.getAlpha()/255.0F);
	  }
}

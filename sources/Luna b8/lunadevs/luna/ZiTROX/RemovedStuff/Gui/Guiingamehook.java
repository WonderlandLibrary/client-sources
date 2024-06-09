package lunadevs.luna.ZiTROX.RemovedStuff.Gui;

import java.awt.Color;

import com.zCore.Core.zCore;

import lunadevs.luna.main.Luna;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.manage.TabGuiManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.config.GommeHD;
import lunadevs.luna.module.config.Hypixel;
import lunadevs.luna.module.config.Mineplex;
import lunadevs.luna.utils.Comparator;
import lunadevs.luna.utils.RenderHelper;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

public class Guiingamehook extends GuiIngame{
	
	public Guiingamehook(Minecraft mcIn) {
		super(mcIn);
	}
	 public void func_175180_a(float p_175180_1_){
		 super.func_175180_a(p_175180_1_);
		 this.renderClientName();
		 this.renderClientVersion();
		 this.renderCoords();
		 this.renderFPS();
		 this.renderMods();
		 this.renderConfig();
		//this.renderHotBarRect();
		 TabGuiManager.init();
		 TabGuiManager.render();
	 }
	 
     public void renderClientName(){
    	 Minecraft mc = Minecraft.getMinecraft();
    	 ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
         Luna.fontRendererHotBar.drawStringWithShadow("Luna", 3, 3, 0xFF9931FF);
        // zCore.rainbowCircle(sr, 2, 2, 30);
     }
     /**
      * TODO: Replace dark color codes with 0xFF9931FF [[Purple]]
      */
     /**
      * Render Client Version
      */
  
  public void renderClientVersion(){
         Luna.fontRenderer.drawStringWithShadow("§fb" + Luna.CLIENT_BUILD + Luna.isPRE, 35, 6, RGBtoHEX(255, 0, 0, 255));
  }
  /**
   * Render Coords
   */
     public void renderCoords(){
         //Luna.fontRenderer.drawStringWithShadow("XYZ: " + String.valueOf(Math.round(Luna.mc.thePlayer.posX) + " " +  Math.round(Luna.mc.thePlayer.posY) + " " + Math.round(Luna.mc.thePlayer.posZ) + " "), 5, GuiMainMenu.height-10, 0x7200ff);
     }
     /**
      * Render FPS
      */
     public void renderFPS(){
         //Luna.fontRenderer.drawStringWithShadow("FPS: " + String.valueOf(Luna.mc.debugFPS), 5, GuiMainMenu.height-18, 0x7200ff);
     }
     /**
      * Renders a rect on the side where it says the config  mode.
      */
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
     /**
      * Renders a rect at your hotbar with information
      */
     public void renderHotBarRect(){
   	 Minecraft mc = Minecraft.getMinecraft();
    	 ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    	 zCore.drawRect(0, sr.getScaledHeight() - 23, sr.getScaledWidth(), sr.getScaledHeight(), -1610612735);
    	 Luna.fontRendererHotBar.drawStringWithShadow("L", 5, sr.getScaledHeight() - 20, RenderUtils.getRainbow(6000, -15 * 60));
    	 Luna.fontRendererArrayList.drawStringWithShadow("§fFPS: " + mc.debugFPS, 18, sr.getScaledHeight() - 13, 0xFF0066);
    	 if(!mc.isSingleplayer()){
        	 Luna.fontRendererArrayList.drawStringWithShadow("§fPING: " + mc.getCurrentServerData().pingToServer, 18, sr.getScaledHeight() - 23, 0);
    	 }
    	 Luna.fontRendererArrayList.drawStringWithShadow("§fX: " + (int)mc.thePlayer.posX, 68, sr.getScaledHeight() - 13, 0xFF0066);
    	 Luna.fontRendererArrayList.drawStringWithShadow("§fY: " + (int)mc.thePlayer.posY, 68, sr.getScaledHeight() - 23, 0xFF0066);
    	 Luna.fontRendererArrayList.drawStringWithShadow("§fZ: " + (int)mc.thePlayer.posZ, 98, sr.getScaledHeight() - 23, 0xFF0066);


    	 
     }
     public static double rn = 1.5D;
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
					Luna.fontRendererArrayList.drawStringWithShadow(m.name +  " §7" + m.getValue(), ((Luna.mc.displayWidth / 2 + 15) - (Luna.fontRenderer).getStringWidth(m.getName() + m.getValue())) - 21 + m.getTransition(), y - 15, RenderUtils.getRainbow(6000, -15 * y) /**getRainbow(6000, -15 * 4)*/);
				//Original: Luna.fontRenderer.drawStringWithShadow(m.name + "\2477[" + m.getValue() + "]", ((Luna.mc.displayWidth / 2 + 15) - (Luna.fontRenderer).getStringWidth(m.getName() + m.getValue())) - 21 + m.getTransition(), y - 15, 0xFF7200ff);
				}else if (m.value == false){
					//zCore.drawRect(Luna.mc.displayWidth / 2 + 15 - (Luna.fontRenderer).getStringWidth(m.getName()), mheight - 1, sr.getScaledWidth(), mheight + zCore.getFontRenderer().FONT_HEIGHT, Integer.MAX_VALUE);
				//Original: Luna.fontRenderer.drawStringWithShadow(m.name, ((Luna.mc.displayWidth / 2 + 15) - (Luna.fontRenderer).getStringWidth(m.getName()))  - 19 +  m.getTransition(), y - 15, 0xFF7200ff);
					Luna.fontRendererArrayList.drawStringWithShadow(m.name, ((Luna.mc.displayWidth / 2 + 15) - (Luna.fontRenderer).getStringWidth(m.getName()))  - 19 +  m.getTransition(), y - 15,  RenderUtils.getRainbow(6000, -15 * y) /**getRainbow(6000, -15 * 4)*/);
				}
					y += Luna.fontRendererArrayList.getStringHeight(m.getName() + m.getValue());
				}
			ModuleManager.mods.sort(new Comparator());
			}
		
		private int Color() {
			int cxd = 0;
			cxd = (int)(cxd + rn);
	            if (cxd > 50) {
	              cxd = 0;
	            }
			Color color = new Color(Color.HSBtoRGB((float)(Minecraft.getMinecraft().thePlayer.ticksExisted / 60.0D + Math.sin(cxd / 60.0D * 1.5707963267948966D)) % 1.0F, 0.5882353F, 1.0F));
	        int col = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200).getRGB();
			return col;
	}

	
	 public static Color fade(long offset, float fade)
	  {
	    float hue = (float)(System.nanoTime() + offset) / 1.0E10F % 1.0F;
	    long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
	    Color c = new Color((int)color);
	    return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
	  }
	 
	    public static int getRainbow(int speed, int offset) {
	        float hue = (System.currentTimeMillis() + offset) % speed;
	        hue /= speed;
	        return Color.getHSBColor(hue, 1f, 1f).getRGB();
	    }
	    //Alternative to rainbow, (same thing but diff code.).
	    public static int RGBtoHEX(int r, int g, int b, int a)
	    {
	      return (a << 24) + (r << 16) + (g << 8) + b;
	    }
	    
}
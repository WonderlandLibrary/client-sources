package Squad.Modules.Other;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventRender2D;
import Squad.Utils.FontUtils;
import Squad.Utils.TimeHelper;
import Squad.Utils.Wrapper;
import Squad.base.Module;
import Squad.base.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;


public class GuiHUD {
	public static int selectedTab = 0;
	public static int selectedModule = 0;
    TimeHelper time;

	public static final int y = 10;
	public static boolean collapsed = false;
	public static int delay = 0;
	public static int mode = 1;
	public GuiHUD(){
	EventManager.register(this);	
	 this.time = new TimeHelper();
     this.time.setLastMS();
    }


	 public static FontUtils fu1 = new FontUtils("Russian", 0, 70);
	  public static FontUtils fu2 = new FontUtils("Russian", 0, 20);
	  public static FontUtils fu3 = new FontUtils("Russian", 0, 47);
	  public static FontUtils f2u24 = new FontUtils("Thruster Regular", 0, 19);
	  public static FontUtils f2u25 = new FontUtils("Thruster Regular", 0, 47);


	  public void renderImage(ResourceLocation resourceLocation, float posX, float posY, float width, float height) {
	        float f = (width + height) / 2;
	        int i = Math.round(f);
	        GL11.glEnable(GL11.GL_BLEND);
	        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
	        GL11.glBegin(GL11.GL_QUADS);
	        GL11.glTexCoord2d(0d, 0d);
	        GL11.glVertex2d((double) posX, (double) posY);
	        GL11.glTexCoord2d(0d, (double) (f / i));
	        GL11.glVertex2d((double) posX, (double) (posY + height));
	        GL11.glTexCoord2d(1d, (double) (f / i));
	        GL11.glVertex2d((double) (posX + width), (double) (posY + height));
	        GL11.glTexCoord2d(1d, 0d);
	        GL11.glVertex2d((double) (posX + width), (double) posY);
	        GL11.glEnd();
	        GL11.glDisable(GL11.GL_BLEND);
	    }
	  

		  @EventTarget
		  public void HUD(EventRender2D ev)
		  {
			  
		    Wrapper.getMC();int ping = Wrapper.getMC().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime();
		    ScaledResolution sr = new ScaledResolution(Wrapper.getMC(), Wrapper.getMC().displayWidth, 
		      Wrapper.getMC().displayHeight);
		    if(mode ==1){
		    	renderImage(new ResourceLocation("squad/SquadHUD.png"), 2, 4, 100, 50);
		    }else if(mode == 2){
		    	renderImage(new ResourceLocation("squad/china.png"), -2, -4, 1000, 500);
		    }else if(mode == 3){
		    	renderImage(new ResourceLocation("squad/Future.png"), 3, 4, 500, 250);
		    }else if(mode == 5){
		    	renderImage(new ResourceLocation("squad/New.png"), -5, -10, 1000, 500);
		    }else if(mode == 6){
		    	renderImage(new ResourceLocation("squad/Ambien.png"), -1, -4, 100, 75);
		    
		    	
			    
		    }
		    f2u24.drawStringWithShadow("FPS I " + Minecraft.getDebugFPS(), 20.0F, 
		      sr.getScaledHeight() - 21, Color.LIGHT_GRAY.getRGB());
		    f2u24.drawStringWithShadow("Ping I " + ping, 20.0F, 
		      sr.getScaledHeight() - 11, Color.LIGHT_GRAY.getRGB());
		    f2u24.drawStringWithShadow("IGN I " + Wrapper.getMC().session.getUsername(), 53.0F, 
		      sr.getScaledHeight() - 11, Color.LIGHT_GRAY.getRGB());
		    Gui.drawFullCircle(9.0D, sr.getScaledHeight() - 11, 10.5D, -1);
		    fu2.drawStringWithShadow("S", 3.0F, sr.getScaledHeight() - 26, 33554431);
		    
		    render();
		    }
		    
		  
//@EventTarget
	//public void TabGUi(EventRender2D ev){
	//	int count = 1;
	//	for (Category cat : Category.values()) {

	//		int color = 0xFF26C6DA;

		//	if (collapsed) {
				
			//	if (Category.values()[selectedTab].toString().equals(cat.name())) {
			//		delay++;
			//		color = 0xff1167a7;
			//	} else {
		  //	color = 0x99000000;
			//	}
				
				
		//	} else if (Category.values()[selectedTab].toString().equals(cat.name())) {
			//	color = 0xff1167a7;
		//	} else {

		//		color = 0x99000000;
		//	}

			//RenderHelper.drawRect(3, ((count * 10) + y)+2, 55, ((count * 10) + 12) + y, 0xFF212121);
			
		//	Gui.drawRect(0, (count * 10) + 17.0F, 60, ((count * 10) + 10) + 17.0F, color);
		//	Gui.drawRect(59, (count * 10) - 80.0F, 60, ((count * 10) + 10) + 17.0F, 0xff1167a7);
			//Gui.drawRect(0, (7 * 10) + 26.5F, 60, ((7 * 10) + 10) + 17.0F, 0xff1167a7);

		//	Gui.drawRect(3, (5 * 17) + 17, 53, ((5 * 14) + 12) +17, 0xFF37474F);
		//	Gui.drawRect(0, (5*17) + 17, 3, ((5 * 14) + 12) + 17, 0xFF6A1B9A);
		//	
			
			
		//	f2u24.drawCenteredString(cat.name(), 27, (count * 10) + (17 - 1), 0xFFFFFF);

		//	count++;
		//}

		//if (collapsed) {
			//java.util.ArrayList<Module> modules = new java.util.ArrayList<Module>();

			//for (Module m : Squad.instance.moduleManager.getModules()) {
				//if (m.getCategory() == Category.values()[selectedTab]) {
				//	modules.add(m);
				//}
			//}

					//	Collections.sort(modules, new Comparator<Module>() {
					//	public int compare(Module m1, Module m2) {
					//
					//	return Integer.valueOf(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m2.getName()))
					//			.compareTo(Integer.valueOf(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m1.getName())));
							//}

					//	});

					//	int count2 = 1;
					//for (Module cat : modules) {

					//	int color = 0xFF26C6DA;


				
					//	if (modules.get(selectedModule).getName().equals(cat.getName())) {
					//	color = 0xff1167a7;
					//	} else if (cat.toggled) {

					//	color = 0xff1167a7;

					//} else {

					////color = 0x99000000;
					////	}
				

					////Gui.drawRect(60, (10*selectedTab)+(count2 * 10) + 17,
					//		f2u24.getWidth(modules.get(0).getName()) + 75, ((10*selectedTab)+(count2 * 10) + 10) + 17, color);
					//	f2u24.drawString(cat.getName(), 60, ((10*selectedTab)+count2 * 10) + (17 -2), 0xFFFFFF);

						//count2++;
					//}
						//}

					//}

@EventTarget
	public void ArrayList(EventRender2D ev){
		   int index = 0;
		   ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		   sr.getScaledWidth();
		   sr.getScaledHeight();
		   
		   int i = 0;
		   
		   int count = 0;
	       for (Module module : Squad.instance.moduleManager.getModules()) {


	           ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().getMinecraft().displayHeight);
	           int sw = res.getScaledWidth();


	           if (module.isFading()) {
	             
	                   if (module.toggled) {
	                       if (module.getFadeAmount() != 2) {
	                           module.setFadeAmount(module.getFadeAmount() + 1);
	                           this.time.setLastMS();
	                       } else {
	                           module.setFading(false);
	                       }


	                   } else if (module.getFadeAmount() != -Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getDisplayName())) {
	                       module.setFadeAmount(module.getFadeAmount() - 1);
	                       this.time.setLastMS();
	                   } else {
	                       module.setFading(false);
	                   }
	               }
	           
	           if ((module.toggled) || (module.isFading())    ) {

	               Gui.drawRect(
	            		   				
	                                  sw - (f2u24.getWidth(module.getDisplayName()) + (module.getFadeAmount() + 4)), 10 * count,
	                                  sw - (f2u24.getWidth(module.getDisplayName()) - 200), 10 * count + 10,
	                                  0xff000000);

	           
	           	Gui.drawRect(
	           			
						sw - ((module.getFadeAmount())-0), (10 * count),
						sw - (f2u24.getWidth(module.getDisplayName()) -1000), (10 * count) + 10, 0xffFF0000);
	         		      f2u24.drawStringWithShadow(module.getDisplayName(),
	                              sw - (f2u24.getWidth(module.getDisplayName())  + module.getFadeAmount() + 3), 10 * count - 1, 0xFF0000);
	      
	                      count++;
	           }
	           
	       }
	       Collections.sort(ModuleManager.modules, new Comparator<Module>() {
               public int compare(Module m1, Module m2) {
                   return String.valueOf(de.Hero.clickgui.FontUtils.getCustonStringWidth(m2.getDisplayName())).compareTo(String.valueOf(de.Hero.clickgui.FontUtils.getCustonStringWidth(m1.getDisplayName())));
               }
           });
	       

	   }
	
	   
	   
	   private void RedCircle(ScaledResolution sr, int x, int y, int rad)
	   {
	     GL11.glPushMatrix();
	     int scale = Minecraft.getMinecraft().gameSettings.guiScale;
	     Minecraft.getMinecraft().gameSettings.guiScale = 2;
	     GL11.glEnable(3042);
	     GL11.glDisable(3553);
	     GL11.glBlendFunc(770, 771);
	     GL11.glEnable(2848);
	     double tau = 6.283185307179586D;
	     double radius = rad;
	     double fans = 55.0D;
	     GL11.glLineWidth(3.0F);
	     GL11.glColor3f(1.0F, 1.0F, 1.0F);
	     GL11.glBegin(3);
	     for (int i = 0; i <= fans; i++)
	     {
	       GL11.glColor3f(1.0F, 1.0F, 1.0F);
	       GL11.glVertex2d(
	         x + rad + radius * Math.cos(i * tau / fans), 
	         y + rad + radius * Math.sin(i * tau / fans));
	     }
	     GL11.glEnd();
	     Minecraft.getMinecraft().gameSettings.guiScale = scale;
	     GL11.glDisable(2848);
	     GlStateManager.disableBlend();
	     GL11.glDisable(3042);
	     GL11.glEnable(3553);
	     GL11.glPopMatrix();
	   }
	   
	   public void render()
	   {
	     Module localModule;
	     for (Iterator localIterator = Squad.moduleManager.getModules().iterator(); localIterator.hasNext(); localModule = (Module)localIterator.next()) {}
	     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
	     if (Squad.setmgr.getSettingByName("KeyStrokes").getValBoolean())
	     {
	       if (Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed)
	       {
	         WhiteCircle(sr, sr.getScaledWidth() - 66, sr.getScaledHeight() - 20, 9);
	         Gui.drawFullCircle(sr.getScaledWidth() - 57, sr.getScaledHeight() - 11, 9.0D, -16777216);
	         
	         f2u24.drawString("§fA", sr.getScaledWidth() - 60, sr.getScaledHeight() - 15, -1);
	       }
	       else
	       {
	         Gui.drawFullCircle(sr.getScaledWidth() - 57, sr.getScaledHeight() - 11, 9.0D, -1);
	         
	         f2u24.drawString("§0A", sr.getScaledWidth() - 60, sr.getScaledHeight() - 15, -16777216);
	       }
	       if (Minecraft.getMinecraft().gameSettings.keyBindBack.pressed)
	       {
	         WhiteCircle(sr, sr.getScaledWidth() - 46, sr.getScaledHeight() - 20, 9);
	         Gui.drawFullCircle(sr.getScaledWidth() - 37, sr.getScaledHeight() - 11, 9.0D, -16777216);
	         f2u24.drawString("§fS", sr.getScaledWidth() - 40, sr.getScaledHeight() - 15, -1);
	       }
	       else
	       {
	         Gui.drawFullCircle(sr.getScaledWidth() - 37, sr.getScaledHeight() - 11, 9.0D, -1);
	         
	         f2u24.drawString("§0S", sr.getScaledWidth() - 40, sr.getScaledHeight() - 15, -16777216);
	         RedCircle(sr, sr.getScaledWidth() - 45, sr.getScaledHeight() - 19, 8);
	       }
	       if (Minecraft.getMinecraft().gameSettings.keyBindRight.pressed)
	       {
	         WhiteCircle(sr, sr.getScaledWidth() - 26, sr.getScaledHeight() - 20, 9);
	         
	         Gui.drawFullCircle(sr.getScaledWidth() - 17, sr.getScaledHeight() - 11, 9.0D, -16777216);
	         f2u24.drawString("§fD", sr.getScaledWidth() - 20, sr.getScaledHeight() - 15, -1);
	       }
	       else
	       {
	         Gui.drawFullCircle(sr.getScaledWidth() - 17, sr.getScaledHeight() - 11, 9.0D, -1);
	         f2u24.drawString("§0D", sr.getScaledWidth() - 20, sr.getScaledHeight() - 15, -16777216);
	       }
	       if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed)
	       {
	         WhiteCircle(sr, sr.getScaledWidth() - 46, sr.getScaledHeight() - 40, 9);
	         Gui.drawFullCircle(sr.getScaledWidth() - 37, sr.getScaledHeight() - 31, 9.0D, -16777216);
	         f2u24.drawString("§cW", sr.getScaledWidth() - 40, sr.getScaledHeight() - 35, -1);
	       }
	       else
	       {
	         Gui.drawFullCircle(sr.getScaledWidth() - 37, sr.getScaledHeight() - 31, 9.0D, -1);
	         
	         f2u24.drawString("§0W", sr.getScaledWidth() - 40, sr.getScaledHeight() - 35, -1);
	       }
	     }
	     
	     
	   }
	   
	   private void WhiteCircle(ScaledResolution sr, int x, int y, int rad)
	   {
	     GL11.glPushMatrix();
	     int scale = Minecraft.getMinecraft().gameSettings.guiScale;
	     Minecraft.getMinecraft().gameSettings.guiScale = 2;
	     GL11.glEnable(3042);
	     GL11.glDisable(3553);
	     GL11.glBlendFunc(770, 771);
	     GL11.glEnable(2848);
	     double tau = 6.283185307179586D;
	     double radius = rad;
	     double fans = 55.0D;
	     GL11.glLineWidth(3.0F);
	     GL11.glColor3f(1.0F, 1.0F, 1.0F);
	     GL11.glBegin(3);
	     for (int i = 0; i <= fans; i++)
	     {
	       GL11.glColor3f(1.0F, 0.3F, 0.3F);
	       GL11.glVertex2d(
	         x + rad + radius * Math.cos(i * tau / fans), 
	         y + rad + radius * Math.sin(i * tau / fans));
	     }
	     GL11.glEnd();
	     Minecraft.getMinecraft().gameSettings.guiScale = scale;
	     GL11.glDisable(2848);
	     GlStateManager.disableBlend();
	     GL11.glDisable(3042);
	     GL11.glEnable(3553);
	     GL11.glPopMatrix();
	   }

	
}

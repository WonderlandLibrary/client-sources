package Snipeware.gui.menu;

import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import Snipeware.Client;
import Snipeware.gui.alt.gui.GuiAltManager;
import Snipeware.util.visual.RenderUtil;
import font.FontRenderer;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class ClientMainMenu extends GuiMainMenu {

    private ResourceLocation finalTexture = null;
    private ResourceLocation texture1;
    private ResourceLocation texture2;
    private ResourceLocation texture3;
    private ResourceLocation texture4;
    private static String Name = "SNIPEW";
    private static String Name2 = "ARE";
    private ArrayList<String> changelogs = new ArrayList<>();
    private double animated;
    private double animated2;
    private double animated3;
    private GLSLSandboxShader backgroundShader;
    
    
    @Override
    public void initGui() {
    	Client.getInstance().getDiscordRP().update("idling", "Main Menu");
       
    	try {
			this.backgroundShader = new GLSLSandboxShader("noise.fsh");
		} catch (IOException e) {
			throw new IllegalStateException("Failed to load backgound shader", e);
		}
    	//Client.getInstance().getDiscordRP().update("idling", "Main Menu");
        super.initGui();
        
    //   buttonList.add(new TextButton(3, xMid + width / 3, initHeight - 153, objWidth, objHeight, strLang));
      //  buttonList.add(new TextButton(2, xMid + width / 3 + 110, initHeight - 153, objWidth, objHeight, strOptions));
       
        //buttonList.add(new ExitButton(5, xMid + width / 3 + 192, initHeight - 157, 25, 25, strOptions));
    	 super.initGui();
    }
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 3:
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                break;
            case 4:
                mc.displayGuiScreen(new GuiAltManager());
                break;
            case 5:
                mc.shutdown();
                DiscordRPC.discordShutdown();
                break;
            case 6:
            	mc.displayGuiScreen(new ProxyGUI(this));
            	break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	GlStateManager.disableCull();
		this.backgroundShader.useShader(this.width, this.height, mouseX, mouseY,
				(System.currentTimeMillis() -  System.currentTimeMillis()) / 1000f);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-1f, -1f);
		GL11.glVertex2f(-1f, 1f);
		GL11.glVertex2f(1f, 1f);
		GL11.glVertex2f(1f, -1f);
		GL11.glEnd();
		GL20.glUseProgram(0);
		
    	int objWidth = 210;
        int objHeight = 183;
    	int xMid = width / 2 - objWidth / 2;
        buttonList.clear();
        final String strSSP = I18n.format("SINGLEPLAYER");
        final String strSMP = I18n.format("MULTIPLAYER");
        final String strAccounts = "ALT LOGIN";
        
        final String strLang = I18n.format("LANGUAGE");
        final String strOptions = "OPTIONS";
        final String strShutDown = "SHUTDOWN";
        final String strProxy = "Proxy";
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        
        int initHeight = sr.getScaledWidth() / 5 + 20;
        int objHeight2 = 22;
        int objWidth2 = 162;
        int xMid2 = width / 2 - objWidth2 / 2;
        
        animated3 = RenderUtil.animate(initHeight, animated3, 0.07);
     
      
        initHeight = (int) animated3;
        buttonList.add(new Button(0, xMid2 ,  (int) Math.round(animated3) + 30, objWidth2, objHeight2, strSSP));
        buttonList.add(new Button(1, xMid2 ,  (int) Math.round(animated3) + 18 - 2 + 30 + 9 ,objWidth2, objHeight2, strSMP));
        buttonList.add(new Button(4, xMid2 ,  (int) Math.round(animated3) + 35 - 3 + 30 + 18, objWidth2 , objHeight2, strAccounts));
        buttonList.add(new Button(2, xMid2 ,  (int) Math.round(animated3) + 52 - 4 + 30 + 27, objWidth2, objHeight2, strOptions));
        buttonList.add(new Button(5, xMid2 , (int) Math.round(animated3)  + 69 - 5 + 30 + 36, objWidth2, objHeight2, strShutDown));
       // buttonList.add(new Button(6, xMid2 , (int) Math.round(animated3)  + 86 - 6 + 30 + 60, objWidth2, objHeight2, strProxy));
        
        this.drawGradientRect(0, height - 150, width, height, 0, -16777216);
        
       
    
        GlStateManager.pushMatrix();
        int YRec = sr.getScaledWidth() /5;
        animated = RenderUtil.animate(YRec, animated, 0.07);
        RenderUtil.drawRect(xMid,animated + 1,objWidth, objHeight + 1, new Color(138,43,226,200).getRGB());
        
        
        RenderUtil.drawRect(xMid, animated,objWidth, objHeight, new Color(42, 42, 42, 253).getRGB());
        
     
        
        for (int i = 0; i < buttonList.size(); ++i) { 
            GuiButton g = (GuiButton) buttonList.get(i);
            g.drawButton(mc, mouseX, mouseY);
        }
    
  
    
        
        
        float YText = sr.getScaledWidth() / 5;
        animated2 = RenderUtil.animate(YText, animated2, 0.07);
    	int xMidStr = width / 2 - 187 / 2;
        
        final FontRenderer fr2 = Client.INSTANCE.getFontManager().getFont("Aquire 62", true);
        fr2.drawStringWithShadow(Name,  xMidStr - 5, (float)animated2 + 9, new Color(158,63,236).getRGB());
        fr2.drawStringWithShadow(Name2,  xMidStr - 5 + 118, (float)animated2 + 9, new Color(158,63,236).getRGB());
        final FontRenderer fr = Client.INSTANCE.getFontManager().getFont("Display 21", true);
        
 
        
        final String welcome = "Welcome back "+Minecraft.getMinecraft().getSession().getUsername();
        final String Credits = "Made by Koljan, Tear, Napoleon, HeyaGlitz";
        final String Build = "Build 1.1";
       

        fr.drawStringWithShadow(Credits, (float)(this.width - fr.getWidth(Credits)) / 110, this.height - 24, new Color(250,250,250,80).getRGB());
        
        fr.drawStringWithShadow(welcome, (float)(this.width - fr.getWidth(welcome) - 13), this.height - 24,  new Color(250,250,250, 80).getRGB());
        
   
        int index = 0;
        int AddY = 0;
        
        changelogs.add("Very big update");
        changelogs.add("Added alot of hypixel bypasses");
        changelogs.add("i forgor what i did");

        

        int color = new Color(250,250,250, 220).getRGB();
        	fr.drawString(changelogs.get(index).toString(), (float)(this.width - fr.getWidth(changelogs.get(index).toString())) / 180,  (float)(this.height / 50 + AddY),  color);
        	index++;
        	AddY += 10;
        	fr.drawString(changelogs.get(index).toString(), (float)(this.width - fr.getWidth(changelogs.get(index).toString())) / 180,  (float)(this.height / 50 + AddY),  color);
        	index++;
        	AddY += 10;
        	fr.drawString(changelogs.get(index).toString(), (float)(this.width - fr.getWidth(changelogs.get(index).toString())) / 180,  (float)(this.height / 50 + AddY),  color);
 	
    
        GlStateManager.popMatrix();
    }
	private int count(int i) {
		// TODO Auto-generated method stub
		return 0;
	}
}

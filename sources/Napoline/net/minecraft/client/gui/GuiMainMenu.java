package net.minecraft.client.gui;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import me.napoleon.napoline.GLSLSandboxShader;
import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.guis.Altmanager.GuiAltManager;
import me.napoleon.napoline.manager.CommandManager;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.utils.render.RenderUtil;
import me.napoleon.napoline.utils.render.RenderUtils;

// TODO Redo the main menu
// -HeyaGlitz, Napoleon

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {

    public static String NOTICE;
    private static int logoanimy = -10;
    private String wallpaperPath = new ResourceLocation("client/UI/bg_blur.png").getResourcePath();
    private static GLSLSandboxShader backgroundShader;
    private long initTime = System.currentTimeMillis();

    public GuiMainMenu() {
        if (Napoline.needReload) {
            // Clean Module Manager
            for (Mod mod : ModuleManager.pluginModsList.keySet()) {
                mod.setStage(false);
                ModuleManager.modList.remove(mod);
            }
            ModuleManager.pluginModsList.clear();

            // Clean Command Manager
            for (Command cmd : CommandManager.pluginCommands.keySet()) {
                CommandManager.commands.remove(cmd);
            }
            CommandManager.pluginCommands.clear();

            Napoline.pluginManager.plugins.clear();
            Napoline.pluginManager.urlCL.clear();

            // Reload
            Napoline.pluginManager.loadPlugins(true);
            Napoline.scriptManager.loadScripts();
            Napoline.needReload = false;
        }
    }

    @Override
    public void confirmClicked(boolean result, int id) {
//        if (result) {
//            mc.displayGuiScreen(new GuiAltManager());
//        } else {
//            AutoUpdate.needOpenUpdateMenu = false;
//            mc.displayGuiScreen(new GuiMainMenu());
//        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {

    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    public void initGui() {
        logoanimy = -10;
        try {
            this.backgroundShader = new GLSLSandboxShader("/shader.fsh");
        } catch (IOException e) {
            e.printStackTrace();
        }
        initTime = System.currentTimeMillis();

    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	//GlStateManager.disableCull();
//        if (AutoUpdate.needOpenUpdateMenu) {
//            GuiYesNo ask = new GuiYesNo(this, "å�‘çŽ°æ–°ç‰ˆæœ¬", "Napolineå®¢æˆ·ç«¯æ£€æµ‹åˆ°æ–°ç‰ˆæœ¬:Napoline" + AutoUpdate.webVer + "æ›´æ–°éœ€è¦�å†…æµ‹è´¦å�·ï¼Œæ˜¯å�¦æ›´æ–°ï¼Ÿ", "Update", "Back", 0);
//            mc.displayGuiScreen(ask);
//        }
        ScaledResolution sr = new ScaledResolution(mc);
        //RenderUtils.drawImage(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new ResourceLocation("client/UI/skybg.png"));

        /*this.drawDefaultBackground();
        this.backgroundShader.useShader(this.width, this.height, mouseX, mouseY,initTime / 1000f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-1f,-1f);
        GL11.glVertex2f(-1f,1f);
        GL11.glVertex2f(1f,1f);
        GL11.glVertex2f(1f,-1f);
        GL11.glEnd();

        GL20.glUseProgram(0);*/
        
        GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("custom/wallpaper2.jpg"));
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1f);
		GlStateManager.scale(width * 0.0041, height * 0.0041, 0);
		this.drawTexturedModalRect(-10, -10, 0, 0, 512 / 2, 512 / 2);
		GlStateManager.popMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

//        RenderUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(38, 38, 38, 50).getRGB());

        //RenderUtils.drawImage((int) (sr.getScaledWidth_double() / 2 - 46), (int) (sr.getScaledHeight_double() / 2 - 115)+logoanimy, 32, 32, new ResourceLocation("client/Napoline.png"), new Color(43,113,177));
        //FontLoaders.F40.drawCenteredString("Napoline", (float) sr.getScaledWidth_double() / 2+16, (float) sr.getScaledHeight_double() / 2 - 110+logoanimy,new Color(43,113,177).getRGB());
        FontLoaders.Logo.drawCenteredStringWithShadow(Napoline.CLIENT_NAME + " " + Napoline.CLIENT_Ver, (float) sr.getScaledWidth_double() / 2, (float) sr.getScaledHeight_double() / 2 - 110, new Color(255, 255, 255).getRGB());


//        RenderUtils.drawRect((int) (sr.getScaledWidth_double() / 2 - 81), (int) (sr.getScaledHeight_double() / 2 - 81) , (int) (sr.getScaledWidth_double() / 2) + 81, (int) (sr.getScaledHeight_double() / 2 + 76) , new Color(52, 52, 52).getRGB());
//        RenderUtils.drawRect((int) (sr.getScaledWidth_double() / 2 - 80), (int) (sr.getScaledHeight_double() / 2 - 80) , (int) (sr.getScaledWidth_double() / 2) + 80, (int) (sr.getScaledHeight_double() / 2 + 75) , new Color(33, 33, 33).getRGB());


        String[] S = new String[]{"Singleplayer", "Multiplayer", "Alt Manager", "Options", "Exit"};

        for (int i = 0; i < 5; i++) {
            RenderUtils.drawRect((int) (sr.getScaledWidth_double() / 2 - 75), (int) (sr.getScaledHeight_double() / 2 - 75 + i * 25 + i * 5), (int) (sr.getScaledWidth_double() / 2) + 75, (int) (sr.getScaledHeight_double() / 2 - 50 + i * 25 + i * 5), isHovered((int) (sr.getScaledWidth_double() / 2 - 75), (int) (sr.getScaledHeight_double() / 2 - 75 + i * 25 + i * 5), (int) (sr.getScaledWidth_double() / 2) + 75, (int) (sr.getScaledHeight_double() / 2 - 50 + i * 25 + i * 5), mouseX, mouseY) ? new Color(0, 0, 0, 179).getRGB() : new Color(0, 0, 0, 77).getRGB());
            FontLoaders.F20.drawCenteredStringWithShadow(S[i], (float) sr.getScaledWidth_double() / 2, (float) sr.getScaledHeight_double() / 2 - 66 + i * 30, isHovered((int) (sr.getScaledWidth_double() / 2 - 75), (int) (sr.getScaledHeight_double() / 2 - 75 + i * 25 + i * 5), (int) (sr.getScaledWidth_double() / 2) + 75, (int) (sr.getScaledHeight_double() / 2 - 50 + i * 25 + i * 5), mouseX, mouseY) ? new Color(255, 255, 255).getRGB() : new Color(255, 255, 255).getRGB());
        }

        //RenderUtils.drawImage((int) sr.getScaledWidth_double() / 2 - 45, (int) sr.getScaledHeight_double() / 2 + 20+logoanimy, 16, 16, new ResourceLocation("client/UI/account.png"), isHovered((int) sr.getScaledWidth_double() / 2 - 80, (int) sr.getScaledHeight_double() / 2 + 20, (int) sr.getScaledWidth_double() / 2 - 80 + 160, (int) sr.getScaledHeight_double() / 2 + 20 + 15, mouseX, mouseY) ? new Color(200, 200, 200) : new Color(255, 255, 255));
        //FontLoaders.F18.drawString("WallPaper", (float) sr.getScaledWidth_double() / 2 - 20, (float) sr.getScaledHeight_double() / 2 + 50+logoanimy, isHovered((int) sr.getScaledWidth_double() / 2 - 80, (int) sr.getScaledHeight_double() / 2 + 45, (int) sr.getScaledWidth_double() / 2 - 80 + 160, (int) sr.getScaledHeight_double() / 2 + 45  + 15, mouseX, mouseY) ? new Color(200, 200, 200).getRGB() : new Color(255, 255, 255).getRGB());
        FontLoaders.F16.drawString("Napoline " + Napoline.CLIENT_Ver, 10, sr.getScaledHeight() - FontLoaders.F16.getStringHeight(" ") - 8, new Color(220, 220, 220).getRGB());
        //FontLoaders.F16.drawString(NOTICE, (float) (sr.getScaledWidth_double() / 2 - FontLoaders.F16.getStringWidth(NOTICE) / 2), 10, new Color(255, 255, 255, 100).getRGB());


//        FontLoaders.F16.drawString("Development - SuperSkidder & MarShall", sr.getScaledWidth() - FontLoaders.F16.getStringWidth("Development - SuperSkidder & MarShall") - 10, sr.getScaledHeight() - FontLoaders.F16.getStringHeight(" ") - 10, new Color(43,113,177).getRGB());
        //Napoline.fontManager.Chinese16.drawStringWithShadow("ä½ å¥½ï¼�2233",5,5,new Color(0,0,0).getRGB());

    }

    public boolean isHovered(double x, double y, float x2, float y2, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2) {
            return true;
        }

        return false;
    }

    /**
     * Called when the mouse is cliced. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        ScaledResolution sr = new ScaledResolution(mc);


        for (int i = 0; i < 5; i++) {
            if (isHovered((int) (sr.getScaledWidth_double() / 2 - 75), (int) (sr.getScaledHeight_double() / 2 - 75 + i * 25 + i * 5), (int) (sr.getScaledWidth_double() / 2) + 75, (int) (sr.getScaledHeight_double() / 2 - 50 + i * 25 + i * 5), mouseX, mouseY)) {
                switch (i) {
                    case 0:
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case 1:
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case 2:
                        mc.displayGuiScreen(new GuiAltManager());
                        break;
                    case 3:
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case 4:
                        mc.shutdown();
                        break;
                    default:
                        break;

                }
            }
        }


        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {

    }
}

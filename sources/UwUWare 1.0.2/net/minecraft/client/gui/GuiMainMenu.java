

        package net.minecraft.client.gui;

        import Ascii4UwUWareClient.API.Updater.GuiOutdated;
        import Ascii4UwUWareClient.API.Updater.Update;
        import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.UI.Font.CFontRenderer;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import Ascii4UwUWareClient.UI.altmanager.GuiAltManager;
import Ascii4UwUWareClient.Util.AnimatedResourceLocation;
import Ascii4UwUWareClient.Util.MainMenuUtil;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.IOException;

        public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {

    CFontRenderer font = FontLoaders.GoogleSans20;

    private AnimatedResourceLocation bg;

    static Clip clip;
    int song = 0;
    public boolean hasLoaded;

    public GuiMainMenu(){

    }

    @Override
    public void initGui() {
        bg = new AnimatedResourceLocation("ClientAssets/BACKGROUND/MainMenu", 6, 500);

         if(Update.isNewerVersionRelease() && !(Minecraft.getMinecraft().currentScreen instanceof GuiOutdated)){
            Minecraft.getMinecraft().displayGuiScreen(new GuiOutdated());
        }

       /*if(clip == null) {
            try {
                clip = AudioSystem.getClip();
            }catch (Exception e){}
        }
        if (!clip.isRunning() && clip != null){
            if (song < 3){
                song = 0;
            }
            InputStream in = null;
            try {
                switch (song){
                    case 0:
                        in = mc.mcDefaultResourcePack.getInputStream(new ResourceLocation("ClientAssets/SOUND/BGSONG1.wav"));
                        break;
                    case 1:
                        in = mc.mcDefaultResourcePack.getInputStream(new ResourceLocation("ClientAssets/SOUND/BGSONG2.wav"));
                        break;
                    case 2:
                        in = mc.mcDefaultResourcePack.getInputStream(new ResourceLocation("ClientAssets/SOUND/BGSONG3.wav"));
                        break;
                    case 3:
                        in = mc.mcDefaultResourcePack.getInputStream(new ResourceLocation("ClientAssets/SOUND/BGSONG4.wav"));
                        break;
                }
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(in));
                clip.start();
                song++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }*/
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //clip.start();
        int Height;
        int h = Height = new ScaledResolution(this.mc).getScaledHeight();
        int Width;
        int w = Width = new ScaledResolution(this.mc).getScaledWidth();
        boolean isOverSingleplayer = mouseX > w / 2 - 100 && mouseX < w / 2 - 84 && mouseY > h / 2 + 26
                && mouseY < h / 2 + 44;
        boolean isOverMultiplayer = mouseX > w / 2 - 56 && mouseX < w / 2 - 32 && mouseY > h / 2 + 26
                && mouseY < h / 2 + 44;
        boolean isOverAltManager = mouseX > w / 2 - 10 && mouseX < w / 2 + 20 && mouseY > h / 2 + 26
                && mouseY < h / 2 + 44;
        boolean isOverSettings = mouseX > w / 2 + 46 && mouseX < w / 2 + 62 && mouseY > h / 2 + 26
                && mouseY < h / 2 + 44;
        boolean isOverExit = mouseX > w / 2 + 90 && mouseX < w / 2 + 105 && mouseY > h / 2 + 26 && mouseY < h / 2 + 44;
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        //Draw Bg
        bg.update();
        mc.getTextureManager().bindTexture(bg.getTexture());
        drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, width, height, width, height, width, height);

        //Draw Info
        MainMenuUtil.drawString("- AAC Speed", width/2 - 5, height/1, new Color(0, 224, 255, 0).getRGB());

        MainMenuUtil.drawString(Client.instance.name, width/2 - this.fontRendererObj.getStringWidth(Client.instance.name)/10, height/3, new Color(0, 224, 255, 0).getRGB());
        //Btn
        FontLoaders.NovICON44.drawString("M", 44, 20, new Color(108, 108, 108).getRGB());
        RenderUtil.R2DUtils.drawRoundedRect(w / 2 - 120, h / 2 + 16, w / 2 + 120, h / 2 + 54,
                new Color(0, 0, 0, 130).getRGB(), new Color(0, 0, 0, 130).getRGB());
        FontLoaders.NovICON44.drawString("C", w / 2 - 100, h / 2 + 26,
                isOverSingleplayer ? new Color(102, 172, 255).getRGB() : new Color(108, 108, 108).getRGB());
        FontLoaders.NovICON44.drawString("B", w / 2 - 56, h / 2 + 26,
                isOverMultiplayer ? new Color(102, 172, 255).getRGB() : new Color(108, 108, 108).getRGB());
        FontLoaders.NovICON44.drawString("A", w / 2 - 10, h / 2 + 26,
                isOverAltManager ? new Color(102, 172, 255).getRGB() : new Color(108, 108, 108).getRGB());
        FontLoaders.NovICON44.drawString("G", w / 2 + 46, h / 2 + 26,
                isOverSettings ? new Color(102, 172, 255).getRGB() : new Color(108, 108, 108).getRGB());
        FontLoaders.NovICON44.drawString("D", w / 2 + 90, h / 2 + 26,
                isOverExit ? new Color(102, 172, 255).getRGB() : new Color(108, 108, 108).getRGB());
        if (isOverSingleplayer) {
            Gui.drawRect(w / 2 - 100, h / 2 + 52, w / 2 - 84, h / 2 + 54, new Color(102, 172, 255).getRGB());
        }
        if (isOverMultiplayer) {
            Gui.drawRect(w / 2 - 56, h / 2 + 52, w / 2 - 32, h / 2 + 54, new Color(102, 172, 255).getRGB());
        }
        if (isOverAltManager) {
            Gui.drawRect(w / 2 - 10, h / 2 + 52, w / 2 + 20, h / 2 + 54, new Color(102, 172, 255).getRGB());
        }
        if (isOverSettings) {
            Gui.drawRect(w / 2 + 46, h / 2 + 52, w / 2 + 62, h / 2 + 54, new Color(102, 172, 255).getRGB());
        }
        if (isOverExit) {
            Gui.drawRect(w / 2 + 90, h / 2 + 52, w / 2 + 105, h / 2 + 54, new Color(102, 172, 255).getRGB());
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1f);
        GL11.glPopMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public int rgbToHex(int r, int g, int b) {
        return ((1 << 24) + (r << 16) + (g << 8) + b);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int Height;
        int h = Height = new ScaledResolution(this.mc).getScaledHeight();
        int Width;
        int w = Width = new ScaledResolution(this.mc).getScaledWidth();
        boolean isOverSingleplayer = mouseX > w / 2 - 100 && mouseX < w / 2 - 84 && mouseY > h / 2 + 26
                && mouseY < h / 2 + 44;
        boolean isOverMultiplayer = mouseX > w / 2 - 56 && mouseX < w / 2 - 32 && mouseY > h / 2 + 26
                && mouseY < h / 2 + 44;
        boolean isOverAltManager = mouseX > w / 2 - 10 && mouseX < w / 2 + 20 && mouseY > h / 2 + 26
                && mouseY < h / 2 + 44;
        boolean isOverSettings = mouseX > w / 2 + 46 && mouseX < w / 2 + 62 && mouseY > h / 2 + 26
                && mouseY < h / 2 + 44;
        boolean isOverExit = mouseX > w / 2 + 90 && mouseX < w / 2 + 105 && mouseY > h / 2 + 26 && mouseY < h / 2 + 44;

        if (mouseButton == 0 && isOverSingleplayer) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (mouseButton == 0 && isOverMultiplayer) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (mouseButton == 0 && isOverAltManager) {
            this.mc.displayGuiScreen(new GuiAltManager ());
        }

        if (mouseButton == 0 && isOverSettings) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (mouseButton == 0 && isOverExit) {
            this.mc.shutdown();
        }

        if (!Minecraft.func_181569_r() && !hasLoaded){
            System.err.println("UwUWare Protection >>> Nice Try Kid");
            System.exit(6969);
        }else {
            hasLoaded = true;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


            @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}


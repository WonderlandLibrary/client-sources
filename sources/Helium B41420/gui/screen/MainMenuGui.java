package rip.helium.gui.screen;

import net.minecraft.util.*;
import rip.helium.gui.screen.account.*;
import rip.helium.gui.screen.credits.*;
import rip.helium.utils.*;
import rip.helium.utils.font.*;

import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import java.awt.*;

import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.io.*;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;

public class MainMenuGui extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger;
    private final ResourceLocation logoPath;
	private int topButtonHeight;
    static ResourceLocation bg;
    
    static {
        logger = LogManager.getLogger();
        MainMenuGui.bg = new ResourceLocation("client/gui/main_menu/panorama/panorama_0.png");
    }
    
    public MainMenuGui() {
        this.logoPath = new ResourceLocation("client/gui/logo/title.png");
    }
    
    public static void drawBackground() {
        GlStateManager.pushMatrix();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.disableAlpha();
        GlStateManager.enableAlpha();
        Gui.drawRect(0, 0, MainMenuGui.width, MainMenuGui.height, new Color(164, 79, 1, 150).getRGB());
        Gui.drawRect(0, 0, MainMenuGui.width, MainMenuGui.height, ColorCreator.createRainbowFromOffset(-6000, 10));
        Gui.drawGradientRect(0, 0, MainMenuGui.width, MainMenuGui.height, -1, new Color(184, 97, 26, 120).getRGB());
        Fonts.verdana3.drawStringWithShadow("User Status: ", (float)(sr.getScaledWidth() - Fonts.verdana3.getStringWidth("User Status: Free") - 4), (float)(sr.getScaledHeight() - 9), new Color(180, 180, 180).getRGB());
        Fonts.verdanaN.drawStringWithShadow("Free", (float)(sr.getScaledWidth() - Fonts.verdanaN.getStringWidth("Free") - 1), (float)(sr.getScaledHeight() - 10), 16777215);
        Fonts.verdana3.drawStringWithShadow("Build: ", (float)(sr.getScaledWidth() - Fonts.verdana3.getStringWidth("Build: 1") - 4), (float)(sr.getScaledHeight() - 21), new Color(180, 180, 180).getRGB());
        Fonts.verdanaN.drawStringWithShadow("1", (float)(sr.getScaledWidth() - Fonts.verdanaN.getStringWidth("1") - 1), (float)(sr.getScaledHeight() - 22), 16777215);
        GlStateManager.popMatrix();
    }

    static boolean meme;

    File music = new File("C:\\Helium\\music\\main.wav");
    AudioInputStream audioInput;

    /*/ cancer  /*/
    {
        try {
            audioInput = AudioSystem.getAudioInputStream(music);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Clip clip;
    {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    boolean playing = false;

    public void playMusic() {
        try {
            //File music = new File("C:\\Helium\\music\\main.wav");
            if (music.exists()) {
                /*/AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
                Clip clip = AudioSystem.getClip();/*/
                if (!playing) {
                    clip.open(audioInput);
                    clip.start();
                    clip.loop(2);
                    playing = true;
                }
            } else {
                System.out.println("not found!");
            }
        } catch (Exception e) {

        }
    }


    
    @Override
    public void initGui() {
        //Fonts.verdana3.drawStringWithShadow("User Status: ", (float)(sr.getScaledWidth() - Fonts.verdana3.getStringWidth("User Status: Free") - 4), (float)(sr.getScaledHeight() - 9), new Color(180, 180, 180).getRGB());
        playMusic();
        final int j = MainMenuGui.height / 4 + 48;
        this.buttonList.add(new GuiButton(1, MainMenuGui.width / 2 - 100, j, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, MainMenuGui.width / 2 - 100, j + 24, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new GuiButton(999, MainMenuGui.width / 2 - 100, j + 48, 98, 20, I18n.format("Alt Login", new Object[0])));
        this.buttonList.add(new GuiButton(899, MainMenuGui.width / 2 + 2, j + 48, 98, 20, I18n.format("Credits", new Object[0])));
        this.buttonList.add(new GuiButton(0, MainMenuGui.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, MainMenuGui.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.mc.func_181537_a(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 999) {
            this.mc.displayGuiScreen(new DirectLoginGui(this));
            clip.stop();
        }
        if (button.id == 899) {
            this.mc.displayGuiScreen(new CreditsGui(this));
            clip.stop();
        }
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
            clip.stop();
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
            clip.stop();
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
            System.out.println("i clicked it");
            clip.stop();


        }
        if (button.id == 4) {
            this.mc.shutdown();
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        //Fonts.verdana3.drawStringWithShadow("User Status: ", (float)(sr.getScaledWidth() - Fonts.verdana3.getStringWidth("User Status: Free") - 4), (float)(sr.getScaledHeight() - 9), new Color(180, 180, 180).getRGB());
        Draw.drawImg(new ResourceLocation("client/Background.jpg"), 0.0, 0.0, this.width, this.height);
        GlStateManager.popMatrix();
        final int logoPositionY = this.topButtonHeight - 30;
        Draw.drawImg(new ResourceLocation("client/gui/logo/64x64.png"), this.width / 2 - 32, logoPositionY - 64, 64.0, 64.0);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }
}

package net.minecraft.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.lwjgl.Sys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import net.minecraft.util.text.ITextComponent;
import zamorozka.gui.GuiKey;
import zamorozka.main.Zamorozka;
import zamorozka.module.h;
import zamorozka.ui.AngleUtil;

public class GuiDisconnected extends GuiScreen
{
    private final String reason;
    private final ITextComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int textHeight;

    public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, ITextComponent chatComp)
    {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey);
        this.message = chatComp;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }
  

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
    	if(GuiKey.WriteKey==false) {
    		JOptionPane.showMessageDialog(null, "Заспуфь свое очко.");
    	}
  
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.textHeight = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT, this.height - 30), I18n.format("gui.toMenu")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 20, "Reconnect"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 40, "Reconnect With Random Name"));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(this.parentScreen);
                break; 
            case 1:
                try {
                    GuiMultiplayer gui = (GuiMultiplayer)this.parentScreen;
                    gui.connectToSelected();
                } catch (Exception ignored) {
                    ;
                }
                break;
            case 2:
                try {
    				String str1 = RandomStringUtils.randomAlphabetic((int) AngleUtil.randomFloat(10, 12));
    		    	mc.session = new Session(str1, "", "", "");
                    GuiMultiplayer gui = (GuiMultiplayer)this.parentScreen;
                    gui.connectToSelected();
                } catch (Exception ignored) {
                    ;
                }
                break;   
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.textHeight / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.textHeight / 2;

        if (this.multilineMessage != null)
        {
            for (String s : this.multilineMessage)
            {
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

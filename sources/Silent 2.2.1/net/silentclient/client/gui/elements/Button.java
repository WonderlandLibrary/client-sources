package net.silentclient.client.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.theme.button.DefaultButtonTheme;
import net.silentclient.client.gui.theme.button.IButtonTheme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.TimerUtils;

import java.awt.*;

public class Button extends GuiButton
{
	
	protected int animatedOpacity = 0;
	public boolean escMenu = false;
	private int fontSize;

	protected TimerUtils animateTimer = new TimerUtils();

	private IButtonTheme theme;

	public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int fontSize, boolean escMenu, IButtonTheme theme) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.escMenu = escMenu;
		this.theme = theme;
		this.fontSize = fontSize;
	}
		
	public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean escMenu, IButtonTheme theme) {
		this(buttonId, x, y, widthIn, heightIn, buttonText, 14, escMenu, theme);
	}

	public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean escMenu) {
		this(buttonId, x, y, widthIn, heightIn, buttonText, escMenu, new DefaultButtonTheme());
	}

	public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		this(buttonId, x, y, widthIn, heightIn, buttonText, false);
	}

	public Button(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }
	
	public Button(int buttonId, int x, int y, String buttonText, boolean escMenu)
    {
        this(buttonId, x, y, 200, 20, buttonText, escMenu);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
    	if(escMenu && Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Vanilla ESC Menu Layout").getValBoolean()) {
    		new GuiButton(this.id, this.xPosition, this.yPosition, this.width, this.height, this.displayString).drawButton(mc, mouseX, mouseY);
    		return;
    	}
        if (this.visible)
        {
            GlStateManager.disableBlend();
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			ColorUtils.setColor(theme.getBackgroundColor().getRGB());
			RenderUtil.drawRoundedRect((float) xPosition, (float)yPosition, width, height, (float)3,  theme.getBackgroundColor().getRGB());
            ColorUtils.setColor(theme.getHoveredBackgroundColor(this.enabled ? animatedOpacity : 0).getRGB());
            RenderUtil.drawRoundedRect((float) xPosition, (float)yPosition, width, height, (float)3,  theme.getHoveredBackgroundColor(this.enabled ? animatedOpacity : 0).getRGB());
            ColorUtils.setColor(this.enabled ? new Color(214, 213, 210, 255).getRGB() : new Color(255,255,255,50).getRGB());
            RenderUtil.drawRoundedOutline(xPosition, yPosition, width, height, 3, 1, theme.getBorderColor().getRGB());
            this.mouseDragged(mc, mouseX, mouseY);
            
            if (this.hovered && this.enabled) {
    			if (this.animatedOpacity < 75 && animateTimer.delay(30)) {
    				this.animatedOpacity += 15;
    				animateTimer.reset();
    			}
    		} else {
    			if (this.animatedOpacity != 0 && animateTimer.delay(30)) {
    				this.animatedOpacity -= 15;
    				animateTimer.reset();
    			}
    		}

			this.drawButtonContent();
        } else {
        	this.animatedOpacity = 0;
        }
    }

	protected void drawButtonContent() {
		ColorUtils.setColor(theme.getTextColor().getRGB());
		Client.getInstance().getSilentFontRenderer().drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - fontSize) / 2, fontSize, SilentFontRenderer.FontType.TITLE, width - (fontSize / 2));
	}
    
    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
    	if(!Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Silent Button Sounds").getValBoolean() && !(escMenu && Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Vanilla ESC Menu Layout").getValBoolean())) {
    		return;
    	}
    	super.playPressSound(soundHandlerIn);
    }

	public void setTheme(IButtonTheme theme) {
		this.theme = theme;
	}

	public IButtonTheme getTheme() {
		return theme;
	}
}

package net.silentclient.client.gui.notification;

import java.awt.Color;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.animation.normal.Animation;
import net.silentclient.client.gui.animation.normal.Direction;
import net.silentclient.client.gui.animation.normal.impl.DecelerateAnimation;
import net.silentclient.client.gui.font.SilentFontRenderer.FontType;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.utils.TimerUtils;

public class Notification {
	private String status;
	private String message;
	private int width;

	private Animation animation;
	private TimerUtils timer = new TimerUtils();
	    
	public void setNotification(String status, String message) {
		this.status = status;
		this.message = message;
		if(Client.getInstance().getSilentFontRenderer().getStringWidth(message, 12, FontType.TITLE) > 65) {
			this.width = Client.getInstance().getSilentFontRenderer().getStringWidth(message, 12, FontType.TITLE) + 35;
		} else {
			this.width = 100;
		}
	}

	public void show() {
		CustomFontRenderer font = new CustomFontRenderer();
		font.setRenderMode(CustomFontRenderer.RenderMode.CUSTOM);
		animation = new DecelerateAnimation(250, width);
		timer.reset();
	}

	public boolean isShown() {
		return !animation.isDone(Direction.BACKWARDS);
	}
	
	public void render() {
    	
    	ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    	
    	if(Minecraft.getMinecraft().theWorld == null) {
    		return;
    	}
    	
    	CustomFontRenderer font = new CustomFontRenderer();
		font.setRenderMode(CustomFontRenderer.RenderMode.CUSTOM);
    	
        double offset = animation.getValue();

        if(timer.delay(3000)) {
        	animation.setDirection(Direction.BACKWARDS);
        }
                
    	RenderUtil.drawRoundedRect((float) (sr.getScaledWidth() - offset), 4, (this.width - 5), 26, 3, new Color(19, 19, 19).getRGB());
        RenderUtil.drawImage(new ResourceLocation("silentclient/mods/notifications/"+ status.toLowerCase() + ".png"), (float) (sr.getScaledWidth() - offset) + 2, 4 + 2, 22, 22);
    	
    	font.drawString(WordUtils.capitalize(status), (int) (sr.getScaledWidth() - offset + 28), 4 + 3, -1, false);
        
        font.drawString(message, (int) (sr.getScaledWidth() - offset + 28), 4 + 12, -1, 12);
    }

}

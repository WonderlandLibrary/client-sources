package me.vaziak.sensation.client.impl.visual;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EventRender2D;
import me.vaziak.sensation.client.impl.visual.notifications.NotificationData;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Notifications extends Module {

	public Notifications() {
		super("Notifications", Category.VISUAL);
		timer = new TimerUtil();
		notificationData = new ArrayList<>();
	}
	public TimerUtil timer;
    public List<NotificationData> notificationData;
	private int extension;
    @Collect
    public void onRenderOverlay(EventRender2D event) {
    	
    	if (!notificationData.isEmpty()) {
    		if (notificationData.get(0) != null) {
    			ScaledResolution scalres = new ScaledResolution(mc);
    			NotificationData dat = notificationData.get(0);
    			int time = (int)System.currentTimeMillis() - (int)notificationData.get(0).currTime();

    			switch (notificationData.get(0).type()) {
					case INFO:
						if (time < 100) {
							extension = 22;

							if (Fonts.f18.getStringWidth(dat.info()) > Fonts.f20.getStringWidth(dat.getTitle())) {
								extension += Fonts.f18.getStringWidth(dat.info());
							} else {
								extension += Fonts.f20.getStringWidth(dat.getTitle());
							}

						}

						Draw.drawBorderedRectangle(scalres.getScaledWidth(), scalres.getScaledHeight() / 2 + 220, scalres.getScaledWidth() - extension,  scalres.getScaledHeight() / 2 + 250, 0, new Color(0,0,0, 120).getRGB(), new Color(0,0,0, 120).getRGB(), false);
						Draw.drawRectangle(scalres.getScaledWidth(), scalres.getScaledHeight() / 2 + 250, scalres.getScaledWidth() - extension, scalres.getScaledHeight() / 2 + 251, new Color(22, 229, 64).getRGB());
						Fonts.f20.drawStringWithShadow(dat.getTitle(), scalres.getScaledWidth() + 10 - extension, scalres.getScaledHeight() / 2 + 230, -1);
						Fonts.f18.drawStringWithShadow(dat.info(), scalres.getScaledWidth() + 10 - extension, scalres.getScaledHeight() / 2 + (230 + Fonts.f20.getStringHeight(dat.getTitle()) + 2), -1);

						break;
				case ERROR:
					break;
				case WARNING:
					break;
				default:
					break;
				}
    			if (time > 500) {
	    			if (extension > 0) {
	    				if (timer.hasPassed(10)) {
	    					extension--;
	    					timer.reset();
	    				}
	    			} else {
	    				notificationData.remove(0); 
	    			} 
    			}
    		}
    	}
    	
    }

}

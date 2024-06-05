package me.vaziak.sensation.client.impl.misc;

import java.util.ArrayList;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;

public class Spammer extends Module {
	 private StringsProperty mode = new StringsProperty("Mode", "How the spammer gets players to message", true, false, new String[]{"Message", "Public"});

	 DoubleProperty minDelay = new DoubleProperty("Min delay", "The delay at which spammer sends messages", null, 500, 1, 10000, 50, "ms");
	 DoubleProperty maxDelay = new DoubleProperty("Max delay", "The delay at which spammer sends messages", null, 500, 1, 10000, 50, "ms");
	
	 private TimerUtil timer;

	 private long delay;
	
	 private ArrayList<String> list = new ArrayList<>(); // useless to have when spammer is init.

	private int index;

	private String[] messages = new String[] {
			"Sensation Client v2.0 by Vaziak",
			"Purchase Sensation on https://azuma.club"
	};

    public Spammer() {
        super("Spammer", Category.MISC);
        registerValue(minDelay, maxDelay);
        timer = new TimerUtil();
    }

    public void onEnable() {
    	index = 0;
    	list.clear();
    	this.delay = 0;
    }
    
    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent event) {
    	if (!event.isPre())
    		return;

    	int delay = (int) Math.round(MathUtils.getRandomInRange(minDelay.getValue(), maxDelay.getValue())); // Returns a int delay

    	GuiPlayerTabOverlay.field_175252_a.sortedCopy(Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap()).forEach(info -> {
 
    		if (info.getGameProfile() != null && info.getDisplayName() != null && info.getDisplayName().getFormattedText() != null) {
    			if (!list.contains(info.getDisplayName().getUnformattedText())) {
    				list.add(info.getDisplayName().getUnformattedText());
    			}
    		}
    	});

	    if (!list.isEmpty()) { 
    		if (index > list.size() || index == list.size()) {
    			index = 0;
    			list.clear();
    		}

	    	if (index < list.size()) {
	    		if (System.currentTimeMillis() - this.delay >= delay) {

	    			if (mode.getValue().get("Message")) {

					}
					mc.thePlayer.sendChatMessage("/msg " + list.get(index) + " ArE yOu DePrEsSeD? iTs OkAy");
//					} else if (mode.getValue().get("Public")) {
//						mc.thePlayer.sendChatMessage(list.get(index) + ", https://www.youtube.com/watch?v=UuGpg_B3Tg4 ");
//					}

	    			index += 1;
	    			this.delay = System.currentTimeMillis();
	    		}
	    	}
	    }
    }
}
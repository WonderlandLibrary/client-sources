package me.vaziak.sensation.client.impl.visual;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EventRender2D;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;

public class Overlay extends Module {
    public Overlay() {
        super("Overlay", Category.VISUAL);
    } 
    
    @Collect
    public void onRenderHud(EventRender2D e) {
    	
    }
}
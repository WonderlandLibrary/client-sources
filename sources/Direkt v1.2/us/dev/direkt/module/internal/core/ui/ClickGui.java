package us.dev.direkt.module.internal.core.ui;


import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.render.EventRender2D;
import us.dev.direkt.event.internal.events.game.world.EventLoadWorld;
import us.dev.direkt.event.internal.events.system.input.EventKeyInput;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.cursor.cursors.QueuedTabCursor;
import us.dev.direkt.gui.tab.handling.Tabs;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.ModProperty;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.Gui;

/**
 * 
 * @author BFCE
 *
 */

@ModData(label = "ClickGui", category = ModCategory.CORE)
public  class ClickGui extends Module {
   
	int posX = 300;
	int posY = 500;
	
    @Listener
    protected Link<EventRender2D> onRender2D = new Link<>(event -> {
    	//posX = 0;
/*    	Gui.drawRect(posX, posY/2, posX+100, posY+100/2, 0x99000000);
    	if(Mouse.isButtonDown(0) && this.checkCollision(posX, posY/2)) {
    		System.out.println("Asd");
    		posX += Mouse.getDX()/2;
    	}*/
    	//Gui.drawRect(Mouse.getX()/2 ,(Wrapper.getMinecraft().displayHeight - Mouse.getY())/2, (Mouse.getX()/2) + 100, (Wrapper.getMinecraft().displayHeight - Mouse.getY())/2 + 100, 0x99000000);
    }, Link.VERY_LOW_PRIORITY + 10);

    private boolean checkCollision(int x, int y) {
    	if(Mouse.getX()/2 >= x && Mouse.getX()/2 <= x+100) {
    		return true;
    	}
		return false;
    }
   
}






package me.vaziak.sensation.client.api.gui.ingame.HUD.element.impl;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.client.api.event.EventSystem;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.KeyPressEvent;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;

import java.awt.*;
import java.util.ArrayList;

public class TabGui extends Element {
    public TabGui() {
        super("TabGui", Quadrant.TOP_LEFT, 2, 30); 
        closed = true;
        selected = -1;
		EventSystem.hook(this);
    }

    private int selected = 0;
	private int animation = 0;
	private float visibility;
	private long lastInteraction;
	private boolean closed;
	private boolean animating;
	private boolean highschooldropout;
	private Category selectedCategory;
	private ArrayList<Module> modules = new ArrayList();

    @Override
    public void drawElement(boolean editor) {
        this.editX = positionX;
        this.editY = positionY;
        this.width = 70;
        /*if (closed) {
        	if (height < 40) {
        		if (visibility > 20) visibility--;
        	}
        	if (height > 0 ) {
        		height -= .25;
        		animating = true;
        	} else {
        		animating = false;
        	}
        } else {
        	if (height < 70) {
        		height += .25;
        		animating = true;
        	} else {
        		animating = false;
        	}
        }
    	double tabheight = height < 30 ? 0 : height - 50;
    	
        double y = positionY; 
        double modY = y - tabheight;
        if (visibility > 0) {
        	if (System.currentTimeMillis() - lastInteraction >= 1500) {
            	if (Minecraft.getMinecraft().thePlayer.ticksExisted % 2 == 0 ) visibility-= .25F / 2;
        	}
        }
        int vb = (int) visibility;
        for (int i = 0; i < Category.values().length; i++) { 
        	if (height > 30 && visibility > 4) {
        		Draw.drawRectangle(positionX, y, positionX + 70, y - (tabheight), new Color(65, 65, 65, vb).getRGB());

            	if (selected == i) {
                	selectedCategory = Category.values()[selected];
            		if (Minecraft.getMinecraft().thePlayer.ticksExisted % 2 == 0) {
            			if (animation > 0) animation -= .00005;
            			if (animation < 0) animation += .0005;
            		}
            		Draw.drawRectangle(positionX, y - animation, positionX + 70, y - (tabheight) - animation, new Color(255, 71, 71, vb).getRGB());
            	}
        	}
        	*//*Fuck using lombok for 1 cheatcategory that caused errors I am not spending time to fix*//*
                
        	String name = Category.values()[i].toString(); 
        	name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        	if (visibility > 4 && height > 30 && !animating) {
        		if (selected == i) {
        			Fonts.f18.drawStringWithShadow(name, (float) positionX + (selected == i ? 3.5f : 2.5f), (float) y - 10, new Color(255,255,250, vb).getRGB());
        		} else {
        			Fonts.f16.drawStringWithShadow(name, (float) positionX + (selected == i ? 3.5f : 2.5f), (float) y - 10, new Color(255,255,230, vb).getRGB());
        		}
                for (Module cheat : Sensation.instance.cheatManager.getCheatRegistry().values()) {
                	if (cheat.getCategory().equals(selectedCategory)) {
                		if (highschooldropout) {
                			if (!modules.contains(cheat)) {
                				modules.add(cheat);
                			}
                   			for (int damn = 0; damn < modules.size(); damn++) {
                   				
                				String moduleName = modules.get(damn).getId();
                				 
                				Fonts.f16.drawStringWithShadow(moduleName, (float) positionX + (width), (float) y + modY, new Color(255,255,230, vb).getRGB());
                				
                				
                				modY += 20;   
                			}

                    	} else if (!modules.isEmpty()) {
                    		modules.clear();
                    	}
                	}
                }
        	}
        	
        	y += tabheight;
        } */
    }	

    @Collect
    public void onKeyPress(KeyPressEvent event) {
    	
        switch (event.getKeyCode()) {
        case Keyboard.KEY_RIGHT:
        	if (!highschooldropout) {
        		highschooldropout = true;
        		visibility = 250;
        	} else {
        		modules.clear();
        	}
    	break;
        case Keyboard.KEY_LEFT:
        	if (highschooldropout) {
        		highschooldropout = false;
        		visibility = 250;
        	} else {
        		modules.clear();
        	}
        	
    	break;
            case Keyboard.KEY_DOWN:
            	lastInteraction = System.currentTimeMillis();
                if (selected < 4) {
                	if (selected == -1) {
                		closed = false;
                	}
                	visibility = 250;
                    animation = 10;
                    selected++;
                    modules.clear();
                    if (highschooldropout) {
                		highschooldropout = false;
                		visibility = 250;
                	}
                }
                break;
            case Keyboard.KEY_UP:
            	lastInteraction = System.currentTimeMillis();
            	if (selected >= 0) {
            		modules.clear();
            		if (highschooldropout) {
                		highschooldropout = false;
                		visibility = 250;
                	}
	            	visibility = 250;
	                animation = -10;
	                selected--;
            	} else {
            		selected = -1;
            		closed = true;
            	}
                break;
        }
    }
}

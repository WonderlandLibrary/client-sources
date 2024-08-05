package fr.dog.ui.clickgui.test.impl;

import fr.dog.module.Module;
import fr.dog.util.input.MouseUtil;
import lombok.Getter;

public class MainModule {

    //Module
    private final Module m;
    private boolean expanded;

    //Values
    private float x, y;
    private final float width;
    @Getter
    private float height;

    public MainModule(final Module m, final float width, final float height) {
        this.m = m;
        this.width = width;
        this.height = height;
    }

    public void draw(final float x, final float y, final int mouseX, final int mouseY){
        this.x = x;
        this.y = y;


    }

    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton){
        final boolean hovered = MouseUtil.isHovering(mouseX,mouseY,x,y,width,height);

        switch (mouseButton){
            case 0 -> {
                if(hovered) m.toggle();
            }

            case 1 -> {
                if(hovered) expanded = !expanded;
            }
        }
    }

}

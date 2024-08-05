package fr.dog.ui.clickgui.test.impl.buttons;

import fr.dog.module.ModuleCategory;
import fr.dog.util.InstanceAccess;
import fr.dog.util.input.MouseUtil;
import fr.dog.util.render.RenderUtil;

import java.awt.*;

public class CategoryButton implements InstanceAccess {

    private final ModuleCategory cat;
    private boolean hovered;
    private final Runnable runnable;
    private final float size;
    public CategoryButton(ModuleCategory cat, Runnable runnable){
        this.runnable = runnable;
        this.cat = cat;
        this.size = 50;
    }

    public final void draw(final float x, final float y, final int mouseX, final int mouseY) {
        hovered = MouseUtil.isHovering(mouseX,mouseY,x,y,size,size);

        RenderUtil.drawRoundedRect(x, y, size, size,size/2.25f, new Color(0,0,0));
    }

    public final void mouseClicked(final int mouseX, final int mouseY) {
        if(hovered) runnable.run();
    }
}

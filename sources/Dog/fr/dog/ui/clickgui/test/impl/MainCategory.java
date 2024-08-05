package fr.dog.ui.clickgui.test.impl;

import fr.dog.Dog;
import fr.dog.module.ModuleCategory;
import fr.dog.util.input.MouseUtil;
import fr.dog.util.input.ScrollUtil;

import java.util.ArrayList;

public class MainCategory {
    //i
    private final ModuleCategory category;
    private final ArrayList<MainModule> modules = new ArrayList<>();
    private final ScrollUtil scrollUtil = new ScrollUtil(0.98f, 50);

    //Values
    private float x, y;
    private final float width, height;
    private int oldMouseX, oldMouseY;
    private boolean dragged, hovered;
    private float scroll = 0;
    public MainCategory(final ModuleCategory category, final float x, final float y){
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = 140;
        this.height = 238;

        addModule(category);
    }

    public void draw(final int mouseX, final int mouseY){
        final float titleHeight = 30;
        hovered = MouseUtil.isHovering(mouseX,mouseY,x,y,width,height);

        scrollUtil.setActive(MouseUtil.isHovering(mouseX,mouseY,x,y,width,height));
        scroll += scrollUtil.getScroll();

        if(dragged) drag(mouseX, mouseY);


    }

    public void drawGlow(){

    }

    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton){
        this.oldMouseX = (int) (mouseX - x);
        this.oldMouseY = (int) (mouseY - y);

        switch (mouseButton) {
            case 0 -> {

            }

            case 1 -> {
                //
            }
        }


    }

    public void mouseReleased(final int mouseButton){
        if(mouseButton == 0) dragged = false;
    }

    private void drag(int mouseX, int mouseY) {
        this.x = mouseX - oldMouseX;
        this.y = mouseY - oldMouseY;
    }

    private void addModule(final ModuleCategory cat){
        Dog.getInstance().getModuleManager().getModulesFromCategory(cat).forEach(module -> this.modules.add(new MainModule(module, width,30)));
    }

    private void stencilShape(final float titleHeight){
        //
    }
}

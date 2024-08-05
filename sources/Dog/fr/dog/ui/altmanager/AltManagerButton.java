package fr.dog.ui.altmanager;

import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;

import java.awt.*;

public class AltManagerButton{
    private int x,y,w,h;
    private String title;
    private Runnable runnable;
    public AltManagerButton(String title, Runnable runnable){
        this.title = title;
        this.runnable = runnable;
    }

    public void onDraw(int x, int y, int w, int h){
        RenderUtil.drawRoundedRect(x,y,w,h,2, new Color(80,80,80));
        int fontSize = 24;
        Fonts.getSanFrancisco(fontSize).drawString(title, x + 10, y + 4, Color.WHITE.getRGB());
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
    }
    public void onClick(int mouseX, int mouseY){
        if(isHovered(mouseX, mouseY)){
            this.runnable.run();
        }

    }

    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h + 1;
    }

}

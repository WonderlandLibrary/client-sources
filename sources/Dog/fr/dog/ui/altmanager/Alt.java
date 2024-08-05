package fr.dog.ui.altmanager;

import fr.dog.component.impl.misc.AltsComponent;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.awt.*;

public class Alt{
    @Getter
    private Session session;
    public Alt(Session session){
        this.session = session;
    }


    private int x,y;

    public void onDraw(int x, int y){
        RenderUtil.drawRect(x,y,100, 20,new Color(60,60,60));
        Fonts.getSanFrancisco(20).drawString(this.session.getUsername(), x + 2, y + 2, Color.WHITE.getRGB());
        Fonts.getSanFrancisco(9).drawString(this.session.getPlayerID(), x + 2, y + 12, Color.WHITE.getRGB());
        this.x = x;
        this.y = y;
    }


    public void onMouseClick(int mouseX, int mouseY, int mouseButton){
        if(isHovered(mouseX, mouseY)){
            if(mouseButton == 0){
                Minecraft.getMinecraft().setSession(this.session);
            }else if(mouseButton == 1){
                AltsComponent.removeAlt(this);
            }


        }
    }
    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x + 100 && mouseY > y && mouseY < y + 20 + 1;
    }

}

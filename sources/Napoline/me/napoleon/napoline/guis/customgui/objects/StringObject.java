package me.napoleon.napoline.guis.customgui.objects;

import net.minecraft.client.gui.Gui;

import java.awt.*;

import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.guis.customgui.GuiObject;

public class StringObject extends GuiObject {
    public String content = "";
    public int red;
    public int green;
    public int blue;


    public StringObject(String name , String content, int x, int y, int red, int green, int blue) {
        super(name,x,y);
        this.content = content;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }


    @Override
    public void drawObject() {
        FontLoaders.F16.drawStringWithShadow(content, super.x, super.y, new Color(red, green, blue).getRGB());
        super.width = FontLoaders.F16.getStringWidth(content);
        super.height = FontLoaders.F16.getStringHeight(content);

    }

}

package me.superskidder.lune.guis.clickgui;

import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.guis.utils.InputBox;
import me.superskidder.lune.utils.render.RenderUtils;
import me.superskidder.lune.utils.irc.IRCMessage;

import java.awt.*;
import java.util.ArrayList;

public class IRCgui {
    public static ArrayList<IRCMessage> msgs = new ArrayList<>();
    public static InputBox inputbox;

    public static void drawIRC(int windowX, int windowY, int windowWeight, int windowHeight) {
        inputbox.xPosition = windowX + 220;
        inputbox.yPosition = windowY + windowHeight-20;

        inputbox.drawTextBox();
        inputbox.setMaxStringLength(20);
        if (inputbox.getText().isEmpty()) {
            FontLoaders.F18.drawString("Input...", windowX + 225, windowY + windowHeight-16, new Color(155, 155, 155).getRGB());
        }

        if(msgs.size()>=16){
            msgs.remove(0);
        }

        int msgy = 30;
        for (IRCMessage i : msgs) {
            int namewidth = FontLoaders.F16.getStringWidth(i.author + "   ");
            int textwidth = FontLoaders.F16.getStringWidth(i.text);
            RenderUtils.drawRoundedRect(windowX + namewidth +210, windowY + msgy ,windowX+namewidth+8+textwidth+2+210,windowY+msgy+10,i.color.getRGB(),new Color(0,0,0,0).getRGB());

            //drawRoundRect(windowX + namewidth + 1+210, windowY + msgy+1 ,windowX+namewidth+8+textwidth+1+210,windowY+msgy+9,new Color(255,255,255).getRGB());
            FontLoaders.F16.drawString(i.author+":",windowX + 210,windowY+msgy+2,i.color.getRGB());

            FontLoaders.F16.drawString(i.text,windowX + namewidth + 212,windowY+msgy+2,i.color.getRGB());
            msgy+=15;
        }

    }
}

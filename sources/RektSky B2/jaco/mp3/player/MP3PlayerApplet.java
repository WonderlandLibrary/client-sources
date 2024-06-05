package jaco.mp3.player;

import javax.swing.*;
import jaco.mp3.player.plaf.*;
import java.net.*;
import java.awt.*;

public class MP3PlayerApplet extends JApplet
{
    @Override
    public void init() {
        try {
            try {
                this.getContentPane().setBackground(Color.decode(this.getParameter("background")));
            }
            catch (Exception ex2) {}
            if ("true".equals(this.getParameter("compact"))) {
                MP3Player.setDefaultUI(MP3PlayerUICompact.class);
            }
            final MP3Player mp3Player;
            (mp3Player = new MP3Player()).setRepeat(true);
            String[] split;
            for (int length = (split = this.getParameter("playlist").split(",")).length, i = 0; i < length; ++i) {
                mp3Player.addToPlayList(new URL(this.getCodeBase() + split[i].trim()));
            }
            this.getContentPane().add(mp3Player);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

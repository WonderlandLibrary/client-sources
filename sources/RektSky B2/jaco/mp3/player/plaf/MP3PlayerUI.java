package jaco.mp3.player.plaf;

import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.plaf.*;
import jaco.mp3.player.*;
import jaco.a.*;
import java.awt.event.*;
import java.awt.*;

public class MP3PlayerUI extends BasicPanelUI
{
    private JButton a;
    private JButton b;
    private JButton c;
    private JButton d;
    private JButton e;
    
    public static ComponentUI createUI(final JComponent component) {
        return new MP3PlayerUI();
    }
    
    @Override
    public final void installUI(final JComponent component) {
        super.installUI(component);
        this.a((MP3Player)component);
    }
    
    @Override
    public final void uninstallUI(final JComponent component) {
        super.uninstallUI(component);
        final MP3Player mp3Player;
        (mp3Player = (MP3Player)component).removeAll();
        mp3Player.removeAllMP3PlayerListeners();
    }
    
    protected void a(final MP3Player mp3Player) {
        mp3Player.setOpaque(false);
        this.a = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerPlay.png")));
        this.b = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerPause.png")));
        this.c = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerStop.png")));
        this.d = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerSkipBackward.png")));
        this.e = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerSkipForward.png")));
        final jaco.mp3.player.plaf.b b = new jaco.mp3.player.plaf.b(this, mp3Player);
        this.a.addActionListener(b);
        this.b.addActionListener(b);
        this.c.addActionListener(b);
        this.d.addActionListener(b);
        this.e.addActionListener(b);
        mp3Player.setLayout(new FlowLayout(1, 1, 1));
        mp3Player.add(this.a);
        mp3Player.add(this.b);
        mp3Player.add(this.c);
        mp3Player.add(this.d);
        mp3Player.add(this.e);
    }
}

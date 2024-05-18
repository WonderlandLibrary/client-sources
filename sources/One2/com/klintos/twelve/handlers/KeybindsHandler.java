// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.handlers;

import java.util.Iterator;
import com.klintos.twelve.handlers.notifications.Notification;
import jaco.mp3.player.MP3Player;
import com.klintos.twelve.mod.Mod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.gui.newclick.ClickGui;

import org.lwjgl.input.Keyboard;

public class KeybindsHandler
{
    public int mPause;
    public int mNext;
    public int mPrev;
    
    public KeybindsHandler() {
        this.mPause = 14;
        this.mNext = 13;
        this.mPrev = 12;
    }
    
    public void onKeyPress() {
        final int keyPressed = Keyboard.getEventKey();
        if (keyPressed == 208) {
            Twelve.getInstance();
            Twelve.getInstance().getTabGui().down();
        }
        if (keyPressed == 200) {
            Twelve.getInstance();
            Twelve.getInstance().getTabGui().up();
        }
        if (keyPressed == 203) {
            Twelve.getInstance();
            Twelve.getInstance().getTabGui().left();
        }
        if (keyPressed == 205) {
            Twelve.getInstance();
            Twelve.getInstance().getTabGui().right();
        }
        if (keyPressed == 28) {
            Twelve.getInstance();
            Twelve.getInstance().getTabGui().enter();
        }
        Twelve.getInstance();
        if (Twelve.getMinecraft().inGameHasFocus) {
            if (keyPressed == 54) {
                final Minecraft minecraft = Minecraft.getMinecraft();
                Twelve.getInstance();
// Mode 1
                minecraft.displayGuiScreen(Twelve.getInstance().getClickGui());
// Mode 2                
//                minecraft.displayGuiScreen(Twelve.getInstance().getClickGui2());
            }
            if (keyPressed == 210 && Twelve.getInstance().ghost) {
                Twelve.getInstance();
                Twelve.getInstance().unGhostClient();
            }
            Twelve.getInstance();
            for (final Mod module : Twelve.getInstance().getModHandler().getMods()) {
                if (keyPressed == module.getModKeybind()) {
                    module.onToggle();
                }
            }
            if (keyPressed == this.mNext && MP3Player.playlist.size() > 0) {
                Twelve.getInstance().getMP3Player().skipForward();
                final String name = Twelve.getInstance().getMP3Player().getCurrentTitleAndArtist();
                Twelve.getInstance().getNotificationHandler().addNotification(new Notification("Skipped forwards, now playing " + name + ".", -43691));
                Twelve.getInstance().getMP3Player();
                MP3Player.scroll = 0.0f;
            }
            else if (keyPressed == this.mPrev && MP3Player.playlist.size() > 0) {
                Twelve.getInstance().getMP3Player().skipBackward();
                final String name = Twelve.getInstance().getMP3Player().getCurrentTitleAndArtist();
                Twelve.getInstance().getNotificationHandler().addNotification(new Notification("Skipped backwards, now playing " + name + ".", -43691));
                Twelve.getInstance().getMP3Player();
                MP3Player.scroll = 0.0f;
            }
            else if (keyPressed == this.mPause && MP3Player.playlist.size() > 0) {
                final String name = Twelve.getInstance().getMP3Player().getCurrentTitleAndArtist();
                if (Twelve.getInstance().getMP3Player().isPaused()) {
                    Twelve.getInstance().getMP3Player().play();
                    Twelve.getInstance().getNotificationHandler().addNotification(new Notification("Resumed playing " + name + ".", -43691));
                }
                else if (Twelve.getInstance().getMP3Player().isStopped()) {
                    Twelve.getInstance().getMP3Player().play();
                    Twelve.getInstance().getNotificationHandler().addNotification(new Notification("Started playing " + name + ".", -43691));
                }
                else {
                    Twelve.getInstance().getMP3Player().pause();
                    Twelve.getInstance().getNotificationHandler().addNotification(new Notification("Paused " + name + ".", -43691));
                }
            }
            else if (MP3Player.playlist.size() <= 0 && (keyPressed == 13 || keyPressed == 12 || keyPressed == 14)) {
                Twelve.getInstance().getNotificationHandler().addNotification(new Notification("No music loaded, do .music reload to load music.", -43691));
            }
        }
    }
}

package tk.rektsky.Module.RektSky;

import tk.rektsky.Module.*;
import java.net.*;
import jaco.mp3.player.*;
import java.util.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.client.gui.*;

public class Sound extends Module
{
    private static final URL MENU;
    private static final URL ENABLE;
    private static final URL DISABLE;
    public static MP3Player menuPlayer;
    public static ArrayList<MP3Player> currentPlayers;
    
    public Sound() {
        super("Sound", "Sigma Time!", Category.REKTSKY);
        this.toggle();
    }
    
    public static void playEnableSound() {
        final MP3Player player = new MP3Player(new URL[] { Sound.ENABLE });
        player.play();
        player.setRepeat(false);
        Sound.currentPlayers.add(player);
    }
    
    public static void playDisableSound() {
        final MP3Player player = new MP3Player(new URL[] { Sound.DISABLE });
        player.play();
        player.setRepeat(false);
        Sound.currentPlayers.add(player);
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof ModuleTogglePreEvent) {
            if (this.mc.theWorld == null || this.mc.thePlayer == null) {
                return;
            }
            if (((ModuleTogglePreEvent)event).isEnabled()) {
                playEnableSound();
            }
            else {
                playDisableSound();
            }
        }
        if (event instanceof ClientTickEvent) {
            if (this.mc.theWorld != null || !GuiMainMenu.rendered) {
                Sound.menuPlayer.stop();
            }
            else {
                if (Sound.menuPlayer.isStopped() && GuiMainMenu.rendered) {
                    Sound.menuPlayer.play();
                }
                Sound.menuPlayer.setRepeat(true);
                this.mc.mcMusicTicker.currentMusic = null;
            }
        }
    }
    
    static {
        MENU = Sound.class.getClassLoader().getResource("assets/minecraft/rektsky/sound/menu.mp3");
        ENABLE = Sound.class.getClassLoader().getResource("assets/minecraft/rektsky/sound/enable.mp3");
        DISABLE = Sound.class.getClassLoader().getResource("assets/minecraft/rektsky/sound/disable.mp3");
        Sound.menuPlayer = new MP3Player(new URL[] { Sound.MENU });
        Sound.currentPlayers = new ArrayList<MP3Player>();
    }
}

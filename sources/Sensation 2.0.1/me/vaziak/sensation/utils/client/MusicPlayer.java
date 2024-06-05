package me.vaziak.sensation.utils.client;
 
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import java.io.InputStream;
import java.util.Objects;

/**
 * Created by FlyCode on 09/10/2019 Package cc.flycode.stitch.util
 */
public class MusicPlayer {
    private Player player;
    private Thread thread; 
    
    public boolean started;
    
    public void stop() { 
    	if (thread != null) {

        	thread.interrupt();
        	thread = null; 
    	}
    	if (player != null) {

        	player.close();
        	player = null;  
    	}
    }

    public void setVolume(double vol) {
        try {
            Mixer.Info[] infos = AudioSystem.getMixerInfo();
            for (Mixer.Info info : infos) {
                Mixer mixer = AudioSystem.getMixer(info);
                if (mixer.isLineSupported(Port.Info.SPEAKER)) {
                    Port port = (Port) mixer.getLine(Port.Info.SPEAKER);
                    port.open();
                    if (port.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl volume = (FloatControl) port.getControl(FloatControl.Type.VOLUME);
                        volume.setValue((float) (vol / 100));
                    }
                    port.close();
                }
            }
        } catch (Exception e) {
        }
    }

    public void start() {
        Objects.requireNonNull(player);
        thread = new Thread(() -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void setStream(InputStream inputStream) {
        try {
            player = new Player(inputStream);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
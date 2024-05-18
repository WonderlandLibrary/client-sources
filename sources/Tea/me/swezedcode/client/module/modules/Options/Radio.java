package me.swezedcode.client.module.modules.Options;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.ModuleUtils;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;

public class Radio extends Module {

	public Radio() {
		super("Radio", Keyboard.KEY_NONE, 0xFFFFFFFF, ModCategory.Options);
	}

	@EventListener
	public void onStart(EventPreMotionUpdates event) {
		if (!ModuleUtils.getMod(Music.class).isToggled()) {
			try {
				this.openStream("http://radiobox.se/NRJ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onEnable() {
		
	}

	public static void aplay(String url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	    Clip c = AudioSystem.getClip();
	    AudioInputStream a = AudioSystem.getAudioInputStream(openStream(url));
	    c = AudioSystem.getClip();
	    c.open(a);
	    c.start();
	}
	public static InputStream openStream(String uri) throws IOException {
	    final URL url = new URL(uri);
	    final URLConnection con = url.openConnection();
	    con.setRequestProperty("User-Agent", "My Client");
	    return con.getInputStream();
	}
	
}

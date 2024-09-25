package none.music;

import java.awt.Color;
import java.awt.im.InputContext;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.logging.log4j.core.net.Advertiser;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import javazoom.jl.player.advanced.jlap;
import javazoom.jl.player.advanced.jlap.InfoListener;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import none.Client;
import none.module.modules.render.ClientColor;
import none.module.modules.render.MusicPlayer;
import none.utils.XYGui;
import none.utils.render.Colors;
import paulscode.sound.SoundSystem;

public class MusicScreen extends GuiScreen{
	
	public ArrayList<XYGui> box = new ArrayList<>();
	public AdvancedPlayer player;
	public PlaybackListener listener;
	
	public MusicScreen() {
		
	}
	
	@Override
	public void initGui() {
		box.clear();
		box.add(new XYGui("Play", 100, 100, 135, 125, mc.fontRendererObj));
		box.add(new XYGui("Stop", 145, 100, 180, 125, mc.fontRendererObj));
		listener = new PlaybackListener() {
			@Override
			public void playbackStarted(PlaybackEvent evt) {
				Client.Show("Music", "Started", 3);
				super.playbackStarted(evt);
			}
			
			@Override
			public void playbackFinished(PlaybackEvent evt) {
				Client.Show("Music", "Stopped", 3);
				player = null;
				super.playbackFinished(evt);
			}
		};
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int renderColor = ClientColor.getColor();
		if (ClientColor.rainbow.getObject()) {
			renderColor = ClientColor.rainbow(10000);
		}
		ScaledResolution res = new ScaledResolution(mc);
		Gui.drawOutineRect(30, 30, res.getScaledWidth() - 30, res.getScaledHeight() - 30, 5, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 200), renderColor);
		for (XYGui gui : box) {
			gui.drawButton(mouseX, mouseY, partialTicks);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0) {
			for (XYGui gui : box) {
				if (gui.onClicked(mouseX, mouseY)) {
					if (gui.getName().equalsIgnoreCase("Play")) {
						Play();
					}else if (gui.getName().equalsIgnoreCase("Stop")) {
						Stop();
					}
				}
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
	}
	
	public void Play() {
		System.out.println("Play");
		if (player == null && MusicPlayer.path.getObject().equalsIgnoreCase(MusicPlayer.path.getStartValue())) {
			try {
				InputStream is = new BufferedInputStream(new FileInputStream(new File(MusicPlayer.path.getObject())));
				player = new AdvancedPlayer(is);
				player.setPlayBackListener(listener);
				new Thread()
			    {
			      public void run()
			      {
			        try
			        {
			          player.play(0, Integer.MAX_VALUE);
			        }
			        catch (Exception e)
			        {
			          throw new RuntimeException(e.getMessage());
			        }
			      }
			    }.start();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			Client.Show("Music", "Music are Playing", 3);
		}
		return;
	}
	
	public void Stop() {
		System.out.println("Stop");
		try {
			if (player == null) {
				Client.Show("Music", "No Music Playing", 3);
				return;
			}else {
				player.stop();
				player = null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			Client.Show("Music", e.getMessage(), 3);
			return;
		}
		return;
	}
}

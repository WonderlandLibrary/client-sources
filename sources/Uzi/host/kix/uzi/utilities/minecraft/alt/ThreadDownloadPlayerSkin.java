package host.kix.uzi.utilities.minecraft.alt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ThreadDownloadPlayerSkin extends Thread {

	private static ThreadDownloadPlayerSkin instance;
	private static final String SKIN_LOCATION = "http://skins.minecraft.net/MinecraftSkins/%s.png";

	private FlexibleArray<String> queuedUsernames;
	private Map<String, BufferedImage> skinImages = new HashMap<String, BufferedImage>();

	private ThreadDownloadPlayerSkin() {
		this.queuedUsernames = new FlexibleArray<String>();
	}

	public static ThreadDownloadPlayerSkin getInstance() {
		if (instance == null) {
			instance = new ThreadDownloadPlayerSkin();
			instance.setName("Energetic Player Skin Downloader");
			instance.setPriority(3);
			instance.start();
		}
		return instance;
	}

	public FlexibleArray<String> getQueuedUsernames() {
		return this.queuedUsernames;
	}

	public Map<String, BufferedImage> getSkinImages() {
		return this.skinImages;
	}

	@Override
	public void run() {
		while (true) {
			for (String username : this.queuedUsernames) {
				try {
					BufferedImage skinImage = ImageIO.read(new URL(String.format(SKIN_LOCATION, username)));
					this.skinImages.put(username, skinImage);
				} catch (Exception exception) {
				}
				synchronized (ThreadDownloadPlayerSkin.class) {
					this.queuedUsernames.remove(username);
				}
			}
		}
	}

}

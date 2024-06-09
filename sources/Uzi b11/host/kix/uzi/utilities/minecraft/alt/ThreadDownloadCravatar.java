package host.kix.uzi.utilities.minecraft.alt;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

// TODO: have a thread pool to download resources from different locations!
public class ThreadDownloadCravatar extends Thread {

	private static ThreadDownloadCravatar instance;
	private static final String SKIN_LOCATION = "http://cravatar.eu/helmhead/%s/256.png";

	private FlexibleArray<String> queuedUsernames;
	private Map<String, BufferedImage> skinImages = new HashMap<String, BufferedImage>();

	private ThreadDownloadCravatar() {
		this.queuedUsernames = new FlexibleArray<String>();
	}

	public static ThreadDownloadCravatar getInstance() {
		if (instance == null) {
			instance = new ThreadDownloadCravatar();
			instance.setName("Energetic Player Cravatar Downloader");
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
					this.skinImages.put(username, null);
				}
				synchronized (ThreadDownloadCravatar.class) {
					this.queuedUsernames.remove(username);
				}
			}
		}
	}
}

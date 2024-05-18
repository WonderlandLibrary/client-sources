package host.kix.uzi.utilities.minecraft.alt;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class PlayerSkinCache {

	private static final String RESOURCE_NAME = "Player Skin (%s): %s";
	private static final PlayerSkinCache INSTANCE = new PlayerSkinCache();

	private Map<String, PlayerSkin> playerSkins = new HashMap<String, PlayerSkin>();
	private Map<String, Cravatar> cravatars = new HashMap<String, Cravatar>();

	private ThreadDownloadPlayerSkin skinDownloader;
	private ThreadDownloadCravatar cravatarDownloader;

	public PlayerSkinCache() {
		this.skinDownloader = ThreadDownloadPlayerSkin.getInstance();
		this.cravatarDownloader = ThreadDownloadCravatar.getInstance();
	}

	public static PlayerSkinCache getInstance() {
		return INSTANCE;
	}

	public PlayerSkin getPlayerSkin(String username) {
		PlayerSkin playerSkin = this.playerSkins.get(username);
		if (playerSkin == null) {
			playerSkin = new PlayerSkin();
			this.playerSkins.put(username, playerSkin);
			if (!(this.skinDownloader.getQueuedUsernames().contains(username))) {
				synchronized (ThreadDownloadPlayerSkin.class) {
					this.skinDownloader.getQueuedUsernames().add(username);
				}
			}
		}
		if (!playerSkin.isSkinLoaded()) {
			BufferedImage skinImage = this.skinDownloader.getSkinImages().get(username);
			if (skinImage != null) {
				playerSkin.setPlayerSkin(skinImage, String.format(RESOURCE_NAME, username, "%s"));
			}
		}
		return playerSkin;
	}
	
	public Cravatar getCravatar(String username) {
		Cravatar cravatar = this.cravatars.get(username);
		if (cravatar == null) {
			cravatar = new Cravatar();
			this.cravatars.put(username, cravatar);
			if (!(this.cravatarDownloader.getQueuedUsernames().contains(username))) {
				synchronized (ThreadDownloadCravatar.class) {
					this.cravatarDownloader.getQueuedUsernames().add(username);
				}
			}
		}
		if (!cravatar.isSkinLoaded()) {
			BufferedImage skinImage = this.cravatarDownloader.getSkinImages().get(username);
			if (skinImage != null) {
				cravatar.setPlayerSkin(skinImage, String.format(RESOURCE_NAME, username, "Cravatar"));
			}
		}
		return cravatar;
	}
}

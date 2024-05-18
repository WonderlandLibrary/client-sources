package net.SliceClient.Utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class PlayerSkinCache
{
  private static final String RESOURCE_NAME = "Player Skin (%s): %s";
  
  public PlayerSkinCache()
  {
    playerSkins = new HashMap();
    skinDownloader = ThreadDownloadPlayerSkin.getInstance();
  }
  
  public static PlayerSkinCache getInstance()
  {
    return INSTANCE;
  }
  
  public PlayerSkin getPlayerSkin(String username)
  {
    PlayerSkin playerSkin = (PlayerSkin)playerSkins.get(username);
    if (playerSkin == null)
    {
      playerSkin = new PlayerSkin();
      playerSkins.put(username, playerSkin);
      if (!skinDownloader.getQueuedUsernames().contains(username)) {
        synchronized (skinDownloader.obj)
        {
          skinDownloader.getQueuedUsernames().add(username);
        }
      }
    }
    if (!playerSkin.isSkinLoaded())
    {
      BufferedImage skinImage = (BufferedImage)skinDownloader.getSkinImages().get(username);
      if (skinImage != null) {
        playerSkin.setPlayerSkin(skinImage, String.format("Player Skin (%s): %s", new Object[] { username, "%s" }));
      }
    }
    return playerSkin;
  }
  
  private static final PlayerSkinCache INSTANCE = new PlayerSkinCache();
  private Map<String, PlayerSkin> playerSkins;
  private ThreadDownloadPlayerSkin skinDownloader;
}

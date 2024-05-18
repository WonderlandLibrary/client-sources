package net.SliceClient.Utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;




public class ThreadDownloadPlayerSkin
  extends Thread
{
  private static ThreadDownloadPlayerSkin instance;
  private static final String SKIN_LOCATION = "http://skins.minecraft.net/MinecraftSkins/%s.png";
  private FlexibleArray<String> queuedUsernames;
  private Map<String, BufferedImage> skinImages;
  public final Object obj;
  
  private ThreadDownloadPlayerSkin()
  {
    skinImages = new HashMap();
    obj = new Object();
    queuedUsernames = new FlexibleArray();
  }
  
  public static ThreadDownloadPlayerSkin getInstance()
  {
    if (instance == null)
    {
      (ThreadDownloadPlayerSkin.instance = new ThreadDownloadPlayerSkin()).setName("Energetic Player Skin Downloader");
      instance.setPriority(3);
      instance.start();
    }
    return instance;
  }
  
  public FlexibleArray<String> getQueuedUsernames()
  {
    return queuedUsernames;
  }
  
  public Map<String, BufferedImage> getSkinImages()
  {
    return skinImages;
  }
  
  public void run()
  {
    Iterator localIterator;
    for (;; localIterator.hasNext())
    {
      localIterator = queuedUsernames.iterator();
    }
  }
}

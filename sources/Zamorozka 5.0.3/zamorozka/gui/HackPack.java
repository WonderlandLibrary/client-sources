package zamorozka.gui;

import java.util.Random;

public class HackPack {
  private static String fakeIP = "";
  private static String fakeUUID = "";
  private static String currentIPPort = "";
  public Random rand = new Random();
  public Random rand2 = new Random();

  
  public static String getFakeIp() { return fakeIP; }


  
  public static String getFakeUUID() { return fakeUUID; }


  
  public static String getCurrentIPPort() { return currentIPPort; }


  
  public static void setFakeIP(String fIP) { fakeIP = fIP; }



  
  public static void setFakeUUID(String fUUID) { fakeUUID = fUUID; }


  
  public static void setCurrentIPPort(String cIPPort) { currentIPPort = cIPPort; }
}


package net.SliceClient.TTF;

public class TTFManager
{
  public TTFManager() {}
  
  private static TTFManager theTTFManager = new TTFManager();
  
  public static TTFManager getInstance() {
    return theTTFManager; }
  

  private TTFRenderer panelTitleFont = null;
  private TTFRenderer buttonExtraFont = null;
  private TTFRenderer standardFont = null;
  private TTFRenderer standardFont1 = null;
  private TTFRenderer AliveFont = null;
  private TTFRenderer AliveFontA = null;
  private TTFRenderer AliveFontB = null;
  private TTFRenderer AliveFontB1 = null;
  private TTFRenderer AliveFontMini = null;
  private TTFRenderer AliveFontMiniA = null;
  private TTFRenderer modListFont = null;
  private TTFRenderer chatFont = null;
  private TTFRenderer waypointFont = null;
  private TTFRenderer largeFont = null;
  private TTFRenderer xLargeFont = null;
  private TTFRenderer LogoMainFont = null;
  private TTFRenderer largeFontC = null;
  private TTFRenderer getImpact20 = null;
  private TTFRenderer getImpact15 = null;
  private TTFRenderer getImpact10 = null;
  private TTFRenderer getLemonMilk = null;
  private TTFRenderer getImpact5 = null;
  private TTFRenderer getImpact30 = null;
  private TTFRenderer getImpact35 = null;
  private TTFRenderer getImpact40 = null;
  private TTFRenderer getLemonMilk12 = null;
  private TTFRenderer getLemonMilk24 = null;
  private TTFRenderer getRoboto = null;
  

  public TTFRenderer getStandardFont()
  {
    if (standardFont == null) {
      standardFont = new TTFRenderer("Comfortaa-Light", 0, 18);
    }
    
    return standardFont;
  }
  
  public TTFRenderer getStandardFont1() {
    if (standardFont1 == null) {
      standardFont1 = new TTFRenderer("Comfortaa-Light", 0, 70);
    }
    
    return standardFont1;
  }
  
  public TTFRenderer getAliveFont() {
    if (AliveFont == null) {
      AliveFont = new TTFRenderer("Comfortaa", 0, 30);
    }
    
    return AliveFont;
  }
  
  public TTFRenderer getImpact30()
  {
    if (getImpact30 == null) {
      getImpact30 = new TTFRenderer("Impact", 0, 30);
    }
    return getImpact30;
  }
  
  public TTFRenderer getImpact20()
  {
    if (getImpact20 == null) {
      getImpact20 = new TTFRenderer("Impact", 0, 20);
    }
    return getImpact20;
  }
  
  public TTFRenderer getImpact15() {
    if (getImpact15 == null) {
      getImpact15 = new TTFRenderer("Impact", 0, 15);
    }
    return getImpact15;
  }
  
  public TTFRenderer getImpact10() {
    if (getImpact10 == null) {
      getImpact10 = new TTFRenderer("Impact", 0, 10);
    }
    return getImpact10;
  }
  
  public TTFRenderer getLemonMilk() {
    if (getLemonMilk == null) {
      getLemonMilk = new TTFRenderer("LemonMilk", 0, 7);
    }
    return getLemonMilk;
  }
  
  public TTFRenderer getLemonMilk12() {
    if (getLemonMilk12 == null) {
      getLemonMilk12 = new TTFRenderer("LemonMilk", 0, 14);
    }
    return getLemonMilk12;
  }
  
  public TTFRenderer getLemonMilk24()
  {
    if (getLemonMilk24 == null) {
      getLemonMilk24 = new TTFRenderer("LemonMilk", 0, 21);
    }
    return getLemonMilk24;
  }
  
  public TTFRenderer getRoboto() {
    if (getRoboto == null) {
      getRoboto = new TTFRenderer("basictitlefont", 0, 36);
    }
    return getRoboto;
  }
  
  public TTFRenderer getImpact5() {
    if (getImpact5 == null) {
      getImpact5 = new TTFRenderer("Impact", 0, 5);
    }
    return getImpact5;
  }
  
  public TTFRenderer getImpact35() {
    if (getImpact35 == null) {
      getImpact35 = new TTFRenderer("Impact", 0, 35);
    }
    return getImpact35;
  }
  
  public TTFRenderer getImpact40() {
    if (getImpact40 == null) {
      getImpact40 = new TTFRenderer("Impact", 0, 40);
    }
    return getImpact40;
  }
  
  public TTFRenderer getAliveFontA() {
    if (AliveFontA == null) {
      AliveFontA = new TTFRenderer("Russian", 0, 35);
    }
    
    return AliveFontA;
  }
  
  public TTFRenderer getAliveFontMiniA() {
    if (AliveFontMiniA == null) {
      AliveFontMiniA = new TTFRenderer("Comfortaa-Light", 0, 23);
    }
    
    return AliveFontMiniA;
  }
  
  public TTFRenderer getAliveFontB() {
    if (AliveFontB == null) {
      AliveFontB = new TTFRenderer("Comfortaa-Light", 0, 18);
    }
    
    return AliveFontB;
  }
  
  public TTFRenderer getAliveFontB1() {
    if (AliveFontB1 == null) {
      AliveFontB1 = new TTFRenderer("Comfortaa-Light", 0, 15);
    }
    
    return AliveFontB1;
  }
  
  public TTFRenderer getAliveFontMini() {
    if (AliveFontMini == null) {
      AliveFontMini = new TTFRenderer("Comfortaa", 0, 12);
    }
    
    return AliveFontMini;
  }
  
  public TTFRenderer getPanelTitleFont() {
    if (panelTitleFont == null) {
      panelTitleFont = new TTFRenderer("Comfortaa-Light", 0, 30);
    }
    return panelTitleFont;
  }
  
  public TTFRenderer getMainFont() {
    if (panelTitleFont == null) {
      panelTitleFont = new TTFRenderer("Comfortaa-Light", 0, 23);
    }
    return panelTitleFont;
  }
  
  public TTFRenderer getjkhbggkujsdgb() {
    if (panelTitleFont == null) {
      panelTitleFont = new TTFRenderer("Comfortaa-Light", 0, 19);
    }
    return panelTitleFont;
  }
  
  public TTFRenderer getButtonExtraFont() {
    if (buttonExtraFont == null) {
      buttonExtraFont = new TTFRenderer("Full Pack 2025", 0, 16);
    }
    return buttonExtraFont;
  }
  
  public TTFRenderer getModListFont() {
    if (modListFont == null) {
      modListFont = new TTFRenderer("Arial", 0, 22);
    }
    return modListFont;
  }
  
  public TTFRenderer getChatFont()
  {
    if (chatFont == null) {
      modListFont = new TTFRenderer("Arial", 0, 26);
    }
    return chatFont;
  }
  
  public TTFRenderer getWaypointFont() {
    if (waypointFont == null) {
      waypointFont = new TTFRenderer("Test", 0, 40);
    }
    return waypointFont;
  }
  
  public TTFRenderer getLargeFont() {
    if (largeFont == null) {
      largeFont = new TTFRenderer("Sans Serif Shaded", 0, 100);
    }
    return largeFont;
  }
  
  public TTFRenderer getLargeFontComfortaa() {
    if (largeFontC == null) {
      largeFontC = new TTFRenderer("Comfortaa", 1, 112);
    }
    return largeFontC;
  }
  
  public TTFRenderer getLargeItalicFont() {
    if (largeFont == null) {
      largeFont = new TTFRenderer("Full Pack 2025", 2, 30);
    }
    return largeFont;
  }
  
  public TTFRenderer getLogoMainFont() {
    if (LogoMainFont == null) {
      LogoMainFont = new TTFRenderer("Full Pack 2025", 0, 100);
    }
    return LogoMainFont;
  }
  
  public TTFRenderer getXLargeFont() {
    if (xLargeFont == null) {
      xLargeFont = new TTFRenderer("Roboto", 0, 30);
    }
    return xLargeFont;
  }
}

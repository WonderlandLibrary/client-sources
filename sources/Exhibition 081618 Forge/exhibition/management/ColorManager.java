package exhibition.management;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ColorManager {
   private static List colorObjectList = new CopyOnWriteArrayList();
   public static ColorObject fTeam = new ColorObject(0, 255, 0, 255);
   public static ColorObject eTeam = new ColorObject(255, 0, 0, 255);
   public static ColorObject fVis = new ColorObject(0, 0, 255, 255);
   public static ColorObject fInvis = new ColorObject(0, 255, 0, 255);
   public static ColorObject eVis = new ColorObject(255, 0, 0, 255);
   public static ColorObject eInvis = new ColorObject(255, 255, 0, 255);
   public static ColorObject hudColor = new ColorObject(0, 192, 255, 255);
   public static ColorObject xhair = new ColorObject(0, 255, 0, 200);
   public static ColorObject chamsVis = new ColorObject(255, 0, 0, 255);
   public static ColorObject chamsInvis = new ColorObject(255, 255, 0, 255);
   public static ColorObject glowColor = new ColorObject(0, 255, 0, 255);

   public static List getColorObjectList() {
      return colorObjectList;
   }

   public static ColorObject getFriendlyVisible() {
      return fVis;
   }

   public static ColorObject getFriendlyInvisible() {
      return fInvis;
   }

   public static ColorObject getEnemyVisible() {
      return eVis;
   }

   public static ColorObject getEnemyInvisible() {
      return eInvis;
   }

   public ColorObject getHudColor() {
      return hudColor;
   }

   public ColorManager() {
      colorObjectList.add(fVis);
      colorObjectList.add(fInvis);
      colorObjectList.add(eVis);
      colorObjectList.add(eInvis);
      colorObjectList.add(hudColor);
      colorObjectList.add(fTeam);
      colorObjectList.add(eTeam);
      colorObjectList.add(xhair);
      colorObjectList.add(chamsInvis);
      colorObjectList.add(chamsVis);
      colorObjectList.add(glowColor);
   }
}

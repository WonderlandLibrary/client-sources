package exhibition.management.command.impl;

import exhibition.management.ColorManager;
import exhibition.management.ColorObject;
import exhibition.management.command.Command;
import exhibition.util.FileUtils;
import exhibition.util.misc.ChatUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ColorCommand extends Command {
   private static final File COLOR_DIR = FileUtils.getConfigFile("Colors");

   public static void saveStatus() {
      List fileContent = new ArrayList();
      fileContent.add(String.format("%s:%s:%s:%s:%s", "friendlyVisible", ColorManager.fVis.getRed(), ColorManager.fVis.getGreen(), ColorManager.fVis.getBlue(), ColorManager.fVis.getAlpha()));
      fileContent.add(String.format("%s:%s:%s:%s:%s", "friendlyInvisible", ColorManager.fInvis.getRed(), ColorManager.fInvis.getGreen(), ColorManager.fInvis.getBlue(), ColorManager.fInvis.getAlpha()));
      fileContent.add(String.format("%s:%s:%s:%s:%s", "enemyVisible", ColorManager.eVis.getRed(), ColorManager.eVis.getGreen(), ColorManager.eVis.getBlue(), ColorManager.eVis.getAlpha()));
      fileContent.add(String.format("%s:%s:%s:%s:%s", "enemyInvisible", ColorManager.eInvis.getRed(), ColorManager.eInvis.getGreen(), ColorManager.eInvis.getBlue(), ColorManager.eInvis.getAlpha()));
      fileContent.add(String.format("%s:%s:%s:%s:%s", "friendlyTeam", ColorManager.fTeam.getRed(), ColorManager.fTeam.getGreen(), ColorManager.fTeam.getBlue(), ColorManager.fTeam.getAlpha()));
      fileContent.add(String.format("%s:%s:%s:%s:%s", "enemyTeam", ColorManager.eTeam.getRed(), ColorManager.eTeam.getGreen(), ColorManager.eTeam.getBlue(), ColorManager.eTeam.getAlpha()));
      fileContent.add(String.format("%s:%s:%s:%s:%s", "hudColor", ColorManager.hudColor.getRed(), ColorManager.hudColor.getGreen(), ColorManager.hudColor.getBlue(), ColorManager.hudColor.getAlpha()));
      fileContent.add(String.format("%s:%s:%s:%s:%s", "xhair", ColorManager.xhair.getRed(), ColorManager.xhair.getGreen(), ColorManager.xhair.getBlue(), ColorManager.xhair.getAlpha()));
      ColorObject c = ColorManager.chamsVis;
      fileContent.add(String.format("%s:%s:%s:%s:%s", "chamsVis", c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
      c = ColorManager.chamsInvis;
      fileContent.add(String.format("%s:%s:%s:%s:%s", "chamsInvis", c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
      FileUtils.write(COLOR_DIR, fileContent, true);
   }

   public static void loadStatus() {
      try {
         List fileContent = FileUtils.read(COLOR_DIR);
         Iterator var1 = fileContent.iterator();

         while(var1.hasNext()) {
            String line = (String)var1.next();
            String[] split = line.split(":");
            String object = split[0];
            int red = Integer.parseInt(split[1]);
            int green = Integer.parseInt(split[2]);
            int blue = Integer.parseInt(split[3]);
            int alpha = Integer.parseInt(split[4]);
            byte var10 = -1;
            switch(object.hashCode()) {
            case -2079258363:
               if (object.equals("enemyInvisible")) {
                  var10 = 3;
               }
               break;
            case -1050181115:
               if (object.equals("enemyTeam")) {
                  var10 = 5;
               }
               break;
            case -883910329:
               if (object.equals("friendlyVisible")) {
                  var10 = 0;
               }
               break;
            case 114017370:
               if (object.equals("xhair")) {
                  var10 = 7;
               }
               break;
            case 255601836:
               if (object.equals("hudColor")) {
                  var10 = 6;
               }
               break;
            case 462115240:
               if (object.equals("friendlyTeam")) {
                  var10 = 4;
               }
               break;
            case 502312906:
               if (object.equals("enemyVisible")) {
                  var10 = 2;
               }
               break;
            case 1431837054:
               if (object.equals("chamsVis")) {
                  var10 = 8;
               }
               break;
            case 1495041858:
               if (object.equals("friendlyInvisible")) {
                  var10 = 1;
               }
               break;
            case 1594023609:
               if (object.equals("chamsInvis")) {
                  var10 = 9;
               }
            }

            switch(var10) {
            case 0:
               ColorManager.fVis.updateColors(red, green, blue, alpha);
               break;
            case 1:
               ColorManager.fInvis.updateColors(red, green, blue, alpha);
               break;
            case 2:
               ColorManager.eVis.updateColors(red, green, blue, alpha);
               break;
            case 3:
               ColorManager.eInvis.updateColors(red, green, blue, alpha);
               break;
            case 4:
               ColorManager.fTeam.updateColors(red, green, blue, alpha);
               break;
            case 5:
               ColorManager.eTeam.updateColors(red, green, blue, alpha);
               break;
            case 6:
               ColorManager.hudColor.updateColors(red, green, blue, alpha);
               break;
            case 7:
               ColorManager.xhair.updateColors(red, green, blue, alpha);
               break;
            case 8:
               ColorManager.chamsVis.updateColors(red, green, blue, alpha);
               break;
            case 9:
               ColorManager.chamsInvis.updateColors(red, green, blue, alpha);
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   public ColorCommand(String[] names, String description) {
      super(names, description);
      loadStatus();
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else if (args.length < 2) {
         this.printUsage();
      } else {
         String[] color = args[1].split(":");
         if (color.length < 4) {
            this.printUsage();
         } else {
            int red = Integer.parseInt(color[0]);
            int green = Integer.parseInt(color[1]);
            int blue = Integer.parseInt(color[2]);
            int alpha = Integer.parseInt(color[3]);
            String var7 = args[0];
            byte var8 = -1;
            switch(var7.hashCode()) {
            case 3236:
               if (var7.equals("ei")) {
                  var8 = 3;
               }
               break;
            case 3247:
               if (var7.equals("et")) {
                  var8 = 4;
               }
               break;
            case 3249:
               if (var7.equals("ev")) {
                  var8 = 2;
               }
               break;
            case 3267:
               if (var7.equals("fi")) {
                  var8 = 1;
               }
               break;
            case 3278:
               if (var7.equals("ft")) {
                  var8 = 5;
               }
               break;
            case 3280:
               if (var7.equals("fv")) {
                  var8 = 0;
               }
               break;
            case 3323:
               if (var7.equals("hc")) {
                  var8 = 6;
               }
            }

            switch(var8) {
            case 0:
               ColorManager.fVis.updateColors(red, green, blue, alpha);
               ChatUtil.printChat("§4[§cE§4]§8 ColorCommand set: §c" + red + "  §a" + green + "  §b" + blue + "  §f" + alpha);
               saveStatus();
            case 1:
               ColorManager.fInvis.updateColors(red, green, blue, alpha);
               ChatUtil.printChat("§4[§cE§4]§8 ColorCommand set: §c" + red + "  §a" + green + "  §b" + blue + "  §f" + alpha);
               saveStatus();
            case 2:
               ColorManager.eVis.updateColors(red, green, blue, alpha);
               ChatUtil.printChat("§4[§cE§4]§8 ColorCommand set: §c" + red + "  §a" + green + "  §b" + blue + "  §f" + alpha);
               saveStatus();
            case 3:
               ColorManager.eInvis.updateColors(red, green, blue, alpha);
               ChatUtil.printChat("§4[§cE§4]§8 ColorCommand set: §c" + red + "  §a" + green + "  §b" + blue + "  §f" + alpha);
               saveStatus();
            case 4:
               ColorManager.eTeam.updateColors(red, green, blue, alpha);
               ChatUtil.printChat("§4[§cE§4]§8 ColorCommand set: §c" + red + "  §a" + green + "  §b" + blue + "  §f" + alpha);
               saveStatus();
            case 5:
               ColorManager.fTeam.updateColors(red, green, blue, alpha);
               ChatUtil.printChat("§4[§cE§4]§8 ColorCommand set: §c" + red + "  §a" + green + "  §b" + blue + "  §f" + alpha);
               saveStatus();
            case 6:
               ColorManager.hudColor.updateColors(red, green, blue, alpha);
               ChatUtil.printChat("§4[§cE§4]§8 ColorCommand set: §c" + red + "  §a" + green + "  §b" + blue + "  §f" + alpha);
               saveStatus();
            default:
               this.printUsage();
            }
         }
      }
   }

   public String getUsage() {
      return "object <fv | ev | fi | ei | hd | et | ft> color <r:g:b:a>";
   }
}

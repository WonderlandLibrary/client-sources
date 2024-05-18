package net.SliceClient.Gui;

import java.util.ArrayList;
import net.SliceClient.TTF.TTFManager;
import net.SliceClient.TTF.TTFRenderer;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.minecraft.client.Minecraft;

public class TabGui
{
  public String colorNormal = "§f";
  public int guiWidth = 88;
  public int guiHeight = 0;
  public int tabHeight = 12;
  public static int selectedTab = 0;
  public static int selectedItem = 2;
  public static boolean mainMenu = true;
  public static ArrayList tabsList;
  private Minecraft mc;
  
  public TabGui(Minecraft minecraft)
  {
    mc = minecraft;
    tabsList = new ArrayList();
    Tab tabPlayer = new Tab(this, "Combat");
    for (Module module : ModuleManager.getModules()) {
      if (module.getCategory() == Category.COMBAT) {
        hacks.add(module);
      }
    }
    tabsList.add(tabPlayer);
    
    Tab tabMove = new Tab(this, "Misc");
    for (Module module : ModuleManager.getModules()) {
      if (module.getCategory() == Category.MISC) {
        hacks.add(module);
      }
    }
    tabsList.add(tabMove);
    
    Tab tabMovement = new Tab(this, "Movement");
    for (Module module : ModuleManager.getModules()) {
      if (module.getCategory() == Category.MOVEMENT) {
        hacks.add(module);
      }
    }
    tabsList.add(tabMovement);
    
    Tab tabMovement2 = new Tab(this, "Player");
    for (Module module : ModuleManager.getModules()) {
      if (module.getCategory() == Category.PLAYER) {
        hacks.add(module);
      }
    }
    tabsList.add(tabMovement2);
    
    Tab tabWorld = new Tab(this, "Render");
    for (Module module : ModuleManager.getModules()) {
      if (module.getCategory() == Category.RENDER) {
        hacks.add(module);
      }
    }
    tabsList.add(tabWorld);
    

    Tab tabSpeed = new Tab(this, "Speed");
    for (Module module : ModuleManager.getModules()) {
      if (module.getCategory() == Category.SPEED) {
        hacks.add(module);
      }
    }
    tabsList.add(tabSpeed);
    


    Tab tabCombat = new Tab(this, "Exploits");
    for (Module module : ModuleManager.getModules()) {
      if (module.getCategory() == Category.EXPLOITS) {
        hacks.add(module);
      }
    }
    tabsList.add(tabCombat);
    
    Tab tabFun = new Tab(this, "World");
    for (Module module : ModuleManager.getModules()) {
      if (module.getCategory() == Category.WORLD) {
        hacks.add(module);
      }
    }
    tabsList.add(tabFun);
    guiHeight = (tabHeight + tabsList.size() * tabHeight);
  }
  
  public int RGBtoHEX(int r, int g, int b, int a)
  {
    return (a << 24) + (r << 16) + (g << 8) + b;
  }
  
  public void drawGui(int posY, int posX, int width)
  {
    int widthAmount = 0;
    int x = posY;
    int y = posX;
    guiWidth = width;
    RenderHelper.drawBorderedRect(x - 1, y, x + guiWidth, y + guiHeight - 13, 1.0E-5F, RGBtoHEX(86, 82, 82, 250), RGBtoHEX(86, 82, 82, 250));
    int yOff = y + 1;
    
    int[] colours = { 34867, -13369498, -6723841, -10040065, -43691, 65382 };
    for (int i = 0; i < tabsList.size(); i++)
    {
      RenderHelper.drawBorderedRect(x - 2, yOff - 2, x + guiWidth + 1, y + tabHeight * i + 11, 2.3F, i == selectedTab ? 0 : 0, i == selectedTab ? RGBtoHEX(96, 99, 192, 255) : 0);
      
      TTFManager.getInstance().getLemonMilk().drawStringWithShadow("§f" + tabsListgettabName, x + 2, yOff, -3);
      if ((i == selectedTab) && (!mainMenu)) {
        ((Tab)tabsList.get(i)).drawTabMenu(x + guiWidth + 2, yOff - 2);
      }
      yOff += tabHeight;
    }
  }
  
  public static void parseKeyUp()
  {
    if (mainMenu)
    {
      selectedTab -= 1;
      if (selectedTab < 0) {
        selectedTab = tabsList.size() - 1;
      }
    }
    else
    {
      selectedItem -= 1;
      if (selectedItem < 0) {
        selectedItem = tabsListgetselectedTabhacks.size() - 1;
      }
    }
  }
  
  public static void parseKeyDown()
  {
    if (mainMenu)
    {
      selectedTab += 1;
      if (selectedTab > tabsList.size() - 1) {
        selectedTab = 0;
      }
    }
    else
    {
      selectedItem += 1;
      if (selectedItem > tabsListgetselectedTabhacks.size() - 1) {
        selectedItem = 0;
      }
    }
  }
  
  public static void parseKeyLeft()
  {
    if (!mainMenu) {
      mainMenu = true;
    }
  }
  
  public static void parseKeyRight()
  {
    if (mainMenu)
    {
      mainMenu = false;
      selectedItem = 0;
    }
  }
  
  public static void parseKeyToggle()
  {
    if (!mainMenu)
    {
      int sel = selectedItem;
      ((Module)tabsListgetselectedTabhacks.get(sel)).toggleModule();
    }
  }
}

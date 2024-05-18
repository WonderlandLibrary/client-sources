package mods.togglesprint.me.imfr0zen.guiapi.example;

import mods.togglesprint.me.imfr0zen.guiapi.ClickGui;
import mods.togglesprint.me.imfr0zen.guiapi.Colors;
import mods.togglesprint.me.imfr0zen.guiapi.Frame;
import mods.togglesprint.me.imfr0zen.guiapi.GuiFrame;
import mods.togglesprint.me.imfr0zen.guiapi.components.Button;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.files.Files;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;

public class ExampleGuiScreen
  extends ClickGui
{
  public static int combatX;
  public static int combatY;
  public static int movementX;
  public static int movementY;
  public static int renderX;
  public static int renderY;
  public static int playerX;
  public static int playerY;
  public static int bedwarsX;
  public static int bedwarsY;
  public static int settingsX;
  public static int settingsY;
  
  public void initGui()
  {
    Colors.setButtonColor(0, 80, 250, 200);
    
    GuiFrame combat = new GuiFrame("Combat", combatX, combatY);
    GuiFrame movement = new GuiFrame("Movement", movementX, movementY);
    GuiFrame render = new GuiFrame("Render", renderX, renderY);
    GuiFrame player = new GuiFrame("Player", playerX, playerY);
    GuiFrame bedwars = new GuiFrame("Bedwars", bedwarsX, bedwarsY);
    
    GuiFrame settings = new GuiFrame("Settings", settingsX, settingsY);
    
    Button enabledButtonColor = new Button("Enabled Button Color");
    Button disabledButtonColor = new Button("Disabled Button Color");
    Button arraylistcolor = new Button("ArrayList Color");
    Button buffer = new Button("§c--------------------§r");
    
    enabledButtonColor.addExtendListener(new Settings(enabledButtonColor));
    disabledButtonColor.addExtendListener(new Settings(disabledButtonColor));
    arraylistcolor.addExtendListener(new Settings(arraylistcolor));
    
    settings.addButton(enabledButtonColor);
    settings.addButton(disabledButtonColor);
    settings.addButton(arraylistcolor);
    settings.addButton(buffer);
    
    addFrame(settings);
    for (Module m : Jannik.getModuleManager().getModules())
    {
      Button moduleButton = new Button(m.getName());
      
      moduleButton.addClickListener(new ExampleClickListener(moduleButton));
      moduleButton.addExtendListener(new ExampleExtendListener(moduleButton));
      if (m.getCategory() == Category.COMBAT) {
        combat.addButton(moduleButton);
      }
      if (m.getCategory() == Category.MOVEMENT) {
        movement.addButton(moduleButton);
      }
      if (m.getCategory() == Category.RENDER) {
        render.addButton(moduleButton);
      }
      if (m.getCategory() == Category.PLAYER) {
        player.addButton(moduleButton);
      }
      if (m.getCategory() == Category.BEDWARS) {
        bedwars.addButton(moduleButton);
      }
      if (m.getCategory() == Category.SETTINGS) {
        settings.addButton(moduleButton);
      }
    }
    addFrame(combat);
    addFrame(movement);
    addFrame(render);
    addFrame(player);
    addFrame(bedwars);
    
    super.initGui();
  }
  
  public void onGuiClosed()
  {
    for (Frame f : this.frames) {
      if ((f instanceof GuiFrame))
      {
        if (((GuiFrame)f).title.equals("Combat"))
        {
          combatX = ((GuiFrame)f).posX;
          combatY = ((GuiFrame)f).posY;
        }
        if (((GuiFrame)f).title.equals("Movement"))
        {
          movementX = ((GuiFrame)f).posX;
          movementY = ((GuiFrame)f).posY;
        }
        if (((GuiFrame)f).title.equals("Render"))
        {
          renderX = ((GuiFrame)f).posX;
          renderY = ((GuiFrame)f).posY;
        }
        if (((GuiFrame)f).title.equals("Player"))
        {
          playerX = ((GuiFrame)f).posX;
          playerY = ((GuiFrame)f).posY;
        }
        if (((GuiFrame)f).title.equals("Bedwars"))
        {
          bedwarsX = ((GuiFrame)f).posX;
          bedwarsY = ((GuiFrame)f).posY;
        }
        if (((GuiFrame)f).title.equals("Settings"))
        {
          settingsX = ((GuiFrame)f).posX;
          settingsY = ((GuiFrame)f).posY;
        }
      }
    }
    Files.saveGui();
    Files.saveSettings();
  }
}

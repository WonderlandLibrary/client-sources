package me.hexxed.mercury.overlay;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.overlay.components.ARMORHUDComponent;
import me.hexxed.mercury.overlay.components.ARRAYLISTComponent;
import me.hexxed.mercury.overlay.components.STRINGComponent;
import me.hexxed.mercury.overlay.components.TABGUIComponent;
import me.hexxed.mercury.util.FileUtils;
import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class OverlayManager
{
  public OverlayManager() {}
  
  private static OverlayManager manager = new OverlayManager();
  
  private List<OverlayComponent> components = new ArrayList();
  

  private List<Class> regcomponents = Arrays.asList(new Class[] {STRINGComponent.class, ARRAYLISTComponent.class, ARMORHUDComponent.class, me.hexxed.mercury.overlay.components.COMPASSCLOCKComponent.class, TABGUIComponent.class });
  
  public static OverlayManager getManager() {
    return manager;
  }
  
  public Class getModClassByName(String name) {
    for (Class comp : regcomponents) {
      if ((comp.getName().trim().equalsIgnoreCase(name.trim())) || 
        (comp.toString().trim().equalsIgnoreCase(name.trim()))) {
        return comp;
      }
    }
    
    return null;
  }
  
  public void setup()
  {
    components = getComponentsFromFile("default");
    System.out.println("Successfully loaded " + components.size() + 
      " GUI components");
  }
  
  public int update(String filename) {
    components = getComponentsFromFile(filename);
    return components.size();
  }
  
  public void resizeUpdate() {
    for (OverlayComponent comp : getActiveComponents()) {
      if ((comp.getXandYString().contains("%height")) || 
        (comp.getXandYString().contains("%width"))) {
        String xs = comp.getXandYString().split(";")[0];
        String ys = comp.getXandYString().split(";")[1];
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc, displayWidth, 
          displayHeight);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        xs = xs.replaceAll("%width%", String.valueOf(width));
        xs = xs.replaceAll("%height%", String.valueOf(height));
        ys = ys.replaceAll("%width%", String.valueOf(width));
        ys = ys.replaceAll("%height%", String.valueOf(height));
        int x = Integer.valueOf((int)Util.eval(xs)).intValue();
        int y = Integer.valueOf((int)Util.eval(ys)).intValue();
        comp.setXAndY(x, y);
      }
    }
  }
  
  public void draw() {
    resizeUpdate();
    Placeholders.updatePlaceHolders();
    renderComponents();
  }
  
  public List<OverlayComponent> getComponentsFromFile(String filename) {
    List<String> file = FileUtils.readFile(Mercury.raidriarDir
      .getAbsolutePath() + "\\gui\\" + filename + ".txt");
    List<OverlayComponent> components = new ArrayList();
    for (String s : file) {
      if (!s.startsWith("#"))
      {

        s.replaceAll(" ", "");
        String[] parts = s.split("::");
        String name = parts[0].split(";")[0];
        String text = parts[0].split(";")[1].replace("-", "");
        boolean chat = Boolean.valueOf(parts[0].split(";")[2]).booleanValue();
        String xs = parts[1].split(";")[0];
        String ys = parts[1].split(";")[1];
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc, displayWidth, 
          displayHeight);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        xs = xs.replaceAll("%width%", String.valueOf(width));
        xs = xs.replaceAll("%height%", String.valueOf(height));
        ys = ys.replaceAll("%width%", String.valueOf(width));
        ys = ys.replaceAll("%height%", String.valueOf(height));
        int x = Integer.valueOf((int)Util.eval(xs)).intValue();
        int y = Integer.valueOf((int)Util.eval(ys)).intValue();
        for (Class comp : getRegisteredComponents()) {
          String compname = comp.getSimpleName().replaceAll("Component", "");
          System.out.println(compname);
          if (name.equalsIgnoreCase(compname))
            try {
              Constructor cons = null;
              OverlayComponent toadd = null;
              Constructor[] arrayOfConstructor;
              int j = (arrayOfConstructor = comp.getDeclaredConstructors()).length; for (int i = 0; i < j; i++) {
                Constructor constr = arrayOfConstructor[i];
                if (constr.getGenericParameterTypes().length == 5) {
                  cons = constr;
                  toadd = (OverlayComponent)cons.newInstance(new Object[] {
                    text, Integer.valueOf(x), Integer.valueOf(y), Boolean.valueOf(chat), parts[1] });
                  break;
                }
                if (constr.getGenericParameterTypes().length == 4) {
                  cons = constr;
                  toadd = (OverlayComponent)cons.newInstance(new Object[] { Integer.valueOf(x), 
                    Integer.valueOf(y), Boolean.valueOf(chat), parts[1] });
                  break;
                }
              }
              components.add(toadd);
            } catch (Exception e) {
              System.out.println("Couldn't add component!");
            }
        }
      }
    }
    return components;
  }
  
  public void renderComponents() {
    for (OverlayComponent comp : getActiveComponents()) {
      if ((getMinecraftcurrentScreen != null) && 
        (getMinecraftcurrentScreen.getClass() == net.minecraft.client.gui.GuiChat.class)) {
        if (comp.getDisplayedWhileChat()) {
          comp.renderComponent();
        }
        
      }
      else
        comp.renderComponent();
    }
  }
  
  public List<OverlayComponent> getActiveComponents() {
    return components;
  }
  
  public List<Class> getRegisteredComponents() {
    return regcomponents;
  }
  
  public List<String> getAllThemes() {
    List<String> themes = new ArrayList();
    File dir = new File(Mercury.raidriarDir
      .getAbsolutePath() + "\\gui");
    File[] files = dir.listFiles();
    for (File f : files) {
      if ((f.isFile()) && 
        (FileUtils.readFile(f.getAbsolutePath()).size() >= 1)) {
        String firstline = (String)FileUtils.readFile(f.getAbsolutePath()).get(0);
        if ((firstline.startsWith("#GUI")) || (f.getName().equalsIgnoreCase("default.txt"))) {
          themes.add(f.getName().replaceAll(".txt", ""));
        }
      }
    }
    return themes;
  }
}

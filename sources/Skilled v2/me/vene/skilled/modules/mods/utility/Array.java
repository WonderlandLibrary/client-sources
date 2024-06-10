 package me.vene.skilled.modules.mods.utility;
 
 import java.awt.Color;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import me.vene.skilled.gui.GUI;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.ModuleManager;
 import me.vene.skilled.modules.mods.main.CombatGUI;
 import me.vene.skilled.modules.mods.main.OtherGUI;
 import me.vene.skilled.modules.mods.main.PlayerGUI;
 import me.vene.skilled.modules.mods.main.RenderGUI;
 import me.vene.skilled.modules.mods.main.UtilityGUI;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraftforge.client.event.RenderGameOverlayEvent;
 import org.lwjgl.opengl.GL11;
 
 public class Array extends Module {
   private Minecraft mc = Minecraft.getMinecraft();
   
   public Array() {
     super("Arraylist", 0, Category.U);
   }
  
   public void onRenderText(RenderGameOverlayEvent.Post e) {
     if (this.mc.theWorld == null) {
       return;
     }
     try {
       if (GUI.renderShit) {
         GL11.glPushMatrix();
         Map<String, Class<? extends Module>> pointers = new HashMap<String, Class<? extends Module>>();
         List<String> moduleNames = new ArrayList<String>();
         for (Module mod : ModuleManager.getModules()) {
            if (mod != null && mod != this && mod.getState() && mod != Module.getModule(CombatGUI.class) && mod != Module.getModule(OtherGUI.class) && mod != Module.getModule(PlayerGUI.class) && mod != Module.getModule(RenderGUI.class) && mod != Module.getModule(UtilityGUI.class)) {
             moduleNames.add(StringRegistry.register(mod.getName()));
            pointers.put(StringRegistry.register(mod.getName()), mod.getClass());
             Collections.sort(moduleNames);
             for (int i = 0; i < moduleNames.size(); i++) {
               String moduleName = moduleNames.get(i);
               Class<? extends Module> clazz = pointers.get(StringRegistry.register(moduleName));
               Module module2 = Module.getModule(clazz);
              int bitch = rainbowXDE(i * 21000000000L, 0.8F).getRGB();
               (Minecraft.getMinecraft()).fontRendererObj.drawString(module2.getName(), GUI.arrayXPos + 2, GUI.arrayYPos + 18 + 10 * i, bitch);
             } 
           } 
         } 
         GL11.glPopMatrix();
       } 
     } catch (Exception exception) {}
   }
    private Color rainbowXDE(long offset, float fade) {
     float hue = (float)(System.nanoTime() + offset) / 1.0E10F % 1.0F;
     long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
     Color c = new Color((int)color);
     return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
   }
 }

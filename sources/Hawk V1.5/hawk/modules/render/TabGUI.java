package hawk.modules.render;

import hawk.Client;
import hawk.events.Event;
import hawk.events.listeners.EventKey;
import hawk.events.listeners.EventRenderGUI;
import hawk.modules.Module;
import hawk.settings.BooleanSetting;
import hawk.settings.KeyBindSetting;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import hawk.util.DrawUtil;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;

public class TabGUI extends Module {
   public int currentTab;
   public ModeSetting ColorOption = new ModeSetting("Color", this, "Colorful", new String[]{"Colorful", "Discord", "Red", "Blue", "Orange", "Green", "White"});
   public boolean expanded;

   public TabGUI() {
      super("TabGUI", 0, Module.Category.RENDER);
      this.toggled = true;
      this.addSettings(new Setting[]{this.ColorOption});
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventRenderGUI) {
         FontRenderer var2 = this.mc.fontRendererObj;
         int var3 = 0;
         int var4 = -1879048192;
         if (this.ColorOption.is("Colorful")) {
            float var5 = (float)(System.currentTimeMillis() % 4500L) / 4500.0F;
            var3 = Color.HSBtoRGB(var5, 1.0F, 1.0F);
         }

         if (this.ColorOption.is("Red")) {
            var3 = Color.HSBtoRGB(1.003112E7F, 1.0F, 1.0F);
         }

         if (this.ColorOption.is("Blue")) {
            var3 = -16756481;
         }

         if (this.ColorOption.is("Orange")) {
            var3 = -1350377;
         }

         if (this.ColorOption.is("Green")) {
            var3 = -13571305;
         }

         if (this.ColorOption.is("White")) {
            var3 = -1;
         }

         if (this.ColorOption.is("Discord")) {
            var3 = (new Color(114, 137, 218, 255)).getRGB();
            var4 = (new Color(47, 49, 54, 255)).getRGB();
         }

         int var19 = var3;
         int var6 = -16748374;
         DrawUtil.drawRoundedRect(5.0D, 30.5D, 70.0D, (double)(30 + Module.Category.values().length * 16) + 1.5D, 10.0D, var4);
         DrawUtil.drawRoundedRect(7.0D, (double)(32.5F + (float)(this.currentTab * 16)), 68.0D, (double)((float)(33 + this.currentTab * 16 + 12) + 0.5F), 10.0D, var3);
         int var7 = 0;
         Module.Category[] var11;
         int var10 = (var11 = Module.Category.values()).length;

         Module.Category var8;
         for(int var9 = 0; var9 < var10; ++var9) {
            var8 = var11[var9];
            if (!var8.name.equalsIgnoreCase("Hidden")) {
               if (this.currentTab == var7) {
                  var2.drawStringWithShadow(var8.name, 17.0D, (double)(35 + var7 * 16), -1);
               } else {
                  var2.drawStringWithShadow(var8.name, 11.0D, (double)(35 + var7 * 16), -1);
               }

               ++var7;
            }
         }

         if (this.expanded) {
            var8 = Module.Category.values()[this.currentTab];
            List var24 = Client.getModulesByCategory(var8);
            if (var24.size() == 0) {
               return;
            }

            DrawUtil.drawRoundedRect(70.0D, 30.5D, 138.0D, (double)(30 + var24.size() * 16) + 1.5D, 10.0D, var4);
            DrawUtil.drawRoundedRect(71.5D, (double)(32.5F + (float)(var8.moduleIndex * 16)), 136.0D, (double)((float)(33 + var8.moduleIndex * 16 + 12) + 0.5F), 10.0D, var3);
            var7 = 0;

            for(Iterator var26 = var24.iterator(); var26.hasNext(); ++var7) {
               Module var25 = (Module)var26.next();
               var2.drawStringWithShadow(var25.name, 73.0D, (double)(35 + var7 * 16), -1);
               if (var7 == var8.moduleIndex && var25.expanded) {
                  int var12 = 0;
                  int var13 = 0;

                  Setting var14;
                  Iterator var15;
                  BooleanSetting var16;
                  NumberSetting var27;
                  ModeSetting var28;
                  KeyBindSetting var29;
                  for(var15 = var25.settings.iterator(); var15.hasNext(); ++var12) {
                     var14 = (Setting)var15.next();
                     if (var14 instanceof BooleanSetting) {
                        var16 = (BooleanSetting)var14;
                        if (var13 < var2.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var16.enabled ? "Enabled" : "Disabled")))) {
                           var13 = var2.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var16.enabled ? "Enabled" : "Disabled")));
                        }
                     }

                     if (var14 instanceof NumberSetting) {
                        var27 = (NumberSetting)var14;
                        if (var13 < var2.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var27.getValue())))) {
                           var13 = var2.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var27.getValue())));
                        }
                     }

                     if (var14 instanceof ModeSetting) {
                        var28 = (ModeSetting)var14;
                        if (var13 < var2.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var28.getMode())))) {
                           var13 = var2.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var28.getMode())));
                        }
                     }

                     if (var14 instanceof KeyBindSetting) {
                        var29 = (KeyBindSetting)var14;
                        if (var13 < var2.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(Keyboard.getKeyName(var29.code))))) {
                           var13 = var2.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(Keyboard.getKeyName(var29.code))));
                        }
                     }
                  }

                  if (!var25.settings.isEmpty()) {
                     DrawUtil.drawRoundedRect(138.0D, 30.5D, (double)(70 + var13 + 9 + 68), (double)(30 + var25.settings.size() * 16) + 1.5D, 10.0D, var4);
                     DrawUtil.drawRoundedRect(140.0D, (double)(32.5F + (float)(var25.index * 16)), (double)(68 + var13 + 9 + 68), (double)((float)(33 + var25.index * 16 + 12) + 0.5F), 10.0D, ((Setting)var25.settings.get(var25.index)).focused ? var6 : var19);
                     var12 = 0;

                     for(var15 = var25.settings.iterator(); var15.hasNext(); ++var12) {
                        var14 = (Setting)var15.next();
                        if (var14 instanceof BooleanSetting) {
                           var16 = (BooleanSetting)var14;
                           var2.drawStringWithShadow(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var16.enabled ? "Enabled" : "Disabled")), 143.0D, (double)(35 + var12 * 16), -1);
                        }

                        if (var14 instanceof NumberSetting) {
                           var27 = (NumberSetting)var14;
                           var2.drawStringWithShadow(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var27.getValue())), 143.0D, (double)(35 + var12 * 16), -1);
                        }

                        if (var14 instanceof ModeSetting) {
                           var28 = (ModeSetting)var14;
                           var2.drawStringWithShadow(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(var28.getMode())), 143.0D, (double)(35 + var12 * 16), -1);
                        }

                        if (var14 instanceof KeyBindSetting) {
                           var29 = (KeyBindSetting)var14;
                           var2.drawStringWithShadow(String.valueOf((new StringBuilder(String.valueOf(var14.name))).append(" : ").append(Keyboard.getKeyName(var29.code))), 143.0D, (double)(35 + var12 * 16), -1);
                        }
                     }
                  }
               }
            }
         }
      }

      if (var1 instanceof EventKey) {
         int var17 = ((EventKey)var1).code;
         Module.Category var21 = Module.Category.values()[this.currentTab];
         List var18 = Client.getModulesByCategory(var21);
         Module var20;
         if (this.expanded && !var18.isEmpty() && ((Module)var18.get(var21.moduleIndex)).expanded) {
            var20 = (Module)var18.get(var21.moduleIndex);
            if (!var20.settings.isEmpty() && ((Setting)var20.settings.get(var20.index)).focused && var20.settings.get(var20.index) instanceof KeyBindSetting && var17 != 28 && var17 != 200 && var17 != 208 && var17 != 203 && var17 != 205 && var17 != 1) {
               KeyBindSetting var23 = (KeyBindSetting)var20.settings.get(var20.index);
               var23.code = var17;
               var23.focused = false;
               return;
            }
         }

         Setting var22;
         if (var17 == 200) {
            if (this.expanded) {
               if (this.expanded && !var18.isEmpty() && ((Module)var18.get(var21.moduleIndex)).expanded) {
                  var20 = (Module)var18.get(var21.moduleIndex);
                  if (!var20.settings.isEmpty()) {
                     if (((Setting)var20.settings.get(var20.index)).focused) {
                        var22 = (Setting)var20.settings.get(var20.index);
                        if (var22 instanceof NumberSetting) {
                           ((NumberSetting)var22).increment(true);
                        }
                     } else if (var20.index <= 0) {
                        var20.index = var20.settings.size() - 1;
                     } else {
                        --var20.index;
                     }
                  }
               } else if (var21.moduleIndex <= 0) {
                  var21.moduleIndex = var18.size() - 1;
               } else {
                  --var21.moduleIndex;
               }
            } else if (this.currentTab <= 0) {
               this.currentTab = Module.Category.values().length - 1;
            } else {
               --this.currentTab;
            }
         }

         if (var17 == 208) {
            if (this.expanded) {
               if (this.expanded && !var18.isEmpty() && ((Module)var18.get(var21.moduleIndex)).expanded) {
                  var20 = (Module)var18.get(var21.moduleIndex);
                  if (!var20.settings.isEmpty()) {
                     if (((Setting)var20.settings.get(var20.index)).focused) {
                        var22 = (Setting)var20.settings.get(var20.index);
                        if (var22 instanceof NumberSetting) {
                           ((NumberSetting)var22).increment(false);
                        }
                     } else if (var20.index >= var20.settings.size() - 1) {
                        var20.index = 0;
                     } else {
                        ++var20.index;
                     }
                  }
               } else if (var21.moduleIndex >= var18.size() - 1) {
                  var21.moduleIndex = 0;
               } else {
                  ++var21.moduleIndex;
               }
            } else if (this.currentTab >= Module.Category.values().length - 1) {
               this.currentTab = 0;
            } else {
               ++this.currentTab;
            }
         }

         if (var17 == 28 && this.expanded && var18.size() != 0) {
            var20 = (Module)var18.get(var21.moduleIndex);
            if (!var20.expanded && !var20.settings.isEmpty()) {
               var20.expanded = true;
            } else if (var20.expanded && !var20.settings.isEmpty()) {
               ((Setting)var20.settings.get(var20.index)).focused = !((Setting)var20.settings.get(var20.index)).focused;
            }
         }

         if (var17 == 205) {
            if (this.expanded && var18.size() != 0) {
               var20 = (Module)var18.get(var21.moduleIndex);
               if (this.expanded && !var18.isEmpty() && var20.expanded) {
                  var22 = (Setting)var20.settings.get(var20.index);
                  if (!var20.settings.isEmpty()) {
                     if (var22 instanceof BooleanSetting) {
                        ((BooleanSetting)var22).toggle();
                     }

                     if (var22 instanceof ModeSetting) {
                        ((ModeSetting)var22).cycle();
                     }
                  }
               } else if (!var20.name.equals("TabGUI")) {
                  var20.toggle();
               }
            } else {
               this.expanded = true;
            }
         }

         if (var17 == 203) {
            if (this.expanded && !var18.isEmpty() && ((Module)var18.get(var21.moduleIndex)).expanded) {
               var20 = (Module)var18.get(var21.moduleIndex);
               if (!var20.settings.isEmpty() && !((Setting)var20.settings.get(var20.index)).focused) {
                  ((Module)var18.get(var21.moduleIndex)).expanded = false;
               }
            } else {
               this.expanded = false;
            }
         }
      }

   }
}

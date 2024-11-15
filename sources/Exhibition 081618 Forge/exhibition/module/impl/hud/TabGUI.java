package exhibition.module.impl.hud;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventKeyPress;
import exhibition.event.impl.EventRenderGui;
import exhibition.event.impl.EventTick;
import exhibition.management.ColorManager;
import exhibition.management.animate.Expand;
import exhibition.management.animate.Translate;
import exhibition.module.Module;
import exhibition.module.ModuleManager;
import exhibition.module.data.ModuleData;
import exhibition.module.data.ModuleData.Type;
import exhibition.module.data.MultiBool;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.MathUtils;
import exhibition.util.RenderingUtil;
import exhibition.util.StringConversions;
import exhibition.util.Timer;
import exhibition.util.misc.ChatUtil;
import exhibition.util.render.Colors;
import exhibition.util.render.Depth;
import exhibition.util.render.TTFFontRenderer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class TabGUI extends Module {
   private int selectedTypeX;
   private int selectedModuleY;
   private int selectedSetY;
   private int moduleBoxY;
   private int currentSetting;
   private int settingBoxX;
   private int categoryBoxY;
   private int categoryBoxX;
   private int currentCategory;
   private int targetY;
   private int targetModY;
   private int targetSetY;
   private int moduleBoxX;
   private int targetX;
   private int targetSetX;
   private boolean inModules;
   private boolean inModSet;
   private boolean inSet;
   private Module selectedModule;
   private Timer timer = new Timer();
   private int opacity = 45;
   private int targetOpacity = 45;
   private boolean isActive;
   private Translate selectedType = new Translate(0.0F, 14.0F);
   private Translate selectedModuleT = new Translate(0.0F, 14.0F);
   private Translate selectedSettingT = new Translate(0.0F, 14.0F);
   private Expand moduleExpand;

   public TabGUI(ModuleData data) {
      super(data);
   }

   public void onEnable() {
      this.targetY = 12;
      this.categoryBoxY = 0;
      this.currentCategory = 0;
      this.inModules = false;
      this.inModSet = false;
      this.inSet = false;
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRenderGui.class, EventTick.class, EventKeyPress.class}
   )
   public void onEvent(Event event) {
      if (!mc.gameSettings.showDebugInfo) {
         TTFFontRenderer font = Client.nametagsFont;
         int longestString;
         int i;
         int y = 0;
         if (event instanceof EventKeyPress) {
            EventKeyPress ek = (EventKeyPress)event;
            if (!this.isActive && this.keyCheck(ek.getKey())) {
               this.isActive = true;
               this.targetOpacity = 200;
               this.timer.reset();
            }

            if (this.isActive && this.keyCheck(ek.getKey())) {
               this.timer.reset();
            }

            if (!this.inModules) {
               if (ek.getKey() == 208) {
                  this.targetY += 12;
                  ++this.currentCategory;
                  if (this.currentCategory > ModuleData.Type.values().length - 1) {
                     this.targetY = 14;
                     this.currentCategory = 0;
                  }
               } else if (ek.getKey() == 200) {
            	   if (this.currentCategory <= 0) {
            		   return;
            	   }
            	   this.targetY -= 12;
                   --this.currentCategory;
                   if (this.currentCategory < 0) {
                      this.targetY = this.categoryBoxY - 11;
                      this.currentCategory = ModuleData.Type.values().length - 2;
                   }
               } else if (ek.getKey() == 205) {
                  this.inModules = true;
                  this.moduleBoxY = 0;
                  this.selectedModuleT.setY(14.0F);
                  this.targetModY = 14;
                  longestString = 0;
                  Module[] var5 = (Module[])Client.getModuleManager().getArray();
                  i = var5.length;

                  for(y = 0; y < i; ++y) {
                     Module modxd = var5[y];
                     if (modxd.getType() == ModuleData.Type.values()[this.currentCategory] && (float)longestString < font.getWidth(modxd.getName())) {
                        longestString = (int)font.getWidth(modxd.getName());
                     }
                  }

                  this.targetX = this.categoryBoxX + 2 + longestString + 7;
                  this.moduleBoxX = this.categoryBoxX + 3;
               }
            } else {
               Thread thread;
               String faggotXD;
               if (!this.inModSet) {
                  if (ek.getKey() == 203) {
                     this.targetX = this.categoryBoxX + 3;
                     thread = new Thread(() -> {
                        try {
                           Thread.sleep(110L);
                        } catch (InterruptedException var2) {
                           ;
                        }

                        this.inModules = false;
                     });
                     thread.start();
                  }

                  if (ek.getKey() == 208) {
                     this.targetModY += 12;
                     ++this.moduleBoxY;
                     if (this.moduleBoxY > this.getModules(ModuleData.Type.values()[this.currentCategory]).size() - 1) {
                        this.targetModY = 14;
                        this.moduleBoxY = 0;
                     }
                  } else if (ek.getKey() == 200) {
                     this.targetModY -= 12;
                     --this.moduleBoxY;
                     if (this.moduleBoxY < 0) {
                        this.targetModY = (this.getModules(ModuleData.Type.values()[this.currentCategory]).size() - 1) * 12 + 14;
                        this.moduleBoxY = this.getModules(ModuleData.Type.values()[this.currentCategory]).size() - 1;
                     }
                  } else if (ek.getKey() == 28) {
                     try {
                        Module mod = (Module)this.getModules(ModuleData.Type.values()[this.currentCategory]).get(this.moduleBoxY);
                        mod.toggle();
                     } catch (Exception var14) {
                        ChatUtil.printChat(this.getModules(ModuleData.Type.values()[this.currentCategory]).size() + ", " + this.moduleBoxY + ", ");
                     }
                  } else if (ek.getKey() == 205) {
                     this.selectedModule = (Module)this.getModules(ModuleData.Type.values()[this.currentCategory]).get(this.moduleBoxY);
                     if (this.getSettings(this.selectedModule) != null) {
                        this.inModSet = true;
                        this.selectedSettingT.setY(14.0F);
                        this.targetSetY = 14;
                        this.currentSetting = 0;
                        longestString = 0;
                        Iterator var20 = this.getSettings(this.selectedModule).iterator();

                        while(var20.hasNext()) {
                           Setting modxd = (Setting)var20.next();
                           faggotXD = modxd.getValue() instanceof Options ? ((Options)modxd.getValue()).getSelected() : (modxd.getValue() instanceof MultiBool ? "N/A" : modxd.getValue().toString());
                           if ((float)longestString < font.getWidth(modxd.getName() + ": §l" + faggotXD)) {
                              longestString = (int)font.getWidth(modxd.getName() + ": §l" + faggotXD);
                           }
                        }

                        this.targetSetX = this.moduleBoxX + longestString + 5;
                        this.settingBoxX = this.moduleBoxX + 1;
                     }
                  }
               } else if (!this.inSet) {
                  if (ek.getKey() == 203) {
                     this.targetSetX = this.moduleBoxX + 1;
                     thread = new Thread(() -> {
                        try {
                           Thread.sleep(110L);
                        } catch (InterruptedException var2) {
                           ;
                        }

                        this.inModSet = false;
                        this.selectedModule = null;
                     });
                     thread.start();
                  } else if (ek.getKey() == 208) {
                     this.targetSetY += 12;
                     ++this.currentSetting;
                     if (this.currentSetting > this.getSettings(this.selectedModule).size() - 1) {
                        this.currentSetting = 0;
                        this.targetSetY = 14;
                     }
                  } else if (ek.getKey() == 200) {
                     this.targetSetY -= 12;
                     --this.currentSetting;
                     if (this.currentSetting < 0) {
                        this.targetSetY = (this.getSettings(this.selectedModule).size() - 1) * 12 + 14;
                        this.currentSetting = this.getSettings(this.selectedModule).size() - 1;
                     }
                  } else if (ek.getKey() == 205) {
                     this.inSet = true;
                  }
               } else if (this.inSet) {
                  if (ek.getKey() == 203) {
                     this.inSet = !this.inSet;
                  } else {
                     double increment;
                     boolean xd;
                     Setting set;
                     ArrayList options;
                     Object newValue;
                     if (ek.getKey() == 200) {
                        set = (Setting)this.getSettings(this.selectedModule).get(this.currentSetting);
                        if (set.getValue() instanceof Number) {
                           increment = set.getInc();
                           faggotXD = MathUtils.isInteger(MathUtils.getIncremental(((Number)((Number)set.getValue())).doubleValue() + increment, increment)) ? (MathUtils.getIncremental(((Number)((Number)set.getValue())).doubleValue() + increment, increment) + "").replace(".0", "") : MathUtils.getIncremental(((Number)((Number)set.getValue())).doubleValue() + increment, increment) + "";
                           if (Double.parseDouble(faggotXD) > set.getMax() && set.getInc() != 0.0D) {
                              return;
                           }

                           newValue = StringConversions.castNumber(faggotXD, increment);
                           if (newValue != null) {
                              set.setValue(newValue);
                              ModuleManager.saveSettings();
                              return;
                           }
                        } else if (set.getValue().getClass().equals(Boolean.class)) {
                           xd = ((Boolean)set.getValue()).booleanValue();
                           set.setValue(!xd);
                           ModuleManager.saveSettings();
                        } else if (set.getValue() instanceof Options) {
                           options = new ArrayList();
                           Collections.addAll(options, ((Options)set.getValue()).getOptions());

                           for(i = 0; i <= options.size() - 1; ++i) {
                              if (((String)options.get(i)).equalsIgnoreCase(((Options)set.getValue()).getSelected())) {
                                 if (i + 1 > options.size() - 1) {
                                    ((Options)set.getValue()).setSelected((String)options.get(0));
                                 } else {
                                    ((Options)set.getValue()).setSelected((String)options.get(i + 1));
                                 }
                                 break;
                              }
                           }
                        }
                     } else if (ek.getKey() == 208) {
                        set = (Setting)this.getSettings(this.selectedModule).get(this.currentSetting);
                        if (set.getValue() instanceof Number) {
                           increment = set.getInc();
                           faggotXD = MathUtils.isInteger(MathUtils.getIncremental(((Number)((Number)set.getValue())).doubleValue() - increment, increment)) ? (MathUtils.getIncremental(((Number)((Number)set.getValue())).doubleValue() - increment, increment) + "").replace(".0", "") : MathUtils.getIncremental(((Number)((Number)set.getValue())).doubleValue() - increment, increment) + "";
                           if (Double.parseDouble(faggotXD) < set.getMin() && increment != 0.0D) {
                              return;
                           }

                           newValue = StringConversions.castNumber(faggotXD, increment);
                           if (newValue != null) {
                              set.setValue(newValue);
                              ModuleManager.saveSettings();
                              return;
                           }
                        } else if (set.getValue().getClass().equals(Boolean.class)) {
                           xd = ((Boolean)set.getValue()).booleanValue();
                           set.setValue(!xd);
                           ModuleManager.saveSettings();
                        } else if (set.getValue() instanceof Options) {
                           options = new ArrayList();
                           Collections.addAll(options, ((Options)set.getValue()).getOptions());

                           for(i = options.size() - 1; i >= 0; --i) {
                              if (((String)options.get(i)).equalsIgnoreCase(((Options)set.getValue()).getSelected())) {
                                 if (i - 1 < 0) {
                                    ((Options)set.getValue()).setSelected((String)options.get(options.size() - 1));
                                 } else {
                                    ((Options)set.getValue()).setSelected((String)options.get(i - 1));
                                 }
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (event instanceof EventTick && this.categoryBoxY == 0) {
            y = 13;
            longestString = -1;
            ModuleData.Type[] var26 = ModuleData.Type.values();
            i = var26.length;

            for(y = 0; y < i; ++y) {
               ModuleData.Type type = var26[y];
               y += 12;
               if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(type.name()) > longestString) {
                  longestString = Minecraft.getMinecraft().fontRendererObj.getStringWidth(type.name());
               }
            }

            this.categoryBoxY = y;
            this.categoryBoxX = longestString + 7;
            this.selectedTypeX = 2;
            this.targetY = 14;
         }

         if (event instanceof EventRenderGui) {
            EventRenderGui er = (EventRenderGui)event;
            if (this.timer.delay(4500.0F)) {
               this.targetOpacity = 70;
               this.isActive = false;
            }

            longestString = this.targetX - this.moduleBoxX;
            int diff5 = this.targetSetX - this.settingBoxX;
            i = this.targetOpacity - this.opacity;
            this.opacity = (int)((double)this.opacity + (double)i * 0.1D);
            this.selectedType.interpolate((float)this.selectedTypeX, (float)this.targetY, 0.35F);
            this.selectedModuleT.interpolate((float)(this.categoryBoxX + 3), (float)this.targetModY, 0.35F);
            this.selectedSettingT.interpolate(0.0F, (float)this.targetSetY, 0.35F);
            this.moduleBoxX = (int)((double)this.moduleBoxX + MathUtils.roundToPlace((double)longestString * 0.25D, 0));
            if (longestString == 1) {
               ++this.moduleBoxX;
            } else if (longestString == -1) {
               --this.moduleBoxX;
            }

            this.settingBoxX = (int)((double)this.settingBoxX + MathUtils.roundToPlace((double)diff5 * 0.25D, 0));
            if (diff5 == 1) {
               ++this.settingBoxX;
            } else if (diff5 == -1) {
               --this.settingBoxX;
            }
            float yAxis = 12;
            for(Type lol : Type.values()) {
            	yAxis += 12;
            }
            
            RenderingUtil.rectangle(2, 14, this.categoryBoxX + 10, yAxis + 1, Colors.getColor(0, 0, 0, this.opacity));
            //RenderingUtil.rectangle(2.0D, 14.0D, (double)(this.categoryBoxX + 10), (double)(this.categoryBoxY + 1), Colors.g);
            RenderingUtil.rectangle((double)this.selectedTypeX + 0.3D, (double)this.selectedType.getY() + 0.5d, (double)(this.categoryBoxX + 10) - 0.3D, (double)(this.selectedType.getY() + 11.0F) - 0.3D, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, this.opacity + 64));
            y = 15;
            ModuleData.Type[] var31 = ModuleData.Type.values();
            int var9 = var31.length;

            int var10;
            ModuleData.Type type;
            boolean isSelected;
            for(var10 = 0; var10 < var9; ++var10) {
               type = var31[var10];
               isSelected = Math.abs((float)y - this.selectedType.getY()) < 6.0F || (float)y - this.selectedType.getY() == 6.0F;
               GlStateManager.pushMatrix();
               GlStateManager.enableBlend();
               font.drawStringWithShadow(type.name(), isSelected ? 6.0F : 4.0F, (float)y + 0.5F, Colors.getColor(175, this.opacity + 64));
               GlStateManager.disableBlend();
               GlStateManager.popMatrix();
               y += 12;
            }

            y = 15;
            Depth.pre();
            Depth.mask();
            RenderingUtil.rectangle((double)this.selectedTypeX + 0.3D, (double)(this.selectedType.getY() - 1.0F), (double)(this.categoryBoxX + 10) - 0.3D, (double)(this.selectedType.getY() + 13.0F), Colors.getColor(255, 255));
            Depth.render(514);
            var31 = ModuleData.Type.values();
            var9 = var31.length;

            for(var10 = 0; var10 < var9; ++var10) {
               type = var31[var10];
               isSelected = Math.abs((float)y - this.selectedType.getY()) < 6.0F || (float)y - this.selectedType.getY() == 6.0F;
               font.drawString(type.name(), isSelected ? 6.0F : 4.0F, (float)y, Colors.getColor(255, this.opacity + 64));
               y += 12;
            }

            Depth.post();
            Iterator var32;
            if (this.inModules) {
               List xd = this.getModules(ModuleData.Type.values()[this.currentCategory]);
               y = 15;
               RenderingUtil.rectangle((double)(this.categoryBoxX + 11), 14.0D, (double)this.moduleBoxX + 11, (double)(xd.size() * 12 + 14), Colors.getColor(0, 0, 0, this.opacity));
               RenderingUtil.rectangle((double)(this.categoryBoxX + 11) + 0.3D, (double)this.selectedModuleT.getY() + 0.3D, (double)this.moduleBoxX + 11 - 0.3D, (double)(this.selectedModuleT.getY() + 12.0F) - 0.3D, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, this.opacity + 64));
               if (longestString == 0 && this.moduleBoxX > this.categoryBoxX + 3) {
                  for(var32 = this.getModules(ModuleData.Type.values()[this.currentCategory]).iterator(); var32.hasNext(); y += 12) {
                     Module mod = (Module)var32.next();
                     if (this.getSettings(mod) != null && !this.inModSet) {
                        RenderingUtil.rectangle((double)(this.moduleBoxX + 12), (double)y, (double)(this.moduleBoxX + 21), (double)(y + 10), Colors.getColor(0, 0, 0, this.opacity));
                        GlStateManager.pushMatrix();
                        GlStateManager.enableBlend();
                        font.drawStringWithShadow("+", (float)this.moduleBoxX + 12.0F, (float)y + 0.5F, Colors.getColor(255, this.opacity + 64));
                        GlStateManager.disableBlend();
                        GlStateManager.popMatrix();
                     }

                     isSelected = Math.abs((float)y - this.selectedModuleT.getY()) < 6.0F || (float)y - this.selectedModuleT.getY() == 6.0F;
                     GlStateManager.pushMatrix();
                     GlStateManager.enableBlend();
                     font.drawStringWithShadow(mod.getName(), (float)(this.categoryBoxX + 8 + (isSelected ? 8 : 6)), (float)y, mod.isEnabled() ? Colors.getColor(255, this.opacity + 64) : Colors.getColor(175, this.opacity + 64));
                     GlStateManager.disableBlend();
                     GlStateManager.popMatrix();
                  }
               }
            }

            if (this.inModSet) {
               RenderingUtil.rectangle((double)(this.moduleBoxX + 12), 14.0D, (double)this.settingBoxX + 12, (double)(14 + this.selectedModule.getSettings().size() * 12), Colors.getColor(0, 0, 0, this.opacity));
               RenderingUtil.rectangle((double)(this.moduleBoxX + 12) + 0.3D, (double)this.selectedSettingT.getY() + 0.3D, (double)this.settingBoxX + 12 - 0.3D, (double)(this.selectedSettingT.getY() + 12.0F) - 0.3D, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, this.opacity + 64));
               int y1 = 15;

               try {
                  var32 = this.selectedModule.getSettings().values().iterator();

                  label344:
                  while(true) {
                     Setting setting;
                     String fagniger;
                     do {
                        do {
                           do {
                              if (!var32.hasNext()) {
                                 Depth.pre();
                                 Depth.mask();
                                 RenderingUtil.rectangle((double)(this.moduleBoxX + 14) + 0.3D, (double)this.selectedSettingT.getY() + 0.3D, (double)this.settingBoxX + 21 - 0.3D, (double)(this.selectedSettingT.getY() + 12.0F) - 0.3D, Colors.getColor(255, 255));
                                 Depth.render(514);
                                 y1 = 15;
                                 var32 = this.selectedModule.getSettings().values().iterator();

                                 while(true) {
                                    do {
                                       do {
                                          do {
                                             if (!var32.hasNext()) {
                                                Depth.post();
                                                break label344;
                                             }

                                             setting = (Setting)var32.next();
                                          } while(setting == null);
                                       } while(diff5 != 0);
                                    } while(this.settingBoxX <= this.moduleBoxX + 3);

                                    isSelected = Math.abs((float)y1 - this.selectedSettingT.getY()) < 6.0F || (float)y1 - this.selectedSettingT.getY() == 6.0F;
                                    fagniger = setting.getValue() instanceof Options ? ((Options)setting.getValue()).getSelected() : (setting.getValue() instanceof MultiBool ? "N/A" : setting.getValue().toString());
                                    font.drawStringWithShadow(setting.getName().charAt(0) + setting.getName().toLowerCase().substring(1) + ": \247l" + fagniger, (float)(this.moduleBoxX + 11 + (isSelected ? 6 : 4)), (float)y1, Colors.getColor(255, this.opacity + 64));
                                    y1 += 12;
                                 }
                              }

                              setting = (Setting)var32.next();
                           } while(setting == null);
                        } while(diff5 != 0);
                     } while(this.settingBoxX <= this.moduleBoxX + 3);

                     isSelected = Math.abs((float)y1 - this.selectedSettingT.getY()) < 6.0F || (float)y1 - this.selectedSettingT.getY() == 6.0F;
                     fagniger = setting.getValue() instanceof Options ? ((Options)setting.getValue()).getSelected() : (setting.getValue() instanceof MultiBool ? "N/A" : setting.getValue().toString());
                     font.drawStringWithShadow(setting.getName().charAt(0) + setting.getName().toLowerCase().substring(1) + ": \247l" + fagniger, (float)(this.moduleBoxX + 11 + (isSelected ? 6 : 4)), (float)y1, Colors.getColor(175, this.opacity + 64));
                     y1 += 12;
                  }
               } catch (Exception var15) {
                  ;
               }
            }

            if (this.inSet) {
               GlStateManager.pushMatrix();
               GlStateManager.enableBlend();
               RenderingUtil.rectangleBordered((double)(this.settingBoxX + 1), (double)(this.selectedSettingT.getY() + 1.0F), (double)(this.settingBoxX + 11), (double)(this.selectedSettingT.getY() + 11.0F), 0.5D, Colors.getColor(0, this.opacity), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, this.opacity + 64));
               font.drawStringWithShadow("§l<", (float)this.settingBoxX + 2.0F, this.selectedSettingT.getY() + 1.5F, Colors.getColor(255, 255, 255, this.opacity + 64));
               GlStateManager.disableBlend();
               GlStateManager.popMatrix();
            }
         }

      }
   }

   private boolean keyCheck(int key) {
      boolean active = false;
      switch(key) {
      case 28:
         active = true;
         break;
      case 200:
         active = true;
         break;
      case 203:
         active = true;
         break;
      case 205:
         active = true;
         break;
      case 208:
         active = true;
      }

      return active;
   }

   private List getSettings(Module mod) {
      List settings = new ArrayList();
      settings.addAll(mod.getSettings().values());
      return settings.isEmpty() ? null : settings;
   }

   private List getModules(ModuleData.Type type) {
      List modulesInType = new ArrayList();
      float width = 0.0F;
      TTFFontRenderer font = Client.nametagsFont;
      Module[] var5 = (Module[])Client.getModuleManager().getArray();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Module mod = var5[var7];
         if (mod.getType() == type) {
            modulesInType.add(mod);
            if (font.getWidth(mod.getName()) > width) {
               width = font.getWidth(mod.getName());
               this.selectedModuleT.setX(font.getWidth(mod.getName()));
            }
         }
      }

      if (modulesInType.isEmpty()) {
         return null;
      } else {
         modulesInType.sort(Comparator.comparing(Module::getName));
         return modulesInType;
      }
   }
}

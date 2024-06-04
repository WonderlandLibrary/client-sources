package yung.purity.module.modules.render.UI;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.GameSettings;
import yung.purity.Client;
import yung.purity.api.EventBus;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventKey;
import yung.purity.api.events.EventRender2D;
import yung.purity.api.value.Mode;
import yung.purity.api.value.Numbers;
import yung.purity.api.value.Option;
import yung.purity.api.value.Value;
import yung.purity.management.ModuleManager;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;
import yung.purity.utils.Helper;
import yung.purity.utils.MathUtil;
import yung.purity.utils.RenderUtil;

public class TabUI implements yung.purity.management.Manager
{
  private Section section = Section.TYPES;
  private ModuleType selectedType = ModuleType.values()[0];
  private Module selectedModule = null;
  private Value selectedValue = null;
  private int currentType = 0;
  private int currentModule = 0;
  private int currentValue = 0;
  private int top = 12;
  private int maxType;
  private int maxModule;
  
  public TabUI() {}
  
  public void init() {
    setSelectedModule();
    ModuleType[] arrmoduleType = ModuleType.values();
    int n = arrmoduleType.length;
    int n2 = 0;
    while (n2 < n) {
      ModuleType mt = arrmoduleType[n2];
      if (maxType <= mcfontRendererObj.getStringWidth(mt.name().toUpperCase()) + 4) {
        maxType = (mcfontRendererObj.getStringWidth(mt.name().toUpperCase()) + 4);
      }
      n2++;
    }
    Client.instance.getModuleManager();
    for (Module m : ModuleManager.getModules()) {
      if (maxModule <= mcfontRendererObj.getStringWidth(m.getName().toUpperCase()) + 4)
        maxModule = (mcfontRendererObj.getStringWidth(m.getName().toUpperCase()) + 4);
    }
    Client.instance.getModuleManager();
    for (Module m : ModuleManager.getModules()) {
      if (!m.getValues().isEmpty()) {
        for (Value val : m.getValues())
          if (maxValue <= mcfontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + 4)
            maxValue = (mcfontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + 4);
      }
    }
    maxModule += 12;
    maxValue += 24;
    boolean highestWidth = false;
    maxType = (maxType < maxModule ? maxModule : maxType);
    maxModule += maxType;
    maxValue += maxModule;
    EventBus.getDefault().register(new Object[] { this });
  }
  
  private void setSelectedModule() {
    selectedModule = ((Module)Client.instance.getModuleManager().getModulesInType(selectedType).get(currentModule));
  }
  
  private void setSelectedType() {
    selectedType = ModuleType.values()[currentType];
  }
  
  private void setSelectedValue() {
    selectedValue = ((Value)selectedModule.getValues().get(currentValue));
  }
  
  private void increment(Section section) {
    switch ($SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section()[section.ordinal()]) {
    case 1: 
      currentType += 1;
      if (currentType > ModuleType.values().length - 1) {
        currentType = 0;
      }
      setSelectedType();
      break;
    
    case 2: 
      currentModule += 1;
      if (currentModule > Client.instance.getModuleManager().getModulesInType(selectedType).size() - 1) {
        currentModule = 0;
      }
      setSelectedModule();
      break;
    
    case 3: 
      currentValue += 1;
      if (currentValue > selectedModule.getValues().size() - 1) {
        currentValue = 0;
      }
      setSelectedValue();
    }
  }
  
  private void deIncrement(Section section)
  {
    switch ($SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section()[section.ordinal()]) {
    case 1: 
      currentType -= 1;
      if (currentType < 0) {
        currentType = (ModuleType.values().length - 1);
      }
      setSelectedType();
      break;
    
    case 2: 
      currentModule -= 1;
      if (currentModule < 0) {
        currentModule = (Client.instance.getModuleManager().getModulesInType(selectedType).size() - 1);
      }
      setSelectedModule();
      break;
    
    case 3: 
      currentValue -= 1;
      if (currentValue < 0) {
        currentValue = (selectedModule.getValues().size() - 1);
      }
      setSelectedValue();
    }
  }
  
  private String capitalize(String input, boolean keepCasing)
  {
    if (input.length() > 1) {
      return String.valueOf(Character.toUpperCase(input.charAt(0))) + (keepCasing ? input.substring(1, input.length() - 1) : input.substring(1, input.length()).toLowerCase());
    }
    return input.toUpperCase();
  }
  
  private void resetValuesLength() {
    maxValue = 0;
    for (Value val : selectedModule.getValues()) {
      int off;
      int n = off = (val instanceof Option) ? 6 : mcfontRendererObj.getStringWidth(String.format(" §7%s", new Object[] { val.getValue().toString() })) + 6;
      if (maxValue <= mcfontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off)
        maxValue = (mcfontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off);
    }
    maxValue += maxModule;
  }
  
  @EventHandler
  private void renderTabGUI(EventRender2D e) {
    if ((!mcgameSettings.showDebugInfo) && (Client.instance.getModuleManager().getModuleByClass(yung.purity.module.modules.render.HUD.class).isEnabled())) {
      int mtY;
      int mY = mtY = top;
      int mVY = mtY;
      RenderUtil.drawBordered(2.0D, mtY + 2, maxType - 3, mtY + 9 * ModuleType.values().length, 1.0D, new Color(0, 0, 0, 130).getRGB(), new Color(0, 0, 0, 180).getRGB());
      ModuleType[] arrmoduleType = ModuleType.values();
      int n = arrmoduleType.length;
      int n2 = 0;
      while (n2 < n) {
        ModuleType mt = arrmoduleType[n2];
        net.minecraft.client.gui.GuiIngame.drawRect(2.0D, mtY + 2, maxType - 1, mtY + 4 + mcfontRendererObj.FONT_HEIGHT + 1, selectedType == mt ? new Color(106, 174, 247, 255).getRGB() : 1);
        mcfontRendererObj.drawStringWithShadow(capitalize(mt.name(), false), selectedType == mt ? 6 : 4, mtY + 4, new Color(255, 255, 255).getRGB());
        mtY += 12;
        n2++;
      }
      if ((section == Section.MODULES) || (section == Section.VALUES)) {
        RenderUtil.drawBordered(maxType + 3, mY + 2, maxModule - 65, mVY - 11.8D + 12 * Client.instance.getModuleManager().getModulesInType(selectedType).size(), 1.0D, new Color(0, 0, 0, 130).getRGB(), new Color(0, 0, 0, 180).getRGB());
        for (Module m : Client.instance.getModuleManager().getModulesInType(selectedType)) {
          Gui.drawRect(maxType + 3, mY + 2, maxModule + 4, mY + 14, selectedModule == m ? new Color(106, 174, 247, 255).getRGB() : 1);
          if (!m.getValues().isEmpty()) {
            Gui.drawRect(maxModule + 3, mY + 2, maxModule + 4, mY + 14, new Color(106, 174, 247, 255).getRGB());
          }
          mcfontRendererObj.drawStringWithShadow(m.getName(), selectedModule == m ? maxType + 8 : maxType + 6, mY + 4, m.isEnabled() ? -1 : 10066329);
          if ((section == Section.VALUES) && (selectedModule == m)) {
            RenderUtil.drawBordered(maxModule + 9, mVY + 2, maxValue - 130, mVY - 11.8D + 12 * selectedModule.getValues().size(), 1.0D, new Color(0, 0, 0, 130).getRGB(), new Color(0, 0, 0, 180).getRGB());
            for (Value val : selectedModule.getValues()) {
              Gui.drawRect(maxModule + 9, mVY + 2, maxValue + 11, mVY + 4 + mcfontRendererObj.FONT_HEIGHT + 1, selectedValue == val ? new Color(106, 174, 247, 255).getRGB() : 0);
              String toRender = val.getDisplayName();
              int color = -1;
              if ((val instanceof Option)) {
                color = ((Boolean)val.getValue()).booleanValue() ? -1 : 10066329;
                mcfontRendererObj.drawStringWithShadow(toRender, selectedValue == val ? maxModule + 13 : maxModule + 11, mVY + 4, color);
              } else {
                String value = "";
                if ((val instanceof Numbers)) {
                  value = String.format("%s", new Object[] { Double.valueOf(((Double)val.getValue()).doubleValue()) });
                } else if ((val instanceof Mode)) {
                  value = String.format("%s", new Object[] { ((Enum)val.getValue()).name() });
                }
                mcfontRendererObj.drawStringWithShadow(String.format("%s §7%s", new Object[] { toRender, value }), selectedValue == val ? maxModule + 13 : maxModule + 11, mVY + 4, color);
              }
              mVY += 12;
            }
          }
          mY += 12;
        }
      }
    }
  }
  
  @EventHandler
  private void onKey(EventKey e) {
    if (!mcgameSettings.showDebugInfo) {
      switch (e.getKey()) {
      case 208: 
        switch ($SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section()[section.ordinal()]) {
        case 1: 
          currentType += 1;
          if (currentType > ModuleType.values().length - 1) {
            currentType = 0;
          }
          selectedType = ModuleType.values()[currentType];
          break;
        
        case 2: 
          currentModule += 1;
          if (currentModule > instancemodulemanager.getModulesInType(selectedType).size() - 1) {
            currentModule = 0;
          }
          selectedModule = ((Module)instancemodulemanager.getModulesInType(selectedType).get(currentModule));
          break;
        
        case 3: 
          currentValue += 1;
          if (currentValue > selectedModule.getValues().size() - 1) {
            currentValue = 0;
          }
          selectedValue = ((Value)selectedModule.getValues().get(currentValue));
        }
        
        break;
      
      case 200: 
        switch ($SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section()[section.ordinal()]) {
        case 1: 
          currentType -= 1;
          if (currentType < 0) {
            currentType = (ModuleType.values().length - 1);
          }
          selectedType = ModuleType.values()[currentType];
          break;
        
        case 2: 
          currentModule -= 1;
          if (currentModule < 0) {
            currentModule = (instancemodulemanager.getModulesInType(selectedType).size() - 1);
          }
          selectedModule = ((Module)instancemodulemanager.getModulesInType(selectedType).get(currentModule));
          break;
        
        case 3: 
          currentValue -= 1;
          if (currentValue < 0) {
            currentValue = (selectedModule.getValues().size() - 1);
          }
          selectedValue = ((Value)selectedModule.getValues().get(currentValue));
        }
        
        break;
      
      case 205: 
        switch ($SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section()[section.ordinal()]) {
        case 1: 
          currentModule = 0;
          selectedModule = ((Module)instancemodulemanager.getModulesInType(selectedType).get(currentModule));
          section = Section.MODULES;
          break;
        
        case 2: 
          selectedModule.setEnabled(!selectedModule.isEnabled());
          break;
        
        case 3: 
          if ((selectedValue instanceof Option)) {
            selectedValue.setValue(Boolean.valueOf(!((Boolean)selectedValue.getValue()).booleanValue()));
          } else if ((selectedValue instanceof Numbers)) {
            Numbers value = (Numbers)selectedValue;
            double inc = ((Double)value.getValue()).doubleValue();
            inc += ((Double)value.getIncrement()).doubleValue();
            if ((inc = MathUtil.toDecimalLength(inc, 1)) > ((Double)value.getMaximum()).doubleValue()) {
              inc = ((Double)((Numbers)selectedValue).getMinimum()).doubleValue();
            }
            selectedValue.setValue(Double.valueOf(inc));
          } else if ((selectedValue instanceof Mode)) {
            Mode theme = (Mode)selectedValue;
            Enum current = (Enum)theme.getValue();
            int next = current.ordinal() + 1 >= theme.getModes().length ? 0 : current.ordinal() + 1;
            selectedValue.setValue(theme.getModes()[next]);
          }
          resetValuesLength();
        }
        
        break;
      
      case 28: 
        switch ($SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section()[section.ordinal()])
        {
        case 1: 
          break;
        case 2: 
          if (!selectedModule.getValues().isEmpty()) {
            resetValuesLength();
            currentValue = 0;
            selectedValue = ((Value)selectedModule.getValues().get(currentValue));
            section = Section.VALUES; }
          break;
        
        case 3: 
          section = Section.MODULES;
        }
        
        break;
      
      case 203: 
        switch ($SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section()[section.ordinal()])
        {
        case 1: 
          break;
        case 2: 
          section = Section.TYPES;
          currentModule = 0;
          break;
        case 3: 
          Enum current;
          if ((selectedValue instanceof Option)) {
            selectedValue.setValue(Boolean.valueOf(!((Boolean)selectedValue.getValue()).booleanValue()));
          } else if ((selectedValue instanceof Numbers)) {
            Numbers value = (Numbers)selectedValue;
            double inc = ((Double)value.getValue()).doubleValue();
            inc -= ((Double)value.getIncrement()).doubleValue();
            if ((inc = MathUtil.toDecimalLength(inc, 1)) < ((Double)value.getMinimum()).doubleValue()) {
              inc = ((Double)((Numbers)selectedValue).getMaximum()).doubleValue();
            }
            selectedValue.setValue(Double.valueOf(inc));
          } else if ((selectedValue instanceof Mode)) {
            Mode theme = (Mode)selectedValue;
            current = (Enum)theme.getValue();
            int next = current.ordinal() - 1 < 0 ? theme.getModes().length - 1 : current.ordinal() - 1;
            selectedValue.setValue(theme.getModes()[next]);
          }
          maxValue = 0;
          for (Value val : selectedModule.getValues()) {
            int off;
            int n = off = (val instanceof Option) ? 6 : getMinecraftfontRendererObj.getStringWidth(String.format(" §7%s", new Object[] { val.getValue().toString() })) + 6;
            if (maxValue <= getMinecraftfontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off)
              maxValue = (getMinecraftfontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off);
          }
          maxValue += maxModule;
        }
        
        break;
      }
    }
  }
  
  static int[] $SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section()
  {
    int[] arrn2 = $SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section;
    if (arrn2 != null) {
      return arrn2;
    }
    int[] arrn = new int[Section.values().length];
    try {
      arrn[Section.MODULES.ordinal()] = 2;
    }
    catch (NoSuchFieldError localNoSuchFieldError) {}
    try {
      arrn[Section.TYPES.ordinal()] = 1;
    }
    catch (NoSuchFieldError localNoSuchFieldError1) {}
    try {
      arrn[Section.VALUES.ordinal()] = 3;
    }
    catch (NoSuchFieldError localNoSuchFieldError2) {}
    $SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section = arrn;
    return $SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section;
  }
  




















  private int maxValue;
  


















  private static int[] $SWITCH_TABLE$yung$purity$module$modules$render$UI$TabUI$Section;
  


















  public static enum Section
  {
    TYPES,  MODULES,  VALUES;
  }
}

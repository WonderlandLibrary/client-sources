package cc.novoline.gui.screen.setting;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.SettingEvent;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting$1;
import cc.novoline.gui.screen.setting.Setting$ColorPickerMode;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.KeyBindProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.LongProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_12;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_16;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_17;
import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.acE;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

public class Setting {
   private final String name;
   private final String displayName;
   private final SettingType settingType;
   private final AbstractModule parentModule;
   private Supplier supplier;
   private Consumer l;
   private int x = 0;
   private int y = 0;
   private int offset;
   private int widthm;
   private BooleanProperty checkBoxProperty;
   private StringProperty comboBoxValue;
   private boolean opened;
   private AbstractNumberProperty sliderNumber;
   private double increment;
   private final double width;
   private boolean dragging;
   private ListProperty selectBox;
   private String hint;
   private StringProperty textBoxValue;
   private boolean textHovered;
   private final Timer backspace;
   private final Timer caretTimer;
   private Setting$ColorPickerMode colorPickerMode;
   private boolean colorPickerRainbow;
   private long colorPickerLastClickTime;
   private ColorProperty color;
   private float separatorHue;
   private float separatorSaturation;
   private float separatorBrightness;
   private Set colorPickedDisabledModes;
   private GuiScreen K;
   private KeyBindProperty keyBindValue;
   private int key;
   private boolean listening;
   private Timer timer;
   private final DecimalFormat decimalFormat;
   private static boolean e;

   public void update() {
      DiscordGUI var1 = Novoline.getInstance().getDiscordGUI();
      this.x = var1.getXCoordinate() + 168;
      this.y = var1.getYCoordinate() + this.offset + ((List)Manager.getSettingsByMod(this.parentModule).stream().filter(Setting::lambda$update$0).collect(Collectors.toList())).indexOf(this) * 25;
      this.widthm = var1.getXCoordinate() + 45 + 105 + var1.getWidth() - 18;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, GuiScreen var5) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.K = var5;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, Consumer var5) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.l = var5;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, GuiScreen var5, Supplier var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.K = var5;
      this.supplier = var6;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, BooleanProperty var5) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.checkBoxProperty = var5;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, BooleanProperty var5, Supplier var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.checkBoxProperty = var5;
      this.supplier = var6;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, boolean var5) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.checkBoxProperty = PropertyFactory.createBoolean(Boolean.valueOf(var5));
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, StringProperty var5) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.comboBoxValue = var5;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, StringProperty var5, Supplier var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.comboBoxValue = var5;
      this.supplier = var6;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, AbstractNumberProperty var5, double var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.sliderNumber = var5;
      this.increment = var6;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, AbstractNumberProperty var5, double var6, Supplier var8) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.sliderNumber = var5;
      this.increment = var6;
      this.supplier = var8;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, double var5, double var7, double var9, double var11) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.sliderNumber = ((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(var5)).minimum(Double.valueOf(var7))).maximum(Double.valueOf(var9));
      this.increment = var11;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, ListProperty var5) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.selectBox = var5;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, ListProperty var5, Supplier var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.selectBox = var5;
      this.supplier = var6;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, List var5, List var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.selectBox = PropertyFactory.createList(var5).acceptableValues((Collection)var6);
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, String var5, StringProperty var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.hint = var5;
      this.textBoxValue = var6;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, String var5, StringProperty var6, Supplier var7) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.hint = var5;
      this.textBoxValue = var6;
      this.supplier = var7;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, String var5, String var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.hint = var5;
      this.textBoxValue = PropertyFactory.createString(var6);
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, ColorProperty var5, EnumSet var6) {
      SettingType.c();
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.color = var5;
      this.colorPickedDisabledModes = var6;
      float[] var8 = var5.getHSB();
      this.separatorHue = var8[0] * 70.0F;
      this.separatorSaturation = var8[1] * 70.0F;
      this.separatorBrightness = var8[2] * 70.0F;
      if(acE.b() == null) {
         SettingType.b(false);
      }

   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, ColorProperty var5, EnumSet var6, Supplier var7) {
      this.offset = 30;
      this.widthm = 5;
      SettingType.c();
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.color = var5;
      this.colorPickedDisabledModes = var6;
      if(var6.contains(Setting$ColorPickerMode.HUE)) {
         if(var6.contains(Setting$ColorPickerMode.BRIGHTNESS)) {
            this.colorPickerMode = Setting$ColorPickerMode.SATURATION;
         }

         this.colorPickerMode = Setting$ColorPickerMode.BRIGHTNESS;
      }

      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      float[] var9 = var5.getHSB();
      this.separatorHue = var9[0] * 70.0F;
      this.separatorSaturation = var9[1] * 70.0F;
      this.separatorBrightness = var9[2] * 70.0F;
      this.supplier = var7;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, Integer var5, EnumSet var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.color = PropertyFactory.createColor(var5);
      this.colorPickedDisabledModes = var6;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, KeyBindProperty var5) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.keyBindValue = var5;
   }

   public Setting(String var1, String var2, SettingType var3, AbstractModule var4, KeyBindProperty var5, Supplier var6) {
      this.offset = 30;
      this.widthm = 5;
      this.opened = false;
      this.width = 70.0D;
      this.backspace = new Timer();
      this.caretTimer = new Timer();
      this.colorPickerMode = Setting$ColorPickerMode.HUE;
      this.timer = new Timer();
      this.decimalFormat = new DecimalFormat("#.#");
      this.decimalFormat.setRoundingMode(RoundingMode.CEILING);
      this.name = var1;
      this.displayName = var2;
      this.settingType = var3;
      this.parentModule = var4;
      this.keyBindValue = var5;
      this.supplier = var6;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void drawScreen(int var1, int var2) {
      boolean var3 = SettingType.c();
      boolean var4 = false;

      try {
         int var5 = ((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).getGUIColor();
         switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.settingType.ordinal()]) {
         case 1:
         case 2:
            boolean var6 = this.isHovered(var1, var2);
            int var7 = var6?var5:1677721600;
            Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.drawString(this.displayName, (float)this.x, (float)this.y, -1);
            RenderUtils.drawBorderedRect((float)(this.widthm - 70), (float)(this.y - 2), (float)this.widthm, (float)(this.y + 8), 1.0F, var7, var4?(new Color(22, 23, 26)).getRGB():-13684945);
            Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawCenteredString((CharSequence)this.comboBoxValue.get(), (float)(this.widthm - 35), (float)this.y, -1);
            if(!this.opened) {
               break;
            }

            List var8 = this.comboBoxValue.getAcceptableValues();
            RenderUtils.drawBorderedRect((float)(this.widthm - 70), (float)(this.y + 10), (float)this.widthm, (float)(this.y + 10 + var8.size() * 11), 1.0F, 1677721600, var4?(new Color(22, 23, 26)).getRGB():-13684945);
            Iterator var9 = var8.iterator();
            if(var9.hasNext()) {
               String var10 = (String)var9.next();
               Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawCenteredString(var10, (float)(this.widthm - 35), (float)(this.y + 13 + var8.indexOf(var10) * 11), this.getComboBoxValue().equalsIgnoreCase(var10)?var5:-1);
            }
         case 3:
            boolean var15 = this.isHovered(var1, var2);
            int var19 = var15?var5:1677721600;
            Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.drawString(this.displayName, (float)this.x, (float)this.y, -1);
            RenderUtils.drawBorderedRect((float)(this.widthm - 70), (float)(this.y - 2), (float)this.widthm, (float)(this.y + 8), 1.0F, var19, var4?(new Color(22, 23, 26)).getRGB():-13684945);
            if(this.selectBox.isEmpty()) {
               Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawCenteredString("...", (float)(this.widthm - 35), (float)this.y, -1);
            }

            Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawCenteredString((CharSequence)(this.selectBox.size() > 1?(String)((List)this.selectBox.get()).get(0) + "...":(CharSequence)((List)this.selectBox.get()).get(0)), (float)(this.widthm - 35), (float)this.y, -1);
            if(!this.opened) {
               break;
            }

            List var21 = this.selectBox.getAcceptableValues();
            RenderUtils.drawBorderedRect((float)(this.widthm - 70), (float)(this.y + 10), (float)this.widthm, (float)(this.y + 10 + var21.size() * 11), 1.0F, 1677721600, var4?(new Color(22, 23, 26)).getRGB():-13684945);
            Iterator var24 = var21.iterator();
            if(var24.hasNext()) {
               String var26 = (String)var24.next();
               Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawCenteredString(var26, (float)(this.widthm - 35), (float)(this.y + 13 + var21.indexOf(var26) * 11), this.selectBox.contains(var26)?var5:-1);
            }
         case 4:
            RenderUtils.drawBorderedRect((float)(this.widthm - 10), (float)(this.y - 2), (float)this.widthm, (float)(this.y + 8), 1.0F, 1677721600, var4?(new Color(22, 23, 26)).getRGB():-13684945);
            if(((Boolean)this.checkBoxProperty.get()).booleanValue()) {
               RenderUtils.drawCheck((double)(this.widthm - 8), (double)(this.y + 2), 2, var5);
            }

            Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.drawString(this.displayName, (float)this.x, (float)this.y, -1);
         case 5:
            double var16 = (((Number)this.sliderNumber.get()).doubleValue() - this.sliderNumber.getMinimum().doubleValue()) / (this.sliderNumber.getMaximum().doubleValue() - this.sliderNumber.getMinimum().doubleValue());
            RenderUtils.drawBorderedRect((float)(this.widthm - 70), (float)(this.y + 2), (float)this.widthm, (float)(this.y + 4), 1.0F, 1677721600, var4?(new Color(22, 23, 26)).getRGB():-13684945);
            int var10000 = this.widthm - 70;
            int var10001 = this.y + 2;
            double var10002 = (double)(this.widthm - 70);
            this.getClass();
            Gui.drawRect(var10000, var10001, (int)(var10002 + var16 * 70.0D), this.y + 4, var5);
            double var38 = (double)(this.widthm - 70);
            this.getClass();
            RenderUtils.drawFilledCircle((float)((int)(var38 + var16 * 70.0D)), (float)(this.y + 3), 2.0F, -1);
            Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.drawString(this.displayName, (float)this.x, (float)this.y, -1);
            FontRenderer var39 = Fonts$SFTHIN$SFTHIN_12.SFTHIN_12;
            String var41 = this.getValue() + "";
            var10002 = (double)(this.widthm - 70);
            this.getClass();
            var39.drawCenteredString(var41, (float)(var10002 + 70.0D * var16), (float)(this.y - 5), -1);
            if(!this.dragging) {
               break;
            }

            double var22 = this.sliderNumber.getMaximum().doubleValue() - this.sliderNumber.getMinimum().doubleValue();
            double var40 = this.sliderNumber.getMinimum().doubleValue();
            double var42 = (double)(var1 - (this.widthm - 70));
            this.getClass();
            double var27 = var40 + MathHelper.clamp_double(var42 / 70.0D, 0.0D, 1.0D) * var22;
            double var12 = MathHelper.incValue(var27, this.getIncrement());
            this.setSlider(var12);
            EventManager.call(new SettingEvent(this.parentModule, this.getName(), this.sliderNumber));
         case 6:
            String var17 = (String)this.textBoxValue.get();
            if(this.textHovered && Keyboard.isKeyDown(14) && this.backspace.delay(100.0D) && var17.length() >= 1) {
               this.textBoxValue.set(var17.substring(0, var17.length() - 1));
               this.backspace.reset();
            }

            Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.drawString(this.displayName, (float)this.x, (float)this.y, -1);
            RenderUtils.drawBorderedRect((float)(this.widthm - 70), (float)(this.y - 2), (float)this.widthm, (float)(this.y + 8), 1.0F, this.isTextHovered()?var5:1677721600, var4?(new Color(22, 23, 26)).getRGB():-13684945);
            if(var17.isEmpty()) {
               if(this.isTextHovered() && this.caretTimer.delay(500.0D)) {
                  Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString("|", (float)(this.widthm - 68), (float)((double)this.y - 0.5D), 1694498815);
                  if(this.caretTimer.delay(1000.0D)) {
                     this.caretTimer.reset();
                  }
               }

               Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString(this.hint, (float)(this.widthm - 65), (float)this.y, 1694498815);
            }

            if(Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.stringWidth(var17) > 65) {
               Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString(Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.trimStringToWidth(var17, 60, true), (float)(this.widthm - 68), (float)this.y, -1);
            }

            Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString(var17, (float)(this.widthm - 68), (float)this.y, -1);
         case 7:
            Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.drawString(this.displayName, (float)this.x, (float)this.y, -1);
            RenderUtils.drawBorderedRect((float)(this.widthm - 70), (float)(this.y - 2), (float)this.widthm, (float)(this.y + 8), 1.0F, this.isTextHovered()?var5:1677721600, var4?(new Color(22, 23, 26)).getRGB():-13684945);
            Integer var18 = this.color.get();
            int var20 = var18.intValue() >> 16 & 255;
            int var23 = var18.intValue() >> 8 & 255;
            int var25 = var18.intValue() & 255;
            float[] var28 = Color.RGBtoHSB(var20, var23, var25, new float[3]);
            if(this.colorPickerRainbow) {
               this.separatorHue = (this.separatorHue + 0.35F) % 70.0F;
            }

            if(this.dragging) {
               double var11 = MathHelper.clamp_double((double)(var1 - this.widthm + 70), 0.35D, 70.0D);
               float var13 = (float)(var11 / 70.0D);
               switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$Setting$ColorPickerMode[this.colorPickerMode.ordinal()]) {
               case 1:
                  this.separatorHue = (float)((int)var11);
                  this.color.set(Integer.valueOf(Color.getHSBColor(var13, var28[1], var28[2]).getRGB()));
               case 2:
                  this.separatorSaturation = (float)((int)var11);
                  this.color.set(Integer.valueOf(Color.getHSBColor(var28[0], var13, var28[2]).getRGB()));
               case 3:
                  this.separatorBrightness = (float)((int)var11);
                  this.color.set(Integer.valueOf(Color.getHSBColor(var28[0], var28[1], var13).getRGB()));
               }
            }

            switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$Setting$ColorPickerMode[this.colorPickerMode.ordinal()]) {
            case 1:
               byte var29 = 70;
               int var32 = 0;
               if(var32 < var29) {
                  Gui.drawRect(this.widthm - 70 + var32, this.y - 2, this.widthm - 69 + var32, this.y + 8, Color.getHSBColor((float)var32 / (float)var29, var28[1], var28[2]).getRGB());
                  ++var32;
               }

               Gui.drawRect((double)((float)(this.widthm - 70) + this.separatorHue), (double)(this.y - 2), (double)((float)(this.widthm - 69) + this.separatorHue), (double)(this.y + 8), -16777216);
            case 2:
               byte var30 = 70;
               int var34 = 0;
               if(var34 < var30) {
                  Gui.drawRect(this.widthm - 70 + var34, this.y - 2, this.widthm - 69 + var34, this.y + 8, Color.getHSBColor(var28[0], (float)var34 / (float)var30, var28[2]).getRGB());
                  ++var34;
               }

               Gui.drawRect((double)((float)(this.widthm - 70) + this.separatorSaturation), (double)(this.y - 2), (double)((float)(this.widthm - 69) + this.separatorSaturation), (double)(this.y + 8), -16777216);
            case 3:
               byte var31 = 70;
               int var36 = 0;
               if(var36 < var31) {
                  Gui.drawRect(this.widthm - 70 + var36, this.y - 2, this.widthm - 69 + var36, this.y + 8, Color.getHSBColor(var28[0], var28[1], (float)var36 / (float)var31).getRGB());
                  ++var36;
               }

               Gui.drawRect((double)((float)(this.widthm - 70) + this.separatorBrightness), (double)(this.y - 2), (double)((float)(this.widthm - 69) + this.separatorBrightness), (double)(this.y + 8), -16777216);
            }
         default:
            throw new IllegalStateException("Unexpected value: " + this.settingType);
         }
      } catch (Throwable var14) {
         var14.printStackTrace();
      }

   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      boolean var4 = SettingType.c();
      if(var3 == 0) {
         switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.settingType.ordinal()]) {
         case 2:
            if(this.toggleOpened(var1, var2)) {
               ;
            }

            if(!this.opened || var1 < this.widthm - 70 || var1 > this.widthm) {
               break;
            }

            int var5 = 0;
            if(var5 < this.comboBoxValue.getAcceptableValues().size()) {
               int var6 = this.y + 10 + var5 * 11;
               if(var2 >= var6 && var2 <= var6 + 11) {
                  this.comboBoxValue.set((String)this.comboBoxValue.getAcceptableValues().get(var5));
                  this.opened = false;
                  EventManager.call(new SettingEvent(this.parentModule, this.getName(), this.comboBoxValue));
                  return true;
               }

               ++var5;
            }
         case 3:
            if(this.toggleOpened(var1, var2) || !this.opened || var1 < this.widthm - 70 || var1 > this.widthm) {
               break;
            }

            List var10 = this.selectBox.getAcceptableValues();
            int var12 = 0;
            if(var12 < var10.size()) {
               int var7 = this.y + 10 + var12 * 11;
               if(var2 >= var7 && var2 <= var7 + 11) {
                  String var8 = (String)var10.get(var12);
                  if(this.selectBox.contains(var8)) {
                     this.selectBox.remove(var8);
                  }

                  this.selectBox.add(var8);
                  EventManager.call(new SettingEvent(this.parentModule, this.getName(), this.selectBox));
                  return true;
               }

               ++var12;
            }
         case 4:
            if(!this.isHovered(var1, var2)) {
               break;
            }

            this.checkBoxProperty.invert();
            EventManager.call(new SettingEvent(this.parentModule, this.getName(), this.getDisplayName(), this.checkBoxProperty));
         case 5:
            if(!this.isHovered(var1, var2)) {
               break;
            }

            this.dragging = true;
         case 6:
            if(this.isHovered(var1, var2)) {
               this.textHovered = !this.textHovered;
            }

            if(!this.textHovered) {
               break;
            }

            this.textHovered = false;
         case 7:
            if(this.isHovered(var1, var2)) {
               this.dragging = true;
            }
         }
      }

      if(var3 == 1 && this.settingType == SettingType.COLOR_PICKER && this.isHovered(var1, var2)) {
         Setting$ColorPickerMode[] var11 = Setting$ColorPickerMode.values();
         int var14 = (Arrays.binarySearch(var11, this.colorPickerMode) + 1) % var11.length;
         if(this.colorPickedDisabledModes == null) {
            this.colorPickerMode = var11[var14];
         }

         if(!this.colorPickedDisabledModes.isEmpty()) {
            int var16 = 0;
            if(var16 < var11.length - 1) {
               Setting$ColorPickerMode var15 = var11[(var14 + var16) % var11.length];
               if(!this.colorPickedDisabledModes.contains(var15)) {
                  this.colorPickerMode = var11[(var16 + var14) % var11.length];
               }

               ++var16;
            }
         }
      }

      return false;
   }

   public Set getColorPickedDisabledModes() {
      return this.colorPickedDisabledModes;
   }

   public void setColorPickerMode(Setting$ColorPickerMode var1) {
      this.colorPickerMode = var1;
   }

   private boolean toggleOpened(int var1, int var2) {
      boolean var3 = SettingType.b();
      if(!this.isHovered(var1, var2)) {
         return false;
      } else {
         if(this.opened = !this.opened) {
            Manager.getSettingList().stream().filter(this::lambda$toggleOpened$1).forEach(Setting::lambda$toggleOpened$2);
         }

         return true;
      }
   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.dragging = false;
   }

   private boolean isHovered(int var1, int var2) {
      boolean var3 = SettingType.c();
      switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.settingType.ordinal()]) {
      case 2:
      case 3:
      case 6:
      case 7:
         return var1 >= this.widthm - 70 && var1 <= this.widthm && var2 >= this.y - 2 && var2 <= this.y + 8;
      case 4:
         return var1 >= this.widthm - 10 && var1 <= this.widthm && var2 >= this.y - 2 && var2 <= this.y + 8;
      case 5:
         boolean var4;
         if(var1 >= this.widthm - 70) {
            double var10000 = (double)var1;
            double var10001 = (double)(this.widthm - 70);
            this.getClass();
            if(var10000 <= var10001 + 70.0D && var2 >= this.y + 2 && var2 <= this.y + 8) {
               var4 = true;
               return var4;
            }
         }

         var4 = false;
         return var4;
      default:
         return false;
      }
   }

   public void keyTyped(char var1, int var2) {
      boolean var3 = SettingType.b();
      if(this.settingType == SettingType.TEXTBOX) {
         if(!this.textHovered) {
            return;
         }

         if(var2 == 1) {
            this.textHovered = false;
         }

         if(var2 == 14 || var2 == 157 || var2 == 29 || var2 == 54 || var2 == 42 || var2 == 15 || var2 == 58 || var2 == 211 || var2 == 199 || var2 == 210 || var2 == 200 || var2 == 208 || var2 == 205 || var2 == 203 || var2 == 56 || var2 == 184 || var2 == 197 || var2 == 70 || var2 == 207 || var2 == 201 || var2 == 209 || var2 == 221 || var2 == 59 || var2 == 60 || var2 == 61 || var2 == 62 || var2 == 63 || var2 == 64 || var2 == 65 || var2 == 66 || var2 == 67 || var2 == 68 || var2 == 87 || var2 == 88) {
            return;
         }

         this.textBoxValue.append(Character.valueOf(var1));
      }

      if(var2 == 1 && (this.settingType == SettingType.SELECTBOX || this.settingType == SettingType.COMBOBOX) && this.opened) {
         this.opened = false;
      }

   }

   public boolean isInsideMenu() {
      SettingType.b();
      DiscordGUI var2 = Novoline.getInstance().getDiscordGUI();
      return this.y <= var2.getYCoordinate() + var2.getHeight() - 10 && this.y >= var2.getYCoordinate() + 23;
   }

   public Object getValue() {
      switch(Setting$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[this.settingType.ordinal()]) {
      case 2:
         return this.comboBoxValue;
      case 3:
         return this.selectBox;
      case 4:
         return this.checkBoxProperty;
      case 5:
         return this.decimalFormat.format(this.getDouble());
      default:
         return null;
      }
   }

   public String getName() {
      return this.name;
   }

   public SettingType getSettingType() {
      return this.settingType;
   }

   public boolean isColorPickerRainbow() {
      return this.colorPickerRainbow;
   }

   public float getSeparatorHue() {
      return this.separatorHue;
   }

   public float getSeparatorBrightness() {
      return this.separatorBrightness;
   }

   public float getSeparatorSaturation() {
      return this.separatorSaturation;
   }

   public void setSeparatorBrightness(float var1) {
      this.separatorBrightness = var1;
   }

   public void setSeparatorHue(float var1) {
      this.separatorHue = var1;
   }

   public void setSeparatorSaturation(float var1) {
      this.separatorSaturation = var1;
   }

   public Setting$ColorPickerMode getColorPickerMode() {
      return this.colorPickerMode;
   }

   public ColorProperty getColor() {
      return this.color;
   }

   public AbstractNumberProperty getSliderNumber() {
      return this.sliderNumber;
   }

   public AbstractModule getParentModule() {
      return this.parentModule;
   }

   public String getTextBoxValue() {
      return (String)this.textBoxValue.get();
   }

   public StringProperty getTextBoxValue2() {
      return this.textBoxValue;
   }

   public void setTextBoxValue(String var1) {
      this.textBoxValue.set(var1);
   }

   public BooleanProperty getCheckBoxProperty() {
      return this.checkBoxProperty;
   }

   public Boolean getCheckBoxValue() {
      return (Boolean)this.checkBoxProperty.get();
   }

   public double getDouble() {
      return (double)Math.round(((Number)this.sliderNumber.get()).doubleValue() / this.increment) * this.increment;
   }

   public float getFloat() {
      return (float)this.getDouble();
   }

   public int getInt() {
      return (int)this.getDouble();
   }

   public long getLong() {
      return (long)this.getDouble();
   }

   public String getComboBoxValue() {
      return (String)this.comboBoxValue.get();
   }

   public StringProperty getComboBoxProperty() {
      return this.comboBoxValue;
   }

   public void setComboBoxValue(String var1) {
      this.comboBoxValue.set(var1);
   }

   public StringProperty getComboBox() {
      return this.comboBoxValue;
   }

   public void setSliderValue(Number var1) {
      boolean var2 = SettingType.c();
      if(this.sliderNumber instanceof IntProperty) {
         this.sliderNumber.set((Number)Integer.valueOf(var1.intValue()));
      }

      if(this.sliderNumber instanceof DoubleProperty) {
         this.sliderNumber.set((Number)Double.valueOf(var1.doubleValue()));
      }

      if(this.sliderNumber instanceof FloatProperty) {
         this.sliderNumber.set((Number)Float.valueOf(var1.floatValue()));
      }

      if(this.sliderNumber instanceof LongProperty) {
         this.sliderNumber.set((Number)Long.valueOf(var1.longValue()));
      }

   }

   public void setSelectBoxValue(boolean var1) {
      this.checkBoxProperty.set(Boolean.valueOf(var1));
   }

   public List getSelectBox() {
      return (List)this.selectBox.get();
   }

   public ListProperty getSelectBoxProperty() {
      return this.selectBox;
   }

   public int getOffset() {
      return this.offset;
   }

   public void setOffset(int var1) {
      this.offset = var1;
   }

   public boolean isOpened() {
      return this.opened;
   }

   public void setOpened(boolean var1) {
      this.opened = var1;
   }

   public boolean isTextHovered() {
      return this.textHovered;
   }

   public void setTextHovered(boolean var1) {
      this.textHovered = var1;
   }

   public int getY() {
      return this.y;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDragging(boolean var1) {
      this.dragging = var1;
   }

   public Color getColorPickerColor() {
      return this.color.getAwtColor();
   }

   public void setColorPickerColor(int var1) {
      this.color.set(Integer.valueOf(var1));
   }

   public double getIncrement() {
      return this.increment;
   }

   public float[] getColorPickerHSB() {
      return this.color.getHSB();
   }

   public float[] getHSBforStupidMinecraft() {
      SettingType.c();
      Integer var2 = this.color.get();
      return var2 == null?Color.RGBtoHSB(0, 0, 0, new float[3]):Color.RGBtoHSB(var2.intValue() & 255, var2.intValue() >> 8 & 255, var2.intValue() >> 16 & 255, new float[3]);
   }

   public KeyBindProperty getKeyBindValue() {
      return this.keyBindValue;
   }

   public boolean isListening() {
      return this.listening;
   }

   public void setListening(boolean var1) {
      this.listening = var1;
   }

   public int getColorPickerInteger() {
      return this.color.get().intValue();
   }

   public Consumer I() {
      return this.l;
   }

   public void setSlider(double var1) {
      this.setSliderValue(Double.valueOf(MathHelper.clamp_double(var1, this.sliderNumber.getMinimum().doubleValue(), this.sliderNumber.getMaximum().doubleValue())));
   }

   public GuiScreen N() {
      return this.K;
   }

   private static void lambda$toggleOpened$2(Setting var0) {
      var0.opened = false;
   }

   private boolean lambda$toggleOpened$1(Setting var1) {
      boolean var2 = SettingType.c();
      return (var1.settingType == SettingType.COMBOBOX || var1.settingType == SettingType.SELECTBOX) && !var1.name.equals(this.name);
   }

   private static boolean lambda$update$0(Setting var0) {
      boolean var1 = SettingType.c();
      return var0.getSupplier() != null?((Boolean)var0.getSupplier().get()).booleanValue():true;
   }

   public static void b(boolean var0) {
      e = var0;
   }

   public static boolean q() {
      return e;
   }

   public static boolean L() {
      boolean var0 = q();
      return true;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }

   static {
      b(false);
   }
}

/*   1:    */ package me.connorm.Nodus.ui.gui;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.awt.Rectangle;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.concurrent.atomic.AtomicBoolean;
/*   8:    */ import me.connorm.Nodus.Nodus;
/*   9:    */ import me.connorm.Nodus.command.NodusCommandManager;
/*  10:    */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  11:    */ import me.connorm.Nodus.module.NodusModuleManager;
/*  12:    */ import me.connorm.Nodus.module.core.Category;
/*  13:    */ import me.connorm.Nodus.module.core.NodusModule;
/*  14:    */ import me.connorm.Nodus.module.value.NodusValue;
/*  15:    */ import me.connorm.Nodus.module.value.NodusValueManager;
/*  16:    */ import me.connorm.lib.EventManager;
/*  17:    */ import me.connorm.lib.EventTarget;
/*  18:    */ import net.minecraft.client.Minecraft;
/*  19:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  20:    */ import net.minecraft.client.gui.FontRenderer;
/*  21:    */ import net.minecraft.client.settings.GameSettings;
/*  22:    */ import net.minecraft.entity.player.EntityPlayer;
/*  23:    */ import org.darkstorm.minecraft.gui.AbstractGuiManager;
/*  24:    */ import org.darkstorm.minecraft.gui.component.Button;
/*  25:    */ import org.darkstorm.minecraft.gui.component.Frame;
/*  26:    */ import org.darkstorm.minecraft.gui.component.Slider;
/*  27:    */ import org.darkstorm.minecraft.gui.component.basic.BasicButton;
/*  28:    */ import org.darkstorm.minecraft.gui.component.basic.BasicCheckButton;
/*  29:    */ import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
/*  30:    */ import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
/*  31:    */ import org.darkstorm.minecraft.gui.layout.Constraint;
/*  32:    */ import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
/*  33:    */ import org.darkstorm.minecraft.gui.layout.GridLayoutManager.HorizontalGridConstraint;
/*  34:    */ import org.darkstorm.minecraft.gui.listener.ButtonListener;
/*  35:    */ import org.darkstorm.minecraft.gui.listener.SliderListener;
/*  36:    */ import org.darkstorm.minecraft.gui.theme.ComponentUI;
/*  37:    */ import org.darkstorm.minecraft.gui.theme.Theme;
/*  38:    */ 
/*  39:    */ public final class NodusGuiManager
/*  40:    */   extends AbstractGuiManager
/*  41:    */ {
/*  42:    */   private final AtomicBoolean setup;
/*  43:    */   public BasicFrame infoFrame;
/*  44:    */   private BasicFrame optionsFrame;
/*  45:    */   
/*  46:    */   private class ModuleFrame
/*  47:    */     extends BasicFrame
/*  48:    */   {
/*  49:    */     private ModuleFrame() {}
/*  50:    */     
/*  51:    */     private ModuleFrame(String title)
/*  52:    */     {
/*  53: 40 */       super();
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57: 49 */   public boolean isNewUI = false;
/*  58: 51 */   public boolean isClickGui = true;
/*  59:    */   
/*  60:    */   public NodusGuiManager()
/*  61:    */   {
/*  62: 55 */     this.setup = new AtomicBoolean();
/*  63: 56 */     EventManager.register(this);
/*  64:    */   }
/*  65:    */   
/*  66:    */   @EventTarget
/*  67:    */   public void updatePlayer(EventPlayerUpdate theEvent)
/*  68:    */   {
/*  69: 62 */     String[] playerDirection = { "West", "North", "East", "South" };
/*  70: 63 */     double directionYaw = (theEvent.getPlayer().rotationYaw + 22.5D) % 360.0D;
/*  71: 64 */     if (directionYaw < 0.0D) {
/*  72: 66 */       directionYaw += 360.0D;
/*  73:    */     }
/*  74: 68 */     this.infoFrame.setTitle(
/*  75: 69 */       String.format("X: %.0f", new Object[] { Double.valueOf(Minecraft.getMinecraft().thePlayer.posX) }) + " " + 
/*  76: 70 */       String.format("Y: %.0f", new Object[] { Double.valueOf(Minecraft.getMinecraft().thePlayer.posY) }) + " " + 
/*  77: 71 */       String.format("Z: %.0f", new Object[] { Double.valueOf(Minecraft.getMinecraft().thePlayer.posZ) }) + " | " + 
/*  78: 72 */       playerDirection[((int)(directionYaw / 90.0D))]);
/*  79:    */     
/*  80: 74 */     this.infoFrame.setWidth(Nodus.theNodus.getMinecraft().fontRenderer.getStringWidth(this.infoFrame.getTitle()) + 20);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setup()
/*  84:    */   {
/*  85: 79 */     if (!this.setup.compareAndSet(false, true)) {
/*  86: 80 */       return;
/*  87:    */     }
/*  88: 82 */     createOptionsFrame();
/*  89: 83 */     createInfoFrame();
/*  90:    */     
/*  91:    */ 
/*  92: 86 */     Map<Category, ModuleFrame> categoryFrames = new HashMap();
/*  93:    */     int yVal;
/*  94: 87 */     for (NodusModule module : Nodus.theNodus.moduleManager.getModules()) {
/*  95: 89 */       if (module.getCategory() != Category.OTHER)
/*  96:    */       {
/*  97: 91 */         ModuleFrame frame = (ModuleFrame)categoryFrames.get(module.getCategory());
/*  98: 92 */         if (frame == null)
/*  99:    */         {
/* 100: 93 */           String name = module.getCategory().name().toLowerCase();
/* 101: 94 */           name = Character.toUpperCase(name.charAt(0)) + 
/* 102: 95 */             name.substring(1);
/* 103: 96 */           frame = new ModuleFrame(name, null);
/* 104: 97 */           frame.setTheme(this.theme);
/* 105: 98 */           frame.setLayoutManager(new GridLayoutManager(1, 0));
/* 106: 99 */           frame.setVisible(true);
/* 107:100 */           frame.setClosable(false);
/* 108:101 */           frame.setMinimized(true);
/* 109:102 */           frame.setPinnable(false);
/* 110:103 */           int xVal = 0;
/* 111:104 */           yVal = 20;
/* 112:105 */           if (name.equalsIgnoreCase("Combat")) {
/* 113:107 */             xVal = 4;
/* 114:    */           }
/* 115:109 */           if (name.equalsIgnoreCase("Display")) {
/* 116:111 */             xVal = 108;
/* 117:    */           }
/* 118:113 */           if (name.equalsIgnoreCase("Player")) {
/* 119:115 */             xVal = 214;
/* 120:    */           }
/* 121:117 */           if (name.equalsIgnoreCase("World")) {
/* 122:119 */             xVal = 320;
/* 123:    */           }
/* 124:121 */           frame.setX(xVal);
/* 125:122 */           frame.setY(yVal);
/* 126:123 */           addFrame(frame);
/* 127:124 */           categoryFrames.put(module.getCategory(), frame);
/* 128:    */         }
/* 129:126 */         BasicButton button = new BasicButton(module.getName());
/* 130:127 */         button.addButtonListener(new ButtonListener()
/* 131:    */         {
/* 132:    */           public void onButtonPress(Button button)
/* 133:    */           {
/* 134:131 */             Nodus.theNodus.commandManager.runCommands(Nodus.theNodus.commandManager.commandPrefix + button.getCommand());
/* 135:    */           }
/* 136:133 */         });
/* 137:134 */         frame.add(button, new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 138:    */       }
/* 139:    */     }
/* 140:138 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 141:139 */     Dimension maxSize = recalculateSizes();
/* 142:140 */     int offsetX = 5;int offsetY = 5;
/* 143:141 */     int scale = minecraft.gameSettings.guiScale;
/* 144:142 */     if (scale == 0) {
/* 145:143 */       scale = 1000;
/* 146:    */     }
/* 147:144 */     int scaleFactor = 0;
/* 148:145 */     while ((scaleFactor < scale) && (minecraft.displayWidth / (scaleFactor + 1) >= 320) && (minecraft.displayHeight / (scaleFactor + 1) >= 240)) {
/* 149:146 */       scaleFactor++;
/* 150:    */     }
/* 151:147 */     for (Frame frame : getFrames())
/* 152:    */     {
/* 153:150 */       offsetX += maxSize.width + 5;
/* 154:151 */       if (offsetX + maxSize.width + 5 > minecraft.displayWidth / scaleFactor)
/* 155:    */       {
/* 156:152 */         offsetX = 5;
/* 157:153 */         offsetY += maxSize.height + 5;
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   private void createOptionsFrame()
/* 163:    */   {
/* 164:160 */     Theme theme = getTheme();
/* 165:161 */     this.optionsFrame = new BasicFrame("Options");
/* 166:162 */     this.optionsFrame.setTheme(theme);
/* 167:    */     
/* 168:164 */     this.optionsFrame.add(new BasicCheckButton("Primary", 1), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 169:    */     
/* 170:166 */     this.optionsFrame.add(new BasicCheckButton("Secondary", 2), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 171:    */     
/* 172:168 */     this.optionsFrame.add(new BasicCheckButton("Chest", 6), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 173:    */     
/* 174:170 */     this.optionsFrame.add(new BasicCheckButton("Aggressive", 7), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 175:    */     
/* 176:172 */     this.optionsFrame.add(new BasicCheckButton("Passive", 8), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 177:    */     
/* 178:174 */     this.optionsFrame.add(new BasicCheckButton("Player", 9), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 179:    */     
/* 180:176 */     this.optionsFrame.add(new BasicCheckButton("Tab Gui", 3), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 181:    */     
/* 182:178 */     this.optionsFrame.add(new BasicCheckButton("Click Gui", 4), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 183:    */     
/* 184:180 */     this.optionsFrame.add(new BasicCheckButton("2.0 Highlight", 5), new Constraint[] { GridLayoutManager.HorizontalGridConstraint.LEFT });
/* 185:    */     
/* 186:182 */     this.optionsFrame.setLayoutManager(new GridLayoutManager(1, 0));
/* 187:183 */     this.optionsFrame.setX(4);
/* 188:184 */     this.optionsFrame.setY(50);
/* 189:185 */     Dimension defaultDimension = theme.getUIForComponent(this.optionsFrame).getDefaultSize(this.optionsFrame);
/* 190:186 */     this.optionsFrame.setWidth(defaultDimension.width);
/* 191:187 */     this.optionsFrame.setHeight(defaultDimension.height);
/* 192:188 */     this.optionsFrame.layoutChildren();
/* 193:189 */     this.optionsFrame.setVisible(true);
/* 194:190 */     this.optionsFrame.setMinimized(true);
/* 195:191 */     this.optionsFrame.setClosable(false);
/* 196:192 */     this.optionsFrame.setPinnable(false);
/* 197:193 */     addFrame(this.optionsFrame);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void createInfoFrame()
/* 201:    */   {
/* 202:198 */     this.infoFrame = new BasicFrame();
/* 203:199 */     this.infoFrame.setTheme(getTheme());
/* 204:200 */     this.infoFrame.setX(108);
/* 205:201 */     this.infoFrame.setY(50);
/* 206:202 */     Dimension defaultDimension = this.theme.getUIForComponent(this.infoFrame).getDefaultSize(this.infoFrame);
/* 207:203 */     this.infoFrame.setWidth(defaultDimension.width);
/* 208:204 */     this.infoFrame.setHeight(defaultDimension.height);
/* 209:205 */     this.infoFrame.layoutChildren();
/* 210:206 */     this.infoFrame.setVisible(true);
/* 211:207 */     this.infoFrame.setClosable(false);
/* 212:208 */     this.infoFrame.setMinimizable(false);
/* 213:209 */     this.infoFrame.setPinnable(true);
/* 214:210 */     addFrame(this.infoFrame);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void createValuesFrame()
/* 218:    */   {
/* 219:215 */     Frame valueFrame = new BasicFrame();
/* 220:216 */     valueFrame.setTheme(getTheme());
/* 221:    */     
/* 222:218 */     Slider forceFieldAPS = new BasicSlider();
/* 223:219 */     final NodusValue forceFieldAPSValue = Nodus.theNodus.valueManager.forceFieldAPS;
/* 224:220 */     forceFieldAPS.setValue(forceFieldAPSValue.getValue());
/* 225:221 */     forceFieldAPS.setIncrement(0.5D);
/* 226:222 */     forceFieldAPS.setMinimumValue(forceFieldAPSValue.getMinimumValue());
/* 227:223 */     forceFieldAPS.setMaximumValue(forceFieldAPSValue.getMaximumValue());
/* 228:224 */     forceFieldAPS.setEnabled(true);
/* 229:225 */     forceFieldAPS.addSliderListener(new SliderListener()
/* 230:    */     {
/* 231:    */       public void onSliderValueChanged(Slider apsSlider)
/* 232:    */       {
/* 233:230 */         forceFieldAPSValue.setValue((float)apsSlider.getValue());
/* 234:    */       }
/* 235:233 */     });
/* 236:234 */     valueFrame.add(forceFieldAPS, new Constraint[] { GridLayoutManager.HorizontalGridConstraint.FILL });
/* 237:    */     
/* 238:236 */     valueFrame.setX(320);
/* 239:237 */     valueFrame.setY(50);
/* 240:238 */     Dimension defaultDimension = this.theme.getUIForComponent(valueFrame).getDefaultSize(valueFrame);
/* 241:239 */     valueFrame.setWidth(defaultDimension.width);
/* 242:240 */     valueFrame.setHeight(defaultDimension.height);
/* 243:241 */     valueFrame.layoutChildren();
/* 244:242 */     valueFrame.setVisible(true);
/* 245:243 */     valueFrame.setClosable(false);
/* 246:244 */     valueFrame.setMinimizable(false);
/* 247:245 */     valueFrame.setPinnable(true);
/* 248:246 */     addFrame(valueFrame);
/* 249:    */   }
/* 250:    */   
/* 251:    */   protected void resizeComponents() {}
/* 252:    */   
/* 253:    */   public void reSetComponents(boolean newTheme) {}
/* 254:    */   
/* 255:    */   private Dimension recalculateSizes()
/* 256:    */   {
/* 257:259 */     Frame[] frames = getFrames();
/* 258:260 */     int maxWidth = 50;int maxHeight = 0;
/* 259:261 */     for (Frame frame : frames)
/* 260:    */     {
/* 261:262 */       Dimension defaultDimension = frame.getTheme().getUIForComponent(frame).getDefaultSize(frame);
/* 262:263 */       maxWidth = Math.max(maxWidth, defaultDimension.width);
/* 263:264 */       frame.setHeight(defaultDimension.height);
/* 264:265 */       if (frame.isMinimized()) {
/* 265:267 */         for (Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame)) {
/* 266:268 */           maxHeight = Math.max(maxHeight, area.height);
/* 267:    */         }
/* 268:    */       } else {
/* 269:270 */         maxHeight = Math.max(maxHeight, defaultDimension.height);
/* 270:    */       }
/* 271:    */     }
/* 272:272 */     for (Frame frame : frames)
/* 273:    */     {
/* 274:273 */       frame.setWidth(maxWidth + 25);
/* 275:274 */       frame.layoutChildren();
/* 276:    */     }
/* 277:276 */     return new Dimension(maxWidth, maxHeight);
/* 278:    */   }
/* 279:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.NodusGuiManager
 * JD-Core Version:    0.7.0.1
 */
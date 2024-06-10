/*   1:    */ package org.darkstorm.minecraft.gui.component;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.concurrent.CopyOnWriteArrayList;
/*   9:    */ import org.darkstorm.minecraft.gui.listener.ComponentListener;
/*  10:    */ import org.darkstorm.minecraft.gui.theme.ComponentUI;
/*  11:    */ import org.darkstorm.minecraft.gui.theme.Theme;
/*  12:    */ 
/*  13:    */ public abstract class AbstractComponent
/*  14:    */   implements Component
/*  15:    */ {
/*  16: 11 */   private Container parent = null;
/*  17:    */   private Theme theme;
/*  18: 14 */   protected Rectangle area = new Rectangle(0, 0, 0, 0);
/*  19:    */   protected ComponentUI ui;
/*  20:    */   protected Color foreground;
/*  21:    */   protected Color background;
/*  22: 17 */   protected boolean enabled = true;
/*  23: 17 */   protected boolean visible = true;
/*  24: 19 */   private List<ComponentListener> listeners = new CopyOnWriteArrayList();
/*  25:    */   
/*  26:    */   public void render()
/*  27:    */   {
/*  28: 22 */     if (this.ui == null) {
/*  29: 23 */       return;
/*  30:    */     }
/*  31: 24 */     this.ui.render(this);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void update()
/*  35:    */   {
/*  36: 29 */     if (this.ui == null) {
/*  37: 30 */       return;
/*  38:    */     }
/*  39: 31 */     this.ui.handleUpdate(this);
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected ComponentUI getUI()
/*  43:    */   {
/*  44: 35 */     return this.theme.getUIForComponent(this);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void onMousePress(int x, int y, int button)
/*  48:    */   {
/*  49: 40 */     if (this.ui != null) {
/*  50: 41 */       for (Rectangle area : this.ui.getInteractableRegions(this)) {
/*  51: 42 */         if (area.contains(x, y))
/*  52:    */         {
/*  53: 43 */           this.ui.handleInteraction(this, new Point(x, y), button);
/*  54: 44 */           break;
/*  55:    */         }
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void onMouseRelease(int x, int y, int button) {}
/*  61:    */   
/*  62:    */   public Theme getTheme()
/*  63:    */   {
/*  64: 55 */     return this.theme;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setTheme(Theme theme)
/*  68:    */   {
/*  69: 59 */     Theme oldTheme = this.theme;
/*  70: 60 */     this.theme = theme;
/*  71: 61 */     if (theme == null)
/*  72:    */     {
/*  73: 62 */       this.ui = null;
/*  74: 63 */       this.foreground = null;
/*  75: 64 */       this.background = null;
/*  76: 65 */       return;
/*  77:    */     }
/*  78: 68 */     this.ui = getUI();
/*  79:    */     boolean changeArea;
/*  80:    */     boolean changeArea;
/*  81: 70 */     if (oldTheme != null)
/*  82:    */     {
/*  83: 71 */       Dimension defaultSize = oldTheme.getUIForComponent(this).getDefaultSize(this);
/*  84: 72 */       changeArea = (this.area.width == defaultSize.width) && (this.area.height == defaultSize.height);
/*  85:    */     }
/*  86:    */     else
/*  87:    */     {
/*  88: 74 */       changeArea = this.area.equals(new Rectangle(0, 0, 0, 0));
/*  89:    */     }
/*  90: 75 */     if (changeArea)
/*  91:    */     {
/*  92: 76 */       Dimension defaultSize = this.ui.getDefaultSize(this);
/*  93: 77 */       this.area = new Rectangle(this.area.x, this.area.y, defaultSize.width, defaultSize.height);
/*  94:    */     }
/*  95: 79 */     this.foreground = this.ui.getDefaultForegroundColor(this);
/*  96: 80 */     this.background = this.ui.getDefaultBackgroundColor(this);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getX()
/* 100:    */   {
/* 101: 84 */     return this.area.x;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getY()
/* 105:    */   {
/* 106: 88 */     return this.area.y;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getWidth()
/* 110:    */   {
/* 111: 92 */     return this.area.width;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getHeight()
/* 115:    */   {
/* 116: 96 */     return this.area.height;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setX(int x)
/* 120:    */   {
/* 121:100 */     this.area.x = x;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setY(int y)
/* 125:    */   {
/* 126:104 */     this.area.y = y;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setWidth(int width)
/* 130:    */   {
/* 131:108 */     this.area.width = width;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setHeight(int height)
/* 135:    */   {
/* 136:112 */     this.area.height = height;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Color getBackgroundColor()
/* 140:    */   {
/* 141:117 */     return this.background;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Color getForegroundColor()
/* 145:    */   {
/* 146:122 */     return this.foreground;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setBackgroundColor(Color color)
/* 150:    */   {
/* 151:127 */     this.background = color;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setForegroundColor(Color color)
/* 155:    */   {
/* 156:132 */     this.foreground = color;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Point getLocation()
/* 160:    */   {
/* 161:136 */     return this.area.getLocation();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Dimension getSize()
/* 165:    */   {
/* 166:140 */     return this.area.getSize();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Rectangle getArea()
/* 170:    */   {
/* 171:144 */     return this.area;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Container getParent()
/* 175:    */   {
/* 176:148 */     return this.parent;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setParent(Container parent)
/* 180:    */   {
/* 181:152 */     if ((!parent.hasChild(this)) || ((this.parent != null) && (this.parent.hasChild(this)))) {
/* 182:153 */       throw new IllegalArgumentException();
/* 183:    */     }
/* 184:154 */     this.parent = parent;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void resize()
/* 188:    */   {
/* 189:158 */     Dimension defaultDimension = this.ui.getDefaultSize(this);
/* 190:159 */     setWidth(defaultDimension.width);
/* 191:160 */     setHeight(defaultDimension.height);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean isEnabled()
/* 195:    */   {
/* 196:164 */     return this.enabled;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setEnabled(boolean enabled)
/* 200:    */   {
/* 201:168 */     if ((this.parent != null) && (!this.parent.isEnabled())) {
/* 202:169 */       this.enabled = false;
/* 203:    */     } else {
/* 204:171 */       this.enabled = enabled;
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isVisible()
/* 209:    */   {
/* 210:175 */     return this.visible;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setVisible(boolean visible)
/* 214:    */   {
/* 215:179 */     if ((this.parent != null) && (!this.parent.isVisible())) {
/* 216:180 */       this.visible = false;
/* 217:    */     } else {
/* 218:182 */       this.visible = visible;
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected void addListener(ComponentListener listener)
/* 223:    */   {
/* 224:186 */     synchronized (this.listeners)
/* 225:    */     {
/* 226:187 */       this.listeners.add(listener);
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   protected void removeListener(ComponentListener listener)
/* 231:    */   {
/* 232:192 */     synchronized (this.listeners)
/* 233:    */     {
/* 234:193 */       this.listeners.remove(listener);
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   protected ComponentListener[] getListeners()
/* 239:    */   {
/* 240:198 */     synchronized (this.listeners)
/* 241:    */     {
/* 242:199 */       return (ComponentListener[])this.listeners.toArray(new ComponentListener[this.listeners.size()]);
/* 243:    */     }
/* 244:    */   }
/* 245:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.AbstractComponent
 * JD-Core Version:    0.7.0.1
 */
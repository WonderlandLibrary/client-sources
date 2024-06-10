/*   1:    */ package org.darkstorm.minecraft.gui.component;
/*   2:    */ 
/*   3:    */ import java.awt.Rectangle;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.LinkedHashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.darkstorm.minecraft.gui.layout.BasicLayoutManager;
/*   9:    */ import org.darkstorm.minecraft.gui.layout.Constraint;
/*  10:    */ import org.darkstorm.minecraft.gui.layout.LayoutManager;
/*  11:    */ import org.darkstorm.minecraft.gui.theme.ComponentUI;
/*  12:    */ import org.darkstorm.minecraft.gui.theme.Theme;
/*  13:    */ 
/*  14:    */ public abstract class AbstractContainer
/*  15:    */   extends AbstractComponent
/*  16:    */   implements Container
/*  17:    */ {
/*  18: 12 */   private final Map<Component, Constraint[]> children = new LinkedHashMap();
/*  19: 14 */   private LayoutManager layoutManager = new BasicLayoutManager();
/*  20:    */   
/*  21:    */   public void render()
/*  22:    */   {
/*  23: 18 */     super.render();
/*  24: 20 */     synchronized (this.children)
/*  25:    */     {
/*  26: 21 */       for (Component child : this.children.keySet()) {
/*  27: 22 */         child.render();
/*  28:    */       }
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public LayoutManager getLayoutManager()
/*  33:    */   {
/*  34: 27 */     return this.layoutManager;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setLayoutManager(LayoutManager layoutManager)
/*  38:    */   {
/*  39: 31 */     if (layoutManager == null) {
/*  40: 32 */       layoutManager = new BasicLayoutManager();
/*  41:    */     }
/*  42: 33 */     this.layoutManager = layoutManager;
/*  43:    */     
/*  44: 35 */     layoutChildren();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Component[] getChildren()
/*  48:    */   {
/*  49: 39 */     synchronized (this.children)
/*  50:    */     {
/*  51: 40 */       return (Component[])this.children.keySet().toArray(new Component[this.children.size()]);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void add(Component child, Constraint... constraints)
/*  56:    */   {
/*  57: 45 */     synchronized (this.children)
/*  58:    */     {
/*  59: 46 */       Container parent = child.getParent();
/*  60: 47 */       if ((parent != null) && (parent.hasChild(child))) {
/*  61: 48 */         parent.remove(child);
/*  62:    */       }
/*  63: 49 */       this.children.put(child, constraints);
/*  64: 50 */       if (!this.enabled) {
/*  65: 51 */         child.setEnabled(false);
/*  66:    */       }
/*  67: 52 */       if (!this.visible) {
/*  68: 53 */         child.setVisible(false);
/*  69:    */       }
/*  70: 54 */       child.setParent(this);
/*  71: 55 */       child.setTheme(getTheme());
/*  72:    */       
/*  73: 57 */       layoutChildren();
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Constraint[] getConstraints(Component child)
/*  78:    */   {
/*  79: 63 */     if (child == null) {
/*  80: 64 */       throw new NullPointerException();
/*  81:    */     }
/*  82: 65 */     synchronized (this.children)
/*  83:    */     {
/*  84: 66 */       Constraint[] constraints = (Constraint[])this.children.get(child);
/*  85: 67 */       return constraints != null ? constraints : new Constraint[0];
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Component getChildAt(int x, int y)
/*  90:    */   {
/*  91: 72 */     synchronized (this.children)
/*  92:    */     {
/*  93: 73 */       for (Component child : this.children.keySet()) {
/*  94: 74 */         if (child.getArea().contains(x, y)) {
/*  95: 75 */           return child;
/*  96:    */         }
/*  97:    */       }
/*  98: 76 */       return null;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean remove(Component child)
/* 103:    */   {
/* 104: 81 */     synchronized (this.children)
/* 105:    */     {
/* 106: 82 */       if (this.children.remove(child) != null)
/* 107:    */       {
/* 108: 83 */         layoutChildren();
/* 109: 84 */         return true;
/* 110:    */       }
/* 111: 86 */       return false;
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean hasChild(Component child)
/* 116:    */   {
/* 117: 91 */     synchronized (this.children)
/* 118:    */     {
/* 119: 92 */       return this.children.get(child) != null;
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setTheme(Theme theme)
/* 124:    */   {
/* 125: 98 */     super.setTheme(theme);
/* 126:100 */     synchronized (this.children)
/* 127:    */     {
/* 128:101 */       for (Component child : this.children.keySet()) {
/* 129:102 */         child.setTheme(theme);
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void layoutChildren()
/* 135:    */   {
/* 136:108 */     synchronized (this.children)
/* 137:    */     {
/* 138:109 */       Component[] components = (Component[])this.children.keySet().toArray(
/* 139:110 */         new Component[this.children.size()]);
/* 140:111 */       Rectangle[] areas = new Rectangle[components.length];
/* 141:112 */       for (int i = 0; i < components.length; i++) {
/* 142:113 */         areas[i] = components[i].getArea();
/* 143:    */       }
/* 144:114 */       Constraint[][] allConstraints = (Constraint[][])this.children.values().toArray(
/* 145:115 */         new Constraint[this.children.size()][]);
/* 146:116 */       if (getTheme() != null) {
/* 147:117 */         this.layoutManager.reposition(this.ui.getChildRenderArea(this), areas, 
/* 148:118 */           allConstraints);
/* 149:    */       }
/* 150:119 */       for (Component child : components) {
/* 151:120 */         if ((child instanceof Container)) {
/* 152:121 */           ((Container)child).layoutChildren();
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void onMousePress(int x, int y, int button)
/* 159:    */   {
/* 160:127 */     super.onMousePress(x, y, button);
/* 161:128 */     synchronized (this.children)
/* 162:    */     {
/* 163:129 */       for (Component child : this.children.keySet()) {
/* 164:130 */         if (child.isVisible()) {
/* 165:132 */           if (!child.getArea().contains(x, y))
/* 166:    */           {
/* 167:    */             Rectangle[] arrayOfRectangle;
/* 168:135 */             int j = (arrayOfRectangle = child.getTheme().getUIForComponent(child).getInteractableRegions(child)).length;
/* 169:135 */             for (int i = 0; i < j; i++)
/* 170:    */             {
/* 171:135 */               Rectangle area = arrayOfRectangle[i];
/* 172:136 */               if (area.contains(x - child.getX(), y - child.getY()))
/* 173:    */               {
/* 174:137 */                 child.onMousePress(x - child.getX(), 
/* 175:138 */                   y - child.getY(), button);
/* 176:139 */                 return;
/* 177:    */               }
/* 178:    */             }
/* 179:    */           }
/* 180:    */         }
/* 181:    */       }
/* 182:144 */       for (Component child : this.children.keySet()) {
/* 183:145 */         if (child.isVisible()) {
/* 184:147 */           if (child.getArea().contains(x, y))
/* 185:    */           {
/* 186:148 */             child.onMousePress(x - child.getX(), y - child.getY(), 
/* 187:149 */               button);
/* 188:150 */             return;
/* 189:    */           }
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void onMouseRelease(int x, int y, int button)
/* 196:    */   {
/* 197:159 */     super.onMouseRelease(x, y, button);
/* 198:160 */     synchronized (this.children)
/* 199:    */     {
/* 200:161 */       for (Component child : this.children.keySet()) {
/* 201:162 */         if (child.isVisible()) {
/* 202:164 */           if (!child.getArea().contains(x, y))
/* 203:    */           {
/* 204:    */             Rectangle[] arrayOfRectangle;
/* 205:167 */             int j = (arrayOfRectangle = child.getTheme().getUIForComponent(child).getInteractableRegions(child)).length;
/* 206:167 */             for (int i = 0; i < j; i++)
/* 207:    */             {
/* 208:167 */               Rectangle area = arrayOfRectangle[i];
/* 209:168 */               if (area.contains(x - child.getX(), y - child.getY()))
/* 210:    */               {
/* 211:169 */                 child.onMouseRelease(x - child.getX(), 
/* 212:170 */                   y - child.getY(), button);
/* 213:171 */                 return;
/* 214:    */               }
/* 215:    */             }
/* 216:    */           }
/* 217:    */         }
/* 218:    */       }
/* 219:176 */       for (Component child : this.children.keySet()) {
/* 220:177 */         if (child.isVisible()) {
/* 221:179 */           if (child.getArea().contains(x, y))
/* 222:    */           {
/* 223:180 */             child.onMouseRelease(x - child.getX(), y - child.getY(), 
/* 224:181 */               button);
/* 225:182 */             return;
/* 226:    */           }
/* 227:    */         }
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setEnabled(boolean enabled)
/* 233:    */   {
/* 234:191 */     super.setEnabled(enabled);
/* 235:192 */     enabled = isEnabled();
/* 236:193 */     synchronized (this.children)
/* 237:    */     {
/* 238:194 */       for (Component child : this.children.keySet()) {
/* 239:195 */         child.setEnabled(enabled);
/* 240:    */       }
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setVisible(boolean visible)
/* 245:    */   {
/* 246:201 */     super.setVisible(visible);
/* 247:202 */     visible = isVisible();
/* 248:203 */     synchronized (this.children)
/* 249:    */     {
/* 250:204 */       for (Component child : this.children.keySet()) {
/* 251:205 */         child.setVisible(visible);
/* 252:    */       }
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void update()
/* 257:    */   {
/* 258:211 */     for (Component child : getChildren()) {
/* 259:212 */       child.update();
/* 260:    */     }
/* 261:    */   }
/* 262:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.AbstractContainer
 * JD-Core Version:    0.7.0.1
 */
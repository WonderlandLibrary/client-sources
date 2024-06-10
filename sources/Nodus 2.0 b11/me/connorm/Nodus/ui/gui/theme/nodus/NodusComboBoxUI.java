/*   1:    */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import org.darkstorm.minecraft.gui.component.ComboBox;
/*   9:    */ import org.darkstorm.minecraft.gui.component.Component;
/*  10:    */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*  11:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  12:    */ import org.lwjgl.input.Mouse;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ public class NodusComboBoxUI
/*  16:    */   extends AbstractComponentUI<ComboBox>
/*  17:    */ {
/*  18:    */   private final NodusTheme theme;
/*  19:    */   
/*  20:    */   NodusComboBoxUI(NodusTheme theme)
/*  21:    */   {
/*  22: 18 */     super(ComboBox.class);
/*  23: 19 */     this.theme = theme;
/*  24:    */     
/*  25: 21 */     this.foreground = Color.WHITE;
/*  26: 22 */     this.background = new Color(128, 128, 128, 192);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void renderComponent(ComboBox component)
/*  30:    */   {
/*  31: 27 */     translateComponent(component, false);
/*  32: 28 */     Rectangle area = component.getArea();
/*  33: 29 */     GL11.glEnable(3042);
/*  34: 30 */     GL11.glDisable(2884);
/*  35:    */     
/*  36: 32 */     GL11.glDisable(3553);
/*  37: 33 */     int maxWidth = 0;
/*  38: 34 */     for (String element : component.getElements()) {
/*  39: 35 */       maxWidth = Math.max(maxWidth, this.theme.getFontRenderer()
/*  40: 36 */         .getStringWidth(element));
/*  41:    */     }
/*  42: 37 */     int extendedHeight = 0;
/*  43: 38 */     if (component.isSelected())
/*  44:    */     {
/*  45: 39 */       String[] elements = component.getElements();
/*  46: 40 */       for (int i = 0; i < elements.length - 1; i++) {
/*  47: 41 */         extendedHeight += this.theme.getFontRenderer().FONT_HEIGHT + 2;
/*  48:    */       }
/*  49: 42 */       extendedHeight += 2;
/*  50:    */     }
/*  51: 45 */     RenderUtil.setColor(component.getBackgroundColor());
/*  52: 46 */     GL11.glBegin(7);
/*  53:    */     
/*  54: 48 */     GL11.glVertex2d(0.0D, 0.0D);
/*  55: 49 */     GL11.glVertex2d(area.width, 0.0D);
/*  56: 50 */     GL11.glVertex2d(area.width, area.height + extendedHeight);
/*  57: 51 */     GL11.glVertex2d(0.0D, area.height + extendedHeight);
/*  58:    */     
/*  59: 53 */     GL11.glEnd();
/*  60: 54 */     Point mouse = RenderUtil.calculateMouseLocation();
/*  61: 55 */     Object parent = component.getParent();
/*  62: 56 */     while (parent != null)
/*  63:    */     {
/*  64: 57 */       mouse.x -= ((Component)parent).getX();
/*  65: 58 */       mouse.y -= ((Component)parent).getY();
/*  66: 59 */       parent = ((Component)parent).getParent();
/*  67:    */     }
/*  68: 61 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, Mouse.isButtonDown(0) ? 0.5F : 0.3F);
/*  69: 62 */     if (area.contains(mouse))
/*  70:    */     {
/*  71: 63 */       GL11.glBegin(7);
/*  72:    */       
/*  73: 65 */       GL11.glVertex2d(0.0D, 0.0D);
/*  74: 66 */       GL11.glVertex2d(area.width, 0.0D);
/*  75: 67 */       GL11.glVertex2d(area.width, area.height);
/*  76: 68 */       GL11.glVertex2d(0.0D, area.height);
/*  77:    */       
/*  78: 70 */       GL11.glEnd();
/*  79:    */     }
/*  80: 71 */     else if ((component.isSelected()) && (mouse.x >= area.x) && 
/*  81: 72 */       (mouse.x <= area.x + area.width))
/*  82:    */     {
/*  83: 73 */       int offset = component.getHeight();
/*  84: 74 */       String[] elements = component.getElements();
/*  85: 75 */       for (int i = 0; i < elements.length; i++) {
/*  86: 76 */         if (i != component.getSelectedIndex())
/*  87:    */         {
/*  88: 78 */           int height = this.theme.getFontRenderer().FONT_HEIGHT + 2;
/*  89: 79 */           if ((component.getSelectedIndex() == 0 ? i == 1 : i == 0) || 
/*  90: 80 */             (component.getSelectedIndex() == elements.length - 1 ? i == elements.length - 2 : 
/*  91: 81 */             i == elements.length - 1)) {
/*  92: 82 */             height++;
/*  93:    */           }
/*  94: 83 */           if ((mouse.y >= area.y + offset) && 
/*  95: 84 */             (mouse.y <= area.y + offset + height))
/*  96:    */           {
/*  97: 85 */             GL11.glBegin(7);
/*  98:    */             
/*  99: 87 */             GL11.glVertex2d(0.0D, offset);
/* 100: 88 */             GL11.glVertex2d(0.0D, offset + height);
/* 101: 89 */             GL11.glVertex2d(area.width, offset + height);
/* 102: 90 */             GL11.glVertex2d(area.width, offset);
/* 103:    */             
/* 104: 92 */             GL11.glEnd();
/* 105: 93 */             break;
/* 106:    */           }
/* 107: 95 */           offset += height;
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111: 98 */     int height = this.theme.getFontRenderer().FONT_HEIGHT + 4;
/* 112: 99 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.3F);
/* 113:100 */     GL11.glBegin(4);
/* 114:102 */     if (component.isSelected())
/* 115:    */     {
/* 116:103 */       GL11.glVertex2d(maxWidth + 4 + height / 2.0D, height / 3.0D);
/* 117:104 */       GL11.glVertex2d(maxWidth + 4 + height / 3.0D, 2.0D * height / 3.0D);
/* 118:105 */       GL11.glVertex2d(maxWidth + 4 + 2.0D * height / 3.0D, 2.0D * height / 3.0D);
/* 119:    */     }
/* 120:    */     else
/* 121:    */     {
/* 122:107 */       GL11.glVertex2d(maxWidth + 4 + height / 3.0D, height / 3.0D);
/* 123:108 */       GL11.glVertex2d(maxWidth + 4 + 2.0D * height / 3.0D, height / 3.0D);
/* 124:109 */       GL11.glVertex2d(maxWidth + 4 + height / 2.0D, 2.0D * height / 3.0D);
/* 125:    */     }
/* 126:112 */     GL11.glEnd();
/* 127:113 */     GL11.glLineWidth(1.0F);
/* 128:114 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/* 129:123 */     if (component.isSelected())
/* 130:    */     {
/* 131:124 */       GL11.glBegin(1);
/* 132:    */       
/* 133:126 */       GL11.glVertex2d(2.0D, area.height);
/* 134:127 */       GL11.glVertex2d(area.width - 2, area.height);
/* 135:    */       
/* 136:129 */       GL11.glEnd();
/* 137:    */     }
/* 138:131 */     GL11.glBegin(1);
/* 139:    */     
/* 140:133 */     GL11.glVertex2d(maxWidth + 4, 2.0D);
/* 141:134 */     GL11.glVertex2d(maxWidth + 4, area.height - 2);
/* 142:    */     
/* 143:136 */     GL11.glEnd();
/* 144:137 */     GL11.glBegin(2);
/* 145:139 */     if (component.isSelected())
/* 146:    */     {
/* 147:140 */       GL11.glVertex2d(maxWidth + 4 + height / 2.0D, height / 3.0D);
/* 148:141 */       GL11.glVertex2d(maxWidth + 4 + height / 3.0D, 2.0D * height / 3.0D);
/* 149:142 */       GL11.glVertex2d(maxWidth + 4 + 2.0D * height / 3.0D, 2.0D * height / 3.0D);
/* 150:    */     }
/* 151:    */     else
/* 152:    */     {
/* 153:144 */       GL11.glVertex2d(maxWidth + 4 + height / 3.0D, height / 3.0D);
/* 154:145 */       GL11.glVertex2d(maxWidth + 4 + 2.0D * height / 3.0D, height / 3.0D);
/* 155:146 */       GL11.glVertex2d(maxWidth + 4 + height / 2.0D, 2.0D * height / 3.0D);
/* 156:    */     }
/* 157:149 */     GL11.glEnd();
/* 158:150 */     GL11.glEnable(3553);
/* 159:    */     
/* 160:152 */     String text = component.getSelectedElement();
/* 161:153 */     this.theme.getFontRenderer().drawString(text, 2, 
/* 162:154 */       area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 2, 
/* 163:155 */       RenderUtil.toRGBA(component.getForegroundColor()));
/* 164:156 */     if (component.isSelected())
/* 165:    */     {
/* 166:157 */       int offset = area.height + 2;
/* 167:158 */       String[] elements = component.getElements();
/* 168:159 */       for (int i = 0; i < elements.length; i++) {
/* 169:160 */         if (i != component.getSelectedIndex())
/* 170:    */         {
/* 171:162 */           this.theme.getFontRenderer().drawString(elements[i], 2, offset, 
/* 172:163 */             RenderUtil.toRGBA(component.getForegroundColor()));
/* 173:164 */           offset += this.theme.getFontRenderer().FONT_HEIGHT + 2;
/* 174:    */         }
/* 175:    */       }
/* 176:    */     }
/* 177:168 */     GL11.glEnable(2884);
/* 178:169 */     GL11.glDisable(3042);
/* 179:170 */     translateComponent(component, true);
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected Dimension getDefaultComponentSize(ComboBox component)
/* 183:    */   {
/* 184:175 */     int maxWidth = 0;
/* 185:176 */     for (String element : component.getElements()) {
/* 186:177 */       maxWidth = Math.max(maxWidth, this.theme.getFontRenderer()
/* 187:178 */         .getStringWidth(element));
/* 188:    */     }
/* 189:179 */     return new Dimension(
/* 190:180 */       maxWidth + 8 + this.theme.getFontRenderer().FONT_HEIGHT, 
/* 191:181 */       this.theme.getFontRenderer().FONT_HEIGHT + 4);
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected Rectangle[] getInteractableComponentRegions(ComboBox component)
/* 195:    */   {
/* 196:186 */     int height = component.getHeight();
/* 197:187 */     if (component.isSelected())
/* 198:    */     {
/* 199:188 */       String[] elements = component.getElements();
/* 200:189 */       for (int i = 0; i < elements.length; i++) {
/* 201:190 */         height += this.theme.getFontRenderer().FONT_HEIGHT + 2;
/* 202:    */       }
/* 203:191 */       height += 2;
/* 204:    */     }
/* 205:193 */     return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
/* 206:194 */       height) };
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected void handleComponentInteraction(ComboBox component, Point location, int button)
/* 210:    */   {
/* 211:200 */     if (button != 0) {
/* 212:201 */       return;
/* 213:    */     }
/* 214:202 */     if ((location.x <= component.getWidth()) && 
/* 215:203 */       (location.y <= component.getHeight()))
/* 216:    */     {
/* 217:204 */       component.setSelected(!component.isSelected());
/* 218:    */     }
/* 219:205 */     else if ((location.x <= component.getWidth()) && (component.isSelected()))
/* 220:    */     {
/* 221:206 */       int offset = component.getHeight() + 2;
/* 222:207 */       String[] elements = component.getElements();
/* 223:208 */       for (int i = 0; i < elements.length; i++) {
/* 224:209 */         if (i != component.getSelectedIndex())
/* 225:    */         {
/* 226:211 */           if (location.y >= offset) {
/* 227:213 */             if (location.y <= offset + this.theme.getFontRenderer().FONT_HEIGHT)
/* 228:    */             {
/* 229:214 */               component.setSelectedIndex(i);
/* 230:215 */               component.setSelected(false);
/* 231:216 */               break;
/* 232:    */             }
/* 233:    */           }
/* 234:218 */           offset += this.theme.getFontRenderer().FONT_HEIGHT + 2;
/* 235:    */         }
/* 236:    */       }
/* 237:    */     }
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusComboBoxUI
 * JD-Core Version:    0.7.0.1
 */
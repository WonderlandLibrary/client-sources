/*   1:    */ package org.darkstorm.minecraft.gui.theme.simple;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import org.darkstorm.minecraft.gui.component.Component;
/*   9:    */ import org.darkstorm.minecraft.gui.component.Frame;
/*  10:    */ import org.darkstorm.minecraft.gui.layout.Constraint;
/*  11:    */ import org.darkstorm.minecraft.gui.layout.LayoutManager;
/*  12:    */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*  13:    */ import org.darkstorm.minecraft.gui.theme.ComponentUI;
/*  14:    */ import org.darkstorm.minecraft.gui.theme.Theme;
/*  15:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  16:    */ import org.lwjgl.opengl.GL11;
/*  17:    */ 
/*  18:    */ public class SimpleFrameUI
/*  19:    */   extends AbstractComponentUI<Frame>
/*  20:    */ {
/*  21:    */   private final SimpleTheme theme;
/*  22:    */   
/*  23:    */   SimpleFrameUI(SimpleTheme theme)
/*  24:    */   {
/*  25: 17 */     super(Frame.class);
/*  26: 18 */     this.theme = theme;
/*  27:    */     
/*  28: 20 */     this.foreground = Color.WHITE;
/*  29: 21 */     this.background = new Color(128, 128, 128, 128);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void renderComponent(Frame component)
/*  33:    */   {
/*  34: 26 */     Rectangle area = new Rectangle(component.getArea());
/*  35: 27 */     int fontHeight = this.theme.getFontRenderer().FONT_HEIGHT;
/*  36: 28 */     translateComponent(component, false);
/*  37: 29 */     GL11.glEnable(3042);
/*  38: 30 */     GL11.glDisable(2884);
/*  39: 31 */     GL11.glDisable(3553);
/*  40: 32 */     GL11.glBlendFunc(770, 771);
/*  41: 35 */     if (component.isMinimized()) {
/*  42: 36 */       area.height = (fontHeight + 4);
/*  43:    */     }
/*  44: 37 */     RenderUtil.setColor(component.getBackgroundColor());
/*  45: 38 */     GL11.glBegin(7);
/*  46:    */     
/*  47: 40 */     GL11.glVertex2d(0.0D, 0.0D);
/*  48: 41 */     GL11.glVertex2d(area.width, 0.0D);
/*  49: 42 */     GL11.glVertex2d(area.width, area.height);
/*  50: 43 */     GL11.glVertex2d(0.0D, area.height);
/*  51:    */     
/*  52: 45 */     GL11.glEnd();
/*  53:    */     
/*  54:    */ 
/*  55: 48 */     int offset = component.getWidth() - 2;
/*  56: 49 */     Point mouse = RenderUtil.calculateMouseLocation();
/*  57: 50 */     Component parent = component;
/*  58: 51 */     while (parent != null)
/*  59:    */     {
/*  60: 52 */       mouse.x -= parent.getX();
/*  61: 53 */       mouse.y -= parent.getY();
/*  62: 54 */       parent = parent.getParent();
/*  63:    */     }
/*  64: 56 */     boolean[] checks = { component.isClosable(), 
/*  65: 57 */       component.isPinnable(), component.isMinimizable() };
/*  66: 58 */     boolean[] overlays = { false, component.isPinned(), 
/*  67: 59 */       component.isMinimized() };
/*  68: 60 */     for (int i = 0; i < checks.length; i++) {
/*  69: 61 */       if (checks[i] != 0)
/*  70:    */       {
/*  71: 63 */         RenderUtil.setColor(component.getBackgroundColor());
/*  72: 64 */         GL11.glBegin(7);
/*  73:    */         
/*  74: 66 */         GL11.glVertex2d(offset - fontHeight, 2.0D);
/*  75: 67 */         GL11.glVertex2d(offset, 2.0D);
/*  76: 68 */         GL11.glVertex2d(offset, fontHeight + 2);
/*  77: 69 */         GL11.glVertex2d(offset - fontHeight, fontHeight + 2);
/*  78:    */         
/*  79: 71 */         GL11.glEnd();
/*  80: 72 */         if (overlays[i] != 0)
/*  81:    */         {
/*  82: 73 */           GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F);
/*  83: 74 */           GL11.glBegin(7);
/*  84:    */           
/*  85: 76 */           GL11.glVertex2d(offset - fontHeight, 2.0D);
/*  86: 77 */           GL11.glVertex2d(offset, 2.0D);
/*  87: 78 */           GL11.glVertex2d(offset, fontHeight + 2);
/*  88: 79 */           GL11.glVertex2d(offset - fontHeight, fontHeight + 2);
/*  89:    */           
/*  90: 81 */           GL11.glEnd();
/*  91:    */         }
/*  92: 83 */         if ((mouse.x >= offset - fontHeight) && (mouse.x <= offset) && 
/*  93: 84 */           (mouse.y >= 2) && (mouse.y <= fontHeight + 2))
/*  94:    */         {
/*  95: 85 */           GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.3F);
/*  96: 86 */           GL11.glBegin(7);
/*  97:    */           
/*  98: 88 */           GL11.glVertex2d(offset - fontHeight, 2.0D);
/*  99: 89 */           GL11.glVertex2d(offset, 2.0D);
/* 100: 90 */           GL11.glVertex2d(offset, fontHeight + 2);
/* 101: 91 */           GL11.glVertex2d(offset - fontHeight, fontHeight + 2);
/* 102:    */           
/* 103: 93 */           GL11.glEnd();
/* 104:    */         }
/* 105: 95 */         GL11.glLineWidth(1.0F);
/* 106: 96 */         GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/* 107: 97 */         GL11.glBegin(2);
/* 108:    */         
/* 109: 99 */         GL11.glVertex2d(offset - fontHeight, 2.0D);
/* 110:100 */         GL11.glVertex2d(offset, 2.0D);
/* 111:101 */         GL11.glVertex2d(offset, fontHeight + 2);
/* 112:102 */         GL11.glVertex2d(offset - fontHeight - 0.5D, fontHeight + 2);
/* 113:    */         
/* 114:104 */         GL11.glEnd();
/* 115:105 */         offset -= fontHeight + 2;
/* 116:    */       }
/* 117:    */     }
/* 118:108 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/* 119:109 */     GL11.glLineWidth(1.0F);
/* 120:110 */     GL11.glBegin(1);
/* 121:    */     
/* 122:112 */     GL11.glVertex2d(2.0D, this.theme.getFontRenderer().FONT_HEIGHT + 4);
/* 123:113 */     GL11.glVertex2d(area.width - 2, this.theme.getFontRenderer().FONT_HEIGHT + 4);
/* 124:    */     
/* 125:115 */     GL11.glEnd();
/* 126:116 */     GL11.glEnable(3553);
/* 127:117 */     this.theme.getFontRenderer().drawStringWithShadow(component.getTitle(), 2, 
/* 128:118 */       2, RenderUtil.toRGBA(component.getForegroundColor()));
/* 129:119 */     GL11.glEnable(2884);
/* 130:120 */     GL11.glDisable(3042);
/* 131:121 */     translateComponent(component, true);
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected Rectangle getContainerChildRenderArea(Frame container)
/* 135:    */   {
/* 136:126 */     Rectangle area = new Rectangle(container.getArea());
/* 137:127 */     area.x = 2;
/* 138:128 */     area.y = (this.theme.getFontRenderer().FONT_HEIGHT + 6);
/* 139:129 */     area.width -= 4;
/* 140:130 */     area.height -= this.theme.getFontRenderer().FONT_HEIGHT + 8;
/* 141:131 */     return area;
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected Dimension getDefaultComponentSize(Frame component)
/* 145:    */   {
/* 146:136 */     Component[] children = component.getChildren();
/* 147:137 */     Rectangle[] areas = new Rectangle[children.length];
/* 148:138 */     Constraint[][] constraints = new Constraint[children.length][];
/* 149:139 */     for (int i = 0; i < children.length; i++)
/* 150:    */     {
/* 151:140 */       Component child = children[i];
/* 152:141 */       Dimension size = child.getTheme().getUIForComponent(child)
/* 153:142 */         .getDefaultSize(child);
/* 154:143 */       areas[i] = new Rectangle(0, 0, size.width, size.height);
/* 155:144 */       constraints[i] = component.getConstraints(child);
/* 156:    */     }
/* 157:146 */     Dimension size = component.getLayoutManager().getOptimalPositionedSize(
/* 158:147 */       areas, constraints);
/* 159:148 */     size.width += 4;
/* 160:149 */     size.height += this.theme.getFontRenderer().FONT_HEIGHT + 8;
/* 161:150 */     return size;
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected Rectangle[] getInteractableComponentRegions(Frame component)
/* 165:    */   {
/* 166:155 */     return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
/* 167:156 */       this.theme.getFontRenderer().FONT_HEIGHT + 4) };
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected void handleComponentInteraction(Frame component, Point location, int button)
/* 171:    */   {
/* 172:162 */     if (button != 0) {
/* 173:163 */       return;
/* 174:    */     }
/* 175:164 */     int offset = component.getWidth() - 2;
/* 176:165 */     int textHeight = this.theme.getFontRenderer().FONT_HEIGHT;
/* 177:166 */     if (component.isClosable())
/* 178:    */     {
/* 179:167 */       if ((location.x >= offset - textHeight) && (location.x <= offset) && 
/* 180:168 */         (location.y >= 2) && (location.y <= textHeight + 2))
/* 181:    */       {
/* 182:169 */         component.close();
/* 183:170 */         return;
/* 184:    */       }
/* 185:172 */       offset -= textHeight + 2;
/* 186:    */     }
/* 187:174 */     if (component.isPinnable())
/* 188:    */     {
/* 189:175 */       if ((location.x >= offset - textHeight) && (location.x <= offset) && 
/* 190:176 */         (location.y >= 2) && (location.y <= textHeight + 2))
/* 191:    */       {
/* 192:177 */         component.setPinned(!component.isPinned());
/* 193:178 */         return;
/* 194:    */       }
/* 195:180 */       offset -= textHeight + 2;
/* 196:    */     }
/* 197:182 */     if (component.isMinimizable())
/* 198:    */     {
/* 199:183 */       if ((location.x >= offset - textHeight) && (location.x <= offset) && 
/* 200:184 */         (location.y >= 2) && (location.y <= textHeight + 2))
/* 201:    */       {
/* 202:185 */         component.setMinimized(!component.isMinimized());
/* 203:186 */         return;
/* 204:    */       }
/* 205:188 */       offset -= textHeight + 2;
/* 206:    */     }
/* 207:190 */     if ((location.x >= 0) && (location.x <= offset) && (location.y >= 0) && 
/* 208:191 */       (location.y <= textHeight + 4))
/* 209:    */     {
/* 210:192 */       component.setDragging(true);
/* 211:193 */       return;
/* 212:    */     }
/* 213:    */   }
/* 214:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.theme.simple.SimpleFrameUI
 * JD-Core Version:    0.7.0.1
 */
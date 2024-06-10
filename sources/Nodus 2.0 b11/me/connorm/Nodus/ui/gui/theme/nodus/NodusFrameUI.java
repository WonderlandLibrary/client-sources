/*   1:    */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import me.connorm.Nodus.Nodus;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.gui.FontRenderer;
/*  10:    */ import org.darkstorm.minecraft.gui.component.Component;
/*  11:    */ import org.darkstorm.minecraft.gui.component.Frame;
/*  12:    */ import org.darkstorm.minecraft.gui.layout.Constraint;
/*  13:    */ import org.darkstorm.minecraft.gui.layout.LayoutManager;
/*  14:    */ import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
/*  15:    */ import org.darkstorm.minecraft.gui.theme.ComponentUI;
/*  16:    */ import org.darkstorm.minecraft.gui.theme.Theme;
/*  17:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*  18:    */ import org.lwjgl.opengl.GL11;
/*  19:    */ 
/*  20:    */ public class NodusFrameUI
/*  21:    */   extends AbstractComponentUI<Frame>
/*  22:    */ {
/*  23:    */   private final NodusTheme theme;
/*  24:    */   
/*  25:    */   NodusFrameUI(NodusTheme theme)
/*  26:    */   {
/*  27: 41 */     super(Frame.class);
/*  28: 42 */     this.theme = theme;
/*  29:    */     
/*  30: 44 */     this.foreground = Color.WHITE;
/*  31: 45 */     this.background = new Color(128, 128, 128, 128);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected void renderComponent(Frame component)
/*  35:    */   {
/*  36: 51 */     Rectangle area = new Rectangle(component.getArea());
/*  37: 52 */     int fontHeight = this.theme.getFontRenderer().FONT_HEIGHT;
/*  38: 53 */     translateComponent(component, false);
/*  39: 54 */     GL11.glEnable(3042);
/*  40: 55 */     GL11.glDisable(2884);
/*  41: 56 */     GL11.glDisable(3553);
/*  42: 57 */     GL11.glBlendFunc(770, 771);
/*  43: 60 */     if (component.isMinimized()) {
/*  44: 61 */       area.height = (fontHeight + 4);
/*  45:    */     }
/*  46: 63 */     if (component.isMinimized())
/*  47:    */     {
/*  48: 64 */       NodusUtils.drawTopNodusRect(-3.0F, -3.0F, area.width - 3, fontHeight + 5);
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52: 67 */       if (!component.getTitle().startsWith("X:")) {
/*  53: 68 */         NodusUtils.drawNodusRect(-3.0F, fontHeight + 5.0F, area.width - 3, area.height + 3);
/*  54:    */       }
/*  55: 69 */       NodusUtils.drawTopNodusRect(-3.0F, -3.0F, area.width - 3, fontHeight + 5);
/*  56:    */     }
/*  57: 73 */     int offset = component.getWidth() - 2;
/*  58: 74 */     Point mouse = RenderUtil.calculateMouseLocation();
/*  59: 75 */     Component parent = component;
/*  60: 76 */     while (parent != null)
/*  61:    */     {
/*  62: 77 */       mouse.x -= parent.getX();
/*  63: 78 */       mouse.y -= parent.getY();
/*  64: 79 */       parent = parent.getParent();
/*  65:    */     }
/*  66: 81 */     boolean[] checks = { component.isClosable(), 
/*  67: 82 */       component.isPinnable(), component.isMinimizable() };
/*  68: 83 */     boolean[] overlays = { false, component.isPinned(), 
/*  69: 84 */       component.isMinimized() };
/*  70: 85 */     for (int i = 0; i < checks.length; i++) {
/*  71: 86 */       if (checks[i] != 0)
/*  72:    */       {
/*  73: 89 */         NodusUtils.drawSmallNodusRect(offset - fontHeight - 6, 1.0F, offset - 5, fontHeight + 2);
/*  74: 91 */         if (overlays[i] != 0) {
/*  75: 93 */           NodusUtils.drawSmallNodusRect(offset - fontHeight - 6, 1.0F, offset - 5, fontHeight + 2);
/*  76:    */         }
/*  77: 96 */         if ((mouse.x >= offset - fontHeight - 6) && (mouse.x <= offset) && 
/*  78: 97 */           (mouse.y >= -4) && (mouse.y <= fontHeight + 2)) {
/*  79: 99 */           if (component.isPinnable()) {
/*  80:101 */             Nodus.theNodus.getMinecraft().fontRenderer.drawString("Pin", mouse.x, mouse.y, -1);
/*  81:    */           } else {
/*  82:104 */             Nodus.theNodus.getMinecraft().fontRenderer.drawString("Expand", mouse.x, mouse.y, -1);
/*  83:    */           }
/*  84:    */         }
/*  85:108 */         offset -= fontHeight + 2;
/*  86:    */       }
/*  87:    */     }
/*  88:110 */     GL11.glEnable(3553);
/*  89:111 */     this.theme.getFontRenderer().drawStringWithShadow(component.getTitle(), 2, 2, ColorUtils.secondaryColor);
/*  90:    */     
/*  91:113 */     String numberOfComponents = "(" + component.getChildren().length + ")";
/*  92:114 */     this.theme.getFontRenderer().drawStringWithShadow(numberOfComponents, this.theme.getFontRenderer().getStringWidth(component.getTitle()) + 5, 2, ColorUtils.primaryColor);
/*  93:115 */     GL11.glEnable(2884);
/*  94:116 */     GL11.glDisable(3042);
/*  95:117 */     translateComponent(component, true);
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected Rectangle getContainerChildRenderArea(Frame container)
/*  99:    */   {
/* 100:122 */     Rectangle area = new Rectangle(container.getArea());
/* 101:123 */     area.x = 2;
/* 102:124 */     area.y = (this.theme.getFontRenderer().FONT_HEIGHT + 9);
/* 103:125 */     area.width -= 4;
/* 104:126 */     area.height -= this.theme.getFontRenderer().FONT_HEIGHT + 8;
/* 105:127 */     return area;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected Dimension getDefaultComponentSize(Frame component)
/* 109:    */   {
/* 110:132 */     Component[] children = component.getChildren();
/* 111:133 */     Rectangle[] areas = new Rectangle[children.length];
/* 112:134 */     Constraint[][] constraints = new Constraint[children.length][];
/* 113:135 */     for (int i = 0; i < children.length; i++)
/* 114:    */     {
/* 115:136 */       Component child = children[i];
/* 116:137 */       Dimension size = child.getTheme().getUIForComponent(child)
/* 117:138 */         .getDefaultSize(child);
/* 118:139 */       areas[i] = new Rectangle(0, 0, size.width, size.height);
/* 119:140 */       constraints[i] = component.getConstraints(child);
/* 120:    */     }
/* 121:142 */     Dimension size = component.getLayoutManager().getOptimalPositionedSize(
/* 122:143 */       areas, constraints);
/* 123:144 */     size.width += 4;
/* 124:145 */     size.height += this.theme.getFontRenderer().FONT_HEIGHT + 8;
/* 125:146 */     return size;
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected Rectangle[] getInteractableComponentRegions(Frame component)
/* 129:    */   {
/* 130:151 */     return new Rectangle[] { new Rectangle(0, 0, component.getWidth(), 
/* 131:152 */       this.theme.getFontRenderer().FONT_HEIGHT + 4) };
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected void handleComponentInteraction(Frame component, Point location, int button)
/* 135:    */   {
/* 136:158 */     if (button != 0) {
/* 137:159 */       return;
/* 138:    */     }
/* 139:160 */     int offset = component.getWidth() - 2;
/* 140:161 */     int textHeight = this.theme.getFontRenderer().FONT_HEIGHT;
/* 141:162 */     if (component.isClosable())
/* 142:    */     {
/* 143:163 */       if ((location.x >= offset - textHeight) && (location.x <= offset) && 
/* 144:164 */         (location.y >= 2) && (location.y <= textHeight + 2))
/* 145:    */       {
/* 146:165 */         component.close();
/* 147:166 */         return;
/* 148:    */       }
/* 149:168 */       offset -= textHeight + 2;
/* 150:    */     }
/* 151:170 */     if (component.isPinnable())
/* 152:    */     {
/* 153:171 */       if ((location.x >= offset - textHeight - 6) && (location.x <= offset) && 
/* 154:172 */         (location.y >= -4) && (location.y <= textHeight + 2))
/* 155:    */       {
/* 156:173 */         component.setPinned(!component.isPinned());
/* 157:174 */         return;
/* 158:    */       }
/* 159:176 */       offset -= textHeight + 2;
/* 160:    */     }
/* 161:178 */     if (component.isMinimizable())
/* 162:    */     {
/* 163:179 */       if ((location.x >= offset - textHeight - 6) && (location.x <= offset) && 
/* 164:180 */         (location.y >= -4) && (location.y <= textHeight + 2))
/* 165:    */       {
/* 166:181 */         component.setMinimized(!component.isMinimized());
/* 167:182 */         return;
/* 168:    */       }
/* 169:184 */       offset -= textHeight + 2;
/* 170:    */     }
/* 171:186 */     if ((location.x >= 0) && (location.x <= offset) && (location.y >= 0) && 
/* 172:187 */       (location.y <= textHeight + 4))
/* 173:    */     {
/* 174:188 */       component.setDragging(true);
/* 175:189 */       return;
/* 176:    */     }
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusFrameUI
 * JD-Core Version:    0.7.0.1
 */
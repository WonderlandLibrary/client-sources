/*   1:    */ package org.darkstorm.minecraft.gui.component.basic;
/*   2:    */ 
/*   3:    */ import java.awt.Point;
/*   4:    */ import org.darkstorm.minecraft.gui.component.AbstractContainer;
/*   5:    */ import org.darkstorm.minecraft.gui.component.Frame;
/*   6:    */ import org.darkstorm.minecraft.gui.theme.ComponentUI;
/*   7:    */ import org.darkstorm.minecraft.gui.util.RenderUtil;
/*   8:    */ import org.lwjgl.input.Mouse;
/*   9:    */ 
/*  10:    */ public class BasicFrame
/*  11:    */   extends AbstractContainer
/*  12:    */   implements Frame
/*  13:    */ {
/*  14:    */   private String title;
/*  15:    */   private Point dragOffset;
/*  16:    */   private boolean pinned;
/*  17: 14 */   private boolean pinnable = true;
/*  18:    */   private boolean minimized;
/*  19: 15 */   private boolean minimizable = true;
/*  20: 16 */   private boolean closable = true;
/*  21:    */   
/*  22:    */   public void render()
/*  23:    */   {
/*  24: 20 */     if (isDragging()) {
/*  25: 21 */       if (Mouse.isButtonDown(0))
/*  26:    */       {
/*  27: 22 */         Point mouseLocation = RenderUtil.calculateMouseLocation();
/*  28: 23 */         setX(mouseLocation.x - this.dragOffset.x);
/*  29: 24 */         setY(mouseLocation.y - this.dragOffset.y);
/*  30:    */       }
/*  31:    */       else
/*  32:    */       {
/*  33: 26 */         setDragging(false);
/*  34:    */       }
/*  35:    */     }
/*  36: 28 */     if (this.minimized)
/*  37:    */     {
/*  38: 29 */       if (this.ui != null) {
/*  39: 30 */         this.ui.render(this);
/*  40:    */       }
/*  41:    */     }
/*  42:    */     else {
/*  43: 32 */       super.render();
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public BasicFrame()
/*  48:    */   {
/*  49: 36 */     this("");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public BasicFrame(String title)
/*  53:    */   {
/*  54: 40 */     setVisible(false);
/*  55: 41 */     this.title = title;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getTitle()
/*  59:    */   {
/*  60: 46 */     return this.title;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setTitle(String title)
/*  64:    */   {
/*  65: 51 */     this.title = title;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isDragging()
/*  69:    */   {
/*  70: 56 */     return this.dragOffset != null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setDragging(boolean dragging)
/*  74:    */   {
/*  75: 61 */     if (dragging)
/*  76:    */     {
/*  77: 62 */       Point mouseLocation = RenderUtil.calculateMouseLocation();
/*  78: 63 */       this.dragOffset = new Point(mouseLocation.x - getX(), mouseLocation.y - 
/*  79: 64 */         getY());
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83: 66 */       this.dragOffset = null;
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isPinned()
/*  88:    */   {
/*  89: 71 */     return this.pinned;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setPinned(boolean pinned)
/*  93:    */   {
/*  94: 76 */     if (!this.pinnable) {
/*  95: 77 */       pinned = false;
/*  96:    */     }
/*  97: 78 */     this.pinned = pinned;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isPinnable()
/* 101:    */   {
/* 102: 83 */     return this.pinnable;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setPinnable(boolean pinnable)
/* 106:    */   {
/* 107: 88 */     if (!pinnable) {
/* 108: 89 */       this.pinned = false;
/* 109:    */     }
/* 110: 90 */     this.pinnable = pinnable;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isMinimized()
/* 114:    */   {
/* 115: 95 */     return this.minimized;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setMinimized(boolean minimized)
/* 119:    */   {
/* 120:100 */     if (!this.minimizable) {
/* 121:101 */       minimized = false;
/* 122:    */     }
/* 123:102 */     this.minimized = minimized;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isMinimizable()
/* 127:    */   {
/* 128:107 */     return this.minimizable;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setMinimizable(boolean minimizable)
/* 132:    */   {
/* 133:112 */     if (!minimizable) {
/* 134:113 */       this.minimized = false;
/* 135:    */     }
/* 136:114 */     this.minimizable = minimizable;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void close()
/* 140:    */   {
/* 141:119 */     if (this.closable) {
/* 142:120 */       setVisible(false);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isClosable()
/* 147:    */   {
/* 148:125 */     return this.closable;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setClosable(boolean closable)
/* 152:    */   {
/* 153:130 */     this.closable = closable;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicFrame
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.client.gui.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.gui.GuiScreen;
/*   8:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*   9:    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*  12:    */ import net.minecraft.tileentity.TileEntitySign;
/*  13:    */ import net.minecraft.util.ChatAllowedCharacters;
/*  14:    */ import org.lwjgl.input.Keyboard;
/*  15:    */ import org.lwjgl.opengl.GL11;
/*  16:    */ 
/*  17:    */ public class GuiEditSign
/*  18:    */   extends GuiScreen
/*  19:    */ {
/*  20: 18 */   protected String field_146850_a = "Edit sign message:";
/*  21:    */   private TileEntitySign field_146848_f;
/*  22:    */   private int field_146849_g;
/*  23:    */   private int field_146851_h;
/*  24:    */   private NodusGuiButton field_146852_i;
/*  25:    */   private static final String __OBFID = "CL_00000764";
/*  26:    */   
/*  27:    */   public GuiEditSign(TileEntitySign par1TileEntitySign)
/*  28:    */   {
/*  29: 27 */     this.field_146848_f = par1TileEntitySign;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void initGui()
/*  33:    */   {
/*  34: 35 */     this.buttonList.clear();
/*  35: 36 */     Keyboard.enableRepeatEvents(true);
/*  36: 37 */     this.buttonList.add(this.field_146852_i = new NodusGuiButton(0, width / 2 - 100, height / 4 + 120, "Done"));
/*  37: 38 */     this.field_146848_f.func_145913_a(false);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void onGuiClosed()
/*  41:    */   {
/*  42: 46 */     Keyboard.enableRepeatEvents(false);
/*  43: 47 */     NetHandlerPlayClient var1 = this.mc.getNetHandler();
/*  44: 49 */     if (var1 != null) {
/*  45: 51 */       var1.addToSendQueue(new C12PacketUpdateSign(this.field_146848_f.field_145851_c, this.field_146848_f.field_145848_d, this.field_146848_f.field_145849_e, this.field_146848_f.field_145915_a));
/*  46:    */     }
/*  47: 54 */     this.field_146848_f.func_145913_a(true);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void updateScreen()
/*  51:    */   {
/*  52: 62 */     this.field_146849_g += 1;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  56:    */   {
/*  57: 67 */     if (p_146284_1_.enabled) {
/*  58: 69 */       if (p_146284_1_.id == 0)
/*  59:    */       {
/*  60: 71 */         this.field_146848_f.onInventoryChanged();
/*  61: 72 */         this.mc.displayGuiScreen(null);
/*  62:    */       }
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void keyTyped(char par1, int par2)
/*  67:    */   {
/*  68: 82 */     if (par2 == 200) {
/*  69: 84 */       this.field_146851_h = (this.field_146851_h - 1 & 0x3);
/*  70:    */     }
/*  71: 87 */     if ((par2 == 208) || (par2 == 28) || (par2 == 156)) {
/*  72: 89 */       this.field_146851_h = (this.field_146851_h + 1 & 0x3);
/*  73:    */     }
/*  74: 92 */     if ((par2 == 14) && (this.field_146848_f.field_145915_a[this.field_146851_h].length() > 0)) {
/*  75: 94 */       this.field_146848_f.field_145915_a[this.field_146851_h] = this.field_146848_f.field_145915_a[this.field_146851_h].substring(0, this.field_146848_f.field_145915_a[this.field_146851_h].length() - 1);
/*  76:    */     }
/*  77: 97 */     if ((ChatAllowedCharacters.isAllowedCharacter(par1)) && (this.field_146848_f.field_145915_a[this.field_146851_h].length() < 15)) {
/*  78: 99 */       this.field_146848_f.field_145915_a[this.field_146851_h] = (this.field_146848_f.field_145915_a[this.field_146851_h] + par1);
/*  79:    */     }
/*  80:102 */     if (par2 == 1) {
/*  81:104 */       actionPerformed(this.field_146852_i);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void drawScreen(int par1, int par2, float par3)
/*  86:    */   {
/*  87:113 */     drawDefaultBackground();
/*  88:114 */     drawCenteredString(this.fontRendererObj, this.field_146850_a, width / 2, 40, 16777215);
/*  89:115 */     GL11.glPushMatrix();
/*  90:116 */     GL11.glTranslatef(width / 2, 0.0F, 50.0F);
/*  91:117 */     float var4 = 93.75F;
/*  92:118 */     GL11.glScalef(-var4, -var4, -var4);
/*  93:119 */     GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  94:120 */     Block var5 = this.field_146848_f.getBlockType();
/*  95:122 */     if (var5 == Blocks.standing_sign)
/*  96:    */     {
/*  97:124 */       float var6 = this.field_146848_f.getBlockMetadata() * 360 / 16.0F;
/*  98:125 */       GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
/*  99:126 */       GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:130 */       int var8 = this.field_146848_f.getBlockMetadata();
/* 104:131 */       float var7 = 0.0F;
/* 105:133 */       if (var8 == 2) {
/* 106:135 */         var7 = 180.0F;
/* 107:    */       }
/* 108:138 */       if (var8 == 4) {
/* 109:140 */         var7 = 90.0F;
/* 110:    */       }
/* 111:143 */       if (var8 == 5) {
/* 112:145 */         var7 = -90.0F;
/* 113:    */       }
/* 114:148 */       GL11.glRotatef(var7, 0.0F, 1.0F, 0.0F);
/* 115:149 */       GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
/* 116:    */     }
/* 117:152 */     if (this.field_146849_g / 6 % 2 == 0) {
/* 118:154 */       this.field_146848_f.field_145918_i = this.field_146851_h;
/* 119:    */     }
/* 120:157 */     TileEntityRendererDispatcher.instance.func_147549_a(this.field_146848_f, -0.5D, -0.75D, -0.5D, 0.0F);
/* 121:158 */     this.field_146848_f.field_145918_i = -1;
/* 122:159 */     GL11.glPopMatrix();
/* 123:160 */     super.drawScreen(par1, par2, par3);
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiEditSign
 * JD-Core Version:    0.7.0.1
 */
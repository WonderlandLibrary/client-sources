/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   6:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*   7:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*   8:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   9:    */ import net.minecraft.client.resources.I18n;
/*  10:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  11:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  12:    */ import net.minecraft.inventory.Container;
/*  13:    */ import net.minecraft.inventory.ContainerRepair;
/*  14:    */ import net.minecraft.inventory.ICrafting;
/*  15:    */ import net.minecraft.inventory.Slot;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*  18:    */ import net.minecraft.util.ResourceLocation;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ import org.apache.commons.io.Charsets;
/*  21:    */ import org.lwjgl.input.Keyboard;
/*  22:    */ import org.lwjgl.opengl.GL11;
/*  23:    */ 
/*  24:    */ public class GuiRepair
/*  25:    */   extends GuiContainer
/*  26:    */   implements ICrafting
/*  27:    */ {
/*  28: 22 */   private static final ResourceLocation field_147093_u = new ResourceLocation("textures/gui/container/anvil.png");
/*  29:    */   private ContainerRepair field_147092_v;
/*  30:    */   private GuiTextField field_147091_w;
/*  31:    */   private InventoryPlayer field_147094_x;
/*  32:    */   private static final String __OBFID = "CL_00000738";
/*  33:    */   
/*  34:    */   public GuiRepair(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
/*  35:    */   {
/*  36: 30 */     super(new ContainerRepair(par1InventoryPlayer, par2World, par3, par4, par5, Minecraft.getMinecraft().thePlayer));
/*  37: 31 */     this.field_147094_x = par1InventoryPlayer;
/*  38: 32 */     this.field_147092_v = ((ContainerRepair)this.field_147002_h);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void initGui()
/*  42:    */   {
/*  43: 40 */     super.initGui();
/*  44: 41 */     Keyboard.enableRepeatEvents(true);
/*  45: 42 */     int var1 = (width - this.field_146999_f) / 2;
/*  46: 43 */     int var2 = (height - this.field_147000_g) / 2;
/*  47: 44 */     this.field_147091_w = new GuiTextField(this.fontRendererObj, var1 + 62, var2 + 24, 103, 12);
/*  48: 45 */     this.field_147091_w.func_146193_g(-1);
/*  49: 46 */     this.field_147091_w.func_146204_h(-1);
/*  50: 47 */     this.field_147091_w.func_146185_a(false);
/*  51: 48 */     this.field_147091_w.func_146203_f(40);
/*  52: 49 */     this.field_147002_h.removeCraftingFromCrafters(this);
/*  53: 50 */     this.field_147002_h.addCraftingToCrafters(this);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void onGuiClosed()
/*  57:    */   {
/*  58: 58 */     super.onGuiClosed();
/*  59: 59 */     Keyboard.enableRepeatEvents(false);
/*  60: 60 */     this.field_147002_h.removeCraftingFromCrafters(this);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/*  64:    */   {
/*  65: 65 */     GL11.glDisable(2896);
/*  66: 66 */     GL11.glDisable(3042);
/*  67: 67 */     this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60, 6, 4210752);
/*  68: 69 */     if (this.field_147092_v.maximumCost > 0)
/*  69:    */     {
/*  70: 71 */       int var3 = 8453920;
/*  71: 72 */       boolean var4 = true;
/*  72: 73 */       String var5 = I18n.format("container.repair.cost", new Object[] { Integer.valueOf(this.field_147092_v.maximumCost) });
/*  73: 75 */       if ((this.field_147092_v.maximumCost >= 40) && (!this.mc.thePlayer.capabilities.isCreativeMode))
/*  74:    */       {
/*  75: 77 */         var5 = I18n.format("container.repair.expensive", new Object[0]);
/*  76: 78 */         var3 = 16736352;
/*  77:    */       }
/*  78: 80 */       else if (!this.field_147092_v.getSlot(2).getHasStack())
/*  79:    */       {
/*  80: 82 */         var4 = false;
/*  81:    */       }
/*  82: 84 */       else if (!this.field_147092_v.getSlot(2).canTakeStack(this.field_147094_x.player))
/*  83:    */       {
/*  84: 86 */         var3 = 16736352;
/*  85:    */       }
/*  86: 89 */       if (var4)
/*  87:    */       {
/*  88: 91 */         int var6 = 0xFF000000 | (var3 & 0xFCFCFC) >> 2 | var3 & 0xFF000000;
/*  89: 92 */         int var7 = this.field_146999_f - 8 - this.fontRendererObj.getStringWidth(var5);
/*  90: 93 */         byte var8 = 67;
/*  91: 95 */         if (this.fontRendererObj.getUnicodeFlag())
/*  92:    */         {
/*  93: 97 */           drawRect(var7 - 3, var8 - 2, this.field_146999_f - 7, var8 + 10, -16777216);
/*  94: 98 */           drawRect(var7 - 2, var8 - 1, this.field_146999_f - 8, var8 + 9, -12895429);
/*  95:    */         }
/*  96:    */         else
/*  97:    */         {
/*  98:102 */           this.fontRendererObj.drawString(var5, var7, var8 + 1, var6);
/*  99:103 */           this.fontRendererObj.drawString(var5, var7 + 1, var8, var6);
/* 100:104 */           this.fontRendererObj.drawString(var5, var7 + 1, var8 + 1, var6);
/* 101:    */         }
/* 102:107 */         this.fontRendererObj.drawString(var5, var7, var8, var3);
/* 103:    */       }
/* 104:    */     }
/* 105:111 */     GL11.glEnable(2896);
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void keyTyped(char par1, int par2)
/* 109:    */   {
/* 110:119 */     if (this.field_147091_w.textboxKeyTyped(par1, par2)) {
/* 111:121 */       func_147090_g();
/* 112:    */     } else {
/* 113:125 */       super.keyTyped(par1, par2);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void func_147090_g()
/* 118:    */   {
/* 119:131 */     String var1 = this.field_147091_w.getText();
/* 120:132 */     Slot var2 = this.field_147092_v.getSlot(0);
/* 121:134 */     if ((var2 != null) && (var2.getHasStack()) && (!var2.getStack().hasDisplayName()) && (var1.equals(var2.getStack().getDisplayName()))) {
/* 122:136 */       var1 = "";
/* 123:    */     }
/* 124:139 */     this.field_147092_v.updateItemName(var1);
/* 125:140 */     this.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|ItemName", var1.getBytes(Charsets.UTF_8)));
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 129:    */   {
/* 130:148 */     super.mouseClicked(par1, par2, par3);
/* 131:149 */     this.field_147091_w.mouseClicked(par1, par2, par3);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void drawScreen(int par1, int par2, float par3)
/* 135:    */   {
/* 136:157 */     super.drawScreen(par1, par2, par3);
/* 137:158 */     GL11.glDisable(2896);
/* 138:159 */     GL11.glDisable(3042);
/* 139:160 */     this.field_147091_w.drawTextBox();
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 143:    */   {
/* 144:165 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 145:166 */     this.mc.getTextureManager().bindTexture(field_147093_u);
/* 146:167 */     int var4 = (width - this.field_146999_f) / 2;
/* 147:168 */     int var5 = (height - this.field_147000_g) / 2;
/* 148:169 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/* 149:170 */     drawTexturedModalRect(var4 + 59, var5 + 20, 0, this.field_147000_g + (this.field_147092_v.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
/* 150:172 */     if (((this.field_147092_v.getSlot(0).getHasStack()) || (this.field_147092_v.getSlot(1).getHasStack())) && (!this.field_147092_v.getSlot(2).getHasStack())) {
/* 151:174 */       drawTexturedModalRect(var4 + 99, var5 + 45, this.field_146999_f, 0, 28, 21);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void sendContainerAndContentsToPlayer(Container par1Container, List par2List)
/* 156:    */   {
/* 157:180 */     sendSlotContents(par1Container, 0, par1Container.getSlot(0).getStack());
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack)
/* 161:    */   {
/* 162:189 */     if (par2 == 0)
/* 163:    */     {
/* 164:191 */       this.field_147091_w.setText(par3ItemStack == null ? "" : par3ItemStack.getDisplayName());
/* 165:192 */       this.field_147091_w.func_146184_c(par3ItemStack != null);
/* 166:194 */       if (par3ItemStack != null) {
/* 167:196 */         func_147090_g();
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void sendProgressBarUpdate(Container par1Container, int par2, int par3) {}
/* 173:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiRepair
 * JD-Core Version:    0.7.0.1
 */
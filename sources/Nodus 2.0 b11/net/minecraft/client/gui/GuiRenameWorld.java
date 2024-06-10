/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.resources.I18n;
/*   7:    */ import net.minecraft.world.storage.ISaveFormat;
/*   8:    */ import net.minecraft.world.storage.WorldInfo;
/*   9:    */ import org.lwjgl.input.Keyboard;
/*  10:    */ 
/*  11:    */ public class GuiRenameWorld
/*  12:    */   extends GuiScreen
/*  13:    */ {
/*  14:    */   private GuiScreen field_146585_a;
/*  15:    */   private GuiTextField field_146583_f;
/*  16:    */   private final String field_146584_g;
/*  17:    */   private static final String __OBFID = "CL_00000709";
/*  18:    */   
/*  19:    */   public GuiRenameWorld(GuiScreen par1GuiScreen, String par2Str)
/*  20:    */   {
/*  21: 19 */     this.field_146585_a = par1GuiScreen;
/*  22: 20 */     this.field_146584_g = par2Str;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void updateScreen()
/*  26:    */   {
/*  27: 28 */     this.field_146583_f.updateCursorCounter();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void initGui()
/*  31:    */   {
/*  32: 36 */     Keyboard.enableRepeatEvents(true);
/*  33: 37 */     this.buttonList.clear();
/*  34: 38 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
/*  35: 39 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*  36: 40 */     ISaveFormat var1 = this.mc.getSaveLoader();
/*  37: 41 */     WorldInfo var2 = var1.getWorldInfo(this.field_146584_g);
/*  38: 42 */     String var3 = var2.getWorldName();
/*  39: 43 */     this.field_146583_f = new GuiTextField(this.fontRendererObj, width / 2 - 100, 60, 200, 20);
/*  40: 44 */     this.field_146583_f.setFocused(true);
/*  41: 45 */     this.field_146583_f.setText(var3);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void onGuiClosed()
/*  45:    */   {
/*  46: 53 */     Keyboard.enableRepeatEvents(false);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  50:    */   {
/*  51: 58 */     if (p_146284_1_.enabled) {
/*  52: 60 */       if (p_146284_1_.id == 1)
/*  53:    */       {
/*  54: 62 */         this.mc.displayGuiScreen(this.field_146585_a);
/*  55:    */       }
/*  56: 64 */       else if (p_146284_1_.id == 0)
/*  57:    */       {
/*  58: 66 */         ISaveFormat var2 = this.mc.getSaveLoader();
/*  59: 67 */         var2.renameWorld(this.field_146584_g, this.field_146583_f.getText().trim());
/*  60: 68 */         this.mc.displayGuiScreen(this.field_146585_a);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void keyTyped(char par1, int par2)
/*  66:    */   {
/*  67: 78 */     this.field_146583_f.textboxKeyTyped(par1, par2);
/*  68: 79 */     ((NodusGuiButton)this.buttonList.get(0)).enabled = (this.field_146583_f.getText().trim().length() > 0);
/*  69: 81 */     if ((par2 == 28) || (par2 == 156)) {
/*  70: 83 */       actionPerformed((NodusGuiButton)this.buttonList.get(0));
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  75:    */   {
/*  76: 92 */     super.mouseClicked(par1, par2, par3);
/*  77: 93 */     this.field_146583_f.mouseClicked(par1, par2, par3);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void drawScreen(int par1, int par2, float par3)
/*  81:    */   {
/*  82:101 */     drawDefaultBackground();
/*  83:102 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), width / 2, 20, 16777215);
/*  84:103 */     drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), width / 2 - 100, 47, 10526880);
/*  85:104 */     this.field_146583_f.drawTextBox();
/*  86:105 */     super.drawScreen(par1, par2, par3);
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiRenameWorld
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import io.netty.buffer.ByteBuf;
/*   4:    */ import java.text.SimpleDateFormat;
/*   5:    */ import java.util.Date;
/*   6:    */ import net.minecraft.command.ICommandManager;
/*   7:    */ import net.minecraft.command.ICommandSender;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.server.MinecraftServer;
/*  10:    */ import net.minecraft.util.ChatComponentText;
/*  11:    */ import net.minecraft.util.IChatComponent;
/*  12:    */ import net.minecraft.util.IChatComponent.Serializer;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public abstract class CommandBlockLogic
/*  16:    */   implements ICommandSender
/*  17:    */ {
/*  18: 16 */   private static final SimpleDateFormat field_145766_a = new SimpleDateFormat("HH:mm:ss");
/*  19:    */   private int field_145764_b;
/*  20: 18 */   private boolean field_145765_c = true;
/*  21: 19 */   private IChatComponent field_145762_d = null;
/*  22: 20 */   private String field_145763_e = "";
/*  23: 21 */   private String field_145761_f = "@";
/*  24:    */   private static final String __OBFID = "CL_00000128";
/*  25:    */   
/*  26:    */   public int func_145760_g()
/*  27:    */   {
/*  28: 26 */     return this.field_145764_b;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public IChatComponent func_145749_h()
/*  32:    */   {
/*  33: 31 */     return this.field_145762_d;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void func_145758_a(NBTTagCompound p_145758_1_)
/*  37:    */   {
/*  38: 36 */     p_145758_1_.setString("Command", this.field_145763_e);
/*  39: 37 */     p_145758_1_.setInteger("SuccessCount", this.field_145764_b);
/*  40: 38 */     p_145758_1_.setString("CustomName", this.field_145761_f);
/*  41: 40 */     if (this.field_145762_d != null) {
/*  42: 42 */       p_145758_1_.setString("LastOutput", IChatComponent.Serializer.func_150696_a(this.field_145762_d));
/*  43:    */     }
/*  44: 45 */     p_145758_1_.setBoolean("TrackOutput", this.field_145765_c);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void func_145759_b(NBTTagCompound p_145759_1_)
/*  48:    */   {
/*  49: 50 */     this.field_145763_e = p_145759_1_.getString("Command");
/*  50: 51 */     this.field_145764_b = p_145759_1_.getInteger("SuccessCount");
/*  51: 53 */     if (p_145759_1_.func_150297_b("CustomName", 8)) {
/*  52: 55 */       this.field_145761_f = p_145759_1_.getString("CustomName");
/*  53:    */     }
/*  54: 58 */     if (p_145759_1_.func_150297_b("LastOutput", 8)) {
/*  55: 60 */       this.field_145762_d = IChatComponent.Serializer.func_150699_a(p_145759_1_.getString("LastOutput"));
/*  56:    */     }
/*  57: 63 */     if (p_145759_1_.func_150297_b("TrackOutput", 1)) {
/*  58: 65 */       this.field_145765_c = p_145759_1_.getBoolean("TrackOutput");
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean canCommandSenderUseCommand(int par1, String par2Str)
/*  63:    */   {
/*  64: 74 */     return par1 <= 2;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void func_145752_a(String p_145752_1_)
/*  68:    */   {
/*  69: 79 */     this.field_145763_e = p_145752_1_;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String func_145753_i()
/*  73:    */   {
/*  74: 84 */     return this.field_145763_e;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void func_145755_a(World p_145755_1_)
/*  78:    */   {
/*  79: 89 */     if (p_145755_1_.isClient) {
/*  80: 91 */       this.field_145764_b = 0;
/*  81:    */     }
/*  82: 94 */     MinecraftServer var2 = MinecraftServer.getServer();
/*  83: 96 */     if ((var2 != null) && (var2.isCommandBlockEnabled()))
/*  84:    */     {
/*  85: 98 */       ICommandManager var3 = var2.getCommandManager();
/*  86: 99 */       this.field_145764_b = var3.executeCommand(this, this.field_145763_e);
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90:103 */       this.field_145764_b = 0;
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getCommandSenderName()
/*  95:    */   {
/*  96:112 */     return this.field_145761_f;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public IChatComponent func_145748_c_()
/* 100:    */   {
/* 101:117 */     return new ChatComponentText(getCommandSenderName());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void func_145754_b(String p_145754_1_)
/* 105:    */   {
/* 106:122 */     this.field_145761_f = p_145754_1_;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void addChatMessage(IChatComponent p_145747_1_)
/* 110:    */   {
/* 111:133 */     if ((this.field_145765_c) && (getEntityWorld() != null) && (!getEntityWorld().isClient))
/* 112:    */     {
/* 113:135 */       this.field_145762_d = new ChatComponentText("[" + field_145766_a.format(new Date()) + "] ").appendSibling(p_145747_1_);
/* 114:136 */       func_145756_e();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public abstract void func_145756_e();
/* 119:    */   
/* 120:    */   public abstract int func_145751_f();
/* 121:    */   
/* 122:    */   public abstract void func_145757_a(ByteBuf paramByteBuf);
/* 123:    */   
/* 124:    */   public void func_145750_b(IChatComponent p_145750_1_)
/* 125:    */   {
/* 126:148 */     this.field_145762_d = p_145750_1_;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandBlockLogic
 * JD-Core Version:    0.7.0.1
 */
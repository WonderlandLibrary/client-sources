/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.GameProfile;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.entity.DataWatcher;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.network.INetHandler;
/*  12:    */ import net.minecraft.network.Packet;
/*  13:    */ import net.minecraft.network.PacketBuffer;
/*  14:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  15:    */ import net.minecraft.util.MathHelper;
/*  16:    */ 
/*  17:    */ public class S0CPacketSpawnPlayer
/*  18:    */   extends Packet
/*  19:    */ {
/*  20:    */   private int field_148957_a;
/*  21:    */   private GameProfile field_148955_b;
/*  22:    */   private int field_148956_c;
/*  23:    */   private int field_148953_d;
/*  24:    */   private int field_148954_e;
/*  25:    */   private byte field_148951_f;
/*  26:    */   private byte field_148952_g;
/*  27:    */   private int field_148959_h;
/*  28:    */   private DataWatcher field_148960_i;
/*  29:    */   private List field_148958_j;
/*  30:    */   private static final String __OBFID = "CL_00001281";
/*  31:    */   
/*  32:    */   public S0CPacketSpawnPlayer() {}
/*  33:    */   
/*  34:    */   public S0CPacketSpawnPlayer(EntityPlayer p_i45171_1_)
/*  35:    */   {
/*  36: 34 */     this.field_148957_a = p_i45171_1_.getEntityId();
/*  37: 35 */     this.field_148955_b = p_i45171_1_.getGameProfile();
/*  38: 36 */     this.field_148956_c = MathHelper.floor_double(p_i45171_1_.posX * 32.0D);
/*  39: 37 */     this.field_148953_d = MathHelper.floor_double(p_i45171_1_.posY * 32.0D);
/*  40: 38 */     this.field_148954_e = MathHelper.floor_double(p_i45171_1_.posZ * 32.0D);
/*  41: 39 */     this.field_148951_f = ((byte)(int)(p_i45171_1_.rotationYaw * 256.0F / 360.0F));
/*  42: 40 */     this.field_148952_g = ((byte)(int)(p_i45171_1_.rotationPitch * 256.0F / 360.0F));
/*  43: 41 */     ItemStack var2 = p_i45171_1_.inventory.getCurrentItem();
/*  44: 42 */     this.field_148959_h = (var2 == null ? 0 : Item.getIdFromItem(var2.getItem()));
/*  45: 43 */     this.field_148960_i = p_i45171_1_.getDataWatcher();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 51 */     this.field_148957_a = p_148837_1_.readVarIntFromBuffer();
/*  52: 52 */     this.field_148955_b = new GameProfile(p_148837_1_.readStringFromBuffer(36), p_148837_1_.readStringFromBuffer(16));
/*  53: 53 */     this.field_148956_c = p_148837_1_.readInt();
/*  54: 54 */     this.field_148953_d = p_148837_1_.readInt();
/*  55: 55 */     this.field_148954_e = p_148837_1_.readInt();
/*  56: 56 */     this.field_148951_f = p_148837_1_.readByte();
/*  57: 57 */     this.field_148952_g = p_148837_1_.readByte();
/*  58: 58 */     this.field_148959_h = p_148837_1_.readShort();
/*  59: 59 */     this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(p_148837_1_);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 67 */     p_148840_1_.writeVarIntToBuffer(this.field_148957_a);
/*  66: 68 */     p_148840_1_.writeStringToBuffer(this.field_148955_b.getId());
/*  67: 69 */     p_148840_1_.writeStringToBuffer(this.field_148955_b.getName());
/*  68: 70 */     p_148840_1_.writeInt(this.field_148956_c);
/*  69: 71 */     p_148840_1_.writeInt(this.field_148953_d);
/*  70: 72 */     p_148840_1_.writeInt(this.field_148954_e);
/*  71: 73 */     p_148840_1_.writeByte(this.field_148951_f);
/*  72: 74 */     p_148840_1_.writeByte(this.field_148952_g);
/*  73: 75 */     p_148840_1_.writeShort(this.field_148959_h);
/*  74: 76 */     this.field_148960_i.func_151509_a(p_148840_1_);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void processPacket(INetHandlerPlayClient p_148950_1_)
/*  78:    */   {
/*  79: 81 */     p_148950_1_.handleSpawnPlayer(this);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List func_148944_c()
/*  83:    */   {
/*  84: 86 */     if (this.field_148958_j == null) {
/*  85: 88 */       this.field_148958_j = this.field_148960_i.getAllWatched();
/*  86:    */     }
/*  87: 91 */     return this.field_148958_j;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String serialize()
/*  91:    */   {
/*  92: 99 */     return String.format("id=%d, gameProfile='%s', x=%.2f, y=%.2f, z=%.2f, carried=%d", new Object[] { Integer.valueOf(this.field_148957_a), this.field_148955_b, Float.valueOf(this.field_148956_c / 32.0F), Float.valueOf(this.field_148953_d / 32.0F), Float.valueOf(this.field_148954_e / 32.0F), Integer.valueOf(this.field_148959_h) });
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int func_148943_d()
/*  96:    */   {
/*  97:104 */     return this.field_148957_a;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public GameProfile func_148948_e()
/* 101:    */   {
/* 102:109 */     return this.field_148955_b;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int func_148942_f()
/* 106:    */   {
/* 107:114 */     return this.field_148956_c;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int func_148949_g()
/* 111:    */   {
/* 112:119 */     return this.field_148953_d;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int func_148946_h()
/* 116:    */   {
/* 117:124 */     return this.field_148954_e;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public byte func_148941_i()
/* 121:    */   {
/* 122:129 */     return this.field_148951_f;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public byte func_148945_j()
/* 126:    */   {
/* 127:134 */     return this.field_148952_g;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int func_148947_k()
/* 131:    */   {
/* 132:139 */     return this.field_148959_h;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void processPacket(INetHandler p_148833_1_)
/* 136:    */   {
/* 137:144 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S0CPacketSpawnPlayer
 * JD-Core Version:    0.7.0.1
 */
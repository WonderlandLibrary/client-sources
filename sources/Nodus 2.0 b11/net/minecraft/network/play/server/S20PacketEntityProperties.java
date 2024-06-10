/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.UUID;
/*   9:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  10:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  11:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  12:    */ import net.minecraft.network.INetHandler;
/*  13:    */ import net.minecraft.network.Packet;
/*  14:    */ import net.minecraft.network.PacketBuffer;
/*  15:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  16:    */ 
/*  17:    */ public class S20PacketEntityProperties
/*  18:    */   extends Packet
/*  19:    */ {
/*  20:    */   private int field_149445_a;
/*  21: 19 */   private final List field_149444_b = new ArrayList();
/*  22:    */   private static final String __OBFID = "CL_00001341";
/*  23:    */   
/*  24:    */   public S20PacketEntityProperties() {}
/*  25:    */   
/*  26:    */   public S20PacketEntityProperties(int p_i45236_1_, Collection p_i45236_2_)
/*  27:    */   {
/*  28: 26 */     this.field_149445_a = p_i45236_1_;
/*  29: 27 */     Iterator var3 = p_i45236_2_.iterator();
/*  30: 29 */     while (var3.hasNext())
/*  31:    */     {
/*  32: 31 */       IAttributeInstance var4 = (IAttributeInstance)var3.next();
/*  33: 32 */       this.field_149444_b.add(new Snapshot(var4.getAttribute().getAttributeUnlocalizedName(), var4.getBaseValue(), var4.func_111122_c()));
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 41 */     this.field_149445_a = p_148837_1_.readInt();
/*  41: 42 */     int var2 = p_148837_1_.readInt();
/*  42: 44 */     for (int var3 = 0; var3 < var2; var3++)
/*  43:    */     {
/*  44: 46 */       String var4 = p_148837_1_.readStringFromBuffer(64);
/*  45: 47 */       double var5 = p_148837_1_.readDouble();
/*  46: 48 */       ArrayList var7 = new ArrayList();
/*  47: 49 */       short var8 = p_148837_1_.readShort();
/*  48: 51 */       for (int var9 = 0; var9 < var8; var9++)
/*  49:    */       {
/*  50: 53 */         UUID var10 = new UUID(p_148837_1_.readLong(), p_148837_1_.readLong());
/*  51: 54 */         var7.add(new AttributeModifier(var10, "Unknown synced attribute modifier", p_148837_1_.readDouble(), p_148837_1_.readByte()));
/*  52:    */       }
/*  53: 57 */       this.field_149444_b.add(new Snapshot(var4, var5, var7));
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 66 */     p_148840_1_.writeInt(this.field_149445_a);
/*  61: 67 */     p_148840_1_.writeInt(this.field_149444_b.size());
/*  62: 68 */     Iterator var2 = this.field_149444_b.iterator();
/*  63:    */     Iterator var4;
/*  64: 70 */     for (; var2.hasNext(); var4.hasNext())
/*  65:    */     {
/*  66: 72 */       Snapshot var3 = (Snapshot)var2.next();
/*  67: 73 */       p_148840_1_.writeStringToBuffer(var3.func_151409_a());
/*  68: 74 */       p_148840_1_.writeDouble(var3.func_151410_b());
/*  69: 75 */       p_148840_1_.writeShort(var3.func_151408_c().size());
/*  70: 76 */       var4 = var3.func_151408_c().iterator();
/*  71:    */       
/*  72: 78 */       continue;
/*  73:    */       
/*  74: 80 */       AttributeModifier var5 = (AttributeModifier)var4.next();
/*  75: 81 */       p_148840_1_.writeLong(var5.getID().getMostSignificantBits());
/*  76: 82 */       p_148840_1_.writeLong(var5.getID().getLeastSignificantBits());
/*  77: 83 */       p_148840_1_.writeDouble(var5.getAmount());
/*  78: 84 */       p_148840_1_.writeByte(var5.getOperation());
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void processPacket(INetHandlerPlayClient p_149443_1_)
/*  83:    */   {
/*  84: 91 */     p_149443_1_.handleEntityProperties(this);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int func_149442_c()
/*  88:    */   {
/*  89: 96 */     return this.field_149445_a;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List func_149441_d()
/*  93:    */   {
/*  94:101 */     return this.field_149444_b;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void processPacket(INetHandler p_148833_1_)
/*  98:    */   {
/*  99:106 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public class Snapshot
/* 103:    */   {
/* 104:    */     private final String field_151412_b;
/* 105:    */     private final double field_151413_c;
/* 106:    */     private final Collection field_151411_d;
/* 107:    */     private static final String __OBFID = "CL_00001342";
/* 108:    */     
/* 109:    */     public Snapshot(String p_i45235_2_, double p_i45235_3_, Collection p_i45235_5_)
/* 110:    */     {
/* 111:118 */       this.field_151412_b = p_i45235_2_;
/* 112:119 */       this.field_151413_c = p_i45235_3_;
/* 113:120 */       this.field_151411_d = p_i45235_5_;
/* 114:    */     }
/* 115:    */     
/* 116:    */     public String func_151409_a()
/* 117:    */     {
/* 118:125 */       return this.field_151412_b;
/* 119:    */     }
/* 120:    */     
/* 121:    */     public double func_151410_b()
/* 122:    */     {
/* 123:130 */       return this.field_151413_c;
/* 124:    */     }
/* 125:    */     
/* 126:    */     public Collection func_151408_c()
/* 127:    */     {
/* 128:135 */       return this.field_151411_d;
/* 129:    */     }
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S20PacketEntityProperties
 * JD-Core Version:    0.7.0.1
 */
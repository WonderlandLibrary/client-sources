/*  1:   */ package net.minecraft.network.status.server;
/*  2:   */ 
/*  3:   */ import com.google.gson.Gson;
/*  4:   */ import com.google.gson.GsonBuilder;
/*  5:   */ import java.io.IOException;
/*  6:   */ import net.minecraft.network.INetHandler;
/*  7:   */ import net.minecraft.network.Packet;
/*  8:   */ import net.minecraft.network.PacketBuffer;
/*  9:   */ import net.minecraft.network.ServerStatusResponse;
/* 10:   */ import net.minecraft.network.ServerStatusResponse.MinecraftProtocolVersionIdentifier;
/* 11:   */ import net.minecraft.network.ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer;
/* 12:   */ import net.minecraft.network.ServerStatusResponse.PlayerCountData;
/* 13:   */ import net.minecraft.network.ServerStatusResponse.PlayerCountData.Serializer;
/* 14:   */ import net.minecraft.network.ServerStatusResponse.Serializer;
/* 15:   */ import net.minecraft.network.status.INetHandlerStatusClient;
/* 16:   */ import net.minecraft.util.ChatStyle;
/* 17:   */ import net.minecraft.util.ChatStyle.Serializer;
/* 18:   */ import net.minecraft.util.EnumTypeAdapterFactory;
/* 19:   */ import net.minecraft.util.IChatComponent;
/* 20:   */ import net.minecraft.util.IChatComponent.Serializer;
/* 21:   */ 
/* 22:   */ public class S00PacketServerInfo
/* 23:   */   extends Packet
/* 24:   */ {
/* 25:17 */   private static final Gson field_149297_a = new GsonBuilder().registerTypeAdapter(ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter(ServerStatusResponse.PlayerCountData.class, new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
/* 26:   */   private ServerStatusResponse field_149296_b;
/* 27:   */   private static final String __OBFID = "CL_00001384";
/* 28:   */   
/* 29:   */   public S00PacketServerInfo() {}
/* 30:   */   
/* 31:   */   public S00PacketServerInfo(ServerStatusResponse p_i45273_1_)
/* 32:   */   {
/* 33:25 */     this.field_149296_b = p_i45273_1_;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:33 */     this.field_149296_b = ((ServerStatusResponse)field_149297_a.fromJson(p_148837_1_.readStringFromBuffer(32767), ServerStatusResponse.class));
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:41 */     p_148840_1_.writeStringToBuffer(field_149297_a.toJson(this.field_149296_b));
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void processPacket(INetHandlerStatusClient p_149295_1_)
/* 49:   */   {
/* 50:46 */     p_149295_1_.handleServerInfo(this);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public ServerStatusResponse func_149294_c()
/* 54:   */   {
/* 55:51 */     return this.field_149296_b;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean hasPriority()
/* 59:   */   {
/* 60:60 */     return true;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void processPacket(INetHandler p_148833_1_)
/* 64:   */   {
/* 65:65 */     processPacket((INetHandlerStatusClient)p_148833_1_);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.status.server.S00PacketServerInfo
 * JD-Core Version:    0.7.0.1
 */
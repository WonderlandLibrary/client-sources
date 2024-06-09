package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S45PacketTitle implements Packet {
   private S45PacketTitle.Type type;
   private IChatComponent message;
   private int fadeInTime;
   private int displayTime;
   private int fadeOutTime;

   public S45PacketTitle() {
   }

   public S45PacketTitle(S45PacketTitle.Type p_i45953_1_, IChatComponent p_i45953_2_) {
      this(p_i45953_1_, p_i45953_2_, -1, -1, -1);
   }

   public S45PacketTitle(int p_i45954_1_, int p_i45954_2_, int p_i45954_3_) {
      this(S45PacketTitle.Type.TIMES, (IChatComponent)null, p_i45954_1_, p_i45954_2_, p_i45954_3_);
   }

   public S45PacketTitle(S45PacketTitle.Type p_i45955_1_, IChatComponent p_i45955_2_, int p_i45955_3_, int p_i45955_4_, int p_i45955_5_) {
      this.type = p_i45955_1_;
      this.message = p_i45955_2_;
      this.fadeInTime = p_i45955_3_;
      this.displayTime = p_i45955_4_;
      this.fadeOutTime = p_i45955_5_;
   }

   public void readPacketData(PacketBuffer data) throws IOException {
      this.type = (S45PacketTitle.Type)data.readEnumValue(S45PacketTitle.Type.class);
      if (this.type == S45PacketTitle.Type.TITLE || this.type == S45PacketTitle.Type.SUBTITLE) {
         this.message = data.readChatComponent();
      }

      if (this.type == S45PacketTitle.Type.TIMES) {
         this.fadeInTime = data.readInt();
         this.displayTime = data.readInt();
         this.fadeOutTime = data.readInt();
      }

   }

   public void writePacketData(PacketBuffer data) throws IOException {
      data.writeEnumValue(this.type);
      if (this.type == S45PacketTitle.Type.TITLE || this.type == S45PacketTitle.Type.SUBTITLE) {
         data.writeChatComponent(this.message);
      }

      if (this.type == S45PacketTitle.Type.TIMES) {
         data.writeInt(this.fadeInTime);
         data.writeInt(this.displayTime);
         data.writeInt(this.fadeOutTime);
      }

   }

   public void func_179802_a(INetHandlerPlayClient p_179802_1_) {
      p_179802_1_.func_175099_a(this);
   }

   public S45PacketTitle.Type getType() {
      return this.type;
   }

   public IChatComponent getMessage() {
      return this.message;
   }

   public int getFadeInTime() {
      return this.fadeInTime;
   }

   public int getDisplayTime() {
      return this.displayTime;
   }

   public int getFadeOutTime() {
      return this.fadeOutTime;
   }

   public void processPacket(INetHandler handler) {
      this.func_179802_a((INetHandlerPlayClient)handler);
   }

   public static enum Type {
      TITLE("TITLE", 0),
      SUBTITLE("SUBTITLE", 1),
      TIMES("TIMES", 2),
      CLEAR("CLEAR", 3),
      RESET("RESET", 4);

      private static final S45PacketTitle.Type[] $VALUES = new S45PacketTitle.Type[]{TITLE, SUBTITLE, TIMES, CLEAR, RESET};

      private Type(String p_i45952_1_, int p_i45952_2_) {
      }

      public static S45PacketTitle.Type func_179969_a(String p_179969_0_) {
         S45PacketTitle.Type[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            S45PacketTitle.Type var4 = var1[var3];
            if (var4.name().equalsIgnoreCase(p_179969_0_)) {
               return var4;
            }
         }

         return TITLE;
      }

      public static String[] func_179971_a() {
         String[] var0 = new String[values().length];
         int var1 = 0;
         S45PacketTitle.Type[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            S45PacketTitle.Type var5 = var2[var4];
            var0[var1++] = var5.name().toLowerCase();
         }

         return var0;
      }
   }
}

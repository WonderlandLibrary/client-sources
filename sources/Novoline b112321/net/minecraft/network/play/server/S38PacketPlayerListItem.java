package net.minecraft.network.play.server;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import net.aXg;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S38PacketPlayerListItem$1;
import net.minecraft.network.play.server.S38PacketPlayerListItem$Action;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings$GameType;

public class S38PacketPlayerListItem implements Packet {
   private S38PacketPlayerListItem$Action action;
   private final List players = Lists.newArrayList();

   public S38PacketPlayerListItem() {
   }

   public S38PacketPlayerListItem(S38PacketPlayerListItem$Action var1, EntityPlayerMP... var2) {
      this.action = var1;

      for(EntityPlayerMP var6 : var2) {
         this.players.add(new aXg(this, var6.getGameProfile(), var6.ping, var6.theItemInWorldManager.getGameType(), var6.getTabListDisplayName()));
      }

   }

   public S38PacketPlayerListItem(S38PacketPlayerListItem$Action var1, Iterable var2) {
      this.action = var1;

      for(EntityPlayerMP var4 : var2) {
         this.players.add(new aXg(this, var4.getGameProfile(), var4.ping, var4.theItemInWorldManager.getGameType(), var4.getTabListDisplayName()));
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.action = (S38PacketPlayerListItem$Action)var1.readEnumValue(S38PacketPlayerListItem$Action.class);
      int var2 = var1.readVarIntFromBuffer();

      for(int var3 = 0; var3 < var2; ++var3) {
         GameProfile var4 = null;
         int var5 = 0;
         WorldSettings$GameType var6 = null;
         IChatComponent var7 = null;
         switch(S38PacketPlayerListItem$1.$SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[this.action.ordinal()]) {
         case 1:
            var4 = new GameProfile(var1.readUuid(), var1.a(16));
            int var8 = var1.readVarIntFromBuffer();
            int var9 = 0;

            for(; var9 < var8; ++var9) {
               String var10 = var1.a(32767);
               String var11 = var1.a(32767);
               if(var1.readBoolean()) {
                  var4.getProperties().put(var10, new Property(var10, var11, var1.a(32767)));
               } else {
                  var4.getProperties().put(var10, new Property(var10, var11));
               }
            }

            var6 = WorldSettings$GameType.getByID(var1.readVarIntFromBuffer());
            var5 = var1.readVarIntFromBuffer();
            if(var1.readBoolean()) {
               var7 = var1.readChatComponent();
            }
            break;
         case 2:
            var4 = new GameProfile(var1.readUuid(), (String)null);
            var6 = WorldSettings$GameType.getByID(var1.readVarIntFromBuffer());
            break;
         case 3:
            var4 = new GameProfile(var1.readUuid(), (String)null);
            var5 = var1.readVarIntFromBuffer();
            break;
         case 4:
            var4 = new GameProfile(var1.readUuid(), (String)null);
            if(var1.readBoolean()) {
               var7 = var1.readChatComponent();
            }
            break;
         case 5:
            var4 = new GameProfile(var1.readUuid(), (String)null);
         }

         this.players.add(new aXg(this, var4, var5, var6, var7));
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.action);
      var1.writeVarIntToBuffer(this.players.size());

      for(aXg var3 : this.players) {
         switch(S38PacketPlayerListItem$1.$SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[this.action.ordinal()]) {
         case 1:
            var1.writeUuid(var3.a().getId());
            var1.writeString(var3.a().getName());
            var1.writeVarIntToBuffer(var3.a().getProperties().size());

            for(Property var5 : var3.a().getProperties().values()) {
               var1.writeString(var5.getName());
               var1.writeString(var5.getValue());
               if(var5.hasSignature()) {
                  var1.writeBoolean(true);
                  var1.writeString(var5.getSignature());
               } else {
                  var1.writeBoolean(false);
               }
            }

            var1.writeVarIntToBuffer(var3.d().getID());
            var1.writeVarIntToBuffer(var3.c());
            if(var3.b() == null) {
               var1.writeBoolean(false);
            } else {
               var1.writeBoolean(true);
               var1.writeChatComponent(var3.b());
            }
            break;
         case 2:
            var1.writeUuid(var3.a().getId());
            var1.writeVarIntToBuffer(var3.d().getID());
            break;
         case 3:
            var1.writeUuid(var3.a().getId());
            var1.writeVarIntToBuffer(var3.c());
            break;
         case 4:
            var1.writeUuid(var3.a().getId());
            if(var3.b() == null) {
               var1.writeBoolean(false);
            } else {
               var1.writeBoolean(true);
               var1.writeChatComponent(var3.b());
            }
            break;
         case 5:
            var1.writeUuid(var3.a().getId());
         }
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handlePlayerListItem(this);
   }

   public List playersDataList() {
      return this.players;
   }

   public S38PacketPlayerListItem$Action getAction() {
      return this.action;
   }

   public String toString() {
      return Objects.toStringHelper(this).add("action", this.action).add("entries", this.players).toString();
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

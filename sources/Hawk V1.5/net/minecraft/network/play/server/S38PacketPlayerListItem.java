package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class S38PacketPlayerListItem implements Packet {
   private S38PacketPlayerListItem.Action field_179770_a;
   private static final String __OBFID = "CL_00001318";
   private final List field_179769_b = Lists.newArrayList();

   public void func_180743_a(INetHandlerPlayClient var1) {
      var1.handlePlayerListItem(this);
   }

   public void processPacket(INetHandler var1) {
      this.func_180743_a((INetHandlerPlayClient)var1);
   }

   public S38PacketPlayerListItem() {
   }

   public List func_179767_a() {
      return this.field_179769_b;
   }

   public S38PacketPlayerListItem(S38PacketPlayerListItem.Action var1, EntityPlayerMP... var2) {
      this.field_179770_a = var1;
      EntityPlayerMP[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EntityPlayerMP var6 = var3[var5];
         this.field_179769_b.add(new S38PacketPlayerListItem.AddPlayerData(this, var6.getGameProfile(), var6.ping, var6.theItemInWorldManager.getGameType(), var6.func_175396_E()));
      }

   }

   public S38PacketPlayerListItem(S38PacketPlayerListItem.Action var1, Iterable var2) {
      this.field_179770_a = var1;
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
         this.field_179769_b.add(new S38PacketPlayerListItem.AddPlayerData(this, var4.getGameProfile(), var4.ping, var4.theItemInWorldManager.getGameType(), var4.func_175396_E()));
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.field_179770_a);
      var1.writeVarIntToBuffer(this.field_179769_b.size());
      Iterator var2 = this.field_179769_b.iterator();

      while(true) {
         while(var2.hasNext()) {
            S38PacketPlayerListItem.AddPlayerData var3 = (S38PacketPlayerListItem.AddPlayerData)var2.next();
            switch(this.field_179770_a) {
            case ADD_PLAYER:
               var1.writeUuid(var3.func_179962_a().getId());
               var1.writeString(var3.func_179962_a().getName());
               var1.writeVarIntToBuffer(var3.func_179962_a().getProperties().size());
               Iterator var4 = var3.func_179962_a().getProperties().values().iterator();

               while(var4.hasNext()) {
                  Property var5 = (Property)var4.next();
                  var1.writeString(var5.getName());
                  var1.writeString(var5.getValue());
                  if (var5.hasSignature()) {
                     var1.writeBoolean(true);
                     var1.writeString(var5.getSignature());
                  } else {
                     var1.writeBoolean(false);
                  }
               }

               var1.writeVarIntToBuffer(var3.func_179960_c().getID());
               var1.writeVarIntToBuffer(var3.func_179963_b());
               if (var3.func_179961_d() == null) {
                  var1.writeBoolean(false);
               } else {
                  var1.writeBoolean(true);
                  var1.writeChatComponent(var3.func_179961_d());
               }
               break;
            case UPDATE_GAME_MODE:
               var1.writeUuid(var3.func_179962_a().getId());
               var1.writeVarIntToBuffer(var3.func_179960_c().getID());
               break;
            case UPDATE_LATENCY:
               var1.writeUuid(var3.func_179962_a().getId());
               var1.writeVarIntToBuffer(var3.func_179963_b());
               break;
            case UPDATE_DISPLAY_NAME:
               var1.writeUuid(var3.func_179962_a().getId());
               if (var3.func_179961_d() == null) {
                  var1.writeBoolean(false);
               } else {
                  var1.writeBoolean(true);
                  var1.writeChatComponent(var3.func_179961_d());
               }
               break;
            case REMOVE_PLAYER:
               var1.writeUuid(var3.func_179962_a().getId());
            }
         }

         return;
      }
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179770_a = (S38PacketPlayerListItem.Action)var1.readEnumValue(S38PacketPlayerListItem.Action.class);
      int var2 = var1.readVarIntFromBuffer();

      for(int var3 = 0; var3 < var2; ++var3) {
         GameProfile var4 = null;
         int var5 = 0;
         WorldSettings.GameType var6 = null;
         IChatComponent var7 = null;
         switch(this.field_179770_a) {
         case ADD_PLAYER:
            var4 = new GameProfile(var1.readUuid(), var1.readStringFromBuffer(16));
            int var8 = var1.readVarIntFromBuffer();
            int var9 = 0;

            for(; var9 < var8; ++var9) {
               String var10 = var1.readStringFromBuffer(32767);
               String var11 = var1.readStringFromBuffer(32767);
               if (var1.readBoolean()) {
                  var4.getProperties().put(var10, new Property(var10, var11, var1.readStringFromBuffer(32767)));
               } else {
                  var4.getProperties().put(var10, new Property(var10, var11));
               }
            }

            var6 = WorldSettings.GameType.getByID(var1.readVarIntFromBuffer());
            var5 = var1.readVarIntFromBuffer();
            if (var1.readBoolean()) {
               var7 = var1.readChatComponent();
            }
            break;
         case UPDATE_GAME_MODE:
            var4 = new GameProfile(var1.readUuid(), (String)null);
            var6 = WorldSettings.GameType.getByID(var1.readVarIntFromBuffer());
            break;
         case UPDATE_LATENCY:
            var4 = new GameProfile(var1.readUuid(), (String)null);
            var5 = var1.readVarIntFromBuffer();
            break;
         case UPDATE_DISPLAY_NAME:
            var4 = new GameProfile(var1.readUuid(), (String)null);
            if (var1.readBoolean()) {
               var7 = var1.readChatComponent();
            }
            break;
         case REMOVE_PLAYER:
            var4 = new GameProfile(var1.readUuid(), (String)null);
         }

         this.field_179769_b.add(new S38PacketPlayerListItem.AddPlayerData(this, var4, var5, var6, var7));
      }

   }

   public S38PacketPlayerListItem.Action func_179768_b() {
      return this.field_179770_a;
   }

   public static enum Action {
      private static final String __OBFID = "CL_00002295";
      UPDATE_LATENCY("UPDATE_LATENCY", 2),
      REMOVE_PLAYER("REMOVE_PLAYER", 4),
      UPDATE_GAME_MODE("UPDATE_GAME_MODE", 1),
      UPDATE_DISPLAY_NAME("UPDATE_DISPLAY_NAME", 3);

      private static final S38PacketPlayerListItem.Action[] $VALUES = new S38PacketPlayerListItem.Action[]{ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER};
      ADD_PLAYER("ADD_PLAYER", 0);

      private static final S38PacketPlayerListItem.Action[] ENUM$VALUES = new S38PacketPlayerListItem.Action[]{ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER};

      private Action(String var3, int var4) {
      }
   }

   static final class SwitchAction {
      static final int[] field_179938_a = new int[S38PacketPlayerListItem.Action.values().length];
      private static final String __OBFID = "CL_00002296";

      static {
         try {
            field_179938_a[S38PacketPlayerListItem.Action.ADD_PLAYER.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_179938_a[S38PacketPlayerListItem.Action.UPDATE_GAME_MODE.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_179938_a[S38PacketPlayerListItem.Action.UPDATE_LATENCY.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_179938_a[S38PacketPlayerListItem.Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_179938_a[S38PacketPlayerListItem.Action.REMOVE_PLAYER.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public class AddPlayerData {
      private final WorldSettings.GameType field_179967_c;
      private static final String __OBFID = "CL_00002294";
      private final int field_179966_b;
      final S38PacketPlayerListItem this$0;
      private final IChatComponent field_179965_e;
      private final GameProfile field_179964_d;

      public WorldSettings.GameType func_179960_c() {
         return this.field_179967_c;
      }

      public GameProfile func_179962_a() {
         return this.field_179964_d;
      }

      public IChatComponent func_179961_d() {
         return this.field_179965_e;
      }

      public int func_179963_b() {
         return this.field_179966_b;
      }

      public AddPlayerData(S38PacketPlayerListItem var1, GameProfile var2, int var3, WorldSettings.GameType var4, IChatComponent var5) {
         this.this$0 = var1;
         this.field_179964_d = var2;
         this.field_179966_b = var3;
         this.field_179967_c = var4;
         this.field_179965_e = var5;
      }
   }
}

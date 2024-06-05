package net;

import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.aRi;
import net.acE;
import net.afz;
import net.axZ;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage$GameProfile;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage$Property;
import viaversion.viarewind.utils.ChatUtil;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class aDl extends acE {
   public void registerMap() {
      this.a(aDl::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      afz.a();
      var0.cancel();
      int var2 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      int var3 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      GameProfileStorage var4 = (GameProfileStorage)var0.user().b(GameProfileStorage.class);
      int var5 = 0;
      if(var5 < var3) {
         UUID var6 = (UUID)var0.read(Type.UUID);
         if(var2 == 0) {
            String var7 = (String)var0.read(Type.STRING);
            GameProfileStorage$GameProfile var8 = var4.get(var6);
            if(var8 == null) {
               var8 = var4.put(var6, var7);
            }

            int var9 = ((Integer)var0.read(Type.VAR_INT)).intValue();
            if(var9-- > 0) {
               var8.properties.add(new GameProfileStorage$Property((String)var0.read(Type.STRING), (String)var0.read(Type.STRING), ((Boolean)var0.read(Type.BOOLEAN)).booleanValue()?(String)var0.read(Type.STRING):null));
            }

            int var10 = ((Integer)var0.read(Type.VAR_INT)).intValue();
            int var11 = ((Integer)var0.read(Type.VAR_INT)).intValue();
            var8.b = var11;
            var8.gamemode = var10;
            if(((Boolean)var0.read(Type.BOOLEAN)).booleanValue()) {
               var8.setDisplayName(ChatUtil.jsonToLegacy((JsonElement)var0.read(Type.COMPONENT)));
            }

            PacketWrapper var12 = new PacketWrapper(56, (ByteBuf)null, var0.user());
            var12.write(Type.STRING, var8.name);
            var12.write(Type.BOOLEAN, Boolean.valueOf(true));
            var12.write(Type.SHORT, Short.valueOf((short)var11));
            PacketUtil.sendPacket(var12, aRi.class);
         }

         label362: {
            if(var2 == 1) {
               int var15 = ((Integer)var0.read(Type.VAR_INT)).intValue();
               GameProfileStorage$GameProfile var19 = var4.get(var6);
               if(var19.gamemode == var15) {
                  break label362;
               }

               if(var15 == 3 || var19.gamemode == 3) {
                  EntityTracker var23 = (EntityTracker)var0.user().b(EntityTracker.class);
                  int var25 = var23.getPlayerEntityId(var6);
                  if(var25 != -1) {
                     if(var15 == 3) {
                        Item[] var26 = new Item[5];
                        var26[4] = var19.getSkull();
                     }

                     Item[] var27 = var23.getPlayerEquipment(var6);
                     if(var27 == null) {
                        var27 = new Item[5];
                     }

                     short var28 = 0;
                     if(var28 < 5) {
                        PacketWrapper var13 = new PacketWrapper(4, (ByteBuf)null, var0.user());
                        var13.write(Type.INT, Integer.valueOf(var25));
                        var13.write(Type.SHORT, Short.valueOf(var28));
                        var13.write(axZ.c, var27[var28]);
                        PacketUtil.sendPacket(var13, aRi.class);
                        ++var28;
                     }
                  }
               }

               var19.gamemode = var15;
            }

            if(var2 == 2) {
               int var16 = ((Integer)var0.read(Type.VAR_INT)).intValue();
               GameProfileStorage$GameProfile var20 = var4.get(var6);
               if(var20 == null) {
                  break label362;
               }

               var20.b = var16;
               PacketWrapper var24 = new PacketWrapper(56, (ByteBuf)null, var0.user());
               var24.write(Type.STRING, var20.name);
               var24.write(Type.BOOLEAN, Boolean.valueOf(true));
               var24.write(Type.SHORT, Short.valueOf((short)var16));
               PacketUtil.sendPacket(var24, aRi.class);
            }

            if(var2 == 3) {
               String var17 = ((Boolean)var0.read(Type.BOOLEAN)).booleanValue()?ChatUtil.jsonToLegacy((String)var0.read(Type.STRING)):null;
               GameProfileStorage$GameProfile var21 = var4.get(var6);
               if(var21.displayName == null && var17 == null) {
                  break label362;
               }

               if(var21.displayName == null && var17 != null || var21.displayName != null && var17 == null || !var21.displayName.equals(var17)) {
                  var21.setDisplayName(var17);
               }
            }

            if(var2 == 4) {
               GameProfileStorage$GameProfile var18 = var4.remove(var6);
            }
         }

         ++var5;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}

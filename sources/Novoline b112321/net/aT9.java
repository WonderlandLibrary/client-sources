package net;

import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.Dl;
import net.aR5;
import net.alB;
import net.cw;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;

public class aT9 extends MetadataRewriter {
   private static boolean e;

   public aT9(Protocol1_11To1_10 var1) {
      super(var1, cw.class);
   }

   protected void handleMetadata(int var1, EntityType var2, Metadata var3, List var4, UserConnection var5) {
      boolean var6 = b();
      if(var3.getValue() instanceof Item) {
         EntityIdRewriter.toClientItem((Item)var3.getValue());
      }

      if(var2 != null) {
         if(var2.is((EntityType)Dl.ELDER_GUARDIAN) || var2.is((EntityType)Dl.GUARDIAN)) {
            int var7 = var3.getId();
            if(var7 == 12) {
               var3.setMetaType(aR5.Boolean);
               boolean var8 = (((Byte)var3.getValue()).byteValue() & 2) == 2;
               var3.setValue(Boolean.valueOf(var8));
            }
         }

         if(var2.isOrHasParent(Dl.ABSTRACT_SKELETON)) {
            int var14 = var3.getId();
            if(var14 == 12) {
               var4.remove(var3);
            }

            if(var14 == 13) {
               var3.setId(12);
            }
         }

         if(var2.isOrHasParent(Dl.ZOMBIE)) {
            if(var2.is(new EntityType[]{Dl.ZOMBIE, Dl.HUSK}) && var3.getId() == 14) {
               var4.remove(var3);
            }

            if(var3.getId() == 15) {
               var3.setId(14);
            }

            if(var3.getId() == 14) {
               var3.setId(15);
            }
         }

         if(var2.isOrHasParent(Dl.ABSTRACT_HORSE)) {
            int var15 = var3.getId();
            if(var15 == 14) {
               var4.remove(var3);
            }

            if(var15 == 16) {
               var3.setId(14);
            }

            if(var15 == 17) {
               var3.setId(16);
            }

            if(!var2.is((EntityType)Dl.HORSE) && (var3.getId() == 15 || var3.getId() == 16)) {
               var4.remove(var3);
            }

            if(var2.is(new EntityType[]{Dl.DONKEY, Dl.MULE}) && var3.getId() == 13) {
               if((((Byte)var3.getValue()).byteValue() & 8) == 8) {
                  var4.add(new Metadata(15, aR5.Boolean, Boolean.valueOf(true)));
               }

               var4.add(new Metadata(15, aR5.Boolean, Boolean.valueOf(false)));
            }
         }

         if(var2.is((EntityType)Dl.ARMOR_STAND) && Via.getConfig().isHologramPatch()) {
            Metadata var16 = this.getMetaByIndex(11, var4);
            Metadata var17 = this.getMetaByIndex(2, var4);
            Metadata var9 = this.getMetaByIndex(3, var4);
            if(var3.getId() == 0 && var16 != null && var17 != null && var9 != null) {
               byte var10 = ((Byte)var3.getValue()).byteValue();
               if((var10 & 32) == 32 && (((Byte)var16.getValue()).byteValue() & 1) == 1 && !((String)var17.getValue()).isEmpty() && ((Boolean)var9.getValue()).booleanValue()) {
                  cw var11 = (cw)var5.b(cw.class);
                  if(!var11.a(var1)) {
                     var11.e(var1);

                     try {
                        PacketWrapper var12 = new PacketWrapper(37, (ByteBuf)null, var5);
                        var12.write(Type.VAR_INT, Integer.valueOf(var1));
                        var12.write(Type.SHORT, Short.valueOf((short)0));
                        var12.write(Type.SHORT, Short.valueOf((short)((int)(128.0D * -Via.getConfig().getHologramYOffset() * 32.0D))));
                        var12.write(Type.SHORT, Short.valueOf((short)0));
                        var12.write(Type.BOOLEAN, Boolean.valueOf(true));
                        var12.send(Protocol1_11To1_10.class);
                     } catch (Exception var13) {
                        var13.printStackTrace();
                     }
                  }
               }
            }
         }

      }
   }

   protected EntityType getTypeFromId(int var1) {
      return alB.a(var1, false);
   }

   protected EntityType getObjectTypeFromId(int var1) {
      return alB.a(var1, true);
   }

   public static Dl a(int param0, List param1) {
      // $FF: Couldn't be decompiled
   }

   public static Optional a(List var0, int var1) {
      b();
      Iterator var3 = var0.iterator();
      if(var3.hasNext()) {
         Metadata var4 = (Metadata)var3.next();
         if(var4.getId() == var1) {
            return Optional.of(var4);
         }
      }

      return Optional.empty();
   }

   public static void a(boolean var0) {
      e = var0;
   }

   public static boolean a() {
      return e;
   }

   public static boolean b() {
      boolean var0 = a();
      return true;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      a(false);
   }
}

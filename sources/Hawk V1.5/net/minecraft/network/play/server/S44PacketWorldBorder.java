package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.border.WorldBorder;

public class S44PacketWorldBorder implements Packet {
   private double field_179789_f;
   private int field_179797_i;
   private S44PacketWorldBorder.Action field_179795_a;
   private int field_179796_h;
   private double field_179794_c;
   private double field_179791_d;
   private double field_179792_e;
   private long field_179790_g;
   private int field_179793_b;
   private static final String __OBFID = "CL_00002292";

   public void func_179787_a(INetHandlerPlayClient var1) {
      var1.func_175093_a(this);
   }

   public void processPacket(INetHandler var1) {
      this.func_179787_a((INetHandlerPlayClient)var1);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179795_a = (S44PacketWorldBorder.Action)var1.readEnumValue(S44PacketWorldBorder.Action.class);
      switch(this.field_179795_a) {
      case SET_SIZE:
         this.field_179792_e = var1.readDouble();
         break;
      case LERP_SIZE:
         this.field_179789_f = var1.readDouble();
         this.field_179792_e = var1.readDouble();
         this.field_179790_g = var1.readVarLong();
         break;
      case SET_CENTER:
         this.field_179794_c = var1.readDouble();
         this.field_179791_d = var1.readDouble();
         break;
      case SET_WARNING_BLOCKS:
         this.field_179797_i = var1.readVarIntFromBuffer();
         break;
      case SET_WARNING_TIME:
         this.field_179796_h = var1.readVarIntFromBuffer();
         break;
      case INITIALIZE:
         this.field_179794_c = var1.readDouble();
         this.field_179791_d = var1.readDouble();
         this.field_179789_f = var1.readDouble();
         this.field_179792_e = var1.readDouble();
         this.field_179790_g = var1.readVarLong();
         this.field_179793_b = var1.readVarIntFromBuffer();
         this.field_179797_i = var1.readVarIntFromBuffer();
         this.field_179796_h = var1.readVarIntFromBuffer();
      }

   }

   public S44PacketWorldBorder(WorldBorder var1, S44PacketWorldBorder.Action var2) {
      this.field_179795_a = var2;
      this.field_179794_c = var1.getCenterX();
      this.field_179791_d = var1.getCenterZ();
      this.field_179789_f = var1.getDiameter();
      this.field_179792_e = var1.getTargetSize();
      this.field_179790_g = var1.getTimeUntilTarget();
      this.field_179793_b = var1.getSize();
      this.field_179797_i = var1.getWarningDistance();
      this.field_179796_h = var1.getWarningTime();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.field_179795_a);
      switch(this.field_179795_a) {
      case SET_SIZE:
         var1.writeDouble(this.field_179792_e);
         break;
      case LERP_SIZE:
         var1.writeDouble(this.field_179789_f);
         var1.writeDouble(this.field_179792_e);
         var1.writeVarLong(this.field_179790_g);
         break;
      case SET_CENTER:
         var1.writeDouble(this.field_179794_c);
         var1.writeDouble(this.field_179791_d);
         break;
      case SET_WARNING_BLOCKS:
         var1.writeVarIntToBuffer(this.field_179797_i);
         break;
      case SET_WARNING_TIME:
         var1.writeVarIntToBuffer(this.field_179796_h);
         break;
      case INITIALIZE:
         var1.writeDouble(this.field_179794_c);
         var1.writeDouble(this.field_179791_d);
         var1.writeDouble(this.field_179789_f);
         var1.writeDouble(this.field_179792_e);
         var1.writeVarLong(this.field_179790_g);
         var1.writeVarIntToBuffer(this.field_179793_b);
         var1.writeVarIntToBuffer(this.field_179797_i);
         var1.writeVarIntToBuffer(this.field_179796_h);
      }

   }

   public void func_179788_a(WorldBorder var1) {
      switch(this.field_179795_a) {
      case SET_SIZE:
         var1.setTransition(this.field_179792_e);
         break;
      case LERP_SIZE:
         var1.setTransition(this.field_179789_f, this.field_179792_e, this.field_179790_g);
         break;
      case SET_CENTER:
         var1.setCenter(this.field_179794_c, this.field_179791_d);
         break;
      case SET_WARNING_BLOCKS:
         var1.setWarningDistance(this.field_179797_i);
         break;
      case SET_WARNING_TIME:
         var1.setWarningTime(this.field_179796_h);
         break;
      case INITIALIZE:
         var1.setCenter(this.field_179794_c, this.field_179791_d);
         if (this.field_179790_g > 0L) {
            var1.setTransition(this.field_179789_f, this.field_179792_e, this.field_179790_g);
         } else {
            var1.setTransition(this.field_179792_e);
         }

         var1.setSize(this.field_179793_b);
         var1.setWarningDistance(this.field_179797_i);
         var1.setWarningTime(this.field_179796_h);
      }

   }

   public S44PacketWorldBorder() {
   }

   public static enum Action {
      private static final S44PacketWorldBorder.Action[] ENUM$VALUES = new S44PacketWorldBorder.Action[]{SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS};
      private static final S44PacketWorldBorder.Action[] $VALUES = new S44PacketWorldBorder.Action[]{SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS};
      SET_WARNING_BLOCKS("SET_WARNING_BLOCKS", 5),
      SET_WARNING_TIME("SET_WARNING_TIME", 4);

      private static final String __OBFID = "CL_00002290";
      LERP_SIZE("LERP_SIZE", 1),
      SET_SIZE("SET_SIZE", 0),
      INITIALIZE("INITIALIZE", 3),
      SET_CENTER("SET_CENTER", 2);

      private Action(String var3, int var4) {
      }
   }

   static final class SwitchAction {
      private static final String __OBFID = "CL_00002291";
      static final int[] field_179947_a = new int[S44PacketWorldBorder.Action.values().length];

      static {
         try {
            field_179947_a[S44PacketWorldBorder.Action.SET_SIZE.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_179947_a[S44PacketWorldBorder.Action.LERP_SIZE.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_179947_a[S44PacketWorldBorder.Action.SET_CENTER.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_179947_a[S44PacketWorldBorder.Action.SET_WARNING_BLOCKS.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_179947_a[S44PacketWorldBorder.Action.SET_WARNING_TIME.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_179947_a[S44PacketWorldBorder.Action.INITIALIZE.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}

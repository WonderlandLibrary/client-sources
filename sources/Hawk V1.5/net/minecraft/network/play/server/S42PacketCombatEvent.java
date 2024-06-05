package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.CombatTracker;

public class S42PacketCombatEvent implements Packet {
   public S42PacketCombatEvent.Event field_179776_a;
   public String field_179773_e;
   private static final String __OBFID = "CL_00002299";
   public int field_179775_c;
   public int field_179772_d;
   public int field_179774_b;

   public void processPacket(INetHandler var1) {
      this.func_179771_a((INetHandlerPlayClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.field_179776_a);
      if (this.field_179776_a == S42PacketCombatEvent.Event.END_COMBAT) {
         var1.writeVarIntToBuffer(this.field_179772_d);
         var1.writeInt(this.field_179775_c);
      } else if (this.field_179776_a == S42PacketCombatEvent.Event.ENTITY_DIED) {
         var1.writeVarIntToBuffer(this.field_179774_b);
         var1.writeInt(this.field_179775_c);
         var1.writeString(this.field_179773_e);
      }

   }

   public void func_179771_a(INetHandlerPlayClient var1) {
      var1.func_175098_a(this);
   }

   public S42PacketCombatEvent() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179776_a = (S42PacketCombatEvent.Event)var1.readEnumValue(S42PacketCombatEvent.Event.class);
      if (this.field_179776_a == S42PacketCombatEvent.Event.END_COMBAT) {
         this.field_179772_d = var1.readVarIntFromBuffer();
         this.field_179775_c = var1.readInt();
      } else if (this.field_179776_a == S42PacketCombatEvent.Event.ENTITY_DIED) {
         this.field_179774_b = var1.readVarIntFromBuffer();
         this.field_179775_c = var1.readInt();
         this.field_179773_e = var1.readStringFromBuffer(32767);
      }

   }

   public S42PacketCombatEvent(CombatTracker var1, S42PacketCombatEvent.Event var2) {
      this.field_179776_a = var2;
      EntityLivingBase var3 = var1.func_94550_c();
      switch(var2) {
      case END_COMBAT:
         this.field_179772_d = var1.func_180134_f();
         this.field_179775_c = var3 == null ? -1 : var3.getEntityId();
         break;
      case ENTITY_DIED:
         this.field_179774_b = var1.func_180135_h().getEntityId();
         this.field_179775_c = var3 == null ? -1 : var3.getEntityId();
         this.field_179773_e = var1.func_151521_b().getUnformattedText();
      }

   }

   public static enum Event {
      END_COMBAT("END_COMBAT", 1);

      private static final String __OBFID = "CL_00002297";
      ENTER_COMBAT("ENTER_COMBAT", 0);

      private static final S42PacketCombatEvent.Event[] $VALUES = new S42PacketCombatEvent.Event[]{ENTER_COMBAT, END_COMBAT, ENTITY_DIED};
      private static final S42PacketCombatEvent.Event[] ENUM$VALUES = new S42PacketCombatEvent.Event[]{ENTER_COMBAT, END_COMBAT, ENTITY_DIED};
      ENTITY_DIED("ENTITY_DIED", 2);

      private Event(String var3, int var4) {
      }
   }

   static final class SwitchEvent {
      private static final String __OBFID = "CL_00002298";
      static final int[] field_179944_a = new int[S42PacketCombatEvent.Event.values().length];

      static {
         try {
            field_179944_a[S42PacketCombatEvent.Event.END_COMBAT.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_179944_a[S42PacketCombatEvent.Event.ENTITY_DIED.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}

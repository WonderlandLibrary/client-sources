package net.minecraft.client.stream;

import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.client.stream.IngestServerTester$3;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.RTMPState;
import tv.twitch.broadcast.StatType;

class IngestServerTester$2 implements IStatCallbacks {
   final IngestServerTester this$0;

   IngestServerTester$2(IngestServerTester var1) {
      this.this$0 = var1;
   }

   public void statCallback(StatType var1, long var2) {
      switch(IngestServerTester$3.$SwitchMap$tv$twitch$broadcast$StatType[var1.ordinal()]) {
      case 1:
         this.this$0.field_153051_i = RTMPState.lookupValue((int)var2);
         break;
      case 2:
         this.this$0.field_153050_h = var2;
      }

   }
}

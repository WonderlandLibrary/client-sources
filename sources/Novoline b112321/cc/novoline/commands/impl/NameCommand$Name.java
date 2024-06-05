package cc.novoline.commands.impl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.a_E;

class NameCommand$Name {
   @Expose
   @SerializedName("name")
   private final String name;
   @Expose
   @SerializedName("changedToAt")
   private final long timestamp;

   public NameCommand$Name(String var1, long var2) {
      this.name = var1;
      this.timestamp = var2;
   }

   public boolean isFirst() {
      int[] var1 = a_E.b();
      return this.timestamp == 0L;
   }

   public String getName() {
      return this.name;
   }

   public long getTimestamp() {
      return this.timestamp;
   }
}

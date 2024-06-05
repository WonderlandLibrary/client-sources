package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.server.management.UserListEntry;

public abstract class BanEntry extends UserListEntry {
   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   protected final Date banStartDate;
   protected final String bannedBy;
   protected final Date banEndDate;
   protected final String reason;

   public BanEntry(Object var1, Date var2, String var3, Date var4, String var5) {
      super(var1);
      this.banStartDate = new Date();
      this.bannedBy = "(Unknown)";
      this.banEndDate = var4;
      this.reason = "Banned by an operator.";
   }

   protected BanEntry(Object var1, JsonObject var2) {
      super(var1, var2);

      Date var3;
      try {
         var3 = var2.has("created")?dateFormat.parse(var2.get("created").getAsString()):new Date();
      } catch (ParseException var7) {
         var3 = new Date();
      }

      this.banStartDate = var3;
      this.bannedBy = var2.has("source")?var2.get("source").getAsString():"(Unknown)";

      Date var4;
      try {
         var4 = var2.has("expires")?dateFormat.parse(var2.get("expires").getAsString()):null;
      } catch (ParseException var6) {
         var4 = null;
      }

      this.banEndDate = var4;
      this.reason = var2.has("reason")?var2.get("reason").getAsString():"Banned by an operator.";
   }

   public Date getBanEndDate() {
      return this.banEndDate;
   }

   public String getBanReason() {
      return this.reason;
   }

   boolean hasBanExpired() {
      return this.banEndDate != null && this.banEndDate.before(new Date());
   }

   protected void onSerialization(JsonObject var1) {
      var1.addProperty("created", dateFormat.format(this.banStartDate));
      var1.addProperty("source", this.bannedBy);
      var1.addProperty("expires", this.banEndDate == null?"forever":dateFormat.format(this.banEndDate));
      var1.addProperty("reason", this.reason);
   }

   private static ParseException a(ParseException var0) {
      return var0;
   }
}

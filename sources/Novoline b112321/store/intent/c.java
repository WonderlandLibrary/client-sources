package store.intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.acE;

public class c {
   @Expose
   @SerializedName("username")
   public String f;
   @Expose
   @SerializedName("email")
   public String i;
   @Expose
   @SerializedName("intent_uid")
   public int j;
   @Expose
   @SerializedName("client_uid")
   public int g;
   @Expose
   @SerializedName("discord_tag")
   public String h;
   @Expose
   @SerializedName("discord_id")
   public String d;
   @Expose
   @SerializedName("twoFactor")
   public boolean k;
   @Expose
   @SerializedName("api_key")
   public String c;
   @Expose
   @SerializedName("loggedIn")
   public boolean e;
   private static acE[] b;

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[2]);
      }

   }
}

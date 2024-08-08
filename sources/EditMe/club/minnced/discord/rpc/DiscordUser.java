package club.minnced.discord.rpc;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordUser extends Structure {
   private static final List FIELD_ORDER = Collections.unmodifiableList(Arrays.asList("userId", "username", "discriminator", "avatar"));
   public String userId;
   public String username;
   public String discriminator;
   public String avatar;

   public DiscordUser(String var1) {
      this.setStringEncoding(var1);
   }

   public DiscordUser() {
      this("UTF-8");
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof DiscordUser)) {
         return false;
      } else {
         DiscordUser var2 = (DiscordUser)var1;
         return Objects.equals(this.userId, var2.userId) && Objects.equals(this.username, var2.username) && Objects.equals(this.discriminator, var2.discriminator) && Objects.equals(this.avatar, var2.avatar);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.userId, this.username, this.discriminator, this.avatar});
   }

   protected List getFieldOrder() {
      return FIELD_ORDER;
   }
}

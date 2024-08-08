package club.minnced.discord.rpc;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordRichPresence extends Structure {
   private static final List FIELD_ORDER = Collections.unmodifiableList(Arrays.asList("state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "matchSecret", "joinSecret", "spectateSecret", "instance"));
   public String state;
   public String details;
   public long startTimestamp;
   public long endTimestamp;
   public String largeImageKey;
   public String largeImageText;
   public String smallImageKey;
   public String smallImageText;
   public String partyId;
   public int partySize;
   public int partyMax;
   public String matchSecret;
   public String joinSecret;
   public String spectateSecret;
   public byte instance;

   public DiscordRichPresence(String var1) {
      this.setStringEncoding(var1);
   }

   public DiscordRichPresence() {
      this("UTF-8");
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof DiscordRichPresence)) {
         return false;
      } else {
         DiscordRichPresence var2 = (DiscordRichPresence)var1;
         return this.startTimestamp == var2.startTimestamp && this.endTimestamp == var2.endTimestamp && this.partySize == var2.partySize && this.partyMax == var2.partyMax && this.instance == var2.instance && Objects.equals(this.state, var2.state) && Objects.equals(this.details, var2.details) && Objects.equals(this.largeImageKey, var2.largeImageKey) && Objects.equals(this.largeImageText, var2.largeImageText) && Objects.equals(this.smallImageKey, var2.smallImageKey) && Objects.equals(this.smallImageText, var2.smallImageText) && Objects.equals(this.partyId, var2.partyId) && Objects.equals(this.matchSecret, var2.matchSecret) && Objects.equals(this.joinSecret, var2.joinSecret) && Objects.equals(this.spectateSecret, var2.spectateSecret);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.state, this.details, this.startTimestamp, this.endTimestamp, this.largeImageKey, this.largeImageText, this.smallImageKey, this.smallImageText, this.partyId, this.partySize, this.partyMax, this.matchSecret, this.joinSecret, this.spectateSecret, this.instance});
   }

   protected List getFieldOrder() {
      return FIELD_ORDER;
   }
}

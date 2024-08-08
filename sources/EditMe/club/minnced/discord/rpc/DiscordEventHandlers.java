package club.minnced.discord.rpc;

import com.sun.jna.Callback;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordEventHandlers extends Structure {
   private static final List FIELD_ORDER = Collections.unmodifiableList(Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest"));
   public DiscordEventHandlers.OnReady ready;
   public DiscordEventHandlers.OnStatus disconnected;
   public DiscordEventHandlers.OnStatus errored;
   public DiscordEventHandlers.OnGameUpdate joinGame;
   public DiscordEventHandlers.OnGameUpdate spectateGame;
   public DiscordEventHandlers.OnJoinRequest joinRequest;

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof DiscordEventHandlers)) {
         return false;
      } else {
         DiscordEventHandlers var2 = (DiscordEventHandlers)var1;
         return Objects.equals(this.ready, var2.ready) && Objects.equals(this.disconnected, var2.disconnected) && Objects.equals(this.errored, var2.errored) && Objects.equals(this.joinGame, var2.joinGame) && Objects.equals(this.spectateGame, var2.spectateGame) && Objects.equals(this.joinRequest, var2.joinRequest);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.ready, this.disconnected, this.errored, this.joinGame, this.spectateGame, this.joinRequest});
   }

   protected List getFieldOrder() {
      return FIELD_ORDER;
   }

   public interface OnJoinRequest extends Callback {
      void accept(DiscordUser var1);
   }

   public interface OnGameUpdate extends Callback {
      void accept(String var1);
   }

   public interface OnStatus extends Callback {
      void accept(int var1, String var2);
   }

   public interface OnReady extends Callback {
      void accept(DiscordUser var1);
   }
}

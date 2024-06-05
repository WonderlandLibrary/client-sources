package cc.novoline.gui.screen.alt.repository.hypixel;

public class TClientResponse {
   private static final TClientResponse EMPTY = new TClientResponse(false, (Object)null);
   private final boolean ok;
   private final Object content;

   private TClientResponse(boolean var1, Object var2) {
      this.ok = var1;
      this.content = var2;
   }

   public static TClientResponse of(boolean var0, Object var1) {
      return new TClientResponse(var0, var1);
   }

   public static TClientResponse empty() {
      return EMPTY;
   }

   public boolean isOkay() {
      return this.ok;
   }

   public Object getContent() {
      return this.content;
   }

   public String toString() {
      return "Response{ok=" + this.ok + ", content=" + this.content + '}';
   }
}

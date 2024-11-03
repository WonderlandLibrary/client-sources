package vestige.anticheat;

import com.mojang.realmsclient.gui.ChatFormatting;
import vestige.Flap;
import vestige.module.impl.misc.AnticheatModule;
import vestige.util.IMinecraft;
import vestige.util.misc.LogUtil;

public abstract class AnticheatCheck implements IMinecraft {
   private final String name;
   protected final ACPlayer player;
   private double buffer;
   private static AnticheatModule module;

   public AnticheatCheck(String name, ACPlayer player) {
      this.name = name;
      this.player = player;
   }

   public abstract void check();

   public final double increaseBuffer() {
      return ++this.buffer;
   }

   public final double increaseBufferBy(double amount) {
      return this.buffer += Math.max(0.0D, amount);
   }

   public final double decreaseBuffer() {
      return this.buffer = Math.max(this.buffer - 1.0D, 0.0D);
   }

   public final double decreaseBufferBy(double amount) {
      return this.buffer = Math.max(this.buffer - Math.max(0.0D, amount), 0.0D);
   }

   public final void resetBuffer() {
      this.buffer = 0.0D;
   }

   public final void alert() {
      if (module.isEnabled()) {
         LogUtil.addChatMessage(this.player.getEntity().getGameProfile().getName() + ChatFormatting.WHITE + " has failed " + this.name);
      }

   }

   public final void alert(String message) {
      if (module.isEnabled()) {
         LogUtil.addChatMessage(this.player.getEntity().getGameProfile().getName() + ChatFormatting.WHITE + " has failed " + this.name + ChatFormatting.GRAY + " | " + message);
      }

   }

   public double round(double value) {
      return (double)Math.round(value * 1000.0D) / 1000.0D;
   }

   public String getName() {
      return this.name;
   }

   public ACPlayer getPlayer() {
      return this.player;
   }

   public double getBuffer() {
      return this.buffer;
   }

   static {
      module = (AnticheatModule)Flap.instance.getModuleManager().getModule(AnticheatModule.class);
   }
}

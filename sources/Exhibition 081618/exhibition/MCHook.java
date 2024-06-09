package exhibition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.renderer.texture.TextureManager;

public class MCHook extends Minecraft {
	
   public MCHook(GameConfiguration gc) {
      super(gc);
   }

   protected void func_180510_a(TextureManager texMan) {
      try {
         (new Client()).setup();
      } catch (RuntimeException var3) {
         var3.printStackTrace();
      }

      super.func_180510_a(texMan);
   }
}

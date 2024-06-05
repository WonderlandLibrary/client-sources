package cc.novoline.modules.visual;

import cc.novoline.modules.visual.DMGParticle$Location;

public class DMGParticle$Particles {
   public int ticks;
   public DMGParticle$Location location;
   public String text;

   public DMGParticle$Particles(DMGParticle$Location var1, String var2) {
      this.location = var1;
      this.text = var2;
      this.ticks = 0;
   }
}

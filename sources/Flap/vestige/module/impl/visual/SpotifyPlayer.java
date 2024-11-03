package vestige.module.impl.visual;

import java.awt.Color;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.module.AlignType;
import vestige.module.Category;
import vestige.module.HUDModule;
import vestige.util.render.RenderUtils2;

public class SpotifyPlayer extends HUDModule {
   private VestigeFontRenderer productSans;
   private ClientTheme theme;
   private boolean initialised;

   public SpotifyPlayer() {
      super("SpotifyPlayer (BETA)", Category.VISUAL, 100.0D, 4.0D, 100, 100, AlignType.LEFT);
      this.setEnabledSilently(true);
   }

   private void initialise() {
      this.productSans = Flap.instance.getFontManager().getProductSans();
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
   }

   protected void renderModule(boolean inChat) {
      if (!this.initialised) {
         this.initialise();
         this.initialised = true;
      }

      if (!mc.gameSettings.showDebugInfo) {
         this.renderNew();
      }
   }

   private void renderNew() {
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      RenderUtils2.drawRect((double)x, (double)y, (double)(x + 2.0F + 120.0F), (double)(y + 30.5F), 1610612736);
      String songname = "Song name";
      this.getStringWidth(songname);
      this.productSans.drawStringWithShadow(songname, x + 5.0F, y + 10.25F, (new Color(200, 200, 200)).getRGB());
      RenderUtils2.drawRect((double)x, (double)(y + 25.0F), (double)(x + 2.0F + 120.0F), (double)(y + 30.5F), (new Color(40, 200, 40)).getRGB());
   }

   private double getStringWidth(String s) {
      return (double)mc.fontRendererObj.getStringWidth(s);
   }
}

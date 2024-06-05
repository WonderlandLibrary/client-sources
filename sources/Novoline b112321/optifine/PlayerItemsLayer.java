package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.aE2;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import optifine.Config;
import optifine.MatchBlock;

public class PlayerItemsLayer implements LayerRenderer {
   private RenderPlayer renderPlayer = null;

   public PlayerItemsLayer(RenderPlayer var1) {
      this.renderPlayer = var1;
   }

   public void doRenderLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.renderEquippedItems(var1, var8, var4);
   }

   protected void renderEquippedItems(EntityLivingBase var1, float var2, float var3) {
      PacketRemapper[] var4 = MatchBlock.b();
      if(Config.isShowCapes() && var1 instanceof AbstractClientPlayer) {
         AbstractClientPlayer var5 = (AbstractClientPlayer)var1;
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableRescaleNormal();
         GlStateManager.enableCull();
         ModelPlayer var6 = this.renderPlayer.getMainModel();
         aE2.a(var6, var5, var2, var3);
         GlStateManager.disableCull();
      }

   }

   public boolean shouldCombineTextures() {
      return false;
   }

   public static void register(Map var0) {
      Set var2 = var0.keySet();
      boolean var3 = false;
      MatchBlock.b();
      Iterator var4 = var2.iterator();
      if(var4.hasNext()) {
         Object var5 = var4.next();
         Object var6 = var0.get(var5);
         if(var6 instanceof RenderPlayer) {
            RenderPlayer var7 = (RenderPlayer)var6;
            var7.addLayer(new PlayerItemsLayer(var7));
            var3 = true;
         }
      }

      if(!var3) {
         Config.warn("PlayerItemsLayer not registered");
      }

   }
}

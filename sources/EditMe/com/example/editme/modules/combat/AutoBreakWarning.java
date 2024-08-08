package com.example.editme.modules.combat;

import com.example.editme.events.PacketEvent;
import com.example.editme.gui.font.CFontRenderer;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.awt.Font;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "AutoBreakWarning",
   category = Module.Category.COMBAT
)
public class AutoBreakWarning extends Module {
   private int delay;
   private Setting minRange = this.register(SettingsManager.doubleBuilder("Min Range").withMinimum(0.0D).withValue((Number)1.5D).withMaximum(10.0D).build());
   private String playerName;
   public static final CFontRenderer fontRenderer = new CFontRenderer(new Font("Arial", 1, 18), true, true);
   @EventHandler
   private Listener receiveListener = new Listener(this::lambda$new$0, new Predicate[0]);
   private Boolean warn = false;
   private Setting obsidianOnly = this.register(SettingsManager.b("Check Only Obby", true));

   public void onRender() {
      if (this.warn) {
         if (this.delay++ > 100) {
            this.warn = false;
         }

         String var1 = String.valueOf((new StringBuilder()).append(this.playerName).append(" IS FUCKING YOUR SURROUND!!!"));
         fontRenderer.drawStringWithShadow(var1, (double)(mc.field_71443_c / 2 - fontRenderer.getStringWidth(var1) / 2), (double)(mc.field_71440_d / 2 - 16), (new Color(255, 0, 0)).getRGB());
      }
   }

   private void lambda$new$0(PacketEvent.Receive var1) {
      if (var1.getPacket() instanceof SPacketBlockBreakAnim) {
         SPacketBlockBreakAnim var2 = (SPacketBlockBreakAnim)var1.getPacket();
         int var3 = var2.func_148846_g();
         int var4 = var2.func_148845_c();
         BlockPos var5 = var2.func_179821_b();
         Block var6 = mc.field_71441_e.func_180495_p(var5).func_177230_c();
         EntityPlayer var7 = (EntityPlayer)mc.field_71441_e.func_73045_a(var4);
         if (var7 == null) {
            return;
         }

         if ((Boolean)this.obsidianOnly.getValue() && !var6.equals(Blocks.field_150343_Z)) {
            return;
         }

         if (var7.field_184831_bT.func_190926_b() || !(var7.field_184831_bT.func_77973_b() instanceof ItemPickaxe)) {
            return;
         }

         if (this.pastDistance(mc.field_71439_g, var5, (Double)this.minRange.getValue())) {
            this.playerName = var7.func_70005_c_();
            this.warn = true;
            this.delay = 0;
            if (var3 == 255) {
               this.warn = false;
            }
         }
      }

   }

   private boolean pastDistance(EntityPlayer var1, BlockPos var2, double var3) {
      return var1.func_174831_c(var2) <= Math.pow(var3, 2.0D);
   }
}

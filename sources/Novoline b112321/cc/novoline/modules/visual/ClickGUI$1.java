package cc.novoline.modules.visual;

import cc.novoline.modules.player.BedBreaker;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.notifications.NotificationType;
import cc.novoline.utils.tasks.FutureTask;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBed$EnumPartType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

class ClickGUI$1 extends FutureTask {
   final ClickGUI this$0;

   ClickGUI$1(ClickGUI var1, int var2) {
      super(var2);
      this.this$0 = var1;
   }

   public void execute() {
      HUD.e();
      int var2 = -20;
      if(var2 < 21) {
         int var3 = -20;
         if(var3 < 21) {
            int var4 = -10;
            if(var4 < 12) {
               BlockPos var5 = new BlockPos(ClickGUI.access$000(this.this$0).player.posX - (double)var2, ClickGUI.access$100(this.this$0).player.posY + (double)var4, ClickGUI.access$200(this.this$0).player.posZ - (double)var3);
               Block var6 = ClickGUI.access$300(this.this$0).world.getBlockState(var5).getBlock();
               if(ClickGUI.access$400(this.this$0).world.getBlockState(var5).getBlock() == Blocks.bed && ClickGUI.access$500(this.this$0).world.getBlockState(var5).getValue(BlockBed.PART) == BlockBed$EnumPartType.HEAD) {
                  ((BedBreaker)this.this$0.getModule(BedBreaker.class)).setWhiteListed(var5);
                  ClickGUI.access$600(this.this$0).getNotificationManager().pop("Whitelisted your own bed!", "Whitelisted bed at " + var5.toString(), 3000, NotificationType.INFO);
               }

               ++var4;
            }

            ++var3;
         }

         ++var2;
      }

   }

   public void run() {
   }
}

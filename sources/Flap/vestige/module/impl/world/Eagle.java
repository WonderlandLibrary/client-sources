package vestige.module.impl.world;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.BlockPlaceEvent;
import vestige.event.impl.RenderEvent;
import vestige.font.VestigeFontRenderer;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.util.player.MovementUtil;
import vestige.util.util.Utils;

public class Eagle extends Module {
   private final DoubleSetting minDelay = new DoubleSetting("Min delay", 100.0D, 0.0D, 500.0D, 1.0D);
   private final DoubleSetting maxDelay = new DoubleSetting("Max delay", 200.0D, 0.0D, 500.0D, 1.0D);
   private final DoubleSetting straightEveryBlock = new DoubleSetting("Straight every block", 1.0D, 1.0D, 8.0D, 1.0D);
   private final DoubleSetting diagonalEveryBlock = new DoubleSetting("Diagonal every block", 1.0D, 1.0D, 8.0D, 1.0D);
   private final BooleanSetting pitchCheck = new BooleanSetting("Pitch check", true);
   private final DoubleSetting pitch = new DoubleSetting("Pitch", () -> {
      return this.pitchCheck.isEnabled();
   }, 45.0D, 0.0D, 90.0D, 5.0D);
   private final BooleanSetting onlySPressed = new BooleanSetting("Only S pressed", false);
   private final BooleanSetting onlySneak = new BooleanSetting("Only sneak", false);
   private final BooleanSetting showBlockCount = new BooleanSetting("Show block count", false);
   private long lastSneakTime = -1L;
   private int blockPlaced = 0;
   private VestigeFontRenderer productSansBold;

   public Eagle() {
      super("Eagle", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.minDelay, this.maxDelay, this.straightEveryBlock, this.diagonalEveryBlock, this.pitchCheck, this.pitch, this.onlySPressed, this.onlySneak, this.showBlockCount});
   }

   public boolean onDisable() {
      this.lastSneakTime = -1L;
      this.blockPlaced = 0;
      this.setSneak(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
      return false;
   }

   @Listener
   public void onPlaceBlock(BlockPlaceEvent event) {
      ++this.blockPlaced;
   }

   @Listener
   public final void onRender(RenderEvent event) {
      ScaledResolution scaledRes = new ScaledResolution(mc);
      int screenWidth = scaledRes.getScaledWidth();
      int screenHeight = scaledRes.getScaledHeight();
      this.productSansBold = Flap.instance.getFontManager().getProductSansBold20();
      int totalBlocksCount = Integer.parseInt(this.totalBlocks());
      mc.thePlayer.getHeldItem();
      if (this.showBlockCount.isEnabled()) {
         int color;
         if (totalBlocksCount < 16) {
            color = 16711680;
         } else if (totalBlocksCount < 30) {
            color = 16776960;
         } else {
            color = 65280;
         }

         this.productSansBold.drawStringWithShadow(this.totalBlocks(), (double)(screenWidth / 2) - this.productSansBold.getStringWidth(this.totalBlocks()) / 2.0D, (double)screenHeight / 1.9D, color);
      }

      if (this.onlySPressed.isEnabled() && !mc.gameSettings.keyBindBack.isKeyDown() || this.pitchCheck.isEnabled() && (double)mc.thePlayer.rotationPitch < this.pitch.getValue() || this.onlySneak.isEnabled() && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
         this.setSneak(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
         this.blockPlaced = 0;
      } else {
         long currentTime = System.currentTimeMillis();
         if ((Utils.overAir() || Utils.onEdge()) && this.blockPlaced % (int)(MovementUtil.isGoingDiagonally() ? this.diagonalEveryBlock.getValue() : this.straightEveryBlock.getValue()) == 0) {
            this.setSneak(true);
            this.lastSneakTime = currentTime;
         } else if (this.lastSneakTime != -1L && (double)(currentTime - this.lastSneakTime) > Math.random() * (this.maxDelay.getValue() - this.minDelay.getValue()) + this.minDelay.getValue()) {
            this.setSneak(false);
            this.lastSneakTime = -1L;
         }

      }
   }

   public String totalBlocks() {
      int totalBlocks = 0;

      for(int i = 0; i < 9; ++i) {
         ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
         if (stack != null && stack.getItem() instanceof ItemBlock && Utils.canBePlaced((ItemBlock)stack.getItem()) && stack.stackSize > 0) {
            totalBlocks += stack.stackSize;
         }
      }

      return Integer.toString(totalBlocks);
   }

   private void setSneak(boolean sneak) {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sneak);
      mc.gameSettings.keyBindSneak.pressed = sneak;
   }
}

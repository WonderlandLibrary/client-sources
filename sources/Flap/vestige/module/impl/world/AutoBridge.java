package vestige.module.impl.world;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PacketSendEvent;
import vestige.event.impl.RenderEvent;
import vestige.event.impl.TickEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.player.FixedRotations;
import vestige.util.player.KeyboardUtil;
import vestige.util.player.PlayerUtil;
import vestige.util.world.BlockInfo;
import vestige.util.world.WorldUtil;

public class AutoBridge extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Sprint", new String[]{"Sprint", "No sprint", "Godbridge"});
   private final BooleanSetting keepY = new BooleanSetting("Keep Y", () -> {
      return this.mode.is("Sprint");
   }, true);
   private final BooleanSetting ninjaBridge = new BooleanSetting("Ninja bridge", () -> {
      return this.mode.is("No sprint");
   }, false);
   private final BooleanSetting eagle = new BooleanSetting("Eagle", () -> {
      return this.mode.is("No sprint");
   }, false);
   private final BooleanSetting alwaysRotateOffground = new BooleanSetting("Always rotate offground", () -> {
      return this.mode.is("Sprint");
   }, false);
   private final BooleanSetting freelook = new BooleanSetting("Freelook", true);
   private final ModeSetting blockPicker = new ModeSetting("Block picker", "Switch", new String[]{"None", "Switch", "Spoof"});
   private FixedRotations rotations;
   private float oldYaw;
   private float oldPitch;
   private boolean freelooking;
   private double lastGroundY;
   private boolean started;
   private int blocksPlaced;
   private int ticks;
   private int counter;
   private int oldSlot;

   public AutoBridge() {
      super("AutoBridge", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.mode, this.keepY, this.alwaysRotateOffground, this.ninjaBridge, this.eagle, this.freelook, this.blockPicker});
   }

   public void onEnable() {
      this.rotations = new FixedRotations(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
      this.oldYaw = mc.thePlayer.rotationYaw;
      this.oldPitch = mc.thePlayer.rotationPitch;
      if (this.freelook.isEnabled()) {
         Flap.instance.getCameraHandler().setFreelooking(true);
         this.freelooking = true;
      }

      this.lastGroundY = mc.thePlayer.onGround ? mc.thePlayer.posY : Math.floor(mc.thePlayer.posY);
      this.started = false;
      this.blocksPlaced = 0;
      this.counter = this.ticks = 0;
      if (this.mode.is("Godbridge")) {
         float yaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
         float roundedYaw = (float)((double)Math.round(yaw * 90.0F) / 90.0D);
         this.rotations.updateRotations(roundedYaw - 135.0F, 76.0F);
         mc.thePlayer.rotationYaw = this.rotations.getYaw();
         mc.thePlayer.rotationPitch = this.rotations.getPitch();
         this.blocksPlaced = 1;
      } else if (this.mode.is("No sprint")) {
         this.rotations.updateRotations(mc.thePlayer.rotationYaw - 180.0F, 76.0F);
         mc.thePlayer.rotationYaw = this.rotations.getYaw();
         mc.thePlayer.rotationPitch = this.rotations.getPitch();
         if (this.ninjaBridge.isEnabled()) {
            mc.gameSettings.keyBindForward.pressed = false;
            mc.gameSettings.keyBindBack.pressed = true;
         } else {
            this.invertKeyPresses();
         }
      }

      this.oldSlot = mc.thePlayer.inventory.currentItem;
      this.pickBlock();
   }

   public boolean onDisable() {
      KeyboardUtil.resetKeybindings(mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSneak);
      mc.gameSettings.keyBindUseItem.pressed = false;
      if (this.freelooking) {
         mc.thePlayer.rotationYaw = Flap.instance.getCameraHandler().getYaw();
         mc.thePlayer.rotationPitch = Flap.instance.getCameraHandler().getPitch();
         Flap.instance.getCameraHandler().setFreelooking(false);
         this.freelooking = false;
      }

      this.switchToOriginalSlot();
      return false;
   }

   private void switchToOriginalSlot() {
      if (!this.blockPicker.is("None")) {
         mc.thePlayer.inventory.currentItem = this.oldSlot;
      }

      Flap.instance.getSlotSpoofHandler().stopSpoofing();
   }

   private void pickBlock() {
      if (!this.blockPicker.is("None")) {
         for(int i = 8; i >= 0; --i) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBlock && !PlayerUtil.isBlockBlacklisted(stack.getItem()) && stack.stackSize > 0) {
               mc.thePlayer.inventory.currentItem = i;
               break;
            }
         }
      }

      if (this.blockPicker.is("Spoof")) {
         Flap.instance.getSlotSpoofHandler().startSpoofing(this.oldSlot);
      }

   }

   @Listener
   public void onTick(TickEvent event) {
      this.pickBlock();
      if (mc.thePlayer.onGround || !this.keepY.isEnabled()) {
         this.lastGroundY = mc.thePlayer.posY;
      }

      boolean isOverAir = WorldUtil.isAirOrLiquid(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ));
      String var3 = this.mode.getMode();
      byte var4 = -1;
      switch(var3.hashCode()) {
      case -2126575899:
         if (var3.equals("Godbridge")) {
            var4 = 0;
         }
         break;
      case -1629441735:
         if (var3.equals("No sprint")) {
            var4 = 1;
         }
      }

      switch(var4) {
      case 0:
         if (this.blocksPlaced >= 9) {
            mc.gameSettings.keyBindJump.pressed = true;
            this.blocksPlaced = 0;
         } else {
            mc.gameSettings.keyBindJump.pressed = false;
         }

         if (this.started) {
            mc.gameSettings.keyBindUseItem.pressed = true;
            mc.rightClickDelayTimer = 0;
            mc.gameSettings.keyBindSneak.pressed = false;
         } else {
            mc.gameSettings.keyBindSneak.pressed = true;
            mc.gameSettings.keyBindUseItem.pressed = false;
         }

         mc.gameSettings.keyBindBack.pressed = true;
         mc.gameSettings.keyBindRight.pressed = true;
         mc.gameSettings.keyBindForward.pressed = false;
         mc.gameSettings.keyBindLeft.pressed = false;
         if (Math.abs(mc.thePlayer.posX - mc.thePlayer.lastTickPosX) < 0.01D && Math.abs(mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) < 0.01D && WorldUtil.isAirOrLiquid(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ))) {
            this.started = true;
         }
         break;
      case 1:
         ++this.ticks;
         if (!this.ninjaBridge.isEnabled()) {
            this.invertKeyPresses();
         }

         if (!this.eagle.isEnabled() || !isOverAir || !mc.thePlayer.onGround || this.blocksPlaced != 0 && this.blocksPlaced % 3 == 0 && !mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            KeyboardUtil.resetKeybinding(mc.gameSettings.keyBindSneak);
         } else {
            mc.gameSettings.keyBindSneak.pressed = true;
         }
      }

   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1629441735:
         if (var2.equals("No sprint")) {
            var3 = 0;
         }
      default:
         switch(var3) {
         case 0:
            if (!this.ninjaBridge.isEnabled()) {
               this.invertKeyPresses();
            }
         default:
         }
      }
   }

   @Listener
   public void onRender(RenderEvent event) {
      BlockInfo info = WorldUtil.getBlockUnder(this.mode.is("Sprint") ? this.lastGroundY : mc.thePlayer.posY, 3);
      String var3 = this.mode.getMode();
      byte var4 = -1;
      switch(var3.hashCode()) {
      case -1811812806:
         if (var3.equals("Sprint")) {
            var4 = 0;
         }
         break;
      case -1629441735:
         if (var3.equals("No sprint")) {
            var4 = 1;
         }
      }

      float pitch;
      MovingObjectPosition result;
      switch(var4) {
      case 0:
         mc.gameSettings.keyBindUseItem.pressed = false;
         boolean jumping = mc.thePlayer.onGround && mc.gameSettings.keyBindJump.isKeyDown();
         if (WorldUtil.isAirOrLiquid(new BlockPos(mc.thePlayer.posX, this.lastGroundY - 1.0D, mc.thePlayer.posZ)) && info != null && info.getFacing() != EnumFacing.DOWN && (info.getFacing() != EnumFacing.UP || !this.keepY.isEnabled()) && !jumping) {
            float yaw = (this.freelooking ? Flap.instance.getCameraHandler().getYaw() : this.oldYaw) - 180.0F;

            for(pitch = 40.0F; pitch <= 90.0F; pitch += 0.1F) {
               this.rotations.updateRotations(yaw, pitch);
               result = WorldUtil.raytrace(this.rotations.getYaw(), this.rotations.getPitch());
               if (result != null && result.getBlockPos().equals(info.getPos()) && result.sideHit == info.getFacing()) {
                  mc.thePlayer.rotationYaw = this.rotations.getYaw();
                  mc.thePlayer.rotationPitch = this.rotations.getPitch();
                  mc.gameSettings.keyBindUseItem.pressed = true;
                  mc.rightClickDelayTimer = 0;
                  break;
               }
            }

            this.invertKeyPresses();
         } else if (!mc.thePlayer.onGround && this.alwaysRotateOffground.isEnabled()) {
            mc.thePlayer.rotationYaw = this.oldYaw - 180.0F;
            mc.thePlayer.rotationPitch = 77.0F;
            this.invertKeyPresses();
         } else {
            mc.thePlayer.rotationYaw = this.freelooking ? Flap.instance.getCameraHandler().getYaw() : this.oldYaw;
            mc.thePlayer.rotationPitch = this.oldPitch;
            KeyboardUtil.resetKeybindings(mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSneak);
         }
         break;
      case 1:
         if (this.freelooking) {
            mc.thePlayer.rotationYaw = Flap.instance.getCameraHandler().getYaw() - 180.0F;
         }

         mc.gameSettings.keyBindUseItem.pressed = false;
         boolean found;
         if (WorldUtil.isAirOrLiquid(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)) && info != null && info.getFacing() != EnumFacing.DOWN) {
            found = false;

            for(pitch = 0.0F; pitch <= 90.0F; pitch += pitch < 60.0F ? 0.2F : 0.1F) {
               this.rotations.updateRotations(mc.thePlayer.rotationYaw, pitch);
               result = WorldUtil.raytrace(this.rotations.getYaw(), this.rotations.getPitch());
               if (result != null && result.getBlockPos().equals(info.getPos()) && result.sideHit == info.getFacing()) {
                  if (result.sideHit == info.getFacing()) {
                     mc.thePlayer.rotationYaw = this.rotations.getYaw();
                     mc.thePlayer.rotationPitch = this.rotations.getPitch();
                     mc.gameSettings.keyBindUseItem.pressed = true;
                     mc.rightClickDelayTimer = 0;
                     mc.gameSettings.keyBindSneak.pressed = false;
                     found = true;
                  }
                  break;
               }
            }

            if (!found) {
               mc.gameSettings.keyBindSneak.pressed = true;
            }
         }

         if (this.ninjaBridge.isEnabled()) {
            mc.gameSettings.keyBindForward.pressed = false;
            mc.gameSettings.keyBindBack.pressed = true;
            found = mc.thePlayer.isPotionActive(Potion.moveSpeed);
            boolean left = found ? this.ticks % 2 == 0 : this.ticks % 5 == 0;
            boolean right = found ? this.ticks % 2 == 1 : this.ticks % 5 == 1;
            if (left) {
               mc.gameSettings.keyBindLeft.pressed = true;
               mc.gameSettings.keyBindRight.pressed = false;
            } else if (right) {
               mc.gameSettings.keyBindRight.pressed = true;
               mc.gameSettings.keyBindLeft.pressed = false;
            } else {
               mc.gameSettings.keyBindLeft.pressed = false;
               mc.gameSettings.keyBindRight.pressed = false;
            }
         } else {
            this.invertKeyPresses();
         }
      }

   }

   private void invertKeyPresses() {
      boolean forward = KeyboardUtil.isPressed(mc.gameSettings.keyBindForward);
      boolean backward = KeyboardUtil.isPressed(mc.gameSettings.keyBindBack);
      boolean left = KeyboardUtil.isPressed(mc.gameSettings.keyBindLeft);
      boolean right = KeyboardUtil.isPressed(mc.gameSettings.keyBindRight);
      mc.gameSettings.keyBindForward.pressed = backward;
      mc.gameSettings.keyBindBack.pressed = forward;
      mc.gameSettings.keyBindLeft.pressed = right;
      mc.gameSettings.keyBindRight.pressed = left;
   }

   @Listener
   public void onSend(PacketSendEvent event) {
      if (event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
         C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement)event.getPacket();
         if (!packet.getPosition().equals(new BlockPos(-1, -1, -1))) {
            ++this.blocksPlaced;
         } else if (this.mode.is("Godbridge") && mc.thePlayer.ticksExisted % 2 == 0) {
            event.setCancelled(true);
         }
      }

   }
}

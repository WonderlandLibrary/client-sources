package cc.slack.features.modules.impl.world;

import cc.slack.events.State;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.BlockUtils;
import cc.slack.utils.player.InventoryUtil;
import cc.slack.utils.player.ItemSpoofUtil;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;
import cc.slack.utils.player.RotationUtil;
import io.github.nevalackin.radbus.Listen;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@ModuleInfo(
   name = "Scaffold",
   category = Category.WORLD,
   key = 47
)
public class Scaffold extends Module {
   private final ModeValue<String> rotationMode = new ModeValue("Rotation Mode", new String[]{"Vanilla", "Hypixel"});
   private final NumberValue<Integer> keepRotationTicks = new NumberValue("Keep Rotation Length", 1, 0, 10, 1);
   private final ModeValue<String> raycastMode = new ModeValue("Placement Check", new String[]{"Normal", "Strict", "Off"});
   private final ModeValue<String> placeTiming = new ModeValue("Placement Timing", new String[]{"Legit", "Pre", "Post"});
   private final ModeValue<String> sprintMode = new ModeValue("Sprint Mode", new String[]{"Always", "No Packet", "Hypixel Safe", "Off"});
   private final NumberValue<Double> speedModifier = new NumberValue("Speed Modifier", 1.0D, 0.0D, 2.0D, 0.01D);
   private final ModeValue<String> safewalkMode = new ModeValue("Safewalk", new String[]{"Ground", "Always", "Sneak", "Off"});
   private final BooleanValue strafeFix = new BooleanValue("Movement Correction", true);
   private final ModeValue<String> towerMode = new ModeValue("Tower Mode", new String[]{"Vanilla", "Vulcan", "Static", "Off"});
   private final BooleanValue towerNoMove = new BooleanValue("Tower No Move", false);
   private final BooleanValue spoofSlot = new BooleanValue("Spoof Item Slot", false);
   float yaw;
   boolean hasBlock = false;
   float[] blockRotation = new float[]{0.0F, 0.0F};
   BlockPos blockPlace = new BlockPos(0, 0, 0);
   BlockPos blockPlacement = new BlockPos(0, 0, 0);
   EnumFacing blockPlacementFace;
   double jumpGround;

   public Scaffold() {
      this.blockPlacementFace = EnumFacing.DOWN;
      this.jumpGround = 0.0D;
      this.addSettings(new Value[]{this.rotationMode, this.keepRotationTicks, this.raycastMode, this.placeTiming, this.sprintMode, this.speedModifier, this.safewalkMode, this.strafeFix, this.towerMode, this.towerNoMove, this.spoofSlot});
   }

   public void onEnable() {
   }

   public void onDisable() {
      ItemSpoofUtil.stopSpoofing();
   }

   @Listen
   public void onMotion(MotionEvent event) {
      if (event.getState() == State.PRE) {
         if (this.placeTiming.getValue() == "Pre") {
            this.placeBlock();
         }
      } else if (this.placeTiming.getValue() == "Post") {
         this.placeBlock();
      }

   }

   @Listen
   public void onMove(MoveEvent event) {
      String var2 = ((String)this.safewalkMode.getValue()).toLowerCase();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1414557169:
         if (var2.equals("always")) {
            var3 = 1;
         }
         break;
      case -1237460601:
         if (var2.equals("ground")) {
            var3 = 0;
         }
         break;
      case 109582100:
         if (var2.equals("sneak")) {
            var3 = 2;
         }
      }

      switch(var3) {
      case 0:
         event.safewalk = event.safewalk || mc.getPlayer().onGround;
         break;
      case 1:
         event.safewalk = true;
         break;
      case 2:
         mc.getGameSettings().keyBindSneak.pressed = PlayerUtil.isOverAir();
      }

   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (!this.pickBlock()) {
         RotationUtil.disable();
      } else {
         this.setSprint();
         this.setMovementCorrection();
         this.runTowerMove();
         this.updatePlayerRotations();
         this.startSearch();
         if (this.placeTiming.getValue() == "Legit") {
            this.placeBlock();
         }

      }
   }

   private boolean pickBlock() {
      if (InventoryUtil.pickHotarBlock(true) != -1) {
         if ((Boolean)this.spoofSlot.getValue()) {
            ItemSpoofUtil.startSpoofing(InventoryUtil.pickHotarBlock(true));
         } else {
            mc.getPlayer().inventory.currentItem = InventoryUtil.pickHotarBlock(true);
         }

         return true;
      } else {
         ItemSpoofUtil.stopSpoofing();
         return false;
      }
   }

   private void setSprint() {
      String var1 = ((String)this.sprintMode.getValue()).toLowerCase();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1414557169:
         if (var1.equals("always")) {
            var2 = 0;
         }
         break;
      case 109935:
         if (var1.equals("off")) {
            var2 = 3;
         }
         break;
      case 561372103:
         if (var1.equals("no packet")) {
            var2 = 1;
         }
         break;
      case 2065509560:
         if (var1.equals("hypixel safe")) {
            var2 = 2;
         }
      }

      switch(var2) {
      case 0:
      case 1:
         mc.getPlayer().setSprinting(true);
         break;
      case 2:
         mc.getPlayer().setSprinting(false);
         EntityPlayerSP var10000 = mc.getPlayer();
         var10000.motionX *= 0.95D;
         var10000 = mc.getPlayer();
         var10000.motionZ *= 0.95D;
         break;
      case 3:
         mc.getPlayer().setSprinting(false);
      }

   }

   private void setMovementCorrection() {
      if ((Boolean)this.strafeFix.getValue()) {
         RotationUtil.setStrafeFix(true, false);
      } else {
         RotationUtil.setStrafeFix(false, false);
      }

   }

   private void updatePlayerRotations() {
      String var1 = ((String)this.rotationMode.getValue()).toLowerCase();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case 233102203:
         if (var1.equals("vanilla")) {
            var2 = 1;
         }
         break;
      case 1381910549:
         if (var1.equals("hypixel")) {
            var2 = 0;
         }
      }

      switch(var2) {
      case 0:
         RotationUtil.setClientRotation(new float[]{mc.getPlayer().rotationYaw + 180.0F, 78.0F}, (Integer)this.keepRotationTicks.getValue());
         break;
      case 1:
         RotationUtil.setClientRotation(this.blockRotation, (Integer)this.keepRotationTicks.getValue());
      }

      BlockPos below = new BlockPos(mc.getPlayer().posX, mc.getPlayer().posY - 1.0D, mc.getPlayer().posZ);
      if (!BlockUtils.isReplaceable(below) && (Integer)this.keepRotationTicks.getValue() == 0) {
         RotationUtil.disable();
      }

   }

   private void runTowerMove() {
      if (GameSettings.isKeyDown(mc.getGameSettings().keyBindJump) && (!(Boolean)this.towerNoMove.getValue() || !MovementUtil.isMoving())) {
         String var1 = ((String)this.towerMode.getValue()).toLowerCase();
         byte var2 = -1;
         switch(var1.hashCode()) {
         case -892481938:
            if (var1.equals("static")) {
               var2 = 0;
            }
            break;
         case -805359837:
            if (var1.equals("vulcan")) {
               var2 = 2;
            }
            break;
         case 109935:
            if (var1.equals("off")) {
               var2 = 3;
            }
            break;
         case 233102203:
            if (var1.equals("vanilla")) {
               var2 = 1;
            }
         }

         switch(var2) {
         case 0:
            mc.getPlayer().motionY = 0.42D;
            break;
         case 1:
            if (mc.getPlayer().onGround) {
               this.jumpGround = mc.getPlayer().posY;
               mc.getPlayer().motionY = 0.42D;
            }

            switch((int)Math.round((mc.getPlayer().posY - this.jumpGround) * 100.0D)) {
            case 42:
               mc.getPlayer().motionY = 0.33D;
               return;
            case 75:
               mc.getPlayer().motionY = 0.25D;
               return;
            case 100:
               this.jumpGround = mc.getPlayer().posY;
               mc.getPlayer().motionY = 0.42D;
               mc.getPlayer().onGround = true;
               return;
            default:
               return;
            }
         case 2:
            if (mc.getPlayer().onGround) {
               this.jumpGround = mc.getPlayer().posY;
               mc.getPlayer().motionY = PlayerUtil.getJumpHeight();
            }

            if (mc.getPlayer().posY > this.jumpGround + 0.65D && MovementUtil.isMoving()) {
               mc.getPlayer().motionY = 0.36D;
               this.jumpGround = mc.getPlayer().posY;
            }
            break;
         case 3:
            if (mc.getPlayer().onGround) {
               mc.getPlayer().motionY = PlayerUtil.getJumpHeight();
            }
         }
      }

   }

   private void startSearch() {
      BlockPos below = new BlockPos(mc.getPlayer().posX, mc.getPlayer().posY - 1.0D, mc.getPlayer().posZ);
      if (BlockUtils.isReplaceable(below)) {
         List<BlockPos> searchQueue = new ArrayList();
         searchQueue.add(below.down());
         searchQueue.add(below.north());
         searchQueue.add(below.east());
         searchQueue.add(below.south());
         searchQueue.add(below.west());
         searchQueue.add(below.north().east());
         searchQueue.add(below.north().west());
         searchQueue.add(below.south().east());
         searchQueue.add(below.south().west());

         int i;
         for(i = 0; i < searchQueue.size(); ++i) {
            if (this.searchBlock((BlockPos)searchQueue.get(i))) {
               this.hasBlock = true;
               return;
            }
         }

         for(i = 0; i < searchQueue.size(); ++i) {
            if (this.searchBlock(((BlockPos)searchQueue.get(i)).down())) {
               this.hasBlock = true;
               return;
            }
         }

      }
   }

   private boolean searchBlock(BlockPos block) {
      if (BlockUtils.isFullBlock(block)) {
         EnumFacing placeFace = BlockUtils.getHorizontalFacingEnum(block);
         if ((double)block.getY() <= mc.getPlayer().posY - 2.0D) {
            placeFace = EnumFacing.UP;
         }

         this.blockRotation = BlockUtils.getFaceRotation(placeFace, block);
         this.blockPlace = block;
         this.blockPlacement = block.add(placeFace.getDirectionVec());
         if (!BlockUtils.isReplaceable(this.blockPlacement)) {
            return false;
         } else {
            this.blockPlacementFace = placeFace;
            return true;
         }
      } else {
         return false;
      }
   }

   private void placeBlock() {
      if (this.hasBlock) {
         boolean canContinue = true;
         MovingObjectPosition raytraced = mc.getWorld().rayTraceBlocks(mc.getPlayer().getPositionEyes(1.0F), mc.getPlayer().getPositionEyes(1.0F).add(RotationUtil.getNormalRotVector(RotationUtil.clientRotation).multiply(4.0D)), false, true, false);
         String var3 = ((String)this.raycastMode.getValue()).toLowerCase();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -1039745817:
            if (var3.equals("normal")) {
               var4 = 0;
            }
            break;
         case -891986231:
            if (var3.equals("strict")) {
               var4 = 1;
            }
         }

         switch(var4) {
         case 0:
            if (raytraced == null) {
               canContinue = false;
            } else if (raytraced.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
               canContinue = false;
            } else {
               canContinue = raytraced.getBlockPos() == this.blockPlacement;
            }
            break;
         case 1:
            if (raytraced == null) {
               canContinue = false;
            } else if (raytraced.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
               canContinue = raytraced.getBlockPos() == this.blockPlacement && raytraced.sideHit == this.blockPlacementFace;
            }
         }

         if (canContinue) {
            BlockPos below = new BlockPos(mc.getPlayer().posX, mc.getPlayer().posY - 1.0D, mc.getPlayer().posZ);
            if (BlockUtils.isReplaceable(below)) {
               Vec3 hitVec = (new Vec3(this.blockPlacementFace.getDirectionVec())).multiply(0.5D).add(new Vec3(0.5D, 0.5D, 0.5D));
               if (mc.getPlayerController().onPlayerRightClick(mc.getPlayer(), mc.getWorld(), mc.getPlayer().getHeldItem(), this.blockPlace, this.blockPlacementFace, hitVec)) {
                  mc.getPlayer().swingItem();
                  EntityPlayerSP var10000 = mc.getPlayer();
                  var10000.motionX *= (Double)this.speedModifier.getValue();
                  var10000 = mc.getPlayer();
                  var10000.motionZ *= (Double)this.speedModifier.getValue();
                  this.hasBlock = false;
               }

            }
         }
      }
   }
}

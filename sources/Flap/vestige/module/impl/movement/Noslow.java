package vestige.module.impl.movement;

import java.util.Objects;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.ItemRenderEvent;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.PacketSendEvent;
import vestige.event.impl.PostMotionEvent;
import vestige.event.impl.PreMotionEvent;
import vestige.event.impl.SlowdownEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.combat.Killaura;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.network.PacketUtil;
import vestige.util.player.MovementUtil;
import vestige.util.world.BlockUtils;

public class Noslow extends Module {
   private final ModeSetting swordMethod = new ModeSetting("Sword mode", "Vanilla", new String[]{"Vanilla", "Hypixel", "NCP", "Alpha", "None"});
   private final ModeSetting consumableMethod = new ModeSetting("Eat mode", "Vanilla", new String[]{"Vanilla", "Hypixel", "Alpha", "Grim", "None"});
   private final DoubleSetting forward = new DoubleSetting("Forward", 1.0D, 0.2D, 1.0D, 0.05D);
   private final DoubleSetting strafe = new DoubleSetting("Strafe", 1.0D, 0.2D, 1.0D, 0.05D);
   private final IntegerSetting blinkTicks = new IntegerSetting("Blink ticks", () -> {
      return this.swordMethod.is("Blink");
   }, 5, 2, 10, 1);
   public final BooleanSetting allowSprinting = new BooleanSetting("Allow sprinting", true);
   private Killaura killauraModule;
   private boolean lastUsingItem;
   private int ticks;
   private boolean send = false;
   private int lastSlot;
   private boolean badC07 = false;
   private boolean wasEating;
   private boolean postPlace;
   private boolean canFloat;
   private boolean reSendConsume;
   private int ticksOffStairs;
   public float fMultiplier = 0.0F;
   public float sMultiplier = 0.0F;
   public boolean sprinting = true;
   boolean send2;
   boolean offset;
   int inAirTicks;

   public Noslow() {
      super("Noslow", Category.MOVEMENT);
      this.addSettings(new AbstractSetting[]{this.swordMethod, this.consumableMethod, this.forward, this.strafe, this.blinkTicks, this.allowSprinting});
   }

   public String getInfo() {
      return this.consumableMethod.getMode();
   }

   public void onEnable() {
      this.lastUsingItem = this.wasEating = false;
      this.lastSlot = mc.thePlayer.inventory.currentItem;
      this.ticks = 0;
   }

   public boolean onDisable() {
      Flap.instance.getPacketBlinkHandler().stopAll();
      return false;
   }

   public void onClientStarted() {
      this.killauraModule = (Killaura)Flap.instance.getModuleManager().getModule(Killaura.class);
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (this.isUsingItem()) {
         String var2;
         byte var3;
         if (this.isBlocking()) {
            var2 = this.swordMethod.getMode();
            var3 = -1;
            switch(var2.hashCode()) {
            case -1248403467:
               if (var2.equals("Hypixel")) {
                  var3 = 2;
               }
               break;
            case 77115:
               if (var2.equals("NCP")) {
                  var3 = 0;
               }
               break;
            case 63357246:
               if (var2.equals("Alpha")) {
                  var3 = 1;
               }
            }

            switch(var3) {
            case 0:
               PacketUtil.releaseUseItem(true);
               break;
            case 1:
               if (mc.thePlayer.ticksExisted % 3 == 0) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, (ItemStack)null, 0.0F, 0.0F, 0.0F));
               }
               break;
            case 2:
               if (mc.thePlayer.ticksExisted % 3 == 0 && !this.badC07 && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
                  mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, (ItemStack)null, 0.0F, 0.0F, 0.0F));
               }

               MovementUtil.spoofNextC03(false);
            }
         } else {
            var2 = this.consumableMethod.getMode();
            var3 = -1;
            switch(var2.hashCode()) {
            case -1248403467:
               if (var2.equals("Hypixel")) {
                  var3 = 2;
               }
               break;
            case 2228079:
               if (var2.equals("Grim")) {
                  var3 = 1;
               }
               break;
            case 63357246:
               if (var2.equals("Alpha")) {
                  var3 = 0;
               }
            }

            switch(var3) {
            case 0:
            default:
               break;
            case 1:
               InventoryPlayer playerInventory = mc.thePlayer.inventory;
               int currentSlotIndex = playerInventory.currentItem;
               PacketUtil.sendPacket(new C09PacketHeldItemChange(currentSlotIndex % 8 + 1));
               PacketUtil.sendPacket(new C09PacketHeldItemChange(currentSlotIndex));
               break;
            case 2:
               if (mc.thePlayer.ticksExisted % 3 == 0 && !this.badC07 && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
                  mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, (ItemStack)null, 0.0F, 0.0F, 0.0F));
               }

               MovementUtil.spoofNextC03(false);
            }
         }
      }

   }

   private void onReceive(PacketReceiveEvent event) {
      if (event.getPacket() instanceof C07PacketPlayerDigging) {
         this.badC07 = true;
      }

   }

   private void setMultipliers(float forward, float strafe) {
      this.fMultiplier = forward;
      this.sMultiplier = strafe;
      this.sprinting = true;
   }

   @Listener
   public void onPostMotion(PostMotionEvent event) {
      boolean usingItem = mc.thePlayer.isUsingItem();
      if (usingItem) {
         if (this.isBlocking()) {
            String var3 = this.swordMethod.getMode();
            byte var4 = -1;
            switch(var3.hashCode()) {
            case 77115:
               if (var3.equals("NCP")) {
                  var4 = 0;
               }
            default:
               switch(var4) {
               case 0:
                  if (this.isBlocking()) {
                     PacketUtil.sendBlocking(true, false);
                  }
               }
            }
         } else {
            Objects.requireNonNull(this.consumableMethod.getMode());
         }
      }

      this.lastUsingItem = usingItem;
      this.wasEating = usingItem && mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemFood || mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion);
   }

   @Listener
   public void onSlowdown(SlowdownEvent event) {
      if ((!this.isBlocking() || !this.swordMethod.is("None")) && (this.isBlocking() || !this.consumableMethod.is("None"))) {
         event.setForward((float)this.forward.getValue());
         event.setStrafe((float)this.strafe.getValue());
         event.setAllowedSprinting(this.allowSprinting.isEnabled());
      }

   }

   @Listener
   public void onSend(PacketSendEvent event) {
      if (event.getPacket() instanceof C07PacketPlayerDigging) {
         C07PacketPlayerDigging packet = (C07PacketPlayerDigging)event.getPacket();
         if (packet.getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) {
            if (this.isHoldingSword() && this.swordMethod.is("Spoof")) {
               event.setCancelled(true);
               int slot = mc.thePlayer.inventory.currentItem;
               PacketUtil.sendPacketFinal(new C09PacketHeldItemChange(slot < 8 ? slot + 1 : 0));
               PacketUtil.sendPacketFinal(new C09PacketHeldItemChange(slot));
            }

            ItemStack var4 = mc.thePlayer.getHeldItem();
         }
      }

   }

   @Listener
   public void onSends(PacketSendEvent event) {
   }

   @Listener
   public void onPremotion(PreMotionEvent event) {
   }

   boolean noSlowItem(String itemStack) {
      return itemStack.contains("Food") || itemStack.contains("Apple") || itemStack.contains("Soup") || itemStack.contains("Potion") || itemStack.contains("Bow");
   }

   public static boolean isRest(Item item) {
      return item instanceof ItemFood;
   }

   public static float getSlowed() {
      if (mc.thePlayer.getHeldItem() != null && Flap.instance.getModuleManager().getModule(Noslow.class) != null && ((Noslow)Flap.instance.getModuleManager().getModule(Noslow.class)).isEnabled()) {
         if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
            return 0.2F;
         } else if (mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion && !ItemPotion.isSplash(mc.thePlayer.getHeldItem().getItemDamage())) {
            return 0.2F;
         } else {
            float val = 0.2F;
            return val;
         }
      } else {
         return 0.2F;
      }
   }

   private void resetFloat() {
      this.reSendConsume = false;
      this.canFloat = false;
   }

   private boolean holdingConsumable(ItemStack itemStack) {
      Item heldItem = itemStack.getItem();
      return heldItem instanceof ItemFood || heldItem instanceof ItemBow || heldItem instanceof ItemPotion && !ItemPotion.isSplash(mc.thePlayer.getHeldItem().getItemDamage());
   }

   private boolean canFloat() {
      return !mc.thePlayer.isOnLadder() && this.ticksOffStairs != 0;
   }

   private boolean lookingAtInteractable() {
      MovingObjectPosition mop = mc.objectMouseOver;
      return mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && BlockUtils.isInteractable(BlockUtils.getBlock(mop.getBlockPos()));
   }

   private boolean holdingEdible(ItemStack stack) {
      if (stack.getItem() instanceof ItemFood && mc.thePlayer.getFoodStats().getFoodLevel() == 20) {
         ItemFood var10000 = (ItemFood)stack.getItem();
         boolean alwaysEdible = true;
         return alwaysEdible;
      } else {
         return true;
      }
   }

   @Listener
   public void onItemRender(ItemRenderEvent event) {
      if (this.isHoldingSword() && this.pressingUseItem() && this.swordMethod.is("Blink")) {
         event.setRenderBlocking(true);
      }

   }

   public boolean isBlocking() {
      return mc.thePlayer.isUsingItem() && this.isHoldingSword();
   }

   public boolean isUsingItem() {
      return mc.thePlayer.isUsingItem() && (!this.killauraModule.isEnabled() || this.killauraModule.getTarget() == null || this.killauraModule.autoblock.is("None"));
   }

   public boolean isHoldingSword() {
      return mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
   }

   public boolean pressingUseItem() {
      return !(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof GuiChest) && Mouse.isButtonDown(1);
   }
}

package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventDamageBlock;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.NetUtil;
import exhibition.util.misc.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class ItemSpoof extends Module {
   private boolean send;
   private BlockPos block;
   private EnumFacing facing;
   private int placeDir;
   private ItemStack stack;
   private float facingX;
   private float facingY;
   private float facingZ;
   public static Entity target;

   public ItemSpoof(ModuleData data) {
      super(data);
   }

   private int getSlotForTool(Block pos) {
      int slot = 9;
      float damage = 0.0F;

      for(int i = 9; i <= 35; ++i) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            float curDamage = stack.getStrVsBlock(pos);
            if (curDamage >= damage) {
               damage = curDamage;
               slot = i;
               System.out.println(stack.getDisplayName() + " " + i);
            }
         }
      }

      return slot;
   }

   public float damage(Block p_180471_1_) {
      ItemStack var4 = mc.thePlayer.inventoryContainer.getSlot(this.getSlotForTool(mc.theWorld.getBlockState(this.block).getBlock())).getStack();
      float var5 = var4.getStrVsBlock(mc.theWorld.getBlockState(this.block).getBlock());
      if (var5 > 1.0F) {
         int var6 = EnchantmentHelper.getEfficiencyModifier(mc.thePlayer);
         if (var6 > 0 && var4 != null) {
            var5 += (float)(var6 * var6 + 1);
         }
      }

      if (mc.thePlayer.isPotionActive(Potion.digSpeed)) {
         var5 *= 1.0F + (float)(mc.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if (mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
         float var7 = 1.0F;
         switch(mc.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
         case 0:
            var7 = 0.3F;
            break;
         case 1:
            var7 = 0.09F;
            break;
         case 2:
            var7 = 0.0027F;
            break;
         default:
            var7 = 8.1E-4F;
         }

         var5 *= var7;
      }

      if (mc.thePlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(mc.thePlayer)) {
         var5 /= 5.0F;
      }

      if (!mc.thePlayer.onGround) {
         var5 /= 5.0F;
      }

      return var5;
   }

   @RegisterEvent(
      events = {EventPacket.class, EventDamageBlock.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventPacket) {
         EventPacket e = (EventPacket)event;
         if (e.isOutgoing() && e.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity attack = (C02PacketUseEntity)e.getPacket();
            if (attack.getAction() == C02PacketUseEntity.Action.ATTACK) {
               ChatUtil.printChat("packet g");
               if (target == null) {
                  target = attack.getEntityFromWorld(mc.theWorld);
               }

               if (target != null) {
                  ChatUtil.printChat("ok?");
                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 9, mc.thePlayer.inventory.currentItem, 2, mc.thePlayer);
                  mc.playerController.updateController();
                  NetUtil.sendPacketNoEvents(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 9, mc.thePlayer.inventory.currentItem, 2, mc.thePlayer);
                  mc.playerController.updateController();
                  target = null;
               }
            }
         }
      }

   }
}

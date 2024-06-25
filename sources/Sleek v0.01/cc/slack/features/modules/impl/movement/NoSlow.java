package cc.slack.features.modules.impl.movement;

import cc.slack.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.combat.KillAura;
import cc.slack.utils.client.mc;
import cc.slack.utils.network.PacketUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(
   name = "NoSlow",
   category = Category.MOVEMENT
)
public class NoSlow extends Module {
   public final ModeValue<String> mode = new ModeValue("Bypass Mode", new String[]{"Vanilla", "Vulcan", "NCP Latest", "Hypixel", "Switch", "Place", "C08 Tick"});
   public final NumberValue<Float> forwardMultiplier = new NumberValue("Forward Multiplier", 1.0F, 0.2F, 1.0F, 0.05F);
   public final NumberValue<Float> strafeMultiplier = new NumberValue("Strafe Multiplier", 1.0F, 0.2F, 1.0F, 0.05F);
   public final BooleanValue onEat = new BooleanValue("Eating NoSlow", true);

   public NoSlow() {
      this.addSettings(new Value[]{this.mode, this.forwardMultiplier, this.strafeMultiplier, this.onEat});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer() != null) {
         boolean usingItem = mc.getPlayer().isUsingItem() || ((KillAura)Slack.getInstance().getModuleManager().getInstance(KillAura.class)).isToggle() || ((KillAura)Slack.getInstance().getModuleManager().getInstance(KillAura.class)).isBlocking;
         if (usingItem && mc.getPlayer().getHeldItem() != null && mc.getPlayer().getHeldItem().item instanceof ItemSword || mc.getPlayer().isUsingItem() && mc.getPlayer().getHeldItem().item instanceof ItemFood && (Boolean)this.onEat.getValue()) {
            String var3 = ((String)this.mode.getValue()).toLowerCase();
            byte var4 = -1;
            switch(var3.hashCode()) {
            case -889473228:
               if (var3.equals("switch")) {
                  var4 = 3;
               }
               break;
            case -805359837:
               if (var3.equals("vulcan")) {
                  var4 = 0;
               }
               break;
            case 106748167:
               if (var3.equals("place")) {
                  var4 = 4;
               }
               break;
            case 233102203:
               if (var3.equals("vanilla")) {
                  var4 = 1;
               }
               break;
            case 455680492:
               if (var3.equals("ncp latest")) {
                  var4 = 2;
               }
               break;
            case 1381910549:
               if (var3.equals("hypixel")) {
                  var4 = 6;
               }
               break;
            case 2026380018:
               if (var3.equals("c08 tick")) {
                  var4 = 5;
               }
            }

            switch(var4) {
            case 0:
            case 1:
            case 2:
            default:
               break;
            case 3:
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.getPlayer().inventory.currentItem % 8 + 1));
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.getPlayer().inventory.currentItem));
               break;
            case 4:
               mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.getPlayer().inventory.getCurrentItem()));
               break;
            case 5:
               if (mc.getPlayer().ticksExisted % 3 == 0) {
                  mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.getPlayer().getHeldItem()));
               }
               break;
            case 6:
               if (mc.getPlayer().isUsingItem() && mc.getPlayer().getHeldItem() != null && mc.getPlayer().getHeldItem().getItem() instanceof ItemSword) {
                  PacketUtil.sendBlocking(true, false);
                  if (!mc.getPlayer().isUsingItem() && !mc.getPlayer().isBlocking() && mc.getPlayer().ticksExisted % 3 == 0) {
                     PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), EnumFacing.UP.getIndex(), (ItemStack)null, 0.0F, 0.0F, 0.0F));
                  }
               }
            }
         }

      }
   }
}

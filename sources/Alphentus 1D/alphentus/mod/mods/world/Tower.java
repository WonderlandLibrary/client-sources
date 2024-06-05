package alphentus.mod.mods.world;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 02.08.2020.
 */
public class Tower extends Mod {

    public Tower() {
        super("Tower", Keyboard.KEY_NONE, true, ModCategory.WORLD);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;
        if (event.getType() == Type.PRE) {
            event.setYaw(mc.thePlayer.rotationYaw);
            event.setPitch(90);
        }else if(event.getType() == Type.POST){
            if (mc.thePlayer.onGround)
                mc.thePlayer.jump();
            ItemStack silentItemStack = null;
            if (mc.thePlayer.getCurrentEquippedItem() == null || !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock || !isPlacableBlock(mc.thePlayer.getCurrentEquippedItem()))) {
                for (int i = 0; i < 9; i++) {
                    if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && isPlacableBlock(mc.thePlayer.inventory.getStackInSlot(i))) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                        silentItemStack = mc.thePlayer.inventory.getStackInSlot(i);
                        break;
                    }
                }
            } else {
                silentItemStack = (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) ? mc.thePlayer.getCurrentEquippedItem() : null;
            }

            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
            mc.thePlayer.swingItem();
            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, silentItemStack, pos, EnumFacing.UP, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
        }


    }

    public boolean isPlacableBlock(ItemStack is) {
        if (is == null || is.getItem().getUnlocalizedName().equals("tnt")
                || is.getItem().getUnlocalizedName().equals("web")
                || is.getItem().getUnlocalizedName().equals("ice"))
            return false;
        return true;
    }
}
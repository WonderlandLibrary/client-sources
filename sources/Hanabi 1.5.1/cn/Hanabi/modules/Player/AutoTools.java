package cn.Hanabi.modules.Player;

import cn.Hanabi.modules.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class AutoTools extends Mod
{
    
    
    public AutoTools() {
        super("AutoTools", Category.PLAYER);
    }
    
    public Entity getItems(final double n) {
        Entity entity = null;
        double n2 = n;
        for (final Entity entity2 : AutoTools.mc.theWorld.loadedEntityList) {
            if (AutoTools.mc.thePlayer.onGround && AutoTools.mc.thePlayer.isCollidedVertically && entity2 instanceof EntityItem) {
                final double n3 = AutoTools.mc.thePlayer.getDistanceToEntity(entity2);
                if (n3 > n2) {
                    continue;
                }
                n2 = n3;
                entity = entity2;
            }
        }
        return entity;
    }
    
    @EventTarget
    public void onAttack(final EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)eventPacket.getPacket()).getAction().equals((Object)C02PacketUseEntity.Action.ATTACK) && !AutoTools.mc.thePlayer.isEating()) {
            this.bestSword(((C02PacketUseEntity)eventPacket.getPacket()).getEntityFromWorld((World)AutoTools.mc.theWorld));
        }
    }
    
    @EventTarget
    public void onClickBlock(final EventPostMotion eventPostMotion) {
        if (!AutoTools.mc.thePlayer.isEating() && AutoTools.mc.playerController.getIsHittingBlock() && !Objects.isNull(AutoTools.mc.objectMouseOver.getBlockPos())) {
            this.bestTool(AutoTools.mc.objectMouseOver.getBlockPos().getX(), AutoTools.mc.objectMouseOver.getBlockPos().getY(), AutoTools.mc.objectMouseOver.getBlockPos().getZ());
        }
    }
    
    public void bestSword(final Entity entity) {
        int currentItem = 0;
        float getDamageVsEntity = -1.0f;
        for (int i = 36; i < 45; ++i) {
            if (AutoTools.mc.thePlayer.inventoryContainer.inventorySlots.toArray()[i] != null && entity != null) {
                final ItemStack getStack = AutoTools.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getStack != null && getStack.getItem() instanceof ItemSword) {
                    final ItemSword itemSword = (ItemSword)getStack.getItem();
                    if (itemSword.getDamageVsEntity() > getDamageVsEntity) {
                        currentItem = i - 36;
                        getDamageVsEntity = itemSword.getDamageVsEntity();
                    }
                }
            }
        }
        if (getDamageVsEntity > -1.0f) {
            AutoTools.mc.thePlayer.inventory.currentItem = currentItem;
            AutoTools.mc.playerController.updateController();
        }
    }
    
    public void bestTool(final int n, final int n2, final int n3) {
        final int getIdFromBlock = Block.getIdFromBlock(AutoTools.mc.theWorld.getBlockState(new BlockPos(n, n2, n3)).getBlock());
        int currentItem = 0;
        float getStrVsBlock = -1.0f;
        for (int i = 36; i < 45; ++i) {
            try {
                final ItemStack getStack = AutoTools.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if ((getStack.getItem() instanceof ItemTool || getStack.getItem() instanceof ItemSword || getStack.getItem() instanceof ItemShears) && getStack.getStrVsBlock(Block.getBlockById(getIdFromBlock)) > getStrVsBlock) {
                    currentItem = i - 36;
                    getStrVsBlock = getStack.getStrVsBlock(Block.getBlockById(getIdFromBlock));
                }
            }
            catch (Exception ex) {}
        }
        if (getStrVsBlock != -1.0f) {
            AutoTools.mc.thePlayer.inventory.currentItem = currentItem;
            AutoTools.mc.playerController.updateController();
        }
    }
}

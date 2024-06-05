package me.rich.module.player;


import clickgui.setting.Setting;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class NoInteract extends Feature {
	
    public Setting craft;
    public Setting furnace;
    public Setting armorStand;

    public NoInteract() {
        super("NoInteract", 0, Category.PLAYER);
        this.craft = new Setting("Crafting table", this, true);
        this.furnace = new Setting("Furnace",this, true);
        this.armorStand = new Setting("Armor Stand",this, true);
    }

    @EventTarget
    public void onUpdate(EventPreMotionUpdate event){
    }

    @EventTarget
    public void onPacket(EventPacket event){
        if(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock){
            final Block block = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            if((block == Blocks.CRAFTING_TABLE && craft.getValBoolean()) ||
                    (block == Blocks.FURNACE && furnace.getValBoolean()) ||
                    (mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityArmorStand && armorStand.getValBoolean())){
                event.setCancelled(true);
            }

        }
    }


}

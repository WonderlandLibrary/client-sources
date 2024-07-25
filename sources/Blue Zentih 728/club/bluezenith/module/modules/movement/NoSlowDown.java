package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.SlowdownEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.ExtendedModeValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.util.client.Pair;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class NoSlowDown extends Module {
    public final ExtendedModeValue extendedModeValue = new ExtendedModeValue(
            this,
            "Mode",
            new Pair<>("NCP", new NCPNoSlowDown()),
            new Pair<>("Hypixel", new HypixelNoSlowDown()),
            new Pair<>("Vanilla", new VanillaNoSlowDown())
    ).setIndex(1);

    private final FloatValue itemMulti = new FloatValue("Reduce by", 1f, 0f, 1f, 0.05f).setIndex(2);

    public NoSlowDown() {
        super("NoSlow", ModuleCategory.MOVEMENT, "noslowdown");

        setExtendedModeValue(extendedModeValue);
    }

    @Override
    public String getTag() {
        return extendedModeValue.get().getKey();
    }

    private void unblockPacket(BlockPos pos, EnumFacing enumFacing) {
        PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, pos, enumFacing));
    }

    private void blockPacket(ItemStack itemStack) {
        PacketUtil.send(new C08PacketPlayerBlockPlacement(itemStack));
    }

    private boolean isUsingItem(Class<? extends Item> itemToCheckFor) {
        ItemStack itemInUse;
        return player.isUsingItem() && (itemInUse = player.getItemInUse()) != null && itemInUse.getItem().getClass() == itemToCheckFor;
    }

    private class VanillaNoSlowDown implements ExtendedModeValue.Mode {

        @Listener
        public void onSlowDown(SlowdownEvent event) {
            if(!mc.thePlayer.isUsingItem()) return;

            if(itemMulti.get() == 0F) event.cancel();
            event.reducer = itemMulti.get();
        }

    }

    private class NCPNoSlowDown implements ExtendedModeValue.Mode {

        @Listener
        public void onSlowDown(SlowdownEvent event) {

            if(itemMulti.get() == 0F) event.cancel();
            event.reducer = itemMulti.get();
        }

        @Listener
        public void onUpdatePlayer(UpdatePlayerEvent event) {
            if(!isUsingItem(ItemSword.class)) return;

            if(event.isPre()) unblockPacket(BlockPos.ORIGIN, EnumFacing.DOWN);
            else if(event.isPost()) blockPacket(mc.thePlayer.getHeldItem());
        }

    }

    private class HypixelNoSlowDown implements ExtendedModeValue.Mode {

        @Listener
        public void onSlowDown(SlowdownEvent event) {

            if(itemMulti.get() == 0F) event.cancel();
            event.reducer = itemMulti.get();
        }

        @Listener
        public void onUpdatePlayer(UpdatePlayerEvent event) {
            if(!mc.thePlayer.isUsingItem() || isUsingItem(ItemSword.class)) return;

            if(event.isPre()) {
                final int nextSlot = mc.thePlayer.inventory.currentItem == 8 ? 0 : mc.thePlayer.inventory.currentItem + 1;

                PacketUtil.send(new C09PacketHeldItemChange(nextSlot));
                PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }
        }

    }
}

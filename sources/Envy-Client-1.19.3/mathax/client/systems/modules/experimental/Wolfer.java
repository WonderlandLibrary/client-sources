package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.SwingCancel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

public class Wolfer extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Boolean> swing = sgGeneral.add(new BoolSetting.Builder()
        .name("Swing Cancel")
        .description("If enabled. will cancel swinging!")
        .defaultValue(false)
        .build()
    );
    private final Setting<Integer> Radius = sgGeneral.add(new IntSetting.Builder()
        .name("Range")
        .description("Range of detection.")
        .defaultValue(4)
        .range(1, 7)
        .sliderRange(1, 7)
        .build()
    );
    //lol


    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;

        double detectionDistance = Radius.get();
        Entity WolfTame = null;
        double closestDistanceSq = Double.MAX_VALUE;

        for (Entity entity : mc.world.getEntities()) {
            if (entity == mc.player || entity.squaredDistanceTo(mc.player) > detectionDistance * detectionDistance)
                continue;

            double entityDistanceSq = mc.player.squaredDistanceTo(entity);

            if (entityDistanceSq < closestDistanceSq) {
                closestDistanceSq = entityDistanceSq;
                WolfTame = entity;
            }
        }
        ItemStack mainHandItem = mc.player.getMainHandStack();
        if (mode.get() == Mode.Client) {
            if (WolfTame instanceof WolfEntity && !((WolfEntity) WolfTame).isTamed()) {
                if (mainHandItem.getItem() == Items.BONE) {
                    if (swing.get()) {
                        SwingCancel.noSwing();
                    }
                    assert mc.interactionManager != null;
                    mc.interactionManager.interactEntity(mc.player,WolfTame, Hand.MAIN_HAND);
                }
            }
        }
        else if (mode.get() == Mode.Packet) {
            if (WolfTame instanceof WolfEntity && !((WolfEntity) WolfTame).isTamed()) {
                if (mainHandItem.getItem() == Items.BONE) {
                    if (swing.get()) {
                        SwingCancel.noSwing();
                    }
                    assert mc.interactionManager != null;
                    mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.interact(WolfTame,false, Hand.MAIN_HAND));
                }
            }
        }
    }
    public enum Mode {
        Client("Packet"),

        Packet("Client");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    private final Setting<Wolfer.Mode> mode = sgGeneral.add(new EnumSetting.Builder<Wolfer.Mode>()
        .name("mode")
        .description("Decide from packet or client sided rotation.")
        .defaultValue(Wolfer.Mode.Packet)
        .build()
    );
    public Wolfer() {
        super(Categories.Experimental, Items.BARRIER, "Wolfer", "Tames Wolves");
    }
}

package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.client.CapesModule;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

public class NoVulcan extends Module {

    public NoVulcan() {
        super(Categories.Movement, Items.DIAMOND_SWORD, "NoFall Vulcan", "Prevents FallDamage");
    }


    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Which NoFall Mode to use.")
        .defaultValue(Mode.Bounce)
        .build()
    );

    @EventHandler
    public void onTick(TickEvent.Post event) {

        if (mode.get() == Mode.Bounce) {
            Block novulcan = mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock();
            Block novulcan2 = mc.world.getBlockState(mc.player.getBlockPos().down(2)).getBlock();

            if (novulcan != Blocks.AIR && mc.player.fallDistance > 3) {
                mc.player.setPos(mc.player.getX(), mc.player.getY() + 0.2, mc.player.getZ());
                mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
                mc.player.fallDistance = 0f;
                mc.player.setOnGround(false);
                if (mc.player.getVelocity().y < 0.3) {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0.5, mc.player.getVelocity().z);
                }
            } else if (novulcan2 != Blocks.AIR && mc.player.fallDistance > 10) {
                mc.player.setPos(mc.player.getX(), mc.player.getY() + 0.2, mc.player.getZ());
                mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
                mc.player.fallDistance = 0f;
                mc.player.setOnGround(false);
                if (mc.player.getVelocity().y < 0.3) {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0.5, mc.player.getVelocity().z);
                }
            }
        }
        if (mode.get() == Mode.Clip) {
            Block novulcan = mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock();
            Block novulcan2 = mc.world.getBlockState(mc.player.getBlockPos().down(2)).getBlock();
            if (novulcan != Blocks.AIR && mc.player.fallDistance >= 3) {
                mc.player.updatePosition(mc.player.getX(), mc.player.getY() + 3, mc.player.getZ());
                mc.player.fallDistance = 0f;
            }
            else if (novulcan2 != Blocks.AIR && mc.player.fallDistance > 10) {
                mc.player.updatePosition(mc.player.getX(), mc.player.getY() + 3, mc.player.getZ());
                mc.player.fallDistance = 0f;
            }
        }
    }


    public enum Mode {
        Bounce("Bounce"),
        Clip("Clip"),
        Dev("Dev");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}

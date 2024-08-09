package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.vayzeutils.AntiWallUtil;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class AntiWall extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<AntiWall.Mode> mode = sgGeneral.add(new EnumSetting.Builder<AntiWall.Mode>()
        .name("mode")
        .description("Ways of antiWall")
        .defaultValue(Mode.oneBlock)
        .build()
    );
    @EventHandler
    public void onTick(TickEvent.Post event) {
       if (mode.get() == Mode.oneBlock) {
           AntiWallUtil.WallUpdate1();
        }
       else if (mode.get() == Mode.twoBlock) {
           AntiWallUtil.WallUpdate2();
       }
       else if (mode.get() == Mode.noCliper) {
           AntiWallUtil.WallUpdate3();
       }
       else if (mode.get() == Mode.exploiter) {
           AntiWallUtil.WallUpdate4();
       }
    }
    public enum Mode {
        oneBlock("1Block"),

        twoBlock("2Block"),

        noCliper("NoClip"),
        exploiter("Exploit");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    public AntiWall() {
        super(Categories.Experimental, Items.PAPER, "AntiWall", "Prevents suffocation in walls");
    }
}

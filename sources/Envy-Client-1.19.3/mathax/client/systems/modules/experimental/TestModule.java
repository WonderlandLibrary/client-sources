package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;

public class TestModule extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> pitch = sgGeneral.add(new IntSetting.Builder()
        .name("Integer")
        .description("Int Slider.")
        .defaultValue(1)
        .range(1, 360)
        .sliderRange(1, 360)
        .build()
    );
    private final Setting<TestModule.Mode> mode = sgGeneral.add(new EnumSetting.Builder<TestModule.Mode>()
        .name("Mode")
        .description("Modes")
        .defaultValue(Mode.Test1)
        .build()
    );
    private final Setting<Boolean> booleansTestModule = sgGeneral.add(new BoolSetting.Builder()
        .name("Boolean")
        .description("True or False")
        .defaultValue(false)
        .build()
    );
    private final Setting<String> stringTestModule = sgGeneral.add(new StringSetting.Builder()
        .name("String")
        .description("Anything here")
        .defaultValue("This is a string.")
        .build()
    );
    @EventHandler
    public void onTick(TickEvent.Post event) {
       //AnyCodeHere


    }
    public enum Mode {
        Test1("Test1"),

        Test2("Test2");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    public TestModule() {
        super(Categories.Experimental, Items.BARRIER, "Test Module", "Module that should have all settings in it.");
    }
}

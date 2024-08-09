package mathax.client.systems.modules.client;

import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.gui.GuiTheme;
import mathax.client.gui.widgets.WWidget;
import mathax.client.gui.widgets.containers.WHorizontalList;
import mathax.client.gui.widgets.pressable.WButton;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.misc.PacketPitch;
import mathax.client.utils.network.Capes;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.item.Items;

public class CapesModule extends Module {

    public CapesModule() {
        super(Categories.Client, Items.CAKE, "capes", "Gives Players Envy Capes");
    }

    public String capeurl = "1.1.1.1";
    public String CDOSCape = "1.2.1.2";
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Decide from packet or client sided rotation.")
        .defaultValue(Mode.Envy)
        .build()
    );


    private final Setting<Boolean> showCape = sgGeneral.add(new BoolSetting.Builder()
        .name("Developer-Capes")
        .description("Shows Developer Capes")
        .defaultValue(true)
        .build()
    );


    @EventHandler
    public boolean onTick(TickEvent.Post event) {

        if (mode.get() == Mode.Envy) {
            capeurl = "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/EnvyCape.png";
        }
        if (mode.get() == Mode.Optifine) {
            capeurl = "http://s.optifine.net/capes/%s.png";
        }
        if (mode.get() == Mode.Cosmetica) {
            capeurl = "23.95.137.176";
        }
        if (mode.get() == Mode.Volcanware) {
            capeurl = "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/VolcanwareCape.png";
        }
        if (mode.get() == Mode.Toxin) {
            capeurl = "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/ToxinCape.png";
        }

        return false;
    }

    @EventHandler
    public boolean onTick(TickEvent.Pre event) {

        if (showCape.get()) {
            if (mc.player.getUuid().equals("f3611166-e8a6-4123-a9e1-f7cc01463698")) {
                CDOSCape.equals("https://cdn.discordapp.com/attachments/1121034355796619337/1156810304974495744/EnvyCapeCDOS.png?ex=6516530d&is=6515018d&hm=95ab8864826b5ae7f3b9d8274dd1e1d3232cbfedb5d81b832de2dde9fc0ddc0e&");
            }
        }

        return false;
    }




    public enum Mode {
        Envy("Envy"),
        Volcanware("Volcanware"),
        Toxin("Toxin"),

        Optifine("Optifine"),
        Cosmetica("Cosmetica");
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

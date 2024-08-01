package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.block.BlockBed;
import org.lwjgl.input.Mouse;
import wtf.diablo.client.event.impl.client.KeyEvent;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(name = "Bed Alert", description = "Alerts you when there is a player within range of your bed", category = ModuleCategoryEnum.MISC)
public final class BedAlertModule extends AbstractModule {
    private final NumberSetting<Integer> range = new NumberSetting<>("Range", 75, 1, 250, 1);

    private BlockBed bed;

    public BedAlertModule() {
        this.registerSettings(range);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        bed = null;
    }

    @EventHandler
    private final Listener<KeyEvent> keyEventListener = event -> {
        if (Mouse.isButtonDown(2)) {

        }
    };

    @EventHandler
    private final Listener<SendPacketEvent> packetEventListener = event -> {

    };
}

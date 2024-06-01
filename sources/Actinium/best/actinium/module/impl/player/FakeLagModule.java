package best.actinium.module.impl.player;

import best.actinium.component.componets.PingSpoofComponent;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.math.RandomUtil;

@ModuleInfo(
        name = "Fake lag",
        description = "Simulates Lagging to give u an advantage in pvp",
        category = ModuleCategory.PLAYER
)
public class FakeLagModule extends Module {
    private NumberProperty minMs = new NumberProperty("Min MS",this,0,500,5000,1);
    private NumberProperty maxMs = new NumberProperty("Max MS",this,0,500,2000,1);

    @Callback
    public void onUpdate(UpdateEvent event) {
        boolean blinkIncoming = true;
        //ima make more settings soon tm
        PingSpoofComponent.setSpoofing((int) RandomUtil.getAdvancedRandom(minMs.getValue().floatValue(),maxMs.getValue().floatValue()), blinkIncoming, blinkIncoming, blinkIncoming, blinkIncoming, blinkIncoming, true);
    }
}


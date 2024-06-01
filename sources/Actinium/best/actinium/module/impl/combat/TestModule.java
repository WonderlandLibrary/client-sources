package best.actinium.module.impl.combat;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.EarlyTickEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.render.ChatUtil;

@ModuleInfo(
        name = "Test",
        description = "a module made for testing stuff",
        category = ModuleCategory.COMBAT
)
public class TestModule extends Module {

    @Callback
    public void onTick(EarlyTickEvent event) {
       // this.setSuffix(String.valueOf(RandomUtil.calculatePing(100,200,40,false)));

        //for (int i = 0; i < 10; i++) {
          //this.setSuffix(String.valueOf(RandomUtil.calculatePing(150, 70, 70, 70, 200)));
      //  }
       // this.setSuffix(String.valueOf(RandomUtil.getRandomPing(100,200)));
    }
}

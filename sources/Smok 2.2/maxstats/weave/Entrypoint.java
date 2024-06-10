package maxstats.weave;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.entities.Utils;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.EventBus;
import net.weavemc.loader.api.event.KeyboardEvent;
import org.lwjgl.input.Keyboard;

// Class from SMok Client by SleepyFish
public class Entrypoint implements ModInitializer {

    public void preInit() {

        EventBus.subscribe(KeyboardEvent.class, e -> {
            if (Smok.inst.injected) {
                if (!Utils.inGui()) {
                    if (e.getKeyCode() == ClientUtils.getBindForGui(1) || e.getKeyCode() == 35 && Keyboard.isKeyDown(42)) {
                        Smok.inst.mc.displayGuiScreen(Smok.inst.guiManager.getClickGui());
                    }

                    Smok.inst.ratManager.getRats().forEach(mod -> {
                        if (e.getKeyCode() == mod.getBind() && e.getKeyState()) {
                            mod.toggle();
                        }
                    });
                }
            }
        });

    }

}
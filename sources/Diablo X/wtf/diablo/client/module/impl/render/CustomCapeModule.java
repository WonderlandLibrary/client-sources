package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.util.ResourceLocation;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(name = "Custom Cape", description = "Gives the player a custom cape", category = ModuleCategoryEnum.RENDER)
public final class CustomCapeModule extends AbstractModule {
    @Override
    protected void onDisable() {
        super.onDisable();
        mc.thePlayer.setLocationOfCape(null);
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        mc.thePlayer.setLocationOfCape(new ResourceLocation("diablo/images/cape.png"));
    };
}

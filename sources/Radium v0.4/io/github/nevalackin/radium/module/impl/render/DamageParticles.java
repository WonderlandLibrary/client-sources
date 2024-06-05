package io.github.nevalackin.radium.module.impl.render;


import io.github.nevalackin.radium.event.impl.player.DamageEntityEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import me.zane.basicbus.api.annotations.Listener;

// TODO: Use DamageEntityEvent
@ModuleInfo(label = "Damage Particles", category = ModuleCategory.RENDER)
public final class DamageParticles extends Module {

    @Listener
    public void onDamageEntityEvent(DamageEntityEvent event) {

    }

}

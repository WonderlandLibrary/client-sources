package arsenic.module.impl.visual;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventAttack;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;

@ModuleInfo(name = "Particles", category = ModuleCategory.Visual, hidden = true)
public class Particles extends Module {

    @EventLink()
    public final Listener<EventAttack> onAttack = event -> {
        Entity target = event.getTarget();
        this.mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC);
    };
}

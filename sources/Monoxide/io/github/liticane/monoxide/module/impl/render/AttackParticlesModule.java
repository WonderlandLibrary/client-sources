package io.github.liticane.monoxide.module.impl.render;

import net.minecraft.util.EnumParticleTypes;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.combat.AttackEntityEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;

@ModuleData(name = "AttackParticles", description = "Spawns particles after attacking someone", category = ModuleCategory.RENDER)
public class AttackParticlesModule extends Module {

    public NumberValue<Integer> crit = new NumberValue<>("Critical", this, 0, 0, 20, 0);
    public NumberValue<Integer> critMagic = new NumberValue<>("Critical Magic", this, 0, 0, 20, 0);

    @Listen
    public void onAttack(AttackEntityEvent attackEntityEvent) {
        for (int i = 0; i < this.crit.getValue(); ++i) {
            Methods.mc.effectRenderer.emitParticleAtEntity(attackEntityEvent.getEntity(), EnumParticleTypes.CRIT);
        }
        for (int i = 0; i < this.critMagic.getValue(); ++i) {
            Methods.mc.effectRenderer.emitParticleAtEntity(attackEntityEvent.getEntity(), EnumParticleTypes.CRIT_MAGIC);
        }
    }

}

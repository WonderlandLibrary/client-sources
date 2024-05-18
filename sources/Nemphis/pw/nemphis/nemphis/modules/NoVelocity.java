/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.COMBAT, color=-37796, name="NoVelocity")
public class NoVelocity
extends ToggleableModule {
    public Value<Integer> percentage;

    public NoVelocity() {
        this.percentage = new Value<Integer>("precentage", 15, this);
        this.addValue(this.percentage);
    }

    @EventListener
    public void on(UpdateEvent e) {
        this.setRenderMode(this.percentage.getValue() + "%");
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.IModelResolver;
import net.optifine.entity.model.anim.ModelVariableUpdater;

public class ModelUpdater {
    private ModelVariableUpdater[] modelVariableUpdaters;

    public ModelUpdater(ModelVariableUpdater[] modelVariableUpdaterArray) {
        this.modelVariableUpdaters = modelVariableUpdaterArray;
    }

    public void update() {
        for (int i = 0; i < this.modelVariableUpdaters.length; ++i) {
            ModelVariableUpdater modelVariableUpdater = this.modelVariableUpdaters[i];
            modelVariableUpdater.update();
        }
    }

    public boolean initialize(IModelResolver iModelResolver) {
        for (int i = 0; i < this.modelVariableUpdaters.length; ++i) {
            ModelVariableUpdater modelVariableUpdater = this.modelVariableUpdaters[i];
            if (modelVariableUpdater.initialize(iModelResolver)) continue;
            return true;
        }
        return false;
    }
}


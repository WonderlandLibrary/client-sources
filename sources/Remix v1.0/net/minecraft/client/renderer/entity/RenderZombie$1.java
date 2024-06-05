package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.model.*;

class RenderZombie$1 extends LayerBipedArmor {
    @Override
    protected void func_177177_a() {
        this.field_177189_c = new ModelZombie(0.5f, true);
        this.field_177186_d = new ModelZombie(1.0f, true);
    }
}
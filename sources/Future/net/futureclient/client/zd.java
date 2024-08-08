package net.futureclient.client;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.model.ModelBase;

public static class zd extends qF {
    private ModelBase k = k;
    
    public zd(final RenderLivingBase<?> renderLivingBase, final EntityLivingBase entityLivingBase, final ModelBase k) {
        super(renderLivingBase, entityLivingBase, k);
    }
    
    @Override
    public ModelBase M() {
        return this.k;
    }
}
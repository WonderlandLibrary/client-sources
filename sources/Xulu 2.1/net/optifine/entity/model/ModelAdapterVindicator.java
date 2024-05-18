package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVindicator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderVindicator;
import net.minecraft.entity.monster.EntityVindicator;

public class ModelAdapterVindicator extends ModelAdapterIllager
{
    public ModelAdapterVindicator()
    {
        super(EntityVindicator.class, "vindication_illager", 0.5F);
    }

    public ModelBase makeModel()
    {
        return new ModelVindicator(0.0F);
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderVindicator rendervindicator = new RenderVindicator(rendermanager);
        rendervindicator.mainModel = modelBase;
        rendervindicator.shadowSize = shadowSize;
        return rendervindicator;
    }
}

package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEvoker;
import net.minecraft.client.renderer.entity.RenderEvoker;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityEvoker;

public class ModelAdapterEvoker extends ModelAdapterIllager
{
    public ModelAdapterEvoker()
    {
        super(EntityEvoker.class, "evocation_illager", 0.5F);
    }

    public ModelBase makeModel()
    {
        return new ModelEvoker(0.0F);
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderEvoker renderevoker = new RenderEvoker(rendermanager);
        renderevoker.mainModel = modelBase;
        renderevoker.shadowSize = shadowSize;
        return renderevoker;
    }
}

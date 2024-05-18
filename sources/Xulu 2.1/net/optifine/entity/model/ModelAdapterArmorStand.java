package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderArmorStand;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelAdapterArmorStand extends ModelAdapter
{
    public ModelAdapterArmorStand()
    {
        super(EntityArmorStand.class, "armor_stand", 0.7F);
    }

    public ModelBase makeModel()
    {
        return new ModelArmorStand();
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelArmorStand))
        {
            return null;
        }
        else
        {
            ModelArmorStand modelarmorstand = (ModelArmorStand)model;
            return modelPart.equals("right") ? modelarmorstand.standRightSide : (modelPart.equals("left") ? modelarmorstand.standLeftSide : (modelPart.equals("waist") ? modelarmorstand.standWaist : (modelPart.equals("base") ? modelarmorstand.standBase : null)));
        }
    }

    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize)
    {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderArmorStand renderarmorstand = new RenderArmorStand(rendermanager);
        renderarmorstand.mainModel = modelBase;
        renderarmorstand.shadowSize = shadowSize;
        return renderarmorstand;
    }
}

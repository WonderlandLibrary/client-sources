package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAdapterIllager extends ModelAdapter
{
    public ModelAdapterIllager(Class entityClass, String name, float shadowSize)
    {
        super(entityClass, name, shadowSize);
    }

    public ModelRenderer getModelRenderer(ModelBase model, String modelPart)
    {
        if (!(model instanceof ModelIllager))
        {
            return null;
        }
        else
        {
            ModelIllager modelillager = (ModelIllager)model;
            return modelPart.equals("head") ? modelillager.field_191217_a : (modelPart.equals("body") ? modelillager.field_191218_b : (modelPart.equals("arms") ? modelillager.field_191219_c : (modelPart.equals("left_leg") ? modelillager.field_191221_e : (modelPart.equals("right_leg") ? modelillager.field_191220_d : (modelPart.equals("nose") ? modelillager.field_191222_f : (modelPart.equals("left_arm") ? modelillager.field_191224_h : (modelPart.equals("right_arm") ? modelillager.field_191223_g : null)))))));
        }
    }
}

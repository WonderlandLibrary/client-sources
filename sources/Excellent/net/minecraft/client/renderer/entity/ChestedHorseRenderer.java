package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.model.HorseArmorChestsModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class ChestedHorseRenderer<T extends AbstractChestedHorseEntity> extends AbstractHorseRenderer<T, HorseArmorChestsModel<T>>
{
    private static final Map < EntityType<?>, ResourceLocation > field_195635_a = Maps.newHashMap(ImmutableMap.of(EntityType.DONKEY, new ResourceLocation("textures/entity/horse/donkey.png"), EntityType.MULE, new ResourceLocation("textures/entity/horse/mule.png")));

    public ChestedHorseRenderer(EntityRendererManager renderManagerIn, float scaleIn)
    {
        super(renderManagerIn, new HorseArmorChestsModel<>(0.0F), scaleIn);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(T entity)
    {
        return field_195635_a.get(entity.getType());
    }
}

/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.RabbitModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class RabbitRenderer
extends MobRenderer<RabbitEntity, RabbitModel<RabbitEntity>> {
    private static final ResourceLocation BROWN = new ResourceLocation("textures/entity/rabbit/brown.png");
    private static final ResourceLocation WHITE = new ResourceLocation("textures/entity/rabbit/white.png");
    private static final ResourceLocation BLACK = new ResourceLocation("textures/entity/rabbit/black.png");
    private static final ResourceLocation GOLD = new ResourceLocation("textures/entity/rabbit/gold.png");
    private static final ResourceLocation SALT = new ResourceLocation("textures/entity/rabbit/salt.png");
    private static final ResourceLocation WHITE_SPLOTCHED = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
    private static final ResourceLocation TOAST = new ResourceLocation("textures/entity/rabbit/toast.png");
    private static final ResourceLocation CAERBANNOG = new ResourceLocation("textures/entity/rabbit/caerbannog.png");

    public RabbitRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new RabbitModel(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(RabbitEntity rabbitEntity) {
        String string = TextFormatting.getTextWithoutFormattingCodes(rabbitEntity.getName().getString());
        if (string != null && "Toast".equals(string)) {
            return TOAST;
        }
        switch (rabbitEntity.getRabbitType()) {
            default: {
                return BROWN;
            }
            case 1: {
                return WHITE;
            }
            case 2: {
                return BLACK;
            }
            case 3: {
                return WHITE_SPLOTCHED;
            }
            case 4: {
                return GOLD;
            }
            case 5: {
                return SALT;
            }
            case 99: 
        }
        return CAERBANNOG;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((RabbitEntity)entity2);
    }
}


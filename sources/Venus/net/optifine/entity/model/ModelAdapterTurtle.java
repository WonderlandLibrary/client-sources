/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TurtleRenderer;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.TurtleModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;
import net.optifine.reflect.Reflector;

public class ModelAdapterTurtle
extends ModelAdapterQuadruped {
    public ModelAdapterTurtle() {
        super(EntityType.TURTLE, "turtle", 0.7f);
    }

    @Override
    public Model makeModel() {
        return new TurtleModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof QuadrupedModel)) {
            return null;
        }
        TurtleModel turtleModel = (TurtleModel)model;
        return string.equals("body2") ? (ModelRenderer)Reflector.ModelTurtle_body2.getValue(turtleModel) : super.getModelRenderer(model, string);
    }

    @Override
    public String[] getModelRendererNames() {
        Object[] objectArray = super.getModelRendererNames();
        return (String[])Config.addObjectToArray(objectArray, "body2");
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        TurtleRenderer turtleRenderer = new TurtleRenderer(entityRendererManager);
        turtleRenderer.entityModel = (TurtleModel)model;
        turtleRenderer.shadowSize = f;
        return turtleRenderer;
    }
}


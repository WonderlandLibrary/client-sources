/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.anim.IModelResolver;
import net.optifine.entity.model.anim.IRenderResolver;
import net.optifine.entity.model.anim.ModelVariableFloat;
import net.optifine.entity.model.anim.ModelVariableType;
import net.optifine.entity.model.anim.RenderResolverEntity;
import net.optifine.entity.model.anim.RenderResolverTileEntity;
import net.optifine.expr.IExpression;
import net.optifine.util.Either;

public class ModelResolver
implements IModelResolver {
    private ModelAdapter modelAdapter;
    private Model model;
    private CustomModelRenderer[] customModelRenderers;
    private ModelRenderer thisModelRenderer;
    private ModelRenderer partModelRenderer;
    private IRenderResolver renderResolver;

    public ModelResolver(ModelAdapter modelAdapter, Model model, CustomModelRenderer[] customModelRendererArray) {
        this.modelAdapter = modelAdapter;
        this.model = model;
        this.customModelRenderers = customModelRendererArray;
        Either<EntityType, TileEntityType> either = modelAdapter.getType();
        this.renderResolver = either.getRight().isPresent() ? new RenderResolverTileEntity() : new RenderResolverEntity();
    }

    @Override
    public IExpression getExpression(String string) {
        ModelVariableFloat modelVariableFloat = this.getModelVariable(string);
        if (modelVariableFloat != null) {
            return modelVariableFloat;
        }
        IExpression iExpression = this.renderResolver.getParameter(string);
        return iExpression != null ? iExpression : null;
    }

    @Override
    public ModelRenderer getModelRenderer(String string) {
        if (string == null) {
            return null;
        }
        if (string.indexOf(":") >= 0) {
            String[] stringArray = Config.tokenize(string, ":");
            ModelRenderer modelRenderer = this.getModelRenderer(stringArray[0]);
            for (int i = 1; i < stringArray.length; ++i) {
                String string2 = stringArray[i];
                ModelRenderer modelRenderer2 = modelRenderer.getChildDeep(string2);
                if (modelRenderer2 == null) {
                    return null;
                }
                modelRenderer = modelRenderer2;
            }
            return modelRenderer;
        }
        if (this.thisModelRenderer != null && string.equals("this")) {
            return this.thisModelRenderer;
        }
        if (this.partModelRenderer != null && string.equals("part")) {
            return this.partModelRenderer;
        }
        ModelRenderer modelRenderer = this.modelAdapter.getModelRenderer(this.model, string);
        if (modelRenderer != null) {
            return modelRenderer;
        }
        for (int i = 0; i < this.customModelRenderers.length; ++i) {
            CustomModelRenderer customModelRenderer = this.customModelRenderers[i];
            ModelRenderer modelRenderer3 = customModelRenderer.getModelRenderer();
            if (string.equals(modelRenderer3.getId())) {
                return modelRenderer3;
            }
            ModelRenderer modelRenderer4 = modelRenderer3.getChildDeep(string);
            if (modelRenderer4 == null) continue;
            return modelRenderer4;
        }
        return null;
    }

    @Override
    public ModelVariableFloat getModelVariable(String string) {
        String[] stringArray = Config.tokenize(string, ".");
        if (stringArray.length != 2) {
            return null;
        }
        String string2 = stringArray[0];
        String string3 = stringArray[5];
        ModelRenderer modelRenderer = this.getModelRenderer(string2);
        if (modelRenderer == null) {
            return null;
        }
        ModelVariableType modelVariableType = ModelVariableType.parse(string3);
        return modelVariableType == null ? null : new ModelVariableFloat(string, modelRenderer, modelVariableType);
    }

    public void setPartModelRenderer(ModelRenderer modelRenderer) {
        this.partModelRenderer = modelRenderer;
    }

    public void setThisModelRenderer(ModelRenderer modelRenderer) {
        this.thisModelRenderer = modelRenderer;
    }
}


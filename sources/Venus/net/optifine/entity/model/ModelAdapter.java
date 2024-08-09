/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.ArrayList;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.util.Either;

public abstract class ModelAdapter {
    private Either<EntityType, TileEntityType> type;
    private String name;
    private float shadowSize;
    private String[] aliases;

    public ModelAdapter(EntityType entityType, String string, float f) {
        this(Either.makeLeft(entityType), string, f, (String[])null);
    }

    public ModelAdapter(EntityType entityType, String string, float f, String[] stringArray) {
        this(Either.makeLeft(entityType), string, f, stringArray);
    }

    public ModelAdapter(TileEntityType tileEntityType, String string, float f) {
        this(Either.makeRight(tileEntityType), string, f, (String[])null);
    }

    public ModelAdapter(TileEntityType tileEntityType, String string, float f, String[] stringArray) {
        this(Either.makeRight(tileEntityType), string, f, stringArray);
    }

    public ModelAdapter(Either<EntityType, TileEntityType> either, String string, float f, String[] stringArray) {
        this.type = either;
        this.name = string;
        this.shadowSize = f;
        this.aliases = stringArray;
    }

    public Either<EntityType, TileEntityType> getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public float getShadowSize() {
        return this.shadowSize;
    }

    public abstract Model makeModel();

    public abstract ModelRenderer getModelRenderer(Model var1, String var2);

    public abstract String[] getModelRendererNames();

    public abstract IEntityRenderer makeEntityRender(Model var1, float var2);

    public ModelRenderer[] getModelRenderers(Model model) {
        String[] stringArray = this.getModelRendererNames();
        ArrayList<ModelRenderer> arrayList = new ArrayList<ModelRenderer>();
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            ModelRenderer modelRenderer = this.getModelRenderer(model, string);
            if (modelRenderer == null) continue;
            arrayList.add(modelRenderer);
        }
        return arrayList.toArray(new ModelRenderer[arrayList.size()]);
    }
}


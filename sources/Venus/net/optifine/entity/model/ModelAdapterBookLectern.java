/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.LecternTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterBookLectern
extends ModelAdapter {
    public ModelAdapterBookLectern() {
        super(TileEntityType.LECTERN, "lectern_book", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new BookModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BookModel)) {
            return null;
        }
        BookModel bookModel = (BookModel)model;
        if (string.equals("cover_right")) {
            return (ModelRenderer)Reflector.ModelBook_ModelRenderers.getValue(bookModel, 0);
        }
        if (string.equals("cover_left")) {
            return (ModelRenderer)Reflector.ModelBook_ModelRenderers.getValue(bookModel, 1);
        }
        if (string.equals("pages_right")) {
            return (ModelRenderer)Reflector.ModelBook_ModelRenderers.getValue(bookModel, 2);
        }
        if (string.equals("pages_left")) {
            return (ModelRenderer)Reflector.ModelBook_ModelRenderers.getValue(bookModel, 3);
        }
        if (string.equals("flipping_page_right")) {
            return (ModelRenderer)Reflector.ModelBook_ModelRenderers.getValue(bookModel, 4);
        }
        if (string.equals("flipping_page_left")) {
            return (ModelRenderer)Reflector.ModelBook_ModelRenderers.getValue(bookModel, 5);
        }
        return string.equals("book_spine") ? (ModelRenderer)Reflector.ModelBook_ModelRenderers.getValue(bookModel, 6) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"cover_right", "cover_left", "pages_right", "pages_left", "flipping_page_right", "flipping_page_left", "book_spine"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        TileEntityRenderer tileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.LECTERN);
        if (!(tileEntityRenderer instanceof LecternTileEntityRenderer)) {
            return null;
        }
        if (tileEntityRenderer.getType() == null) {
            tileEntityRenderer = new LecternTileEntityRenderer(tileEntityRendererDispatcher);
        }
        if (!Reflector.TileEntityLecternRenderer_modelBook.exists()) {
            Config.warn("Field not found: TileEntityLecternRenderer.modelBook");
            return null;
        }
        Reflector.setFieldValue(tileEntityRenderer, Reflector.TileEntityLecternRenderer_modelBook, model);
        return tileEntityRenderer;
    }
}


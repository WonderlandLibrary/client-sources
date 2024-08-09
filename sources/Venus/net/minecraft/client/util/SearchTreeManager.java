/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;

public class SearchTreeManager
implements IResourceManagerReloadListener {
    public static final Key<ItemStack> ITEMS = new Key();
    public static final Key<ItemStack> TAGS = new Key();
    public static final Key<RecipeList> RECIPES = new Key();
    private final Map<Key<?>, IMutableSearchTree<?>> trees = Maps.newHashMap();

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        for (IMutableSearchTree<?> iMutableSearchTree : this.trees.values()) {
            iMutableSearchTree.recalculate();
        }
    }

    public <T> void add(Key<T> key, IMutableSearchTree<T> iMutableSearchTree) {
        this.trees.put(key, iMutableSearchTree);
    }

    public <T> IMutableSearchTree<T> get(Key<T> key) {
        return this.trees.get(key);
    }

    public static class Key<T> {
    }
}


package xyz.mathax.mathaxclient.systems.modules;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.renderer.packer.GuiTexture;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class Category {
    public final String name;

    public final Pair<Identifier, Item> icons;

    public final Color color;

    private final int nameHash;

    public Category(String name, Pair<Identifier, Item> icons, Color color) {
        this.name = name;
        this.icons = icons;
        this.color = color;
        this.nameHash = name.hashCode();
    }

    public Pair<GuiTexture, Item> getIcons() {
        for (var pair : GuiRenderer.CATEGORIES) {
            if (name.startsWith(pair.getLeft())) {
                return new Pair<>(pair.getRight(), icons.getRight());
            }
        }

        return new Pair<>(GuiRenderer.addTexture(icons.getLeft(), name), icons.getRight());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Category category = (Category) object;
        return nameHash == category.nameHash;
    }

    @Override
    public int hashCode() {
        return nameHash;
    }
}

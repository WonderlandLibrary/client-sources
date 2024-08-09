package net.minecraft.client.gui.recipebook;

import net.minecraft.item.Item;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Set;

public class BlastFurnaceRecipeGui extends AbstractRecipeBookGui
{
    private static final ITextComponent field_243409_i = new TranslationTextComponent("gui.recipebook.toggleRecipes.blastable");

    protected ITextComponent func_230479_g_()
    {
        return field_243409_i;
    }

    protected Set<Item> func_212958_h()
    {
        return AbstractFurnaceTileEntity.getBurnTimes().keySet();
    }
}

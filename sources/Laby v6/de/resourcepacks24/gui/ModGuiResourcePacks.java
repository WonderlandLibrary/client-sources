package de.resourcepacks24.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiResourcePackList;
import net.minecraft.client.resources.ResourcePackListEntry;

public class ModGuiResourcePacks extends GuiResourcePackList
{
    private String title;

    public ModGuiResourcePacks(Minecraft mcIn, int p_i45054_2_, int p_i45054_3_, List<ResourcePackListEntry> p_i45054_4_)
    {
        super(mcIn, p_i45054_2_, p_i45054_3_, p_i45054_4_);
    }

    protected String getListHeader()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}

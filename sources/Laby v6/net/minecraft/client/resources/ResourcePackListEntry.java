package net.minecraft.client.resources;

import de.resourcepacks24.main.Pack;
import de.resourcepacks24.main.ResourcePacks24;
import de.resourcepacks24.utils.Color;
import de.resourcepacks24.utils.DrawUtils;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry
{
    private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
    private static final IChatComponent field_183020_d = new ChatComponentTranslation("resourcePack.incompatible", new Object[0]);
    private static final IChatComponent field_183021_e = new ChatComponentTranslation("resourcePack.incompatible.old", new Object[0]);
    private static final IChatComponent field_183022_f = new ChatComponentTranslation("resourcePack.incompatible.new", new Object[0]);
    protected final Minecraft mc;
    protected final GuiScreenResourcePacks resourcePacksGUI;
    ItemStack itemIron = new ItemStack(Item.getItemById(265));
    ItemStack itemGold = new ItemStack(Item.getItemById(266));
    ItemStack itemDiamond = new ItemStack(Item.getItemById(264));
    ItemStack itemBarrier = new ItemStack(Item.getItemById(166));
    private int listWidth = 0;
    private Pack pack;
    private int hoverAnimation = 0;
    private boolean folder = false;

    public void setFolder(boolean p_setFolder_1_)
    {
        this.folder = p_setFolder_1_;
    }

    public ResourcePackListEntry(GuiScreenResourcePacks resourcePacksGUIIn)
    {
        this.resourcePacksGUI = resourcePacksGUIIn;
        this.mc = Minecraft.getMinecraft();
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
    {
        int i = this.func_183019_a();
        this.listWidth = listWidth;

        if (this.pack != null)
        {
            ResourcePacks24.getInstance().getDraw();
            DrawUtils.drawRect(x + 32, y, ResourcePacks24.getInstance().getDraw().getWidth() - 30, y + 32, Color.toRGB(this.hoverAnimation, this.hoverAnimation, this.hoverAnimation, this.hoverAnimation));
        }

        if (this.pack != null && ResourcePacks24.getInstance().getSelectedPack() == this.pack)
        {
            ResourcePacks24.getInstance().getDraw();
            DrawUtils.drawRect(x, y, ResourcePacks24.getInstance().getDraw().getWidth() - 30, y + 32, -2147470229);
        }

        if (this.pack != null)
        {
            if (y > ResourcePacks24.getInstance().getDraw().getHeight() || y < 0)
            {
                return;
            }

            if (this.pack.getPremiumId() != 0)
            {
                int j = ResourcePacks24.getInstance().getDraw().getStringWidth(this.pack.getCreator() + " " + this.pack.getBestPossibleName());
                GL11.glPushMatrix();
                GL11.glScaled(0.5D, 0.5D, 0.5D);
                ItemStack itemstack = this.itemIron;

                switch (this.pack.getPremiumId())
                {
                    case 2:
                        itemstack = this.itemGold;
                        break;

                    case 3:
                        itemstack = this.itemDiamond;
                }

                ResourcePacks24.getInstance().getDraw().drawItem(itemstack, (int)((double)(x + 55 + j) / 0.5D), (int)((double)(y + 1) / 0.5D));
                GL11.glPopMatrix();
            }

            ResourcePacks24.getInstance().getDraw();
            DrawUtils.drawRect(x + 32, y, listWidth + 80, y + 32, 441470000);
        }
        else
        {
            ResourcePacks24.getInstance().getDraw();
            DrawUtils.drawRect(x + 32, y, x + listWidth - 5, y + 32, Color.toRGB(this.hoverAnimation, this.hoverAnimation, this.hoverAnimation, this.hoverAnimation));
            boolean flag = false;
            ResourcePackRepository.Entry resourcepackrepository$entry1 = null;

            for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mc.getResourcePackRepository().getRepositoryEntries())
            {
                if (resourcepackrepository$entry.getResourcePackName().equals(this.getTitle()))
                {
                    flag = true;
                    break;
                }
            }

            for (ResourcePackRepository.Entry resourcepackrepository$entry2 : this.mc.getResourcePackRepository().getRepositoryEntriesAll())
            {
                if (resourcepackrepository$entry2.getResourcePackName().equals(this.getTitle()))
                {
                    break;
                }
            }

            if (!this.resourcePacksGUI.getSelectedResourcePacks().contains(this) && !flag && !this.folder)
            {
                if (mouseX > x + 183 - 5 && mouseX < x + 183 + 10 && mouseY > y - 5 && mouseY < y + 10)
                {
                    GL11.glPushMatrix();
                    GL11.glScaled(0.7D, 0.7D, 0.7D);
                    ResourcePacks24.getInstance().getDraw().drawItem(this.itemBarrier, (int)((double)(x + 183 - 1) / 0.7D), (int)((double)(y - 1) / 0.7D));
                    GL11.glPopMatrix();
                }
                else
                {
                    GL11.glPushMatrix();
                    GL11.glScaled(0.5D, 0.5D, 0.5D);
                    ResourcePacks24.getInstance().getDraw().drawItem(this.itemBarrier, (int)((double)(x + 183) / 0.5D), (int)((double)y / 0.5D));
                    GL11.glPopMatrix();
                }
            }
        }

        if (i != 1)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawRect(x - 1, y - 1, x + listWidth - 9, y + slotHeight + 1, -8978432);
        }

        this.func_148313_c();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (this.pack == null)
        {
            if (this.folder)
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture(ResourcePacks24.RESOURCE_FOLDER);
            }

            Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        }
        else
        {
            ResourcePacks24.getInstance().getDraw();
            DrawUtils.drawRect(x, y, x + 32, y + 32, Integer.MIN_VALUE);
            ResourcePacks24.getInstance().getDraw().drawCenteredString("?", (double)(x + 16), (double)(y + 7), 2.0D);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            this.pack.drawImage(x, y, 32, 32, 0.13D);
        }

        String s = this.func_148312_b();
        String s1 = this.func_148311_a();

        if ((this.mc.gameSettings.touchscreen || isSelected) && this.func_148310_d())
        {
            this.hoverAnimation += 18;

            if (this.hoverAnimation > 100)
            {
                this.hoverAnimation = 100;
            }
        }
        else
        {
            this.hoverAnimation -= 18;

            if (this.hoverAnimation < 0)
            {
                this.hoverAnimation = 0;
            }
        }

        if ((this.mc.gameSettings.touchscreen || isSelected) && this.func_148310_d())
        {
            this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);

            if (this.pack == null)
            {
                Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int i1 = mouseX - x;
            int k1 = mouseY - y;

            if (i < 1)
            {
                s = field_183020_d.getFormattedText();
                s1 = field_183021_e.getFormattedText();
            }
            else if (i > 1)
            {
                s = field_183020_d.getFormattedText();
                s1 = field_183022_f.getFormattedText();
            }

            if (this.folder)
            {
                ResourcePacks24.getInstance().getDraw().drawCenteredString(this.getTitle().equals("..") ? "BACK" : "OPEN", (double)(x + 16), (double)(y + 12), 1.0D);
            }

            if (this.pack == null && !this.folder)
            {
                if (this.func_148309_e())
                {
                    if (i1 < 32)
                    {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    }
                    else
                    {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }
                else
                {
                    if (this.func_148308_f())
                    {
                        if (i1 < 16)
                        {
                            Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                        }
                        else
                        {
                            Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                        }
                    }

                    if (this.func_148314_g())
                    {
                        if (i1 < 32 && i1 > 16 && k1 < 16)
                        {
                            Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                        }
                        else
                        {
                            Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                        }
                    }

                    if (this.func_148307_h())
                    {
                        if (i1 < 32 && i1 > 16 && k1 > 16)
                        {
                            Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                        }
                        else
                        {
                            Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                        }
                    }
                }
            }
        }

        int j1 = this.mc.fontRendererObj.getStringWidth(s);
        int l1 = 157;

        if (this.pack != null)
        {
            l1 = listWidth - 150;
        }

        if (j1 > l1)
        {
            s = this.mc.fontRendererObj.trimStringToWidth(s, 157 - this.mc.fontRendererObj.getStringWidth("...")) + "...";
        }

        this.mc.fontRendererObj.drawStringWithShadow(s, (float)(x + 32 + 2), (float)(y + 1), 16777215);
        List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(s1, l1);

        if (this.pack == null)
        {
            for (int k = 0; k < 2 && k < list.size(); ++k)
            {
                this.mc.fontRendererObj.drawStringWithShadow((String)list.get(k), (float)(x + 32 + 2), (float)(y + 12 + 10 * k), 8421504);
            }
        }
        else
        {
            GL11.glPushMatrix();
            double d0 = 0.6D;
            GL11.glScaled(d0, d0, d0);

            for (int l = 0; l < 2 && l < list.size(); ++l)
            {
                this.mc.fontRendererObj.drawStringWithShadow((String)list.get(l), (float)((double)(x + 32 + 2) / d0), (float)((double)(y + 12 + 10 * l) / d0), 8421504);
            }

            GL11.glPopMatrix();
            ResourcePacks24.getInstance().getDraw().drawRightString(Color.cl("a") + this.pack.getDownloads() + " Downloads", (double)((float)(x + listWidth - 15)), (double)((float)(y + 1)));
            ResourcePacks24.getInstance().getDraw().drawRightString(Color.cl("7") + ResourcePacks24.simpleDateFormat.format(Long.valueOf(this.pack.getUpTime() * 1000L)) + "", (double)((float)(x + listWidth - 15)), (double)((float)(y + 12)));
            ResourcePacks24.getInstance().getDraw().drawRightString(Color.cl("b") + this.pack.getVotes() + " Votes", (double)((float)(x + listWidth - 15)), (double)((float)(y + 22)));
            ResourcePacks24.getInstance().getDraw();
            DrawUtils.drawRect(x, y + 25, x + 32, y + 32, Integer.MIN_VALUE);
            ResourcePacks24.getInstance().getDraw().drawString(Color.cl("f") + Color.cl("f") + this.pack.getSize(), (double)((float)(x + 1)), (double)((float)(y + 27)), 0.6D);
        }
    }

    public void setPackInfo(Pack p_setPackInfo_1_)
    {
        this.pack = p_setPackInfo_1_;
    }

    protected abstract int func_183019_a();

    protected abstract String func_148311_a();

    protected abstract String func_148312_b();

    public String getTitle()
    {
        return this.func_148312_b();
    }

    protected abstract void func_148313_c();

    protected boolean func_148310_d()
    {
        return true;
    }

    protected boolean func_148309_e()
    {
        return !this.resourcePacksGUI.hasResourcePackEntry(this);
    }

    protected boolean func_148308_f()
    {
        return this.resourcePacksGUI.hasResourcePackEntry(this);
    }

    protected boolean func_148314_g()
    {
        List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
        int i = list.indexOf(this);
        return i > 0 && ((ResourcePackListEntry)list.get(i - 1)).func_148310_d();
    }

    protected boolean func_148307_h()
    {
        List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
        int i = list.indexOf(this);
        return i >= 0 && i < list.size() - 1 && ((ResourcePackListEntry)list.get(i + 1)).func_148310_d();
    }

    /**
     * Returns true if the mouse has been pressed on this control.
     */
    public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
    {
        if (this.pack != null)
        {
            ResourcePacks24.getInstance().setSelectedPack(this.pack);
            return true;
        }
        else if (this.folder)
        {
            if (this.getTitle().equals(".."))
            {
                ResourcePacks24.getInstance().setPath("");
                this.resourcePacksGUI.refreshPackList();
            }
            else
            {
                for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntriesAll())
                {
                    if (resourcepackrepository$entry1.getDirPath().equals(this.getTitle()))
                    {
                        ResourcePacks24.getInstance().setPath(resourcepackrepository$entry1.getDirPath());
                        this.resourcePacksGUI.refreshPackList();
                        break;
                    }
                }
            }

            return true;
        }
        else
        {
            boolean flag = false;

            for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mc.getResourcePackRepository().getRepositoryEntries())
            {
                if (resourcepackrepository$entry.getResourcePackName().equals(this.getTitle()))
                {
                    flag = true;
                    break;
                }
            }

            if (p_148278_5_ > 178 && p_148278_5_ < 193 && p_148278_6_ > 0 && p_148278_6_ < 10 && !this.resourcePacksGUI.getSelectedResourcePacks().contains(this) && !flag)
            {
                this.mc.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
                {
                    public void confirmClicked(boolean result, int id)
                    {
                        if (result)
                        {
                            File file1 = Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks();
                            File file2 = new File(file1.getAbsolutePath(), ResourcePackListEntry.this.getTitle());

                            if (file2.delete())
                            {
                                System.out.println("[Resourcepack24] Deleted " + ResourcePackListEntry.this.getTitle() + " successfully!");
                            }
                            else
                            {
                                ResourcePacks24.getInstance().getDeletedPacks().add(ResourcePackListEntry.this.getTitle());
                                System.out.println("[Resourcepack24] Can\'t delete " + ResourcePackListEntry.this.getTitle() + ", try again later..");
                            }
                        }

                        ResourcePackListEntry.this.mc.displayGuiScreen(new GuiScreenResourcePacks(ResourcePackListEntry.this.resourcePacksGUI.getParentScreen()));
                    }
                }, Color.cl("c") + "Delete this pack?", this.getTitle(), 0));
                return true;
            }
            else
            {
                if (this.func_148310_d() && p_148278_5_ <= 32)
                {
                    if (this.func_148309_e())
                    {
                        this.resourcePacksGUI.markChanged();
                        int i = this.func_183019_a();

                        if (i != 1)
                        {
                            String s1 = I18n.format("resourcePack.incompatible.confirm.title", new Object[0]);
                            String s = I18n.format("resourcePack.incompatible.confirm." + (i > 1 ? "new" : "old"), new Object[0]);
                            this.mc.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
                            {
                                public void confirmClicked(boolean result, int id)
                                {
                                    List<ResourcePackListEntry> list2 = ResourcePackListEntry.this.resourcePacksGUI.getListContaining(ResourcePackListEntry.this);
                                    ResourcePackListEntry.this.mc.displayGuiScreen(ResourcePackListEntry.this.resourcePacksGUI);

                                    if (result)
                                    {
                                        list2.remove(ResourcePackListEntry.this);
                                        ResourcePackListEntry.this.resourcePacksGUI.getSelectedResourcePacks().add(0, ResourcePackListEntry.this);
                                    }
                                }
                            }, s1, s, 0));
                        }
                        else
                        {
                            this.resourcePacksGUI.getListContaining(this).remove(this);
                            this.resourcePacksGUI.getSelectedResourcePacks().add(0, this);
                        }

                        return true;
                    }

                    if (p_148278_5_ < 16 && this.func_148308_f())
                    {
                        this.resourcePacksGUI.getListContaining(this).remove(this);
                        this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
                        this.resourcePacksGUI.markChanged();
                        return true;
                    }

                    if (p_148278_5_ > 16 && p_148278_6_ < 16 && this.func_148314_g())
                    {
                        List<ResourcePackListEntry> list1 = this.resourcePacksGUI.getListContaining(this);
                        int k = list1.indexOf(this);
                        list1.remove(this);
                        list1.add(k - 1, this);
                        this.resourcePacksGUI.markChanged();
                        return true;
                    }

                    if (p_148278_5_ > 16 && p_148278_6_ > 16 && this.func_148307_h())
                    {
                        List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
                        int j = list.indexOf(this);
                        list.remove(this);
                        list.add(j + 1, this);
                        this.resourcePacksGUI.markChanged();
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_)
    {
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
    }
}

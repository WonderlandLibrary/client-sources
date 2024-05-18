package mods.accountmanager.gui;

import java.io.IOException;
import mods.accountmanager.AccountManager;
import mods.accountmanager.utils.FancyGuiListExtended;
import mods.accountmanager.utils.FancyGuiSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class AccountEntry implements FancyGuiListExtended.IGuiListEntry
{
    private final Minecraft mc = Minecraft.getMinecraft();
    private String field_148299_g;
    private long field_148298_f;
    private static final String __OBFID = "CL_00000817";
    private String name;
    private FancyGuiSlot parentList;
    private AccountManagerGUI parent;

    protected AccountEntry(AccountManagerGUI parent, String name, FancyGuiSlot parentList)
    {
        this.name = name;
        this.parent = parent;
        this.parentList = parentList;
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
    {
        int i = y + 3;

        if (i - 3 >= this.parentList.top && i + 13 <= this.parentList.bottom)
        {
            this.drawPlayerHead(StringUtils.stripControlCodes(this.name), (double)(x + 1), (double)(i + 1), 0.5D);
            String s = this.name;

            if (Minecraft.getMinecraft().getSession().getUsername().equals(s))
            {
                s = "\u00a7a" + s;
            }

            this.mc.fontRendererObj.drawString(s, x + 30, i + 2, 16777215);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void drawPlayerHead(String player, double x, double y, double size)
    {
        if (AccountManager.playerHeads.containsKey(player))
        {
            GL11.glPushMatrix();
            GL11.glScaled(size, size, size);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture((ResourceLocation)AccountManager.playerHeads.get(player));
            AccountManager.drawTexturedModalRect(x / size, (y - 3.0D) / size, 32.0D, 32.0D, 32.0D, 32.0D);
            AccountManager.drawTexturedModalRect(x / size, (y - 3.0D) / size, 160.0D, 32.0D, 32.0D, 32.0D);
            GL11.glPopMatrix();
        }
    }

    private boolean func_178013_b()
    {
        return true;
    }

    public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
    {
        this.parent.getList().setSelected(slotIndex);

        for (GuiButton guibutton : this.parent.getButtonList())
        {
            if (guibutton.id == 3 || guibutton.id == 4)
            {
                guibutton.enabled = true;
            }
        }

        if (Minecraft.getSystemTime() - this.field_148298_f <= 1000L)
        {
            GuiButton guibutton2 = null;

            for (GuiButton guibutton1 : this.parent.getButtonList())
            {
                if (guibutton1.id == 3)
                {
                    guibutton2 = guibutton1;
                    break;
                }
            }

            try
            {
                this.parent.actionPerformed(guibutton2);
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }

        this.field_148298_f = Minecraft.getSystemTime();
        return false;
    }

    public String getName()
    {
        return this.name;
    }

    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_)
    {
    }

    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
    }
}

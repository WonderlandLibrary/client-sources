package viamcp.gui;

import club.bluezenith.ui.GuiViaMCPCredits;
import club.bluezenith.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

import java.io.IOException;

import static club.bluezenith.util.font.FontUtil.vkMedium35;
import static net.minecraft.util.EnumChatFormatting.GRAY;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static viamcp.ViaMCP.getInstance;
import static viamcp.protocols.ProtocolCollection.getProtocolById;
import static viamcp.protocols.ProtocolCollection.values;

public class GuiProtocolSelector extends GuiScreen
{
    public static float sliderDragValue = -1.0f;

    boolean allow;
    private GuiScreen parent;
    public SlotList list;

    public GuiProtocolSelector(GuiScreen parent)
    {
        this.parent = parent;
        allow = true;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(new GuiButton(1, width / 2 - 100, height - 25, 200, 20, "Back"));
        buttonList.add(new GuiButton(2, 5, height - 25, 95, 20, "Credits"));
        list = new SlotList(mc, width, height, 32, height - 32, 10);
    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException
    {
        list.actionPerformed(p_actionPerformed_1_);

        if (p_actionPerformed_1_.id == 1)
        {
            mc.displayGuiScreen(parent);
        } else if(p_actionPerformed_1_.id == 2) {
            mc.displayGuiScreen(new GuiViaMCPCredits(this));
        }
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        list.handleMouseInput();
        super.handleMouseInput();
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_)
    {
        list.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    //    GlStateManager.pushMatrix();
        FontUtil.vkDemibold.drawString("Select version", this.width/2f - FontUtil.vkDemibold.getStringWidthF("Select version")/2f, 5, -1, true);
        // this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD + "ViaMCP Reborn", this.width / 4, 5, 16777215);
    //    drawString(this.fontRendererObj, "Maintained by Hideri (1.8.x Version)", 3, 3, -1);
    //    drawString(this.fontRendererObj, "Credits", 3, (this.height - 15) * 2, -1);
    //    drawString(this.fontRendererObj, "ViaForge: https://github.com/FlorianMichael/ViaForge", 3, (this.height - 10) * 2, -1);
    //    drawString(this.fontRendererObj, "Original ViaMCP: https://github.com/LaVache-FR/ViaMCP", 3, (this.height - 5) * 2, -1);
    //     GlStateManager.popMatrix();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }

    class SlotList extends GuiSlot
    {
        public SlotList(Minecraft p_i1052_1_, int p_i1052_2_, int p_i1052_3_, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_)
        {
            super(p_i1052_1_, p_i1052_2_, p_i1052_3_, p_i1052_4_, p_i1052_5_, 17);
        }

        @Override
        protected int getSize()
        {
            return values().length;
        }

        @Override
        protected void elementClicked(int i, boolean b, int i1, int i2)
        {
            getInstance().setVersion(values()[i].getVersion().getVersion());
        }

        @Override
        protected boolean isSelected(int i)
        {
            return false;
        }

        @Override
        protected void drawBackground()
        {
            drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5)
        {
            final String toDraw = (getInstance().getVersion() == values()[i].getVersion().getVersion() ? GREEN.toString() : GRAY.toString()) + getProtocolById(values()[i].getVersion().getVersion()).getName();
            vkMedium35.drawString(toDraw, this.width/2f - vkMedium35.getStringWidthF(toDraw)/2f + 0.01F, i2, -1);
     //       drawCenteredString(mc.fontRendererObj,toDraw, width / 2, i2 + 2, -1);
        }
    }
}

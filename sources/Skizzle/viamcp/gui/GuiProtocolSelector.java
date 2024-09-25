/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package viamcp.gui;

import com.github.creeper123123321.viafabric.ViaFabric;
import com.github.creeper123123321.viafabric.util.ProtocolUtils;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import viamcp.utils.ProtocolSorter;

public class GuiProtocolSelector
extends GuiScreen {
    public SlotList list;
    private GuiScreen parent;

    public GuiProtocolSelector(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 27, 200, 20, "Back"));
        this.list = new SlotList(this.mc, this.width, this.height, 32, this.height - 32, 10);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        this.list.actionPerformed(button);
        if (button.id == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        this.list.handleMouseInput();
        super.handleMouseInput();
    }

    @Override
    public void drawScreen(int drawScreen, int mouseX, float mouseY) {
        this.list.drawScreen(drawScreen, mouseX, mouseY);
        GL11.glPushMatrix();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        this.drawCenteredString(this.fontRendererObj, String.valueOf(EnumChatFormatting.BOLD.toString()) + "Version Selector", this.width / 4, 6.0f, 0xFFFFFF);
        GL11.glPopMatrix();
        super.drawScreen(drawScreen, mouseX, mouseY);
    }

    class SlotList
    extends GuiSlot {
        public SlotList(Minecraft p_i1052_1_, int p_i1052_2_, int p_i1052_3_, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_) {
            super(p_i1052_1_, p_i1052_2_, p_i1052_3_, p_i1052_4_, p_i1052_5_, p_i1052_6_);
        }

        @Override
        protected int getSize() {
            return ProtocolSorter.getProtocolVersions().size();
        }

        @Override
        protected void elementClicked(int i, boolean b, int i1, int i2) {
            ViaFabric.clientSideVersion = ProtocolSorter.getProtocolVersions().get(i).getVersion();
        }

        @Override
        protected boolean isSelected(int i) {
            return false;
        }

        @Override
        protected void drawBackground() {
            GuiProtocolSelector.this.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5) {
            int color = -1;
            if (ViaFabric.clientSideVersion == ProtocolSorter.getProtocolVersions().get(i).getVersion()) {
                color = -16740097;
            }
            GuiProtocolSelector.this.drawCenteredString(this.mc.fontRendererObj, ProtocolUtils.getProtocolName(ProtocolSorter.getProtocolVersions().get(i).getVersion()), this.width / 2, i2 + 2, color);
        }
    }
}


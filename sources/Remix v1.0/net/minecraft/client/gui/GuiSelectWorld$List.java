package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.world.storage.*;
import org.apache.commons.lang3.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;

class List extends GuiSlot
{
    public List(final Minecraft mcIn) {
        super(mcIn, GuiScreen.width, GuiScreen.height, 32, GuiScreen.height - 64, 36);
    }
    
    @Override
    protected int getSize() {
        return GuiSelectWorld.access$000(GuiSelectWorld.this).size();
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        GuiSelectWorld.access$102(GuiSelectWorld.this, slotIndex);
        final boolean var5 = GuiSelectWorld.access$100(GuiSelectWorld.this) >= 0 && GuiSelectWorld.access$100(GuiSelectWorld.this) < this.getSize();
        GuiSelectWorld.access$200(GuiSelectWorld.this).enabled = var5;
        GuiSelectWorld.access$300(GuiSelectWorld.this).enabled = var5;
        GuiSelectWorld.access$400(GuiSelectWorld.this).enabled = var5;
        GuiSelectWorld.access$500(GuiSelectWorld.this).enabled = var5;
        if (isDoubleClick && var5) {
            GuiSelectWorld.this.func_146615_e(slotIndex);
        }
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return slotIndex == GuiSelectWorld.access$100(GuiSelectWorld.this);
    }
    
    @Override
    protected int getContentHeight() {
        return GuiSelectWorld.access$000(GuiSelectWorld.this).size() * 36;
    }
    
    @Override
    protected void drawBackground() {
        GuiSelectWorld.this.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        final SaveFormatComparator var7 = GuiSelectWorld.access$000(GuiSelectWorld.this).get(p_180791_1_);
        String var8 = var7.getDisplayName();
        if (StringUtils.isEmpty((CharSequence)var8)) {
            var8 = GuiSelectWorld.access$600(GuiSelectWorld.this) + " " + (p_180791_1_ + 1);
        }
        String var9 = var7.getFileName();
        var9 = var9 + " (" + GuiSelectWorld.access$700(GuiSelectWorld.this).format(new Date(var7.getLastTimePlayed()));
        var9 += ")";
        String var10 = "";
        if (var7.requiresConversion()) {
            var10 = GuiSelectWorld.access$800(GuiSelectWorld.this) + " " + var10;
        }
        else {
            var10 = GuiSelectWorld.access$900(GuiSelectWorld.this)[var7.getEnumGameType().getID()];
            if (var7.isHardcoreModeEnabled()) {
                var10 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
            }
            if (var7.getCheatsEnabled()) {
                var10 = var10 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
            }
        }
        GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, var8, p_180791_2_ + 2, p_180791_3_ + 1, 16777215);
        GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, var9, p_180791_2_ + 2, p_180791_3_ + 12, 8421504);
        GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, var10, p_180791_2_ + 2, p_180791_3_ + 12 + 10, 8421504);
    }
}

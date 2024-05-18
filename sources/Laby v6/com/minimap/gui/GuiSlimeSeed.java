package com.minimap.gui;

import com.minimap.*;
import com.minimap.settings.*;
import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import java.io.*;
import net.minecraft.client.gui.*;
import org.apache.commons.lang3.*;
import de.labystudio.modapi.*;

public class GuiSlimeSeed extends GuiSettings
{
    public GuiTextField seedTextField;
    
    public GuiSlimeSeed() {
        super(XaeroMinimap.getSettings());
        super.options = new ModOptions[] { ModOptions.SLIME_CHUNKS };
    }
    
    @Override
    public void initGui() {
        super.initGui();
        super.screenTitle = I18n.format("gui.xaero_slime_chunks", new Object[0]);
        (this.seedTextField = new GuiTextField(0, super.fontRendererObj, super.width / 2 - 100, super.height / 7 + 68, 200, 20)).setText("" + ((XaeroMinimap.getSettings().serverSlimeSeed == 0L) ? "" : XaeroMinimap.getSettings().serverSlimeSeed));
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partial) {
        super.drawScreen(mouseX, mouseY, partial);
        this.seedTextField.drawTextBox();
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_used_seed", new Object[0]), super.width / 2, super.height / 7 + 55, 16777215);
    }
    
    @Override
    public void updateScreen() {
        this.seedTextField.updateCursorCounter();
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        this.seedTextField.mouseClicked(par1, par2, par3);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) throws IOException {
        if (this.seedTextField.isFocused()) {
            this.seedTextField.textboxKeyTyped(par1, par2);
        }
        if (par2 == 28) {
            this.actionPerformed(super.buttonList.get(0));
        }
        final String s = this.seedTextField.getText();
        if (!StringUtils.isEmpty((CharSequence)s)) {
            try {
                final long j = Long.parseLong(s);
                if (j != 0L) {
                    XaeroMinimap.getSettings().serverSlimeSeed = j;
                }
            }
            catch (NumberFormatException numberformatexception) {
                XaeroMinimap.getSettings().serverSlimeSeed = s.hashCode();
            }
        }
        super.keyTyped(par1, par2);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled && par1GuiButton.id == 200) {
            super.mc.displayGuiScreen(ModAPI.getLastScreen());
        }
        super.actionPerformed(par1GuiButton);
    }
}

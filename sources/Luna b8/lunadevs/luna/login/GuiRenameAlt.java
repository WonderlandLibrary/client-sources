package lunadevs.luna.login;

import java.io.IOException;

import lunadevs.luna.gui.button.ExpandButton;
import lunadevs.luna.utils.FileUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiRenameAlt
        extends GuiScreen
{
    private final GuiAltManager manager;
    private GuiTextField nameField;
    private String status = "Waiting...";

    public GuiRenameAlt(GuiAltManager manager)
    {
        this.manager = manager;
    }

    public void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                this.mc.displayGuiScreen(this.manager);
                break;
            case 0:
                this.manager.selectedAlt.setMask(this.nameField.getText());
                this.status = "\247aRenamed!";
                FileUtils.saveAlts();
        }
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Rename Alt", this.width / 2, 10,
                -1);
        drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 20, -1);
        this.nameField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    public void initGui()
    {
        this.buttonList.add(new ExpandButton(0, this.width / 2 - 100,
                this.height / 4 + 92 + 12, "Rename"));
        this.buttonList.add(new ExpandButton(1, this.width / 2 - 100,
                this.height / 4 + 116 + 12, "Cancel"));
        this.nameField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 76,
                200, 20);
    }

    protected void keyTyped(char par1, int par2)
    {
        this.nameField.textboxKeyTyped(par1, par2);
        if ((par1 == '\t') && (this.nameField.isFocused())) {
            this.nameField.setFocused(!this.nameField.isFocused());
        }
        if (par1 == '\r') {
            actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    protected void mouseClicked(int par1, int par2, int par3)
    {
        try
        {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.nameField.mouseClicked(par1, par2, par3);
    }
}

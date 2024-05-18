package togglesneak;

import de.labystudio.modapi.ModAPI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiTSConfig extends GuiScreen
{
    private GuiButton btnToggleSneak;
    private GuiButton btnToggleSprint;
    private GuiButton btnShowHUDText;
    private GuiButton btnDoubleTap;
    private GuiButton btnFlyBoost;
    private GuiButton btnSaveSettings;
    private GuiButton btnPositionMode;
    private GuiSlideControl sliderHUDTextPosX;
    private GuiSlideControl sliderHUDTextPosY;
    private GuiSlideControl sliderFlyBoostAmount;
    private GuiButton lastPressed;
    private boolean changedShowHUD;
    private boolean changedToggleSprint;
    private int headerPos;
    private int footerPos;
    private byte byte0 = -16;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.headerPos = this.height / 4 - 52;
        this.footerPos = this.height - 29;
        this.btnToggleSneak = new GuiButton(1, this.width / 2 - 98, this.getRowPos(1), 60, 20, String.valueOf(ToggleSneakMod.optionToggleSneak));
        this.btnToggleSprint = new GuiButton(2, this.width / 2 + 102, this.getRowPos(1), 60, 20, String.valueOf(ToggleSneakMod.optionToggleSprint));
        this.btnShowHUDText = new GuiButton(3, this.width / 2 + 2, this.getRowPos(2), 60, 20, String.valueOf(ToggleSneakMod.optionShowHUDText));
        this.btnPositionMode = new GuiButton(133, this.width / 2 + 2, this.getRowPos(3), 60, 20, ToggleSneakMod.optionPositionMode);
        this.sliderHUDTextPosX = new GuiSlideControl(50, this.width / 2 + 2, this.getRowPos(4), 150, 20, "X Pos: ", 1.0F, (float)(this.width - 25), (float)ToggleSneakMod.optionHUDTextPosX, true);
        this.sliderHUDTextPosY = new GuiSlideControl(60, this.width / 2 + 2, this.getRowPos(5), 150, 20, "Y Pos: ", 1.0F, (float)(this.height - 8), (float)ToggleSneakMod.optionHUDTextPosY, true);
        this.btnDoubleTap = new GuiButton(4, this.width / 2 + 2, this.getRowPos(6), 60, 20, String.valueOf(ToggleSneakMod.optionDoubleTap));
        this.btnFlyBoost = new GuiButton(5, this.width / 2 - 113, this.getRowPos(7), 60, 20, String.valueOf(ToggleSneakMod.optionEnableFlyBoost));
        this.sliderFlyBoostAmount = new GuiSlideControl(70, this.width / 2 + 57, this.getRowPos(7), 150, 20, "x", 0.0F, 10.0F, (float)ToggleSneakMod.optionFlyBoostAmount, false);
        this.btnSaveSettings = new GuiButton(100, this.width / 2 - 30, this.footerPos, 60, 20, "Done");
        this.buttonList.add(this.btnToggleSneak);
        this.buttonList.add(this.btnToggleSprint);
        this.buttonList.add(this.btnShowHUDText);
        this.buttonList.add(this.btnPositionMode);

        if (ToggleSneakMod.optionPositionMode.equals("CUSTOM"))
        {
            this.buttonList.add(this.sliderHUDTextPosX);
            this.buttonList.add(this.sliderHUDTextPosY);
        }

        this.buttonList.add(this.btnDoubleTap);
        this.buttonList.add(this.btnFlyBoost);
        this.buttonList.add(this.sliderFlyBoostAmount);
        this.buttonList.add(this.btnSaveSettings);
    }

    public int getRowPos(int rowNumber)
    {
        return rowNumber > 3 ? this.height / 4 + 0 + (24 * (rowNumber - (ToggleSneakMod.optionPositionMode.equals("CUSTOM") ? 0 : 2)) - 24) + this.byte0 : this.height / 4 + 0 + (24 * rowNumber - 24) + this.byte0;
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            for (int i = 0; i < this.buttonList.size(); ++i)
            {
                GuiButton guibutton = (GuiButton)this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
                {
                    this.lastPressed = guibutton;
                    this.actionPerformed(guibutton);
                }
            }
        }
    }

    /**
     * Called when a mouse button is released.  Args : mouseX, mouseY, releaseButton
     */
    protected void mouseReleased(int mouseX, int mouseY, int which)
    {
        if (this.lastPressed != null && which == 0)
        {
            this.lastPressed.mouseReleased(mouseX, mouseY);
            this.actionPerformed_MouseUp(this.lastPressed);
            this.lastPressed = null;
        }
    }

    protected void actionPerformed_MouseUp(GuiButton button)
    {
        if (button instanceof GuiSlideControl)
        {
            this.actionPerformed(button);
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                ToggleSneakMod.optionToggleSneak = !ToggleSneakMod.optionToggleSneak;
                this.btnToggleSneak.displayString = String.valueOf(ToggleSneakMod.optionToggleSneak);
                break;

            case 2:
                ToggleSneakMod.optionToggleSprint = !ToggleSneakMod.optionToggleSprint;
                this.btnToggleSprint.displayString = String.valueOf(ToggleSneakMod.optionToggleSprint);
                this.changedToggleSprint = true;
                break;

            case 3:
                ToggleSneakMod.optionShowHUDText = !ToggleSneakMod.optionShowHUDText;
                this.btnShowHUDText.displayString = String.valueOf(ToggleSneakMod.optionShowHUDText);
                this.changedShowHUD = true;
                break;

            case 4:
                ToggleSneakMod.optionDoubleTap = !ToggleSneakMod.optionDoubleTap;
                this.btnDoubleTap.displayString = String.valueOf(ToggleSneakMod.optionDoubleTap);
                break;

            case 5:
                ToggleSneakMod.optionEnableFlyBoost = !ToggleSneakMod.optionEnableFlyBoost;
                this.btnFlyBoost.displayString = String.valueOf(ToggleSneakMod.optionEnableFlyBoost);
                break;

            case 50:
                ToggleSneakMod.optionHUDTextPosX = this.sliderHUDTextPosX.GetValueAsInt();
                break;

            case 60:
                ToggleSneakMod.optionHUDTextPosY = this.sliderHUDTextPosY.GetValueAsInt();
                break;

            case 70:
                ToggleSneakMod.optionFlyBoostAmount = (double)this.sliderFlyBoostAmount.GetValueAsFloat();
                break;

            case 100:
                this.mc.displayGuiScreen(ModAPI.getLastScreen());
                break;

            case 133:
                String s = PositionMode.getNext(ToggleSneakMod.optionPositionMode).name();
                ToggleSneakMod.optionPositionMode = s;
                this.btnPositionMode.displayString = s;
                this.initGui();
        }

        this.saveAll();
    }

    private void saveAll()
    {
        if (this.changedShowHUD)
        {
            ToggleSneakModEvents.SetHUDText("");
        }

        if (this.changedToggleSprint && this.mc.theWorld != null)
        {
            ToggleSneakMod.wasSprintDisabled = true;
        }

        ToggleSneakMod.saveConfig();
        this.changedShowHUD = false;
        this.changedToggleSprint = false;
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        String s = "Enable ToggleSneak";
        String s1 = "Enable ToggleSprint";
        String s2 = "Show status on HUD";
        String s3 = "Horizontal HUD Location";
        String s4 = "Vertical HUD Location";
        String s5 = "Enable Double-Tapping";
        String s6 = "Enable Fly Boost";
        String s7 = "Fly Boost Multiplier";
        String s8 = "HUD Position-Mode";
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "ToggleSneak Settings", this.width / 2, this.headerPos, 16777215);
        this.drawString(this.fontRendererObj, s, this.width / 2 - 100 - this.fontRendererObj.getStringWidth(s), this.getRowPos(1) + 6, 16777215);
        this.drawString(this.fontRendererObj, s1, this.width / 2 + 100 - this.fontRendererObj.getStringWidth(s1), this.getRowPos(1) + 6, 16777215);
        this.drawString(this.fontRendererObj, s2, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s2), this.getRowPos(2) + 6, 16777215);
        this.drawString(this.fontRendererObj, s8, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s8), this.getRowPos(3) + 6, 16777215);

        if (ToggleSneakMod.optionPositionMode.equals("CUSTOM"))
        {
            this.drawString(this.fontRendererObj, s3, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s3), this.getRowPos(4) + 6, 16777215);
            this.drawString(this.fontRendererObj, s4, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s4), this.getRowPos(5) + 6, 16777215);
        }

        this.drawString(this.fontRendererObj, s5, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s5), this.getRowPos(6) + 6, 16777215);
        this.drawString(this.fontRendererObj, s6, this.width / 2 - 115 - this.fontRendererObj.getStringWidth(s6), this.getRowPos(7) + 6, 16777215);
        this.drawString(this.fontRendererObj, s7, this.width / 2 + 50 - this.fontRendererObj.getStringWidth(s7), this.getRowPos(7) + 6, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

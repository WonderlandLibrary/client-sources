package directionhud;

import de.labystudio.modapi.ModAPI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiDHConfig extends GuiScreen
{
    private GuiButton btnEnable;
    private GuiButton btnShowWhileChat;
    private GuiButton btnPositionMode;
    private GuiButton btnMarkerColor;
    private GuiButton btnSaveSettings;
    private GuiSlideControl sliderCustomX;
    private GuiSlideControl sliderCustomY;
    private GuiButton lastPressed;
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
        this.btnEnable = new GuiButton(1, this.width / 2 - 98, this.getRowPos(1), 60, 20, String.valueOf(DirectionHUD.optionEnable));
        this.btnShowWhileChat = new GuiButton(2, this.width / 2 + 102, this.getRowPos(1), 60, 20, String.valueOf(DirectionHUD.showWhileChat));
        this.btnMarkerColor = new GuiButton(3, this.width / 2 + 2, this.getRowPos(2), 60, 20, DirectionHUD.optionMarkerColor);
        this.btnPositionMode = new GuiButton(133, this.width / 2 + 2, this.getRowPos(3), 60, 20, DirectionHUD.optionPositionMode);
        this.sliderCustomX = new GuiSlideControl(50, this.width / 2 + 2, this.getRowPos(4), 150, 20, "X Pos: ", 1.0F, (float)(this.width - 25), (float)DirectionHUD.optionCustomX, true);
        this.sliderCustomY = new GuiSlideControl(60, this.width / 2 + 2, this.getRowPos(5), 150, 20, "Y Pos: ", 1.0F, (float)(this.height - 8), (float)DirectionHUD.optionCustomY, true);
        this.btnSaveSettings = new GuiButton(100, this.width / 2 - 30, this.footerPos, 60, 20, "Done");
        this.buttonList.add(this.btnEnable);
        this.buttonList.add(this.btnShowWhileChat);
        this.buttonList.add(this.btnMarkerColor);
        this.buttonList.add(this.btnPositionMode);

        if (DirectionHUD.optionPositionMode.equals("CUSTOM"))
        {
            this.buttonList.add(this.sliderCustomX);
            this.buttonList.add(this.sliderCustomY);
        }

        this.buttonList.add(this.btnSaveSettings);
    }

    public int getRowPos(int rowNumber)
    {
        return rowNumber > 3 ? this.height / 4 + 0 + (24 * (rowNumber - (DirectionHUD.optionPositionMode.equals("CUSTOM") ? 0 : 2)) - 24) + this.byte0 : this.height / 4 + 0 + (24 * rowNumber - 24) + this.byte0;
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
                DirectionHUD.optionEnable = !DirectionHUD.optionEnable;
                this.btnEnable.displayString = String.valueOf(DirectionHUD.optionEnable);
                break;

            case 2:
                DirectionHUD.showWhileChat = !DirectionHUD.showWhileChat;
                this.btnShowWhileChat.displayString = String.valueOf(DirectionHUD.showWhileChat);
                break;

            case 3:
                DirectionHUD.optionMarkerColor = DirectionHUD.getNextColor(DirectionHUD.optionMarkerColor);
                this.btnMarkerColor.displayString = DirectionHUD.optionMarkerColor;
                break;

            case 50:
                DirectionHUD.optionCustomX = this.sliderCustomX.GetValueAsInt();
                break;

            case 60:
                DirectionHUD.optionCustomY = this.sliderCustomY.GetValueAsInt();
                break;

            case 100:
                this.mc.displayGuiScreen(ModAPI.getLastScreen());
                break;

            case 133:
                String s = PositionMode.getNext(DirectionHUD.optionPositionMode).name();
                DirectionHUD.optionPositionMode = s;
                this.btnPositionMode.displayString = s;
                this.initGui();
        }

        this.saveAll();
    }

    private void saveAll()
    {
        DirectionHUD.saveConfig();
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        String s = "Enable";
        String s1 = "Show while chat";
        String s2 = "Marker-Color";
        String s3 = "Custom X Position";
        String s4 = "Custom Y Position";
        String s5 = "Position-Mode";
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "DirectionHUD Settings", this.width / 2, this.headerPos, 16777215);
        this.drawString(this.fontRendererObj, s, this.width / 2 - 100 - this.fontRendererObj.getStringWidth(s), this.getRowPos(1) + 6, 16777215);
        this.drawString(this.fontRendererObj, s1, this.width / 2 + 100 - this.fontRendererObj.getStringWidth(s1), this.getRowPos(1) + 6, 16777215);
        this.drawString(this.fontRendererObj, s2, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s2), this.getRowPos(2) + 6, 16777215);
        this.drawString(this.fontRendererObj, s5, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s5), this.getRowPos(3) + 6, 16777215);

        if (DirectionHUD.optionPositionMode.equals("CUSTOM"))
        {
            this.drawString(this.fontRendererObj, s3, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s3), this.getRowPos(4) + 6, 16777215);
            this.drawString(this.fontRendererObj, s4, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(s4), this.getRowPos(5) + 6, 16777215);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

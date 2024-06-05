package rip.helium.ui.main.components.tab.cheat;

import org.lwjgl.input.*;

import rip.helium.cheat.*;
import rip.helium.ui.main.*;
import rip.helium.ui.main.components.base.*;
import rip.helium.ui.main.tab.cheat.*;
import rip.helium.utils.*;
import rip.helium.utils.font.*;

public class ButtonCheat extends BaseButton
{
    private TabDefaultCheat parentTab;
    private ContainerCheats parentContainer;
    private Cheat cheat;
    private Stopwatch stopwatch;
    private int opacityOffset;
    private boolean listeningForKey;
    
    public ButtonCheat(final Interface theInterface, final TabDefaultCheat parentTab, final ContainerCheats parentContainer, final Cheat cheat, final double x, final double y, final double width, final double height, final Action action) {
        super(theInterface, x, y, width, height, action);
        this.stopwatch = new Stopwatch();
        this.opacityOffset = 0;
        this.listeningForKey = false;
        this.parentTab = parentTab;
        this.parentContainer = parentContainer;
        this.cheat = cheat;
    }
    
    @Override
    public void drawComponent(final double x, final double y) {
        this.positionX = x - this.theInterface.getPositionX();
        this.positionY = y - this.theInterface.getPositionY();
        if (this.stopwatch.hasPassed(50.0)) {
            if (this.isMouseOver()) {
                if (this.opacityOffset < 80) {
                    this.opacityOffset += 16;
                }
            }
            else if (this.opacityOffset > 0) {
                this.opacityOffset -= 16;
            }
            this.stopwatch.reset();
        }
        final int rgb = (this.parentTab.getSelectedCheat() == this.cheat) ? 18 : 24;
        //Draw.drawRectangle(x, y, x + this.maxWidth, y - 2 + this.maxHeight, this.theInterface.getColor(rgb, rgb, rgb, 255));
        Fonts.f16.drawString("" + this.cheat.getId(), x + 5.0, y + 7.0, this.theInterface.getColor(0, 0, 0));
        //String extraInfo = "";
        if (this.cheat.getPropertyRegistry().isEmpty()) {
            //extraInfo = "No options";
        }
        else {
            //extraInfo = String.valueOf(this.cheat.getPropertyRegistry().size()) + ((this.cheat.getPropertyRegistry().size() > 1) ? " options" : " option");
        }
        //Fonts.f12.drawString(extraInfo, x + 5.0, y + 17.0, this.theInterface.getColor(100, 100, 100));
        Draw.drawRectangle(x + this.maxWidth - 25.0, y + 4.0, x + this.maxWidth - 5.0, y + 12.0, this.theInterface.getColor(0, 40, 40));
        if (this.cheat.getState()) {
            Draw.drawRectangle(x + this.maxWidth - 14.0, y + 5.0, x + this.maxWidth - 6.0, y + 11.0, this.theInterface.getColor(80, 150, 80));
        }
        else {
            Draw.drawRectangle(x + this.maxWidth - 24.0, y + 5.0, x + this.maxWidth - 16.0, y + 11.0, this.theInterface.getColor(200, 80, 80));
        }
        String bindText = (this.cheat.getBind() != 0) ? Keyboard.getKeyName(this.cheat.getBind()) : "  ";
        if (this.listeningForKey) {
            bindText = "...";
        }
        Fonts.f14.drawString("[" + bindText + "]", x + this.maxWidth - 5.0 - Fonts.f14.getStringWidth("[" + bindText + "]"), y + 16.5, this.theInterface.getColor(80, 80, 80));
    }
    
    @Override
    public boolean mouseButtonClicked(final int button) {
        String bindText = (this.cheat.getBind() != 0) ? Keyboard.getKeyName(this.cheat.getBind()) : "  ";
        if (this.listeningForKey) {
            bindText = "...";
        }
        if (this.theInterface.isMouseInBounds(this.theInterface.getPositionX() + this.positionX + this.maxWidth - 25.0, this.theInterface.getPositionX() + this.positionX + this.maxWidth - 5.0, this.theInterface.getPositionY() + this.positionY + 4.0, this.theInterface.getPositionY() + this.positionY + 12.0)) {
            this.cheat.setState(!this.cheat.getState(), true);
            return true;
        }
        if (this.theInterface.isMouseInBounds(this.theInterface.getPositionX() + this.positionX + this.maxWidth - 7.0 - Fonts.f14.getStringWidth("[" + bindText + "]"), this.theInterface.getPositionX() + this.positionX + this.maxWidth - 3.0, this.theInterface.getPositionY() + this.positionY + 14.5, this.theInterface.getPositionY() + this.positionY + 24.0)) {
            return this.listeningForKey = true;
        }
        if (this.theInterface.isMouseInBounds(this.theInterface.getPositionX() + this.positionX, this.theInterface.getPositionX() + this.positionX + this.maxWidth, this.theInterface.getPositionY() + this.positionY, this.theInterface.getPositionY() + this.positionY + this.maxHeight)) {
            if (!this.cheat.getPropertyRegistry().isEmpty()) {
                this.parentTab.setSelectedCheat(this.cheat);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean keyTyped(final char typedChar, final int keyCode) {
        if (this.listeningForKey) {
            if (keyCode == 1) {
                this.cheat.setBind(0);
                this.listeningForKey = false;
            }
            else {
                this.cheat.setBind(keyCode);
                this.listeningForKey = false;
            }
            return true;
        }
        return false;
    }
}

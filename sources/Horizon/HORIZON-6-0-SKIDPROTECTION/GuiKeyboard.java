package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.input.Keyboard;

public class GuiKeyboard extends GuiScreen
{
    private Mod HorizonCode_Horizon_È;
    
    public GuiKeyboard(final Mod m) {
        this.HorizonCode_Horizon_È = m;
    }
    
    public Mod m_() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(new GuiKeyboardButton(1, GuiKeyboard.Çªà¢ / 2 - 140, GuiKeyboard.Ê / 2 - 40, 20, 20, "Q"));
        this.ÇŽÉ.add(new GuiKeyboardButton(2, GuiKeyboard.Çªà¢ / 2 - 115, GuiKeyboard.Ê / 2 - 40, 20, 20, "W"));
        this.ÇŽÉ.add(new GuiKeyboardButton(3, GuiKeyboard.Çªà¢ / 2 - 90, GuiKeyboard.Ê / 2 - 40, 20, 20, "E"));
        this.ÇŽÉ.add(new GuiKeyboardButton(4, GuiKeyboard.Çªà¢ / 2 - 65, GuiKeyboard.Ê / 2 - 40, 20, 20, "R"));
        this.ÇŽÉ.add(new GuiKeyboardButton(5, GuiKeyboard.Çªà¢ / 2 - 40, GuiKeyboard.Ê / 2 - 40, 20, 20, "T"));
        this.ÇŽÉ.add(new GuiKeyboardButton(6, GuiKeyboard.Çªà¢ / 2 - 15, GuiKeyboard.Ê / 2 - 40, 20, 20, "Z"));
        this.ÇŽÉ.add(new GuiKeyboardButton(7, GuiKeyboard.Çªà¢ / 2 + 10, GuiKeyboard.Ê / 2 - 40, 20, 20, "U"));
        this.ÇŽÉ.add(new GuiKeyboardButton(8, GuiKeyboard.Çªà¢ / 2 + 35, GuiKeyboard.Ê / 2 - 40, 20, 20, "I"));
        this.ÇŽÉ.add(new GuiKeyboardButton(9, GuiKeyboard.Çªà¢ / 2 + 60, GuiKeyboard.Ê / 2 - 40, 20, 20, "O"));
        this.ÇŽÉ.add(new GuiKeyboardButton(10, GuiKeyboard.Çªà¢ / 2 + 85, GuiKeyboard.Ê / 2 - 40, 20, 20, "P"));
        this.ÇŽÉ.add(new GuiKeyboardButton(11, GuiKeyboard.Çªà¢ / 2 + 110, GuiKeyboard.Ê / 2 - 40, 20, 20, "Ü"));
        this.ÇŽÉ.add(new GuiKeyboardButton(12, GuiKeyboard.Çªà¢ / 2 - 140, GuiKeyboard.Ê / 2 - 15, 20, 20, "A"));
        this.ÇŽÉ.add(new GuiKeyboardButton(13, GuiKeyboard.Çªà¢ / 2 - 115, GuiKeyboard.Ê / 2 - 15, 20, 20, "S"));
        this.ÇŽÉ.add(new GuiKeyboardButton(14, GuiKeyboard.Çªà¢ / 2 - 90, GuiKeyboard.Ê / 2 - 15, 20, 20, "D"));
        this.ÇŽÉ.add(new GuiKeyboardButton(15, GuiKeyboard.Çªà¢ / 2 - 65, GuiKeyboard.Ê / 2 - 15, 20, 20, "F"));
        this.ÇŽÉ.add(new GuiKeyboardButton(16, GuiKeyboard.Çªà¢ / 2 - 40, GuiKeyboard.Ê / 2 - 15, 20, 20, "G"));
        this.ÇŽÉ.add(new GuiKeyboardButton(17, GuiKeyboard.Çªà¢ / 2 - 15, GuiKeyboard.Ê / 2 - 15, 20, 20, "H"));
        this.ÇŽÉ.add(new GuiKeyboardButton(18, GuiKeyboard.Çªà¢ / 2 + 10, GuiKeyboard.Ê / 2 - 15, 20, 20, "J"));
        this.ÇŽÉ.add(new GuiKeyboardButton(19, GuiKeyboard.Çªà¢ / 2 + 35, GuiKeyboard.Ê / 2 - 15, 20, 20, "K"));
        this.ÇŽÉ.add(new GuiKeyboardButton(20, GuiKeyboard.Çªà¢ / 2 + 60, GuiKeyboard.Ê / 2 - 15, 20, 20, "L"));
        this.ÇŽÉ.add(new GuiKeyboardButton(21, GuiKeyboard.Çªà¢ / 2 + 85, GuiKeyboard.Ê / 2 - 15, 20, 20, "Ö"));
        this.ÇŽÉ.add(new GuiKeyboardButton(22, GuiKeyboard.Çªà¢ / 2 + 110, GuiKeyboard.Ê / 2 - 15, 20, 20, "Ä"));
        this.ÇŽÉ.add(new GuiKeyboardButton(23, GuiKeyboard.Çªà¢ / 2 - 90, GuiKeyboard.Ê / 2 + 10, 20, 20, "Y"));
        this.ÇŽÉ.add(new GuiKeyboardButton(24, GuiKeyboard.Çªà¢ / 2 - 65, GuiKeyboard.Ê / 2 + 10, 20, 20, "X"));
        this.ÇŽÉ.add(new GuiKeyboardButton(25, GuiKeyboard.Çªà¢ / 2 - 40, GuiKeyboard.Ê / 2 + 10, 20, 20, "C"));
        this.ÇŽÉ.add(new GuiKeyboardButton(26, GuiKeyboard.Çªà¢ / 2 - 15, GuiKeyboard.Ê / 2 + 10, 20, 20, "V"));
        this.ÇŽÉ.add(new GuiKeyboardButton(27, GuiKeyboard.Çªà¢ / 2 + 10, GuiKeyboard.Ê / 2 + 10, 20, 20, "B"));
        this.ÇŽÉ.add(new GuiKeyboardButton(28, GuiKeyboard.Çªà¢ / 2 + 35, GuiKeyboard.Ê / 2 + 10, 20, 20, "N"));
        this.ÇŽÉ.add(new GuiKeyboardButton(29, GuiKeyboard.Çªà¢ / 2 + 60, GuiKeyboard.Ê / 2 + 10, 20, 20, "M"));
        this.ÇŽÉ.add(new GuiKeyboardButton(30, GuiKeyboard.Çªà¢ / 2 - 90, GuiKeyboard.Ê / 2 + 35, 170, 20, "NONE"));
        this.ÇŽÉ.add(new GuiKeyboardButton(31, GuiKeyboard.Çªà¢ / 2 - 140, GuiKeyboard.Ê / 2 + 10, 45, 20, "LCtrl"));
        this.ÇŽÉ.add(new GuiKeyboardButton(32, GuiKeyboard.Çªà¢ / 2 + 85, GuiKeyboard.Ê / 2 + 10, 45, 20, "RCtrl"));
        this.ÇŽÉ.add(new GuiKeyboardButton(33, GuiKeyboard.Çªà¢ / 2 - 140, GuiKeyboard.Ê / 2 + 35, 45, 20, "LAlt"));
        this.ÇŽÉ.add(new GuiKeyboardButton(34, GuiKeyboard.Çªà¢ / 2 + 85, GuiKeyboard.Ê / 2 + 35, 45, 20, "RAlt"));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiKeyboard.Çªà¢, GuiKeyboard.Ê, -1072689136, -804253680);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        final String title = "Set Keybind for " + this.m_().Ý();
        UIFonts.Ø.HorizonCode_Horizon_È(title, GuiKeyboard.Çªà¢ / 2 - UIFonts.Ø.HorizonCode_Horizon_È(title) / 2, 13, -1869240922);
        UIFonts.Ø.HorizonCode_Horizon_È(title, GuiKeyboard.Çªà¢ / 2 - UIFonts.Ø.HorizonCode_Horizon_È(title) / 2 + 2, 10, -1);
        final String current = "Current Keybind: " + Keyboard.getKeyName(this.m_().Âµá€());
        UIFonts.áŒŠÆ.HorizonCode_Horizon_È(current, GuiKeyboard.Çªà¢ / 2 - UIFonts.áŒŠÆ.HorizonCode_Horizon_È(current) / 2, 50, -1869240922);
        UIFonts.áŒŠÆ.HorizonCode_Horizon_È(current, GuiKeyboard.Çªà¢ / 2 - UIFonts.áŒŠÆ.HorizonCode_Horizon_È(current) / 2 + 2, 48, -1);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 1: {
                this.m_().Ø­áŒŠá(16);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 2: {
                this.m_().Ø­áŒŠá(17);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 3: {
                this.m_().Ø­áŒŠá(18);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 4: {
                this.m_().Ø­áŒŠá(19);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 5: {
                this.m_().Ø­áŒŠá(20);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 6: {
                this.m_().Ø­áŒŠá(44);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 7: {
                this.m_().Ø­áŒŠá(22);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 8: {
                this.m_().Ø­áŒŠá(23);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 9: {
                this.m_().Ø­áŒŠá(24);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 10: {
                this.m_().Ø­áŒŠá(25);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 11: {
                this.m_().Ø­áŒŠá(39);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 12: {
                this.m_().Ø­áŒŠá(30);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 13: {
                this.m_().Ø­áŒŠá(31);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 14: {
                this.m_().Ø­áŒŠá(32);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 15: {
                this.m_().Ø­áŒŠá(33);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 16: {
                this.m_().Ø­áŒŠá(34);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 17: {
                this.m_().Ø­áŒŠá(35);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 18: {
                this.m_().Ø­áŒŠá(36);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 19: {
                this.m_().Ø­áŒŠá(37);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 20: {
                this.m_().Ø­áŒŠá(38);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 21: {
                this.m_().Ø­áŒŠá(41);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 22: {
                this.m_().Ø­áŒŠá(40);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 23: {
                this.m_().Ø­áŒŠá(21);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 24: {
                this.m_().Ø­áŒŠá(45);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 25: {
                this.m_().Ø­áŒŠá(46);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 26: {
                this.m_().Ø­áŒŠá(47);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 27: {
                this.m_().Ø­áŒŠá(48);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 28: {
                this.m_().Ø­áŒŠá(49);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 29: {
                this.m_().Ø­áŒŠá(50);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 30: {
                this.m_().Ø­áŒŠá(0);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 31: {
                this.m_().Ø­áŒŠá(29);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 32: {
                this.m_().Ø­áŒŠá(157);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 33: {
                this.m_().Ø­áŒŠá(56);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
            case 34: {
                this.m_().Ø­áŒŠá(184);
                Horizon.HorizonCode_Horizon_È.áˆºÏ.Â("Gui").ˆÏ­();
                break;
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        System.out.println(Keyboard.getKeyName(keyCode));
        super.HorizonCode_Horizon_È(typedChar, keyCode);
    }
}

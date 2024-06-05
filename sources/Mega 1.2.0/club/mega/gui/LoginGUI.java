package club.mega.gui;

import club.mega.Mega;
import club.mega.gui.altmanager.PasswordField;
import club.mega.util.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class LoginGUI extends GuiScreen {

    private TextFieldUtil textField;
    private PasswordField passwordField;
    private double current, current2;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(0, 0, width, height, new Color(40,40,40));

        GL11.glPushMatrix();
       // GL11.glScaled(current, current, current);
        GL11.glTranslated(0,current, 0);
        RenderUtil.drawRoundedRect(width / 2D - 100, height / 2D - 160, 200, 230, 8, new Color(1, 1, 1, 140));
       // RenderUtil.drawRoundedRect(width / 2D - 100, height / 5D + 1, 200, 230, 8, new Color(1, 1, 1, 140));
        Mega.INSTANCE.getFontManager().getFont("Arial 12").drawCenteredString(Mega.INSTANCE.getVersion(), width / 2D - 86, height / 2D - 155, new Color(255,255,255,140));
        Mega.INSTANCE.getFontManager().getFont("Roboto bold 80").drawCenteredString(Mega.INSTANCE.getName(), width / 2D, height / 2D - 126, ColorUtil.getMainColor());
        Mega.INSTANCE.getFontManager().getFont("Arial 22").drawString("login", width / 2D + 40, height / 2D - 125, new Color(170,170,170).getRGB());
        textField.drawTextBox();
        if (!textField.isFocused() && textField.getText().isEmpty())
            Mega.INSTANCE.getFontManager().getFont("Roboto medium 20").drawString("UID", width / 2D - Mega.INSTANCE.getFontManager().getFont("Arial 20").getWidth("UID") * 2, height / 2D - 45, new Color(255,255,255,140));
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();

        current = AnimationUtil.animate(current, 0, 5);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            mc.displayGuiScreen(new LoginGUI());
        }
        textField.textboxKeyTyped(typedChar, keyCode);
        if (typedChar == '\t')
            textField.setFocused(!textField.isFocused());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        textField.mouseClicked(mouseX, mouseY, state);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id)
        {
            case 2:
                setClipboardString(SecurityUtil.getHWID());
                break;
            case 3:
                //mc.displayGuiScreen(SecurityUtil.checkHWID(textField.getText()) ? new GuiMainMenu() : new LoginGUI());
                mc.displayGuiScreen(new GuiMainMenu());
                break;
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        current = -(height / 2D - 150);
        textField = new TextFieldUtil(1, width / 2D - 40, height / 2D - 50,80, 20, 3);
        buttonList.add(new GuiButton(2, width / 2D - 40, height / 2D, 80, 20, "HWID"));
        buttonList.add(new GuiButton(3, width / 2D - 40, height / 2D - 22, 80, 20, "Login"));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}

/*package xyz.vergoclient.ui.guis.altManager;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import xyz.vergoclient.files.impl.FileAlts;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.ui.guis.GuiAltManager;
import xyz.vergoclient.ui.guis.altManager.accounts.Account;
import xyz.vergoclient.ui.guis.altManager.elements.AltButton;
import xyz.vergoclient.ui.guis.altManager.handling.Alt;
import xyz.vergoclient.ui.guis.altManager.pages.DirectLogin;
import xyz.vergoclient.util.main.ColorUtils;
import xyz.vergoclient.util.main.RenderUtils2;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.opengl.GL11.*;

public final class NewAltMgr extends GuiScreen {

    private final GuiScreen parent;

    public NewAltMgr(GuiScreen parent) {
        this.parent = parent;
    }

    public static CopyOnWriteArrayList<Alt> alts = new CopyOnWriteArrayList<>();

    private static GuiAltManager guiAltManager = new GuiAltManager();
    public static GuiAltManager getGuiAltManager() {
        return guiAltManager;
    }

    private GuiButton loginButton, deleteButton, altButton;

    public ArrayList<AltButton> cButtons = new ArrayList<>();

    private Account currentAccount;

    private boolean scrollbarActive;
    private  int scrollOffset;

    private long lastClick;

    public static FileAlts altsFile = new FileAlts();

    public static transient double scroll = 0, scrollTarget = 0;
    public static transient boolean isAddingAlt = false, isLoggingIntoAlt = false, isAltSelected = false;
    public static transient long deadAltMessage = Long.MIN_VALUE;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        this.loginButton.enabled = true;

        // Draws the entire background.
        //RenderUtils2.drawRect(0, 0, width, height, new Color(20, 20, 20).getRGB());

        // Smooth Shadow Spell
        glDisable(GL_ALPHA_TEST);

        // Background
        RenderUtils2.drawRect(0, 0, width, height, new Color(10, 10, 10).getRGB());

        // Top
        RenderUtils2.drawRect(0, 0, this.width, 45, new Color(10, 10, 10).getRGB());

        // Shadow-Top
        ColorUtils.glDrawFilledQuad(0, 45, this.width, 4, 0x96000000, 0);

        JelloFontRenderer comNorm = FontUtil.comfortaaHuge;
        JelloFontRenderer comRNorm = FontUtil.comfortaaNormal;
        JelloFontRenderer comSmall = FontUtil.comfortaaSmall;

        String altText = "Vergo Alt Manager";
        String altAmnt = String.format("You Have \2476%s\247r Alts.", altsFile.alts.size());

        // Bottom
        ColorUtils.glDrawFilledQuad(0, this.height - 58, this.width, 58, 0xFF090909);

        // Bottom shadow
        ColorUtils.glDrawFilledQuad(0, this.height - 62, this.width, 4, 0, 0x96000000);

        glEnable(GL_ALPHA_TEST);

        mc.fontRendererObj.drawStringWithShadow("\2477Current Account: \247B" + mc.getSession().getUsername(), 4, 4, 0xFFFFFFFF);

        comNorm.drawString(altText, width / 2 - (comNorm.getStringWidth(altText) / 2), 13, -1);
        comSmall.drawString(altAmnt, width / 2 - (comSmall.getStringWidth(altAmnt) / 2), 33, -1);

        RenderUtils2.drawRect(0, this.height - 58, this.width, 58, 0xFF090909);

        for (GuiAltManager.Button button : GuiAltManager.altButtons) {

            //Gui.drawRect(button.posAndColor.x1, button.posAndColor.y1, button.posAndColor.x2, button.posAndColor.y2, Colors.ALT_MANAGER_BUTTONS.getColor());
            if (button.alt.username.equals(mc.session.getUsername())) {
                //Gui.drawRect(button.posAndColor.x2 - 10, button.posAndColor.y1, button.posAndColor.x2, button.posAndColor.y2, Colors.ALT_MANAGER_PURPLE.getColor());

                RenderUtils2.drawBorderedRect((int) button.posAndColor.x1, (int) button.posAndColor.y1, 60, 40, 1f, new Color(19, 19, 19, 255), new Color(64, 64, 64, 190));
            }
            JelloFontRenderer fr3 = FontUtil.neurialGrotesk;

            fr3.drawString(button.alt.username, button.posAndColor.x1 + 5, (float) button.posAndColor.y1 + 5, -1);
            fr3.drawString(button.alt.email, button.posAndColor.x1 + 5, (float) button.posAndColor.y1 + 24, -1);
        }

        // super
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        JelloFontRenderer fr = FontUtil.comfortaaNormal;
        String loginStr = "Add Alt";

        final int buttonWidth = this.width / 6 - 2 * 2;
        final int buttonHeight = 20;
        final int loginButtonW = (int) ((width / 2) - (buttonWidth / 2));
        final int loginButtonH = (int) (height / 1.075);

        this.buttonList.add(loginButton = new GuiButton(0, loginButtonW, loginButtonH, buttonWidth, buttonHeight, loginStr));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                mc.displayGuiScreen(new DirectLogin(this));
        }
    }

}*/

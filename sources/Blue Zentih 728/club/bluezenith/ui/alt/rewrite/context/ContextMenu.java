package club.bluezenith.ui.alt.rewrite.context;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.alt.info.AccountType;
import club.bluezenith.ui.alt.rewrite.AccountElement;
import club.bluezenith.ui.alt.rewrite.actions.GuiAccountInfo;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.client.TriConsumer;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

public class ContextMenu {
    private static final TFontRenderer TITLE_FONT = FontUtil.createFont("Product Sans Bold", 35);

    private final AccountElement accountElement;
    private boolean shouldDisappear;
    private float alpha;

    private final float posX, posY;
    private final float width, height;

    private final List<ContextButton> buttons;

    private float buttonY;
    private final float buttonHeight;
    private final float buttonMargin;

    private boolean listenCtrlZ;

    public ContextMenu(AccountElement accountElement, float posX, float posY) {
        this.accountElement = accountElement;
        this.posX = posX;
        this.posY = posY;

                                  //ww ww just for extra width
        this.width = Math.max(90, TITLE_FONT.getStringWidthF("W" + accountElement.getAccount().getEffectiveUsername() + "W"));
        this.buttons = new ArrayList<>();

        buttonY = posY + 12;
        buttonHeight = 12;
        buttonMargin = 2;

        button("Login", (mouseX, mouseY, button) -> BlueZenith.getBlueZenith().getNewAltManagerGUI().loginWithAccount(accountElement));

        if(accountElement.getAccount().getAccountType() != AccountType.OFFLINE) {
            button("Check ban", (mouseX, mouseY, button) -> {
               createInfoGui().buttonList.stream()
                        .filter(guiButton -> guiButton.displayString.endsWith("Hypixel"))
                        .findFirst()
                        .ifPresent(GuiButton::runClickCallback);

            });
        }

        button("Copy", (mouseX, mouseY, button) ->
                createInfoGui().buttonList.stream()
                        .filter(guiButton -> guiButton.displayString.endsWith("credentials"))
                        .findFirst()
                        .ifPresent(GuiButton::runClickCallback)
        );

        button("Details / Edit", (mouseX, mouseY, button) ->
                Minecraft.getMinecraft().displayGuiScreen(new GuiAccountInfo(
                        BlueZenith.getBlueZenith().getNewAltManagerGUI(),
                        true,
                        accountElement
                )));

        button("Remove", (mouseX, mouseY, button) -> {
                    BlueZenith.getBlueZenith().getNotificationPublisher().postInfo(
                            "Account Manager",
                            "Account \"" + accountElement.getAccount().getEffectiveUsername() + "\" was removed \n " +
                                    "Press CTRL + Z to undo this.",
                            4000
                    );
                    BlueZenith.getBlueZenith().getNewAltManagerGUI().removeAccount(accountElement.getAccount());
                    listenCtrlZ = true;
                });

        //todo fix improper height calculation (i cant do basic math)
        height = 4 + (buttonHeight + buttonMargin*2) * this.buttons.size();
    }

    public void draw(int mouseX, int mouseY) {
        this.alpha = RenderUtil.animate(shouldDisappear ? 0 : 1F, this.alpha, shouldDisappear ? 0.15F : 0.06F);

        glPushMatrix();
        RenderUtil.rect(posX, posY, posX + width, posY + height, new Color(6/255F, 6/255F, 6/255F, (220 * alpha)/255F));

        final String username = accountElement.getAccount().getEffectiveUsername();

        TITLE_FONT.drawString(
                username,
                posX + (width/2F - TITLE_FONT.getStringWidthF(username)/2F),
                posY + 1,
                new Color(1, 1, 1, alpha).getRGB()
        );

        for (ContextButton button : this.buttons) {
            button.setAlpha(alpha);
            button.draw(mouseX, mouseY);
        }
        glPopMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.buttons.stream()
                .filter(button -> button.isMouseOver(mouseX, mouseY))
                .findFirst()
                .ifPresent(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
    }

    public void keyTyped(int code) {
        if(!listenCtrlZ) return;

        int keyToListenFor = code == Keyboard.KEY_LCONTROL  //if the pressed key is LCTRL
                ? Keyboard.KEY_Z //check for Z to be pressed
                : code == Keyboard.KEY_Z ? //if the pressed key is Z
                Keyboard.KEY_LCONTROL //check for LCTRL to be pressed
                : -1; //if neither, don't listen for any key

        if(keyToListenFor != -1 && Keyboard.isKeyDown(keyToListenFor)) {
            BlueZenith.getBlueZenith().getAccountRepository().addAccount(accountElement.getAccount());
            listenCtrlZ = false;
            BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess(
                    "Account Manager",
                         "Account \"" + accountElement.getAccount().getEffectiveUsername() + "\" was re-added.",
                    2500);
        }
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, posX, posY, posX + width, posY + height);
    }

    public boolean shouldDisappear() {
        return this.shouldDisappear;
    }

    public boolean hasDisappeared() {
        return this.shouldDisappear && this.alpha <= 0.09F;
    }

    public void disappear() {
        this.shouldDisappear = true;
    }

    public AccountElement getAccount() {
        return this.accountElement;
    }

    private void button(String name, TriConsumer<Integer, Integer, Integer> onClick) {
        this.buttons.add(new ContextButton(name, this, accountElement, posX, buttonY, width, buttonHeight)
                .onClick(onClick));
        buttonY += buttonHeight + buttonMargin;
    }

    public float getAlpha() {
        return this.alpha;
    }

    private GuiAccountInfo createInfoGui() {
        final GuiAccountInfo guiAccountInfo =  new GuiAccountInfo(BlueZenith.getBlueZenith().getNewAltManagerGUI(), false, accountElement);

        final Minecraft mc = Minecraft.getMinecraft();

        guiAccountInfo.setWorldAndResolution(mc, mc.displayWidth, mc.displayHeight);
        guiAccountInfo.initGui();

        return guiAccountInfo;
    }
}

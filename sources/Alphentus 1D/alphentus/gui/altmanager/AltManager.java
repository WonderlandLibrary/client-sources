package alphentus.gui.altmanager;

import alphentus.file.files.create.LastAltCreate;
import alphentus.file.files.load.LastAltLoad;
import alphentus.gui.buttons.CustomButton;
import alphentus.gui.text.GuiCustomTextField;
import alphentus.init.Init;
import alphentus.utils.GLUtil;
import alphentus.utils.RenderUtils;
import alphentus.utils.Test;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import alphentus.file.files.create.LastAltCreate;
import alphentus.file.files.load.LastAltLoad;
import alphentus.gui.buttons.CustomButton;
import alphentus.gui.text.GuiCustomTextField;
import alphentus.init.Init;
import alphentus.utils.GLUtil;
import alphentus.utils.RenderUtils;
import alphentus.utils.Test;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 04/08/2020.
 */
public class AltManager extends GuiScreen {

    public ArrayList<Alt> altArrayList = new ArrayList();

    private final Init init = Init.getInstance();
    private final UnicodeFontRenderer fontRenderer = init.fontManager.myinghei25;
    private final UnicodeFontRenderer fontRenderer40 = init.fontManager.myinghei40;
    private final UnicodeFontRenderer fontRenderer2 = init.fontManager.myinghei19;

    private String apiKey = "api-9wf2-zqae-v2bu";

    private final Rescan rescan = new Rescan();

    private int scrolling;

    public static String selectedAlt = "";

    boolean addingAltGui = false;
    boolean closingAltGUI = false;

    public boolean deleteScannedAccounts = false;


    public GuiCustomTextField guiTextFieldEmail;
    public GuiCustomTextField guiTextFieldPassword;
    public GuiCustomTextField guiTextFieldApiKey;

    public GuiButton addMojangAlt;
    public GuiButton generate;

    Translate translate;
    Translate translateMain;


    public AltManager() {
        translate = new Translate(0, 0);
        translateMain = new Translate(0, 0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        translateMain.interpolate(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 8);

        GL11.glPushMatrix();

        GL11.glTranslatef(scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 2, 0);
        GL11.glScaled(translateMain.getX() / scaledResolution.getScaledWidth(), translateMain.getY() / scaledResolution.getScaledHeight(), 1);
        GL11.glTranslatef(-scaledResolution.getScaledWidth() / 2, -scaledResolution.getScaledHeight() / 2, 0);

        final int STRING_COLOR = new Color(86, 86, 86, 255).getRGB();

        /*
         * Draws Background
         */

        RenderUtils.relativeRect(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledWidth(), new Color(241, 241, 241, 255).getRGB());

        fontRenderer.drawStringWithShadow("AltManager", 10, 9, STRING_COLOR, false);

        fontRenderer2.drawStringWithShadow("Username: " + mc.getSession().getUsername(), 20 + fontRenderer.getStringWidth("AltManager"), 6 + fontRenderer.FONT_HEIGHT / 2, STRING_COLOR, false);


        /*
         * isHovering Method for 'Add' Button
         */

        final boolean isHoveringAddButton = mouseX > scaledResolution.getScaledWidth() - 12 - fontRenderer.getStringWidth("Add") && mouseX < scaledResolution.getScaledWidth() - 6 && mouseY > 8 && mouseY < 8 + fontRenderer.FONT_HEIGHT;
        fontRenderer.drawStringWithShadow("Add", scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add"), 9, STRING_COLOR, false);

        final boolean isHoveringClipBoard = mouseX > scaledResolution.getScaledWidth() - 12 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 && mouseX < scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Add") - 13 && mouseY > 8 && mouseY < 8 + fontRenderer.FONT_HEIGHT;
        fontRenderer.drawStringWithShadow("Clipboard Login", scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10, 9, STRING_COLOR, false);

        final boolean isHoveringLastAlt = mouseX > scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 - fontRenderer.getStringWidth("Last Alt") - 10 && mouseX < scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 23 && mouseY > 8 && mouseY < 8 + fontRenderer.FONT_HEIGHT;
        fontRenderer.drawStringWithShadow("Last Alt", scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 - fontRenderer.getStringWidth("Last Alt") - 10, 9, STRING_COLOR, false);

        final boolean isHoveringScan = mouseX > scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 - fontRenderer.getStringWidth("Last Alt") - fontRenderer.getStringWidth("Last Alt") - 10 && mouseX < scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 51 - fontRenderer.getStringWidth("Scan") && mouseY > 8 && mouseY < 8 + fontRenderer.FONT_HEIGHT;
        fontRenderer.drawStringWithShadow("Scan", scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 20 - fontRenderer.getStringWidth("Last Alt") - 10 - fontRenderer.getStringWidth("Scan"), 9, STRING_COLOR, false);


        if (isHoveringLastAlt)
            RenderUtils.relativeRect(scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 - fontRenderer.getStringWidth("Last Alt") - 10, 9 + fontRenderer.FONT_HEIGHT, scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 - fontRenderer.getStringWidth("Last Alt") - 10 + fontRenderer.getStringWidth("Last Alt") + 2, 9 + fontRenderer.FONT_HEIGHT + 1, init.CLIENT_COLOR.getRGB());

        if (isHoveringScan)
            RenderUtils.relativeRect(scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 20 - fontRenderer.getStringWidth("Scan") - fontRenderer.getStringWidth("Last Alt") - 10, 9 + fontRenderer.FONT_HEIGHT, scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 - fontRenderer.getStringWidth("Last Alt") - fontRenderer.getStringWidth("Scan") / 2 - 4, 9 + fontRenderer.FONT_HEIGHT + 1, init.CLIENT_COLOR.getRGB());

        if (isHoveringClipBoard)
            RenderUtils.relativeRect(scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10, 9 + fontRenderer.FONT_HEIGHT, scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Add") - 16, 9 + fontRenderer.FONT_HEIGHT + 1, init.CLIENT_COLOR.getRGB());

        if (isHoveringAddButton)
            RenderUtils.relativeRect(scaledResolution.getScaledWidth() - 12 - fontRenderer.getStringWidth("Add"), 9 + fontRenderer.FONT_HEIGHT, scaledResolution.getScaledWidth() - 6, 9 + fontRenderer.FONT_HEIGHT + 1, init.CLIENT_COLOR.getRGB());

        /*
         * Drawing AltsCreate
         */

        int yAltHeight = 25;


        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GLUtil.makeScissorBox(10, 35, scaledResolution.getScaledWidth() - 7, scaledResolution.getScaledHeight());

        for (Alt alt : altArrayList) {
            alt.setPosition(10, 10 + yAltHeight - scrolling, scaledResolution.getScaledWidth() - 18, 36);
            alt.drawScreen();
            yAltHeight += 38;
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        /*
         * Showing Altadding Screen
         */

        if (deleteScannedAccounts) {
            RenderUtils.relativeRect(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Integer.MIN_VALUE);
            String text = "Remove all not working Accounts";
            fontRenderer40.drawStringWithShadow(text, scaledResolution.getScaledWidth() / 2 - fontRenderer40.getStringWidth(text) / 2, 20, -1, false);

            boolean isHoveringRemoveAccounts = mouseX > 25 && mouseX < 25 + fontRenderer.getStringWidth("Remove all Accounts") && mouseY > scaledResolution.getScaledHeight() / 2 - fontRenderer.FONT_HEIGHT / 2 && mouseY < scaledResolution.getScaledHeight() / 2 + fontRenderer.FONT_HEIGHT;
            fontRenderer.drawStringWithShadow("Remove all Accounts", 25, scaledResolution.getScaledHeight() / 2 - fontRenderer.FONT_HEIGHT / 2, -1);
            if (isHoveringRemoveAccounts)
                RenderUtils.relativeRect(25, scaledResolution.getScaledHeight() / 2 + fontRenderer.FONT_HEIGHT / 2, 25 + fontRenderer.getStringWidth("Remove all Accounts"), scaledResolution.getScaledHeight() / 2 + fontRenderer.FONT_HEIGHT / 2 + 1, init.CLIENT_COLOR.getRGB());

            boolean isHoveringKeepAccounts = mouseX > scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Keep all Accounts") - 25 && mouseX < scaledResolution.getScaledWidth() - 25 && mouseY > scaledResolution.getScaledHeight() / 2 - fontRenderer.FONT_HEIGHT / 2 && mouseY < scaledResolution.getScaledHeight() / 2 + fontRenderer.FONT_HEIGHT;
            fontRenderer.drawStringWithShadow("Keep all Accounts", scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Keep all Accounts") - 25, scaledResolution.getScaledHeight() / 2 - fontRenderer.FONT_HEIGHT / 2, -1);

            if (isHoveringKeepAccounts)
                RenderUtils.relativeRect(scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Keep all Accounts") - 25, scaledResolution.getScaledHeight() / 2 + fontRenderer.FONT_HEIGHT / 2, scaledResolution.getScaledWidth() - 25, scaledResolution.getScaledHeight() / 2 + fontRenderer.FONT_HEIGHT / 2 + 1, init.CLIENT_COLOR.getRGB());


        }

        if (!deleteScannedAccounts) {
            if (addingAltGui) {

                GL11.glPushMatrix();
                if (closingAltGUI) {
                    translate.interpolate(0, 0, 8);
                    if (translate.getX() < 175) {
                        closingAltGUI = false;
                        addingAltGui = false;
                    }
                } else {
                    translate.interpolate(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 8);
                }

                GL11.glTranslatef(scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 2, 0);
                GL11.glScaled(translate.getX() / scaledResolution.getScaledWidth(), translate.getY() / scaledResolution.getScaledHeight(), 1);
                GL11.glTranslatef(-scaledResolution.getScaledWidth() / 2, -scaledResolution.getScaledHeight() / 2, 0);

                drawRect(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(33, 33, 33, 120).getRGB());
                init.blurUtil.blur(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 5);


                RenderUtils.drawRoundedRect(scaledResolution.getScaledWidth() / 2 - 73, scaledResolution.getScaledHeight() / 2 - 95, 73 * 2, 97 * 2, 7, new Color(255, 255, 255, 255));

                fontRenderer40.drawStringWithShadow("Add Alt", scaledResolution.getScaledWidth() / 2 - 57, scaledResolution.getScaledHeight() / 2 - 84, STRING_COLOR, false);

                guiTextFieldEmail.drawTextBox();
                guiTextFieldPassword.drawTextBox();
                guiTextFieldApiKey.drawTextBox();

                addMojangAlt.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
                generate.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

                GL11.glPopMatrix();
            } else {
                translate = new Translate(0, 0);
            }
        }
        GL11.glPopMatrix();

        /*
         * Scrolling
         */

        int mouse = Mouse.getDWheel();

        if (mouse < 0 && yAltHeight > scaledResolution.getScaledHeight() && scrolling < yAltHeight - scaledResolution.getScaledHeight() + 10) {
            scrolling += 20;
        }

        if (mouse > 0 && scrolling > 0) {
            scrolling -= 20;
        }

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        if (deleteScannedAccounts)
            return;

        if (!addingAltGui)
            return;

        if (button.id == 1) {
            try {

                if (guiTextFieldEmail.getText().contains("@alt.com")) {

                    String em = guiTextFieldEmail.getText();

                    YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(
                            Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
                    yua.setUsername(em);
                    yua.setPassword("ASFGSG");
                    yua.logIn();
                    this.mc.session = new Session(yua.getSelectedProfile().getName(),
                            yua.getSelectedProfile().getId().toString(), yua.getAuthenticatedToken(), "mojang");
                    this.altArrayList.add(new Alt(em, "ASFASf", AltType.THEALTENING));
                    this.addingAltGui = false;
                } else {
                    String em = guiTextFieldEmail.getText();
                    String pa = guiTextFieldPassword.getText();
                    YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(
                            Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
                    yua.setUsername(em);
                    yua.setPassword(pa);
                    yua.logIn();
                    this.mc.session = new Session(yua.getSelectedProfile().getName(),
                            yua.getSelectedProfile().getId().toString(), yua.getAuthenticatedToken(), "mojang");
                    this.altArrayList.add(new Alt(em, pa, AltType.MOJANG));
                    this.addingAltGui = false;
                }
            } catch (Exception exception) {
            }
        }

        if (button.id == 2) {
            if (!(guiTextFieldApiKey.getText().length() > 0))
                return;


            YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
            try {
                yggdrasilUserAuthentication.setPassword("4511c-bfecc@alt.com");
                yggdrasilUserAuthentication.logIn();

                Minecraft.getMinecraft().session =
                        new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication
                                .getSelectedProfile().getId().toString(),
                                yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
                this.apiKey = guiTextFieldApiKey.getText();
                this.addingAltGui = false;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.actionPerformed(button);

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {

        if (deleteScannedAccounts)
            return;

        if (addingAltGui) {
            if (keyCode == Keyboard.KEY_ESCAPE)
                closingAltGUI = true;

            guiTextFieldEmail.textboxKeyTyped(typedChar, keyCode);
            guiTextFieldApiKey.textboxKeyTyped(typedChar, keyCode);
            guiTextFieldPassword.textboxKeyTyped(typedChar, keyCode);

            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {


        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        final boolean isHoveringAddButton = mouseX > scaledResolution.getScaledWidth() - 12 - fontRenderer.getStringWidth("Add") && mouseX < scaledResolution.getScaledWidth() - 6 && mouseY > 8 && mouseY < 8 + fontRenderer.FONT_HEIGHT;
        final boolean isHoveringLastAlt = mouseX > scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 - fontRenderer.getStringWidth("Last Alt") - 10 && mouseX < scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 23 && mouseY > 8 && mouseY < 8 + fontRenderer.FONT_HEIGHT;
        final boolean isHoveringScan = mouseX > scaledResolution.getScaledWidth() - 10 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 10 - fontRenderer.getStringWidth("Last Alt") - fontRenderer.getStringWidth("Last Alt") - 10 && mouseX < scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 51 - fontRenderer.getStringWidth("Scan") && mouseY > 8 && mouseY < 8 + fontRenderer.FONT_HEIGHT;

        if (deleteScannedAccounts) {
            boolean isHoveringRemoveAccounts = mouseX > 25 && mouseX < 25 + fontRenderer.getStringWidth("Remove all Accounts") && mouseY > scaledResolution.getScaledHeight() / 2 - fontRenderer.FONT_HEIGHT / 2 && mouseY < scaledResolution.getScaledHeight() / 2 + fontRenderer.FONT_HEIGHT;
            boolean isHoveringKeepAccounts = mouseX > scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Keep all Accounts") - 25 && mouseX < scaledResolution.getScaledWidth() - 25 && mouseY > scaledResolution.getScaledHeight() / 2 - fontRenderer.FONT_HEIGHT / 2 && mouseY < scaledResolution.getScaledHeight() / 2 + fontRenderer.FONT_HEIGHT;
            if (isHoveringKeepAccounts && mouseButton == 0)
                deleteScannedAccounts = false;
            if (isHoveringRemoveAccounts && mouseButton == 0)
                rescan.deleteFaleAccounts();
        }

        if (deleteScannedAccounts)
            return;

        if (addingAltGui) {
            guiTextFieldEmail.mouseClicked(mouseX, mouseY, mouseButton);
            guiTextFieldApiKey.mouseClicked(mouseX, mouseY, mouseButton);
            guiTextFieldPassword.mouseClicked(mouseX, mouseY, mouseButton);

            //  if (!(mouseX > scaledResolution.getScaledWidth() / 2 - 70 && mouseX < scaledResolution.getScaledWidth() / 2 + 70 && mouseY > scaledResolution.getScaledHeight() / 2 - 60F && mouseY < scaledResolution.getScaledHeight() / 2 + 70) && mouseButton == 0) {
            //      this.closingAltGUI = true;
            //   }

        } else {

            for (Alt alt : altArrayList) {
                if (mouseY > 35)
                    alt.mouseClicked(mouseX, mouseY, mouseButton);
            }

            if (isHoveringAddButton && mouseButton == 0) {
                addingAltGui = true;
            }

            if (isHoveringLastAlt && mouseButton == 0) {
                LastAltLoad lastAltLoad = new LastAltLoad();
                lastAltLoad.loadCustom();
            }

            final boolean isHoveringClipBoard = mouseX > scaledResolution.getScaledWidth() - 12 - fontRenderer.getStringWidth("Add") - fontRenderer.getStringWidth("Clipboard Login") - 13 && mouseX < scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Add") - 10 && mouseY > 8 && mouseY < 8 + fontRenderer.FONT_HEIGHT;

            if (isHoveringClipBoard && mouseButton == 0) {
                String ep = "";
                try {
                    Transferable alt = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

                    boolean setAlt = (alt != null && alt.isDataFlavorSupported(DataFlavor.stringFlavor));

                    if (setAlt) {
                        try {
                            ep = (String) alt.getTransferData(DataFlavor.stringFlavor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    String em = ep.split(":")[0];
                    String pa = ep.split(":")[1];

                    if (em.contains("@alt.com")) {
                        YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(
                                Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);

                        em = ep;

                        yua.setUsername(em);
                        yua.setPassword("ASGASG");

                        LastAltCreate lastAltCreate = new LastAltCreate();
                        lastAltCreate.createCustomAlt(em, "ASGASG");

                        yua.logIn();

                        this.mc.session = new Session(yua.getSelectedProfile().getName(),
                                yua.getSelectedProfile().getId().toString(), yua.getAuthenticatedToken(), "mojang");
                        this.altArrayList.add(new Alt(mc.session.getUsername(), em, "ASSASA", AltType.THEALTENING));
                    } else {
                        YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(
                                Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
                        yua.setUsername(em);
                        yua.setPassword(pa);

                        LastAltCreate lastAltCreate = new LastAltCreate();
                        lastAltCreate.createCustomAlt(em, pa);

                        yua.logIn();

                        this.mc.session = new Session(yua.getSelectedProfile().getName(),
                                yua.getSelectedProfile().getId().toString(), yua.getAuthenticatedToken(), "mojang");
                    }
                } catch (Exception exception) {
                }
            }

            if (isHoveringScan && mouseButton == 0) {
                rescan.scan();
                return;
            }

        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void initGui() {

        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        this.guiTextFieldEmail = new GuiCustomTextField(1, fontRendererObj, scaledResolution.getScaledWidth() / 2 - 60, scaledResolution.getScaledHeight() / 2 + 6 - Init.getInstance().fontManager.myinghei25.FONT_HEIGHT * 2 - 20, 120, 20);
        this.guiTextFieldPassword = new GuiCustomTextField(2, fontRendererObj, scaledResolution.getScaledWidth() / 2 - 60, scaledResolution.getScaledHeight() / 2 + 6 - Init.getInstance().fontManager.myinghei25.FONT_HEIGHT - 10, 120, 20);
        this.guiTextFieldApiKey = new GuiCustomTextField(3, fontRendererObj, scaledResolution.getScaledWidth() / 2 - 60, scaledResolution.getScaledHeight() / 2 + 6 - Init.getInstance().fontManager.myinghei25.FONT_HEIGHT + 15, 120, 20);

        this.guiTextFieldEmail.setTextPreview("E-Mail");
        this.guiTextFieldPassword.setTextPreview("Password");
        this.guiTextFieldApiKey.setTextPreview("TheAltening Token");

        this.guiTextFieldApiKey.setText(apiKey);

        this.buttonList.add(new CustomButton(1, scaledResolution.getScaledWidth() / 2 - 60, scaledResolution.getScaledHeight() / 2 + 37, 120, 22, "Add Mojang Alt"));
        this.buttonList.add(new CustomButton(2, scaledResolution.getScaledWidth() / 2 - 60, scaledResolution.getScaledHeight() / 2 + 67, 120, 22, "Generate TheAltening"));

        this.addMojangAlt = new CustomButton(1, scaledResolution.getScaledWidth() / 2 - 60, scaledResolution.getScaledHeight() / 2 + 37, 120, 22, "Add Mojang Alt");
        this.generate = new CustomButton(2, scaledResolution.getScaledWidth() / 2 - 60, scaledResolution.getScaledHeight() / 2 + 67, 120, 22, "Generate TheAltening");

        translateMain = new Translate(0, 0);

        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        this.apiKey = guiTextFieldApiKey.getText();
        super.onGuiClosed();
    }
}



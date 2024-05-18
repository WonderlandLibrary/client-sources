package host.kix.uzi.ui.alt.guiscreens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import host.kix.uzi.Uzi;
import host.kix.uzi.ui.alt.LoginThread;
import host.kix.uzi.utilities.minecraft.RenderingMethods;
import host.kix.uzi.utilities.minecraft.alt.Cravatar;
import host.kix.uzi.utilities.minecraft.alt.PlayerSkin;
import host.kix.uzi.utilities.minecraft.alt.PlayerSkinCache;
import host.kix.uzi.utilities.minecraft.alt.SkinComponent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import host.kix.uzi.ui.alt.Alt;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

public class GuiAltManager extends GuiScreen {
    private GuiScreen parent;
    private GuiButton login, remove, last;
    private int offset;
    public Alt selected;
    public int index;

    private GuiTextField searchBar;

    private LoginThread loginThread;

    public GuiAltManager(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 24, 75, 20, "Cancel"));
        this.buttonList.add(login = new GuiButton(1, this.width / 2 - 154, this.height - 48, 100, 20, "Login"));
        this.buttonList.add(remove = new GuiButton(2, this.width / 2 - 74, this.height - 24, 70, 20, "Remove"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 48, 100, 20, "Add"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 48, 100, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 154, this.height - 24, 70, 20, "Random"));
        this.buttonList.add(last = new GuiButton(6, this.width / 2 + 4, this.height - 24, 70, 20, "Relog"));
        this.searchBar = new GuiTextField(0, mc.fontRendererObj, this.width / 2 - 100, 14, 200, 16);
        login.enabled = false;
        remove.enabled = false;
        last.enabled = false;
        if (!Uzi.getInstance().getAltManager().getAlts().isEmpty()) {
            index = 0;
            selected = Uzi.getInstance().getAltManager().getAlts().get(index);
        } else {
            index = 0;
            selected = null;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ArrayList<Alt> alts = (ArrayList<Alt>) Uzi.getInstance().getAltManager().getAlts().clone();
        if (!searchBar.getText().isEmpty()) {
            alts.clear();
            for (Alt alt : Uzi.getInstance().getAltManager().getAlts()) {
                if (alt.getUsername().toLowerCase().contains(searchBar.getText().toLowerCase())) {
                    alts.add(alt);
                }
            }
        }
        searchBar.mouseClicked(mouseX, mouseY, mouseButton);
        if (offset < 0) {
            offset = 0;
        }
        int y = 38 - offset;
        for (final Alt alt : alts) {
            if (isMouseOverAlt(mouseX, mouseY, y)) {
                if (alt == selected) {
                    actionPerformed((GuiButton) buttonList.get(1));
                    return;
                }
                selected = alt;
                index = Uzi.getInstance().getAltManager().getAlts().indexOf(alt);
            }
            y += 26;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                if (loginThread == null || !loginThread.getStatus().contains("Logging in")) {
                    mc.displayGuiScreen(parent);
                    break;
                }
                break;
            case 1:
                final String user = selected.getUsername();
                final String pass = selected.getPassword();
                loginThread = new LoginThread(user, pass);
                loginThread.start();
                break;
            case 2:
                if (loginThread != null) {
                    loginThread = null;
                }
                Uzi.getInstance().getAltManager().getAlts().remove(selected);
                break;
            case 3:
                if (loginThread != null) {
                    loginThread = null;
                }
                mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            case 4:
                if (loginThread != null) {
                    loginThread = null;
                }
                mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            case 5:
                try {
                    Random rand = new Random();
                    Alt randomAlt = Uzi.getInstance().getAltManager().getAlts()
                            .get(rand.nextInt(Uzi.getInstance().getAltManager().getAlts().size() - 1));
                    loginThread = new LoginThread(randomAlt.getUsername(), randomAlt.getPassword());
                    loginThread.start();
                } catch (Exception e) {
                }
                break;
            case 6:
                loginThread = new LoginThread(Uzi.getInstance().getAltManager().getLastAlt().getUsername(),
                        Uzi.getInstance().getAltManager().getLastAlt().getPassword());
                loginThread.start();
                break;
        }
    }

    @Override
    public void keyTyped(char character, int keyCode) {
        searchBar.textboxKeyTyped(character, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Mouse.hasWheel()) {
            final int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                offset += 26;
                if (offset < 0) {
                    offset = 0;
                }
            } else if (wheel > 0) {
                offset -= 26;
                if (offset < 0) {
                    offset = 0;
                }
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            offset -= 13;
            if (offset < 0) {
                offset = 0;
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            offset += 13;
            if (offset < 0) {
                offset = 0;
            }
        }
        drawDefaultBackground();
        drawCenteredString(mc.fontRendererObj,
                "Account Manager - " + Uzi.getInstance().getAltManager().getAlts().size()
                        + (Uzi.getInstance().getAltManager().getAlts().size() == 1 ? " alt." : " alts."),
                width / 2, 2, 0xFFFFFFFF);
        drawString(mc.fontRendererObj, loginThread == null ? "Idle..." : loginThread.getStatus(), 10, 2, 0xFFFFFFFF);
        drawString(mc.fontRendererObj, mc.session.getUsername(), 10, 12, 0xFF888888);
        RenderingMethods.drawBorderedRect(50, 33, width - 50, height - 50, 1, 0xFF000000, 0x80000000);
        GL11.glPushMatrix();
        prepareScissorBox(0, 33, width, height - 50);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ArrayList<Alt> alts = (ArrayList<Alt>) Uzi.getInstance().getAltManager().getAlts().clone();
        if (!searchBar.getText().isEmpty()) {
            alts.clear();
            for (Alt alt : Uzi.getInstance().getAltManager().getAlts()) {
                if (alt.getUsername().toLowerCase().contains(searchBar.getText().toLowerCase())) {
                    alts.add(alt);
                }
            }
        }
        if (offset > (alts.size() - 1) * 26) {
            offset = (alts.size() - 1) * 26;
        }
        int y = 38;
        for (Alt alt : alts) {
            if (!isAltInArea(y)) {
                continue;
            }
            if (alt == selected) {
                if (isMouseOverAlt(mouseX, mouseY, y - offset) && Mouse.isButtonDown(0)) {
                    RenderingMethods.drawBorderedRect(52, y - offset - 4, width - 52, y - offset + 20, 1, 0xFF000000,
                            0x80454545);
                } else if (isMouseOverAlt(mouseX, mouseY, y - offset)) {
                    RenderingMethods.drawBorderedRect(52, y - offset - 4, width - 52, y - offset + 20, 1, 0xFF000000,
                            0x80525252);
                } else {
                    RenderingMethods.drawBorderedRect(52, y - offset - 4, width - 52, y - offset + 20, 1, 0xFF000000,
                            0x80313131);
                }
            } else if (isMouseOverAlt(mouseX, mouseY, y - offset) && Mouse.isButtonDown(0)) {
                RenderingMethods.drawBorderedRect(52, y - offset - 4, width - 52, y - offset + 20, 1, 0xFF000000, 0x80151515);
            } else if (isMouseOverAlt(mouseX, mouseY, y - offset)) {
                RenderingMethods.drawBorderedRect(52, y - offset - 4, width - 52, y - offset + 20, 1, 0xFF000000, 0x80232323);
            }
            if (alt.getUsername().contains("@")) {
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    drawCenteredString(mc.fontRendererObj,
                            alt.getDisplay() + EnumChatFormatting.WHITE + " (" + alt.getUsername() + ")", width / 2,
                            y - offset, 0xFF009ACD);
                } else {
                    drawCenteredString(mc.fontRendererObj, alt.getDisplay(), width / 2, y - offset, 0xFF009ACD);
                }
            } else {
                drawCenteredString(mc.fontRendererObj, alt.getUsername(), width / 2, y - offset, 0xFFFFFFFF);
            }
            drawCenteredString(mc.fontRendererObj, alt.getPassword().isEmpty() ? EnumChatFormatting.RED + "Cracked"
                    : alt.getPassword().replaceAll(".", "*"), width / 2, y - offset + 10, 5592405);
            Cravatar cravatar = PlayerSkinCache.getInstance().getCravatar(alt.getDisplay());

            if (cravatar.isCravatarDown() || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                PlayerSkin skin = PlayerSkinCache.getInstance().getPlayerSkin(alt.getDisplay());
                skin.renderComponent(SkinComponent.HEAD, 60, y - offset - 3, 22, 22);
                skin.renderComponent(SkinComponent.HAT, 60, y - offset - 3, 22, 22);
            } else {
                cravatar.renderComponent(60, y - offset - 4, 24, 24);
            }
            y += 26;
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (selected == null) {
            login.enabled = false;
            remove.enabled = false;
        } else {
            login.enabled = true;
            remove.enabled = true;
        }
        last.enabled = Uzi.getInstance().getAltManager().getLastAlt() != null;
        searchBar.drawTextBox();
    }

    public void prepareScissorBox(float x, float y, float x2, float y2) {
        final int factor = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
        GL11.glScissor((int) (x * factor),
                (int) ((new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaledHeight() - y2) * factor),
                (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    private boolean isAltInArea(int y) {
        return y - offset <= height - 50;
    }

    private boolean isMouseOverAlt(int x, int y, int y1) {
        return x >= 52 && y >= y1 - 4 && x <= width - 52 && y <= y1 + 20 && x >= 0 && y >= 33 && x <= width
                && y <= height - 50;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}

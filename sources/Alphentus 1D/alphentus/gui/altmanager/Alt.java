package alphentus.gui.altmanager;

import alphentus.file.files.create.LastAltCreate;
import alphentus.init.Init;
import alphentus.utils.RenderUtils;
import alphentus.utils.Test;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.net.Proxy;

/**
 * @author avox | lmao
 * @since on 05/08/2020.
 */
public class Alt {

    private String accountUsername = "null";
    private String accountEmail;
    private String accountPassword;

    private final AltType altType;

    private final Init init = Init.getInstance();
    private final UnicodeFontRenderer fontRenderer = init.fontManager.myinghei19;

    private int doubleClick = 0;


    private int x, y, width, height;

    public Alt(String accountEmail, String accountPassword, AltType altType) {
        this.accountEmail = accountEmail;
        this.accountPassword = accountPassword;
        this.altType = altType;

        try {
            YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(
                    Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
            yua.setUsername(accountEmail);
            yua.setPassword(accountPassword);
            yua.logIn();
            this.accountUsername = yua.getSelectedProfile().getName();
        } catch (Exception e) {
            this.accountUsername = "null";
            e.printStackTrace();
        }
    }

    public Alt(String accountUsername, String accountEmail, String accountPassword, AltType altType) {
        this.accountUsername = accountUsername;
        this.accountEmail = accountEmail;
        this.accountPassword = accountPassword;
        this.altType = altType;
    }

    public void drawScreen() {

        final int STRING_COLOR = new Color(45, 45, 45, 255).getRGB();

        Test.draw(x, y, x + width - 1, y + height);


        RenderUtils.relativeRect(x, y, x + width, y + height, AltManager.selectedAlt.equals(accountEmail) ? new Color(187, 187, 187, 255).getRGB() : new Color(255, 255, 255, 255).getRGB());

        fontRenderer.drawStringWithShadow("Name: " + accountUsername, x + 35, y + 1, STRING_COLOR, false);
        fontRenderer.drawStringWithShadow("E-Mail: " + accountEmail, x + 35, y + 1 + fontRenderer.FONT_HEIGHT, STRING_COLOR, false);
        fontRenderer.drawStringWithShadow("Password: " + "******", x + 35, y + 1 + fontRenderer.FONT_HEIGHT * 2, STRING_COLOR, false);

        fontRenderer.drawStringWithShadow("" + altType, x + width - fontRenderer.getStringWidth("" + altType) - 5, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, STRING_COLOR, false);

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height && mouseButton == 0) {
            AltManager.selectedAlt = accountEmail;
            doubleClick++;
        }

        if (AltManager.selectedAlt.equals(accountEmail) && mouseButton == 0)
            if (doubleClick == 2) {
                if (altType.equals(AltType.THEALTENING)) {
                    YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
                    try {
                        yggdrasilUserAuthentication.setUsername(accountEmail);
                        yggdrasilUserAuthentication.setPassword("4511c-bfecc@alt.com");
                        yggdrasilUserAuthentication.logIn();

                        LastAltCreate lastAltCreate = new LastAltCreate();
                        lastAltCreate.createCustomAlt(accountEmail, accountPassword);

                        Minecraft.getMinecraft().session =
                                new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication
                                        .getSelectedProfile().getId().toString(),
                                        yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (altType.equals(AltType.MOJANG)) {

                    try {
                        YggdrasilUserAuthentication yua = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(
                                Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
                        yua.setUsername(accountEmail);
                        yua.setPassword(accountPassword);
                        yua.logIn();

                        LastAltCreate lastAltCreate = new LastAltCreate();
                        lastAltCreate.createCustomAlt(accountEmail, accountPassword);

                        Minecraft.getMinecraft().session = new Session(yua.getSelectedProfile().getName(),
                                yua.getSelectedProfile().getId().toString(), yua.getAuthenticatedToken(), "mojang");
                    } catch (Exception exception) {

                    }
                }
                doubleClick = 0;
            }


    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public int getX() {
        return x;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}

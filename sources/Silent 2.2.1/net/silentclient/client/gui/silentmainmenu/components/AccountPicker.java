package net.silentclient.client.gui.silentmainmenu.components;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.utils.AccountManager;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MouseCursorHandler;

import java.awt.*;

public class AccountPicker {
    protected boolean open;
    protected final int x;
    protected final int y;

    public AccountPicker(int x, int y) {
        this.x = x;
        this.y = y;
        this.open = false;
    }

    public MouseCursorHandler.CursorType draw(Minecraft mc, int mouseX, int mouseY) {
        MouseCursorHandler.CursorType cursorType = null;
        boolean hovered = MouseUtils.isInside(mouseX, mouseY, x - (Client.getInstance().getSilentFontRenderer().getStringWidth(Client.getInstance().getAccount().original_username, 14, SilentFontRenderer.FontType.TITLE) / 2), y, Client.getInstance().getSilentFontRenderer().getStringWidth(Client.getInstance().getAccount().original_username, 14, SilentFontRenderer.FontType.TITLE), 14);
        ColorUtils.setColor(hovered ? new Color(255, 255, 255, 127).getRGB() : -1);
        Client.getInstance().getSilentFontRenderer().drawCenteredString(Client.getInstance().getAccount().original_username, x, y, 14, SilentFontRenderer.FontType.TITLE);
        if(hovered) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }
        if(this.open) {
            int accountY = y + 15;
            for(AccountManager.AccountType account : Client.getInstance().getAccountManager().getAccounts()) {
                if(Client.getInstance().getAccount().getUsername().equalsIgnoreCase(account.username)) {
                    continue;
                }
                boolean accountHovered = MouseUtils.isInside(mouseX, mouseY, x - (Client.getInstance().getSilentFontRenderer().getStringWidth(Client.getInstance().getAccount().original_username, 14, SilentFontRenderer.FontType.TITLE) / 2), accountY, Client.getInstance().getSilentFontRenderer().getStringWidth(Client.getInstance().getAccount().original_username, 14, SilentFontRenderer.FontType.TITLE), 14);
                if(accountHovered) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                ColorUtils.setColor(accountHovered ? new Color(255, 255, 255, 127).getRGB() : -1);
                Client.getInstance().getSilentFontRenderer().drawCenteredString(account.username, x, accountY, 14, SilentFontRenderer.FontType.TITLE);
                accountY += 15;
            }
        }

        return cursorType;
    }

    public void onClick(Minecraft mc, int mouseX, int mouseY) {
        if(MouseUtils.isInside(mouseX, mouseY, x - (Client.getInstance().getSilentFontRenderer().getStringWidth(Client.getInstance().getAccount().original_username, 14, SilentFontRenderer.FontType.TITLE) / 2), y, Client.getInstance().getSilentFontRenderer().getStringWidth(Client.getInstance().getAccount().original_username, 14, SilentFontRenderer.FontType.TITLE), 14)) {
            this.open = !this.open;
            (new Thread(() -> {
                Client.getInstance().getAccountManager().updateAccounts();
            })).start();
            return;
        }

        if(this.open) {
            int accountY = y + 15;
            int accountIndex = 0;
            for(AccountManager.AccountType account : Client.getInstance().getAccountManager().getAccounts()) {
                if(Client.getInstance().getAccount().getUsername().equalsIgnoreCase(account.username)) {
                    accountIndex += 1;
                    continue;
                }
                if(MouseUtils.isInside(mouseX, mouseY, x - (Client.getInstance().getSilentFontRenderer().getStringWidth(account.username, 14, SilentFontRenderer.FontType.TITLE) / 2), accountY, Client.getInstance().getSilentFontRenderer().getStringWidth(account.username, 14, SilentFontRenderer.FontType.TITLE), 14)) {
                    Client.getInstance().getAccountManager().setSelected(account, accountIndex, false);
                    break;
                }
                accountY += 15;
                accountIndex += 1;
            }
            this.open = false;
        }
    }
}

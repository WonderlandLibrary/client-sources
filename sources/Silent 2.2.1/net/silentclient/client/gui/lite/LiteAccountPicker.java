package net.silentclient.client.gui.lite;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.silentmainmenu.components.AccountPicker;
import net.silentclient.client.gui.theme.button.DefaultButtonTheme;
import net.silentclient.client.gui.theme.button.IButtonTheme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

public class LiteAccountPicker extends AccountPicker {
    public static HashMap<String, MinecraftAvatar> avatars = new HashMap<>();
    public static boolean loading;
    protected int animatedOpacity = 0;
    protected TimerUtils animateTimer = new TimerUtils();

    public LiteAccountPicker(int x, int y) {
        super(x, y);
    }

    public MouseCursorHandler.CursorType draw(Minecraft mc, int mouseX, int mouseY) {
        MouseCursorHandler.CursorType cursorType = null;
        IButtonTheme theme = new DefaultButtonTheme();
        ColorUtils.setColor(theme.getBackgroundColor().getRGB());
        RenderUtil.drawRoundedRect(x, y, 100, 18, 3,  theme.getBackgroundColor().getRGB());
        ColorUtils.setColor(theme.getHoveredBackgroundColor(animatedOpacity).getRGB());
        RenderUtil.drawRoundedRect(x, y, 100, 18, 3,  theme.getHoveredBackgroundColor(animatedOpacity ).getRGB());
        ColorUtils.setColor(theme.getBorderColor().getRGB());
        RenderUtil.drawRoundedOutline(x, y, 100, 18, 3, 1, theme.getBorderColor().getRGB());

        if(!avatars.containsKey(Client.getInstance().getAccount().original_username)) {
            avatars.put(Client.getInstance().getAccount().original_username, new MinecraftAvatar(Client.getInstance().getAccount().original_username));
        }

        if(!avatars.get(Client.getInstance().getAccount().original_username).initSkin) {
            avatars.get(Client.getInstance().getAccount().original_username).loadSkin();
        }

        RenderUtil.drawImage(avatars.get(Client.getInstance().getAccount().original_username).imageLocation, x + 2, y + 2, 14, 14, false);
        RenderUtil.drawImage(new ResourceLocation("silentclient/icons/dropdown-icon.png"), x + 100 - 16, y + 2, 14, 14, false);

        Client.getInstance().getSilentFontRenderer().drawString(Client.getInstance().getAccount().original_username, x + 18, y + 9 - 6, 12, SilentFontRenderer.FontType.TITLE, 70);

        if(MouseUtils.isInside(mouseX, mouseY, x, y, 100, 18)) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
            if (this.animatedOpacity < 75 && animateTimer.delay(30)) {
                this.animatedOpacity += 15;
                animateTimer.reset();
            }
        } else {
            if (this.animatedOpacity != 0 && animateTimer.delay(30)) {
                this.animatedOpacity -= 15;
                animateTimer.reset();
            }
        }

        if(this.open) {
            int accountY = y + 20;
            RenderUtil.drawRoundedRect(x, y + 20, 100, (Client.getInstance().getAccountManager().getAccounts().size() - 1) * 18, 3, new Color(20, 20, 20).getRGB());
            for(AccountManager.AccountType account : Client.getInstance().getAccountManager().getAccounts()) {
                if(Client.getInstance().getAccount().getUsername().equalsIgnoreCase(account.username)) {
                    continue;
                }
                if(!avatars.containsKey(account.username)) {
                    avatars.put(account.username, new MinecraftAvatar(account.username));
                }

                if(!avatars.get(account.username).initSkin) {
                    avatars.get(account.username).loadSkin();
                }

                if(MouseUtils.isInside(mouseX, mouseY, x, accountY, 100, 18)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                    RenderUtil.drawRoundedRect(x, accountY, 100, 18, 3, theme.getHoveredBackgroundColor(75).getRGB());
                }

                RenderUtil.drawImage(avatars.get(account.username).imageLocation, x + 2, accountY + 2, 14, 14, false);
                Client.getInstance().getSilentFontRenderer().drawString(account.username, x + 18, accountY + 9 - 6, 12, SilentFontRenderer.FontType.TITLE, 70);
                accountY += 18;
            }
        }

        return cursorType;
    }

    public void onClick(Minecraft mc, int mouseX, int mouseY) {
        if(MouseUtils.isInside(mouseX, mouseY, x, y, 100, 18)) {
            this.open = !this.open;
            (new Thread(() -> {
                Client.getInstance().getAccountManager().updateAccounts();
            })).start();
            return;
        }

        if(this.open) {
            int accountY = y + 20;
            int accountIndex = 0;
            for(AccountManager.AccountType account : Client.getInstance().getAccountManager().getAccounts()) {
                if(Client.getInstance().getAccount().getUsername().equalsIgnoreCase(account.username)) {
                    accountIndex += 1;
                    continue;
                }
                if(MouseUtils.isInside(mouseX, mouseY, x, accountY, 100, 18)) {
                    Client.getInstance().getAccountManager().setSelected(account, accountIndex, false);
                    break;
                }
                accountY += 18;
            }
        }

        this.open = false;
    }

    public static class MinecraftAvatar {
        public ResourceLocation imageLocation = new ResourceLocation("silentclient/images/steve_head.png");
        public BufferedImage image = null;
        public boolean initSkin = false;
        private final String username;

        public MinecraftAvatar(String username) {
            this.username = username;
        }

        public void loadSkin() {
            if(image == null && !LiteAccountPicker.loading && !initSkin) {
                LiteAccountPicker.loading = true;
                (new Thread("McAvatarThread") {
                    public void run() {
                        Client.logger.info(String.format("Downloading avatar %s...", username));
                        BufferedImage avatar = SCTextureManager.getImage(String.format("https://cache.silentclient.net/avatar/%s.png", username));
                        if(avatar != null) {
                            Client.logger.info(String.format("Avatar %s successfully downloaded!", username));
                            image = avatar;
                        } else {
                            Client.logger.info(String.format("Avatar %s downloading failed!", username));
                            initSkin = true;
                        }
                        LiteAccountPicker.loading = false;
                    }
                }).start();
            }
            if(image != null && !initSkin) {
                Client.logger.info(String.format("Crating ResourceLocation of avatar %s...", username));
                imageLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("player_avatar_" + new Random().nextLong(), new DynamicTexture(image));
                initSkin = true;
            }
        }
    }
}

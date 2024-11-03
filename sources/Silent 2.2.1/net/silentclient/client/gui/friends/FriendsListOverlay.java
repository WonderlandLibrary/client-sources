package net.silentclient.client.gui.friends;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.elements.TooltipIconButton;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.LiteAccountPicker;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.theme.button.DefaultButtonTheme;
import net.silentclient.client.gui.theme.button.SelectedButtonTheme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.types.FriendsResponse;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class FriendsListOverlay extends SilentScreen {
    private double scrollY;
    private SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
    private boolean showRequests;
    private SimpleAnimation introAnimation;

    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        this.introAnimation = new SimpleAnimation(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Animations").getValBoolean() ? -150 : 0);
        Client.getInstance().updateFriendsList();
        this.scrollY = 0;
        MenuBlurUtils.loadBlur();
        this.buttonList.add(new IconButton(1, 133, 6, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));

        this.buttonList.add(new Button(2, 3, 26, 70, 15, "Friends", 12, false, !showRequests ? new SelectedButtonTheme() : new DefaultButtonTheme()));
        this.buttonList.add(new Button(3, 76, 26, 70, 15, "Requests", 12, false, showRequests ? new SelectedButtonTheme() : new DefaultButtonTheme()));

        this.buttonList.add(new TooltipIconButton(4, 116, 6, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/friends-add.png"), "Add friend"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        this.buttonList.get(2).displayString = "Requests (" + Client.getInstance().getFriends().getRequestCount() + ")";
        SilentFontRenderer font = Client.getInstance().getSilentFontRenderer();
        GlStateManager.pushMatrix();
        int width = 150;
        GlStateManager.translate(this.introAnimation.getValue(), 0, 0);
        this.introAnimation.setValue(0);
        RenderUtils.drawRect(0, 0, width, height, Theme.backgroundColor().getRGB());
        if(!LiteAccountPicker.avatars.containsKey(Client.getInstance().getAccount().original_username)) {
            LiteAccountPicker.avatars.put(Client.getInstance().getAccount().original_username, new LiteAccountPicker.MinecraftAvatar(Client.getInstance().getAccount().original_username));
        }

        if(!LiteAccountPicker.avatars.get(Client.getInstance().getAccount().original_username).initSkin) {
            LiteAccountPicker.avatars.get(Client.getInstance().getAccount().original_username).loadSkin();
        }
        RenderUtil.drawImage(LiteAccountPicker.avatars.get(Client.getInstance().getAccount().original_username).imageLocation, 3, 5, 18, 18, false);
        ColorUtils.setColor(new Color(9, 165, 51).getRGB());
        font.drawString(Client.getInstance().getAccount().original_username, 23, 5 - 1, 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255, 127).getRGB());
        font.drawString(mc.getCurrentServerData() == null ? "Online" : "Playing on " + mc.getCurrentServerData().serverIP, 23, 5 + 8, 10, SilentFontRenderer.FontType.TITLE, 90);
        super.drawScreen(mouseX, mouseY, partialTicks);
        int friendY = 46 - (int) scrollAnimation.getValue();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution r = new ScaledResolution(mc);
        int s = r.getScaleFactor();
        int listHeight = height - 46;
        int translatedY = r.getScaledHeight() - 46 - listHeight;
        GL11.glScissor(0 * s, translatedY * s, width * s, listHeight * s);
        if(!showRequests) {
            for(FriendsResponse.Friend friend : Client.getInstance().getFriends().getFriends()) {
                if(!LiteAccountPicker.avatars.containsKey(friend.username)) {
                    LiteAccountPicker.avatars.put(friend.username, new LiteAccountPicker.MinecraftAvatar(friend.username));
                }

                if(!LiteAccountPicker.avatars.get(friend.username).initSkin) {
                    LiteAccountPicker.avatars.get(friend.username).loadSkin();
                }
                RenderUtil.drawImage(LiteAccountPicker.avatars.get(friend.username).imageLocation, 3, friendY, 18, 18, false);
                if(friend.isOnline()) {
                    ColorUtils.setColor(new Color(9, 165, 51).getRGB());
                } else {
                    ColorUtils.setColor(new Color(255, 255, 255).getRGB());
                }
                font.drawString(friend.getUsername(), 23, friendY - 1, 12, SilentFontRenderer.FontType.TITLE);
                ColorUtils.setColor(new Color(255, 255, 255, 127).getRGB());
                font.drawString(friend.isOnline() ? friend.getCurrentServer().equals("") ? "Online" : "Playing on " + friend.getCurrentServer() : "Offline", 23, friendY + 8, 10, SilentFontRenderer.FontType.TITLE, 108);
                RenderUtil.drawImage(new ResourceLocation("silentclient/icons/cross.png"), 135, friendY + 3, 12, 12, false);
                if(MouseUtils.isInside(mouseX, mouseY, 135, friendY + 3, 12, 12)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                friendY += 25;
            }
        } else {
            for(FriendsResponse.Request request : Client.getInstance().getFriends().getRequests()) {
                if(!LiteAccountPicker.avatars.containsKey(request.username)) {
                    LiteAccountPicker.avatars.put(request.username, new LiteAccountPicker.MinecraftAvatar(request.username));
                }

                if(!LiteAccountPicker.avatars.get(request.username).initSkin) {
                    LiteAccountPicker.avatars.get(request.username).loadSkin();
                }
                RenderUtil.drawImage(LiteAccountPicker.avatars.get(request.username).imageLocation, 3, friendY, 18, 18, false);
                ColorUtils.setColor(new Color(255, 255, 255).getRGB());
                font.drawString(request.getUsername(), 23, friendY - 1, 12, SilentFontRenderer.FontType.TITLE);
                ColorUtils.setColor(new Color(255, 255, 255, 127).getRGB());
                font.drawString(request.isIncoming() ? "Incoming Friend Request" : "Outgoing Friend Request", 23, friendY + 8, 10, SilentFontRenderer.FontType.TITLE);
                if(request.isIncoming()) {
                    RenderUtil.drawImage(new ResourceLocation("silentclient/icons/accept.png"), 120, friendY + 3, 12, 12, false);
                    if(MouseUtils.isInside(mouseX, mouseY, 120, friendY + 3, 12, 12)) {
                        cursorType = MouseCursorHandler.CursorType.POINTER;
                    }
                }
                if(MouseUtils.isInside(mouseX, mouseY, 135, friendY + 3, 12, 12)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                RenderUtil.drawImage(new ResourceLocation("silentclient/icons/cross.png"), 135, friendY + 3, 12, 12, false);
                friendY += 25;
            }
        }

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
        GlStateManager.popMatrix();

        scrollAnimation.setAnimation((float) scrollY, 16);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dw = Mouse.getEventDWheel();
        double newScrollY = this.scrollY;
        if(dw != 0) {
            if (dw > 0) {
                dw = -1;
            } else {
                dw = 1;
            }
            float amountScrolled = (float) (dw * 10);
            int friendsHeight = (showRequests ? Client.getInstance().getFriends().getRequests().size() : Client.getInstance().getFriends().getFriends().size()) * 25;

            if (newScrollY + amountScrolled > 0)
                newScrollY += amountScrolled;
            else
                newScrollY = 0;

            if((newScrollY < friendsHeight && friendsHeight > height - 46) || amountScrolled < 0) {
                this.scrollY = (float) newScrollY;
                if(this.scrollY < 0) {
                    this.scrollY = 0;
                }
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button instanceof Button) {
            if(button.id == 2) {
                this.buttonList.forEach(oldButton -> {
                    if(oldButton instanceof Button) {
                        ((Button) oldButton).setTheme(new DefaultButtonTheme());
                    }
                });
                ((Button) button).setTheme(new SelectedButtonTheme());
                showRequests = false;
            } else {
                this.buttonList.forEach(oldButton -> {
                    if(oldButton instanceof Button) {
                        ((Button) oldButton).setTheme(new DefaultButtonTheme());
                    }
                });
                ((Button) button).setTheme(new SelectedButtonTheme());
                showRequests = true;
            }
            scrollY = 0;
        }
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(null);
                break;
            case 4:
                mc.displayGuiScreen(new AddFriendModal(this));
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int friendY = 46 + (int) scrollY;
        if(!showRequests) {
            for(FriendsResponse.Friend friend : Client.getInstance().getFriends().getFriends()) {
                if(MouseUtils.isInside(mouseX, mouseY, 135, friendY + 3, 12, 12)) {
                    (new Thread("cancelFriendRequest") {
                        public void run() {
                            friend.cancelRequest();
                        }
                    }).start();
                }
                friendY += 25;
            }
        } else {
            for(FriendsResponse.Request request : Client.getInstance().getFriends().getRequests()) {
                if(request.isIncoming() && MouseUtils.isInside(mouseX, mouseY, 120, friendY + 3, 12, 12)) {
                    (new Thread("acceptFriendRequest") {
                        public void run() {
                            request.acceptRequest();
                        }
                    }).start();
                }
                if(MouseUtils.isInside(mouseX, mouseY, 135, friendY + 3, 12, 12)) {
                    (new Thread("cancelFriendRequest") {
                        public void run() {
                            request.cancelRequest();
                        }
                    }).start();
                }
                friendY += 25;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        MenuBlurUtils.unloadBlur();
    }
}

package net.silentclient.client.emotes.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.emotes.PlayerModelManager;
import net.silentclient.client.emotes.config.EmotesConfig;
import net.silentclient.client.emotes.config.EmotesConfigType;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.socket.EmoteSocket;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.theme.input.DefaultInputTheme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.ScrollHelper;
import net.silentclient.client.utils.types.PlayerResponse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EmoteMenuGui extends SilentScreen {
    private ScrollHelper scrollHelper = new ScrollHelper();

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.silentInputs.clear();
        defaultCursor = false;
        MenuBlurUtils.loadBlur();
        int width = 255;
        int height = 200;
        int x = this.width / 2 - (width / 2);
        int y = this.height / 2 - (height / 2);
        this.buttonList.add(new IconButton(0, x + width - 14 - 3, y + 3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));

        HashMap<Integer, EmotesConfigType.Bind> emoteBinds = new HashMap<>();

        for(EmotesConfigType.Bind bind : EmotesConfig.getConfig().getBinds()) {
            emoteBinds.put(bind.emoteId, bind);
        }

        for(PlayerResponse.Account.Cosmetics.CosmeticItem emote : Client.getInstance().getCosmetics().getMyEmotes()) {
            String emoteName = PlayerModelManager.get().map.get(emote.id);
            if(emoteName == null) {
                continue;
            }
            Emote emoteInstance = PlayerModelManager.get().getEmote(emoteName);
            if(emoteInstance == null) {
                continue;
            }
            EmotesConfigType.Bind bind = emoteBinds.get(emote.id);
            this.silentInputs.add(new Input(emote.name, bind != null ? bind.keyId : -1));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        MenuBlurUtils.renderBackground(this);
        int width = 255;
        int height = 200;
        int x = this.width / 2 - (width / 2);
        int y = this.height / 2 - (height / 2);
        scrollHelper.setStep(5);
        scrollHelper.setElementsHeight((float) Math.ceil((Client.getInstance().getCosmetics().getMyEmotes().size() + 3) / 3) * 85);
        scrollHelper.setMaxScroll(height - 20);
        scrollHelper.setSpeed(200);
        scrollHelper.setFlag(true);
        float scrollY = scrollHelper.getScroll();
        RenderUtil.drawRoundedRect(x, y, width, height, 4, Theme.backgroundColor().getRGB());
        Client.getInstance().getSilentFontRenderer().drawString(x + 3, y + 3, "Emotes", 14, SilentFontRenderer.FontType.TITLE);
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
        int s = r.getScaleFactor();
        int listHeight = height - 20;
        int translatedY = r.getScaledHeight() - y - 20 - listHeight;
        GL11.glScissor(0 * s, translatedY * s, this.width * s, listHeight * s);
        int emoteX = x + 3;
        float emoteY = y + 20 + scrollY;
        int emoteIndex = 0;
        int realEmoteIndex = 0;
        for(PlayerResponse.Account.Cosmetics.CosmeticItem emote : Client.getInstance().getCosmetics().getMyEmotes()) {
            String emoteName = PlayerModelManager.get().map.get(emote.id);
            if(emoteName == null) {
                continue;
            }
            Emote emoteInstance = PlayerModelManager.get().getEmote(emoteName);
            if(emoteInstance == null) {
                continue;
            }

            Input input = this.silentInputs.get(realEmoteIndex);

            input.render(mouseX, mouseY, emoteX + 3, emoteY + 80 - 3 - 15, 74, true, new DefaultInputTheme(), true);

            boolean isHovered = MouseUtils.isInside(mouseX, mouseY, emoteX, emoteY, 80, 80) && !MouseUtils.isInside(mouseX, mouseY, emoteX + 80 - 3 - 10, emoteY + 3, 10, 10) && !input.isHovered();
            if(isHovered) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
                RenderUtil.drawRoundedRect(emoteX, emoteY, 80, 80, 3, new Color(255, 255, 255,  30).getRGB());
            }
            if(MouseUtils.isInside(mouseX, mouseY, emoteX + 80 - 3 - 10, emoteY + 3, 10, 10)) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
            }
            RenderUtil.drawRoundedOutline(emoteX, emoteY, 80, 80, 3, 1, Theme.borderColor().getRGB());
            Client.getInstance().getSilentFontRenderer().drawString(emote.getName(), emoteX + 3, (int) (emoteY + 3), 12, SilentFontRenderer.FontType.TITLE, 64);

            boolean favorite = false;
            for(Number i : Client.getInstance().getAccount().getFavoriteCosmetics().emotes == null ? new ArrayList<Number>() : Client.getInstance().getAccount().getFavoriteCosmetics().emotes) {
                if(i.intValue() == emote.getId()) {
                    favorite = true;
                }
            }

            RenderUtil.drawImage(new ResourceLocation(favorite ? "silentclient/icons/star.png" : "silentclient/icons/star_outline.png"), emoteX + 80 - 3 - 10, emoteY + 3, 10, 10);

            RenderUtil.drawImage(emoteInstance.icon.icon, emoteX + 3 + 37 - 19, emoteY + 16, 39, 39);

            emoteIndex += 1;
            realEmoteIndex++;
            if(emoteIndex == 3) {
                emoteIndex = 0;
                emoteX = x + 3;
                emoteY += 85;
            } else {
                emoteX += 83;
            }
        }
        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 0) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int width = 255;
        int height = 200;
        int x = this.width / 2 - (width / 2);
        int y = this.height / 2 - (height / 2);
        int emoteX = x + 3;
        float emoteY = (int) (y + 20 + scrollHelper.getScroll());
        int emoteIndex = 0;
        int realEmoteIndex = 0;
        for(PlayerResponse.Account.Cosmetics.CosmeticItem emote : Client.getInstance().getCosmetics().getMyEmotes()) {
            String emoteName = PlayerModelManager.get().map.get(emote.id);
            if(emoteName == null) {
                continue;
            }
            Emote emoteInstance = PlayerModelManager.get().getEmote(emoteName);
            if(emoteInstance == null) {
                continue;
            }
            Input input = this.silentInputs.get(realEmoteIndex);
            input.onClick(mouseX, mouseY, emoteX + 3, (int) (emoteY + 80 - 3 - 15), 74, true);
            boolean isHovered = MouseUtils.isInside(mouseX, mouseY, emoteX, emoteY, 80, 80) && !MouseUtils.isInside(mouseX, mouseY, emoteX + 80 - 3 - 10, emoteY + 3, 10, 10) && !input.isHovered();
            if(isHovered) {
                EmoteSocket.get().startEmote(emote.getId());
                mc.displayGuiScreen(null);
                break;
            }

            if(MouseUtils.isInside(mouseX, mouseY, emoteX + 80 - 3 - 10, emoteY + 3, 10, 10)) {
                Client.getInstance().getAccount().updateFavorite(emote.getId(), "emotes");
                break;
            }

            emoteIndex += 1;
            realEmoteIndex++;
            if(emoteIndex == 3) {
                emoteIndex = 0;
                emoteX = x + 3;
                emoteY += 85;
            } else {
                emoteX += 83;
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(mc.thePlayer == null) {
            Client.backgroundPanorama.tickPanorama();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        int inputIndex = 0;
        boolean neededKeyCheck = true;

        for(PlayerResponse.Account.Cosmetics.CosmeticItem emote : Client.getInstance().getCosmetics().getMyEmotes()) {
            String emoteName = PlayerModelManager.get().map.get(emote.id);
            if(emoteName == null) {
                continue;
            }
            Emote emoteInstance = PlayerModelManager.get().getEmote(emoteName);
            if(emoteInstance == null) {
                continue;
            }
            if(silentInputs.get(inputIndex).isFocused()) {
                this.silentInputs.get(inputIndex).onKeyTyped(typedChar, keyCode);
                EmotesConfig.getConfig().addBind(emote.id, this.silentInputs.get(inputIndex).getKey());
                EmotesConfig.save();
                if(keyCode == Keyboard.KEY_ESCAPE) {
                    neededKeyCheck = false;
                    break;
                }
            }
            inputIndex++;
        }

        if (neededKeyCheck && keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        };
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        MenuBlurUtils.unloadBlur();
    }
}

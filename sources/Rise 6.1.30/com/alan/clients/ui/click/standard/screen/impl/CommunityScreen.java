package com.alan.clients.ui.click.standard.screen.impl;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.BackendPacketEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.api.Category;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.ui.click.standard.components.ModuleComponent;
import com.alan.clients.ui.click.standard.screen.Colors;
import com.alan.clients.ui.click.standard.screen.Screen;
import com.alan.clients.ui.click.standard.screen.impl.communityscreen.Element;
import com.alan.clients.ui.click.standard.screen.impl.communityscreen.Row;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.NetworkUtil;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.gui.ScrollUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.tuples.Triple;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector2f;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import rip.vantage.commons.packet.impl.client.community.C2SPacketCommunityInfo;
import rip.vantage.commons.packet.impl.client.community.C2SPacketLoadCloudConfig;
import rip.vantage.commons.packet.impl.client.protection.C2SPacketConvertConfig;
import rip.vantage.commons.packet.impl.server.community.S2CPacketCommunityInfo;
import rip.vantage.network.core.Network;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.stream.Collectors;

@Getter
@Setter
public final class CommunityScreen implements Screen, Accessor {

    public ScrollUtil scrollUtil = new ScrollUtil();
    public static int PADDING = 10;
    public static boolean SCROLL;

    private Row featuredConfigs = new Row(Arrays.asList(
            new Element("", "Loading"),
            new Element("", "Loading"),
            new Element("", "Loading")
    ), "Featured Configs");

    private Row communityConfigs = new Row(Arrays.asList(
            new Element("", "Loading"),
            new Element("", "Loading"),
            new Element("", "Loading")
    ), "Community Configs");

    private Row yourConfigs = new Row(Arrays.asList(
            new Element("", ""),
            new Element("", ""),
            new Element("", "")
    ), "Your Configs");

    private ArrayList<ModuleComponent> yourScripts = new ArrayList<>();

    public Row[] rows = new Row[]{featuredConfigs, /*communityConfigs,*/ /*communityScripts,*/ yourConfigs};
    private boolean registered;
    private Animation animation = new Animation(Easing.EASE_OUT_EXPO, 400);

    @Override
    public void onRender(final int mouseX, final int mouseY, final float partialTicks) {
        if (!registered) {
            Client.INSTANCE.getEventBus().register(this);
            registered = true;
        }

        RiseClickGUI clickGUI = this.getClickGUI();
        Vector2f position = new Vector2f(getClickGUI().getPosition().x, getClickGUI().getPosition().y);
        Vector2f scale = new Vector2f(getClickGUI().getScale().x, getClickGUI().getScale().y);

        if (clickGUI.animationTime > 0.99) {
//            StencilUtil.initStencil();
//            StencilUtil.bindWriteStencilBuffer();
//            RenderUtil.roundedRectangle(clickGUI.position.x + clickGUI.sidebar.sidebarWidth, clickGUI.position.y, clickGUI.scale.x, clickGUI.scale.y, clickGUI.getRound(), Color.BLACK, false, true, true, false);
//            StencilUtil.bindReadStencilBuffer(1);
//            GlStateManager.enableBlend();
//            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
//            GlStateManager.enableTexture2D();
        }

        SCROLL = true;
        for (Row row : rows) row.onScroll();
        scrollUtil.onRender(SCROLL);

        position.x += clickGUI.sidebar.sidebarWidth + PADDING;
        position.y += scrollUtil.getScroll() + PADDING;
        scale.x += -PADDING * 2 - clickGUI.sidebar.sidebarWidth;

        Vector2f imageScale = new Vector2f(scale.x, 110);
        RenderUtil.roundedRectangle(position.x, position.y, imageScale.x, imageScale.y, 10, Colors.OVERLAY.getWithAlpha(Colors.OVERLAY.get().getAlpha() * 2));

        position.y += imageScale.y + PADDING * 2;

        for (Row row : rows) {
            try {
                row.render(position);

                position.y += PADDING * 2 + row.getHeight();
            } catch (ConcurrentModificationException ignored) {

            }
        }

        Fonts.MAIN.get(18, Weight.REGULAR).draw("Your Scripts", position.x, position.y, Color.WHITE.getRGB());
        position.y += PADDING + Fonts.MAIN.get(18, Weight.REGULAR).height();

        for (final ModuleComponent module : this.yourScripts) {
            module.draw(new Vector2d(clickGUI.position.x + clickGUI.sidebar.sidebarWidth + 8, position.y), mouseX, mouseY, partialTicks);
            position.y += module.scale.y + 7;
        }

        double padding = 7;
        double scrollX = clickGUI.getPosition().getX() + clickGUI.getScale().getX() - 4;
        double scrollY = clickGUI.getPosition().getY() + padding;

        scrollUtil.renderScrollBar(new Vector2d(scrollX, scrollY), getClickGUI().scale.y - padding * 2);

        scrollUtil.setMax(-(position.y - scrollUtil.getScroll() - clickGUI.position.y) + clickGUI.scale.y - 7);

        if (clickGUI.animationTime > 0.99) {
//            GlStateManager.disableBlend();
//            StencilUtil.uninitStencilBuffer();
        }
    }

    @Override
    public void onKey(final char typedChar, final int keyCode) {
        for (final ModuleComponent module : yourScripts) {
            module.key(typedChar, keyCode);
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        for (Row row : rows) {
            try {
                row.onClick(mouseX, mouseY, mouseButton);
            } catch (ConcurrentModificationException concurrentModificationException) {
                concurrentModificationException.printStackTrace();
            }
        }

        for (final ModuleComponent moduleComponent : yourScripts) {
            moduleComponent.click(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onMouseRelease() {
        for (final ModuleComponent module : yourScripts) {
            module.released();
        }
    }

    @Override
    public void onBloom() {
        for (final ModuleComponent module : yourScripts) {
            module.bloom();
        }
    }

    @Override
    public void onInit() {
        if (!registered) {
            Client.INSTANCE.getEventBus().register(this);
            registered = true;
        }

        for (Row row : rows) {
            row.init();
        }

        Client.INSTANCE.getConfigManager().update();

        yourConfigs.clear();
        Client.INSTANCE.getConfigManager().forEach(config ->
                yourConfigs.add(new Element("Click to load", config.getName(), config::read)));

        this.yourScripts = Client.INSTANCE.getClickGUI().getModuleList().stream()
                .filter((module) -> module.getModule().getModuleInfo().category() == Category.SCRIPT)
                .collect(Collectors.toCollection(ArrayList::new));
        Network.getInstance().getClient().sendMessage(new C2SPacketCommunityInfo().export());

        new Thread(() -> {
            ArrayList<Triple<String, String, String>> configData = this.getAvailableConfigs();

            this.featuredConfigs.clear();
            configData.forEach(data -> this.featuredConfigs.add(new Element("Click to load", data.getFirst(), () -> Network.getInstance().getClient().sendMessage(new C2SPacketConvertConfig(NetworkUtil.request("https://raw.githubusercontent.com/risellc/RiseOnlineConfigs/main/" + data.getFirst().toLowerCase() + ".json")).export()))));
        }).start();
    }

    @EventLink
    public final Listener<BackendPacketEvent> onBackend = event -> {
        if (event.getPacket() instanceof S2CPacketCommunityInfo) {
            S2CPacketCommunityInfo wrapper = (S2CPacketCommunityInfo) event.getPacket();
            JSONArray jsonArray = wrapper.getConfigs();

            communityConfigs.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = StringUtils.capitalize(jsonObject.getString("a"));
                String author = jsonObject.getString("b");
                String id = jsonObject.getString("c");

                communityConfigs.add(new Element("Click to load", name, () -> Network.getInstance().getClient().sendMessage(new C2SPacketLoadCloudConfig(id).export())));
            }
        }
    };

    @SneakyThrows
    public ArrayList<Triple<String, String, String>> getAvailableConfigs() {
        JsonArray jsonArray = NetworkUtil.requestAsGsonArray("https://raw.githubusercontent.com/risellc/RiseOnlineConfigs/main/index.json");

        ArrayList<Triple<String, String, String>> configDataList = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject configData = element.getAsJsonObject();

            String name = configData.get("name").getAsString();
            String ip = configData.get("ip").getAsString();
            String lastUpdated = configData.get("last updated").getAsString();

            configDataList.add(new Triple<>(name, ip, lastUpdated));
        }

        return configDataList;
    }
}
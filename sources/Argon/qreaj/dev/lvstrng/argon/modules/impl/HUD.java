package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.auth.Authentication;
import dev.lvstrng.argon.clickgui.ClickGUI;
import dev.lvstrng.argon.event.events.Render2DEvent;
import dev.lvstrng.argon.event.listeners.Render2DListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.utils.FontRenderer;
import dev.lvstrng.argon.utils.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public final class HUD extends Module implements Render2DListener {
    private final BooleanSetting showInfo;
    private final BooleanSetting showModules;

    public HUD() {
        super("HUD", "Renders the client version and enabled modules on the HUD", 0, Category.RENDER);
        this.showInfo = new BooleanSetting("Info", true);
        this.showModules = new BooleanSetting("Modules", true).setDescription("Renders module array list");
        this.addSettings(new Setting[]{this.showInfo, this.showModules});
    }

    private static int compareTextWidth(final Module module1, final Module module2) {
        return Integer.compare(FontRenderer.getTextWidth(module2.getName()), FontRenderer.getTextWidth(module1.getName()));
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(Render2DListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(Render2DListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender2D(final Render2DEvent event) {
        if (this.mc.currentScreen != Argon.INSTANCE.clickGUI) {
            final List<Module> modules = Argon.INSTANCE.getModuleManager().getModules2();
            modules.sort(HUD::compareTextWidth);
            final DrawContext ctx = event.ctx;
            final int method590 = ClickGui.customFontSetting.getValue() ? 1 : 0;
            if (!(this.mc.currentScreen instanceof ClickGUI)) {
                if (this.showInfo.getValue() && this.mc.player != null) {
                    RenderUtil.startDrawing();
                    final int x = 10;
                    final int textWidth = 10 + FontRenderer.getTextWidth("Argon |");
                    final String method591 = "Ping: ";
                    final String s = "FPS: " + this.mc.getCurrentFps() + " |";
                    final String s2 = (this.mc.getCurrentServerEntry() == null) ? "None" : this.mc.getCurrentServerEntry().address;
                    String string;
                    if (this.mc != null && this.mc.player != null && this.mc.getNetworkHandler() != null) {
                        final PlayerListEntry playerListEntry = this.mc.getNetworkHandler().getPlayerListEntry(this.mc.player.getUuid());
                        if (playerListEntry != null) string = method591 + playerListEntry.getLatency() + " |";
                        else string = method591 + "N/A |";
                    } else string = method591 + "N/A |";

                    RenderUtil.method419(ctx.getMatrices(), new Color(35, 35, 35, 255), 5.0, 6.0, textWidth + FontRenderer.getTextWidth(s) + FontRenderer.getTextWidth(string) + FontRenderer.getTextWidth(s2) + 35, 30.0, 5.0, 15.0);
                    FontRenderer.drawText("Argon |", ctx, x, 12, Authentication.method331(255, 4).getRGB());
                    final int n5 = x + FontRenderer.getTextWidth("Argon |");
                    FontRenderer.drawText(s, ctx, n5 + 10, 12, Authentication.method331(255, 3).getRGB());
                    FontRenderer.drawText(string, ctx, n5 + 10 + FontRenderer.getTextWidth(s) + 10, 12, Authentication.method331(255, 2).getRGB());
                    FontRenderer.drawText(s2, ctx, n5 + 10 + FontRenderer.getTextWidth(s) + FontRenderer.getTextWidth(string) + 20, 12, Authentication.method331(255, 1).getRGB());
                    RenderUtil.stopDrawing();
                }
                if (this.showModules.getValue()) {
                    int n6 = 55;
                    for (final Module module : modules) {
                        RenderUtil.startDrawing();
                        final int n8 = 10 + FontRenderer.getTextWidth(module.getName());
                        final MatrixStack matrices = ctx.getMatrices();
                        final Color color = new Color(0, 0, 0, 175);
                        final double n9 = 0.0;
                        final double n10 = n6 - 4;
                        final double n11 = n8 + 5;
                        final int n12 = n6;
                        Objects.requireNonNull(this.mc.textRenderer);
                        RenderUtil.method416(matrices, color, n9, n10, n11, n12 + 18 - 1, 0.0, 0.0, 0.0, 5.0, 10.0);
                        final int n13 = 0;
                        final int n14 = n6 - 4;
                        final int n15 = 2;
                        final int n16 = n6;
                        Objects.requireNonNull(this.mc.textRenderer);
                        ctx.fillGradient(n13, n14, n15, n16 + 18, Authentication.method331(255, modules.indexOf(module)).getRGB(), Authentication.method331(255, modules.indexOf(module) + 1).getRGB());
                        FontRenderer.drawText(module.getName(), ctx, (method590 != 0) ? 5 : 8, n6 + method590, Authentication.method331(255, modules.indexOf(module)).getRGB());
                        final int n17 = n6;
                        Objects.requireNonNull(this.mc.textRenderer);
                        n6 = n17 + 21;
                        RenderUtil.stopDrawing();
                    }
                }
            }
        }
    }
}
/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.ui.mainmenu.MainScreen;
import mpp.venusfr.venusfr;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;

public class DeathScreen
extends Screen {
    private int enableButtonsTimer;
    private final ITextComponent causeOfDeath;
    private final boolean isHardcoreMode;
    private ITextComponent field_243285_p;

    public DeathScreen(@Nullable ITextComponent iTextComponent, boolean bl) {
        super(new TranslationTextComponent(bl ? "deathScreen.title.hardcore" : "deathScreen.title"));
        this.causeOfDeath = iTextComponent;
        this.isHardcoreMode = bl;
    }

    @Override
    protected void init() {
        this.enableButtonsTimer = 0;
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 72, 200, 20, this.isHardcoreMode ? new TranslationTextComponent("deathScreen.spectate") : new TranslationTextComponent("deathScreen.respawn"), this::lambda$init$0));
        Button button = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96, 200, 20, new TranslationTextComponent("deathScreen.titleScreen"), this::lambda$init$1));
        if (!this.isHardcoreMode && this.minecraft.getSession() == null) {
            button.active = false;
        }
        for (Widget widget : this.buttons) {
            widget.active = false;
        }
        this.field_243285_p = new TranslationTextComponent("deathScreen.score").appendString(": ").append(new StringTextComponent(Integer.toString(this.minecraft.player.getScore())).mergeStyle(TextFormatting.YELLOW));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    private void confirmCallback(boolean bl) {
        if (bl) {
            this.func_228177_a_();
        } else {
            this.minecraft.player.respawnPlayer();
            this.minecraft.displayGuiScreen(null);
        }
    }

    private void func_228177_a_() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        if (this.minecraft.world != null) {
            this.minecraft.world.sendQuittingDisconnectingPacket();
        }
        this.minecraft.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
        this.minecraft.displayGuiScreen(new MainScreen());
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.fillGradient(matrixStack, 0, 0, this.width, this.height, 0x60500000, -1602211792);
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2.0f, 2.0f, 2.0f);
        DeathScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2 / 2, 30, 0xFFFFFF);
        RenderSystem.popMatrix();
        if (this.causeOfDeath != null) {
            DeathScreen.drawCenteredString(matrixStack, this.font, this.causeOfDeath, this.width / 2, 85, 0xFFFFFF);
        }
        DeathScreen.drawCenteredString(matrixStack, this.font, this.field_243285_p, this.width / 2, 100, 0xFFFFFF);
        if (this.causeOfDeath != null && n2 > 85 && n2 < 94) {
            Style style = this.func_238623_a_(n);
            this.renderComponentHoverEffect(matrixStack, style, n, n2);
        }
        super.render(matrixStack, n, n2, f);
    }

    @Nullable
    private Style func_238623_a_(int n) {
        if (this.causeOfDeath == null) {
            return null;
        }
        int n2 = this.minecraft.fontRenderer.getStringPropertyWidth(this.causeOfDeath);
        int n3 = this.width / 2 - n2 / 2;
        int n4 = this.width / 2 + n2 / 2;
        return n >= n3 && n <= n4 ? this.minecraft.fontRenderer.getCharacterManager().func_238357_a_(this.causeOfDeath, n - n3) : null;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        Style style;
        if (this.causeOfDeath != null && d2 > 85.0 && d2 < 94.0 && (style = this.func_238623_a_((int)d)) != null && style.getClickEvent() != null && style.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
            this.handleComponentClicked(style);
            return true;
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        ++this.enableButtonsTimer;
        if (this.enableButtonsTimer == 20) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }

    private void lambda$init$1(Button button) {
        if (this.isHardcoreMode) {
            this.func_228177_a_();
        } else {
            ConfirmScreen confirmScreen = new ConfirmScreen(this::confirmCallback, new TranslationTextComponent("deathScreen.quit.confirm"), StringTextComponent.EMPTY, new TranslationTextComponent("deathScreen.titleScreen"), new TranslationTextComponent("deathScreen.respawn"));
            this.minecraft.displayGuiScreen(confirmScreen);
            confirmScreen.setButtonDelay(20);
        }
    }

    private void lambda$init$0(Button button) {
        this.minecraft.player.respawnPlayer();
        this.minecraft.displayGuiScreen(null);
    }
}


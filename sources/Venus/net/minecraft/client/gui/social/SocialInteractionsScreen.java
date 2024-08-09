/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.social;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.social.FilterList;
import net.minecraft.client.gui.social.FilterManager;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class SocialInteractionsScreen
extends Screen {
    protected static final ResourceLocation field_244666_a = new ResourceLocation("textures/gui/social_interactions.png");
    private static final ITextComponent field_244667_b = new TranslationTextComponent("gui.socialInteractions.tab_all");
    private static final ITextComponent field_244668_c = new TranslationTextComponent("gui.socialInteractions.tab_hidden");
    private static final ITextComponent field_244762_p = new TranslationTextComponent("gui.socialInteractions.tab_blocked");
    private static final ITextComponent field_244669_p = field_244667_b.copyRaw().mergeStyle(TextFormatting.UNDERLINE);
    private static final ITextComponent field_244670_q = field_244668_c.copyRaw().mergeStyle(TextFormatting.UNDERLINE);
    private static final ITextComponent field_244763_s = field_244762_p.copyRaw().mergeStyle(TextFormatting.UNDERLINE);
    private static final ITextComponent field_244671_r = new TranslationTextComponent("gui.socialInteractions.search_hint").mergeStyle(TextFormatting.ITALIC).mergeStyle(TextFormatting.GRAY);
    private static final ITextComponent field_244764_u = new TranslationTextComponent("gui.socialInteractions.search_empty").mergeStyle(TextFormatting.GRAY);
    private static final ITextComponent field_244672_s = new TranslationTextComponent("gui.socialInteractions.empty_hidden").mergeStyle(TextFormatting.GRAY);
    private static final ITextComponent field_244765_w = new TranslationTextComponent("gui.socialInteractions.empty_blocked").mergeStyle(TextFormatting.GRAY);
    private static final ITextComponent field_244766_x = new TranslationTextComponent("gui.socialInteractions.blocking_hint");
    private FilterList field_244673_t;
    private TextFieldWidget field_244674_u;
    private String field_244675_v = "";
    private Mode field_244676_w = Mode.ALL;
    private Button field_244677_x;
    private Button field_244678_y;
    private Button field_244760_E;
    private Button field_244761_F;
    @Nullable
    private ITextComponent field_244679_z;
    private int field_244662_A;
    private boolean field_244664_C;
    @Nullable
    private Runnable field_244665_D;

    public SocialInteractionsScreen() {
        super(new TranslationTextComponent("gui.socialInteractions.title"));
        this.func_244680_a(Minecraft.getInstance());
    }

    private int func_244689_k() {
        return Math.max(52, this.height - 128 - 16);
    }

    private int func_244690_l() {
        return this.func_244689_k() / 16;
    }

    private int func_244691_m() {
        return 80 + this.func_244690_l() * 16 - 8;
    }

    private int func_244692_n() {
        return (this.width - 238) / 2;
    }

    @Override
    public String getNarrationMessage() {
        return super.getNarrationMessage() + ". " + this.field_244679_z.getString();
    }

    @Override
    public void tick() {
        super.tick();
        this.field_244674_u.tick();
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        if (this.field_244664_C) {
            this.field_244673_t.updateSize(this.width, this.height, 88, this.func_244691_m());
        } else {
            this.field_244673_t = new FilterList(this, this.minecraft, this.width, this.height, 88, this.func_244691_m(), 36);
        }
        int n = this.field_244673_t.getRowWidth() / 3;
        int n2 = this.field_244673_t.getRowLeft();
        int n3 = this.field_244673_t.func_244736_r();
        int n4 = this.font.getStringPropertyWidth(field_244766_x) + 40;
        int n5 = 64 + 16 * this.func_244690_l();
        int n6 = (this.width - n4) / 2;
        this.field_244677_x = this.addButton(new Button(n2, 45, n, 20, field_244667_b, this::lambda$init$0));
        this.field_244678_y = this.addButton(new Button((n2 + n3 - n) / 2 + 1, 45, n, 20, field_244668_c, this::lambda$init$1));
        this.field_244760_E = this.addButton(new Button(n3 - n + 1, 45, n, 20, field_244762_p, this::lambda$init$2));
        this.field_244761_F = this.addButton(new Button(n6, n5, n4, 20, field_244766_x, this::lambda$init$4));
        String string = this.field_244674_u != null ? this.field_244674_u.getText() : "";
        this.field_244674_u = new TextFieldWidget(this, this.font, this.func_244692_n() + 28, 78, 196, 16, field_244671_r){
            final SocialInteractionsScreen this$0;
            {
                this.this$0 = socialInteractionsScreen;
                super(fontRenderer, n, n2, n3, n4, iTextComponent);
            }

            @Override
            protected IFormattableTextComponent getNarrationMessage() {
                return !this.this$0.field_244674_u.getText().isEmpty() && this.this$0.field_244673_t.func_244660_f() ? super.getNarrationMessage().appendString(", ").append(field_244764_u) : super.getNarrationMessage();
            }
        };
        this.field_244674_u.setMaxStringLength(16);
        this.field_244674_u.setEnableBackgroundDrawing(true);
        this.field_244674_u.setVisible(false);
        this.field_244674_u.setTextColor(0xFFFFFF);
        this.field_244674_u.setText(string);
        this.field_244674_u.setResponder(this::func_244687_b);
        this.children.add(this.field_244674_u);
        this.children.add(this.field_244673_t);
        this.field_244664_C = true;
        this.func_244682_a(this.field_244676_w);
    }

    private void func_244682_a(Mode mode) {
        this.field_244676_w = mode;
        this.field_244677_x.setMessage(field_244667_b);
        this.field_244678_y.setMessage(field_244668_c);
        this.field_244760_E.setMessage(field_244762_p);
        Collection<UUID> collection = switch (2.$SwitchMap$net$minecraft$client$gui$social$SocialInteractionsScreen$Mode[mode.ordinal()]) {
            case 1 -> {
                this.field_244677_x.setMessage(field_244669_p);
                yield this.minecraft.player.connection.func_244695_f();
            }
            case 2 -> {
                this.field_244678_y.setMessage(field_244670_q);
                yield this.minecraft.func_244599_aA().func_244644_a();
            }
            case 3 -> {
                this.field_244760_E.setMessage(field_244763_s);
                FilterManager var3_3 = this.minecraft.func_244599_aA();
                yield this.minecraft.player.connection.func_244695_f().stream().filter(var3_3::func_244757_e).collect(Collectors.toSet());
            }
            default -> ImmutableList.of();
        };
        this.field_244676_w = mode;
        this.field_244673_t.func_244759_a(collection, this.field_244673_t.getScrollAmount());
        if (!this.field_244674_u.getText().isEmpty() && this.field_244673_t.func_244660_f() && !this.field_244674_u.isFocused()) {
            NarratorChatListener.INSTANCE.say(field_244764_u.getString());
        } else if (collection.isEmpty()) {
            if (mode == Mode.HIDDEN) {
                NarratorChatListener.INSTANCE.say(field_244672_s.getString());
            } else if (mode == Mode.BLOCKED) {
                NarratorChatListener.INSTANCE.say(field_244765_w.getString());
            }
        }
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        int n = this.func_244692_n() + 3;
        super.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bindTexture(field_244666_a);
        this.blit(matrixStack, n, 64, 1, 1, 236, 8);
        int n2 = this.func_244690_l();
        for (int i = 0; i < n2; ++i) {
            this.blit(matrixStack, n, 72 + 16 * i, 1, 10, 236, 16);
        }
        this.blit(matrixStack, n, 72 + 16 * n2, 1, 27, 236, 8);
        this.blit(matrixStack, n + 10, 76, 243, 1, 12, 12);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.func_244680_a(this.minecraft);
        this.renderBackground(matrixStack);
        if (this.field_244679_z != null) {
            SocialInteractionsScreen.drawString(matrixStack, this.minecraft.fontRenderer, this.field_244679_z, this.func_244692_n() + 8, 35, -1);
        }
        if (!this.field_244673_t.func_244660_f()) {
            this.field_244673_t.render(matrixStack, n, n2, f);
        } else if (!this.field_244674_u.getText().isEmpty()) {
            SocialInteractionsScreen.drawCenteredString(matrixStack, this.minecraft.fontRenderer, field_244764_u, this.width / 2, (78 + this.func_244691_m()) / 2, -1);
        } else {
            switch (2.$SwitchMap$net$minecraft$client$gui$social$SocialInteractionsScreen$Mode[this.field_244676_w.ordinal()]) {
                case 2: {
                    SocialInteractionsScreen.drawCenteredString(matrixStack, this.minecraft.fontRenderer, field_244672_s, this.width / 2, (78 + this.func_244691_m()) / 2, -1);
                    break;
                }
                case 3: {
                    SocialInteractionsScreen.drawCenteredString(matrixStack, this.minecraft.fontRenderer, field_244765_w, this.width / 2, (78 + this.func_244691_m()) / 2, -1);
                }
            }
        }
        if (!this.field_244674_u.isFocused() && this.field_244674_u.getText().isEmpty()) {
            SocialInteractionsScreen.drawString(matrixStack, this.minecraft.fontRenderer, field_244671_r, this.field_244674_u.x, this.field_244674_u.y, -1);
        } else {
            this.field_244674_u.render(matrixStack, n, n2, f);
        }
        this.field_244761_F.visible = this.field_244676_w == Mode.BLOCKED;
        super.render(matrixStack, n, n2, f);
        if (this.field_244665_D != null) {
            this.field_244665_D.run();
        }
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.field_244674_u.isFocused()) {
            this.field_244674_u.mouseClicked(d, d2, n);
        }
        return super.mouseClicked(d, d2, n) || this.field_244673_t.mouseClicked(d, d2, n);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (!this.field_244674_u.isFocused() && this.minecraft.gameSettings.field_244602_au.matchesKey(n, n2)) {
            this.minecraft.displayGuiScreen(null);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private void func_244687_b(String string) {
        if (!(string = string.toLowerCase(Locale.ROOT)).equals(this.field_244675_v)) {
            this.field_244673_t.func_244658_a(string);
            this.field_244675_v = string;
            this.func_244682_a(this.field_244676_w);
        }
    }

    private void func_244680_a(Minecraft minecraft) {
        int n = minecraft.getConnection().getPlayerInfoMap().size();
        if (this.field_244662_A != n) {
            String string = "";
            ServerData serverData = minecraft.getCurrentServerData();
            if (minecraft.isIntegratedServerRunning()) {
                string = minecraft.getIntegratedServer().getMOTD();
            } else if (serverData != null) {
                string = serverData.serverName;
            }
            this.field_244679_z = n > 1 ? new TranslationTextComponent("gui.socialInteractions.server_label.multiple", string, n) : new TranslationTextComponent("gui.socialInteractions.server_label.single", string, n);
            this.field_244662_A = n;
        }
    }

    public void func_244683_a(NetworkPlayerInfo networkPlayerInfo) {
        this.field_244673_t.func_244657_a(networkPlayerInfo, this.field_244676_w);
    }

    public void func_244685_a(UUID uUID) {
        this.field_244673_t.func_244659_a(uUID);
    }

    public void func_244684_a(@Nullable Runnable runnable) {
        this.field_244665_D = runnable;
    }

    private void lambda$init$4(Button button) {
        this.minecraft.displayGuiScreen(new ConfirmOpenLinkScreen(this::lambda$init$3, "https://aka.ms/javablocking", true));
    }

    private void lambda$init$3(boolean bl) {
        if (bl) {
            Util.getOSType().openURI("https://aka.ms/javablocking");
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void lambda$init$2(Button button) {
        this.func_244682_a(Mode.BLOCKED);
    }

    private void lambda$init$1(Button button) {
        this.func_244682_a(Mode.HIDDEN);
    }

    private void lambda$init$0(Button button) {
        this.func_244682_a(Mode.ALL);
    }

    public static enum Mode {
        ALL,
        HIDDEN,
        BLOCKED;

    }
}


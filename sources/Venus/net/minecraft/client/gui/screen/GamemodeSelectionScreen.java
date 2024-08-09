/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class GamemodeSelectionScreen
extends Screen {
    private static final ResourceLocation field_238703_a_ = new ResourceLocation("textures/gui/container/gamemode_switcher.png");
    private static final int field_238704_b_ = Mode.values().length * 30 - 5;
    private static final ITextComponent field_238705_c_ = new TranslationTextComponent("debug.gamemodes.select_next", new TranslationTextComponent("debug.gamemodes.press_f4").mergeStyle(TextFormatting.AQUA));
    private final Optional<Mode> field_238706_p_;
    private Optional<Mode> field_238707_q_ = Optional.empty();
    private int field_238708_r_;
    private int field_238709_s_;
    private boolean field_238710_t_;
    private final List<SelectorWidget> field_238711_u_ = Lists.newArrayList();

    public GamemodeSelectionScreen() {
        super(NarratorChatListener.EMPTY);
        this.field_238706_p_ = Mode.func_238731_b_(this.func_241608_k_());
    }

    private GameType func_241608_k_() {
        GameType gameType = Minecraft.getInstance().playerController.getCurrentGameType();
        GameType gameType2 = Minecraft.getInstance().playerController.func_241822_k();
        if (gameType2 == GameType.NOT_SET) {
            gameType2 = gameType == GameType.CREATIVE ? GameType.SURVIVAL : GameType.CREATIVE;
        }
        return gameType2;
    }

    @Override
    protected void init() {
        super.init();
        this.field_238707_q_ = this.field_238706_p_.isPresent() ? this.field_238706_p_ : Mode.func_238731_b_(this.minecraft.playerController.getCurrentGameType());
        for (int i = 0; i < Mode.field_238721_e_.length; ++i) {
            Mode mode = Mode.field_238721_e_[i];
            this.field_238711_u_.add(new SelectorWidget(this, mode, this.width / 2 - field_238704_b_ / 2 + i * 30, this.height / 2 - 30));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (!this.func_238718_l_()) {
            matrixStack.push();
            RenderSystem.enableBlend();
            this.minecraft.getTextureManager().bindTexture(field_238703_a_);
            int n3 = this.width / 2 - 62;
            int n4 = this.height / 2 - 30 - 27;
            GamemodeSelectionScreen.blit(matrixStack, n3, n4, 0.0f, 0.0f, 125, 75, 128, 128);
            matrixStack.pop();
            super.render(matrixStack, n, n2, f);
            this.field_238707_q_.ifPresent(arg_0 -> this.lambda$render$0(matrixStack, arg_0));
            GamemodeSelectionScreen.drawCenteredString(matrixStack, this.font, field_238705_c_, this.width / 2, this.height / 2 + 5, 0xFFFFFF);
            if (!this.field_238710_t_) {
                this.field_238708_r_ = n;
                this.field_238709_s_ = n2;
                this.field_238710_t_ = true;
            }
            boolean bl = this.field_238708_r_ == n && this.field_238709_s_ == n2;
            for (SelectorWidget selectorWidget : this.field_238711_u_) {
                selectorWidget.render(matrixStack, n, n2, f);
                this.field_238707_q_.ifPresent(arg_0 -> GamemodeSelectionScreen.lambda$render$1(selectorWidget, arg_0));
                if (bl || !selectorWidget.isHovered()) continue;
                this.field_238707_q_ = Optional.of(selectorWidget.field_238736_b_);
            }
        }
    }

    private void func_238717_j_() {
        GamemodeSelectionScreen.func_238713_a_(this.minecraft, this.field_238707_q_);
    }

    private static void func_238713_a_(Minecraft minecraft, Optional<Mode> optional) {
        if (minecraft.playerController != null && minecraft.player != null && optional.isPresent()) {
            Optional<Mode> optional2 = Mode.func_238731_b_(minecraft.playerController.getCurrentGameType());
            Mode mode = optional.get();
            if (optional2.isPresent() && minecraft.player.hasPermissionLevel(1) && mode != optional2.get()) {
                minecraft.player.sendChatMessage(mode.func_238730_b_());
            }
        }
    }

    private boolean func_238718_l_() {
        if (!InputMappings.isKeyDown(this.minecraft.getMainWindow().getHandle(), 292)) {
            this.func_238717_j_();
            this.minecraft.displayGuiScreen(null);
            return false;
        }
        return true;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 293 && this.field_238707_q_.isPresent()) {
            this.field_238710_t_ = false;
            this.field_238707_q_ = this.field_238707_q_.get().func_238733_c_();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private static void lambda$render$1(SelectorWidget selectorWidget, Mode mode) {
        selectorWidget.func_238741_e_(mode == selectorWidget.field_238736_b_);
    }

    private void lambda$render$0(MatrixStack matrixStack, Mode mode) {
        GamemodeSelectionScreen.drawCenteredString(matrixStack, this.font, mode.func_238725_a_(), this.width / 2, this.height / 2 - 30 - 20, -1);
    }

    static enum Mode {
        CREATIVE(new TranslationTextComponent("gameMode.creative"), "/gamemode creative", new ItemStack(Blocks.GRASS_BLOCK)),
        SURVIVAL(new TranslationTextComponent("gameMode.survival"), "/gamemode survival", new ItemStack(Items.IRON_SWORD)),
        ADVENTURE(new TranslationTextComponent("gameMode.adventure"), "/gamemode adventure", new ItemStack(Items.MAP)),
        SPECTATOR(new TranslationTextComponent("gameMode.spectator"), "/gamemode spectator", new ItemStack(Items.ENDER_EYE));

        protected static final Mode[] field_238721_e_;
        final ITextComponent field_238722_f_;
        final String field_238723_g_;
        final ItemStack field_238724_h_;

        private Mode(ITextComponent iTextComponent, String string2, ItemStack itemStack) {
            this.field_238722_f_ = iTextComponent;
            this.field_238723_g_ = string2;
            this.field_238724_h_ = itemStack;
        }

        private void func_238729_a_(ItemRenderer itemRenderer, int n, int n2) {
            itemRenderer.renderItemAndEffectIntoGUI(this.field_238724_h_, n, n2);
        }

        private ITextComponent func_238725_a_() {
            return this.field_238722_f_;
        }

        private String func_238730_b_() {
            return this.field_238723_g_;
        }

        private Optional<Mode> func_238733_c_() {
            switch (this) {
                case CREATIVE: {
                    return Optional.of(SURVIVAL);
                }
                case SURVIVAL: {
                    return Optional.of(ADVENTURE);
                }
                case ADVENTURE: {
                    return Optional.of(SPECTATOR);
                }
            }
            return Optional.of(CREATIVE);
        }

        private static Optional<Mode> func_238731_b_(GameType gameType) {
            switch (gameType) {
                case SPECTATOR: {
                    return Optional.of(SPECTATOR);
                }
                case SURVIVAL: {
                    return Optional.of(SURVIVAL);
                }
                case CREATIVE: {
                    return Optional.of(CREATIVE);
                }
                case ADVENTURE: {
                    return Optional.of(ADVENTURE);
                }
            }
            return Optional.empty();
        }

        static {
            field_238721_e_ = Mode.values();
        }
    }

    public class SelectorWidget
    extends Widget {
        private final Mode field_238736_b_;
        private boolean field_238737_c_;
        final GamemodeSelectionScreen this$0;

        public SelectorWidget(GamemodeSelectionScreen gamemodeSelectionScreen, Mode mode, int n, int n2) {
            this.this$0 = gamemodeSelectionScreen;
            super(n, n2, 25, 25, mode.func_238725_a_());
            this.field_238736_b_ = mode;
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
            Minecraft minecraft = Minecraft.getInstance();
            this.func_238738_a_(matrixStack, minecraft.getTextureManager());
            this.field_238736_b_.func_238729_a_(this.this$0.itemRenderer, this.x + 5, this.y + 5);
            if (this.field_238737_c_) {
                this.func_238740_b_(matrixStack, minecraft.getTextureManager());
            }
        }

        @Override
        public boolean isHovered() {
            return super.isHovered() || this.field_238737_c_;
        }

        public void func_238741_e_(boolean bl) {
            this.field_238737_c_ = bl;
            this.narrate();
        }

        private void func_238738_a_(MatrixStack matrixStack, TextureManager textureManager) {
            textureManager.bindTexture(field_238703_a_);
            matrixStack.push();
            matrixStack.translate(this.x, this.y, 0.0);
            SelectorWidget.blit(matrixStack, 0, 0, 0.0f, 75.0f, 25, 25, 128, 128);
            matrixStack.pop();
        }

        private void func_238740_b_(MatrixStack matrixStack, TextureManager textureManager) {
            textureManager.bindTexture(field_238703_a_);
            matrixStack.push();
            matrixStack.translate(this.x, this.y, 0.0);
            SelectorWidget.blit(matrixStack, 0, 0, 25.0f, 75.0f, 25, 25, 128, 128);
            matrixStack.pop();
        }
    }
}


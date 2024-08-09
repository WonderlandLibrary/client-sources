/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.AccessibilityScreen;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.ChatOptionsScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.ControlsScreen;
import net.minecraft.client.gui.screen.CustomizeSkinScreen;
import net.minecraft.client.gui.screen.LanguageScreen;
import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraft.client.gui.screen.PackScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.LockIconButton;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.network.play.client.CLockDifficultyPacket;
import net.minecraft.network.play.client.CSetDifficultyPacket;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;

public class OptionsScreen
extends Screen {
    private static final AbstractOption[] SCREEN_OPTIONS = new AbstractOption[]{AbstractOption.FOV};
    private final Screen lastScreen;
    private final GameSettings settings;
    private Button difficultyButton;
    private LockIconButton lockButton;
    private Difficulty worldDifficulty;

    public OptionsScreen(Screen screen, GameSettings gameSettings) {
        super(new TranslationTextComponent("options.title"));
        this.lastScreen = screen;
        this.settings = gameSettings;
    }

    @Override
    protected void init() {
        int n = 0;
        for (AbstractOption abstractOption : SCREEN_OPTIONS) {
            int n2 = this.width / 2 - 155 + n % 2 * 160;
            int n3 = this.height / 6 - 12 + 24 * (n >> 1);
            this.addButton(abstractOption.createWidget(this.minecraft.gameSettings, n2, n3, 150));
            ++n;
        }
        if (this.minecraft.world != null) {
            this.worldDifficulty = this.minecraft.world.getDifficulty();
            this.difficultyButton = this.addButton(new Button(this.width / 2 - 155 + n % 2 * 160, this.height / 6 - 12 + 24 * (n >> 1), 150, 20, this.func_238630_a_(this.worldDifficulty), this::lambda$init$0));
            if (this.minecraft.isSingleplayer() && !this.minecraft.world.getWorldInfo().isHardcore()) {
                this.difficultyButton.setWidth(this.difficultyButton.getWidth() - 20);
                this.lockButton = this.addButton(new LockIconButton(this.difficultyButton.x + this.difficultyButton.getWidth(), this.difficultyButton.y, this::lambda$init$1));
                this.lockButton.setLocked(this.minecraft.world.getWorldInfo().isDifficultyLocked());
                this.lockButton.active = !this.lockButton.isLocked();
                this.difficultyButton.active = !this.lockButton.isLocked();
            } else {
                this.difficultyButton.active = false;
            }
        } else {
            this.addButton(new OptionButton(this.width / 2 - 155 + n % 2 * 160, this.height / 6 - 12 + 24 * (n >> 1), 150, 20, AbstractOption.REALMS_NOTIFICATIONS, AbstractOption.REALMS_NOTIFICATIONS.func_238152_c_(this.settings), this::lambda$init$2));
        }
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, new TranslationTextComponent("options.skinCustomisation"), this::lambda$init$3));
        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, new TranslationTextComponent("options.sounds"), this::lambda$init$4));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, new TranslationTextComponent("options.video"), this::lambda$init$5));
        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, new TranslationTextComponent("options.controls"), this::lambda$init$6));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, new TranslationTextComponent("options.language"), this::lambda$init$7));
        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, new TranslationTextComponent("options.chat.title"), this::lambda$init$8));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, new TranslationTextComponent("options.resourcepack"), this::lambda$init$9));
        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, new TranslationTextComponent("options.accessibility.title"), this::lambda$init$10));
        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, DialogTexts.GUI_DONE, this::lambda$init$11));
    }

    private void func_241584_a_(ResourcePackList resourcePackList) {
        ImmutableList<String> immutableList = ImmutableList.copyOf(this.settings.resourcePacks);
        this.settings.resourcePacks.clear();
        this.settings.incompatibleResourcePacks.clear();
        for (ResourcePackInfo resourcePackInfo : resourcePackList.getEnabledPacks()) {
            if (resourcePackInfo.isOrderLocked()) continue;
            this.settings.resourcePacks.add(resourcePackInfo.getName());
            if (resourcePackInfo.getCompatibility().isCompatible()) continue;
            this.settings.incompatibleResourcePacks.add(resourcePackInfo.getName());
        }
        this.settings.saveOptions();
        ImmutableList<String> immutableList2 = ImmutableList.copyOf(this.settings.resourcePacks);
        if (!immutableList2.equals(immutableList)) {
            this.minecraft.reloadResources();
        }
    }

    private ITextComponent func_238630_a_(Difficulty difficulty) {
        return new TranslationTextComponent("options.difficulty").appendString(": ").append(difficulty.getDisplayName());
    }

    private void accept(boolean bl) {
        this.minecraft.displayGuiScreen(this);
        if (bl && this.minecraft.world != null) {
            this.minecraft.getConnection().sendPacket(new CLockDifficultyPacket(true));
            this.lockButton.setLocked(false);
            this.lockButton.active = false;
            this.difficultyButton.active = false;
        }
    }

    @Override
    public void onClose() {
        this.settings.saveOptions();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        OptionsScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$11(Button button) {
        this.minecraft.displayGuiScreen(this.lastScreen);
    }

    private void lambda$init$10(Button button) {
        this.minecraft.displayGuiScreen(new AccessibilityScreen(this, this.settings));
    }

    private void lambda$init$9(Button button) {
        this.minecraft.displayGuiScreen(new PackScreen(this, this.minecraft.getResourcePackList(), this::func_241584_a_, this.minecraft.getFileResourcePacks(), new TranslationTextComponent("resourcePack.title")));
    }

    private void lambda$init$8(Button button) {
        this.minecraft.displayGuiScreen(new ChatOptionsScreen(this, this.settings));
    }

    private void lambda$init$7(Button button) {
        this.minecraft.displayGuiScreen(new LanguageScreen((Screen)this, this.settings, this.minecraft.getLanguageManager()));
    }

    private void lambda$init$6(Button button) {
        this.minecraft.displayGuiScreen(new ControlsScreen(this, this.settings));
    }

    private void lambda$init$5(Button button) {
        this.minecraft.displayGuiScreen(new VideoSettingsScreen(this, this.settings));
    }

    private void lambda$init$4(Button button) {
        this.minecraft.displayGuiScreen(new OptionsSoundsScreen(this, this.settings));
    }

    private void lambda$init$3(Button button) {
        this.minecraft.displayGuiScreen(new CustomizeSkinScreen(this, this.settings));
    }

    private void lambda$init$2(Button button) {
        AbstractOption.REALMS_NOTIFICATIONS.nextValue(this.settings);
        this.settings.saveOptions();
        button.setMessage(AbstractOption.REALMS_NOTIFICATIONS.func_238152_c_(this.settings));
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(new ConfirmScreen(this::accept, new TranslationTextComponent("difficulty.lock.title"), new TranslationTextComponent("difficulty.lock.question", new TranslationTextComponent("options.difficulty." + this.minecraft.world.getWorldInfo().getDifficulty().getTranslationKey()))));
    }

    private void lambda$init$0(Button button) {
        this.worldDifficulty = Difficulty.byId(this.worldDifficulty.getId() + 1);
        this.minecraft.getConnection().sendPacket(new CSetDifficultyPacket(this.worldDifficulty));
        this.difficultyButton.setMessage(this.func_238630_a_(this.worldDifficulty));
    }
}


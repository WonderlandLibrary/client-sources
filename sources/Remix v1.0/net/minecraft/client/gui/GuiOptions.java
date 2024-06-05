package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.audio.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.client.stream.*;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback
{
    private static final GameSettings.Options[] field_146440_f;
    private final GuiScreen field_146441_g;
    private final GameSettings game_settings_1;
    protected String field_146442_a;
    private GuiButton field_175357_i;
    private GuiLockIconButton field_175356_r;
    
    public GuiOptions(final GuiScreen p_i1046_1_, final GameSettings p_i1046_2_) {
        this.field_146442_a = "Options";
        this.field_146441_g = p_i1046_1_;
        this.game_settings_1 = p_i1046_2_;
    }
    
    @Override
    public void initGui() {
        int var1 = 0;
        this.field_146442_a = I18n.format("options.title", new Object[0]);
        for (final GameSettings.Options var5 : GuiOptions.field_146440_f) {
            if (var5.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), GuiOptions.width / 2 - 155 + var1 % 2 * 160, GuiOptions.height / 6 - 12 + 24 * (var1 >> 1), var5));
            }
            else {
                final GuiOptionButton var6 = new GuiOptionButton(var5.returnEnumOrdinal(), GuiOptions.width / 2 - 155 + var1 % 2 * 160, GuiOptions.height / 6 - 12 + 24 * (var1 >> 1), var5, this.game_settings_1.getKeyBinding(var5));
                this.buttonList.add(var6);
            }
            ++var1;
        }
        if (GuiOptions.mc.theWorld != null) {
            final EnumDifficulty var7 = GuiOptions.mc.theWorld.getDifficulty();
            this.field_175357_i = new GuiButton(108, GuiOptions.width / 2 - 155 + var1 % 2 * 160, GuiOptions.height / 6 - 12 + 24 * (var1 >> 1), 150, 20, this.func_175355_a(var7));
            this.buttonList.add(this.field_175357_i);
            if (GuiOptions.mc.isSingleplayer() && !GuiOptions.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                this.field_175357_i.func_175211_a(this.field_175357_i.getButtonWidth() - 20);
                this.field_175356_r = new GuiLockIconButton(109, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition);
                this.buttonList.add(this.field_175356_r);
                this.field_175356_r.func_175229_b(GuiOptions.mc.theWorld.getWorldInfo().isDifficultyLocked());
                this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
                this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
            }
            else {
                this.field_175357_i.enabled = false;
            }
        }
        this.buttonList.add(new GuiButton(110, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
        this.buttonList.add(new GuiButton(8675309, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 48 - 6, 150, 20, "Super Secret Settings...") {
            @Override
            public void playPressSound(final SoundHandler soundHandlerIn) {
                final SoundEventAccessorComposite var2 = soundHandlerIn.getRandomSoundFromCategories(SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER);
                if (var2 != null) {
                    soundHandlerIn.playSound(PositionedSoundRecord.createPositionedSoundRecord(var2.getSoundEventLocation(), 0.5f));
                }
            }
        });
        this.buttonList.add(new GuiButton(106, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
        this.buttonList.add(new GuiButton(107, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 72 - 6, 150, 20, I18n.format("options.stream", new Object[0])));
        this.buttonList.add(new GuiButton(101, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
        this.buttonList.add(new GuiButton(100, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
        this.buttonList.add(new GuiButton(102, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
        this.buttonList.add(new GuiButton(103, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 120 - 6, 150, 20, I18n.format("options.multiplayer.title", new Object[0])));
        this.buttonList.add(new GuiButton(105, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
        this.buttonList.add(new GuiButton(104, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiOptions.width / 2 - 100, GuiOptions.height / 6 + 168, I18n.format("gui.done", new Object[0])));
    }
    
    public String func_175355_a(final EnumDifficulty p_175355_1_) {
        final ChatComponentText var2 = new ChatComponentText("");
        var2.appendSibling(new ChatComponentTranslation("options.difficulty", new Object[0]));
        var2.appendText(": ");
        var2.appendSibling(new ChatComponentTranslation(p_175355_1_.getDifficultyResourceKey(), new Object[0]));
        return var2.getFormattedText();
    }
    
    @Override
    public void confirmClicked(final boolean result, final int id) {
        GuiOptions.mc.displayGuiScreen(this);
        if (id == 109 && result && GuiOptions.mc.theWorld != null) {
            GuiOptions.mc.theWorld.getWorldInfo().setDifficultyLocked(true);
            this.field_175356_r.func_175229_b(true);
            this.field_175356_r.enabled = false;
            this.field_175357_i.enabled = false;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id < 100 && button instanceof GuiOptionButton) {
                final GameSettings.Options var2 = ((GuiOptionButton)button).returnEnumOptions();
                this.game_settings_1.setOptionValue(var2, 1);
                button.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 108) {
                GuiOptions.mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(GuiOptions.mc.theWorld.getDifficulty().getDifficultyId() + 1));
                this.field_175357_i.displayString = this.func_175355_a(GuiOptions.mc.theWorld.getDifficulty());
            }
            if (button.id == 109) {
                GuiOptions.mc.displayGuiScreen(new GuiYesNo(this, new ChatComponentTranslation("difficulty.lock.title", new Object[0]).getFormattedText(), new ChatComponentTranslation("difficulty.lock.question", new Object[] { new ChatComponentTranslation(GuiOptions.mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]) }).getFormattedText(), 109));
            }
            if (button.id == 110) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiCustomizeSkin(this));
            }
            if (button.id == 8675309) {
                GuiOptions.mc.entityRenderer.activateNextShader();
            }
            if (button.id == 101) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiVideoSettings(this, this.game_settings_1));
            }
            if (button.id == 100) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
            }
            if (button.id == 102) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, GuiOptions.mc.getLanguageManager()));
            }
            if (button.id == 103) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
            }
            if (button.id == 104) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
            }
            if (button.id == 200) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(this.field_146441_g);
            }
            if (button.id == 105) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }
            if (button.id == 106) {
                GuiOptions.mc.gameSettings.saveOptions();
                GuiOptions.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
            }
            if (button.id == 107) {
                GuiOptions.mc.gameSettings.saveOptions();
                final IStream var3 = GuiOptions.mc.getTwitchStream();
                if (var3.func_152936_l() && var3.func_152928_D()) {
                    GuiOptions.mc.displayGuiScreen(new GuiStreamOptions(this, this.game_settings_1));
                }
                else {
                    GuiStreamUnavailable.func_152321_a(this);
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.field_146442_a, GuiOptions.width / 2, 15, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        field_146440_f = new GameSettings.Options[] { GameSettings.Options.FOV };
    }
}

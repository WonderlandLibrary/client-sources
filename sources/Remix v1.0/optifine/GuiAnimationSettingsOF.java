package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiAnimationSettingsOF extends GuiScreen
{
    private static GameSettings.Options[] enumOptions;
    protected String title;
    private GuiScreen prevScreen;
    private GameSettings settings;
    
    public GuiAnimationSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.animationsTitle", new Object[0]);
        this.buttonList.clear();
        for (int i = 0; i < GuiAnimationSettingsOF.enumOptions.length; ++i) {
            final GameSettings.Options opt = GuiAnimationSettingsOF.enumOptions[i];
            final int x = GuiAnimationSettingsOF.width / 2 - 155 + i % 2 * 160;
            final int y = GuiAnimationSettingsOF.height / 6 + 21 * (i / 2) - 12;
            if (!opt.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(opt.returnEnumOrdinal(), x, y, opt, this.settings.getKeyBinding(opt)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(opt.returnEnumOrdinal(), x, y, opt));
            }
        }
        this.buttonList.add(new GuiButton(210, GuiAnimationSettingsOF.width / 2 - 155, GuiAnimationSettingsOF.height / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
        this.buttonList.add(new GuiButton(211, GuiAnimationSettingsOF.width / 2 - 155 + 80, GuiAnimationSettingsOF.height / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
        this.buttonList.add(new GuiOptionButton(200, GuiAnimationSettingsOF.width / 2 + 5, GuiAnimationSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
                guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
            }
            if (guibutton.id == 200) {
                GuiAnimationSettingsOF.mc.gameSettings.saveOptions();
                GuiAnimationSettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
            if (guibutton.id == 210) {
                GuiAnimationSettingsOF.mc.gameSettings.setAllAnimations(true);
            }
            if (guibutton.id == 211) {
                GuiAnimationSettingsOF.mc.gameSettings.setAllAnimations(false);
            }
            final ScaledResolution sr = new ScaledResolution(GuiAnimationSettingsOF.mc, GuiAnimationSettingsOF.mc.displayWidth, GuiAnimationSettingsOF.mc.displayHeight);
            this.setWorldAndResolution(GuiAnimationSettingsOF.mc, sr.getScaledWidth(), sr.getScaledHeight());
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float f) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiAnimationSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(x, y, f);
    }
    
    static {
        GuiAnimationSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE, GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE, GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME, GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES, GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH, GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES, GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN, GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.FIREWORK_PARTICLES, GameSettings.Options.PARTICLES };
    }
}

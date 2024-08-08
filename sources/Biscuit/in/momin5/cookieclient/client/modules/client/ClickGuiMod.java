package in.momin5.cookieclient.client.modules.client;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;
import in.momin5.cookieclient.client.gui.hud.HUDEditor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

/**
 * powered by trinity :3 Thanks leon for da blur, i improved it a bit and fixed some crashes
*/
public class ClickGuiMod extends Module {
    public static ClickGuiMod INSTANCE;

    public SettingMode theme = register( new SettingMode("theme", this, "Original", "Clear", "Original","New"));

    public SettingNumber AnimationSpeed = register( new SettingNumber("animation", this, 200, 0, 1000, 50));

    public SettingNumber scrolls =register( new SettingNumber("ScrollSpeed", this, 10, 0, 20, 1));
    public SettingMode ScrollMode = register(new SettingMode("scroll", this, "container", "container", "screen"));
    public SettingColor EnabledColor =register( new SettingColor("EnabledColor", this, new CustomColor(117,0,188, 137)));
    public SettingColor SettingBackgroundColor = register( new SettingColor("BackgroundColor", this, new CustomColor(45,50,59,255)));
    public SettingColor BackgroundColor = new SettingColor("BackgroundPenis", this, new CustomColor(45, 50, 59, 255));
    public SettingColor OutlineColor = new SettingColor("SettingsHighlight", this, new CustomColor(0, 0, 0, 0));
    public SettingColor categoryColor = register( new SettingColor("CategoryColor",this,new CustomColor(117,0,188,137)));
    public SettingColor FontColor = register(new SettingColor("FontColor", this, new CustomColor(255, 255, 255, 255)));
    public SettingNumber opacity = register(new SettingNumber("opacity", this, 255,0,255, 5));
    public SettingBoolean blur = register(new SettingBoolean("Blur",this,true));

    public ClickGuiMod() {
        super("ClickGUI", "no retard", Category.HUD);
        setKey(Keyboard.KEY_Y);
    }

    @Override
    public void onEnable() {
        CookieClient.clickGUI.enterGUI();
        MinecraftForge.EVENT_BUS.register(this);
        loadShader();
    }

    public void onUpdate() {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            this.setEnabled(false);
        }

        if(ModuleManager.isModuleEnabled(HUDEditor.class)){
            this.setEnabled(false);
        }
    }

    public void onDisable(){
        MinecraftForge.EVENT_BUS.unregister(this);
        if(mc.entityRenderer.isShaderActive()){
            stopShader();
        }
    }

    private void loadShader(){
        if(blur.isEnabled()){
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    private void stopShader(){
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
    }

}

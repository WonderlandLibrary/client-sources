package in.momin5.cookieclient.api.setting.settings;

import com.lukflug.panelstudio.settings.ColorSetting;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.Setting;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;

import java.awt.*;

public class SettingColor extends Setting implements ColorSetting {
    private boolean rainbow;
    private CustomColor value;

    public SettingColor (String name, Module parent, final CustomColor value) {
        super(name,parent);
        this.value=value;
    }

    public CustomColor getValue() {
        if (rainbow) {
            return CustomColor.fromHSB((System.currentTimeMillis()%(360*20))/(360f * 20),0.5f,1f);
        }
        return this.value;
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }

    public void setValue (boolean rainbow, final CustomColor value) {
        this.rainbow = rainbow;
        this.value = value;
    }

    public long toInteger() {
        return this.value.getRGB() & (0xFFFFFFFF);
    }

    public void fromInteger (long number) {
        this.value = new CustomColor(Math.toIntExact(number & 0xFFFFFFFF),true);
    }

    public CustomColor getColor() {
        return this.value;
    }

    @Override
    public boolean getRainbow() {
        return this.rainbow;
    }

    @Override
    public void setValue(Color value) {
        setValue(getRainbow(),new CustomColor(value));
    }

    @Override
    public void setRainbow(boolean rainbow) {
        this.rainbow=rainbow;
    }
}

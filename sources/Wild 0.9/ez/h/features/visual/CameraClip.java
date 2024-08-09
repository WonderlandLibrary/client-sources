package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class CameraClip extends Feature
{
    public static OptionBoolean smooth;
    public static OptionSlider z;
    public static OptionSlider distance;
    public static OptionSlider y;
    public static OptionSlider x;
    public static OptionBoolean noclip;
    
    public CameraClip() {
        super("CameraClip", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u043d\u0430\u0441\u0442\u0440\u043e\u0438\u0442\u044c \u043a\u0430\u043c\u0435\u0440\u0443 \u0442\u0440\u0435\u0442\u044c\u0435\u0433\u043e \u043b\u0438\u0446\u0430.", Category.VISUAL);
        CameraClip.noclip = new OptionBoolean(this, "NoClip", true);
        CameraClip.smooth = new OptionBoolean(this, "Smooth", true);
        CameraClip.distance = new OptionSlider(this, "Distance", 4.0f, 0.0f, 20.0f, OptionSlider.SliderType.M);
        CameraClip.x = new OptionSlider(this, "X", 0.0f, -5.0f, 5.0f, OptionSlider.SliderType.M);
        CameraClip.y = new OptionSlider(this, "Y", 0.0f, -5.0f, 5.0f, OptionSlider.SliderType.M);
        CameraClip.z = new OptionSlider(this, "Z", 0.0f, -5.0f, 5.0f, OptionSlider.SliderType.M);
        this.addOptions(CameraClip.noclip, CameraClip.smooth, CameraClip.distance, CameraClip.x, CameraClip.y, CameraClip.z);
    }
}

package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class ViewModel extends Feature
{
    public static OptionSlider leftx;
    public static OptionSlider rightx;
    public static OptionSlider shieldHeight;
    public static OptionSlider lefty;
    public static OptionSlider righty;
    public static OptionBoolean lowShield;
    public static OptionSlider rightz;
    public static OptionSlider leftz;
    
    public ViewModel() {
        super("ViewModel", "\u0418\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u043f\u043e\u043b\u043e\u0436\u0435\u043d\u0438\u0435 \u0440\u0443\u043a.", Category.VISUAL);
        ViewModel.leftx = new OptionSlider(this, "Left X", 0.0f, -3.0f, 3.0f, OptionSlider.SliderType.NULL);
        ViewModel.lefty = new OptionSlider(this, "Left Y", 0.0f, -3.0f, 3.0f, OptionSlider.SliderType.NULL);
        ViewModel.leftz = new OptionSlider(this, "Left Z", -0.3f, -3.0f, 3.0f, OptionSlider.SliderType.NULL);
        ViewModel.rightx = new OptionSlider(this, "Right X", 0.0f, -3.0f, 3.0f, OptionSlider.SliderType.NULL);
        ViewModel.righty = new OptionSlider(this, "Right Y", 0.0f, -3.0f, 3.0f, OptionSlider.SliderType.NULL);
        ViewModel.rightz = new OptionSlider(this, "Right Z", 0.0f, -3.0f, 3.0f, OptionSlider.SliderType.NULL);
        ViewModel.lowShield = new OptionBoolean(this, "Low Shield", false);
        ViewModel.shieldHeight = new OptionSlider(this, "Shield Height", 0.5f, -2.0f, 2.0f, OptionSlider.SliderType.NULL);
        this.addOptions(ViewModel.leftx, ViewModel.lefty, ViewModel.leftz, ViewModel.rightx, ViewModel.righty, ViewModel.rightz, ViewModel.lowShield, ViewModel.shieldHeight);
    }
    
    @Override
    public void updateElements() {
        ViewModel.shieldHeight.display = ViewModel.lowShield.enabled;
        super.updateElements();
    }
}

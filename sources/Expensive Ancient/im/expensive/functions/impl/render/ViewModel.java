package im.expensive.functions.impl.render;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.SliderSetting;

@FunctionRegister(name = "ViewModel", type = Category.Render)
public class ViewModel extends Function {
    public final SliderSetting right_x = new SliderSetting("RightX", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting right_y = new SliderSetting("RightY", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting right_z = new SliderSetting("RightZ", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting left_x = new SliderSetting("LeftX", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting left_y = new SliderSetting("LeftY", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting left_z = new SliderSetting("LeftZ", 0.0F, -2.0f, 2.0f, 0.1F);

    public ViewModel() {
        addSettings(right_x, right_y, right_z, left_x, left_y, left_z);
    }
}

package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class CustomModel extends Feature
{
    public static OptionMode model;
    
    public CustomModel() {
        super("CustomModel", "\u0418\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u043c\u043e\u0434\u0435\u043b\u0438 \u0438\u0433\u0440\u043e\u043a\u043e\u0432.", Category.VISUAL);
        CustomModel.model = new OptionMode(this, "Model", "Rabbit", new String[] { "Rabbit", "Sonic" }, 0);
        this.addOptions(CustomModel.model);
    }
}

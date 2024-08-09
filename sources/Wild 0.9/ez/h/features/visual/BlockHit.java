package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class BlockHit extends Feature
{
    public static OptionBoolean thirdPerson;
    public static OptionMode mode;
    
    public BlockHit() {
        super("BlockHit", "\u0411\u043b\u043e\u043a\u0438\u0440\u0443\u0435\u0442 \u043c\u0435\u0447\u043e\u043c \u0432\u043c\u0435\u0441\u0442\u043e \u0449\u0438\u0442\u0430 \u043a\u0430\u043a \u043d\u0430 1.8 \u0432\u0435\u0440\u0441\u0438\u0438.", Category.VISUAL);
        BlockHit.thirdPerson = new OptionBoolean(this, "Third Person", true);
        this.addOptions(BlockHit.thirdPerson);
    }
}

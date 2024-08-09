package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class ClientCape extends Feature
{
    public static OptionBoolean onlySelf;
    
    public ClientCape() {
        super("ClientCape", "\u0420\u0435\u043d\u0434\u0435\u0440\u0438\u0442 \u043f\u043b\u0430\u0449 \u043d\u0430 \u0432\u0430\u0448\u0435\u043c \u0442\u0435\u043b\u0435 \u0441 \u043b\u043e\u0433\u043e\u0442\u0438\u043f\u043e\u043c \u043a\u043b\u0438\u0435\u043d\u0442\u0430.", Category.VISUAL);
        ClientCape.onlySelf = new OptionBoolean(this, "Only Self", false);
        this.addOptions(ClientCape.onlySelf);
    }
}

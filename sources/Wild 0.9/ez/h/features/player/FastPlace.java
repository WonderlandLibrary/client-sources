package ez.h.features.player;

import ez.h.features.*;

public class FastPlace extends Feature
{
    @Override
    public void onDisable() {
        FastPlace.mc.as = 4;
    }
    
    public FastPlace() {
        super("FastPlace", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0437\u0430\u0434\u0435\u0440\u0436\u043a\u0443 \u043f\u0440\u0438 \u043d\u0430\u0436\u0430\u0442\u0438\u0438 \u041f\u041a\u041c.", Category.PLAYER);
    }
}

package Focus.Beta.IMPL.Module.impl.focus;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;

public class Cape extends Module {

    public Mode<Enum> mode = new Mode<>("Modes", "Modes", Styles.values(), Styles.Focus1);
    public Cape(){
        super("Cape", new String[0], Type.FOCUS, "No");
        this.addValues(mode);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        if(mode.getModeAsString().equalsIgnoreCase("Focus1"))
            setSuffix("1");
        else if(mode.getModeAsString().equalsIgnoreCase("Focus2"))
            setSuffix("2");

    }
    enum Styles{
        Focus1, Focus2
    }


}

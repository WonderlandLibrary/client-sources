package Focus.Beta.IMPL.Module.impl.focus;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.render.EventRender2D;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;

public class FocusBot extends Module {
    public FocusBot(){
        super("FocusBot", new String[0], Type.FOCUS, "No");
    }
    
    public void onEnable() {
this.mc.displayGuiScreen(new Focus.Beta.API.GUI.beta.FocusBot.FocusBot());
this.setEnabled(false);
    }
}

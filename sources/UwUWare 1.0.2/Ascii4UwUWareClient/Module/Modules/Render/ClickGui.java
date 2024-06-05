package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.UI.ClickUi.ClickUi;
import Ascii4UwUWareClient.UI.Hanabi.NewClickGui;
import Ascii4UwUWareClient.UI.clickguisex.MainWindow;

public class ClickGui extends Module {
	private Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[]) Uimode.values(), (Enum) Uimode.Normal);


	public ClickGui() {
        super("ClickGui", new String[]{"clickui"}, ModuleType.Render);
        addValues(mode);
    }


    @Override
    public void onEnable() {
        setSuffix(mode.getModeAsString());

        switch (mode.getModeAsString()){
            case "CSGO":
                this.mc.displayGuiScreen(new NewClickGui());
                this.setEnabled(false);
                break;
            case "Normal":
                this.mc.displayGuiScreen ( new ClickUi () );
                this.setEnabled ( false );
                break;
            case "Fiux":
                this.mc.displayGuiScreen ( new MainWindow() );
                this.setEnabled ( false );
                break;
        }


    }

    enum Uimode {
        CSGO, Normal, Fiux
    }
}

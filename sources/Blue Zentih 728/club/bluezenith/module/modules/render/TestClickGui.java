package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.ui.clickgui.IntellijClickGui;

@SuppressWarnings("unused")
public class TestClickGui extends Module {
    public static IntellijClickGui click = null;
    public TestClickGui() {
        super("TestClickGui", ModuleCategory.RENDER);
    }

    @Override
    public void toggle(){
        if(!state){
            onEnable();
        }
    }

    @Override
    public Module setState(boolean sex){
        if(sex != state){
            toggle();
        }
        this.state = false;
        return this;
    }

    @Override
    public void onEnable(){
        if(click == null) click = new IntellijClickGui();
        mc.displayGuiScreen(click);
    }
}

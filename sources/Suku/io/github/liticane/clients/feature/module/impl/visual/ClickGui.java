package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.util.interfaces.IMethods;
import io.github.liticane.clients.ui.imgui.ImGuiScreen;

@Module.Info(name = "ClickGui", category = Module.Category.VISUAL, keyBind = 54)
public class ClickGui extends Module {

    @Override
    protected void onEnable() {
        System.out.println("www");
        IMethods.mc.display(new ImGuiScreen());
        toggle();
    }

}

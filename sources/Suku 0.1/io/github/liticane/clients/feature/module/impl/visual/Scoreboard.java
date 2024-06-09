package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;

@Module.Info(name = "Scoreboard", category = Module.Category.VISUAL)
public class Scoreboard extends Module {
    private BooleanProperty name = new BooleanProperty("Server Name",this,false);
    private BooleanProperty y = new BooleanProperty("Y offset",this,false);
}

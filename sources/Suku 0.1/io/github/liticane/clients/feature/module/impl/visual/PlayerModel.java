package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;

@Module.Info(name = "PlayerModule", category = Module.Category.VISUAL)
public class PlayerModel extends Module {
    public NumberProperty angle = new NumberProperty("Angle", this, 90, 1, 360, 1);
    public NumberProperty sizeX = new NumberProperty("Size X", this, 1, 0, 50, 1);
    public NumberProperty sizeY = new NumberProperty("Size Y", this, 1, 0, 50, 1);
    public NumberProperty sizeZ = new NumberProperty("Size Z", this, 1, 0, 50, 1);
    public BooleanProperty bat = new BooleanProperty("Bat pose",this,false);
    public BooleanProperty omlyu = new BooleanProperty("Only Player",this,true);
}

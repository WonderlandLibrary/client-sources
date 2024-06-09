package io.github.liticane.clients.feature.module.impl.movement;

import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;

@Module.Info(name = "LegitScaffold", category = Module.Category.MOVEMENT)
public class LegitScaffold extends Module{
    //todo: finish legit scaf fold
    public NumberProperty delay = new NumberProperty("Delay", this, 1, 0, 6, 1);
    public BooleanProperty rots = new BooleanProperty("Rotations",this,false);
    public BooleanProperty swing = new BooleanProperty("Swing",this,false);
    public BooleanProperty sprint = new BooleanProperty("Sprint",this,false);
    public BooleanProperty switchitem = new BooleanProperty("Switch Item",this,false);
}

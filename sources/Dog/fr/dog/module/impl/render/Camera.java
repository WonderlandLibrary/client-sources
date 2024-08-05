package fr.dog.module.impl.render;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.camera.CameraHurtEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;


public class Camera extends Module {
    public Camera() {
        super("Camera", ModuleCategory.RENDER);
        this.registerProperties(noShake, trw,nofire);
    }

    private final BooleanProperty noShake = BooleanProperty.newInstance("No Shake", false);
    public final BooleanProperty trw = BooleanProperty.newInstance("Through Wall",false);
    public final BooleanProperty nofire = BooleanProperty.newInstance("No Fire",false);


    @SubscribeEvent
    private void onHurt(CameraHurtEvent event){
        if(noShake.getValue())
            event.setCancelled(true);
    }

}

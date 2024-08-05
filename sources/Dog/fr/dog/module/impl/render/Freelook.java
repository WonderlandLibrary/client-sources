package fr.dog.module.impl.render;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Freelook extends Module {
    public Freelook() {
        super("Freelook", ModuleCategory.RENDER);
        this.registerProperty(autoF5);

    }


    private BooleanProperty autoF5 = BooleanProperty.newInstance("Auto F5", true);


    private float renderRotationYaw = 0;
    private float renderRotationPitch = 0;
    private float lastRenderRotationYaw = 0;
    private float lastRenderRotationPitch = 0;

    public void onEnable(){
        renderRotationYaw = mc.thePlayer.rotationYaw;
        renderRotationPitch = mc.thePlayer.rotationPitch;
        lastRenderRotationYaw = mc.thePlayer.prevRotationYaw;
        lastRenderRotationPitch = mc.thePlayer.prevRotationPitch;
    }

    public void onDisable(){
        if(autoF5.getValue()){
            mc.gameSettings.thirdPersonView = 0;
        }
    }

    @SubscribeEvent
    private void onTick(PlayerTickEvent event){
        if(autoF5.getValue()){
            mc.gameSettings.thirdPersonView = 1;
        }
    }

    // remember, left alt is "lmenu"


    public float[] getRenderRotations(){
        if(this.isEnabled()){
            return new float[]{renderRotationYaw, renderRotationPitch, lastRenderRotationYaw, lastRenderRotationPitch};
        }else{
            return new float[]{mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.prevRotationYaw, mc.thePlayer.prevRotationPitch};
        }
    }
}

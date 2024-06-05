package digital.rbq.module.implement.Render;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.util.ResourceLocation;
import digital.rbq.event.UIRenderEvent;
import digital.rbq.gui.clickgui.classic.RenderUtil;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.utility.AnimationUtils;
import digital.rbq.module.value.BooleanValue;
import digital.rbq.module.value.FloatValue;
import digital.rbq.module.value.ModeValue;

public class Pendant extends Module {

    public static ModeValue Fubukistyle = new ModeValue("Pendant","Fubukistyle","GIF","Static");

    public static BooleanValue Taco = new BooleanValue("Pendant","Taco",false);
    public static BooleanValue Fubuki = new BooleanValue("Pendant","Fubuki",false);
    public static FloatValue positionY = new FloatValue("Pendant","PositionY",130f,0f,1000f,5f);
    public static FloatValue positionX = new FloatValue("Pendant","PositionX",40f,0f,1000f,5f);
    public static FloatValue size = new FloatValue("Pendant","Size",100f,10f,500f,1f);


    float posX = 0;
    public Pendant() { super("Pendant", Category.Render , false); }

    @EventTarget
    public void flat(UIRenderEvent event) {

        if (Taco.getValue()){
            Taco();
        }

        if (Fubuki.getValue()){
            Fubuki();
        }

    }

    public void Fubuki(){

        if (Fubukistyle.isCurrentMode("GIF")){
            int state = (mc.thePlayer.ticksExisted % 16) + 1;
            RenderUtil.drawImage(new ResourceLocation("Pendant/fubuki/" + state + ".png"),positionX.getValue().intValue() , RenderUtil.height() - positionY.getValue().intValue(), size.getValue().intValue(), size.getValue().intValue());
        }

        if (Fubukistyle.isCurrentMode("Static")){
            RenderUtil.drawImage(new ResourceLocation("Pendant/fubuki/Static.png"),positionX.getValue().intValue() , RenderUtil.height() - positionY.getValue().intValue(), 77 ,250);
        }
    }

    public void Taco(){
        if (posX < RenderUtil.width()) {
            posX += AnimationUtils.delta * 0.1;
        } else {
            posX = 0;
        }
        int state = (mc.thePlayer.ticksExisted % 12) + 1;
        RenderUtil.drawImage(new ResourceLocation("Pendant/taco/" + state + ".png"), posX, RenderUtil.height() - 80, 42, 27);
    }


}

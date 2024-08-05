package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.render.Render3DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.model.ESPUtil;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

import java.awt.*;

public class ChestESP extends Module {
    public ChestESP() {
        super("ChestESP", ModuleCategory.RENDER);
        this.registerProperties(syncTheme, enderChest);
    }


    private final BooleanProperty syncTheme = BooleanProperty.newInstance("Sync Theme", false);
    private final BooleanProperty enderChest = BooleanProperty.newInstance("Ender Chest", true);




    @SubscribeEvent
    private void onRender3d(Render3DEvent event){
        mc.theWorld.loadedTileEntityList.forEach(t->{
            if(t instanceof TileEntityChest tileEntityChest){
                Color color = new Color(255, 191, 20);
                if(syncTheme.getValue()){
                    color = Dog.getInstance().getThemeManager().getCurrentTheme().color1;
                }
                ESPUtil.filledBlockESP(tileEntityChest.getPos(), color, 0.25F);
            }
            if(t instanceof TileEntityEnderChest tileEntityChest && enderChest.getValue()){
                Color color = new Color(141, 20, 255);
                if(syncTheme.getValue()){
                    color = Dog.getInstance().getThemeManager().getCurrentTheme().color2;
                }
                ESPUtil.filledBlockESP(tileEntityChest.getPos(), color, 0.25F);
            }
        });


    // RAHHHHHHHH INSANE AMOUNT OF SHIT CODE RAHHHHHH
    }


}

package zamorozka.modules.VISUALLY;

import java.awt.Color;


import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.Render2DEvent;
import zamorozka.ui.Render3DEvent;
import zamorozka.ui.RenderUtility;
import zamorozka.ui.RenderUtils;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingUtils;

public class BlockEsp extends Module
{
    public BlockEsp()
    {
        super("BlockEsp", 0, Category.VISUALLY);
    }
    @Override
    public void setup() {
    	Zamorozka.settingsManager.rSetting(new Setting("EnderChest", this, true));
    	Zamorozka.settingsManager.rSetting(new Setting("SpawnerEsp", this, true));
    	Zamorozka.settingsManager.rSetting(new Setting("ShulkerEsp", this, true));
    	Zamorozka.settingsManager.rSetting(new Setting("StoveEsp", this, true));
    }
    public void onRender()
    {
        if (this.getState())
        {
                for (Object o : mc.world.loadedTileEntityList)
                {
                	if(Zamorozka.settingsManager.getSettingByName("SpawnerEsp").getValBoolean()){
	                    if (o instanceof TileEntityMobSpawner)
	                    {
	                    	RenderUtils.drawboxESP5(((TileEntityMobSpawner) o).getPos(), Color.BLACK);
	                    }
                }
                	if(Zamorozka.settingsManager.getSettingByName("ShulkerEsp").getValBoolean()){
                        if (o instanceof TileEntityShulkerBox) {}
                }
                	if(Zamorozka.settingsManager.getSettingByName("StoveEsp").getValBoolean()){
                        if (o instanceof TileEntityFurnace)
                        {
                        	RenderUtils2.blockEspBox(((TileEntityFurnace) o).getPos(), 	0.4f, 0.6f, 1.0f);
                        }
                	}
        	}
        }
    }
    
    
}
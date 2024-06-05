package zamorozka.modules.VISUALLY;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtils;

public class Wallhack extends Module{

	public Wallhack() {
		super("Alfa-Wallhack", 0, Category.VISUALLY);
	}
	
	private final ArrayList<EntityPlayer> players = new ArrayList();
    public void onRender()
    {
        if (this.getState()){
        
	        		GL11.glDisable(2929);
        }
    }
}
    
 

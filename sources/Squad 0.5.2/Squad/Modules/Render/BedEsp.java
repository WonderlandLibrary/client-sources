package Squad.Modules.Render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;

public class BedEsp extends Module{

	Block b;
	
	public BedEsp() {
		super("BedESP", Keyboard.KEY_NONE, 0x88, Category.Render);
		// TODO Auto-generated constructor stub
	}
	
	
	@EventTarget
	public void onUpdate(EventUpdate e){
    int width = 30;
    for(int x = width; x >= -width; x--){
    	for(int y = width; y >= -width; y--){
    		for (int z = width; z >= -width; z--) {
    			int xx = (int) mc.thePlayer.posX + x;
    			int yy = (int) mc.thePlayer.posY + y;
    			int zz = (int) mc.thePlayer.posZ + z;
    			 if(b == Block.getBlockById(26)){
    					double renderX = xx - RenderManager.renderPosX;
						double renderY = yy - RenderManager.renderPosY;
						double renderZ = zz - RenderManager.renderPosZ;
    		 GL11.glPushMatrix();
    		 GL11.glTranslated(renderX, renderY, renderZ);
    		 GL11.glColor3d(0, 0, 1);
    		 GL11.glPopMatrix();
    		
    		 
    		
   
    				
    			}
    		}
    	}
    }
			
			}

}

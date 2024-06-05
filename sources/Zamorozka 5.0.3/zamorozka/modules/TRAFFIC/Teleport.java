package zamorozka.modules.TRAFFIC;

import org.lwjgl.input.Mouse;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtils;

public class Teleport extends Module{
	
	
	public Teleport(){
		super("Teleport", 0, Category.Exploits);
	}
	
	public void onUpdate(){
		if(!this.getState()){
			return;
		}

		BlockPos tpc = mc.objectMouseOver.getBlockPos();
		
        if(Mouse.isButtonDown(0)){
        	for(int i=0; i<10 ;i++){
            	int x1 = (int) (tpc.getX()-mc.player.posX);
            	int y1 = (int) (tpc.getY()-mc.player.posY);
            	int z1 = (int) (tpc.getZ()-mc.player.posZ);
            	
            	mc.player.setPosition(tpc.getX(), tpc.getY(), tpc.getZ());
            	mc.player.connection.sendPacket(new CPacketPlayer.Position(x1, y1, z1, true));
            	mc.player.connection.sendPacket(new CPacketPlayer.Position(x1, y1, z1, false));
            	mc.timer.timerSpeed=100;
        	}
        }
		
	}
	
	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		super.onDisable();
	}
}
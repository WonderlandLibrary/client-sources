package Squad.Modules.World;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.Utils.BlockData;
import Squad.Utils.EntityHelper;
import Squad.Utils.TimeHelper;
import Squad.Utils.Wrapper;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Tower extends Module {
	
	private int waitDelay = 0;

	public Tower() {
		super("Tower", Keyboard.KEY_X, 0x15CFA7, Category.World);
	}
    TimeHelper time = new TimeHelper();
    public static float yaw;
    public static float pitch;
    int sneakdelay = 0;
    private BlockData blockData = null;
    public static boolean isenabled = false;
	public static String mode = "NCP";
	double groundy = 0;


    public void onEnable() {
        this.blockData = null;
    }
    
    
    
    
    
    public void setup() {
	 	ArrayList<String> options = new ArrayList<>();
 		options.add("NCPtower");
 		options.add("Legit");
 Squad.instance.setmgr.rSetting(new Setting("TowerMode", this, "NCPtower", options));
	}

        @EventTarget
        public void onUpdate(EventUpdate e) {
        	if(Squad.instance.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("NCPtower")){
				setDisplayname("Tower §7NCP");
				Wrapper.getMC().rightClickDelayTimer = 0;
				mc.thePlayer.motionY = 0.3;
				mc.gameSettings.keyBindUseItem.pressed = true;
        	mc.thePlayer.rotationPitch = 90;
        	mc.thePlayer.rotationYaw = 90;
        	mc.gameSettings.keyBindJump.pressed = true;
        	}
        	
        	if(Squad.instance.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("Legit")){
				setDisplayname("Tower §7Legit");
				if(mc.thePlayer.onGround){
		        	mc.thePlayer.rotationPitch = 90;
		        	mc.thePlayer.rotationYaw = 90;
			mc.thePlayer.jump();
			mc.gameSettings.keyBindUseItem.pressed = true;
				}
			
        	}
        	
        	}

        	
        	
        
            


            

            

        	
 	
        
        
        public void ncp() {
        this.blockData = null;
        try {
        	if ((mc.thePlayer.getHeldItem() != null) && (!mc.thePlayer.isSneaking()) &&
                    ((mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))) { 
                BlockPos blockBelow = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
                if (mc.theWorld.getBlockState(blockBelow).getBlock() == Blocks.air) {
                    this.blockData = getBlockData(blockBelow);

                    if (this.blockData != null) {
                    	if(Squad.instance.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("NCPtower")) {
                    	mc.thePlayer.jump();
                        float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
                        mc.thePlayer.swingItem();
                        mc.thePlayer.onGround = true;
                        final EntityPlayerSP tp1 = mc.thePlayer;
                        tp1.motionX *= 0.6;
                        mc.thePlayer.onGround = false;
                        final EntityPlayerSP tp2 = mc.thePlayer;
                        tp2.motionZ *= 0.6;
                        mc.thePlayer.onGround = true;
                    }
                    }
                        
                     
                    
                    }
                }
            
        } catch (Exception exe) {}
        

        {
            if (this.blockData == null) {
                return;
            }

            {

                {
                    float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
   
                    mc.thePlayer.swingItem();
                    sneakdelay = 1;
                    try {
                    	if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
                    		if(Squad.instance.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("NCPtower")) {
                    			mc.thePlayer.jump();
                                                                                    mc.thePlayer.swingItem();
                                                                                    mc.thePlayer.swingItem();
                                                                                    mc.thePlayer.onGround = true;
                                                                                    final EntityPlayerSP tp1 = mc.thePlayer;
                                                                                    tp1.motionX *= 0.9;
                                                                                    mc.thePlayer.onGround = false;
                                                                                    final EntityPlayerSP tp2 = mc.thePlayer;
                                                                                    tp2.motionZ *= 0.9;
                                                                                    mc.thePlayer.onGround = true;

                                
                                
                    		}
                        }
                    } catch (Exception ex) {}
                }
            }

            {
                mc.thePlayer.swingItem();

                sneakdelay = 0;
                mc.thePlayer.motionX *= 0.1;
                mc.thePlayer.motionZ *= 0.1;
                mc.thePlayer.swingItem();
                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {                
                	if(Squad.instance.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("NCPtower")) {           
                	mc.thePlayer.swingItem();  
                                                                            mc.thePlayer.swingItem();
                                                                            mc.thePlayer.swingItem();
                                                                            mc.thePlayer.swingItem();
                                                                            mc.thePlayer.swingItem();
                                                                            mc.thePlayer.swingItem();
                                                                            mc.timer.timerSpeed = 1.5F;
                	}
                }
            }
        }

        if (this.blockData == null) {
            return;
        }
        float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);

        mc.thePlayer.onGround = true;

        mc.thePlayer.swingItem();

    }


    public BlockData getBlockData(BlockPos pos) {
        if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }

    public void onDisable() {
    	mc.gameSettings.keyBindJump.pressed = false;
    	mc.timer.timerSpeed = 1;
		mc.gameSettings.keyBindUseItem.pressed = false;

    }
    
   
}
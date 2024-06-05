package zamorozka.modules.COMBAT;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.BlockUtils;

public class Crystalaura extends Module{
	private Entity o;
	public Crystalaura() {
		super("CrystalAura", 0, Category.COMBAT);
	}
    @Override
    public void setup() {
        Zamorozka.settingsManager.rSetting(new Setting("CryDistance", this, 3.70D, 0, 7, true));
        Zamorozka.settingsManager.rSetting(new Setting("CrystalWall", this, false));
        Zamorozka.settingsManager.rSetting(new Setting("AutoPut", this, false));
        Zamorozka.settingsManager.rSetting(new Setting("AutoSidian", this, false));
    }
    private int xPos;
    private int yPos;
    private int zPos;
    private static int radius = 7;
	public void onUpdate(){
		if(!getState()){
			return;
		}
		if(mc.player.getCooledAttackStrength(0) >= 1)
		{
			for(Object o: mc.world.loadedEntityList)
			{
				Entity e = (Entity) o;
				if(Zamorozka.settingsManager.getSettingByName("CrystalWall").getValBoolean()){
					if(e instanceof EntityEnderCrystal && mc.player.getDistanceToEntity(e) <= Zamorozka.settingsManager.getSettingByName("CryDistance").getValDouble()) 
					{
							mc.player.setSprinting(false);
							mc.playerController.attackEntity(mc.player, e);
							mc.player.swingArm(EnumHand.MAIN_HAND);	
					}
				}
				else{
					if(e instanceof EntityEnderCrystal && mc.player.getDistanceToEntity(e) <= Zamorozka.settingsManager.getSettingByName("CryDistance").getValDouble()&& mc.player.canEntityBeSeen(e)) 
					{
							mc.player.setSprinting(false);
							mc.playerController.attackEntity(mc.player, e);
							mc.player.swingArm(EnumHand.MAIN_HAND);	
						}				
					}	
				if(Zamorozka.settingsManager.getSettingByName("AutoPut").getValBoolean()){
					for(int x = - radius; x < radius; x++) {
						for(int y = radius; y > - radius; y--) {
							for(int z = - radius; z < radius; z++) {
								this.xPos = (int)mc.player.posX + x;
								this.yPos = (int)mc.player.posY + y;
								this.zPos = (int)mc.player.posZ + z;
								ItemStack items = new ItemStack(Item.getItemById(426));
								BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
								
								Block block = mc.world.getBlockState(blockPos).getBlock(); 
								ItemStack itemstack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
								if(mc.player.posY <= yPos+1){
									if(Block.getIdFromBlock(block) == 49 ||Block.getIdFromBlock(block) == 7){
										if(itemstack.isItemEqual(items)){
									          mc.playerController.processRightClickBlock(mc.player, mc.world, blockPos, EnumFacing.NORTH, Vec3d.ZERO, EnumHand.MAIN_HAND);
										}
									}	
								}
								}
							
						}
						}
					}
				if(Zamorozka.settingsManager.getSettingByName("AutoSidian").getValBoolean()){
					for(int x = - radius; x < radius; x++) {
						for(int y = radius; y > - radius; y--) {
							for(int z = - radius; z < radius; z++) {
								this.xPos = (int)mc.player.posX + x;
								this.yPos = (int)mc.player.posY + y;
								this.zPos = (int)mc.player.posZ + z;
								ItemStack items = new ItemStack(Item.getItemById(49));
								BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
								Block block = mc.world.getBlockState(blockPos).getBlock(); 
								ItemStack itemstack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
								 
								if(mc.player.posY <= yPos+1){
									if(Block.getIdFromBlock(block) == 0){
										if(itemstack.isItemEqual(items)){
									          mc.playerController.processRightClickBlock(mc.player, mc.world, blockPos, EnumFacing.NORTH, Vec3d.ZERO, EnumHand.MAIN_HAND);
										}
									}	
								}
								}
							
						}
						}
					}
				}

		}	}
	}


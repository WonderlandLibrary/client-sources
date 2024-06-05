package zamorozka.modules.ZAMOROZKA;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtils;

public class Zamorozka1 extends Module
{
	  public Zamorozka1()
	  {
	    super("Zamorozka", 0, Category.Zamorozka);
	  }
	  
		public void onRender() {
			if(getState()) {

				 for(int i = 1; i < 8; ++i) {
		              mc.world.spawnParticle(EnumParticleTypes.SPIT, mc.player.posX + Math.sin((double)i) * 0.6D, mc.player.posY + 0.2D, mc.player.posZ + Math.cos((double)i) * 0.3D, 0.0D, 0.0D, 0.0D);
		              this.mc.world.setBlockState(new BlockPos(this.mc.player.posX, this.mc.player.posY - 1.0D, this.mc.player.posZ), Blocks.SNOW.getDefaultState(), 2);
		              this.mc.world.setBlockState(new BlockPos(this.mc.player.posX +1, this.mc.player.posY - 1.0D, this.mc.player.posZ), Blocks.ICE.getDefaultState(), 5);
				 }
			}
		}
}


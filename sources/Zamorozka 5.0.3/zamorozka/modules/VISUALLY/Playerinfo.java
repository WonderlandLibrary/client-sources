package zamorozka.modules.VISUALLY;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtils;

public class Playerinfo extends Module
{
	
	  public Playerinfo()
	  {
	    super("Items", 0, Category.VISUALLY);
	  }
		
		@Override
		public void onRender() {
			if(getState()) {
	            for (EntityPlayer player :mc.world.playerEntities) {
	                if (player == null) continue;
	                if (player.deathTime > 0) continue;
	                if (player == mc.player) continue;
					double x = ((player.lastTickPosX + (player.posX - player.lastTickPosX)- RenderManager.renderPosX));
					double y = ((player.lastTickPosY + (player.posY - player.lastTickPosY)- RenderManager.renderPosY));
					double z = ((player.lastTickPosZ + (player.posZ - player.lastTickPosZ)- RenderManager.renderPosZ));
	                
					final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
					//GuiIngame.itemRenderer.renderItem(bookObj, null);
					//itemRenderer.renderItemIntoGUI(Items.BED, 1, 20);
					
	            }
			}
		}
		
	   

	    public static void drawItem(ItemStack itemstack, int i, int j) 
		{

		}

	}
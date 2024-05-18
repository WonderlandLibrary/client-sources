package com.darkcart.xdolf.mods.render;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.fonts.Fonts;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Friend;
import com.darkcart.xdolf.util.RenderUtils;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Nametags extends Module {

	public Nametags() {
		super("Nametags", "Displays custom nametags.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
            for (EntityPlayer player : Wrapper.getWorld().playerEntities) {
                if (player == null) continue;
                if (player.deathTime > 0) continue;
                if (player == Wrapper.getPlayer()) continue;
				double x = ((player.lastTickPosX + (player.posX - player.lastTickPosX)
						- RenderManager.renderPosX));
				double y = ((player.lastTickPosY + (player.posY - player.lastTickPosY)
						- RenderManager.renderPosY));
				double z = ((player.lastTickPosZ + (player.posZ - player.lastTickPosZ)
						- RenderManager.renderPosZ));
                renderTag(player, x, y, z);
            }
		}
	}
	
    private void renderTag(Entity entity, double x, double y, double z) {
        String name = entity.getName();
        if(Wrapper.getFriends().isFriend(name)) {
        	name = "\2479" + name;
        }
        if (entity instanceof EntityLivingBase) {
        name = name + " \247a" + ((double)Math.round((((EntityLivingBase) entity).getHealth() * 100) / 100) / 2);
        }
        
        for(Friend friend: Wrapper.getFriends().friendsList) {
        	name.replace(friend.getName(), friend.getAlias());
        }
        float var13 = 1.6F;
        float var14 = (float) (0.016666668F * (Wrapper.getPlayer().getDistanceToEntity(entity)) / 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y + entity.height + 0.5F, (float) z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-var14, -var14, var14);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        Tessellator var15 = Tessellator.getInstance();
        VertexBuffer vertexbuffer = var15.getBuffer();
        int var16 = (int) -Wrapper.getPlayer().getDistanceToEntity(entity) / (int) var13;
        if (entity.isSneaking()) {
            var16 += 4;
        } else if (var16 < -14) {
            var16 = -14;
        }

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int var17 = Fonts.roboto18.getStringWidth(name) / 2;
        RenderUtils.drawBorderedRect(-var17 - 2, var16, var17 + 2, 11 + var16, 0.5F, 0xFF000000, 0x80000000);

        Fonts.roboto18.drawStringWithShadow(name, -var17, var16, 0xFFFFFF);
        
        Wrapper.getMinecraft().entityRenderer.disableLightmap();
        GL11.glLineWidth(1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        
        GL11.glPopMatrix();
    }
    
    
    public static void drawItem(ItemStack itemstack, int i, int j) 
	{
    	final RenderItem itemRenderer = Wrapper.getMinecraft().getRenderItem();
    	//TODO: FOR RENDERING BLOCKS IN PLAYERESP GuiIngame.itemRenderer.renderIcon(j, j, null, j, j);
		itemRenderer.renderItemIntoGUI(itemstack, i, j);
		itemRenderer.renderItemOverlayIntoGUI(Wrapper.getMinecraft().fontRendererObj, itemstack, i, j, null);
	    GL11.glDisable(2884 /* GL_CULL_FACE */);
	    GL11.glEnable(3008 /* GL_ALPHA_TEST */);
	    GL11.glDisable(3042 /* GL_BLEND */);
	    GL11.glDisable(2896 /* GL_LIGHTING */);
	    GL11.glDisable(2884 /* GL_CULL_FACE */);
	    GL11.glClear(256);
	}
}

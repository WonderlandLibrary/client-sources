package net.minecraft.client.renderer;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.util.Stopwatch;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import optifine.Config;
import optifine.DynamicLights;
import optifine.Reflector;
import shadersmod.client.Shaders;

public class ItemRenderer
{
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    private Stopwatch spinTimer = new Stopwatch();
    /** A reference to the Minecraft object. */
    private final Minecraft mc;
    private ItemStack itemToRender;

    /**
     * How far the current item has been equipped (0 disequipped and 1 fully up)
     */
    private float equippedProgress;
    private float prevEquippedProgress;
    private final RenderManager field_178111_g;
    private final RenderItem itemRenderer;

    /** The index of the currently held item (0-8, or -1 if not yet updated) */
    private int equippedItemSlot = -1;

    public ItemRenderer(Minecraft mcIn)
    {
        this.mc = mcIn;
        this.field_178111_g = mcIn.getRenderManager();
        this.itemRenderer = mcIn.getRenderItem();
    }

    public void renderItem(EntityLivingBase p_178099_1_, ItemStack p_178099_2_, ItemCameraTransforms.TransformType p_178099_3_)
    {
        if (p_178099_2_ != null)
        {
            Item var4 = p_178099_2_.getItem();
            Block var5 = Block.getBlockFromItem(var4);
            GlStateManager.pushMatrix();

            if (this.itemRenderer.func_175050_a(p_178099_2_))
            {
            	double size = 1.0D;
                GlStateManager.scale(2.0F * size, 2.0F * size, 2.0F * size);

                if (this.func_178107_a(var5))
                {
                    GlStateManager.depthMask(false);
                }
            }

            this.itemRenderer.func_175049_a(p_178099_2_, p_178099_1_, p_178099_3_);

            if (this.func_178107_a(var5))
            {
                GlStateManager.depthMask(true);
            }

            GlStateManager.popMatrix();
        }
    }

    private boolean func_178107_a(Block p_178107_1_)
    {
        return p_178107_1_ != null && p_178107_1_.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
    }

    private void func_178101_a(float p_178101_1_, float p_178101_2_)
    {
    	
        GlStateManager.pushMatrix();
        GlStateManager.rotate(p_178101_1_, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(p_178101_2_, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void func_178109_a(AbstractClientPlayer p_178109_1_)
    {
        int var2 = this.mc.theWorld.getCombinedLight(new BlockPos(p_178109_1_.posX, p_178109_1_.posY + (double)p_178109_1_.getEyeHeight(), p_178109_1_.posZ), 0);

        if (Config.isDynamicLights())
        {
            var2 = DynamicLights.getCombinedLight(this.mc.func_175606_aa(), var2);
        }

        float var3 = (float)(var2 & 65535);
        float var4 = (float)(var2 >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var3, var4);
    }

    private void func_178110_a(EntityPlayerSP p_178110_1_, float p_178110_2_)
    {
        float var3 = p_178110_1_.prevRenderArmPitch + (p_178110_1_.renderArmPitch - p_178110_1_.prevRenderArmPitch) * p_178110_2_;
        float var4 = p_178110_1_.prevRenderArmYaw + (p_178110_1_.renderArmYaw - p_178110_1_.prevRenderArmYaw) * p_178110_2_;
        GlStateManager.rotate((p_178110_1_.rotationPitch - var3) * 0.1F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((p_178110_1_.rotationYaw - var4) * 0.1F, 0.0F, 1.0F, 0.0F);
    }

    private float func_178100_c(float p_178100_1_)
    {
        float var2 = 1.0F - p_178100_1_ / 45.0F + 0.1F;
        var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
        var2 = -MathHelper.cos(var2 * (float)Math.PI) * 0.5F + 0.5F;
        return var2;
    }

    private void func_180534_a(RenderPlayer p_180534_1_)
    {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.25F, -0.85F, 0.75F);
        p_180534_1_.func_177138_b(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }

    private void func_178106_b(RenderPlayer p_178106_1_)
    {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-0.3F, -1.1F, 0.45F);
        p_178106_1_.func_177139_c(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }

    private void func_178102_b(AbstractClientPlayer p_178102_1_)
    {
        this.mc.getTextureManager().bindTexture(p_178102_1_.getLocationSkin());
        Render var2 = this.field_178111_g.getEntityRenderObject(this.mc.thePlayer);
        RenderPlayer var3 = (RenderPlayer)var2;

        if (!p_178102_1_.isInvisible())
        {
            this.func_180534_a(var3);
            this.func_178106_b(var3);
        }
    }

    private void func_178097_a(AbstractClientPlayer p_178097_1_, float p_178097_2_, float p_178097_3_, float p_178097_4_)
    {
        float var5 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float)Math.PI);
        float var6 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float)Math.PI * 2.0F);
        float var7 = -0.2F * MathHelper.sin(p_178097_4_ * (float)Math.PI);
        GlStateManager.translate(var5, var6, var7);
        float var8 = this.func_178100_c(p_178097_2_);
        GlStateManager.translate(0.0F, 0.04F, -0.72F);
        GlStateManager.translate(0.0F, p_178097_3_ * -1.2F, 0.0F);
        GlStateManager.translate(0.0F, var8 * -0.5F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var8 * -85.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        this.func_178102_b(p_178097_1_);
        float var9 = MathHelper.sin(p_178097_4_ * p_178097_4_ * (float)Math.PI);
        float var10 = MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float)Math.PI);
        GlStateManager.rotate(var9 * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var10 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(var10 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.38F, 0.38F, 0.38F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-1.0F, -1.0F, 0.0F);
        GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
        this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        Tessellator var11 = Tessellator.getInstance();
        WorldRenderer var12 = var11.getWorldRenderer();
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        var12.startDrawingQuads();
        var12.addVertexWithUV(-7.0D, 135.0D, 0.0D, 0.0D, 1.0D);
        var12.addVertexWithUV(135.0D, 135.0D, 0.0D, 1.0D, 1.0D);
        var12.addVertexWithUV(135.0D, -7.0D, 0.0D, 1.0D, 0.0D);
        var12.addVertexWithUV(-7.0D, -7.0D, 0.0D, 0.0D, 0.0D);
        var11.draw();
        MapData var13 = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);

        if (var13 != null)
        {
            this.mc.entityRenderer.getMapItemRenderer().func_148250_a(var13, false);
        }
    }

    private void func_178095_a(AbstractClientPlayer p_178095_1_, float p_178095_2_, float p_178095_3_)
    {
        float var4 = -0.3F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI);
        float var5 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI * 2.0F);
        float var6 = -0.4F * MathHelper.sin(p_178095_3_ * (float)Math.PI);
        GlStateManager.translate(var4, var5, var6);
        GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
        GlStateManager.translate(0.0F, p_178095_2_ * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float var7 = MathHelper.sin(p_178095_3_ * p_178095_3_ * (float)Math.PI);
        float var8 = MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float)Math.PI);
        GlStateManager.rotate(var8 * 70.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var7 * -20.0F, 0.0F, 0.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(p_178095_1_.getLocationSkin());
        GlStateManager.translate(-1.0F, 3.6F, 3.5F);
        GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(5.6F, 0.0F, 0.0F);
        Render var9 = this.field_178111_g.getEntityRenderObject(this.mc.thePlayer);
        RenderPlayer var10 = (RenderPlayer)var9;
        var10.func_177138_b(this.mc.thePlayer);
    }

    private void func_178105_d(float p_178105_1_)
    {
        float var2 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float)Math.PI);
        float var3 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float)Math.PI * 2.0F);
        float var4 = -0.2F * MathHelper.sin(p_178105_1_ * (float)Math.PI);
        GlStateManager.translate(var2, var3, var4);
    }

    private void func_178104_a(AbstractClientPlayer p_178104_1_, float p_178104_2_)
    {
        float var3 = (float)p_178104_1_.getItemInUseCount() - p_178104_2_ + 1.0F;
        float var4 = var3 / (float)this.itemToRender.getMaxItemUseDuration();
        float var5 = MathHelper.abs(MathHelper.cos(var3 / 4.0F * (float)Math.PI) * 0.1F);

        if (var4 >= 0.8F)
        {
            var5 = 0.0F;
        }

        GlStateManager.translate(0.0F, var5, 0.0F);
        float var6 = 1.0F - (float)Math.pow((double)var4, 27.0D);
        GlStateManager.translate(var6 * 0.6F, var6 * -0.5F, var6 * 0.0F);
        GlStateManager.rotate(var6 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(var6 * 30.0F, 0.0F, 0.0F, 1.0F);
    }

    private void func_178096_b(float p_178096_1_, float p_178096_2_)
    {
        GlStateManager.translate(0.56F, (Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Cool") && mc.thePlayer.isBlocking()) ? -0.42F : -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, p_178096_1_ * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float var3 = MathHelper.sin(p_178096_2_ * p_178096_2_ * (float)Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * (float)Math.PI);
        GlStateManager.rotate(var3 * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
        double size = Xatz.getModuleByName("Camera").getNumberSetting("ItemSize").getValue() / 2.5;
        GlStateManager.scale(size, size, size);
    	/*GlStateManager.translate(0.56F, -0.42F, -0.71999997F);
        GlStateManager.translate(0.0F, p_178096_1_ * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float var3 = MathHelper.sin(p_178096_2_ * p_178096_2_ * (float) Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * (float) Math.PI);
        GlStateManager.rotate(var3 * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);*/
    }

    private void func_178098_a(float p_178098_1_, AbstractClientPlayer p_178098_2_)
    {
        GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.9F, 0.2F, 0.0F);
        float var3 = (float)this.itemToRender.getMaxItemUseDuration() - ((float)p_178098_2_.getItemInUseCount() - p_178098_1_ + 1.0F);
        float var4 = var3 / 20.0F;
        var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;

        if (var4 > 1.0F)
        {
            var4 = 1.0F;
        }

        if (var4 > 0.1F)
        {
            float var5 = MathHelper.sin((var3 - 0.1F) * 1.3F);
            float var6 = var4 - 0.1F;
            float var7 = var5 * var6;
            GlStateManager.translate(var7 * 0.0F, var7 * 0.01F, var7 * 0.0F);
        }

        GlStateManager.translate(var4 * 0.0F, var4 * 0.0F, var4 * 0.1F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F + var4 * 0.2F);
    }

    private void func_178103_d()
    {
    	float angle = (int) ((System.currentTimeMillis() / 1.5) % 360);
        angle =  (angle > 180 ? 360 - angle : angle) * 2;
        angle /= 180f;
        
        float angle2 = (int) ((System.currentTimeMillis() / 3.5) % 120);
        angle2 =  (angle2 > 30 ? 120 - angle2 : angle2) * 2;
        angle2 /= 1f;
        
        
        float angle3 = (int) ((System.currentTimeMillis() / 3.5) % 110);
        angle3 =  (angle3 > 30 ? 110 - angle3 : angle3) * 2;
        angle3 /= 1f;
        
    	int random1 = 0;
    	int random2 = 0;
    	int random3 = 0;
    	int random4 = 0;
    	float random5 = 0;
    	int random6 = 0;
    	int random7 = 0;
    	float random8 = 0;
    	int random10 = 0;
    	
    	String mode = Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().toString();
    	ArrayList idk = new ArrayList();
    	idk.add("Spin");
    	idk.add("Whack");
    	idk.add("Penis");
    	idk.add("UpDown");
    	
    	switch(mode) {
    	case "Spin":
    		random4 = (int) 0.2f;
    		random1 = (int) (System.currentTimeMillis() / 2 % 360);
    		random2 = 1;
    		random5 = 1;
    		random4 = -59;
    		random6 = -1;
    		random7 = 0;
    		random10 = 3;
    	break;
    	case "Whack":
    		random1 = 80;
    		random2 = 1;
    		random3 = 0;
    		random4 = (int) angle3;
    		random5 = 0;
    		random6 = 1;
    		random7 = 1;
    		random8 = -0.5f;
    		random10 = 0;
    		break;
    	case "UpDown":
    		random1 = (int) (System.currentTimeMillis() / 3 % 360);
    		random2 = 0;
    		random10 = 3;
		break;
    	case "Penis":
    		random4 = (int) 0.2f;
    		random1 = 80;
    		random2 = 1;
    		random5 = angle;
    		random4 = -59;
    		random6 = -1;
    		random7 = 0;
    		random8 = random5 - 2;
    		random10 = 3;
    		break;
    	}
    	if(mode != "Spin" && mode != "Whack" && mode != "UpDown" && mode != "Penis") {
    		random1 = 80;
    		random2 = 1;
    		random3 = 0;
    		random4 = 30;
    		random5 = 0;
    		random6 = 0;
    		random7 = 1;
    		random8 = -0.5f;
    		random10 = 0;
    	}
        /*if(mode.equalsIgnoreCase("Shotbowxd") {
            this.transformFirstPersonItem(f, f2 / 40.0f);
            this.func_178103_d();
            break Label_1045;
        }*/
    	
    	
    	//BLOCK ANIMATION STUFF
        GlStateManager.translate(random8, 0.2f, -random5);
        GlStateManager.rotate(random4, random6, random7, random10);
        GlStateManager.rotate(-random1, random2, random3, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    /**
     * Renders the active item in the player's hand when in first person mode. Args: partialTickTime
     */
    public void renderItemInFirstPerson(float p_78440_1_)
    {
        float var2 = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * p_78440_1_);
        EntityPlayerSP var3 = this.mc.thePlayer;
        float var4 = var3.getSwingProgress(p_78440_1_);
        float var8 = var4;
        float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * p_78440_1_;
        float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * p_78440_1_;
        this.func_178101_a(var5, var6);
        this.func_178109_a(var3);
        this.func_178110_a(var3, p_78440_1_);
        double size = Xatz.getModuleByName("Camera").getNumberSetting("ItemSize").getValue();
        //if(size > 0.5D && size < 1.3D) {
        //GL11.glScaled(1.7F, 1.7F, 2.2F);
        /*} else {
        	GL11.glScaled(1.7f, 1.7f, 1.7f);
        }
        */
        if(Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Spinny") && mc.thePlayer.isBlocking()) {
        	//float delay = this.spinTimer.getElapsedTime() / 5;
			//if(delay > 360) {
				//this.spinTimer.reset();
			//}
			//GlStateManager.rotate(delay, 0.0F, 100.0F, 0.0F);
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();

        if (this.itemToRender != null)
        {
            if (this.itemToRender.getItem() instanceof ItemMap)
            {
                this.func_178097_a(var3, var5, var2, var4);
            }
            else if (var3.getItemInUseCount() > 0)
            {
                EnumAction var7 = this.itemToRender.getItemUseAction();

                switch (ItemRenderer.SwitchEnumAction.field_178094_a[var7.ordinal()])
                {
                    case 1:
                        this.func_178096_b(var2, 0.0F);
                        break;

                    case 2:
                    case 3:
                        this.func_178104_a(var3, p_78440_1_);
                        this.func_178096_b(var2, 0.0F);
                        break;

                    case 4:
                    	float var15 =MathHelper.sin(var4 * var4 * 3.1415927F);
    					if(Xatz.getModuleByName("Animations").isEnabled()) {
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Vanilla"))) {
    							this.func_178096_b(var2 / 2f + 0.1f, var4);
    	    					this.func_178103_d();
    	    					GlStateManager.translate(-0.5, 0, 0);
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Slide"))) {
    							this.sigma2(var2*0.3f, var4);
                        		this.func_178103_d();
                        		//GL11.glRotatef(System.nanoTime() / 5500000, 0, 100, 0);
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Tap"))) {
    							this.tap(var2, var4);
                        		this.func_178103_d();
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Tap2"))) {
    							this.tap2(var2, var4);
                        		this.func_178103_d();
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Avatar"))) {
    							this.avatar(var2, var4);
                                this.func_178103_d();
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Sigma"))) {
    							this.func_178096_b(var2*0.5f, 0);                      	
                        		GlStateManager.rotate(-var15 * 55 / 2.0F, -8.0F, -0.0F, 9.0F);
                        		GlStateManager.rotate(-var15 * 45, 1.0F, var15 / 2, -0.0F);
                        		this.func_178103_d();
                        		GL11.glTranslated(1.2, 0.3, 0.5);
                                GL11.glTranslatef(-1, this.mc.thePlayer.isSneaking() ? -0.1F : -0.2F, 0.2F);
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Helium"))) {
    							 this.func_178096_b(var2, 1.0f);
    	                            this.func_178103_d();
    	                            GL11.glTranslatef(0.4f, 0.1f, -0.01f);
    	                            final float var16 = MathHelper.sin(var4 * var4 * 5.1415925f);
    	                            GlStateManager.translate(-0.1f, 0.3f, 0.2f);
    	                            GlStateManager.rotate(var16 * 13.0f, -10.0f, -1.4f, -10.0f);
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Xatz"))) {
    							this.func_178096_b(var2, 1.0f);
                                this.func_178103_d();
                                GL11.glTranslatef(0.4f, 0.3f, 0.3f);
                                final float var17 = MathHelper.sin(var4 * var4 * 5.1415925f);
                                GlStateManager.translate(-0.4f, -0.1f, -0.2f);
                                GlStateManager.rotate(var17 * 17.0f, -65.0f, -0.4f, -5.0f);
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Derp"))) {
    							 /*this.func_178096_b(var2, var4);
    	                            this.func_178103_d();
    	                            final float var18 = MathHelper.sin(var4 * var4 * 5.1415925f);
    	                            GL11.glTranslatef(0.1f, -0.1f, 0.3f);
    	                            GlStateManager.translate(0.1f, -0.1f, 0.4f);
    	                            GlStateManager.rotate(var18 * 523, 215.0f, 1.4f, 525.0f);*/
    							float delay2 = (this.spinTimer.getElapsedTime()) * mc.timer.timerSpeed;
    							if(delay2 > 360) {
    								this.spinTimer.reset();
    							}
    							GL11.glRotated(delay2, 0, 0, 100);
    							this.func_178096_b(var4, 0.6f);
    							GL11.glRotated(delay2, 100, 100, 100);
    							this.func_178103_d();
    							GL11.glRotated(delay2, 100, 100, 100);
    							float var90 = MathHelper.sin(MathHelper.sqrt_float(8.5f) * 3.1415927F);
    							GL11.glRotatef(var90 * 40f / 2.0F, var90 / 2.0F, -0.0F, 4.0F);
			                     GL11.glRotatef(var90 * 30f, 1.0F, var90 / 2f, -0.0F);
    							//GL11.glTranslatef(0.1f, -0.1f, 0.3f);
	                            GlStateManager.translate(-0.5f, -0.1f, 0.4f);
    							
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Derp2"))) {
    							if(!(var4 >= 20)) {
    							 this.func_178096_b(var2, var4);
    	                            this.func_178103_d();
    	                            float var9 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
    	                            GL11.glTranslatef(0.1f, -0.1f, 0.3f);
    	                            GlStateManager.translate(0.1f, -0.1f, 0.4f);
    	                            GlStateManager.rotate(var9 * 100f, 215.0f, 1.4f, 525.0f);
    							} else {
    								
    							}
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("HaxxerClient"))) {
    							float var9 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
    							this.func_178096_b(var2 / 2.0F, 1.5F);
    							GL11.glRotatef(-var9 * 40.0F / 2.0F, var9 / 2.0F, -0.0F, 9.0F);
    							GL11.glRotatef(-var9 * 30.0F, 1.0F, var9 / 2.0F, -0.0F);
    							this.func_178103_d();
    							
    						}
    						if(Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Exhi")) {
    							/*float var9 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
    							this.func_178096_b(var2 / 2.0F, 0.0F);
    							GL11.glTranslated(0D, 0.15D, 0D);
    		                     GL11.glRotatef(-var9 * 40.0F / 2.0F, var9 / 2.0F, -0.0F, 9.0F);
    		                     GL11.glRotatef(-var9 * 30.0F, 1.0F, var9 / 2.0F, -0.0F);
    		                     this.func_178103_d();*/
    							/*float var151;
    							this.func_178096_b(var2, 0.0F);
    			                  var151 = MathHelper.sin(var4 * var4 * 3.1415927F);
    			                  GL11.glTranslatef(0F, 0.55F, 0.0F);
    			                  GlStateManager.rotate(-var151 * 40.0F / 2.0F, var151 / 2.0F, -0.0F, 9.0F);
    			                  GlStateManager.rotate(-var151 * 30.0F, 1.0F, var151 / 2.0F, -0.0F);
    			                  this.func_178103_d();
    			                  GL11.glRotated(System.nanoTime() / 5500000, 100f, 0f, 0f);
    			                  break;*/
    							float var90 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
								GL11.glTranslated(-0.1D, 0.25D, 0.0D);
								
								float var100 = MathHelper.sin(MathHelper.sqrt_float(var4) * 4.5895123F);
								//GlStateManager.rotate(System.nanoTime() / 5500000, 0.0F, 0.0F, 100.0F);
								this.func_178096_b(var2 / 2, 0);
			                    GL11.glRotatef(-var90 * 40f / 2.0F, var90 / 2.0F, -0.0F, 4.0F);
			                     GL11.glRotatef(-var90 * 30f, 1.0F, var90 / 2f, -0.0F);
			                     
			                     this.func_178103_d();
			                     break;
    						}
    						if(Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Spinny")) {
    							//GL11.glTranslated(delay / 360, 0, 0);
    					        GL11.glScaled(1.7, 1.7, 2.5);
    							mc.timer.timerSpeed = 1.0f;
    							float delay = 180;
    							float delay2 = (this.spinTimer.getElapsedTime() / 5) * mc.timer.timerSpeed;
    							if(delay2 > 360) {
    								this.spinTimer.reset();
    							}
    							//GL11.glTranslated(0, 0.0D, -0.2D);
    							//GlStateManager.rotate(delay2, 0.0F, 0.0F, 100.0F);
    							//GL11.glTranslated(-0.49, 0.2, 0);
    							float down = var4 * 1.5f;
    							this.func_178096_b(var2, 0);
    							//mc.thePlayer.rotationYaw = delay2;
    							//GL11.glRotated(System.nanoTime() / 100000000, 0.1, 0, 0);
    							//GL11.glTranslated(0D, 0.65D, 0D);
    							//this.func_178103_d();
    							//GlStateManager.rotate(System.nanoTime() / 5500000, 0.0F, 0.0F, 100.0F);
    							//mc.thePlayer.cameraPitch = 0.105f;
    							//GlStateManager.rotate(delay, 0.0F, 100.0F, 0.0F);
    							//this.func_178103_d();
    							GL11.glRotated(delay2, 0, 100, 0);
    							//GlStateManager.rotate(delay + delay2, 0.0F, 100.0F, 0.0F);
    						}
    						if(Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Spin2")) {
    	                        final float ticksExisted = (float)this.mc.thePlayer.ticksExisted;
    	                        final float interpolatedAngle = ticksExisted - 1.0f + (ticksExisted - (ticksExisted - 1.0f)) * this.mc.timer.renderPartialTicks;
    	                        GlStateManager.translate(0.4f, -0.3f, -0.71999997f);
    	                        GlStateManager.translate(0.0f, -0.1f * -0.6f, 0.0f);
    	                        GlStateManager.rotate(40.0f, 0.0f, 1.0f, 0.0f);
    	                        GlStateManager.rotate(45.0f, -1.0f, 1.0f, -1.0f);
    	                        final float f5 = MathHelper.sin(var4 * var4 * 3.1415927f);
    	                        final float f6 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927f);
    	                        GlStateManager.rotate(0.0f, 0.0f, 1.0f, 0.0f);
    	                        GlStateManager.rotate(0.0f, -1.5f, 1.0f, 1.0f);
    	                        GlStateManager.rotate(-interpolatedAngle * 30.0f, 1.0f, -0.3f, -0.2f);
    	                        GlStateManager.scale(0.2f, 0.2f, 0.2f);
    						}
    						if(Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Spin") || Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Whack") || Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("UpDown") || Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Penis")) {
    	                        /*final float ticksExisted = (float)this.mc.thePlayer.ticksExisted;
    	                        final float interpolatedAngle = ticksExisted - 1.0f + (ticksExisted - (ticksExisted - 1.0f)) * this.mc.timer.renderPartialTicks;
    	                        GlStateManager.translate(0.4f, -0.3f, -0.71999997f);
    	                        GlStateManager.translate(0.0f, var2 * -0.6f, 0.0f);
    	                        GlStateManager.rotate(40.0f, 0.0f, 1.0f, 0.0f);
    	                        GlStateManager.rotate(45.0f, -1.0f, 1.0f, -1.0f);
    	                        final float f5 = MathHelper.sin(var4 * var4 * 3.1415927f);
    	                        final float f6 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927f);
    	                        GlStateManager.rotate(0.0f, 0.0f, 1.0f, 0.0f);
    	                        GlStateManager.rotate(0.0f, -1.5f, 1.0f, 1.0f);
    	                        GlStateManager.rotate(-interpolatedAngle * 30.0f, 1.0f, -0.3f, -0.2f);
    	                        GlStateManager.scale(0.2f, 0.2f, 0.2f);*/
    							this.func_178096_b(var2, 0.0F);
    		                    this.func_178103_d();
    						}
    						if((Xatz.getModuleByName("Animations").getModeSetting("Block Animation").getMode().equals("Cool"))) {
    							this.func_178096_b((var2 / 2f), var8);
                        		this.func_178103_d();
    						}
    					}else {
    					this.func_178096_b(var2 / 2f + 0.1f, var4);
    					this.func_178103_d();
    					GlStateManager.translate(-0.5, 0, 0);
    					}
                        break;

                    case 5:
                        this.func_178096_b(var2, var4);
                        this.func_178098_a(p_78440_1_, var3);
                }
            }
            else
            {
            	if(Xatz.getModuleByName("Animations").isEnabled()) {
    				if((Xatz.getModuleByName("Animations").getModeSetting("Hit Animation").getMode().equals("Smooth"))) {
    				 this.func_178096_b(var2*0.5F, var4);
    				}
    			
    			else if((Xatz.getModuleByName("Animations").getModeSetting("Hit Animation").getMode().equals("Vanilla"))) {
    				this.func_178105_d(var4);
    				this.func_178096_b(var2, var4);
    			}
    		}else {
    			this.func_178105_d(var4);
    			this.func_178096_b(var2, var4);
    		}
    			
            	
               
                if(Xatz.getModuleByName("Camera").getBooleanSetting("Spinny Items").isEnabled()) {
                	GlStateManager.rotate(System.nanoTime() / 10000000, 0.0F, 100.0F, 0.0F);
            	}
                this.spinTimer.reset();
            }

            this.renderItem(var3, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        }
        else if (!var3.isInvisible())
        {
            this.func_178095_a(var3, var2, var4);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }

    private void sigma2(float var2, float swingProgress) {
    	float smooth = (swingProgress*0.8f - (swingProgress*swingProgress)*0.8f);
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 2+smooth*0.5f, smooth*3);
        float var3 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(0f, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.37F, 0.37F, 0.37F);
    }
    private void tap(float var2, float swingProgress) {
    	float smooth = (swingProgress*0.8f - (swingProgress*swingProgress)*0.8f);
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F,  var2 * -0.15F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float var3 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(smooth * -90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.37F, 0.37F, 0.37F);
    }
    private void avatar(float equipProgress, float swingProgress){
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, 0, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
        GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -40.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }
    private void tap2(float var2, float swing) {
        float var3 = MathHelper.sin(swing * swing * (float) Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(swing) * (float) Math.PI);
        GlStateManager.translate(0.56F, -0.42F, -0.71999997F);
        GlStateManager.translate(0.0F,  var2 * -0.15F, 0.0F);
        GlStateManager.rotate(30 , 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(var3*-20 , 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var4 * -30.0F, 0.0F, 1.0F, 0.0F);
      //  GlStateManager.rotate(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
        //GlStateManager.rotate(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }
    
    /**
     * Renders all the overlays that are in first person mode. Args: partialTickTime
     */
    public void renderOverlays(float p_78447_1_)
    {
        GlStateManager.disableAlpha();

        if (this.mc.thePlayer.isEntityInsideOpaqueBlock())
        {
            BlockPos blockPos = new BlockPos(this.mc.thePlayer);
            IBlockState var2 = this.mc.theWorld.getBlockState(blockPos);
            EntityPlayerSP var3 = this.mc.thePlayer;

            for (int overlayType = 0; overlayType < 8; ++overlayType)
            {
                double var5 = var3.posX + (double)(((float)((overlayType >> 0) % 2) - 0.5F) * var3.width * 0.8F);
                double var7 = var3.posY + (double)(((float)((overlayType >> 1) % 2) - 0.5F) * 0.1F);
                double var9 = var3.posZ + (double)(((float)((overlayType >> 2) % 2) - 0.5F) * var3.width * 0.8F);
                blockPos = new BlockPos(var5, var7 + (double)var3.getEyeHeight(), var9);
                IBlockState var12 = this.mc.theWorld.getBlockState(blockPos);

                if (var12.getBlock().isVisuallyOpaque())
                {
                    var2 = var12;
                }
            }

            if (var2.getBlock().getRenderType() != -1)
            {
                Object var13 = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);

                if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, new Object[] {this.mc.thePlayer, Float.valueOf(p_78447_1_), var13, var2, blockPos}))
                {
                    this.func_178108_a(p_78447_1_, this.mc.getBlockRendererDispatcher().func_175023_a().func_178122_a(var2));
                }
            }
        }

        if (!this.mc.thePlayer.func_175149_v())
        {
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, new Object[] {this.mc.thePlayer, Float.valueOf(p_78447_1_)}))
            {
                this.renderWaterOverlayTexture(p_78447_1_);
            }

            if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, new Object[] {this.mc.thePlayer, Float.valueOf(p_78447_1_)}))
            {
                this.renderFireInFirstPerson(p_78447_1_);
            }
        }

        GlStateManager.enableAlpha();
    }

    private void func_178108_a(float p_178108_1_, TextureAtlasSprite p_178108_2_)
    {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Tessellator var3 = Tessellator.getInstance();
        WorldRenderer var4 = var3.getWorldRenderer();
        float var5 = 0.1F;
        GlStateManager.color(var5, var5, var5, 0.5F);
        GlStateManager.pushMatrix();
        float var6 = -1.0F;
        float var7 = 1.0F;
        float var8 = -1.0F;
        float var9 = 1.0F;
        float var10 = -0.5F;
        float var11 = p_178108_2_.getMinU();
        float var12 = p_178108_2_.getMaxU();
        float var13 = p_178108_2_.getMinV();
        float var14 = p_178108_2_.getMaxV();
        var4.startDrawingQuads();
        var4.addVertexWithUV((double)var6, (double)var8, (double)var10, (double)var12, (double)var14);
        var4.addVertexWithUV((double)var7, (double)var8, (double)var10, (double)var11, (double)var14);
        var4.addVertexWithUV((double)var7, (double)var9, (double)var10, (double)var11, (double)var13);
        var4.addVertexWithUV((double)var6, (double)var9, (double)var10, (double)var12, (double)var13);
        var3.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders a texture that warps around based on the direction the player is looking. Texture needs to be bound
     * before being called. Used for the water overlay. Args: parialTickTime
     */
    private void renderWaterOverlayTexture(float p_78448_1_)
    {
        this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
        Tessellator var2 = Tessellator.getInstance();
        WorldRenderer var3 = var2.getWorldRenderer();
        float var4 = this.mc.thePlayer.getBrightness(p_78448_1_);
        GlStateManager.color(var4, var4, var4, 0.5F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        float var5 = 4.0F;
        float var6 = -1.0F;
        float var7 = 1.0F;
        float var8 = -1.0F;
        float var9 = 1.0F;
        float var10 = -0.5F;
        float var11 = -this.mc.thePlayer.rotationYaw / 64.0F;
        float var12 = this.mc.thePlayer.rotationPitch / 64.0F;
        var3.startDrawingQuads();
        var3.addVertexWithUV((double)var6, (double)var8, (double)var10, (double)(var5 + var11), (double)(var5 + var12));
        var3.addVertexWithUV((double)var7, (double)var8, (double)var10, (double)(0.0F + var11), (double)(var5 + var12));
        var3.addVertexWithUV((double)var7, (double)var9, (double)var10, (double)(0.0F + var11), (double)(0.0F + var12));
        var3.addVertexWithUV((double)var6, (double)var9, (double)var10, (double)(var5 + var11), (double)(0.0F + var12));
        var2.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
    }

    /**
     * Renders the fire on the screen for first person mode. Arg: partialTickTime
     */
    private void renderFireInFirstPerson(float p_78442_1_)
    {
        Tessellator var2 = Tessellator.getInstance();
        WorldRenderer var3 = var2.getWorldRenderer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float var4 = 1.0F;

        for (int var5 = 0; var5 < 2; ++var5)
        {
            GlStateManager.pushMatrix();
            TextureAtlasSprite var6 = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            float var7 = var6.getMinU();
            float var8 = var6.getMaxU();
            float var9 = var6.getMinV();
            float var10 = var6.getMaxV();
            float var11 = (0.0F - var4) / 2.0F;
            float var12 = var11 + var4;
            float var13 = 0.0F - var4 / 2.0F;
            float var14 = var13 + var4;
            float var15 = -0.5F;
            GlStateManager.translate((float)(-(var5 * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GlStateManager.rotate((float)(var5 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            var3.startDrawingQuads();
            var3.addVertexWithUV((double)var11, (double)var13, (double)var15, (double)var8, (double)var10);
            var3.addVertexWithUV((double)var12, (double)var13, (double)var15, (double)var7, (double)var10);
            var3.addVertexWithUV((double)var12, (double)var14, (double)var15, (double)var7, (double)var9);
            var3.addVertexWithUV((double)var11, (double)var14, (double)var15, (double)var8, (double)var9);
            var2.draw();
            GlStateManager.popMatrix();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }

    public void updateEquippedItem()
    {
        this.prevEquippedProgress = this.equippedProgress;
        EntityPlayerSP var1 = this.mc.thePlayer;
        ItemStack var2 = var1.inventory.getCurrentItem();
        boolean var3 = false;

        if (this.itemToRender != null && var2 != null)
        {
            if (!this.itemToRender.getIsItemStackEqual(var2))
            {
                if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists())
                {
                    boolean var4 = Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, new Object[] {this.itemToRender, var2, Boolean.valueOf(this.equippedItemSlot != var1.inventory.currentItem)});

                    if (!var4)
                    {
                        this.itemToRender = var2;
                        this.equippedItemSlot = var1.inventory.currentItem;
                        return;
                    }
                }

                var3 = true;
            }
        }
        else if (this.itemToRender == null && var2 == null)
        {
            var3 = false;
        }
        else
        {
            var3 = true;
        }

        float var41 = 0.4F;
        float var5 = var3 ? 0.0F : 1.0F;
        float var6 = MathHelper.clamp_float(var5 - this.equippedProgress, -var41, var41);
        this.equippedProgress += var6;

        if (this.equippedProgress < 0.1F)
        {
            if (Config.isShaders())
            {
                Shaders.itemToRender = var2;
            }

            this.itemToRender = var2;
            this.equippedItemSlot = var1.inventory.currentItem;
        }
    }

    /**
     * Resets equippedProgress
     */
    public void resetEquippedProgress()
    {
        this.equippedProgress = 0.0F;
    }

    /**
     * Resets equippedProgress
     */
    public void resetEquippedProgress2()
    {
        this.equippedProgress = 0.0F;
    }

    static final class SwitchEnumAction
    {
        static final int[] field_178094_a = new int[EnumAction.values().length];

        static
        {
            try
            {
                field_178094_a[EnumAction.NONE.ordinal()] = 1;
            }
            catch (NoSuchFieldError var5)
            {
                ;
            }

            try
            {
                field_178094_a[EnumAction.EAT.ordinal()] = 2;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                field_178094_a[EnumAction.DRINK.ordinal()] = 3;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                field_178094_a[EnumAction.BLOCK.ordinal()] = 4;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                field_178094_a[EnumAction.BOW.ordinal()] = 5;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
}

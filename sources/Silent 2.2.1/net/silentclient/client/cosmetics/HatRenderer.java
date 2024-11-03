package net.silentclient.client.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.silentclient.client.Client;
import net.silentclient.client.blc.BlcGlStateManager;
import net.silentclient.client.mixin.accessors.MinecraftAccessor;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mods.settings.CosmeticsMod;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class HatRenderer extends ModelBase implements LayerRenderer<AbstractClientPlayer> {
    private final RenderPlayer playerRenderer;
    private static HashMap<UUID, Integer> uuidToHatLayer = new HashMap<UUID, Integer>();
    private static HashSet<UUID> checkedUuids = new HashSet<UUID>();
    public final String type;

    public HatRenderer(RenderPlayer playerRendererIn, String type)
    {
        this.playerRenderer = playerRendererIn;
        this.type = type;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer entityIn, float p_177141_2_, float p_177141_3_,
                              float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Hats").getValBoolean() || entityIn.isInvisible()) {
        	return;
        }
    	
        HatData data = null;
        	
    	switch(type) {
    	case "hat":
    		data = ((AbstractClientPlayerExt) entityIn).silent$getHat();
    		break;
    	case "mask":
    		data = ((AbstractClientPlayerExt) entityIn).silent$getMask();
    		break;
    	case "neck":
    		data = ((AbstractClientPlayerExt) entityIn).silent$getNeck();
    		break;
    	}
    	
    	if(data == null || !Client.getInstance().getCosmetics().hatModels.containsKey(data.getModel()) || !Client.getInstance().getCosmetics().hatModels.get(data.getModel()).loadModel()) {
            return;
        }
    	
    	
        
        GlStateManager.pushMatrix();
		BlcGlStateManager.t();
        BlcGlStateManager.s();
        BlcGlStateManager.g();
        BlcGlStateManager.a(770, 771);
        BlcGlStateManager.q();
        BlcGlStateManager.d();
        BlcGlStateManager.t();
		if(entityIn.isSneaking()) {
			GlStateManager.translate(0.0f, data.getModel().equals("gold_chain") ? 0.2f : 0.25f, 0.0f);
		}
		switch(data.getModel()) {
		case "gold_chain":
			if (playerRenderer.getMainModel().bipedBody.rotateAngleZ != 0.0F) {
	            GlStateManager.rotate(playerRenderer.getMainModel().bipedBody.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
	        }

	        if (playerRenderer.getMainModel().bipedBody.rotateAngleY != 0.0F) {
	            GlStateManager.rotate(playerRenderer.getMainModel().bipedBody.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
	        }

	        if (playerRenderer.getMainModel().bipedBody.rotateAngleX != 0.0F) {
	            GlStateManager.rotate(playerRenderer.getMainModel().bipedBody.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
	        }
	        break;
		default:
			if (playerRenderer.getMainModel().bipedHead.rotateAngleZ != 0.0F) {
	            GlStateManager.rotate(playerRenderer.getMainModel().bipedHead.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
	        }

	        if (playerRenderer.getMainModel().bipedHead.rotateAngleY != 0.0F) {
	            GlStateManager.rotate(playerRenderer.getMainModel().bipedHead.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
	        }

	        if (playerRenderer.getMainModel().bipedHead.rotateAngleX != 0.0F) {
	            GlStateManager.rotate(playerRenderer.getMainModel().bipedHead.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
	        }
	        break;
		}
		double applyTransformations = this.applyTransformations(data.getModel());
        
        applyTransformations += this.manipulate(entityIn, data.getModel());

        GlStateManager.scale(applyTransformations, applyTransformations, applyTransformations);
        data.getTexture().bindTexture();
        BlcGlStateManager.k();
        Client.getInstance().getCosmetics().hatModels.get(data.getModel()).renderModel();
        BlcGlStateManager.c();
        BlcGlStateManager.u();
        BlcGlStateManager.c(1029);
        BlcGlStateManager.l();
        BlcGlStateManager.r();
        BlcGlStateManager.u();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        data.getTexture().update(partialTicks);
    }
    
    private void runSkinProcessing(final UUID uuid, final String s) {
        if (HatRenderer.checkedUuids.contains(uuid)) {
            return;
        }
        HatRenderer.checkedUuids.add(uuid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String substring = s;
                final String string = ((MinecraftAccessor) Minecraft.getMinecraft()).getFileAssets().getAbsolutePath() + "/skins/" + substring.substring(0, 2) + "/" + s;
                try {
                	HatRenderer.uuidToHatLayer.put(uuid, HatRenderer.findMaxHatLayer(ImageIO.read(new File(string))));
                	HatRenderer.checkedUuids.remove(uuid);
                }
                catch (final Exception ex) {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (final InterruptedException ex2) {}
                    HatRenderer.checkedUuids.remove(uuid);
                }
            }
        }).start();
    }
    
    public static int findMaxHatLayer(final BufferedImage bufferedImage) {
        for (int i = 8, n = 8; i < 16; ++i, --n) {
            for (int j = 32; j < 64; ++j) {
                if (bufferedImage.getRGB(j, i) >> 24 != 0) {
                    return n;
                }
            }
        }
        return 0;
    }
	
	private double applyTransformations(String model) {
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        
        Vector4f rotations = new Vector4f(180.0f, 0.0f, 1.0f, 0.0f);
        Vector3f translations = null;
        double scale = 0.068F;
        
        switch(model) {
        case "halo":
        case "halo2":
        case "sharp_halo":
        	translations = new Vector3f(0.65f, 0.65f, 0.65f);
        	scale = 0.08f;
        	break;
        case "cowboy":
        	translations = new Vector3f(0.0f, 0.5f, 0.0f);
        	scale = 0.0101f;
        	break;
        case "snapback":
            rotations = new Vector4f(180.0f, 90.0f, 1.0f, 0.0f);
            translations = new Vector3f(0.0f, 1.5f, 0.0f);
            scale = 1;
            break;
        case "crown":
        	rotations = null;
        	translations = new Vector3f(0.08f, 0.4f, 0.0f);
        	break;
        case "flowersnew":
        	translations = new Vector3f(0.0f, 0.275f, 0.0f);
        	break;
        }
        
        if(rotations != null) {
        	GlStateManager.rotate(rotations.x, rotations.y, rotations.z, rotations.w);
        }
        if(translations != null) {
        	GlStateManager.translate(translations.x, translations.y, translations.z);
        }

        return scale;
    }
	
	private double manipulate(AbstractClientPlayer entity, String model) {
		double n = 0.0;
		
		ItemStack helmet = entity.getCurrentArmor(3);
		ItemStack chestplate = entity.getCurrentArmor(2);
		
		if(chestplate != null && model.equals("gold_chain")) {
			return 0.018;
		}

		if(helmet != null) {
			switch(model) {
            case "sombrero":
            	GL11.glTranslatef(0.0f, 0.065f, 0.0f);
            	break;
            case "cowboy":
            	GL11.glTranslatef(0.0f, 0.18f, 0.0f);
            	break;
            case "crown_v2":
            	GL11.glTranslatef(0.0f, 0.15f, 0.0f);
                break;
            case "panda":
            	n = 0.018;
                GL11.glTranslatef(0.0f, -0.1f, 0.0f);
                break;
            case "flowersnew":
            	n = 0.012;
            	break;
            case "zekich_hat":
            	GL11.glTranslatef(0.0f, 0.01f, 0.0f);
            	break;
            case "gaming_headset":
            	n = 0.018;
            	break;
            case "snapback":
                n = 0.25;
                GL11.glTranslatef(0.0f, 0.3f, 0.0f);
                break;
            }
		} else {
			final Integer n2 = HatRenderer.uuidToHatLayer.get(entity.getUniqueID());
            if (n2 == null) {
                this.runSkinProcessing(entity.getUniqueID(), entity.getLocationSkin().getResourcePath().replace("skins/", ""));
                return n;
            }
            if (n2 == 0) {
                return n;
            }
            switch(model) {
            case "sombrero":
            	GL11.glTranslatef(0.0f, 0.04f, 0.0f);
            	break;
            case "bunny_laying_hat":
            	GL11.glTranslatef(0.0f, -0.06f, 0.0f);
            	break;
            case "zekich_hat":
            	GL11.glTranslatef(0.0f, -0.05f, 0.0f);
            	break;
            }
		}
		
		return n;
	}

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

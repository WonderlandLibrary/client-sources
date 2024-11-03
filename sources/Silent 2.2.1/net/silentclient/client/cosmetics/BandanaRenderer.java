package net.silentclient.client.cosmetics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

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
import net.silentclient.client.utils.ColorUtils;

public class BandanaRenderer extends ModelBase implements LayerRenderer<AbstractClientPlayer> {
	private final RenderPlayer playerRenderer;
	private static HashMap<UUID, Integer> uuidToHatLayer = new HashMap<UUID, Integer>();
    private static HashSet<UUID> checkedUuids = new HashSet<UUID>();
	
	public BandanaRenderer(RenderPlayer playerRendererIn)
    {
        this.playerRenderer = playerRendererIn;
    }

	@Override
	public void doRenderLayer(AbstractClientPlayer entityIn, float p_177141_2_, float p_177141_3_,
			float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
		if(Client.getInstance().getCosmetics().getBandana() == null || !Client.getInstance().getCosmetics().getBandana().loadModel() || !Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Bandanas").getValBoolean() || ((AbstractClientPlayerExt) entityIn).silent$getBandana() == null || entityIn.isInvisible()) {
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
			GlStateManager.translate(0.0f, 0.25f, 0.0f);
		}
		if (playerRenderer.getMainModel().bipedHead.rotateAngleZ != 0.0F) {
            GlStateManager.rotate(playerRenderer.getMainModel().bipedHead.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
        }

        if (playerRenderer.getMainModel().bipedHead.rotateAngleY != 0.0F) {
            GlStateManager.rotate(playerRenderer.getMainModel().bipedHead.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        }

        if (playerRenderer.getMainModel().bipedHead.rotateAngleX != 0.0F) {
            GlStateManager.rotate(playerRenderer.getMainModel().bipedHead.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
        }
		double applyTransformations = this.applyTransformations();
		if(((AbstractClientPlayerExt) entityIn).silent$getAccount() != null && ((AbstractClientPlayerExt) entityIn).silent$getBandana().getTexture().getResourcePath().equals("silentclient/cosmetics/bandanas/17/0.png")) {
			if(((AbstractClientPlayerExt) entityIn).silent$getAccount().getBandanaColor() == 50) {
				ColorUtils.setColor(ColorUtils.getChromaColor(0, 0, 1).getRGB());
			} else {
				ColorUtils.setColor(((AbstractClientPlayerExt) entityIn).silent$getAccount().getBandanaColor());
			}
		}
        
        applyTransformations += this.manipulate(entityIn);

        GlStateManager.scale(applyTransformations, applyTransformations, applyTransformations);
        ((AbstractClientPlayerExt) entityIn).silent$getBandana().bindTexture();
        BlcGlStateManager.k();
        Client.getInstance().getCosmetics().getBandana().renderModel();
        BlcGlStateManager.c();
        BlcGlStateManager.u();
        BlcGlStateManager.c(1029);
        BlcGlStateManager.l();
        BlcGlStateManager.r();
        BlcGlStateManager.u();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        ((AbstractClientPlayerExt) entityIn).silent$getBandana().update(partialTicks);
	}
	
	private void runSkinProcessing(final UUID uuid, final String s) {
        if (BandanaRenderer.checkedUuids.contains(uuid)) {
            return;
        }
        BandanaRenderer.checkedUuids.add(uuid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String substring = s;
                final String string = ((MinecraftAccessor) Minecraft.getMinecraft()).getFileAssets().getAbsolutePath() + "/skins/" + substring.substring(0, 2) + "/" + s;
                try {
                	BandanaRenderer.uuidToHatLayer.put(uuid, BandanaRenderer.findMaxHatLayer(ImageIO.read(new File(string))));
                    BandanaRenderer.checkedUuids.remove(uuid);
                }
                catch (final Exception ex) {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (final InterruptedException ex2) {}
                    BandanaRenderer.checkedUuids.remove(uuid);
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
	
	private double applyTransformations() {	
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        
        final Vector4f rotations = new Vector4f(180.0f, 0.0f, 1.0f, 0.0f);
        final Vector3f translations = new Vector3f(0.0f, 0.375f, 0.0f);
        
        GlStateManager.rotate(rotations.x, rotations.y, rotations.z, rotations.w);
        GlStateManager.translate(translations.x, translations.y, translations.z);

        return 0.068F;
    }
	
	private double manipulate(AbstractClientPlayer entity) {
		double n = 0.0;
		
		GlStateManager.translate(0.0, -1 * 0.06, 0.0);
		ItemStack helmet = entity.getCurrentArmor(3);
		if(helmet != null) {
			n = 0.012;
		} else {
			final Integer n2 = BandanaRenderer.uuidToHatLayer.get(entity.getUniqueID());
            if (n2 == null) {
                this.runSkinProcessing(entity.getUniqueID(), entity.getLocationSkin().getResourcePath().replace("skins/", ""));
                return n;
            }
            if (n2 == 0) {
                return n;
            }
            if (n2 > 4) {
                n = 0.001;
            }
		}
		
		return n;
	}

	@Override
	public boolean shouldCombineTextures() {
		// TODO Auto-generated method stub
		return false;
	}
}

package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;

public class BlockModelUtils {
	public static IBakedModel makeModelCube(String p_makeModelCube_0_, int p_makeModelCube_1_) {
		TextureAtlasSprite textureatlassprite = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(p_makeModelCube_0_);
		return makeModelCube(textureatlassprite, p_makeModelCube_1_);
	}

	public static IBakedModel makeModelCube(TextureAtlasSprite p_makeModelCube_0_, int p_makeModelCube_1_) {
		List list = new ArrayList();
		EnumFacing[] aenumfacing = EnumFacing.VALUES;
		Map<EnumFacing, List<BakedQuad>> map = new HashMap();

		for (EnumFacing enumfacing : aenumfacing) {
			List list1 = new ArrayList();
			list1.add(makeBakedQuad(enumfacing, p_makeModelCube_0_, p_makeModelCube_1_));
			map.put(enumfacing, list1);
		}

		ItemOverrideList itemoverridelist = new ItemOverrideList(new ArrayList());
		IBakedModel ibakedmodel = new SimpleBakedModel(list, map, true, true, p_makeModelCube_0_, ItemCameraTransforms.DEFAULT, itemoverridelist);
		return ibakedmodel;
	}

	private static BakedQuad makeBakedQuad(EnumFacing p_makeBakedQuad_0_, TextureAtlasSprite p_makeBakedQuad_1_, int p_makeBakedQuad_2_) {
		Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
		Vector3f vector3f1 = new Vector3f(16.0F, 16.0F, 16.0F);
		BlockFaceUV blockfaceuv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
		BlockPartFace blockpartface = new BlockPartFace(p_makeBakedQuad_0_, p_makeBakedQuad_2_, "#" + p_makeBakedQuad_0_.getName(), blockfaceuv);
		ModelRotation modelrotation = ModelRotation.X0_Y0;
		BlockPartRotation blockpartrotation = null;
		boolean flag = false;
		boolean flag1 = true;
		FaceBakery facebakery = new FaceBakery();
		BakedQuad bakedquad = facebakery.makeBakedQuad(vector3f, vector3f1, blockpartface, p_makeBakedQuad_1_, p_makeBakedQuad_0_, modelrotation, blockpartrotation, flag, flag1);
		return bakedquad;
	}

	public static IBakedModel makeModel(String p_makeModel_0_, String p_makeModel_1_, String p_makeModel_2_) {
		TextureMap texturemap = Config.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(p_makeModel_1_);
		TextureAtlasSprite textureatlassprite1 = texturemap.getSpriteSafe(p_makeModel_2_);
		return makeModel(p_makeModel_0_, textureatlassprite, textureatlassprite1);
	}

	public static IBakedModel makeModel(String p_makeModel_0_, TextureAtlasSprite p_makeModel_1_, TextureAtlasSprite p_makeModel_2_) {
		if ((p_makeModel_1_ != null) && (p_makeModel_2_ != null)) {
			ModelManager modelmanager = Config.getModelManager();

			if (modelmanager == null) {
				return null;
			} else {
				ModelResourceLocation modelresourcelocation = new ModelResourceLocation(p_makeModel_0_, "normal");
				IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);

				if ((ibakedmodel != null) && (ibakedmodel != modelmanager.getMissingModel())) {
					IBakedModel ibakedmodel1 = ModelUtils.duplicateModel(ibakedmodel);
					EnumFacing[] aenumfacing = EnumFacing.VALUES;

					for (EnumFacing enumfacing : aenumfacing) {
						List<BakedQuad> list = ibakedmodel1.getQuads((IBlockState) null, enumfacing, 0L);
						replaceTexture(list, p_makeModel_1_, p_makeModel_2_);
					}

					List<BakedQuad> list1 = ibakedmodel1.getQuads((IBlockState) null, (EnumFacing) null, 0L);
					replaceTexture(list1, p_makeModel_1_, p_makeModel_2_);
					return ibakedmodel1;
				} else {
					return null;
				}
			}
		} else {
			return null;
		}
	}

	private static void replaceTexture(List<BakedQuad> p_replaceTexture_0_, TextureAtlasSprite p_replaceTexture_1_, TextureAtlasSprite p_replaceTexture_2_) {
		List<BakedQuad> list = new ArrayList();

		for (BakedQuad bakedquad : p_replaceTexture_0_) {
			if (bakedquad.getSprite() != p_replaceTexture_1_) {
				list.add(bakedquad);
				break;
			}

			BakedQuad bakedquad1 = new BakedQuadRetextured(bakedquad, p_replaceTexture_2_);
			list.add(bakedquad1);
		}

		p_replaceTexture_0_.clear();
		p_replaceTexture_0_.addAll(list);
	}
}

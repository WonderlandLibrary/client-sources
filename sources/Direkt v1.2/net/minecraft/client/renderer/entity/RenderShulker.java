package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RenderShulker extends RenderLiving<EntityShulker> {
	private static final ResourceLocation SHULKER_ENDERGOLEM_TEXTURE = new ResourceLocation("textures/entity/shulker/endergolem.png");
	private int modelVersion;

	public RenderShulker(RenderManager manager, ModelShulker p_i46550_2_) {
		super(manager, p_i46550_2_, 0.0F);
		this.addLayer(new RenderShulker.HeadLayer());
		this.modelVersion = p_i46550_2_.getModelVersion();
		this.shadowSize = 0.0F;
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	@Override
	public void doRender(EntityShulker entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (this.modelVersion != ((ModelShulker) this.mainModel).getModelVersion()) {
			this.mainModel = new ModelShulker();
			this.modelVersion = ((ModelShulker) this.mainModel).getModelVersion();
		}

		int i = entity.getClientTeleportInterp();

		if ((i > 0) && entity.isAttachedToBlock()) {
			BlockPos blockpos = entity.getAttachmentPos();
			BlockPos blockpos1 = entity.getOldAttachPos();
			double d0 = (i - partialTicks) / 6.0D;
			d0 = d0 * d0;
			double d1 = (blockpos.getX() - blockpos1.getX()) * d0;
			double d2 = (blockpos.getY() - blockpos1.getY()) * d0;
			double d3 = (blockpos.getZ() - blockpos1.getZ()) * d0;
			super.doRender(entity, x - d1, y - d2, z - d3, entityYaw, partialTicks);
		} else {
			super.doRender(entity, x, y, z, entityYaw, partialTicks);
		}
	}

	@Override
	public boolean shouldRender(EntityShulker livingEntity, ICamera camera, double camX, double camY, double camZ) {
		if (super.shouldRender(livingEntity, camera, camX, camY, camZ)) {
			return true;
		} else {
			if ((livingEntity.getClientTeleportInterp() > 0) && livingEntity.isAttachedToBlock()) {
				BlockPos blockpos = livingEntity.getOldAttachPos();
				BlockPos blockpos1 = livingEntity.getAttachmentPos();
				Vec3d vec3d = new Vec3d(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
				Vec3d vec3d1 = new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ());

				if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord, vec3d.zCoord))) { return true; }
			}

			return false;
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityShulker entity) {
		return SHULKER_ENDERGOLEM_TEXTURE;
	}

	@Override
	protected void rotateCorpse(EntityShulker entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
		super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);

		switch (entityLiving.getAttachmentFacing()) {
		case DOWN:
		default:
			break;

		case EAST:
			GlStateManager.translate(0.5F, 0.5F, 0.0F);
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			break;

		case WEST:
			GlStateManager.translate(-0.5F, 0.5F, 0.0F);
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
			break;

		case NORTH:
			GlStateManager.translate(0.0F, 0.5F, -0.5F);
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			break;

		case SOUTH:
			GlStateManager.translate(0.0F, 0.5F, 0.5F);
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			break;

		case UP:
			GlStateManager.translate(0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		}
	}

	/**
	 * Allows the render to do state modifications necessary before the model is rendered.
	 */
	@Override
	protected void preRenderCallback(EntityShulker entitylivingbaseIn, float partialTickTime) {
		float f = 0.999F;
		GlStateManager.scale(0.999F, 0.999F, 0.999F);
	}

	class HeadLayer implements LayerRenderer<EntityShulker> {
		private HeadLayer() {
		}

		@Override
		public void doRenderLayer(EntityShulker entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			GlStateManager.pushMatrix();

			switch (entitylivingbaseIn.getAttachmentFacing()) {
			case DOWN:
			default:
				break;

			case EAST:
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(1.0F, -1.0F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				break;

			case WEST:
				GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(-1.0F, -1.0F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				break;

			case NORTH:
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0.0F, -1.0F, -1.0F);
				break;

			case SOUTH:
				GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0.0F, -1.0F, 1.0F);
				break;

			case UP:
				GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0.0F, -2.0F, 0.0F);
			}

			ModelRenderer modelrenderer = ((ModelShulker) RenderShulker.this.getMainModel()).head;
			modelrenderer.rotateAngleY = netHeadYaw * 0.017453292F;
			modelrenderer.rotateAngleX = headPitch * 0.017453292F;
			RenderShulker.this.bindTexture(RenderShulker.SHULKER_ENDERGOLEM_TEXTURE);
			modelrenderer.render(scale);
			GlStateManager.popMatrix();
		}

		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
	}
}

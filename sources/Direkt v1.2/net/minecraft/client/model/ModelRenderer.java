package net.minecraft.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.src.ModelSprite;

public class ModelRenderer {
	/** The size of the texture file's width in pixels. */
	public float textureWidth;

	/** The size of the texture file's height in pixels. */
	public float textureHeight;

	/** The X offset into the texture used for displaying this model */
	private int textureOffsetX;

	/** The Y offset into the texture used for displaying this model */
	private int textureOffsetY;
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	private boolean compiled;

	/** The GL display list rendered by the Tessellator for this model */
	private int displayList;
	public boolean mirror;
	public boolean showModel;

	/** Hides the model. */
	public boolean isHidden;
	public List<ModelBox> cubeList;
	public List<ModelRenderer> childModels;
	public final String boxName;
	private final ModelBase baseModel;
	public float offsetX;
	public float offsetY;
	public float offsetZ;
	public List spriteList;
	public boolean mirrorV;
	float savedScale;

	public ModelRenderer(ModelBase model, String boxNameIn) {
		this.spriteList = new ArrayList();
		this.mirrorV = false;
		this.textureWidth = 64.0F;
		this.textureHeight = 32.0F;
		this.showModel = true;
		this.cubeList = Lists.<ModelBox> newArrayList();
		this.baseModel = model;
		model.boxList.add(this);
		this.boxName = boxNameIn;
		this.setTextureSize(model.textureWidth, model.textureHeight);
	}

	public ModelRenderer(ModelBase model) {
		this(model, (String) null);
	}

	public ModelRenderer(ModelBase model, int texOffX, int texOffY) {
		this(model);
		this.setTextureOffset(texOffX, texOffY);
	}

	/**
	 * Sets the current box's rotation points and rotation angles to another box.
	 */
	public void addChild(ModelRenderer renderer) {
		if (this.childModels == null) {
			this.childModels = Lists.<ModelRenderer> newArrayList();
		}

		this.childModels.add(renderer);
	}

	public ModelRenderer setTextureOffset(int x, int y) {
		this.textureOffsetX = x;
		this.textureOffsetY = y;
		return this;
	}

	public ModelRenderer addBox(String partName, float offX, float offY, float offZ, int width, int height, int depth) {
		partName = this.boxName + "." + partName;
		TextureOffset textureoffset = this.baseModel.getTextureOffset(partName);
		this.setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
		this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F)).setBoxName(partName));
		return this;
	}

	public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth) {
		this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F));
		return this;
	}

	public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth, boolean mirrored) {
		this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F, mirrored));
		return this;
	}

	/**
	 * Creates a textured box.
	 */
	public void addBox(float offX, float offY, float offZ, int width, int height, int depth, float scaleFactor) {
		this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, scaleFactor));
	}

	public void setRotationPoint(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
		this.rotationPointX = rotationPointXIn;
		this.rotationPointY = rotationPointYIn;
		this.rotationPointZ = rotationPointZIn;
	}

	public void render(float scale) {
		if (!this.isHidden && this.showModel) {
			if (!this.compiled) {
				this.compileDisplayList(scale);
			}

			GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);

			if ((this.rotateAngleX == 0.0F) && (this.rotateAngleY == 0.0F) && (this.rotateAngleZ == 0.0F)) {
				if ((this.rotationPointX == 0.0F) && (this.rotationPointY == 0.0F) && (this.rotationPointZ == 0.0F)) {
					GlStateManager.callList(this.displayList);

					if (this.childModels != null) {
						for (int k = 0; k < this.childModels.size(); ++k) {
							this.childModels.get(k).render(scale);
						}
					}
				} else {
					GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
					GlStateManager.callList(this.displayList);

					if (this.childModels != null) {
						for (int j = 0; j < this.childModels.size(); ++j) {
							this.childModels.get(j).render(scale);
						}
					}

					GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
				}
			} else {
				GlStateManager.pushMatrix();
				GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

				if (this.rotateAngleZ != 0.0F) {
					GlStateManager.rotate(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
				}

				if (this.rotateAngleY != 0.0F) {
					GlStateManager.rotate(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if (this.rotateAngleX != 0.0F) {
					GlStateManager.rotate(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
				}

				GlStateManager.callList(this.displayList);

				if (this.childModels != null) {
					for (int i = 0; i < this.childModels.size(); ++i) {
						this.childModels.get(i).render(scale);
					}
				}

				GlStateManager.popMatrix();
			}

			GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
		}
	}

	public void renderWithRotation(float scale) {
		if (!this.isHidden && this.showModel) {
			if (!this.compiled) {
				this.compileDisplayList(scale);
			}

			GlStateManager.pushMatrix();
			GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

			if (this.rotateAngleY != 0.0F) {
				GlStateManager.rotate(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			}

			if (this.rotateAngleX != 0.0F) {
				GlStateManager.rotate(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
			}

			if (this.rotateAngleZ != 0.0F) {
				GlStateManager.rotate(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
			}

			GlStateManager.callList(this.displayList);
			GlStateManager.popMatrix();
		}
	}

	/**
	 * Allows the changing of Angles after a box has been rendered
	 */
	public void postRender(float scale) {
		if (!this.isHidden && this.showModel) {
			if (!this.compiled) {
				this.compileDisplayList(scale);
			}

			if ((this.rotateAngleX == 0.0F) && (this.rotateAngleY == 0.0F) && (this.rotateAngleZ == 0.0F)) {
				if ((this.rotationPointX != 0.0F) || (this.rotationPointY != 0.0F) || (this.rotationPointZ != 0.0F)) {
					GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
				}
			} else {
				GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

				if (this.rotateAngleZ != 0.0F) {
					GlStateManager.rotate(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
				}

				if (this.rotateAngleY != 0.0F) {
					GlStateManager.rotate(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if (this.rotateAngleX != 0.0F) {
					GlStateManager.rotate(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
				}
			}
		}
	}

	/**
	 * Compiles a GL display list for this model
	 */
	private void compileDisplayList(float scale) {
		if (this.displayList == 0) {
			this.savedScale = scale;
			this.displayList = GLAllocation.generateDisplayLists(1);
		}

		GlStateManager.glNewList(this.displayList, 4864);
		VertexBuffer vertexbuffer = Tessellator.getInstance().getBuffer();

		for (int i = 0; i < this.cubeList.size(); ++i) {
			this.cubeList.get(i).render(vertexbuffer, scale);
		}

		for (int j = 0; j < this.spriteList.size(); ++j) {
			ModelSprite modelsprite = (ModelSprite) this.spriteList.get(j);
			modelsprite.render(Tessellator.getInstance(), scale);
		}

		GlStateManager.glEndList();
		this.compiled = true;
	}

	/**
	 * Returns the model renderer with the new texture parameters.
	 */
	public ModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		return this;
	}

	public void addSprite(float p_addSprite_1_, float p_addSprite_2_, float p_addSprite_3_, int p_addSprite_4_, int p_addSprite_5_, int p_addSprite_6_, float p_addSprite_7_) {
		this.spriteList
				.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, p_addSprite_1_, p_addSprite_2_, p_addSprite_3_, p_addSprite_4_, p_addSprite_5_, p_addSprite_6_, p_addSprite_7_));
	}

	public boolean getCompiled() {
		return this.compiled;
	}

	public int getDisplayList() {
		return this.displayList;
	}

	public void resetDisplayList() {
		if (this.compiled) {
			this.compiled = false;
			this.compileDisplayList(this.savedScale);
		}
	}
}

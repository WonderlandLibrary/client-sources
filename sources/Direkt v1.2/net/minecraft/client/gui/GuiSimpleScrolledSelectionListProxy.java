package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.realms.RealmsSimpleScrolledSelectionList;
import net.minecraft.util.math.MathHelper;

public class GuiSimpleScrolledSelectionListProxy extends GuiSlot {
	private final RealmsSimpleScrolledSelectionList realmsScrolledSelectionList;

	public GuiSimpleScrolledSelectionListProxy(RealmsSimpleScrolledSelectionList p_i45525_1_, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
		super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
		this.realmsScrolledSelectionList = p_i45525_1_;
	}

	@Override
	protected int getSize() {
		return this.realmsScrolledSelectionList.getItemCount();
	}

	/**
	 * The element in the slot that was clicked, boolean for whether it was double clicked or not
	 */
	@Override
	protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
		this.realmsScrolledSelectionList.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	@Override
	protected boolean isSelected(int slotIndex) {
		return this.realmsScrolledSelectionList.isSelectedItem(slotIndex);
	}

	@Override
	protected void drawBackground() {
		this.realmsScrolledSelectionList.renderBackground();
	}

	@Override
	protected void drawSlot(int entryID, int insideLeft, int yPos, int insideSlotHeight, int mouseXIn, int mouseYIn) {
		this.realmsScrolledSelectionList.renderItem(entryID, insideLeft, yPos, insideSlotHeight, mouseXIn, mouseYIn);
	}

	public int getWidth() {
		return super.width;
	}

	public int getMouseY() {
		return super.mouseY;
	}

	public int getMouseX() {
		return super.mouseX;
	}

	/**
	 * Return the height of the content being scrolled
	 */
	@Override
	protected int getContentHeight() {
		return this.realmsScrolledSelectionList.getMaxPosition();
	}

	@Override
	protected int getScrollBarX() {
		return this.realmsScrolledSelectionList.getScrollbarPosition();
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
	}

	@Override
	public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
		if (this.visible) {
			this.mouseX = mouseXIn;
			this.mouseY = mouseYIn;
			this.drawBackground();
			int i = this.getScrollBarX();
			int j = i + 6;
			this.bindAmountScrolled();
			GlStateManager.disableLighting();
			GlStateManager.disableFog();
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vertexbuffer = tessellator.getBuffer();
			int k = ((this.left + (this.width / 2)) - (this.getListWidth() / 2)) + 2;
			int l = (this.top + 4) - (int) this.amountScrolled;

			if (this.hasListHeader) {
				this.drawListHeader(k, l, tessellator);
			}

			this.drawSelectionBox(k, l, mouseXIn, mouseYIn);
			GlStateManager.disableDepth();
			this.overlayBackground(0, this.top, 255, 255);
			this.overlayBackground(this.bottom, this.height, 255, 255);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
			GlStateManager.disableAlpha();
			GlStateManager.shadeModel(7425);
			GlStateManager.disableTexture2D();
			int i1 = this.getMaxScroll();

			if (i1 > 0) {
				int j1 = ((this.bottom - this.top) * (this.bottom - this.top)) / this.getContentHeight();
				j1 = MathHelper.clamp_int(j1, 32, this.bottom - this.top - 8);
				int k1 = (((int) this.amountScrolled * (this.bottom - this.top - j1)) / i1) + this.top;

				if (k1 < this.top) {
					k1 = this.top;
				}

				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				vertexbuffer.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
				tessellator.draw();
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				vertexbuffer.pos(i, k1 + j1, 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
				vertexbuffer.pos(j, k1 + j1, 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
				vertexbuffer.pos(j, k1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
				vertexbuffer.pos(i, k1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
				tessellator.draw();
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				vertexbuffer.pos(i, (k1 + j1) - 1, 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
				vertexbuffer.pos(j - 1, (k1 + j1) - 1, 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
				vertexbuffer.pos(j - 1, k1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
				vertexbuffer.pos(i, k1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
				tessellator.draw();
			}

			this.renderDecorations(mouseXIn, mouseYIn);
			GlStateManager.enableTexture2D();
			GlStateManager.shadeModel(7424);
			GlStateManager.enableAlpha();
			GlStateManager.disableBlend();
		}
	}
}

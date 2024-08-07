package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsScrolledSelectionList;

public class GuiSlotRealmsProxy extends GuiSlot {
	private final RealmsScrolledSelectionList selectionList;

	public GuiSlotRealmsProxy(RealmsScrolledSelectionList selectionListIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
		super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
		this.selectionList = selectionListIn;
	}

	@Override
	protected int getSize() {
		return this.selectionList.getItemCount();
	}

	/**
	 * The element in the slot that was clicked, boolean for whether it was double clicked or not
	 */
	@Override
	protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
		this.selectionList.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	@Override
	protected boolean isSelected(int slotIndex) {
		return this.selectionList.isSelectedItem(slotIndex);
	}

	@Override
	protected void drawBackground() {
		this.selectionList.renderBackground();
	}

	@Override
	protected void drawSlot(int entryID, int insideLeft, int yPos, int insideSlotHeight, int mouseXIn, int mouseYIn) {
		this.selectionList.renderItem(entryID, insideLeft, yPos, insideSlotHeight, mouseXIn, mouseYIn);
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
		return this.selectionList.getMaxPosition();
	}

	@Override
	protected int getScrollBarX() {
		return this.selectionList.getScrollbarPosition();
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
	}
}

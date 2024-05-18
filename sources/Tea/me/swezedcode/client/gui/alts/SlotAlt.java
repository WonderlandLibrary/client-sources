package me.swezedcode.client.gui.alts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

public class SlotAlt extends GuiSlot {
	private GuiAltList aList;
	private int selected;
	
	public SlotAlt(Minecraft theMinecraft, GuiAltList aList) {
		super(theMinecraft, aList.width, aList.height, 32, aList.height - 59, Manager.slotHeight);
		this.aList = aList;
		this.selected = 0;
	}
	
	@Override
	protected int getContentHeight() {
		return this.getSize() * Manager.slotHeight;
	}
	
	@Override
	protected int getSize() {
		return Manager.altList.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2, int var3, int var4) {
		this.selected = var1;
	}

	@Override
	protected boolean isSelected(int var1) {
		return this.selected == var1;
	}
	
	protected int getSelected() {
		return this.selected;
	}

	@Override
	protected void drawBackground() {
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/index.jpg"));
		drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight());
	}
	
	public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
    {
        float var10 = 1.0F / tileWidth;
        float var11 = 1.0F / tileHeight;
        Tessellator var12 = Tessellator.getInstance();
        WorldRenderer var13 = var12.getWorldRenderer();
        var13.startDrawingQuads();
        var13.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var10), (double)((v + (float)vHeight) * var11));
        var13.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)uWidth) * var10), (double)((v + (float)vHeight) * var11));
        var13.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)uWidth) * var10), (double)(v * var11));
        var13.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var10), (double)(v * var11));
        var12.draw();
    }

	@Override
	protected void drawSlot(int selectedIndex, int x, int y, int var4, int var6, int var7) {
		try {
			Alt theAlt = Manager.altList.get(selectedIndex);
			aList.drawString(aList.getLocalFontRenderer(), theAlt.getUsername(), x, y + 1, 0xFFFFFF);
			if(theAlt.isPremium()) {
				aList.drawString(aList.getLocalFontRenderer(), Manager.makePassChar(theAlt.getPassword()), x, y + 12, 0x808080);
			} else {
				aList.drawString(aList.getLocalFontRenderer(), "N/A", x, y + 12, 0x808080);
			}
		} catch(AccountManagementException error) {
			error.printStackTrace();
		}
	}
}

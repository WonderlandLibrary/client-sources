package com.darkcart.xdolf.altmanager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class SlotAlt extends GuiSlot
{
	private GuiAltList aList;
	private int selected;
	
	public SlotAlt(Minecraft theMinecraft, GuiAltList aList)
	{
		super(theMinecraft, aList.width, aList.height, 32, aList.height - 59, Manager.slotHeight);
		this.aList = aList;
		this.selected = -1;
	}
	
	@Override
	protected int getSize()
	{
		return Manager.altList.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2, int var3, int var4)
	{
		this.selected = var1;
	}

	@Override
	protected boolean isSelected(int var1)
	{
		return this.selected == var1;
	}
	
	protected int getSelected()
	{
		return this.selected;
	}

	@Override
	protected void drawBackground()
	{
		aList.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
		Alt theAlt = Manager.altList.get(var1);
		aList.drawString(aList.getLocalFontRenderer(), theAlt.getUsername(), var2, var3 + 1, 0xFFFFFF);
		if(theAlt.isPremium())
		{
			aList.drawString(aList.getLocalFontRenderer(), "Premium", var2, var3 + 12, 0x00FF00);
		}else
		{
			aList.drawString(aList.getLocalFontRenderer(), "\247mPremium", var2, var3 + 12, 0x990000);
		}

	}

    public void drawTexturedModalRectZ(final float i, final float y, final int k, final int i1, final int j1, final int k1, final float zLevel) {
		float f = 0.015625F;
		float f1 = 0.03125F;
		Tessellator tesselator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tesselator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*
		tesselator.getWorldRenderer().addVertexWithUV(i + 0, y + k1, zLevel, (k + 0) * f, (i1 + k1) * f1);
		tesselator.getWorldRenderer().addVertexWithUV(i + j1, y + k1, zLevel, (k + j1) * f, (i1 + k1) * f1);
		tesselator.getWorldRenderer().addVertexWithUV(i + j1, y + 0, zLevel, (k + j1) * f, (i1 + 0) * f1);
		tesselator.getWorldRenderer().addVertexWithUV(i + 0, y + 0, zLevel, (k + 0) * f, (i1 + 0) * f1);
		*/
		
		vertexbuffer.pos(i + 0, y + k1, zLevel);
		vertexbuffer.pos(i + j1, y + k1, zLevel);
		vertexbuffer.pos(i + j1, y + 0, zLevel);
		vertexbuffer.pos(i + 0, y + 0, zLevel);

		tesselator.draw();
    }
}

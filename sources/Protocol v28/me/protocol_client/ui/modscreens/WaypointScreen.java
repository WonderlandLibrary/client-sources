package me.protocol_client.ui.modscreens;

import java.io.IOException;

import me.protocol_client.Wrapper;
import me.protocol_client.utils.MiscUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

public class WaypointScreen extends GuiScreen
{
	protected GuiScreen prevMenu;
	protected GuiTextField xBox;
	protected GuiTextField yBox;
	protected GuiTextField zBox;
	protected GuiTextField rBox;
	protected GuiTextField gBox;
	protected GuiTextField bBox;
	
	protected String errorText = "";
	protected String helpText = "";
	
	
	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen()
	{
		xBox.updateCursorCounter();
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 72 + 12, "Add waypoint"));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Cancel"));
		xBox = new GuiTextField(1, fontRendererObj, width / 2 - 100, 60, 60, 20);
		yBox = new GuiTextField(1, fontRendererObj, width / 2 - 35, 60, 60, 20);
		zBox = new GuiTextField(1, fontRendererObj, width / 2 + 30, 60, 60, 20);
		rBox = new GuiTextField(1, fontRendererObj, width / 2 - 100, 85, 60, 20);
		gBox = new GuiTextField(1, fontRendererObj, width / 2 - 35, 85, 60, 20);
		bBox = new GuiTextField(1, fontRendererObj, width / 2 + 30, 85, 60, 20);
		xBox.setMaxStringLength(5);
		xBox.setFocused(true);
		yBox.setMaxStringLength(5);
		yBox.setFocused(false);
		zBox.setMaxStringLength(5);
		zBox.setFocused(false);
		rBox.setMaxStringLength(3);
		rBox.setFocused(false);
		gBox.setMaxStringLength(3);
		gBox.setFocused(false);
		bBox.setMaxStringLength(3);
		bBox.setFocused(false);
		
	}
	
	/**
	 * "Called when the screen is unloaded. Used to disable keyboard repeat events."
	 */
	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.enabled)
			if(button.id == 1)
				mc.displayGuiScreen(prevMenu);
			else if(button.id == 0)
			{
				if(MiscUtils.isInteger(xBox.getText()) && MiscUtils.isInteger(yBox.getText()) && MiscUtils.isInteger(zBox.getText()) && MiscUtils.isInteger(rBox.getText()) && MiscUtils.isInteger(gBox.getText()) && MiscUtils.isInteger(bBox.getText())){
				//	Protocol.waypoint.x = Integer.parseInt(xBox.getText());
				//Protocol.waypoint.y = Integer.parseInt(yBox.getText());
				//Protocol.waypoint.z = Integer.parseInt(zBox.getText());
				//Protocol.waypoint.red = Integer.parseInt(rBox.getText());
				//Protocol.waypoint.green = Integer.parseInt(gBox.getText());
				//Protocol.waypoint.blue = Integer.parseInt(bBox.getText());
			}
	}
	}
	
	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char par1, int par2)
	{
		xBox.textboxKeyTyped(par1, par2);
		yBox.textboxKeyTyped(par1, par2);
		zBox.textboxKeyTyped(par1, par2);
		rBox.textboxKeyTyped(par1, par2);
		gBox.textboxKeyTyped(par1, par2);
		bBox.textboxKeyTyped(par1, par2);
		
		if(par2 == 28 || par2 == 156)
			actionPerformed((GuiButton)buttonList.get(0));
	}
	
	/**
	 * Called when the mouse is clicked.
	 *
	 * @throws IOException
	 */
	@Override
	protected void mouseClicked(int par1, int par2, int par3)
		throws IOException
	{
		super.mouseClicked(par1, par2, par3);
		xBox.mouseClicked(par1, par2, par3);
		yBox.mouseClicked(par1, par2, par3);
		zBox.mouseClicked(par1, par2, par3);
		rBox.mouseClicked(par1, par2, par3);
		gBox.mouseClicked(par1, par2, par3);
		bBox.mouseClicked(par1, par2, par3);
		if(xBox.isFocused())
		{
			errorText = "";
			helpText = "";
		}
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		
		xBox.drawTextBox();
		yBox.drawTextBox();
		zBox.drawTextBox();
		rBox.drawTextBox();
		gBox.drawTextBox();
		bBox.drawTextBox();
		
		Gui.drawString(Wrapper.mc().fontRendererObj, "X", width / 2 - 100, 51, 0xffffffff);
		
		super.drawScreen(par1, par2, par3);
	}
}
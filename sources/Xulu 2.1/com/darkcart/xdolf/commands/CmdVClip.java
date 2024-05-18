package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.util.Value;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class CmdVClip extends Command
{
	public CmdVClip() 
	{
		super("vclip");
	}
	public static Value height = new Value(".vclip Height");

	@Override
	public void runCommand(String s, String[] args)
	{
		if(args.length != 1)
			 Minecraft.getMinecraft();
	      EntityPlayerSP var10000 = Minecraft.getMinecraft().player;
	      Minecraft.getMinecraft();
	      double var10001 = Minecraft.getMinecraft().player.posX;
	      Minecraft.getMinecraft();
	      double var10002 = Minecraft.getMinecraft().player.posY + height.getValue();
	      Minecraft.getMinecraft();
	      var10000.setPosition(var10001, var10002, Minecraft.getMinecraft().player.posZ);
	}

	@Override
	public String getDescription()
	{
		return "What do you think it does? Figure it out yourself";
	}

	@Override
	public String getSyntax()
	{
		return "vclip";
	}
}

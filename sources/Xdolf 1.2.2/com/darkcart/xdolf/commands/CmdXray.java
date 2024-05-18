 package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.mods.world.XRay;
import net.minecraft.block.Block;

public class CmdXray extends Command
{
	public CmdXray()
	{
		super("xray");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add"))
			{
				String name = args[1];
				if(!((XRay)Hacks.findMod(XRay.class)).xrayBlocks.contains(Block.getBlockFromName(name)))
				{
					if(Block.getBlockFromName(name) != null)
 					{	
						((XRay)Hacks.findMod(XRay.class)).xrayBlocks.add(Block.getBlockFromName(name));
						Wrapper.addChatMessage("Added \247e" + name + "\247f to xray list.");
						if(Hacks.findMod(XRay.class).isEnabled())
						{
							Wrapper.getMinecraft().renderGlobal.loadRenderers();
						}
						Wrapper.getFileManager().saveXrayList();
				}else{
					Wrapper.addChatMessage("\247e" + name + "\247f is not a recognized block.");
				}
				}else
				{
					Wrapper.addChatMessage("\247e" + name + "\247f is already in the xray list.");
				}
			}else if(args[0].equalsIgnoreCase("del"))
			{
				String name = args[1];
				if(((XRay)Hacks.findMod(XRay.class)).xrayBlocks.contains(Block.getBlockFromName(name)))
				{
					XRay.xrayBlocks.remove(Block.getBlockFromName(name));
					
					Wrapper.addChatMessage("Removed \247e" + name + "\247f from xray list.");
					if(Hacks.findMod(XRay.class).isEnabled())
					{
						Wrapper.getMinecraft().renderGlobal.loadRenderers();
					}
					Wrapper.getFileManager().saveXrayList();
				}else
				{
					Wrapper.addChatMessage("\247e" + name + "\247f is not in the xray list.");
				}
			}
		}catch(Exception e)
		{
			Wrapper.addChatMessage("Usage: " + getSyntax());
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() 
	{
		return "Custom xray.";
	}

	@Override
	public String getSyntax() 
	{
		return "xray add/del <block name>";
	}
}
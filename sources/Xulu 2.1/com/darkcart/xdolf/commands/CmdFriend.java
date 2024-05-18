package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Friend;

public class CmdFriend extends Command
{
	public CmdFriend()
	{
		super("friend"); 
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add"))
			{
				String name = args[1];
				String alias = args[2];
				if(!Wrapper.getFriends().isFriend(name))
				{
					Wrapper.addChatMessage("Protected " + name + " as " + alias + ".");
					Wrapper.getFriends().addFriend(name, alias);
					Wrapper.getFileManager().saveFriends();
				}else
				{
					Wrapper.addChatMessage(name + " is already your friend.");
				}
			}
			if(args[0].equalsIgnoreCase("del"))
			{
				String name = args[1];
				if(Wrapper.getFriends().isFriend(name))
				{
					Wrapper.getFriends().removeFriend(name);
					Wrapper.addChatMessage("Removed " + name + " from friends.");
					Wrapper.getFileManager().saveFriends();
				}else
				{
					Wrapper.addChatMessage(name + " is not your friend.");
				}
			}
			if(args[0].equalsIgnoreCase("list"))
			{
				for(Friend friend: Wrapper.getFriends().friendsList)
				{
					Wrapper.addChatMessage(friend.getAlias());
				}
				Wrapper.addChatMessage(Wrapper.getFriends().friendsList.size() + " friend(s).");
			}
			if(args[0].equalsIgnoreCase("clear"))
			{
				try
				{
					Wrapper.getFriends().friendsList.clear();
					Wrapper.getFileManager().saveFriends();
					Wrapper.addChatMessage("Cleared friends.");
				}catch(Exception e) {}
			}
		}catch(Exception e)
		{
			Wrapper.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Adds and removes friends.";
	}

	@Override
	public String getSyntax()
	{
		return "friend add <name> <alias>, friend del <name>";
	}
}

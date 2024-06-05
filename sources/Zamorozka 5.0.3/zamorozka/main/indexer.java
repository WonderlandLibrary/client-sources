package zamorozka.main;

import zamorozka.ui.FriendManager;

public class indexer {
	public static FriendManager friendManager;IiIiiIII
	
	public static FriendManager getFriends()
	{
		if(friendManager == null) friendManager = new FriendManager();
		
		return friendManager;
	}
}

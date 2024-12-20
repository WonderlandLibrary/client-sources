package net.minecraft.server.management;

import java.io.File;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListBans extends UserList<GameProfile, UserListBansEntry> {
	public UserListBans(File bansFile) {
		super(bansFile);
	}

	@Override
	protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
		return new UserListBansEntry(entryData);
	}

	public boolean isBanned(GameProfile profile) {
		return this.hasEntry(profile);
	}

	@Override
	public String[] getKeys() {
		String[] astring = new String[this.getValues().size()];
		int i = 0;

		for (UserListBansEntry userlistbansentry : this.getValues().values()) {
			astring[i++] = userlistbansentry.getValue().getName();
		}

		return astring;
	}

	/**
	 * Gets the key value for the given object
	 */
	@Override
	protected String getObjectKey(GameProfile obj) {
		return obj.getId().toString();
	}

	/**
	 * Get a {@link GameProfile} that is a member of this list by its user name. Returns {@code null} if no entry was found.
	 */
	public GameProfile getBannedProfile(String username) {
		for (UserListBansEntry userlistbansentry : this.getValues().values()) {
			if (username.equalsIgnoreCase(userlistbansentry.getValue().getName())) { return userlistbansentry.getValue(); }
		}

		return null;
	}
}

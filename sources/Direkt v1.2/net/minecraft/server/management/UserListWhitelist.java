package net.minecraft.server.management;

import java.io.File;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListWhitelist extends UserList<GameProfile, UserListWhitelistEntry> {
	public UserListWhitelist(File p_i1132_1_) {
		super(p_i1132_1_);
	}

	@Override
	protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
		return new UserListWhitelistEntry(entryData);
	}

	@Override
	public String[] getKeys() {
		String[] astring = new String[this.getValues().size()];
		int i = 0;

		for (UserListWhitelistEntry userlistwhitelistentry : this.getValues().values()) {
			astring[i++] = userlistwhitelistentry.getValue().getName();
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
	 * Get a GameProfile entry by its name
	 */
	public GameProfile getByName(String profileName) {
		for (UserListWhitelistEntry userlistwhitelistentry : this.getValues().values()) {
			if (profileName.equalsIgnoreCase(userlistwhitelistentry.getValue().getName())) { return userlistwhitelistentry.getValue(); }
		}

		return null;
	}
}

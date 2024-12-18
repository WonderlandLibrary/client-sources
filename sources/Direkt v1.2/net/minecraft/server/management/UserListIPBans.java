package net.minecraft.server.management;

import java.io.File;
import java.net.SocketAddress;

import com.google.gson.JsonObject;

public class UserListIPBans extends UserList<String, UserListIPBansEntry> {
	public UserListIPBans(File bansFile) {
		super(bansFile);
	}

	@Override
	protected UserListEntry<String> createEntry(JsonObject entryData) {
		return new UserListIPBansEntry(entryData);
	}

	public boolean isBanned(SocketAddress address) {
		String s = this.addressToString(address);
		return this.hasEntry(s);
	}

	public UserListIPBansEntry getBanEntry(SocketAddress address) {
		String s = this.addressToString(address);
		return this.getEntry(s);
	}

	private String addressToString(SocketAddress address) {
		String s = address.toString();

		if (s.contains("/")) {
			s = s.substring(s.indexOf(47) + 1);
		}

		if (s.contains(":")) {
			s = s.substring(0, s.indexOf(58));
		}

		return s;
	}
}

package net.minecraft.server.management;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;

public class PreYggdrasilConverter {
	private static final Logger LOGGER = LogManager.getLogger();
	public static final File OLD_IPBAN_FILE = new File("banned-ips.txt");
	public static final File OLD_PLAYERBAN_FILE = new File("banned-players.txt");
	public static final File OLD_OPS_FILE = new File("ops.txt");
	public static final File OLD_WHITELIST_FILE = new File("white-list.txt");

	private static void lookupNames(MinecraftServer server, Collection<String> names, ProfileLookupCallback callback) {
		String[] astring = Iterators.toArray(Iterators.filter(names.iterator(), new Predicate<String>() {
			@Override
			public boolean apply(@Nullable String p_apply_1_) {
				return !StringUtils.isNullOrEmpty(p_apply_1_);
			}
		}), String.class);

		if (server.isServerInOnlineMode()) {
			server.getGameProfileRepository().findProfilesByNames(astring, Agent.MINECRAFT, callback);
		} else {
			for (String s : astring) {
				UUID uuid = EntityPlayer.getUUID(new GameProfile((UUID) null, s));
				GameProfile gameprofile = new GameProfile(uuid, s);
				callback.onProfileLookupSucceeded(gameprofile);
			}
		}
	}

	public static String convertMobOwnerIfNeeded(final MinecraftServer server, String username) {
		if (!StringUtils.isNullOrEmpty(username) && (username.length() <= 16)) {
			GameProfile gameprofile = server.getPlayerProfileCache().getGameProfileForUsername(username);

			if ((gameprofile != null) && (gameprofile.getId() != null)) {
				return gameprofile.getId().toString();
			} else if (!server.isSinglePlayer() && server.isServerInOnlineMode()) {
				final List<GameProfile> list = Lists.<GameProfile> newArrayList();
				ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
					@Override
					public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
						server.getPlayerProfileCache().addEntry(p_onProfileLookupSucceeded_1_);
						list.add(p_onProfileLookupSucceeded_1_);
					}

					@Override
					public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
						PreYggdrasilConverter.LOGGER.warn("Could not lookup user whitelist entry for {}", new Object[] { p_onProfileLookupFailed_1_.getName(), p_onProfileLookupFailed_2_ });
					}
				};
				lookupNames(server, Lists.newArrayList(new String[] { username }), profilelookupcallback);
				return !list.isEmpty() && (list.get(0).getId() != null) ? list.get(0).getId().toString() : "";
			} else {
				return EntityPlayer.getUUID(new GameProfile((UUID) null, username)).toString();
			}
		} else {
			return username;
		}
	}
}

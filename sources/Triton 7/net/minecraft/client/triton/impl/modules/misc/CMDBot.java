package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.client.triton.management.enemies.EnemyManager;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.PacketReceiveEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.friend.Friend;
import net.minecraft.client.triton.management.friend.FriendManager;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.module.ModuleManager;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.PathFind;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Mod
public class CMDBot extends Module {

	private boolean following;
	private String followName;

	@EventTarget(1)
	public void preTick(UpdateEvent e) {
		if (e.getState() == e.getState().PRE) {
			if (ClientUtils.player().isDead) {
				ClientUtils.player().respawnPlayer();
			}
			if (this.following) {
				try {
					PathFind pf = new PathFind(this.followName);
					e.setPitch(PathFind.fakePitch - 30);
					e.setYaw(PathFind.fakeYaw);
				} catch (Exception ex) {

				}
			}
		}
	}

	@EventTarget
	public void onReceivePacket(PacketReceiveEvent e) {
		if (e.getPacket() instanceof S02PacketChat) {
			S02PacketChat message = (S02PacketChat) e.getPacket();
			if (message.func_148915_c().getFormattedText().contains("-follow")) {
				for (Friend friend : FriendManager.friendsList) {
					if (message.func_148915_c().getFormattedText().contains(friend.name)) {
						String s = message.func_148915_c().getFormattedText();
						s = s.substring(s.indexOf("-follow ") + 8);
						s = s.substring(0, s.indexOf("§"));
						this.following = true;
						this.followName = s;
						ClientUtils.player().sendChatMessage("Now following " + s);
						break;
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-stopfollow")) {
				for (Friend friend : FriendManager.friendsList) {
					if (message.func_148915_c().getFormattedText().contains(friend.name)) {
						String s = message.func_148915_c().getFormattedText();
						s = s.substring(s.indexOf("-stopfollow ") + 12);
						s = s.substring(0, s.indexOf("§"));
						this.following = false;
						this.followName = "";
						ClientUtils.player().sendChatMessage("No longer following " + s);
						break;
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-an hero")) {
				for (Friend friend : FriendManager.friendsList) {
					if (message.func_148915_c().getFormattedText().contains(friend.name)) {
						ClientUtils.player().sendChatMessage("/suicide");
						break;
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-tpahere")) {
				for (Friend friend : FriendManager.friendsList) {
					if (message.func_148915_c().getFormattedText().contains(friend.name)) {
						ClientUtils.player().sendChatMessage("/tpa " + friend.name);
						break;
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-pvp")) {
				for (Friend friend : FriendManager.friendsList) {
					if (message.func_148915_c().getFormattedText().contains(friend.name)) {
						ClientUtils.player().sendChatMessage("/pvp");
						break;
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-newkit")) {
				for (Friend friend : FriendManager.friendsList) {
					if (message.func_148915_c().getFormattedText().contains(friend.name)) {
						ClientUtils.player().sendChatMessage("Grabbing a new kit");
						ClientUtils.player().sendChatMessage("/newkit");
						break;
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-unstuck")) {
				for (Friend friend : FriendManager.friendsList) {
					if (message.func_148915_c().getFormattedText().contains(friend.name)) {
						ClientUtils.player().sendChatMessage("Attempting to restart movement.");
						ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
								BlockPos.ORIGIN, EnumFacing.DOWN));
						break;
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-enemy ")) {
				for (Entity mod : ClientUtils.loadedEntityList()) {
					if (mod instanceof EntityPlayer) {
						if (message.func_148915_c().getFormattedText().contains("-enemy " + mod.getName())) {
							for (Friend friend : FriendManager.friendsList) {
								if (message.func_148915_c().getFormattedText().contains(friend.name)) {
									if (EnemyManager.isEnemy(mod.getName())) {
										ClientUtils.player().sendChatMessage(mod.getName() + " is already enemied.");
										break;
									} else if (!EnemyManager.isEnemy(mod.getName())) {
										ClientUtils.player().sendChatMessage(mod.getName() + " has been enemied.");
										EnemyManager.addEnemy(mod.getName(), mod.getName());
										break;
									}
									break;
								}
							}
						}
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-enemyremove ")) {
				for (Entity mod : ClientUtils.loadedEntityList()) {
					if (mod instanceof EntityPlayer) {
						if (message.func_148915_c().getFormattedText().contains("-enemyremove " + mod.getName())) {
							for (Friend friend : FriendManager.friendsList) {
								if (message.func_148915_c().getFormattedText().contains(friend.name)) {
									if (EnemyManager.isEnemy(mod.getName())) {
										EnemyManager.removeEnemy(mod.getName());
										ClientUtils.player().sendChatMessage(mod.getName() + " has been removed from enemies.");
										break;
									} else if (!EnemyManager.isEnemy(mod.getName())) {
										ClientUtils.player().sendChatMessage(mod.getName() + " is not enemied.");
										break;
									}
									break;
								}
							}
						}
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-friend ")) {
				for (Entity mod : ClientUtils.loadedEntityList()) {
					if (mod instanceof EntityPlayer) {
						if (message.func_148915_c().getFormattedText().contains("-friend " + mod.getName())) {
							for (Friend friend : FriendManager.friendsList) {
								if (message.func_148915_c().getFormattedText().contains(friend.name)) {
									if (FriendManager.isFriend(mod.getName())) {
										ClientUtils.player().sendChatMessage(mod.getName() + " is already a friend.");
										break;
									} else if (!FriendManager.isFriend(mod.getName())) {
										ClientUtils.player().sendChatMessage(mod.getName() + " has been friended.");
										FriendManager.addFriend(mod.getName(), mod.getName());
										break;
									}
									break;
								}
							}
						}
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-friendremove ")) {
				for (Entity mod : ClientUtils.loadedEntityList()) {
					if (mod instanceof EntityPlayer) {
						if (message.func_148915_c().getFormattedText().contains("-friendremove " + mod.getName())) {
							for (Friend friend : FriendManager.friendsList) {
								if (message.func_148915_c().getFormattedText().contains(friend.name)) {
									if (FriendManager.isFriend(mod.getName())) {
										FriendManager.removeFriend(mod.getName());
										ClientUtils.player().sendChatMessage(mod.getName() + " has been removed from friends.");
										break;
									} else if (!FriendManager.isFriend(mod.getName())) {
										ClientUtils.player().sendChatMessage(mod.getName() + " is not friended.");
										break;
									}
									break;
								}
							}
						}
					}
				}
			}
			if (message.func_148915_c().getFormattedText().contains("-toggle ")) {
				for (Module mod : ModuleManager.getModules()) {
					if (message.func_148915_c().getFormattedText().contains("-toggle " + mod.getDisplayName())) {
						for (Friend friend : FriendManager.friendsList) {
							if (message.func_148915_c().getFormattedText().contains(friend.name)) {
								mod.toggle();
								boolean state = mod.isEnabled();
								String s = state ? "On" : "Off";
								ClientUtils.player().sendChatMessage(mod.getDisplayName() + " is now " + s);
								break;
							}
						}
					}
				}
			}
		}
	}
}

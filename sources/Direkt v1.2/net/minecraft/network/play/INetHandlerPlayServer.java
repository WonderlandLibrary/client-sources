package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
import net.minecraft.network.play.client.*;

public interface INetHandlerPlayServer extends INetHandler {
	void handleAnimation(CPacketAnimation packetIn);

	/**
	 * Process chat messages (broadcast back to clients) and commands (executes)
	 */
	void processChatMessage(CPacketChatMessage packetIn);

	/**
	 * Retrieves possible tab completions for the requested command string and sends them to the client
	 */
	void processTabComplete(CPacketTabComplete packetIn);

	/**
	 * Processes the client status updates: respawn attempt from player, opening statistics or achievements, or acquiring 'open inventory' achievement
	 */
	void processClientStatus(CPacketClientStatus packetIn);

	/**
	 * Updates serverside copy of client settings: language, render distance, chat visibility, chat colours, difficulty, and whether to show the cape
	 */
	void processClientSettings(CPacketClientSettings packetIn);

	/**
	 * Received in response to the server requesting to confirm that the client-side open container matches the servers' after a mismatched container-slot manipulation. It will unlock the player's ability to manipulate the container contents
	 */
	void processConfirmTransaction(CPacketConfirmTransaction packetIn);

	/**
	 * Enchants the item identified by the packet given some convoluted conditions (matching window, which should/shouldn't be in use?)
	 */
	void processEnchantItem(CPacketEnchantItem packetIn);

	/**
	 * Executes a container/inventory slot manipulation as indicated by the packet. Sends the serverside result if they didn't match the indicated result and prevents further manipulation by the player until he confirms that it has the same open container/inventory
	 */
	void processClickWindow(CPacketClickWindow packetIn);

	/**
	 * Processes the client closing windows (container)
	 */
	void processCloseWindow(CPacketCloseWindow packetIn);

	/**
	 * Synchronizes serverside and clientside book contents and signing
	 */
	void processCustomPayload(CPacketCustomPayload packetIn);

	/**
	 * Processes interactions ((un)leashing, opening command block GUI) and attacks on an entity with players currently equipped item
	 */
	void processUseEntity(CPacketUseEntity packetIn);

	/**
	 * Updates a players' ping statistics
	 */
	void processKeepAlive(CPacketKeepAlive packetIn);

	/**
	 * Processes clients perspective on player positioning and/or orientation
	 */
	void processPlayer(CPacketPlayer packetIn);

	/**
	 * Processes a player starting/stopping flying
	 */
	void processPlayerAbilities(CPacketPlayerAbilities packetIn);

	/**
	 * Processes the player initiating/stopping digging on a particular spot, as well as a player dropping items?. (0: initiated, 1: reinitiated, 2? , 3-4 drop item (respectively without or with player control), 5: stopped; x,y,z, side clicked on;)
	 */
	void processPlayerDigging(CPacketPlayerDigging packetIn);

	/**
	 * Processes a range of action-types: sneaking, sprinting, waking from sleep, opening the inventory or setting jump height of the horse the player is riding
	 */
	void processEntityAction(CPacketEntityAction packetIn);

	/**
	 * Processes player movement input. Includes walking, strafing, jumping, sneaking; excludes riding and toggling flying/sprinting
	 */
	void processInput(CPacketInput packetIn);

	/**
	 * Updates which quickbar slot is selected
	 */
	void processHeldItemChange(CPacketHeldItemChange packetIn);

	/**
	 * Update the server with an ItemStack in a slot.
	 */
	void processCreativeInventoryAction(CPacketCreativeInventoryAction packetIn);

	void processUpdateSign(CPacketUpdateSign packetIn);

	void processRightClickBlock(CPacketPlayerTryUseItemOnBlock packetIn);

	/**
	 * Processes block placement and block activation (anvil, furnace, etc.)
	 */
	void processPlayerBlockPlacement(CPacketPlayerTryUseItem packetIn);

	void handleSpectate(CPacketSpectate packetIn);

	void handleResourcePackStatus(CPacketResourcePackStatus packetIn);

	void processSteerBoat(CPacketSteerBoat packetIn);

	void processVehicleMove(CPacketVehicleMove packetIn);

	void processConfirmTeleport(CPacketConfirmTeleport packetIn);
}

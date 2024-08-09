/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network.play;

import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.SAdvancementInfoPacket;
import net.minecraft.network.play.server.SAnimateBlockBreakPacket;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.network.play.server.SBlockActionPacket;
import net.minecraft.network.play.server.SCameraPacket;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.network.play.server.SCloseWindowPacket;
import net.minecraft.network.play.server.SCollectItemPacket;
import net.minecraft.network.play.server.SCombatPacket;
import net.minecraft.network.play.server.SCommandListPacket;
import net.minecraft.network.play.server.SConfirmTransactionPacket;
import net.minecraft.network.play.server.SCooldownPacket;
import net.minecraft.network.play.server.SCustomPayloadPlayPacket;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.network.play.server.SDisplayObjectivePacket;
import net.minecraft.network.play.server.SEntityEquipmentPacket;
import net.minecraft.network.play.server.SEntityHeadLookPacket;
import net.minecraft.network.play.server.SEntityMetadataPacket;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.network.play.server.SEntityPropertiesPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.network.play.server.SEntityTeleportPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;
import net.minecraft.network.play.server.SJoinGamePacket;
import net.minecraft.network.play.server.SKeepAlivePacket;
import net.minecraft.network.play.server.SMapDataPacket;
import net.minecraft.network.play.server.SMerchantOffersPacket;
import net.minecraft.network.play.server.SMountEntityPacket;
import net.minecraft.network.play.server.SMoveVehiclePacket;
import net.minecraft.network.play.server.SMultiBlockChangePacket;
import net.minecraft.network.play.server.SOpenBookWindowPacket;
import net.minecraft.network.play.server.SOpenHorseWindowPacket;
import net.minecraft.network.play.server.SOpenSignMenuPacket;
import net.minecraft.network.play.server.SOpenWindowPacket;
import net.minecraft.network.play.server.SPlaceGhostRecipePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlaySoundPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SPlayerDiggingPacket;
import net.minecraft.network.play.server.SPlayerListHeaderFooterPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.network.play.server.SPlayerLookPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.network.play.server.SQueryNBTResponsePacket;
import net.minecraft.network.play.server.SRecipeBookPacket;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SScoreboardObjectivePacket;
import net.minecraft.network.play.server.SSelectAdvancementsTabPacket;
import net.minecraft.network.play.server.SSendResourcePackPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.network.play.server.SSetExperiencePacket;
import net.minecraft.network.play.server.SSetPassengersPacket;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.network.play.server.SSpawnExperienceOrbPacket;
import net.minecraft.network.play.server.SSpawnMobPacket;
import net.minecraft.network.play.server.SSpawnMovingSoundEffectPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.network.play.server.SSpawnPaintingPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.network.play.server.SSpawnPlayerPacket;
import net.minecraft.network.play.server.SStatisticsPacket;
import net.minecraft.network.play.server.SStopSoundPacket;
import net.minecraft.network.play.server.STabCompletePacket;
import net.minecraft.network.play.server.STagsListPacket;
import net.minecraft.network.play.server.STeamsPacket;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.network.play.server.SUnloadChunkPacket;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.network.play.server.SUpdateChunkPositionPacket;
import net.minecraft.network.play.server.SUpdateHealthPacket;
import net.minecraft.network.play.server.SUpdateLightPacket;
import net.minecraft.network.play.server.SUpdateRecipesPacket;
import net.minecraft.network.play.server.SUpdateScorePacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.network.play.server.SUpdateViewDistancePacket;
import net.minecraft.network.play.server.SWindowItemsPacket;
import net.minecraft.network.play.server.SWindowPropertyPacket;
import net.minecraft.network.play.server.SWorldBorderPacket;
import net.minecraft.network.play.server.SWorldSpawnChangedPacket;

public interface IClientPlayNetHandler
extends INetHandler {
    public void handleSpawnObject(SSpawnObjectPacket var1);

    public void handleSpawnExperienceOrb(SSpawnExperienceOrbPacket var1);

    public void handleSpawnMob(SSpawnMobPacket var1);

    public void handleScoreboardObjective(SScoreboardObjectivePacket var1);

    public void handleSpawnPainting(SSpawnPaintingPacket var1);

    public void handleSpawnPlayer(SSpawnPlayerPacket var1);

    public void handleAnimation(SAnimateHandPacket var1);

    public void handleStatistics(SStatisticsPacket var1);

    public void handleRecipeBook(SRecipeBookPacket var1);

    public void handleBlockBreakAnim(SAnimateBlockBreakPacket var1);

    public void handleSignEditorOpen(SOpenSignMenuPacket var1);

    public void handleUpdateTileEntity(SUpdateTileEntityPacket var1);

    public void handleBlockAction(SBlockActionPacket var1);

    public void handleBlockChange(SChangeBlockPacket var1);

    public void handleChat(SChatPacket var1);

    public void handleMultiBlockChange(SMultiBlockChangePacket var1);

    public void handleMaps(SMapDataPacket var1);

    public void handleConfirmTransaction(SConfirmTransactionPacket var1);

    public void handleCloseWindow(SCloseWindowPacket var1);

    public void handleWindowItems(SWindowItemsPacket var1);

    public void handleOpenHorseWindow(SOpenHorseWindowPacket var1);

    public void handleWindowProperty(SWindowPropertyPacket var1);

    public void handleSetSlot(SSetSlotPacket var1);

    public void handleCustomPayload(SCustomPayloadPlayPacket var1);

    public void handleDisconnect(SDisconnectPacket var1);

    public void handleEntityStatus(SEntityStatusPacket var1);

    public void handleEntityAttach(SMountEntityPacket var1);

    public void handleSetPassengers(SSetPassengersPacket var1);

    public void handleExplosion(SExplosionPacket var1);

    public void handleChangeGameState(SChangeGameStatePacket var1);

    public void handleKeepAlive(SKeepAlivePacket var1);

    public void handleChunkData(SChunkDataPacket var1);

    public void processChunkUnload(SUnloadChunkPacket var1);

    public void handleEffect(SPlaySoundEventPacket var1);

    public void handleJoinGame(SJoinGamePacket var1);

    public void handleEntityMovement(SEntityPacket var1);

    public void handlePlayerPosLook(SPlayerPositionLookPacket var1);

    public void handleParticles(SSpawnParticlePacket var1);

    public void handlePlayerAbilities(SPlayerAbilitiesPacket var1);

    public void handlePlayerListItem(SPlayerListItemPacket var1);

    public void handleDestroyEntities(SDestroyEntitiesPacket var1);

    public void handleRemoveEntityEffect(SRemoveEntityEffectPacket var1);

    public void handleRespawn(SRespawnPacket var1);

    public void handleEntityHeadLook(SEntityHeadLookPacket var1);

    public void handleHeldItemChange(SHeldItemChangePacket var1);

    public void handleDisplayObjective(SDisplayObjectivePacket var1);

    public void handleEntityMetadata(SEntityMetadataPacket var1);

    public void handleEntityVelocity(SEntityVelocityPacket var1);

    public void handleEntityEquipment(SEntityEquipmentPacket var1);

    public void handleSetExperience(SSetExperiencePacket var1);

    public void handleUpdateHealth(SUpdateHealthPacket var1);

    public void handleTeams(STeamsPacket var1);

    public void handleUpdateScore(SUpdateScorePacket var1);

    public void func_230488_a_(SWorldSpawnChangedPacket var1);

    public void handleTimeUpdate(SUpdateTimePacket var1);

    public void handleSoundEffect(SPlaySoundEffectPacket var1);

    public void handleSpawnMovingSoundEffect(SSpawnMovingSoundEffectPacket var1);

    public void handleCustomSound(SPlaySoundPacket var1);

    public void handleCollectItem(SCollectItemPacket var1);

    public void handleEntityTeleport(SEntityTeleportPacket var1);

    public void handleEntityProperties(SEntityPropertiesPacket var1);

    public void handleEntityEffect(SPlayEntityEffectPacket var1);

    public void handleTags(STagsListPacket var1);

    public void handleCombatEvent(SCombatPacket var1);

    public void handleServerDifficulty(SServerDifficultyPacket var1);

    public void handleCamera(SCameraPacket var1);

    public void handleWorldBorder(SWorldBorderPacket var1);

    public void handleTitle(STitlePacket var1);

    public void handlePlayerListHeaderFooter(SPlayerListHeaderFooterPacket var1);

    public void handleResourcePack(SSendResourcePackPacket var1);

    public void handleUpdateBossInfo(SUpdateBossInfoPacket var1);

    public void handleCooldown(SCooldownPacket var1);

    public void handleMoveVehicle(SMoveVehiclePacket var1);

    public void handleAdvancementInfo(SAdvancementInfoPacket var1);

    public void handleSelectAdvancementsTab(SSelectAdvancementsTabPacket var1);

    public void handlePlaceGhostRecipe(SPlaceGhostRecipePacket var1);

    public void handleCommandList(SCommandListPacket var1);

    public void handleStopSound(SStopSoundPacket var1);

    public void handleTabComplete(STabCompletePacket var1);

    public void handleUpdateRecipes(SUpdateRecipesPacket var1);

    public void handlePlayerLook(SPlayerLookPacket var1);

    public void handleNBTQueryResponse(SQueryNBTResponsePacket var1);

    public void handleUpdateLight(SUpdateLightPacket var1);

    public void handleOpenBookPacket(SOpenBookWindowPacket var1);

    public void handleOpenWindowPacket(SOpenWindowPacket var1);

    public void handleMerchantOffers(SMerchantOffersPacket var1);

    public void handleUpdateViewDistancePacket(SUpdateViewDistancePacket var1);

    public void handleChunkPositionPacket(SUpdateChunkPositionPacket var1);

    public void handleAcknowledgePlayerDigging(SPlayerDiggingPacket var1);
}


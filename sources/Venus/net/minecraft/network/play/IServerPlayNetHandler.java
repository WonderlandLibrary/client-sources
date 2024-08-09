/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
import net.minecraft.network.play.client.CAnimateHandPacket;
import net.minecraft.network.play.client.CChatMessagePacket;
import net.minecraft.network.play.client.CClickWindowPacket;
import net.minecraft.network.play.client.CClientSettingsPacket;
import net.minecraft.network.play.client.CClientStatusPacket;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CCreativeInventoryActionPacket;
import net.minecraft.network.play.client.CCustomPayloadPacket;
import net.minecraft.network.play.client.CEditBookPacket;
import net.minecraft.network.play.client.CEnchantItemPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CInputPacket;
import net.minecraft.network.play.client.CJigsawBlockGeneratePacket;
import net.minecraft.network.play.client.CKeepAlivePacket;
import net.minecraft.network.play.client.CLockDifficultyPacket;
import net.minecraft.network.play.client.CMarkRecipeSeenPacket;
import net.minecraft.network.play.client.CMoveVehiclePacket;
import net.minecraft.network.play.client.CPickItemPacket;
import net.minecraft.network.play.client.CPlaceRecipePacket;
import net.minecraft.network.play.client.CPlayerAbilitiesPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.client.CQueryEntityNBTPacket;
import net.minecraft.network.play.client.CQueryTileEntityNBTPacket;
import net.minecraft.network.play.client.CRenameItemPacket;
import net.minecraft.network.play.client.CResourcePackStatusPacket;
import net.minecraft.network.play.client.CSeenAdvancementsPacket;
import net.minecraft.network.play.client.CSelectTradePacket;
import net.minecraft.network.play.client.CSetDifficultyPacket;
import net.minecraft.network.play.client.CSpectatePacket;
import net.minecraft.network.play.client.CSteerBoatPacket;
import net.minecraft.network.play.client.CTabCompletePacket;
import net.minecraft.network.play.client.CUpdateBeaconPacket;
import net.minecraft.network.play.client.CUpdateCommandBlockPacket;
import net.minecraft.network.play.client.CUpdateJigsawBlockPacket;
import net.minecraft.network.play.client.CUpdateMinecartCommandBlockPacket;
import net.minecraft.network.play.client.CUpdateRecipeBookStatusPacket;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.network.play.client.CUpdateStructureBlockPacket;
import net.minecraft.network.play.client.CUseEntityPacket;

public interface IServerPlayNetHandler
extends INetHandler {
    public void handleAnimation(CAnimateHandPacket var1);

    public void processChatMessage(CChatMessagePacket var1);

    public void processClientStatus(CClientStatusPacket var1);

    public void processClientSettings(CClientSettingsPacket var1);

    public void processConfirmTransaction(CConfirmTransactionPacket var1);

    public void processEnchantItem(CEnchantItemPacket var1);

    public void processClickWindow(CClickWindowPacket var1);

    public void processPlaceRecipe(CPlaceRecipePacket var1);

    public void processCloseWindow(CCloseWindowPacket var1);

    public void processCustomPayload(CCustomPayloadPacket var1);

    public void processUseEntity(CUseEntityPacket var1);

    public void processKeepAlive(CKeepAlivePacket var1);

    public void processPlayer(CPlayerPacket var1);

    public void processPlayerAbilities(CPlayerAbilitiesPacket var1);

    public void processPlayerDigging(CPlayerDiggingPacket var1);

    public void processEntityAction(CEntityActionPacket var1);

    public void processInput(CInputPacket var1);

    public void processHeldItemChange(CHeldItemChangePacket var1);

    public void processCreativeInventoryAction(CCreativeInventoryActionPacket var1);

    public void processUpdateSign(CUpdateSignPacket var1);

    public void processTryUseItemOnBlock(CPlayerTryUseItemOnBlockPacket var1);

    public void processTryUseItem(CPlayerTryUseItemPacket var1);

    public void handleSpectate(CSpectatePacket var1);

    public void handleResourcePackStatus(CResourcePackStatusPacket var1);

    public void processSteerBoat(CSteerBoatPacket var1);

    public void processVehicleMove(CMoveVehiclePacket var1);

    public void processConfirmTeleport(CConfirmTeleportPacket var1);

    public void handleRecipeBookUpdate(CMarkRecipeSeenPacket var1);

    public void func_241831_a(CUpdateRecipeBookStatusPacket var1);

    public void handleSeenAdvancements(CSeenAdvancementsPacket var1);

    public void processTabComplete(CTabCompletePacket var1);

    public void processUpdateCommandBlock(CUpdateCommandBlockPacket var1);

    public void processUpdateCommandMinecart(CUpdateMinecartCommandBlockPacket var1);

    public void processPickItem(CPickItemPacket var1);

    public void processRenameItem(CRenameItemPacket var1);

    public void processUpdateBeacon(CUpdateBeaconPacket var1);

    public void processUpdateStructureBlock(CUpdateStructureBlockPacket var1);

    public void processSelectTrade(CSelectTradePacket var1);

    public void processEditBook(CEditBookPacket var1);

    public void processNBTQueryEntity(CQueryEntityNBTPacket var1);

    public void processNBTQueryBlockEntity(CQueryTileEntityNBTPacket var1);

    public void func_217262_a(CUpdateJigsawBlockPacket var1);

    public void func_230549_a_(CJigsawBlockGeneratePacket var1);

    public void func_217263_a(CSetDifficultyPacket var1);

    public void func_217261_a(CLockDifficultyPacket var1);
}


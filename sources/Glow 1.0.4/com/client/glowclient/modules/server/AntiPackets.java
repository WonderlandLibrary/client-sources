package com.client.glowclient.modules.server;

import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.*;
import net.minecraft.network.play.client.*;

public class AntiPackets extends ModuleContainer
{
    public static BooleanValue Hb;
    public static final BooleanValue chat;
    public static BooleanValue HC;
    public static BooleanValue Gc;
    public static BooleanValue pa;
    public static final BooleanValue seenAdvancements;
    public static final BooleanValue player;
    public static BooleanValue ta;
    public static final BooleanValue advancementInfo;
    public static BooleanValue Na;
    public static BooleanValue Oa;
    public static BooleanValue Ya;
    public static final BooleanValue camera;
    public static BooleanValue oa;
    public static BooleanValue sa;
    public static final BooleanValue entityAction;
    public static BooleanValue Ua;
    public static BooleanValue Wa;
    public static BooleanValue Ta;
    public static BooleanValue ra;
    public static BooleanValue Qa;
    public static final BooleanValue resourcePackStatus;
    public static final BooleanValue changeGameState;
    public static BooleanValue Va;
    public static BooleanValue Za;
    public static final BooleanValue vehicleMove;
    public static final BooleanValue confirmTransaction;
    public static BooleanValue ua;
    public static final BooleanValue collectItem;
    public static BooleanValue wa;
    public static final BooleanValue input;
    public static final BooleanValue recipeInfo;
    public static final BooleanValue confirmTeleport;
    public static final BooleanValue blockBreakAnim;
    public static BooleanValue ia;
    public static final BooleanValue updateSign;
    public static BooleanValue La;
    public static final BooleanValue clientStatus;
    public static BooleanValue Ba;
    public static BooleanValue ha;
    public static BooleanValue aa;
    public static BooleanValue fa;
    public static BooleanValue Ia;
    public static final BooleanValue clickWindow;
    public static final BooleanValue playerAbilities;
    public static final BooleanValue closeWindow;
    public static BooleanValue ba;
    public static final BooleanValue clientSettings;
    public static BooleanValue Ea;
    public static final BooleanValue heldItemChange;
    public static BooleanValue la;
    public static final BooleanValue playerTryUseItem;
    public static BooleanValue Da;
    public static BooleanValue Ka;
    public static BooleanValue ca;
    public static final BooleanValue animation;
    public static final BooleanValue useEntity;
    public static final BooleanValue steerBoat;
    public static final BooleanValue playerDigging;
    public static final BooleanValue closeWindow;
    public static BooleanValue n;
    public static BooleanValue r;
    public static BooleanValue t;
    public static BooleanValue O;
    public static final BooleanValue chunkData;
    public static final BooleanValue enchantItem;
    public static final BooleanValue playerTryUseItemOnBlock;
    public static BooleanValue W;
    public static BooleanValue u;
    public static BooleanValue S;
    public static final BooleanValue creativeInventoryAction;
    public static BooleanValue o;
    public static final BooleanValue animation;
    public static BooleanValue U;
    public static final BooleanValue customPayload;
    public static BooleanValue s;
    public static BooleanValue Y;
    public static BooleanValue y;
    public static BooleanValue R;
    public static BooleanValue w;
    public static BooleanValue P;
    public static BooleanValue x;
    public static BooleanValue m;
    public static BooleanValue C;
    public static BooleanValue J;
    public static BooleanValue E;
    public static final BooleanValue keepAlive;
    public static final BooleanValue blockAction;
    public static BooleanValue j;
    public static BooleanValue F;
    public static final BooleanValue tabComplete;
    public static BooleanValue e;
    public static BooleanValue a;
    public static BooleanValue i;
    public static BooleanValue l;
    public static final BooleanValue blockChange;
    public static BooleanValue c;
    public static final BooleanValue placeRecipe;
    public static BooleanValue H;
    public static final BooleanValue chatMessage;
    public static BooleanValue M;
    public static BooleanValue G;
    public static BooleanValue d;
    public static BooleanValue L;
    public static BooleanValue A;
    public static final BooleanValue spectate;
    public static BooleanValue b;
    
    static {
        animation = ValueFactory.M("AntiPackets", "Animation", "Cancel CPacketAnimation", false);
        chatMessage = ValueFactory.M("AntiPackets", "ChatMessage", "Cancel CPacketChatMessage", false);
        clickWindow = ValueFactory.M("AntiPackets", "ClickWindow", "Cancel CPacketClickWindow", false);
        clientSettings = ValueFactory.M("AntiPackets", "ClientSettings", "Cancel CPacketClientSettings", false);
        clientStatus = ValueFactory.M("AntiPackets", "ClientStatus", "Cancel CPacketClientStatus", false);
        closeWindow = ValueFactory.M("AntiPackets", "CloseWindow", "Cancel CPacketCloseWindow", false);
        confirmTeleport = ValueFactory.M("AntiPackets", "ConfirmTeleport", "Cancel CPacketConfirmTeleport", false);
        confirmTransaction = ValueFactory.M("AntiPackets", "ConfirmTransaction", "Cancel CPacketConfirmTransaction", false);
        creativeInventoryAction = ValueFactory.M("AntiPackets", "CreativeInventoryAction", "Cancel CPacketCreativeInventoryAction", false);
        customPayload = ValueFactory.M("AntiPackets", "CustomPayload", "Cancel CPacketCustomPayload", false);
        enchantItem = ValueFactory.M("AntiPackets", "EnchantItem", "Cancel CPacketEnchantItem", false);
        entityAction = ValueFactory.M("AntiPackets", "EntityAction", "Cancel CPacketEntityAction", false);
        heldItemChange = ValueFactory.M("AntiPackets", "HeldItemChange", "Cancel CPacketHeldItemChange", false);
        input = ValueFactory.M("AntiPackets", "Input", "Cancel CPacketInput", false);
        keepAlive = ValueFactory.M("AntiPackets", "KeepAlive", "Cancel CPacketKeepAlive", false);
        placeRecipe = ValueFactory.M("AntiPackets", "PlaceRecipe", "Cancel CPacketPlaceRecipe", false);
        player = ValueFactory.M("AntiPackets", "Player", "Cancel CPacketPlayer", false);
        playerAbilities = ValueFactory.M("AntiPackets", "PlayerAbilities", "Cancel CPacketPlayerAbilities", false);
        playerDigging = ValueFactory.M("AntiPackets", "PlayerDigging", "Cancel CPacketPlayerDigging", false);
        playerTryUseItem = ValueFactory.M("AntiPackets", "PlayerTryUseItem", "Cancel CPacketPlayerTryUseItem", false);
        playerTryUseItemOnBlock = ValueFactory.M("AntiPackets", "PlayerTryUseItemOnBlock", "Cancel CPacketPlayerTryUseItemOnBlock", false);
        recipeInfo = ValueFactory.M("AntiPackets", "RecipeInfo", "Cancel CPacketRecipeInfo", false);
        resourcePackStatus = ValueFactory.M("AntiPackets", "ResourcePackStatus", "Cancel CPacketResourcePackStatus", false);
        seenAdvancements = ValueFactory.M("AntiPackets", "SeenAdvancements", "Cancel CPacketSeenAdvancements", false);
        spectate = ValueFactory.M("AntiPackets", "Spectate", "Cancel CPacketSpectate", false);
        steerBoat = ValueFactory.M("AntiPackets", "SteerBoat", "Cancel CPacketSteerBoat", false);
        tabComplete = ValueFactory.M("AntiPackets", "TabComplete", "Cancel CPacketTabComplete", false);
        updateSign = ValueFactory.M("AntiPackets", "UpdateSign", "Cancel CPacketUpdateSign", false);
        useEntity = ValueFactory.M("AntiPackets", "UseEntity", "Cancel CPacketUseEntity", false);
        vehicleMove = ValueFactory.M("AntiPackets", "VehicleMove", "Cancel CPacketVehicleMove", false);
        advancementInfo = ValueFactory.M("AntiPacketsServer", "AdvancementInfo", "Cancel SPacketAdvancementInfo", false);
        animation = ValueFactory.M("AntiPacketsServer", "Animation", "Cancel SPacketAnimation", false);
        blockAction = ValueFactory.M("AntiPacketsServer", "BlockAction", "Cancel SPacketBlockAction", false);
        blockBreakAnim = ValueFactory.M("AntiPacketsServer", "BlockBreakAnim", "Cancel SPacketBlockBreakAnim", false);
        blockChange = ValueFactory.M("AntiPacketsServer", "BlockChange", "Cancel SPacketBlockChange", false);
        camera = ValueFactory.M("AntiPacketsServer", "Camera", "Cancel SPacketCamera", false);
        changeGameState = ValueFactory.M("AntiPacketsServer", "ChangeGameState", "Cancel SPacketChangeGameState", false);
        chat = ValueFactory.M("AntiPacketsServer", "Chat", "Cancel SPacketChat", false);
        chunkData = ValueFactory.M("AntiPacketsServer", "ChunkData", "Cancel SPacketChunkData", false);
        closeWindow = ValueFactory.M("AntiPacketsServer", "CloseWindow", "Cancel SPacketCloseWindow", false);
        collectItem = ValueFactory.M("AntiPacketsServer", "CollectItem", "Cancel SPacketCollectItem", false);
        AntiPackets.O = ValueFactory.D("CombatEvent");
        AntiPackets.n = ValueFactory.D("ConfirmTransaction");
        AntiPackets.Oa = ValueFactory.D("Cooldown");
        AntiPackets.Ea = ValueFactory.D("CustomPayload");
        AntiPackets.HC = ValueFactory.D("CustomSound");
        AntiPackets.l = ValueFactory.D("DestroyEntities");
        AntiPackets.sa = ValueFactory.D("Disconnect");
        AntiPackets.J = ValueFactory.D("DisplayObjective");
        AntiPackets.a = ValueFactory.D("Effect");
        AntiPackets.o = ValueFactory.D("Entity");
        AntiPackets.fa = ValueFactory.D("EntityAttach");
        AntiPackets.R = ValueFactory.D("EntityEffect");
        AntiPackets.y = ValueFactory.D("EntityEquipment");
        AntiPackets.wa = ValueFactory.D("EntityHeadLook");
        AntiPackets.Ta = ValueFactory.D("EntityMetadata");
        AntiPackets.ha = ValueFactory.D("EntityProperties");
        AntiPackets.La = ValueFactory.D("EntityStatus");
        AntiPackets.ba = ValueFactory.D("EntityTeleport");
        AntiPackets.ra = ValueFactory.D("EntityVelocity");
        AntiPackets.Va = ValueFactory.D("Explosion");
        AntiPackets.Qa = ValueFactory.D("HeldIteMC.ange");
        AntiPackets.U = ValueFactory.D("JoinGame");
        AntiPackets.s = ValueFactory.D("KeepAlive");
        AntiPackets.Ba = ValueFactory.D("Maps");
        AntiPackets.Hb = ValueFactory.D("MoveVehicle");
        AntiPackets.Gc = ValueFactory.D("MultiBlockChange");
        AntiPackets.A = ValueFactory.D("OpenWindow");
        AntiPackets.Da = ValueFactory.D("Particles");
        AntiPackets.ca = ValueFactory.D("PlaceGhostRecipe");
        AntiPackets.Ia = ValueFactory.D("PlayerAbilities");
        AntiPackets.Y = ValueFactory.D("PlayerListHeaderFooter");
        AntiPackets.ia = ValueFactory.D("PlayerListItem");
        AntiPackets.ta = ValueFactory.D("PlayerPosLook");
        AntiPackets.Za = ValueFactory.D("RecipeBook");
        AntiPackets.M = ValueFactory.D("RemoveEntityEffect");
        AntiPackets.P = ValueFactory.D("ResourcePackSend");
        AntiPackets.pa = ValueFactory.D("Respawn");
        AntiPackets.t = ValueFactory.D("ScoreboardObjective");
        AntiPackets.r = ValueFactory.D("SelectAdvancementsTab");
        AntiPackets.H = ValueFactory.D("ServerDifficulty");
        AntiPackets.Ka = ValueFactory.D("SetExperience");
        AntiPackets.E = ValueFactory.D("SetPassengers");
        AntiPackets.j = ValueFactory.D("SetSlot");
        AntiPackets.F = ValueFactory.D("SignEditorOpen");
        AntiPackets.c = ValueFactory.D("SoundEffect");
        AntiPackets.Wa = ValueFactory.D("SpawnExperienceOrb");
        AntiPackets.L = ValueFactory.D("SpawnGlobalEntity");
        AntiPackets.m = ValueFactory.D("SpawnMob");
        AntiPackets.Na = ValueFactory.D("SpawnObject");
        AntiPackets.i = ValueFactory.D("SpawnPainting");
        AntiPackets.d = ValueFactory.D("SpawnPlayer");
        AntiPackets.C = ValueFactory.D("SpawnPosition");
        AntiPackets.Ya = ValueFactory.D("Statistics");
        AntiPackets.b = ValueFactory.D("TabComplete");
        AntiPackets.Ua = ValueFactory.D("Teams");
        AntiPackets.S = ValueFactory.D("TimeUpdate");
        AntiPackets.ua = ValueFactory.D("Title");
        AntiPackets.oa = ValueFactory.D("UnloadChunk");
        AntiPackets.aa = ValueFactory.D("UpdateBossInfo");
        AntiPackets.e = ValueFactory.D("UpdateHealth");
        AntiPackets.w = ValueFactory.D("UpdateScore");
        AntiPackets.W = ValueFactory.D("UpdateTileEntity");
        AntiPackets.la = ValueFactory.D("UseBed");
        AntiPackets.G = ValueFactory.D("WindowItems");
        AntiPackets.x = ValueFactory.D("WindowProperty");
        AntiPackets.u = ValueFactory.D("WorldBorder");
    }
    
    public AntiPackets() {
        super(Category.SERVER, "AntiPackets", false, -1, "Cancel specific packets");
    }
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        if (eventServerPacket.getPacket() instanceof SPacketAdvancementInfo && AntiPackets.advancementInfo.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketAnimation && AntiPackets.animation.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketBlockAction && AntiPackets.blockAction.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketBlockBreakAnim && AntiPackets.blockBreakAnim.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketBlockChange && AntiPackets.blockChange.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketCamera && AntiPackets.camera.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketChangeGameState && AntiPackets.changeGameState.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketChat && AntiPackets.chat.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketChunkData && AntiPackets.chunkData.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketCloseWindow && AntiPackets.closeWindow.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketCollectItem && AntiPackets.collectItem.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketCombatEvent && AntiPackets.O.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketConfirmTransaction && AntiPackets.n.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketCooldown && AntiPackets.Oa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketCustomPayload && AntiPackets.Ea.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketCustomSound && AntiPackets.HC.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketDestroyEntities && AntiPackets.l.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketDisconnect && AntiPackets.sa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketDisplayObjective && AntiPackets.J.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEffect && AntiPackets.a.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntity && AntiPackets.o.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityAttach && AntiPackets.fa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityEffect && AntiPackets.R.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityEquipment && AntiPackets.y.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityHeadLook && AntiPackets.wa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityMetadata && AntiPackets.Ta.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityProperties && AntiPackets.ha.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityStatus && AntiPackets.La.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityTeleport && AntiPackets.ba.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketEntityVelocity && AntiPackets.ra.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketExplosion && AntiPackets.Va.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketHeldItemChange && AntiPackets.Qa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketJoinGame && AntiPackets.U.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketKeepAlive && AntiPackets.s.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketMaps && AntiPackets.Ba.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketMoveVehicle && AntiPackets.Hb.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketMultiBlockChange && AntiPackets.Gc.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketOpenWindow && AntiPackets.A.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketParticles && AntiPackets.Da.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketPlaceGhostRecipe && AntiPackets.ca.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketPlayerAbilities && AntiPackets.Ia.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketPlayerListHeaderFooter && AntiPackets.Y.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketPlayerListItem && AntiPackets.ia.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketPlayerPosLook && AntiPackets.ta.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketRecipeBook && AntiPackets.Za.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketRemoveEntityEffect && AntiPackets.M.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketResourcePackSend && AntiPackets.P.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketRespawn && AntiPackets.pa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketScoreboardObjective && AntiPackets.t.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSelectAdvancementsTab && AntiPackets.r.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketServerDifficulty && AntiPackets.H.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSetExperience && AntiPackets.Ka.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSetPassengers && AntiPackets.E.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSetSlot && AntiPackets.j.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSignEditorOpen && AntiPackets.F.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSoundEffect && AntiPackets.c.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSpawnExperienceOrb && AntiPackets.Wa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSpawnGlobalEntity && AntiPackets.L.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSpawnMob && AntiPackets.m.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSpawnObject && AntiPackets.Na.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSpawnPainting && AntiPackets.i.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSpawnPlayer && AntiPackets.d.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketSpawnPosition && AntiPackets.C.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketStatistics && AntiPackets.Ya.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketTabComplete && AntiPackets.b.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketTeams && AntiPackets.Ua.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketTimeUpdate && AntiPackets.S.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketTitle && AntiPackets.ua.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketUnloadChunk && AntiPackets.oa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketUpdateBossInfo && AntiPackets.aa.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketUpdateHealth && AntiPackets.e.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketUpdateScore && AntiPackets.w.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketUpdateTileEntity && AntiPackets.W.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketUseBed && AntiPackets.la.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketWindowItems && AntiPackets.G.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketWindowProperty && AntiPackets.x.M()) {
            eventServerPacket.setCanceled(true);
        }
        if (eventServerPacket.getPacket() instanceof SPacketWorldBorder && AntiPackets.u.M()) {
            eventServerPacket.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void M(final Cd cd) {
        if (cd.getPacket() instanceof CPacketAnimation && AntiPackets.animation.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketChatMessage && AntiPackets.chatMessage.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketClickWindow && AntiPackets.clickWindow.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketClientSettings && AntiPackets.clientSettings.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketClientStatus && AntiPackets.clientStatus.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketCloseWindow && AntiPackets.closeWindow.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketConfirmTeleport && AntiPackets.confirmTeleport.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketConfirmTransaction && AntiPackets.confirmTransaction.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketCreativeInventoryAction && AntiPackets.creativeInventoryAction.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketCustomPayload && AntiPackets.customPayload.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketEnchantItem && AntiPackets.enchantItem.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketEntityAction && AntiPackets.entityAction.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketHeldItemChange && AntiPackets.heldItemChange.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketInput && AntiPackets.input.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketKeepAlive && AntiPackets.keepAlive.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketPlaceRecipe && AntiPackets.placeRecipe.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketPlayer && AntiPackets.player.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketPlayerAbilities && AntiPackets.playerAbilities.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketPlayerDigging && AntiPackets.playerDigging.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketPlayerTryUseItem && AntiPackets.playerTryUseItem.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && AntiPackets.playerTryUseItemOnBlock.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketRecipeInfo && AntiPackets.recipeInfo.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketResourcePackStatus && AntiPackets.resourcePackStatus.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketSeenAdvancements && AntiPackets.seenAdvancements.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketSpectate && AntiPackets.spectate.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketSteerBoat && AntiPackets.steerBoat.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketTabComplete && AntiPackets.tabComplete.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketUpdateSign && AntiPackets.updateSign.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketUseEntity && AntiPackets.useEntity.M()) {
            cd.setCanceled(true);
        }
        if (cd.getPacket() instanceof CPacketVehicleMove && AntiPackets.vehicleMove.M()) {
            cd.setCanceled(true);
        }
    }
}

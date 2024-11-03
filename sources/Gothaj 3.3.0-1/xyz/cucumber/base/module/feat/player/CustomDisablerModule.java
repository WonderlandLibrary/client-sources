package xyz.cucumber.base.module.feat.player;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S45PacketTitle;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Packet editor",
   name = "Custom Disabler"
)
public class CustomDisablerModule extends Mod {
   private final BooleanSettings c00 = new BooleanSettings("C00PacketKeepAlive", false);
   private final BooleanSettings c01 = new BooleanSettings("C01PacketChatMessage", false);
   private final BooleanSettings c02 = new BooleanSettings("C02PacketUseEntity", false);
   private final BooleanSettings c03 = new BooleanSettings("C03PacketPlayer", false);
   private final BooleanSettings c07 = new BooleanSettings("C07PacketPlayerDigging", false);
   private final BooleanSettings c08 = new BooleanSettings("C08PacketPlayerBlockPlacement", false);
   private final BooleanSettings c09 = new BooleanSettings("C09PacketHeldItemChange", false);
   private final BooleanSettings c0a = new BooleanSettings("C0APacketAnimation", false);
   private final BooleanSettings c0b = new BooleanSettings("C0BPacketEntityAction", false);
   private final BooleanSettings c0c = new BooleanSettings("C0CPacketInput", false);
   private final BooleanSettings c0d = new BooleanSettings("C0DPacketCloseWindow", false);
   private final BooleanSettings c0e = new BooleanSettings("C0EPacketClickWindow", false);
   private final BooleanSettings c0f = new BooleanSettings("C0FPacketConfirmTransaction", false);
   private final BooleanSettings c10 = new BooleanSettings("C10PacketCreativeInventoryAction", false);
   private final BooleanSettings c11 = new BooleanSettings("C11PacketEnchantItem", false);
   private final BooleanSettings c12 = new BooleanSettings("C12PacketUpdateSign", false);
   private final BooleanSettings c13 = new BooleanSettings("C13PacketPlayerAbilities", false);
   private final BooleanSettings c14 = new BooleanSettings("C14PacketTabComplete", false);
   private final BooleanSettings c15 = new BooleanSettings("C15PacketClientSettings", false);
   private final BooleanSettings c16 = new BooleanSettings("C16PacketClientStatus", false);
   private final BooleanSettings c17 = new BooleanSettings("C17PacketCustomPayload", false);
   private final BooleanSettings c18 = new BooleanSettings("C18PacketSpectate", false);
   private final BooleanSettings c19 = new BooleanSettings("C19PacketResourcePackStatus", false);
   private final BooleanSettings s00 = new BooleanSettings("S00PacketKeepAlive", false);
   private final BooleanSettings s01 = new BooleanSettings("S01PacketJoinGame", false);
   private final BooleanSettings s02 = new BooleanSettings("S02PacketChat", false);
   private final BooleanSettings s03 = new BooleanSettings("S03PacketTimeUpdate", false);
   private final BooleanSettings s04 = new BooleanSettings("S04PacketEntityEquipment", false);
   private final BooleanSettings s05 = new BooleanSettings("S05PacketSpawnPosition", false);
   private final BooleanSettings s06 = new BooleanSettings("S06PacketUpdateHealth", false);
   private final BooleanSettings s07 = new BooleanSettings("S07PacketRespawn", false);
   private final BooleanSettings s08 = new BooleanSettings("S08PacketPlayerPosLook", false);
   private final BooleanSettings s09 = new BooleanSettings("S09PacketHeldItemChange", false);
   private final BooleanSettings s0a = new BooleanSettings("S0APacketUseBed", false);
   private final BooleanSettings s0b = new BooleanSettings("S0BPacketAnimation", false);
   private final BooleanSettings s0c = new BooleanSettings("S0CPacketSpawnPlayer", false);
   private final BooleanSettings s0d = new BooleanSettings("S0DPacketCollectItem", false);
   private final BooleanSettings s0e = new BooleanSettings("S0EPacketSpawnObject", false);
   private final BooleanSettings s0f = new BooleanSettings("S0FPacketSpawnMob", false);
   private final BooleanSettings s10 = new BooleanSettings("S10PacketSpawnPainting", false);
   private final BooleanSettings s11 = new BooleanSettings("S11PacketSpawnExperienceOrb", false);
   private final BooleanSettings s12 = new BooleanSettings("S12PacketEntityVelocity", false);
   private final BooleanSettings s13 = new BooleanSettings("S13PacketDestroyEntities", false);
   private final BooleanSettings s14 = new BooleanSettings("S14PacketEntity", false);
   private final BooleanSettings s18 = new BooleanSettings("S18PacketEntityTeleport", false);
   private final BooleanSettings s19 = new BooleanSettings("S19PacketEntityHeadLook", false);
   private final BooleanSettings s1b = new BooleanSettings("S1BPacketEntityAttach", false);
   private final BooleanSettings s1c = new BooleanSettings("S1CPacketEntityMetadata", false);
   private final BooleanSettings s1d = new BooleanSettings("S1DPacketEntityEffect", false);
   private final BooleanSettings s1e = new BooleanSettings("S1EPacketRemoveEntityEffect", false);
   private final BooleanSettings s1f = new BooleanSettings("S1FPacketSetExperience", false);
   private final BooleanSettings s20 = new BooleanSettings("S20PacketEntityProperties", false);
   private final BooleanSettings s21 = new BooleanSettings("S21PacketChunkData", false);
   private final BooleanSettings s22 = new BooleanSettings("S22PacketMultiBlockChange", false);
   private final BooleanSettings s23 = new BooleanSettings("S23PacketBlockChange", false);
   private final BooleanSettings s24 = new BooleanSettings("S24PacketBlockAction", false);
   private final BooleanSettings s25 = new BooleanSettings("S25PacketBlockBreakAnim", false);
   private final BooleanSettings s26 = new BooleanSettings("S26PacketMapChunkBulk", false);
   private final BooleanSettings s27 = new BooleanSettings("S27PacketExplosion", false);
   private final BooleanSettings s28 = new BooleanSettings("S28PacketEffect", false);
   private final BooleanSettings s29 = new BooleanSettings("S29PacketSoundEffect", false);
   private final BooleanSettings s2a = new BooleanSettings("S2APacketParticles", false);
   private final BooleanSettings s2b = new BooleanSettings("S2BPacketChangeGameState", false);
   private final BooleanSettings s2c = new BooleanSettings("S2CPacketSpawnGlobalEntity", false);
   private final BooleanSettings s2d = new BooleanSettings("S2DPacketOpenWindow", false);
   private final BooleanSettings s2e = new BooleanSettings("S2EPacketCloseWindow", false);
   private final BooleanSettings s2f = new BooleanSettings("S2FPacketSetSlot", false);
   private final BooleanSettings s30 = new BooleanSettings("S30PacketWindowItems", false);
   private final BooleanSettings s31 = new BooleanSettings("S31PacketWindowProperty", false);
   private final BooleanSettings s32 = new BooleanSettings("S32PacketConfirmTransaction", false);
   private final BooleanSettings s33 = new BooleanSettings("S33PacketUpdateSign", false);
   private final BooleanSettings s34 = new BooleanSettings("S34PacketMaps", false);
   private final BooleanSettings s35 = new BooleanSettings("S35PacketUpdateTileEntity", false);
   private final BooleanSettings s36 = new BooleanSettings("S36PacketSignEditorOpen", false);
   private final BooleanSettings s37 = new BooleanSettings("S37PacketStatistics", false);
   private final BooleanSettings s38 = new BooleanSettings("S38PacketPlayerListItem", false);
   private final BooleanSettings s39 = new BooleanSettings("S39PacketPlayerAbilities", false);
   private final BooleanSettings s3a = new BooleanSettings("S3APacketTabComplete", false);
   private final BooleanSettings s3b = new BooleanSettings("S3BPacketScoreboardObjective", false);
   private final BooleanSettings s3c = new BooleanSettings("S3CPacketUpdateScore", false);
   private final BooleanSettings s3d = new BooleanSettings("S3DPacketDisplayScoreboard", false);
   private final BooleanSettings s3e = new BooleanSettings("S3EPacketTeams", false);
   private final BooleanSettings s3f = new BooleanSettings("S3FPacketCustomPayload", false);
   private final BooleanSettings s40 = new BooleanSettings("S40PacketDisconnect", false);
   private final BooleanSettings s41 = new BooleanSettings("S41PacketServerDifficulty", false);
   private final BooleanSettings s42 = new BooleanSettings("S42PacketCombatEvent", false);
   private final BooleanSettings s43 = new BooleanSettings("S43PacketCamera", false);
   private final BooleanSettings s44 = new BooleanSettings("S44PacketWorldBorder", false);
   private final BooleanSettings s45 = new BooleanSettings("S45PacketTitle", false);

   public CustomDisablerModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.c00,
            this.c01,
            this.c02,
            this.c03,
            this.c07,
            this.c08,
            this.c09,
            this.c0a,
            this.c0b,
            this.c0c,
            this.c0d,
            this.c0e,
            this.c0f,
            this.c10,
            this.c11,
            this.c12,
            this.c13,
            this.c14,
            this.c15,
            this.c16,
            this.c17,
            this.c18,
            this.c19,
            this.s00,
            this.s01,
            this.s02,
            this.s03,
            this.s04,
            this.s05,
            this.s06,
            this.s07,
            this.s08,
            this.s09,
            this.s0a,
            this.s0b,
            this.s0c,
            this.s0d,
            this.s0e,
            this.s0f,
            this.s10,
            this.s11,
            this.s12,
            this.s13,
            this.s14,
            this.s18,
            this.s19,
            this.s1b,
            this.s1c,
            this.s1d,
            this.s1e,
            this.s1f,
            this.s20,
            this.s21,
            this.s22,
            this.s23,
            this.s24,
            this.s25,
            this.s26,
            this.s27,
            this.s28,
            this.s29,
            this.s2a,
            this.s2b,
            this.s2c,
            this.s2d,
            this.s2e,
            this.s2f,
            this.s30,
            this.s31,
            this.s32,
            this.s33,
            this.s34,
            this.s35,
            this.s36,
            this.s37,
            this.s38,
            this.s39,
            this.s3a,
            this.s3b,
            this.s3c,
            this.s3d,
            this.s3e,
            this.s3f,
            this.s40,
            this.s41,
            this.s42,
            this.s43,
            this.s44,
            this.s45
         }
      );
   }

   @EventListener
   public void onSendPacket(EventSendPacket event) {
      Packet<?> packet = event.getPacket();
      if (packet instanceof C00PacketKeepAlive && this.c00.isEnabled()
         || packet instanceof C01PacketChatMessage && this.c01.isEnabled()
         || packet instanceof C02PacketUseEntity && this.c02.isEnabled()
         || packet instanceof C03PacketPlayer && this.c03.isEnabled()
         || packet instanceof C07PacketPlayerDigging && this.c07.isEnabled()
         || packet instanceof C08PacketPlayerBlockPlacement && this.c08.isEnabled()
         || packet instanceof C09PacketHeldItemChange && this.c09.isEnabled()
         || packet instanceof C0APacketAnimation && this.c0a.isEnabled()
         || packet instanceof C0BPacketEntityAction && this.c0b.isEnabled()
         || packet instanceof C0CPacketInput && this.c0c.isEnabled()
         || packet instanceof C0DPacketCloseWindow && this.c0d.isEnabled()
         || packet instanceof C0EPacketClickWindow && this.c0e.isEnabled()
         || packet instanceof C0FPacketConfirmTransaction && this.c0f.isEnabled()
         || packet instanceof C10PacketCreativeInventoryAction && this.c10.isEnabled()
         || packet instanceof C11PacketEnchantItem && this.c11.isEnabled()
         || packet instanceof C12PacketUpdateSign && this.c12.isEnabled()
         || packet instanceof C13PacketPlayerAbilities && this.c13.isEnabled()
         || packet instanceof C14PacketTabComplete && this.c14.isEnabled()
         || packet instanceof C15PacketClientSettings && this.c15.isEnabled()
         || packet instanceof C16PacketClientStatus && this.c16.isEnabled()
         || packet instanceof C17PacketCustomPayload && this.c17.isEnabled()
         || packet instanceof C18PacketSpectate && this.c18.isEnabled()
         || packet instanceof C19PacketResourcePackStatus && this.c19.isEnabled()) {
         event.setCancelled(true);
      }
   }

   @EventListener
   public void onReceivePacket(EventReceivePacket event) {
      Packet<?> packet = event.getPacket();
      if (packet instanceof S00PacketKeepAlive && this.s00.isEnabled()
         || packet instanceof S01PacketJoinGame && this.s01.isEnabled()
         || packet instanceof S02PacketChat && this.s02.isEnabled()
         || packet instanceof S03PacketTimeUpdate && this.s03.isEnabled()
         || packet instanceof S04PacketEntityEquipment && this.s04.isEnabled()
         || packet instanceof S05PacketSpawnPosition && this.s05.isEnabled()
         || packet instanceof S06PacketUpdateHealth && this.s06.isEnabled()
         || packet instanceof S07PacketRespawn && this.s07.isEnabled()
         || packet instanceof S08PacketPlayerPosLook && this.s08.isEnabled()
         || packet instanceof S09PacketHeldItemChange && this.s09.isEnabled()
         || packet instanceof S0APacketUseBed && this.s0a.isEnabled()
         || packet instanceof S0BPacketAnimation && this.s0b.isEnabled()
         || packet instanceof S0CPacketSpawnPlayer && this.s0c.isEnabled()
         || packet instanceof S0DPacketCollectItem && this.s0d.isEnabled()
         || packet instanceof S0EPacketSpawnObject && this.s0e.isEnabled()
         || packet instanceof S0FPacketSpawnMob && this.s0f.isEnabled()
         || packet instanceof S10PacketSpawnPainting && this.s10.isEnabled()
         || packet instanceof S11PacketSpawnExperienceOrb && this.s11.isEnabled()
         || packet instanceof S12PacketEntityVelocity && this.s12.isEnabled()
         || packet instanceof S13PacketDestroyEntities && this.s13.isEnabled()
         || packet instanceof S14PacketEntity && this.s14.isEnabled()
         || packet instanceof S18PacketEntityTeleport && this.s18.isEnabled()
         || packet instanceof S19PacketEntityHeadLook && this.s19.isEnabled()
         || packet instanceof S1BPacketEntityAttach && this.s1b.isEnabled()
         || packet instanceof S1CPacketEntityMetadata && this.s1c.isEnabled()
         || packet instanceof S1DPacketEntityEffect && this.s1d.isEnabled()
         || packet instanceof S1EPacketRemoveEntityEffect && this.s1e.isEnabled()
         || packet instanceof S1FPacketSetExperience && this.s1f.isEnabled()
         || packet instanceof S20PacketEntityProperties && this.s20.isEnabled()
         || packet instanceof S21PacketChunkData && this.s21.isEnabled()
         || packet instanceof S22PacketMultiBlockChange && this.s22.isEnabled()
         || packet instanceof S23PacketBlockChange && this.s23.isEnabled()
         || packet instanceof S24PacketBlockAction && this.s24.isEnabled()
         || packet instanceof S25PacketBlockBreakAnim && this.s25.isEnabled()
         || packet instanceof S26PacketMapChunkBulk && this.s26.isEnabled()
         || packet instanceof S27PacketExplosion && this.s27.isEnabled()
         || packet instanceof S28PacketEffect && this.s28.isEnabled()
         || packet instanceof S29PacketSoundEffect && this.s29.isEnabled()
         || packet instanceof S2APacketParticles && this.s2a.isEnabled()
         || packet instanceof S2BPacketChangeGameState && this.s2b.isEnabled()
         || packet instanceof S2CPacketSpawnGlobalEntity && this.s2c.isEnabled()
         || packet instanceof S2DPacketOpenWindow && this.s2d.isEnabled()
         || packet instanceof S2EPacketCloseWindow && this.s2e.isEnabled()
         || packet instanceof S2FPacketSetSlot && this.s2f.isEnabled()
         || packet instanceof S30PacketWindowItems && this.s30.isEnabled()
         || packet instanceof S31PacketWindowProperty && this.s31.isEnabled()
         || packet instanceof S32PacketConfirmTransaction && this.s32.isEnabled()
         || packet instanceof S33PacketUpdateSign && this.s33.isEnabled()
         || packet instanceof S34PacketMaps && this.s34.isEnabled()
         || packet instanceof S35PacketUpdateTileEntity && this.s35.isEnabled()
         || packet instanceof S36PacketSignEditorOpen && this.s36.isEnabled()
         || packet instanceof S37PacketStatistics && this.s37.isEnabled()
         || packet instanceof S38PacketPlayerListItem && this.s38.isEnabled()
         || packet instanceof S39PacketPlayerAbilities && this.s39.isEnabled()
         || packet instanceof S3APacketTabComplete && this.s3a.isEnabled()
         || packet instanceof S3BPacketScoreboardObjective && this.s3b.isEnabled()
         || packet instanceof S3CPacketUpdateScore && this.s3c.isEnabled()
         || packet instanceof S3DPacketDisplayScoreboard && this.s3d.isEnabled()
         || packet instanceof S3EPacketTeams && this.s3e.isEnabled()
         || packet instanceof S3FPacketCustomPayload && this.s3f.isEnabled()
         || packet instanceof S40PacketDisconnect && this.s40.isEnabled()
         || packet instanceof S41PacketServerDifficulty && this.s41.isEnabled()
         || packet instanceof S42PacketCombatEvent && this.s42.isEnabled()
         || packet instanceof S43PacketCamera && this.s43.isEnabled()
         || packet instanceof S44PacketWorldBorder && this.s44.isEnabled()
         || packet instanceof S45PacketTitle && this.s45.isEnabled()) {
         event.setCancelled(true);
      }
   }
}

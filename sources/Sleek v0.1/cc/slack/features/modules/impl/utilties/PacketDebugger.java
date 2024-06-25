package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.utils.other.PrintUtil;
import io.github.nevalackin.radbus.Listen;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
import net.minecraft.network.play.server.S19PacketEntityStatus;
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
import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;

@ModuleInfo(
   name = "PacketDebugger",
   category = Category.UTILITIES
)
public class PacketDebugger extends Module {
   private final BooleanValue printTime = new BooleanValue("Print Time", true);
   private final BooleanValue printFields = new BooleanValue("Print Fields", true);
   private final ArrayList<BooleanValue> settingsList = new ArrayList();
   private final Class[] packetsList = new Class[]{C00PacketKeepAlive.class, C01PacketChatMessage.class, C02PacketUseEntity.class, C03PacketPlayer.class, C03PacketPlayer.C04PacketPlayerPosition.class, C03PacketPlayer.C05PacketPlayerLook.class, C03PacketPlayer.C06PacketPlayerPosLook.class, C07PacketPlayerDigging.class, C08PacketPlayerBlockPlacement.class, C09PacketHeldItemChange.class, C0APacketAnimation.class, C0BPacketEntityAction.class, C0CPacketInput.class, C0DPacketCloseWindow.class, C0EPacketClickWindow.class, C0FPacketConfirmTransaction.class, C10PacketCreativeInventoryAction.class, C11PacketEnchantItem.class, C12PacketUpdateSign.class, C13PacketPlayerAbilities.class, C14PacketTabComplete.class, C15PacketClientSettings.class, C16PacketClientStatus.class, C17PacketCustomPayload.class, C18PacketSpectate.class, C19PacketResourcePackStatus.class, S00PacketKeepAlive.class, S01PacketJoinGame.class, S02PacketChat.class, S03PacketTimeUpdate.class, S04PacketEntityEquipment.class, S05PacketSpawnPosition.class, S06PacketUpdateHealth.class, S07PacketRespawn.class, S08PacketPlayerPosLook.class, S09PacketHeldItemChange.class, S0APacketUseBed.class, S0BPacketAnimation.class, S0CPacketSpawnPlayer.class, S0DPacketCollectItem.class, S0EPacketSpawnObject.class, S0FPacketSpawnMob.class, S10PacketSpawnPainting.class, S11PacketSpawnExperienceOrb.class, S12PacketEntityVelocity.class, S13PacketDestroyEntities.class, S14PacketEntity.class, S14PacketEntity.S15PacketEntityRelMove.class, S14PacketEntity.S16PacketEntityLook.class, S14PacketEntity.S17PacketEntityLookMove.class, S18PacketEntityTeleport.class, S19PacketEntityHeadLook.class, S19PacketEntityStatus.class, S1BPacketEntityAttach.class, S1CPacketEntityMetadata.class, S1DPacketEntityEffect.class, S1EPacketRemoveEntityEffect.class, S1FPacketSetExperience.class, S20PacketEntityProperties.class, S21PacketChunkData.class, S22PacketMultiBlockChange.class, S23PacketBlockChange.class, S24PacketBlockAction.class, S25PacketBlockBreakAnim.class, S26PacketMapChunkBulk.class, S27PacketExplosion.class, S28PacketEffect.class, S29PacketSoundEffect.class, S2APacketParticles.class, S2BPacketChangeGameState.class, S2CPacketSpawnGlobalEntity.class, S2DPacketOpenWindow.class, S2EPacketCloseWindow.class, S2FPacketSetSlot.class, S30PacketWindowItems.class, S31PacketWindowProperty.class, S32PacketConfirmTransaction.class, S33PacketUpdateSign.class, S34PacketMaps.class, S35PacketUpdateTileEntity.class, S36PacketSignEditorOpen.class, S37PacketStatistics.class, S38PacketPlayerListItem.class, S39PacketPlayerAbilities.class, S3APacketTabComplete.class, S3BPacketScoreboardObjective.class, S3CPacketUpdateScore.class, S3DPacketDisplayScoreboard.class, S3EPacketTeams.class, S3FPacketCustomPayload.class, S40PacketDisconnect.class, S41PacketServerDifficulty.class, S42PacketCombatEvent.class, S43PacketCamera.class, S44PacketWorldBorder.class, S45PacketTitle.class, S46PacketSetCompressionLevel.class, S47PacketPlayerListHeaderFooter.class, S48PacketResourcePackSend.class, S49PacketUpdateEntityNBT.class};

   public PacketDebugger() {
      this.addSettings(new Value[]{this.printTime, this.printFields});
      Class[] var1 = this.packetsList;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class c = var1[var3];
         this.settingsList.add(new BooleanValue(c.getName().replace("net.minecraft.network.play.server.", "").replace("net.minecraft.network.play.client.", ""), false));
         this.addSettings(new Value[]{(Value)this.settingsList.get(this.settingsList.size() - 1)});
      }

   }

   @Listen
   public void onPacket(PacketEvent event) {
      Packet packet = event.getPacket();

      for(int i = 0; i < this.settingsList.size(); ++i) {
         BooleanValue setting = (BooleanValue)this.settingsList.get(i);
         if (packet.getClass().getName() == setting.getName() && (Boolean)setting.getValue()) {
            Field[] var5 = this.packetsList[i].getDeclaredFields();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Field field = var5[var7];

               try {
                  if (field.isAccessible()) {
                     PrintUtil.message(field.getName() + ": " + field.get(packet));
                  }
               } catch (Exception var10) {
                  PrintUtil.message(field.getName() + "Erm, what the sigma");
               }
            }
         }
      }

   }
}

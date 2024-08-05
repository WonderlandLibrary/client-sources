package fr.dog.module.impl.combat;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.event.impl.player.move.MoveInputEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.util.player.RotationUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.util.Comparator;
import java.util.List;

public class SumoBot extends Module {
    public EntityPlayer target = null;
    public boolean hasGameStarted = false;
    public SumoBot() {
        super("SumoBot", ModuleCategory.COMBAT);
    }
    @SubscribeEvent
    public void onMovementInput(MoveInputEvent event){
        if(hasGameStarted){
            if(target != null){
                if(target.getDistanceToEntity(mc.thePlayer) > 2){
                    event.setForward(1f);
                }
            }
        }
    }
    @SubscribeEvent
    public void onEventUpdate(PlayerTickEvent event){
        if(hasGameStarted){
            if(mc.thePlayer.ticksExisted % 2 == 0 && target.getDistanceToEntity(target) < 4){
                mc.clickMouse();
            }
            if(mc.thePlayer.hurtTime == 8){
                mc.thePlayer.motionX *= 0.6f;
                mc.thePlayer.motionZ *= 0.6f;
            }
        }
    }
    @SubscribeEvent
    public void onEventPre(PlayerNetworkTickEvent event){
        if(mc.thePlayer.ticksExisted < 5){
            hasGameStarted = false;
        }
        if(!hasGameStarted){
            return;
        }
        List<EntityPlayer>possibilities = mc.theWorld.playerEntities
                .stream()
                .filter(entity -> {
                    if (entity.isDead || entity.deathTime != 0)
                        return false;
                    return mc.thePlayer != entity;
                })
                .filter(entity -> mc.thePlayer.getDistanceToEntity(entity) <= 16)
                .sorted(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity)))
                .toList();
        target = possibilities.stream()
                .findFirst()
                .orElse(null);
        if(target != null){
            float[] rota = RotationUtil.getRotation(target);
            mc.thePlayer.rotationYaw = MathHelper.wrapAngleTo180_float(rota[0]);
            mc.thePlayer.rotationPitch = rota[1];
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketReceiveEvent event){
        if(event.getPacket() instanceof S02PacketChat c01PacketChatMessage){
            if(EnumChatFormatting.getTextWithoutFormattingCodes(c01PacketChatMessage.getChatComponent().getUnformattedText()).contains("Eliminate your")){
                hasGameStarted = true;
            }
            if(c01PacketChatMessage.getChatComponent().getUnformattedText().toLowerCase().contains("melee accuracy")){
                mc.thePlayer.sendChatMessage("/play duels_sumo_duel");
                hasGameStarted = false;
            }
        }
    }
}

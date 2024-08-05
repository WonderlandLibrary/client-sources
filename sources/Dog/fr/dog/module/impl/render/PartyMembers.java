package fr.dog.module.impl.render;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.dog.Dog;
import fr.dog.component.impl.player.HypixelComponent;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.util.packet.RequestUtil;
import fr.dog.util.player.ChatUtil;
import fr.dog.util.render.RenderUtil;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundPartyInfoPacket;
import net.hypixel.modapi.packet.impl.serverbound.ServerboundPartyInfoPacket;

import java.awt.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PartyMembers extends Module {
    public PartyMembers() {
        super("PartyMembers", ModuleCategory.RENDER);
        this.setDraggable(true);

        this.setWidth(200);
        this.setHeight(15);
        this.setX(200);
        this.setY(20);
    }

    @SubscribeEvent
    private void onPlayerTickEvent(PlayerTickEvent event){
        if(mc.thePlayer.ticksExisted % 10 == 0){
            try{
                HypixelModAPI.getInstance().sendPacket(new ServerboundPartyInfoPacket());
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    @SubscribeEvent
    private void onRender2DEvent(Render2DEvent event){
        if(HypixelComponent.partyHashMap == null || HypixelComponent.partyHashMap.isEmpty()){
            this.setHeight(15);
        }else{
            this.setHeight((HypixelComponent.partyHashMap.size() * 12) + 15);
        }



        RenderUtil.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), new Color(40, 40, 40, 60));
        RenderUtil.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), Dog.getInstance().getThemeManager().getCurrentTheme().color1);
        mc.fontRendererObj.drawString(this.getCustomName(), this.getX() + 3, this.getY()+ 3 , Color.WHITE.getRGB());

        int yf = this.getX() + 15;

        for(ClientboundPartyInfoPacket.PartyMember member : HypixelComponent.partyHashMap.values()){

            String username = "";

            if(HypixelComponent.namecache.containsKey(member.getUuid())){
                username = HypixelComponent.namecache.get(member.getUuid());
            }else{




                CompletableFuture.runAsync(grahGrah(member.getUuid().toString()))
                        .exceptionally(ex -> {
                            System.out.println("Task failed: " + ex.getMessage());
                            return null;
                        });
            }

            mc.fontRendererObj.drawString(member.getRole().name(), this.getX(), yf + 2, Color.WHITE.getRGB());
            mc.fontRendererObj.drawString(username, this.getX() + 100, yf + 2, Color.WHITE.getRGB());

            yf += 12;
        }

    }
    private Runnable grahGrah(final String uuid){

        Runnable r = new Runnable(){
            public void run(){
                String ret = RequestUtil.requestResultAll.apply("hhttps://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                JsonObject object = JsonParser.parseString(ret).getAsJsonObject();
                HypixelComponent.namecache.put(UUID.fromString(uuid), object.get("name").getAsString());
            }
        };

        return r;

    }
}

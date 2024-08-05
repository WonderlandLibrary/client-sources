package fr.dog.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.dog.component.impl.player.HypixelComponent;
import fr.dog.util.math.TimeUtil;
import fr.dog.util.packet.RequestUtil;
import lombok.Getter;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Getter
public class PlayerStats {
    private UUID playerUUID;
    private float fkdr, kdr, wlr, index;
    private int fk, fd, k, d, w, l, stars;
    private String tags = "N";
    private String username;
    private boolean loaded = false;
    private boolean invalid = false;
    public PlayerStats(String username, UUID playerUUID){
        this.playerUUID = playerUUID;
        this.username = username;
    }
    public void reloadStats(){
        if(invalid){
            return;
        }
        this.tags = "";
        Runnable tsk = ()->{
            String ret = RequestUtil.requestResultAll.apply("https://api.hypixel.net/player?key=" + HypixelComponent.HYPIXEL_APIKEY + "&uuid=" + this.playerUUID.toString());

            JsonObject object = JsonParser.parseString(ret).getAsJsonObject();

            try {
                JsonObject objectPlayer = object.getAsJsonObject("player");
                JsonObject objectStats = objectPlayer.getAsJsonObject("stats");
                JsonObject objectBWStats = objectStats.getAsJsonObject("Bedwars");
                JsonObject objectAchievements = objectPlayer.getAsJsonObject("achievements");
                fd = objectBWStats.get("final_deaths_bedwars").getAsInt();
                d = objectBWStats.get("deaths_bedwars").getAsInt();
                l = objectBWStats.get("losses_bedwars").getAsInt();

                fk = objectBWStats.get("final_kills_bedwars").getAsInt();
                k = objectBWStats.get("kills_bedwars").getAsInt();
                w = objectBWStats.get("wins_bedwars").getAsInt();

                stars = objectAchievements.get("bedwars_level").getAsInt();
                username = objectPlayer.get("displayname").getAsString();

                if(fd != 0){
                    fkdr = (float) fk / fd;
                }else{
                    fkdr = fk;
                }

                if(d != 0){
                    kdr = (float) k / d;
                }else{
                    kdr = k;
                }

                if(l != 0){
                    wlr = (float) w / l;
                }else{
                    wlr = w;
                }


                index = fkdr * fkdr * stars;




                if(fkdr > 10 && stars < 8){
                    this.tags += EnumChatFormatting.DARK_GREEN + "A"; // uhh certified alt
                }
                if(fkdr > 200){
                    this.tags += EnumChatFormatting.DARK_RED + "S"; // Worth to snipe
                }
                if(stars > 600 && fkdr < 1){
                    this.tags += EnumChatFormatting.LIGHT_PURPLE + "N"; // Nosteria found ;(
                }
                if(fkdr > 0.8 && fkdr < 2 && wlr < 0.1){
                    this.tags += EnumChatFormatting.RED + "P"; // Probable Sniper, normal fkdr but low ahh wlr
                }
                if(kdr > 1.75){
                    this.tags += EnumChatFormatting.RED + "K"; // high kdr, sus
                }



                loaded = true;


            } catch (Exception e) {
                e.printStackTrace();


                this.tags = EnumChatFormatting.LIGHT_PURPLE + "M"; // nicked, or api down ;(

                invalid = true;
            }
            if(HypixelComponent.known_cheaters_or_alts.contains(this.playerUUID)){
                this.tags += EnumChatFormatting.GOLD + "C"; // Known Cheater, alt, or just a target. worth to stay
            }
        };

        CompletableFuture.runAsync(tsk)
                .exceptionally(ex -> {
                    System.out.println("Task failed: " + ex.getMessage());
                    return null;
                });

    }




}

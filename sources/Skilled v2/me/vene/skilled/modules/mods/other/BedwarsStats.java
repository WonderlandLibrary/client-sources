 package me.vene.skilled.modules.mods.other;
 
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;
 import java.io.IOException;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.HashMap;
 import me.vene.skilled.gui.GUI;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraftforge.client.event.ClientChatReceivedEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 import org.apache.commons.io.IOUtils;

public class BedwarsStats extends Module
{
    public static String unformattedMessage;
    private Minecraft mc;
    private ArrayList<String> playerArray;
    public static String apiKey;
    private long nextDelay;
    protected long lastMS;
    HashMap<String, String> localStorage;
    private String quit;
    private String joined;
    
    public BedwarsStats() throws IOException {
        super(StringRegistry.register("Bedwars Stats"), 0, Category.O);
        this.mc = Minecraft.getMinecraft();
        this.playerArray = new ArrayList<String>();
        this.localStorage = new HashMap<String, String>();
        this.quit = new String(new char[] { ' ', 'h', 'a', 's', ' ', 'q', 'u', 'i', 't', '!' });
        this.joined = new String(new char[] { ' ', 'h', 'a', 's', ' ', 'j', 'o', 'i', 'n', 'e', 'd' });
    }
    
    @Override
    public void onEnable() {
        this.updatebefore();
    }
    
    public static double roundAvoid(final double value, final int places) {
        final double scale = Math.pow(10.0, places);
        return Math.round(value * scale) / scale;
    }
    
    private boolean hasTimePassedMS(final long MS) {
        return this.getCurrentMS() >= this.lastMS + MS;
    }
    
    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    private void updatebefore() {
        this.lastMS = this.getCurrentMS();
    }
    
    @SubscribeEvent
    public void onChatMessageRecieved(final ClientChatReceivedEvent event) {
        if (this.mc.thePlayer != null || this.mc.thePlayer != null) {
            final String message = event.message.getUnformattedText();
            if (message.contains("Your new API key is ")) {
                BedwarsStats.apiKey = message.split("Your new API key is ")[1];
            }
        }
    }
    
    @SubscribeEvent
    public void onRender(final TickEvent.RenderTickEvent e) throws IOException {
        if (this.hasTimePassedMS(1000L)) {
            this.updatebefore();
            this.playerArray.clear();
            for (final Object object : this.mc.theWorld.loadedEntityList) {
                if (!(object instanceof EntityPlayer)) {
                    continue;
                }
                final EntityLivingBase entity = (EntityLivingBase)object;
                final String player = entity.getDisplayName().getUnformattedText();
                if (this.localStorage.get(player) != null) {
                    this.playerArray.add(this.localStorage.get(player));
                }
                else {
                    JsonObject obj = new JsonObject();
                    final JsonParser parser = new JsonParser();
                    String uuid = IOUtils.toString(new URL("https://api.mojang.com/users/profiles/minecraft/" + player));
                    obj = (JsonObject)parser.parse(uuid);
                    uuid = obj.get("id").getAsString();
                    final String response = IOUtils.toString(new URL("https://api.hypixel.net/player?key=" + BedwarsStats.apiKey + "&uuid=" + uuid));
                    obj = (JsonObject)parser.parse(response);
                    if (obj.get("player") == null) {
                        continue;
                    }
                    final JsonObject playerJson = obj.get("player").getAsJsonObject();
                    final JsonObject stats = playerJson.get("stats").getAsJsonObject();
                    final JsonObject bedwars = stats.get("Bedwars").getAsJsonObject();
                    double fkdr = 0.0;
                    int finalDeaths = 1;
                    int finalKills = 0;
                    finalDeaths = bedwars.get("final_deaths_bedwars").getAsInt();
                    finalKills = bedwars.get("final_kills_bedwars").getAsInt();
                    fkdr = finalKills / (double)finalDeaths;
                    final JsonObject achievements = playerJson.get("achievements").getAsJsonObject();
                    int star = 0;
                    if (achievements.get("bedwars_level") != null) {
                        star = achievements.get("bedwars_level").getAsInt();
                    }
                    this.playerArray.add("[" + star + "] " + player + " FKDR: " + roundAvoid(fkdr, 2));
                    this.localStorage.put(player, "[" + star + "] " + player + " FKDR: " + roundAvoid(fkdr, 2));
                }
            }
        }
        for (int i = 0; i < this.playerArray.size(); ++i) {
            Minecraft.getMinecraft().fontRendererObj.drawString(this.playerArray.get(i).toString(), GUI.arrayXPos + 2, GUI.arrayYPos + 9 * i, -1);
        }
    }
    
    static {
        BedwarsStats.apiKey = "dd5cdbea-0e37-4b23-bccd-d66e8df643c0";
    }
}

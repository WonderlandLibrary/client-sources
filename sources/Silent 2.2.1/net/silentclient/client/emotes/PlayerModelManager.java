package net.silentclient.client.emotes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.emotes.animation.Animation;
import net.silentclient.client.emotes.animation.AnimationMeshConfig;
import net.silentclient.client.emotes.animation.json.AnimationMeshConfigAdapter;
import net.silentclient.client.emotes.animation.json.AnimatorConfigAdapter;
import net.silentclient.client.emotes.animation.json.AnimatorHeldItemConfigAdapter;
import net.silentclient.client.emotes.animation.model.AnimatorConfig;
import net.silentclient.client.emotes.animation.model.AnimatorHeldItemConfig;
import net.silentclient.client.emotes.bobj.BOBJLoader;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.emoticons.Icon;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.christmas.IcebergEmote;
import net.silentclient.client.emotes.emoticons.christmas.PresentEmote;
import net.silentclient.client.emotes.emoticons.emoticons.CryingEmote;
import net.silentclient.client.emotes.emoticons.emoticons.DisgustedEmote;
import net.silentclient.client.emotes.emoticons.emoticons.SneezeEmote;
import net.silentclient.client.emotes.emoticons.emoticons.StarPowerEmote;
import net.silentclient.client.emotes.emoticons.halloween.RisingFromDeadEmote;
import net.silentclient.client.emotes.emoticons.halloween.TrickOrTreatEmote;
import net.silentclient.client.emotes.emoticons.newyear.ChampagneEmote;
import net.silentclient.client.emotes.emoticons.thanksgiving.PumpkinEmote;
import net.silentclient.client.emotes.emoticons.valentines.BlowKissEmote;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;

import java.util.*;

public class PlayerModelManager {
    public static Map<String, EmoteData> emoteData = new HashMap<>();
    public Animation steve;
    public Animation alex;
    public AnimatorConfig steveConfig;
    public AnimatorConfig alexConfig;
    public List<String> emotes = new ArrayList<>();
    public Map<String, Emote> registry = new HashMap<>();
    public Map<Integer, String> map = new TreeMap<>();
    public List<String> emotesName = new ArrayList<>();

    public static PlayerModelManager instance;

    public static PlayerModelManager get() {
        if(instance == null) {
            instance = new PlayerModelManager();
            return instance;
        }
        return instance;
    }

    public PlayerModelManager() {
        try {
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.registerTypeAdapter(AnimationMeshConfig.class, new AnimationMeshConfigAdapter());
            gsonbuilder.registerTypeAdapter(AnimatorConfig.class, new AnimatorConfigAdapter());
            gsonbuilder.registerTypeAdapter(AnimatorHeldItemConfig.class, new AnimatorHeldItemConfigAdapter());
            Gson gson = gsonbuilder.create();
            Class<?> oclass = this.getClass();
            BOBJLoader.BOBJData bobjloader$bobjdata1 = BOBJLoader.readData(oclass.getResourceAsStream("/assets/minecraft/silentclient/emotes/models/armor.bobj"));
            BOBJLoader.BOBJData bobjloader$bobjdata2 = BOBJLoader.readData(oclass.getResourceAsStream("/assets/minecraft/silentclient/emotes/models/default.bobj"));
            BOBJLoader.BOBJData bobjloader$bobjdata3 = BOBJLoader.readData(oclass.getResourceAsStream("/assets/minecraft/silentclient/emotes/models/slim.bobj"));
            BOBJLoader.BOBJData bobjloader$bobjdata4 = BOBJLoader.readData(oclass.getResourceAsStream("/assets/minecraft/silentclient/emotes/models/actions.bobj"));
            BOBJLoader.BOBJData bobjloader$bobjdata5 = BOBJLoader.readData(oclass.getResourceAsStream("/assets/minecraft/silentclient/emotes/models/props.bobj"));
            BOBJLoader.merge(bobjloader$bobjdata2, bobjloader$bobjdata1);
            BOBJLoader.merge(bobjloader$bobjdata3, bobjloader$bobjdata1);
            BOBJLoader.merge(bobjloader$bobjdata2, bobjloader$bobjdata5);
            BOBJLoader.merge(bobjloader$bobjdata3, bobjloader$bobjdata5);
            bobjloader$bobjdata2.actions.putAll(bobjloader$bobjdata4.actions);
            bobjloader$bobjdata3.actions.putAll(bobjloader$bobjdata4.actions);
            this.steve = new Animation("default", bobjloader$bobjdata2);
            this.alex = new Animation("slim", bobjloader$bobjdata3);
            this.steve.init();
            this.alex.init();
            this.steveConfig = gson.fromJson(
                    IOUtils.toString(Objects.requireNonNull(oclass.getResourceAsStream("/assets/minecraft/silentclient/emotes/models/default.json"))), AnimatorConfig.class
            );
            this.alexConfig = gson.fromJson(
                    IOUtils.toString(Objects.requireNonNull(oclass.getResourceAsStream("/assets/minecraft/silentclient/emotes/models/slim.json"))), AnimatorConfig.class
            );
            this.steve.data.armatures.get("Armature").enabled = true;
            this.alex.data.armatures.get("Armature").enabled = true;

            for (String s1 : bobjloader$bobjdata4.actions.keySet()) {
                if (s1.startsWith("emote_") && !s1.endsWith("_IK")) {
                    String s = s1.substring(6);
                    int i = s.indexOf(":");
                    if (i != -1) {
                        s = s.substring(0, i);
                    }

                    if (!this.emotes.contains(s)) {
                        this.emotes.add(s);
                    }
                }
            }

            this.registerEmote("best_mates", "Best Mates", 999);
            this.registerEmote("boneless", "Boneless", 2);
            this.registerEmote("bow", "Bow");
            this.registerEmote("boy", "Boyyy");
            this.registerEmote("calculated", "Calculated");
            this.registerEmote("chicken", "Chicken", 4);
            this.registerEmote("clapping", "Clapping", 3);
            this.registerEmote("confused", "Confused");
            this.registerEmote(new CryingEmote("crying", "Crying").looping(3));
            this.registerEmote("dab", "Dab");
            this.registerEmote("default", "Dance moves");
            this.registerEmote("disco_fever", "Disco Fever", 2);
            this.registerEmote("electro_shuffle", "Electro Shuffle", 2);
            this.registerEmote("facepalm", "Facepalm");
            this.registerEmote("fist", "Fist");
            this.registerEmote("floss", "Floss", 3);
            this.registerEmote("free_flow", "Free Flow");
            this.registerEmote("fresh", "Fresh", 2);
            this.registerEmote("gangnam_style", "Gangnam", 4);
            this.registerEmote("get_funky", "Get Funky", 2);
            this.registerEmote("hype", "Hype", 3);
            this.registerEmote("infinite_dab", "Infinite Dab", 4);
            this.registerEmote("laughing", "Laughing", 2);
            this.registerEmote("no", "No");
            this.registerEmote("orange_justice", "Orange Justice");
            this.registerEmote("pointing", "Pointing");
            this.registerEmote("salute", "Salute");
            this.registerEmote("shimmer", "Shimmer");
            this.registerEmote("shrug", "Shrug");
            this.registerEmote("skibidi", "Skibidi", 4);
            this.registerEmote("squat_kick", "Squat Kick");
            this.registerEmote(new StarPowerEmote("star_power", "Star Power", 1));
            this.registerEmote("t_pose", "T-Pose");
            this.registerEmote("take_the_l", "Take The L", 4);
            this.registerEmote("thinking", "Thinking");
            this.registerEmote("tidy", "Tidy");
            this.registerEmote("wave", "Wave");
            this.registerEmote("yes", "Yes");
            this.registerEmote(new RisingFromDeadEmote("rising_from_dead", "Dead Rising"));
            this.registerEmote(new PumpkinEmote("pumpkin", "Pumpkin"));
            this.registerEmote(new TrickOrTreatEmote("trick_or_treat", "Trick or Treat"));
            this.registerEmote(new BlowKissEmote("blow_kiss", "Blow Kiss"));
            this.registerEmote("twerk", "Twerking", 8);
            this.registerEmote("club", "Clubbing", 4);
            this.registerEmote(new SneezeEmote("sneeze", "Sneeze"));
            this.registerEmote("punch", "Punch!");
            this.registerEmote("bongo_cat", "Bongo Cat");
            this.registerEmote("exhausted", "Exhausted");
            this.registerEmote(new DisgustedEmote("disgusted", "Disgusted"));
            this.registerEmote("bitchslap", "Slap");
            this.registerEmote("threatening", "Threatening");
            this.registerEmote(new PropEmote("woah", "Woah!"));
            this.registerEmote("breathtaking", "Breathtaking");
            this.registerEmote("bunny_hop", "Bunny Hop");
            this.registerEmote("chicken_dance", "Chicken Dance");
            this.registerEmote(new PropEmote("broom", "Broom"));
            this.registerEmote(new IcebergEmote("iceberg", "Iceberg"));
            this.registerEmote(new PresentEmote("present", "Present"));
            this.registerEmote(new ChampagneEmote("champagne", "Champagne"));
            this.map.put(1, "default");
            this.map.put(2, "best_mates");
            this.map.put(3, "boneless");
            this.map.put(4, "bow");
            this.map.put(5, "boy");
            this.map.put(6, "calculated");
            this.map.put(7, "chicken");
            this.map.put(8, "clapping");
            this.map.put(9, "confused");
            this.map.put(10, "crying");
            this.map.put(11, "dab");
            this.map.put(12, "disco_fever");
            this.map.put(13, "electro_shuffle");
            this.map.put(14, "facepalm");
            this.map.put(15, "fist");
            this.map.put(16, "floss");
            this.map.put(17, "free_flow");
            this.map.put(18, "fresh");
            this.map.put(19, "gangnam_style");
            this.map.put(20, "get_funky");
            this.map.put(21, "hype");
            this.map.put(22, "infinite_dab");
            this.map.put(23, "laughing");
            this.map.put(24, "no");
            this.map.put(25, "orange_justice");
            this.map.put(26, "pointing");
            this.map.put(27, "salute");
            this.map.put(28, "shimmer");
            this.map.put(29, "shrug");
            this.map.put(30, "skibidi");
            this.map.put(31, "squat_kick");
            this.map.put(32, "star_power");
            this.map.put(33, "t_pose");
            this.map.put(34, "take_the_l");
            this.map.put(35, "thinking");
            this.map.put(36, "tidy");
            this.map.put(37, "wave");
            this.map.put(38, "yes");
            this.map.put(39, "rising_from_dead");
            this.map.put(40, "pumpkin");
            this.map.put(41, "trick_or_treat");
            this.map.put(42, "blow_kiss");
            this.map.put(43, "twerk");
            this.map.put(44, "club");
            this.map.put(45, "sneeze");
            this.map.put(46, "punch");
            this.map.put(47, "bongo_cat");
            this.map.put(48, "exhausted");
            this.map.put(49, "disgusted");
            this.map.put(50, "bitchslap");
            this.map.put(51, "threatening");
            this.map.put(52, "woah");
            this.map.put(53, "breathtaking");
            this.map.put(54, "bunny_hop");
            this.map.put(55, "chicken_dance");
            this.map.put(56, "broom");
            this.map.put(57, "iceberg");
            this.map.put(58, "present");
            this.map.put(59, "champagne");

            for (String value : this.map.values()) {
                emoteData.put(value, new EmoteData(value));
            }
        } catch (Exception var13) {
            LogManager.getLogger().info("Error registering emotes: " + var13.getMessage());
            LogManager.getLogger().catching(var13);
            var13.printStackTrace();
        }
    }

    private void registerEmote(String s, String s1) {
        this.registerEmote(s, s1, 1);
    }

    private void registerEmote(String s, String s1, int i) {
        this.registerEmote(new Emote(s, s1).looping(i));
    }

    private void registerEmote(Emote emote) {
        if (this.emotes.contains(emote.id)) {
            this.registry.put(emote.id, emote);
            this.emotesName.add(emote.title);
            this.setEmoteIcon(emote.id);
        } else {
            LogManager.getLogger().info("No emote registered for '" + emote.id + "' !");
        }
    }

    private void setEmoteIcon(String s) {
        this.setEmoteIcon(s, 100, 100);
    }

    private void setEmoteIcon(String s, int i, int j) {
        Emote emote = this.registry.get(s);
        if (emote != null) {
            emote.icon = new Icon(new ResourceLocation("silentclient/emotes/icons/" + s + ".png"), i, j);
        }
    }

    public String getKey(String s) {
        Emote emote = this.registry.get(s);
        return emote == null ? s : emote.title;
    }

    public Emote getEmote(String s) {
        String s1 = s.contains(":") ? s.split(":")[0] : s;
        Emote emote = this.registry.get(s1);
        if (s.contains(":")) {
            emote = emote.getDynamicEmote(s.split(":")[1]);
        }

        return emote;
    }
}

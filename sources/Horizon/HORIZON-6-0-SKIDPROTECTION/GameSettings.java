package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import java.util.Collection;
import com.google.common.collect.ImmutableSet;
import java.util.Iterator;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.apache.commons.lang3.ArrayUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import java.lang.reflect.Type;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;

public class GameSettings
{
    private static final Logger Ñ¢È;
    private static final Gson Çªáˆºá;
    private static final ParameterizedType ˆÕ;
    private static final String[] ÇªÈ;
    private static final String[] ÇªÅ;
    private static final String[] ÇŽ;
    private static final String[] ÇŽÅ;
    private static final String[] ¥Ðƒá;
    private static final String[] ÐƒÇŽà;
    private static final String[] ¥Ê;
    public float HorizonCode_Horizon_È;
    public boolean Â;
    public int Ý;
    public boolean Ø­áŒŠá;
    public boolean Âµá€;
    public boolean Ó;
    public int à;
    public int Ø;
    public float áŒŠÆ;
    public int áˆºÑ¢Õ;
    public boolean ÂµÈ;
    public int á;
    public boolean ˆÏ­;
    public boolean £á;
    public boolean Å;
    public float £à;
    public int µà;
    public int ˆà;
    public float ¥Æ;
    public int Ø­à;
    public int µÕ;
    public int Æ;
    public int Šáƒ;
    public int Ï­Ðƒà;
    public boolean áŒŠà;
    public boolean ŠÄ;
    public boolean Ñ¢á;
    public boolean ŒÏ;
    public boolean Çªà¢;
    public boolean Ê;
    public int ÇŽÉ;
    public int ˆá;
    public boolean ÇŽÕ;
    public int É;
    public boolean áƒ;
    public boolean á€;
    public String Õ;
    public boolean à¢;
    public boolean ŠÂµà;
    public boolean ¥à;
    public boolean Âµà;
    public boolean Ç;
    public boolean È;
    public boolean áŠ;
    public int ˆáŠ;
    public boolean áŒŠ;
    public boolean £ÂµÄ;
    public boolean Ø­Âµ;
    public int Ä;
    public int Ñ¢Â;
    public int Ï­à;
    public boolean áˆºáˆºÈ;
    public boolean ÇŽá€;
    public boolean Ï;
    public boolean Ô;
    public boolean ÇªÓ;
    public boolean áˆºÏ;
    public boolean ˆáƒ;
    public boolean Œ;
    public boolean £Ï;
    public boolean Ø­á;
    public boolean ˆÉ;
    public boolean Ï­Ï­Ï;
    public boolean £Â;
    public boolean £Ó;
    public static final int ˆÐƒØ­à = 0;
    public static final int £Õ = 1;
    public static final int Ï­Ô = 2;
    public static final int Œà = 3;
    public static final int Ðƒá = 0;
    public static final int ˆÏ = 1;
    public static final int áˆºÇŽØ = 2;
    public static final int ÇªÂµÕ = 0;
    public static final int áŒŠÏ = 1;
    public static final int áŒŠáŠ = 2;
    public static final String ˆÓ = "Default";
    public KeyBinding ¥Ä;
    private File ÐƒÓ;
    public boolean ÇªÔ;
    public boolean Û;
    public int ŠÓ;
    public List ÇŽá;
    public EntityPlayer.HorizonCode_Horizon_È Ñ¢à;
    public boolean ÇªØ­;
    public boolean £áŒŠá;
    public boolean áˆº;
    public float Šà;
    public boolean áŒŠá€;
    public boolean ¥Ï;
    public boolean ˆà¢;
    public boolean Ñ¢Ç;
    public boolean £É;
    public boolean Ðƒáƒ;
    public boolean Ðƒà;
    public boolean ¥É;
    public boolean £ÇªÓ;
    private final Set áˆºÕ;
    public boolean ÂµÕ;
    public int Š;
    public int Ø­Ñ¢á€;
    public boolean Ñ¢Ó;
    public float Ø­Æ;
    public float áŒŠÔ;
    public float ŠÕ;
    public float £Ø­à;
    public boolean µÐƒáƒ;
    public int áŒŠÕ;
    private Map ŒÐƒà;
    public float ÂµÂ;
    public float áŒŠá;
    public float ˆØ;
    public float áˆºà;
    public float ÐƒÂ;
    public int £áƒ;
    public boolean Ï­áˆºÓ;
    public String Çª;
    public int ÇŽÄ;
    public int ˆÈ;
    public int ˆÅ;
    public KeyBinding ÇªÉ;
    public KeyBinding ŠÏ­áˆºá;
    public KeyBinding ÇŽà;
    public KeyBinding ŠáˆºÂ;
    public KeyBinding Ø­Ñ¢Ï­Ø­áˆº;
    public KeyBinding ŒÂ;
    public KeyBinding Ï­Ï;
    public KeyBinding ŠØ;
    public KeyBinding ˆÐƒØ;
    public KeyBinding Çªà;
    public KeyBinding ¥Å;
    public KeyBinding Œáƒ;
    public KeyBinding Œá;
    public KeyBinding µÂ;
    public KeyBinding Ñ¢ÇŽÏ;
    public KeyBinding ÇªÂ;
    public KeyBinding ÂµáˆºÂ;
    public KeyBinding ¥Âµá€;
    public KeyBinding ÇŽÈ;
    public KeyBinding ÇªáˆºÕ;
    public KeyBinding Ï­Ä;
    public KeyBinding ¥áŠ;
    public KeyBinding µÊ;
    public KeyBinding áˆºáˆºáŠ;
    public KeyBinding[] áŒŠÉ;
    public KeyBinding[] ÇŽØ;
    protected Minecraft ŒÓ;
    private File ÐƒáˆºÄ;
    public EnumDifficulty ÇŽÊ;
    public boolean µ;
    public int µÏ;
    public boolean µÐƒÓ;
    public boolean ¥áŒŠà;
    public String ˆÂ;
    public boolean áŒŠÈ;
    public boolean ˆØ­áˆº;
    public float £Ô;
    public float ŠÏ;
    public float ˆ;
    public int ŠÑ¢Ó;
    public int áˆºá;
    public String Ï­Ó;
    public boolean ŠáŒŠà¢;
    private static final String áˆºÉ = "CL_00000650";
    
    static {
        Ñ¢È = LogManager.getLogger();
        Çªáˆºá = new Gson();
        ˆÕ = new ParameterizedType() {
            private static final String HorizonCode_Horizon_È = "CL_00000651";
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { String.class };
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        ÇªÈ = new String[] { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
        ÇªÅ = new String[] { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
        ÇŽ = new String[] { "options.ao.off", "options.ao.min", "options.ao.max" };
        ÇŽÅ = new String[] { "options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high" };
        ¥Ðƒá = new String[] { "options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never" };
        ÐƒÇŽà = new String[] { "options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods" };
        ¥Ê = new String[] { "options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk" };
    }
    
    public GameSettings(final Minecraft mcIn, final File p_i46326_2_) {
        this.HorizonCode_Horizon_È = 0.5f;
        this.Ý = -1;
        this.Ø­áŒŠá = true;
        this.Ó = true;
        this.à = 120;
        this.Ø = 1;
        this.áŒŠÆ = 0.8f;
        this.áˆºÑ¢Õ = 0;
        this.ÂµÈ = false;
        this.á = 0;
        this.ˆÏ­ = false;
        this.£á = Config.¥Ï();
        this.Å = Config.¥Ï();
        this.£à = 1.0f;
        this.µà = 0;
        this.ˆà = 0;
        this.¥Æ = 0.0f;
        this.Ø­à = 0;
        this.µÕ = 0;
        this.Æ = 0;
        this.Šáƒ = 3;
        this.Ï­Ðƒà = 4000;
        this.áŒŠà = false;
        this.ŠÄ = false;
        this.Ñ¢á = true;
        this.ŒÏ = true;
        this.Çªà¢ = true;
        this.Ê = true;
        this.ÇŽÉ = 1;
        this.ˆá = 0;
        this.ÇŽÕ = false;
        this.É = 0;
        this.áƒ = false;
        this.á€ = false;
        this.Õ = "Default";
        this.à¢ = true;
        this.ŠÂµà = true;
        this.¥à = true;
        this.Âµà = true;
        this.Ç = true;
        this.È = true;
        this.áŠ = true;
        this.ˆáŠ = 2;
        this.áŒŠ = false;
        this.£ÂµÄ = false;
        this.Ø­Âµ = true;
        this.Ä = 0;
        this.Ñ¢Â = 0;
        this.Ï­à = 0;
        this.áˆºáˆºÈ = true;
        this.ÇŽá€ = true;
        this.Ï = true;
        this.Ô = true;
        this.ÇªÓ = true;
        this.áˆºÏ = true;
        this.ˆáƒ = true;
        this.Œ = true;
        this.£Ï = true;
        this.Ø­á = true;
        this.ˆÉ = true;
        this.Ï­Ï­Ï = true;
        this.£Â = true;
        this.£Ó = true;
        this.ÇªÔ = true;
        this.Û = true;
        this.ŠÓ = 2;
        this.ÇŽá = Lists.newArrayList();
        this.Ñ¢à = EntityPlayer.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.ÇªØ­ = true;
        this.£áŒŠá = true;
        this.áˆº = true;
        this.Šà = 1.0f;
        this.áŒŠá€ = true;
        this.ˆà¢ = true;
        this.Ñ¢Ç = false;
        this.£É = true;
        this.Ðƒáƒ = false;
        this.£ÇªÓ = true;
        this.áˆºÕ = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.Ñ¢Ó = true;
        this.Ø­Æ = 1.0f;
        this.áŒŠÔ = 1.0f;
        this.ŠÕ = 0.44366196f;
        this.£Ø­à = 1.0f;
        this.µÐƒáƒ = true;
        this.áŒŠÕ = 4;
        this.ŒÐƒà = Maps.newEnumMap((Class)SoundCategory.class);
        this.ÂµÂ = 0.5f;
        this.áŒŠá = 1.0f;
        this.ˆØ = 1.0f;
        this.áˆºà = 0.5412844f;
        this.ÐƒÂ = 0.31690142f;
        this.£áƒ = 1;
        this.Ï­áˆºÓ = true;
        this.Çª = "";
        this.ÇŽÄ = 0;
        this.ˆÈ = 0;
        this.ˆÅ = 0;
        this.ÇªÉ = new KeyBinding("key.forward", 17, "key.categories.movement");
        this.ŠÏ­áˆºá = new KeyBinding("key.left", 30, "key.categories.movement");
        this.ÇŽà = new KeyBinding("key.back", 31, "key.categories.movement");
        this.ŠáˆºÂ = new KeyBinding("key.right", 32, "key.categories.movement");
        this.Ø­Ñ¢Ï­Ø­áˆº = new KeyBinding("key.jump", 57, "key.categories.movement");
        this.ŒÂ = new KeyBinding("key.sneak", 42, "key.categories.movement");
        this.Ï­Ï = new KeyBinding("key.inventory", 18, "key.categories.inventory");
        this.ŠØ = new KeyBinding("key.use", -99, "key.categories.gameplay");
        this.ˆÐƒØ = new KeyBinding("key.drop", 16, "key.categories.gameplay");
        this.Çªà = new KeyBinding("key.attack", -100, "key.categories.gameplay");
        this.¥Å = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
        this.Œáƒ = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
        this.Œá = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
        this.µÂ = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
        this.Ñ¢ÇŽÏ = new KeyBinding("key.command", 53, "key.categories.multiplayer");
        this.ÇªÂ = new KeyBinding("key.screenshot", 60, "key.categories.misc");
        this.ÂµáˆºÂ = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
        this.¥Âµá€ = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
        this.ÇŽÈ = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
        this.ÇªáˆºÕ = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
        this.Ï­Ä = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
        this.¥áŠ = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
        this.µÊ = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
        this.áˆºáˆºáŠ = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
        this.áŒŠÉ = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
        this.ÇŽØ = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.Çªà, this.ŠØ, this.ÇªÉ, this.ŠÏ­áˆºá, this.ÇŽà, this.ŠáˆºÂ, this.Ø­Ñ¢Ï­Ø­áˆº, this.ŒÂ, this.ˆÐƒØ, this.Ï­Ï, this.Œá, this.µÂ, this.¥Å, this.Ñ¢ÇŽÏ, this.ÇªÂ, this.ÂµáˆºÂ, this.¥Âµá€, this.Œáƒ, this.Ï­Ä, this.¥áŠ, this.µÊ, this.áˆºáˆºáŠ, this.ÇŽÈ, this.ÇªáˆºÕ }, (Object[])this.áŒŠÉ);
        this.ÇŽÊ = EnumDifficulty.Ý;
        this.ˆÂ = "";
        this.£Ô = 70.0f;
        this.Ï­Ó = "en_US";
        this.ŠáŒŠà¢ = false;
        this.ŒÓ = mcIn;
        this.ÐƒáˆºÄ = new File(p_i46326_2_, "options.txt");
        this.ÐƒÓ = new File(p_i46326_2_, "optionsof.txt");
        this.à = (int)GameSettings.HorizonCode_Horizon_È.áŒŠÆ.Âµá€();
        this.¥Ä = new KeyBinding("Zoom", 46, "key.categories.misc");
        this.ÇŽØ = (KeyBinding[])ArrayUtils.add((Object[])this.ÇŽØ, (Object)this.¥Ä);
        GameSettings.HorizonCode_Horizon_È.Ó.HorizonCode_Horizon_È(32.0f);
        this.Ý = 8;
        this.HorizonCode_Horizon_È();
        Config.HorizonCode_Horizon_È(this);
    }
    
    public GameSettings() {
        this.HorizonCode_Horizon_È = 0.5f;
        this.Ý = -1;
        this.Ø­áŒŠá = true;
        this.Ó = true;
        this.à = 120;
        this.Ø = 1;
        this.áŒŠÆ = 0.8f;
        this.áˆºÑ¢Õ = 0;
        this.ÂµÈ = false;
        this.á = 0;
        this.ˆÏ­ = false;
        this.£á = Config.¥Ï();
        this.Å = Config.¥Ï();
        this.£à = 1.0f;
        this.µà = 0;
        this.ˆà = 0;
        this.¥Æ = 0.0f;
        this.Ø­à = 0;
        this.µÕ = 0;
        this.Æ = 0;
        this.Šáƒ = 3;
        this.Ï­Ðƒà = 4000;
        this.áŒŠà = false;
        this.ŠÄ = false;
        this.Ñ¢á = true;
        this.ŒÏ = true;
        this.Çªà¢ = true;
        this.Ê = true;
        this.ÇŽÉ = 1;
        this.ˆá = 0;
        this.ÇŽÕ = false;
        this.É = 0;
        this.áƒ = false;
        this.á€ = false;
        this.Õ = "Default";
        this.à¢ = true;
        this.ŠÂµà = true;
        this.¥à = true;
        this.Âµà = true;
        this.Ç = true;
        this.È = true;
        this.áŠ = true;
        this.ˆáŠ = 2;
        this.áŒŠ = false;
        this.£ÂµÄ = false;
        this.Ø­Âµ = true;
        this.Ä = 0;
        this.Ñ¢Â = 0;
        this.Ï­à = 0;
        this.áˆºáˆºÈ = true;
        this.ÇŽá€ = true;
        this.Ï = true;
        this.Ô = true;
        this.ÇªÓ = true;
        this.áˆºÏ = true;
        this.ˆáƒ = true;
        this.Œ = true;
        this.£Ï = true;
        this.Ø­á = true;
        this.ˆÉ = true;
        this.Ï­Ï­Ï = true;
        this.£Â = true;
        this.£Ó = true;
        this.ÇªÔ = true;
        this.Û = true;
        this.ŠÓ = 2;
        this.ÇŽá = Lists.newArrayList();
        this.Ñ¢à = EntityPlayer.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.ÇªØ­ = true;
        this.£áŒŠá = true;
        this.áˆº = true;
        this.Šà = 1.0f;
        this.áŒŠá€ = true;
        this.ˆà¢ = true;
        this.Ñ¢Ç = false;
        this.£É = true;
        this.Ðƒáƒ = false;
        this.£ÇªÓ = true;
        this.áˆºÕ = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.Ñ¢Ó = true;
        this.Ø­Æ = 1.0f;
        this.áŒŠÔ = 1.0f;
        this.ŠÕ = 0.44366196f;
        this.£Ø­à = 1.0f;
        this.µÐƒáƒ = true;
        this.áŒŠÕ = 4;
        this.ŒÐƒà = Maps.newEnumMap((Class)SoundCategory.class);
        this.ÂµÂ = 0.5f;
        this.áŒŠá = 1.0f;
        this.ˆØ = 1.0f;
        this.áˆºà = 0.5412844f;
        this.ÐƒÂ = 0.31690142f;
        this.£áƒ = 1;
        this.Ï­áˆºÓ = true;
        this.Çª = "";
        this.ÇŽÄ = 0;
        this.ˆÈ = 0;
        this.ˆÅ = 0;
        this.ÇªÉ = new KeyBinding("key.forward", 17, "key.categories.movement");
        this.ŠÏ­áˆºá = new KeyBinding("key.left", 30, "key.categories.movement");
        this.ÇŽà = new KeyBinding("key.back", 31, "key.categories.movement");
        this.ŠáˆºÂ = new KeyBinding("key.right", 32, "key.categories.movement");
        this.Ø­Ñ¢Ï­Ø­áˆº = new KeyBinding("key.jump", 57, "key.categories.movement");
        this.ŒÂ = new KeyBinding("key.sneak", 42, "key.categories.movement");
        this.Ï­Ï = new KeyBinding("key.inventory", 18, "key.categories.inventory");
        this.ŠØ = new KeyBinding("key.use", -99, "key.categories.gameplay");
        this.ˆÐƒØ = new KeyBinding("key.drop", 16, "key.categories.gameplay");
        this.Çªà = new KeyBinding("key.attack", -100, "key.categories.gameplay");
        this.¥Å = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
        this.Œáƒ = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
        this.Œá = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
        this.µÂ = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
        this.Ñ¢ÇŽÏ = new KeyBinding("key.command", 53, "key.categories.multiplayer");
        this.ÇªÂ = new KeyBinding("key.screenshot", 60, "key.categories.misc");
        this.ÂµáˆºÂ = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
        this.¥Âµá€ = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
        this.ÇŽÈ = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
        this.ÇªáˆºÕ = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
        this.Ï­Ä = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
        this.¥áŠ = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
        this.µÊ = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
        this.áˆºáˆºáŠ = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
        this.áŒŠÉ = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
        this.ÇŽØ = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.Çªà, this.ŠØ, this.ÇªÉ, this.ŠÏ­áˆºá, this.ÇŽà, this.ŠáˆºÂ, this.Ø­Ñ¢Ï­Ø­áˆº, this.ŒÂ, this.ˆÐƒØ, this.Ï­Ï, this.Œá, this.µÂ, this.¥Å, this.Ñ¢ÇŽÏ, this.ÇªÂ, this.ÂµáˆºÂ, this.¥Âµá€, this.Œáƒ, this.Ï­Ä, this.¥áŠ, this.µÊ, this.áˆºáˆºáŠ, this.ÇŽÈ, this.ÇªáˆºÕ }, (Object[])this.áŒŠÉ);
        this.ÇŽÊ = EnumDifficulty.Ý;
        this.ˆÂ = "";
        this.£Ô = 70.0f;
        this.Ï­Ó = "en_US";
        this.ŠáŒŠà¢ = false;
    }
    
    public static String HorizonCode_Horizon_È(final int p_74298_0_) {
        return (p_74298_0_ < 0) ? I18n.HorizonCode_Horizon_È("key.mouseButton", p_74298_0_ + 101) : ((p_74298_0_ < 256) ? Keyboard.getKeyName(p_74298_0_) : String.format("%c", (char)(p_74298_0_ - 256)).toUpperCase());
    }
    
    public static boolean HorizonCode_Horizon_È(final KeyBinding p_100015_0_) {
        final int keyCode = p_100015_0_.áŒŠÆ();
        return keyCode >= -100 && keyCode <= 255 && p_100015_0_.áŒŠÆ() != 0 && ((p_100015_0_.áŒŠÆ() < 0) ? Mouse.isButtonDown(p_100015_0_.áŒŠÆ() + 100) : Keyboard.isKeyDown(p_100015_0_.áŒŠÆ()));
    }
    
    public void HorizonCode_Horizon_È(final KeyBinding p_151440_1_, final int p_151440_2_) {
        p_151440_1_.Â(p_151440_2_);
        this.Â();
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_74304_1_, final float p_74304_2_) {
        this.Â(p_74304_1_, p_74304_2_);
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.Â) {
            this.HorizonCode_Horizon_È = p_74304_2_;
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.Ý) {
            this.£Ô = p_74304_2_;
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.Ø­áŒŠá) {
            this.ŠÏ = p_74304_2_;
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.áŒŠÆ) {
            this.à = (int)p_74304_2_;
            this.ˆà¢ = false;
            if (this.à <= 0) {
                this.à = (int)GameSettings.HorizonCode_Horizon_È.áŒŠÆ.Âµá€();
                this.ˆà¢ = true;
            }
            this.áŒŠÆ();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.¥Æ) {
            this.Šà = p_74304_2_;
            this.ŒÓ.Šáƒ.Ø­áŒŠá().Ý();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.ŒÏ) {
            this.£Ø­à = p_74304_2_;
            this.ŒÓ.Šáƒ.Ø­áŒŠá().Ý();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.Çªà¢) {
            this.ŠÕ = p_74304_2_;
            this.ŒÓ.Šáƒ.Ø­áŒŠá().Ý();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.Ñ¢á) {
            this.áŒŠÔ = p_74304_2_;
            this.ŒÓ.Šáƒ.Ø­áŒŠá().Ý();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.ŠÄ) {
            this.Ø­Æ = p_74304_2_;
            this.ŒÓ.Šáƒ.Ø­áŒŠá().Ý();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.Ê) {
            final int var3 = this.áŒŠÕ;
            this.áŒŠÕ = (int)p_74304_2_;
            if (var3 != p_74304_2_) {
                this.ŒÓ.áŠ().HorizonCode_Horizon_È(this.áŒŠÕ);
                this.ŒÓ.¥à().HorizonCode_Horizon_È(TextureMap.à);
                this.ŒÓ.áŠ().HorizonCode_Horizon_È(false, this.áŒŠÕ > 0);
                this.ŒÓ.ŠÄ();
            }
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.Ç) {
            this.£É = !this.£É;
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.Ó) {
            this.Ý = (int)p_74304_2_;
            this.ŒÓ.áˆºÑ¢Õ.áˆºÑ¢Õ();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.ˆá) {
            this.ÂµÂ = p_74304_2_;
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.ÇŽÕ) {
            this.áŒŠá = p_74304_2_;
            this.ŒÓ.Ä().£à();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.É) {
            this.ˆØ = p_74304_2_;
            this.ŒÓ.Ä().£à();
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.áƒ) {
            this.áˆºà = p_74304_2_;
        }
        if (p_74304_1_ == GameSettings.HorizonCode_Horizon_È.á€) {
            this.ÐƒÂ = p_74304_2_;
        }
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_74306_1_, final int p_74306_2_) {
        this.Â(p_74306_1_, p_74306_2_);
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            this.Â = !this.Â;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.£á) {
            this.ŠÑ¢Ó = (this.ŠÑ¢Ó + p_74306_2_ & 0x3);
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Å) {
            this.áˆºá = (this.áˆºá + p_74306_2_) % 3;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.à) {
            this.Ø­áŒŠá = !this.Ø­áŒŠá;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.ÂµÈ) {
            this.ÇªÔ = !this.ÇªÔ;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.ÇŽÉ) {
            this.ŠáŒŠà¢ = !this.ŠáŒŠà¢;
            this.ŒÓ.µà.HorizonCode_Horizon_È(this.ŒÓ.È().HorizonCode_Horizon_È() || this.ŠáŒŠà¢);
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.áˆºÑ¢Õ) {
            this.Ó = !this.Ó;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Ø) {
            this.Âµá€ = !this.Âµá€;
            this.ŒÓ.Ó();
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.á) {
            this.Û = !this.Û;
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.ˆÏ­) {
            this.ŠÓ = (this.ŠÓ + p_74306_2_) % 3;
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.£à) {
            this.Ñ¢à = EntityPlayer.HorizonCode_Horizon_È.HorizonCode_Horizon_È((this.Ñ¢à.HorizonCode_Horizon_È() + p_74306_2_) % 3);
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Õ) {
            this.£áƒ = (this.£áƒ + p_74306_2_) % 3;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.à¢) {
            this.Ï­áˆºÓ = !this.Ï­áˆºÓ;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.ŠÂµà) {
            this.ÇŽÄ = (this.ÇŽÄ + p_74306_2_) % 3;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.¥à) {
            this.ˆÈ = (this.ˆÈ + p_74306_2_) % 3;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Âµà) {
            this.ˆÅ = (this.ˆÅ + p_74306_2_) % 2;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.µà) {
            this.ÇªØ­ = !this.ÇªØ­;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.ˆà) {
            this.£áŒŠá = !this.£áŒŠá;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Ø­à) {
            this.áˆº = !this.áˆº;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.µÕ) {
            this.áŒŠá€ = !this.áŒŠá€;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.áŒŠà) {
            this.ÂµÕ = !this.ÂµÕ;
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Æ) {
            this.¥Ï = !this.¥Ï;
            if (this.ŒÓ.á€() != this.¥Ï) {
                this.ŒÓ.ˆà();
            }
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Šáƒ) {
            Display.setVSyncEnabled(this.ˆà¢ = !this.ˆà¢);
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Ï­Ðƒà) {
            this.Ñ¢Ç = !this.Ñ¢Ç;
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.Ç) {
            this.£É = !this.£É;
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (p_74306_1_ == GameSettings.HorizonCode_Horizon_È.È) {
            this.Ðƒáƒ = !this.Ðƒáƒ;
        }
        this.Â();
    }
    
    public float HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_74296_1_) {
        return (p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Ñ¢Â) ? this.¥Æ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.áˆºÏ) ? this.£à : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.áŒŠÆ) ? ((this.à == GameSettings.HorizonCode_Horizon_È.áŒŠÆ.Âµá€() && this.ˆà¢) ? 0.0f : this.à) : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Ý) ? this.£Ô : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Ø­áŒŠá) ? this.ŠÏ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Âµá€) ? this.ˆ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Â) ? this.HorizonCode_Horizon_È : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.¥Æ) ? this.Šà : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.ŒÏ) ? this.£Ø­à : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Çªà¢) ? this.ŠÕ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.ŠÄ) ? this.Ø­Æ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Ñ¢á) ? this.áŒŠÔ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.áŒŠÆ) ? this.à : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Ê) ? this.áŒŠÕ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.Ó) ? this.Ý : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.ˆá) ? this.ÂµÂ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.ÇŽÕ) ? this.áŒŠá : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.É) ? this.ˆØ : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.áƒ) ? this.áˆºà : ((p_74296_1_ == GameSettings.HorizonCode_Horizon_È.á€) ? this.ÐƒÂ : 0.0f)))))))))))))))))));
    }
    
    public boolean Â(final HorizonCode_Horizon_È p_74308_1_) {
        switch (GameSettings.Â.HorizonCode_Horizon_È[p_74308_1_.ordinal()]) {
            case 1: {
                return this.Â;
            }
            case 2: {
                return this.Ø­áŒŠá;
            }
            case 3: {
                return this.Âµá€;
            }
            case 4: {
                return this.Ó;
            }
            case 5: {
                return this.ÇªÔ;
            }
            case 6: {
                return this.ÇªØ­;
            }
            case 7: {
                return this.£áŒŠá;
            }
            case 8: {
                return this.áˆº;
            }
            case 9: {
                return this.áŒŠá€;
            }
            case 10: {
                return this.¥Ï;
            }
            case 11: {
                return this.ˆà¢;
            }
            case 12: {
                return this.Ñ¢Ç;
            }
            case 13: {
                return this.ÂµÕ;
            }
            case 14: {
                return this.Ï­áˆºÓ;
            }
            case 15: {
                return this.ŠáŒŠà¢;
            }
            case 16: {
                return this.£É;
            }
            case 17: {
                return this.Ðƒáƒ;
            }
            default: {
                return false;
            }
        }
    }
    
    private static String HorizonCode_Horizon_È(final String[] p_74299_0_, int p_74299_1_) {
        if (p_74299_1_ < 0 || p_74299_1_ >= p_74299_0_.length) {
            p_74299_1_ = 0;
        }
        return I18n.HorizonCode_Horizon_È(p_74299_0_[p_74299_1_], new Object[0]);
    }
    
    public String Ý(final HorizonCode_Horizon_È p_74297_1_) {
        final String strOF = this.Ø­áŒŠá(p_74297_1_);
        if (strOF != null) {
            return strOF;
        }
        final String var2 = String.valueOf(I18n.HorizonCode_Horizon_È(p_74297_1_.Ø­áŒŠá(), new Object[0])) + ": ";
        if (p_74297_1_.HorizonCode_Horizon_È()) {
            final float var3 = this.HorizonCode_Horizon_È(p_74297_1_);
            final float var4 = p_74297_1_.Â(var3);
            return (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Â) ? ((var4 == 0.0f) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.sensitivity.min", new Object[0])) : ((var4 == 1.0f) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.sensitivity.max", new Object[0])) : (String.valueOf(var2) + (int)(var4 * 200.0f) + "%"))) : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Ý) ? ((var3 == 70.0f) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.fov.min", new Object[0])) : ((var3 == 110.0f) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.fov.max", new Object[0])) : (String.valueOf(var2) + (int)var3))) : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.áŒŠÆ) ? ((var3 == p_74297_1_.ˆØ) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.framerateLimit.max", new Object[0])) : (String.valueOf(var2) + (int)var3 + " fps")) : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.ÂµÈ) ? ((var3 == p_74297_1_.áŒŠá) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.cloudHeight.min", new Object[0])) : (String.valueOf(var2) + ((int)var3 + 128))) : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Ø­áŒŠá) ? ((var4 == 0.0f) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.gamma.min", new Object[0])) : ((var4 == 1.0f) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.gamma.max", new Object[0])) : (String.valueOf(var2) + "+" + (int)(var4 * 100.0f) + "%"))) : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Âµá€) ? (String.valueOf(var2) + (int)(var4 * 400.0f) + "%") : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.¥Æ) ? (String.valueOf(var2) + (int)(var4 * 90.0f + 10.0f) + "%") : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Çªà¢) ? (String.valueOf(var2) + GuiNewChat.Â(var4) + "px") : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.ŒÏ) ? (String.valueOf(var2) + GuiNewChat.Â(var4) + "px") : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Ñ¢á) ? (String.valueOf(var2) + GuiNewChat.HorizonCode_Horizon_È(var4) + "px") : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Ó) ? (String.valueOf(var2) + (int)var3 + " chunks") : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Ê) ? ((var3 == 0.0f) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.off", new Object[0])) : (String.valueOf(var2) + (int)var3)) : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.á€) ? (String.valueOf(var2) + TwitchStream.HorizonCode_Horizon_È(var4) + " fps") : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.áƒ) ? (String.valueOf(var2) + TwitchStream.Â(var4) + " Kbps") : ((p_74297_1_ == GameSettings.HorizonCode_Horizon_È.ˆá) ? (String.valueOf(var2) + String.format("%.3f bpp", TwitchStream.Ý(var4))) : ((var4 == 0.0f) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.off", new Object[0])) : (String.valueOf(var2) + (int)(var4 * 100.0f) + "%"))))))))))))))));
        }
        if (p_74297_1_.Â()) {
            final boolean var5 = this.Â(p_74297_1_);
            return var5 ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.on", new Object[0])) : (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.off", new Object[0]));
        }
        if (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.£á) {
            return String.valueOf(var2) + HorizonCode_Horizon_È(GameSettings.ÇªÈ, this.ŠÑ¢Ó);
        }
        if (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.£à) {
            return String.valueOf(var2) + I18n.HorizonCode_Horizon_È(this.Ñ¢à.Â(), new Object[0]);
        }
        if (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Å) {
            return String.valueOf(var2) + HorizonCode_Horizon_È(GameSettings.ÇªÅ, this.áˆºá);
        }
        if (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.ˆÏ­) {
            return String.valueOf(var2) + HorizonCode_Horizon_È(GameSettings.ÇŽ, this.ŠÓ);
        }
        if (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Õ) {
            return String.valueOf(var2) + HorizonCode_Horizon_È(GameSettings.ÇŽÅ, this.£áƒ);
        }
        if (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.ŠÂµà) {
            return String.valueOf(var2) + HorizonCode_Horizon_È(GameSettings.¥Ðƒá, this.ÇŽÄ);
        }
        if (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.¥à) {
            return String.valueOf(var2) + HorizonCode_Horizon_È(GameSettings.ÐƒÇŽà, this.ˆÈ);
        }
        if (p_74297_1_ == GameSettings.HorizonCode_Horizon_È.Âµà) {
            return String.valueOf(var2) + HorizonCode_Horizon_È(GameSettings.¥Ê, this.ˆÅ);
        }
        if (p_74297_1_ != GameSettings.HorizonCode_Horizon_È.á) {
            return var2;
        }
        if (this.Û) {
            return String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.graphics.fancy", new Object[0]);
        }
        final String var6 = "options.graphics.fast";
        return String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.graphics.fast", new Object[0]);
    }
    
    public void HorizonCode_Horizon_È() {
        try {
            if (!this.ÐƒáˆºÄ.exists()) {
                return;
            }
            final BufferedReader var9 = new BufferedReader(new FileReader(this.ÐƒáˆºÄ));
            String var10 = "";
            this.ŒÐƒà.clear();
            while ((var10 = var9.readLine()) != null) {
                try {
                    final String[] var11 = var10.split(":");
                    if (var11[0].equals("mouseSensitivity")) {
                        this.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("fov")) {
                        this.£Ô = this.HorizonCode_Horizon_È(var11[1]) * 40.0f + 70.0f;
                    }
                    if (var11[0].equals("gamma")) {
                        this.ŠÏ = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("saturation")) {
                        this.ˆ = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("invertYMouse")) {
                        this.Â = var11[1].equals("true");
                    }
                    if (var11[0].equals("renderDistance")) {
                        this.Ý = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("guiScale")) {
                        this.ŠÑ¢Ó = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("particles")) {
                        this.áˆºá = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("bobView")) {
                        this.Ø­áŒŠá = var11[1].equals("true");
                    }
                    if (var11[0].equals("anaglyph3d")) {
                        this.Âµá€ = var11[1].equals("true");
                    }
                    if (var11[0].equals("maxFps")) {
                        this.à = Integer.parseInt(var11[1]);
                        this.ˆà¢ = false;
                        if (this.à <= 0) {
                            this.à = (int)GameSettings.HorizonCode_Horizon_È.áŒŠÆ.Âµá€();
                            this.ˆà¢ = true;
                        }
                        this.áŒŠÆ();
                    }
                    if (var11[0].equals("fboEnable")) {
                        this.Ó = var11[1].equals("true");
                    }
                    if (var11[0].equals("difficulty")) {
                        this.ÇŽÊ = EnumDifficulty.HorizonCode_Horizon_È(Integer.parseInt(var11[1]));
                    }
                    if (var11[0].equals("fancyGraphics")) {
                        this.Û = var11[1].equals("true");
                    }
                    if (var11[0].equals("ao")) {
                        if (var11[1].equals("true")) {
                            this.ŠÓ = 2;
                        }
                        else if (var11[1].equals("false")) {
                            this.ŠÓ = 0;
                        }
                        else {
                            this.ŠÓ = Integer.parseInt(var11[1]);
                        }
                    }
                    if (var11[0].equals("renderClouds")) {
                        this.ÇªÔ = var11[1].equals("true");
                    }
                    if (var11[0].equals("resourcePacks")) {
                        this.ÇŽá = (List)GameSettings.Çªáˆºá.fromJson(var10.substring(var10.indexOf(58) + 1), (Type)GameSettings.ˆÕ);
                        if (this.ÇŽá == null) {
                            this.ÇŽá = Lists.newArrayList();
                        }
                    }
                    if (var11[0].equals("lastServer") && var11.length >= 2) {
                        this.ˆÂ = var10.substring(var10.indexOf(58) + 1);
                    }
                    if (var11[0].equals("lang") && var11.length >= 2) {
                        this.Ï­Ó = var11[1];
                    }
                    if (var11[0].equals("chatVisibility")) {
                        this.Ñ¢à = EntityPlayer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(Integer.parseInt(var11[1]));
                    }
                    if (var11[0].equals("chatColors")) {
                        this.ÇªØ­ = var11[1].equals("true");
                    }
                    if (var11[0].equals("chatLinks")) {
                        this.£áŒŠá = var11[1].equals("true");
                    }
                    if (var11[0].equals("chatLinksPrompt")) {
                        this.áˆº = var11[1].equals("true");
                    }
                    if (var11[0].equals("chatOpacity")) {
                        this.Šà = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("snooperEnabled")) {
                        this.áŒŠá€ = var11[1].equals("true");
                    }
                    if (var11[0].equals("fullscreen")) {
                        this.¥Ï = var11[1].equals("true");
                    }
                    if (var11[0].equals("enableVsync")) {
                        this.ˆà¢ = var11[1].equals("true");
                        this.áŒŠÆ();
                    }
                    if (var11[0].equals("useVbo")) {
                        this.Ñ¢Ç = var11[1].equals("true");
                    }
                    if (var11[0].equals("hideServerAddress")) {
                        this.Ðƒà = var11[1].equals("true");
                    }
                    if (var11[0].equals("advancedItemTooltips")) {
                        this.¥É = var11[1].equals("true");
                    }
                    if (var11[0].equals("pauseOnLostFocus")) {
                        this.£ÇªÓ = var11[1].equals("true");
                    }
                    if (var11[0].equals("touchscreen")) {
                        this.ÂµÕ = var11[1].equals("true");
                    }
                    if (var11[0].equals("overrideHeight")) {
                        this.Ø­Ñ¢á€ = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("overrideWidth")) {
                        this.Š = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("heldItemTooltips")) {
                        this.Ñ¢Ó = var11[1].equals("true");
                    }
                    if (var11[0].equals("chatHeightFocused")) {
                        this.£Ø­à = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("chatHeightUnfocused")) {
                        this.ŠÕ = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("chatScale")) {
                        this.Ø­Æ = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("chatWidth")) {
                        this.áŒŠÔ = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("showInventoryAchievementHint")) {
                        this.µÐƒáƒ = var11[1].equals("true");
                    }
                    if (var11[0].equals("mipmapLevels")) {
                        this.áŒŠÕ = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("streamBytesPerPixel")) {
                        this.ÂµÂ = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("streamMicVolume")) {
                        this.áŒŠá = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("streamSystemVolume")) {
                        this.ˆØ = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("streamKbps")) {
                        this.áˆºà = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("streamFps")) {
                        this.ÐƒÂ = this.HorizonCode_Horizon_È(var11[1]);
                    }
                    if (var11[0].equals("streamCompression")) {
                        this.£áƒ = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("streamSendMetadata")) {
                        this.Ï­áˆºÓ = var11[1].equals("true");
                    }
                    if (var11[0].equals("streamPreferredServer") && var11.length >= 2) {
                        this.Çª = var10.substring(var10.indexOf(58) + 1);
                    }
                    if (var11[0].equals("streamChatEnabled")) {
                        this.ÇŽÄ = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("streamChatUserFilter")) {
                        this.ˆÈ = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("streamMicToggleBehavior")) {
                        this.ˆÅ = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("forceUnicodeFont")) {
                        this.ŠáŒŠà¢ = var11[1].equals("true");
                    }
                    if (var11[0].equals("allowBlockAlternatives")) {
                        this.£É = var11[1].equals("true");
                    }
                    if (var11[0].equals("reducedDebugInfo")) {
                        this.Ðƒáƒ = var11[1].equals("true");
                    }
                    for (final KeyBinding var15 : this.ÇŽØ) {
                        if (var11[0].equals("key_" + var15.à())) {
                            var15.Â(Integer.parseInt(var11[1]));
                        }
                    }
                    for (final SoundCategory var17 : SoundCategory.values()) {
                        if (var11[0].equals("soundCategory_" + var17.HorizonCode_Horizon_È())) {
                            this.ŒÐƒà.put(var17, this.HorizonCode_Horizon_È(var11[1]));
                        }
                    }
                    for (final EnumPlayerModelParts var19 : EnumPlayerModelParts.values()) {
                        if (var11[0].equals("modelPart_" + var19.Ý())) {
                            this.HorizonCode_Horizon_È(var19, var11[1].equals("true"));
                        }
                    }
                }
                catch (Exception var20) {
                    GameSettings.Ñ¢È.warn("Skipping bad option: " + var10);
                    var20.printStackTrace();
                }
            }
            KeyBinding.Â();
            var9.close();
        }
        catch (Exception var21) {
            GameSettings.Ñ¢È.error("Failed to load options", (Throwable)var21);
        }
        this.Ó();
    }
    
    private float HorizonCode_Horizon_È(final String p_74305_1_) {
        return p_74305_1_.equals("true") ? 1.0f : (p_74305_1_.equals("false") ? 0.0f : Float.parseFloat(p_74305_1_));
    }
    
    public void Â() {
        if (Reflector.Ø­Âµ.Â()) {
            final Object var6 = Reflector.Ó(Reflector.Ä, new Object[0]);
            if (var6 != null && Reflector.Â(var6, Reflector.Ñ¢Â, new Object[0])) {
                return;
            }
        }
        try {
            final PrintWriter var7 = new PrintWriter(new FileWriter(this.ÐƒáˆºÄ));
            var7.println("invertYMouse:" + this.Â);
            var7.println("mouseSensitivity:" + this.HorizonCode_Horizon_È);
            var7.println("fov:" + (this.£Ô - 70.0f) / 40.0f);
            var7.println("gamma:" + this.ŠÏ);
            var7.println("saturation:" + this.ˆ);
            var7.println("renderDistance:" + this.Ý);
            var7.println("guiScale:" + this.ŠÑ¢Ó);
            var7.println("particles:" + this.áˆºá);
            var7.println("bobView:" + this.Ø­áŒŠá);
            var7.println("anaglyph3d:" + this.Âµá€);
            var7.println("maxFps:" + this.à);
            var7.println("fboEnable:" + this.Ó);
            var7.println("difficulty:" + this.ÇŽÊ.HorizonCode_Horizon_È());
            var7.println("fancyGraphics:" + this.Û);
            var7.println("ao:" + this.ŠÓ);
            var7.println("renderClouds:" + this.ÇªÔ);
            var7.println("resourcePacks:" + GameSettings.Çªáˆºá.toJson((Object)this.ÇŽá));
            var7.println("lastServer:" + this.ˆÂ);
            var7.println("lang:" + this.Ï­Ó);
            var7.println("chatVisibility:" + this.Ñ¢à.HorizonCode_Horizon_È());
            var7.println("chatColors:" + this.ÇªØ­);
            var7.println("chatLinks:" + this.£áŒŠá);
            var7.println("chatLinksPrompt:" + this.áˆº);
            var7.println("chatOpacity:" + this.Šà);
            var7.println("snooperEnabled:" + this.áŒŠá€);
            var7.println("fullscreen:" + this.¥Ï);
            var7.println("enableVsync:" + this.ˆà¢);
            var7.println("useVbo:" + this.Ñ¢Ç);
            var7.println("hideServerAddress:" + this.Ðƒà);
            var7.println("advancedItemTooltips:" + this.¥É);
            var7.println("pauseOnLostFocus:" + this.£ÇªÓ);
            var7.println("touchscreen:" + this.ÂµÕ);
            var7.println("overrideWidth:" + this.Š);
            var7.println("overrideHeight:" + this.Ø­Ñ¢á€);
            var7.println("heldItemTooltips:" + this.Ñ¢Ó);
            var7.println("chatHeightFocused:" + this.£Ø­à);
            var7.println("chatHeightUnfocused:" + this.ŠÕ);
            var7.println("chatScale:" + this.Ø­Æ);
            var7.println("chatWidth:" + this.áŒŠÔ);
            var7.println("showInventoryAchievementHint:" + this.µÐƒáƒ);
            var7.println("mipmapLevels:" + this.áŒŠÕ);
            var7.println("streamBytesPerPixel:" + this.ÂµÂ);
            var7.println("streamMicVolume:" + this.áŒŠá);
            var7.println("streamSystemVolume:" + this.ˆØ);
            var7.println("streamKbps:" + this.áˆºà);
            var7.println("streamFps:" + this.ÐƒÂ);
            var7.println("streamCompression:" + this.£áƒ);
            var7.println("streamSendMetadata:" + this.Ï­áˆºÓ);
            var7.println("streamPreferredServer:" + this.Çª);
            var7.println("streamChatEnabled:" + this.ÇŽÄ);
            var7.println("streamChatUserFilter:" + this.ˆÈ);
            var7.println("streamMicToggleBehavior:" + this.ˆÅ);
            var7.println("forceUnicodeFont:" + this.ŠáŒŠà¢);
            var7.println("allowBlockAlternatives:" + this.£É);
            var7.println("reducedDebugInfo:" + this.Ðƒáƒ);
            for (final KeyBinding var11 : this.ÇŽØ) {
                var7.println("key_" + var11.à() + ":" + var11.áŒŠÆ());
            }
            for (final SoundCategory var13 : SoundCategory.values()) {
                var7.println("soundCategory_" + var13.HorizonCode_Horizon_È() + ":" + this.HorizonCode_Horizon_È(var13));
            }
            for (final EnumPlayerModelParts var15 : EnumPlayerModelParts.values()) {
                var7.println("modelPart_" + var15.Ý() + ":" + this.áˆºÕ.contains(var15));
            }
            var7.close();
        }
        catch (Exception var16) {
            GameSettings.Ñ¢È.error("Failed to save options", (Throwable)var16);
        }
        this.à();
        this.Ý();
    }
    
    public float HorizonCode_Horizon_È(final SoundCategory p_151438_1_) {
        return this.ŒÐƒà.containsKey(p_151438_1_) ? this.ŒÐƒà.get(p_151438_1_) : 1.0f;
    }
    
    public void HorizonCode_Horizon_È(final SoundCategory p_151439_1_, final float p_151439_2_) {
        this.ŒÓ.£ÂµÄ().HorizonCode_Horizon_È(p_151439_1_, p_151439_2_);
        this.ŒÐƒà.put(p_151439_1_, p_151439_2_);
    }
    
    public void Ý() {
        if (this.ŒÓ.á != null) {
            int var1 = 0;
            for (final EnumPlayerModelParts var3 : this.áˆºÕ) {
                var1 |= var3.HorizonCode_Horizon_È();
            }
            this.ŒÓ.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C15PacketClientSettings(this.Ï­Ó, this.Ý, this.Ñ¢à, this.ÇªØ­, var1));
        }
    }
    
    public Set Ø­áŒŠá() {
        return (Set)ImmutableSet.copyOf((Collection)this.áˆºÕ);
    }
    
    public void HorizonCode_Horizon_È(final EnumPlayerModelParts p_178878_1_, final boolean p_178878_2_) {
        if (p_178878_2_) {
            this.áˆºÕ.add(p_178878_1_);
        }
        else {
            this.áˆºÕ.remove(p_178878_1_);
        }
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final EnumPlayerModelParts p_178877_1_) {
        if (!this.Ø­áŒŠá().contains(p_178877_1_)) {
            this.áˆºÕ.add(p_178877_1_);
        }
        else {
            this.áˆºÕ.remove(p_178877_1_);
        }
        this.Ý();
    }
    
    public boolean Âµá€() {
        return this.Ý >= 4 && this.ÇªÔ;
    }
    
    private void Â(final HorizonCode_Horizon_È option, final float val) {
        if (option == GameSettings.HorizonCode_Horizon_È.Ñ¢Â) {
            this.¥Æ = val;
            this.ŒÓ.áˆºÑ¢Õ.ÂµÈ();
        }
        if (option == GameSettings.HorizonCode_Horizon_È.áˆºÏ) {
            this.£à = val;
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
    }
    
    private void Â(final HorizonCode_Horizon_È par1EnumOptions, final int par2) {
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŠ) {
            switch (this.Ø) {
                case 1: {
                    this.Ø = 2;
                    if (!Config.Ø­áŒŠá()) {
                        this.Ø = 3;
                        break;
                    }
                    break;
                }
                case 2: {
                    this.Ø = 3;
                    break;
                }
                case 3: {
                    this.Ø = 1;
                    break;
                }
                default: {
                    this.Ø = 1;
                    break;
                }
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆáŠ) {
            this.áŒŠÆ += 0.2f;
            if (this.áŒŠÆ > 0.81f) {
                this.áŒŠÆ = 0.2f;
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠ) {
            ++this.áˆºÑ¢Õ;
            if (this.áˆºÑ¢Õ > 3) {
                this.áˆºÑ¢Õ = 0;
            }
            TextureUtils.Â();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£ÂµÄ) {
            this.ÂµÈ = false;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ø­Âµ) {
            this.á = 0;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇªÂµÕ) {
            this.£á = !this.£á;
            Config.à();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ä) {
            ++this.ˆà;
            if (this.ˆà > 3) {
                this.ˆà = 0;
            }
            this.ŒÓ.áˆºÑ¢Õ.ÂµÈ();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ï­à) {
            ++this.Ø­à;
            if (this.Ø­à > 2) {
                this.Ø­à = 0;
            }
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Š) {
            ++this.Æ;
            if (this.Æ > 2) {
                this.Æ = 0;
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áˆºáˆºÈ) {
            ++this.µÕ;
            if (this.µÕ > 3) {
                this.µÕ = 0;
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇŽá€) {
            ++this.Ñ¢Â;
            if (this.Ñ¢Â > 2) {
                this.Ñ¢Â = 0;
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ï) {
            ++this.Ï­à;
            if (this.Ï­à > 2) {
                this.Ï­à = 0;
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ô) {
            this.áˆºáˆºÈ = !this.áˆºáˆºÈ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇªÓ) {
            this.ÇŽá€ = !this.ÇŽá€;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ø­á) {
            this.Ï = !this.Ï;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆÉ) {
            this.Ô = !this.Ô;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ï­Ï­Ï) {
            this.ÇªÓ = !this.ÇªÓ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£Â) {
            this.áˆºÏ = !this.áˆºÏ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠÏ) {
            this.ˆáƒ = !this.ˆáƒ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠáŠ) {
            this.Œ = !this.Œ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.¥Ä) {
            this.Ø­á = !this.Ø­á;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇªÔ) {
            this.ˆÉ = !this.ˆÉ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ŠÓ) {
            this.Ï­Ï­Ï = !this.Ï­Ï­Ï;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇªØ­) {
            this.£Â = !this.£Â;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ðƒà) {
            this.£Ó = !this.£Ó;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆÓ) {
            this.£Ï = !this.£Ï;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆáƒ) {
            this.áŒŠà = !this.áŒŠà;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Œ) {
            this.Ï­Ðƒà *= 10;
            if (this.Ï­Ðƒà > 40000) {
                this.Ï­Ðƒà = 40;
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£Ï) {
            ++this.Šáƒ;
            if (this.Šáƒ > 3) {
                this.Šáƒ = 1;
            }
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ñ¢Ç) {
            ++this.ˆáŠ;
            if (this.ˆáŠ > 3) {
                this.ˆáŠ = 1;
            }
            if (this.ˆáŠ != 2) {
                this.ŒÓ.ŠÄ();
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£Ó) {
            this.Ñ¢á = !this.Ñ¢á;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆÐƒØ­à) {
            this.ŒÏ = !this.ŒÏ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£Õ) {
            this.Çªà¢ = !this.Çªà¢;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ï­Ô) {
            this.Ê = !this.Ê;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Œà) {
            ++this.ÇŽÉ;
            if (this.ÇŽÉ > 5) {
                this.ÇŽÉ = 1;
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£ÇªÓ) {
            ++this.ˆá;
            if (this.ˆá > 2) {
                this.ˆá = 0;
            }
            this.áˆºÑ¢Õ();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ðƒá) {
            this.ÇŽÕ = !this.ÇŽÕ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆÏ) {
            ++this.É;
            if (this.É > 3) {
                this.É = 0;
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áˆºÇŽØ) {
            this.áƒ = !this.áƒ;
            this.ÂµÈ();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£É) {
            this.µà = 0;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Û) {
            this.ŠÄ = !this.ŠÄ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇŽá) {
            this.á€ = !this.á€;
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£áŒŠá) {
            this.à¢ = !this.à¢;
            CustomColorizer.Â();
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áˆº) {
            this.ŠÂµà = !this.ŠÂµà;
            RandomMobs.HorizonCode_Horizon_È();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Šà) {
            this.¥à = !this.¥à;
            CustomColorizer.Â();
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠá€) {
            this.Âµà = !this.Âµà;
            this.ŒÓ.µà.HorizonCode_Horizon_È(Config.ˆáŠ());
            this.ŒÓ.ˆà.HorizonCode_Horizon_È(Config.ˆáŠ());
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.¥Ï) {
            this.Ç = !this.Ç;
            CustomColorizer.HorizonCode_Horizon_È();
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ñ¢Ó) {
            this.È = !this.È;
            CustomSky.Â();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆà¢) {
            this.áŠ = !this.áŠ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.¥É) {
            this.áŒŠ = !this.áŒŠ;
            NaturalTextures.HorizonCode_Horizon_È();
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ø­Æ) {
            this.£ÂµÄ = !this.£ÂµÄ;
            MathHelper.Ó = this.£ÂµÄ;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠÔ) {
            this.Ø­Âµ = !this.Ø­Âµ;
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ŠÕ) {
            if (this.Ä == 0) {
                this.Ä = 1;
            }
            else if (this.Ä == 1) {
                this.Ä = 2;
            }
            else if (this.Ä == 2) {
                this.Ä = 0;
            }
            else {
                this.Ä = 0;
            }
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ø­Ñ¢á€) {
            this.Å = !this.Å;
            Config.áŒŠá€();
            if (!Config.¥Ï()) {
                this.Å = false;
            }
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ñ¢à) {
            final List modeList = Arrays.asList(Config.Ï­Ô());
            if (this.Õ.equals("Default")) {
                this.Õ = modeList.get(0);
            }
            else {
                int index = modeList.indexOf(this.Õ);
                if (index < 0) {
                    this.Õ = "Default";
                }
                else if (++index >= modeList.size()) {
                    this.Õ = "Default";
                }
                else {
                    this.Õ = modeList.get(index);
                }
            }
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÂµÕ) {
            this.Ñ¢Ó = !this.Ñ¢Ó;
        }
    }
    
    private String Ø­áŒŠá(final HorizonCode_Horizon_È par1EnumOptions) {
        String var2 = String.valueOf(I18n.HorizonCode_Horizon_È(par1EnumOptions.Ø­áŒŠá(), new Object[0])) + ": ";
        if (var2 == null) {
            var2 = par1EnumOptions.Ø­áŒŠá();
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ó) {
            final int var3 = (int)this.HorizonCode_Horizon_È(par1EnumOptions);
            String str = "Tiny";
            byte baseDist = 2;
            if (var3 >= 4) {
                str = "Short";
                baseDist = 4;
            }
            if (var3 >= 8) {
                str = "Normal";
                baseDist = 8;
            }
            if (var3 >= 16) {
                str = "Far";
                baseDist = 16;
            }
            if (var3 >= 32) {
                str = "Extreme";
                baseDist = 32;
            }
            final int diff = this.Ý - baseDist;
            String descr = str;
            if (diff > 0) {
                descr = String.valueOf(str) + "+";
            }
            return String.valueOf(var2) + var3 + " " + descr;
        }
        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŠ) {
            switch (this.Ø) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
                case 3: {
                    return String.valueOf(var2) + "OFF";
                }
                default: {
                    return String.valueOf(var2) + "OFF";
                }
            }
        }
        else {
            if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆáŠ) {
                return String.valueOf(var2) + this.áŒŠÆ;
            }
            if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠ) {
                switch (this.áˆºÑ¢Õ) {
                    case 0: {
                        return String.valueOf(var2) + "Nearest";
                    }
                    case 1: {
                        return String.valueOf(var2) + "Linear";
                    }
                    case 2: {
                        return String.valueOf(var2) + "Bilinear";
                    }
                    case 3: {
                        return String.valueOf(var2) + "Trilinear";
                    }
                    default: {
                        return String.valueOf(var2) + "Nearest";
                    }
                }
            }
            else {
                if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£ÂµÄ) {
                    return this.ÂµÈ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                }
                if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ø­Âµ) {
                    return (this.á == 0) ? (String.valueOf(var2) + "OFF") : (String.valueOf(var2) + this.á);
                }
                if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇªÂµÕ) {
                    return this.£á ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                }
                if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ä) {
                    switch (this.ˆà) {
                        case 1: {
                            return String.valueOf(var2) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var2) + "Fancy";
                        }
                        case 3: {
                            return String.valueOf(var2) + "OFF";
                        }
                        default: {
                            return String.valueOf(var2) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ï­à) {
                    switch (this.Ø­à) {
                        case 1: {
                            return String.valueOf(var2) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var2) + "Fancy";
                        }
                        default: {
                            return String.valueOf(var2) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Š) {
                    switch (this.Æ) {
                        case 1: {
                            return String.valueOf(var2) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var2) + "Fancy";
                        }
                        default: {
                            return String.valueOf(var2) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áˆºáˆºÈ) {
                    switch (this.µÕ) {
                        case 1: {
                            return String.valueOf(var2) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var2) + "Fancy";
                        }
                        case 3: {
                            return String.valueOf(var2) + "OFF";
                        }
                        default: {
                            return String.valueOf(var2) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇŽá€) {
                    switch (this.Ñ¢Â) {
                        case 1: {
                            return String.valueOf(var2) + "Dynamic";
                        }
                        case 2: {
                            return String.valueOf(var2) + "OFF";
                        }
                        default: {
                            return String.valueOf(var2) + "ON";
                        }
                    }
                }
                else if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ï) {
                    switch (this.Ï­à) {
                        case 1: {
                            return String.valueOf(var2) + "Dynamic";
                        }
                        case 2: {
                            return String.valueOf(var2) + "OFF";
                        }
                        default: {
                            return String.valueOf(var2) + "ON";
                        }
                    }
                }
                else {
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ô) {
                        return this.áˆºáˆºÈ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇªÓ) {
                        return this.ÇŽá€ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ø­á) {
                        return this.Ï ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆÉ) {
                        return this.Ô ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ï­Ï­Ï) {
                        return this.ÇªÓ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£Â) {
                        return this.áˆºÏ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠÏ) {
                        return this.ˆáƒ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠáŠ) {
                        return this.Œ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.¥Ä) {
                        return this.Ø­á ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇªÔ) {
                        return this.ˆÉ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ŠÓ) {
                        return this.Ï­Ï­Ï ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇªØ­) {
                        return this.£Â ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ðƒà) {
                        return this.£Ó ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆÓ) {
                        return this.£Ï ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆáƒ) {
                        return this.áŒŠà ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Œ) {
                        return (this.Ï­Ðƒà <= 40) ? (String.valueOf(var2) + "Default (2s)") : ((this.Ï­Ðƒà <= 400) ? (String.valueOf(var2) + "20s") : ((this.Ï­Ðƒà <= 4000) ? (String.valueOf(var2) + "3min") : (String.valueOf(var2) + "30min")));
                    }
                    if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£Ï) {
                        switch (this.Šáƒ) {
                            case 1: {
                                return String.valueOf(var2) + "Fast";
                            }
                            case 2: {
                                return String.valueOf(var2) + "Fancy";
                            }
                            default: {
                                return String.valueOf(var2) + "OFF";
                            }
                        }
                    }
                    else if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ñ¢Ç) {
                        switch (this.ˆáŠ) {
                            case 1: {
                                return String.valueOf(var2) + "Fast";
                            }
                            case 2: {
                                return String.valueOf(var2) + "Fancy";
                            }
                            default: {
                                return String.valueOf(var2) + "OFF";
                            }
                        }
                    }
                    else {
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£Ó) {
                            return this.Ñ¢á ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆÐƒØ­à) {
                            return this.ŒÏ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£Õ) {
                            return this.Çªà¢ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ï­Ô) {
                            return this.Ê ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Œà) {
                            return String.valueOf(var2) + this.ÇŽÉ;
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£ÇªÓ) {
                            return (this.ˆá == 1) ? (String.valueOf(var2) + "Smooth") : ((this.ˆá == 2) ? (String.valueOf(var2) + "Multi-Core") : (String.valueOf(var2) + "Default"));
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ðƒá) {
                            return this.ÇŽÕ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆÏ) {
                            return (this.É == 1) ? (String.valueOf(var2) + "Day Only") : ((this.É == 3) ? (String.valueOf(var2) + "Night Only") : (String.valueOf(var2) + "Default"));
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áˆºÇŽØ) {
                            return this.áƒ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£É) {
                            return (this.µà == 0) ? (String.valueOf(var2) + "OFF") : (String.valueOf(var2) + this.µà);
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Û) {
                            return this.ŠÄ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÇŽá) {
                            return this.á€ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.£áŒŠá) {
                            return this.à¢ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áˆº) {
                            return this.ŠÂµà ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Šà) {
                            return this.¥à ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠá€) {
                            return this.Âµà ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.¥Ï) {
                            return this.Ç ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ñ¢Ó) {
                            return this.È ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ˆà¢) {
                            return this.áŠ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.¥É) {
                            return this.áŒŠ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ø­Æ) {
                            return this.£ÂµÄ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠÔ) {
                            return this.Ø­Âµ ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ŠÕ) {
                            return (this.Ä == 1) ? (String.valueOf(var2) + "Fast") : ((this.Ä == 2) ? (String.valueOf(var2) + "Fancy") : (String.valueOf(var2) + "Default"));
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ø­Ñ¢á€) {
                            return this.Å ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.Ñ¢à) {
                            return String.valueOf(var2) + this.Õ;
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.ÂµÕ) {
                            return this.Ñ¢Ó ? (String.valueOf(var2) + "ON") : (String.valueOf(var2) + "OFF");
                        }
                        if (par1EnumOptions == GameSettings.HorizonCode_Horizon_È.áŒŠÆ) {
                            final float var4 = this.HorizonCode_Horizon_È(par1EnumOptions);
                            return (var4 == 0.0f) ? (String.valueOf(var2) + "VSync") : ((var4 == par1EnumOptions.ˆØ) ? (String.valueOf(var2) + I18n.HorizonCode_Horizon_È("options.framerateLimit.max", new Object[0])) : (String.valueOf(var2) + (int)var4 + " fps"));
                        }
                        return null;
                    }
                }
            }
        }
    }
    
    public void Ó() {
        try {
            File exception = this.ÐƒÓ;
            if (!exception.exists()) {
                exception = this.ÐƒáˆºÄ;
            }
            if (!exception.exists()) {
                return;
            }
            final BufferedReader bufferedreader = new BufferedReader(new FileReader(exception));
            String s = "";
            while ((s = bufferedreader.readLine()) != null) {
                try {
                    final String[] exception2 = s.split(":");
                    if (exception2[0].equals("ofRenderDistanceChunks") && exception2.length >= 2) {
                        this.Ý = Integer.valueOf(exception2[1]);
                        this.Ý = Config.HorizonCode_Horizon_È(this.Ý, 2, 32);
                    }
                    if (exception2[0].equals("ofFogType") && exception2.length >= 2) {
                        this.Ø = Integer.valueOf(exception2[1]);
                        this.Ø = Config.HorizonCode_Horizon_È(this.Ø, 1, 3);
                    }
                    if (exception2[0].equals("ofFogStart") && exception2.length >= 2) {
                        this.áŒŠÆ = Float.valueOf(exception2[1]);
                        if (this.áŒŠÆ < 0.2f) {
                            this.áŒŠÆ = 0.2f;
                        }
                        if (this.áŒŠÆ > 0.81f) {
                            this.áŒŠÆ = 0.8f;
                        }
                    }
                    if (exception2[0].equals("ofMipmapType") && exception2.length >= 2) {
                        this.áˆºÑ¢Õ = Integer.valueOf(exception2[1]);
                        this.áˆºÑ¢Õ = Config.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, 0, 3);
                    }
                    if (exception2[0].equals("ofLoadFar") && exception2.length >= 2) {
                        this.ÂµÈ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofPreloadedChunks") && exception2.length >= 2) {
                        this.á = Integer.valueOf(exception2[1]);
                        if (this.á < 0) {
                            this.á = 0;
                        }
                        if (this.á > 8) {
                            this.á = 8;
                        }
                    }
                    if (exception2[0].equals("ofOcclusionFancy") && exception2.length >= 2) {
                        this.ˆÏ­ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSmoothWorld") && exception2.length >= 2) {
                        this.£á = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAoLevel") && exception2.length >= 2) {
                        this.£à = Float.valueOf(exception2[1]);
                        this.£à = Config.HorizonCode_Horizon_È(this.£à, 0.0f, 1.0f);
                    }
                    if (exception2[0].equals("ofClouds") && exception2.length >= 2) {
                        this.ˆà = Integer.valueOf(exception2[1]);
                        this.ˆà = Config.HorizonCode_Horizon_È(this.ˆà, 0, 3);
                    }
                    if (exception2[0].equals("ofCloudsHeight") && exception2.length >= 2) {
                        this.¥Æ = Float.valueOf(exception2[1]);
                        this.¥Æ = Config.HorizonCode_Horizon_È(this.¥Æ, 0.0f, 1.0f);
                    }
                    if (exception2[0].equals("ofTrees") && exception2.length >= 2) {
                        this.Ø­à = Integer.valueOf(exception2[1]);
                        this.Ø­à = Config.HorizonCode_Horizon_È(this.Ø­à, 0, 2);
                    }
                    if (exception2[0].equals("ofDroppedItems") && exception2.length >= 2) {
                        this.Æ = Integer.valueOf(exception2[1]);
                        this.Æ = Config.HorizonCode_Horizon_È(this.Æ, 0, 2);
                    }
                    if (exception2[0].equals("ofRain") && exception2.length >= 2) {
                        this.µÕ = Integer.valueOf(exception2[1]);
                        this.µÕ = Config.HorizonCode_Horizon_È(this.µÕ, 0, 3);
                    }
                    if (exception2[0].equals("ofAnimatedWater") && exception2.length >= 2) {
                        this.Ñ¢Â = Integer.valueOf(exception2[1]);
                        this.Ñ¢Â = Config.HorizonCode_Horizon_È(this.Ñ¢Â, 0, 2);
                    }
                    if (exception2[0].equals("ofAnimatedLava") && exception2.length >= 2) {
                        this.Ï­à = Integer.valueOf(exception2[1]);
                        this.Ï­à = Config.HorizonCode_Horizon_È(this.Ï­à, 0, 2);
                    }
                    if (exception2[0].equals("ofAnimatedFire") && exception2.length >= 2) {
                        this.áˆºáˆºÈ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedPortal") && exception2.length >= 2) {
                        this.ÇŽá€ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedRedstone") && exception2.length >= 2) {
                        this.Ï = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedExplosion") && exception2.length >= 2) {
                        this.Ô = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedFlame") && exception2.length >= 2) {
                        this.ÇªÓ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedSmoke") && exception2.length >= 2) {
                        this.áˆºÏ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofVoidParticles") && exception2.length >= 2) {
                        this.ˆáƒ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofWaterParticles") && exception2.length >= 2) {
                        this.Œ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofPortalParticles") && exception2.length >= 2) {
                        this.Ø­á = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofPotionParticles") && exception2.length >= 2) {
                        this.ˆÉ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofDrippingWaterLava") && exception2.length >= 2) {
                        this.Ï­Ï­Ï = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedTerrain") && exception2.length >= 2) {
                        this.£Â = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedTextures") && exception2.length >= 2) {
                        this.£Ó = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofRainSplash") && exception2.length >= 2) {
                        this.£Ï = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofLagometer") && exception2.length >= 2) {
                        this.áŒŠà = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAutoSaveTicks") && exception2.length >= 2) {
                        this.Ï­Ðƒà = Integer.valueOf(exception2[1]);
                        this.Ï­Ðƒà = Config.HorizonCode_Horizon_È(this.Ï­Ðƒà, 40, 40000);
                    }
                    if (exception2[0].equals("ofBetterGrass") && exception2.length >= 2) {
                        this.Šáƒ = Integer.valueOf(exception2[1]);
                        this.Šáƒ = Config.HorizonCode_Horizon_È(this.Šáƒ, 1, 3);
                    }
                    if (exception2[0].equals("ofConnectedTextures") && exception2.length >= 2) {
                        this.ˆáŠ = Integer.valueOf(exception2[1]);
                        this.ˆáŠ = Config.HorizonCode_Horizon_È(this.ˆáŠ, 1, 3);
                    }
                    if (exception2[0].equals("ofWeather") && exception2.length >= 2) {
                        this.Ñ¢á = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSky") && exception2.length >= 2) {
                        this.ŒÏ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofStars") && exception2.length >= 2) {
                        this.Çªà¢ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSunMoon") && exception2.length >= 2) {
                        this.Ê = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofChunkUpdates") && exception2.length >= 2) {
                        this.ÇŽÉ = Integer.valueOf(exception2[1]);
                        this.ÇŽÉ = Config.HorizonCode_Horizon_È(this.ÇŽÉ, 1, 5);
                    }
                    if (exception2[0].equals("ofChunkLoading") && exception2.length >= 2) {
                        this.ˆá = Integer.valueOf(exception2[1]);
                        this.ˆá = Config.HorizonCode_Horizon_È(this.ˆá, 0, 2);
                        this.áˆºÑ¢Õ();
                    }
                    if (exception2[0].equals("ofChunkUpdatesDynamic") && exception2.length >= 2) {
                        this.ÇŽÕ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofTime") && exception2.length >= 2) {
                        this.É = Integer.valueOf(exception2[1]);
                        this.É = Config.HorizonCode_Horizon_È(this.É, 0, 3);
                    }
                    if (exception2[0].equals("ofClearWater") && exception2.length >= 2) {
                        this.áƒ = Boolean.valueOf(exception2[1]);
                        this.ÂµÈ();
                    }
                    if (exception2[0].equals("ofAaLevel") && exception2.length >= 2) {
                        this.µà = Integer.valueOf(exception2[1]);
                        this.µà = Config.HorizonCode_Horizon_È(this.µà, 0, 16);
                    }
                    if (exception2[0].equals("ofProfiler") && exception2.length >= 2) {
                        this.ŠÄ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofBetterSnow") && exception2.length >= 2) {
                        this.á€ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSwampColors") && exception2.length >= 2) {
                        this.à¢ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofRandomMobs") && exception2.length >= 2) {
                        this.ŠÂµà = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSmoothBiomes") && exception2.length >= 2) {
                        this.¥à = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofCustomFonts") && exception2.length >= 2) {
                        this.Âµà = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofCustomColors") && exception2.length >= 2) {
                        this.Ç = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofCustomSky") && exception2.length >= 2) {
                        this.È = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofShowCapes") && exception2.length >= 2) {
                        this.áŠ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofNaturalTextures") && exception2.length >= 2) {
                        this.áŒŠ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofLazyChunkLoading") && exception2.length >= 2) {
                        this.Å = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofFullscreenMode") && exception2.length >= 2) {
                        this.Õ = exception2[1];
                    }
                    if (exception2[0].equals("ofFastMath") && exception2.length >= 2) {
                        this.£ÂµÄ = Boolean.valueOf(exception2[1]);
                        MathHelper.Ó = this.£ÂµÄ;
                    }
                    if (exception2[0].equals("ofFastRender") && exception2.length >= 2) {
                        this.Ø­Âµ = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofTranslucentBlocks") && exception2.length >= 2) {
                        this.Ä = Integer.valueOf(exception2[1]);
                        this.Ä = Config.HorizonCode_Horizon_È(this.Ä, 0, 2);
                    }
                    if (!exception2[0].equals("key_" + this.¥Ä.à())) {
                        continue;
                    }
                    this.¥Ä.Â(Integer.parseInt(exception2[1]));
                }
                catch (Exception var5) {
                    Config.HorizonCode_Horizon_È("Skipping bad option: " + s);
                    var5.printStackTrace();
                }
            }
            KeyBinding.Â();
            bufferedreader.close();
        }
        catch (Exception var6) {
            Config.Â("Failed to load options");
            var6.printStackTrace();
        }
    }
    
    public void à() {
        try {
            final PrintWriter exception = new PrintWriter(new FileWriter(this.ÐƒÓ));
            exception.println("ofRenderDistanceChunks:" + this.Ý);
            exception.println("ofFogType:" + this.Ø);
            exception.println("ofFogStart:" + this.áŒŠÆ);
            exception.println("ofMipmapType:" + this.áˆºÑ¢Õ);
            exception.println("ofLoadFar:" + this.ÂµÈ);
            exception.println("ofPreloadedChunks:" + this.á);
            exception.println("ofOcclusionFancy:" + this.ˆÏ­);
            exception.println("ofSmoothWorld:" + this.£á);
            exception.println("ofAoLevel:" + this.£à);
            exception.println("ofClouds:" + this.ˆà);
            exception.println("ofCloudsHeight:" + this.¥Æ);
            exception.println("ofTrees:" + this.Ø­à);
            exception.println("ofDroppedItems:" + this.Æ);
            exception.println("ofRain:" + this.µÕ);
            exception.println("ofAnimatedWater:" + this.Ñ¢Â);
            exception.println("ofAnimatedLava:" + this.Ï­à);
            exception.println("ofAnimatedFire:" + this.áˆºáˆºÈ);
            exception.println("ofAnimatedPortal:" + this.ÇŽá€);
            exception.println("ofAnimatedRedstone:" + this.Ï);
            exception.println("ofAnimatedExplosion:" + this.Ô);
            exception.println("ofAnimatedFlame:" + this.ÇªÓ);
            exception.println("ofAnimatedSmoke:" + this.áˆºÏ);
            exception.println("ofVoidParticles:" + this.ˆáƒ);
            exception.println("ofWaterParticles:" + this.Œ);
            exception.println("ofPortalParticles:" + this.Ø­á);
            exception.println("ofPotionParticles:" + this.ˆÉ);
            exception.println("ofDrippingWaterLava:" + this.Ï­Ï­Ï);
            exception.println("ofAnimatedTerrain:" + this.£Â);
            exception.println("ofAnimatedTextures:" + this.£Ó);
            exception.println("ofRainSplash:" + this.£Ï);
            exception.println("ofLagometer:" + this.áŒŠà);
            exception.println("ofAutoSaveTicks:" + this.Ï­Ðƒà);
            exception.println("ofBetterGrass:" + this.Šáƒ);
            exception.println("ofConnectedTextures:" + this.ˆáŠ);
            exception.println("ofWeather:" + this.Ñ¢á);
            exception.println("ofSky:" + this.ŒÏ);
            exception.println("ofStars:" + this.Çªà¢);
            exception.println("ofSunMoon:" + this.Ê);
            exception.println("ofChunkUpdates:" + this.ÇŽÉ);
            exception.println("ofChunkLoading:" + this.ˆá);
            exception.println("ofChunkUpdatesDynamic:" + this.ÇŽÕ);
            exception.println("ofTime:" + this.É);
            exception.println("ofClearWater:" + this.áƒ);
            exception.println("ofAaLevel:" + this.µà);
            exception.println("ofProfiler:" + this.ŠÄ);
            exception.println("ofBetterSnow:" + this.á€);
            exception.println("ofSwampColors:" + this.à¢);
            exception.println("ofRandomMobs:" + this.ŠÂµà);
            exception.println("ofSmoothBiomes:" + this.¥à);
            exception.println("ofCustomFonts:" + this.Âµà);
            exception.println("ofCustomColors:" + this.Ç);
            exception.println("ofCustomSky:" + this.È);
            exception.println("ofShowCapes:" + this.áŠ);
            exception.println("ofNaturalTextures:" + this.áŒŠ);
            exception.println("ofLazyChunkLoading:" + this.Å);
            exception.println("ofFullscreenMode:" + this.Õ);
            exception.println("ofFastMath:" + this.£ÂµÄ);
            exception.println("ofFastRender:" + this.Ø­Âµ);
            exception.println("ofTranslucentBlocks:" + this.Ä);
            exception.println("key_" + this.¥Ä.à() + ":" + this.¥Ä.áŒŠÆ());
            exception.close();
        }
        catch (Exception var2) {
            Config.Â("Failed to save options");
            var2.printStackTrace();
        }
    }
    
    public void Ø() {
        this.Ý = 8;
        this.Ø­áŒŠá = true;
        this.Âµá€ = false;
        this.à = (int)GameSettings.HorizonCode_Horizon_È.áŒŠÆ.Âµá€();
        this.ˆà¢ = false;
        this.áŒŠÆ();
        this.áŒŠÕ = 4;
        this.Û = true;
        this.ŠÓ = 2;
        this.ÇªÔ = true;
        this.£Ô = 70.0f;
        this.ŠÏ = 0.0f;
        this.ŠÑ¢Ó = 0;
        this.áˆºá = 0;
        this.Ñ¢Ó = true;
        this.Ñ¢Ç = false;
        this.£É = true;
        this.ŠáŒŠà¢ = false;
        this.Ø = 1;
        this.áŒŠÆ = 0.8f;
        this.áˆºÑ¢Õ = 0;
        this.ÂµÈ = false;
        this.á = 0;
        this.ˆÏ­ = false;
        Config.áŒŠá€();
        this.£á = Config.¥Ï();
        this.Å = Config.¥Ï();
        this.£ÂµÄ = false;
        this.Ø­Âµ = true;
        this.Ä = 0;
        this.£à = 1.0f;
        this.µà = 0;
        this.ˆà = 0;
        this.¥Æ = 0.0f;
        this.Ø­à = 0;
        this.µÕ = 0;
        this.Šáƒ = 3;
        this.Ï­Ðƒà = 4000;
        this.áŒŠà = false;
        this.ŠÄ = false;
        this.Ñ¢á = true;
        this.ŒÏ = true;
        this.Çªà¢ = true;
        this.Ê = true;
        this.ÇŽÉ = 1;
        this.ˆá = 0;
        this.ÇŽÕ = false;
        this.É = 0;
        this.áƒ = false;
        this.á€ = false;
        this.Õ = "Default";
        this.à¢ = true;
        this.ŠÂµà = true;
        this.¥à = true;
        this.Âµà = true;
        this.Ç = true;
        this.È = true;
        this.áŠ = true;
        this.ˆáŠ = 2;
        this.áŒŠ = false;
        this.Ñ¢Â = 0;
        this.Ï­à = 0;
        this.áˆºáˆºÈ = true;
        this.ÇŽá€ = true;
        this.Ï = true;
        this.Ô = true;
        this.ÇªÓ = true;
        this.áˆºÏ = true;
        this.ˆáƒ = true;
        this.Œ = true;
        this.£Ï = true;
        this.Ø­á = true;
        this.ˆÉ = true;
        this.Ï­Ï­Ï = true;
        this.£Â = true;
        this.£Ó = true;
        this.ÂµÈ();
        this.ŒÓ.Ó();
        this.Â();
    }
    
    public void áŒŠÆ() {
        Display.setVSyncEnabled(this.ˆà¢);
    }
    
    private void ÂµÈ() {
        if (this.ŒÓ.Ê() && this.ŒÓ.ˆá() != null) {
            Config.áˆºÑ¢Õ = true;
        }
        ClearWater.HorizonCode_Horizon_È(this, this.ŒÓ.áŒŠÆ);
    }
    
    public void áˆºÑ¢Õ() {
        if (this.ŒÓ.áˆºÑ¢Õ != null) {
            this.ŒÓ.áˆºÑ¢Õ.Ø­áŒŠá();
        }
    }
    
    public void HorizonCode_Horizon_È(final boolean flag) {
        final int animVal = flag ? 0 : 2;
        this.Ñ¢Â = animVal;
        this.Ï­à = animVal;
        this.áˆºáˆºÈ = flag;
        this.ÇŽá€ = flag;
        this.Ï = flag;
        this.Ô = flag;
        this.ÇªÓ = flag;
        this.áˆºÏ = flag;
        this.ˆáƒ = flag;
        this.Œ = flag;
        this.£Ï = flag;
        this.Ø­á = flag;
        this.ˆÉ = flag;
        this.áˆºá = (flag ? 0 : 2);
        this.Ï­Ï­Ï = flag;
        this.£Â = flag;
        this.£Ó = flag;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true), 
        Â("SENSITIVITY", 1, "SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false), 
        Ý("FOV", 2, "FOV", 2, "FOV", 2, "options.fov", true, false, 30.0f, 110.0f, 1.0f), 
        Ø­áŒŠá("GAMMA", 3, "GAMMA", 3, "GAMMA", 3, "options.gamma", true, false), 
        Âµá€("SATURATION", 4, "SATURATION", 4, "SATURATION", 4, "options.saturation", true, false), 
        Ó("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0f, 16.0f, 1.0f), 
        à("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true), 
        Ø("ANAGLYPH", 7, "ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true), 
        áŒŠÆ("FRAMERATE_LIMIT", 8, "FRAMERATE_LIMIT", 8, "FRAMERATE_LIMIT", 8, "options.framerateLimit", true, false, 0.0f, 260.0f, 5.0f), 
        áˆºÑ¢Õ("FBO_ENABLE", 9, "FBO_ENABLE", 9, "FBO_ENABLE", 9, "options.fboEnable", false, true), 
        ÂµÈ("RENDER_CLOUDS", 10, "RENDER_CLOUDS", 10, "RENDER_CLOUDS", 10, "options.renderClouds", false, true), 
        á("GRAPHICS", 11, "GRAPHICS", 11, "GRAPHICS", 11, "options.graphics", false, false), 
        ˆÏ­("AMBIENT_OCCLUSION", 12, "AMBIENT_OCCLUSION", 12, "AMBIENT_OCCLUSION", 12, "options.ao", false, false), 
        £á("GUI_SCALE", 13, "GUI_SCALE", 13, "GUI_SCALE", 13, "options.guiScale", false, false), 
        Å("PARTICLES", 14, "PARTICLES", 14, "PARTICLES", 14, "options.particles", false, false), 
        £à("CHAT_VISIBILITY", 15, "CHAT_VISIBILITY", 15, "CHAT_VISIBILITY", 15, "options.chat.visibility", false, false), 
        µà("CHAT_COLOR", 16, "CHAT_COLOR", 16, "CHAT_COLOR", 16, "options.chat.color", false, true), 
        ˆà("CHAT_LINKS", 17, "CHAT_LINKS", 17, "CHAT_LINKS", 17, "options.chat.links", false, true), 
        ¥Æ("CHAT_OPACITY", 18, "CHAT_OPACITY", 18, "CHAT_OPACITY", 18, "options.chat.opacity", true, false), 
        Ø­à("CHAT_LINKS_PROMPT", 19, "CHAT_LINKS_PROMPT", 19, "CHAT_LINKS_PROMPT", 19, "options.chat.links.prompt", false, true), 
        µÕ("SNOOPER_ENABLED", 20, "SNOOPER_ENABLED", 20, "SNOOPER_ENABLED", 20, "options.snooper", false, true), 
        Æ("USE_FULLSCREEN", 21, "USE_FULLSCREEN", 21, "USE_FULLSCREEN", 21, "options.fullscreen", false, true), 
        Šáƒ("ENABLE_VSYNC", 22, "ENABLE_VSYNC", 22, "ENABLE_VSYNC", 22, "options.vsync", false, true), 
        Ï­Ðƒà("USE_VBO", 23, "USE_VBO", 23, "USE_VBO", 23, "options.vbo", false, true), 
        áŒŠà("TOUCHSCREEN", 24, "TOUCHSCREEN", 24, "TOUCHSCREEN", 24, "options.touchscreen", false, true), 
        ŠÄ("CHAT_SCALE", 25, "CHAT_SCALE", 25, "CHAT_SCALE", 25, "options.chat.scale", true, false), 
        Ñ¢á("CHAT_WIDTH", 26, "CHAT_WIDTH", 26, "CHAT_WIDTH", 26, "options.chat.width", true, false), 
        ŒÏ("CHAT_HEIGHT_FOCUSED", 27, "CHAT_HEIGHT_FOCUSED", 27, "CHAT_HEIGHT_FOCUSED", 27, "options.chat.height.focused", true, false), 
        Çªà¢("CHAT_HEIGHT_UNFOCUSED", 28, "CHAT_HEIGHT_UNFOCUSED", 28, "CHAT_HEIGHT_UNFOCUSED", 28, "options.chat.height.unfocused", true, false), 
        Ê("MIPMAP_LEVELS", 29, "MIPMAP_LEVELS", 29, "MIPMAP_LEVELS", 29, "options.mipmapLevels", true, false, 0.0f, 4.0f, 1.0f), 
        ÇŽÉ("FORCE_UNICODE_FONT", 30, "FORCE_UNICODE_FONT", 30, "FORCE_UNICODE_FONT", 30, "options.forceUnicodeFont", false, true), 
        ˆá("STREAM_BYTES_PER_PIXEL", 31, "STREAM_BYTES_PER_PIXEL", 31, "STREAM_BYTES_PER_PIXEL", 31, "options.stream.bytesPerPixel", true, false), 
        ÇŽÕ("STREAM_VOLUME_MIC", 32, "STREAM_VOLUME_MIC", 32, "STREAM_VOLUME_MIC", 32, "options.stream.micVolumne", true, false), 
        É("STREAM_VOLUME_SYSTEM", 33, "STREAM_VOLUME_SYSTEM", 33, "STREAM_VOLUME_SYSTEM", 33, "options.stream.systemVolume", true, false), 
        áƒ("STREAM_KBPS", 34, "STREAM_KBPS", 34, "STREAM_KBPS", 34, "options.stream.kbps", true, false), 
        á€("STREAM_FPS", 35, "STREAM_FPS", 35, "STREAM_FPS", 35, "options.stream.fps", true, false), 
        Õ("STREAM_COMPRESSION", 36, "STREAM_COMPRESSION", 36, "STREAM_COMPRESSION", 36, "options.stream.compression", false, false), 
        à¢("STREAM_SEND_METADATA", 37, "STREAM_SEND_METADATA", 37, "STREAM_SEND_METADATA", 37, "options.stream.sendMetadata", false, true), 
        ŠÂµà("STREAM_CHAT_ENABLED", 38, "STREAM_CHAT_ENABLED", 38, "STREAM_CHAT_ENABLED", 38, "options.stream.chat.enabled", false, false), 
        ¥à("STREAM_CHAT_USER_FILTER", 39, "STREAM_CHAT_USER_FILTER", 39, "STREAM_CHAT_USER_FILTER", 39, "options.stream.chat.userFilter", false, false), 
        Âµà("STREAM_MIC_TOGGLE_BEHAVIOR", 40, "STREAM_MIC_TOGGLE_BEHAVIOR", 40, "STREAM_MIC_TOGGLE_BEHAVIOR", 40, "options.stream.micToggleBehavior", false, false), 
        Ç("BLOCK_ALTERNATIVES", 41, "BLOCK_ALTERNATIVES", 41, "BLOCK_ALTERNATIVES", 41, "options.blockAlternatives", false, true), 
        È("REDUCED_DEBUG_INFO", 42, "REDUCED_DEBUG_INFO", 42, "REDUCED_DEBUG_INFO", 42, "options.reducedDebugInfo", false, true), 
        áŠ("FOG_FANCY", 43, "FOG_FANCY", 43, "FOG", 999, "Fog", false, false), 
        ˆáŠ("FOG_START", 44, "FOG_START", 44, "", 999, "Fog Start", false, false), 
        áŒŠ("MIPMAP_TYPE", 45, "MIPMAP_TYPE", 45, "", 999, "Mipmap Type", false, false), 
        £ÂµÄ("LOAD_FAR", 46, "LOAD_FAR", 46, "", 999, "Load Far", false, false), 
        Ø­Âµ("PRELOADED_CHUNKS", 47, "PRELOADED_CHUNKS", 47, "", 999, "Preloaded Chunks", false, false), 
        Ä("CLOUDS", 48, "CLOUDS", 48, "", 999, "Clouds", false, false), 
        Ñ¢Â("CLOUD_HEIGHT", 49, "CLOUD_HEIGHT", 49, "", 999, "Cloud Height", true, false), 
        Ï­à("TREES", 50, "TREES", 50, "", 999, "Trees", false, false), 
        áˆºáˆºÈ("RAIN", 51, "RAIN", 51, "", 999, "Rain & Snow", false, false), 
        ÇŽá€("ANIMATED_WATER", 52, "ANIMATED_WATER", 52, "", 999, "Water Animated", false, false), 
        Ï("ANIMATED_LAVA", 53, "ANIMATED_LAVA", 53, "", 999, "Lava Animated", false, false), 
        Ô("ANIMATED_FIRE", 54, "ANIMATED_FIRE", 54, "", 999, "Fire Animated", false, false), 
        ÇªÓ("ANIMATED_PORTAL", 55, "ANIMATED_PORTAL", 55, "", 999, "Portal Animated", false, false), 
        áˆºÏ("AO_LEVEL", 56, "AO_LEVEL", 56, "", 999, "Smooth Lighting Level", true, false), 
        ˆáƒ("LAGOMETER", 57, "LAGOMETER", 57, "", 999, "Lagometer", false, false), 
        Œ("AUTOSAVE_TICKS", 58, "AUTOSAVE_TICKS", 58, "", 999, "Autosave", false, false), 
        £Ï("BETTER_GRASS", 59, "BETTER_GRASS", 59, "", 999, "Better Grass", false, false), 
        Ø­á("ANIMATED_REDSTONE", 60, "ANIMATED_REDSTONE", 60, "", 999, "Redstone Animated", false, false), 
        ˆÉ("ANIMATED_EXPLOSION", 61, "ANIMATED_EXPLOSION", 61, "", 999, "Explosion Animated", false, false), 
        Ï­Ï­Ï("ANIMATED_FLAME", 62, "ANIMATED_FLAME", 62, "", 999, "Flame Animated", false, false), 
        £Â("ANIMATED_SMOKE", 63, "ANIMATED_SMOKE", 63, "", 999, "Smoke Animated", false, false), 
        £Ó("WEATHER", 64, "WEATHER", 64, "", 999, "Weather", false, false), 
        ˆÐƒØ­à("SKY", 65, "SKY", 65, "", 999, "Sky", false, false), 
        £Õ("STARS", 66, "STARS", 66, "", 999, "Stars", false, false), 
        Ï­Ô("SUN_MOON", 67, "SUN_MOON", 67, "", 999, "Sun & Moon", false, false), 
        Œà("CHUNK_UPDATES", 68, "CHUNK_UPDATES", 68, "", 999, "Chunk Updates", false, false), 
        Ðƒá("CHUNK_UPDATES_DYNAMIC", 69, "CHUNK_UPDATES_DYNAMIC", 69, "", 999, "Dynamic Updates", false, false), 
        ˆÏ("TIME", 70, "TIME", 70, "", 999, "Time", false, false), 
        áˆºÇŽØ("CLEAR_WATER", 71, "CLEAR_WATER", 71, "", 999, "Clear Water", false, false), 
        ÇªÂµÕ("SMOOTH_WORLD", 72, "SMOOTH_WORLD", 72, "", 999, "Smooth World", false, false), 
        áŒŠÏ("VOID_PARTICLES", 73, "VOID_PARTICLES", 73, "", 999, "Void Particles", false, false), 
        áŒŠáŠ("WATER_PARTICLES", 74, "WATER_PARTICLES", 74, "", 999, "Water Particles", false, false), 
        ˆÓ("RAIN_SPLASH", 75, "RAIN_SPLASH", 75, "", 999, "Rain Splash", false, false), 
        ¥Ä("PORTAL_PARTICLES", 76, "PORTAL_PARTICLES", 76, "", 999, "Portal Particles", false, false), 
        ÇªÔ("POTION_PARTICLES", 77, "POTION_PARTICLES", 77, "", 999, "Potion Particles", false, false), 
        Û("PROFILER", 78, "PROFILER", 78, "", 999, "Debug Profiler", false, false), 
        ŠÓ("DRIPPING_WATER_LAVA", 79, "DRIPPING_WATER_LAVA", 79, "", 999, "Dripping Water/Lava", false, false), 
        ÇŽá("BETTER_SNOW", 80, "BETTER_SNOW", 80, "", 999, "Better Snow", false, false), 
        Ñ¢à("FULLSCREEN_MODE", 81, "FULLSCREEN_MODE", 81, "", 999, "Fullscreen Mode", false, false), 
        ÇªØ­("ANIMATED_TERRAIN", 82, "ANIMATED_TERRAIN", 82, "", 999, "Terrain Animated", false, false), 
        £áŒŠá("SWAMP_COLORS", 83, "SWAMP_COLORS", 83, "", 999, "Swamp Colors", false, false), 
        áˆº("RANDOM_MOBS", 84, "RANDOM_MOBS", 84, "", 999, "Random Mobs", false, false), 
        Šà("SMOOTH_BIOMES", 85, "SMOOTH_BIOMES", 85, "", 999, "Smooth Biomes", false, false), 
        áŒŠá€("CUSTOM_FONTS", 86, "CUSTOM_FONTS", 86, "", 999, "Custom Fonts", false, false), 
        ¥Ï("CUSTOM_COLORS", 87, "CUSTOM_COLORS", 87, "", 999, "Custom Colors", false, false), 
        ˆà¢("SHOW_CAPES", 88, "SHOW_CAPES", 88, "", 999, "Show Capes", false, false), 
        Ñ¢Ç("CONNECTED_TEXTURES", 89, "CONNECTED_TEXTURES", 89, "", 999, "Connected Textures", false, false), 
        £É("AA_LEVEL", 90, "AA_LEVEL", 90, "", 999, "Antialiasing", false, false), 
        Ðƒáƒ("AF_LEVEL", 91, "AF_LEVEL", 91, "", 999, "Anisotropic Filtering", false, false), 
        Ðƒà("ANIMATED_TEXTURES", 92, "ANIMATED_TEXTURES", 92, "", 999, "Textures Animated", false, false), 
        ¥É("NATURAL_TEXTURES", 93, "NATURAL_TEXTURES", 93, "", 999, "Natural Textures", false, false), 
        £ÇªÓ("CHUNK_LOADING", 94, "CHUNK_LOADING", 94, "", 999, "Chunk Loading", false, false), 
        ÂµÕ("HELD_ITEM_TOOLTIPS", 95, "HELD_ITEM_TOOLTIPS", 95, "", 999, "Held Item Tooltips", false, false), 
        Š("DROPPED_ITEMS", 96, "DROPPED_ITEMS", 96, "", 999, "Dropped Items", false, false), 
        Ø­Ñ¢á€("LAZY_CHUNK_LOADING", 97, "LAZY_CHUNK_LOADING", 97, "", 999, "Lazy Chunk Loading", false, false), 
        Ñ¢Ó("CUSTOM_SKY", 98, "CUSTOM_SKY", 98, "", 999, "Custom Sky", false, false), 
        Ø­Æ("FAST_MATH", 99, "FAST_MATH", 99, "", 999, "Fast Math", false, false), 
        áŒŠÔ("FAST_RENDER", 100, "FAST_RENDER", 100, "", 999, "Fast Render", false, false), 
        ŠÕ("TRANSLUCENT_BLOCKS", 101, "TRANSLUCENT_BLOCKS", 101, "", 999, "Translucent Blocks", false, false);
        
        private final boolean £Ø­à;
        private final boolean µÐƒáƒ;
        private final String áŒŠÕ;
        private final float ÂµÂ;
        private float áŒŠá;
        private float ˆØ;
        private static final HorizonCode_Horizon_È[] áˆºà;
        private static final String ÐƒÂ = "CL_00000653";
        private static final HorizonCode_Horizon_È[] £áƒ;
        
        static {
            Ï­áˆºÓ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ, HorizonCode_Horizon_È.Šáƒ, HorizonCode_Horizon_È.Ï­Ðƒà, HorizonCode_Horizon_È.áŒŠà, HorizonCode_Horizon_È.ŠÄ, HorizonCode_Horizon_È.Ñ¢á, HorizonCode_Horizon_È.ŒÏ, HorizonCode_Horizon_È.Çªà¢, HorizonCode_Horizon_È.Ê, HorizonCode_Horizon_È.ÇŽÉ, HorizonCode_Horizon_È.ˆá, HorizonCode_Horizon_È.ÇŽÕ, HorizonCode_Horizon_È.É, HorizonCode_Horizon_È.áƒ, HorizonCode_Horizon_È.á€, HorizonCode_Horizon_È.Õ, HorizonCode_Horizon_È.à¢, HorizonCode_Horizon_È.ŠÂµà, HorizonCode_Horizon_È.¥à, HorizonCode_Horizon_È.Âµà, HorizonCode_Horizon_È.Ç, HorizonCode_Horizon_È.È, HorizonCode_Horizon_È.áŠ, HorizonCode_Horizon_È.ˆáŠ, HorizonCode_Horizon_È.áŒŠ, HorizonCode_Horizon_È.£ÂµÄ, HorizonCode_Horizon_È.Ø­Âµ, HorizonCode_Horizon_È.Ä, HorizonCode_Horizon_È.Ñ¢Â, HorizonCode_Horizon_È.Ï­à, HorizonCode_Horizon_È.áˆºáˆºÈ, HorizonCode_Horizon_È.ÇŽá€, HorizonCode_Horizon_È.Ï, HorizonCode_Horizon_È.Ô, HorizonCode_Horizon_È.ÇªÓ, HorizonCode_Horizon_È.áˆºÏ, HorizonCode_Horizon_È.ˆáƒ, HorizonCode_Horizon_È.Œ, HorizonCode_Horizon_È.£Ï, HorizonCode_Horizon_È.Ø­á, HorizonCode_Horizon_È.ˆÉ, HorizonCode_Horizon_È.Ï­Ï­Ï, HorizonCode_Horizon_È.£Â, HorizonCode_Horizon_È.£Ó, HorizonCode_Horizon_È.ˆÐƒØ­à, HorizonCode_Horizon_È.£Õ, HorizonCode_Horizon_È.Ï­Ô, HorizonCode_Horizon_È.Œà, HorizonCode_Horizon_È.Ðƒá, HorizonCode_Horizon_È.ˆÏ, HorizonCode_Horizon_È.áˆºÇŽØ, HorizonCode_Horizon_È.ÇªÂµÕ, HorizonCode_Horizon_È.áŒŠÏ, HorizonCode_Horizon_È.áŒŠáŠ, HorizonCode_Horizon_È.ˆÓ, HorizonCode_Horizon_È.¥Ä, HorizonCode_Horizon_È.ÇªÔ, HorizonCode_Horizon_È.Û, HorizonCode_Horizon_È.ŠÓ, HorizonCode_Horizon_È.ÇŽá, HorizonCode_Horizon_È.Ñ¢à, HorizonCode_Horizon_È.ÇªØ­, HorizonCode_Horizon_È.£áŒŠá, HorizonCode_Horizon_È.áˆº, HorizonCode_Horizon_È.Šà, HorizonCode_Horizon_È.áŒŠá€, HorizonCode_Horizon_È.¥Ï, HorizonCode_Horizon_È.ˆà¢, HorizonCode_Horizon_È.Ñ¢Ç, HorizonCode_Horizon_È.£É, HorizonCode_Horizon_È.Ðƒáƒ, HorizonCode_Horizon_È.Ðƒà, HorizonCode_Horizon_È.¥É, HorizonCode_Horizon_È.£ÇªÓ, HorizonCode_Horizon_È.ÂµÕ, HorizonCode_Horizon_È.Š, HorizonCode_Horizon_È.Ø­Ñ¢á€, HorizonCode_Horizon_È.Ñ¢Ó, HorizonCode_Horizon_È.Ø­Æ, HorizonCode_Horizon_È.áŒŠÔ, HorizonCode_Horizon_È.ŠÕ };
            áˆºà = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ, HorizonCode_Horizon_È.Šáƒ, HorizonCode_Horizon_È.Ï­Ðƒà, HorizonCode_Horizon_È.áŒŠà, HorizonCode_Horizon_È.ŠÄ, HorizonCode_Horizon_È.Ñ¢á, HorizonCode_Horizon_È.ŒÏ, HorizonCode_Horizon_È.Çªà¢, HorizonCode_Horizon_È.Ê, HorizonCode_Horizon_È.ÇŽÉ, HorizonCode_Horizon_È.ˆá, HorizonCode_Horizon_È.ÇŽÕ, HorizonCode_Horizon_È.É, HorizonCode_Horizon_È.áƒ, HorizonCode_Horizon_È.á€, HorizonCode_Horizon_È.Õ, HorizonCode_Horizon_È.à¢, HorizonCode_Horizon_È.ŠÂµà, HorizonCode_Horizon_È.¥à, HorizonCode_Horizon_È.Âµà, HorizonCode_Horizon_È.Ç, HorizonCode_Horizon_È.È };
            £áƒ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ, HorizonCode_Horizon_È.Šáƒ, HorizonCode_Horizon_È.Ï­Ðƒà, HorizonCode_Horizon_È.áŒŠà, HorizonCode_Horizon_È.ŠÄ, HorizonCode_Horizon_È.Ñ¢á, HorizonCode_Horizon_È.ŒÏ, HorizonCode_Horizon_È.Çªà¢, HorizonCode_Horizon_È.Ê, HorizonCode_Horizon_È.ÇŽÉ, HorizonCode_Horizon_È.ˆá, HorizonCode_Horizon_È.ÇŽÕ, HorizonCode_Horizon_È.É, HorizonCode_Horizon_È.áƒ, HorizonCode_Horizon_È.á€, HorizonCode_Horizon_È.Õ, HorizonCode_Horizon_È.à¢, HorizonCode_Horizon_È.ŠÂµà, HorizonCode_Horizon_È.¥à, HorizonCode_Horizon_È.Âµà, HorizonCode_Horizon_È.Ç, HorizonCode_Horizon_È.È, HorizonCode_Horizon_È.áŠ, HorizonCode_Horizon_È.ˆáŠ, HorizonCode_Horizon_È.áŒŠ, HorizonCode_Horizon_È.£ÂµÄ, HorizonCode_Horizon_È.Ø­Âµ, HorizonCode_Horizon_È.Ä, HorizonCode_Horizon_È.Ñ¢Â, HorizonCode_Horizon_È.Ï­à, HorizonCode_Horizon_È.áˆºáˆºÈ, HorizonCode_Horizon_È.ÇŽá€, HorizonCode_Horizon_È.Ï, HorizonCode_Horizon_È.Ô, HorizonCode_Horizon_È.ÇªÓ, HorizonCode_Horizon_È.áˆºÏ, HorizonCode_Horizon_È.ˆáƒ, HorizonCode_Horizon_È.Œ, HorizonCode_Horizon_È.£Ï, HorizonCode_Horizon_È.Ø­á, HorizonCode_Horizon_È.ˆÉ, HorizonCode_Horizon_È.Ï­Ï­Ï, HorizonCode_Horizon_È.£Â, HorizonCode_Horizon_È.£Ó, HorizonCode_Horizon_È.ˆÐƒØ­à, HorizonCode_Horizon_È.£Õ, HorizonCode_Horizon_È.Ï­Ô, HorizonCode_Horizon_È.Œà, HorizonCode_Horizon_È.Ðƒá, HorizonCode_Horizon_È.ˆÏ, HorizonCode_Horizon_È.áˆºÇŽØ, HorizonCode_Horizon_È.ÇªÂµÕ, HorizonCode_Horizon_È.áŒŠÏ, HorizonCode_Horizon_È.áŒŠáŠ, HorizonCode_Horizon_È.ˆÓ, HorizonCode_Horizon_È.¥Ä, HorizonCode_Horizon_È.ÇªÔ, HorizonCode_Horizon_È.Û, HorizonCode_Horizon_È.ŠÓ, HorizonCode_Horizon_È.ÇŽá, HorizonCode_Horizon_È.Ñ¢à, HorizonCode_Horizon_È.ÇªØ­, HorizonCode_Horizon_È.£áŒŠá, HorizonCode_Horizon_È.áˆº, HorizonCode_Horizon_È.Šà, HorizonCode_Horizon_È.áŒŠá€, HorizonCode_Horizon_È.¥Ï, HorizonCode_Horizon_È.ˆà¢, HorizonCode_Horizon_È.Ñ¢Ç, HorizonCode_Horizon_È.£É, HorizonCode_Horizon_È.Ðƒáƒ, HorizonCode_Horizon_È.Ðƒà, HorizonCode_Horizon_È.¥É, HorizonCode_Horizon_È.£ÇªÓ, HorizonCode_Horizon_È.ÂµÕ, HorizonCode_Horizon_È.Š, HorizonCode_Horizon_È.Ø­Ñ¢á€, HorizonCode_Horizon_È.Ñ¢Ó, HorizonCode_Horizon_È.Ø­Æ, HorizonCode_Horizon_È.áŒŠÔ, HorizonCode_Horizon_È.ŠÕ };
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final int p_74379_0_) {
            for (final HorizonCode_Horizon_È var4 : values()) {
                if (var4.Ý() == p_74379_0_) {
                    return var4;
                }
            }
            return null;
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i46381_1_, final int p_i46381_2_, final String p_i1015_1_, final int p_i1015_2_, final String p_i1015_3_, final boolean p_i1015_4_, final boolean p_i1015_5_) {
            this(s, n, p_i46381_1_, p_i46381_2_, p_i1015_1_, p_i1015_2_, p_i1015_3_, p_i1015_4_, p_i1015_5_, 0.0f, 1.0f, 0.0f);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i46382_1_, final int p_i46382_2_, final String p_i45004_1_, final int p_i45004_2_, final String p_i45004_3_, final boolean p_i45004_4_, final boolean p_i45004_5_, final float p_i45004_6_, final float p_i45004_7_, final float p_i45004_8_) {
            this.áŒŠÕ = p_i45004_3_;
            this.£Ø­à = p_i45004_4_;
            this.µÐƒáƒ = p_i45004_5_;
            this.áŒŠá = p_i45004_6_;
            this.ˆØ = p_i45004_7_;
            this.ÂµÂ = p_i45004_8_;
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.£Ø­à;
        }
        
        public boolean Â() {
            return this.µÐƒáƒ;
        }
        
        public int Ý() {
            return this.ordinal();
        }
        
        public String Ø­áŒŠá() {
            return this.áŒŠÕ;
        }
        
        public float Âµá€() {
            return this.ˆØ;
        }
        
        public void HorizonCode_Horizon_È(final float p_148263_1_) {
            this.ˆØ = p_148263_1_;
        }
        
        public float Â(final float p_148266_1_) {
            return MathHelper.HorizonCode_Horizon_È((this.Ø­áŒŠá(p_148266_1_) - this.áŒŠá) / (this.ˆØ - this.áŒŠá), 0.0f, 1.0f);
        }
        
        public float Ý(final float p_148262_1_) {
            return this.Ø­áŒŠá(this.áŒŠá + (this.ˆØ - this.áŒŠá) * MathHelper.HorizonCode_Horizon_È(p_148262_1_, 0.0f, 1.0f));
        }
        
        public float Ø­áŒŠá(float p_148268_1_) {
            p_148268_1_ = this.Âµá€(p_148268_1_);
            return MathHelper.HorizonCode_Horizon_È(p_148268_1_, this.áŒŠá, this.ˆØ);
        }
        
        protected float Âµá€(float p_148264_1_) {
            if (this.ÂµÂ > 0.0f) {
                p_148264_1_ = this.ÂµÂ * Math.round(p_148264_1_ / this.ÂµÂ);
            }
            return p_148264_1_;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00000652";
        
        static {
            HorizonCode_Horizon_È = new int[GameSettings.HorizonCode_Horizon_È.values().length];
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.à.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.Ø.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.áˆºÑ¢Õ.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.ÂµÈ.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.µà.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.ˆà.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.Ø­à.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.µÕ.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.Æ.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.Šáƒ.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.Ï­Ðƒà.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.áŒŠà.ordinal()] = 13;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.à¢.ordinal()] = 14;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.ÇŽÉ.ordinal()] = 15;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.Ç.ordinal()] = 16;
            }
            catch (NoSuchFieldError noSuchFieldError16) {}
            try {
                GameSettings.Â.HorizonCode_Horizon_È[GameSettings.HorizonCode_Horizon_È.È.ordinal()] = 17;
            }
            catch (NoSuchFieldError noSuchFieldError17) {}
        }
    }
}

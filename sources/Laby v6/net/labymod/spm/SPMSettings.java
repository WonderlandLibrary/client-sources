package net.labymod.spm;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.world.EnumDifficulty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SPMSettings
{
    private static final Logger logger = LogManager.getLogger();
    private static final Gson gson = new Gson();
    private static final ParameterizedType typeListString = new ParameterizedType()
    {
        public Type[] getActualTypeArguments()
        {
            return new Type[] {String.class};
        }
        public Type getRawType()
        {
            return List.class;
        }
        public Type getOwnerType()
        {
            return null;
        }
    };
    public float mouseSensitivity = 0.5F;
    public boolean invertMouse;
    public int renderDistanceChunks = -1;
    public boolean viewBobbing = true;
    public boolean anaglyph;
    public boolean fboEnable = true;
    public int limitFramerate = 120;
    public int clouds = 2;
    public boolean fancyGraphics = true;
    public int ambientOcclusion = 2;
    public List<String> resourcePacks = Lists.<String>newArrayList();
    public List<String> field_183018_l = Lists.<String>newArrayList();
    public EntityPlayer.EnumChatVisibility chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
    public boolean chatColours = true;
    public boolean chatLinks = true;
    public boolean chatLinksPrompt = true;
    public float chatOpacity = 1.0F;
    public boolean snooperEnabled = true;
    public boolean fullScreen;
    public boolean enableVsync = true;
    public boolean useVbo = false;
    public boolean allowBlockAlternatives = true;
    public boolean reducedDebugInfo = false;
    public boolean hideServerAddress;
    public boolean advancedItemTooltips;
    public boolean pauseOnLostFocus = true;
    private final Set<EnumPlayerModelParts> setModelParts = Sets.newHashSet(EnumPlayerModelParts.values());
    public boolean touchscreen;
    public int overrideWidth;
    public int overrideHeight;
    public boolean heldItemTooltips = true;
    public float chatScale = 1.0F;
    public float chatWidth = 1.0F;
    public float chatHeightUnfocused = 0.44366196F;
    public float chatHeightFocused = 1.0F;
    public boolean showInventoryAchievementHint = true;
    public int mipmapLevels = 4;
    private Map<SoundCategory, Float> mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
    public float streamBytesPerPixel = 0.5F;
    public float streamMicVolume = 1.0F;
    public float streamGameVolume = 1.0F;
    public float streamKbps = 0.5412844F;
    public float streamFps = 0.31690142F;
    public int streamCompression = 1;
    public boolean streamSendMetadata = true;
    public String streamPreferredServer = "";
    public int streamChatEnabled = 0;
    public int streamChatUserFilter = 0;
    public int streamMicToggleBehavior = 0;
    public boolean field_181150_U = true;
    public boolean field_181151_V = true;
    public SPMBinding keyBindForward = new SPMBinding("key.forward", 17, "key.categories.movement");
    public SPMBinding keyBindLeft = new SPMBinding("key.left", 30, "key.categories.movement");
    public SPMBinding keyBindBack = new SPMBinding("key.back", 31, "key.categories.movement");
    public SPMBinding keyBindRight = new SPMBinding("key.right", 32, "key.categories.movement");
    public SPMBinding keyBindJump = new SPMBinding("key.jump", 57, "key.categories.movement");
    public SPMBinding keyBindSneak = new SPMBinding("key.sneak", 42, "key.categories.movement");
    public SPMBinding keyBindSprint = new SPMBinding("key.sprint", 29, "key.categories.movement");
    public SPMBinding keyBindInventory = new SPMBinding("key.inventory", 18, "key.categories.inventory");
    public SPMBinding keyBindUseItem = new SPMBinding("key.use", -99, "key.categories.gameplay");
    public SPMBinding keyBindDrop = new SPMBinding("key.drop", 16, "key.categories.gameplay");
    public SPMBinding keyBindAttack = new SPMBinding("key.attack", -100, "key.categories.gameplay");
    public SPMBinding keyBindPickBlock = new SPMBinding("key.pickItem", -98, "key.categories.gameplay");
    public SPMBinding keyBindChat = new SPMBinding("key.chat", 20, "key.categories.multiplayer");
    public SPMBinding keyBindPlayerList = new SPMBinding("key.playerlist", 15, "key.categories.multiplayer");
    public SPMBinding keyBindCommand = new SPMBinding("key.command", 53, "key.categories.multiplayer");
    public SPMBinding keyBindScreenshot = new SPMBinding("key.screenshot", 60, "key.categories.misc");
    public SPMBinding keyBindTogglePerspective = new SPMBinding("key.togglePerspective", 63, "key.categories.misc");
    public SPMBinding keyBindSmoothCamera = new SPMBinding("key.smoothCamera", 0, "key.categories.misc");
    public SPMBinding keyBindFullscreen = new SPMBinding("key.fullscreen", 87, "key.categories.misc");
    public SPMBinding keyBindSpectatorOutlines = new SPMBinding("key.spectatorOutlines", 0, "key.categories.misc");
    public SPMBinding keyBindStreamStartStop = new SPMBinding("key.streamStartStop", 64, "key.categories.stream");
    public SPMBinding keyBindStreamPauseUnpause = new SPMBinding("key.streamPauseUnpause", 65, "key.categories.stream");
    public SPMBinding keyBindStreamCommercials = new SPMBinding("key.streamCommercial", 0, "key.categories.stream");
    public SPMBinding keyBindStreamToggleMic = new SPMBinding("key.streamToggleMic", 0, "key.categories.stream");
    public SPMBinding[] keyBindsHotbar = new SPMBinding[] {new SPMBinding("key.hotbar.1", 2, "key.categories.inventory"), new SPMBinding("key.hotbar.2", 3, "key.categories.inventory"), new SPMBinding("key.hotbar.3", 4, "key.categories.inventory"), new SPMBinding("key.hotbar.4", 5, "key.categories.inventory"), new SPMBinding("key.hotbar.5", 6, "key.categories.inventory"), new SPMBinding("key.hotbar.6", 7, "key.categories.inventory"), new SPMBinding("key.hotbar.7", 8, "key.categories.inventory"), new SPMBinding("key.hotbar.8", 9, "key.categories.inventory"), new SPMBinding("key.hotbar.9", 10, "key.categories.inventory")};
    public SPMBinding[] keyBindings;
    private File optionsFile;
    public EnumDifficulty difficulty;
    public boolean hideGUI;
    public int thirdPersonView;
    public boolean showDebugInfo;
    public boolean showDebugProfilerChart;
    public boolean field_181657_aC;
    public String lastServer;
    public boolean smoothCamera;
    public boolean debugCamEnable;
    public float fovSetting;
    public float gammaSetting;
    public float saturation;
    public int guiScale;
    public int particleSetting;
    public String language;
    public boolean forceUnicodeFont;

    public SPMSettings(File file)
    {
        this.keyBindings = (SPMBinding[])((SPMBinding[])ArrayUtils.addAll(new SPMBinding[] {this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.keyBindSpectatorOutlines}, this.keyBindsHotbar));
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = "";
        this.fovSetting = 70.0F;
        this.language = "en_US";
        this.forceUnicodeFont = false;
        this.optionsFile = file;
        this.renderDistanceChunks = 8;
        this.loadOptions();
    }

    public void loadOptions()
    {
        try
        {
            if (!this.optionsFile.exists())
            {
                return;
            }

            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.optionsFile));
            String s = "";
            this.mapSoundLevels.clear();

            while ((s = bufferedreader.readLine()) != null)
            {
                try
                {
                    String[] astring = s.split(":");

                    if (astring[0].equals("mouseSensitivity"))
                    {
                        this.mouseSensitivity = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("fov"))
                    {
                        this.fovSetting = this.parseFloat(astring[1]) * 40.0F + 70.0F;
                    }

                    if (astring[0].equals("gamma"))
                    {
                        this.gammaSetting = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("saturation"))
                    {
                        this.saturation = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("invertYMouse"))
                    {
                        this.invertMouse = astring[1].equals("true");
                    }

                    if (astring[0].equals("renderDistance"))
                    {
                        this.renderDistanceChunks = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("guiScale"))
                    {
                        this.guiScale = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("particles"))
                    {
                        this.particleSetting = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("bobView"))
                    {
                        this.viewBobbing = astring[1].equals("true");
                    }

                    if (astring[0].equals("anaglyph3d"))
                    {
                        this.anaglyph = astring[1].equals("true");
                    }

                    if (astring[0].equals("maxFps"))
                    {
                        this.limitFramerate = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("fboEnable"))
                    {
                        this.fboEnable = astring[1].equals("true");
                    }

                    if (astring[0].equals("difficulty"))
                    {
                        this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(astring[1]));
                    }

                    if (astring[0].equals("fancyGraphics"))
                    {
                        this.fancyGraphics = astring[1].equals("true");
                    }

                    if (astring[0].equals("ao"))
                    {
                        if (astring[1].equals("true"))
                        {
                            this.ambientOcclusion = 2;
                        }
                        else if (astring[1].equals("false"))
                        {
                            this.ambientOcclusion = 0;
                        }
                        else
                        {
                            this.ambientOcclusion = Integer.parseInt(astring[1]);
                        }
                    }

                    if (astring[0].equals("renderClouds"))
                    {
                        if (astring[1].equals("true"))
                        {
                            this.clouds = 2;
                        }
                        else if (astring[1].equals("false"))
                        {
                            this.clouds = 0;
                        }
                        else if (astring[1].equals("fast"))
                        {
                            this.clouds = 1;
                        }
                    }

                    if (astring[0].equals("resourcePacks"))
                    {
                        this.resourcePacks = (List)gson.fromJson((String)s.substring(s.indexOf(58) + 1), typeListString);

                        if (this.resourcePacks == null)
                        {
                            this.resourcePacks = Lists.<String>newArrayList();
                        }
                    }

                    if (astring[0].equals("incompatibleResourcePacks"))
                    {
                        this.field_183018_l = (List)gson.fromJson((String)s.substring(s.indexOf(58) + 1), typeListString);

                        if (this.field_183018_l == null)
                        {
                            this.field_183018_l = Lists.<String>newArrayList();
                        }
                    }

                    if (astring[0].equals("lastServer") && astring.length >= 2)
                    {
                        this.lastServer = s.substring(s.indexOf(58) + 1);
                    }

                    if (astring[0].equals("lang") && astring.length >= 2)
                    {
                        this.language = astring[1];
                    }

                    if (astring[0].equals("chatVisibility"))
                    {
                        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(astring[1]));
                    }

                    if (astring[0].equals("chatColors"))
                    {
                        this.chatColours = astring[1].equals("true");
                    }

                    if (astring[0].equals("chatLinks"))
                    {
                        this.chatLinks = astring[1].equals("true");
                    }

                    if (astring[0].equals("chatLinksPrompt"))
                    {
                        this.chatLinksPrompt = astring[1].equals("true");
                    }

                    if (astring[0].equals("chatOpacity"))
                    {
                        this.chatOpacity = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("snooperEnabled"))
                    {
                        this.snooperEnabled = astring[1].equals("true");
                    }

                    if (astring[0].equals("fullscreen"))
                    {
                        this.fullScreen = astring[1].equals("true");
                    }

                    if (astring[0].equals("enableVsync"))
                    {
                        this.enableVsync = astring[1].equals("true");
                    }

                    if (astring[0].equals("useVbo"))
                    {
                        this.useVbo = astring[1].equals("true");
                    }

                    if (astring[0].equals("hideServerAddress"))
                    {
                        this.hideServerAddress = astring[1].equals("true");
                    }

                    if (astring[0].equals("advancedItemTooltips"))
                    {
                        this.advancedItemTooltips = astring[1].equals("true");
                    }

                    if (astring[0].equals("pauseOnLostFocus"))
                    {
                        this.pauseOnLostFocus = astring[1].equals("true");
                    }

                    if (astring[0].equals("touchscreen"))
                    {
                        this.touchscreen = astring[1].equals("true");
                    }

                    if (astring[0].equals("overrideHeight"))
                    {
                        this.overrideHeight = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("overrideWidth"))
                    {
                        this.overrideWidth = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("heldItemTooltips"))
                    {
                        this.heldItemTooltips = astring[1].equals("true");
                    }

                    if (astring[0].equals("chatHeightFocused"))
                    {
                        this.chatHeightFocused = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("chatHeightUnfocused"))
                    {
                        this.chatHeightUnfocused = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("chatScale"))
                    {
                        this.chatScale = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("chatWidth"))
                    {
                        this.chatWidth = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("showInventoryAchievementHint"))
                    {
                        this.showInventoryAchievementHint = astring[1].equals("true");
                    }

                    if (astring[0].equals("mipmapLevels"))
                    {
                        this.mipmapLevels = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("streamBytesPerPixel"))
                    {
                        this.streamBytesPerPixel = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("streamMicVolume"))
                    {
                        this.streamMicVolume = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("streamSystemVolume"))
                    {
                        this.streamGameVolume = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("streamKbps"))
                    {
                        this.streamKbps = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("streamFps"))
                    {
                        this.streamFps = this.parseFloat(astring[1]);
                    }

                    if (astring[0].equals("streamCompression"))
                    {
                        this.streamCompression = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("streamSendMetadata"))
                    {
                        this.streamSendMetadata = astring[1].equals("true");
                    }

                    if (astring[0].equals("streamPreferredServer") && astring.length >= 2)
                    {
                        this.streamPreferredServer = s.substring(s.indexOf(58) + 1);
                    }

                    if (astring[0].equals("streamChatEnabled"))
                    {
                        this.streamChatEnabled = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("streamChatUserFilter"))
                    {
                        this.streamChatUserFilter = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("streamMicToggleBehavior"))
                    {
                        this.streamMicToggleBehavior = Integer.parseInt(astring[1]);
                    }

                    if (astring[0].equals("forceUnicodeFont"))
                    {
                        this.forceUnicodeFont = astring[1].equals("true");
                    }

                    if (astring[0].equals("allowBlockAlternatives"))
                    {
                        this.allowBlockAlternatives = astring[1].equals("true");
                    }

                    if (astring[0].equals("reducedDebugInfo"))
                    {
                        this.reducedDebugInfo = astring[1].equals("true");
                    }

                    if (astring[0].equals("useNativeTransport"))
                    {
                        this.field_181150_U = astring[1].equals("true");
                    }

                    if (astring[0].equals("entityShadows"))
                    {
                        this.field_181151_V = astring[1].equals("true");
                    }

                    for (SPMBinding spmbinding : this.keyBindings)
                    {
                        if (astring[0].equals("key_" + spmbinding.getKeyDescription()))
                        {
                            spmbinding.setKeyCode(Integer.parseInt(astring[1]));
                        }
                    }

                    for (SoundCategory soundcategory : SoundCategory.values())
                    {
                        if (astring[0].equals("soundCategory_" + soundcategory.getCategoryName()))
                        {
                            this.mapSoundLevels.put(soundcategory, Float.valueOf(this.parseFloat(astring[1])));
                        }
                    }

                    for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values())
                    {
                        if (astring[0].equals("modelPart_" + enumplayermodelparts.getPartName()))
                        {
                            ;
                        }
                    }
                }
                catch (Exception var8)
                {
                    logger.warn("Skipping bad option: " + s);
                }
            }

            bufferedreader.close();
        }
        catch (Exception exception)
        {
            logger.error((String)"Failed to load options", (Throwable)exception);
        }
    }

    private float parseFloat(String p_74305_1_)
    {
        return p_74305_1_.equals("true") ? 1.0F : (p_74305_1_.equals("false") ? 0.0F : Float.parseFloat(p_74305_1_));
    }
}

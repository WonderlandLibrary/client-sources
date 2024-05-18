package net.minecraft.client.audio;

import net.minecraft.client.settings.*;
import com.google.common.collect.*;
import paulscode.sound.libraries.*;
import paulscode.sound.codecs.*;
import net.minecraft.entity.player.*;
import io.netty.util.internal.*;
import java.util.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import java.io.*;
import java.net.*;
import paulscode.sound.*;

public class SoundManager
{
    private static final Logger logger;
    private final SoundHandler sndHandler;
    private SoundSystemStarterThread sndSystem;
    private final Map<String, Integer> playingSoundsStopTime;
    private final Map<String, ISound> playingSounds;
    private final GameSettings options;
    private final Map<ISound, String> invPlayingSounds;
    private final Multimap<SoundCategory, String> categorySounds;
    private int playTime;
    private final Map<ISound, Integer> delayedSounds;
    private static final Marker LOG_MARKER;
    private static final String[] I;
    private boolean loaded;
    private final List<ITickableSound> tickableSounds;
    private Map<ISound, SoundPoolEntry> playingSoundPoolEntries;
    
    static void access$1(final SoundManager soundManager, final SoundSystemStarterThread sndSystem) {
        soundManager.sndSystem = sndSystem;
    }
    
    public boolean isSoundPlaying(final ISound sound) {
        if (!this.loaded) {
            return "".length() != 0;
        }
        final String s = this.invPlayingSounds.get(sound);
        int n;
        if (s == null) {
            n = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (!this.sndSystem.playing(s) && (!this.playingSoundsStopTime.containsKey(s) || this.playingSoundsStopTime.get(s) > this.playTime)) {
            n = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    private float getSoundCategoryVolume(final SoundCategory soundCategory) {
        float soundLevel;
        if (soundCategory != null && soundCategory != SoundCategory.MASTER) {
            soundLevel = this.options.getSoundLevel(soundCategory);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            soundLevel = 1.0f;
        }
        return soundLevel;
    }
    
    private float getNormalizedPitch(final ISound sound, final SoundPoolEntry soundPoolEntry) {
        return (float)MathHelper.clamp_double(sound.getPitch() * soundPoolEntry.getPitch(), 0.5, 2.0);
    }
    
    public SoundManager(final SoundHandler sndHandler, final GameSettings options) {
        this.playTime = "".length();
        this.playingSounds = (Map<String, ISound>)HashBiMap.create();
        this.invPlayingSounds = (Map<ISound, String>)((BiMap)this.playingSounds).inverse();
        this.playingSoundPoolEntries = (Map<ISound, SoundPoolEntry>)Maps.newHashMap();
        this.categorySounds = (Multimap<SoundCategory, String>)HashMultimap.create();
        this.tickableSounds = (List<ITickableSound>)Lists.newArrayList();
        this.delayedSounds = (Map<ISound, Integer>)Maps.newHashMap();
        this.playingSoundsStopTime = (Map<String, Integer>)Maps.newHashMap();
        this.sndHandler = sndHandler;
        this.options = options;
        try {
            SoundSystemConfig.addLibrary((Class)LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec(SoundManager.I[" ".length()], (Class)CodecJOrbis.class);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        catch (SoundSystemException ex) {
            SoundManager.logger.error(SoundManager.LOG_MARKER, SoundManager.I["  ".length()], (Throwable)ex);
        }
    }
    
    static void access$2(final SoundManager soundManager, final boolean loaded) {
        soundManager.loaded = loaded;
    }
    
    private static void I() {
        (I = new String[0x46 ^ 0x56])["".length()] = I("\u0017\"&:=\u0017", "Dmsty");
        SoundManager.I[" ".length()] = I("!\u0002\u0010", "NewJT");
        SoundManager.I["  ".length()] = I("\u001c'\u0013\u0002\ny9\b\u0003\u00130;\u0006M\u000f0!\tM\f10A!\u0011;'\u0000\u001f\u0001\u00134\u0017\f+6 \u000f\tX)9\u0014\nU0;", "YUamx");
        SoundManager.I["   ".length()] = I(":*\u001f>\u001cI\t\u00032\n\b7\u0013p4\u0006$\u000e5\n", "iEjPx");
        SoundManager.I[0xBF ^ 0xBB] = I("40&-\bQ1 #\b\u0005+:%Z\"-!,\u001e\";'6\u001f\u001clt\u0016\u000f\u0003,=,\u001dQ-2$Z\u0002-!,\u001e\u0002brb\u0017\u00041=!", "qBTBz");
        SoundManager.I[0x3C ^ 0x39] = I("??%&#\b>h*=\f4&,9M!5i7\b9)<&\bz!=r\u001ez&&!M*$(,\u00044/i4\u0003#%&'\b", "mZHIU");
        SoundManager.I[0x6D ^ 0x6B] = I("\u0004.\u0007 \u00162!N \n6<\u0007>\u0001w6\u0001%\b3\u0000\u00185\b#\u007fN+\u001b{e\u00031\u0015# \u001cp\u00108)\u001b=\u0003w2\u000f#F- \u001c?", "WEnPf");
        SoundManager.I[0x67 ^ 0x60] = I("3\u0006\u0014\u0018+\u0003H\u0001\u0015g\u0016\u0004\u0014\u0003g\u0013\u0006\u001e\u0014(\u0011\u0006U\t(\u0013\u0006\u0011?1\u0003\u0006\u0001@g\u001d\u0015", "fhuzG");
        SoundManager.I[0xCB ^ 0xC3] = I(",\u000544!\u001cK!9m\t\u00074/m\u001c\u0006%\"4Y\u0018:##\u001d.#3#\rQu-0", "ykUVM");
        SoundManager.I[0x98 ^ 0x91] = I("\u001d;\u0011'\t+4X'\u0015/)\u00119\u001en#\u0017\"\u0017*p\u0003*Un&\u0017;\f#5X \u0018=p\u00022\u000b!~", "NPxWy");
        SoundManager.I[0x4B ^ 0x41] = I("\u0003)\u001240=\"S>6&+\u0017m\".e\u0015\"+s \u0005(7'e\b0y26S.12+\u001d(5s>\u000e", "SEsMY");
        SoundManager.I[0xB9 ^ 0xB2] = I("\u0017\u0019&\u0000&)\u001fs\u0010'&\u0016=\u0016#g\u0003.", "GxSsO");
        SoundManager.I[0xA0 ^ 0xAC] = I("\u0010\u00118\u001f\u0015+\u001a,J\u001b*\u0015%\u0004\u001d.T0\u0017", "BtKjx");
        SoundManager.I[0x2A ^ 0x27] = I("\u007f>tW2`h=", "ZMNrA");
        SoundManager.I[0xBC ^ 0xB2] = I("\n677\u0016\t1 7\u000e\u0006<*", "gUDXc");
        SoundManager.I[0x1A ^ 0x15] = I("\u0013\n<\"Og\u0016\u0019\u0003\u0010+<X\u0005\u0014)!\u0014\bU27\u0014M\u0010?&\u001d\u001d\u0001.*\u0016LU}\u0001", "GExmu");
    }
    
    public void unloadSoundSystem() {
        if (this.loaded) {
            this.stopAllSounds();
            this.sndSystem.cleanup();
            this.loaded = ("".length() != 0);
        }
    }
    
    static Marker access$5() {
        return SoundManager.LOG_MARKER;
    }
    
    public void stopSound(final ISound sound) {
        if (this.loaded) {
            final String s = this.invPlayingSounds.get(sound);
            if (s != null) {
                this.sndSystem.stop(s);
            }
        }
    }
    
    public void setListener(final EntityPlayer entityPlayer, final float n) {
        if (this.loaded && entityPlayer != null) {
            final float n2 = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * n;
            final float n3 = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * n;
            final double n4 = entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * n;
            final double n5 = entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * n + entityPlayer.getEyeHeight();
            final double n6 = entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * n;
            final float cos = MathHelper.cos((n3 + 90.0f) * 0.017453292f);
            final float sin = MathHelper.sin((n3 + 90.0f) * 0.017453292f);
            final float cos2 = MathHelper.cos(-n2 * 0.017453292f);
            final float sin2 = MathHelper.sin(-n2 * 0.017453292f);
            final float cos3 = MathHelper.cos((-n2 + 90.0f) * 0.017453292f);
            final float sin3 = MathHelper.sin((-n2 + 90.0f) * 0.017453292f);
            final float n7 = cos * cos2;
            final float n8 = sin * cos2;
            final float n9 = cos * cos3;
            final float n10 = sin * cos3;
            this.sndSystem.setListenerPosition((float)n4, (float)n5, (float)n6);
            this.sndSystem.setListenerOrientation(n7, sin2, n8, n9, sin3, n10);
        }
    }
    
    static Logger access$0() {
        return SoundManager.logger;
    }
    
    public void resumeAllSounds() {
        final Iterator<String> iterator = this.playingSounds.keySet().iterator();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final Logger logger = SoundManager.logger;
            final Marker log_MARKER = SoundManager.LOG_MARKER;
            final String s2 = SoundManager.I[0x42 ^ 0x4E];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            logger.debug(log_MARKER, s2, array);
            this.sndSystem.play(s);
        }
    }
    
    public void reloadSoundSystem() {
        this.unloadSoundSystem();
        this.loadSoundSystem();
    }
    
    public void updateAllSounds() {
        this.playTime += " ".length();
        final Iterator<ITickableSound> iterator = this.tickableSounds.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ITickableSound tickableSound = iterator.next();
            tickableSound.update();
            if (tickableSound.isDonePlaying()) {
                this.stopSound(tickableSound);
                "".length();
                if (false) {
                    throw null;
                }
                continue;
            }
            else {
                final String s = this.invPlayingSounds.get(tickableSound);
                this.sndSystem.setVolume(s, this.getNormalizedVolume(tickableSound, this.playingSoundPoolEntries.get(tickableSound), this.sndHandler.getSound(tickableSound.getSoundLocation()).getSoundCategory()));
                this.sndSystem.setPitch(s, this.getNormalizedPitch(tickableSound, this.playingSoundPoolEntries.get(tickableSound)));
                this.sndSystem.setPosition(s, tickableSound.getXPosF(), tickableSound.getYPosF(), tickableSound.getZPosF());
            }
        }
        final Iterator<Map.Entry<String, ISound>> iterator2 = this.playingSounds.entrySet().iterator();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final Map.Entry<String, ISound> entry = iterator2.next();
            final String s2 = entry.getKey();
            final ISound sound = entry.getValue();
            if (!this.sndSystem.playing(s2) && this.playingSoundsStopTime.get(s2) <= this.playTime) {
                final int repeatDelay = sound.getRepeatDelay();
                if (sound.canRepeat() && repeatDelay > 0) {
                    this.delayedSounds.put(sound, this.playTime + repeatDelay);
                }
                iterator2.remove();
                final Logger logger = SoundManager.logger;
                final Marker log_MARKER = SoundManager.LOG_MARKER;
                final String s3 = SoundManager.I[0x41 ^ 0x44];
                final Object[] array = new Object[" ".length()];
                array["".length()] = s2;
                logger.debug(log_MARKER, s3, array);
                this.sndSystem.removeSource(s2);
                this.playingSoundsStopTime.remove(s2);
                this.playingSoundPoolEntries.remove(sound);
                try {
                    this.categorySounds.remove((Object)this.sndHandler.getSound(sound.getSoundLocation()).getSoundCategory(), (Object)s2);
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
                catch (RuntimeException ex) {}
                if (!(sound instanceof ITickableSound)) {
                    continue;
                }
                this.tickableSounds.remove(sound);
            }
        }
        final Iterator<Map.Entry<ISound, Integer>> iterator3 = this.delayedSounds.entrySet().iterator();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (iterator3.hasNext()) {
            final Map.Entry<ISound, Integer> entry2 = iterator3.next();
            if (this.playTime >= entry2.getValue()) {
                final ISound sound2 = entry2.getKey();
                if (sound2 instanceof ITickableSound) {
                    ((ITickableSound)sound2).update();
                }
                this.playSound(sound2);
                iterator3.remove();
            }
        }
    }
    
    public void playSound(final ISound sound) {
        if (this.loaded) {
            if (this.sndSystem.getMasterVolume() <= 0.0f) {
                final Logger logger = SoundManager.logger;
                final Marker log_MARKER = SoundManager.LOG_MARKER;
                final String s = SoundManager.I[0x17 ^ 0x11];
                final Object[] array = new Object[" ".length()];
                array["".length()] = sound.getSoundLocation();
                logger.debug(log_MARKER, s, array);
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                final SoundEventAccessorComposite sound2 = this.sndHandler.getSound(sound.getSoundLocation());
                if (sound2 == null) {
                    final Logger logger2 = SoundManager.logger;
                    final Marker log_MARKER2 = SoundManager.LOG_MARKER;
                    final String s2 = SoundManager.I[0x21 ^ 0x26];
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = sound.getSoundLocation();
                    logger2.warn(log_MARKER2, s2, array2);
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                }
                else {
                    final SoundPoolEntry cloneEntry = sound2.cloneEntry();
                    if (cloneEntry == SoundHandler.missing_sound) {
                        final Logger logger3 = SoundManager.logger;
                        final Marker log_MARKER3 = SoundManager.LOG_MARKER;
                        final String s3 = SoundManager.I[0xCE ^ 0xC6];
                        final Object[] array3 = new Object[" ".length()];
                        array3["".length()] = sound2.getSoundEventLocation();
                        logger3.warn(log_MARKER3, s3, array3);
                        "".length();
                        if (2 < -1) {
                            throw null;
                        }
                    }
                    else {
                        final float volume = sound.getVolume();
                        float n = 16.0f;
                        if (volume > 1.0f) {
                            n *= volume;
                        }
                        final SoundCategory soundCategory = sound2.getSoundCategory();
                        final float normalizedVolume = this.getNormalizedVolume(sound, cloneEntry, soundCategory);
                        final double n2 = this.getNormalizedPitch(sound, cloneEntry);
                        final ResourceLocation soundPoolEntryLocation = cloneEntry.getSoundPoolEntryLocation();
                        if (normalizedVolume == 0.0f) {
                            final Logger logger4 = SoundManager.logger;
                            final Marker log_MARKER4 = SoundManager.LOG_MARKER;
                            final String s4 = SoundManager.I[0x66 ^ 0x6F];
                            final Object[] array4 = new Object[" ".length()];
                            array4["".length()] = soundPoolEntryLocation;
                            logger4.debug(log_MARKER4, s4, array4);
                            "".length();
                            if (2 == 3) {
                                throw null;
                            }
                        }
                        else {
                            int n3;
                            if (sound.canRepeat() && sound.getRepeatDelay() == 0) {
                                n3 = " ".length();
                                "".length();
                                if (2 < 2) {
                                    throw null;
                                }
                            }
                            else {
                                n3 = "".length();
                            }
                            final int n4 = n3;
                            final String string = MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()).toString();
                            if (cloneEntry.isStreamingSound()) {
                                this.sndSystem.newStreamingSource((boolean)("".length() != 0), string, getURLForSoundResource(soundPoolEntryLocation), soundPoolEntryLocation.toString(), (boolean)(n4 != 0), sound.getXPosF(), sound.getYPosF(), sound.getZPosF(), sound.getAttenuationType().getTypeInt(), n);
                                "".length();
                                if (2 >= 3) {
                                    throw null;
                                }
                            }
                            else {
                                this.sndSystem.newSource((boolean)("".length() != 0), string, getURLForSoundResource(soundPoolEntryLocation), soundPoolEntryLocation.toString(), (boolean)(n4 != 0), sound.getXPosF(), sound.getYPosF(), sound.getZPosF(), sound.getAttenuationType().getTypeInt(), n);
                            }
                            final Logger logger5 = SoundManager.logger;
                            final Marker log_MARKER5 = SoundManager.LOG_MARKER;
                            final String s5 = SoundManager.I[0x7B ^ 0x71];
                            final Object[] array5 = new Object["   ".length()];
                            array5["".length()] = cloneEntry.getSoundPoolEntryLocation();
                            array5[" ".length()] = sound2.getSoundEventLocation();
                            array5["  ".length()] = string;
                            logger5.debug(log_MARKER5, s5, array5);
                            this.sndSystem.setPitch(string, (float)n2);
                            this.sndSystem.setVolume(string, normalizedVolume);
                            this.sndSystem.play(string);
                            this.playingSoundsStopTime.put(string, this.playTime + (0xD3 ^ 0xC7));
                            this.playingSounds.put(string, sound);
                            this.playingSoundPoolEntries.put(sound, cloneEntry);
                            if (soundCategory != SoundCategory.MASTER) {
                                this.categorySounds.put((Object)soundCategory, (Object)string);
                            }
                            if (sound instanceof ITickableSound) {
                                this.tickableSounds.add((ITickableSound)sound);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void setSoundCategoryVolume(final SoundCategory soundCategory, final float masterVolume) {
        if (this.loaded) {
            if (soundCategory == SoundCategory.MASTER) {
                this.sndSystem.setMasterVolume(masterVolume);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                final Iterator<String> iterator = this.categorySounds.get((Object)soundCategory).iterator();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final String s = iterator.next();
                    final ISound sound = this.playingSounds.get(s);
                    final float normalizedVolume = this.getNormalizedVolume(sound, this.playingSoundPoolEntries.get(sound), soundCategory);
                    if (normalizedVolume <= 0.0f) {
                        this.stopSound(sound);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        this.sndSystem.setVolume(s, normalizedVolume);
                    }
                }
            }
        }
    }
    
    public void pauseAllSounds() {
        final Iterator<String> iterator = this.playingSounds.keySet().iterator();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final Logger logger = SoundManager.logger;
            final Marker log_MARKER = SoundManager.LOG_MARKER;
            final String s2 = SoundManager.I[0x7A ^ 0x71];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            logger.debug(log_MARKER, s2, array);
            this.sndSystem.pause(s);
        }
    }
    
    static {
        I();
        LOG_MARKER = MarkerManager.getMarker(SoundManager.I["".length()]);
        logger = LogManager.getLogger();
    }
    
    public void stopAllSounds() {
        if (this.loaded) {
            final Iterator<String> iterator = this.playingSounds.keySet().iterator();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.sndSystem.stop((String)iterator.next());
            }
            this.playingSounds.clear();
            this.delayedSounds.clear();
            this.tickableSounds.clear();
            this.categorySounds.clear();
            this.playingSoundPoolEntries.clear();
            this.playingSoundsStopTime.clear();
        }
    }
    
    private static URL getURLForSoundResource(final ResourceLocation resourceLocation) {
        final String s = SoundManager.I[0x44 ^ 0x49];
        final Object[] array = new Object["   ".length()];
        array["".length()] = SoundManager.I[0xB5 ^ 0xBB];
        array[" ".length()] = resourceLocation.getResourceDomain();
        array["  ".length()] = resourceLocation.getResourcePath();
        final String format = String.format(s, array);
        final URLStreamHandler urlStreamHandler = new URLStreamHandler(resourceLocation) {
            private final ResourceLocation val$p_148612_0_;
            
            @Override
            protected URLConnection openConnection(final URL url) {
                return new URLConnection(this, url, this.val$p_148612_0_) {
                    private final ResourceLocation val$p_148612_0_;
                    final SoundManager$2 this$1;
                    
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return Minecraft.getMinecraft().getResourceManager().getResource(this.val$p_148612_0_).getInputStream();
                    }
                    
                    private static String I(final String s, final String s2) {
                        final StringBuilder sb = new StringBuilder();
                        final char[] charArray = s2.toCharArray();
                        int length = "".length();
                        final char[] charArray2 = s.toCharArray();
                        final int length2 = charArray2.length;
                        int i = "".length();
                        while (i < length2) {
                            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                            ++length;
                            ++i;
                            "".length();
                            if (2 == 4) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public void connect() throws IOException {
                    }
                };
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        try {
            return new URL(null, format, urlStreamHandler);
        }
        catch (MalformedURLException ex) {
            throw new Error(SoundManager.I[0x6 ^ 0x9]);
        }
    }
    
    public void playDelayedSound(final ISound sound, final int n) {
        this.delayedSounds.put(sound, this.playTime + n);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static SoundSystemStarterThread access$3(final SoundManager soundManager) {
        return soundManager.sndSystem;
    }
    
    private synchronized void loadSoundSystem() {
        if (!this.loaded) {
            try {
                new Thread(new Runnable(this) {
                    private static final String[] I;
                    final SoundManager this$0;
                    
                    private static void I() {
                        (I = new String[" ".length()])["".length()] = I("\u0012'&\n\u001ea-=\u0003\u0013/-s\u0017\u000e :'\u0001\u001e", "AHSdz");
                    }
                    
                    private static String I(final String s, final String s2) {
                        final StringBuilder sb = new StringBuilder();
                        final char[] charArray = s2.toCharArray();
                        int length = "".length();
                        final char[] charArray2 = s.toCharArray();
                        final int length2 = charArray2.length;
                        int i = "".length();
                        while (i < length2) {
                            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                            ++length;
                            ++i;
                            "".length();
                            if (2 <= -1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    static {
                        I();
                    }
                    
                    @Override
                    public void run() {
                        SoundSystemConfig.setLogger((SoundSystemLogger)new SoundSystemLogger(this) {
                            private static final String[] I;
                            final SoundManager$1 this$1;
                            
                            public void message(final String s, final int n) {
                                if (!s.isEmpty()) {
                                    SoundManager.access$0().info(s);
                                }
                            }
                            
                            static {
                                I();
                            }
                            
                            private static String I(final String s, final String s2) {
                                final StringBuilder sb = new StringBuilder();
                                final char[] charArray = s2.toCharArray();
                                int length = "".length();
                                final char[] charArray2 = s.toCharArray();
                                final int length2 = charArray2.length;
                                int i = "".length();
                                while (i < length2) {
                                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                                    ++length;
                                    ++i;
                                    "".length();
                                    if (4 <= 3) {
                                        throw null;
                                    }
                                }
                                return sb.toString();
                            }
                            
                            public void errorMessage(final String s, final String s2, final int n) {
                                if (!s2.isEmpty()) {
                                    SoundManager.access$0().error(SoundManager$1$1.I["".length()] + s + SoundManager$1$1.I[" ".length()]);
                                    SoundManager.access$0().error(s2);
                                }
                            }
                            
                            private static void I() {
                                (I = new String["  ".length()])["".length()] = I("4?\u0004#=Q$\u0018l,\u001d,\u0005?oV", "qMvLO");
                                SoundManager$1$1.I[" ".length()] = I("u", "RyBTf");
                            }
                            
                            public void importantMessage(final String s, final int n) {
                                if (!s.isEmpty()) {
                                    SoundManager.access$0().warn(s);
                                }
                            }
                        });
                        SoundManager.access$1(this.this$0, this.this$0.new SoundSystemStarterThread(null));
                        SoundManager.access$2(this.this$0, " ".length() != 0);
                        SoundManager.access$3(this.this$0).setMasterVolume(SoundManager.access$4(this.this$0).getSoundLevel(SoundCategory.MASTER));
                        SoundManager.access$0().info(SoundManager.access$5(), SoundManager$1.I["".length()]);
                    }
                }, SoundManager.I["   ".length()]).start();
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            catch (RuntimeException ex) {
                SoundManager.logger.error(SoundManager.LOG_MARKER, SoundManager.I[0x11 ^ 0x15], (Throwable)ex);
                this.options.setSoundLevel(SoundCategory.MASTER, 0.0f);
                this.options.saveOptions();
            }
        }
    }
    
    static GameSettings access$4(final SoundManager soundManager) {
        return soundManager.options;
    }
    
    private float getNormalizedVolume(final ISound sound, final SoundPoolEntry soundPoolEntry, final SoundCategory soundCategory) {
        return (float)MathHelper.clamp_double(sound.getVolume() * soundPoolEntry.getVolume(), 0.0, 1.0) * this.getSoundCategoryVolume(soundCategory);
    }
    
    class SoundSystemStarterThread extends SoundSystem
    {
        final SoundManager this$0;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        SoundSystemStarterThread(final SoundManager soundManager, final SoundSystemStarterThread soundSystemStarterThread) {
            this(soundManager);
        }
        
        private SoundSystemStarterThread(final SoundManager this$0) {
            this.this$0 = this$0;
        }
        
        public boolean playing(final String s) {
            synchronized (SoundSystemConfig.THREAD_SYNC) {
                if (this.soundLibrary == null) {
                    // monitorexit(SoundSystemConfig.THREAD_SYNC)
                    return "".length() != 0;
                }
                final Source source = this.soundLibrary.getSources().get(s);
                int n;
                if (source == null) {
                    n = "".length();
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else if (!source.playing() && !source.paused() && !source.preLoad) {
                    n = "".length();
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
                else {
                    n = " ".length();
                }
                // monitorexit(SoundSystemConfig.THREAD_SYNC)
                return n != 0;
            }
        }
    }
}

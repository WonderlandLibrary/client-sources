package net.minecraft.client.audio;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class MusicTicker implements ITickable
{
    private final Random rand;
    private int timeUntilNextMusic;
    private final Minecraft mc;
    private ISound currentMusic;
    
    public void func_181558_a(final MusicType musicType) {
        this.currentMusic = PositionedSoundRecord.create(musicType.getMusicLocation());
        this.mc.getSoundHandler().playSound(this.currentMusic);
        this.timeUntilNextMusic = 2082326728 + 1321776362 + 1399520689 + 1638827164;
    }
    
    @Override
    public void update() {
        final MusicType ambientMusicType = this.mc.getAmbientMusicType();
        if (this.currentMusic != null) {
            if (!ambientMusicType.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
                this.mc.getSoundHandler().stopSound(this.currentMusic);
                this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, "".length(), ambientMusicType.getMinDelay() / "  ".length());
            }
            if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
                this.currentMusic = null;
                this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, ambientMusicType.getMinDelay(), ambientMusicType.getMaxDelay()), this.timeUntilNextMusic);
            }
        }
        if (this.currentMusic == null) {
            final int timeUntilNextMusic = this.timeUntilNextMusic;
            this.timeUntilNextMusic = timeUntilNextMusic - " ".length();
            if (timeUntilNextMusic <= 0) {
                this.func_181558_a(ambientMusicType);
            }
        }
    }
    
    public void func_181557_a() {
        if (this.currentMusic != null) {
            this.mc.getSoundHandler().stopSound(this.currentMusic);
            this.currentMusic = null;
            this.timeUntilNextMusic = "".length();
        }
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
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public MusicTicker(final Minecraft mc) {
        this.rand = new Random();
        this.timeUntilNextMusic = (0x31 ^ 0x55);
        this.mc = mc;
    }
    
    public enum MusicType
    {
        END(MusicType.I[0x35 ^ 0x39], 0x92 ^ 0x94, new ResourceLocation(MusicType.I[0x47 ^ 0x4A]), 2385 + 389 - 650 + 3876, 22681 + 16105 - 15690 + 904);
        
        private static final MusicType[] ENUM$VALUES;
        private final int minDelay;
        private final int maxDelay;
        
        MENU(MusicType.I["".length()], "".length(), new ResourceLocation(MusicType.I[" ".length()]), 0x2F ^ 0x3B, 218 + 197 + 34 + 151), 
        NETHER(MusicType.I[0x56 ^ 0x5E], 0x75 ^ 0x71, new ResourceLocation(MusicType.I[0x4C ^ 0x45]), 1194 + 584 - 1268 + 690, 1980 + 490 - 1612 + 2742), 
        END_BOSS(MusicType.I[0x30 ^ 0x3A], 0x1F ^ 0x1A, new ResourceLocation(MusicType.I[0x5D ^ 0x56]), "".length(), "".length());
        
        private final ResourceLocation musicLocation;
        
        CREDITS(MusicType.I[0xBE ^ 0xB8], "   ".length(), new ResourceLocation(MusicType.I[0x3 ^ 0x4]), 1824726200 + 1734855081 + 1803452388 + 1079417274, 1878143598 + 1390392242 + 1701774755 + 1472140348), 
        CREATIVE(MusicType.I[0x19 ^ 0x1D], "  ".length(), new ResourceLocation(MusicType.I[0x3 ^ 0x6]), 453 + 580 - 669 + 836, 878 + 2830 - 2871 + 2763);
        
        private static final String[] I;
        
        GAME(MusicType.I["  ".length()], " ".length(), new ResourceLocation(MusicType.I["   ".length()]), 7709 + 107 + 89 + 4095, 7732 + 13712 - 13319 + 15875);
        
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
                if (-1 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private MusicType(final String s, final int n, final ResourceLocation musicLocation, final int minDelay, final int maxDelay) {
            this.musicLocation = musicLocation;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
        }
        
        public ResourceLocation getMusicLocation() {
            return this.musicLocation;
        }
        
        private static void I() {
            (I = new String[0xA6 ^ 0xA8])["".length()] = I("!\u0017\u0004\u0014", "lRJAF");
            MusicType.I[" ".length()] = I(")=\u0003\u0016\u000265\u000b\u0007[)!\u001e\u001a\u0002j9\b\u001d\u0014", "DTmsa");
            MusicType.I["  ".length()] = I("\u000e\f:-", "IMwhn");
            MusicType.I["   ".length()] = I("\t.?0;\u0016&7!b\t2\"<;J 08=", "dGQUX");
            MusicType.I[0xB3 ^ 0xB7] = I("\u0013$\u000e\u0016'\u0019 \u000e", "PvKWs");
            MusicType.I[0x33 ^ 0x36] = I("\b;\u0016\f\u000e\u00173\u001e\u001dW\b'\u000b\u0000\u000eK5\u0019\u0004\bK1\n\f\f\u0011;\u000e\f", "eRxim");
            MusicType.I[0x52 ^ 0x54] = I("+\n\u000f\"\u000f<\u000b", "hXJfF");
            MusicType.I[0x9A ^ 0x9D] = I("\u001d\r\f2\u000b\u0002\u0005\u0004#R\u001d\u0011\u0011>\u000b^\u0003\u0003:\r^\u0001\f3F\u0013\u0016\u00073\u0001\u0004\u0017", "pdbWh");
            MusicType.I[0x84 ^ 0x8C] = I("\u001b1\u0006\t+\u0007", "UtRAn");
            MusicType.I[0xB5 ^ 0xBC] = I("$8$\u0007,;0,\u0016u$$9\u000b,g6+\u000f*g?/\u0016',#", "IQJbO");
            MusicType.I[0xCE ^ 0xC4] = I("0/\u0015\u000f.:2\u0002", "uaQPl");
            MusicType.I[0xC ^ 0x7] = I("= \b\u0012!\"(\u0000\u0003x=<\u0015\u001e!~.\u0007\u001a'~,\b\u0013l4;\u0007\u0010->", "PIfwB");
            MusicType.I[0x6C ^ 0x60] = I("3\u0002.", "vLjFv");
            MusicType.I[0x79 ^ 0x74] = I("7.\u0017\u000e((&\u001f\u001fq72\n\u0002(t \u0018\u0006.t\"\u0017\u000f", "ZGykK");
        }
        
        public int getMinDelay() {
            return this.minDelay;
        }
        
        public int getMaxDelay() {
            return this.maxDelay;
        }
        
        static {
            I();
            final MusicType[] enum$VALUES = new MusicType[0x59 ^ 0x5E];
            enum$VALUES["".length()] = MusicType.MENU;
            enum$VALUES[" ".length()] = MusicType.GAME;
            enum$VALUES["  ".length()] = MusicType.CREATIVE;
            enum$VALUES["   ".length()] = MusicType.CREDITS;
            enum$VALUES[0xD ^ 0x9] = MusicType.NETHER;
            enum$VALUES[0x9C ^ 0x99] = MusicType.END_BOSS;
            enum$VALUES[0x51 ^ 0x57] = MusicType.END;
            ENUM$VALUES = enum$VALUES;
        }
    }
}

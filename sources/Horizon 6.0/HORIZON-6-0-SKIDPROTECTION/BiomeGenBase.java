package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collections;
import java.awt.Color;
import com.google.common.collect.Lists;
import java.util.Random;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Logger;

public abstract class BiomeGenBase
{
    private static final Logger Ñ¢à;
    protected static final HorizonCode_Horizon_È HorizonCode_Horizon_È;
    protected static final HorizonCode_Horizon_È Â;
    protected static final HorizonCode_Horizon_È Ý;
    protected static final HorizonCode_Horizon_È Ø­áŒŠá;
    protected static final HorizonCode_Horizon_È Âµá€;
    protected static final HorizonCode_Horizon_È Ó;
    protected static final HorizonCode_Horizon_È à;
    protected static final HorizonCode_Horizon_È Ø;
    protected static final HorizonCode_Horizon_È áŒŠÆ;
    protected static final HorizonCode_Horizon_È áˆºÑ¢Õ;
    protected static final HorizonCode_Horizon_È ÂµÈ;
    protected static final HorizonCode_Horizon_È á;
    protected static final HorizonCode_Horizon_È ˆÏ­;
    private static final BiomeGenBase[] ÇªØ­;
    public static final Set £á;
    public static final Map Å;
    public static final BiomeGenBase £à;
    public static final BiomeGenBase µà;
    public static final BiomeGenBase ˆà;
    public static final BiomeGenBase ¥Æ;
    public static final BiomeGenBase Ø­à;
    public static final BiomeGenBase µÕ;
    public static final BiomeGenBase Æ;
    public static final BiomeGenBase Šáƒ;
    public static final BiomeGenBase Ï­Ðƒà;
    public static final BiomeGenBase áŒŠà;
    public static final BiomeGenBase ŠÄ;
    public static final BiomeGenBase Ñ¢á;
    public static final BiomeGenBase ŒÏ;
    public static final BiomeGenBase Çªà¢;
    public static final BiomeGenBase Ê;
    public static final BiomeGenBase ÇŽÉ;
    public static final BiomeGenBase ˆá;
    public static final BiomeGenBase ÇŽÕ;
    public static final BiomeGenBase É;
    public static final BiomeGenBase áƒ;
    public static final BiomeGenBase á€;
    public static final BiomeGenBase Õ;
    public static final BiomeGenBase à¢;
    public static final BiomeGenBase ŠÂµà;
    public static final BiomeGenBase ¥à;
    public static final BiomeGenBase Âµà;
    public static final BiomeGenBase Ç;
    public static final BiomeGenBase È;
    public static final BiomeGenBase áŠ;
    public static final BiomeGenBase ˆáŠ;
    public static final BiomeGenBase áŒŠ;
    public static final BiomeGenBase £ÂµÄ;
    public static final BiomeGenBase Ø­Âµ;
    public static final BiomeGenBase Ä;
    public static final BiomeGenBase Ñ¢Â;
    public static final BiomeGenBase Ï­à;
    public static final BiomeGenBase áˆºáˆºÈ;
    public static final BiomeGenBase ÇŽá€;
    public static final BiomeGenBase Ï;
    public static final BiomeGenBase Ô;
    public static final BiomeGenBase ÇªÓ;
    protected static final NoiseGeneratorPerlin áˆºÏ;
    protected static final NoiseGeneratorPerlin ˆáƒ;
    protected static final WorldGenDoublePlant Œ;
    public String £Ï;
    public int Ø­á;
    public int ˆÉ;
    public IBlockState Ï­Ï­Ï;
    public IBlockState £Â;
    public int £Ó;
    public float ˆÐƒØ­à;
    public float £Õ;
    public float Ï­Ô;
    public float Œà;
    public int Ðƒá;
    public BiomeDecorator ˆÏ;
    protected List áˆºÇŽØ;
    protected List ÇªÂµÕ;
    protected List áŒŠÏ;
    protected List áŒŠáŠ;
    protected boolean ˆÓ;
    protected boolean ¥Ä;
    public final int ÇªÔ;
    protected WorldGenTrees Û;
    protected WorldGenBigTree ŠÓ;
    protected WorldGenSwamp ÇŽá;
    private static final String £áŒŠá = "CL_00000158";
    
    static {
        Ñ¢à = LogManager.getLogger();
        HorizonCode_Horizon_È = new HorizonCode_Horizon_È(0.1f, 0.2f);
        Â = new HorizonCode_Horizon_È(-0.5f, 0.0f);
        Ý = new HorizonCode_Horizon_È(-1.0f, 0.1f);
        Ø­áŒŠá = new HorizonCode_Horizon_È(-1.8f, 0.1f);
        Âµá€ = new HorizonCode_Horizon_È(0.125f, 0.05f);
        Ó = new HorizonCode_Horizon_È(0.2f, 0.2f);
        à = new HorizonCode_Horizon_È(0.45f, 0.3f);
        Ø = new HorizonCode_Horizon_È(1.5f, 0.025f);
        áŒŠÆ = new HorizonCode_Horizon_È(1.0f, 0.5f);
        áˆºÑ¢Õ = new HorizonCode_Horizon_È(0.0f, 0.025f);
        ÂµÈ = new HorizonCode_Horizon_È(0.1f, 0.8f);
        á = new HorizonCode_Horizon_È(0.2f, 0.3f);
        ˆÏ­ = new HorizonCode_Horizon_È(-0.2f, 0.1f);
        ÇªØ­ = new BiomeGenBase[256];
        £á = Sets.newHashSet();
        Å = Maps.newHashMap();
        £à = new BiomeGenOcean(0).Â(112).HorizonCode_Horizon_È("Ocean").HorizonCode_Horizon_È(BiomeGenBase.Ý);
        µà = new BiomeGenPlains(1).Â(9286496).HorizonCode_Horizon_È("Plains");
        ˆà = new BiomeGenDesert(2).Â(16421912).HorizonCode_Horizon_È("Desert").Â().HorizonCode_Horizon_È(2.0f, 0.0f).HorizonCode_Horizon_È(BiomeGenBase.Âµá€);
        ¥Æ = new BiomeGenHills(3, false).Â(6316128).HorizonCode_Horizon_È("Extreme Hills").HorizonCode_Horizon_È(BiomeGenBase.áŒŠÆ).HorizonCode_Horizon_È(0.2f, 0.3f);
        Ø­à = new BiomeGenForest(4, 0).Â(353825).HorizonCode_Horizon_È("Forest");
        µÕ = new BiomeGenTaiga(5, 0).Â(747097).HorizonCode_Horizon_È("Taiga").HorizonCode_Horizon_È(5159473).HorizonCode_Horizon_È(0.25f, 0.8f).HorizonCode_Horizon_È(BiomeGenBase.Ó);
        Æ = new BiomeGenSwamp(6).Â(522674).HorizonCode_Horizon_È("Swampland").HorizonCode_Horizon_È(9154376).HorizonCode_Horizon_È(BiomeGenBase.ˆÏ­).HorizonCode_Horizon_È(0.8f, 0.9f);
        Šáƒ = new BiomeGenRiver(7).Â(255).HorizonCode_Horizon_È("River").HorizonCode_Horizon_È(BiomeGenBase.Â);
        Ï­Ðƒà = new BiomeGenHell(8).Â(16711680).HorizonCode_Horizon_È("Hell").Â().HorizonCode_Horizon_È(2.0f, 0.0f);
        áŒŠà = new BiomeGenEnd(9).Â(8421631).HorizonCode_Horizon_È("The End").Â();
        ŠÄ = new BiomeGenOcean(10).Â(9474208).HorizonCode_Horizon_È("FrozenOcean").Ý().HorizonCode_Horizon_È(BiomeGenBase.Ý).HorizonCode_Horizon_È(0.0f, 0.5f);
        Ñ¢á = new BiomeGenRiver(11).Â(10526975).HorizonCode_Horizon_È("FrozenRiver").Ý().HorizonCode_Horizon_È(BiomeGenBase.Â).HorizonCode_Horizon_È(0.0f, 0.5f);
        ŒÏ = new BiomeGenSnow(12, false).Â(16777215).HorizonCode_Horizon_È("Ice Plains").Ý().HorizonCode_Horizon_È(0.0f, 0.5f).HorizonCode_Horizon_È(BiomeGenBase.Âµá€);
        Çªà¢ = new BiomeGenSnow(13, false).Â(10526880).HorizonCode_Horizon_È("Ice Mountains").Ý().HorizonCode_Horizon_È(BiomeGenBase.à).HorizonCode_Horizon_È(0.0f, 0.5f);
        Ê = new BiomeGenMushroomIsland(14).Â(16711935).HorizonCode_Horizon_È("MushroomIsland").HorizonCode_Horizon_È(0.9f, 1.0f).HorizonCode_Horizon_È(BiomeGenBase.á);
        ÇŽÉ = new BiomeGenMushroomIsland(15).Â(10486015).HorizonCode_Horizon_È("MushroomIslandShore").HorizonCode_Horizon_È(0.9f, 1.0f).HorizonCode_Horizon_È(BiomeGenBase.áˆºÑ¢Õ);
        ˆá = new BiomeGenBeach(16).Â(16440917).HorizonCode_Horizon_È("Beach").HorizonCode_Horizon_È(0.8f, 0.4f).HorizonCode_Horizon_È(BiomeGenBase.áˆºÑ¢Õ);
        ÇŽÕ = new BiomeGenDesert(17).Â(13786898).HorizonCode_Horizon_È("DesertHills").Â().HorizonCode_Horizon_È(2.0f, 0.0f).HorizonCode_Horizon_È(BiomeGenBase.à);
        É = new BiomeGenForest(18, 0).Â(2250012).HorizonCode_Horizon_È("ForestHills").HorizonCode_Horizon_È(BiomeGenBase.à);
        áƒ = new BiomeGenTaiga(19, 0).Â(1456435).HorizonCode_Horizon_È("TaigaHills").HorizonCode_Horizon_È(5159473).HorizonCode_Horizon_È(0.25f, 0.8f).HorizonCode_Horizon_È(BiomeGenBase.à);
        á€ = new BiomeGenHills(20, true).Â(7501978).HorizonCode_Horizon_È("Extreme Hills Edge").HorizonCode_Horizon_È(BiomeGenBase.áŒŠÆ.HorizonCode_Horizon_È()).HorizonCode_Horizon_È(0.2f, 0.3f);
        Õ = new BiomeGenJungle(21, false).Â(5470985).HorizonCode_Horizon_È("Jungle").HorizonCode_Horizon_È(5470985).HorizonCode_Horizon_È(0.95f, 0.9f);
        à¢ = new BiomeGenJungle(22, false).Â(2900485).HorizonCode_Horizon_È("JungleHills").HorizonCode_Horizon_È(5470985).HorizonCode_Horizon_È(0.95f, 0.9f).HorizonCode_Horizon_È(BiomeGenBase.à);
        ŠÂµà = new BiomeGenJungle(23, true).Â(6458135).HorizonCode_Horizon_È("JungleEdge").HorizonCode_Horizon_È(5470985).HorizonCode_Horizon_È(0.95f, 0.8f);
        ¥à = new BiomeGenOcean(24).Â(48).HorizonCode_Horizon_È("Deep Ocean").HorizonCode_Horizon_È(BiomeGenBase.Ø­áŒŠá);
        Âµà = new BiomeGenStoneBeach(25).Â(10658436).HorizonCode_Horizon_È("Stone Beach").HorizonCode_Horizon_È(0.2f, 0.3f).HorizonCode_Horizon_È(BiomeGenBase.ÂµÈ);
        Ç = new BiomeGenBeach(26).Â(16445632).HorizonCode_Horizon_È("Cold Beach").HorizonCode_Horizon_È(0.05f, 0.3f).HorizonCode_Horizon_È(BiomeGenBase.áˆºÑ¢Õ).Ý();
        È = new BiomeGenForest(27, 2).HorizonCode_Horizon_È("Birch Forest").Â(3175492);
        áŠ = new BiomeGenForest(28, 2).HorizonCode_Horizon_È("Birch Forest Hills").Â(2055986).HorizonCode_Horizon_È(BiomeGenBase.à);
        ˆáŠ = new BiomeGenForest(29, 3).Â(4215066).HorizonCode_Horizon_È("Roofed Forest");
        áŒŠ = new BiomeGenTaiga(30, 0).Â(3233098).HorizonCode_Horizon_È("Cold Taiga").HorizonCode_Horizon_È(5159473).Ý().HorizonCode_Horizon_È(-0.5f, 0.4f).HorizonCode_Horizon_È(BiomeGenBase.Ó).Ý(16777215);
        £ÂµÄ = new BiomeGenTaiga(31, 0).Â(2375478).HorizonCode_Horizon_È("Cold Taiga Hills").HorizonCode_Horizon_È(5159473).Ý().HorizonCode_Horizon_È(-0.5f, 0.4f).HorizonCode_Horizon_È(BiomeGenBase.à).Ý(16777215);
        Ø­Âµ = new BiomeGenTaiga(32, 1).Â(5858897).HorizonCode_Horizon_È("Mega Taiga").HorizonCode_Horizon_È(5159473).HorizonCode_Horizon_È(0.3f, 0.8f).HorizonCode_Horizon_È(BiomeGenBase.Ó);
        Ä = new BiomeGenTaiga(33, 1).Â(4542270).HorizonCode_Horizon_È("Mega Taiga Hills").HorizonCode_Horizon_È(5159473).HorizonCode_Horizon_È(0.3f, 0.8f).HorizonCode_Horizon_È(BiomeGenBase.à);
        Ñ¢Â = new BiomeGenHills(34, true).Â(5271632).HorizonCode_Horizon_È("Extreme Hills+").HorizonCode_Horizon_È(BiomeGenBase.áŒŠÆ).HorizonCode_Horizon_È(0.2f, 0.3f);
        Ï­à = new BiomeGenSavanna(35).Â(12431967).HorizonCode_Horizon_È("Savanna").HorizonCode_Horizon_È(1.2f, 0.0f).Â().HorizonCode_Horizon_È(BiomeGenBase.Âµá€);
        áˆºáˆºÈ = new BiomeGenSavanna(36).Â(10984804).HorizonCode_Horizon_È("Savanna Plateau").HorizonCode_Horizon_È(1.0f, 0.0f).Â().HorizonCode_Horizon_È(BiomeGenBase.Ø);
        ÇŽá€ = new BiomeGenMesa(37, false, false).Â(14238997).HorizonCode_Horizon_È("Mesa");
        Ï = new BiomeGenMesa(38, false, true).Â(11573093).HorizonCode_Horizon_È("Mesa Plateau F").HorizonCode_Horizon_È(BiomeGenBase.Ø);
        Ô = new BiomeGenMesa(39, false, false).Â(13274213).HorizonCode_Horizon_È("Mesa Plateau").HorizonCode_Horizon_È(BiomeGenBase.Ø);
        ÇªÓ = BiomeGenBase.£à;
        BiomeGenBase.µà.ÂµÈ();
        BiomeGenBase.ˆà.ÂµÈ();
        BiomeGenBase.Ø­à.ÂµÈ();
        BiomeGenBase.µÕ.ÂµÈ();
        BiomeGenBase.Æ.ÂµÈ();
        BiomeGenBase.ŒÏ.ÂµÈ();
        BiomeGenBase.Õ.ÂµÈ();
        BiomeGenBase.ŠÂµà.ÂµÈ();
        BiomeGenBase.áŒŠ.ÂµÈ();
        BiomeGenBase.Ï­à.ÂµÈ();
        BiomeGenBase.áˆºáˆºÈ.ÂµÈ();
        BiomeGenBase.ÇŽá€.ÂµÈ();
        BiomeGenBase.Ï.ÂµÈ();
        BiomeGenBase.Ô.ÂµÈ();
        BiomeGenBase.È.ÂµÈ();
        BiomeGenBase.áŠ.ÂµÈ();
        BiomeGenBase.ˆáŠ.ÂµÈ();
        BiomeGenBase.Ø­Âµ.ÂµÈ();
        BiomeGenBase.¥Æ.ÂµÈ();
        BiomeGenBase.Ñ¢Â.ÂµÈ();
        BiomeGenBase.Ø­Âµ.Ø­áŒŠá(BiomeGenBase.Ä.ÇªÔ + 128).HorizonCode_Horizon_È("Redwood Taiga Hills M");
        for (final BiomeGenBase var4 : BiomeGenBase.ÇªØ­) {
            if (var4 != null) {
                if (BiomeGenBase.Å.containsKey(var4.£Ï)) {
                    throw new Error("Biome \"" + var4.£Ï + "\" is defined as both ID " + BiomeGenBase.Å.get(var4.£Ï).ÇªÔ + " and " + var4.ÇªÔ);
                }
                BiomeGenBase.Å.put(var4.£Ï, var4);
                if (var4.ÇªÔ < 128) {
                    BiomeGenBase.£á.add(var4);
                }
            }
        }
        BiomeGenBase.£á.remove(BiomeGenBase.Ï­Ðƒà);
        BiomeGenBase.£á.remove(BiomeGenBase.áŒŠà);
        BiomeGenBase.£á.remove(BiomeGenBase.ŠÄ);
        BiomeGenBase.£á.remove(BiomeGenBase.á€);
        áˆºÏ = new NoiseGeneratorPerlin(new Random(1234L), 1);
        ˆáƒ = new NoiseGeneratorPerlin(new Random(2345L), 1);
        Œ = new WorldGenDoublePlant();
    }
    
    protected BiomeGenBase(final int p_i1971_1_) {
        this.Ï­Ï­Ï = Blocks.Ø­áŒŠá.¥à();
        this.£Â = Blocks.Âµá€.¥à();
        this.£Ó = 5169201;
        this.ˆÐƒØ­à = BiomeGenBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.£Õ = BiomeGenBase.HorizonCode_Horizon_È.Â;
        this.Ï­Ô = 0.5f;
        this.Œà = 0.5f;
        this.Ðƒá = 16777215;
        this.áˆºÇŽØ = Lists.newArrayList();
        this.ÇªÂµÕ = Lists.newArrayList();
        this.áŒŠÏ = Lists.newArrayList();
        this.áŒŠáŠ = Lists.newArrayList();
        this.¥Ä = true;
        this.Û = new WorldGenTrees(false);
        this.ŠÓ = new WorldGenBigTree(false);
        this.ÇŽá = new WorldGenSwamp();
        this.ÇªÔ = p_i1971_1_;
        BiomeGenBase.ÇªØ­[p_i1971_1_] = this;
        this.ˆÏ = this.HorizonCode_Horizon_È();
        this.ÇªÂµÕ.add(new Â(EntitySheep.class, 12, 4, 4));
        this.ÇªÂµÕ.add(new Â(EntityRabbit.class, 10, 3, 3));
        this.ÇªÂµÕ.add(new Â(EntityPig.class, 10, 4, 4));
        this.ÇªÂµÕ.add(new Â(EntityChicken.class, 10, 4, 4));
        this.ÇªÂµÕ.add(new Â(EntityCow.class, 8, 4, 4));
        this.áˆºÇŽØ.add(new Â(EntitySpider.class, 100, 4, 4));
        this.áˆºÇŽØ.add(new Â(EntityZombie.class, 100, 4, 4));
        this.áˆºÇŽØ.add(new Â(EntitySkeleton.class, 100, 4, 4));
        this.áˆºÇŽØ.add(new Â(EntityCreeper.class, 100, 4, 4));
        this.áˆºÇŽØ.add(new Â(EntitySlime.class, 100, 4, 4));
        this.áˆºÇŽØ.add(new Â(EntityEnderman.class, 10, 1, 4));
        this.áˆºÇŽØ.add(new Â(EntityWitch.class, 5, 1, 1));
        this.áŒŠÏ.add(new Â(EntitySquid.class, 10, 4, 4));
        this.áŒŠáŠ.add(new Â(EntityBat.class, 10, 8, 8));
    }
    
    protected BiomeDecorator HorizonCode_Horizon_È() {
        return new BiomeDecorator();
    }
    
    protected BiomeGenBase HorizonCode_Horizon_È(final float p_76732_1_, final float p_76732_2_) {
        if (p_76732_1_ > 0.1f && p_76732_1_ < 0.2f) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        this.Ï­Ô = p_76732_1_;
        this.Œà = p_76732_2_;
        return this;
    }
    
    protected final BiomeGenBase HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_150570_1_) {
        this.ˆÐƒØ­à = p_150570_1_.HorizonCode_Horizon_È;
        this.£Õ = p_150570_1_.Â;
        return this;
    }
    
    protected BiomeGenBase Â() {
        this.¥Ä = false;
        return this;
    }
    
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return (p_150567_1_.nextInt(10) == 0) ? this.ŠÓ : this.Û;
    }
    
    public WorldGenerator Â(final Random p_76730_1_) {
        return new WorldGenTallGrass(BlockTallGrass.HorizonCode_Horizon_È.Â);
    }
    
    public BlockFlower.Â HorizonCode_Horizon_È(final Random p_180623_1_, final BlockPos p_180623_2_) {
        return (p_180623_1_.nextInt(3) > 0) ? BlockFlower.Â.HorizonCode_Horizon_È : BlockFlower.Â.Â;
    }
    
    protected BiomeGenBase Ý() {
        this.ˆÓ = true;
        return this;
    }
    
    protected BiomeGenBase HorizonCode_Horizon_È(final String p_76735_1_) {
        this.£Ï = p_76735_1_;
        return this;
    }
    
    protected BiomeGenBase HorizonCode_Horizon_È(final int p_76733_1_) {
        this.£Ó = p_76733_1_;
        return this;
    }
    
    protected BiomeGenBase Â(final int p_76739_1_) {
        this.HorizonCode_Horizon_È(p_76739_1_, false);
        return this;
    }
    
    protected BiomeGenBase Ý(final int p_150563_1_) {
        this.ˆÉ = p_150563_1_;
        return this;
    }
    
    protected BiomeGenBase HorizonCode_Horizon_È(final int p_150557_1_, final boolean p_150557_2_) {
        this.Ø­á = p_150557_1_;
        if (p_150557_2_) {
            this.ˆÉ = (p_150557_1_ & 0xFEFEFE) >> 1;
        }
        else {
            this.ˆÉ = p_150557_1_;
        }
        return this;
    }
    
    public int HorizonCode_Horizon_È(float p_76731_1_) {
        p_76731_1_ /= 3.0f;
        p_76731_1_ = MathHelper.HorizonCode_Horizon_È(p_76731_1_, -1.0f, 1.0f);
        return Color.getHSBColor(0.62222224f - p_76731_1_ * 0.05f, 0.5f + p_76731_1_ * 0.1f, 1.0f).getRGB();
    }
    
    public List HorizonCode_Horizon_È(final EnumCreatureType p_76747_1_) {
        switch (BiomeGenBase.Ý.HorizonCode_Horizon_È[p_76747_1_.ordinal()]) {
            case 1: {
                return this.áˆºÇŽØ;
            }
            case 2: {
                return this.ÇªÂµÕ;
            }
            case 3: {
                return this.áŒŠÏ;
            }
            case 4: {
                return this.áŒŠáŠ;
            }
            default: {
                return Collections.emptyList();
            }
        }
    }
    
    public boolean Ø­áŒŠá() {
        return this.áˆºÑ¢Õ();
    }
    
    public boolean Âµá€() {
        return !this.áˆºÑ¢Õ() && this.¥Ä;
    }
    
    public boolean Ó() {
        return this.Œà > 0.85f;
    }
    
    public float à() {
        return 0.1f;
    }
    
    public final int Ø() {
        return (int)(this.Œà * 65536.0f);
    }
    
    public final float áŒŠÆ() {
        return this.Œà;
    }
    
    public final float HorizonCode_Horizon_È(final BlockPos p_180626_1_) {
        if (p_180626_1_.Â() > 64) {
            final float var2 = (float)(BiomeGenBase.áˆºÏ.HorizonCode_Horizon_È(p_180626_1_.HorizonCode_Horizon_È() * 1.0 / 8.0, p_180626_1_.Ý() * 1.0 / 8.0) * 4.0);
            return this.Ï­Ô - (var2 + p_180626_1_.Â() - 64.0f) * 0.05f / 30.0f;
        }
        return this.Ï­Ô;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        this.ˆÏ.HorizonCode_Horizon_È(worldIn, p_180624_2_, this, p_180624_3_);
    }
    
    public int Â(final BlockPos p_180627_1_) {
        final double var2 = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(p_180627_1_), 0.0f, 1.0f);
        final double var3 = MathHelper.HorizonCode_Horizon_È(this.áŒŠÆ(), 0.0f, 1.0f);
        return ColorizerGrass.HorizonCode_Horizon_È(var2, var3);
    }
    
    public int Ý(final BlockPos p_180625_1_) {
        final double var2 = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(p_180625_1_), 0.0f, 1.0f);
        final double var3 = MathHelper.HorizonCode_Horizon_È(this.áŒŠÆ(), 0.0f, 1.0f);
        return ColorizerFoliage.HorizonCode_Horizon_È(var2, var3);
    }
    
    public boolean áˆºÑ¢Õ() {
        return this.ˆÓ;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_, final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
        this.Â(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
    
    public final void Â(final World worldIn, final Random p_180628_2_, final ChunkPrimer p_180628_3_, final int p_180628_4_, final int p_180628_5_, final double p_180628_6_) {
        final boolean var8 = true;
        IBlockState var9 = this.Ï­Ï­Ï;
        IBlockState var10 = this.£Â;
        int var11 = -1;
        final int var12 = (int)(p_180628_6_ / 3.0 + 3.0 + p_180628_2_.nextDouble() * 0.25);
        final int var13 = p_180628_4_ & 0xF;
        final int var14 = p_180628_5_ & 0xF;
        for (int var15 = 255; var15 >= 0; --var15) {
            if (var15 <= p_180628_2_.nextInt(5)) {
                p_180628_3_.HorizonCode_Horizon_È(var14, var15, var13, Blocks.áŒŠÆ.¥à());
            }
            else {
                final IBlockState var16 = p_180628_3_.HorizonCode_Horizon_È(var14, var15, var13);
                if (var16.Ý().Ó() == Material.HorizonCode_Horizon_È) {
                    var11 = -1;
                }
                else if (var16.Ý() == Blocks.Ý) {
                    if (var11 == -1) {
                        if (var12 <= 0) {
                            var9 = null;
                            var10 = Blocks.Ý.¥à();
                        }
                        else if (var15 >= 59 && var15 <= 64) {
                            var9 = this.Ï­Ï­Ï;
                            var10 = this.£Â;
                        }
                        if (var15 < 63 && (var9 == null || var9.Ý().Ó() == Material.HorizonCode_Horizon_È)) {
                            if (this.HorizonCode_Horizon_È(new BlockPos(p_180628_4_, var15, p_180628_5_)) < 0.15f) {
                                var9 = Blocks.¥Ï.¥à();
                            }
                            else {
                                var9 = Blocks.ÂµÈ.¥à();
                            }
                        }
                        var11 = var12;
                        if (var15 >= 62) {
                            p_180628_3_.HorizonCode_Horizon_È(var14, var15, var13, var9);
                        }
                        else if (var15 < 56 - var12) {
                            var9 = null;
                            var10 = Blocks.Ý.¥à();
                            p_180628_3_.HorizonCode_Horizon_È(var14, var15, var13, Blocks.Å.¥à());
                        }
                        else {
                            p_180628_3_.HorizonCode_Horizon_È(var14, var15, var13, var10);
                        }
                    }
                    else if (var11 > 0) {
                        --var11;
                        p_180628_3_.HorizonCode_Horizon_È(var14, var15, var13, var10);
                        if (var11 == 0 && var10.Ý() == Blocks.£á) {
                            var11 = p_180628_2_.nextInt(4) + Math.max(0, var15 - 63);
                            var10 = ((var10.HorizonCode_Horizon_È(BlockSand.Õ) == BlockSand.HorizonCode_Horizon_È.Â) ? Blocks.áˆºÛ.¥à() : Blocks.ŒÏ.¥à());
                        }
                    }
                }
            }
        }
    }
    
    protected BiomeGenBase ÂµÈ() {
        return this.Ø­áŒŠá(this.ÇªÔ + 128);
    }
    
    protected BiomeGenBase Ø­áŒŠá(final int p_180277_1_) {
        return new BiomeGenMutated(p_180277_1_, this);
    }
    
    public Class á() {
        return this.getClass();
    }
    
    public boolean HorizonCode_Horizon_È(final BiomeGenBase p_150569_1_) {
        return p_150569_1_ == this || (p_150569_1_ != null && this.á() == p_150569_1_.á());
    }
    
    public Ø­áŒŠá ˆÏ­() {
        return (this.Ï­Ô < 0.2) ? BiomeGenBase.Ø­áŒŠá.Â : ((this.Ï­Ô < 1.0) ? BiomeGenBase.Ø­áŒŠá.Ý : BiomeGenBase.Ø­áŒŠá.Ø­áŒŠá);
    }
    
    public static BiomeGenBase[] £á() {
        return BiomeGenBase.ÇªØ­;
    }
    
    public static BiomeGenBase Âµá€(final int p_150568_0_) {
        return HorizonCode_Horizon_È(p_150568_0_, null);
    }
    
    public static BiomeGenBase HorizonCode_Horizon_È(final int p_180276_0_, final BiomeGenBase p_180276_1_) {
        if (p_180276_0_ >= 0 && p_180276_0_ <= BiomeGenBase.ÇªØ­.length) {
            final BiomeGenBase var2 = BiomeGenBase.ÇªØ­[p_180276_0_];
            return (var2 == null) ? p_180276_1_ : var2;
        }
        BiomeGenBase.Ñ¢à.warn("Biome ID is out of bounds: " + p_180276_0_ + ", defaulting to 0 (Ocean)");
        return BiomeGenBase.£à;
    }
    
    public enum Ø­áŒŠá
    {
        HorizonCode_Horizon_È("OCEAN", 0, "OCEAN", 0), 
        Â("COLD", 1, "COLD", 1), 
        Ý("MEDIUM", 2, "MEDIUM", 2), 
        Ø­áŒŠá("WARM", 3, "WARM", 3);
        
        private static final Ø­áŒŠá[] Âµá€;
        private static final String Ó = "CL_00000160";
        
        static {
            à = new Ø­áŒŠá[] { Ø­áŒŠá.HorizonCode_Horizon_È, Ø­áŒŠá.Â, Ø­áŒŠá.Ý, Ø­áŒŠá.Ø­áŒŠá };
            Âµá€ = new Ø­áŒŠá[] { Ø­áŒŠá.HorizonCode_Horizon_È, Ø­áŒŠá.Â, Ø­áŒŠá.Ý, Ø­áŒŠá.Ø­áŒŠá };
        }
        
        private Ø­áŒŠá(final String s, final int n, final String p_i45372_1_, final int p_i45372_2_) {
        }
    }
    
    public static class HorizonCode_Horizon_È
    {
        public float HorizonCode_Horizon_È;
        public float Â;
        private static final String Ý = "CL_00000159";
        
        public HorizonCode_Horizon_È(final float p_i45371_1_, final float p_i45371_2_) {
            this.HorizonCode_Horizon_È = p_i45371_1_;
            this.Â = p_i45371_2_;
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
            return new HorizonCode_Horizon_È(this.HorizonCode_Horizon_È * 0.8f, this.Â * 0.6f);
        }
    }
    
    public static class Â extends WeightedRandom.HorizonCode_Horizon_È
    {
        public Class HorizonCode_Horizon_È;
        public int Â;
        public int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00000161";
        
        public Â(final Class p_i1970_1_, final int p_i1970_2_, final int p_i1970_3_, final int p_i1970_4_) {
            super(p_i1970_2_);
            this.HorizonCode_Horizon_È = p_i1970_1_;
            this.Â = p_i1970_3_;
            this.Ø­áŒŠá = p_i1970_4_;
        }
        
        @Override
        public String toString() {
            return String.valueOf(this.HorizonCode_Horizon_È.getSimpleName()) + "*(" + this.Â + "-" + this.Ø­áŒŠá + "):" + this.Ý;
        }
    }
    
    static final class Ý
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002150";
        
        static {
            HorizonCode_Horizon_È = new int[EnumCreatureType.values().length];
            try {
                Ý.HorizonCode_Horizon_È[EnumCreatureType.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumCreatureType.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumCreatureType.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumCreatureType.Ý.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}

package net.minecraft.client.particle;

import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import java.util.concurrent.*;
import net.minecraft.client.renderer.*;
import net.minecraft.crash.*;
import com.google.common.collect.*;
import java.util.*;
import optfine.*;
import net.minecraft.util.*;

public class EffectRenderer
{
    private static final String[] I;
    private Map particleTypes;
    private Random rand;
    private TextureManager renderer;
    private static final ResourceLocation particleTextures;
    private static final String __OBFID;
    protected World worldObj;
    private List[][] fxLayers;
    private List particleEmitters;
    
    public void addBlockHitEffects(final BlockPos blockPos, final MovingObjectPosition movingObjectPosition) {
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        final ReflectorMethod forgeBlock_addHitEffects = Reflector.ForgeBlock_addHitEffects;
        final Object[] array = new Object["   ".length()];
        array["".length()] = this.worldObj;
        array[" ".length()] = movingObjectPosition;
        array["  ".length()] = this;
        final boolean callBoolean = Reflector.callBoolean(block, forgeBlock_addHitEffects, array);
        if (block != null && !callBoolean) {
            this.addBlockHitEffects(blockPos, movingObjectPosition.sideHit);
        }
    }
    
    public void addBlockDestroyEffects(final BlockPos blockPos, IBlockState actualState) {
        int n2;
        if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
            final Block block2;
            final Block block = block2 = actualState.getBlock();
            final ReflectorMethod forgeBlock_isAir = Reflector.ForgeBlock_isAir;
            final Object[] array = new Object["  ".length()];
            array["".length()] = this.worldObj;
            array[" ".length()] = blockPos;
            Reflector.callBoolean(block2, forgeBlock_isAir, array);
            final Block block3 = block;
            final ReflectorMethod forgeBlock_isAir2 = Reflector.ForgeBlock_isAir;
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = this.worldObj;
            array2[" ".length()] = blockPos;
            int n = 0;
            Label_0169: {
                if (!Reflector.callBoolean(block3, forgeBlock_isAir2, array2)) {
                    final Block block4 = block;
                    final ReflectorMethod forgeBlock_addDestroyEffects = Reflector.ForgeBlock_addDestroyEffects;
                    final Object[] array3 = new Object["   ".length()];
                    array3["".length()] = this.worldObj;
                    array3[" ".length()] = blockPos;
                    array3["  ".length()] = this;
                    if (!Reflector.callBoolean(block4, forgeBlock_addDestroyEffects, array3)) {
                        n = " ".length();
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                        break Label_0169;
                    }
                }
                n = "".length();
            }
            n2 = n;
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            int n3;
            if (actualState.getBlock().getMaterial() != Material.air) {
                n3 = " ".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            n2 = n3;
        }
        if (n2 != 0) {
            actualState = actualState.getBlock().getActualState(actualState, this.worldObj, blockPos);
            final int n4 = 0x14 ^ 0x10;
            int i = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (i < n4) {
                int j = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (j < n4) {
                    int k = "".length();
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                    while (k < n4) {
                        final double n5 = blockPos.getX() + (i + 0.5) / n4;
                        final double n6 = blockPos.getY() + (j + 0.5) / n4;
                        final double n7 = blockPos.getZ() + (k + 0.5) / n4;
                        this.addEffect(new EntityDiggingFX(this.worldObj, n5, n6, n7, n5 - blockPos.getX() - 0.5, n6 - blockPos.getY() - 0.5, n7 - blockPos.getZ() - 0.5, actualState).func_174846_a(blockPos));
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
    }
    
    static {
        I();
        __OBFID = EffectRenderer.I["".length()];
        particleTextures = new ResourceLocation(EffectRenderer.I[" ".length()]);
    }
    
    private void moveToLayer(final EntityFX entityFX, final int n, final int n2) {
        int i = "".length();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (i < (0x12 ^ 0x16)) {
            if (this.fxLayers[i][n].contains(entityFX)) {
                this.fxLayers[i][n].remove(entityFX);
                this.fxLayers[i][n2].add(entityFX);
            }
            ++i;
        }
    }
    
    public String getStatistics() {
        int length = "".length();
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < (0x2F ^ 0x2B)) {
            int j = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (j < "  ".length()) {
                length += this.fxLayers[i][j].size();
                ++j;
            }
            ++i;
        }
        return new StringBuilder().append(length).toString();
    }
    
    private void updateEffectLayer(final int n) {
        int i = "".length();
        "".length();
        if (4 < 2) {
            throw null;
        }
        while (i < "  ".length()) {
            this.updateEffectAlphaLayer(this.fxLayers[n][i]);
            ++i;
        }
    }
    
    public EntityFX spawnEffectParticle(final int n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        final IParticleFactory particleFactory = this.particleTypes.get(n);
        if (particleFactory != null) {
            final EntityFX entityFX = particleFactory.getEntityFX(n, this.worldObj, n2, n3, n4, n5, n6, n7, array);
            if (entityFX != null) {
                this.addEffect(entityFX);
                return entityFX;
            }
        }
        return null;
    }
    
    public void moveToNoAlphaLayer(final EntityFX entityFX) {
        this.moveToLayer(entityFX, "".length(), " ".length());
    }
    
    public void renderParticles(final Entity entity, final float n) {
        final float rotationX = ActiveRenderInfo.getRotationX();
        final float rotationZ = ActiveRenderInfo.getRotationZ();
        final float rotationYZ = ActiveRenderInfo.getRotationYZ();
        final float rotationXY = ActiveRenderInfo.getRotationXY();
        final float rotationXZ = ActiveRenderInfo.getRotationXZ();
        EntityFX.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        EntityFX.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        EntityFX.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(139 + 393 + 172 + 66, 525 + 228 - 630 + 648);
        GlStateManager.alphaFunc(198 + 67 + 51 + 200, 0.003921569f);
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < "   ".length()) {
            final int n2 = i;
            int j = "".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (j < "  ".length()) {
                if (!this.fxLayers[n2][j].isEmpty()) {
                    switch (j) {
                        case 0: {
                            GlStateManager.depthMask("".length() != 0);
                            "".length();
                            if (3 <= 0) {
                                throw null;
                            }
                            break;
                        }
                        case 1: {
                            GlStateManager.depthMask(" ".length() != 0);
                            break;
                        }
                    }
                    switch (n2) {
                        default: {
                            this.renderer.bindTexture(EffectRenderer.particleTextures);
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                            break;
                        }
                        case 1: {
                            this.renderer.bindTexture(TextureMap.locationBlocksTexture);
                            break;
                        }
                    }
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    worldRenderer.begin(0x98 ^ 0x9F, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                    int k = "".length();
                    "".length();
                    if (!true) {
                        throw null;
                    }
                    while (k < this.fxLayers[n2][j].size()) {
                        final EntityFX entityFX = this.fxLayers[n2][j].get(k);
                        try {
                            entityFX.renderParticle(worldRenderer, entity, n, rotationX, rotationXZ, rotationZ, rotationYZ, rotationXY);
                            "".length();
                            if (2 == 1) {
                                throw null;
                            }
                        }
                        catch (Throwable t) {
                            final CrashReport crashReport = CrashReport.makeCrashReport(t, EffectRenderer.I[0x2E ^ 0x28]);
                            final CrashReportCategory category = crashReport.makeCategory(EffectRenderer.I[0xBB ^ 0xBC]);
                            category.addCrashSectionCallable(EffectRenderer.I[0xB1 ^ 0xB9], new Callable(this, entityFX) {
                                final EffectRenderer this$0;
                                private static final String[] I;
                                private static final String __OBFID;
                                private final EntityFX val$entityfx;
                                
                                @Override
                                public String call() throws Exception {
                                    return this.val$entityfx.toString();
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
                                        if (2 != 2) {
                                            throw null;
                                        }
                                    }
                                    return sb.toString();
                                }
                                
                                static {
                                    I();
                                    __OBFID = EffectRenderer$3.I["".length()];
                                }
                                
                                private static void I() {
                                    (I = new String[" ".length()])["".length()] = I("\n>\u0015tFyBz}Gq", "IrJDv");
                                }
                                
                                @Override
                                public Object call() throws Exception {
                                    return this.call();
                                }
                            });
                            category.addCrashSectionCallable(EffectRenderer.I[0x4B ^ 0x42], new Callable(this, n2) {
                                private static final String __OBFID;
                                final EffectRenderer this$0;
                                private static final String[] I;
                                private final int val$j;
                                
                                static {
                                    I();
                                    __OBFID = EffectRenderer$4.I[0x3A ^ 0x3E];
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
                                        if (-1 == 0) {
                                            throw null;
                                        }
                                    }
                                    return sb.toString();
                                }
                                
                                private static void I() {
                                    (I = new String[0xA6 ^ 0xA3])["".length()] = I(",\u0010\n\u0004\u001e5\u001c\u0001\u0013\u00143\u001c", "aYYGA");
                                    EffectRenderer$4.I[" ".length()] = I("=\u00027\u001d( \t:\u001b,1\u00130\u001d,", "iGeOi");
                                    EffectRenderer$4.I["  ".length()] = I(".'\"/\u001726&'\u0011? 5*\u00064=3>\u0017>;3", "kivfC");
                                    EffectRenderer$4.I["   ".length()] = I("4\u0018\u0005\u00149\u0016\u0018NWv", "avnzV");
                                    EffectRenderer$4.I[0x66 ^ 0x62] = I("\t\u0015\bXGzigQFs", "JYWhw");
                                }
                                
                                @Override
                                public String call() throws Exception {
                                    String string;
                                    if (this.val$j == 0) {
                                        string = EffectRenderer$4.I["".length()];
                                        "".length();
                                        if (1 == 2) {
                                            throw null;
                                        }
                                    }
                                    else if (this.val$j == " ".length()) {
                                        string = EffectRenderer$4.I[" ".length()];
                                        "".length();
                                        if (1 == 3) {
                                            throw null;
                                        }
                                    }
                                    else if (this.val$j == "   ".length()) {
                                        string = EffectRenderer$4.I["  ".length()];
                                        "".length();
                                        if (!true) {
                                            throw null;
                                        }
                                    }
                                    else {
                                        string = EffectRenderer$4.I["   ".length()] + this.val$j;
                                    }
                                    return string;
                                }
                                
                                @Override
                                public Object call() throws Exception {
                                    return this.call();
                                }
                            });
                            throw new ReportedException(crashReport);
                        }
                        ++k;
                    }
                    instance.draw();
                }
                ++j;
            }
            ++i;
        }
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(390 + 104 - 203 + 225, 0.1f);
    }
    
    public void addBlockHitEffects(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block.getRenderType() != -" ".length()) {
            final int x = blockPos.getX();
            final int y = blockPos.getY();
            final int z = blockPos.getZ();
            final float n = 0.1f;
            double n2 = x + this.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - n * 2.0f) + n + block.getBlockBoundsMinX();
            double n3 = y + this.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - n * 2.0f) + n + block.getBlockBoundsMinY();
            double n4 = z + this.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - n * 2.0f) + n + block.getBlockBoundsMinZ();
            if (enumFacing == EnumFacing.DOWN) {
                n3 = y + block.getBlockBoundsMinY() - n;
            }
            if (enumFacing == EnumFacing.UP) {
                n3 = y + block.getBlockBoundsMaxY() + n;
            }
            if (enumFacing == EnumFacing.NORTH) {
                n4 = z + block.getBlockBoundsMinZ() - n;
            }
            if (enumFacing == EnumFacing.SOUTH) {
                n4 = z + block.getBlockBoundsMaxZ() + n;
            }
            if (enumFacing == EnumFacing.WEST) {
                n2 = x + block.getBlockBoundsMinX() - n;
            }
            if (enumFacing == EnumFacing.EAST) {
                n2 = x + block.getBlockBoundsMaxX() + n;
            }
            this.addEffect(new EntityDiggingFX(this.worldObj, n2, n3, n4, 0.0, 0.0, 0.0, blockState).func_174846_a(blockPos).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f));
        }
    }
    
    public EffectRenderer(final World worldObj, final TextureManager renderer) {
        this.fxLayers = new List[0x37 ^ 0x33][];
        this.particleEmitters = Lists.newArrayList();
        this.rand = new Random();
        this.particleTypes = Maps.newHashMap();
        this.worldObj = worldObj;
        this.renderer = renderer;
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < (0x2B ^ 0x2F)) {
            this.fxLayers[i] = new List["  ".length()];
            int j = "".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (j < "  ".length()) {
                this.fxLayers[i][j] = Lists.newArrayList();
                ++j;
            }
            ++i;
        }
        this.registerVanillaParticles();
    }
    
    public void emitParticleAtEntity(final Entity entity, final EnumParticleTypes enumParticleTypes) {
        this.particleEmitters.add(new EntityParticleEmitter(this.worldObj, entity, enumParticleTypes));
    }
    
    private void tickParticle(final EntityFX entityFX) {
        try {
            entityFX.onUpdate();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, EffectRenderer.I["  ".length()]);
            final CrashReportCategory category = crashReport.makeCategory(EffectRenderer.I["   ".length()]);
            final int fxLayer = entityFX.getFXLayer();
            category.addCrashSectionCallable(EffectRenderer.I[0x26 ^ 0x22], new Callable(this, entityFX) {
                private static final String __OBFID;
                private static final String[] I;
                private final EntityFX val$p_178923_1_;
                final EffectRenderer this$0;
                
                static {
                    I();
                    __OBFID = EffectRenderer$1.I["".length()];
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
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
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public String call() throws Exception {
                    return this.val$p_178923_1_.toString();
                }
                
                private static void I() {
                    (I = new String[" ".length()])["".length()] = I("\u0005\u00153tivi\\}hp", "FYlDY");
                }
            });
            category.addCrashSectionCallable(EffectRenderer.I[0x26 ^ 0x23], new Callable(this, fxLayer) {
                private final int val$i;
                final EffectRenderer this$0;
                private static final String __OBFID;
                private static final String[] I;
                
                private static void I() {
                    (I = new String[0x50 ^ 0x55])["".length()] = I(".\u001c\u0011\f\u001b7\u0010\u001a\u001b\u00111\u0010", "cUBOD");
                    EffectRenderer$2.I[" ".length()] = I("\f/\u001a\b+\u0011$\u0017\u000e/\u0000>\u001d\b/", "XjHZj");
                    EffectRenderer$2.I["  ".length()] = I("2-\u0003\u0013,.<\u0007\u001b*#*\u0014\u0016=(7\u0012\u0002,\"1\u0012", "wcWZx");
                    EffectRenderer$2.I["   ".length()] = I(";?\u00129\f\u0019?YzC", "nQyWc");
                    EffectRenderer$2.I[0x8A ^ 0x8E] = I("\u0015\u0007\u000eEVf{aLWa", "VKQuf");
                }
                
                @Override
                public String call() throws Exception {
                    String string;
                    if (this.val$i == 0) {
                        string = EffectRenderer$2.I["".length()];
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                    else if (this.val$i == " ".length()) {
                        string = EffectRenderer$2.I[" ".length()];
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                    }
                    else if (this.val$i == "   ".length()) {
                        string = EffectRenderer$2.I["  ".length()];
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                    }
                    else {
                        string = EffectRenderer$2.I["   ".length()] + this.val$i;
                    }
                    return string;
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
                
                static {
                    I();
                    __OBFID = EffectRenderer$2.I[0x47 ^ 0x43];
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
            });
            throw new ReportedException(crashReport);
        }
    }
    
    public void updateEffects() {
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < (0x88 ^ 0x8C)) {
            this.updateEffectLayer(i);
            ++i;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<EntityParticleEmitter> iterator = this.particleEmitters.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityParticleEmitter entityParticleEmitter = iterator.next();
            entityParticleEmitter.onUpdate();
            if (entityParticleEmitter.isDead) {
                arrayList.add(entityParticleEmitter);
            }
        }
        this.particleEmitters.removeAll(arrayList);
    }
    
    private void updateEffectAlphaLayer(final List list) {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < list.size()) {
            final EntityFX entityFX = list.get(i);
            this.tickParticle(entityFX);
            if (entityFX.isDead) {
                arrayList.add(entityFX);
            }
            ++i;
        }
        list.removeAll(arrayList);
    }
    
    private static void I() {
        (I = new String[0x7C ^ 0x76])["".length()] = I("';\u0012b[TG}kZQ", "dwMRk");
        EffectRenderer.I[" ".length()] = I("1\u0003\u001a-%7\u0003\u0011v $\u0014\u001603)\u0003M)17\u0012\u000b:< \u0015L)>\"", "EfbYP");
        EffectRenderer.I["  ".length()] = I("#\u001e\u0012\t;\u0019\u0010Q23\u0005\u0003\u0018\u0001>\u0012", "wwqbR");
        EffectRenderer.I["   ".length()] = I("?\u0014\"&\u0005\f\u00195r\u000e\n\u001c>5L\u001b\u001c39\t\u000b", "ouPRl");
        EffectRenderer.I[0x5F ^ 0x5B] = I(":\u0012#\u0001\u001d\t\u001f4", "jsQut");
        EffectRenderer.I[0xC5 ^ 0xC0] = I("*\u0004\u0011\u0015(\u0019\t\u0006A\u0015\u0003\u0015\u0006", "zecaA");
        EffectRenderer.I[0x71 ^ 0x77] = I("&\f\u0000\n\f\u0006\u0000\u0000\tI$\b\u001c\u001a\u0000\u0017\u0005\u000b", "tinni");
        EffectRenderer.I[0xB ^ 0xC] = I("\u0015\u0010  (&\u001d7t# \u0018<3a7\u0014<0$7\u00146", "EqRTA");
        EffectRenderer.I[0x4F ^ 0x47] = I("\u0002&\u001d\u001d!1+\n", "RGoiH");
        EffectRenderer.I[0x8D ^ 0x84] = I("\u00078\u001c,\u000445\u000bx9.)\u000b", "WYnXm");
    }
    
    public void addEffect(final EntityFX entityFX) {
        if (entityFX != null && (!(entityFX instanceof EntityFirework.SparkFX) || Config.isFireworkParticles())) {
            final int fxLayer = entityFX.getFXLayer();
            int n;
            if (entityFX.getAlpha() != 1.0f) {
                n = "".length();
                "".length();
                if (!true) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            final int n2 = n;
            if (this.fxLayers[fxLayer][n2].size() >= 1220 + 2545 - 2877 + 3112) {
                this.fxLayers[fxLayer][n2].remove("".length());
            }
            this.fxLayers[fxLayer][n2].add(entityFX);
        }
    }
    
    public void registerParticle(final int n, final IParticleFactory particleFactory) {
        this.particleTypes.put(n, particleFactory);
    }
    
    public void moveToAlphaLayer(final EntityFX entityFX) {
        this.moveToLayer(entityFX, " ".length(), "".length());
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void renderLitParticles(final Entity entity, final float n) {
        final float cos = MathHelper.cos(entity.rotationYaw * 0.017453292f);
        final float sin = MathHelper.sin(entity.rotationYaw * 0.017453292f);
        final float n2 = -sin * MathHelper.sin(entity.rotationPitch * 0.017453292f);
        final float n3 = cos * MathHelper.sin(entity.rotationPitch * 0.017453292f);
        final float cos2 = MathHelper.cos(entity.rotationPitch * 0.017453292f);
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < "  ".length()) {
            final List list = this.fxLayers["   ".length()][i];
            if (!list.isEmpty()) {
                final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
                int j = "".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
                while (j < list.size()) {
                    list.get(j).renderParticle(worldRenderer, entity, n, cos, cos2, sin, n2, n3);
                    ++j;
                }
            }
            ++i;
        }
    }
    
    private void registerVanillaParticles() {
        this.registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new EntityExplodeFX.Factory());
        this.registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new EntityBubbleFX.Factory());
        this.registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new EntitySplashFX.Factory());
        this.registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new EntityFishWakeFX.Factory());
        this.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new EntityRainFX.Factory());
        this.registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new EntitySuspendFX.Factory());
        this.registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new EntityAuraFX.Factory());
        this.registerParticle(EnumParticleTypes.CRIT.getParticleID(), new EntityCrit2FX.Factory());
        this.registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new EntityCrit2FX.MagicFactory());
        this.registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new EntitySmokeFX.Factory());
        this.registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new EntityCritFX.Factory());
        this.registerParticle(EnumParticleTypes.SPELL.getParticleID(), new EntitySpellParticleFX.Factory());
        this.registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new EntitySpellParticleFX.InstantFactory());
        this.registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new EntitySpellParticleFX.MobFactory());
        this.registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new EntitySpellParticleFX.AmbientMobFactory());
        this.registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new EntitySpellParticleFX.WitchFactory());
        this.registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new EntityDropParticleFX.WaterFactory());
        this.registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new EntityDropParticleFX.LavaFactory());
        this.registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new EntityHeartFX.AngryVillagerFactory());
        this.registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new EntityAuraFX.HappyVillagerFactory());
        this.registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new EntityAuraFX.Factory());
        this.registerParticle(EnumParticleTypes.NOTE.getParticleID(), new EntityNoteFX.Factory());
        this.registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new EntityPortalFX.Factory());
        this.registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
        this.registerParticle(EnumParticleTypes.FLAME.getParticleID(), new EntityFlameFX.Factory());
        this.registerParticle(EnumParticleTypes.LAVA.getParticleID(), new EntityLavaFX.Factory());
        this.registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new EntityFootStepFX.Factory());
        this.registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new EntityCloudFX.Factory());
        this.registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new EntityReddustFX.Factory());
        this.registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new EntityBreakingFX.SnowballFactory());
        this.registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new EntitySnowShovelFX.Factory());
        this.registerParticle(EnumParticleTypes.SLIME.getParticleID(), new EntityBreakingFX.SlimeFactory());
        this.registerParticle(EnumParticleTypes.HEART.getParticleID(), new EntityHeartFX.Factory());
        this.registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier.Factory());
        this.registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new EntityBreakingFX.Factory());
        this.registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new EntityDiggingFX.Factory());
        this.registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new EntityBlockDustFX.Factory());
        this.registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new EntityHugeExplodeFX.Factory());
        this.registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new EntityLargeExplodeFX.Factory());
        this.registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new EntityFirework.Factory());
        this.registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new MobAppearance.Factory());
    }
    
    public void clearEffects(final World worldObj) {
        this.worldObj = worldObj;
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < (0xAC ^ 0xA8)) {
            int j = "".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (j < "  ".length()) {
                this.fxLayers[i][j].clear();
                ++j;
            }
            ++i;
        }
        this.particleEmitters.clear();
    }
}

package com.client.glowclient;

import net.minecraftforge.fml.relauncher.*;
import mcp.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.profiler.*;
import net.minecraft.client.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.chunk.*;
import org.lwjgl.util.vector.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;

@SideOnly(Side.CLIENT)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class JB extends RenderGlobal
{
    private int V;
    private double p;
    private Pc W;
    private int u;
    private oB S;
    private Set<RenderChunk> z;
    private int o;
    private double q;
    public static final int U = 66;
    private boolean v;
    private double s;
    private double Y;
    public static final int y = 69696;
    private double R;
    private int w;
    public static final int P = 2;
    private int x;
    private E m;
    private double C;
    private XA J;
    private final RenderManager E;
    private double h;
    private int D;
    private final Profiler j;
    private int F;
    public static final int I = 32;
    private BB e;
    private int a;
    private final wc i;
    private int g;
    private double l;
    public static final JB K;
    private ChunkRenderDispatcher c;
    private final Minecraft k;
    private static final cC H;
    private double f;
    private List<MC> M;
    private double G;
    private boolean d;
    public static final int L = 16;
    private Set<RA> A;
    private double B;
    private static final WB b;
    
    public void updateChunks(final long n) {
        this.v |= this.c.runChunkUploads(n);
        final Iterator<RenderChunk> iterator2;
        Iterator<RenderChunk> iterator = iterator2 = this.z.iterator();
        RenderChunk renderChunk;
        while (iterator.hasNext() && this.c.updateChunkLater(renderChunk = iterator2.next())) {
            final Iterator<RenderChunk> iterator3 = iterator2;
            renderChunk.clearNeedsUpdate();
            iterator3.remove();
            if (n - System.nanoTime() < 0L) {
                break;
            }
            iterator = iterator2;
        }
        this.v |= this.W.runChunkUploads(n);
        final Iterator<RA> iterator5;
        Iterator<RA> iterator4 = iterator5 = this.A.iterator();
        RA ra;
        while (iterator4.hasNext() && this.W.updateChunkLater((RenderChunk)(ra = iterator5.next()))) {
            final Iterator<RA> iterator6 = iterator5;
            ra.clearNeedsUpdate();
            iterator6.remove();
            if (n - System.nanoTime() < 0L) {
                break;
            }
            iterator4 = iterator5;
        }
    }
    
    public void notifyBlockUpdate(final World world, final BlockPos blockPos, final IBlockState blockState, final IBlockState blockState2, final int n) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.M(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1, (n & 0x8) != 0x0);
    }
    
    public boolean hasCloudFog(final double n, final double n2, final double n3, final float n4) {
        return false;
    }
    
    private Set<EnumFacing> M(final BlockPos blockPos) {
        final VisGraph visGraph = new VisGraph();
        final BlockPos blockPos2 = new BlockPos(blockPos.getX() & 0xFFFFFFF0, blockPos.getY() & 0xFFFFFFF0, blockPos.getZ() & 0xFFFFFFF0);
        final int n = 15;
        final Iterator iterator2;
        Iterator<BlockPos$MutableBlockPos> iterator = (Iterator<BlockPos$MutableBlockPos>)(iterator2 = BlockPos.getAllInBoxMutable(blockPos2, blockPos2.add(n, n, n)).iterator());
        while (iterator.hasNext()) {
            final BlockPos$MutableBlockPos opaqueCube = iterator2.next();
            if (!this.J.getBlockState((BlockPos)opaqueCube).isOpaqueCube()) {
                iterator = (Iterator<BlockPos$MutableBlockPos>)iterator2;
            }
            else {
                visGraph.setOpaqueCube((BlockPos)opaqueCube);
                iterator = (Iterator<BlockPos$MutableBlockPos>)iterator2;
            }
        }
        return (Set<EnumFacing>)visGraph.getVisibleFacings(blockPos);
    }
    
    public void playEvent(final EntityPlayer entityPlayer, final int n, final BlockPos blockPos, final int n2) {
    }
    
    public void setDisplayListEntitiesDirty() {
        this.v = true;
    }
    
    public JB(final Minecraft k) {
        final int n = 3553;
        final int f = 0;
        final boolean v = true;
        final boolean d = false;
        final int g = -1;
        final Pc pc = null;
        final double n2 = Double.MIN_VALUE;
        final double n3 = Double.MIN_VALUE;
        final double r = Double.MIN_VALUE;
        final int d2 = Integer.MIN_VALUE;
        final int n4 = Integer.MIN_VALUE;
        final double n5 = Double.MIN_VALUE;
        final double l = Double.MIN_VALUE;
        final BB e = null;
        final int n6 = 69696;
        super(k);
        this.i = new wc();
        this.z = (Set<RenderChunk>)Sets.newLinkedHashSet();
        this.A = (Set<RA>)Sets.newLinkedHashSet();
        this.M = (List<MC>)Lists.newArrayListWithCapacity(n6);
        this.e = e;
        this.l = l;
        this.B = n5;
        this.G = n5;
        this.w = n4;
        this.u = n4;
        this.D = d2;
        this.R = r;
        this.f = n3;
        this.s = n3;
        this.p = n2;
        this.q = n2;
        this.c = pc;
        this.W = pc;
        this.g = g;
        this.d = d;
        this.v = v;
        this.F = f;
        this.k = k;
        this.j = k.profiler;
        this.E = k.getRenderManager();
        GlStateManager.glTexParameteri(n, 10242, 10497);
        GlStateManager.glTexParameteri(3553, 10243, 10497);
        GlStateManager.bindTexture(0);
        this.d = OpenGlHelper.useVbo();
        if (this.d) {
            this.D();
            return;
        }
        this.A();
    }
    
    public void onEntityAdded(final Entity entity) {
    }
    
    public void setWorldAndLoadRenderers(@Nullable final XA xa) {
        if (this.J != null) {
            this.J.removeEventListener((IWorldEventListener)this);
        }
        final int d = Integer.MIN_VALUE;
        final int n = Integer.MIN_VALUE;
        final double n2 = Double.MIN_VALUE;
        this.l = Double.MIN_VALUE;
        this.B = n2;
        this.G = n2;
        this.w = n;
        this.u = n;
        this.D = d;
        this.E.setWorld((World)xa);
        if ((this.J = xa) != null) {
            xa.addEventListener((IWorldEventListener)this);
            this.loadRenderers();
            return;
        }
        this.z.clear();
        this.A.clear();
        this.M.clear();
        if (this.e != null) {
            this.e.deleteGlResources();
        }
        this.e = null;
        if (this.c != null) {
            this.c.stopWorkerThreads();
        }
        this.c = null;
        if (this.W != null) {
            this.W.stopWorkerThreads();
        }
        this.W = null;
    }
    
    static {
        K = new JB(Minecraft.getMinecraft());
        H = new cC("schematica", null, "shaders/alpha.frag");
        b = new WB();
    }
    
    private void M(final XA j, final float n) {
        if (this.J != j) {
            this.J = j;
            this.loadRenderers();
        }
        JB.b.D(eb.l).D(this.J.L.b, this.J.L.A, this.J.L.B);
        if (OpenGlHelper.shadersSupported && SC.W) {
            GL20.glUseProgram(JB.H.M());
            GL20.glUniform1f(GL20.glGetUniformLocation(JB.H.M(), (CharSequence)"alpha_multiplier"), SC.U);
        }
        this.M(n, System.nanoTime() + 1000000000 / Math.max(Minecraft.getDebugFPS(), 30));
        if (OpenGlHelper.shadersSupported && SC.W) {
            GL20.glUseProgram(0);
        }
    }
    
    public void playRecord(final SoundEvent soundEvent, final BlockPos blockPos) {
    }
    
    public String getDebugInfoRenders() {
        final int length = this.e.renderChunks.length;
        final int renderedChunks = this.getRenderedChunks();
        final Object[] array = new Object[5];
        final int n = 2;
        final Object[] array2 = array;
        final int n2 = 1;
        array[0] = renderedChunks;
        array2[n2] = length;
        array2[n] = (this.k.renderChunksMany ? "(s) " : "");
        final String s = ":\u0015Y\n\u001d\u0000\\KY\n\nkC\u000f\\KU\u000f\\\\";
        final int n3 = 4;
        final Object[] array3 = array;
        array3[3] = this.g;
        array3[n3] = this.c.getDebugInfo();
        return String.format(FC.M(s), array);
    }
    
    private void M(final XA xa, final boolean b) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        final TC m;
        final TC tc = m = TC.M();
        tc.setTranslation(-eb.l.A, -eb.l.b, -eb.l.L);
        tc.setDelta(SC.Ja);
        if (eb.A) {
            final TC tc2 = m;
            tc2.beginQuads();
            tc2.drawCuboid(eb.H, 63, 1069481984);
            tc2.drawCuboid(eb.k, 63, 1056964799);
            tc2.draw();
        }
        m.beginLines();
        if (eb.A) {
            final TC tc3 = m;
            tc3.drawCuboid(eb.H, 63, 1069481984);
            tc3.drawCuboid(eb.k, 63, 1056964799);
            tc3.drawCuboid(eb.G, eb.c, 63, 2130755328);
        }
        if (b) {
            this.i.set(xa.L.b + xa.getWidth() - 1, xa.L.A + xa.getHeight() - 1, xa.L.B + xa.getLength() - 1);
            m.drawCuboid(xa.L, this.i, 63, 2143223999);
        }
        m.draw();
        GlStateManager.depthMask(false);
        final boolean b2 = true;
        this.S.renderOverlay();
        GlStateManager.depthMask(b2);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }
    
    public void onResourceManagerReload(final IResourceManager resourceManager) {
    }
    
    public void setWorldAndLoadRenderers(@Nullable final WorldClient worldClient) {
        if (worldClient instanceof XA) {
            this.setWorldAndLoadRenderers((XA)worldClient);
            return;
        }
        this.setWorldAndLoadRenderers(null);
    }
    
    private void M(final float n, final long n2) {
        GlStateManager.enableCull();
        this.j.endStartSection("culling");
        final Frustum frustum = new Frustum();
        final Entity renderViewEntity = this.k.getRenderViewEntity();
        final double a = JB.b.A;
        final double b = JB.b.b;
        final double l = JB.b.L;
        final int n3 = 7425;
        frustum.setPosition(a, b, l);
        GlStateManager.shadeModel(n3);
        this.j.endStartSection("prepareterrain");
        this.k.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        RenderHelper.disableStandardItemLighting();
        this.j.endStartSection("terrain_setup");
        this.setupTerrain(renderViewEntity, n, (ICamera)frustum, this.F++, this.M(a, b, l));
        final int n4 = 5888;
        this.j.endStartSection("updatechunks");
        this.updateChunks(n2 / 2L);
        this.j.endStartSection("terrain");
        GlStateManager.matrixMode(n4);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.renderBlockLayer(BlockRenderLayer.SOLID, n, 2, renderViewEntity);
        this.renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, n, 2, renderViewEntity);
        final ITextureObject texture = this.k.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        final boolean b2 = false;
        texture.setBlurMipmap(b2, b2);
        this.renderBlockLayer(BlockRenderLayer.CUTOUT, n, 2, renderViewEntity);
        final int n5 = 770;
        final int n6 = 7425;
        final int n7 = 5888;
        final int n8 = 770;
        final int n9 = 7424;
        this.k.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(n9);
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.j.endStartSection("entities");
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(n8, 771, 1, 0);
        GlStateManager.disableBlend();
        RenderHelper.disableStandardItemLighting();
        this.M();
        GlStateManager.matrixMode(n7);
        GlStateManager.popMatrix();
        GlStateManager.enableCull();
        GlStateManager.alphaFunc(516, 0.1f);
        this.k.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.shadeModel(n6);
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        this.j.endStartSection("translucent");
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(n5, 771, 1, 0);
        this.renderBlockLayer(BlockRenderLayer.TRANSLUCENT, n, 2, renderViewEntity);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.shadeModel(7424);
        GlStateManager.enableCull();
    }
    
    public void renderEntities(final Entity entity, final ICamera camera, final float n) {
        final int o = 0;
        final int n2 = 0;
        final int v = 0;
        this.j.startSection("prepare");
        TileEntityRendererDispatcher.instance.prepare((World)this.J, this.k.getTextureManager(), this.k.fontRenderer, entity, this.k.objectMouseOver, n);
        this.E.cacheActiveRenderInfo((World)this.J, this.k.fontRenderer, entity, this.k.pointedEntity, this.k.gameSettings, n);
        this.V = v;
        this.a = n2;
        this.x = n2;
        this.o = o;
        final double a = JB.b.A;
        final double b = JB.b.b;
        final double l = JB.b.L;
        TileEntityRendererDispatcher.staticPlayerX = a;
        TileEntityRendererDispatcher.staticPlayerY = b;
        TileEntityRendererDispatcher.staticPlayerZ = l;
        TileEntityRendererDispatcher.instance.entityX = a;
        TileEntityRendererDispatcher.instance.entityY = b;
        TileEntityRendererDispatcher.instance.entityZ = l;
        this.E.setRenderPosition(a, b, l);
        this.k.entityRenderer.enableLightmap();
        this.j.endStartSection("blockentities");
        RenderHelper.enableStandardItemLighting();
        TileEntityRendererDispatcher.instance.preDrawBatch();
        final Iterator<MC> iterator = this.M.iterator();
        while (iterator.hasNext()) {
            final Iterator iterator2 = iterator.next().A.getCompiledChunk().getTileEntities().iterator();
        Label_0249:
            while (true) {
                Iterator<TileEntity> iterator3 = (Iterator<TileEntity>)iterator2;
                while (iterator3.hasNext()) {
                    final TileEntity tileEntity2;
                    final TileEntity tileEntity = tileEntity2 = iterator2.next();
                    final AxisAlignedBB renderBoundingBox = tileEntity.getRenderBoundingBox();
                    final int n3 = 0;
                    ++this.x;
                    if (!tileEntity.shouldRenderInPass(n3) || !camera.isBoundingBoxInFrustum(renderBoundingBox)) {
                        continue Label_0249;
                    }
                    if (!this.k.world.isAirBlock(tileEntity2.getPos().add((Vec3i)this.J.L))) {
                        iterator3 = (Iterator<TileEntity>)iterator2;
                    }
                    else {
                        TileEntityRendererDispatcher.instance.render(tileEntity2, n, -1);
                        iterator3 = (Iterator<TileEntity>)iterator2;
                        ++this.o;
                    }
                }
                break;
            }
        }
        TileEntityRendererDispatcher.instance.drawBatch(0);
        this.k.entityRenderer.disableLightmap();
        this.j.endSection();
    }
    
    public void stopChunkUpdates() {
        this.z.clear();
        this.A.clear();
        this.c.stopChunkUpdates();
        this.W.stopChunkUpdates();
    }
    
    public void renderWorldBorder(final Entity entity, final float n) {
    }
    
    public void makeEntityOutlineShader() {
    }
    
    public void markBlockRangeForRenderUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.M(n - 1, n2 - 1, n3 - 1, n4 + 1, n5 + 1, n6 + 1, true);
    }
    
    private boolean M(final double n, final double n2, final double n3) {
        return n >= -1.0 && n2 >= -1.0 && n3 >= -1.0 && n <= this.J.getWidth() && n2 <= this.J.getHeight() && n3 <= this.J.getLength();
    }
    
    public void notifyLightSet(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.M(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1, true);
    }
    
    public void broadcastSound(final int n, final BlockPos blockPos, final int n2) {
    }
    
    public void deleteAllDisplayLists() {
    }
    
    public void drawBlockDamageTexture(final Tessellator tessellator, final BufferBuilder bufferBuilder, final Entity entity, final float n) {
    }
    
    public void spawnParticle(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
    }
    
    public int renderBlockLayer(final BlockRenderLayer blockRenderLayer, final double n, final int n2, final Entity entity) {
        RenderHelper.disableStandardItemLighting();
        if (blockRenderLayer == BlockRenderLayer.TRANSLUCENT) {
            this.j.startSection("translucent_sort");
            final double a = JB.b.A;
            final double b = JB.b.b;
            final double l = JB.b.L;
            final double n3 = a - this.C;
            final double n4 = b - this.h;
            final double n5 = l - this.Y;
            final double n6 = n3;
            final double n7 = n6 * n6;
            final double n8 = n4;
            final double n9 = n7 + n8 * n8;
            final double n10 = n5;
            if (n9 + n10 * n10 > 1.0) {
                final int n11 = 0;
                final double y = l;
                final double h = b;
                this.C = a;
                this.h = h;
                this.Y = y;
                int n12 = n11;
                final Iterator<MC> iterator = this.M.iterator();
            Label_0120:
                while (true) {
                    Iterator<MC> iterator2 = iterator;
                    while (iterator2.hasNext()) {
                        final MC mc;
                        if (!(mc = iterator.next()).A.compiledChunk.isLayerStarted(blockRenderLayer)) {
                            continue Label_0120;
                        }
                        final int n13 = n12;
                        final int n14 = 15;
                        ++n12;
                        if (n13 >= n14) {
                            iterator2 = iterator;
                        }
                        else {
                            this.c.updateTransparencyLater(mc.A);
                            this.W.updateTransparencyLater((RenderChunk)mc.b);
                            iterator2 = iterator;
                        }
                    }
                    break;
                }
            }
            this.j.endSection();
        }
        this.j.startSection("filterempty");
        int n15 = 0;
        final boolean b2 = blockRenderLayer == BlockRenderLayer.TRANSLUCENT;
        final int n16 = b2 ? (this.M.size() - 1) : 0;
        final int n17 = b2 ? -1 : this.M.size();
        final int n18 = b2 ? -1 : 1;
        int n19;
        int i = n19 = n16;
        while (i != n17) {
            final MC mc2 = this.M.get(n19);
            final RenderChunk a2 = mc2.A;
            final RA b3 = mc2.b;
            if (!a2.getCompiledChunk().isLayerEmpty(blockRenderLayer)) {
                final oB s = this.S;
                final RenderChunk renderChunk = a2;
                ++n15;
                s.addRenderChunk(renderChunk, blockRenderLayer);
            }
            int n20 = 0;
            Label_0390: {
                if (b2 && b3 != null) {
                    if (b3.getCompiledChunk().isLayerEmpty(blockRenderLayer)) {
                        n20 = n19;
                        break Label_0390;
                    }
                    ++n15;
                    this.S.addRenderOverlay(b3);
                }
                n20 = n19;
            }
            i = (n19 = n20 + n18);
        }
        this.j.endStartSection(new StringBuilder().insert(0, "render_").append(blockRenderLayer).toString());
        this.M(blockRenderLayer);
        this.j.endSection();
        return n15;
    }
    
    private void A() {
        this.S = new Wb();
        this.m = new mb(this);
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent renderWorldLastEvent) {
        final EntityPlayerSP player;
        if ((player = this.k.player) != null) {
            final EntityPlayerSP entityPlayerSP = player;
            this.j.startSection("schematica");
            eb.M((EntityPlayer)entityPlayerSP, renderWorldLastEvent.getPartialTicks());
            final XA g;
            final boolean b2;
            final boolean b = b2 = ((g = eb.g) != null && g.d);
            this.j.startSection("schematic");
            if (b2) {
                GlStateManager.pushMatrix();
                this.M(g, renderWorldLastEvent.getPartialTicks());
                GlStateManager.popMatrix();
            }
            this.j.endStartSection("guide");
            if (eb.A || b) {
                GlStateManager.pushMatrix();
                this.M(g, b);
                GlStateManager.popMatrix();
            }
            this.j.endSection();
            this.j.endSection();
        }
    }
    
    public int getRenderedChunks() {
        int n = 0;
        final Iterator<MC> iterator = this.M.iterator();
    Label_0012:
        while (true) {
            Iterator<MC> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final CompiledChunk compiledChunk;
                if ((compiledChunk = iterator.next().A.compiledChunk) == CompiledChunk.DUMMY) {
                    continue Label_0012;
                }
                if (compiledChunk.isEmpty()) {
                    iterator2 = iterator;
                }
                else {
                    ++n;
                    iterator2 = iterator;
                }
            }
            break;
        }
        return n;
    }
    
    public String getDebugInfoEntities() {
        return String.format("E: %d/%d", this.a, this.V);
    }
    
    public Vector3f getViewVector(final Entity entity, final double n) {
        return super.getViewVector(entity, n);
    }
    
    public void sendBlockBreakProgress(final int n, final BlockPos blockPos, final int n2) {
    }
    
    public void createBindEntityOutlineFbs(final int n, final int n2) {
    }
    
    public boolean hasNoChunkUpdates() {
        return this.z.isEmpty() && this.c.hasNoChunkUpdates();
    }
    
    public void renderSky(final float n, final int n2) {
    }
    
    private RenderChunk M(final BlockPos blockPos, final RenderChunk renderChunk, final EnumFacing enumFacing) {
        final BlockPos blockPosOffset16 = renderChunk.getBlockPosOffset16(enumFacing);
        if (MathHelper.abs(blockPos.getX() - blockPosOffset16.getX()) > this.g * 16) {
            return null;
        }
        if (blockPosOffset16.getY() < 0 || blockPosOffset16.getY() >= 256) {
            return null;
        }
        if (MathHelper.abs(blockPos.getZ() - blockPosOffset16.getZ()) > this.g * 16) {
            return null;
        }
        return this.e.getRenderChunk(blockPosOffset16);
    }
    
    private void D() {
        this.S = new Sb();
        this.m = new od(this);
    }
    
    public boolean isRenderEntityOutlines() {
        return false;
    }
    
    public void renderClouds(final float n, final int n2, final double n3, final double n4, final double n5) {
    }
    
    private void M() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public void updateClouds() {
    }
    
    public void onEntityRemoved(final Entity entity) {
    }
    
    private RA M(final BlockPos blockPos, final RenderChunk renderChunk, final EnumFacing enumFacing) {
        final BlockPos blockPosOffset16 = renderChunk.getBlockPosOffset16(enumFacing);
        if (MathHelper.abs(blockPos.getX() - blockPosOffset16.getX()) > this.g * 16) {
            return null;
        }
        if (blockPosOffset16.getY() < 0 || blockPosOffset16.getY() >= 256) {
            return null;
        }
        if (MathHelper.abs(blockPos.getZ() - blockPosOffset16.getZ()) > this.g * 16) {
            return null;
        }
        return this.e.getRenderOverlay(blockPosOffset16);
    }
    
    public void spawnParticle(final int n, final boolean b, final boolean b2, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
    }
    
    public void drawSelectionBox(final EntityPlayer entityPlayer, final RayTraceResult rayTraceResult, final int n, final float n2) {
    }
    
    private void M(final BlockRenderLayer blockRenderLayer) {
        this.k.entityRenderer.enableLightmap();
        this.S.renderChunkLayer(blockRenderLayer);
        this.k.entityRenderer.disableLightmap();
    }
    
    public void updateTileEntities(final Collection<TileEntity> collection, final Collection<TileEntity> collection2) {
    }
    
    public void playSoundToAllNearExcept(final EntityPlayer entityPlayer, final SoundEvent soundEvent, final SoundCategory soundCategory, final double n, final double n2, final double n3, final float n4, final float n5) {
    }
    
    public void refresh() {
        this.loadRenderers();
    }
    
    public void loadRenderers() {
        if (this.J != null) {
            if (this.c == null) {
                this.c = new ChunkRenderDispatcher(5);
            }
            if (this.W == null) {
                this.W = new Pc(5);
            }
            this.v = true;
            this.g = SC.c;
            final boolean d = this.d;
            this.d = OpenGlHelper.useVbo();
            JB jb;
            if (d && !this.d) {
                jb = this;
                this.A();
            }
            else {
                if (!d && this.d) {
                    this.D();
                }
                jb = this;
            }
            if (jb.e != null) {
                this.e.deleteGlResources();
            }
            this.stopChunkUpdates();
            (this.e = new BB((World)this.J, this.g, this, this.m)).updateChunkPositions(JB.b.A, JB.b.L);
        }
    }
    
    public void setupTerrain(final Entity entity, final double n, final ICamera camera, final int frameIndex, final boolean b) {
        if (SC.c != this.g || this.d != OpenGlHelper.useVbo()) {
            this.loadRenderers();
        }
        this.j.startSection("camera");
        final double a = JB.b.A;
        final double b2 = JB.b.b;
        final double l = JB.b.L;
        final double n2 = a - this.l;
        final double n3 = b2 - this.B;
        final double n4 = l - this.G;
        final int n5 = MathHelper.floor(a) >> 4;
        final int n6 = MathHelper.floor(b2) >> 4;
        final int n7 = MathHelper.floor(l) >> 4;
        Label_0207: {
            if (this.w == n5 && this.u == n6 && this.D == n7) {
                final double n8 = n2;
                final double n9 = n8 * n8;
                final double n10 = n3;
                final double n11 = n9 + n10 * n10;
                final double n12 = n4;
                if (n11 + n12 * n12 <= 16.0) {
                    break Label_0207;
                }
            }
            final int d = n7;
            final int u = n6;
            final int w = n5;
            final double g = l;
            final double b3 = b2;
            this.l = a;
            this.B = b3;
            this.G = g;
            this.w = w;
            this.u = u;
            this.D = d;
            this.e.updateChunkPositions(a, l);
        }
        this.j.endStartSection("renderlistcamera");
        this.S.initialize(a, b2, l);
        this.j.endStartSection("culling");
        final BlockPos blockPos = new BlockPos(a, b2 + entity.getEyeHeight(), l);
        final RenderChunk renderChunk = this.e.getRenderChunk(blockPos);
        final RA renderOverlay = this.e.getRenderOverlay(blockPos);
        this.v = (this.v || !this.z.isEmpty() || a != this.R || b2 != this.f || l != this.s || entity.rotationPitch != this.p || entity.rotationYaw != this.q);
        final double s = l;
        final double f = b2;
        this.R = a;
        this.f = f;
        this.s = s;
        this.p = entity.rotationPitch;
        this.q = entity.rotationYaw;
        this.j.endStartSection("update");
        if (this.v) {
            final int n13 = 69696;
            this.v = false;
            this.M = (List<MC>)Lists.newArrayListWithCapacity(n13);
            final LinkedList linkedList = Lists.newLinkedList();
            boolean renderChunksMany = this.k.renderChunksMany;
            JB jb = null;
            Label_0790: {
                if (renderChunk == null) {
                    final int n14 = (blockPos.getY() > 0) ? 248 : 8;
                    int n15;
                    int i = n15 = -this.g;
                    while (i <= this.g) {
                        int n16;
                        int j = n16 = -this.g;
                        while (j <= this.g) {
                            final BlockPos blockPos2 = new BlockPos((n15 << 4) + 8, n14, (n16 << 4) + 8);
                            final RenderChunk renderChunk2 = this.e.getRenderChunk(blockPos2);
                            final RA renderOverlay2 = this.e.getRenderOverlay(blockPos2);
                            if (renderChunk2 != null && camera.isBoundingBoxInFrustum(renderChunk2.boundingBox)) {
                                renderChunk2.setFrameIndex(frameIndex);
                                renderOverlay2.setFrameIndex(frameIndex);
                                linkedList.add(new MC(this, renderChunk2, renderOverlay2, null, 0));
                            }
                            j = ++n16;
                        }
                        i = ++n15;
                    }
                }
                else {
                    boolean b4 = false;
                    final MC mc = new MC(this, renderChunk, renderOverlay, null, 0);
                    final Set<EnumFacing> m;
                    if ((m = this.M(blockPos)).size() == 1) {
                        final Vector3f viewVector = this.getViewVector(entity, n);
                        m.remove(EnumFacing.getFacingFromVector(viewVector.x, viewVector.y, viewVector.z).getOpposite());
                    }
                    if (m.isEmpty()) {
                        b4 = true;
                    }
                    if (b4 && !b) {
                        jb = this;
                        this.M.add(mc);
                        break Label_0790;
                    }
                    if (b && this.J.getBlockState(blockPos).isOpaqueCube()) {
                        renderChunksMany = false;
                    }
                    renderChunk.setFrameIndex(frameIndex);
                    renderOverlay.setFrameIndex(frameIndex);
                    linkedList.add(mc);
                }
                jb = this;
            }
            jb.j.startSection("iteration");
            while (!linkedList.isEmpty()) {
                final MC mc3;
                final MC mc2 = mc3 = linkedList.poll();
                final RenderChunk a2 = mc2.A;
                final EnumFacing g2 = mc2.G;
                this.M.add(mc3);
                final EnumFacing[] values = EnumFacing.VALUES;
                final int length = values.length;
                int n17;
                int k = n17 = 0;
                while (k < length) {
                    final EnumFacing enumFacing = values[n17];
                    final RenderChunk m2 = this.M(blockPos, a2, enumFacing);
                    final RA m3 = this.M(blockPos, a2, enumFacing);
                    if ((!renderChunksMany || !mc3.B.contains(enumFacing.getOpposite())) && (!renderChunksMany || g2 == null || a2.getCompiledChunk().isVisible(g2.getOpposite(), enumFacing)) && m2 != null && m2.setFrameIndex(frameIndex) && camera.isBoundingBoxInFrustum(m2.boundingBox)) {
                        final MC mc4;
                        (mc4 = new MC(this, m2, m3, enumFacing, mc3.d + 1)).B.addAll(mc3.B);
                        mc4.B.add(enumFacing);
                        linkedList.add(mc4);
                    }
                    k = ++n17;
                }
            }
            this.j.endSection();
        }
        this.j.endStartSection("rebuild");
        final Set<RenderChunk> z = this.z;
        final Set<RA> a3 = this.A;
        this.z = (Set<RenderChunk>)Sets.newLinkedHashSet();
        this.A = (Set<RA>)Sets.newLinkedHashSet();
        for (final MC mc5 : this.M) {
            final RenderChunk a4 = mc5.A;
            final RA b5 = mc5.b;
            if (a4.needsUpdate() || z.contains(a4)) {
                this.v = true;
                this.z.add(a4);
            }
            if (b5.needsUpdate() || a3.contains(b5)) {
                this.v = true;
                this.A.add(b5);
            }
        }
        this.z.addAll(z);
        this.A.addAll(a3);
        this.j.endSection();
    }
    
    private void M(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        if (this.J == null) {
            return;
        }
        final wc l = this.J.L;
        this.e.markBlocksForUpdate(n - l.b, n2 - l.A, n3 - l.B, n4 - l.b, n5 - l.A, n6 - l.B, b);
    }
    
    public void renderEntityOutlineFramebuffer() {
    }
    
    public String getDebugInfoTileEntities() {
        return String.format("TE: %d/%d", this.o, this.x);
    }
}

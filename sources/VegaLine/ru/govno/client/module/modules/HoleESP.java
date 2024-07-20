/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class HoleESP
extends Module {
    final List<Hole> holesList = new ArrayList<Hole>();
    private boolean callRenderSidePoses;
    private final AnimationUtils alphaMod = new AnimationUtils(0.0f, 0.0f, 0.1f);
    int checkTick = 0;
    private final List<HoledSide> allSides = Arrays.asList(HoledSide.XP, HoledSide.XM, HoledSide.ZP, HoledSide.ZM);
    private final List<RenderSidePos> renderSidePoses = new ArrayList<RenderSidePos>();

    public HoleESP() {
        super("HoleESP", 0, Module.Category.RENDER);
        this.settings.add(new Settings("EspMode", "Box", (Module)this, new String[]{"Box", "Gradient"}));
        this.settings.add(new Settings("ColorGood", ColorUtils.getColor(40, 255, 20, 155), (Module)this));
        this.settings.add(new Settings("ColorNormal", ColorUtils.getColor(205, 40, 255, 155), (Module)this));
        this.settings.add(new Settings("ColorOnPlayer", ColorUtils.getColor(255, 185, 40, 155), (Module)this));
        this.settings.add(new Settings("PlayersHolesides", true, (Module)this));
        this.settings.add(new Settings("SidesColor", ColorUtils.getColor(200, 145, 200, 175), (Module)this, () -> this.currentBooleanValue("PlayersHolesides")));
    }

    @Override
    public void alwaysRender3D() {
        if ((double)this.alphaMod.getAnim() > 0.01) {
            this.draw3d(this.alphaMod.getAnim());
        }
    }

    @Override
    public void alwaysUpdate() {
        float f = this.alphaMod.to = this.actived ? 1.0f : 0.0f;
        if ((double)this.alphaMod.getAnim() > 0.01) {
            ++this.checkTick;
            if (this.checkTick % 5 == 0) {
                float[] ranges = this.getRanges();
                this.collectHolesArround(this.getEntityBlockPos(this.getMe()), ranges[0], ranges[1], ranges[2]);
            } else if (this.checkTick % 5 == 3) {
                this.endCollectHolesArround();
            }
        }
        this.callRenderSidePoses = this.ifCanSetupRenderSidePoses(this.currentBooleanValue("PlayersHolesides"), 16.0);
    }

    private float[] getRanges() {
        return new float[]{9.0f, 2.0f, 2.0f};
    }

    private void drawHoleBox(Hole hole, float alphaPC, boolean isBoxRender, int color) {
        int colorOutline = ColorUtils.swapAlpha(color, (float)(ColorUtils.getAlphaFromColor(color) * (isBoxRender ? 1 : 2)) * alphaPC * hole.alpha.getAnim());
        int colorFill = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) * (isBoxRender ? 0.4f : 0.65f) * alphaPC * hole.alpha.getAnim());
        AxisAlignedBB aabbHole = new AxisAlignedBB(hole.pos, (isBoxRender ? 0.1f : 0.7f) * hole.alpha.getAnim());
        RenderUtils.glColor(color);
        if (isBoxRender) {
            RenderUtils.drawCanisterBox(aabbHole, true, true, true, colorOutline, colorOutline, colorFill);
        } else {
            RenderUtils.drawGradientAlphaBox(aabbHole, true, true, colorOutline, colorFill);
        }
    }

    private void draw3d(float alphaPC) {
        if (this.holesList != null) {
            double glX = RenderManager.viewerPosX;
            double glY = RenderManager.viewerPosY;
            double glZ = RenderManager.viewerPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(1.0E-5f);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDisable(2896);
            HoleESP.mc.entityRenderer.disableLightmap();
            GL11.glShadeModel(7425);
            GL11.glTranslated(-glX, -glY, -glZ);
            boolean isBoxModeRender = this.currentMode("EspMode").equalsIgnoreCase("Box");
            int colorGood = this.currentColorValue("ColorGood");
            int colorNormal = this.currentColorValue("ColorNormal");
            int colorPlayer = this.currentColorValue("ColorOnPlayer");
            List otherPlayers = HoleESP.mc.world.getLoadedEntityList().stream().map(Entity::getOtherPlayerOf).filter(Objects::nonNull).collect(Collectors.toList());
            this.holesList.forEach(hole -> {
                boolean playerIn = otherPlayers.stream().anyMatch(otherPlayer -> this.getEntityBlockPos((Entity)otherPlayer).equals(hole.getPos()));
                int color = playerIn ? colorPlayer : (hole.good ? colorGood : colorNormal);
                this.drawHoleBox((Hole)hole, alphaPC, isBoxModeRender, color);
            });
            GL11.glTranslated(glX, glY, glZ);
            GL11.glLineWidth(1.0f);
            GL11.glShadeModel(7424);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.resetColor();
            GL11.glPopMatrix();
        }
        if (this.callRenderSidePoses) {
            int colorise = this.currentColorValue("SidesColor");
            RenderUtils.setup3dForBlockPos(() -> {
                GlStateManager.disableCull();
                RenderUtils.glColor(ColorUtils.swapAlpha(colorise, (float)ColorUtils.getAlphaFromColor(colorise) * alphaPC * 0.7f));
                this.getRenderSidePoses().forEach(side -> side.drawSide());
                GlStateManager.enableCull();
            }, true);
        }
    }

    final EntityPlayer getMe() {
        return FreeCam.get.actived && FreeCam.fakePlayer != null ? FreeCam.fakePlayer : Minecraft.player;
    }

    final BlockPos getEntityBlockPos(Entity entity) {
        return new BlockPos(entity.posX, entity.posY, entity.posZ);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean isInRanges(BlockPos myPosition, BlockPos holePos, float rangeXZ, float rangeYplus, float rangeYminus) {
        if (!((float)myPosition.getY() + rangeYplus > (float)holePos.getY())) return false;
        if (!((float)myPosition.getY() - rangeYminus <= (float)holePos.getY())) return false;
        Vec3d vec3d = new Vec3d(holePos.getX(), holePos.getY(), holePos.getZ());
        if (!(this.getMe().getDistanceToVec3dXZ(vec3d) <= (double)rangeXZ)) return false;
        return true;
    }

    List<BlockPos> blocksZone(float rangeXZ, float rangeYplus, float rangeYminus) {
        CopyOnWriteArrayList<BlockPos> poses = new CopyOnWriteArrayList<BlockPos>();
        double xzRange = rangeXZ;
        double yRangeUp = rangeYplus;
        double yRangeDown = rangeYminus;
        double xPos = this.getEntityBlockPos(this.getMe()).getX();
        double yPos = this.getEntityBlockPos(this.getMe()).getY();
        double zPos = this.getEntityBlockPos(this.getMe()).getZ();
        BlockPos selfPos = this.getEntityBlockPos(this.getMe());
        for (double x = -xzRange; x < xzRange; x += 1.0) {
            for (double y = -yRangeDown; y < yRangeDown; y += 1.0) {
                for (double z = -xzRange; z < xzRange; z += 1.0) {
                    poses.add(selfPos.add(x, y, z));
                }
            }
        }
        return poses;
    }

    private void collectHolesArround(BlockPos myPosition, float rangeXZ, float rangeYplus, float rangeYminus) {
        BlockPos selfPos = this.getEntityBlockPos(this.getMe());
        float[] ranges = this.getRanges();
        List<BlockPos> toAddHolesPoses = this.blocksZone(rangeXZ, rangeYplus, rangeYminus).stream().filter(pos -> !selfPos.equals(pos)).filter(pos -> this.isInRanges(selfPos, (BlockPos)pos, ranges[0], ranges[1], ranges[2])).filter(pos -> this.isHoled((BlockPos)pos, false, true)).filter(Objects::nonNull).collect(Collectors.toList());
        if (this.holesList.size() < 35) {
            toAddHolesPoses.forEach(pos -> {
                Hole wearHole = new Hole((BlockPos)pos, this.isHoled((BlockPos)pos, true, true));
                if (!this.holesList.contains(wearHole)) {
                    this.holesList.add(wearHole);
                }
            });
        }
    }

    private void endCollectHolesArround() {
        if (this.holesList != null && this.holesList.size() > 0) {
            BlockPos selfPos = this.getEntityBlockPos(this.getMe());
            float[] ranges = this.getRanges();
            List<Hole> toDetachHoles = this.holesList.stream().filter(hole -> !this.isInRanges(selfPos, hole.pos, ranges[0], ranges[1], ranges[2]) || !this.isHoled(hole.pos, hole.good, true) || selfPos.equals(hole.pos)).filter(Objects::nonNull).collect(Collectors.toList());
            toDetachHoles.forEach(hole -> {
                hole.alpha.to = 0.0f;
                hole.toRemove = true;
            });
            this.holesList.removeIf(hole -> hole.toRemove && (double)hole.alpha.getAnim() < 0.01);
        }
    }

    final Block getBlock(BlockPos position) {
        return HoleESP.mc.world.getBlockState(position).getBlock();
    }

    private final boolean isBedrock(BlockPos position) {
        Block state = Blocks.BEDROCK;
        return this.getBlock(position) == state;
    }

    private final boolean isObsidian(BlockPos position) {
        Block state = Blocks.OBSIDIAN;
        return this.getBlock(position) == state;
    }

    private final boolean isCurrentBlock(BlockPos position, boolean goodHole) {
        return goodHole ? this.isBedrock(position) : this.isBedrock(position) || this.isObsidian(position);
    }

    private final boolean isHoled(BlockPos position, boolean goodHole, boolean up1) {
        Block state = Blocks.AIR;
        return this.isCurrentBlock(position.add(1, 0, 0), goodHole) && this.isCurrentBlock(position.add(-1, 0, 0), goodHole) && this.isCurrentBlock(position.add(0, 0, 1), goodHole) && this.isCurrentBlock(position.add(0, 0, -1), goodHole) && Speed.posBlock(position.add(0, -1, 0).getX(), position.add(0, -1, 0).getY(), position.add(0, -1, 0).getZ()) && this.getBlock(position) == state && (this.getBlock(position.add(0, 1, 0)) == state && this.getBlock(position.add(0, 2, 0)) == state || !up1);
    }

    private boolean isHoledNoGoodEntity(EntityLivingBase base) {
        BlockPos pos = BlockUtils.getEntityBlockPos(base);
        return this.isHoled(pos, false, false);
    }

    private List<HoledSide> entityHoledSideList(EntityLivingBase base) {
        BlockPos pos = BlockUtils.getEntityBlockPos(base);
        return this.allSides.stream().filter(side -> this.isObsidian(pos.add(side.getAdded()))).collect(Collectors.toList());
    }

    private List<EntityLivingBase> targetEntitiesToHoleCheck(double range) {
        return HoleESP.mc.world.getLoadedEntityList().stream().map(Entity::getOtherPlayerOf).filter(Objects::nonNull).filter(EntityLivingBase::isEntityAlive).filter(other -> this.isHoledNoGoodEntity((EntityLivingBase)other)).filter(other -> (double)this.getMe().getSmoothDistanceToEntity((Entity)other) <= range).collect(Collectors.toList());
    }

    private boolean ifCanSetupRenderSidePoses(boolean enabled, double range) {
        if (enabled) {
            this.renderSidePoses.clear();
            this.targetEntitiesToHoleCheck(range).forEach(player -> this.entityHoledSideList((EntityLivingBase)player).forEach(side -> this.renderSidePoses.add(new RenderSidePos(BlockUtils.getEntityBlockPos(player), (HoledSide)((Object)((Object)side))))));
        } else if (!this.renderSidePoses.isEmpty()) {
            this.renderSidePoses.clear();
        }
        return enabled && !this.renderSidePoses.isEmpty();
    }

    private List<RenderSidePos> getRenderSidePoses() {
        return this.renderSidePoses;
    }

    private static enum HoledSide {
        XP(new BlockPos(1, 0, 0)),
        XM(new BlockPos(-1, 0, 0)),
        ZP(new BlockPos(0, 0, 1)),
        ZM(new BlockPos(0, 0, -1));

        BlockPos added;

        private HoledSide(BlockPos added) {
            this.added = added;
        }

        BlockPos getAdded() {
            return this.added;
        }
    }

    public class Hole {
        BlockPos pos;
        boolean good;
        boolean toRemove = false;
        AnimationUtils alpha = new AnimationUtils(0.0f, 1.0f, 0.05f);

        public boolean equals(Object obj) {
            if (obj instanceof Hole) {
                Hole hole = (Hole)obj;
                return hole.pos.equals(this.pos);
            }
            return false;
        }

        public Hole(BlockPos pos, boolean good) {
            this.pos = pos;
            this.good = good;
        }

        public void setPos(BlockPos pos) {
            this.pos = pos;
        }

        public BlockPos getPos() {
            return this.pos;
        }
    }

    private class RenderSidePos {
        BlockPos pos;
        HoledSide side;

        public RenderSidePos(BlockPos pos, HoledSide side) {
            this.pos = pos;
            this.side = side;
        }

        public BlockPos getPos() {
            return this.pos;
        }

        public HoledSide getSide() {
            return this.side;
        }

        public void drawSide() {
            GL11.glTranslated(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
            GL11.glTranslated(0.5, 0.75, 0.5);
            GL11.glScaled(0.95, 0.05, 0.95);
            GL11.glTranslated(-0.5, -0.75, -0.5);
            GL11.glBegin(9);
            switch (this.getSide()) {
                case XP: {
                    GL11.glVertex3d(1.0, 0.0, 0.0);
                    GL11.glVertex3d(1.0, 1.0, 0.0);
                    GL11.glVertex3d(1.0, 1.0, 1.0);
                    GL11.glVertex3d(1.0, 0.0, 1.0);
                    break;
                }
                case XM: {
                    GL11.glVertex3d(0.0, 0.0, 0.0);
                    GL11.glVertex3d(0.0, 1.0, 0.0);
                    GL11.glVertex3d(0.0, 1.0, 1.0);
                    GL11.glVertex3d(0.0, 0.0, 1.0);
                    break;
                }
                case ZP: {
                    GL11.glVertex3d(0.0, 0.0, 1.0);
                    GL11.glVertex3d(0.0, 1.0, 1.0);
                    GL11.glVertex3d(1.0, 1.0, 1.0);
                    GL11.glVertex3d(1.0, 0.0, 1.0);
                    break;
                }
                case ZM: {
                    GL11.glVertex3d(0.0, 0.0, 0.0);
                    GL11.glVertex3d(0.0, 1.0, 0.0);
                    GL11.glVertex3d(1.0, 1.0, 0.0);
                    GL11.glVertex3d(1.0, 0.0, 0.0);
                }
            }
            GL11.glEnd();
            GL11.glTranslated(0.5, 0.75, 0.5);
            GL11.glScaled(1.0526315789473684, 20.0, 1.0526315789473684);
            GL11.glTranslated(-0.5, -0.75, -0.5);
            GL11.glTranslated(-this.getPos().getX(), -this.getPos().getY(), -this.getPos().getZ());
        }
    }
}


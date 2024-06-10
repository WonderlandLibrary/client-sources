 package me.vene.skilled.modules.mods.other;
 
 import com.google.common.collect.Iterables;
 import com.google.common.collect.Lists;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Collection;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Timer;
 import org.lwjgl.input.Mouse;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import net.minecraft.block.state.IBlockState;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.init.Blocks;
 import net.minecraft.potion.Potion;
 import net.minecraft.scoreboard.Score;
 import net.minecraft.scoreboard.ScoreObjective;
 import net.minecraft.scoreboard.ScorePlayerTeam;
 import net.minecraft.scoreboard.Scoreboard;
 import net.minecraft.scoreboard.Team;
 import net.minecraft.util.BlockPos;
 import net.minecraft.util.MovingObjectPosition;
 import net.minecraftforge.client.event.MouseEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SumoFences extends Module
{
    private Timer t;
    private final List<String> m;
    private static final List<BlockPos> f_p;
    private Minecraft mc;
    private IBlockState f;
    private int ticks;
    
    public SumoFences() {
        super("Sumo Fences", 0, Category.O);
        this.m = Arrays.asList("Sumo", "Space Mine", "White Crystal");
        this.mc = Minecraft.getMinecraft();
        this.f = Blocks.oak_fence.getDefaultState();
        this.ticks = 0;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        ++this.ticks;
        if (this.ticks % 10 == 0) {
            this.ticks = 0;
            if (this.is()) {
                for (final BlockPos p : SumoFences.f_p) {
                    for (int i = 0; i < 3.0; ++i) {
                        final BlockPos p2 = new BlockPos(p.getX(), p.getY() + i, p.getZ());
                        if (this.mc.theWorld.getBlockState(p2).getBlock() == Blocks.air) {
                            this.mc.theWorld.setBlockState(p2, this.f);
                        }
                    }
                }
            }
        }
    }
    
    public List<String> getPlayersFromScoreboard() {
        final List<String> lines = new ArrayList<String>();
        if (this.mc.theWorld == null) {
            return lines;
        }
        final Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        if (scoreboard == null) {
            return lines;
        }
        final ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective != null) {
            Collection<Score> scores = (Collection<Score>)scoreboard.getSortedScores(objective);
            final List<Score> list = new ArrayList<Score>();
            for (final Score score : scores) {
                if (score != null && score.getPlayerName() != null && !score.getPlayerName().startsWith("#")) {
                    list.add(score);
                }
            }
            if (list.size() > 15) {
                scores = (Collection<Score>)Lists.newArrayList(Iterables.skip((Iterable)list, scores.size() - 15));
            }
            else {
                scores = list;
            }
            for (final Score score : scores) {
                final ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
                lines.add(ScorePlayerTeam.formatPlayerName((Team)team, score.getPlayerName()));
            }
        }
        return lines;
    }
    
    public void swing() {
        final EntityPlayerSP p = this.mc.thePlayer;
        final int armSwingEnd = p.isPotionActive(Potion.digSpeed) ? (6 - (1 + p.getActivePotionEffect(Potion.digSpeed).getAmplifier())) : (p.isPotionActive(Potion.digSlowdown) ? (6 + (1 + p.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
        if (!p.isSwingInProgress || p.swingProgressInt >= armSwingEnd / 2 || p.swingProgressInt < 0) {
            p.swingProgressInt = -1;
            p.isSwingInProgress = true;
        }
    }
    
    @SubscribeEvent
    public void m(final MouseEvent e) {
        if (e.buttonstate && (e.button == 0 || e.button == 1) && this.is()) {
            final MovingObjectPosition mop = this.mc.objectMouseOver;
            if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final int x = mop.getBlockPos().getX();
                final int z = mop.getBlockPos().getZ();
                for (final BlockPos pos : SumoFences.f_p) {
                    if (pos.getX() == x && pos.getZ() == z) {
                        e.setCanceled(true);
                        if (e.button == 0) {
                            this.swing();
                        }
                        Mouse.poll();
                        break;
                    }
                }
            }
        }
    }
    
    private boolean is() {
        for (final String s : this.getPlayersFromScoreboard()) {
            final String l = s;
            if (s.startsWith("Map:")) {
                if (this.m.contains(s.substring(5))) {
                    return true;
                }
                continue;
            }
            else {
                if (s.equals("Mode: Sumo Duel")) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    static {
        f_p = Arrays.asList(new BlockPos(9, 65, -2), new BlockPos(9, 65, -1), new BlockPos(9, 65, 0), new BlockPos(9, 65, 1), new BlockPos(9, 65, 2), new BlockPos(9, 65, 3), new BlockPos(8, 65, 3), new BlockPos(8, 65, 4), new BlockPos(8, 65, 5), new BlockPos(7, 65, 5), new BlockPos(7, 65, 6), new BlockPos(7, 65, 7), new BlockPos(6, 65, 7), new BlockPos(5, 65, 7), new BlockPos(5, 65, 8), new BlockPos(4, 65, 8), new BlockPos(3, 65, 8), new BlockPos(3, 65, 9), new BlockPos(2, 65, 9), new BlockPos(1, 65, 9), new BlockPos(0, 65, 9), new BlockPos(-1, 65, 9), new BlockPos(-2, 65, 9), new BlockPos(-3, 65, 9), new BlockPos(-3, 65, 8), new BlockPos(-4, 65, 8), new BlockPos(-5, 65, 8), new BlockPos(-5, 65, 7), new BlockPos(-6, 65, 7), new BlockPos(-7, 65, 7), new BlockPos(-7, 65, 6), new BlockPos(-7, 65, 5), new BlockPos(-8, 65, 5), new BlockPos(-8, 65, 4), new BlockPos(-8, 65, 3), new BlockPos(-9, 65, 3), new BlockPos(-9, 65, 2), new BlockPos(-9, 65, 1), new BlockPos(-9, 65, 0), new BlockPos(-9, 65, -1), new BlockPos(-9, 65, -2), new BlockPos(-9, 65, -3), new BlockPos(-8, 65, -3), new BlockPos(-8, 65, -4), new BlockPos(-8, 65, -5), new BlockPos(-7, 65, -5), new BlockPos(-7, 65, -6), new BlockPos(-7, 65, -7), new BlockPos(-6, 65, -7), new BlockPos(-5, 65, -7), new BlockPos(-5, 65, -8), new BlockPos(-4, 65, -8), new BlockPos(-3, 65, -8), new BlockPos(-3, 65, -9), new BlockPos(-2, 65, -9), new BlockPos(-1, 65, -9), new BlockPos(0, 65, -9), new BlockPos(1, 65, -9), new BlockPos(2, 65, -9), new BlockPos(3, 65, -9), new BlockPos(3, 65, -8), new BlockPos(4, 65, -8), new BlockPos(5, 65, -8), new BlockPos(5, 65, -7), new BlockPos(6, 65, -7), new BlockPos(7, 65, -7), new BlockPos(7, 65, -6), new BlockPos(7, 65, -5), new BlockPos(8, 65, -5), new BlockPos(8, 65, -4), new BlockPos(8, 65, -3), new BlockPos(9, 65, -3));
    }
}

package us.dev.direkt.module.internal.core.autocheat;

import com.google.common.collect.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import us.dev.api.timing.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.event.internal.events.game.network.EventPreReceivePacket;
import us.dev.direkt.event.internal.events.game.render.EventRender2D;
import us.dev.direkt.event.internal.events.game.world.EventLoadWorld;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.gui.color.ClientColors;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.internal.core.autocheat.anticheat.AntiCheat;
import us.dev.direkt.module.internal.core.autocheat.anticheat.anticheats.*;
import us.dev.direkt.module.internal.core.autocheat.check.Check;
import us.dev.direkt.module.internal.core.autocheat.check.CheckStatus;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Foundry
 */
@ModData(label = "Auto Cheat", aliases = "ac", category = ModCategory.CORE)
public class AutoCheat extends Module {
    private static final Logger logger = Logger.getLogger(AutoCheat.class.getSimpleName());

    private static final UndetectedAntiCheat UNDETECTED_ANTICHEAT = new UndetectedAntiCheat();

    private static final ClassToInstanceMap<AntiCheat> anticheatRegistry = new ImmutableClassToInstanceMap.Builder<AntiCheat>()
            .put(UndetectedAntiCheat.class, UNDETECTED_ANTICHEAT)
            .put(NCPAntiCheat.class, new NCPAntiCheat())
            .put(AACAntiCheat.class, new AACAntiCheat())
            .put(GGuardAntiCheat.class, new GGuardAntiCheat())
            .put(JohnCenaAntiCheat.class, new JohnCenaAntiCheat())
            .build();

    private static final ImmutableMap<String, AntiCheat> pluginAnticheatMapping = new ImmutableMap.Builder<String, AntiCheat>()
            .put("nocheatplus", anticheatRegistry.getInstance(NCPAntiCheat.class))
            .put("aac", anticheatRegistry.getInstance(AACAntiCheat.class))
            .build();

    private static final ImmutableMap<String, AntiCheat> serverAnticheatMapping = new ImmutableMap.Builder<String, AntiCheat>()
            .put("(?i)([a-z]*.badlion.net)", anticheatRegistry.getInstance(GGuardAntiCheat.class))
            .put("(?i)(play.cubecraft.net)", anticheatRegistry.getInstance(NCPAntiCheat.class))
            .put("(?i)(play.primemc.org)", anticheatRegistry.getInstance(JohnCenaAntiCheat.class))
            .put("(?i)(2b2t.net)", anticheatRegistry.getInstance(NCPAntiCheat.class))
            .build();

    private static AntiCheat currentAnticheat = UNDETECTED_ANTICHEAT;

    private final ExecutorService checkExecutor = Executors.newCachedThreadPool();
    private final BlockingQueue<Future<CheckStatus>> checkResults = Queues.newLinkedBlockingQueue();

    private final Timer timer = new Timer();
    private Timer animationTimer = new Timer();
    private int scissorBoxWidth;

    private boolean shouldCheckPlugins, doneScissorBoxing;

	private int chatOffsetVals;

    public AutoCheat() {
        super.addCommand(new Command(Direkt.getInstance().getCommandManager(), getLabel(), getAliases()) {
            @Executes("set|s")
            public String set(String anticheatName) {
                for (AntiCheat a : anticheatRegistry.values()) {
                    if (a.getLabel().equalsIgnoreCase(anticheatName)) {
                        return String.format("Anticheat set to \u00A72%s", (currentAnticheat = a).getLabel());
                    } else {
                        for (String alias : a.getAliases()) {
                            if (alias.equalsIgnoreCase(anticheatName)) {
                                return String.format("Anticheat set to \u00A72%s", (currentAnticheat = a).getLabel());
                            }
                        }
                    }
                }
                return String.format("Invalid Anticheat: %s", anticheatName);
            }

            @Executes("list|l")
            public String list() {
                StringBuilder formattedOut = new StringBuilder("Registered Anticheats: " + System.lineSeparator() + " \u00A78[");
                anticheatRegistry.values().forEach(a -> {
                    formattedOut.append("\u00A79");
                    formattedOut.append(a.getLabel());
                    formattedOut.append("\u00A78, ");
                });
                formattedOut.deleteCharAt(formattedOut.length() - 2);
                formattedOut.setLength(formattedOut.length() - 1);
                formattedOut.append("\u00A78]");
                return formattedOut.toString();
            }

            @Executes( "get|g" )
            public String get() {
                return String.format("AntiCheat: \247c%s", currentAnticheat.getLabel());
            }
        });
        Executors.newSingleThreadExecutor().execute(new Worker());
    }

    @Listener
    protected Link<EventLoadWorld> onWorldLoad = new Link<>(event -> {
        currentAnticheat = UNDETECTED_ANTICHEAT;
        if (!Wrapper.getMinecraft().isSingleplayer()) {
            for (Map.Entry<String, AntiCheat> e : serverAnticheatMapping.entrySet()) {
                if (Pattern.matches(e.getKey(), Wrapper.getMinecraft().getCurrentServerData().serverIP)) {
                    currentAnticheat = e.getValue();
                    return;
                }
            }
            shouldCheckPlugins = true;
            Wrapper.sendPacket(new CPacketTabComplete("/", new BlockPos(0, 0, 0), false));
        }
    });

    @Listener
    protected Link<EventPreReceivePacket> onPreReceivePacket = new Link<>(event -> {
        if (shouldCheckPlugins) {
            final Set<String> detectedPlugins = Sets.newHashSet();
            final String[] matches = ((SPacketTabComplete)event.getPacket()).getMatches();
            for (int length = matches.length, i = 0; i < length; ++i) {
                final String[] pluginsArray = matches[i].split(":");
                String pluginName = pluginsArray[0].substring(1);
                if (pluginsArray.length > 1 && !detectedPlugins.contains(pluginName)) {
                    detectedPlugins.add(pluginName);

                    AntiCheat ac = pluginAnticheatMapping.get(pluginName);
                    if (ac != null) {
                        currentAnticheat = ac;
                        for (Check check : ac.getChecks()) {
                            checkResults.add(checkExecutor.submit(check));
                        }
                    }
                }
            }
            event.setCancelled(true);
            shouldCheckPlugins = false;
        }
    }, new PacketFilter<>(SPacketTabComplete.class));

    @Listener
    protected Link<EventRender2D> onRender2D = new Link<>(event -> {
        final ScaledResolution res = Wrapper.getResolution();
        final FontRenderer font = Wrapper.getFontRenderer();
        int fontRenderingOffset = 20;
        boolean doChatOffset = Wrapper.getMinecraft().currentScreen instanceof GuiChat;
        if(animationTimer.hasReach(6)) {
            if(doChatOffset && chatOffsetVals < 15) {
                chatOffsetVals += 2;
            } if(!doChatOffset && chatOffsetVals > 0)
                chatOffsetVals -= 2;
            animationTimer.reset();
        }
        if (!doneScissorBoxing) {
            final int maxScissorBox = Math.max(font.getStringWidth(this.getLabel()), font.getStringWidth(currentAnticheat.getLabel()));
            if (scissorBoxWidth < maxScissorBox) {
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                OGLRender.prepareScissorBox(res.getScaledWidth() - scissorBoxWidth, res.getScaledHeight() - 2 - fontRenderingOffset, res.getScaledWidth(), res.getScaledHeight());
                updateScissorBox(maxScissorBox);
            } else {
                doneScissorBoxing = true;
            }
        }

        font.drawStringWithShadow(this.getLabel(), res.getScaledWidth() - 1 - font.getStringWidth(this.getLabel()), -chatOffsetVals +  res.getScaledHeight() + 2 - fontRenderingOffset, 0xFFFFFF);
        font.drawStringWithShadow(currentAnticheat.getLabel(), res.getScaledWidth() - 1 - font.getStringWidth(currentAnticheat.getLabel()), -chatOffsetVals + res.getScaledHeight() + 1 - fontRenderingOffset / 2,
                ClientColors.FADING_GREEN.getColor());
        if (!doneScissorBoxing) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
    });

    private void updateScissorBox(int maxWidth) {
        if (timer.hasReach(8)) {
            scissorBoxWidth = Math.min(scissorBoxWidth+6, maxWidth);
        }
    }

    public static boolean hasAnticheat(Class<? extends AntiCheat> anticheatClass) {
        return currentAnticheat.getClass().equals(anticheatClass);
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            for (;;) {
                try {
                    final CheckStatus latestStatus = checkResults.take().get();
                    if (latestStatus.hasPassed()) {
                        currentAnticheat = anticheatRegistry.getInstance(latestStatus.getCheck().getPassConclusion());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

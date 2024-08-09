package xyz.mathax.mathaxclient.systems.modules.render;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalGetToBlock;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.OpenScreenEvent;
import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.WLabel;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WCheckbox;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WMinus;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.screens.EditSystemScreen;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.systems.waypoints.Waypoint;
import xyz.mathax.mathaxclient.systems.waypoints.Waypoints;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.text.ChatUtils;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import xyz.mathax.mathaxclient.settings.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ListIterator;

public class WaypointsModule extends Module {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup deathPositionsSettings = settings.createGroup("Death Positions");

    // General

    public final Setting<Integer> textRenderDistanceSetting = generalSettings.add(new IntSetting.Builder()
            .name("Text render distance")
            .description("Maximum distance from the center of the screen at which text will be rendered.")
            .defaultValue(100)
            .min(0)
            .sliderRange(0, 200)
            .build()
    );

    // Death Positions

    private final Setting<Integer> maxDeathPositionsSetting = deathPositionsSettings.add(new IntSetting.Builder()
            .name("Max death positions")
            .description("Amount of death positions to save. (0 to disable)")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .onChanged(this::cleanDeathWaypoints)
            .build()
    );

    private final Setting<Boolean> deathPositionChatSetting = deathPositionsSettings.add(new BoolSetting.Builder()
            .name("Chat")
            .description("Send a chat message with your position once you die")
            .defaultValue(false)
            .build()
    );

    public WaypointsModule(Category category) {
        super(category, "Waypoints", "Allows you to create waypoints.");
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent event) {
        if (!(event.screen instanceof DeathScreen)) {
            return;
        }

        if (!event.isCancelled()) {
            addDeath(mc.player.getPos());
        }
    }

    public void addDeath(Vec3d deathPos) {
        String time = dateFormat.format(new Date());
        if (deathPositionChatSetting.get()) {
            MutableText text = Text.literal("Died at ");
            text.append(ChatUtils.formatCoords(deathPos));
            text.append(String.format(" on %s.", time));
            info(text);
        }

        if (maxDeathPositionsSetting.get() > 0) {
            Waypoint waypoint = new Waypoint.Builder().name("Death " + time).icon("skull").pos(new BlockPos(deathPos).up(2)).dimension(PlayerUtils.getDimension()).build();
            Waypoints.get().add(waypoint);
        }

        cleanDeathWaypoints(maxDeathPositionsSetting.get());
    }

    private void cleanDeathWaypoints(int max) {
        int oldWaypointC = 0;
        ListIterator<Waypoint> iterator = Waypoints.get().iteratorReverse();
        while (iterator.hasPrevious()) {
            Waypoint waypoint = iterator.previous();
            if (waypoint.nameSetting.get().startsWith("Death ") && "skull".equals(waypoint.iconSetting.get())) {
                oldWaypointC++;
                if (oldWaypointC > max) {
                    Waypoints.get().remove(waypoint);
                }
            }
        }
    }

    @Override
    public WWidget getWidget(Theme theme) {
        if (!Utils.canUpdate()) {
            return theme.label("You need to be in a world.");
        }

        WTable table = theme.table();
        initTable(theme, table);
        return table;
    }

    private void initTable(Theme theme, WTable table) {
        table.clear();

        for (Waypoint waypoint : Waypoints.get()) {
            boolean validDim = Waypoints.checkDimension(waypoint);

            table.add(new WIcon(waypoint));

            WLabel name = table.add(theme.label(waypoint.nameSetting.get())).expandCellX().widget();
            if (!validDim) {
                name.color = Color.GRAY;
            }

            WCheckbox visible = table.add(theme.checkbox(waypoint.visibleSetting.get())).widget();
            visible.action = () -> {
                waypoint.visibleSetting.set(visible.checked);
                Waypoints.get().save();
            };

            WButton edit = table.add(theme.button(GuiRenderer.EDIT)).widget();
            edit.action = () -> mc.setScreen(new EditWaypointScreen(theme, waypoint, null));

            if (validDim) {
                WButton gotoB = table.add(theme.button("Goto")).widget();
                gotoB.action = () -> {
                    IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
                    if (baritone.getPathingBehavior().isPathing()) {
                        baritone.getPathingBehavior().cancelEverything();
                    }

                    baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(waypoint.getPos()));
                };
            }

            WMinus remove = table.add(theme.minus()).widget();
            remove.action = () -> {
                Waypoints.get().remove(waypoint);
                initTable(theme, table);
            };

            table.row();
        }

        table.add(theme.horizontalSeparator()).expandX();
        table.row();

        WButton create = table.add(theme.button("Create")).expandX().widget();
        create.action = () -> mc.setScreen(new EditWaypointScreen(theme, null, () -> initTable(theme, table)));
    }

    private class EditWaypointScreen extends EditSystemScreen<Waypoint> {
        public EditWaypointScreen(Theme theme, Waypoint value, Runnable reload) {
            super(theme, value, reload);
        }

        @Override
        public Waypoint create() {
            return new Waypoint.Builder().pos(mc.player.getBlockPos().up(2)).dimension(PlayerUtils.getDimension()).build();
        }

        @Override
        public boolean save() {
            return !isNew || Waypoints.get().add(value);
        }

        @Override
        public Settings getSettings() {
            return value.settings;
        }
    }

    private static class WIcon extends WWidget {
        private final Waypoint waypoint;

        public WIcon(Waypoint waypoint) {
            this.waypoint = waypoint;
        }

        @Override
        protected void onCalculateSize() {
            double scale = theme.scale(32);

            width = scale;
            height = scale;
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            renderer.post(() -> waypoint.renderIcon(x, y, 1, width));
        }
    }
}

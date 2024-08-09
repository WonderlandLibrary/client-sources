package mathax.client.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.misc.Pool;
import mathax.client.utils.render.color.Color;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.dimension.DimensionType;

import java.util.*;
public class Breadcrumbs extends Module {
    private final Map<Entity, SectionManager> sectionManagers = new HashMap<>();

    private DimensionType lastDimension;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final SettingGroup sgColors = settings.createGroup("Colors");

    // General

    private final Setting<Object2BooleanMap<EntityType<?>>> entities = sgGeneral.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Which entities to leave breadcrumbs for.")
        .defaultValue(EntityType.PLAYER)
        .build()
    );

    private final Setting<Integer> maxSections = sgGeneral.add(new IntSetting.Builder()
        .name("max-sections")
        .description("The maximum number of sections.")
        .defaultValue(100)
        .min(1)
        .sliderRange(1, 5000)
        .build()
    );

    private final Setting<Double> sectionLength = sgGeneral.add(new DoubleSetting.Builder()
        .name("section-length")
        .description("The section length in blocks.")
        .defaultValue(0.5)
        .min(0)
        .sliderRange(0, 1)
        .build()
    );

    // Colors

    public final Setting<SettingColor> selfColor = sgColors.add(new ColorSetting.Builder()
        .name("self")
        .description("Your own color.")
        .defaultValue(new SettingColor(0, 165, 255))
        .build()
    );

    private final Setting<SettingColor> playersColor = sgColors.add(new ColorSetting.Builder()
        .name("players")
        .description("The other player's color.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b))
        .build()
    );

    private final Setting<SettingColor> mobsColor = sgColors.add(new ColorSetting.Builder()
        .name("mobs")
        .description("The mob's color.")
        .defaultValue(new SettingColor(25, 255, 25))
        .build()
    );

    public Breadcrumbs() {
        super(Categories.Render, Items.BREAD, "breadcrumbs", "Displays a trail behind where you have walked.");
    }

    @Override
    public boolean onActivate() {
        populateSectionManagers();

        lastDimension = mc.world.getDimension();
        return false;
    }

    @Override
    public void onDeactivate() {
        sectionManagers.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (lastDimension != mc.world.getDimension()) {
            lastDimension = mc.world.getDimension();
            populateSectionManagers();
        }
        //EW NOBRE EWWWWWWW
        Set<Entity> entities = new HashSet<>(sectionManagers.keySet());
        List<Entity> inWorld = getWorldEntitiesFiltered();
        entities.addAll(inWorld);
        for (Entity entity : entities) {
            if (!inWorld.contains(entity)) {
                sectionManagers.remove(entity);
            } else if (!sectionManagers.containsKey(entity)) {
                sectionManagers.put(entity, new SectionManager(entity));
            }else {
                sectionManagers.get(entity).tick();
            }
        }
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        for (SectionManager sectionManager : sectionManagers.values()) sectionManager.render(event);
    }

    private boolean isFarEnough(Section section) {
        return Math.abs(section.entity.getX() - section.x1) >= sectionLength.get() || Math.abs(section.entity.getY() - section.y1) >= sectionLength.get() || Math.abs(section.entity.getZ() - section.z1) >= sectionLength.get();
    }

    private List<Entity> getWorldEntitiesFiltered() {
        List<Entity> filtered = new ArrayList<>();
        filtered.add((PlayerEntity) mc.player);
        for (Entity entity : mc.world.getEntities()) {
            if (this.entities.get().getBoolean(entity.getType())) {
                filtered.add(entity);
            }
        }
        return filtered;
    }

    private void populateSectionManagers() {
        sectionManagers.clear();
        for (Entity entity : getWorldEntitiesFiltered()) {
            sectionManagers.put(entity, new SectionManager(entity));
        }
    }

    private class Section {
        public float x1, y1, z1;
        public float x2, y2, z2;
        public Entity entity;

        public Section(Entity entity) {
            this.entity = entity;
        }

        public void set1() {
            x1 = (float) entity.getX();
            y1 = (float) entity.getY();
            z1 = (float) entity.getZ();
        }

        public void set2() {
            x2 = (float) entity.getX();
            y2 = (float) entity.getY();
            z2 = (float) entity.getZ();
        }

        public Color getColor() {
            if (entity == mc.player) return selfColor.get();
            if (entity.getType() == EntityType.PLAYER) return playersColor.get();
            return mobsColor.get();
        }

        public void render(Render3DEvent event) {
            event.renderer.line(x1, y1, z1, x2, y2, z2, getColor());
        }
    }

    private class SectionManager{
        private final Pool<Section> pool;
        private final Queue<Section> sections = new ArrayDeque<>();
        private Section section;

        public SectionManager(Entity entity){
            pool = new Pool<>(() -> new Section(entity));
            section = pool.get();
            section.set1();
        }

        public void tick() {
            if (isFarEnough(section)) {
                section.set2();

                if (sections.size() >= maxSections.get()) {
                    Section section = sections.poll();
                    if (section != null) pool.free(section);
                }

                sections.add(section);
                section = pool.get();
                section.set1();
            }
        }

        public void render(Render3DEvent event) {
            int iLast = -1;

            for (Section section : sections) {
                if (iLast == -1) {
                    iLast = event.renderer.lines.vec3(section.x1, section.y1, section.z1).color(section.getColor()).next();
                }

                int i = event.renderer.lines.vec3(section.x2, section.y2, section.z2).color(section.getColor()).next();
                event.renderer.lines.line(iLast, i);
                iLast = i;
            }
        }
    }
}

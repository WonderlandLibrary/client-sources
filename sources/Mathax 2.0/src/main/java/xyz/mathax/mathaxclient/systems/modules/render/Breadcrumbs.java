package xyz.mathax.mathaxclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.misc.Pool;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.world.dimension.DimensionType;
import xyz.mathax.mathaxclient.settings.*;

import java.util.*;

public class Breadcrumbs extends Module {
    private final Map<Entity, SectionManager> sectionManagers = new HashMap<>();

    private DimensionType lastDimension;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup colorSettings = settings.createGroup("Colors");

    // General

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = generalSettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Which entities to leave breadcrumbs for.")
            .defaultValue(EntityType.PLAYER)
            .build()
    );

    private final Setting<Integer> maxSectionsSetting = generalSettings.add(new IntSetting.Builder()
            .name("Max sections")
            .description("The maximum number of sections.")
            .defaultValue(1000)
            .min(1)
            .sliderRange(1, 5000)
            .build()
    );

    private final Setting<Double> sectionLengthSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Section length")
            .description("The section length in blocks.")
            .defaultValue(0.5)
            .sliderRange(0, 1)
            .build()
    );

    // Colors

    public final Setting<SettingColor> selfColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Self")
            .description("Your own color.")
            .defaultValue(new SettingColor(0, 165, 255))
            .build()
    );

    private final Setting<SettingColor> playersColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Players")
            .description("The other player's color.")
            .defaultValue(new SettingColor(Color.MATHAX))
            .build()
    );

    private final Setting<SettingColor> mobsColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Mobs")
            .description("The mob's color.")
            .defaultValue(new SettingColor(25, 255, 25))
            .build()
    );

    public Breadcrumbs(Category category) {
        super(category, "Breadcrumbs", "Displays a trail behind where you have walked.");
    }

    @Override
    public void onEnable() {
        populateSectionManagers();

        lastDimension = mc.world.getDimension();
    }

    @Override
    public void onDisable() {
        sectionManagers.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (lastDimension != mc.world.getDimension()) {
            lastDimension = mc.world.getDimension();
            populateSectionManagers();
        }

        Set<Entity> entities = new HashSet<>(sectionManagers.keySet());
        List<Entity> inWorld = getWorldEntitiesFiltered();
        entities.addAll(inWorld);
        for (Entity entity : entities) {
            if (!inWorld.contains(entity)) {
                sectionManagers.remove(entity);
            } else if (!sectionManagers.containsKey(entity)) {
                sectionManagers.put(entity, new SectionManager(entity));
            } else {
                sectionManagers.get(entity).tick();
            }
        }
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        for (SectionManager sectionManager : sectionManagers.values()) {
            sectionManager.render(event);
        }
    }

    private boolean isFarEnough(Section section) {
        return Math.abs(section.entity.getX() - section.x1) >= sectionLengthSetting.get() || Math.abs(section.entity.getY() - section.y1) >= sectionLengthSetting.get() || Math.abs(section.entity.getZ() - section.z1) >= sectionLengthSetting.get();
    }

    private List<Entity> getWorldEntitiesFiltered() {
        List<Entity> filtered = new ArrayList<>();
        filtered.add(mc.player);
        for (Entity entity : mc.world.getEntities()) {
            if (entitiesSetting.get().getBoolean(entity.getType())) {
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
            if (entity == mc.player) {
                return selfColorSetting.get();
            }

            if (entity.getType() == EntityType.PLAYER) {
                return playersColorSetting.get();
            }

            return mobsColorSetting.get();
        }

        public void render(Render3DEvent event) {
            event.renderer.line(x1, y1, z1, x2, y2, z2, getColor());
        }
    }

    private class SectionManager {
        private final Pool<Section> sectionPool;
        private final Queue<Section> sections = new ArrayDeque<>();
        
        private Section section;

        public SectionManager(Entity entity){
            sectionPool = new Pool<>(() -> new Section(entity));
            section = sectionPool.get();
            section.set1();
        }

        public void tick() {
            if (isFarEnough(section)) {
                section.set2();

                if (sections.size() >= maxSectionsSetting.get()) {
                    Section section = sections.poll();
                    if (section != null) {
                        sectionPool.free(section);
                    }
                }

                sections.add(section);
                section = sectionPool.get();
                section.set1();
            }
        }

        public void render(Render3DEvent event) {
            int last = -1;
            for (Section section : sections) {
                if (last == -1) {
                    last = event.renderer.lines.vec3(section.x1, section.y1, section.z1).color(section.getColor()).next();
                }

                int current = event.renderer.lines.vec3(section.x2, section.y2, section.z2).color(section.getColor()).next();
                event.renderer.lines.line(last, current);
                last = current;
            }
        }
    }
}
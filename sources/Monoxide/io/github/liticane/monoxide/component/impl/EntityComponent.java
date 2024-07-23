package io.github.liticane.monoxide.component.impl;

import io.github.liticane.monoxide.component.api.Component;
import io.github.liticane.monoxide.component.api.ComponentInfo;
import io.github.liticane.monoxide.listener.event.minecraft.game.RunTickEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import net.minecraft.entity.Entity;
import io.github.liticane.monoxide.component.impl.entity.IgnoreList;

import java.util.ArrayList;
import java.util.List;

@ComponentInfo(name = "EntityComponent")
public class EntityComponent extends Component {

    private final List<Entity> ignored = new ArrayList<>();
    private final List<IgnoreList> ignoreLists = new ArrayList<>();

    @Listen
    public void onTick(RunTickEvent runTickEvent) {
        this.ignored.clear();

        if(mc.theWorld == null || mc.thePlayer == null)
            return;

        for(Entity entity : mc.theWorld.loadedEntityList) {
            for(IgnoreList ignoreList : ignoreLists) {
                if(ignoreList.shouldSkipEntity(entity)) {
                    this.ignored.add(entity);
                }
            }
        }
    }

    public void addIgnoreList(IgnoreList ignoreList) {
        this.ignoreLists.add(ignoreList);
    }

    public final boolean isIgnored(Entity entity) {
        return ignored.stream().filter(entity1 -> entity1.getEntityID() == entity.getEntityID()).findFirst().orElse(null) != null;
    }

    public final List<Entity> getIgnored() {
        return ignored;
    }

}

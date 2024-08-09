package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.player.pet.Pet;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.ListValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ModuleInfo(name = "Little Bro", description = "летающий за вами бро.", category = Category.PLAYER)
public class LittleBro extends Module {
    public static final Singleton<LittleBro> singleton = Singleton.create(() -> Module.link(LittleBro.class));
    private final ListValue<Pet.PetTexture> texture = new ListValue<>("Тип", this) {{
        add(Pet.PetTexture.values());
    }};
    private final Pet pet = new Pet();

    @Override
    public String getSuffix() {
        return texture.getValue().getName();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        scriptReset();
    }

    private void scriptReset() {
        pet.getPetController().getScripts().getScript("idle").ifPresent(script -> {
            script.getScriptConstructor().setLoop(true);
            script.getScriptConstructor().cleanup()
                    .addStep(3000,
                            () -> pet.getPetController().idleState(),
                            () -> pet.getCurrentState().equals(Pet.PetState.IDLE)
                    );
        });
    }

    @Override
    public void toggle() {
        super.toggle();
        pet.reset();
    }

    private final Listener<Render3DPosedEvent> onRender3D = pet::onRender3DPosedEvent;
    private final Listener<WorldChangeEvent> onWorldChange = event -> {
        scriptReset();
        pet.reset();
    };
    private final Listener<WorldLoadEvent> onWorldLoad = event -> {
        scriptReset();
        pet.reset();
    };
}

package wtf.shiyeno.modules.impl.render;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;
import wtf.shiyeno.util.render.RenderUtil.Render3D;

@FunctionAnnotation(
        name = "BlockESP",
        type = Type.Render
)
public class BlockESP extends Function {
    private final MultiBoxSetting blocksSelection = new MultiBoxSetting("Выбрать блоки", new BooleanOption[]{new BooleanOption("Сундуки", true), new BooleanOption("Эндер Сундуки", true), new BooleanOption("Спавнера", true), new BooleanOption("Шалкера", true), new BooleanOption("Кровати", true), new BooleanOption("Вагонетка", true)});

    public BlockESP() {
        this.addSettings(new Setting[]{this.blocksSelection});
    }

    public void onEvent(Event event) {
        if (event instanceof EventRender e) {
            if (e.isRender3D()) {
                Iterator var3 = mc.world.loadedTileEntityList.iterator();

                while(var3.hasNext()) {
                    TileEntity t = (TileEntity)var3.next();
                    if (t instanceof ChestTileEntity && this.blocksSelection.get(0)) {
                        Render3D.drawBlockBox(t.getPos(), -1);
                    }

                    if (t instanceof EnderChestTileEntity && this.blocksSelection.get(1)) {
                        Render3D.drawBlockBox(t.getPos(), -1);
                    }

                    if (t instanceof MobSpawnerTileEntity && this.blocksSelection.get(2)) {
                        Render3D.drawBlockBox(t.getPos(), -1);
                    }

                    if (t instanceof ShulkerBoxTileEntity && this.blocksSelection.get(3)) {
                        Render3D.drawBlockBox(t.getPos(), -1);
                    }

                    if (t instanceof BedTileEntity && this.blocksSelection.get(4)) {
                        Render3D.drawBlockBox(t.getPos(), -1);
                    }
                }

                var3 = mc.world.getAllEntities().iterator();

                while(var3.hasNext()) {
                    Entity entity = (Entity)var3.next();
                    if (entity instanceof MinecartEntity && this.blocksSelection.get(5)) {
                        Render3D.drawBlockBox(entity.getPosition(), -1);
                    }
                }
            }
        }
    }
}
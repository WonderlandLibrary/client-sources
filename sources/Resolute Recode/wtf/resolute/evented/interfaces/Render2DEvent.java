package wtf.resolute.evented.interfaces;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.ActiveRenderInfo;
@Getter
@AllArgsConstructor
public class Render2DEvent {
    private final MatrixStack matrix;
    private final ActiveRenderInfo activeRenderInfo;
    private final MainWindow mainWindow;
    private final float partialTicks;
}

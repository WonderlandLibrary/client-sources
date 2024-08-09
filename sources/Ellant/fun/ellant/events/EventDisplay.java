package fun.ellant.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.MainWindow;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDisplay {
    public MatrixStack matrixStack;
    float partialTicks;
    Type type;

    public EventDisplay(MatrixStack matrixStack, float partialTicks) {
        this.matrixStack = matrixStack;
        this.partialTicks = partialTicks;
    }


    public enum Type {
        PRE, POST, HIGH
    }
}

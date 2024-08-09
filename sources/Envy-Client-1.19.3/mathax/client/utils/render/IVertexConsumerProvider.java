package mathax.client.utils.render;

import net.minecraft.client.render.VertexConsumerProvider;

public interface IVertexConsumerProvider extends VertexConsumerProvider {
    void setOffset(double offsetX, double offsetY, double offsetZ);
}

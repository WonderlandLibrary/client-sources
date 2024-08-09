package via;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import im.expensive.Expensive;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;

public class VersionSelectScreen
        extends
        TextFieldWidget {


    public VersionSelectScreen(FontRenderer p_i232260_1_, int p_i232260_2_, int p_i232260_3_, int p_i232260_4_, int p_i232260_5_, ITextComponent p_i232260_6_) {
        super(p_i232260_1_, p_i232260_2_, p_i232260_3_, p_i232260_4_, p_i232260_5_, p_i232260_6_);
        setText("1.16.5");
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if (ProtocolVersion.getClosest(getText()) == null) {
            setTextColor(ColorUtils.rgba(200,20,20,255));
        } else {
            ViaLoadingBase.getInstance().reload(ProtocolVersion.getClosest(getText()));
            setTextColor(ColorUtils.rgba(255,255,255,255));
        }

    }
}


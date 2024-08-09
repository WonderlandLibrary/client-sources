/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;
import via.ViaLoadingBase;

public class VersionSelectScreen
extends TextFieldWidget {
    public VersionSelectScreen(FontRenderer fontRenderer, int n, int n2, int n3, int n4, ITextComponent iTextComponent) {
        super(fontRenderer, n, n2, n3, n4, iTextComponent);
        this.setText("1.16.5");
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        super.render(matrixStack, n, n2, f);
        if (ProtocolVersion.getClosest(this.getText()) == null) {
            this.setTextColor(ColorUtils.rgba(200, 20, 20, 255));
        } else {
            ViaLoadingBase.getInstance().reload(ProtocolVersion.getClosest(this.getText()));
            this.setTextColor(ColorUtils.rgba(255, 255, 255, 255));
        }
    }
}


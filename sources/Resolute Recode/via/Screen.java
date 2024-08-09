package via;

import com.mojang.blaze3d.matrix.MatrixStack;

public interface Screen {
    public void init();

    public void render(MatrixStack var1, int var2, int var3, float var4);

    public boolean mouseClicked(double var1, double var3, int var5);

    public boolean mouseReleased(double var1, double var3, int var5);

    public boolean keyPressed(int var1, int var2, int var3);

    public boolean charTyped(char var1, int var2);

    public void onClose();
}


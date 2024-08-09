/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EditSignScreen
extends Screen {
    private final SignTileEntityRenderer.SignModel signModel = new SignTileEntityRenderer.SignModel();
    private final SignTileEntity tileSign;
    private int updateCounter;
    private int editLine;
    private TextInputUtil textInputUtil;
    private final String[] field_238846_r_ = (String[])IntStream.range(0, 4).mapToObj(signTileEntity::getText).map(ITextComponent::getString).toArray(EditSignScreen::lambda$new$0);

    public EditSignScreen(SignTileEntity signTileEntity) {
        super(new TranslationTextComponent("sign.edit"));
        this.tileSign = signTileEntity;
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, DialogTexts.GUI_DONE, this::lambda$init$1));
        this.tileSign.setEditable(true);
        this.textInputUtil = new TextInputUtil(this::lambda$init$2, this::lambda$init$3, TextInputUtil.getClipboardTextSupplier(this.minecraft), TextInputUtil.getClipboardTextSetter(this.minecraft), this::lambda$init$4);
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        ClientPlayNetHandler clientPlayNetHandler = this.minecraft.getConnection();
        if (clientPlayNetHandler != null) {
            clientPlayNetHandler.sendPacket(new CUpdateSignPacket(this.tileSign.getPos(), this.field_238846_r_[0], this.field_238846_r_[1], this.field_238846_r_[2], this.field_238846_r_[3]));
        }
        this.tileSign.setEditable(false);
    }

    @Override
    public void tick() {
        ++this.updateCounter;
        if (!this.tileSign.getType().isValidBlock(this.tileSign.getBlockState().getBlock())) {
            this.close();
        }
    }

    private void close() {
        this.tileSign.markDirty();
        this.minecraft.displayGuiScreen(null);
    }

    @Override
    public boolean charTyped(char c, int n) {
        this.textInputUtil.putChar(c);
        return false;
    }

    @Override
    public void closeScreen() {
        this.close();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 265) {
            this.editLine = this.editLine - 1 & 3;
            this.textInputUtil.moveCursorToEnd();
            return false;
        }
        if (n != 264 && n != 257 && n != 335) {
            return this.textInputUtil.specialKeyPressed(n) ? true : super.keyPressed(n, n2, n3);
        }
        this.editLine = this.editLine + 1 & 3;
        this.textInputUtil.moveCursorToEnd();
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        int n3;
        int n4;
        String string;
        int n5;
        RenderHelper.setupGuiFlatDiffuseLighting();
        this.renderBackground(matrixStack);
        EditSignScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        matrixStack.push();
        matrixStack.translate(this.width / 2, 0.0, 50.0);
        float f2 = 93.75f;
        matrixStack.scale(93.75f, -93.75f, 93.75f);
        matrixStack.translate(0.0, -1.3125, 0.0);
        BlockState blockState = this.tileSign.getBlockState();
        boolean bl = blockState.getBlock() instanceof StandingSignBlock;
        if (!bl) {
            matrixStack.translate(0.0, -0.3125, 0.0);
        }
        boolean bl2 = this.updateCounter / 6 % 2 == 0;
        float f3 = 0.6666667f;
        matrixStack.push();
        matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
        IRenderTypeBuffer.Impl impl = this.minecraft.getRenderTypeBuffers().getBufferSource();
        RenderMaterial renderMaterial = SignTileEntityRenderer.getMaterial(blockState.getBlock());
        IVertexBuilder iVertexBuilder = renderMaterial.getBuffer(impl, this.signModel::getRenderType);
        this.signModel.signBoard.render(matrixStack, iVertexBuilder, 0xF000F0, OverlayTexture.NO_OVERLAY);
        if (bl) {
            this.signModel.signStick.render(matrixStack, iVertexBuilder, 0xF000F0, OverlayTexture.NO_OVERLAY);
        }
        matrixStack.pop();
        float f4 = 0.010416667f;
        matrixStack.translate(0.0, 0.3333333432674408, 0.046666666865348816);
        matrixStack.scale(0.010416667f, -0.010416667f, 0.010416667f);
        int n6 = this.tileSign.getTextColor().getTextColor();
        int n7 = this.textInputUtil.getEndIndex();
        int n8 = this.textInputUtil.getStartIndex();
        int n9 = this.editLine * 10 - this.field_238846_r_.length * 5;
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        for (n5 = 0; n5 < this.field_238846_r_.length; ++n5) {
            string = this.field_238846_r_[n5];
            if (string == null) continue;
            if (this.font.getBidiFlag()) {
                string = this.font.bidiReorder(string);
            }
            float f5 = -this.minecraft.fontRenderer.getStringWidth(string) / 2;
            this.minecraft.fontRenderer.func_238411_a_(string, f5, n5 * 10 - this.field_238846_r_.length * 5, n6, false, matrix4f, impl, false, 0, 0xF000F0, true);
            if (n5 != this.editLine || n7 < 0 || !bl2) continue;
            n4 = this.minecraft.fontRenderer.getStringWidth(string.substring(0, Math.max(Math.min(n7, string.length()), 0)));
            n3 = n4 - this.minecraft.fontRenderer.getStringWidth(string) / 2;
            if (n7 < string.length()) continue;
            this.minecraft.fontRenderer.func_238411_a_("_", n3, n9, n6, false, matrix4f, impl, false, 0, 0xF000F0, true);
        }
        impl.finish();
        for (n5 = 0; n5 < this.field_238846_r_.length; ++n5) {
            string = this.field_238846_r_[n5];
            if (string == null || n5 != this.editLine || n7 < 0) continue;
            int n10 = this.minecraft.fontRenderer.getStringWidth(string.substring(0, Math.max(Math.min(n7, string.length()), 0)));
            n4 = n10 - this.minecraft.fontRenderer.getStringWidth(string) / 2;
            if (bl2 && n7 < string.length()) {
                EditSignScreen.fill(matrixStack, n4, n9 - 1, n4 + 1, n9 + 9, 0xFF000000 | n6);
            }
            if (n8 == n7) continue;
            n3 = Math.min(n7, n8);
            int n11 = Math.max(n7, n8);
            int n12 = this.minecraft.fontRenderer.getStringWidth(string.substring(0, n3)) - this.minecraft.fontRenderer.getStringWidth(string) / 2;
            int n13 = this.minecraft.fontRenderer.getStringWidth(string.substring(0, n11)) - this.minecraft.fontRenderer.getStringWidth(string) / 2;
            int n14 = Math.min(n12, n13);
            int n15 = Math.max(n12, n13);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            RenderSystem.disableTexture();
            RenderSystem.enableColorLogicOp();
            RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(matrix4f, n14, n9 + 9, 0.0f).color(0, 0, 255, 255).endVertex();
            bufferBuilder.pos(matrix4f, n15, n9 + 9, 0.0f).color(0, 0, 255, 255).endVertex();
            bufferBuilder.pos(matrix4f, n15, n9, 0.0f).color(0, 0, 255, 255).endVertex();
            bufferBuilder.pos(matrix4f, n14, n9, 0.0f).color(0, 0, 255, 255).endVertex();
            bufferBuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferBuilder);
            RenderSystem.disableColorLogicOp();
            RenderSystem.enableTexture();
        }
        matrixStack.pop();
        RenderHelper.setupGui3DDiffuseLighting();
        super.render(matrixStack, n, n2, f);
    }

    private boolean lambda$init$4(String string) {
        return this.minecraft.fontRenderer.getStringWidth(string) <= 90;
    }

    private void lambda$init$3(String string) {
        this.field_238846_r_[this.editLine] = string;
        this.tileSign.setText(this.editLine, new StringTextComponent(string));
    }

    private String lambda$init$2() {
        return this.field_238846_r_[this.editLine];
    }

    private void lambda$init$1(Button button) {
        this.close();
    }

    private static String[] lambda$new$0(int n) {
        return new String[n];
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.network.play.client.CUpdateStructureBlockPacket;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EditStructureScreen
extends Screen {
    private static final ITextComponent field_243355_a = new TranslationTextComponent("structure_block.structure_name");
    private static final ITextComponent field_243356_b = new TranslationTextComponent("structure_block.position");
    private static final ITextComponent field_243357_c = new TranslationTextComponent("structure_block.size");
    private static final ITextComponent field_243358_p = new TranslationTextComponent("structure_block.integrity");
    private static final ITextComponent field_243359_q = new TranslationTextComponent("structure_block.custom_data");
    private static final ITextComponent field_243360_r = new TranslationTextComponent("structure_block.include_entities");
    private static final ITextComponent field_243361_s = new TranslationTextComponent("structure_block.detect_size");
    private static final ITextComponent field_243362_t = new TranslationTextComponent("structure_block.show_air");
    private static final ITextComponent field_243363_u = new TranslationTextComponent("structure_block.show_boundingbox");
    private final StructureBlockTileEntity tileStructure;
    private Mirror mirror = Mirror.NONE;
    private Rotation rotation = Rotation.NONE;
    private StructureMode mode = StructureMode.DATA;
    private boolean ignoreEntities;
    private boolean showAir;
    private boolean showBoundingBox;
    private TextFieldWidget nameEdit;
    private TextFieldWidget posXEdit;
    private TextFieldWidget posYEdit;
    private TextFieldWidget posZEdit;
    private TextFieldWidget sizeXEdit;
    private TextFieldWidget sizeYEdit;
    private TextFieldWidget sizeZEdit;
    private TextFieldWidget integrityEdit;
    private TextFieldWidget seedEdit;
    private TextFieldWidget dataEdit;
    private Button doneButton;
    private Button cancelButton;
    private Button saveButton;
    private Button loadButton;
    private Button rotateZeroDegreesButton;
    private Button rotateNinetyDegreesButton;
    private Button rotate180DegreesButton;
    private Button rotate270DegressButton;
    private Button modeButton;
    private Button detectSizeButton;
    private Button showEntitiesButton;
    private Button mirrorButton;
    private Button showAirButton;
    private Button showBoundingBoxButton;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.0###");

    public EditStructureScreen(StructureBlockTileEntity structureBlockTileEntity) {
        super(new TranslationTextComponent(Blocks.STRUCTURE_BLOCK.getTranslationKey()));
        this.tileStructure = structureBlockTileEntity;
        this.decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    }

    @Override
    public void tick() {
        this.nameEdit.tick();
        this.posXEdit.tick();
        this.posYEdit.tick();
        this.posZEdit.tick();
        this.sizeXEdit.tick();
        this.sizeYEdit.tick();
        this.sizeZEdit.tick();
        this.integrityEdit.tick();
        this.seedEdit.tick();
        this.dataEdit.tick();
    }

    private void func_195275_h() {
        if (this.func_210143_a(StructureBlockTileEntity.UpdateCommand.UPDATE_DATA)) {
            this.minecraft.displayGuiScreen(null);
        }
    }

    private void func_195272_i() {
        this.tileStructure.setMirror(this.mirror);
        this.tileStructure.setRotation(this.rotation);
        this.tileStructure.setMode(this.mode);
        this.tileStructure.setIgnoresEntities(this.ignoreEntities);
        this.tileStructure.setShowAir(this.showAir);
        this.tileStructure.setShowBoundingBox(this.showBoundingBox);
        this.minecraft.displayGuiScreen(null);
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.doneButton = this.addButton(new Button(this.width / 2 - 4 - 150, 210, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$0));
        this.cancelButton = this.addButton(new Button(this.width / 2 + 4, 210, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
        this.saveButton = this.addButton(new Button(this.width / 2 + 4 + 100, 185, 50, 20, new TranslationTextComponent("structure_block.button.save"), this::lambda$init$2));
        this.loadButton = this.addButton(new Button(this.width / 2 + 4 + 100, 185, 50, 20, new TranslationTextComponent("structure_block.button.load"), this::lambda$init$3));
        this.modeButton = this.addButton(new Button(this.width / 2 - 4 - 150, 185, 50, 20, new StringTextComponent("MODE"), this::lambda$init$4));
        this.detectSizeButton = this.addButton(new Button(this.width / 2 + 4 + 100, 120, 50, 20, new TranslationTextComponent("structure_block.button.detect_size"), this::lambda$init$5));
        this.showEntitiesButton = this.addButton(new Button(this.width / 2 + 4 + 100, 160, 50, 20, new StringTextComponent("ENTITIES"), this::lambda$init$6));
        this.mirrorButton = this.addButton(new Button(this.width / 2 - 20, 185, 40, 20, new StringTextComponent("MIRROR"), this::lambda$init$7));
        this.showAirButton = this.addButton(new Button(this.width / 2 + 4 + 100, 80, 50, 20, new StringTextComponent("SHOWAIR"), this::lambda$init$8));
        this.showBoundingBoxButton = this.addButton(new Button(this.width / 2 + 4 + 100, 80, 50, 20, new StringTextComponent("SHOWBB"), this::lambda$init$9));
        this.rotateZeroDegreesButton = this.addButton(new Button(this.width / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, new StringTextComponent("0"), this::lambda$init$10));
        this.rotateNinetyDegreesButton = this.addButton(new Button(this.width / 2 - 1 - 40 - 20, 185, 40, 20, new StringTextComponent("90"), this::lambda$init$11));
        this.rotate180DegreesButton = this.addButton(new Button(this.width / 2 + 1 + 20, 185, 40, 20, new StringTextComponent("180"), this::lambda$init$12));
        this.rotate270DegressButton = this.addButton(new Button(this.width / 2 + 1 + 40 + 1 + 20, 185, 40, 20, new StringTextComponent("270"), this::lambda$init$13));
        this.nameEdit = new TextFieldWidget(this, this.font, this.width / 2 - 152, 40, 300, 20, (ITextComponent)new TranslationTextComponent("structure_block.structure_name")){
            final EditStructureScreen this$0;
            {
                this.this$0 = editStructureScreen;
                super(fontRenderer, n, n2, n3, n4, iTextComponent);
            }

            @Override
            public boolean charTyped(char c, int n) {
                return !this.this$0.isValidCharacterForName(this.getText(), c, this.getCursorPosition()) ? false : super.charTyped(c, n);
            }
        };
        this.nameEdit.setMaxStringLength(64);
        this.nameEdit.setText(this.tileStructure.getName());
        this.children.add(this.nameEdit);
        BlockPos blockPos = this.tileStructure.getPosition();
        this.posXEdit = new TextFieldWidget(this.font, this.width / 2 - 152, 80, 80, 20, new TranslationTextComponent("structure_block.position.x"));
        this.posXEdit.setMaxStringLength(15);
        this.posXEdit.setText(Integer.toString(blockPos.getX()));
        this.children.add(this.posXEdit);
        this.posYEdit = new TextFieldWidget(this.font, this.width / 2 - 72, 80, 80, 20, new TranslationTextComponent("structure_block.position.y"));
        this.posYEdit.setMaxStringLength(15);
        this.posYEdit.setText(Integer.toString(blockPos.getY()));
        this.children.add(this.posYEdit);
        this.posZEdit = new TextFieldWidget(this.font, this.width / 2 + 8, 80, 80, 20, new TranslationTextComponent("structure_block.position.z"));
        this.posZEdit.setMaxStringLength(15);
        this.posZEdit.setText(Integer.toString(blockPos.getZ()));
        this.children.add(this.posZEdit);
        BlockPos blockPos2 = this.tileStructure.getStructureSize();
        this.sizeXEdit = new TextFieldWidget(this.font, this.width / 2 - 152, 120, 80, 20, new TranslationTextComponent("structure_block.size.x"));
        this.sizeXEdit.setMaxStringLength(15);
        this.sizeXEdit.setText(Integer.toString(blockPos2.getX()));
        this.children.add(this.sizeXEdit);
        this.sizeYEdit = new TextFieldWidget(this.font, this.width / 2 - 72, 120, 80, 20, new TranslationTextComponent("structure_block.size.y"));
        this.sizeYEdit.setMaxStringLength(15);
        this.sizeYEdit.setText(Integer.toString(blockPos2.getY()));
        this.children.add(this.sizeYEdit);
        this.sizeZEdit = new TextFieldWidget(this.font, this.width / 2 + 8, 120, 80, 20, new TranslationTextComponent("structure_block.size.z"));
        this.sizeZEdit.setMaxStringLength(15);
        this.sizeZEdit.setText(Integer.toString(blockPos2.getZ()));
        this.children.add(this.sizeZEdit);
        this.integrityEdit = new TextFieldWidget(this.font, this.width / 2 - 152, 120, 80, 20, new TranslationTextComponent("structure_block.integrity.integrity"));
        this.integrityEdit.setMaxStringLength(15);
        this.integrityEdit.setText(this.decimalFormat.format(this.tileStructure.getIntegrity()));
        this.children.add(this.integrityEdit);
        this.seedEdit = new TextFieldWidget(this.font, this.width / 2 - 72, 120, 80, 20, new TranslationTextComponent("structure_block.integrity.seed"));
        this.seedEdit.setMaxStringLength(31);
        this.seedEdit.setText(Long.toString(this.tileStructure.getSeed()));
        this.children.add(this.seedEdit);
        this.dataEdit = new TextFieldWidget(this.font, this.width / 2 - 152, 120, 240, 20, new TranslationTextComponent("structure_block.custom_data"));
        this.dataEdit.setMaxStringLength(128);
        this.dataEdit.setText(this.tileStructure.getMetadata());
        this.children.add(this.dataEdit);
        this.mirror = this.tileStructure.getMirror();
        this.updateMirrorButton();
        this.rotation = this.tileStructure.getRotation();
        this.updateDirectionButtons();
        this.mode = this.tileStructure.getMode();
        this.updateMode();
        this.ignoreEntities = this.tileStructure.ignoresEntities();
        this.updateEntitiesButton();
        this.showAir = this.tileStructure.showsAir();
        this.updateToggleAirButton();
        this.showBoundingBox = this.tileStructure.showsBoundingBox();
        this.updateToggleBoundingBox();
        this.setFocusedDefault(this.nameEdit);
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.nameEdit.getText();
        String string2 = this.posXEdit.getText();
        String string3 = this.posYEdit.getText();
        String string4 = this.posZEdit.getText();
        String string5 = this.sizeXEdit.getText();
        String string6 = this.sizeYEdit.getText();
        String string7 = this.sizeZEdit.getText();
        String string8 = this.integrityEdit.getText();
        String string9 = this.seedEdit.getText();
        String string10 = this.dataEdit.getText();
        this.init(minecraft, n, n2);
        this.nameEdit.setText(string);
        this.posXEdit.setText(string2);
        this.posYEdit.setText(string3);
        this.posZEdit.setText(string4);
        this.sizeXEdit.setText(string5);
        this.sizeYEdit.setText(string6);
        this.sizeZEdit.setText(string7);
        this.integrityEdit.setText(string8);
        this.seedEdit.setText(string9);
        this.dataEdit.setText(string10);
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    private void updateEntitiesButton() {
        this.showEntitiesButton.setMessage(DialogTexts.optionsEnabled(!this.tileStructure.ignoresEntities()));
    }

    private void updateToggleAirButton() {
        this.showAirButton.setMessage(DialogTexts.optionsEnabled(this.tileStructure.showsAir()));
    }

    private void updateToggleBoundingBox() {
        this.showBoundingBoxButton.setMessage(DialogTexts.optionsEnabled(this.tileStructure.showsBoundingBox()));
    }

    private void updateMirrorButton() {
        Mirror mirror = this.tileStructure.getMirror();
        switch (mirror) {
            case NONE: {
                this.mirrorButton.setMessage(new StringTextComponent("|"));
                break;
            }
            case LEFT_RIGHT: {
                this.mirrorButton.setMessage(new StringTextComponent("< >"));
                break;
            }
            case FRONT_BACK: {
                this.mirrorButton.setMessage(new StringTextComponent("^ v"));
            }
        }
    }

    private void updateDirectionButtons() {
        this.rotateZeroDegreesButton.active = true;
        this.rotateNinetyDegreesButton.active = true;
        this.rotate180DegreesButton.active = true;
        this.rotate270DegressButton.active = true;
        switch (this.tileStructure.getRotation()) {
            case NONE: {
                this.rotateZeroDegreesButton.active = false;
                break;
            }
            case CLOCKWISE_180: {
                this.rotate180DegreesButton.active = false;
                break;
            }
            case COUNTERCLOCKWISE_90: {
                this.rotate270DegressButton.active = false;
                break;
            }
            case CLOCKWISE_90: {
                this.rotateNinetyDegreesButton.active = false;
            }
        }
    }

    private void updateMode() {
        this.nameEdit.setVisible(true);
        this.posXEdit.setVisible(true);
        this.posYEdit.setVisible(true);
        this.posZEdit.setVisible(true);
        this.sizeXEdit.setVisible(true);
        this.sizeYEdit.setVisible(true);
        this.sizeZEdit.setVisible(true);
        this.integrityEdit.setVisible(true);
        this.seedEdit.setVisible(true);
        this.dataEdit.setVisible(true);
        this.saveButton.visible = false;
        this.loadButton.visible = false;
        this.detectSizeButton.visible = false;
        this.showEntitiesButton.visible = false;
        this.mirrorButton.visible = false;
        this.rotateZeroDegreesButton.visible = false;
        this.rotateNinetyDegreesButton.visible = false;
        this.rotate180DegreesButton.visible = false;
        this.rotate270DegressButton.visible = false;
        this.showAirButton.visible = false;
        this.showBoundingBoxButton.visible = false;
        switch (this.tileStructure.getMode()) {
            case SAVE: {
                this.nameEdit.setVisible(false);
                this.posXEdit.setVisible(false);
                this.posYEdit.setVisible(false);
                this.posZEdit.setVisible(false);
                this.sizeXEdit.setVisible(false);
                this.sizeYEdit.setVisible(false);
                this.sizeZEdit.setVisible(false);
                this.saveButton.visible = true;
                this.detectSizeButton.visible = true;
                this.showEntitiesButton.visible = true;
                this.showAirButton.visible = true;
                break;
            }
            case LOAD: {
                this.nameEdit.setVisible(false);
                this.posXEdit.setVisible(false);
                this.posYEdit.setVisible(false);
                this.posZEdit.setVisible(false);
                this.integrityEdit.setVisible(false);
                this.seedEdit.setVisible(false);
                this.loadButton.visible = true;
                this.showEntitiesButton.visible = true;
                this.mirrorButton.visible = true;
                this.rotateZeroDegreesButton.visible = true;
                this.rotateNinetyDegreesButton.visible = true;
                this.rotate180DegreesButton.visible = true;
                this.rotate270DegressButton.visible = true;
                this.showBoundingBoxButton.visible = true;
                this.updateDirectionButtons();
                break;
            }
            case CORNER: {
                this.nameEdit.setVisible(false);
                break;
            }
            case DATA: {
                this.dataEdit.setVisible(false);
            }
        }
        this.modeButton.setMessage(new TranslationTextComponent("structure_block.mode." + this.tileStructure.getMode().getString()));
    }

    private boolean func_210143_a(StructureBlockTileEntity.UpdateCommand updateCommand) {
        BlockPos blockPos = new BlockPos(this.parseCoordinate(this.posXEdit.getText()), this.parseCoordinate(this.posYEdit.getText()), this.parseCoordinate(this.posZEdit.getText()));
        BlockPos blockPos2 = new BlockPos(this.parseCoordinate(this.sizeXEdit.getText()), this.parseCoordinate(this.sizeYEdit.getText()), this.parseCoordinate(this.sizeZEdit.getText()));
        float f = this.parseIntegrity(this.integrityEdit.getText());
        long l = this.parseSeed(this.seedEdit.getText());
        this.minecraft.getConnection().sendPacket(new CUpdateStructureBlockPacket(this.tileStructure.getPos(), updateCommand, this.tileStructure.getMode(), this.nameEdit.getText(), blockPos, blockPos2, this.tileStructure.getMirror(), this.tileStructure.getRotation(), this.dataEdit.getText(), this.tileStructure.ignoresEntities(), this.tileStructure.showsAir(), this.tileStructure.showsBoundingBox(), f, l));
        return false;
    }

    private long parseSeed(String string) {
        try {
            return Long.valueOf(string);
        } catch (NumberFormatException numberFormatException) {
            return 0L;
        }
    }

    private float parseIntegrity(String string) {
        try {
            return Float.valueOf(string).floatValue();
        } catch (NumberFormatException numberFormatException) {
            return 1.0f;
        }
    }

    private int parseCoordinate(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            return 1;
        }
    }

    @Override
    public void closeScreen() {
        this.func_195272_i();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (n != 257 && n != 335) {
            return true;
        }
        this.func_195275_h();
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        StructureMode structureMode = this.tileStructure.getMode();
        EditStructureScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 10, 0xFFFFFF);
        if (structureMode != StructureMode.DATA) {
            EditStructureScreen.drawString(matrixStack, this.font, field_243355_a, this.width / 2 - 153, 30, 0xA0A0A0);
            this.nameEdit.render(matrixStack, n, n2, f);
        }
        if (structureMode == StructureMode.LOAD || structureMode == StructureMode.SAVE) {
            EditStructureScreen.drawString(matrixStack, this.font, field_243356_b, this.width / 2 - 153, 70, 0xA0A0A0);
            this.posXEdit.render(matrixStack, n, n2, f);
            this.posYEdit.render(matrixStack, n, n2, f);
            this.posZEdit.render(matrixStack, n, n2, f);
            EditStructureScreen.drawString(matrixStack, this.font, field_243360_r, this.width / 2 + 154 - this.font.getStringPropertyWidth(field_243360_r), 150, 0xA0A0A0);
        }
        if (structureMode == StructureMode.SAVE) {
            EditStructureScreen.drawString(matrixStack, this.font, field_243357_c, this.width / 2 - 153, 110, 0xA0A0A0);
            this.sizeXEdit.render(matrixStack, n, n2, f);
            this.sizeYEdit.render(matrixStack, n, n2, f);
            this.sizeZEdit.render(matrixStack, n, n2, f);
            EditStructureScreen.drawString(matrixStack, this.font, field_243361_s, this.width / 2 + 154 - this.font.getStringPropertyWidth(field_243361_s), 110, 0xA0A0A0);
            EditStructureScreen.drawString(matrixStack, this.font, field_243362_t, this.width / 2 + 154 - this.font.getStringPropertyWidth(field_243362_t), 70, 0xA0A0A0);
        }
        if (structureMode == StructureMode.LOAD) {
            EditStructureScreen.drawString(matrixStack, this.font, field_243358_p, this.width / 2 - 153, 110, 0xA0A0A0);
            this.integrityEdit.render(matrixStack, n, n2, f);
            this.seedEdit.render(matrixStack, n, n2, f);
            EditStructureScreen.drawString(matrixStack, this.font, field_243363_u, this.width / 2 + 154 - this.font.getStringPropertyWidth(field_243363_u), 70, 0xA0A0A0);
        }
        if (structureMode == StructureMode.DATA) {
            EditStructureScreen.drawString(matrixStack, this.font, field_243359_q, this.width / 2 - 153, 110, 0xA0A0A0);
            this.dataEdit.render(matrixStack, n, n2, f);
        }
        EditStructureScreen.drawString(matrixStack, this.font, structureMode.func_242703_b(), this.width / 2 - 153, 174, 0xA0A0A0);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private void lambda$init$13(Button button) {
        this.tileStructure.setRotation(Rotation.COUNTERCLOCKWISE_90);
        this.updateDirectionButtons();
    }

    private void lambda$init$12(Button button) {
        this.tileStructure.setRotation(Rotation.CLOCKWISE_180);
        this.updateDirectionButtons();
    }

    private void lambda$init$11(Button button) {
        this.tileStructure.setRotation(Rotation.CLOCKWISE_90);
        this.updateDirectionButtons();
    }

    private void lambda$init$10(Button button) {
        this.tileStructure.setRotation(Rotation.NONE);
        this.updateDirectionButtons();
    }

    private void lambda$init$9(Button button) {
        this.tileStructure.setShowBoundingBox(!this.tileStructure.showsBoundingBox());
        this.updateToggleBoundingBox();
    }

    private void lambda$init$8(Button button) {
        this.tileStructure.setShowAir(!this.tileStructure.showsAir());
        this.updateToggleAirButton();
    }

    private void lambda$init$7(Button button) {
        switch (this.tileStructure.getMirror()) {
            case NONE: {
                this.tileStructure.setMirror(Mirror.LEFT_RIGHT);
                break;
            }
            case LEFT_RIGHT: {
                this.tileStructure.setMirror(Mirror.FRONT_BACK);
                break;
            }
            case FRONT_BACK: {
                this.tileStructure.setMirror(Mirror.NONE);
            }
        }
        this.updateMirrorButton();
    }

    private void lambda$init$6(Button button) {
        this.tileStructure.setIgnoresEntities(!this.tileStructure.ignoresEntities());
        this.updateEntitiesButton();
    }

    private void lambda$init$5(Button button) {
        if (this.tileStructure.getMode() == StructureMode.SAVE) {
            this.func_210143_a(StructureBlockTileEntity.UpdateCommand.SCAN_AREA);
            this.minecraft.displayGuiScreen(null);
        }
    }

    private void lambda$init$4(Button button) {
        this.tileStructure.nextMode();
        this.updateMode();
    }

    private void lambda$init$3(Button button) {
        if (this.tileStructure.getMode() == StructureMode.LOAD) {
            this.func_210143_a(StructureBlockTileEntity.UpdateCommand.LOAD_AREA);
            this.minecraft.displayGuiScreen(null);
        }
    }

    private void lambda$init$2(Button button) {
        if (this.tileStructure.getMode() == StructureMode.SAVE) {
            this.func_210143_a(StructureBlockTileEntity.UpdateCommand.SAVE_AREA);
            this.minecraft.displayGuiScreen(null);
        }
    }

    private void lambda$init$1(Button button) {
        this.func_195272_i();
    }

    private void lambda$init$0(Button button) {
        this.func_195275_h();
    }
}


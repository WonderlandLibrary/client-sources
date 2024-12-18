/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiEditStructure
extends GuiScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final int[] LEGAL_KEY_CODES = new int[]{203, 205, 14, 211, 199, 207};
    private final TileEntityStructure tileStructure;
    private Mirror mirror = Mirror.NONE;
    private Rotation rotation = Rotation.NONE;
    private TileEntityStructure.Mode mode = TileEntityStructure.Mode.DATA;
    private boolean ignoreEntities;
    private boolean showAir;
    private boolean showBoundingBox;
    private GuiTextField nameEdit;
    private GuiTextField posXEdit;
    private GuiTextField posYEdit;
    private GuiTextField posZEdit;
    private GuiTextField sizeXEdit;
    private GuiTextField sizeYEdit;
    private GuiTextField sizeZEdit;
    private GuiTextField integrityEdit;
    private GuiTextField seedEdit;
    private GuiTextField dataEdit;
    private GuiButton doneButton;
    private GuiButton cancelButton;
    private GuiButton saveButton;
    private GuiButton loadButton;
    private GuiButton rotateZeroDegreesButton;
    private GuiButton rotateNinetyDegreesButton;
    private GuiButton rotate180DegreesButton;
    private GuiButton rotate270DegressButton;
    private GuiButton modeButton;
    private GuiButton detectSizeButton;
    private GuiButton showEntitiesButton;
    private GuiButton mirrorButton;
    private GuiButton showAirButton;
    private GuiButton showBoundingBoxButton;
    private final List<GuiTextField> tabOrder = Lists.newArrayList();
    private final DecimalFormat decimalFormat = new DecimalFormat("0.0###");

    public GuiEditStructure(TileEntityStructure p_i47142_1_) {
        this.tileStructure = p_i47142_1_;
        this.decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
    }

    @Override
    public void updateScreen() {
        this.nameEdit.updateCursorCounter();
        this.posXEdit.updateCursorCounter();
        this.posYEdit.updateCursorCounter();
        this.posZEdit.updateCursorCounter();
        this.sizeXEdit.updateCursorCounter();
        this.sizeYEdit.updateCursorCounter();
        this.sizeZEdit.updateCursorCounter();
        this.integrityEdit.updateCursorCounter();
        this.seedEdit.updateCursorCounter();
        this.dataEdit.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.doneButton = this.addButton(new GuiButton(0, width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.done", new Object[0])));
        this.cancelButton = this.addButton(new GuiButton(1, width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.saveButton = this.addButton(new GuiButton(9, width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.save", new Object[0])));
        this.loadButton = this.addButton(new GuiButton(10, width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.load", new Object[0])));
        this.modeButton = this.addButton(new GuiButton(18, width / 2 - 4 - 150, 185, 50, 20, "MODE"));
        this.detectSizeButton = this.addButton(new GuiButton(19, width / 2 + 4 + 100, 120, 50, 20, I18n.format("structure_block.button.detect_size", new Object[0])));
        this.showEntitiesButton = this.addButton(new GuiButton(20, width / 2 + 4 + 100, 160, 50, 20, "ENTITIES"));
        this.mirrorButton = this.addButton(new GuiButton(21, width / 2 - 20, 185, 40, 20, "MIRROR"));
        this.showAirButton = this.addButton(new GuiButton(22, width / 2 + 4 + 100, 80, 50, 20, "SHOWAIR"));
        this.showBoundingBoxButton = this.addButton(new GuiButton(23, width / 2 + 4 + 100, 80, 50, 20, "SHOWBB"));
        this.rotateZeroDegreesButton = this.addButton(new GuiButton(11, width / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, "0"));
        this.rotateNinetyDegreesButton = this.addButton(new GuiButton(12, width / 2 - 1 - 40 - 20, 185, 40, 20, "90"));
        this.rotate180DegreesButton = this.addButton(new GuiButton(13, width / 2 + 1 + 20, 185, 40, 20, "180"));
        this.rotate270DegressButton = this.addButton(new GuiButton(14, width / 2 + 1 + 40 + 1 + 20, 185, 40, 20, "270"));
        this.nameEdit = new GuiTextField(2, this.fontRendererObj, width / 2 - 152, 40, 300, 20);
        this.nameEdit.setMaxStringLength(64);
        this.nameEdit.setText(this.tileStructure.getName());
        this.tabOrder.add(this.nameEdit);
        BlockPos blockpos = this.tileStructure.getPosition();
        this.posXEdit = new GuiTextField(3, this.fontRendererObj, width / 2 - 152, 80, 80, 20);
        this.posXEdit.setMaxStringLength(15);
        this.posXEdit.setText(Integer.toString(blockpos.getX()));
        this.tabOrder.add(this.posXEdit);
        this.posYEdit = new GuiTextField(4, this.fontRendererObj, width / 2 - 72, 80, 80, 20);
        this.posYEdit.setMaxStringLength(15);
        this.posYEdit.setText(Integer.toString(blockpos.getY()));
        this.tabOrder.add(this.posYEdit);
        this.posZEdit = new GuiTextField(5, this.fontRendererObj, width / 2 + 8, 80, 80, 20);
        this.posZEdit.setMaxStringLength(15);
        this.posZEdit.setText(Integer.toString(blockpos.getZ()));
        this.tabOrder.add(this.posZEdit);
        BlockPos blockpos1 = this.tileStructure.getStructureSize();
        this.sizeXEdit = new GuiTextField(6, this.fontRendererObj, width / 2 - 152, 120, 80, 20);
        this.sizeXEdit.setMaxStringLength(15);
        this.sizeXEdit.setText(Integer.toString(blockpos1.getX()));
        this.tabOrder.add(this.sizeXEdit);
        this.sizeYEdit = new GuiTextField(7, this.fontRendererObj, width / 2 - 72, 120, 80, 20);
        this.sizeYEdit.setMaxStringLength(15);
        this.sizeYEdit.setText(Integer.toString(blockpos1.getY()));
        this.tabOrder.add(this.sizeYEdit);
        this.sizeZEdit = new GuiTextField(8, this.fontRendererObj, width / 2 + 8, 120, 80, 20);
        this.sizeZEdit.setMaxStringLength(15);
        this.sizeZEdit.setText(Integer.toString(blockpos1.getZ()));
        this.tabOrder.add(this.sizeZEdit);
        this.integrityEdit = new GuiTextField(15, this.fontRendererObj, width / 2 - 152, 120, 80, 20);
        this.integrityEdit.setMaxStringLength(15);
        this.integrityEdit.setText(this.decimalFormat.format(this.tileStructure.getIntegrity()));
        this.tabOrder.add(this.integrityEdit);
        this.seedEdit = new GuiTextField(16, this.fontRendererObj, width / 2 - 72, 120, 80, 20);
        this.seedEdit.setMaxStringLength(31);
        this.seedEdit.setText(Long.toString(this.tileStructure.getSeed()));
        this.tabOrder.add(this.seedEdit);
        this.dataEdit = new GuiTextField(17, this.fontRendererObj, width / 2 - 152, 120, 240, 20);
        this.dataEdit.setMaxStringLength(128);
        this.dataEdit.setText(this.tileStructure.getMetadata());
        this.tabOrder.add(this.dataEdit);
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
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.tileStructure.setMirror(this.mirror);
                this.tileStructure.setRotation(this.rotation);
                this.tileStructure.setMode(this.mode);
                this.tileStructure.setIgnoresEntities(this.ignoreEntities);
                this.tileStructure.setShowAir(this.showAir);
                this.tileStructure.setShowBoundingBox(this.showBoundingBox);
                this.mc.displayGuiScreen(null);
            } else if (button.id == 0) {
                if (this.sendToServer(1)) {
                    this.mc.displayGuiScreen(null);
                }
            } else if (button.id == 9) {
                if (this.tileStructure.getMode() == TileEntityStructure.Mode.SAVE) {
                    this.sendToServer(2);
                    this.mc.displayGuiScreen(null);
                }
            } else if (button.id == 10) {
                if (this.tileStructure.getMode() == TileEntityStructure.Mode.LOAD) {
                    this.sendToServer(3);
                    this.mc.displayGuiScreen(null);
                }
            } else if (button.id == 11) {
                this.tileStructure.setRotation(Rotation.NONE);
                this.updateDirectionButtons();
            } else if (button.id == 12) {
                this.tileStructure.setRotation(Rotation.CLOCKWISE_90);
                this.updateDirectionButtons();
            } else if (button.id == 13) {
                this.tileStructure.setRotation(Rotation.CLOCKWISE_180);
                this.updateDirectionButtons();
            } else if (button.id == 14) {
                this.tileStructure.setRotation(Rotation.COUNTERCLOCKWISE_90);
                this.updateDirectionButtons();
            } else if (button.id == 18) {
                this.tileStructure.nextMode();
                this.updateMode();
            } else if (button.id == 19) {
                if (this.tileStructure.getMode() == TileEntityStructure.Mode.SAVE) {
                    this.sendToServer(4);
                    this.mc.displayGuiScreen(null);
                }
            } else if (button.id == 20) {
                this.tileStructure.setIgnoresEntities(!this.tileStructure.ignoresEntities());
                this.updateEntitiesButton();
            } else if (button.id == 22) {
                this.tileStructure.setShowAir(!this.tileStructure.showsAir());
                this.updateToggleAirButton();
            } else if (button.id == 23) {
                this.tileStructure.setShowBoundingBox(!this.tileStructure.showsBoundingBox());
                this.updateToggleBoundingBox();
            } else if (button.id == 21) {
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
        }
    }

    private void updateEntitiesButton() {
        boolean flag = !this.tileStructure.ignoresEntities();
        this.showEntitiesButton.displayString = flag ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]);
    }

    private void updateToggleAirButton() {
        boolean flag = this.tileStructure.showsAir();
        this.showAirButton.displayString = flag ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]);
    }

    private void updateToggleBoundingBox() {
        boolean flag = this.tileStructure.showsBoundingBox();
        this.showBoundingBoxButton.displayString = flag ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]);
    }

    private void updateMirrorButton() {
        Mirror mirror = this.tileStructure.getMirror();
        switch (mirror) {
            case NONE: {
                this.mirrorButton.displayString = "|";
                break;
            }
            case LEFT_RIGHT: {
                this.mirrorButton.displayString = "< >";
                break;
            }
            case FRONT_BACK: {
                this.mirrorButton.displayString = "^ v";
            }
        }
    }

    private void updateDirectionButtons() {
        this.rotateZeroDegreesButton.enabled = true;
        this.rotateNinetyDegreesButton.enabled = true;
        this.rotate180DegreesButton.enabled = true;
        this.rotate270DegressButton.enabled = true;
        switch (this.tileStructure.getRotation()) {
            case NONE: {
                this.rotateZeroDegreesButton.enabled = false;
                break;
            }
            case CLOCKWISE_180: {
                this.rotate180DegreesButton.enabled = false;
                break;
            }
            case COUNTERCLOCKWISE_90: {
                this.rotate270DegressButton.enabled = false;
                break;
            }
            case CLOCKWISE_90: {
                this.rotateNinetyDegreesButton.enabled = false;
            }
        }
    }

    private void updateMode() {
        this.nameEdit.setFocused(false);
        this.posXEdit.setFocused(false);
        this.posYEdit.setFocused(false);
        this.posZEdit.setFocused(false);
        this.sizeXEdit.setFocused(false);
        this.sizeYEdit.setFocused(false);
        this.sizeZEdit.setFocused(false);
        this.integrityEdit.setFocused(false);
        this.seedEdit.setFocused(false);
        this.dataEdit.setFocused(false);
        this.nameEdit.setVisible(false);
        this.nameEdit.setFocused(false);
        this.posXEdit.setVisible(false);
        this.posYEdit.setVisible(false);
        this.posZEdit.setVisible(false);
        this.sizeXEdit.setVisible(false);
        this.sizeYEdit.setVisible(false);
        this.sizeZEdit.setVisible(false);
        this.integrityEdit.setVisible(false);
        this.seedEdit.setVisible(false);
        this.dataEdit.setVisible(false);
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
                this.nameEdit.setVisible(true);
                this.nameEdit.setFocused(true);
                this.posXEdit.setVisible(true);
                this.posYEdit.setVisible(true);
                this.posZEdit.setVisible(true);
                this.sizeXEdit.setVisible(true);
                this.sizeYEdit.setVisible(true);
                this.sizeZEdit.setVisible(true);
                this.saveButton.visible = true;
                this.detectSizeButton.visible = true;
                this.showEntitiesButton.visible = true;
                this.showAirButton.visible = true;
                break;
            }
            case LOAD: {
                this.nameEdit.setVisible(true);
                this.nameEdit.setFocused(true);
                this.posXEdit.setVisible(true);
                this.posYEdit.setVisible(true);
                this.posZEdit.setVisible(true);
                this.integrityEdit.setVisible(true);
                this.seedEdit.setVisible(true);
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
                this.nameEdit.setVisible(true);
                this.nameEdit.setFocused(true);
                break;
            }
            case DATA: {
                this.dataEdit.setVisible(true);
                this.dataEdit.setFocused(true);
            }
        }
        this.modeButton.displayString = I18n.format("structure_block.mode." + this.tileStructure.getMode().getName(), new Object[0]);
    }

    private boolean sendToServer(int p_189820_1_) {
        try {
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            this.tileStructure.writeCoordinates(packetbuffer);
            packetbuffer.writeByte(p_189820_1_);
            packetbuffer.writeString(this.tileStructure.getMode().toString());
            packetbuffer.writeString(this.nameEdit.getText());
            packetbuffer.writeInt(this.parseCoordinate(this.posXEdit.getText()));
            packetbuffer.writeInt(this.parseCoordinate(this.posYEdit.getText()));
            packetbuffer.writeInt(this.parseCoordinate(this.posZEdit.getText()));
            packetbuffer.writeInt(this.parseCoordinate(this.sizeXEdit.getText()));
            packetbuffer.writeInt(this.parseCoordinate(this.sizeYEdit.getText()));
            packetbuffer.writeInt(this.parseCoordinate(this.sizeZEdit.getText()));
            packetbuffer.writeString(this.tileStructure.getMirror().toString());
            packetbuffer.writeString(this.tileStructure.getRotation().toString());
            packetbuffer.writeString(this.dataEdit.getText());
            packetbuffer.writeBoolean(this.tileStructure.ignoresEntities());
            packetbuffer.writeBoolean(this.tileStructure.showsAir());
            packetbuffer.writeBoolean(this.tileStructure.showsBoundingBox());
            packetbuffer.writeFloat(this.parseIntegrity(this.integrityEdit.getText()));
            packetbuffer.writeVarLong(this.parseSeed(this.seedEdit.getText()));
            this.mc.getConnection().sendPacket(new CPacketCustomPayload("MC|Struct", packetbuffer));
            return true;
        } catch (Exception exception) {
            LOGGER.warn("Could not send structure block info", (Throwable)exception);
            return false;
        }
    }

    private long parseSeed(String p_189821_1_) {
        try {
            return Long.valueOf(p_189821_1_);
        } catch (NumberFormatException var3) {
            return 0L;
        }
    }

    private float parseIntegrity(String p_189819_1_) {
        try {
            return Float.valueOf(p_189819_1_).floatValue();
        } catch (NumberFormatException var3) {
            return 1.0f;
        }
    }

    private int parseCoordinate(String p_189817_1_) {
        try {
            return Integer.parseInt(p_189817_1_);
        } catch (NumberFormatException var3) {
            return 0;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.nameEdit.getVisible() && GuiEditStructure.isValidCharacterForName(typedChar, keyCode)) {
            this.nameEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.posXEdit.getVisible()) {
            this.posXEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.posYEdit.getVisible()) {
            this.posYEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.posZEdit.getVisible()) {
            this.posZEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.sizeXEdit.getVisible()) {
            this.sizeXEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.sizeYEdit.getVisible()) {
            this.sizeYEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.sizeZEdit.getVisible()) {
            this.sizeZEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.integrityEdit.getVisible()) {
            this.integrityEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.seedEdit.getVisible()) {
            this.seedEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.dataEdit.getVisible()) {
            this.dataEdit.textboxKeyTyped(typedChar, keyCode);
        }
        if (keyCode == 15) {
            GuiTextField guitextfield = null;
            GuiTextField guitextfield1 = null;
            for (GuiTextField guitextfield2 : this.tabOrder) {
                if (guitextfield != null && guitextfield2.getVisible()) {
                    guitextfield1 = guitextfield2;
                    break;
                }
                if (!guitextfield2.isFocused() || !guitextfield2.getVisible()) continue;
                guitextfield = guitextfield2;
            }
            if (guitextfield != null && guitextfield1 == null) {
                for (GuiTextField guitextfield3 : this.tabOrder) {
                    if (!guitextfield3.getVisible() || guitextfield3 == guitextfield) continue;
                    guitextfield1 = guitextfield3;
                    break;
                }
            }
            if (guitextfield1 != null && guitextfield1 != guitextfield) {
                guitextfield.setFocused(false);
                guitextfield1.setFocused(true);
            }
        }
        if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 1) {
                this.actionPerformed(this.cancelButton);
            }
        } else {
            this.actionPerformed(this.doneButton);
        }
    }

    private static boolean isValidCharacterForName(char p_190301_0_, int p_190301_1_) {
        boolean flag = true;
        for (int i : LEGAL_KEY_CODES) {
            if (i != p_190301_1_) continue;
            return true;
        }
        for (char c0 : ChatAllowedCharacters.ILLEGAL_STRUCTURE_CHARACTERS) {
            if (c0 != p_190301_0_) continue;
            flag = false;
            break;
        }
        return flag;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.nameEdit.getVisible()) {
            this.nameEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.posXEdit.getVisible()) {
            this.posXEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.posYEdit.getVisible()) {
            this.posYEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.posZEdit.getVisible()) {
            this.posZEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.sizeXEdit.getVisible()) {
            this.sizeXEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.sizeYEdit.getVisible()) {
            this.sizeYEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.sizeZEdit.getVisible()) {
            this.sizeZEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.integrityEdit.getVisible()) {
            this.integrityEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.seedEdit.getVisible()) {
            this.seedEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.dataEdit.getVisible()) {
            this.dataEdit.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        TileEntityStructure.Mode tileentitystructure$mode = this.tileStructure.getMode();
        this.drawCenteredString(this.fontRendererObj, I18n.format("tile.structureBlock.name", new Object[0]), width / 2, 10, 0xFFFFFF);
        if (tileentitystructure$mode != TileEntityStructure.Mode.DATA) {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.structure_name", new Object[0]), width / 2 - 153, 30, 0xA0A0A0);
            this.nameEdit.drawTextBox();
        }
        if (tileentitystructure$mode == TileEntityStructure.Mode.LOAD || tileentitystructure$mode == TileEntityStructure.Mode.SAVE) {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.position", new Object[0]), width / 2 - 153, 70, 0xA0A0A0);
            this.posXEdit.drawTextBox();
            this.posYEdit.drawTextBox();
            this.posZEdit.drawTextBox();
            String s = I18n.format("structure_block.include_entities", new Object[0]);
            int i = this.fontRendererObj.getStringWidth(s);
            this.drawString(this.fontRendererObj, s, width / 2 + 154 - i, 150, 0xA0A0A0);
        }
        if (tileentitystructure$mode == TileEntityStructure.Mode.SAVE) {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.size", new Object[0]), width / 2 - 153, 110, 0xA0A0A0);
            this.sizeXEdit.drawTextBox();
            this.sizeYEdit.drawTextBox();
            this.sizeZEdit.drawTextBox();
            String s2 = I18n.format("structure_block.detect_size", new Object[0]);
            int k = this.fontRendererObj.getStringWidth(s2);
            this.drawString(this.fontRendererObj, s2, width / 2 + 154 - k, 110, 0xA0A0A0);
            String s1 = I18n.format("structure_block.show_air", new Object[0]);
            int j = this.fontRendererObj.getStringWidth(s1);
            this.drawString(this.fontRendererObj, s1, width / 2 + 154 - j, 70, 0xA0A0A0);
        }
        if (tileentitystructure$mode == TileEntityStructure.Mode.LOAD) {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.integrity", new Object[0]), width / 2 - 153, 110, 0xA0A0A0);
            this.integrityEdit.drawTextBox();
            this.seedEdit.drawTextBox();
            String s3 = I18n.format("structure_block.show_boundingbox", new Object[0]);
            int l = this.fontRendererObj.getStringWidth(s3);
            this.drawString(this.fontRendererObj, s3, width / 2 + 154 - l, 70, 0xA0A0A0);
        }
        if (tileentitystructure$mode == TileEntityStructure.Mode.DATA) {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.custom_data", new Object[0]), width / 2 - 153, 110, 0xA0A0A0);
            this.dataEdit.drawTextBox();
        }
        String s4 = "structure_block.mode_info." + tileentitystructure$mode.getName();
        this.drawString(this.fontRendererObj, I18n.format(s4, new Object[0]), width / 2 - 153, 174, 0xA0A0A0);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}


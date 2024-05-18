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

public class GuiEditStructure extends GuiScreen
{
    private static final Logger field_189845_a = LogManager.getLogger();
    private final TileEntityStructure field_189846_f;
    private Mirror field_189847_g = Mirror.NONE;
    private Rotation field_189848_h = Rotation.NONE;
    private TileEntityStructure.Mode field_189849_i = TileEntityStructure.Mode.DATA;
    private boolean field_189850_r;
    private boolean field_189851_s;
    private boolean field_189852_t;
    private GuiTextField field_189853_u;
    private GuiTextField field_189854_v;
    private GuiTextField field_189855_w;
    private GuiTextField field_189856_x;
    private GuiTextField field_189857_y;
    private GuiTextField field_189858_z;
    private GuiTextField field_189825_A;
    private GuiTextField field_189826_B;
    private GuiTextField field_189827_C;
    private GuiTextField field_189828_D;
    private GuiButton field_189829_E;
    private GuiButton field_189830_F;
    private GuiButton field_189831_G;
    private GuiButton field_189832_H;
    private GuiButton field_189833_I;
    private GuiButton field_189834_J;
    private GuiButton field_189835_K;
    private GuiButton field_189836_L;
    private GuiButton field_189837_M;
    private GuiButton field_189838_N;
    private GuiButton field_189839_O;
    private GuiButton field_189840_P;
    private GuiButton field_189841_Q;
    private GuiButton field_189842_R;
    private final List<GuiTextField> field_189843_S = Lists.<GuiTextField>newArrayList();
    private final DecimalFormat field_189844_T = new DecimalFormat("0.0###");

    public GuiEditStructure(TileEntityStructure p_i47142_1_)
    {
        this.field_189846_f = p_i47142_1_;
        this.field_189844_T.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_189853_u.updateCursorCounter();
        this.field_189854_v.updateCursorCounter();
        this.field_189855_w.updateCursorCounter();
        this.field_189856_x.updateCursorCounter();
        this.field_189857_y.updateCursorCounter();
        this.field_189858_z.updateCursorCounter();
        this.field_189825_A.updateCursorCounter();
        this.field_189826_B.updateCursorCounter();
        this.field_189827_C.updateCursorCounter();
        this.field_189828_D.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.field_189829_E = this.func_189646_b(new GuiButton(0, this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.done", new Object[0])));
        this.field_189830_F = this.func_189646_b(new GuiButton(1, this.width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.field_189831_G = this.func_189646_b(new GuiButton(9, this.width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.save", new Object[0])));
        this.field_189832_H = this.func_189646_b(new GuiButton(10, this.width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.load", new Object[0])));
        this.field_189837_M = this.func_189646_b(new GuiButton(18, this.width / 2 - 4 - 150, 185, 50, 20, "MODE"));
        this.field_189838_N = this.func_189646_b(new GuiButton(19, this.width / 2 + 4 + 100, 120, 50, 20, I18n.format("structure_block.button.detect_size", new Object[0])));
        this.field_189839_O = this.func_189646_b(new GuiButton(20, this.width / 2 + 4 + 100, 160, 50, 20, "ENTITIES"));
        this.field_189840_P = this.func_189646_b(new GuiButton(21, this.width / 2 - 20, 185, 40, 20, "MIRROR"));
        this.field_189841_Q = this.func_189646_b(new GuiButton(22, this.width / 2 + 4 + 100, 80, 50, 20, "SHOWAIR"));
        this.field_189842_R = this.func_189646_b(new GuiButton(23, this.width / 2 + 4 + 100, 80, 50, 20, "SHOWBB"));
        this.field_189833_I = this.func_189646_b(new GuiButton(11, this.width / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, "0"));
        this.field_189834_J = this.func_189646_b(new GuiButton(12, this.width / 2 - 1 - 40 - 20, 185, 40, 20, "90"));
        this.field_189835_K = this.func_189646_b(new GuiButton(13, this.width / 2 + 1 + 20, 185, 40, 20, "180"));
        this.field_189836_L = this.func_189646_b(new GuiButton(14, this.width / 2 + 1 + 40 + 1 + 20, 185, 40, 20, "270"));
        this.field_189853_u = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 152, 40, 300, 20);
        this.field_189853_u.setMaxStringLength(64);
        this.field_189853_u.setText(this.field_189846_f.func_189715_d());
        this.field_189843_S.add(this.field_189853_u);
        BlockPos blockpos = this.field_189846_f.func_189711_e();
        this.field_189854_v = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 152, 80, 80, 20);
        this.field_189854_v.setMaxStringLength(15);
        this.field_189854_v.setText(Integer.toString(blockpos.getX()));
        this.field_189843_S.add(this.field_189854_v);
        this.field_189855_w = new GuiTextField(4, this.fontRendererObj, this.width / 2 - 72, 80, 80, 20);
        this.field_189855_w.setMaxStringLength(15);
        this.field_189855_w.setText(Integer.toString(blockpos.getY()));
        this.field_189843_S.add(this.field_189855_w);
        this.field_189856_x = new GuiTextField(5, this.fontRendererObj, this.width / 2 + 8, 80, 80, 20);
        this.field_189856_x.setMaxStringLength(15);
        this.field_189856_x.setText(Integer.toString(blockpos.getZ()));
        this.field_189843_S.add(this.field_189856_x);
        BlockPos blockpos1 = this.field_189846_f.func_189717_g();
        this.field_189857_y = new GuiTextField(6, this.fontRendererObj, this.width / 2 - 152, 120, 80, 20);
        this.field_189857_y.setMaxStringLength(15);
        this.field_189857_y.setText(Integer.toString(blockpos1.getX()));
        this.field_189843_S.add(this.field_189857_y);
        this.field_189858_z = new GuiTextField(7, this.fontRendererObj, this.width / 2 - 72, 120, 80, 20);
        this.field_189858_z.setMaxStringLength(15);
        this.field_189858_z.setText(Integer.toString(blockpos1.getY()));
        this.field_189843_S.add(this.field_189858_z);
        this.field_189825_A = new GuiTextField(8, this.fontRendererObj, this.width / 2 + 8, 120, 80, 20);
        this.field_189825_A.setMaxStringLength(15);
        this.field_189825_A.setText(Integer.toString(blockpos1.getZ()));
        this.field_189843_S.add(this.field_189825_A);
        this.field_189826_B = new GuiTextField(15, this.fontRendererObj, this.width / 2 - 152, 120, 80, 20);
        this.field_189826_B.setMaxStringLength(15);
        this.field_189826_B.setText(this.field_189844_T.format((double)this.field_189846_f.func_189702_n()));
        this.field_189843_S.add(this.field_189826_B);
        this.field_189827_C = new GuiTextField(16, this.fontRendererObj, this.width / 2 - 72, 120, 80, 20);
        this.field_189827_C.setMaxStringLength(31);
        this.field_189827_C.setText(Long.toString(this.field_189846_f.func_189719_o()));
        this.field_189843_S.add(this.field_189827_C);
        this.field_189828_D = new GuiTextField(17, this.fontRendererObj, this.width / 2 - 152, 120, 240, 20);
        this.field_189828_D.setMaxStringLength(128);
        this.field_189828_D.setText(this.field_189846_f.func_189708_j());
        this.field_189843_S.add(this.field_189828_D);
        this.field_189847_g = this.field_189846_f.func_189716_h();
        this.func_189816_h();
        this.field_189848_h = this.field_189846_f.func_189726_i();
        this.func_189824_i();
        this.field_189849_i = this.field_189846_f.func_189700_k();
        this.func_189823_j();
        this.field_189850_r = this.field_189846_f.func_189713_m();
        this.func_189822_a();
        this.field_189851_s = this.field_189846_f.func_189707_H();
        this.func_189814_f();
        this.field_189852_t = this.field_189846_f.func_189721_I();
        this.func_189815_g();
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 1)
            {
                this.field_189846_f.setMirror(this.field_189847_g);
                this.field_189846_f.setRotation(this.field_189848_h);
                this.field_189846_f.setMode(this.field_189849_i);
                this.field_189846_f.setIgnoresEntities(this.field_189850_r);
                this.field_189846_f.func_189703_e(this.field_189851_s);
                this.field_189846_f.func_189710_f(this.field_189852_t);
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (button.id == 0)
            {
                if (this.func_189820_b(1))
                {
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
            }
            else if (button.id == 9)
            {
                if (this.field_189846_f.func_189700_k() == TileEntityStructure.Mode.SAVE)
                {
                    this.func_189820_b(2);
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
            }
            else if (button.id == 10)
            {
                if (this.field_189846_f.func_189700_k() == TileEntityStructure.Mode.LOAD)
                {
                    this.func_189820_b(3);
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
            }
            else if (button.id == 11)
            {
                this.field_189846_f.setRotation(Rotation.NONE);
                this.func_189824_i();
            }
            else if (button.id == 12)
            {
                this.field_189846_f.setRotation(Rotation.CLOCKWISE_90);
                this.func_189824_i();
            }
            else if (button.id == 13)
            {
                this.field_189846_f.setRotation(Rotation.CLOCKWISE_180);
                this.func_189824_i();
            }
            else if (button.id == 14)
            {
                this.field_189846_f.setRotation(Rotation.COUNTERCLOCKWISE_90);
                this.func_189824_i();
            }
            else if (button.id == 18)
            {
                this.field_189846_f.func_189724_l();
                this.func_189823_j();
            }
            else if (button.id == 19)
            {
                if (this.field_189846_f.func_189700_k() == TileEntityStructure.Mode.SAVE)
                {
                    this.func_189820_b(4);
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
            }
            else if (button.id == 20)
            {
                this.field_189846_f.setIgnoresEntities(!this.field_189846_f.func_189713_m());
                this.func_189822_a();
            }
            else if (button.id == 22)
            {
                this.field_189846_f.func_189703_e(!this.field_189846_f.func_189707_H());
                this.func_189814_f();
            }
            else if (button.id == 23)
            {
                this.field_189846_f.func_189710_f(!this.field_189846_f.func_189721_I());
                this.func_189815_g();
            }
            else if (button.id == 21)
            {
                switch (this.field_189846_f.func_189716_h())
                {
                    case NONE:
                        this.field_189846_f.setMirror(Mirror.LEFT_RIGHT);
                        break;

                    case LEFT_RIGHT:
                        this.field_189846_f.setMirror(Mirror.FRONT_BACK);
                        break;

                    case FRONT_BACK:
                        this.field_189846_f.setMirror(Mirror.NONE);
                }

                this.func_189816_h();
            }
        }
    }

    private void func_189822_a()
    {
        boolean flag = !this.field_189846_f.func_189713_m();

        if (flag)
        {
            this.field_189839_O.displayString = I18n.format("options.on", new Object[0]);
        }
        else
        {
            this.field_189839_O.displayString = I18n.format("options.off", new Object[0]);
        }
    }

    private void func_189814_f()
    {
        boolean flag = this.field_189846_f.func_189707_H();

        if (flag)
        {
            this.field_189841_Q.displayString = I18n.format("options.on", new Object[0]);
        }
        else
        {
            this.field_189841_Q.displayString = I18n.format("options.off", new Object[0]);
        }
    }

    private void func_189815_g()
    {
        boolean flag = this.field_189846_f.func_189721_I();

        if (flag)
        {
            this.field_189842_R.displayString = I18n.format("options.on", new Object[0]);
        }
        else
        {
            this.field_189842_R.displayString = I18n.format("options.off", new Object[0]);
        }
    }

    private void func_189816_h()
    {
        Mirror mirror = this.field_189846_f.func_189716_h();

        switch (mirror)
        {
            case NONE:
                this.field_189840_P.displayString = "|";
                break;

            case LEFT_RIGHT:
                this.field_189840_P.displayString = "< >";
                break;

            case FRONT_BACK:
                this.field_189840_P.displayString = "^ v";
        }
    }

    private void func_189824_i()
    {
        this.field_189833_I.enabled = true;
        this.field_189834_J.enabled = true;
        this.field_189835_K.enabled = true;
        this.field_189836_L.enabled = true;

        switch (this.field_189846_f.func_189726_i())
        {
            case NONE:
                this.field_189833_I.enabled = false;
                break;

            case CLOCKWISE_180:
                this.field_189835_K.enabled = false;
                break;

            case COUNTERCLOCKWISE_90:
                this.field_189836_L.enabled = false;
                break;

            case CLOCKWISE_90:
                this.field_189834_J.enabled = false;
        }
    }

    private void func_189823_j()
    {
        this.field_189853_u.setFocused(false);
        this.field_189854_v.setFocused(false);
        this.field_189855_w.setFocused(false);
        this.field_189856_x.setFocused(false);
        this.field_189857_y.setFocused(false);
        this.field_189858_z.setFocused(false);
        this.field_189825_A.setFocused(false);
        this.field_189826_B.setFocused(false);
        this.field_189827_C.setFocused(false);
        this.field_189828_D.setFocused(false);
        this.field_189853_u.setVisible(false);
        this.field_189853_u.setFocused(false);
        this.field_189854_v.setVisible(false);
        this.field_189855_w.setVisible(false);
        this.field_189856_x.setVisible(false);
        this.field_189857_y.setVisible(false);
        this.field_189858_z.setVisible(false);
        this.field_189825_A.setVisible(false);
        this.field_189826_B.setVisible(false);
        this.field_189827_C.setVisible(false);
        this.field_189828_D.setVisible(false);
        this.field_189831_G.visible = false;
        this.field_189832_H.visible = false;
        this.field_189838_N.visible = false;
        this.field_189839_O.visible = false;
        this.field_189840_P.visible = false;
        this.field_189833_I.visible = false;
        this.field_189834_J.visible = false;
        this.field_189835_K.visible = false;
        this.field_189836_L.visible = false;
        this.field_189841_Q.visible = false;
        this.field_189842_R.visible = false;

        switch (this.field_189846_f.func_189700_k())
        {
            case SAVE:
                this.field_189853_u.setVisible(true);
                this.field_189853_u.setFocused(true);
                this.field_189854_v.setVisible(true);
                this.field_189855_w.setVisible(true);
                this.field_189856_x.setVisible(true);
                this.field_189857_y.setVisible(true);
                this.field_189858_z.setVisible(true);
                this.field_189825_A.setVisible(true);
                this.field_189831_G.visible = true;
                this.field_189838_N.visible = true;
                this.field_189839_O.visible = true;
                this.field_189841_Q.visible = true;
                break;

            case LOAD:
                this.field_189853_u.setVisible(true);
                this.field_189853_u.setFocused(true);
                this.field_189854_v.setVisible(true);
                this.field_189855_w.setVisible(true);
                this.field_189856_x.setVisible(true);
                this.field_189826_B.setVisible(true);
                this.field_189827_C.setVisible(true);
                this.field_189832_H.visible = true;
                this.field_189839_O.visible = true;
                this.field_189840_P.visible = true;
                this.field_189833_I.visible = true;
                this.field_189834_J.visible = true;
                this.field_189835_K.visible = true;
                this.field_189836_L.visible = true;
                this.field_189842_R.visible = true;
                this.func_189824_i();
                break;

            case CORNER:
                this.field_189853_u.setVisible(true);
                this.field_189853_u.setFocused(true);
                break;

            case DATA:
                this.field_189828_D.setVisible(true);
                this.field_189828_D.setFocused(true);
        }

        this.field_189837_M.displayString = I18n.format("structure_block.mode." + this.field_189846_f.func_189700_k().getName(), new Object[0]);
    }

    private boolean func_189820_b(int p_189820_1_)
    {
        try
        {
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            this.field_189846_f.func_189705_a(packetbuffer);
            packetbuffer.writeByte(p_189820_1_);
            packetbuffer.writeString(this.field_189846_f.func_189700_k().toString());
            packetbuffer.writeString(this.field_189853_u.getText());
            packetbuffer.writeInt(this.func_189817_c(this.field_189854_v.getText()));
            packetbuffer.writeInt(this.func_189817_c(this.field_189855_w.getText()));
            packetbuffer.writeInt(this.func_189817_c(this.field_189856_x.getText()));
            packetbuffer.writeInt(this.func_189817_c(this.field_189857_y.getText()));
            packetbuffer.writeInt(this.func_189817_c(this.field_189858_z.getText()));
            packetbuffer.writeInt(this.func_189817_c(this.field_189825_A.getText()));
            packetbuffer.writeString(this.field_189846_f.func_189716_h().toString());
            packetbuffer.writeString(this.field_189846_f.func_189726_i().toString());
            packetbuffer.writeString(this.field_189828_D.getText());
            packetbuffer.writeBoolean(this.field_189846_f.func_189713_m());
            packetbuffer.writeBoolean(this.field_189846_f.func_189707_H());
            packetbuffer.writeBoolean(this.field_189846_f.func_189721_I());
            packetbuffer.writeFloat(this.func_189819_b(this.field_189826_B.getText()));
            packetbuffer.writeVarLong(this.func_189821_a(this.field_189827_C.getText()));
            this.mc.getConnection().sendPacket(new CPacketCustomPayload("MC|Struct", packetbuffer));
            return true;
        }
        catch (Exception exception)
        {
            field_189845_a.warn((String)"Could not send structure block info", (Throwable)exception);
            return false;
        }
    }

    private long func_189821_a(String p_189821_1_)
    {
        try
        {
            return Long.valueOf(p_189821_1_).longValue();
        }
        catch (NumberFormatException var3)
        {
            return 0L;
        }
    }

    private float func_189819_b(String p_189819_1_)
    {
        try
        {
            return Float.valueOf(p_189819_1_).floatValue();
        }
        catch (NumberFormatException var3)
        {
            return 1.0F;
        }
    }

    private int func_189817_c(String p_189817_1_)
    {
        try
        {
            return Integer.parseInt(p_189817_1_);
        }
        catch (NumberFormatException var3)
        {
            return 0;
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.field_189853_u.getVisible() && func_189818_a(typedChar))
        {
            this.field_189853_u.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189854_v.getVisible())
        {
            this.field_189854_v.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189855_w.getVisible())
        {
            this.field_189855_w.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189856_x.getVisible())
        {
            this.field_189856_x.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189857_y.getVisible())
        {
            this.field_189857_y.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189858_z.getVisible())
        {
            this.field_189858_z.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189825_A.getVisible())
        {
            this.field_189825_A.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189826_B.getVisible())
        {
            this.field_189826_B.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189827_C.getVisible())
        {
            this.field_189827_C.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.field_189828_D.getVisible())
        {
            this.field_189828_D.textboxKeyTyped(typedChar, keyCode);
        }

        if (keyCode == 15)
        {
            GuiTextField guitextfield = null;
            GuiTextField guitextfield1 = null;

            for (GuiTextField guitextfield2 : this.field_189843_S)
            {
                if (guitextfield != null && guitextfield2.getVisible())
                {
                    guitextfield1 = guitextfield2;
                    break;
                }

                if (guitextfield2.isFocused() && guitextfield2.getVisible())
                {
                    guitextfield = guitextfield2;
                }
            }

            if (guitextfield != null && guitextfield1 == null)
            {
                for (GuiTextField guitextfield3 : this.field_189843_S)
                {
                    if (guitextfield3.getVisible() && guitextfield3 != guitextfield)
                    {
                        guitextfield1 = guitextfield3;
                        break;
                    }
                }
            }

            if (guitextfield1 != null && guitextfield1 != guitextfield)
            {
                guitextfield.setFocused(false);
                guitextfield1.setFocused(true);
            }
        }

        if (keyCode != 28 && keyCode != 156)
        {
            if (keyCode == 1)
            {
                this.actionPerformed(this.field_189830_F);
            }
        }
        else
        {
            this.actionPerformed(this.field_189829_E);
        }
    }

    private static boolean func_189818_a(char p_189818_0_)
    {
        boolean flag = true;

        for (char c0 : ChatAllowedCharacters.field_189861_b)
        {
            if (c0 == p_189818_0_)
            {
                flag = false;
                break;
            }
        }

        return flag;
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (this.field_189853_u.getVisible())
        {
            this.field_189853_u.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189854_v.getVisible())
        {
            this.field_189854_v.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189855_w.getVisible())
        {
            this.field_189855_w.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189856_x.getVisible())
        {
            this.field_189856_x.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189857_y.getVisible())
        {
            this.field_189857_y.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189858_z.getVisible())
        {
            this.field_189858_z.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189825_A.getVisible())
        {
            this.field_189825_A.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189826_B.getVisible())
        {
            this.field_189826_B.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189827_C.getVisible())
        {
            this.field_189827_C.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.field_189828_D.getVisible())
        {
            this.field_189828_D.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        TileEntityStructure.Mode tileentitystructure$mode = this.field_189846_f.func_189700_k();
        this.drawCenteredString(this.fontRendererObj, I18n.format("tile.structureBlock.name", new Object[0]), this.width / 2, 10, 16777215);

        if (tileentitystructure$mode != TileEntityStructure.Mode.DATA)
        {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.structure_name", new Object[0]), this.width / 2 - 153, 30, 10526880);
            this.field_189853_u.drawTextBox();
        }

        if (tileentitystructure$mode == TileEntityStructure.Mode.LOAD || tileentitystructure$mode == TileEntityStructure.Mode.SAVE)
        {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.position", new Object[0]), this.width / 2 - 153, 70, 10526880);
            this.field_189854_v.drawTextBox();
            this.field_189855_w.drawTextBox();
            this.field_189856_x.drawTextBox();
            String s = I18n.format("structure_block.include_entities", new Object[0]);
            int i = this.fontRendererObj.getStringWidth(s);
            this.drawString(this.fontRendererObj, s, this.width / 2 + 154 - i, 150, 10526880);
        }

        if (tileentitystructure$mode == TileEntityStructure.Mode.SAVE)
        {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.size", new Object[0]), this.width / 2 - 153, 110, 10526880);
            this.field_189857_y.drawTextBox();
            this.field_189858_z.drawTextBox();
            this.field_189825_A.drawTextBox();
            String s2 = I18n.format("structure_block.detect_size", new Object[0]);
            int k = this.fontRendererObj.getStringWidth(s2);
            this.drawString(this.fontRendererObj, s2, this.width / 2 + 154 - k, 110, 10526880);
            String s1 = I18n.format("structure_block.show_air", new Object[0]);
            int j = this.fontRendererObj.getStringWidth(s1);
            this.drawString(this.fontRendererObj, s1, this.width / 2 + 154 - j, 70, 10526880);
        }

        if (tileentitystructure$mode == TileEntityStructure.Mode.LOAD)
        {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.integrity", new Object[0]), this.width / 2 - 153, 110, 10526880);
            this.field_189826_B.drawTextBox();
            this.field_189827_C.drawTextBox();
            String s3 = I18n.format("structure_block.show_boundingbox", new Object[0]);
            int l = this.fontRendererObj.getStringWidth(s3);
            this.drawString(this.fontRendererObj, s3, this.width / 2 + 154 - l, 70, 10526880);
        }

        if (tileentitystructure$mode == TileEntityStructure.Mode.DATA)
        {
            this.drawString(this.fontRendererObj, I18n.format("structure_block.custom_data", new Object[0]), this.width / 2 - 153, 110, 10526880);
            this.field_189828_D.drawTextBox();
        }

        String s4 = "structure_block.mode_info." + tileentitystructure$mode.getName();
        this.drawString(this.fontRendererObj, I18n.format(s4, new Object[0]), this.width / 2 - 153, 174, 10526880);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}

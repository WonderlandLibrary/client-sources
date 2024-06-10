using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using GlobalLowLevelHooks;

namespace daidesign
{
    public partial class clicker : UserControl
    {
        public clicker()
        {
            InitializeComponent();
        }

        public Color offdescolor, offdotbcolor, offdotfcolor, offnamecolor,offpboxcolor,offpanelcolor;
        public int selected = 1;

        public bool setkey = false;
        public bool setkey2 = false;

        private void clicker_Load(object sender, EventArgs e)
        {
            kbhook.KeyDown += keyboardHook_KeyDown;
            kbhook.Install();
            offdescolor = label2.ForeColor;
            offdotbcolor = label3.BackColor;
            offdotfcolor = label3.ForeColor;
            offnamecolor = label1.ForeColor;
            offpboxcolor = pictureBox1.BackColor;
            offpanelcolor = panel1.BackColor;
        }

        private void keyboardHook_KeyDown(KeyboardHook.VKeys key)
        {
            if (setkey)
            {
                clicker_settings.LeftBind = key;
                guna2Button2.Text = key.ToString().Replace("KEY_", "").Replace("OEM_", "").Replace("ACCEPT", "/");
                setkey = false;
            }
            else if (setkey2)
            {
                clicker_settings.RightBind = key;
                guna2Button1.Text = key.ToString().Replace("KEY_", "").Replace("OEM_", "").Replace("ACCEPT", "/");
                setkey2 = false;
            }
            else if(key == clicker_settings.LeftBind)
            {
                //işlem
            }
            else if(key == clicker_settings.RightBind)
            {
                //işlem
            }
        }


        KeyboardHook kbhook = new KeyboardHook();

        private void guna2ToggleSwitch2_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2ToggleSwitch2.Checked)
            {
                pictureBox2.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                guna2Button1.FillColor = onpanelcolor;
                guna2Button1.BorderColor = guna2ToggleSwitch1.CheckedState.FillColor;
                guna2Button1.ForeColor = Color.White;
                label6.ForeColor = Color.White;
                label5.ForeColor = ondescolor;
                pictureBox2.Image = Properties.Resources.mouse_96px;
                label4.BackColor = ondotbcolor;
                label4.ForeColor = ondotfcolor;
                panel2.BackColor = onpanelcolor;
            }
            else
            {
                guna2Transition1.HideSync(panel3);
                pictureBox2.BackColor = offpboxcolor;
                guna2Button1.FillColor = guna2ToggleSwitch1.UncheckedState.FillColor;
                guna2Button1.BorderColor = guna2ToggleSwitch1.UncheckedState.BorderColor;
                guna2Button1.ForeColor = guna2ToggleSwitch1.UncheckedState.InnerColor;
                label6.ForeColor = offnamecolor;
                label5.ForeColor = offdescolor;
                pictureBox2.Image = Properties.Resources.mouse_96pxoff;
                label4.BackColor = offdotbcolor;
                label4.ForeColor = offdotfcolor;
                panel2.BackColor = offpanelcolor;
            }
        }

        private void guna2TextBox2_TextChanged(object sender, EventArgs e)
        {
            if(guna2TextBox2.Text.Length == 0)
            {
                guna2TextBox2.Text = "0";
            }
        }

        private void guna2TextBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = !char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar);
        }

        private void guna2TextBox1_TextChanged(object sender, EventArgs e)
        {
            if (guna2TextBox1.Text.Length == 0)
            {
                guna2TextBox1.Text = "0";
            }
        }

        private void guna2TextBox2_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = !char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar);
        }

        private void label3_Click(object sender, EventArgs e)
        {
            if(label3.BackColor != guna2ToggleSwitch1.CheckedState.FillColor && guna2ToggleSwitch1.Checked && label4.BackColor != guna2ToggleSwitch1.CheckedState.FillColor)
            {
                selected = 1;
                guna2TrackBar1.Value = clicker_settings.LeftMin;
                guna2TrackBar2.Value = clicker_settings.LeftMax;
                guna2TextBox1.Text = clicker_settings.LeftMin.ToString();
                guna2TextBox2.Text = clicker_settings.LeftMax.ToString();
                guna2ToggleSwitch3.Checked = clicker_settings.LeftJitter;
                guna2Transition1.ShowSync(panel3);
                label3.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label3.ForeColor = Color.White;
            }
            else if(guna2ToggleSwitch1.Checked && label3.BackColor == guna2ToggleSwitch1.CheckedState.FillColor)
            {
                guna2Transition1.HideSync(panel3);
                label3.BackColor = ondotbcolor;
                label3.ForeColor = ondotfcolor;
            }

        }

        private void guna2TrackBar2_Scroll(object sender, ScrollEventArgs e)
        {

            if(selected == 1)
            {
                guna2TextBox2.Text = guna2TrackBar2.Value.ToString();
                clicker_settings.LeftMax = guna2TrackBar2.Value;
            }
            else
            {
                guna2TextBox2.Text = guna2TrackBar2.Value.ToString();
                clicker_settings.RightMax = guna2TrackBar2.Value;
            }
        }

        private void guna2TrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            if(selected == 1)
            {
                guna2TextBox1.Text = guna2TrackBar1.Value.ToString();
                clicker_settings.LeftMin = guna2TrackBar1.Value;
            }
            else
            {
                guna2TextBox1.Text = guna2TrackBar1.Value.ToString();
                clicker_settings.RightMin = guna2TrackBar1.Value;
            }
        }

        private void label4_Click(object sender, EventArgs e)
        {
            if (label4.BackColor != guna2ToggleSwitch1.CheckedState.FillColor && guna2ToggleSwitch2.Checked && label3.BackColor != guna2ToggleSwitch1.CheckedState.FillColor)
            {
                selected = 2;
                guna2TrackBar1.Value = clicker_settings.RightMin;
                guna2TrackBar2.Value = clicker_settings.RightMax;
                guna2TextBox1.Text = clicker_settings.RightMin.ToString();
                guna2TextBox2.Text = clicker_settings.RightMax.ToString();
                guna2ToggleSwitch3.Checked = clicker_settings.RightJitter;
                guna2Transition1.ShowSync(panel3);
                label4.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label4.ForeColor = Color.White;
            }
            else if (guna2ToggleSwitch2.Checked && label4.BackColor == guna2ToggleSwitch1.CheckedState.FillColor)
            {
                guna2Transition1.HideSync(panel3);
                label4.BackColor = ondotbcolor;
                label4.ForeColor = ondotfcolor;
            }
        }

        private void guna2ToggleSwitch3_CheckedChanged(object sender, EventArgs e)
        {
            if(selected == 1)
            {
                clicker_settings.LeftJitter = guna2ToggleSwitch3.Checked;

            }
            else
            {
                clicker_settings.RightJitter = guna2ToggleSwitch3.Checked;
            }
        }

        private void guna2Button2_Click(object sender, EventArgs e)
        {
            setkey = true;
            guna2Button2.Text = "> / <";
        }

        private void guna2Button1_Click(object sender, EventArgs e)
        {
            setkey2 = true;
            guna2Button1.Text = "> / <";
        }

        public Color ondescolor = Color.FromArgb(91, 91, 93);
        public Color ondotbcolor = Color.FromArgb(46, 46, 51);
        public Color ondotfcolor = Color.FromArgb(73, 75, 80);
        public Color onpanelcolor = Color.FromArgb(38, 38, 41);
        private void guna2ToggleSwitch1_CheckedChanged(object sender, EventArgs e)
        {
            if(guna2ToggleSwitch1.Checked)
            {
                pictureBox1.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                guna2Button2.FillColor = onpanelcolor;
                guna2Button2.BorderColor = guna2ToggleSwitch1.CheckedState.FillColor;
                guna2Button2.ForeColor = Color.White;
                label1.ForeColor = Color.White;
                label2.ForeColor = ondescolor;
                pictureBox1.Image = Properties.Resources.mouse_96px;
                label3.BackColor = ondotbcolor;
                label3.ForeColor = ondotfcolor;
                panel1.BackColor = onpanelcolor;
            }
            else
            {
                guna2Transition1.HideSync(panel3);
                guna2Button2.FillColor = guna2ToggleSwitch1.UncheckedState.FillColor;
                guna2Button2.BorderColor = guna2ToggleSwitch1.UncheckedState.BorderColor;
                guna2Button2.ForeColor = guna2ToggleSwitch1.UncheckedState.InnerColor;
                pictureBox1.BackColor = offpboxcolor;
                label1.ForeColor = offnamecolor;
                label2.ForeColor = offdescolor;
                pictureBox1.Image = Properties.Resources.mouse_96pxoff;
                label3.BackColor = offdotbcolor;
                label3.ForeColor = offdotfcolor;
                panel1.BackColor = offpanelcolor;
            }

        }
    }
}

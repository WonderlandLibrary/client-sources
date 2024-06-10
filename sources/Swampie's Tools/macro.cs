using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace daidesign
{
    public partial class macro : UserControl
    {
        public macro()
        {
            InitializeComponent();
        }

        private void label3_Click(object sender, EventArgs e)
        {
            if (label3.BackColor != guna2ToggleSwitch1.CheckedState.FillColor && guna2ToggleSwitch1.Checked && label4.BackColor != guna2ToggleSwitch1.CheckedState.FillColor)
            {
                selected = 1;
                guna2TrackBar1.Value = clicker_settings.RodDelay;
                guna2NumericUpDown1.Value = clicker_settings.RodSwordSlot;
                guna2NumericUpDown2.Value = clicker_settings.RodSlot;
                guna2TextBox1.Text = clicker_settings.RodDelay.ToString();
                label8.Text = "Rod Slot";
                label11.Text = clicker_settings.RodBind.ToString().Replace("KEY_", "").Replace("OEM_", "").Replace("ACCEPT", "...");
                guna2Transition1.ShowSync(panel3);
                label3.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label3.ForeColor = Color.White;
            }
            else if (guna2ToggleSwitch1.Checked && label3.BackColor == guna2ToggleSwitch1.CheckedState.FillColor)
            {
                guna2Transition1.HideSync(panel3);
                label3.BackColor = ondotbcolor;
                label3.ForeColor = ondotfcolor;
            }
        }
        public Color offdescolor, offdotbcolor, offdotfcolor, offnamecolor, offpboxcolor, offpanelcolor;
        public int selected = 1;

        private void macro_Load(object sender, EventArgs e)
        {
            offdescolor = label2.ForeColor;
            offdotbcolor = label3.BackColor;
            offdotfcolor = label3.ForeColor;
            offnamecolor = label1.ForeColor;
            offpboxcolor = pictureBox1.BackColor;
            offpanelcolor = panel1.BackColor;
        }

        private void guna2ToggleSwitch2_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2ToggleSwitch2.Checked)
            {
                pictureBox2.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label6.ForeColor = Color.White;
                label5.ForeColor = ondescolor;
                pictureBox2.Image = Properties.Resources.spiderweb_96px;
                label4.BackColor = ondotbcolor;
                label4.ForeColor = ondotfcolor;
                panel2.BackColor = onpanelcolor;

            }
            else
            {
                guna2Transition1.HideSync(panel3);
                pictureBox2.BackColor = offpboxcolor;
                label6.ForeColor = offnamecolor;
                label5.ForeColor = offdescolor;
                pictureBox2.Image = Properties.Resources.spiderweb_96pxoff;
                label4.BackColor = offdotbcolor;
                label4.ForeColor = offdotfcolor;
                panel2.BackColor = offpanelcolor;
               
            }
        }

        private void label4_Click(object sender, EventArgs e)
        {
            if (label4.BackColor != guna2ToggleSwitch1.CheckedState.FillColor && guna2ToggleSwitch2.Checked && label3.BackColor != guna2ToggleSwitch1.CheckedState.FillColor)
            {
                selected = 2;
                guna2TrackBar1.Value = clicker_settings.WebDelay;
                guna2NumericUpDown1.Value = clicker_settings.WebSwordSlot;
                guna2NumericUpDown2.Value = clicker_settings.WebSlot;
                guna2TextBox1.Text = clicker_settings.WebDelay.ToString();
                label8.Text = "Web Slot";
                label11.Text = clicker_settings.WebBind.ToString().Replace("KEY_", "").Replace("OEM_", "").Replace("ACCEPT", "...");
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

        private void guna2TrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            if(selected == 1)
            {
                clicker_settings.RodDelay = guna2TrackBar1.Value;
                guna2TextBox1.Text = guna2TrackBar1.Value.ToString();
            }
            else
            {
                clicker_settings.WebDelay = guna2TrackBar1.Value;
                guna2TextBox1.Text = guna2TrackBar1.Value.ToString();
            }
        }

        private void guna2NumericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            if(selected == 1)
            {
                clicker_settings.RodSwordSlot = (int)guna2NumericUpDown1.Value;
            }
            else
            {
                clicker_settings.WebSwordSlot = (int)guna2NumericUpDown1.Value;
            }
        }

        private void guna2NumericUpDown2_ValueChanged(object sender, EventArgs e)
        {
            if (selected == 1)
            {
                clicker_settings.RodSlot = (int)guna2NumericUpDown2.Value;
            }
            else
            {
                clicker_settings.WebSlot = (int)guna2NumericUpDown2.Value;
            }
        }

        public Color ondescolor = Color.FromArgb(91, 91, 93);
        public Color ondotbcolor = Color.FromArgb(46, 46, 51);
        public Color ondotfcolor = Color.FromArgb(73, 75, 80);
        public Color onpanelcolor = Color.FromArgb(38, 38, 41);

        private void guna2ToggleSwitch1_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2ToggleSwitch1.Checked)
            {
                pictureBox1.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label1.ForeColor = Color.White;
                label2.ForeColor = ondescolor;
                pictureBox1.Image = Properties.Resources.fishing_pole_96px;
                label3.BackColor = ondotbcolor;
                label3.ForeColor = ondotfcolor;
                panel1.BackColor = onpanelcolor;
            }
            else
            {
                guna2Transition1.HideSync(panel3);
                pictureBox1.BackColor = offpboxcolor;
                label1.ForeColor = offnamecolor;
                label2.ForeColor = offdescolor;
                pictureBox1.Image = Properties.Resources.fishing_pole_96pxoff;
                label3.BackColor = offdotbcolor;
                label3.ForeColor = offdotfcolor;
                panel1.BackColor = offpanelcolor;
            }
        }
    }
}

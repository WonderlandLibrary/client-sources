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
    public partial class cheats : UserControl
    {
        public cheats()
        {
            InitializeComponent();
        }

        public Color offdescolor, offdotbcolor, offdotfcolor, offnamecolor, offpboxcolor, offpanelcolor;
        public int selected = 1;

        public Color ondescolor = Color.FromArgb(91, 91, 93);

        private void cheats_Load(object sender, EventArgs e)
        {
            offdescolor = label2.ForeColor;
            offdotbcolor = label3.BackColor;
            offdotfcolor = label3.ForeColor;
            offnamecolor = label1.ForeColor;
            offpboxcolor = pictureBox1.BackColor;
            offpanelcolor = panel1.BackColor;
        }

        private void guna2ToggleSwitch3_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2ToggleSwitch3.Checked)
            {
                pictureBox3.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label9.ForeColor = Color.White;
                label8.ForeColor = ondescolor;
                pictureBox3.Image = Properties.Resources.diamond_96px;
                label7.BackColor = ondotbcolor;
                label7.ForeColor = ondotfcolor;
                panel3.BackColor = onpanelcolor;
            }
            else
            {
                pictureBox3.BackColor = offpboxcolor;
                label9.ForeColor = offnamecolor;
                label8.ForeColor = offdescolor;
                pictureBox3.Image = Properties.Resources.diamond_96pxoff;
                label7.BackColor = offdotbcolor;
                label7.ForeColor = offdotfcolor;
                panel3.BackColor = offpanelcolor;
            }
        }

        private void guna2ToggleSwitch4_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2ToggleSwitch4.Checked)
            {
                pictureBox4.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label12.ForeColor = Color.White;
                label11.ForeColor = ondescolor;
                pictureBox4.Image = Properties.Resources.speed_96px;
                label10.BackColor = ondotbcolor;
                label10.ForeColor = ondotfcolor;
                panel4.BackColor = onpanelcolor;
            }
            else
            {
                pictureBox4.BackColor = offpboxcolor;
                label12.ForeColor = offnamecolor;
                label11.ForeColor = offdescolor;
                pictureBox4.Image = Properties.Resources.diamond_96pxoff;
                label10.BackColor = offdotbcolor;
                label10.ForeColor = offdotfcolor;
                panel4.BackColor = offpanelcolor;
            }
        }

        private void label10_Click(object sender, EventArgs e)
        {
            if(guna2ToggleSwitch4.Checked && label10.BackColor != guna2ToggleSwitch4.CheckedState.FillColor)
            {
                guna2TrackBar1.Value = clicker_settings.speed;
                guna2TextBox1.Text = clicker_settings.speed.ToString();
                guna2Transition1.ShowSync(panel5);
                label10.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label10.ForeColor = Color.White;
            }
            else if (guna2ToggleSwitch4.Checked && label10.BackColor == guna2ToggleSwitch1.CheckedState.FillColor)
            {
                guna2Transition1.HideSync(panel5);
                label10.BackColor = ondotbcolor;
                label10.ForeColor = ondotfcolor;
            }
        }

        private void guna2TrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            guna2TextBox1.Text = guna2TrackBar1.Value.ToString();
            clicker_settings.speed = guna2TrackBar1.Value;
        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

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
                pictureBox1.Image = Properties.Resources.base_jumping_96px;
                label3.BackColor = ondotbcolor;
                label3.ForeColor = ondotfcolor;
                panel1.BackColor = onpanelcolor;
            }
            else
            {
                pictureBox1.BackColor = offpboxcolor;
                label1.ForeColor = offnamecolor;
                label2.ForeColor = offdescolor;
                pictureBox1.Image = Properties.Resources.base_jumping_96pxoff;
                label3.BackColor = offdotbcolor;
                label3.ForeColor = offdotfcolor;
                panel1.BackColor = offpanelcolor;
            }
        }

        private void guna2ToggleSwitch2_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2ToggleSwitch2.Checked)
            {
                pictureBox2.BackColor = guna2ToggleSwitch1.CheckedState.FillColor;
                label6.ForeColor = Color.White;
                label5.ForeColor = ondescolor;
                pictureBox2.Image = Properties.Resources.eye_96px;
                label4.BackColor = ondotbcolor;
                label4.ForeColor = ondotfcolor;
                panel2.BackColor = onpanelcolor;
            }
            else
            {

                pictureBox2.BackColor = offpboxcolor;
                label6.ForeColor = offnamecolor;
                label5.ForeColor = offdescolor;
                pictureBox2.Image = Properties.Resources.eyelashes_2d_96px;
                label4.BackColor = offdotbcolor;
                label4.ForeColor = offdotfcolor;
                panel2.BackColor = offpanelcolor;
            }
        }
    }
}

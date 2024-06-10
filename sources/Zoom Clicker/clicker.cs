using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Zoom
{
    public partial class Zoom : Form
    {
        public Zoom()
        {
            InitializeComponent();
        }

        private void clicker_Load(object sender, EventArgs e)
        {
           
        }

        private void guna2TrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            
        }

        private void guna2CheckBox3_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2CheckBox3.Checked)
            {
                timer1.Start();
            }
            else
            {
                timer1.Stop();
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            try
            {
                timer1.Interval = 800 / Convert.ToInt32((guna2TrackBar1.Value).ToString("0"));
                if (guna2CheckBox3.Checked == true)
                {
                    if (MouseButtons == MouseButtons.Left)
                    {
                        clickerclass.leftclick(1);
                    }
                }
            }
            catch (Exception) { }
        }

        private void guna2Button2_Click(object sender, EventArgs e)
        {
            this.Hide();
            timer1.Stop();
            timer2.Stop();
        }

        private void guna2TrackBar1_Scroll_1(object sender, ScrollEventArgs e)
        {
            
            label1.Text = (guna2TrackBar1.Value).ToString("0.0") + "";
            
        }

        private void guna2Button1_Click(object sender, EventArgs e)
        {
            settings rr = new settings();
            rr.Show();
            
        }

        private void gunaTrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            label3.Text = (gunaTrackBar1.Value).ToString("0.0") + "";
        }

        private void guna2CheckBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2CheckBox1.Checked)
            {
                timer2.Start();
            }
            else
            {
                timer2.Stop();
            }
        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void guna2PictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            try
            {
                timer2.Interval = 800 / Convert.ToInt32((gunaTrackBar1.Value).ToString("0"));
                if (guna2CheckBox1.Checked == true)
                {
                    if (MouseButtons == MouseButtons.Right)
                    {
                        clickerclass.rightclick(1);
                    }
                }
            }
            catch (Exception) { }
        }
    }
}

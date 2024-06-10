using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using System.IO;
using MetroFramework.Controls;
using System.Diagnostics;
using System.Runtime.CompilerServices;
using System.Threading;
using Bunifu.Framework.UI;
using System.Net;

namespace Telegram
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private Random random_0 = new Random();

        [DllImport("user32.dll")]
        public static extern bool mouse_event(int asd, int dsa, int gsd, int he, int agfh);

        public const int AaAa = 2;
        public const int BbBb = 4;
        public const int CcCc = 32;
        public const int DdDd = 64;
        public const int EeEe = 8;
        public const int FfFf = 16;

        private int Telegram;

        private int Spotify;

        int mouseX = 0, mouseY = 0;
        bool mouseDown;

        private void bunifuGradientPanel1_MouseDown(object sender, MouseEventArgs e)
        {
            mouseDown = true;
        }

        private void bunifuGradientPanel1_MouseMove(object sender, MouseEventArgs e)
        {
            if (mouseDown)
            {
                mouseX = MousePosition.X - 150;
                mouseY = MousePosition.Y - 20;

                this.SetDesktopLocation(mouseX, mouseY);
            }
        }

        private void bunifuGradientPanel1_MouseUp(object sender, MouseEventArgs e)
        {
            mouseDown = false;
        }

        private void bunifuThinButton23_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void bunifuThinButton24_Click(object sender, EventArgs e)
        {
            this.WindowState = FormWindowState.Minimized;
        }

        //Left Clicker

        private void bunifuTrackbar1_ValueChanged(object sender, EventArgs e)
        {
            this.bunifuCustomLabel5.Text = this.bunifuTrackbar1.Value.ToString();
            if (this.bunifuTrackbar1.Value > this.bunifuTrackbar2.Value)
            {
                this.bunifuTrackbar2.Value = this.bunifuTrackbar1.Value;
                this.bunifuCustomLabel6.Text = this.bunifuTrackbar2.Value.ToString();
            }
        }

        private void bunifuThinButton21_Click(object sender, EventArgs e)
        {
            this.Telegram++;
            if (this.Telegram == 1)
            {
                return;
            }
            this.Telegram = 0;
        }

        private void bunifuThinButton21_TextChanged(object sender, EventArgs e)
        {
            if (this.bunifuThinButton21.Text == "")
            {
                this.Timer1.Start();
                return;
            }
            this.Timer1.Stop();
        }

        private void bunifuTrackbar2_ValueChanged(object sender, EventArgs e)
        {
            this.bunifuCustomLabel6.Text = this.bunifuTrackbar2.Value.ToString();
            if (this.bunifuTrackbar2.Value < this.bunifuTrackbar1.Value)
            {
                this.bunifuTrackbar1.Value = this.bunifuTrackbar2.Value;
                this.bunifuCustomLabel5.Text = this.bunifuTrackbar1.Value.ToString();
            }
        }

        private void bunifuThinButton21_MouseHover(object sender, EventArgs e)
        {
            if (this.bunifuThinButton21.Text == "")
            {
                this.Timer1.Stop();
            }
        }

        private void bunifuThinButton21_MouseLeave(object sender, EventArgs e)
        {
            if (this.bunifuThinButton21.Text == "")
            {
                this.Timer1.Start();
            }
        }

        private void bunifuTrackbar1_MouseDown(object sender, MouseEventArgs e)
        {
            if (this.bunifuThinButton21.Text == "")
            {
                this.Timer1.Stop();
            }
        }

        private void bunifuTrackbar1_MouseUp(object sender, MouseEventArgs e)
        {
            if (this.bunifuThinButton21.Text == "")
            {
                this.Timer1.Start();
            }
        }



        private void Form1_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.F)
            {
                this.Telegram++;
                if (this.Telegram == 1)
                {
                    this.bunifuThinButton21.Text = "";
                    return;
                }
                this.Telegram = 0;
                this.bunifuThinButton21.Text = "";
            }
        }

        private void bunifuCustomLabel5_Click(object sender, EventArgs e)
        {

        }


        private void timer1_Tick(object sender, EventArgs e)
        {
            try
            {
                int num = (int)(1000.0 / (double)this.bunifuTrackbar1.Value);
                int num2 = (int)(1000.0 / (double)this.bunifuTrackbar2.Value);
                this.Timer1.Interval = (num + num2) / 2;
                if (Control.MouseButtons == MouseButtons.Right)
                {
                    Form1.mouse_event(4, 0, 0, 0, 0);
                    Form1.mouse_event(2, 0, 0, 0, 0);
                }
            }
            catch
            {
                Close();
            }
        }

        //Right Clicker


        private void bunifuTrackbar3_ValueChanged(object sender, EventArgs e)
        {
            
        }

        private void bunifuThinButton22_Click(object sender, EventArgs e)
        {
        }

        private void bunifuThinButton22_TextChanged(object sender, EventArgs e)
        {
        }

                private void bunifuTrackbar4_ValueChanged(object sender, EventArgs e)
                {

                }

                private void bunifuThinButton22_MouseHover(object sender, EventArgs e)
                {

                }

                private void bunifuThinButton22_MouseLeave(object sender, EventArgs e)
                {

                }

                private void bunifuTrackbar3_MouseDown(object sender, MouseEventArgs e)
                {

                }

                private void bunifuTrackbar3_MouseUp(object sender, MouseEventArgs e)
                {

                }


                private void bunifuCustomLabel8_Click(object sender, EventArgs e)
                {

                }

                private void bunifuGradientPanel3_Paint(object sender, PaintEventArgs e)
                {

                }

                private void chkMinecraft_OnChange(object sender, EventArgs e)
                {

                }

                private void bunifuTrackbar4_ValueChanged_1(object sender, EventArgs e)
                {

                }

                private void Form2_KeyDown(object sender, KeyEventArgs e)

                {




                }

                private void bunifuThinButton21_MouseDown(object sender, EventArgs e)
                {

                }

                private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
                {


                }



                private void timer3_Tick(object sender, EventArgs e)
                {

                }




            }
        }
    






        



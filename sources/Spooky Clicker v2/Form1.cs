using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace spookyclientremakev2
{
    public partial class Form1 : Form
    {
        [DllImport("user32.dll", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall)]
        public static extern void mouse_event(int dwFlags, int dw, int dy, int cButtons, int dwExtraInfo);

        public const int LEFTUP = 0x0004;
        public const int LEFTDOWN = 0x0002;
        public Form1()
        {
            InitializeComponent();
        }

        private void bunifuHScrollBar3_Scroll(object sender, Bunifu.UI.WinForms.BunifuHScrollBar.ScrollEventArgs e)
        {

        }

        private void bunifuHScrollBar1_Scroll(object sender, Bunifu.UI.WinForms.BunifuHScrollBar.ScrollEventArgs e)
        {
            bunifuCustomLabel5.Text = "" + bunifuHScrollBar1.Value;
        }

        private void bunifuHScrollBar2_Scroll(object sender, Bunifu.UI.WinForms.BunifuHScrollBar.ScrollEventArgs e)
        {
            bunifuCustomLabel6.Text = "" + bunifuHScrollBar2.Value;
        }

        private void bunifuCustomLabel10_Click(object sender, EventArgs e)
        {

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            int mincps = bunifuHScrollBar1.Value;
            int maxcps = bunifuHScrollBar2.Value;
            int cps = 1000 / mincps + maxcps;

            try
            {
                timer1.Interval = rnd.Next(cps);
            }
            catch { }

            bool mousedown = MouseButtons == MouseButtons.Left;
            if (mousedown)
            {
                mouse_event(dwFlags: LEFTUP, dw: 0, dy: 0, cButtons: 0, dwExtraInfo: 0);
                Thread.Sleep(rnd.Next(2, 4));
                mouse_event(dwFlags: LEFTDOWN, dw: 0, dy: 0, cButtons: 0, dwExtraInfo: 0);
            }
        }

        private void bunifuFlatButton1_Click(object sender, EventArgs e)
        {
            if (bunifuFlatButton1.Text == "Status: Disabled")
            {
                bunifuFlatButton1.Text = "Status: Enabled";
                timer1.Start();
            }
            else if (bunifuFlatButton1.Text == "Status: Enabled")
            {
                bunifuFlatButton1.Text = "Status: Disabled";
                timer1.Stop();
            }
        }

        private void bunifuImageButton3_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Remake by MUSMICKEY | Skidders Corp.");
        }

        private void bunifuImageButton4_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Remake by MUSMICKEY | Skidders Corp.");
        }

        private void bunifuImageButton5_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Remake by MUSMICKEY | Skidders Corp.");
        }

        private void bunifuImageButton6_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Remake by MUSMICKEY | Skidders Corp.");
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void bunifuImageButton1_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }

        private void bunifuImageButton2_Click(object sender, EventArgs e)
        {
            this.WindowState = FormWindowState.Minimized;
        }
    }
}

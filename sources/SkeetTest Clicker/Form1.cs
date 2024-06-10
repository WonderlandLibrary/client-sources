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

namespace skeettest
{
    public partial class mainform : Form
    {
        [DllImport("user32.dll")]
        public static extern int FindWindow(string lpClassName, string lpWindowName);

        // Token: 0x06000027 RID: 39
        [DllImport("user32.dll")]
        public static extern int SendMessage(IntPtr hWnd, int wMsg, int wParam, int lParam);

        [DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
        private static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll")]
        static extern short GetAsyncKeyState(Keys vKey);

        public mainform()
        {
            InitializeComponent();
        }

        private void shadowLabel1_Click(object sender, EventArgs e)
        {

        }

        private void outlineLabel1_Click(object sender, EventArgs e)
        {

        }

        private void mainform_Load(object sender, EventArgs e)
        {

        }

        private void guna2HScrollBar2_Scroll(object sender, ScrollEventArgs e)
        {
            label1.Text = "" + guna2HScrollBar2.Value;
        }

        private void guna2HScrollBar3_Scroll(object sender, ScrollEventArgs e)
        {
            label2.Text = "" + guna2HScrollBar3.Value;
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            int mincps = guna2HScrollBar2.Value;
            int maxcps = guna2HScrollBar3.Value;
            Random rnd = new Random();

            try
            {
                int cps;
                if (mincps != maxcps)
                {
                    cps = rnd.Next(mincps, maxcps);
                    timer1.Interval = rnd.Next(1750 / cps);
                }
                else if (mincps == maxcps)
                {
                    cps = maxcps + 1;
                    timer1.Interval = 1000 / cps;
                }
            }
            catch { }

            if (GetAsyncKeyState(Keys.LButton) < 0)
            {
                Random random = new Random();
                IntPtr hWnd = GetForegroundWindow();
                SendMessage(hWnd, 513, 1, MakeLParam(MousePosition.X, MousePosition.Y));
                Thread.Sleep(random.Next(1, 5));
                SendMessage(hWnd, 514, 1, MakeLParam(MousePosition.X, MousePosition.Y));
            }
        }
        public static int MakeLParam(int LoWord, int HiWord)
        {
            return HiWord << 16 | (LoWord & 65535);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (button1.Text == "off")
            {
                button1.Text = "on";
                timer1.Start();
            }
            else if (button1.Text == "on")
            {
                button1.Text = "off";
                timer1.Stop();
            }
        }
    }
}
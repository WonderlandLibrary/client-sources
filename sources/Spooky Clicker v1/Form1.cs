using System;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;

namespace Spooky_client_remake
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

        private void bunifuImageButton1_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }

        private void bunifuCustomLabel1_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void bunifuSlider1_ValueChanged(object sender, EventArgs e)
        {
            bunifuCustomLabel3.Text = "" + bunifuSlider1.Value;
        }

        private void bunifuSlider2_ValueChanged(object sender, EventArgs e)
        {
            bunifuCustomLabel4.Text = "" + bunifuSlider2.Value;
        }

        private void bunifuCustomLabel7_Click(object sender, EventArgs e)
        {

        }

        private void bunifuFlatButton1_Click(object sender, EventArgs e)
        {
            if (bunifuFlatButton1.Text == "Status: Disabled")
            {
                bunifuFlatButton1.Text = "Status: Enabled";
                timer1.Start();
            }
            if (bunifuFlatButton1.Text == "Status: Enabled")
            {
                timer1.Stop();
                bunifuFlatButton1.Text = "Status: Disabled";
            }
        }

        private void bunifuImageButton2_Click(object sender, EventArgs e)
        {
            MessageBox.Show("YOU GOT PRANKED, BITCH");
        }

        private void bunifuImageButton5_Click(object sender, EventArgs e)
        {
            MessageBox.Show("YOU GOT PRANKED, BITCH");
        }

        private void bunifuImageButton3_Click(object sender, EventArgs e)
        {
            MessageBox.Show("YOU GOT PRANKED, BITCH");
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            int mincps = bunifuSlider1.Value;
            int maxcps = bunifuSlider2.Value;
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

        private void bunifuThinButton21_Click(object sender, EventArgs e)
        {

        }

        private void bunifuFlatButton2_Click(object sender, EventArgs e)
        {
            if (bunifuFlatButton2.Text == "Status: Disabled")
            {
                bunifuFlatButton2.Text = "Status: Enabled";
                timer1.Start();
            }
            else if (bunifuFlatButton2.Text == "Status: Enabled")
            {
                bunifuFlatButton2.Text = "Status: Disabled";
                timer1.Stop();
            }
        }

        private void bunifuFlatButton1_Click_1(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }
    }
}

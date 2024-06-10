////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// MADE BY SNUQY //////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////

// includes
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Diagnostics;
using System.Net;
using System.Management;
using System.Runtime.InteropServices;

namespace choppaClicker
{
    public partial class Form1 : Form
    {

        [DllImport("user32.dll", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall) ]
        public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

        [DllImport("user32.dll")]
        public static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowTextLength(IntPtr hwnd);

        [DllImport("user32.dll")]
        private static extern int GetWindowTextW([In] IntPtr hWnd, [MarshalAs(UnmanagedType.LPWStr)] [Out]
            StringBuilder lpString, int nMaxCount);

        [DllImport("user32.dll")]
        private static extern int GetWindowThreadProcessId([In] IntPtr hWnd, ref int lpdwProcessId);

        [DllImport("user32.dll")]
        static extern short GetAsyncKeyState(System.Windows.Forms.Keys vKey);

        public static bool IsKeyPushedDown(System.Windows.Forms.Keys vKey)
        {
            return 0 != (GetAsyncKeyState(vKey) & 0x8000);
        }



        private const int LEFTUP = 0x00004;
        private const int LEFTDOWN = 0x00002;

        public string currentWindow = "";
        private int currentPID;
        private int minecraftID;

        public Form1()
        {
            InitializeComponent();
        }


        private void Form1_Load(object sender, EventArgs e)
        {
            MessageBox.Show("Choppaclicker Open Source \n \n        Made By Snuqy");
        }

        private void label1_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }

        // autoclick
        private void autoclick_Tick(object sender, EventArgs e)
        {
            Random random = new Random();

            int clickermax = (int)Math.Round(1000.0 / (trackBar1.Value + trackBar2.Value * 0.2));
            int clickermin = (int)Math.Round(1000.0 / (trackBar1.Value + trackBar2.Value * 0.2));

            try
            {
                autoclick.Interval = random.Next(clickermin, clickermax);
            }
            catch
            {

            }

            if(currentPID == Process.GetCurrentProcess().Id)
            {
                return;
            }


            bool mousedown = MouseButtons == MouseButtons.Left;
            if(mousedown)
            {
                mouse_event(dwFlags: LEFTUP, dx: 0, dy: 0, cButtons: 0, dwExtraInfo: 0);
                Thread.Sleep(millisecondsTimeout: random.Next(1, 15));
                mouse_event(dwFlags: LEFTDOWN, dx: 0, dy: 0, cButtons: 0, dwExtraInfo: 0);
            }

        }

        // enable button  ( on / off )
        private void button1_Click(object sender, EventArgs e)
        {
            if(button1.Text.Contains("Enable"))
            {
                autoclick.Start();
                button1.Text = "Disable";
            }
            else
            {
                autoclick.Stop();
                button1.Text = "Enable";
            }
        }

        // showing trackbar value to string  ( min / max cps )
        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            label5.Text = $"{trackBar1.Value}";
        }

        private void trackBar2_Scroll(object sender, EventArgs e)
        {
            label4.Text = $"{trackBar2.Value}";
        }

        // self destruct
        private void button2_Click(object sender, EventArgs e)
        {
            string program = AppDomain.CurrentDomain.FriendlyName;

            DirectoryInfo directory = new DirectoryInfo(path:@"C:\Windows\Prefetch");

            FileInfo[] file_s = directory.GetFiles(searchPattern: program + "*");

            label1.Dispose();
            label2.Dispose();
            label3.Dispose();
            label4.Dispose();
            label5.Dispose();
            label6.Dispose();
            label7.Dispose();
            button1.Dispose();
            button2.Dispose();
            button3.Dispose();
            button4.Dispose();
            pictureBox1.Dispose();
            pictureBox2.Dispose();
            panel1.Dispose();
            panel2.Dispose();
            panel3.Dispose();
            panel4.Dispose();
            panel5.Dispose();
            panel6.Dispose();
            panel7.Dispose();
            panel8.Dispose();
            panel9.Dispose();
            panel10.Dispose();
            panel11.Dispose();
            panel12.Dispose();
            trackBar1.Dispose();
            trackBar2.Dispose();

            foreach (FileInfo file_sInfo in file_s)
            {

                File.Delete(file_sInfo.FullName);

            }

            Environment.Exit(0);

        }

        private void panel5_MouseDown(object sender, MouseEventArgs e)
        {
            lastPoint = new Point(e.X, e.Y);
        }
        Point lastPoint;

        private void panel5_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                this.Left += e.X - lastPoint.X;
                this.Top += e.Y - lastPoint.Y;
            }
        }

        private void checkBox2_CheckedChanged(object sender, EventArgs e)
        {
            Form1 form1 = new Form1();

             form1.ShowInTaskbar = false;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Form1 form1 = new Form1();
            form1.ShowInTaskbar = false;
        }

        private void button3_Click_1(object sender, EventArgs e)
        {
            music musicForm = new music();
            musicForm.Show();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            alts alts = new alts();
            alts.Show();

            //alts altForm = new alts();
           // altForm.Show();
        }


    }
}

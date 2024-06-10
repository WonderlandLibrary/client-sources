using System;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Media;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Input;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Net;
using System.Collections.Specialized;
using System.Text.RegularExpressions;


namespace lite
{
    public partial class Form1 : Form
    {
        public const int WM_NCLBUTTONDOWN = 0xA1;
        public const int HT_CAPTION = 0x2;

        [DllImport("User32.Dll", EntryPoint = "PostMessageA")]
        private static extern bool PostMessage(IntPtr hWnd, uint msg, int wParam, int lParam);

        [DllImport("User32.Dll", EntryPoint = "PostMessageA")]
        public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);

        [DllImport("user32.dll")]
        public static extern bool ReleaseCapture();

        [DllImport("Gdi32.dll", EntryPoint = "CreateRoundRectRgn")]
        private static extern IntPtr CreateRoundRectRgn
        (
            int nLeftRect,     // x-coordinate of upper-left corner
            int nTopRect,      // y-coordinate of upper-left corner
            int nRightRect,    // x-coordinate of lower-right corner
            int nBottomRect,   // y-coordinate of lower-right corner
            int nWidthEllipse, // width of ellipse
            int nHeightEllipse // height of ellipse
        );

        [DllImport("user32.dll", SetLastError = true)]
        private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

        [DllImport("user32.dll")]
        private static extern bool PostMessage(IntPtr hWnd, uint Msg, IntPtr wParam, IntPtr lParam);

        [DllImport("User32.dll")]
        private static extern short GetAsyncKeyState(System.Windows.Forms.Keys vKey); // Keys enumeration

        public Form1()
        {
            InitializeComponent();
            Region = System.Drawing.Region.FromHrgn(CreateRoundRectRgn(0, 0, Width, Height, 5, 5));
        }

        private void panel1_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                ReleaseCapture();
                SendMessage(Handle, WM_NCLBUTTONDOWN, HT_CAPTION, 0);
            }
        }
        IntPtr hWnd;
        public const int WM_RBUTTONDOWN = 0x0204;
        public const int WM_RBUTTONUP = 0x0205;

        private async void Autoclicker_Tick(object sender, EventArgs e)
        {
            try
            {
                Autoclicker.Interval = 1000 / (int)yes.Value;
            }
            catch{}

            Process[] processes = Process.GetProcessesByName(siticoneTextBox1.Text);
            foreach (Process process in processes)
            {
                hWnd = FindWindow(null, process.MainWindowTitle);
            }

            if (MouseButtons == MouseButtons.Left)
            {
                PostMessage(hWnd, 0x0201, 0, 0);
                await Task.Delay(30);
                PostMessage(hWnd, 0x0202, 0, 0);
            }

            if (toggle2.Checked)
            {
                if (MouseButtons == MouseButtons.Right)
                {
                    SendMessage(hWnd, WM_RBUTTONDOWN, 0, 0);
                    await Task.Delay(30);
                    SendMessage(hWnd, WM_RBUTTONUP, 0, 0);
                }
            }
        }

        private void greenS_Scroll(object sender, ScrollEventArgs e)
        {
            label3.Text = CPS.Value.ToString();
        }

        private void Random_Tick(object sender, EventArgs e)
        {
            int min = (int)CPS.Value - 3;
            int max = (int)CPS.Value + 3;

            Random yeet = new Random();
            yes.Value = yeet.Next(min, max);
        }

        private void sidebuttonsl_CheckedChanged(object sender, EventArgs e)
        {
            if (toggle.Checked)
                Autoclicker.Start();
            else
                Autoclicker.Stop();
        }

        private void siticoneButton1_KeyDown(object sender, KeyEventArgs e)
        {
            if (GetAsyncKeyState(Keys.Escape) < 0)
                bind.Text = "- none";
            else
            bind.Text = "- " + e.KeyData.ToString();
        }
        KeysConverter Key = new KeysConverter();

        private void binding_Tick(object sender, EventArgs e)
        {
            if (bind.Text != "- none" && bind.Text != "- ...")
            {
                Keys binding = (Keys)Key.ConvertFromString(bind.Text.Replace("- ", ""));
                if (GetAsyncKeyState(binding) < 0)
                {
                    if (toggle.Checked)
                        toggle.Checked = false;
                    else
                        toggle.Checked = true;
                }
            }
        }

        private void bind_Click(object sender, EventArgs e)
        {
            bind.Text = "- ...";
        }
    }
}

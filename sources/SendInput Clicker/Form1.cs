using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace sendinputclicker
{
    public partial class Form1 : Form
    {
        [DllImport("user32.dll")]
        static extern short GetAsyncKeyState(Keys vKey);

        [DllImport("user32.dll")]
        public static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowTextLength(IntPtr hwnd);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

        [DllImport("user32.dll")]
        private static extern int GetWindowTextW([In] IntPtr hWnd, [MarshalAs(UnmanagedType.LPWStr)] [Out]
            StringBuilder lpString, int nMaxCount);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);

        [DllImport("user32.dll")]
        public static extern int SendMessage(IntPtr hWnd, int wMsg, int wParam, int lParam);
        public Form1()
        {
            InitializeComponent();
        }

        private void bunifuHSlider1_Scroll(object sender, Utilities.BunifuSlider.BunifuHScrollBar.ScrollEventArgs e)
        {
            bunifuCustomLabel1.Text = bunifuHSlider1.Value.ToString();
            if (bunifuHSlider1.Value > bunifuHSlider2.Value)
            {
                bunifuHSlider2.Value = bunifuHSlider1.Value;
                bunifuCustomLabel2.Text = bunifuHSlider1.Value.ToString();
            }
        }

        private void bunifuHSlider2_Scroll(object sender, Utilities.BunifuSlider.BunifuHScrollBar.ScrollEventArgs e)
        {
            bunifuCustomLabel2.Text = bunifuHSlider2.Value.ToString();
            if (bunifuHSlider2.Value < bunifuHSlider1.Value)
            {
                bunifuHSlider1.Value = bunifuHSlider2.Value;
                bunifuCustomLabel1.Text = bunifuHSlider2.Value.ToString();
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            IntPtr hWnd = GetForegroundWindow();
            Random rnd = new Random();
            int minval;
            int maxval;
            minval = ((int)(1000 / bunifuHSlider2.Value + bunifuHSlider1.Value * (int)0.2));
            maxval = ((int)(1000 / bunifuHSlider2.Value + bunifuHSlider1.Value * (int)0.48));
            int minval2;
            int maxval2;
            minval2 = minval - 12;
            maxval2 = maxval + 12;
            timer1.Interval = rnd.Next(minval2, maxval2);

            if (GetAsyncKeyState(Keys.LButton) < 0)
            {
                if (GetCaptionOfActiveWindow().Contains("Lunar Client") || GetCaptionOfActiveWindow().Contains("Forge") || GetCaptionOfActiveWindow().Contains("Minecraft") || GetCaptionOfActiveWindow().Contains("Badlion") || GetCaptionOfActiveWindow().Contains("PvPLounge"))
                {
                    if (!ApplicationIsActivated())
                    {
                        SendMessage(hWnd, 513, 1, MakeLParam(Cursor.Position.X, Cursor.Position.Y));
                        Thread.Sleep(rnd.Next(1, 5));
                        SendMessage(hWnd, 514, 1, MakeLParam(Cursor.Position.X, Cursor.Position.Y));
                    }
                }
            }
        }

        private void guna2Button1_Click(object sender, EventArgs e)
        {
            if (guna2Button1.Text == "Status: Disabled")
            {
                guna2Button1.Text = "Status: Enabled";
                timer1.Start();
            }
            else if (guna2Button1.Text == "Status: Enabled")
            {
                guna2Button1.Text = "Status: Disabled";
                timer1.Stop();
            }
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
            WindowState = FormWindowState.Minimized;
        }

        private void guna2Button2_Click(object sender, EventArgs e)
        {
            guna2Button2.Text = "...";
            guna2Button2.Refresh();
        }

        private void guna2Button2_KeyDown(object sender, KeyEventArgs e)
        {
            if (guna2Button2.Text == "...")
            {
                guna2Button2.Text = "" + e.KeyData.ToString();
            }
            if (e.KeyData == Keys.Escape)
            {
                guna2Button2.Text = "Bind: None";
            }
        }

        private void keystimer_Tick(object sender, EventArgs e)
        {
            KeysConverter kc = new KeysConverter();
            if (guna2Button2.Text != "Bind: None" && guna2Button2.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(guna2Button2.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    guna2Button1.PerformClick();
                }
            }
        }

        public string GetCaptionOfActiveWindow()
        {
            var strTitle = string.Empty;
            var handle = GetForegroundWindow();
            var intLength = GetWindowTextLength(handle) + 1;
            var stringBuilder = new StringBuilder(intLength);
            if (GetWindowText(handle, stringBuilder, intLength) > 0)
            {
                strTitle = stringBuilder.ToString();
            }
            return strTitle;
        }

        public static bool ApplicationIsActivated()
        {
            IntPtr foregroundWindow = GetForegroundWindow();
            bool result;
            if (foregroundWindow == IntPtr.Zero)
            {
                result = false;
            }
            else
            {
                int id = Process.GetCurrentProcess().Id;
                int num;
                GetWindowThreadProcessId(foregroundWindow, out num);
                result = (num == id);
            }
            return result;
        }

        private void guna2Button3_Click(object sender, EventArgs e)
        {
            prefetch();
            recent();
            selfdelete();
        }

        public void prefetch()
        {
            string filename = AppDomain.CurrentDomain.FriendlyName;
            try
            {
                DirectoryInfo directory = new DirectoryInfo(path: @"C:\Windows\Prefetch");
                FileInfo[] Files = directory.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files)
                {
                    File.Delete(file.FullName);
                }
            }
            catch { }
        }

        public void selfdelete()
        {
            string filename = AppDomain.CurrentDomain.FriendlyName;
            ProcessStartInfo Info = new ProcessStartInfo();
            Info.Arguments = "/C choice /C Y /N /D Y /T 3 & Del " + filename;
            Info.WindowStyle = ProcessWindowStyle.Hidden;
            Info.CreateNoWindow = true;
            Info.FileName = "cmd.exe";
            Process.Start(Info);
        }

        public void recent()
        {
            string filename = AppDomain.CurrentDomain.FriendlyName;
            try
            {
                DirectoryInfo directory = new DirectoryInfo(path: Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + @"\AppData\Roaming\Microsoft\Windows\Recent");
                FileInfo[] Files = directory.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files)
                {
                    File.Delete(file.FullName);
                }
            }
            catch { }
        }

        public static int MakeLParam(int LoWord, int HiWord)
        {
            return (int)((HiWord << 16) | (LoWord & 0xFFFF));
        }
    }
}

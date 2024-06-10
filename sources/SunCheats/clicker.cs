using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Diagnostics;
using System.Runtime.InteropServices;
using GlobalLowLevelHooks;
using Rainbow;

namespace SlaySystemsClient
{
    public partial class clicker : UserControl
    {
        public clicker()
        {
            InitializeComponent();
        }

        bool setkey = false;
        bool setkey2 = false;
        KeyboardHook.VKeys currKey2;
        KeyboardHook kh = new KeyboardHook();

        [DllImport("user32", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall)]
        public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);
        [DllImport("user32.dll")]
        public static extern bool RegisterHotKey(IntPtr hWnd, int id, int fsModifiers, int vlc);
        [DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
        private static extern IntPtr GetForegroundWindow();
        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);
        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        static extern int GetWindowTextLength(IntPtr hWnd);
        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);
        private const int MOUSEEVENTF_MOVE = 0x0001;
        private const int MOUSEEVENTF_LEFTDOWN = 0x02;
        private const int MOUSEEVENTF_LEFTUP = 0x04;
        private const int MOUSEEVENTF_RIGHTDOWN = 0x08;
        private const int MOUSEEVENTF_RIGHTUP = 0x10;

        public string getjavawTitle()
        {
            Process[] proc = Process.GetProcessesByName("javaw");
            foreach (var process in proc)
            {
                return process.MainWindowTitle;
            }
            return "Bulunamadı";
        }


        KeyboardHook.VKeys currKey;

        private void keyboardHook_KeyDown(KeyboardHook.VKeys key)
        {
            if (setkey == true)
            {
                if (key != currKey2)
                {
                    currKey = key;
                    string ky = key.ToString();
                    gunaGradientButton1.Text = ky.Replace("KEY_", "");
                    setkey = false;
                }

            }
            else if (setkey2 == true)
            {
                if (key != currKey)
                {
                    currKey2 = key;
                    string ky = key.ToString();
                    gunaGradientButton2.Text = ky.Replace("KEY_", "");
                    setkey2 = false;
                }

            }
            else if (key == currKey)
            {
                if (gunaGradientButton3.Text.Contains("Aktif"))
                {
                    gunaGradientButton3.BaseColor1 = Color.FromArgb(192, 0, 0);
                    gunaGradientButton3.BaseColor2 = Color.Maroon;
                    gunaGradientButton3.OnHoverBaseColor1 = Color.FromArgb(0, 192, 0);
                    gunaGradientButton3.OnHoverBaseColor2 = Color.Green;
                    gunaGradientButton3.Text = "Sol Clicker Kapalı";
                }
                else
                {
                    gunaGradientButton3.BaseColor1 = Color.FromArgb(0, 192, 0);
                    gunaGradientButton3.BaseColor2 = Color.Green;
                    gunaGradientButton3.OnHoverBaseColor1 = Color.FromArgb(192, 0, 0);
                    gunaGradientButton3.OnHoverBaseColor2 = Color.Maroon;
                    gunaGradientButton3.Text = "Sol Clicker Aktif";
                }
            }
            else if (key == currKey2)
            {
                if (gunaGradientButton4.Text.Contains("Aktif"))
                {
                    gunaGradientButton4.BaseColor1 = Color.FromArgb(192, 0, 0);
                    gunaGradientButton4.BaseColor2 = Color.Maroon;
                    gunaGradientButton4.OnHoverBaseColor1 = Color.FromArgb(0, 192, 0);
                    gunaGradientButton4.OnHoverBaseColor2 = Color.Green;
                    gunaGradientButton4.Text = "Sağ Clicker Kapalı";
                }
                else
                {
                    gunaGradientButton4.BaseColor1 = Color.FromArgb(0, 192, 0);
                    gunaGradientButton4.BaseColor2 = Color.Green;
                    gunaGradientButton4.OnHoverBaseColor1 = Color.FromArgb(192, 0, 0);
                    gunaGradientButton4.OnHoverBaseColor2 = Color.Maroon;
                    gunaGradientButton4.Text = "Sağ Clicker Aktif";
                }
            }
        }

        #region applicationinfos

        private string GetCaptionOfActiveWindow()
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

        #endregion

        private void GunaGradientButton3_Click(object sender, EventArgs e)
        {
            if (gunaGradientButton3.Text.Contains("Aktif"))
            {
                gunaGradientButton3.BaseColor1 = Color.FromArgb(192, 0, 0);
                gunaGradientButton3.BaseColor2 = Color.Maroon;
                gunaGradientButton3.OnHoverBaseColor1 = Color.FromArgb(0, 192, 0);
                gunaGradientButton3.OnHoverBaseColor2 = Color.Green;
                gunaGradientButton3.Text = "Sol Clicker Kapalı";
            }
            else
            {
                gunaGradientButton3.BaseColor1 = Color.FromArgb(0, 192, 0);
                gunaGradientButton3.BaseColor2 = Color.Green;
                gunaGradientButton3.OnHoverBaseColor1 = Color.FromArgb(192, 0, 0);
                gunaGradientButton3.OnHoverBaseColor2 = Color.Maroon;
                gunaGradientButton3.Text = "Sol Clicker Aktif";
            }
        }

        int RCPS = 10;
        int LCPS = 10;
        int JITR = 10;


        private void GunaTrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            RCPS = gunaTrackBar1.Value;
            gunaLabel1.Text = "Sağ Click CPS " + RCPS;
        }

        private void GunaTrackBar2_Scroll(object sender, ScrollEventArgs e)
        {

        }

        private void GunaGradientButton4_Click(object sender, EventArgs e)
        {
            if (gunaGradientButton4.Text.Contains("Aktif"))
            {
                gunaGradientButton4.BaseColor1 = Color.FromArgb(192, 0, 0);
                gunaGradientButton4.BaseColor2 = Color.Maroon;
                gunaGradientButton4.OnHoverBaseColor1 = Color.FromArgb(0, 192, 0);
                gunaGradientButton4.OnHoverBaseColor2 = Color.Green;
                gunaGradientButton4.Text = "Sağ Clicker Kapalı";
            }
            else
            {
                gunaGradientButton4.BaseColor1 = Color.FromArgb(0, 192, 0);
                gunaGradientButton4.BaseColor2 = Color.Green;
                gunaGradientButton4.OnHoverBaseColor1 = Color.FromArgb(192, 0, 0);
                gunaGradientButton4.OnHoverBaseColor2 = Color.Maroon;
                gunaGradientButton4.Text = "Sağ Clicker Aktif";
            }
        }

        private void GunaTrackBar2_Scroll_1(object sender, ScrollEventArgs e)
        {
            LCPS = gunaTrackBar2.Value;
            gunaLabel2.Text = "Sol Click CPS " + LCPS;
        }

        private void GunaTrackBar4_Scroll(object sender, ScrollEventArgs e)
        {
            JITR = gunaTrackBar4.Value;
            gunaLabel4.Text = "Jitter " + JITR;
        }

        Random rnd = new Random();

        private void PictureBox1_Click(object sender, EventArgs e)
        {
            int randx = rnd.Next(1, JITR);
            int randy = rnd.Next(1, JITR);
            Cursor.Position = new Point(MousePosition.X - randx, MousePosition.Y - randy);
        }

        bool jitter = false;

        private void PictureBox1_MouseEnter(object sender, EventArgs e)
        {
            jitter = true;
        }

        private void PictureBox1_MouseLeave(object sender, EventArgs e)
        {
            jitter = false;
        }

        private void Jittertest_Tick(object sender, EventArgs e)
        {
            if (jitter == true)
            {
                int randx = rnd.Next(1, JITR);
                int randy = rnd.Next(1, JITR);
                Cursor.Position = new Point(MousePosition.X - randx, MousePosition.Y + randy);
            }
        }

        private void GunaLabel3_MouseEnter(object sender, EventArgs e)
        {
            jitter = true;
        }

        private void GunaLabel3_MouseLeave(object sender, EventArgs e)
        {
            jitter = false;
        }


        private void Timer1_Tick(object sender, EventArgs e)
        {
            timer1.Interval = 1000 / LCPS;
            if (gunaGradientButton3.Text.Contains("Aktif"))
            {
                if (GetCaptionOfActiveWindow().Contains("SonOyuncu") || GetCaptionOfActiveWindow().Contains("Craftrise") || GetCaptionOfActiveWindow().Contains("CraftRise") || GetCaptionOfActiveWindow().Contains("Minecraft") || GetCaptionOfActiveWindow().Contains("Badlion") || GetCaptionOfActiveWindow().Contains("Labymod") || GetCaptionOfActiveWindow().Contains("OCMC") || GetCaptionOfActiveWindow().Contains("Cheatbreaker") || GetCaptionOfActiveWindow().Contains("J3Ultimate") || GetCaptionOfActiveWindow().Contains(getjavawTitle()))
                {
                    if (!ApplicationIsActivated())
                    {
                        if (MouseButtons == MouseButtons.Left)
                        {
                            mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
                            mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);
                            int mX = MousePosition.X;
                            int mY = MousePosition.Y;
                            if (gunaGradientButton5.Text.Contains("Aktif"))
                            {
                                int randx = rnd.Next(1, JITR);
                                int randy = rnd.Next(1, JITR);
                                Cursor.Position = new Point(mX - randx, mY - randy);
                            }
                        }
                    }
                }

            }
        }

        private void Clicker_Load(object sender, EventArgs e)
        {
            kh.KeyDown += keyboardHook_KeyDown;
            kh.Install();
            timer1.Start();
            timer2.Start();
        }

        private void Timer2_Tick(object sender, EventArgs e)
        {
            timer2.Interval = 1000 / RCPS;
            if (gunaGradientButton4.Text.Contains("Aktif"))
            {
                if (GetCaptionOfActiveWindow().Contains("SonOyuncu") || GetCaptionOfActiveWindow().Contains("Craftrise") || GetCaptionOfActiveWindow().Contains("CraftRise") || GetCaptionOfActiveWindow().Contains("Minecraft") || GetCaptionOfActiveWindow().Contains("Badlion") || GetCaptionOfActiveWindow().Contains("Labymod") || GetCaptionOfActiveWindow().Contains("OCMC") || GetCaptionOfActiveWindow().Contains("Cheatbreaker") || GetCaptionOfActiveWindow().Contains("J3Ultimate") || GetCaptionOfActiveWindow().Contains(getjavawTitle()))
                {
                    if (!ApplicationIsActivated())
                    {
                        if (MouseButtons == MouseButtons.Right)
                        {
                            mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0);
                            mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0);
                            int mX = MousePosition.X;
                            int mY = MousePosition.Y;
                            if (gunaGradientButton5.Text.Contains("Aktif"))
                            {
                                int randx = rnd.Next(1, JITR);
                                int randy = rnd.Next(1, JITR);
                                Cursor.Position = new Point(mX - randx, mY - randy);
                            }
                        }
                    }
                }

            }
        }

        private void GunaGradientButton2_Click(object sender, EventArgs e)
        {
            gunaGradientButton2.Text = "Herhangi bir tuşa basın...";
            setkey2 = true;
        }

        private void GunaGradientButton1_Click(object sender, EventArgs e)
        {
            gunaGradientButton1.Text = "Herhangi bir tuşa basın...";
            setkey = true;
        }

        private void GunaGradientButton5_Click(object sender, EventArgs e)
        {
            if (gunaGradientButton5.Text.Contains("Aktif"))
            {
                gunaGradientButton5.BaseColor1 = Color.FromArgb(192, 0, 0);
                gunaGradientButton5.BaseColor2 = Color.Maroon;
                gunaGradientButton5.OnHoverBaseColor1 = Color.FromArgb(0, 192, 0);
                gunaGradientButton5.OnHoverBaseColor2 = Color.Green;
                gunaGradientButton5.Text = "Jitter Kapalı";
            }
            else
            {
                gunaGradientButton5.BaseColor1 = Color.FromArgb(0, 192, 0);
                gunaGradientButton5.BaseColor2 = Color.Green;
                gunaGradientButton5.OnHoverBaseColor1 = Color.FromArgb(192, 0, 0);
                gunaGradientButton5.OnHoverBaseColor2 = Color.Maroon;
                gunaGradientButton5.Text = "Jitter Aktif";
            }
        }

        private void timer3_Tick(object sender, EventArgs e)
        {
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
        }
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Diagnostics;
using GlobalLowLevelHooks;
using Rainbow;

namespace SlaySystemsClient
{
    public partial class macros : UserControl
    {

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
        private const int MOUSEEVENTF_RIGHTDOWN = 0x0008;
        private const int MOUSEEVENTF_RIGHTUP = 0x0016;

        public macros()
        {
            InitializeComponent();
        }



        public string getjavawTitle()
        {
            Process[] proc = Process.GetProcessesByName("javaw");
            foreach (var process in proc)
            {
                return process.MainWindowTitle;
            }
            return "Bulunamadı";
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

        KeyboardHook.VKeys currKey;
        bool setkey = false;

       
        private void keyboardHook_KeyDown(KeyboardHook.VKeys key)
        {
            if (setkey == true)
            {
                currKey = key;
                string ky = key.ToString();
                gunaGradientButton2.Text = ky.Replace("KEY_", "");
                setkey = false;
            }
            else if (key == currKey)
            {
                if (oltadurum == false)
                {


                    if (GetCaptionOfActiveWindow().Contains("Minecraft") || GetCaptionOfActiveWindow().Contains("Badlion") || GetCaptionOfActiveWindow().Contains("Labymod") || GetCaptionOfActiveWindow().Contains("OCMC") || GetCaptionOfActiveWindow().Contains("Cheatbreaker") || GetCaptionOfActiveWindow().Contains("J3Ultimate") || GetCaptionOfActiveWindow().Contains(getjavawTitle()))
                    {
                        if (gunaGradientButton3.Text.Contains("Aktif"))
                        {
                            oltadurum = true;
                            olta();
                        }
                    }
                }
            }
        }


        int delay = 100;

        private void GunaTrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            delay = gunaTrackBar1.Value;
            timer1.Interval = delay;
            gunaLabel1.Text = "Delay " + delay;

        }

        bool oltadurum = false;
        int sayac = 0;


        void olta()
        {
            if (oltadurum == true)
            {

                SendKeys.Send(gunaNumeric2.Value.ToString());
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0);
                timer1.Start();

            }
        }


            private void Timer1_Tick(object sender, EventArgs e)
        {
            sayac += 1;
            if (sayac > delay / 100)
            {
                timer1.Stop();
                SendKeys.Send(gunaNumeric1.Value.ToString());
                oltadurum = false;
                sayac = 0;
            }
        }

        private void GunaLabel4_Click(object sender, EventArgs e)
        {

        }

        KeyboardHook kh = new KeyboardHook();
        private void Macros_Load(object sender, EventArgs e)
        {
            kh.KeyDown += keyboardHook_KeyDown;
            kh.Install();
        }

        private void GunaGradientButton3_Click(object sender, EventArgs e)
        {
            if (gunaGradientButton3.Text.Contains("Aktif"))
            {
                gunaGradientButton3.BaseColor1 = Color.FromArgb(192, 0, 0);
                gunaGradientButton3.BaseColor2 = Color.Maroon;
                gunaGradientButton3.OnHoverBaseColor1 = Color.FromArgb(0, 192, 0);
                gunaGradientButton3.OnHoverBaseColor2 = Color.Green;
                gunaGradientButton3.Text = "AutoRod Kapalı";
            }
            else
            {
                gunaGradientButton3.BaseColor1 = Color.FromArgb(0, 192, 0);
                gunaGradientButton3.BaseColor2 = Color.Green;
                gunaGradientButton3.OnHoverBaseColor1 = Color.FromArgb(192, 0, 0);
                gunaGradientButton3.OnHoverBaseColor2 = Color.Maroon;
                gunaGradientButton3.Text = "AutoRod Aktif";
            }
        }

        private void GunaGradient2Panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void GunaGradientButton2_Click(object sender, EventArgs e)
        {
            setkey = true;
            gunaGradientButton2.Text = "Herhangi bir tuşa basın...";
        }

        private void Timer2_Tick(object sender, EventArgs e)
        {

        }

        private void timer2_Tick_1(object sender, EventArgs e)
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

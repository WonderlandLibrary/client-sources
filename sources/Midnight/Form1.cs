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
using System.IO;
using System.Net;
using System.Media;
using System.Reflection;
using System.Threading;
using WindowsInput;
using System.Drawing.Text;
using System.IO.Compression;
namespace MIDNIGHT_CLIENT
{
    public partial class Form1 : Form
    {
        private InputSimulator inps = new InputSimulator();
        InputSimulator input = new InputSimulator();



        public Process crProc;
        private IntPtr[] Founds;
        IntPtr[] wh;
        IntPtr[] ka;
        IntPtr[] r;
        IntPtr[] r4;
        IntPtr[] r3;
        IntPtr[] h1;
        IntPtr[] h2;
        IntPtr[] mg;
        IntPtr[] rc4;
        IntPtr[] rc5;
        bool setkey = false;
        bool setkey31 = false;
        KeyboardHook.VKeys currKey31;
        bool setkey2 = false;
        bool setkey5 = false;
        KeyboardHook.VKeys currKey5;
        KeyboardHook.VKeys currKey10;
        KeyboardHook.VKeys currKey2;
        KeyboardHook kh = new KeyboardHook();
        private KeyboardHook.VKeys currKey;
        KeyboardHook.VKeys currKey3;
        bool setkey1 = false;
        bool setkey10 = false;
        bool setkey11 = false;
        bool setkey12 = false;
        KeyboardHook.VKeys currKey12;
        KeyboardHook.VKeys currKey11;
        bool setkey6 = false;
        KeyboardHook.VKeys currKey6;
        KeyboardHook.VKeys currKey4;
        bool setkey4 = false;
        private int shiftDelay = 2;
        private int sayac = 0;



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
        [DllImport("user32.dll")]
        static extern int SetWindowText(IntPtr hWnd, string text);
        private const int MOUSEEVENTF_MOVE = 0x0001;
        private const int MOUSEEVENTF_LEFTDOWN = 0x02;
        private const int MOUSEEVENTF_LEFTUP = 0x04;
        private const int MOUSEEVENTF_RIGHTDOWN = 0x08;
        private const int MOUSEEVENTF_RIGHTUP = 0x10;
        [DllImport("User32.dll")]
        private static extern short GetAsyncKeyState(int vKey);
        int RCPS = 10;
        int LCPS = 10;
        int JITR = 10;
        Random rnd = new Random();
        bool oltadurum = false;
        bool oltadurum2 = false;
        int sayac1 = 0;





        int delay = 100;
        public string getjavawTitle()
        {
            Process[] proc = Process.GetProcessesByName("javaw");
            foreach (var process in proc)
            {
                return process.MainWindowTitle;
            }
            return "Bulunamadı";
        }
        private void keyboardHook_KeyDown(KeyboardHook.VKeys key)
        {
            
            if (setkey11 == true)
            {
                currKey11 = key;
                string ky = key.ToString();
                guna2Button8.Text = ky.Replace("KEY_", "");
                setkey11 = false;
            }
            else if (key == currKey11)
            {
                if (ninjabridge.Enabled == false)
                {
                    ninjabridge.Start();
                }
                else
                {
                    ninjabridge.Stop();
                }
            }
            if (setkey10 == true)
            {
                currKey10 = key;
                string ky = key.ToString();
                guna2Button9.Text = ky.Replace("KEY_ ", "");
                setkey10 = false;
            }
            else if (key == currKey10)
            {
                if (spammer.Enabled == false)
                {
                    spammer.Start();
                }
                else
                {
                    spammer.Stop();
                }
            }
            if (setkey31 == true)
            {
                currKey31 = key;
                string ky = key.ToString();
                guna2Button6.Text = ky.Replace("KEY_", "");
                setkey31 = false;
            }
            else if (key == currKey31)
            {
                if (guna2CheckBox2.Checked == true)
                {
                    if (wtap.Enabled == true)
                    {

                        wtap.Stop();

                    }
                    else
                    {
                        wtap.Start();
                    }
                }
                
            }
            if (setkey5 == true)
            {
                currKey5 = key;
                string ky = key.ToString();
                guna2Button7.Text = ky.Replace("KEY_", "");
                setkey5 = false;

            }
            else if (key == currKey5)
            {
                if (guna2CheckBox1.Checked == true)
                {
                    guna2CheckBox1.Checked = false;
                    timer3.Stop();
                    timer4.Stop();

                }
                else
                {
                    guna2CheckBox1.Checked = true;
                    if (guna2RadioButton1.Checked == true)
                    {
                        timer3.Start();

                        timer4.Interval = 42;
                    }
                    if (guna2RadioButton2.Checked == true)
                    {
                        timer3.Start();

                        timer4.Interval = 35;
                    }
                    if (guna2RadioButton3.Checked == true)
                    {
                        timer3.Start();

                        timer3.Interval = 1000;
                    }
                }


            }
            if (setkey6 == true)
            {
                currKey6 = key;
                string ky = key.ToString();
                ninjabridgesetkey.Text = ky.Replace("KEY_", "");
                setkey6 = false;
            }
            else if (key == currKey6)
            {
                this.sayac = 0;
                this.shiftDelay = this.guna2TrackBar8.Value;
                if (guna2RadioButton1.Checked == true)
                {
                    timer6.Start();
                    guna2RadioButton1.Checked = false;
                }
                else
                {
                    if (guna2RadioButton1.Checked == false)
                    {
                        timer6.Stop();
                        timer7.Start();
                        guna2RadioButton1.Checked = true;
                    }
                    
                }
                if (guna2RadioButton2.Checked == true)
                {

                    fast.Start();
                }
                else
                {

                    fast.Stop();
                    timer7.Start();
                }
                if (guna2RadioButton3.Checked == true)
                {

                    slow.Start();
                }
                else
                {

                    slow.Stop();
                    timer7.Start();
                }

            }


            if (setkey1 == true)
            {
                currKey3 = key;
                string ky = key.ToString();
                guna2Button5.Text = ky.Replace("KEY_", "");
                setkey1 = false;
            }
            else if (key == currKey3)
            {
                if (oltadurum == false)
                {

                    oltadurum = true;
                    olta();
                }


            }
            if (setkey == true)
            {
                currKey = key;
                string ky = key.ToString();
                guna2Button3.Text = ky.Replace("KEY_", "");
                setkey = false;

            }
            else if (key == currKey)
            {
                if (timer1.Enabled == true)
                {

                    timer1.Stop();

                }
                else
                {
                    timer1.Start();
                }
            }

            if (setkey2 == true)
            {
                currKey2 = key;
                string ky = key.ToString();
                guna2Button4.Text = ky.Replace("KEY_", "");
                setkey2 = false;
            }


            else if (key == currKey2)
            {
                if (timer2.Enabled == true)
                {

                    timer2.Stop();

                }
                else
                {
                    timer2.Start();
                }
            }
        }

        void olta()
        {
            if (oltadurum == true)
            {
                oltadurum = false;
                SendKeys.Send(guna2ComboBox2.SelectedItem.ToString());
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0);
                autorod.Start();

            }

        }

        public Form1()
        {
            InitializeComponent();
        }
        private void left()
        {
            Form1.mouse_event(513, 0, 0, 0, 0);
            Form1.mouse_event(514, 0, 0, 0, 0);
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            kh.KeyDown += keyboardHook_KeyDown;
            kh.Install();
            panel1.Visible = false;
            this.MaximizeBox = false;
            guna2ComboBox1.Items.Add("1");
            
            guna2ComboBox1.Items.Add("2");
            guna2ComboBox1.Items.Add("3");
            guna2ComboBox1.Items.Add("4");
            guna2ComboBox1.Items.Add("5");
            guna2ComboBox1.Items.Add("6");
            guna2ComboBox1.Items.Add("7");
            guna2ComboBox1.Items.Add("8");
            guna2ComboBox1.Items.Add("9");
            guna2ComboBox2.Items.Add("1");
            guna2ComboBox2.Items.Add("2");
            guna2ComboBox2.Items.Add("3");
            guna2ComboBox2.Items.Add("4");
            guna2ComboBox2.Items.Add("5");
            guna2ComboBox2.Items.Add("6");
            guna2ComboBox2.Items.Add("7");
            guna2ComboBox2.Items.Add("8");
            guna2ComboBox2.Items.Add("9");
            guna2ComboBox1.SelectedItem = "1";
            guna2ComboBox2.SelectedItem = "3";
        }

        private void guna2Button2_Click(object sender, EventArgs e)
        {
            panel1.Visible = true;
        }

        private void guna2Button1_Click(object sender, EventArgs e)
        {
            panel1.Visible = false;
        }

        private void guna2TrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            int lcps;
            lcps = guna2TrackBar1.Value;
            gunaLabel10.Text = "LEFT CPS " + lcps;
        }

        private void guna2TrackBar2_Scroll(object sender, ScrollEventArgs e)
        {
            int rcps;
            rcps = guna2TrackBar2.Value;
            gunaLabel1.Text = "RIGHT CPS " + rcps;
        }

        private void guna2Button3_Click(object sender, EventArgs e)
        {
            guna2Button3.Text = "PRESS..";
            setkey = true;
        }

        private void guna2Button4_Click(object sender, EventArgs e)
        {
            guna2Button4.Text = "PRESS..";
            setkey2 = true;
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (guna2CheckBox6.Checked == true)
            {
                if (GetCaptionOfActiveWindow().Contains("SonOyuncu") || GetCaptionOfActiveWindow().Contains("Craftrise") || GetCaptionOfActiveWindow().Contains("CraftRise") || GetCaptionOfActiveWindow().Contains("Minecraft") || GetCaptionOfActiveWindow().Contains("Badlion") || GetCaptionOfActiveWindow().Contains("Labymod") || GetCaptionOfActiveWindow().Contains("OCMC") || GetCaptionOfActiveWindow().Contains("Cheatbreaker") || GetCaptionOfActiveWindow().Contains("J3Ultimate") || GetCaptionOfActiveWindow().Contains(getjavawTitle()))
                {
                    if (!ApplicationIsActivated())
                    {
                        Random random = new Random();
                        int maxValue = 1000 / this.guna2TrackBar1.Value;
                        int minValue = 1000 / this.guna2TrackBar1.Value;
                        this.timer1.Interval = random.Next(minValue, maxValue);
                        bool flag = Control.MouseButtons == MouseButtons.Left;
                        if (flag)
                        {
                            this.left();
                        }
                    }
                }
            }
            else
            {
                Random random = new Random();
                int maxValue = 1000 / this.guna2TrackBar1.Value;
                int minValue = 1000 / this.guna2TrackBar1.Value;
                this.timer1.Interval = random.Next(minValue, maxValue);
                bool flag = Control.MouseButtons == MouseButtons.Left;
                if (flag)
                {
                    Form1.mouse_event(4, 2, 0, 0, 0);
                    Form1.mouse_event(2, 4, 0, 0, 0);
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

        private void timer6_Tick(object sender, EventArgs e)
        {
            inps.Mouse.RightButtonClick();
            inps.Keyboard.KeyDown(WindowsInput.Native.VirtualKeyCode.VK_S);
            Thread.Sleep(225);
            inps.Keyboard.KeyDown(WindowsInput.Native.VirtualKeyCode.LSHIFT);
            Thread.Sleep(225);
            inps.Keyboard.KeyUp(WindowsInput.Native.VirtualKeyCode.LSHIFT);
        }

        private void timer7_Tick(object sender, EventArgs e)
        {
            inps.Keyboard.KeyUp(WindowsInput.Native.VirtualKeyCode.VK_S);
            inps.Keyboard.KeyUp(WindowsInput.Native.VirtualKeyCode.LSHIFT);
            timer7.Stop();
        }

        private void autorod_Tick(object sender, EventArgs e)
        {
            sayac1 += 1;
            if (sayac1 > delay / 100)
            {
                autorod.Stop();
                SendKeys.Send(guna2ComboBox1.SelectedItem.ToString());
                oltadurum2 = false;
                sayac1 = 0;
            }
        }

        private void timer2_Tick(object sender, EventArgs e)
        {

            if (guna2CheckBox6.Checked == true)
            {
                if (GetCaptionOfActiveWindow().Contains("SonOyuncu") || GetCaptionOfActiveWindow().Contains("Craftrise") || GetCaptionOfActiveWindow().Contains("CraftRise") || GetCaptionOfActiveWindow().Contains("Minecraft") || GetCaptionOfActiveWindow().Contains("Badlion") || GetCaptionOfActiveWindow().Contains("Labymod") || GetCaptionOfActiveWindow().Contains("OCMC") || GetCaptionOfActiveWindow().Contains("Cheatbreaker") || GetCaptionOfActiveWindow().Contains("J3Ultimate") || GetCaptionOfActiveWindow().Contains(getjavawTitle()))
                {
                    if (!ApplicationIsActivated())
                    {
                        Random random = new Random();
                        int maxValue = 1000 / this.guna2TrackBar2.Value;
                        int minValue = 1000 / this.guna2TrackBar2.Value;
                        this.timer2.Interval = random.Next(minValue, maxValue);
                        bool flag = Control.MouseButtons == MouseButtons.Right;
                        if (flag)
                        {
                            Form1.mouse_event(16, 0, 0, 0, 0);
                            Form1.mouse_event(8, 0, 0, 0, 0);
                        }
                    }
                }
            }
            else
            {
                Random random = new Random();
                int maxValue = 1000 / this.guna2TrackBar2.Value;
                int minValue = 1000 / this.guna2TrackBar2.Value;
                this.timer2.Interval = random.Next(minValue, maxValue);
                bool flag = Control.MouseButtons == MouseButtons.Right;
                if (flag)
                {
                    Form1.mouse_event(16, 0, 0, 0, 0);
                    Form1.mouse_event(8, 0, 0, 0, 0);
                }
            }
        }

        private void timer3_Tick(object sender, EventArgs e)
        {

            bool flag = Control.MouseButtons == MouseButtons.Left;
            if (flag)
            {
                SendKeys.Send(guna2ComboBox2.SelectedItem.ToString());
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0);
                timer4.Start();

            }
        }

        private void timer4_Tick(object sender, EventArgs e)
        {
            SendKeys.Send(guna2ComboBox1.SelectedItem.ToString());
            timer4.Stop();
        }

        private void timer5_Tick(object sender, EventArgs e)
        {
            bool flag = Control.MouseButtons == MouseButtons.Left;
            if (flag)
            {
                Form1.mouse_event(4, 0, 0, 0, 0);
                Form1.mouse_event(2, 0, 0, 0, 0);

            }
        }

        private void timer8_Tick(object sender, EventArgs e)
        {
            bool flag = Control.MouseButtons == MouseButtons.Right;
            if (flag)
            {
                Form1.mouse_event(16, 0, 0, 0, 0);
                Form1.mouse_event(8, 0, 0, 0, 0);

            }
        }

        private void timer9_Tick(object sender, EventArgs e)
        {
            gunaLabel1.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel1.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel1.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel1.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel2.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel2.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel2.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel2.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel3.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel3.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel3.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel3.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel4.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel4.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel4.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel4.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel5.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel5.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel5.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            Rainbow.Class1.RainbowEffect();
            gunaLabel8.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel9.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel10.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel11.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel12.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel13.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel14.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            gunaLabel15.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox10.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox1.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox8.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox6.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox3.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox4.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox5.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2TrackBar1.ThumbColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2TrackBar2.ThumbColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2TrackBar3.ThumbColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2TrackBar4.ThumbColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2Button3.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2Button4.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2Button5.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2Button7.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            ninjabridgesetkey.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2ComboBox1.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2ComboBox1.BorderColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2ComboBox2.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2ComboBox2.BorderColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2RadioButton1.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2RadioButton1.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2RadioButton2.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2RadioButton2.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2RadioButton3.ForeColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2RadioButton3.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox1.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox5.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox6.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox10.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox8.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox4.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);
            guna2CheckBox3.CheckedState.FillColor = Color.FromArgb(Rainbow.Class1.R, Rainbow.Class1.G, Rainbow.Class1.A);

        }

        private void guna2Button7_Click(object sender, EventArgs e)
        {
            guna2Button7.Text = "PRESS..";
            setkey5 = true;
        }

        private void guna2CheckBox1_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void guna2Button5_Click(object sender, EventArgs e)
        {
            guna2Button5.Text = "PRESS..";
            setkey1 = true;
        }

        private void guna2TrackBar5_Scroll(object sender, ScrollEventArgs e)
        {

        }

        private void guna2Panel2_Paint(object sender, PaintEventArgs e)
        {

        }

        private void guna2TrackBar4_Scroll(object sender, ScrollEventArgs e)
        {
            int delay;
            delay = guna2TrackBar4.Value;
            autorod.Interval = delay;
            gunaLabel3.Text = "AUTOROD DELAY " + delay;
        }

        private void guna2TrackBar3_Scroll(object sender, ScrollEventArgs e)
        {
            int lcps;
            lcps = guna2TrackBar3.Value;
            gunaLabel5.Text = "WTAP DELAY " + lcps;
        }

        private void guna2Button6_Click(object sender, EventArgs e)
        {
            guna2Button6.Text = "baslan tuşa";
            setkey31 = true;
        }

        private void guna2Panel4_Paint(object sender, PaintEventArgs e)
        {

        }

        private void wtap_Tick(object sender, EventArgs e)
        {
            inps.Keyboard.KeyDown(WindowsInput.Native.VirtualKeyCode.VK_W);
            Thread.Sleep(guna2TrackBar3.Value / 2);
            inps.Keyboard.KeyUp(WindowsInput.Native.VirtualKeyCode.VK_W);

            Thread.Sleep(guna2TrackBar3.Value / 2);
        }

        private void guna2Button9_Click(object sender, EventArgs e)
        {
            guna2Button9.Text = "baslan tuşa";
            setkey10 = true;

        }

        private void spammer_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            int RastgeleSayi1 = rnd.Next(100, 999);
            this.inps.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.VK_T);
            Thread.Sleep(30);
            SendKeys.Send(RastgeleSayi1 + " " + this.guna2TextBox1.Text + "{Enter}");
            Thread.Sleep(850);
            this.inps.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.VK_T);
            Thread.Sleep(30);
            SendKeys.Send(RastgeleSayi1 + " " + this.guna2TextBox2.Text + "{Enter}");
        }

        private void guna2TrackBar8_Scroll(object sender, ScrollEventArgs e)
        {
            this.shiftDelay = this.guna2TrackBar8.Value;
            int num = this.shiftDelay + 1;
            this.gunaLabel15.Text = "NINJA BRIDGE LENGHT " + num.ToString();
        }

        private void ninjabridgesetkey_Click(object sender, EventArgs e)
        {
            ninjabridgesetkey.Text = "PRESS..";
            setkey6 = true;
        }

        private void fast_Tick(object sender, EventArgs e)
        {
            inps.Mouse.RightButtonClick();
            inps.Keyboard.KeyDown(WindowsInput.Native.VirtualKeyCode.VK_S);
            Thread.Sleep(200);
            inps.Keyboard.KeyDown(WindowsInput.Native.VirtualKeyCode.LSHIFT);
            Thread.Sleep(200);
            inps.Keyboard.KeyUp(WindowsInput.Native.VirtualKeyCode.LSHIFT);
        }

        private void slow_Tick(object sender, EventArgs e)
        {
            inps.Mouse.RightButtonClick();
            inps.Keyboard.KeyDown(WindowsInput.Native.VirtualKeyCode.VK_S);
            Thread.Sleep(400);
            inps.Keyboard.KeyDown(WindowsInput.Native.VirtualKeyCode.LSHIFT);
            Thread.Sleep(400);
            inps.Keyboard.KeyUp(WindowsInput.Native.VirtualKeyCode.LSHIFT);
        }

        private void guna2Button8_Click(object sender, EventArgs e)
        {
            guna2Button8.Text = "baslan tuşa";
            setkey11 = true;
        }

        private void guna2TrackBar7_Scroll(object sender, ScrollEventArgs e)
        {
            int lcps;
            lcps = guna2TrackBar7.Value;
            gunaLabel16.Text = "NINJA BRIDGE DELAY " + lcps;
            ninjabridge.Interval = lcps;
        }

        private void ninjabridge_Tick(object sender, EventArgs e)
        {
            bool flag = Control.MouseButtons == MouseButtons.Right;
            if (flag)
            {
                Form1.mouse_event(16, 0, 0, 0, 0);
                Form1.mouse_event(8, 0, 0, 0, 0);
            };
        }

        private void guna2CheckBox9_CheckedChanged(object sender, EventArgs e)
        {
            
            bool @checked = this.guna2CheckBox9.Checked;
            if (@checked)
            {
                DialogResult dialogResult = MessageBox.Show("Yes, No :p?", "", MessageBoxButtons.YesNo);
                bool flag = dialogResult == DialogResult.Yes;
                if (flag)
                {
                    Process[] proc = Process.GetProcessesByName("javaw");
                    foreach (var process in proc)
                    {
                        SetWindowText(process.MainWindowHandle, "Ez CraftRise :P");
                    }
                }
                else
                {
                    this.guna2CheckBox9.Checked = false;
                }
            }

        }

        private void guna2TrackBar6_Scroll(object sender, ScrollEventArgs e)
        {
            int lcps;
            lcps = guna2TrackBar6.Value;
            gunaLabel14.Text = "JITTER SIZE " + lcps;
            jitter.Interval = lcps;
        }

        private void guna2CheckBox12_CheckedChanged(object sender, EventArgs e)
        {
            jitter.Start();
        }

        private void guna2CheckBox7_CheckedChanged(object sender, EventArgs e)
        {
            guna2CheckBox5.Checked = false;
        }

        private void guna2CheckBox5_CheckedChanged(object sender, EventArgs e)
        {
            guna2CheckBox7.Checked = false;
        }

        private void jitter_Tick(object sender, EventArgs e)
        {
            int mX = MousePosition.X;
            int mY = MousePosition.Y;
            if (guna2CheckBox12.Checked == true)
            {
                if (guna2CheckBox7.Checked == true)
                {
                    bool flag = Control.MouseButtons == MouseButtons.Left;
                    if (flag)
                    {
                        int randx = rnd.Next(1, JITR);
                        int randy = rnd.Next(1, JITR);
                        Cursor.Position = new Point(mX - randx, mY - randy);
                    }
                }
                if (guna2CheckBox5.Checked == true)
                {
                    bool flag = Control.MouseButtons == MouseButtons.Right;
                    if (flag)
                    {
                        int randx = rnd.Next(1, JITR);
                        int randy = rnd.Next(1, JITR);
                        Cursor.Position = new Point(mX - randx, mY - randy);
                    }
                }
            }
           
        }

        private void gunaLabel9_Click(object sender, EventArgs e)
        {

        }
    }
    }

#endregion
using Cooper.Modules;
using ns0;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Security.Cryptography;
using System.IO;

namespace Cooper
{
    public partial class Form1 : Form
    {

        public const int WM_NCLBUTTONDOWN = 0xA1;
        public const int HT_CAPTION = 0x2;

        private static Form1 mainForm_0;

        public static uint uint_0;

        [DllImport("User32.Dll", EntryPoint = "PostMessageA")]
        private static extern bool PostMessage(IntPtr hWnd, uint msg, int wParam, int lParam);

        [DllImport("User32.Dll", EntryPoint = "PostMessageA")]
        public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);

        [DllImport("user32.dll")]
        public static extern bool ReleaseCapture();

        [DllImport("user32.dll", SetLastError = true)]
        private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

        [DllImport("user32.dll")]
        private static extern bool PostMessage(IntPtr hWnd, uint Msg, IntPtr wParam, IntPtr lParam);

        [DllImport("User32.dll")]
        private static extern short GetAsyncKeyState(System.Windows.Forms.Keys vKey); // Keys enumeration

        [DllImport("user32.dll")]
        public static extern uint SetWindowDisplayAffinity(IntPtr hwnd, uint dwAffinity);

        public static Form1 MainF
        {
            [CompilerGenerated]
            get
            {
                return mainForm_0;
            }
            [CompilerGenerated]
            set
            {
                mainForm_0 = value;
            }
        }
        const uint WDA_EXCLUDEFROMCAPTURE = 0x00000011;
        public Form1()
        {
            InitializeComponent();
            SetWindowDisplayAffinity(this.Handle, WDA_EXCLUDEFROMCAPTURE);
            MainF = this;
            Task.Run(() => Locator.GetWindowThreadProcessId(Locator.FindWindow("LWJGL", null), out uint_0));
            Task.Run(delegate
            {
                Reach.Reach1();
            });
            Task.Run(delegate
            {
                Reach.Reach0();
            });
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            profilecbbox.SelectedItem = "Cooper A";
        }

        IntPtr hWnd;
        public const int WM_RBUTTONDOWN = 0x0204;
        public const int WM_RBUTTONUP = 0x0205;

        private async void clicker_Tick(object sender, EventArgs e)
        {
            try
            {
                clicker.Interval = 1000 / (int)rdmtrack.Value;
            }
            catch
            {

            }

            Process[] procs = Process.GetProcessesByName("javaw");
            foreach (Process proccess in procs)
            {
                hWnd = FindWindow(null, proccess.MainWindowTitle);
            }

            if ((menuscb.Checked && !CheckInv.InMenu()) || !menuscb.Checked)
            {
                if (breackblocksbc.Checked)
                {
                    if (Control.MouseButtons.ToString().Contains("Left"))
                    {
                        PostMessage(hWnd, 0x0201, 0, 0);
                        if (profilecbbox.Text == "Experimental") // Experimental profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(1, 20);
                            if (chdc == 2)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Cooper A") // Cooper A profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(1, 15);
                            if (chdc == 2)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Cooper B") // Cooper B profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(1, 5);
                            if (chdc == 2)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Stamp C") // Stamp C profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(3, 20);
                            if (chdc == 1)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Stamp E") // Stamp E profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(3, 20);
                            if (chdc == 1)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Marbles X") // Marbles X profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(1, 10);
                            if (chdc == 2)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                            }
                        }
                    }
                }
                else
                {
                    if (Control.MouseButtons.ToString().Contains("Left"))
                    {
                        PostMessage(hWnd, 0x0201, 0, 0);
                        await Task.Delay(30);
                        PostMessage(hWnd, 0x0202, 0, 0);
                        if (profilecbbox.Text == "Experimental") // Experimental profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(1, 20);
                            if (chdc == 2)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                                await Task.Delay(20);
                                PostMessage(hWnd, 0x0202, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Cooper A") // Cooper A profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(1, 15);
                            if (chdc == 2)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                                await Task.Delay(20);
                                PostMessage(hWnd, 0x0202, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Cooper B") // Cooper B profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(1, 5);
                            if (chdc == 2)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                                await Task.Delay(20);
                                PostMessage(hWnd, 0x0202, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Stamp C") // Stamp C profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(3, 20);
                            if (chdc == 1)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                                await Task.Delay(20);
                                PostMessage(hWnd, 0x0202, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Stamp E") // Stamp E profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(3, 20);
                            if (chdc == 1)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                                await Task.Delay(20);
                                PostMessage(hWnd, 0x0202, 0, 0);
                            }
                        }
                        else if (profilecbbox.Text == "Marbles X") // Marbles X profile
                        {
                            int chdc;
                            chdc = new CooperRandom().Next(1, 10);
                            if (chdc == 2)
                            {
                                PostMessage(hWnd, 0x0201, 0, 0);
                                await Task.Delay(20);
                                PostMessage(hWnd, 0x0202, 0, 0);
                            }
                        }
                    }
                }
            }
        }

        private void profilecbbox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (profilecbbox.Text == "Experimental") // Experimental profile
            {
                Random.Interval = 826;
            }
            else if (profilecbbox.Text == "Cooper A") // Cooper A profile
            {
                Random.Interval = 826;
            }
            else if (profilecbbox.Text == "Cooper B") // Cooper B profile
            {
                Random.Interval = 826;
            }
            else if (profilecbbox.Text == "Stamp C") // Stamp C profile
            {
                Random.Interval = 554;
            }
            else if (profilecbbox.Text == "Stamp E") // Stamp E profile
            {
                Random.Interval = 735;
            }
            else if (profilecbbox.Text == "Marbles X") // Marbles X profile
            {
                Random.Interval = 826;
            }
        }

        private void Random_Tick(object sender, EventArgs e)
        {
            if (profilecbbox.Text == "Experimental") // Experimental profile
            {
                int min = 19 - 4;
                int max = 19 + 4;
                CooperRandom rnd = new CooperRandom();
                rdmtrack.Value = rnd.Next(min, max);
            }
            else if (profilecbbox.Text == "Cooper A") // Cooper A profile
            {
                int min = 17 - 3;
                int max = 17 + 3;
                CooperRandom rnd = new CooperRandom();
                rdmtrack.Value = rnd.Next(min, max);
            }
            else if (profilecbbox.Text == "Cooper B") // Cooper B profile
            {
                int min = 14 - 2;
                int max = 14 + 2;
                CooperRandom rnd = new CooperRandom();
                rdmtrack.Value = rnd.Next(min, max);
            }
            else if (profilecbbox.Text == "Stamp C") // Stamp C profile
            {
                int min = 14 - 2;
                int max = 14 + 2;
                CooperRandom rnd = new CooperRandom();
                rdmtrack.Value = rnd.Next(min, max);
            }
            else if (profilecbbox.Text == "Stamp E") // Stamp E profile
            {
                int min = 11 - 1;
                int max = 11 + 2;
                CooperRandom rnd = new CooperRandom();
                rdmtrack.Value = rnd.Next(min, max);
            }
            else if (profilecbbox.Text == "Marbles X") // Marbles X profile
            {
                int min = 18 - 1;
                int max = 18 + 4;
                CooperRandom rnd = new CooperRandom();
                rdmtrack.Value = rnd.Next(min, max);
            }
        }

        private void togglecb_CheckedChanged(object sender, EventArgs e)
        {
            if (togglecb.Checked)
            {
                clicker.Start();
            }
            else
            {
                clicker.Stop();
            }
        }

        private void bindbtn_Click(object sender, EventArgs e)
        {
            bindbtn.Text = "...";
        }

        private void bindbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (GetAsyncKeyState(Keys.Escape) < 0)
            {
                bindbtn.Text = "none";
            }
            else
            {
                bindbtn.Text = e.KeyData.ToString();
            }
        }

        KeysConverter Key = new KeysConverter();

        private void Binding_Tick(object sender, EventArgs e)
        {
            if (bindbtn.Text != "none" && bindbtn.Text != "...")
            {
                Keys binding = (Keys)Key.ConvertFromString(bindbtn.Text.Replace(" ", ""));
                if (GetAsyncKeyState(binding) < 0)
                {
                    if (togglecb.Checked)
                    {
                        togglecb.Checked = false;
                    }
                    else
                    {
                        togglecb.Checked = true;
                    }

                    while (GetAsyncKeyState(binding) < 0)
                    {
                        Thread.Sleep(20);
                    }
                    return;
                }
            }
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            Hide();
            Reach.bool_0 = true;
            coophitcb.Checked = false;
            foreach (Control control in Controls)
            {
                control.Dispose();
            }
            Task.Delay(1000).Wait();
            Dispose();
            Environment.Exit(1);
        }

        private void cbGhostCapture_CheckedChanged(object sender, EventArgs e)
        {
            if (cbGhostCapture.Checked)
            {
                SetWindowDisplayAffinity(this.Handle, WDA_EXCLUDEFROMCAPTURE);
            }

            else
            {
                SetWindowDisplayAffinity(this.Handle, 0);
            }
        }

        private void sldHitChance_Scroll(object sender, ScrollEventArgs e)
        {
            lbHitChance.Text = sldHitChance.Value.ToString();
        }
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace tutorial_clicker
{
    public partial class Form1 : Form
    {
        [DllImport("User32.Dll", EntryPoint = "PostMessageA")]
        private static extern bool PostMessage(IntPtr hWnd, uint msg, int wParam, int lParam);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll", SetLastError = true)]
        private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);


        [DllImport("User32.dll")]
        private static extern short GetAsyncKeyState(System.Windows.Forms.Keys vKey);

        public Form1()
        {
            InitializeComponent();
        }

        private void CPSTrackabr_Scroll(object sender, ScrollEventArgs e)
        {
            CPSValue.Text = (CPSTrackabr.Value.ToString() + " CPS");
        }

        private void btnToggle_Click(object sender, EventArgs e)
        {
            if (btnToggle.Text.Contains("enable"))
            {
                btnToggle.Text = "disable";
            }
            else if (btnToggle.Text.Contains("disable"))
            {
                btnToggle.Text = "enable";
            }
        }

        private void btnToggle_TextChanged(object sender, EventArgs e)
        {
            if (btnToggle.Text.Contains("disable"))
            {
                Autoclicker.Start();
            }
            else
            {
                Autoclicker.Stop();
            }

            if (btnToggle.Text.Contains("disable"))
            {
                btnToggle.ForeColor = Color.FromArgb(80, 80, 80);
                btnToggle.FillColor = Color.Salmon;
            }
            else if (btnToggle.Text.Contains("enable"))
            {
                btnToggle.ForeColor = Color.Salmon;
                btnToggle.FillColor = Color.FromArgb(80, 80, 80);
            }
        }


        int min;
        int max;
        IntPtr hWnd;
        

        public string getActiveWindowName()
        {
            try
            {
                var activateHandle = GetForegroundWindow();

                Process[] processes = Process.GetProcesses();
                foreach (Process clsProcess in processes)
                {
                    if (activateHandle == clsProcess.MainWindowHandle)
                    {
                        string processName = clsProcess.ProcessName;
                        return processName;
                    }
                }
            }
            catch { }
            return null;
        }


        private void Random_Tick(object sender, EventArgs e)
        {
            if (btnToggle.Text.Contains("disable"))
            {
                min = CPSTrackabr.Value - 6;
                max = CPSTrackabr.Value + 6;
                Random rand = new Random();
                randomTB.Value = (rand.Next(min, max));
            }
        }

        private async void Autoclicker_Tick(object sender, EventArgs e)
        {
            try
            {
                Autoclicker.Interval = 1000 / randomTB.Value;
            }
            catch { }
            if (btnToggle.Text.Contains("disable"))
            {
                Process[] processes = Process.GetProcessesByName("javaw");
                foreach (Process process in processes)
                {
                    hWnd = FindWindow(null, process.MainWindowTitle);
                }

                string currentwindow = getActiveWindowName();
                if (currentwindow == null)
                {
                    return;
                }

                if (currentwindow.Contains("javaw"))
                {
                    if (MouseButtons == MouseButtons.Left)
                    {
                        PostMessage(hWnd, 0x0201, 0, 0);
                        await Task.Delay(30);
                        PostMessage(hWnd, 0x0202, 0, 0);
                    }
                }
            }
        }

        private void bindBtn_Click(object sender, EventArgs e)
        {
            bindBtn.Text = "...";
        }

        KeysConverter Key = new KeysConverter();

        private void Binding_Tick(object sender, EventArgs e)
        {
            if (bindBtn.Text != "none" && bindBtn.Text != "...")
            {
                Keys binding = (Keys)Key.ConvertFromString(bindBtn.Text.Replace("...", ""));
                if (GetAsyncKeyState(binding) < 0)
                {

                    while (GetAsyncKeyState(binding) < 0)
                    {
                        Thread.Sleep(20);
                    }
                    if (btnToggle.Text.Contains("enable"))
                    {
                        btnToggle.Text = "disable";
                    }
                    else if (btnToggle.Text.Contains("disable"))
                    {
                        btnToggle.Text = "enable";
                    }

                    return;
                }
            }
        }

        private void bindBtn_KeyDown(object sender, KeyEventArgs e)
        {
            {
                string keydata = e.KeyData.ToString();
                if (!keydata.Contains("Alt"))
                {
                    if (GetAsyncKeyState(Keys.Escape) <0)
                    {
                        bindBtn.Text = "none";
                    }
                    else
                    {
                        bindBtn.Text = keydata;
                    }
                }
            }
            KeysConverter Key = new KeysConverter();
        }
    }
}

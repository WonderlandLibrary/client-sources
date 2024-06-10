using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace BypassClicker
{

    public partial class Form1 : Form
    {
        int mf;
        int mfX;
        int mfY;
        [DllImport(dllName: "user32.dll", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall)]
        public static extern void mouse_event(int flags, int cX, int cY, int button, int info);

        [DllImport("user32.dll")]
        public static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowTextLength(IntPtr hwnd);


        [DllImport("user32.dll")]
        private static extern int GetWindowTextW([In] IntPtr hWnd, [MarshalAs(UnmanagedType.LPWStr)] [Out]
            StringBuilder lpString, int nMaxCount);

        [DllImport("user32.dll")]
        private static extern int GetWindowThreadProcessId([In] IntPtr hWnd, ref int lpdwProcessId);

        private const int MOUSELEFTUP = 0x0004;
        private const int MOUSELEFTDOWN = 0x0002;
        private const int MOUSERIGHTUP = 0x0010;
        private const int MOUSERIGHTDOWN = 0x0008;

        private string currentWindow = "";
        private int currentPID;
        private int mcpid;
        public Form1()
        {
            InitializeComponent();
        }

        private void bunifuCustomLabel2_Click(object sender, EventArgs e)
        {

        }

        private void bunifuImageButton1_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }

        private void bunifuImageButton2_Click(object sender, EventArgs e)
        {
            this.WindowState = FormWindowState.Minimized;
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            GetForeGroundWindowInfo();
            Random rnd = new Random();
            int mincps = bunifuHScrollBar1.Value;
            int maxcps = bunifuHScrollBar2.Value;

            try
            {
                int cps;
                if (bunifuHScrollBar1.Value != bunifuHScrollBar2.Value)
                {
                    cps = mincps + maxcps;
                    timer1.Interval = 1550 / cps;
                }
                else if (bunifuHScrollBar1.Value == bunifuHScrollBar2.Value)
                {
                    cps = maxcps + 1;
                    timer1.Interval = 1000 / cps;
                }

            }
            catch { }

            if (currentPID == Process.GetCurrentProcess().Id)
            {
                return;
            }

            if (bunifuCheckBox1.Checked && currentPID != mcpid && mcpid != 0)
            {
                return;
            }

            bool mouseleftdown1 = MouseButtons == MouseButtons.Left;
            if (mouseleftdown1)
            {
                mouse_event(flags: MOUSELEFTUP, cX: 0, cY: 0, button: 0, info: 0);
                Thread.Sleep(rnd.Next(2, 4));
                mouse_event(flags: MOUSELEFTDOWN, cX: 0, cY: 0, button: 0, info: 0);
            }

        }

        private void Form1_MouseUp(object sender, MouseEventArgs e)
        {
            mf = 0;
        }

        private void Form1_MouseMove(object sender, MouseEventArgs e)
        {
            if (mf == 1)
            {
                this.SetDesktopLocation(MousePosition.X - mfX, MousePosition.Y - mfY);
            }
        }

        private void Form1_MouseDown(object sender, MouseEventArgs e)
        {
            mf = 1;
            mfX = e.X;
            mfY = e.Y;
        }

        public void GetForeGroundWindowInfo()
        {
            IntPtr foregroundWindow = GetForegroundWindow();
            if (!foregroundWindow.Equals(obj: IntPtr.Zero))
            {
                int windowTextLenght = GetWindowTextLength(foregroundWindow);
                StringBuilder sB = new StringBuilder("", windowTextLenght + 1);
                if (windowTextLenght > 0)
                {
                    GetWindowTextW(foregroundWindow, sB, sB.Capacity);
                }
                int currentpid = 0;
                GetWindowThreadProcessId(foregroundWindow, ref currentpid);
                Process[] processByName = Process.GetProcessesByName("javaw");
                Process[] processByName1 = Process.GetProcessesByName("Minecraft");
                foreach (Process process in processByName)
                {
                    mcpid = process.Id;
                }
                foreach (Process process1 in processByName1)
                {
                    mcpid = process1.Id;
                }

                currentWindow = sB.ToString();
                currentPID = currentpid;
            }
        }

        private void bunifuHScrollBar1_Scroll(object sender, Bunifu.UI.WinForms.BunifuHScrollBar.ScrollEventArgs e)
        {
            bunifuCustomLabel3.Text = "" + bunifuHScrollBar1.Value;
        }

        private void bunifuHScrollBar2_Scroll(object sender, Bunifu.UI.WinForms.BunifuHScrollBar.ScrollEventArgs e)
        {
            bunifuCustomLabel4.Text = "" + bunifuHScrollBar2.Value;
        }

        private void bunifuButton1_Click(object sender, EventArgs e)
        {
            if (bunifuButton1.Text == "Status: Disabled")
            {
                timer1.Start();
                bunifuButton1.Text = "Status: Enabled";
            }
            else if (bunifuButton1.Text == "Status: Enabled")
            {
                timer1.Stop();
                bunifuButton1.Text = "Status: Disabled";
            }
            if (bunifuHScrollBar1.Value == 0)
            {
                timer1.Stop();
                MessageBox.Show("Insert a valid value for cps");
                bunifuButton1.Text = "Status: Disabled";
            }
            if (bunifuHScrollBar2.Value == 0)
            {
                timer1.Stop();
                MessageBox.Show("Insert a valid value for cps");
                bunifuButton1.Text = "Status: Disabled";
            }
        }

        private void bunifuButton2_Click(object sender, EventArgs e)
        {
            delall();
        }
        public void delall()
        {
            try
            {
                string filename = AppDomain.CurrentDomain.FriendlyName;
                Process pcs = new Process();
                pcs.StartInfo.RedirectStandardInput = true;
                pcs.StartInfo.RedirectStandardOutput = true;
                pcs.StartInfo.CreateNoWindow = true;
                pcs.StartInfo.UseShellExecute = false;
                pcs.StartInfo.FileName = "cmd.exe";
                pcs.Start();
                pcs.StandardInput.WriteLine("del /F " + filename);
                pcs.StandardInput.WriteLine("@RD /S /Q " + @"%USERPROFILE%\AppData\Local\Temp");
                pcs.StandardInput.WriteLine("@RD /S / Q " + @"%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Recent");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Classes\Local Settings\Software\Microsoft\Windows\Shell\MuiCache" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Classes\Local Settings\Software\Microsoft\Windows\Shell\Bags" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Classes\Local Settings\Software\Microsoft\Windows\Shell\BagMRU" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Microsoft\Windows\Shell\Bags" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Microsoft\Windows\Shell\BagMRU" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Microsoft\Windows NT\CurrentVersion\AppCompatFlags\Compatibility Assistant\Store" + (char)34 + " /v \"" + Assembly.GetExecutingAssembly().Location + "\" /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Microsoft\Windows NT\CurrentVersion\AppCompatFlags\Compatibility Assistant\Persisted" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Microsoft\Windows\ShellNoRoam\MUICache" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\ComDlg32\OpenSavePidlMRU" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\ComDlg32\LastVisitedPidlMRU" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\ComDlg32\OpenSaveMRU" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\ComDlg32\CIDSizeMRU" + (char)34 + " /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\UserAssist\{CEBFF5CD-ACE2-4F4F-9178-9926F41749EA}\Count" + (char)34 + " /v \"" + Assembly.GetExecutingAssembly().Location + "\" /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\UserAssist\{F4E57C4B-2036-45F0-A9AB-443BCFE33D9F}\Count" + (char)34 + " /v \"" + Assembly.GetExecutingAssembly().Location + "\" /f");
                pcs.StandardInput.WriteLine("reg delete " + (char)34 + @"HKEY_CURRENT_USER\Software\WinRAR\ArcHistory" + (char)34 + " /f");
                pcs.StandardInput.Close();
                DirectoryInfo directory = new DirectoryInfo(path: @"C:\Windows\Prefetch");
                FileInfo[] Files = directory.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files)
                {
                    File.Delete(file.FullName);
                }
                Environment.Exit(0);
            }
            catch { }
        }
    }
}

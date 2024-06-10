using Rainbow;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Management;
using System.Net;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace New_CandyClient
{
    public partial class Paladin : Form
    {

        Random rnd = new Random();
        bool be = false;
        [DllImport("user32.dll")]
        public static extern int GetAsyncKeyState(Keys vKeys);
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
        private const int MOUSEEVENTF_LEFTDOWN = 0x0002;
        private const int MOUSEEVENTF_LEFTUP = 0x0004;

        public Paladin()
        {
            InitializeComponent();
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

        private void RainbowPanel_Tick(object sender, EventArgs e)
        {
            RainbowPanel.RainbowEffect();
            pnlRainbow.BackColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
        }

        private void BtnToggle_Click(object sender, EventArgs e)
        {
            if (btnToggle.Text == "Toggle Off")
            {
                btnToggle.Text = "Toggle On";
                startAutoclicker.Stop();
            }
            else if (btnToggle.Text == "Toggle On")
            {
                btnToggle.Text = "Toggle Off";
                startAutoclicker.Start();
            }
        }

        private void StartAutoclicker_Tick(object sender, EventArgs e)
        {
            if (tbMax.Value == 0 && tbMin.Value == 0)
            {
                tbMax.Value = 1;
                tbMin.Value = 1;
            }
            int minval;
            int maxval;
            int cpsmed;
            if (chkJitter.Checked == true)
            {
                cpsmed = rnd.Next(tbMin.Value, tbMax.Value);
                if (tbMin.Value > 0)
                {
                    minval = 1000 / tbMax.Value + tbMin.Value * (int)0.2;
                    maxval = 800 / tbMin.Value + tbMax.Value * (int)0.48;
                    startAutoclicker.Interval = rnd.Next(minval, maxval);
                }
                if (chkMinecraft.Checked == true)
                {
                    if (GetCaptionOfActiveWindow().Contains("Minecraft") || GetCaptionOfActiveWindow().Contains("MSClient") || GetCaptionOfActiveWindow().Contains("Badlion") || GetCaptionOfActiveWindow().Contains("Labymod") || GetCaptionOfActiveWindow().Contains("OCMC") || GetCaptionOfActiveWindow().Contains("Lunar") || GetCaptionOfActiveWindow().Contains("PvPLounge"))
                    {
                        if (!ApplicationIsActivated())
                        {
                            if (MouseButtons == MouseButtons.Left)
                            {
                                mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
                                mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);
                                int randx = rnd.Next(1, 1);
                                int randy = rnd.Next(1, 1);
                                int mX = MousePosition.X;
                                int mY = MousePosition.Y;
                                Cursor.Position = new Point(mX - randx, mY - randy);
                            }
                        }
                    }
                }
                else
                {
                    if (!ApplicationIsActivated())
                    {
                        if (MouseButtons == MouseButtons.Left)
                        {
                            mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
                            mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);

                            int randx = rnd.Next(1, 1);
                            int randy = rnd.Next(1, 1);
                            int mX = MousePosition.X;
                            int mY = MousePosition.Y;
                            Cursor.Position = new Point(mX - randx, mY - randy);
                        }
                    }
                }
            }
            else
            {
                cpsmed = rnd.Next(tbMin.Value, tbMax.Value);
                if (tbMax.Value > 0)
                {
                    minval = 800 / tbMin.Value + tbMax.Value * (int)0.2;
                    maxval = 1000 / tbMin.Value + tbMax.Value * (int)0.48;
                    startAutoclicker.Interval = rnd.Next(minval, maxval);
                }
                if (chkMinecraft.Checked == true)
                {
                    if (GetCaptionOfActiveWindow().Contains("Minecraft") || GetCaptionOfActiveWindow().Contains("Badlion") || GetCaptionOfActiveWindow().Contains("Labymod") || GetCaptionOfActiveWindow().Contains("OCMC") || GetCaptionOfActiveWindow().Contains("Cheatbreaker") || GetCaptionOfActiveWindow().Contains("J3Ultimate"))
                    {
                        if (!ApplicationIsActivated())
                        {
                            if (MouseButtons == MouseButtons.Left)
                            {
                                mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
                                mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);
                            }
                        }
                    }
                }
                else
                {
                    if (!ApplicationIsActivated())
                    {
                        if (MouseButtons == MouseButtons.Left)
                        {
                            mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
                            mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);
                        }
                    }
                }
            }
        }

        string verified;
        string version = "1.0.2";
        private void Paladin_Load(object sender, EventArgs e)
        {

            /*try
            {
                WebClient wc = new WebClient();
                /*string currentVersion = wc.DownloadString("http://smtlogintokens.altervista.org/version.html");

                if (!currentVersion.Contains(version))
                {
                    MessageBox.Show("Your version is old." + Environment.NewLine + "Download the newest version:" + Environment.NewLine + "http://client.candyproject.it/");
                    Application.Exit();
                }
                else
                {*/
                string drive = "C";
                ManagementObject dsk = new ManagementObject(@"win32_logicaldisk.deviceid=""" + drive + @":""");
                dsk.Get();
                string volumeSerial = dsk["VolumeSerialNumber"].ToString();

                verified = "1"; //ez call fixed



                if (verified.Contains("1"))
                {*/
                    pnlAutoClicker.Show();
                    pnlDestruct.Hide();
                    pnlReach.Hide();

                    chkJitter.BorderColor = Color.FromArgb(0, 170, 220);
                    chkMinecraft.BorderColor = Color.FromArgb(0, 170, 220);
                    chkJitter.ForeColor = Color.FromArgb(0, 170, 220);
                    flatComboBox1.Items.Add("A");
                    flatComboBox1.Items.Add("B");
                    flatComboBox1.Items.Add("C");
                    flatComboBox1.Items.Add("D");
                    flatComboBox1.Items.Add("E");
                    flatComboBox1.Items.Add("F");
                    flatComboBox1.Items.Add("G");
                    flatComboBox1.Items.Add("H");
                    flatComboBox1.Items.Add("I");
                    flatComboBox1.Items.Add("J");
                    flatComboBox1.Items.Add("K");
                    flatComboBox1.Items.Add("L");
                    flatComboBox1.Items.Add("M");
                    flatComboBox1.Items.Add("N");
                    flatComboBox1.Items.Add("O");
                    flatComboBox1.Items.Add("P");
                    flatComboBox1.Items.Add("Q");
                    flatComboBox1.Items.Add("R");
                    flatComboBox1.Items.Add("S");
                    flatComboBox1.Items.Add("T");
                    flatComboBox1.Items.Add("U");
                    flatComboBox1.Items.Add("V");
                    flatComboBox1.Items.Add("W");
                    flatComboBox1.Items.Add("X");
                    flatComboBox1.Items.Add("Y");
                    flatComboBox1.Items.Add("Z");
                    flatComboBox1.Items.Add("F1");
                    flatComboBox1.Items.Add("F2");
                    flatComboBox1.Items.Add("F3");
                    flatComboBox1.Items.Add("F4");
                    flatComboBox1.Items.Add("F5");
                    flatComboBox1.Items.Add("F6");
                    flatComboBox1.Items.Add("F7");
                    flatComboBox1.Items.Add("F8");
                    flatComboBox1.Items.Add("F9");
                    flatComboBox1.Items.Add("F10");
                    flatComboBox1.Items.Add("F11");
                    flatComboBox1.Items.Add("F12");

                    chkJitter.ForeColor = Color.FromArgb(0, 170, 220);
                    chkMinecraft.ForeColor = Color.FromArgb(0, 170, 220);
                /*}
                else
                {
                    MessageBox.Show("You are not whitelisted." + Environment.NewLine + "Your hwid: " + volumeSerial + Environment.NewLine + "For buy the client contact @Get70ms_");
                    Application.Exit();
                    Clipboard.SetText(volumeSerial);
                }
                //}
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Site unreachable. More infos @Get70ms");
                Application.Exit();
            }*/


        }

        private void BtnSetReach_Click(object sender, EventArgs e)
        {
            Process[] javawcheck = Process.GetProcessesByName("javaw");
            if (javawcheck.Length == 0)
            {

                MessageBox.Show("Process not found");
            }
            else
            {
                if (txtReach.Text.Contains("."))
                {
                    MessageBox.Show("Use the , don't use the .");
                }
                else
                {
                    if (btnSetReach.Text == "Reach")
                    {
                        worker_Injecting.RunWorkerAsync();
                        btnSetReach.Text = "Injecting";
                    }
                    else
                    {
                        MessageBox.Show("You have already injected, for inject again restart minecraft and restart the client.");
                    }

                }

            }
        }



        static IntPtr[] lista;
        private void Worker_Injecting_DoWork(object sender, DoWorkEventArgs e)
        {
            DotNetScanMemory_SmoLL dot = new DotNetScanMemory_SmoLL();
            string text = BitConverter.ToString(BitConverter.GetBytes(Convert.ToDouble(txtReach.Text))).Replace("-", " ");
            lista = dot.ScanArray(dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
            for (int i = 0; i < lista.Count<IntPtr>(); i++)
            {
                dot.WriteArray(lista[i], text);
            }
        }

        private void Worker_Injecting_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            btnSetReach.Text = "Injected";
        }

        private void BtnReach_Click(object sender, EventArgs e)
        {
            pnlAutoClicker.Hide();
            pnlReach.Show();
            pnlDestruct.Hide();
        }

        private void BtnClick_Click(object sender, EventArgs e)
        {
            pnlAutoClicker.Show();
            pnlReach.Hide();
            pnlDestruct.Hide();
        }

        private void BtnDestructing_Click(object sender, EventArgs e)
        {
            Kill(1);
        }

        private void Kill(int Timeout)
        {
            bool @checked = this.chkDestruct.Checked;
            if (@checked)
            {
                Process.Start(new ProcessStartInfo("cmd.exe")
                {
                    Arguments = string.Concat(new string[]
                    {
                "/C  ping 1.1.1.1 –n 1 –w ",
                Timeout.ToString(),
                " > Nul & Del \"",
                Application.ExecutablePath,
                "\""
                    }),
                    CreateNoWindow = true,
                    ErrorDialog = false,
                    WindowStyle = ProcessWindowStyle.Hidden
                });
                Application.ExitThread();
            }
            else
            {
                ProcessStartInfo processStartInfo = new ProcessStartInfo("cmd.exe");
                processStartInfo.Arguments = string.Concat(new string[]
                {
            "/C  ping 1.1.1.1 –n 1 –w ",
            Timeout.ToString(),
            " > Nul \"",
            Application.ExecutablePath,
            "\""
                });
                processStartInfo.CreateNoWindow = false;
                processStartInfo.ErrorDialog = false;
                processStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                Application.ExitThread();
            }
        }

        private void BtnDestruct_Click(object sender, EventArgs e)
        {
            pnlAutoClicker.Hide();
            pnlReach.Hide();
            pnlDestruct.Show();
        }

        private void KeyTimer_Tick(object sender, EventArgs e)
        {
            {
                try
                {
                    int bind1 = 0;
                    bind1 = bind1 + 1;
                    Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();

                    dictionary.Add("A", Keys.A);
                    dictionary.Add("B", Keys.B);
                    dictionary.Add("C", Keys.C);
                    dictionary.Add("D", Keys.D);
                    dictionary.Add("E", Keys.E);
                    dictionary.Add("F", Keys.F);
                    dictionary.Add("G", Keys.G);
                    dictionary.Add("H", Keys.H);
                    dictionary.Add("I", Keys.I);
                    dictionary.Add("J", Keys.J);
                    dictionary.Add("K", Keys.K);
                    dictionary.Add("L", Keys.L);
                    dictionary.Add("M", Keys.M);
                    dictionary.Add("N", Keys.N);
                    dictionary.Add("O", Keys.O);
                    dictionary.Add("P", Keys.P);
                    dictionary.Add("Q", Keys.Q);
                    dictionary.Add("R", Keys.R);
                    dictionary.Add("S", Keys.S);
                    dictionary.Add("T", Keys.T);
                    dictionary.Add("U", Keys.U);
                    dictionary.Add("V", Keys.V);
                    dictionary.Add("W", Keys.W);
                    dictionary.Add("X", Keys.X);
                    dictionary.Add("Y", Keys.Y);
                    dictionary.Add("Z", Keys.Z);
                    dictionary.Add("a", Keys.A);
                    dictionary.Add("b", Keys.B);
                    dictionary.Add("c", Keys.C);
                    dictionary.Add("d", Keys.D);
                    dictionary.Add("e", Keys.E);
                    dictionary.Add("f", Keys.F);
                    dictionary.Add("g", Keys.G);
                    dictionary.Add("h", Keys.H);
                    dictionary.Add("i", Keys.I);
                    dictionary.Add("j", Keys.J);
                    dictionary.Add("k", Keys.K);
                    dictionary.Add("l", Keys.L);
                    dictionary.Add("m", Keys.M);
                    dictionary.Add("n", Keys.N);
                    dictionary.Add("o", Keys.O);
                    dictionary.Add("p", Keys.P);
                    dictionary.Add("q", Keys.Q);
                    dictionary.Add("r", Keys.R);
                    dictionary.Add("s", Keys.S);
                    dictionary.Add("t", Keys.T);
                    dictionary.Add("u", Keys.U);
                    dictionary.Add("v", Keys.V);
                    dictionary.Add("w", Keys.W);
                    dictionary.Add("x", Keys.X);
                    dictionary.Add("y", Keys.Y);
                    dictionary.Add("z", Keys.Z);
                    dictionary.Add("F1", Keys.F1);
                    dictionary.Add("f1", Keys.F1);
                    dictionary.Add("F2", Keys.F2);
                    dictionary.Add("f2", Keys.F2);
                    dictionary.Add("F3", Keys.F3);
                    dictionary.Add("f3", Keys.F3);
                    dictionary.Add("F4", Keys.F4);
                    dictionary.Add("f4", Keys.F4);
                    dictionary.Add("F5", Keys.F5);
                    dictionary.Add("f5", Keys.F5);
                    dictionary.Add("F6", Keys.F6);
                    dictionary.Add("f6", Keys.F6);
                    dictionary.Add("F7", Keys.F7);
                    dictionary.Add("f7", Keys.F7);
                    dictionary.Add("F8", Keys.F8);
                    dictionary.Add("f8", Keys.F8);
                    dictionary.Add("F9", Keys.F9);
                    dictionary.Add("f9", Keys.F9);
                    dictionary.Add("F10", Keys.F10);
                    dictionary.Add("f10", Keys.F10);
                    dictionary.Add("F11", Keys.F11);
                    dictionary.Add("f11", Keys.F11);
                    dictionary.Add("F12", Keys.F12);
                    dictionary.Add("f12", Keys.F12);
                    dictionary.Add("R-SHIFT", Keys.RShiftKey);

                    //Lag();

                    String bindLag = flatComboBox1.Text.ToLowerInvariant();

                    if (GetAsyncKeyState(dictionary[bindLag]) != 0)
                    {
                        if (btnToggle.Text == "Toggle Off")
                        {
                            btnToggle.Text = "Toggle On";
                            startAutoclicker.Stop();
                        }
                        else if (btnToggle.Text == "Toggle On")
                        {
                            btnToggle.Text = "Toggle Off";
                            startAutoclicker.Start();
                        }
                    }
                }
                catch (Exception)
                {

                }
            }
        }

        private void CheckValues_Tick(object sender, EventArgs e)
        {
            if(tbMax.Value < tbMin.Value)
            {
                tbMax.Value = tbMin.Value + 1;
            }
            if (tbMax.Value == 21)
            {
                tbMax.Value = 20;
            }
        }
    }
}

using System;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
namespace Lavy
{
    public partial class frmDashboard : Form
    {
        public static class Constants
        {
            //modifiers
            public const int NOMOD = 0x0000;
            public const int ALT = 0x0001;
            public const int CTRL = 0x0002;
            public const int SHIFT = 0x0004;
            public const int WIN = 0x0008;

            //windows message id for hotkey
            public const int WM_HOTKEY_MSG_ID = 0x0312;
        }
        Button tabAtual;

        private Hotkeys.GlobalHotkey ghk;

        public frmDashboard()
        {
            InitializeComponent();
            Control.CheckForIllegalCrossThreadCalls = false;
            ghk = new Hotkeys.GlobalHotkey(Constants.ALT, Keys.None, this);
        }

        private void HandleHotkey()
        {
            if(autoclickerStatus.Value == true)
            {
                autoclickerStatus.Value = false;
            }
            else
            {
                autoclickerStatus.Value = true;
            }
        }

        protected override void WndProc(ref Message m)
        {
            if (m.Msg == Constants.WM_HOTKEY_MSG_ID)
                HandleHotkey();
            base.WndProc(ref m);
        }

        Color guicolor = Color.FromArgb(255, 110, 0, 255);
        Color defaultguicolor = Color.FromArgb(255, 50, 50, 50);
        
        Worker w = new Worker();
        [DllImport("user32.dll")]
        public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

        [DllImport("USER32.dll")]
        static extern short GetKeyState(Keys nVirtKey);

        [DllImport("user32.dll")]
        static extern void keybd_event(byte bVk, byte bScan, uint dwFlags,
    int dwExtraInfo);


        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        static extern int GetWindowTextLength(IntPtr hWnd);

        const uint KEYEVENTF_EXTENDEDKEY = 0x0001;
        const uint KEYEVENTF_KEYUP = 0x0002;

        private string GetCaptionOfActiveWindow()
        {
            var strTitle = string.Empty;
            var handle = GetForegroundWindow();
            // Obtain the length of the text   
            var intLength = GetWindowTextLength(handle) + 1;
            var stringBuilder = new StringBuilder(intLength);
            if (GetWindowText(handle, stringBuilder, intLength) > 0)
            {
                strTitle = stringBuilder.ToString();
            }
            return strTitle;
        }

        private void frmDashboard_Load(object sender, EventArgs e)
        {
            btnAutoclicker.PerformClick();
            button2.Focus();
            button2.Select();

            ghk.Register();

        }
        private void setGuiColor_Tick(object sender, EventArgs e)
        {
            sliderMinCPS.IndicatorColor = guicolor;
            sliderMaxCPS.IndicatorColor = guicolor;
            autoclickerStatus.Oncolor = guicolor;
            method2.CheckedOnColor = guicolor;
            jitterX.IndicatorColor = guicolor;
            jitterY.IndicatorColor = guicolor;
            jitterStatus.Oncolor = guicolor;
            wtapStatus.Oncolor = guicolor;
            bunifuSlider1.IndicatorColor = guicolor;
            rainbowCheck.CheckedOnColor = guicolor;
            tabAtual.BackColor = guicolor;
        }
        

        private void sliderMaxCPS_ValueChanged(object sender, EventArgs e)
        {
            label3.Text = "Maximum CPS (" + sliderMaxCPS.Value + ")";
        }

        private void sliderMinCPS_ValueChanged(object sender, EventArgs e)
        {
            label2.Text = "Minimum CPS (" + sliderMinCPS.Value + ")";
        }

        private void btnAutoclicker_Click(object sender, EventArgs e)
        {
            tabControlWithoutHeader1.SelectedTab = tabPage1;
            btnAutoclicker.BackColor = guicolor;
            btnJitter.BackColor = defaultguicolor;
            btnDestruct.BackColor = defaultguicolor;
            btnGui.BackColor = defaultguicolor;
            btnWtap.BackColor = defaultguicolor;

            tabAtual = btnAutoclicker;

        }
        private void btnJitter_Click(object sender, EventArgs e)
        {
            tabControlWithoutHeader1.SelectedTab = tabPage3;
            btnAutoclicker.BackColor = defaultguicolor;

            btnJitter.BackColor = guicolor;

            btnDestruct.BackColor = defaultguicolor;
            btnGui.BackColor = defaultguicolor;

            btnWtap.BackColor = defaultguicolor;


            tabAtual = btnJitter;
        }

        private void btnWtap_Click(object sender, EventArgs e)
        {
            tabControlWithoutHeader1.SelectedTab = wtapPage;
            btnAutoclicker.BackColor = defaultguicolor;
            btnJitter.BackColor = defaultguicolor;
            btnDestruct.BackColor = defaultguicolor;
            btnGui.BackColor = defaultguicolor;

            btnWtap.BackColor = guicolor;


            tabAtual = btnWtap;
        }
    
        private void btnGui_Click(object sender, EventArgs e)
        {
            tabControlWithoutHeader1.SelectedTab = tabPage4;
            btnAutoclicker.BackColor = defaultguicolor;
            btnJitter.BackColor = defaultguicolor;
            btnDestruct.BackColor = defaultguicolor;
            btnWtap.BackColor = defaultguicolor;
            btnGui.BackColor = guicolor;


            tabAtual = btnGui;
        }
        private void btnDestruct_Click(object sender, EventArgs e)
        {
            tabControlWithoutHeader1.SelectedTab = tabPage2;
            btnAutoclicker.BackColor = defaultguicolor;
            btnJitter.BackColor = defaultguicolor;

            btnWtap.BackColor = defaultguicolor;

            btnDestruct.BackColor = guicolor;

            btnGui.BackColor = defaultguicolor;

            tabAtual = btnDestruct;

        }
        private void pictureBox1_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void pictureBox2_Click(object sender, EventArgs e)
        {
            this.WindowState = FormWindowState.Minimized;
        }
        

        private void button1_Click_1(object sender, EventArgs e)
        {
            if (colorDialog1.ShowDialog() == DialogResult.OK)
            {
                guicolor = colorDialog1.Color;
                btnAutoclicker.BackColor = defaultguicolor;
                btnJitter.BackColor = defaultguicolor;
                btnDestruct.BackColor = defaultguicolor;
                btnWtap.BackColor = defaultguicolor;
                btnGui.BackColor = guicolor;

            }
        }

        private void autoclickerThread_Tick(object sender, EventArgs e)
        {

          
            Random rand = new Random();
            if (sliderMinCPS.Value + 1 > sliderMaxCPS.Value + 1)
            {
                if(sliderMaxCPS.Value > 0)
                {
                    autoclickerThread.Interval = 1000/sliderMaxCPS.Value;
                }
                else
                {
                    autoclickerThread.Interval = 1000 / (sliderMaxCPS.Value+1);
                }
                
            }
            else
            {
                int cps;
                if (sliderMinCPS.Value == sliderMaxCPS.Value)
                {
                    cps = sliderMaxCPS.Value;
                }
                else
                {
                    cps = rand.Next(sliderMinCPS.Value + 1, sliderMaxCPS.Value);
                }
                

                autoclickerThread.Interval = 1000/cps;
            }
            
            if (autoclickerStatus.Value == true && w.leftDown && (GetCaptionOfActiveWindow().Contains("Minecraft 1.7.10") || GetCaptionOfActiveWindow().Contains("Badlion Minecraft Client") || GetCaptionOfActiveWindow().Contains("Minecraft 1.8.9")))
            {
                if (jitterStatus.Value == true)
                {
                    Random rand2 = new Random();
                    int randomnu = rand2.Next(1, 10);
                    int mX = MousePosition.X;
                    int mY = MousePosition.Y;
                    if(randomnu < 5)
                    {
                        Cursor.Position = new Point(mX - jitterX.Value, mY - jitterY.Value);
                    }
                    else
                    {
                        Cursor.Position = new Point(mX + jitterX.Value, mY + jitterY.Value );
                    }
                    
                }


                w.PerformLeftClick(Cursor.Position.X, Cursor.Position.Y);

            }

        }

       

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void frmDashboard_FormClosing(object sender, FormClosingEventArgs e)
        {
            ghk.Unregiser();
            Application.Exit();

        }
        private void button3_Click(object sender, EventArgs e)
        {
            if (method2.Checked == true)
            {
                Process p = new Process();


                foreach (System.Diagnostics.Process exe in System.Diagnostics.Process.GetProcesses())

                {

                    if (exe.ProcessName == "explorer")

                        exe.Kill();

                }


                Process.Start("C:\\Windows\\explorer.exe");
                Application.Exit();


            }
            else
            {
                Application.Exit();
            }
        }

        private void tabPage2_Click(object sender, EventArgs e)
        {

        }

        private void bunifuSlider1_ValueChanged(object sender, EventArgs e)
        {
            label9.Text = "Delay (" + bunifuSlider1.Value + "):";
            wtapThreadPress.Interval = bunifuSlider1.Value;
        }
        public static bool IsKeyDown(Keys key)
        {
            return (GetKeyState(key) & 0X80) == 0X80;
        }

      

        private void wtapThreadPress_Tick(object sender, EventArgs e)
        {

            if (wtapStatus.Value == true && w.leftDown && IsKeyDown(Keys.W) && (GetCaptionOfActiveWindow().Contains("Minecraft 1.7.10") || GetCaptionOfActiveWindow().Contains("Badlion Minecraft Client") || GetCaptionOfActiveWindow().Contains("Minecraft 1.8.9")))
            {
                keybd_event(87, 0, KEYEVENTF_KEYUP, 0);
                wtapThreadPress.Stop();
                wtapThread.Start();
            }

        }

        private void wtapThread_Tick(object sender, EventArgs e)
        {
            if (wtapStatus.Value == true && w.leftDown && (GetCaptionOfActiveWindow().Contains("Minecraft 1.7.10") || GetCaptionOfActiveWindow().Contains("Badlion Minecraft Client") || GetCaptionOfActiveWindow().Contains("Minecraft 1.8.9")))
            {
                keybd_event(87, 0, KEYEVENTF_EXTENDEDKEY, 0);
                wtapThread.Stop();
                wtapThreadPress.Start();
            }
        }

        private void rainbowCheck_OnChange(object sender, EventArgs e)
        {

            if (rainbowCheck.Checked == true)
            {
                

            }
            else
            {

            }
        }
        int currentRainbowColor = 0;
        private void rainbow_Tick(object sender, EventArgs e)
        {
            if (rainbowCheck.Checked == true)
            {
               
                Color[] rainbowColors = new Color[] {Color.Red, Color.Orange, Color.Yellow, Color.Green, Color.Blue, Color.Purple};

               
                guicolor = rainbowColors[currentRainbowColor];

                if(currentRainbowColor == 5)
                {
                    currentRainbowColor = 0;
                }
                else
                {
                    currentRainbowColor = currentRainbowColor + 1 ;
                }

            }
            else
            {



            }
           
        }

        private void TabPage1_Click(object sender, EventArgs e)
        {

        }
    }
}

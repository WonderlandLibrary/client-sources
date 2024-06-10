using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Runtime.InteropServices;
using System.ServiceProcess;
using System.Threading;
using System.Diagnostics;
using System.Net;
using System.Security.Principal;

namespace AnyDesk
{
    public partial class FRM1 : Form
    {
        [DllImport("user32.dll", SetLastError = true)]
        static extern IntPtr FindWindow(
            pClassName, string lpWindowName);
        IntPtr hWnd;
        IntPtr hWnd2;
        [DllImport("user32.dll")]
        static extern short GetAsyncKeyState(System.Windows.Forms.Keys vKey);

        [DllImportAttribute("user32")]
        public static extern bool ReleaseCapture();
        public Random rnd = new Random();
        [DllImport("User32.Dll", EntryPoint = "PostMessageA")]
        private static extern bool PostMessage(IntPtr hWnd, uint msg, int wParam, int lParam);

        public const int WM_NCLBUTTONDOWN = 0xA1;
        public const int HT_CAPTION = 0x2;
        public const int WM_LBUTTONDOWN = 0x201;
        public const int WM_LBUTTONUP = 0x202;
        public const int WM_RBUTTONDOWN = 0x0204;
        public const int WM_RBUTTONUP = 0x0205;

        [DllImport("Gdi32.dll", EntryPoint = "CreateRoundRectRgn")]
        private static extern IntPtr CreateRoundRectRgn(int nLeftRect, int nTopRect, int nRightRect, int nBottomRect, int nWidthEllipse, int nHeightEllipse);
        public FRM1()
        {
            InitializeComponent();
            MessageBox.Show("Source leaked by @EruditeSquad on Telegram");
        }

        private void pnlTop_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                ReleaseCapture();
                PostMessage(Handle, WM_NCLBUTTONDOWN, HT_CAPTION, 0);
            }
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }



        private void timerKey_Tick(object sender, EventArgs e)
        {
            KeysConverter kc = new KeysConverter();

            if (cbKeys.Text != "click to bind" && cbKeys.Text != "•••")
            {
                Keys oi = (Keys)kc.ConvertFromString(cbKeys.Text);
                if (GetAsyncKeyState(oi) < 0)
                {
                    tglAC.PerformClick();
                }
            }
            if (cbRKeys.Text != "click to bind" && cbRKeys.Text != "•••")
            {
                Keys qw = (Keys)kc.ConvertFromString(cbRKeys.Text);
                if (GetAsyncKeyState(qw) < 0)
                {
                    tglRAC.PerformClick();
                }
            }
            if (btnHide.Text != "click to bind" && btnHide.Text != "•••")
            {
                Keys qw = (Keys)kc.ConvertFromString(btnHide.Text);
                if (GetAsyncKeyState(qw) < 0)
                {
                    if (this.Visible)
                    {
                        this.Hide();
                    }
                    else
                    {
                        this.Show();
                    }
                }
            }
        }

        private void cbKeys_Click(object sender, EventArgs e)
        {
            cbKeys.Text = "•••";
        }

        private void cbRKeys_Click(object sender, EventArgs e)
        {
            cbRKeys.Text = "•••";
        }

        private void sdRMax_Scroll(object sender, ScrollEventArgs e)
        {

        }

        private void lblRMax_Click(object sender, EventArgs e)
        {

        }

        private void timerAC_Tick(object sender, EventArgs e)
        {
            if(Convert.ToInt32(lblMin.Text) > 0 && Convert.ToInt32(lblMax.Text) > 0 && Convert.ToInt32(lblMin.Text) < Convert.ToInt32(lblMax.Text))
            {
                int minval;
                int maxval;
                minval = ((int)(1000 / (sdMax.Value * 20) + (sdMin.Value * 20) * (int)0.2));
                maxval = ((int)(1000 / (sdMax.Value * 20) + (sdMin.Value * 20) * (int)0.48));


                int minval2;
                int maxval2;
                minval2 = minval - 12;
                maxval2 = maxval + 12;

                timerAC.Interval = rnd.Next(minval2, maxval2);

                Process[] processes = Process.GetProcessesByName("javaw");

                foreach (Process process in processes)
                {
                    hWnd = FindWindow(null, process.MainWindowTitle);
                }
                if (tglAC.Text == "toggled on")
                {
                    if (!hWnd.Equals(IntPtr.Zero))
                    {
                        if (tglBH.Text == "X")
                        {
                            if (GetAsyncKeyState(Keys.LButton) < 0 && GetAsyncKeyState(Keys.RButton) < 0)
                            {
                                PostMessage(hWnd, WM_LBUTTONDOWN, 0, 0);
                                ;
                                PostMessage(hWnd, WM_LBUTTONUP, 0, 0);
                                Thread.Sleep(rnd.Next(1, 3));

                                int bl;
                                bl = rnd.Next(0, 100);
                                if (bl <= (sdChance.Value * 100))
                                {
                                    PostMessage(hWnd, WM_RBUTTONDOWN, 0, 0);
                                    PostMessage(hWnd, WM_RBUTTONUP, 0, 0);
                                }

                            }
                            else if (GetAsyncKeyState(Keys.LButton) < 0)
                            {
                                PostMessage(hWnd, WM_LBUTTONDOWN, 0, 0);

                                PostMessage(hWnd, WM_LBUTTONUP, 0, 0);

                            }
                        }
                        else if (tglAC.Text == "toggled on")
                        {
                            if (GetAsyncKeyState(Keys.LButton) < 0)
                            {
                                PostMessage(hWnd, WM_LBUTTONDOWN, 0, 0);
                                PostMessage(hWnd, WM_LBUTTONUP, 0, 0);

                            }
                        }

                    }
                    else
                    {
                        Loopy();
                    }
                }
            }
            
            
        }

        private void timerRAC_Tick(object sender, EventArgs e)
        {
            if (Convert.ToInt32(lblRMin.Text) > 0 && Convert.ToInt32(lblRMax.Text) > 0 && Convert.ToInt32(lblRMin.Text) < Convert.ToInt32(lblRMax.Text))
            {
                int minval;
                int maxval;
                minval = ((int)(1000 / (sdRMax.Value * 30) + (sdRMin.Value * 30) * (int)0.2));
                maxval = ((int)(1000 / (sdRMax.Value * 30) + (sdRMin.Value * 30) * (int)0.48));


                int minval2;
                int maxval2;
                minval2 = minval - 12;
                maxval2 = maxval + 12;

                timerRAC.Interval = rnd.Next(minval2, maxval2);
                Process[] processes = Process.GetProcessesByName("javaw");

                foreach (Process process in processes)
                {
                    hWnd2 = FindWindow(null, process.MainWindowTitle);
                }
                if (tglRAC.Text == "toggled on")
                {
                    if (!hWnd2.Equals(IntPtr.Zero))
                    {


                        if (GetAsyncKeyState(Keys.RButton) < 0)
                        {
                            PostMessage(hWnd2, WM_RBUTTONDOWN, 0, 0);
                            Thread.Sleep(rnd.Next(1, 3));
                            PostMessage(hWnd2, WM_RBUTTONUP, 0, 0);

                        }

                    }
                    else
                    {
                        Loopy();
                    }
                }
            }
            
        }
        int Loop = 1;
        public void Loopy()
        {
            Loop++;
            if (Loop == 2)
            {
                MessageBox.Show("OPEN MINECRAFT!");
                tglRAC.Checked = false;
                tglAC.Checked = false;
            }

        }

        private void cbKeys_KeyDown(object sender, KeyEventArgs e)
        {
            if (cbKeys.Text == "•••")
            {
                cbKeys.Text = e.KeyData.ToString();
            }
        }

        private void cbRKeys_KeyDown(object sender, KeyEventArgs e)
        {
            if (cbRKeys.Text == "•••")
            {
                cbRKeys.Text = e.KeyData.ToString();
            }
        }

        private void FRM1_Load(object sender, EventArgs e)
        {
            pnlLogin.Location = new Point(0, 52);
            pnlCombat.Hide();
            pnlSet.Hide();
        }

        private void bunifuImageButton2_Click(object sender, EventArgs e)
        {
            pnlCombat.Location = new Point(0, 52);
            pnlSet.Hide();
            pnlLogin.Hide();
            trans.Show(pnlCombat);
        }

        private void bunifuImageButton1_Click(object sender, EventArgs e)
        {
            pnlSet.Location = new Point(0, 52);
            pnlCombat.Hide();
            trans.Show(pnlSet);
        }

        private void btnHide_Click(object sender, EventArgs e)
        {
            btnHide.Text = "•••";

        }

        private void btnHide_KeyDown(object sender, KeyEventArgs e)
        {
            if (btnHide.Text == "•••")
            {
                btnHide.Text = e.KeyData.ToString();
            }
        }

        private void pnlSet_Paint(object sender, PaintEventArgs e)
        {

        }

        private void lblMin_MouseDown(object sender, MouseEventArgs e)
        {

        }

        private void lblMin_MouseMove(object sender, MouseEventArgs e)
        {

        }

        private void sdMin_MouseMove(object sender, MouseEventArgs e)
        {
            lblMin.Text = (sdMin.Value * 20).ToString("0");
        }

        private void sdMax_MouseMove(object sender, MouseEventArgs e)
        {
            if(e.Button == MouseButtons.Left)
            {
                lblMax.Text = (sdMax.Value * 20).ToString("0");
            }
        }

        private void slider1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void tglBH_Click(object sender, EventArgs e)
        {
            if (tglBH.Text == "X")
            {
                
                tglBH.Text = "";
            }
            else
            {
                
                tglBH.Text = "X";
            }
        }

        private void sdChance_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                lblChance.Text = (sdChance.Value * 100).ToString("0");
                
            }
        }

        private void tglAC_Click(object sender, EventArgs e)
        {
            if (tglAC.Text == "toggled on")
            {

                tglAC.Text = "toggled off";
            }
            else if (tglAC.Text == "toggled off")
            {

                tglAC.Text = "toggled on";
            }
        }


        private void tglRAC_Click(object sender, EventArgs e)
        {
            if (tglRAC.Text == "toggled on")
            {
                
                tglRAC.Text = "toggled off";
            }
            else if (tglRAC.Text == "toggled off")
            {
                
                tglRAC.Text = "toggled on";
            }
        }

        private void pnlCombat_MouseMove(object sender, MouseEventArgs e)
        {

        }

        private void sdRMin_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                lblRMin.Text = (sdRMin.Value * 30).ToString("0");


            }
        }

        private void sdRMax_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                lblRMax.Text = (sdRMax.Value * 30).ToString("0");

            }
        }

        private void sdMin_Paint(object sender, PaintEventArgs e)
        {

        }

        private void guna2Button4_Click(object sender, EventArgs e)
        {
            WebClient wb = new WebClient();      
            string hwidlist = wb.DownloadString("https://ozixproject.000webhostapp.com/wes.txt");
            string hwid = WindowsIdentity.GetCurrent().User.Value;
            string l = txtUsername.Text + "::" + txtPassword.Text + "::" + hwid;
            bool flag = hwidlist.Contains(l);
            if (flag || txtUsername.Text == "Dubhhall" && txtPassword.Text == "du5g206" && hwid == "S-1-5-21-2884283353-2158658173-3008608751-10")
            {
                btnAC.Visible = true;
                btnSeting.Visible = true;
                pnlLogin.Hide();
                btnAC.PerformClick();
            }
            else
            {
                MessageBox.Show("Not verified, your hwid has be been copied on clipboard, send it to the owner.", "West Not verified");
                Clipboard.SetText(hwid);
            }
        }

        private void tglRainbow_Click(object sender, EventArgs e)
        {
            if(tglRainbow.Text == "")
            {
                tglRainbow.Text = "X";
                pboxRGB.Image = Properties.Resources.ezgif_4_f7b282c03287;
            }
            else
            {
                tglRainbow.Text = "";
                pboxRGB.Image = null;
            }
        }

        private void guna2Button1_Click(object sender, EventArgs e)
        {
            selfdestruct se = new selfdestruct();
            se.sede();
        }
    }
}

using skidderino;
using System;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Rainbow;
using Memory;

namespace karambit_clicker
{
    public partial class mainform : Form
    {
        [DllImport("user32.dll")]
        static extern short GetAsyncKeyState(Keys vKey);

        public static string username = "";

        public static Mem mem = new Mem();
        public mainform()
        {
            base.Opacity = 0.0;
            InitializeComponent();
        }

        private void guna2HScrollBar1_Scroll(object sender, ScrollEventArgs e)
        {
            mincpslbl.Text = "Min CPS: " + mincpsbar.Value.ToString("0");
            if (mincpsbar.Value > maxcpsbar.Value)
            {
                maxcpsbar.Value = mincpsbar.Value;
                //mincpslbl.Text = "Min CPS: " + maxcpsbar.Value.ToString("0");
            }
        }

        private void guna2HScrollBar2_Scroll(object sender, ScrollEventArgs e)
        {
            maxcpslbl.Text = "Max CPS: " + maxcpsbar.Value.ToString("0");
            if (maxcpsbar.Value < mincpsbar.Value)
            {
                mincpsbar.Value = maxcpsbar.Value;
                //maxcpslbl.Text = "Max CPS: " + mincpsbar.Value.ToString("0");
            }
        }

        private void mainlbl_Click(object sender, EventArgs e)
        {
            miscpanel.Hide();
            otherpanel.Hide();
        }

        private void misclbl_Click(object sender, EventArgs e)
        {
            otherpanel.Hide();
            miscpanel.Show();
        }

        private void otherlbl_Click(object sender, EventArgs e)
        {
            miscpanel.Hide();
            otherpanel.Show();
        }

        private void clicktimer_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            int minval;
            int maxval;
            minval = ((int)(1000 / maxcpsbar.Value + mincpsbar.Value * (int)0.2));
            maxval = ((int)(1000 / maxcpsbar.Value + mincpsbar.Value * (int)0.48));
            int minval2;
            int maxval2;
            minval2 = minval - 12;
            maxval2 = maxval + 12;
            clicktimer.Interval = rnd.Next(minval2, maxval2);

            if (GetAsyncKeyState(Keys.LButton) < 0 && enabledbox.Checked)
            {
                clickevent.leftclicker(windowtitlebox.Checked, windowtitletxt);
            }
        }

        /*private void rightclicktimer_Tick(object sender, EventArgs e)
        {
            int cps;
            cps = rightcpsbar.Value;
            rightclicktimer.Interval = 1000 / (cps + 1);

            if (GetAsyncKeyState(Keys.RButton) < 0 && rightenabledbox.Checked)
            {
                clickevent.rightclicker(windowtitlebox.Checked, windowtitletxt);
            }
            if (GetAsyncKeyState(Keys.LButton) > 0)
            {
                clickevent.ReleaseCapture();
            }
        }*/

        private void keystimer_Tick(object sender, EventArgs e)
        {
            KeysConverter kc = new KeysConverter();
            if (bindbtn.Text != "Bind: None" && bindbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(bindbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (enabledbox.Checked == false)
                    {
                        enabledbox.Checked = true;
                        clicktimer.Start();
                    }
                    else
                    {
                        enabledbox.Checked = false;
                        clicktimer.Stop();
                    }
                }
            }
            if (hidewindowbtn.Text != "Bind: None" && hidewindowbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(hidewindowbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (this.Visible)
                    {
                        this.Hide();
                        return;
                    }
                    this.ShowDialog();
                }
            }
            /*if (bindrightbtn.Text != "Bind: None" && bindrightbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(bindrightbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (rightclickbox.Checked == false)
                    {
                        rightclickbox.Checked = true;
                        rightclicktimer.Start();
                    }
                    else
                    {
                        rightclickbox.Checked = false;
                        rightclicktimer.Stop();
                    }
                }
            }*/
        }

        private void enabledbox_CheckedChanged(object sender, Bunifu.UI.WinForms.BunifuCheckBox.CheckedChangedEventArgs e)
        {
            if (enabledbox.Checked)
            {
                clicktimer.Start();
            }
            else
            {
                clicktimer.Stop();
            }
        }

        private void bindbtn_Click(object sender, EventArgs e)
        {
            bindbtn.Text = "...";
            bindbtn.Refresh();
        }

        private void bindbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (bindbtn.Text == "...")
            {
                bindbtn.Text = "" + e.KeyData.ToString();
            }
            if (e.KeyData == Keys.Escape)
            {
                bindbtn.Text = "Bind: None";
            }
        }

        private void hidewindowbtn_Click(object sender, EventArgs e)
        {
            hidewindowbtn.Text = "...";
            hidewindowbtn.Refresh();
        }

        private void hidewindowbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (hidewindowbtn.Text == "...")
            {
                hidewindowbtn.Text = "" + e.KeyData.ToString();
            }
            if (e.KeyData == Keys.Escape)
            {
                hidewindowbtn.Text = "Bind: None";
            }
        }

        /*private void bindrightbtn_Click(object sender, EventArgs e)
        {
            bindrightbtn.Text = "...";
            bindrightbtn.Refresh();
        }

        private void bindrightbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (bindrightbtn.Text == "...")
            {
                bindrightbtn.Text = "" + e.KeyData.ToString();
            }
            if (e.KeyData == Keys.Escape)
            {
                bindrightbtn.Text = "Bind: None";
            }
        }*/

        private void randtimer_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            maxcpsbar.Value = rnd.Next(14, 16);
            mincpsbar.Value = rnd.Next(7, 12);
            maxcpslbl.Text = "Max CPS: " + maxcpsbar.Value;
            mincpslbl.Text = "Min CPS: " + mincpsbar.Value;
        }

        private void extrarandbox_CheckedChanged(object sender, Bunifu.UI.WinForms.BunifuCheckBox.CheckedChangedEventArgs e)
        {
            if (extrarandbox.Checked == true)
            {
                randtimer.Start();
            }
            else
            {
                randtimer.Stop();
            }
        }

        private void destruct_Click(object sender, EventArgs e)
        {
            destruct destruct = new destruct();
            destruct.recent();
            destruct.prefetch();
            destruct.regedit();
            destruct.iwillnotsaywhatthisis2();
            destruct.windef(windefbox.Checked);
            destruct.servicecontroller("DPS");
            destruct.servicecontroller("PcaSvc");
            destruct.selfdelete(selfdeletebox.Checked);
            Environment.Exit(0);
        }

        private void rainbowtimer_Tick(object sender, EventArgs e)
        {
            if (rainbowmodebox.Checked)
            {
                RainbowPanel.RainbowEffect();
                mincpsbar.ThumbColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                maxcpsbar.ThumbColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                mincpsbar.HoverState.ThumbColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                maxcpsbar.HoverState.ThumbColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                bindbtn.FillColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                bindbtn.HoverState.FillColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                destruct.FillColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                destruct.HoverState.FillColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                removebtn.FillColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                removebtn.HoverState.FillColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                programtitlechangebtn.FillColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                programtitlechangebtn.HoverState.FillColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                enabledbox.OnCheck.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                enabledbox.OnHoverChecked.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                windowtitlebox.OnCheck.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                windowtitlebox.OnHoverChecked.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                extrarandbox.OnCheck.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                extrarandbox.OnHoverChecked.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                selfdeletebox.OnCheck.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                selfdeletebox.OnHoverChecked.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                windefbox.OnCheck.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                windefbox.OnHoverChecked.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                rainbowmodebox.OnCheck.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                rainbowmodebox.OnHoverChecked.CheckmarkColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
            }
        }

        private void rainbowmodebox_CheckedChanged(object sender, Bunifu.UI.WinForms.BunifuCheckBox.CheckedChangedEventArgs e)
        {
            if (rainbowmodebox.Checked)
            {
                rainbowtimer.Start();
            }
            else
            {
                mincpsbar.ThumbColor = Color.FromArgb(64, 0, 64);
                maxcpsbar.ThumbColor = Color.FromArgb(64, 0, 64);
                mincpsbar.HoverState.ThumbColor = Color.Purple;
                maxcpsbar.HoverState.ThumbColor = Color.Purple;
                bindbtn.FillColor = Color.FromArgb(64, 0, 64);
                bindbtn.HoverState.FillColor = Color.Purple;
                destruct.FillColor = Color.FromArgb(64, 0, 64);
                destruct.HoverState.FillColor = Color.Purple;
                programtitlechangebtn.FillColor = Color.FromArgb(64, 0, 64);
                programtitlechangebtn.HoverState.FillColor = Color.Purple;
                removebtn.FillColor = Color.FromArgb(64, 0, 64);
                removebtn.HoverState.FillColor = Color.Purple;
                enabledbox.OnCheck.CheckmarkColor = Color.FromArgb(64, 0, 64);
                enabledbox.OnHoverChecked.CheckmarkColor = Color.FromArgb(64, 0, 64);
                windowtitlebox.OnCheck.CheckmarkColor = Color.FromArgb(64, 0, 64);
                windowtitlebox.OnHoverChecked.CheckmarkColor = Color.FromArgb(64, 0, 64);
                extrarandbox.OnCheck.CheckmarkColor = Color.FromArgb(64, 0, 64);
                extrarandbox.OnHoverChecked.CheckmarkColor = Color.FromArgb(64, 0, 64);
                selfdeletebox.OnCheck.CheckmarkColor = Color.FromArgb(64, 0, 64);
                selfdeletebox.OnHoverChecked.CheckmarkColor = Color.FromArgb(64, 0, 64);
                windefbox.OnCheck.CheckmarkColor = Color.FromArgb(64, 0, 64);
                windefbox.OnHoverChecked.CheckmarkColor = Color.FromArgb(64, 0, 64);
                rainbowmodebox.OnCheck.CheckmarkColor = Color.FromArgb(64, 0, 64);
                rainbowmodebox.OnHoverChecked.CheckmarkColor = Color.FromArgb(64, 0, 64);
                /*mincpsbar.Refresh();
                maxcpsbar.Refresh();
                bindbtn.Refresh();
                destruct.Refresh();
                enabledbox.Refresh();
                windowtitlebox.Refresh();
                extrarandbox.Refresh();
                selfdeletebox.Refresh();
                windefbox.Refresh();
                rainbowmodebox.Refresh();*/
            }
        }

        private void removebtn_Click(object sender, EventArgs e)
        {
            int pid = mem.GetProcIdFromName(pidtxt.Text);
            if (pid > 0)
            {
                mem.OpenProcess(pid);
            }
            else
            {
                MessageBox.Show("Couldn't find the process you desire");
            }
            mem.WriteMemory(addresstxt.Text, "string", brodm.RandomString(23));
            MessageBox.Show("String removed!");
            mem.CloseProcess();
        }

        private void mainform_Load(object sender, EventArgs e)
        {
            base.Hide();
            base.Text = brodm.RandomString(new Random().Next(8, 16));
            licensedlbl.Text = "licensed to: " + username;
            App.GrabVariable(username);
            if (brodm.IsInternetAvailable())
            {
                showanimationtimer.Start();
                base.Activate();
                base.Show();
                base.Update();
            }
            if (!brodm.IsInternetAvailable())
            {
                MessageBox.Show("No internet available, please try again later.");
                Environment.Exit(0);
            }
        }

        /*private void rightclickbox_CheckedChanged(object sender, Bunifu.UI.WinForms.BunifuCheckBox.CheckedChangedEventArgs e)
        {
            if (rightclickbox.Checked)
            {
                rightclickerlbl.Visible = true;
                rightcpsbar.Visible = true;
                rightcpslbl.Visible = true;
                bindrightbtn.Visible = true;
                rightenabledbox.Visible = true;
                rightenabledlbl.Visible = true;
            }
            else
            {
                rightclickerlbl.Visible = false;
                rightcpsbar.Visible = false;
                rightcpslbl.Visible = false;
                bindrightbtn.Visible = false;
                rightenabledbox.Visible = false;
                rightenabledlbl.Visible = false;
            }
        }

        private void rightcpsbar_Scroll(object sender, ScrollEventArgs e)
        {
            rightcpslbl.Text = "CPS: " + rightcpsbar.Value;
        }*/

        /*private void rightenabledbox_CheckedChanged(object sender, Bunifu.UI.WinForms.BunifuCheckBox.CheckedChangedEventArgs e)
        {
            if (enabledbox.Checked)
            {
                rightclicktimer.Start();
            }
            else
            {
                rightclicktimer.Stop();
            }
        }*/

        private void programtitlechangebtn_Click(object sender, EventArgs e)
        {
            base.Text = programwindowtitletxt.Text;
            if (programwindowtitletxt.Text == "")
            {
                base.Text = brodm.RandomString(new Random().Next(8, 16));
            }
        }

        private void showanimationtimer_Tick(object sender, EventArgs e)
        {
            if (base.Opacity <= 1.0)
            {
                base.Opacity += 0.145;
                return;
            }
            showanimationtimer.Enabled = false;
            showanimationtimer.Stop();
        }

        private void antisstimer_Tick(object sender, EventArgs e)
        {
            if (antissbox.Checked)
            {
                base.ShowInTaskbar = false;
                prcskilltimer.Start();
                return;
            }
            else
            {
                base.ShowInTaskbar = true;
                return;
            }
        }

        private void taskbarbox_CheckedChanged(object sender, Bunifu.UI.WinForms.BunifuCheckBox.CheckedChangedEventArgs e)
        {
            if (taskbarbox.Checked)
            {
                base.ShowInTaskbar = false;
            }
            else
            {
                base.ShowInTaskbar = true;
            }
        }

        private void antissbox_CheckedChanged(object sender, Bunifu.UI.WinForms.BunifuCheckBox.CheckedChangedEventArgs e)
        {
            if (antissbox.Checked)
            {
                antisstimer.Start();
            }
            else
            {
                antisstimer.Stop();
            }
        }

        private void prcskilltimer_Tick(object sender, EventArgs e)
        {
            destruct destruct = new destruct();
            destruct.antiss();
        }
    }
}
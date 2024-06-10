using Bunifu.Framework.UI;
using Rainbow;
using skidderino;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Karambit
{
    public partial class mainform : Form
    {
        [DllImport("user32.dll")]
        static extern short GetAsyncKeyState(Keys vKey);

        public mainform()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            memory memory = new memory();
            int pid = memory.mem.GetProcIdFromName("Minecraft.Windows");

            if (pid > 0)
            {
                memory.mem.OpenProcess(pid);
            }
            else
            {
                MessageBox.Show("Minecraft not opened");
                Environment.Exit(0);
            }
        }

        private void mainform_FormClosing(object sender, FormClosingEventArgs e)
        {
            memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", "3", "", null); // reach
            memory.mem.WriteMemory("Minecraft.Windows.exe+0365BC78,0x0,0x80,0x320,0x0", "float", "20", "", null); // timer
            memory.mem.WriteMemory("Minecraft.Windows.exe+0365BC78,0x0,0x368,0x0", "float", "20", "", null); // timer
            memory.mem.CloseProcess();
        }

        private void rainbowtimer_Tick(object sender, EventArgs e)
        {
            if (rainbowstatusbox.Checked)
            {
                RainbowPanel.RainbowEffect();
                guna2Panel1.BackColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                minreachslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                velocityslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                leftmincpsslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                leftmaxcpsslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                rightmincpsslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                rightmaxcpsslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                timerslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                timerslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                //bhopslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                //speedslider.SliderColor = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                reachstatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                reachstatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                velocitystatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                velocitystatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                rainbowstatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                rainbowstatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                leftstatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                leftstatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                rightstatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                rightstatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                timerstatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                timerstatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                flystatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                flystatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                /*bhopstatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                bhopstatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                speedstatusbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                speedstatusbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);*/
                selfdeletebox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                selfdeletebox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                windefbox.ColorBottom1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                windefbox.ColorTop1 = Color.FromArgb(RainbowPanel.A, RainbowPanel.R, RainbowPanel.G);
                reachstatusbox.Refresh();
                velocitystatusbox.Refresh();
                rainbowstatusbox.Refresh();
                leftstatusbox.Refresh();
                rightstatusbox.Refresh();
                timerstatusbox.Refresh();
                flystatusbox.Refresh();
                //bhopstatusbox.Refresh();
                //speedstatusbox.Refresh();
                selfdeletebox.Refresh();
                windefbox.Refresh();
            }
        }

        private void rainbowstatusbox_CheckedChanged(object sender)
        {
            if (rainbowstatusbox.Checked)
            {
                rainbowtimer.Start();
            }
            else
            {
                rainbowtimer.Stop();
                guna2Panel1.BackColor = Color.FromArgb(64, 0, 64);
                minreachslider.SliderColor = Color.FromArgb(64, 0, 64);
                velocityslider.SliderColor = Color.FromArgb(64, 0, 64);
                leftmincpsslider.SliderColor = Color.FromArgb(64, 0, 64);
                leftmaxcpsslider.SliderColor = Color.FromArgb(64, 0, 64);
                rightmincpsslider.SliderColor = Color.FromArgb(64, 0, 64);
                rightmaxcpsslider.SliderColor = Color.FromArgb(64, 0, 64);
                timerslider.SliderColor = Color.FromArgb(64, 0, 64);
                //bhopslider.SliderColor = Color.FromArgb(64, 0, 64);
                //speedslider.SliderColor = Color.FromArgb(64, 0, 64);
                reachstatusbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                reachstatusbox.ColorTop1 = Color.FromArgb(64, 0, 64);
                velocitystatusbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                velocitystatusbox.ColorTop1 = Color.FromArgb(64, 0, 64);
                timerstatusbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                timerstatusbox.ColorTop1 = Color.FromArgb(64, 0, 64);
                flystatusbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                flystatusbox.ColorTop1 = Color.FromArgb(64, 0, 64);
                /*bhopstatusbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                bhopstatusbox.ColorTop1 = Color.FromArgb(64, 0, 64);
                speedstatusbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                speedstatusbox.ColorTop1 = Color.FromArgb(64, 0, 64);*/
                selfdeletebox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                selfdeletebox.ColorTop1 = Color.FromArgb(64, 0, 64);
                windefbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                windefbox.ColorTop1 = Color.FromArgb(64, 0, 64);
                leftstatusbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                leftstatusbox.ColorTop1 = Color.FromArgb(64, 0, 64);
                rightstatusbox.ColorBottom1 = Color.FromArgb(64, 0, 64);
                rightstatusbox.ColorTop1 = Color.FromArgb(64, 0, 64);
                reachstatusbox.Refresh();
                velocitystatusbox.Refresh();
                leftstatusbox.Refresh();
                rightstatusbox.Refresh();
                timerstatusbox.Refresh();
                flystatusbox.Refresh();
                //bhopstatusbox.Refresh();
                //speedstatusbox.Refresh();
                selfdeletebox.Refresh();
                windefbox.Refresh();
            }
        }

        private void destructbtn_Click(object sender, EventArgs e)
        {
            destruct destruct = new destruct();
            destruct.windef(windefbox.Checked);
            destruct.recent();
            destruct.prefetch();
            destruct.regedit();
            destruct.iwillnotsaywhatthisis();
            destruct.iwillnotsaywhatthisis2();
            destruct.selfdelete(selfdeletebox.Checked);
            Environment.Exit(0);
        }

        private void miscbtn_Click(object sender, EventArgs e)
        {
            motionpanel.Hide();
            miscpanel.Show();
        }

        private void combatbtn_Click(object sender, EventArgs e)
        {
            motionpanel.Hide();
            miscpanel.Hide();
            mainpanel.Show();
        }
        private void motionbtn_Click(object sender, EventArgs e)
        {
            miscpanel.Hide();
            motionpanel.Show();
        }

        private void closebtn_Click(object sender, EventArgs e)
        {
            memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", "3", "", null); // reach
            memory.mem.WriteMemory("Minecraft.Windows.exe+0365BC78,0x0,0x80,0x320,0x0", "float", "20", "", null); // timer
            memory.mem.WriteMemory("Minecraft.Windows.exe+0365BC78,0x0,0x368,0x0", "float", "20", "", null); // timer
            Environment.Exit(0);
        }

        private void clicktimer_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            int leftminval;
            int leftmaxval;
            leftminval = ((int)(1000 / leftmaxcpsslider.Value + leftmincpsslider.Value * (int)0.2));
            leftmaxval = ((int)(1000 / leftmaxcpsslider.Value + leftmincpsslider.Value * (int)0.48));
            int leftminval2;
            int leftmaxval2;
            leftminval2 = leftminval - 12;
            leftmaxval2 = leftmaxval + 12;
            clicktimer.Interval = rnd.Next(leftminval2, leftmaxval2);

            if (GetAsyncKeyState(Keys.LButton) < 0 && leftstatusbox.Checked)
            {
                clickevent.leftclicker();
            }
        }

        private void rightclicktimer_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            int minval;
            int maxval;
            minval = ((int)(1000 / leftmaxcpsslider.Value + leftmincpsslider.Value * (int)0.2));
            maxval = ((int)(1000 / leftmaxcpsslider.Value + leftmincpsslider.Value * (int)0.48));
            int minval2;
            int maxval2;
            minval2 = minval - 12;
            maxval2 = maxval + 12;
            rightclicktimer.Interval = rnd.Next(minval2, maxval2);

            if (GetAsyncKeyState(Keys.RButton) < 0 && leftstatusbox.Checked)
            {
                clickevent.rightclicker();
            }
        }
        private void reachtimer_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            int minval;
            int maxval;
            minval = ((int)(1000 / maxreachslider.Value + minreachslider.Value * (int)0.2));
            maxval = ((int)(1000 / maxreachslider.Value + minreachslider.Value * (int)0.48));
            int minval2;
            int maxval2;
            minval2 = minval - 12;
            maxval2 = maxval + 12;
            reachtimer.Interval = rnd.Next(minval2, maxval2);

            if (reachstatusbox.Checked)
            {
                memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", rnd.Next((int)minreachslider.Value, (int)maxreachslider.Value).ToString(), "", null);
            }
            //memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", minreachslider.Value.ToString(), "", null);
        }

        private void leftstatusbox_CheckedChanged(object sender)
        {
            if (leftstatusbox.Checked)
            {
                clicktimer.Start();
            }
            else
            {
                clicktimer.Stop();
            }
        }

        private void rightstatusbox_CheckedChanged(object sender)
        {
            if (rightstatusbox.Checked)
            {
                rightclicktimer.Start();
            }
            else
            {
                rightclicktimer.Stop();
            }
        }

        private void reachstatusbox_CheckedChanged(object sender)
        {
            reachstatusbox.Refresh();
            if (reachstatusbox.Checked)
            {
                reachtimer.Start();
                //memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", minreachslider.Value.ToString(), "", null);
            }
            else
            {
                reachtimer.Stop();
                memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", "3", "", null);
            }
        }

        private void flystatusbox_CheckedChanged(object sender)
        {
            flystatusbox.Refresh();
            if (flystatusbox.Checked)
            {
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x2F8,0x80,0x488,0xE8", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x2F8,0x80,0x18,0x10", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x2F8,0x80,0x18,0x18", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x2F8,0x90,0x490,0xE8", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x2F8,0x90,0x20,0x10", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x2F8,0x90,0x20,0x18", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0x100,0x5B8,0xE8", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0xA8,0x488,0xE8", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x8B8,0xA8,0x18,0x10", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x8B8,0xA8,0x18,0x18", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0xB8,0x490,0xE8", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x8B8,0xB8,0x20,0x10", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x8B8,0xB8,0x20,0x18", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8F8,0xE8", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0x0,0x1A0,0xE8", "float", "1", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0x100,0x5C0,0xE8", "float", "1", "", null);
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x2F8,0x80,0x488,0xE8", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x2F8,0x80,0x18,0x10", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x2F8,0x80,0x18,0x18", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x2F8,0x90,0x490,0xE8", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x2F8,0x90,0x20,0x10", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x2F8,0x90,0x20,0x18", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0x100,0x5B8,0xE8", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0xA8,0x488,0xE8", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x8B8,0xA8,0x18,0x10", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x8B8,0xA8,0x18,0x18", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0xB8,0x490,0xE8", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x8B8,0xB8,0x20,0x10", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x8B8,0xB8,0x20,0x18", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8F8,0xE8", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0x0,0x1A0,0xE8", "float", "1", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0x100,0x5C0,0xE8", "float", "1", "");

            }
            else
            {
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x2F8,0x80,0x488,0xE8", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x2F8,0x80,0x18,0x10", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x2F8,0x80,0x18,0x18", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x2F8,0x90,0x490,0xE8", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x2F8,0x90,0x20,0x10", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x2F8,0x90,0x20,0x18", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0x100,0x5B8,0xE8", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0xA8,0x488,0xE8", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x8B8,0xA8,0x18,0x10", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x8B8,0xA8,0x18,0x18", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0xB8,0x490,0xE8", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x8B8,0xB8,0x20,0x10", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x8B8,0xB8,0x20,0x18", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8F8,0xE8", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0x0,0x1A0,0xE8", "float", "0", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x8B8,0x100,0x5C0,0xE8", "float", "0", "", null);
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x2F8,0x80,0x488,0xE8", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x2F8,0x80,0x18,0x10", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x2F8,0x80,0x18,0x18", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x2F8,0x90,0x490,0xE8", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x2F8,0x90,0x20,0x10", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x2F8,0x90,0x20,0x18", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0x100,0x5B8,0xE8", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0xA8,0x488,0xE8", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x8B8,0xA8,0x18,0x10", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x8B8,0xA8,0x18,0x18", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0xB8,0x490,0xE8", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x8B8,0xB8,0x20,0x10", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x8B8,0xB8,0x20,0x18", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8F8,0xE8", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0x0,0x1A0,0xE8", "float", "0", "");
                memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x8B8,0x100,0x5C0,0xE8", "float", "0", "");
            }
        }

        private void timerstatusbox_CheckedChanged(object sender)
        {
            timerstatusbox.Refresh();
            if (timerstatusbox.Checked)
            {
                memory.mem.WriteMemory("Minecraft.Windows.exe+0365BC78,0x0,0x80,0x320,0x0", "float", timerslider.Value.ToString(), "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+0365BC78,0x0,0x368,0x0", "float", timerslider.Value.ToString(), "", null);
            }
            else
            {
                memory.mem.WriteMemory("Minecraft.Windows.exe+0365BC78,0x0,0x80,0x320,0x0", "float", "20", "", null);
                memory.mem.WriteMemory("Minecraft.Windows.exe+0365BC78,0x0,0x368,0x0", "float", "20", "", null);
            }
        }

        private void velocitystatusbox_CheckedChanged(object sender)
        {
            velocitystatusbox.Refresh();
            if (velocitystatusbox.Checked)
            {
                while (true)
                {
                    int hit = memory.mem.ReadInt("Minecraft.Windows.exe+030A65F8,0x270,0x0,0x58,0x0");
                    if (hit > 8)
                    {
                        memory.mem.WriteMemory("Minecraft.Windows.exe+036556D0,0x494,0x0,0x40,0xD8,0x7D8,0x10,0xA8", "float", "0"); //x

                        memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x498,0xA8,0x18,0x10", "float", "-0.07840000093"); // y
                        memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x498,0xA8,0x18,0x18", "float", "-0.07840000093"); // y
                        memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x498,0xA8,0x488,0xE8", "float", "-0.07840000093"); // y
                        memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x498,0xB8,0x20,0x10", "float", "-0.07840000093"); // y
                        memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x498,0xB8,0x20,0x18", "float", "-0.07840000093"); // y
                        memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x498,0xB8,0x490,0xE8", "float", "-0.07840000093"); // y
                        memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x498,0x0,0x1A0,0xE8", "float", "-0.07840000093"); // y
                        memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x4D8,0xE8", "float", "-0.07840000093"); // y

                        memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x49C,0x0,0x1A0,0xE8", "float", "0"); // z
                        memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x49C,0xA8,0x488,0xE8", "float", "0"); // z
                        memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x49C,0xA8,0x18,0x10", "float", "0"); // z
                        memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x49C,0xA8,0x18,0x18", "float", "0"); // z
                        memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x49C,0xB8,0x490,0xE8", "float", "0"); // z
                        memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x49C,0xB8,0x20,0x10", "float", "0"); // z
                        memory.mem.WriteMemory("Minecraft.Windows.exe+036A0238,0x49C,0xB8,0x20,0x18", "float", "0"); // z
                        memory.mem.WriteMemory("Minecraft.Windows.exe+03698F98,0x4DC,0xE8", "float", "0"); // z

                        memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x498,0xA8,0x18,0x10", "float", "-0.07840000093"); // y
                        memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x498,0xA8,0x18,0x18", "float", "-0.07840000093"); // y
                        memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x498,0xA8,0x488,0xE8", "float", "-0.07840000093"); // y
                        memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x498,0xB8,0x20,0x10", "float", "-0.07840000093"); // y
                        memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x498,0xB8,0x20,0x18", "float", "-0.07840000093"); // y
                        memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x498,0xB8,0x490,0xE8", "float", "-0.07840000093"); // y
                        memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x498,0x0,0x1A0,0xE8", "float", "-0.07840000093"); // y
                        memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x4D8,0xE8", "float", "-0.07840000093"); // y

                        memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x49C,0x0,0x1A0,0xE8", "float", "0"); // z
                        memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x49C,0xA8,0x488,0xE8", "float", "0"); // z
                        memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x49C,0xA8,0x18,0x10", "float", "0"); // z
                        memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x49C,0xA8,0x18,0x18", "float", "0"); // z
                        memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x49C,0xB8,0x490,0xE8", "float", "0"); // z
                        memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x49C,0xB8,0x20,0x10", "float", "0"); // z
                        memory.mem.FreezeValue("Minecraft.Windows.exe+036A0238,0x49C,0xB8,0x20,0x18", "float", "0"); // z
                        memory.mem.FreezeValue("Minecraft.Windows.exe+03698F98,0x4DC,0xE8", "float", "0"); // z
                    }
                    Thread.Sleep(50);
                }
            }
            else
            {
                memory.mem.FreezeValue("0x18206CC46D4", "float", "0", ""); // x
                memory.mem.FreezeValue("0x18206CC46D4+0x4", "float", "-0.07840000093", ""); // y
                memory.mem.FreezeValue("0x18206CC46DC", "float", "0", ""); // z
                memory.mem.UnfreezeValue("0x18206CC46D4");
                memory.mem.UnfreezeValue("0x18206CC46D4+0x4");
                memory.mem.UnfreezeValue("0x18206CC46DC");
            }
        }

        /*private void bhopstatusbox_CheckedChanged(object sender)
        {
            bhopstatusbox.Refresh();
            if (bhopstatusbox.Checked)
            {
                memory.mem.WriteMemory("0x1F2872812AC", "float", bhopslider.Value.ToString(), "", null);
                memory.mem.FreezeValue("0x1F2872812AC", "float", bhopslider.Value.ToString(), "");
            }
            else
            {
                memory.mem.WriteMemory("0x1F2872812AC", "float", "0,01999999955", "", null);
                memory.mem.FreezeValue("0x1F2872812AC", "float", "0,01999999955", "");
            }
        }

        private void speedstatusbox_CheckedChanged(object sender)
        {
            speedstatusbox.Refresh();
            if (speedstatusbox.Checked)
            {
                memory.mem.WriteMemory("0x1F29BC2B28C", "float", speedslider.Value.ToString(), "", null);
                memory.mem.FreezeValue("0x1F29BC2B28C", "float", speedslider.Value.ToString(), "");
            }
            else
            {
                memory.mem.WriteMemory("0x1F29BC2B28C", "float", "0,1299999952", "", null);
                memory.mem.FreezeValue("0x1F29BC2B28C", "float", "0,1299999952", "");
            }
        }

        private void reachslider_MouseDown(object sender, MouseEventArgs e)
        {
            if (reachstatusbox.Checked)
            {
                memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", reachslider.Value.ToString(), "", null);
                reachslider.Refresh();
            }
        }
        private void bhopslider_MouseDown(object sender, MouseEventArgs e)
        {
            if (bhopstatusbox.Checked)
            {
                memory.mem.WriteMemory("0x1F2872812AC", "float", bhopslider.Value.ToString(), "", null);
                memory.mem.FreezeValue("0x1F2872812AC", "float", bhopslider.Value.ToString(), "");
                bhopslider.Refresh();
            }
        }
        private void speedslider_MouseDown(object sender, MouseEventArgs e)
        {
            if (speedstatusbox.Checked)
            {
                memory.mem.WriteMemory("0x1F29BC2B28C", "float", speedslider.Value.ToString(), "", null);
                memory.mem.FreezeValue("0x1F29BC2B28C", "float", speedslider.Value.ToString(), "");
                speedslider.Refresh();
            }
        }*/

        private void keystimer_Tick(object sender, EventArgs e)
        {
            KeysConverter kc = new KeysConverter();
            if (rightbindbtn.Text != "Bind: None" && rightbindbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(rightbindbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (rightstatusbox.Checked)
                    {
                        rightstatusbox.Checked = false;
                        rightstatusbox.Refresh();
                        rightclicktimer.Stop();
                    }
                    else
                    {
                        rightstatusbox.Checked = true;
                        rightstatusbox.Refresh();
                        rightclicktimer.Start();
                    }
                }
            }
            if (leftbindbtn.Text != "Bind: None" && leftbindbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(leftbindbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (leftstatusbox.Checked)
                    {
                        leftstatusbox.Checked = false;
                        leftstatusbox.Refresh();
                        clicktimer.Stop();
                    }
                    else
                    {
                        leftstatusbox.Checked = true;
                        leftstatusbox.Refresh();
                        clicktimer.Start();
                    }
                }
            }
            if (reachbindbtn.Text != "Bind: None" && reachbindbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(reachbindbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (reachstatusbox.Checked)
                    {
                        reachstatusbox.Checked = false;
                        reachstatusbox.Refresh();
                        flystatusbox.Refresh(); 
                        reachtimer.Stop();
                        memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", "3", "", null);
                    }
                    else
                    {
                        reachstatusbox.Checked = true;
                        reachstatusbox.Refresh();
                        reachtimer.Start();
                        //memory.mem.WriteMemory("Minecraft.Windows.exe+2409B78", "float", minreachslider.Value.ToString(), "", null);
                    }
                }
            }
            if (velocitybindbtn.Text != "Bind: None" && velocitybindbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(velocitybindbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (velocitystatusbox.Checked)
                    {
                        velocitystatusbox.Checked = false;
                        velocitystatusbox.Refresh();
                    }
                    else
                    {
                        velocitystatusbox.Checked = true;
                        velocitystatusbox.Refresh();
                    }
                }
            }
            if (timerbindbtn.Text != "Bind: None" && timerbindbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(timerbindbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (timerstatusbox.Checked)
                    {
                        timerstatusbox.Checked = false;
                        timerstatusbox.Refresh();
                    }
                    else
                    {
                        timerstatusbox.Checked = true;
                        timerstatusbox.Refresh();
                    }
                }
            }
            if (flybindbtn.Text != "Bind: None" && flybindbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(flybindbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (flystatusbox.Checked)
                    {
                        flystatusbox.Checked = false;
                        flystatusbox.Refresh();
                        memory.mem.WriteMemory("0x135889662F8", "int", "0", "", null);
                        memory.mem.FreezeValue("0x135889662F8", "int", "0", "");
                    }
                    else
                    {
                        flystatusbox.Checked = true;
                        flystatusbox.Refresh();
                        memory.mem.WriteMemory("0x135889662F8", "int", "1", "", null);
                        memory.mem.FreezeValue("0x135889662F8", "int", "1", "");
                    }
                }
            }
            /*if (bhopbindbtn.Text != "Bind: None" && bhopbindbtn.Text != "...")
            {
                Keys key = (Keys)kc.ConvertFromString(bhopbindbtn.Text);
                if (GetAsyncKeyState(key) < 0)
                {
                    if (bhopstatusbox.Checked)
                    {
                        bhopstatusbox.Checked = false;
                        bhopstatusbox.Refresh();
                        memory.mem.WriteMemory("0x1F2872812AC", "float", bhopslider.Value.ToString(), "", null);
                        memory.mem.FreezeValue("0x1F2872812AC", "float", bhopslider.Value.ToString(), "");
                    }
                    else
                    {
                        bhopstatusbox.Checked = true;
                        bhopstatusbox.Refresh();
                        memory.mem.WriteMemory("0x1F2872812AC", "float", "0,01999999955", "", null);
                        memory.mem.UnfreezeValue("0x1F2872812AC");
                    }
                }
            }*/
        }

        private void leftbindbtn_Click(object sender, EventArgs e)
        {
            leftbindbtn.Text = "...";
            leftbindbtn.Focus();
            leftbindbtn.Refresh();
        }

        private void leftbindbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (leftbindbtn.Text == "...")
            {
                leftbindbtn.Text = "" + e.KeyData.ToString();
                leftbindbtn.Refresh();
            }
            if (e.KeyData == Keys.Escape)
            {
                leftbindbtn.Text = "Bind: None";
                leftbindbtn.Refresh();
            }
        }

        private void rightbindbtn_Click(object sender, EventArgs e)
        {
            rightbindbtn.Text = "...";
            rightbindbtn.Focus();
            rightbindbtn.Refresh();
        }

        private void rightbindbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (rightbindbtn.Text == "...")
            {
                rightbindbtn.Text = "" + e.KeyData.ToString();
                rightbindbtn.Refresh();
            }
            if (e.KeyData == Keys.Escape)
            {
                rightbindbtn.Text = "Bind: None";
                rightbindbtn.Refresh();
            }
        }

        private void reachbindbtn_Click(object sender, EventArgs e)
        {
            reachbindbtn.Text = "...";
            reachbindbtn.Focus();
            reachbindbtn.Refresh();
        }

        private void reachbindbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (reachbindbtn.Text == "...")
            {
                reachbindbtn.Text = "" + e.KeyData.ToString();
                reachbindbtn.Refresh();
            }
            if (e.KeyData == Keys.Escape)
            {
                reachbindbtn.Text = "Bind: None";
                reachbindbtn.Refresh();
            }
        }

        private void velocitybindbtn_Click(object sender, EventArgs e)
        {
            velocitybindbtn.Text = "...";
            velocitybindbtn.Focus();
            velocitybindbtn.Refresh();
        }

        private void velocitybindbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (velocitybindbtn.Text == "...")
            {
                velocitybindbtn.Text = "" + e.KeyData.ToString();
                velocitybindbtn.Refresh();
            }
            if (e.KeyData == Keys.Escape)
            {
                velocitybindbtn.Text = "Bind: None";
                velocitybindbtn.Refresh();
            }
        }

        private void timerbindbtn_Click(object sender, EventArgs e)
        {
            timerbindbtn.Text = "...";
            timerbindbtn.Focus();
            timerbindbtn.Refresh();
        }

        private void timerbindbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (timerbindbtn.Text == "...")
            {
                timerbindbtn.Text = "" + e.KeyData.ToString();
                timerbindbtn.Refresh();
            }
            if (e.KeyData == Keys.Escape)
            {
                timerbindbtn.Text = "Bind: None";
                timerbindbtn.Refresh();
            }
        }

        private void flybindbtn_Click(object sender, EventArgs e)
        {
            flybindbtn.Text = "...";
            flybindbtn.Focus();
            flybindbtn.Refresh();
        }

        private void flybindbtn_KeyDown(object sender, KeyEventArgs e)
        {
            if (flybindbtn.Text == "...")
            {
                flybindbtn.Text = "" + e.KeyData.ToString();
                flybindbtn.Refresh();
            }
            if (e.KeyData == Keys.Escape)
            {
                flybindbtn.Text = "Bind: None";
                flybindbtn.Refresh();
            }
        }

        private void stringremovebtn_Click(object sender, EventArgs e)
        {
            int pid = memory.mem.GetProcIdFromName(pidtxt.Text);
            if (pid > 0)
            {
                memory.mem.OpenProcess(pid);
            }
            else
            {
                MessageBox.Show("Couldn't find the process you desire");
            }
            memory.mem.WriteMemory(addresstxt.Text, "string", brodm.RandomString(new Random().Next(200, 450)), "", null);
            MessageBox.Show("String removed");
            memory.mem.CloseProcess();
        }
        /*private void bhopbindbtn_Click(object sender, EventArgs e)
{
   bhopbindbtn.Text = "...";
   bhopbindbtn.Focus();
   bhopbindbtn.Refresh();
}

private void bhopbindbtn_KeyDown(object sender, KeyEventArgs e)
{
   if (bhopbindbtn.Text == "...")
   {
       bhopbindbtn.Text = "" + e.KeyData.ToString();
       bhopbindbtn.Refresh();
   }
   if (e.KeyData == Keys.Escape)
   {
       bhopbindbtn.Text = "Bind: None";
       bhopbindbtn.Refresh();
   }
}

private void speedbindbtn_Click(object sender, EventArgs e)
{
   speedbindbtn.Text = "...";
   speedbindbtn.Focus();
   speedbindbtn.Refresh();
}

private void speedbindbtn_KeyDown(object sender, KeyEventArgs e)
{
   if (speedbindbtn.Text == "...")
   {
       speedbindbtn.Text = "" + e.KeyData.ToString();
       speedbindbtn.Refresh();
   }
   if (e.KeyData == Keys.Escape)
   {
       speedbindbtn.Text = "Bind: None";
       speedbindbtn.Refresh();
   }
}*/
    }
}
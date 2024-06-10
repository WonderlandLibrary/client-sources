using skidderino;
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

namespace karambit_client
{
    public partial class mainform : Form
    {
        [DllImport("user32.dll")]
        static extern short GetAsyncKeyState(Keys vKey);

        bool hided = false;
        static VAMemory mem = new VAMemory("Minecraft.Windows");
        static Process mcprocess;
        static IntPtr baseaddress;

        public mainform()
        {
            InitializeComponent();
        }

        private void mainform_Load(object sender, EventArgs e)
        {
            if (mem.CheckProcess() == false)
            {
                MessageBox.Show("Launch minecraft for open the client.", "Karambit");
                Environment.Exit(0);
            }
            else
            {
                mcprocess = Process.GetProcessesByName("Minecraft.Windows").FirstOrDefault();
                baseaddress = mcprocess.MainModule.BaseAddress;
                Console.WriteLine(mcprocess);
                Console.WriteLine(baseaddress);
            }
        }

        private void closebtn_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }

        private void minimizebtn_Click(object sender, EventArgs e)
        {
            this.WindowState = FormWindowState.Minimized;
        }
        private void slidepanel_MouseEnter(object sender, EventArgs e)
        {
            if (hided == false)
            {
                hided = true;
                slidetimer.Start();
            }
            else
            {
                slidetimer.Stop();
            }
        }

        private void slidepanel_MouseLeave(object sender, EventArgs e)
        {
            hided = false;

        }

        private void slidestatepanel_MouseEnter(object sender, EventArgs e)
        {
            if (hided == false)
            {
                if (slidepanel.Location.X > 1)
                {
                    hided = true;
                }
                else
                {
                    hided = false;
                }
            }
            else
            {
                slidetimer.Stop();
            }
        }

        private void combatbtn_MouseEnter(object sender, EventArgs e)
        {
            if (hided == false)
            {
                if (slidepanel.Location.X > 1)
                {
                    hided = true;
                }
                else
                {
                    hided = false;
                }
            }
            else
            {
                slidetimer.Stop();
            }
        }

        private void movementbtn_MouseEnter(object sender, EventArgs e)
        {
            if (hided == false)
            {
                if (slidepanel.Location.X > 1)
                {
                    hided = true;
                }
                else
                {
                    hided = false;
                }
            }
            else
            {
                slidetimer.Stop();
            }
        }

        private void slidetimer_Tick(object sender, EventArgs e)
        {
            if (hided == true)
            {
                slidestatepanel.Visible = true;
                slidepanel.Location = new Point(slidepanel.Location.X + 30, slidepanel.Location.Y);
                slidestatepanel.Width = slidestatepanel.Width + 30;
                arrowlbl.Text = "<";
                if (slidepanel.Location.X >= 160 && slidestatepanel.Width >= 160)
                {
                    slidepanel.Location = new Point(158, slidepanel.Location.Y);
                    slidestatepanel.Width = 160;
                    slidetimer.Stop();
                }
            }
            else
            {
                slidepanel.Location = new Point(slidepanel.Location.X - 30, slidepanel.Location.Y);
                slidestatepanel.Width = slidestatepanel.Width - 30;
                if (slidepanel.Location.X <= -1 && slidestatepanel.Width <= 57)
                {
                    slidestatepanel.Visible = false;
                    slidepanel.Location = new Point(-1, slidepanel.Location.Y);
                    slidestatepanel.Width = 57;
                    arrowlbl.Text = ">";
                    slidetimer.Stop();
                }
            }
        }

        private void minreachslider_Scroll(object sender, ScrollEventArgs e)
        {
            double num = (300.0 + (double)minreachslider.Value) / 100.0;
            minreachlbl.Text = "Min: " + num.ToString();
            if (minreachslider.Value > maxreachslider.Value)
            {
                maxreachslider.Value = minreachslider.Value;
                minreachlbl.Refresh();
            }
        }

        private void maxreachslider_Scroll(object sender, ScrollEventArgs e)
        {
            double num = (300.0 + (double)maxreachslider.Value) / 100.0;
            maxreachlbl.Text = "Max: " + num.ToString();
            if (maxreachslider.Value < minreachslider.Value)
            {
                minreachslider.Value = maxreachslider.Value;
                maxreachlbl.Refresh();
            }
        }

        private void avgcpsslider_Scroll(object sender, ScrollEventArgs e)
        {
            double num = (double)mincpsbar.Value / 10.0;
            mincpslbl.Text = "Min: " + num.ToString();
            if (mincpsbar.Value > maxcpsbar.Value)
            {
                maxcpsbar.Value = mincpsbar.Value;
            }
        }

        private void maxcpsbar_Scroll(object sender, ScrollEventArgs e)
        {
            double num = (double)maxcpsbar.Value / 10.0;
            maxcpslbl.Text = "Max: " + num.ToString();
            if (maxcpsbar.Value < mincpsbar.Value)
            {
                mincpsbar.Value = maxcpsbar.Value;
            }
        }

        private void heightslider_Scroll(object sender, ScrollEventArgs e)
        {
            heightlbl.Text = "Height: " + heightslider.Value + "%";
        }

        private void linearslider_Scroll(object sender, ScrollEventArgs e)
        {
            linearlbl.Text = "Linear: " + linearslider.Value + "%";
        }

        private void timerslider_Scroll(object sender, ScrollEventArgs e)
        {
            double num = (double)timerslider.Value / 10.0;
            tpslbl.Text = num.ToString();
        }

        private void combatbtn_Click(object sender, EventArgs e)
        {
            movementpanel.Visible = false;
        }

        private void movementbtn_Click(object sender, EventArgs e)
        {
            movementpanel.Visible = true;
        }

        private void reachstatusbtn_CheckedChanged(object sender, EventArgs e)
        {
            if (reachstatusbtn.Checked)
            {
                reachtimer.Start();
            }
        }
        private void timerstatusbtn_CheckedChanged(object sender, EventArgs e)
        {
            if (timerstatusbtn.Checked)
            {
                tpstimer.Start();
            }
        }

        private void clickerstatusbtn_CheckedChanged(object sender, EventArgs e)
        {
            if (clickerstatusbtn.Checked)
            {
                clicktimer.Start();
                Console.WriteLine("Clicker Enabled");
            }
            else
            {
                clicktimer.Stop();
                Console.WriteLine("Clicker Disabled");
            }
        }

        private void reachtimer_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            double num = (300.0 + (double)maxreachslider.Value) / 100.0;
            double num2 = (300.0 + (double)minreachslider.Value) / 100.0;
            int minval;
            int maxval;
            minval = ((int)(1000 / num + num2 * (int)0.2));
            maxval = ((int)(1000 / num + num2 * (int)0.48));
            int minval2;
            int maxval2;
            minval2 = minval - 12;
            maxval2 = maxval + 12;
            reachtimer.Interval = rnd.Next(minval2, maxval2);

            IntPtr address = IntPtr.Add(baseaddress, 0x2961A08);
            float basevalue = mem.ReadFloat(address);
            double rndreach = skidderino.memory.NextDouble(num2, num);

            if (reachstatusbtn.Checked)
            {
                mem.WriteFloat(address, (float)rndreach);
                Console.WriteLine("Reach: " + basevalue);
            }
            else
            {
                mem.WriteFloat(address, 3);
                Console.WriteLine("Reach: " + basevalue);
                Thread.Sleep(5);
                reachtimer.Stop();
            }
        }

        private void tpstimer_Tick(object sender, EventArgs e)
        {
            double num = (double)timerslider.Value / 10.0;
            float basevalue = mem.ReadFloat((IntPtr)0x219BEE77260);
            IntPtr address = (IntPtr)0x219BEE77260;

            if (timerstatusbtn.Checked)
            {
                mem.WriteFloat(address, (float)num);
                Console.WriteLine("TPS: " + basevalue);
            }
            else
            {
                mem.WriteFloat(address, 20);
                Console.WriteLine("TPS: " + basevalue);
                tpstimer.Stop();
            }
        }

        private void clicktimer_Tick(object sender, EventArgs e)
        {
            Random rnd = new Random();
            int minval;
            int maxval;
            double num = (double)maxcpsbar.Value / 10.0;
            double num1 = (double)mincpsbar.Value / 10.0;
            minval = ((int)(1000 / num + num1 * (int)0.2));
            maxval = ((int)(1000 / num + num1 * (int)0.48));
            int minval2;
            int maxval2;
            minval2 = minval - 12;
            maxval2 = maxval + 12;
            clicktimer.Interval = rnd.Next(minval2, maxval2);

            if (GetAsyncKeyState(Keys.LButton) > 0)
            {
                clickevent.leftclicker();
                Console.WriteLine("Holding");
            }
        }
    }
}

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
using System.Threading;
using Rainbow;
using System.Reflection;
using System.IO;
using SunCheats.Properties;

namespace SlaySystemsClient
{
    public partial class cheats : UserControl
    {
        public cheats()
        {
            InitializeComponent();
        }

        #region CHEATS  

        DotNetScanMemory_SmoLL scan = new DotNetScanMemory_SmoLL();

        void wallhack_off()
        {
            wh = scan.ScanArray(crProc, "00 00 60 40 F4 F4 F4 F4 F4 F4 F4 F4");
            scan.WriteArray(wh[0], "00 00 20 40 F4 F4 F4 F4 F4 F4 F4 F4");
            if (wh[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach4block()
        {
            wh = scan.ScanArray(crProc, "00 00 00 3F 00 00 B4 43");
            scan.WriteArray(wh[0], "00 00 80 40 00 00 B4 43 00 00");
            if (wh[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach4block_off()
        {
            rc4 = scan.ScanArray(crProc, "00 00 80 40 00 00 B4 43 00 00");
            scan.WriteArray(rc4[0], "00 00 00 3F 00 00 B4 43");
            if (rc4[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void wallhack()
        {
            wh = scan.ScanArray(crProc, "00 00 20 40 F4 F4 F4 F4 F4 F4 F4 F4");
            scan.WriteArray(wh[0], "00 00 60 40 F4 F4 F4 F4 F4 F4 F4 F4");
            if (wh[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void killaura()
        {
            ka = scan.ScanArray(crProc, "00 00 00 3F 00 00 B4 43");
            scan.WriteArray(ka[0], "00 00 C0 40 00 00 B4 43 00 00");
            if (ka[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach()
        {
            r = scan.ScanArray(crProc, "00 00 00 3F 00 00 B4 43");
            scan.WriteArray(r[0], "00 00 A0 40 00 00 B4 43 00 00");
            if (r[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }
        void reach_off()
        {
            r = scan.ScanArray(crProc, "00 00 A0 40 00 00 B4 43 00 00");
            scan.WriteArray(r[0], "00 00 00 3F 00 00 B4 43");
            if (r[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach4_off()
        {
            rc4 = scan.ScanArray(crProc, "00 00 80 40 00 00 B4 43 00 00");
            scan.WriteArray(rc4[0], "00 00 00 3F 00 00 B4 43");
            if (rc4[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach3_off()
        {
            r3 = scan.ScanArray(crProc, "00 00 40 40 00 00 B4 43 00 00");
            scan.WriteArray(r3[0], "00 00 00 3F 00 00 B4 43");
            if (r3[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach3()
        {
            r3 = scan.ScanArray(crProc, "00 00 00 3F 00 00 B4 43");
            scan.WriteArray(r3[0], "00 00 40 40 00 00 B4 43 00 00");
            if (r3[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }


        void hitbox1_off()
        {
            h1 = scan.ScanArray(crProc, "00 00 80 3F 00 00 B4 43");
            scan.WriteArray(h1[0], "00 00 00 3F 00 00 B4 43");
            if (h1[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";
            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void hitbox1()
        {
            h1 = scan.ScanArray(crProc, "00 00 00 3F 00 00 B4 43");
            scan.WriteArray(h1[0], "00 00 80 3F 00 00 B4 43");
            if (h1[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";
            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }


        void hitbox2_off()
        {
            h2 = scan.ScanArray(crProc, "00 00 00 40 00 00 B4 43");
            scan.WriteArray(h2[0], "00 00 00 3F 00 00 B4 43");
            if (h2[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";
            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void hitbox2()
        {
            h2 = scan.ScanArray(crProc, "00 00 00 3F 00 00 B4 43");
            scan.WriteArray(h2[0], "00 00 00 40 00 00 B4 43");
            if (h2[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach4()
        {
            r4 = scan.ScanArray(crProc, "00 00 00 3F 00 00 B4 43");
            scan.WriteArray(r4[0], "00 00 80 40 00 00 B4 43 00 00");
            if (r4[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void megajump4()
        {
            mg = scan.ScanArray(crProc, "3D 0A D7 3E");
            scan.WriteArray(mg[0], "00 00 00 40");
            if (mg[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void megajump4_off()
        {
            mg = scan.ScanArray(crProc, "00 00 00 40");
            scan.WriteArray(mg[0], "3D 0A D7 3E");
            if (mg[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach5blok()
        {
            rc5 = scan.ScanArray(crProc, "00 00 00 3F 00 00 B4 43");
            scan.WriteArray(rc5[0], "00 00 A0 40 00 00 B4 43");
            if (rc5[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void reach5blok_off()
        {
            rc5 = scan.ScanArray(crProc, "00 00 A0 40 00 00 B4 43");
            scan.WriteArray(rc5[0], "00 00 00 3F 00 00 B4 43");
            if (rc5[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }

        void killaura_off()
        {
            ka = scan.ScanArray(crProc, "00 00 C0 40 00 00 B4 43 00 00");
            scan.WriteArray(ka[0], "00 00 00 3F 00 00 B4 43");
            if (ka[0].ToString("X") != "0")
            {
                label3.Text = "Adres Bulundu.";

            }
            else
            {
                label3.Text = "Adres Bulunamadı.";
            }
        }
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
        #endregion


        int selected = 0;

        private void GunaGradientButton6_Click(object sender, EventArgs e)
        {
            selected = 3;
            label1.Text = "Aimbot";
            label2.Text = "Rakiplere otomatik aim alır. Premium versiyona özeldir.";
        }




        private void Cheats_Load(object sender, EventArgs e)
        {

        }


        bool mc = false;
        public Process crProc;

        public string getjavawTitle()
        {
            Process[] proc = Process.GetProcessesByName("javaw");
            foreach (var process in proc)
            {
                return process.MainWindowTitle;
            }
            return "Bulunamadı";
        }

        private void Timer1_Tick(object sender, EventArgs e)
        {
            if (getjavawTitle() != "Bulunamadı")
            {
                mc = true;
                Process[] proc = Process.GetProcessesByName("javaw");
                foreach (var process in proc)
                {
                    if (process.MainWindowTitle.Contains("RiseClient"))
                    {
                        crProc = process;
                        label4.Text = "RiseClient Bulundu! - " + crProc.MainWindowTitle;
                    }
                    else if (process.MainWindowTitle.Contains("SonOyuncu"))
                    {
                        crProc = process;
                        label4.Text = "SonOyuncu Bulundu! - " + crProc.MainWindowTitle;
                    }
                    else if (process.MainWindowTitle.Contains("Minecraft"))
                    {
                        crProc = process;
                        label4.Text = "Minecraft Bulundu! - " + crProc.MainWindowTitle;
                    }
                }
            }
            else
            {
                mc = false;
                label4.Text = "SonOyuncu , RiseClient veya Minecraft Bulunamadı!";
            }

        }

        private void GunaGradientButton7_Click(object sender, EventArgs e)
        {
            if (mc == true)
            {
                if (selected == 1)
                {
                    wallhack();
                }
                else if (selected == 2)
                {
                    killaura();
                }
                else if (selected == 3)
                {
                    MessageBox.Show("Premium versiyona özeldir.");
                }
                else if (selected == 4)
                {
                    hitbox1();
                }
                else if (selected == 5)
                {
                    hitbox2();
                }
                else if (selected == 6)
                {
                    reach3();
                }
                else if (selected == 8)
                {
                    megajump4();
                }
                else if (selected == 9)
                {
                    reach4block();
                }
                else if (selected == 10)
                {
                    reach5blok();
                }
            }
            else
            {
                label3.Text = "Minecraft açık değil.";
            }
        }

        private void GunaGradientButton8_Click(object sender, EventArgs e)
        {
            if (mc == true)
            {
                if (selected == 1)
                {
                    wallhack_off();
                }
                else if (selected == 2)
                {
                    killaura_off();
                }
                else if (selected == 3)
                {
                    MessageBox.Show("Premium versiyona özeldir.");
                }
                else if (selected == 4)
                {
                    hitbox1_off();
                }
                else if (selected == 5)
                {
                    hitbox2_off();
                }
                else if (selected == 6)
                {
                    reach3_off();
                }
                else if (selected == 8)
                {
                    megajump4_off();
                }
                else if (selected == 9)
                {
                    reach4block_off();
                }
                else if (selected == 10)
                {
                    reach5blok_off();
                }
            }
            else
            {
                label3.Text = "Minecraft açık değil.";
            }
        }

        private void GunaGradientButton5_Click(object sender, EventArgs e)
        {

        }

        private void GunaGradientButton3_Click(object sender, EventArgs e)
        {

        }

        private void GunaGradientButton1_Click(object sender, EventArgs e)
        {

        }

        private void GunaGradientButton2_Click(object sender, EventArgs e)
        {

        }

        private void GunaGradientButton4_Click(object sender, EventArgs e)
        {

        }

        private void GunaComboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

            int secilen = gunaComboBox1.SelectedIndex;
            if (secilen == 0)
            {
                selected = 3;
                label1.Text = "Aimbot";
                label2.Text = "Rakiplere otomatik aim alır. Premium versiyona özeldir.";
            }
            else if (secilen == 1)
            {
                selected = 6;
                label1.Text = "3 Blok Reach";
                label2.Text = "Rakiplerin hitboxunu 3 blok genişletir. 3 bloktan vurmanızı sağlar.";
            }
            else if (secilen == 2)
            {
                selected = 2;
                label1.Text = "KillAura";
                label2.Text = "Rakiplere 6 blok öteden vurmanızı sağlar. Arkanız dönük olsa dahi rakiplere vurabilirsiniz.";
            }
            else if (secilen == 3)
            {
                selected = 4;
                label1.Text = "1 Blok Reach";
                label2.Text = "Rakiplerin hitboxunu 1 blok genişletir.";
            }
            else if (secilen == 4)
            {
                selected = 5;
                label1.Text = "2 Blok Reach";
                label2.Text = "Rakiplerin hitboxunu 2 blok genişletir.";
            }
            else if (secilen == 5)
            {
                selected = 1;
                label1.Text = "WallHack";
                label2.Text = "Rakipleri duvar arkası görmenizi sağlar..";
            }
            else if (secilen == 6)
            {
                selected = 8;
                label1.Text = "MegaJump";
                label2.Text = "Lobide yükseğe zıplamanızı sağlar..";
            }
            else if (secilen == 7)
            {
                selected = 9;
                label1.Text = "4 Blok Reach";
                label2.Text = "Rakiplerin hitboxunu 4 blok genişletir. 4 bloktan vurmanızı sağlar.";
            }
            else if (secilen == 8)
            {
                selected = 10;
                label1.Text = "5 Blok Reach";
                label2.Text = "Rakiplerin hitboxunu 5 blok genişletir. 5 bloktan vurmanızı sağlar.";
            }

        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
        }

        private void button1_Click(object sender, EventArgs e)
        {
          
        }
    }
}


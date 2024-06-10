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
using System.Runtime.InteropServices;
using GlobalLowLevelHooks;
using System.IO;
using System.Net;
using Rainbow;

namespace SlaySystemsClient
{
    public partial class regedit : UserControl
    {
        private string string_0;

        public regedit()
        {
            InitializeComponent();
        }

        private void method_ferdixd(object sender, AsyncCompletedEventArgs e)
        {
            if (Directory.Exists(Application.StartupPath + "\\regedit"))
            {
                Process.Start(Application.StartupPath + "\\regedit\\" + this.string_0 + ".reg");
            }
        }


        private void method_ferdiofficial(string string_1, string string_2)
        {
            this.string_0 = string_2;
            if (Directory.Exists(Application.StartupPath + "\\regedit"))
            {
                using (WebClient webClient = new WebClient())
                {
                    webClient.DownloadFileCompleted += this.method_ferdixd;
                    webClient.DownloadFileAsync(new Uri(string_1), Application.StartupPath + "\\regedit\\" + string_2 + ".reg");
                    return;
                }
            }
            Directory.CreateDirectory(Application.StartupPath + "\\regedit");
            using (WebClient webClient2 = new WebClient())
            {
                webClient2.DownloadFileCompleted += this.method_ferdixd;
                webClient2.DownloadFileAsync(new Uri(string_1), Application.StartupPath + "\\regedit\\" + string_2 + ".reg");
            }
        }

        private void regedit_Load(object sender, EventArgs e)
        {

        }

        private void gunaGradientButton7_Click(object sender, EventArgs e)
        {
            method_ferdiofficial("https://cdn.discordapp.com/attachments/638469678582202368/645187562633297921/Rod_Method.reg", "HızlıOlta");
            
        }

        private void gunaGradientButton8_Click(object sender, EventArgs e)
        {
            method_ferdiofficial("https://cdn.discordapp.com/attachments/638469678582202368/645188403914211328/Fps.reg", "Fps");
        }

        private void gunaGradientButton1_Click(object sender, EventArgs e)
        {
            method_ferdiofficial("https://cdn.discordapp.com/attachments/638469678582202368/645188622684913666/TheBestHit.reg", "BestHit");
        }

        private void gunaGradientButton4_Click(object sender, EventArgs e)
        {
            method_ferdiofficial("https://cdn.discordapp.com/attachments/638469678582202368/645188928084770826/OpReach.reg", "OpReach");
        }

        private void gunaGradientButton5_Click(object sender, EventArgs e)
        {
            method_ferdiofficial("https://cdn.discordapp.com/attachments/638491988987215882/652831752209039371/0KB.reg", "0KB");
        }

        private void gunaGradientButton6_Click(object sender, EventArgs e)
        {
            method_ferdiofficial("https://cdn.discordapp.com/attachments/638491988987215882/652832072699740162/BestReach.reg", "BestReach");
        }

        private void gunaGradientButton3_Click(object sender, EventArgs e)
        {
            method_ferdiofficial("https://cdn.discordapp.com/attachments/638491988987215882/652832897786445834/BestRegedit.reg", "BestRegedit");
        }

        private void gunaGradientButton2_Click(object sender, EventArgs e)
        {
            method_ferdiofficial("https://cdn.discordapp.com/attachments/638491988987215882/652833306806714368/0MS.reg", "0MS");
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
        }
    }
}
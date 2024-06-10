using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Rainbow;
namespace SlaySystemsClient
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void GunaButton1_Click(object sender, EventArgs e)
        {
            gunaButton1.BaseColor = Color.Purple;
            gunaButton3.BaseColor = Color.Transparent;
            gunaButton4.BaseColor = Color.Transparent;
            gunaButton5.BaseColor = Color.Transparent;
            clicker1.BringToFront();
        }

        private void GunaButton2_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
        }

        private void Clicker1_Load(object sender, EventArgs e)
        {

        }

        private void GunaButton3_Click(object sender, EventArgs e)
        {
            gunaButton3.BaseColor = Color.Purple;
            gunaButton1.BaseColor = Color.Transparent;
            gunaButton4.BaseColor = Color.Transparent;
            gunaButton5.BaseColor = Color.Transparent;
            macros1.BringToFront();
        }

        private void GunaButton4_Click(object sender, EventArgs e)
        {
            gunaButton4.BaseColor = Color.Purple;
            gunaButton1.BaseColor = Color.Transparent;
            gunaButton3.BaseColor = Color.Transparent;
            gunaButton5.BaseColor = Color.Transparent;
            cheats1.BringToFront();
        }

        private void Macros1_Load(object sender, EventArgs e)
        {

        }

        private void Timer1_Tick(object sender, EventArgs e)
        {
            gunaGradientPanel1.GradientColor1 = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class1.RainbowEffect();
            gunaGradientPanel1.GradientColor2 = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class1.RainbowEffect();
            gunaGradientPanel1.GradientColor3 = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class1.RainbowEffect();
            gunaGradientPanel1.GradientColor4 = Color.FromArgb(Class1.R, Class1.G, Class1.A);
        }

        private static List<string> DownloadLines(string hostUrl)
        {

            List<string> strContent = new List<string>();

            var webRequest = WebRequest.Create(hostUrl);

            using (var response = webRequest.GetResponse())
            using (var content = response.GetResponseStream())
            using (var reader = new StreamReader(content))
            {
                while (!reader.EndOfStream)
                {
                    strContent.Add(reader.ReadLine());
                }
            }
            return strContent;
        }

        private void GunaGradientPanel2_Click(object sender, EventArgs e)
        {

        }

        private void gunaButton5_Click(object sender, EventArgs e)
        {
            gunaButton5.BaseColor = Color.Purple;
            gunaButton1.BaseColor = Color.Transparent;
            gunaButton2.BaseColor = Color.Transparent;
            gunaButton3.BaseColor = Color.Transparent;
            gunaButton4.BaseColor = Color.Transparent;
            regedit1.BringToFront();
        }

        private void pictureBox2_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://discord.gg/qZ8Xz7S");
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            pictureBox1.BackColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class1.RainbowEffect();
            pictureBox1.BackColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class1.RainbowEffect();
            pictureBox1.BackColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class1.RainbowEffect();
            pictureBox1.BackColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
        }

        private void timer3_Tick(object sender, EventArgs e)
        {
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator1.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
        }

        private void gunaButton6_Click(object sender, EventArgs e)
        {
            DialogResult result2 = MessageBox.Show(" Bu client dai ve FerdiOfficial tarafından kodlanmıştır." + Environment.NewLine + " Eğer bir sorununuz varsa , destek talebi göndermek isterseniz aşağıdan evet  butonuna tıklayınız.", "BİLGİ", MessageBoxButtons.YesNoCancel);
            if (result2 == DialogResult.Yes)
            {
                gunaButton1.BaseColor = Color.Transparent;
                gunaButton3.BaseColor = Color.Transparent;
                gunaButton4.BaseColor = Color.Transparent;
                gunaButton5.BaseColor = Color.Transparent;
                destektalebi1.BringToFront();

            }
            else if (result2 == DialogResult.No)
            {

            }
            else if (result2 == DialogResult.Cancel)
            {
            }
        }

        private void cheats1_Load(object sender, EventArgs e)
        {

        }

        private void timer4_Tick(object sender, EventArgs e)
        {

        }
    }

}



using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using Rainbow;

namespace SunCheats
{
    public partial class destektalebi : UserControl
    {
        public destektalebi()
        {
            InitializeComponent();
        }
        Webhook wh = new Webhook();
        private void gunaGradientButton7_Click(object sender, EventArgs e)
        {
            string text = new WebClient().DownloadString("https://wtfismyip.com/text");
            this.wh.URL = "https://discordapp.com/api/webhooks/654759389923639298/x92oC9DKcQr4fZ59YoqKGTN-8GzDBVNrG0P1_VOc5RaMDf0FJtbKAebhb91xFPN3Umn6";
            this.wh.SendMsg(string.Concat(new string[]
            {

                this.gunaTextBox1.Text,
                "   **<<< Sorunu** ",
                Environment.NewLine,
                "**PC ADI:** ",
                Environment.UserName,
                Environment.NewLine,
                "**IP:** ",
                text,


                this.gunaTextBox2.Text,
                "   **<<< = Discord Adresi @everyone**"
            }));
            MessageBox.Show("Bildiriminiz iletildi!" + Environment.NewLine + "Boş yere bildirim yaparsanız ban yiyebilirsiniz.");
            return;

        }
        
        
        
    
    
        private void destektalebi_Load(object sender, EventArgs e)
        {
            
        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            gunaSeparator2.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator2.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator2.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
            Class2.RainbowEffect();
            gunaSeparator2.LineColor = Color.FromArgb(Class1.R, Class1.G, Class1.A);
        }
    }
}

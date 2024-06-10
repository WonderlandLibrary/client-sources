using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Diagnostics;
using System.Net;
using System.Management;
using System.Runtime.InteropServices;

namespace choppaClicker
{
    public partial class alts : Form
    {
        public alts()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
             WebClient client = new WebClient();

             var altGen = client.DownloadString(" ALT LIST URL ").Split('\n');

             var r = new Random();
             var idx = r.Next(altGen.Length);

             textBox1.Text = altGen[new Random().Next(0, altGen.Length)]; 
            sendAlt.Start();
        }

        private void alts_Load(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {
            this.Hide();
        }

        private void sendAlt_Tick(object sender, EventArgs e)
        {
            button1.Enabled = true;
            button1.Text = "Generate";
            WebClient client = new WebClient();

            var altGen = client.DownloadString(" ALT LIST URL ").Split('\n');

            var r = new Random();
            var idx = r.Next(altGen.Length);

            textBox1.Text = altGen[new Random().Next(0, altGen.Length)];
            
        }

        // didnt get to finish cooldown its like 5 more lines just add it
        private void cooldown_Tick(object sender, EventArgs e)
        {
            button1.Enabled = false;
            button1.Text = "cooldown!";

            sendAlt.Start();
        }
    }
}

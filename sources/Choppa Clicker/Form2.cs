using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Diagnostics;
using System.Management;
using System.Windows.Forms;
using System.Drawing.Drawing2D;
using System.Runtime.InteropServices;

namespace choppaClicker
{
    public partial class Form2 : Form
    {

        public Form2()
        {
            InitializeComponent();

        }

        private void Form2_Load(object sender, EventArgs e)
        {

            // checks for anydesk being open
            // you can add checks for other programs just copy and paste
            var anydesk = "anydesk";
            var target = Process.GetProcessesByName(anydesk).FirstOrDefault();

            if(target != null)
            {
                MessageBox.Show("ERROR: AFShK!6v4K", "error");
            } 

            timer1.Start();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            timer2.Start();
            timer1.Stop();
        }

        // hwid check
        private void timer2_Tick(object sender, EventArgs e)
        {
            WebClient client = new WebClient();

            string HWID = System.Security.Principal.WindowsIdentity.GetCurrent().User.Value;

            string appHWID = client.DownloadString(" HWID LIST URL "); // your hwid list

            label1.Text = "Checking HWID";

            if(appHWID.Contains(HWID))
            {
                timer3.Start();
                timer2.Stop();
            }
            else
            {
                label1.Text = "INVALID!";
                timer2.Stop();
            }

        }

        // simple version check you can add auto update very easy
        private void timer3_Tick(object sender, EventArgs e)
        {
            label1.Text = "Checking Version";

            WebClient client = new WebClient();

            string versionO = client.DownloadString(" VERSION URL ");
            string version = "0.1"; // version

            if(versionO.Contains(version))
            {
                timer4.Start();
                timer3.Stop();
            }
            else
            {
                label1.Text = "Outdated Version";
            }
        }

        // load clicker form
        private void timer4_Tick(object sender, EventArgs e)
        {
            timer4.Stop();
            Form1 form1 = new Form1();
            form1.Show();
            this.Hide();
        }
    }
}

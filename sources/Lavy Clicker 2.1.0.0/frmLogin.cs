using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Management;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lavy
{
    public partial class frmLogin : Form
    {
        public frmLogin()
        {
            InitializeComponent();
        }


        string version = "2.1.0.0";
        WebClient wc = new WebClient();
        string verified;


        private void bunifuFlatButton1_Click(object sender, EventArgs e)
        {
                MessageBox.Show("Hey hi dear. For the few people who remember this clicker (in Italy) there was the good kelvin meme that re-saddled him at even extraordinary prices ... well after figuring out how it worked, because in the click test online it didn't work I understood because that clicker was shit. The clicker only works with minecraft 1.7.10 and 1.8.9 and with all versions of the BLC" , "Read");
                frmDashboard frm = new frmDashboard();
                this.Hide();
                frm.Show();
        }
      
        private void frmLogin_Load(object sender, EventArgs e)
        {
            try
            {
                
                string getCurrentVersion = wc.DownloadString("http://lavy.ml/api/version.txt");
                
                if (!getCurrentVersion.Contains(version))
                {
                    
                }
                else
                {
                    string drive = "C";
                    ManagementObject dsk = new ManagementObject(@"win32_logicaldisk.deviceid=""" + drive + @":""");
                    dsk.Get();
                    string volumeSerial = dsk["VolumeSerialNumber"].ToString();

                     verified = wc.DownloadString("http://lavy.ml/api/hwid?hwid=" + volumeSerial);
                    
                }
            }
            catch (IOException ee)
            { 
            

                MessageBox.Show("Apparently our services are out of reach, try later!" + ee.Source);
                Application.Exit();

            }
            
            
        }

        private void frmLogin_FormClosing(object sender, FormClosingEventArgs e)
        {
            Application.Exit();
        }
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Text;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lavy
{
    public partial class frmSplash : Form
    {
        
        public frmSplash()
        {
            InitializeComponent();
            this.Opacity = 0.1;
        }
      
        private void Form1_Load(object sender, EventArgs e)
        {
            
        }

        private void Form1_Paint(object sender, PaintEventArgs e)
        {
         
        }

        private void fadein_Tick(object sender, EventArgs e)
        {
            if (this.Opacity <= 1.0)
            {
                this.Opacity += 0.025;
            }
            else
            {
                fadein.Stop();
            }
        }

        int countY;
        private void count_Tick(object sender, EventArgs e)
        {
            if(countY != 5)
            {
                countY++;
            }
            else
            {
                count.Stop();
                frmLogin frmLogin = new frmLogin();
                this.Hide();
                frmLogin.Show();

            }
           
        }

        private void frmSplash_FormClosing(object sender, FormClosingEventArgs e)
        {
            Application.Exit();
        }
    }
}

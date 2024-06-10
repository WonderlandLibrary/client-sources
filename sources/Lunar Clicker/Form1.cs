using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

//Made by BlackHat#0060

namespace Lunar_Client
{
    public partial class LoadingForm : Form
    {
        public LoadingForm()
        {
            InitializeComponent();
        }

        private void LoadingForm_Load(object sender, EventArgs e)
        {
            //setting up and starting timer1 to use as fake loading

            timer1.Interval = 500;

            timer1.Start(); 

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            //Switch to main form and stopping timer1

            Main Mainfrm = new Main(); 

            Mainfrm.Show();

            timer1.Stop();

            this.Hide();
            
        }
    }
}

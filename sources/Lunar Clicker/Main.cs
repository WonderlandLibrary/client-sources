using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using System.Media;
using System.IO;





/// <summary>
/// I TRIED TO LABLE EVERYTHING BUT ITS TOO MUCH WORK LMAO AND I HAVE TO GIVE THIS TO HEADED RN!!!!!!!



/// MADE BY BlackHat#0060
/// 
/// 
/// </summary>





namespace Lunar_Client
{
    public partial class Main : Form
    {
        public Main()
        {
            InitializeComponent();
        }

        private void exitBTN_Click(object sender, EventArgs e)
        {
            Environment.Exit(0); //Exiting the app
        }

        void settingsClear()//we use the void keyword so it is easy to call to it from the other buttons when ever we want. (less work)
        {
            //code for changing buttons colors when clicked
            settingsBTN1.FillColor = Color.FromArgb(10, 10, 10);

            settingsBTN1.BorderColor = Color.FromArgb(10, 10, 10);
        }

        void settingsColor()//we use the void keyword so it is easy to call to it from the other buttons when ever we want. (less work)
        {
            //code for changing buttons colors when clicked
            settingsBTN1.FillColor = Color.FromArgb(31, 31, 31);

            settingsBTN1.BorderColor = Color.FromArgb(43, 43, 43);
        }

        void homeClear()//we use the void keyword so it is easy to call to it from the other buttons when ever we want. (less work)
        {
            //code for changing buttons colors when clicked
            homeBTN1.FillColor = Color.FromArgb(10, 10, 10);

            homeBTN1.BorderColor = Color.FromArgb(10, 10, 10);
        }

        void homeColor()//we use the void keyword so it is easy to call to it from the other buttons when ever we want. (less work)
        {
            //code for changing buttons colors when clicked
            homeBTN1.FillColor = Color.FromArgb(31, 31, 31);

            homeBTN1.BorderColor = Color.FromArgb(43, 43, 43);
        }

        void serversClear()//we use the void keyword so it is easy to call to it from the other buttons when ever we want. (less work)
        {
            //code for changing buttons colors when clicked
            serversBTN1.FillColor = Color.FromArgb(10, 10, 10);

            serversBTN1.BorderColor = Color.FromArgb(10, 10, 10);
        }

        void serversColor()//we use the void keyword so it is easy to call to it from the other buttons when ever we want. (less work)
        {
            //code for changing buttons colors when clicked
            serversBTN1.FillColor = Color.FromArgb(31, 31, 31);

            serversBTN1.BorderColor = Color.FromArgb(43, 43, 43);
        }

        void aboutClear()//we use the void keyword so it is easy to call to it from the other buttons when ever we want. (less work)
        {
            //code for changing buttons colors when clicked
            aboutBTN1.FillColor = Color.FromArgb(10, 10, 10);

            aboutBTN1.BorderColor = Color.FromArgb(10, 10, 10);
        }

        void aboutColor()//we use the void keyword so it is easy to call to it from the other buttons when ever we want. (less work)
        {
            //code for changing buttons colors when clicked
            aboutBTN1.FillColor = Color.FromArgb(31, 31, 31);

            aboutBTN1.BorderColor = Color.FromArgb(43, 43, 43);
        }

        private void serversBTN1_Click(object sender, EventArgs e)
        {
            //changing button colors when clicked
            serversColor();
            homeClear();
            settingsClear();
            aboutClear();
            settingsPanel.SendToBack();
            serverPanel.BringToFront();
            realserverpanel.BringToFront();
            
        }

        private void homeBTN1_Click(object sender, EventArgs e)
        {
            //changing button colors when clicked
            homeColor();
            serversClear();
            settingsClear();
            aboutClear();
            homePanel.BringToFront();
        }

        private void launchPic_MouseEnter(object sender, EventArgs e)
        {
            
        }

        private void guna2Button2_MouseEnter(object sender, EventArgs e)
        {
            
            launchPic.BringToFront(); //bring this pic to the front when we hover over the invis button

            launchBTN.BringToFront(); //bring the invis button to the front when we hover over the invis button

        }

        private void guna2Button2_MouseHover(object sender, EventArgs e)
        {
            
        }

        private void guna2Button2_MouseLeave(object sender, EventArgs e)
        {
            launchPic.SendToBack(); //when we stop hovering over the button send the pic to the back
        }
        
        private void settingsBTN1_Click(object sender, EventArgs e)
        {
            //changing button colors when clicked
            settingsColor();
            serversClear();
            homeClear();
            aboutClear();
            
            serverPanel.BringToFront();
            settingsPanel.BringToFront();


        }

        private void aboutBTN1_Click(object sender, EventArgs e)
        {
            //changing button colors when clicked
            aboutColor();
            serversClear();
            homeClear();
            settingsClear();
            serverPanel.BringToFront();
            aboutPanel.BringToFront();
        }

        private void storeBTN1_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://store.lunarclient.com/"); //open this link in browser
        }


        //error code
        void error() //we use the void keyword so it is easy to call to it (error) from other buttons, pics, etc when ever we want. (less work)
        {
            launchpic2.BringToFront(); //launching pic to front

            this.Cursor = Cursors.WaitCursor; // cursor to thinking

            Thread.Sleep(2000); // freeze for 2 sec

            this.Cursor = Cursors.Default; //cursor to normal

            SystemSounds.Beep.Play(); //play error sound

            Thread.Sleep(300); // freeze for 300 ms 

            SystemSounds.Beep.Play(); //play error sound 

            MessageBox.Show("An unknow error occurred, please try again later.", "Lunar Client"); //error message

            Environment.Exit(0); //exit app
        }


        private void guna2Button2_Click(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void Main_Load(object sender, EventArgs e)
        {
            //bring home panel to front when loading
            homePanel.BringToFront();
            //send these pics to back when loading up
            launchpic2.SendToBack();
            launchPic.SendToBack();
            

        }

        private void guna2PictureBox3_Click(object sender, EventArgs e)
        {
            error();//do error code
        }

        private void guna2PictureBox4_Click(object sender, EventArgs e)
        {
            error();//do error code
        }

        private void guna2PictureBox5_Click(object sender, EventArgs e)
        {
            error();//do error code
        }

        private void guna2PictureBox6_Click(object sender, EventArgs e)
        {
            error();//do error code
        }

        private void guna2PictureBox7_Click(object sender, EventArgs e)
        {
            error();//do error code
        }

        private void socialBTN_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://discord.gg/YwZ2pSpJ3N"); //open this link in browser
        }

        private void guna2PictureBox14_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://twitter.com/LunarClient/status/1375887251234639877"); //open this link in browser
        }

        private void guna2PictureBox15_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://twitter.com/LunarClient/status/1373744527824064513?s=20"); //open this link in browser
        }

        private void guna2PictureBox18_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://store.lunarclient.com/spring"); //open this link in browser
        }

        private void guna2PictureBox11_Click(object sender, EventArgs e)
        {

        }

        private void guna2Button1_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://discord.gg/YwZ2pSpJ3N"); //open this link in browser
        }

        private void launchBTN3_MouseEnter(object sender, EventArgs e)
        {
            guna2PictureBox22.BringToFront(); //bring this pic to the front when we hover over the invis button

            launchBTN3.BringToFront(); //bring the invis button to the front when we hover over the invis button
        }

        private void launchBTN3_MouseLeave(object sender, EventArgs e)
        {
            guna2PictureBox22.SendToBack(); //when we stop hovering over the button send the pic to the back
        }

        private void guna2Button2_Click_1(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void guna2Button3_Click(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void guna2Button4_Click(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void guna2Button5_Click(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void guna2Button6_Click(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void guna2Button7_Click(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void guna2Button8_Click(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void serverPanel_Paint(object sender, PaintEventArgs e)
        {

        }

        private void launchBTN3_Click(object sender, EventArgs e)
        {
            error(); //do error code
        }

        private void panel4_Paint(object sender, PaintEventArgs e)
        {

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            try
            {
                timer1.Interval = 800 / Convert.ToInt32((guna2TrackBar1.Value).ToString("0"));
                if (guna2TrackBar1.Value > 0)
                {
                    if (MouseButtons == MouseButtons.Left)
                    {
                        clickerclass.leftclick(1);
                    }
                }
            }
            catch (Exception) { }
        }
        int nu = 16;
        int lot;
        private void guna2TrackBar1_Scroll(object sender, ScrollEventArgs e)
        {
            if (guna2TrackBar1.Value > 0)
            {
                timer1.Start();
            }
            else if (guna2TrackBar1.Value == 0)
            {
                timer1.Stop();
            }

            label1.Text = (guna2TrackBar1.Value).ToString("0.0 GB ALLOCATED");
            lot =  nu - guna2TrackBar1.Value;
            label4.Text = lot.ToString("0  G B  L E F T  T O  A L L O C A T E");
        }

        private void guna2Button9_Click(object sender, EventArgs e)
        {
            guna2Button9.FillColor = Color.FromArgb(23, 157, 68);
            guna2Button9.HoverState.FillColor = Color.FromArgb(40, 175, 85);

            guna2Button10.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button10.HoverState.FillColor = Color.FromArgb(31, 31, 31);

            guna2Button11.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button11.HoverState.FillColor = Color.FromArgb(31, 31, 31);
        }

        private void guna2Button10_Click(object sender, EventArgs e)
        {
            guna2Button10.FillColor = Color.FromArgb(23, 157, 68);
            guna2Button10.HoverState.FillColor = Color.FromArgb(40, 175, 85);

            guna2Button9.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button9.HoverState.FillColor = Color.FromArgb(31, 31, 31);

            guna2Button11.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button11.HoverState.FillColor = Color.FromArgb(31, 31, 31);
        }

        private void guna2Button11_Click(object sender, EventArgs e)
        {
            guna2Button11.FillColor = Color.FromArgb(23, 157, 68);
            guna2Button11.HoverState.FillColor = Color.FromArgb(40, 175, 85);

            guna2Button9.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button9.HoverState.FillColor = Color.FromArgb(31, 31, 31);

            guna2Button10.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button10.HoverState.FillColor = Color.FromArgb(31, 31, 31);
        }

        private void guna2Button12_Click(object sender, EventArgs e)
        {
            var fileContent = string.Empty;
            var filePath = string.Empty;

            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.InitialDirectory = "c:\\";
                openFileDialog.Filter = "txt files (*.txt)|*.txt|All files (*.*)|*.*";
                openFileDialog.FilterIndex = 2;
                openFileDialog.RestoreDirectory = true;

                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    //Get the path of specified file
                    filePath = openFileDialog.FileName;

                    //Read the contents of the file into a stream
                    var fileStream = openFileDialog.OpenFile();

                    using (StreamReader reader = new StreamReader(fileStream))
                    {
                        fileContent = reader.ReadToEnd();
                    }
                   
                }
             

            }

            

        }

        private void guna2Button15_Click(object sender, EventArgs e)
        {
            guna2Button15.FillColor = Color.FromArgb(23, 157, 68);
            guna2Button15.HoverState.FillColor = Color.FromArgb(40, 175, 85);

            guna2Button14.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button14.HoverState.FillColor = Color.FromArgb(31, 31, 31);

            
        }

        private void guna2Button14_Click(object sender, EventArgs e)
        {
            guna2Button14.FillColor = Color.FromArgb(23, 157, 68);
            guna2Button14.HoverState.FillColor = Color.FromArgb(40, 175, 85);

            guna2Button15.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button15.HoverState.FillColor = Color.FromArgb(31, 31, 31);

            this.Cursor = Cursors.WaitCursor; // cursor to thinking

            Thread.Sleep(2000); // freeze for 2 sec

            this.Cursor = Cursors.Default; //cursor to normal

            SystemSounds.Beep.Play(); //play error sound

            guna2Button15.FillColor = Color.FromArgb(23, 157, 68);
            guna2Button15.HoverState.FillColor = Color.FromArgb(40, 175, 85);

            guna2Button14.FillColor = Color.FromArgb(23, 23, 23);
            guna2Button14.HoverState.FillColor = Color.FromArgb(31, 31, 31);
        }

        private void guna2Button16_Click(object sender, EventArgs e)
        {
            var fileContent = string.Empty;
            var filePath = string.Empty;

            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.InitialDirectory = "c:\\";
                openFileDialog.Filter = "txt files (*.txt)|*.txt|All files (*.*)|*.*";
                openFileDialog.FilterIndex = 2;
                openFileDialog.RestoreDirectory = true;

                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    //Get the path of specified file
                    filePath = openFileDialog.FileName;

                    //Read the contents of the file into a stream
                    var fileStream = openFileDialog.OpenFile();

                    using (StreamReader reader = new StreamReader(fileStream))
                    {
                        fileContent = reader.ReadToEnd();
                    }

                }
                

            }
        }

        private void guna2Button17_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start(" https://www.lunarclient.com/terms/"); //open this link in browser
       
        }

        private void guna2Button18_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://www.lunarclient.com/"); //open this link in browser

        }

        private void guna2Button19_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://www.lunarclient.com/faq/"); //open this link in browser

        }

        private void guna2Button20_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start("https://discord.gg/YwZ2pSpJ3N"); //open this link in browser
        }
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Media;

namespace choppaClicker
{
    public partial class music : Form
    {
        public music()
        {
            InitializeComponent();
        }

        private void label3_Click(object sender, EventArgs e)
        {
            this.Hide();
        }


        private void button1_Click(object sender, EventArgs e)
        {

            string song1 = textBox1.Text;
            string song2 = textBox1.Text;
            string song3 = textBox1.Text;

            string songg1 = "music1";
            string songg2 = "music2";
            string songg3 = "music3";

            if (song1.Contains(songg1))
            {
                System.Media.SoundPlayer player = new System.Media.SoundPlayer();
                     player.SoundLocation = @"C:\choppamusic\music1.wav";
                    player.Play();
            }
            else if(song2.Contains(songg2))
            {
                System.Media.SoundPlayer player = new System.Media.SoundPlayer();
                player.SoundLocation = @"C:\choppamusic\music2.wav";
                player.Play();
            }
            else if(song3.Contains(songg3))
            {
                System.Media.SoundPlayer player = new System.Media.SoundPlayer();
                player.SoundLocation = @"C:\choppamusic\music3.wav";
                player.Play();
            }
            else
            {
                MessageBox.Show("please choose a song \nif you dont know how to go follow \nthe steps in discord!", "choppaclicker");
            }
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            SoundPlayer player = new SoundPlayer();
            player.Stop();
        }
    }
}

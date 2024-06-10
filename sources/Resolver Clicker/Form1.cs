using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using AnyDeskResolver.Helpers;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace AnyDeskResolver
{
    public partial class Form1 : Form
    {
        [DllImport("user32.dll")]
        public static extern bool ReleaseCapture();

        [DllImport("user32.dll")]
        public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);

        public const int WM_NCLBUTTONDOWN = 0xA1;
        public const int HT_CAPTION = 0x2;

        public Form1()
        {
            InitializeComponent();
            if (Properties.Settings.Default.savelog)
            {
                skeetToggle1.Checked = true;
                skeetTextBox1.Text = Properties.Settings.Default.filepath;
            }

            label15.Text += Utils.Tools.GetAddy();
        }


        public static void AppendText(RichTextBox box, string text, Color color, bool bold)
        {
            box.SelectionStart = box.TextLength;
            box.SelectionLength = 0;

            if (bold) box.SelectionFont = new Font(box.Font, FontStyle.Bold);

            box.SelectionColor = color;
            box.AppendText(text);
            box.SelectionColor = box.ForeColor;
        }
        private static string isVPN(string ip)
        {
            using (var client = new WebClient())
            {
                client.Proxy = new WebProxy();
                string result = client.DownloadString($"https://levensles.club/api/vpn.php?ip={ip}");

                dynamic data = JObject.Parse(result);
                return data.proxy.ToString();


            }
        }

        private void skeetButton1_Click(object sender, EventArgs e)
        {
            try
            {
                dynamic data = JObject.Parse(Resolver._resolve());
                string ip = data.IP.ToString();
                if (ip.Length > 3)
                {
                    AppendText(richTextBox2, data.IP.ToString(), Color.Gray, false);
                    AppendText(richTextBox2, " | ", Color.DarkViolet, false);
                    AppendText(richTextBox2, data.countryCode.ToString(), Color.Gray, false);
                    AppendText(richTextBox2, " | ", Color.DarkViolet, false);
                    AppendText(richTextBox2, isVPN(data.IP.ToString()), Color.Gray, false);
                    AppendText(richTextBox2, "\n", Color.Gray, false);
                    if (Properties.Settings.Default.savelog)
                    {
                        File.AppendAllText(Properties.Settings.Default.filepath, data.IP.ToString() + " | " + data.countryCode.ToString() + " | " + isVPN(data.IP.ToString()) + "\n");

                    }
                }
                else
                {
                    MessageBox.Show("Failed to get IP");
                }
            }
            catch (Exception kankerException)
            {
                MessageBox.Show(kankerException.Message);
            }


        }

        private void label7_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }

        private void label2_Click(object sender, EventArgs e)
        {
            flatTabControl1.SelectedIndex = 1;
        }

        private void label1_Click(object sender, EventArgs e)
        {
            flatTabControl1.SelectedIndex = 0;

        }

        private void skeetButton2_Click(object sender, EventArgs e)
        {

            if (!File.Exists(skeetTextBox1.Text))
            {
                if (skeetToggle1.Checked)
                {
                    MessageBox.Show("Cant save because the file does not exist");
                }
                else
                {
                    Properties.Settings.Default.filepath = skeetTextBox1.Text;
                    Properties.Settings.Default.savelog = false;
                    Properties.Settings.Default.Save();
                }
            }
            else
            {
                Properties.Settings.Default.filepath = skeetTextBox1.Text;
                Properties.Settings.Default.savelog = true;
                Properties.Settings.Default.Save();
            }
            
        }

        private void skeetToggle1_CheckedChanged(object sender)
        {
            skeetTextBox1.Enabled = skeetToggle1.Checked;
            if (skeetTextBox1.Text.Length > 1) skeetTextBox1.Text = "";
            skeetToggle1.Refresh();
        }

        private void skeetButton12_Click(object sender, EventArgs e)
        {
            Utils.Tools.resetAddy();
            Thread.Sleep(2500);
            label13.Text = "New:" + Utils.Tools.GetAddy();
        }

        private void label6_Click(object sender, EventArgs e)
        {
            WindowState = FormWindowState.Minimized;

        }

        private void panel1_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                ReleaseCapture();
                SendMessage(Handle, WM_NCLBUTTONDOWN, HT_CAPTION, 0);
            }
        }
    }
}

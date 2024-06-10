using skidderino;
using System;
using System.Diagnostics;
using System.Windows.Forms;
using Discord.Webhook;
using Discord.Webhook.HookRequest;

namespace karambit_clicker
{
    public partial class hwidcheck : Form
    {
        public hwidcheck()
        {
            base.Opacity = 0.0;
            InitializeComponent();
            registerpnl.Hide();
        }

        private void loginbtn_Click(object sender, EventArgs e)
        {
            if (!loginpnl.Visible)
            {
                registerpnl.Hide();
                loginpnl.Show();
                return;
            }
            if (API.Login(usernametxt.Text, passwordtxt.Text))
            {
                DiscordWebhook discordWebhook = new DiscordWebhook();
                discordWebhook.HookUrl = "https://discordapp.com/api/webhooks/784183257645318243/epwedVzPD1EgykOkKvHJ11ycMuVFMU0BelZ0mPCUThUKcYwwOLESYNSloLuK4r8q1KyV";
                DiscordHookBuilder discordHookBuilder = DiscordHookBuilder.Create("Yuuto", null);
                string value = skidderino.brodm.getID();
                DateTime now = DateTime.Now;
                DiscordEmbedField[] fields = new DiscordEmbedField[]
                {
                        new DiscordEmbedField("IP: ", value, false),
                        new DiscordEmbedField("User: ", usernametxt.Text, false),
                        new DiscordEmbedField("Date and Time: ", now.ToString(), false),
                };
                DiscordEmbed item = new DiscordEmbed("karambit clicker login log", "", 3319890, "", "Karambit Clicker", "", fields);
                discordHookBuilder.Embeds.Add(item);
                DiscordHook hookRequest = discordHookBuilder.Build();
                discordWebhook.Hook(hookRequest);
                Form mainform2 = new mainform();
                mainform.username = usernametxt.Text;
                this.Hide();
                mainform2.ShowDialog();
                this.Close();
            }
        }

        private void registerbtn_Click(object sender, EventArgs e)
        {
            if (!registerpnl.Visible)
            {
                registerpnl.Show();
                loginpnl.Hide();
                return;
            }

            if (API.Register(userregtxt.Text, passwdregtxt.Text, emailtxt.Text, keytxt.Text))
            {
                DiscordWebhook discordWebhook = new DiscordWebhook();
                discordWebhook.HookUrl = "https://discordapp.com/api/webhooks/784183257645318243/epwedVzPD1EgykOkKvHJ11ycMuVFMU0BelZ0mPCUThUKcYwwOLESYNSloLuK4r8q1KyV";
                DiscordHookBuilder discordHookBuilder = DiscordHookBuilder.Create("Yuuto", null);
                string value = skidderino.brodm.getID();
                DateTime now = DateTime.Now;
                DiscordEmbedField[] fields = new DiscordEmbedField[]
                {
                        new DiscordEmbedField("IP: ", value, false),
                        new DiscordEmbedField("User: ", userregtxt.Text, false),
                        new DiscordEmbedField("Date and Time: ", now.ToString(), false),
                };
                DiscordEmbed item = new DiscordEmbed("karambit clicker new register log", "", 3319890, "", "Karambit Clicker", "", fields);
                discordHookBuilder.Embeds.Add(item);
                DiscordHook hookRequest = discordHookBuilder.Build();
                discordWebhook.Hook(hookRequest);
                MessageBox.Show("Redeemed, restart the application, click the ok button!");
                Application.Exit();
            }
        }

        private void hwidcheck_Load(object sender, EventArgs e)
        {
            base.Hide();
            if (anticrack.IsVM())
            {
                brodm.AutoClosingMessageBox.Show("Karambit Clicker can't run in VMs.", "Error", 4000);
                return;
            }
            if (anticrack.IsSandbox())
            {
                brodm.AutoClosingMessageBox.Show("Karambit Clicker can't run in Sandboxes.", "Error", 4000);
                return;
            }
            if (anticrack.IsEmulation())
            {
                brodm.AutoClosingMessageBox.Show("Process is being emulated.", "Error", 4000);
                return;
            }
            base.Text = brodm.RandomString(new Random().Next(8, 16));
            if (brodm.IsInternetAvailable())
            {
                showanimationtimer.Start();
                base.Activate();
                base.Show();
                base.Update();
            }
            if (!brodm.IsInternetAvailable())
            {
                MessageBox.Show("No internet available, please try again later.");
                Environment.Exit(0);
            }
            anticracktimer.Start();
        }

        private void anticracktimer_Tick(object sender, EventArgs e)
        {
            foreach (Process process in Process.GetProcessesByName("Wireshark"))
            {
                try
                {
                    process.Kill();
                }
                catch { }
            }
            foreach (Process process2 in Process.GetProcessesByName("Fiddler"))
            {
                try
                {
                    process2.Kill();
                }
                catch { }
            }
            foreach (Process process3 in Process.GetProcessesByName("Process Hacker"))
            {
                try
                {
                    process3.Kill();
                }
                catch { }
            }
            foreach (Process process4 in Process.GetProcessesByName("Process Explorer"))
            {
                try
                {
                    process4.Kill();
                }
                catch { }
            }
            foreach (Process process5 in Process.GetProcessesByName("dnSpy"))
            {
                try
                {
                    process5.Kill();
                }
                catch { }
            }
            foreach (Process process6 in Process.GetProcessesByName("ollydbg"))
            {
                try
                {
                    process6.Kill();
                }
                catch { }
            }
            foreach (Process process7 in Process.GetProcessesByName("x64dbg"))
            {
                try
                {
                    process7.Kill();
                }
                catch { }
            }
        }

        private void showanimationtimer_Tick(object sender, EventArgs e)
        {
            if (base.Opacity <= 1.0)
            {
                base.Opacity += 0.145;
                return;
            }
            showanimationtimer.Enabled = false;
            showanimationtimer.Stop();
        }
    }
}

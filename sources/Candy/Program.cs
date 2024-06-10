using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace New_CandyClient
{
    static class Program
    {
        [Obfuscation(Feature = "virtualization", Exclude = false)]
        [Obfuscation(Feature = "parameters renaming", Exclude = true)]
        [Obfuscation(Feature = "internalization", Exclude = true)]
        [Obfuscation(Feature = "Apply to member * when method or constructor: virtualization", Exclude = false)]
        [Obfuscation(Feature = "internalization", Exclude = true)]
        /// <summary>
        /// Punto di ingresso principale dell'applicazione.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Paladin());
        }
    }
}

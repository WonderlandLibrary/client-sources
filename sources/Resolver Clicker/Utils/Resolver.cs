using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Linq.Expressions;
using System.Management;
using System.Net;
using System.Net.NetworkInformation;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AnyDeskResolver.Helpers
{
    static class Resolver
    {
        public static string GetCommandLine(this Process process)
        {
            using (ManagementObjectSearcher searcher =
                new ManagementObjectSearcher("SELECT CommandLine FROM Win32_Process WHERE ProcessId = " + process.Id))
            using (ManagementObjectCollection objects = searcher.Get())
            {
                return objects.Cast<ManagementBaseObject>().SingleOrDefault()?["CommandLine"]?.ToString();
            }
        }

        public static string _resolve()
        {
            int pid = 0;
            foreach (var procs in Process.GetProcessesByName("AnyDesk"))
            {
                string cmdline = GetCommandLine(procs);
                if (cmdline.Contains(" --service"))
                    pid = procs.Id;
            }

            if (pid == 0)
            {
                if (!File.Exists("C:\\Program Files (x86)\\AnyDesk\\AnyDesk.exe"))
                {

                    MessageBox.Show("AnyDesk is not installed");

                }


                return "Failed to find the AnyDesk service.";
            }

            Process proc = new Process();
            ProcessStartInfo ps = new ProcessStartInfo();
            ps.Arguments = " /c netstat -nao | find \"" + pid + "\"";
            ps.FileName = "cmd.exe";
            ps.UseShellExecute = false;
            ps.WindowStyle = ProcessWindowStyle.Hidden;
            ps.CreateNoWindow = true;
            ps.RedirectStandardInput = true;
            ps.RedirectStandardOutput = true;
            ps.RedirectStandardError = true;
            proc.StartInfo = ps;
            proc.Start();
            StreamReader stdOut = proc.StandardOutput;
            StreamReader stdErr = proc.StandardError;
            string content = stdOut.ReadToEnd();

            string gay = stdErr.ReadToEnd();
            string exit = proc.ExitCode.ToString();
            string[] lines = content.Split(
                new[] {Environment.NewLine},
                StringSplitOptions.None
            );
            string resolved = "";

            foreach (string fag in lines)
            {
                if (!fag.EndsWith("" + pid) || !fag.Contains("TCP") || fag.Contains(":443") || fag.Contains(":80") ||
                    fag.Contains("0.0.0.0") || fag.Contains(":0") || resolved.Length > 0)
                    continue;
                string form = fag.Replace("  ", " ");
                for (var a = 0; a <= 20; a++)
                    form = form.Replace("  ", " ");
                form = form.Substring(5);
                form = form.Replace("  ", " ");
                form = form.Substring(form.IndexOf(" "));
                form = form.Replace("  ", " ");

                int nIgger = 0;
                bool doddd = true;

                if (form.Contains("SYN_SENT") || form.Contains("ESTABLISHED"))
                {

                    form = form.Substring(0, form.IndexOf(":"));
                    form = form.Replace(" ", "");

                    using (var client = new WebClient())
                    {
                        client.Proxy = new WebProxy();
                        string result = client.DownloadString($"http://ip-api.com/json/{form}?fields=country,countryCode,regionName,city,zip,isp");
                 
                        if (result.Length < 5)
                        {

                        }
                        else
                        {
                            result = result.Replace("}", ", \"IP\":\"" + form + "\"}");
                            return result;
                            
                        }

                    }

                }

   
            }

            return "";
        }
    }
}
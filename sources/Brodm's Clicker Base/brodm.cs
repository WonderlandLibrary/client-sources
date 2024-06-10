using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace skidderino
{
    public class brodm
    {
        [DllImport("user32.dll")]
        public static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowTextLength(IntPtr hwnd);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

        [DllImport("user32.dll")]
        private static extern int GetWindowTextW([In] IntPtr hWnd, [MarshalAs(UnmanagedType.LPWStr)] [Out]
            StringBuilder lpString, int nMaxCount);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);


        public string currentWindow = "";
        public int currentPID;
        public int mcpid;

        public static string Transform(string value)
        {
            char[] array = value.ToCharArray();
            for (int i = 0; i < array.Length; i++)
            {
                int num = (int)array[i];
                bool flag = num >= 97 && num <= 122;
                if (flag)
                {
                    bool flag2 = num > 109;
                    if (flag2)
                    {
                        num -= 13;
                    }
                    else
                    {
                        num += 13;
                    }
                }
                else
                {
                    bool flag3 = num >= 65 && num <= 90;
                    if (flag3)
                    {
                        bool flag4 = num > 77;
                        if (flag4)
                        {
                            num -= 13;
                        }
                        else
                        {
                            num += 13;
                        }
                    }
                }
                array[i] = (char)num;
            }
            return new string(array);
        }


        public static string RandomString(int length)
        {
            Random rnd = new Random();
            return new string((from s in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", length)
                               select s[rnd.Next(s.Length)]).ToArray<char>());
        }

        public string GetCaptionOfActiveWindow()
        {
            var strTitle = string.Empty;
            var handle = GetForegroundWindow();
            var intLength = GetWindowTextLength(handle) + 1;
            var stringBuilder = new StringBuilder(intLength);
            if (GetWindowText(handle, stringBuilder, intLength) > 0)
            {
                strTitle = stringBuilder.ToString();
            }
            return strTitle;
        }

        public static bool ApplicationIsActivated()
        {
            IntPtr foregroundWindow = GetForegroundWindow();
            bool result;
            if (foregroundWindow == IntPtr.Zero)
            {
                result = false;
            }
            else
            {
                int id = Process.GetCurrentProcess().Id;
                int num;
                GetWindowThreadProcessId(foregroundWindow, out num);
                result = (num == id);
            }
            return result;
        }

        public static int MakeLParam(int LoWord, int HiWord)
        {
            return (int)((HiWord << 16) | (LoWord & 0xFFFF));
        }

        /*public void GetForeGroundWindowInfo()
        {
            IntPtr foregroundWindow = GetForegroundWindow();
            if (!foregroundWindow.Equals(obj: IntPtr.Zero))
            {
                int windowTextLenght = GetWindowTextLength(foregroundWindow);
                StringBuilder sB = new StringBuilder("", windowTextLenght + 1);
                if (windowTextLenght > 0)
                {
                    GetWindowTextW(foregroundWindow, sB, sB.Capacity);
                }
                int currentpid = 0;
                GetWindowThreadProcessId(foregroundWindow, ref currentpid);
                Process[] processByName = Process.GetProcessesByName("javaw");
                foreach (Process process in processByName)
                {
                    mcpid = process.Id;
                }
                currentWindow = sB.ToString();
                currentPID = currentpid;
            }
        }*/
    }
}

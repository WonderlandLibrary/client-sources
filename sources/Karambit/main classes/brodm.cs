using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;

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

        [DllImport("wininet.dll")]
        private static extern bool InternetGetConnectedState(out int description, int reservedValue);

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
            return new string((from s in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzáàéèìóòúù0123456789;:[]{}()@`,./_?!#$%&'=^~\\|*-+", length)
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

        public void GetForeGroundWindowInfo()
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
                GetWindowThreadProcessId(foregroundWindow, out currentpid);
                Process[] processByName = Process.GetProcessesByName("javaw");
                foreach (Process process in processByName)
                {
                    mcpid = process.Id;
                }
                currentWindow = sB.ToString();
                currentPID = currentpid;
            }
        }

        public static string getID()
        {
            String address = "";
            WebRequest request = WebRequest.Create("http://checkip.dyndns.org/");
            using (WebResponse response = request.GetResponse())
            using (StreamReader stream = new StreamReader(response.GetResponseStream()))
            {
                address = stream.ReadToEnd();
            }
            int first = address.IndexOf("Address: ") + 9;
            int last = address.LastIndexOf("</body>");
            address = address.Substring(first, last - first);

            return address;
        }

        public static bool IsInternetAvailable()
        {
            int num;
            return InternetGetConnectedState(out num, 0);
        }

        public static int MakeLParam(int LoWord, int HiWord)
        {
            return (int)((HiWord << 16) | (LoWord & 0xFFFF));
        }

        public class AutoClosingMessageBox
        {
            [DllImport("user32.dll", SetLastError = true)]
            private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

            [DllImport("user32.dll", CharSet = CharSet.Auto)]
            private static extern IntPtr SendMessage(IntPtr hWnd, uint Msg, IntPtr wParam, IntPtr lParam);

            private string _caption;
            private System.Threading.Timer _timeoutTimer;
            private AutoClosingMessageBox(string text, string caption, int timeout)
            {
                this._caption = caption;
                this._timeoutTimer = new System.Threading.Timer(new TimerCallback(this.OnTimerElapsed), null, timeout, -1);
                MessageBox.Show(text, caption);
            }

            public static void Show(string text, string caption, int timeout)
            {
                new AutoClosingMessageBox(text, caption, timeout);
            }

            private void OnTimerElapsed(object state)
            {
                IntPtr intPtr = AutoClosingMessageBox.FindWindow(null, this._caption);
                if (intPtr != IntPtr.Zero)
                {
                    AutoClosingMessageBox.SendMessage(intPtr, 16U, IntPtr.Zero, IntPtr.Zero);
                }
                this._timeoutTimer.Dispose();
            }
        }
    }
}

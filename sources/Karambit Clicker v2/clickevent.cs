using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Security.Policy;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace skidderino
{
    public class clickevent
    {

        [DllImport(dllName: "user32.dll", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall)]
        public static extern void mouse_event(int flags, int cX, int cY, int button, int info);

        [DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
        private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);


        public static void leftclicker()
        {
            brodm brodm = new brodm();
            Random rnd = new Random();
            IntPtr hWnd;
            hWnd = brodm.GetForegroundWindow();

            if (brodm.GetCaptionOfActiveWindow().Contains("Minecraft")
                /*|| brodm.GetCaptionOfActiveWindow().Contains(textbox.Text)*/)
            {
                if (!brodm.ApplicationIsActivated())
                {
                    apimouse_event(4, 0, 0, 0, 0);
                    Thread.Sleep(rnd.Next(1, 5));
                    apimouse_event(2, 0, 0, 0, 0);
                }
            }
        }

        public static void rightclicker()
        {
            brodm brodm = new brodm();
            Random rnd = new Random();

            if (brodm.GetCaptionOfActiveWindow().Contains("Minecraft")
                /*|| brodm.GetCaptionOfActiveWindow().Contains(textbox.Text)*/)
            {
                if (!brodm.ApplicationIsActivated())
                {
                    apimouse_event(16, 0, 0, 0, 0);
                    Thread.Sleep(rnd.Next(1, 5));
                    apimouse_event(8, 0, 0, 0, 0);
                }
            }
        }

        public static void blockhit(int slider)
        {
            Random rnd = new Random();
            apimouse_event(4, 0, 0, 0, 0);
            Thread.Sleep(rnd.Next(1, 5));

            int val;
            val = rnd.Next(0, 100);
            if (val <= (slider * 100))
            {
                apimouse_event(16, 0, 0, 0, 0);
                Thread.Sleep(rnd.Next(1, 5));
                apimouse_event(8, 0, 0, 0, 0);
            }
        }

        public static void jitter(int mX, int mY)
        {
            Random rnd = new Random();
            int rndnmb = rnd.Next(1, 10);
            if (rndnmb < 5)
            {
                Cursor.Position = new Point(mX - rnd.Next(1, 5), mY - rnd.Next(2, 4));
            }
            else
            {
                Cursor.Position = new Point(mX + rnd.Next(2, 4), mY + rnd.Next(1, 5));
            }
        }
    }
}

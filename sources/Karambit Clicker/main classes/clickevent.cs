using Guna.UI2.WinForms;
using System;
using System.Collections.Generic;
using System.Diagnostics;
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
        [DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
        private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

        [DllImport("user32.dll")]
        public static extern int SendMessage(IntPtr hWnd, int wMsg, int wParam, int lParam);

        public static void leftclicker(bool windowtitle, Guna2TextBox textbox)
        {
            brodm brodm = new brodm();
            Random rnd = new Random();
            IntPtr hWnd = brodm.GetForegroundWindow();

            if (windowtitle)
            {
                if (brodm.GetCaptionOfActiveWindow().Contains(textbox.Text))
                //brodm.GetCaptionOfActiveWindow().Contains("Minecraft")
                {
                    if (!brodm.ApplicationIsActivated())
                    {
                        if (brodm.currentPID == Process.GetCurrentProcess().Id || brodm.GetCaptionOfActiveWindow().Contains("Lunar Client") || brodm.GetCaptionOfActiveWindow().Contains("Badlion"))
                        {
                            SendMessage(hWnd, 513, 1, brodm.MakeLParam(Cursor.Position.X, Cursor.Position.Y));
                            Thread.Sleep(rnd.Next(1, 5));
                            SendMessage(hWnd, 514, 1, brodm.MakeLParam(Cursor.Position.X, Cursor.Position.Y));
                        }
                        else
                        {
                            apimouse_event(4, 0, 0, 0, 0);
                            Thread.Sleep(rnd.Next(1, 5));
                            apimouse_event(2, 0, 0, 0, 0);
                        }
                        /*SendMessage(hWnd, 513, 1, brodm.MakeLParam(positionx, positiony));
                        Thread.Sleep(rnd.Next(1, 5));
                        SendMessage(hWnd, 514, 1, brodm.MakeLParam(positionx, positiony));*/
                    }
                }
            }
            else
            {
                if (brodm.currentPID == Process.GetCurrentProcess().Id || brodm.GetCaptionOfActiveWindow().Contains("Lunar Client") || brodm.GetCaptionOfActiveWindow().Contains("Badlion"))
                {
                    SendMessage(hWnd, 513, 1, brodm.MakeLParam(Cursor.Position.X, Cursor.Position.Y));
                    Thread.Sleep(rnd.Next(1, 5));
                    SendMessage(hWnd, 514, 1, brodm.MakeLParam(Cursor.Position.X, Cursor.Position.Y));
                }
                else
                {
                    apimouse_event(4, 0, 0, 0, 0);
                    Thread.Sleep(rnd.Next(1, 5));
                    apimouse_event(2, 0, 0, 0, 0);
                }
            }
        }

        public static void rightclicker(bool windowtitle, Guna2TextBox textbox)
        {
            brodm brodm = new brodm();
            Random rnd = new Random();
            //IntPtr hWnd = brodm.GetForegroundWindow();

            if (windowtitle)
            {
                if (brodm.GetCaptionOfActiveWindow().Contains(textbox.Text))
                //brodm.GetCaptionOfActiveWindow().Contains("Minecraft")
                {
                    if (!brodm.ApplicationIsActivated())
                    {
                        apimouse_event(16, 0, 0, 0, 0);
                        Thread.Sleep(rnd.Next(1, 5));
                        apimouse_event(8, 0, 0, 0, 0);
                        /*SendMessage(hWnd, WM_RBUTTONDOWN, 1, brodm.MakeLParam(positionx, positiony));
                        Thread.Sleep(rnd.Next(1, 5));
                        SendMessage(hWnd, WM_RBUTTONUP, 1, brodm.MakeLParam(positionx, positiony));*/
                    }
                }
            }
            else
            {
                apimouse_event(16, 0, 0, 0, 0);
                Thread.Sleep(rnd.Next(1, 5));
                apimouse_event(8, 0, 0, 0, 0);
                /*SendMessage(hWnd, WM_RBUTTONDOWN, 1, brodm.MakeLParam(positionx, positiony));
                Thread.Sleep(rnd.Next(1, 5));
                SendMessage(hWnd, WM_RBUTTONUP, 1, brodm.MakeLParam(positionx, positiony));*/
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
                Cursor.Position = new Point(mX - rnd.Next(1, 8), mY - rnd.Next(1, 8));
            }
            else
            {
                Cursor.Position = new Point(mX + rnd.Next(1, 6), mY + rnd.Next(1, 6));
            }
        }
    }
}

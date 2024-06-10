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
        /*[DllImport("User32.Dll", EntryPoint = "PostMessageA")]
        private static extern bool PostMessage(IntPtr hWnd, uint msg, int wParam, int lParam);*/

        [DllImport(dllName: "user32.dll", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall)]
        public static extern void mouse_event(int flags, int cX, int cY, int button, int info);

        /*public const int WM_LBUTTONDOWN = 0x201;
        public const int WM_LBUTTONUP = 0x202;
        public const int WM_RBUTTONDOWN = 0x0204;
        public const int WM_RBUTTONUP = 0x0205;*/

        public static void leftclicker(bool mconly, bool breakblocks)
        {
            brodm brodm = new brodm();
            Random rnd = new Random();
            IntPtr hWnd;
            hWnd = brodm.GetForegroundWindow();

            if (mconly)
            {
                if (brodm.GetCaptionOfActiveWindow().Contains("Minecraft 1.7.10")
                    || brodm.GetCaptionOfActiveWindow().Contains("Minecraft 1.8.9")
                    || brodm.GetCaptionOfActiveWindow().Contains("Badlion")
                    || brodm.GetCaptionOfActiveWindow().Contains("Minecraft")
                    || brodm.GetCaptionOfActiveWindow().Contains("Lunar Client")
                    || brodm.GetCaptionOfActiveWindow().Contains("PvPLounge Client")
                    || brodm.GetCaptionOfActiveWindow().Contains("CheatBreaker")
                    || brodm.GetCaptionOfActiveWindow().Contains("CheatBreaker")
                    || brodm.GetCaptionOfActiveWindow().Contains("Labymod")
                    || brodm.GetCaptionOfActiveWindow().Contains("Cosmic Client"))
                {
                    if (!brodm.ApplicationIsActivated())
                    {
                        /*PostMessage(hWnd, WM_LBUTTONDOWN, 0, 0);
                        Thread.Sleep(rnd.Next(1, 3));
                        PostMessage(hWnd, WM_LBUTTONUP, 0, 0);*/
                        mouse_event(4, 0, 0, 0, 0);
                        Thread.Sleep(rnd.Next(1, 5));
                        mouse_event(2, 0, 0, 0, 0);
                        /*SendMessage(hWnd, WM_LBUTTONDOWN, 1, brodm.MakeLParam(mX, mY));
                        Thread.Sleep(rnd.Next(1, 5));
                        SendMessage(hWnd, WM_LBUTTONUP, 1, brodm.MakeLParam(mX, mY));*/
                    }
                }
            }
            else
            {
                mouse_event(4, 0, 0, 0, 0);
                Thread.Sleep(rnd.Next(1, 5));
                mouse_event(2, 0, 0, 0, 0);
                /*PostMessage(hWnd, WM_LBUTTONDOWN, 0, 0);
                Thread.Sleep(rnd.Next(1, 3));
                PostMessage(hWnd, WM_LBUTTONUP, 0, 0);*/
            }

            if (breakblocks)
            {
                if (brodm.GetCaptionOfActiveWindow().Contains("Minecraft 1.7.10")
                    || brodm.GetCaptionOfActiveWindow().Contains("Minecraft 1.8.9")
                    || brodm.GetCaptionOfActiveWindow().Contains("Badlion")
                    || brodm.GetCaptionOfActiveWindow().Contains("Minecraft")
                    || brodm.GetCaptionOfActiveWindow().Contains("Lunar Client")
                    || brodm.GetCaptionOfActiveWindow().Contains("PvPLounge Client")
                    || brodm.GetCaptionOfActiveWindow().Contains("CheatBreaker")
                    || brodm.GetCaptionOfActiveWindow().Contains("CheatBreaker")
                    || brodm.GetCaptionOfActiveWindow().Contains("Labymod")
                    || brodm.GetCaptionOfActiveWindow().Contains("Cosmic Client"))
                {
                    if (!brodm.ApplicationIsActivated())
                    {
                        /*PostMessage(hWnd, WM_LBUTTONDOWN, 0, 0);
                        Thread.Sleep(rnd.Next(1, 3));
                        PostMessage(hWnd, WM_LBUTTONUP, 0, 0);*/
                        mouse_event(4, 0, 0, 0, 0);
                        mouse_event(2, 0, 0, 0, 0);
                        /*SendMessage(hWnd, WM_LBUTTONDOWN, 1, brodm.MakeLParam(mX, mY));
                        Thread.Sleep(rnd.Next(1, 5));
                        SendMessage(hWnd, WM_LBUTTONUP, 1, brodm.MakeLParam(mX, mY));*/
                    }
                }
            }
            else
            {
                mouse_event(4, 0, 0, 0, 0);
                mouse_event(2, 0, 0, 0, 0);
                /*PostMessage(hWnd, WM_LBUTTONDOWN, 0, 0);
                Thread.Sleep(rnd.Next(1, 3));
                PostMessage(hWnd, WM_LBUTTONUP, 0, 0);*/
            }
        }

        public static void rightclicker(bool mconly)
        {
            brodm brodm = new brodm();
            Random rnd = new Random();
            /*IntPtr hWnd;
            hWnd = brodm.GetForegroundWindow();*/

            if (mconly)
            {
                if (brodm.GetCaptionOfActiveWindow().Contains("Minecraft 1.7.10")
                    || brodm.GetCaptionOfActiveWindow().Contains("Minecraft 1.8.9")
                    || brodm.GetCaptionOfActiveWindow().Contains("Badlion")
                    || brodm.GetCaptionOfActiveWindow().Contains("Minecraft")
                    || brodm.GetCaptionOfActiveWindow().Contains("Lunar Client")
                    || brodm.GetCaptionOfActiveWindow().Contains("PvPLounge Client")
                    || brodm.GetCaptionOfActiveWindow().Contains("CheatBreaker")
                    || brodm.GetCaptionOfActiveWindow().Contains("Offline CheatBreaker")
                    || brodm.GetCaptionOfActiveWindow().Contains("Labymod")
                    || brodm.GetCaptionOfActiveWindow().Contains("Cosmic Client"))
                {
                    if (!brodm.ApplicationIsActivated())
                    {
                        mouse_event(10, 0, 0, 0, 0);
                        Thread.Sleep(rnd.Next(1, 5));
                        mouse_event(8, 0, 0, 0, 0);
                        /*PostMessage(hWnd, WM_RBUTTONDOWN, 0, 0);
                        Thread.Sleep(rnd.Next(1, 3));
                        PostMessage(hWnd, WM_RBUTTONUP, 0, 0);*/
                    }
                }
            }
            else
            {
                mouse_event(10, 0, 0, 0, 0);
                Thread.Sleep(rnd.Next(1, 5));
                mouse_event(8, 0, 0, 0, 0);
                /*PostMessage(hWnd, WM_RBUTTONDOWN, 0, 0);
                Thread.Sleep(rnd.Next(1, 3));
                PostMessage(hWnd, WM_RBUTTONUP, 0, 0);*/
            }
        }

        public static void blockhit(int slider)
        {
            Random rnd = new Random();
            mouse_event(4, 0, 0, 0, 0);
            Thread.Sleep(rnd.Next(1, 5));

            int val;
            val = rnd.Next(0, 100);
            if (val <= (slider * 100))
            {
                mouse_event(10, 0, 0, 0, 0);
                Thread.Sleep(rnd.Next(1, 5));
                mouse_event(8, 0, 0, 0, 0);
            }
        }

        public static void jitter(int mX, int mY)
        {
            Random rnd = new Random();
            int rndnmb = rnd.Next(1, 10);
            if (rndnmb < 5)
            {
                Cursor.Position = new Point(mX - rnd.Next(1, 3), mY - rnd.Next(2, 4));
            }
            else
            {
                Cursor.Position = new Point(mX + rnd.Next(2, 4), mY + rnd.Next(1, 3));
            }
        }
    }
}

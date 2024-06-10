using System;
using System.Drawing;
using System.IO;
using System.Management;
using System.Media;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;
using static Dope.utils.Imports;

namespace Dope.utils
{
    class Utils
    {
        public const int WM_LBUTTONDOWN = 0x0201;
        public const int WM_LBUTTONUP = 0x0202;
        public const int WM_RBUTTONDOWN = 0x0204;
        public const int WM_RBUTTONUP = 0x0205;

        public static Random rnd = new Random();

        public static string GetDiskId()
        {
            return GetDiskId("").ToLower();
        }
        private static string GetDiskId(string diskLetter)
        {
            //Find first drive
            if (string.IsNullOrEmpty(diskLetter))
            {
                foreach (var compDrive in DriveInfo.GetDrives())
                {
                    if (compDrive.IsReady)
                    {
                        diskLetter = compDrive.RootDirectory.ToString();
                        break;
                    }
                }
            }
            if (!string.IsNullOrEmpty(diskLetter) && diskLetter.EndsWith(":\\"))
            {
                //C:\ -> C
                diskLetter = diskLetter.Substring(0, diskLetter.Length - 2);
            }
            var disk = new ManagementObject(@"win32_logicaldisk.deviceid=""" + diskLetter + @":""");
            disk.Get();

            var volumeSerial = disk["VolumeSerialNumber"].ToString();
            disk.Dispose();

            return volumeSerial;
        }

        public static bool MConTop()
        {
            return FindWindow("LWJGL", null) == GetForegroundWindow();
        }

        public static bool RclickDown()
        {
            return (GetAsyncKeyState(0x02) & 0x8000) != 0;
        }

        public static bool LclickDown()
        {
            return (GetAsyncKeyState(0x01) & 0x8000) != 0;
        }

        public static void SendLeftClick()
        {
            SendMessage(GetForegroundWindow(), WM_LBUTTONDOWN,0,0);
            Thread.Sleep(rnd.Next(10, 40));
            SendMessage(GetForegroundWindow(), WM_LBUTTONUP,0,0);
        }

        public static void SendLeftBreak()
        {
            SendMessage(GetForegroundWindow(), WM_LBUTTONUP, 0, 0);
            SendMessage(GetForegroundWindow(), WM_LBUTTONDOWN, 0, 0);
        }

        public static void SendRightClick()
        {
            SendMessage(GetForegroundWindow(), WM_RBUTTONDOWN, 0, 0);
            Thread.Sleep(rnd.Next(10, 40));
            SendMessage(GetForegroundWindow(), WM_RBUTTONUP, 0, 0);
        }

        public static void ShakePointer(int strengh)
        {
            POINT lpPoint;
            GetCursorPos(out lpPoint);
            int direction = rnd.Next(3);
            int Pixels = rnd.Next(-strengh, strengh);
            switch (direction)
            {
                case 0:
                    Cursor.Position = new Point(lpPoint.x + Pixels, lpPoint.y - Pixels);
                    break;
                case 1:
                    Cursor.Position = new Point(lpPoint.x - Pixels, lpPoint.y + Pixels);
                    break;
                case 2:
                    Cursor.Position = new Point(lpPoint.x + Pixels, lpPoint.y + Pixels);
                    break;
                case 3:
                    Cursor.Position = new Point(lpPoint.x - Pixels, lpPoint.y - Pixels);
                    break;
            }

        }

        public static void ClickSound(string s)
        {
            Stream Sound = Properties.Resources.Default;
            switch (s)
            {
                case "Regular":
                    Sound = Properties.Resources.Regular;
                    break;
                case "G502":
                    Sound = Properties.Resources.G502;
                    break;
                case "GPro":
                    Sound = Properties.Resources.GPro;
                    break;
                case "G303":
                    Sound = Properties.Resources.G303;
                    break;
                case "HP":
                    Sound = Properties.Resources.HP;
                    break;
                default:
                    break;
            }
            new SoundPlayer(Sound).Play();
        }

        public static void UpdateVolume(int volume)
        {
            int NewVolume = ((ushort.MaxValue / 10) * volume);
            // Set the same volume for both the left and the right channels
            uint NewVolumeAllChannels = (((uint)NewVolume & 0x0000ffff) | ((uint)NewVolume << 16));
            // Set the volume
            waveOutSetVolume(IntPtr.Zero, NewVolumeAllChannels);
        }

        public static bool Playing()
        {
            var h = Cursors.WaitCursor.Handle;

            CURSORINFO pci;
            pci.cbSize = Marshal.SizeOf(typeof(CURSORINFO));
            GetCursorInfo(out pci);

            string a = pci.hCursor.ToString();
            string b = Cursor.Current.Handle.ToString();

            //Console.WriteLine(a + " | " + b);

            return pci.hCursor.ToString() != Cursor.Current.Handle.ToString();
        }
    }
}

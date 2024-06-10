using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace Lavy
{
    class Worker
    {

            private volatile bool running = true;
            private MouseHook mouseHook = new MouseHook();
            public const int MOUSEEVENTF_LEFTDOWN = 2;
            public const int MOUSEEVENTF_LEFTUP = 4;
            public const int MOUSEEVENTF_RIGHTDOWN = 8;
            public const int MOUSEEVENTF_RIGHTUP = 16;
            public bool leftDown;
        public bool rightDown;
            private int mouseX;
            private int mouseY;
            private bool first;

            [DllImport("user32.dll")]
            public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

            public void PerformLeftClick(int xpos, int ypos)
            {
                Worker.mouse_event(2, xpos, ypos, 0, 0);
            }

            public void PerformRightClick(int xpos, int ypos)
            {
                Worker.mouse_event(8, xpos, ypos, 0, 0);
            }

            private void mouseMove(MouseHook.MSLLHOOKSTRUCT mouseStruct)
            {
                this.mouseX = mouseStruct.pt.x;
                this.mouseY = mouseStruct.pt.y;
            }

            public Worker()
            {
                this.mouseHook.MouseMove += new MouseHook.MouseHookCallback(this.mouseMove);
                this.mouseHook.LeftButtonDown += new MouseHook.MouseHookCallback(this.leftMouseDown);
                this.mouseHook.LeftButtonUp += new MouseHook.MouseHookCallback(this.leftMouseUp);
                this.mouseHook.RightButtonDown += new MouseHook.MouseHookCallback(this.rightMouseDown);
                this.mouseHook.RightButtonUp += new MouseHook.MouseHookCallback(this.rightMouseUp);
                this.mouseHook.Install();
               
            }

          
            private void rightMouseDown(MouseHook.MSLLHOOKSTRUCT mouseStruct)
            {
                this.rightDown = true;
            }

            private void rightMouseUp(MouseHook.MSLLHOOKSTRUCT mouseStruct)
            {
                this.rightDown = false;
            }

            private void leftMouseUp(MouseHook.MSLLHOOKSTRUCT mouseStruct)
            {
                this.leftDown = false;
            }

            private void leftMouseDown(MouseHook.MSLLHOOKSTRUCT mouseStruct)
            {
                this.leftDown = true;
            }

            public void Click()
            {
                    if (this.leftDown)
                    {

                PerformLeftClick(this.mouseX, this.mouseY);
                            
                    }
              
            }

        }
    

}


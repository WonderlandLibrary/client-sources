using Dope.Theme;
using Dope.utils;
using Dope.utils.Hook;
using Dope.views;
using System;
using System.Threading;
using System.Windows.Forms;
using static Dope.utils.Imports;

namespace Dope
{
    public partial class Main : Form
    {
        Config cfg;
        Form[] Views;
        MouseHook mousehook;
        KeyboardHook keyBoardHook;
        string hwid;

        public Main()
        {
            InitializeComponent();
            hwid = Utils.GetDiskId();
            cfg = new Config();
            InitViews();
            InitHook();
            Text = cfg.CurrentSlot.ToString();
            //starts threads
            new Thread(() => LeftClick()).Start();
            new Thread(() => RightClick()).Start();
        }

        //=============================================================

        private void Main_FormClosing(object sender, FormClosingEventArgs e)
        {
            DeleteHook();
            cfg.Seldestruct = true;
        }

        private void InitViews()
        {
            Views = new Form[] { new Left(cfg,this),new Jitter(cfg, this), new ClickSound(cfg, this), new Configs(), new Settings(cfg,this), new Right(cfg, this) };
            foreach (Form f in Views)
            {
                f.TopLevel = false;
                ViewsList.Controls.Add(f);
                f.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right;
                f.Show();
            }
            Views[0].BringToFront();
        }

        public void ChangeViews(int index)
        {
            Views[index].BringToFront();
        }

        private void side1_IndexChange(object sender, EventArgs e)
        {
            Views[side1.Index].BringToFront();
        }

        //=============================================================

        private void InitHook()
        {
            //create object
            mousehook = new MouseHook();
            keyBoardHook = new KeyboardHook();

            //register callbacks
            mousehook.MouseWheel += new MouseHookCallback(MouseEvent);
            keyBoardHook.KeyDown += new KeyboardHookCallback(KeyboardEvent);

            //install
            mousehook.Install();
            keyBoardHook.Install();
        }

        private void DeleteHook()
        {
            //unregister callbacks
            mousehook.MouseWheel -= new MouseHookCallback(MouseEvent);
            keyBoardHook.KeyDown -= new KeyboardHookCallback(KeyboardEvent);

            //ininstall
            mousehook.Uninstall();
            keyBoardHook.Uninstall();
        }

        public int getDelta(uint data)
        {
            int delta = (int)(data & 0xffff0000) >> 16;
            if (delta > SystemInformation.MouseWheelScrollDelta)
            {
                delta = delta - ushort.MaxValue + 1;
            }
            return delta;
        }

        public void MouseEvent(MSLLHOOKSTRUCT me)
        {
            if (getDelta(me.mouseData) > 0)
            {
                cfg.CurrentSlot--;
                if (cfg.CurrentSlot < 0)
                {
                    cfg.CurrentSlot = 8;
                }
            }
            else
            {
                cfg.CurrentSlot++;
                if (cfg.CurrentSlot > 8)
                {
                    cfg.CurrentSlot = 0;
                }
            }
            Text = cfg.CurrentSlot.ToString();
        }

        public void KeyboardEvent(VKeys k)
        {
            int Keypressed = (int)k;
            for(int i = 0; i < 9; i++)
            {
                if (Keypressed == cfg.Left_SlotList.GetKeyIndex(i) || Keypressed == cfg.right_SlotList.GetKeyIndex(i))
                {
                    cfg.CurrentSlot = i;
                }
            }
            Text = cfg.CurrentSlot.ToString();
        }

        //=============================================================

        void LeftClick()
        {
            while (cfg != null || cfg.Seldestruct)
            {
                Start:
                if (cfg.Seldestruct) break;
                int delay = (int)(1000 / (Utils.rnd.Next() % (cfg.left_Max - cfg.left_Min) + cfg.left_Min) * 0.8);
                if (cfg.left_IA)
                {
                    //je ferai un truc mieux plus tard
                    delay = (delay * (Utils.rnd.Next(8, 15) / 10));
                }
                if (cfg.Left_Slots && !cfg.Left_SlotList.GetBoolIndex(cfg.CurrentSlot))
                {
                    Thread.Sleep(1);
                    goto Start;
                }
                if (cfg.left_focus && !Utils.MConTop())
                {
                    Thread.Sleep(1);
                    goto Start;
                }
                if (cfg.Left_Slots && !cfg.Left_SlotList.GetBoolIndex(cfg.CurrentSlot))
                {
                    Thread.Sleep(1);
                    goto Start;
                }
                if (Utils.LclickDown() && cfg.left_Click)
                {
                    new Thread(() =>
                    {
                        if (cfg.left_Break)
                        {
                            Utils.SendLeftBreak();
                        }
                        else
                        {
                            Utils.SendLeftClick();
                        }
                        if (cfg.Sound)
                        {
                            Utils.ClickSound(cfg.Sound_preset);
                        }
                        if (cfg.Jitter)
                        {
                            Utils.ShakePointer(cfg.Jitter_strengh);
                        }
                    }).Start();
                }
                Thread.Sleep(delay);
            }
        }

        void RightClick()
        {
            while (cfg != null || cfg.Seldestruct)
            {
            Start:
                if (cfg.Seldestruct) break;
                int delay = (int)(1000 / (Utils.rnd.Next() % (cfg.right_Max - cfg.right_Min) + cfg.right_Min) * 0.8);
                if (cfg.right_IA)
                {
                    //je ferai un truc mieux plus tard
                    delay = (delay * (Utils.rnd.Next(8, 15) / 10));
                }
                if (cfg.right_focus && !Utils.MConTop())
                {
                    Thread.Sleep(1);
                    goto Start;
                }
                if (cfg.right_Slots && !cfg.right_SlotList.GetBoolIndex(cfg.CurrentSlot))
                {
                    Thread.Sleep(1);
                    goto Start;
                }
                if (Utils.RclickDown() && cfg.right_Click)
                {
                    new Thread(() =>
                    {
                        Utils.SendRightClick();
                        if (cfg.Sound)
                        {
                            Utils.ClickSound(cfg.Sound_preset);
                        }
                        if (cfg.Jitter)
                        {
                            Utils.ShakePointer(cfg.Jitter_strengh);
                        }
                    }).Start();                  
                }
                Thread.Sleep(delay);
            }
        }
    }
}

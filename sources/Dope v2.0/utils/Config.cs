using Dope.Theme;
using System.Collections.Generic;

namespace Dope.utils
{
    public class Config
    {
        //left clicker
        public bool left_Click = false;
        public int left_Key = 0;
        public double left_Max = 13;
        public double left_Min = 9;
        public bool Left_playing = true;
        public bool left_IA = false;
        public bool left_Break = false;
        public bool left_focus = true;
        public bool Left_Slots = false;
        public Slot Left_SlotList = new Slot();

        //right clicker
        public bool right_Click = false;
        public int right_Key = 0;
        public double right_Max = 13;
        public double right_Min = 9;
        public bool right_playing = true;
        public bool right_IA = false;
        public bool right_focus = true;
        public bool right_Slots = false;
        public Slot right_SlotList = new Slot();

        //Jitter
        public bool Jitter = false;
        public int Jitter_Key = 0;
        public int Jitter_strengh = 5;

        //ClickSound
        public bool Sound = false;
        public int Sound_Key = 0;
        public int Sound_Volume = 10;
        public string Sound_preset = "Default";

        //Config

        //Settings
        public bool Hide = false;
        public int Hide_Key = 0;
        public bool AlwaysOnTop = false;

        //Global
        public bool Seldestruct = false;
        public int CurrentSlot = 0;
    }
}

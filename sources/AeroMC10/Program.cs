using Binarysharp.MemoryManagement;
using System;
using System.Collections.Generic;
using System.Diagnost
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace AeroMC10
{
    class Program
    {

        static Process process;
        static VAMemory vam;
        static IntPtr baseAddress;

        static void Main(string[] args)
        {
            Console.Title = "AeroMC10 by Aeroverra";
            Console.WriteLine("Welcome to AeroMC10 by Aeroverra");

            process = Process.GetProcessesByName("Minecraft.Windows").FirstOrDefault();

            //Find modules in the event of multiple dll pointers
            //var modules = process.Modules.Cast<ProcessModule>().ToList().OrderBy(x=>x.FileName).ToList();
            //foreach (var module in modules)
            //{
            //    Console.WriteLine($"{module.FileName}");
            //}

            vam = new VAMemory("Minecraft.Windows");
            Console.WriteLine($"Minecraft Found!");
            baseAddress = process.MainModule.BaseAddress;
            Console.WriteLine();
            //ToggleThunder();
            while (true)
            {

                var output = "";
                Console.WriteLine("--- Menu ---");
                Console.WriteLine("[1] Toggle InstaBreak");
                Console.WriteLine("[2] Toggle Fly");
                Console.WriteLine("[3] Set Anvil Cost To 1");
                Console.WriteLine("[4] Toggle Brightness");
                var input = Console.ReadLine();
                int option = 0;
                int.TryParse(input, out option);
                switch (option)
                {
                    case 0:
                        output = "Invalid Input";
                        break;
                    case 1:
                        ToggleInstaBreak();
                        output = "InstaBreak Toggled";
                        break;
                    case 2:
                        ToggleFly();
                        output = "Fly Toggled";
                        break;
                    case 3:
                        SetAnvilCost();
                        output = "Anvil Cost Set To 1.";
                        break;
                    case 4:
                        ToggleBrightness();
                        output = "Brightness Toggled";
                        break;

                }
                Console.Clear();
                Console.WriteLine(output);
                Console.WriteLine();
            }






        }
        public static void ToggleThunder()
        {
            //Scan for 128 when thundering and 0 when not
            //63 on and 0 off also works  on == trident shoots lightning
            //63 rain animation also exists  0 would be off. 62 for a very short second in between?
            //128 and 0 also has a weather animation
            //03656F68 798 48 28 E0 138


            int staticPointerOffset = 0x03656F68;
            int[] offsets = new int[] { 0x798, 0x48, 0x28, 0xE0, 0x138 };

            while (true)
            {
                // var pointer = FindPointer(baseAddress, staticPointerOffset, offsets);
                var pointer = new IntPtr(0x1792582093A);
                var value = vam.ReadInt64(pointer);
                Console.WriteLine(value);
            }
        }
        public static void ToggleBrightness()
        {
            //Search for float. If brightness is 66 than the float will be between .66 and .68
            //036542E8 20 C10 B0 138 F0
            int staticPointerOffset = 0x036542E8;
            int[] offsets = new int[] { 0x20, 0xC10, 0xB0, 0x138, 0xF0 };

            // while (true)
            // {
            var pointer = FindPointer(baseAddress, staticPointerOffset, offsets);
            var value = vam.ReadFloat(pointer);
            Console.WriteLine(value);
            if (value > 1)
            {
                vam.WriteFloat(pointer, 1);
                return;
            }
            vam.WriteFloat(pointer, 15);


            // }
        }
        public static void SetAnvilCost()
        {
            int staticPointerOffset = 0x035F9470;
            int[] offsets = new int[] { 0x70, 0xBC0, 0x38, 0x10, 0x30, 0x50, 0xA8 };


            Console.WriteLine("Setting Anvil Cost to 1 for 10 seconds. You may need to click the side of the interface to update the price.");
            for (int x = 0; x <= 20; x++)
            {
                var pointer = FindPointer(baseAddress, staticPointerOffset, offsets);
                var value = vam.ReadInt32(pointer);
                vam.WriteInt32(pointer, 1);
                //while (true)
                //{
                //    Console.WriteLine(pointer.AsHex());
                //    Thread.Sleep(5000);
                //}
                Thread.Sleep(500);
            }
        }
        static volatile bool FlyIsOn = false;
        public static void ToggleFly()
        {
            if (FlyIsOn)
            {
                FlyIsOn = false;
                return;
            }
            FlyIsOn = true;
            int staticPointerOffset = 0x03655728;
            int[] offsets = new int[] { 0xA8, 0x20, 0x48, 0x10, 0x58, 0x0, 0x8B8 };

            var thread = new Thread(() =>
            {
                while (FlyIsOn)
                {
                    var pointer = FindPointer(baseAddress, staticPointerOffset, offsets);
                    var value = vam.ReadInt32(pointer);
                    //Console.WriteLine($"value: {value}");
                    vam.WriteInt32(pointer, 1);
                    Thread.Sleep(100);
                }
            });
            thread.Start();



        }
        public static void ToggleInstaBreak()
        {
            int staticPointerOffset = 0x3683F48;
            int[] offsets = new int[] { 0xD0, 0x58, 0xB8, 0x158, 0x140, 0x1C84 };

            var pointer = FindPointer(baseAddress, staticPointerOffset, offsets);

            var value = vam.ReadInt32(pointer);
            if (value == 1)
            {
                vam.WriteInt32(pointer, 0);
            }
            else
            {
                vam.WriteInt32(pointer, 1);
            }

        }
        public static IntPtr FindPointer(IntPtr applicationBaseAddress, int baseAddressOffset, int[] offsets)
        {
            //Get the start address by adding the static offset to the base minecraft memory adress
            var pointer = IntPtr.Add(applicationBaseAddress, baseAddressOffset);

            //Read the first value
            pointer = (IntPtr)vam.ReadInt64(pointer);

            //Itterate through the offsets and add them to the values read to get the next one
            for (int x = 0; x < offsets.Count(); x++)
            {
                var offset = offsets[x];
                pointer = IntPtr.Add(pointer, offset);

                //Only set this if its not the last offset. the last offset is the value we are looking to manipulate
                if (x != offsets.Count() - 1)
                {
                    pointer = (IntPtr)vam.ReadInt64(pointer);
                }

            }
            return pointer;
        }

    }
}

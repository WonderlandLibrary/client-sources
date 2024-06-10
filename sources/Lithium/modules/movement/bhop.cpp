#include "..\..\klynt.h"

static double defaultbhop = 0.3;

namespace v
{
    namespace modules
    {
        namespace bhop
        {
            void enable() {
                double newbhop = defaultbhop * v::modules::bhop::speed;
                double buffer;
                for (size_t x = 0; x < v::modules::bhop::locations.size(); x++) {

                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::bhop::locations[x], &buffer, sizeof(double), 0)) {
                        if (buffer <= 30.0 && buffer >= 0.3) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::bhop::locations[x], &newbhop, sizeof(double), 0);
                        }
                        else {
                            v::modules::bhop::locations.erase(v::modules::bhop::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::bhop::locations.erase(v::modules::bhop::locations.begin() + x);
                        x = 0;
                    }
                }

            }

            void disable() {
                double buffer;
                for (size_t x = 0; x < v::modules::bhop::locations.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::bhop::locations[x], &buffer, sizeof(double), 0)) {
                        if (buffer <= 30.0 && buffer >= 0.3) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::bhop::locations[x], &defaultbhop, sizeof(double), 0);
                        }
                        else {
                            v::modules::bhop::locations.erase(v::modules::bhop::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::bhop::locations.erase(v::modules::bhop::locations.begin() + x);
                        x = 0;
                    }
                }
            }

            void thread() {
                while (v::info::verified) {
                    if (v::modules::bhop::enabled) {
                        v::modules::bhop::used = true;
                        enable();
                    }
                    else {
                        if (v::modules::bhop::used) {
                            disable();
                            v::modules::bhop::used = false;
                        }

                    }

                    if (v::modules::bhop::enabled) {
                        std::this_thread::sleep_for(std::chrono::milliseconds(500));
                    }
                    else {
                        std::this_thread::sleep_for(std::chrono::milliseconds(50));
                    }

                }

            }
        }
    }
}
#include "..\..\klynt.h"
static float defaultesp = 2.5f;
static float newesp = 3.3699999f;

namespace v
{
    namespace modules
    {
        namespace esp
        {
            void enable() {
                float buffer;
                for (size_t x = 0; x < v::modules::esp::locations.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::esp::locations[x], &buffer, sizeof(float), 0)) {
                        if (buffer >= 2.5f && buffer <= 3.3699999f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::esp::locations[x], &newesp, sizeof(float), 0);
                        }
                        else {
                            v::modules::esp::locations.erase(v::modules::esp::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::esp::locations.erase(v::modules::esp::locations.begin() + x);
                        x = 0;
                    }
                }


            }

            void disable() {
                float buffer;
                for (size_t x = 0; x < v::modules::esp::locations.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::esp::locations[x], &buffer, sizeof(float), 0)) {
                        if (buffer >= 2.5f && buffer <= 3.3699999f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::esp::locations[x], &defaultesp, sizeof(float), 0);
                        }
                        else {
                            v::modules::esp::locations.erase(v::modules::esp::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::esp::locations.erase(v::modules::esp::locations.begin() + x);
                        x = 0;
                    }
                }

            }

            void thread() {
                while (v::info::verified) {
                    if (v::modules::esp::enabled) {
                        v::modules::esp::used = true;
                        enable();
                    }
                    else {
                        if (v::modules::esp::used) {
                            disable();
                            v::modules::esp::used = false;
                        }

                    }

                    if (v::modules::esp::enabled) {
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
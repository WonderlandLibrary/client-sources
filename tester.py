# REMOVE FILE BEFORE PUSHING
# If you see this on github then sorry lol

import os

while True:
    term = input("enter: ")
    for source_name in os.listdir("sources"):
        if term.lower() in source_name.lower():
            print(source_name)
            

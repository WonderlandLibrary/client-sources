import os

template = open("README.template", "r").read()

output = template

sources_out = ""
available_out = ""
for source_name in os.listdir("sources"):
    sources_out += f"- {source_name}\n"
    available_out += f"{source_name}\n"

output = output.replace("sources-here", sources_out)    

readme = open("README.md", "w")
readme.write(output)
readme.close()

available = open("available", "w")
available.write(available_out)
available.close()

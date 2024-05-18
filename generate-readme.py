import os

template = open("README.template", "r").read()

output = template

sources_out = ""
for source_name in os.listdir("sources"):
    sources_out += f"- {source_name}\n"

output = output.replace("sources-here", sources_out)    

f = open("README.md", "w")
f.write(output)
f.close()

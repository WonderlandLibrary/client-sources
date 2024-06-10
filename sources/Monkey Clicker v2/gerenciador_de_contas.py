import time
import mysql.connector
import sys

import itertools
import threading

from colorama import Fore, Style, init

init(convert=True)

# https://youtu.be/umPfQvDyb-w
done = False
# here is the animation


class MyConverter(mysql.connector.conversion.MySQLConverter):
    def row_to_python(self, row, fields):
        row = super(MyConverter, self).row_to_python(row, fields)

        def to_unicode(col):
            if type(col) == bytearray:
                return col.decode("utf-8")
            return col

        return [to_unicode(col) for col in row]


def animate():
    for c in itertools.cycle(["|", "/", "-", "\\"]):
        if done:
            break
        sys.stdout.write("\rloading " + c)
        sys.stdout.flush()
        time.sleep(0.1)


if len(sys.argv) < 2:
    print(
        Fore.YELLOW
        + """
    escolha o modo:
    ativar conta - python3 %s ativar
    desativar conta - python3 %s desativar
    """
        % (sys.argv[0], sys.argv[0])
        + Style.RESET_ALL
    )
    sys.exit()

if (sys.argv[1] != "ativar") and (sys.argv[1] != "desativar"):
    print(
        Fore.RED
        + """
    argumento inválido (talvez vc errou algo)
    """
        + Style.RESET_ALL
    )
    sys.exit()

operacao = 0

if sys.argv[1] == "ativar":
    operacao = 0
else:
    operacao = 1


print(" ")
if operacao:
    print(Fore.RED + "desativar conta" + Style.RESET_ALL)
else:
    print(Fore.GREEN + "ativar conta" + Style.RESET_ALL)


print(Fore.YELLOW + "username: " + Style.RESET_ALL, end="")
name = input()


print(" ")
t = threading.Thread(target=animate)
t.start()
cnx = mysql.connector.connect(
    user="uzhmwrsk_nigger",
    password="D9AwFyFQV1rdFlKDt",
    host="31.214.240.101",
    database="uzhmwrsk_monkey	",
)


query = """SELECT hwid_atual FROM loader WHERE name = '%s'"""

cursor = cnx.cursor(prepared=True)
cursor.execute(query % name)

if not cursor.fetchone():
    done = True
    print("\r            ")
    print("\r" + Fore.RED + "erro:  username não existe" + Style.RESET_ALL)
    sys.exit()
cursor.execute(query % name)
row1temp = cursor.fetchone()

row1 = ""
row1 = str(row1temp[0], "utf-8")


print("\r              ")
print("\r" + Fore.CYAN + "user hwid: %s" % row1 + Style.RESET_ALL)
query = """UPDATE loader SET hwid = ("%s") where name = "%s";"""
cursor.execute(query % (row1, name))


if operacao:
    query = """UPDATE loader SET ativado = 'false' where name = '%s';"""
else:
    query = """UPDATE loader SET ativado = 'true' where name = '%s';"""

cursor.execute(query % name)
cnx.commit()
done = True
print("\r             ")
if operacao:
    print("\r" + Fore.GREEN + "usuário desativado com sucesso!" + Style.RESET_ALL)
else:
    print("\r" + Fore.GREEN + "usuário ativado com sucesso" + Style.RESET_ALL)

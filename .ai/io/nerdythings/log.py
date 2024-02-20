# Apache License
# Version 2.0, January 2004
# Author: Eugene Tkachenko

class Log:
    # ANSI escape codes for some colors
    RED = '\033[31m'
    GREEN = '\033[32m'
    YELLOW = '\033[33m'
    RESET = '\033[0m'

    @staticmethod
    def print_red(*args):
        text = ' '.join(str(arg) for arg in args)
        print(f"{Log.RED}{text}{Log.RESET}")

    @staticmethod
    def print_green(*args):
        text = ' '.join(str(arg) for arg in args)
        print(f"{Log.GREEN}{text}{Log.RESET}")

    @staticmethod
    def print_yellow(*args):
        text = ' '.join(str(arg) for arg in args)
        print(f"{Log.YELLOW}{text}{Log.RESET}")

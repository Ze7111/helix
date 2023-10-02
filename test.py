func_cache = {}

def multiple_dispatch(*args):
    def inner(func):
        func_cache[args] = func
        def wrapper(*args):
            try:
                func = func_cache[tuple([type(arg) for arg in args])]
            except KeyError:
                raise TypeError("No matching function found")
            return func(*args)
        return wrapper
    return inner

# import final
from typing import Final

import traceback

def add(a, b):
    return a + b

# Get the python bytecode
import dis
dis.dis(add)

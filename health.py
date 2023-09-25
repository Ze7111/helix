from abc import ABC, abstractmethod

# Define the interface
class Animal(ABC): # interface

    @abstractmethod
    def eat(self):
        pass

    @abstractmethod
    def sound(self):
        pass

class Dog(Animal):
    def eat(self):
        print("Dog eats")

    def sound(self):
        print("Dog barks")

class Cat(Animal):
    def eat(self):
        print("Cat eats")

dog = Dog()
dog.eat()
dog.sound()
lambda x: x + 1; # is this a vaild lambda expression?
# yes, it is

cat = Cat()
cat.eat()
cat.sound()

import threading
# Create a thread
thread = threading.Thread(target=function)
threading.threa 
# Start the thread
thread.start()

# Join the thread
thread.join()
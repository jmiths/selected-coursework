#!/usr/bin/python
import sys
import random
f = open("p1/blah",'w')

for i in range(0,int(sys.argv[1])):
    f.write(str(random.randint(0,99)).zfill(2)+ " ")

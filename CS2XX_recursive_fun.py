#!/usr/bin/python
import sys

A = []

def fibm(n):
	while (len(A) <= 1):
		A.append(1)
	if (len(A) < n-1):
		fibm(n-1)
	if (len(A) == n-1):
		A.append( A[n-3] + A[n-2] )
	return A[n-1]

def main():
	print sys.argv[1] + " : " + str(fibm(int(sys.argv[1])))

if __name__ == "__main__":
	main()

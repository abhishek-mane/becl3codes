import sys
from xml.dom.minidom import parse
import xml.dom.minidom

class EightQueensProblem:
	@staticmethod
	def isConsistent(solList, n):
		for i in range(0, n):
			if(solList[i] == solList[n] or abs(solList[i] - solList[n]) == abs(i - n)):
				return False
		return True
	
	@staticmethod
	def printQueens(solList):
		for i in range(0,len(solList)):
			for j in range(0,len(solList)):
				if(solList[i] == j):
					sys.stdout.write("Q ")
				else:
					sys.stdout.write("* ")
			print ""
		print ""
		
	@staticmethod
	def iterate(solList, n):
		if(n == len(solList)):
			EightQueensProblem.printQueens(solList)
		else:
			for i in range(0,len(solList)):
				solList[n] = i
				if(EightQueensProblem.isConsistent(solList, n)):
					EightQueensProblem.iterate(solList, n+1)
		
	@staticmethod
	def Initialize(inputXMLFile):
		solList = [0, 0, 0, 0, 0, 0, 0, 0]
		DOMTree = xml.dom.minidom.parse(inputXMLFile)
		solution = DOMTree.documentElement
		rows = solution.getElementsByTagName("row")
		solList[0] = int(rows[0].childNodes[0].data)
		EightQueensProblem.iterate(solList, 1)

if(len(sys.argv) < 2):
	print "Usage : eightQueens.py inputXMLFile.xml"
	sys.exit(1);
EightQueensProblem.Initialize(str(sys.argv[1]))

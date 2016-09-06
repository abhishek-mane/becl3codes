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
	def iterate(solList, n, placedRow):
		if(n==placedRow):	
			if(EightQueensProblem.isConsistent(solList, n)):
				EightQueensProblem.iterate(solList, n+1, placedRow)
		else:
			if(n == len(solList)):
				EightQueensProblem.printQueens(solList)
			else:
				for i in range(0,len(solList)):
					solList[n] = i
					if(EightQueensProblem.isConsistent(solList, n)):
						EightQueensProblem.iterate(solList, n+1, placedRow)

	@staticmethod
	def Initialize(inputXMLFile):
		placedRow = 0
		solList = [0, 0, 0, 0, 0, 0, 0, 0]
		
		DOMTree = xml.dom.minidom.parse(inputXMLFile)
		solution = DOMTree.documentElement
		rows = solution.getElementsByTagName("row")
		for i in range(0,len(solList)):
			solList[i] = int(rows[i].childNodes[0].data)
			if(solList[i]!=-1):
				placedRow=i
				break

		EightQueensProblem.iterate(solList, 0, placedRow)

if(len(sys.argv) < 2):
	print "Usage : eightQueens.py inputXMLFile.xml"
	sys.exit(1);
EightQueensProblem.Initialize(str(sys.argv[1]))

from SHA1 import *
import sys
import socket

s = socket.socket()
host = "localhost"
port = 7685
s.bind(("localhost", port))
s.listen(5)

while True:
	c, addr = s.accept()
	print 'Got connection from', addr
	msg = c.recv(1024)
	print "Received msg    : ", msg
	hash = c.recv(4096)
	print "Received Hash   : ", hash
	cHash = sha1(msg)
	print "Calculated Hash : ", cHash
	
	print "-------------------------------"
	if(hash == cHash):
		print "hash matched... Message not altered."
	else:
		print "hash unmatched... Message altered."
	print "-------------------------------\n\n\n"
	c.close()

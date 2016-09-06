from SHA1 import *
import socket

s = socket.socket()
host = "localhost"
port = 7685
s.connect(("localhost", port))

msg = raw_input("Enter msg : ")
s.send(msg)
hash = sha1(msg)
print "Hash : ", hash
s.send(hash)
print "sending msg & hash..."
print "message & hash sent."
s.close()

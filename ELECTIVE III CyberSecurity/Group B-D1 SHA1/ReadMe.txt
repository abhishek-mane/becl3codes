
I'd advice you to please refer this code for SHA1, bcz java program we had earlier uses inbuilt sha1 algorithm.

code contains 3 files
1. Client.py : includes only socket programming code to create socket & sends msg recieved from user along with its
		calculated hash value.

2. Server.py : include socket programming code for receiving msg & hash from client. recalculate hash on received
		message. if recalculated hash & recieved hash matches, integrity of message is preserved.

3. SHA1.py   : includes sha1() function which is implementation of sha1 algorithm to calculate 160 bit
		hash of given message. this file acts as library for Client.py & Server.py
		
Happy Coding... :)

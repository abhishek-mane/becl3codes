folder contains 2 .war(web archive) files.
one shows each booth step within table
another shows only the multiplication (for those who find html table creation difficult).

for running program one of archive can be imported to eclipse for java EE developers. after importing if you find
some errors in import statements of Controller.java, it means your tomcat server is not well configured within
imported project. for that just right click on project - goto properties type 'runtime' in search box on upper left
then goto targeted runtime tab & make sure that checkbox for server is selected else select it & ok
for entire process above you must have tomcat server already configured with eclipse.

on other hand, you can directly extract war file inside ROOT(TOMCAT/webapps/ROOT) directory of your tomcat & start it &
run it from browser.

Happy Coding... :)

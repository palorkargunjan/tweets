*************************** Application *******************************
#Application Info
This application prompts for the selection of a topic on startup, then based on the
selection of topic, it will search all related tweets in the twitter using twitter4j api.
Then it'll store those collected tweets into a file named with that topic name in a specified
folder.

#Requirements
-> Twitter4j jar file
-> Twitter api keys
	- consumer api key
	- consumer api secret key
	- access token
	- access token secret
-> IntelliJ, Eclipse Java IDE or alternative

#Setup

-> Install IntelliJ and open this java project.
--> open project structure and add twitter jars into path library
-> Go to src/com/application/Properties.java
-> update the following values
	- consumer api key
	- consumer api secret key
	- access token
	- access token secret
	- path [to store the files in a directory]
	- topics [ predefined topics are saved here]
	
--> Run the Application.java class
--> Follow the instructions on console
--> Output as text file with topic name will be stored in specified path

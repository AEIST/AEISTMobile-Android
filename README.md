AEISTMobile-Android
===================

The developer has it own fork of the project, on which implements new features and bug fixes, when finished, 
it will be contributed to the main tree through a "Pull request", where a gate keeper will accept the changes 
or request for further changes. Once the "Pull request" is accepted, 
it will be merge into the main tree, which will go into production.

Concepts
--------
+ Upstream: The main git tree of AEIST Mobile (git@github.com:AEIST/AEISTMobile-Android.git)
+ Fork: A clone of upstream in github, each developer has its own fork.


Process setup
--------
1. Developer forks in github AEIST Mobile project to its own github fork.
2. Clones his/her github fork in the development computer (folder - $PROJECT).
3. Open Eclipse and do:
	
	* Refactor project name
		> Right-Click on project -> Refactor -> Rename

	* Get project code
		> New -> Android Project from Existing Code -> $PROJECT -> Finish

	* Setup the right Android version
		> Project Properties -> Android -> Android 4.0

4. Run app

Process setup
--------
Check info at: 
	
	https://github.com/AEIST/AEISTMobile-Backoffice/blob/master/README.md

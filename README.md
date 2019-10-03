* Create database in mysql :-
	database name - poc
	
* Run spring boot project :-
	open project folder in terminal > execute run command > clean install spring-boot:run
	
* Open swagger in browser with this below url and click on "Try it out" button for admin sign up in mysql database :-
	http://localhost:8080/swagger-ui.html#!/account/adminSignUpUsingPOST

* Open postman for call oauth2 login api 
	select method > POST
	paste this url > http://localhost:8080/oauth/token
	In authorization select "Basic Auth" > username = client > password = secret 
	> after that click on right side button "Update Request"
	> click on Body in title bar > select "x-www-form-urlencoded"
	>		key     :      value
		username	:	 adminstrator@poc.io
		password	:	123456
		grant_type	:	password
	> after that click on button "Send".
	
* So you can get "access_token" and "refresh_token" in that response.

* Now you can also login with using refresh_token in request. Follow below step to login with refresh_token.
	select method > POST
	paste this url > http://localhost:8080/oauth/token
	In authorization select "Basic Auth" > username = client > password = secret 
	> after that click on right side button "Update Request"
	> click on Body in title bar > select "x-www-form-urlencoded"
	>		key     	:      value
		grant_type		:	refresh_token
		client_id		:	client
		refresh_token	:	copy and paste refresh_token u get in above request.
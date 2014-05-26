# Introduction

This project is meant to replace the quickstart for Parse Projects/IntegratingFacebookTutorial by providing an 
Android project offering twitter and facebook login out of the box. It also allows Anonymous Parse login with 
the ability to link to Facebook/Twitter afterwards. I don't currently have email/password login option, but I 
might add it later. I have also added an optional "username" activity that will allow users to set a username 
and logic to check for uniqueness and validity. 

This is a modified version of the Parse Tutorial [Integrating Facebook in Android](https://www.parse.com/tutorials/integrating-facebook-in-android). 

# Setup

Clone/Fork this repo or download it. Set up twitter and facebook apps as described on their developer portals/in the tutorial described above. Paste the Parse App ID/Client Key, Facebook App ID, and Twitter Client Key/Secret into the proper spots in res/strings.xml.

# Features
## LoginActivity

If a user has not previously authenticated with the app, or has logged out, the user will see this. Otherwise it 
will launch the MainActivity. This LoginActivity has three buttons allowing the user to login anonymously, via 
facebook, or via twitter. Following a successful login, two things could happen. If the user has not ever set a 
username, the UsernameActivity is launched. Otherwise the MainActivity is launched.

## UsernameActivity

After logging in, new users will see this activity. It holds a single EditText to capture the username, a 
validation button to check that the username is available, and a "finish' button that finishes the set up. 
Clicking the validation button will perform a simple Parse query and then change the button to be green or red 
for a success or failure. If the name is already used it will display an appropriate message. Similarly, a 
message is shown if the username contains anything other than 0-9,A-Z (case doesn't matter).

## MainActivity

After logging in the user will see this activity. It merely shows the parse "username". If the user is logged in via facebook or twitter, the options menu will have the option to logout. If the user is logged in anonymously, the options menu will allow users to link to either twitter or facebook. 

![Log In Pic](http://media.tumblr.com/98d8679e79530d68764a78f71363ae7d/tumblr_inline_n62hrul06F1r0plfi.png "Main Login Screen")
![Log In Dialog](http://media.tumblr.com/8d2a6353ff124fae7e1ebb48886de62f/tumblr_inline_n62hs5kRSe1r0plfi.png "Anonymous Login Dialog")
![Main Activity](http://media.tumblr.com/40aa7e511e8c1f3857ddf4c58798f009/tumblr_inline_n62hs8BmSj1r0plfi.png "Main Activity w/ Options")

# Special Thanks
Special thanks to HMKCode for [this blog post](http://hmkcode.com/android-layout-design-by-example-social-networks-sign-in-buttons/) which was a basis for the login page. 

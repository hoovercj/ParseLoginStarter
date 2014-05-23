# Introduction

This project is meant to replace the quickstart for Parse Projects/IntegratingFacebookTutorial by providing an Android project offering twitter and facebook login out of the box. It also allows Anonymous Parse login with the ability to link to Facebook/Twitter afterwards. I don't currently have email/password login option, but I might add it later.

This is a modified version of the Parse Tutorial <a href="https://www.parse.com/tutorials/integrating-facebook-in-android">Integrating Facebook in Android</a>. 

# Setup

Clone/Fork this repo or download it. Set up twitter and facebook apps as described on their developer portals/in the tutorial described above. Paste the Parse App ID/Client Key, Facebook App ID, and Twitter Client Key/Secret into the proper spots in res/strings.xml.

# Features
## LoginActivity

If a user has not previously authenticated with the app, or has logged out, the user will see this. Otherwise it will launch the MainActivity. This LoginActivity has three buttons allowing the user to login anonymously, via facebook, or via twitter. Following a successful login the MainActivity is launched.

## MainActivity

After logging in the user will see this activity. It merely shows the parse "username". If the user is logged in via facebook or twitter, the options menu will have the option to logout. If the user is logged in anonymously, the options menu will allow users to link to either twitter or facebook. 
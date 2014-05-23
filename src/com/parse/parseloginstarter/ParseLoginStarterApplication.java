package com.parse.parseloginstarter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.integratingfacebooktutorial.R;

public class ParseLoginStarterApplication extends Application {

	static final String TAG = "MyApp";

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, getString(R.string.PARSE_APPLICATION_ID),
				getString(R.string.PARSE_CLIENT_KEY));

		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize(getString(R.string.FACEBOOK_APP_ID));
		ParseTwitterUtils.initialize(getString(R.string.TWITTER_API_KEY), getString(R.string.TWITTER_API_SECRET));

	}

}

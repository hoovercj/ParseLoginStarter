package com.parse.parseloginstarter;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.integratingfacebooktutorial.R;

public class LoginActivity extends Activity {

	private Button anonLoginButton;
	private Button fbLoginButton;
	private Button twitterLoginButton;
	private Dialog progressDialog;
	public enum LoginTypes { ANONYMOUS, FACEBOOK, TWITTER };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);
		Log.d(ParseLoginStarterApplication.TAG, "Login - onCreate");

		anonLoginButton = (Button) findViewById(R.id.button_login_anon);
		anonLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginButtonClicked(LoginTypes.ANONYMOUS);
			}
		});
		
		fbLoginButton = (Button) findViewById(R.id.button_login_fb);
		fbLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginButtonClicked(LoginTypes.FACEBOOK);
			}
		});
		
		twitterLoginButton = (Button) findViewById(R.id.button_login_twitter);
		twitterLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginButtonClicked(LoginTypes.TWITTER);
			}
		});

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null)) {
			// Go to the user info activity
			showMainActivity();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void onLoginButtonClicked(LoginTypes loginType) {
		LoginActivity.this.progressDialog = ProgressDialog.show(
				LoginActivity.this, "", getString(R.string.spinner_logging_in), true);
		switch (loginType) {
		case ANONYMOUS:
			ParseAnonymousUtils.logIn(new MyLogInCallback("Anonymously"));
			break;
		case TWITTER:
			ParseTwitterUtils.logIn(this, new MyLogInCallback("Twitter"));
			break;
		case FACEBOOK:
			ParseFacebookUtils.logIn(this, new MyLogInCallback("Facebook"));
			break;
		default:
			break;
		}
	}

	private void showMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	private class MyLogInCallback extends LogInCallback {
		
		private String loginType;
		
		public MyLogInCallback(String type) {
			super();
			loginType = type;
		}
		
		@Override
		public void done(ParseUser user, ParseException err) {
			LoginActivity.this.progressDialog.dismiss();
			if (user == null) {
				Log.d(ParseLoginStarterApplication.TAG,
						"Uh oh. The user cancelled the login.");
			} else if (user.isNew()) {
				Log.d(ParseLoginStarterApplication.TAG,
						"User signed up and logged in via " + loginType + "!");
				showMainActivity();
			} else {
				Log.d(ParseLoginStarterApplication.TAG,
						"User logged in via " + loginType + "!");
				showMainActivity();
			}
		}
	}
}

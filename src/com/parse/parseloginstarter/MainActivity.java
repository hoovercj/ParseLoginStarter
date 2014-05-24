package com.parse.parseloginstarter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.integratingfacebooktutorial.R;

public class MainActivity extends Activity implements MyDialog.MyDialogListener {

	private TextView userNameView;
	private Dialog progressDialog;
	private ParseUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		userNameView = (TextView) findViewById(R.id.username);
	}

	@Override
	public void onResume() {
		super.onResume();
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
			this.currentUser = currentUser;
			showUsername();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			startLoginActivity();
		}
	}

	private void showUsername() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			userNameView.setText(currentUser.getUsername());
		}
	}

	private void linkFacebook() {	
		if (!ParseFacebookUtils.isLinked(currentUser)) {
			MainActivity.this.progressDialog = ProgressDialog.show(
					MainActivity.this, "", getString(R.string.spinner_linking), true);	
			ParseFacebookUtils.link(currentUser, this, new SaveCallback() {
				@Override
				public void done(ParseException ex) {
					MainActivity.this.progressDialog.dismiss();
					if (ParseFacebookUtils.isLinked(currentUser)) {
						Log.d(ParseLoginStarterApplication.TAG, "Woohoo, user logged in with Facebook!");
						Toast.makeText(MainActivity.this, getString(R.string.link_success), Toast.LENGTH_LONG).show();
					} else {
						Log.d(ParseLoginStarterApplication.TAG, "Whoops, user failed to link");
						Toast.makeText(MainActivity.this, getString(R.string.link_failure), Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void linkTwitter() {
		if (!ParseTwitterUtils.isLinked(currentUser)) {
			MainActivity.this.progressDialog = ProgressDialog.show(
					MainActivity.this, "", getString(R.string.spinner_linking), true);		
			ParseTwitterUtils.link(currentUser, this, new SaveCallback() {
				@Override
				public void done(ParseException ex) {
					MainActivity.this.progressDialog.dismiss();
					if (ParseTwitterUtils.isLinked(currentUser)) {
						Log.d(ParseLoginStarterApplication.TAG, "Woohoo, user logged in with Twitter!");
						Toast.makeText(MainActivity.this, getString(R.string.link_success), Toast.LENGTH_LONG).show();
					} else {
						Log.d(ParseLoginStarterApplication.TAG, "Whoops, user failed to link");
						Toast.makeText(MainActivity.this, getString(R.string.link_failure), Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}

	private void logout() {
		// Log the user out
		ParseUser.logOut();

		// Go to the login view
		startLoginActivity();
	}

	private void startLoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (currentUser == null) {
			Log.e(ParseLoginStarterApplication.TAG, "Shouldn't get here. User shouldn't be null");
			return false;
		}
		if (ParseFacebookUtils.isLinked(currentUser) 
				|| ParseTwitterUtils.isLinked(currentUser)) {
			menu.findItem(R.id.menu_link_facebook).setVisible(false);
			menu.findItem(R.id.menu_link_twitter).setVisible(false);
		} else {
			menu.findItem(R.id.menu_link_facebook).setVisible(true);
			menu.findItem(R.id.menu_link_twitter).setVisible(true);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_link_facebook:
			linkFacebook();
			return true;
		case R.id.menu_link_twitter:
			linkTwitter();
			return true;
		case R.id.menu_logout:
			if (ParseAnonymousUtils.isLinked(currentUser)) {
				new MyDialog().show(getFragmentManager(), ParseLoginStarterApplication.TAG);
			} else {
				logout();
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		logout();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// Do Nothing
	}	
}

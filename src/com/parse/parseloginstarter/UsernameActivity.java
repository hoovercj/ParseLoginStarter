package com.parse.parseloginstarter;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.integratingfacebooktutorial.R;


public class UsernameActivity extends Activity {

	private EditText usernameEditText;
	private Dialog progressDialog;
	private ParseUser currentUser;
	private Button checkUsernameButton;
	private Button finishLoginButton;
	private TextView usernameErrorText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.username_activity);
		usernameEditText = (EditText) findViewById(R.id.edit_login_username);
		checkUsernameButton = (Button) findViewById(R.id.button_check_availability);
		finishLoginButton = (Button) findViewById(R.id.button_finish_login);
		usernameErrorText = (TextView) findViewById(R.id.textview_username_error);

		usernameEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				disableFinishButton();
				checkUsernameButton.getBackground().setLevel(Const.DEFAULT);
			}

			@Override
			public void afterTextChanged(Editable s) {
				// Do nothing
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Do nothing

			}
		});

		checkUsernameButton = (Button) findViewById(R.id.button_check_availability);
		checkUsernameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				usernameEditText.setEnabled(false);
				checkUsernameButton.setEnabled(false);
				usernameErrorText.setVisibility(View.INVISIBLE);
				String username = usernameEditText.getText().toString().toLowerCase().trim();
				if (validateUsername(username)) {
					checkUsernameAvailability(username);
				} else {
					usernameErrorText.setVisibility(View.VISIBLE);
					usernameErrorText.setText(R.string.login_text_username_invalid);
					usernameEditText.setEnabled(true);
					usernameEditText.setEnabled(true);
				}
			}
		});
		
		finishLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setUsernameAndFinish();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
			this.currentUser = currentUser;
			usernameEditText.setEnabled(true);
			checkUsernameButton.setEnabled(true);
			checkUsernameButton.getBackground().setLevel(Const.DEFAULT);
			finishLoginButton.setEnabled(false);
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			startLoginActivity();
		}
	}

	private boolean validateUsername(String username) {
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		return !p.matcher(username).find();
	}
	
	private void checkUsernameAvailability(String username) {	
		UsernameActivity.this.progressDialog = ProgressDialog.show(
				this, "", getString(R.string.spinner_checking_availability), true);	

		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("username", username);
		query.countInBackground(new CountCallback() {
			public void done(int count, ParseException e) {
				UsernameActivity.this.progressDialog.dismiss();
				usernameEditText.setEnabled(true);
				checkUsernameButton.setEnabled(true);
				if (e == null) {
					// The count request succeeded. Log the count
					if (count != 0) {
						usernameUnavailable();
					} else {
						usernameAvailable();
					}
				} else {
					// The request failed
				}
			}
		});			
	}

	private void showMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		menu.findItem(R.id.menu_link_facebook).setVisible(false);
		menu.findItem(R.id.menu_link_twitter).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_link_facebook:
			// Shouldn't happen
			return true;
		case R.id.menu_link_twitter:
			// Shouldn't happen
			return true;
		case R.id.menu_logout:
			logout();
		default:
			return super.onOptionsItemSelected(item);
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

	private void usernameUnavailable() {
		usernameErrorText.setText(R.string.login_text_username_unavailable);
		checkUsernameButton.getBackground().setLevel(Const.FAILURE);
		usernameErrorText.setVisibility(View.VISIBLE);
		disableFinishButton();
	}

	private void usernameAvailable() {
		checkUsernameButton.getBackground().setLevel(Const.SUCCESS);
		usernameErrorText.setVisibility(View.INVISIBLE);
		enableFinishButton();
	}

	private void enableFinishButton() {
		finishLoginButton.setEnabled(true);
	}

	private void disableFinishButton() {
		finishLoginButton.setEnabled(false);
	}
	
	private void setUsernameAndFinish() {
		currentUser.setUsername(usernameEditText.getText().toString().toLowerCase());
		currentUser.put(Const.USERNAME_FLAG, true);
		currentUser.saveInBackground();
		showMainActivity();
		finish();
	}
}

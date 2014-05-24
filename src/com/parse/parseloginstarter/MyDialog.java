package com.parse.parseloginstarter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.parse.integratingfacebooktutorial.R;

/**
 * Generic dialog. Used in this application to
 * inform the user how anonymous login works.
 */
public class MyDialog extends DialogFragment {

	public interface MyDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	// Use this instance of the interface to deliver action events
	private MyDialogListener mListener;
	private int titleText = R.string.dialog_title_confirm;
	private int messageText = R.string.dialog_anonymous_warning;
	private int positiveText = R.string.action_logout;
	private int negativeText = R.string.action_cancel;
	
	// Override the Fragment.onAttach() method to instantiate the Listener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the listener so we can send events to the host
			mListener = (MyDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement MyDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(messageText)
		.setTitle(titleText)
		.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onDialogPositiveClick(MyDialog.this);
			}
		})
		.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				mListener.onDialogNegativeClick(MyDialog.this);
			}
		});
		// Create the AlertDialog object and return it
		return builder.create();
	}
	
	public void setTitleText(int titleTextResId) {
		this.titleText = titleTextResId;
	}

	public void setMessageText(int messageTextResId) {
		this.messageText = messageTextResId;
	}

	public void setPositiveText(int positiveTextResId) {
		this.positiveText = positiveTextResId;
	}

	public void setNegativeText(int negativeTextResId) {
		this.negativeText = negativeTextResId;
	}
}
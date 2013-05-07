package org.michenux.yourappidea.activity;

import org.michenux.android.eula.Eula;
import org.michenux.yourappidea.R;

import com.slidingmenu.lib.SlidingMenu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

public class YourAppMainActivity extends FragmentActivity {

	private SlidingMenu slidingMenu ;
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see roboguice.activity.RoboActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.slidingmenu);
		
		Eula.show(this, R.string.eula_title, R.string.eula_accept, R.string.eula_refuse);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		this.showQuitDialog();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.slidingMenu.toggle();
			return true;
		case R.id.menuitem_quit:
			showQuitDialog();
			return true;
		case R.id.menuitem_preferences:
			startActivityForResult(new Intent(this, MyPreferences.class),
					RequestCodes.PREFERENCES_RESULTCODE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see roboguice.activity.RoboActivity#onActivityResult(int, int,
	 *      android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RequestCodes.PREFERENCES_RESULTCODE) {
			Toast.makeText(this, "Back from preferences", Toast.LENGTH_SHORT)
					.show();

		}
	}

	/**
	 * 
	 */
	protected void showQuitDialog() {
		ConfirmQuitDialog newFragment = ConfirmQuitDialog.newInstance();
		newFragment.show(getSupportFragmentManager(), "dialog");
	}
	
	/**
	 * @author Michenux
	 * 
	 */
	public static class ConfirmQuitDialog extends DialogFragment {

		public static ConfirmQuitDialog newInstance() {
			ConfirmQuitDialog frag = new ConfirmQuitDialog();
			Bundle args = new Bundle();
			// args.putInt("title", title);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// int title = getArguments().getInt("title");

			return new AlertDialog.Builder(getActivity())
					.setMessage(R.string.confirm_quit)
					.setPositiveButton(R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									((YourAppMainActivity) getActivity()).finish();
								}
							})
					.setNegativeButton(R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
								}
							}).create();
		}
	}

}
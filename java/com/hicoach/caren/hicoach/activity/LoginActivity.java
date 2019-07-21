package com.hicoach.caren.hicoach.activity;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.hanks.library.AnimateCheckBox;
import com.hicoach.caren.hicoach.R;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] COMMERCE_ACCOUNT = new String[]{
            "commerce:commerce"
    };
    private static final String[] COACH_ACCOUNT= new String[]{
            "coach:coach"
    };
    private static final String[] USER_ACCOUNT = new String[]{
            "users:users"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private ImageView loginimage;
    private TextView changelogin;
    private AnimateCheckBox remebername,autologin;
    private int startid;
    private ActionProcessButton loginbutton;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        Intent intent = getIntent();
        startid = intent.getIntExtra("startid",1);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        loginimage = (ImageView)findViewById(R.id.login_image);
        changelogin = (TextView)findViewById(R.id.changelogin);
        remebername = (AnimateCheckBox)findViewById(R.id.remebername);
        autologin = (AnimateCheckBox)findViewById(R.id.autologin);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        changeimage(startid);
        changelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startid == 0){
                    startid = 1;
                    changeimage(startid);
                }else if(startid == 1){
                    startid = 2;
                    changeimage(startid);
                }else if(startid == 2){
                    startid =0;
                    changeimage(startid);
                }
            }
        });

        loginbutton = (ActionProcessButton)findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        sp = getSharedPreferences("logindata",0);
        if (sp.getInt("checkstate",0) != 0){
            if(sp.getInt("checkstate",0) == 1){
                mEmailView.setText(sp.getString("username","邮箱|手机|用户名"));
                remebername.setChecked(true);
                startid = sp.getInt("startid",0);
                changeimage(startid);
            }else{
                mEmailView.setText(sp.getString("username","邮箱|手机|用户名"));
                mPasswordView.setText(sp.getString("password","0000"));
                autologin.setChecked(true);
                remebername.setChecked(true);
                if(intent.getIntExtra("activityid",1) != 0){
                    startid = sp.getInt("startid",0);
                    changeimage(startid);
                    attemptLogin();
                }
            }
        }
        editor = sp.edit();
    }

    private void changeimage(int startid){
        if(startid == 0){
            loginimage.setImageResource(R.drawable.coach);
        }else if(startid == 1){
            loginimage.setImageResource(R.drawable.commerce);
        }else{
            loginimage.setImageResource(R.drawable.user);
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            loginbutton.setProgress(1);
            mEmailView.setEnabled(false);
            mPasswordView.setEnabled(false);
            loginbutton.setEnabled(false);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private String mEmail;
        private String mPassword;
        private int flag;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            if (startid == 0){
                for (String credential : COACH_ACCOUNT) {
                    String[] pieces = credential.split(":");
                    if (pieces[0].equals(mEmail)) {
                        // Account exists, return true if the password matches.
                        flag = 1;
                        return pieces[1].equals(mPassword);
                    }else{
                        flag = 0;
                        return false;
                    }
                }
            }else if(startid == 1){
                for (String credential : COMMERCE_ACCOUNT) {
                    String[] pieces = credential.split(":");
                    if (pieces[0].equals(mEmail)) {
                        // Account exists, return true if the password matches.
                        flag = 1;
                        return pieces[1].equals(mPassword);
                    }else{
                        flag = 0;
                        return false;
                    }
                }
            }else{
                for (String credential : USER_ACCOUNT) {
                    String[] pieces = credential.split(":");
                    if (pieces[0].equals(mEmail)) {
                        // Account exists, return true if the password matches.
                        flag = 1;
                        return pieces[1].equals(mPassword);
                    }else{
                        flag = 0;
                        return false;
                    }
                }
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            Intent intent = new Intent();
            if (success) {
                loginbutton.setProgress(100);
                if(startid == 0){
                    if(remebername.isChecked()){
                        editor.putString("username",mEmail);
                        editor.putInt("checkstate",1);
                        editor.putInt("startid",0);
                        editor.commit();
                    }if(autologin.isChecked()){
                        editor.putString("username",mEmail);
                        editor.putString("password",mPassword);
                        editor.putInt("checkstate",2);
                        editor.putInt("startid",0);
                        editor.commit();
                    }
                    intent.setClass(LoginActivity.this,CoachActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    overridePendingTransition(R.transition.in_from_right,R.transition.out_to_left);
                }else if(startid == 1){
                    if(remebername.isChecked()){
                        editor.putString("username",mEmail);
                        editor.putInt("checkstate",1);
                        editor.putInt("startid",1);
                        editor.commit();
                    }if(autologin.isChecked()){
                        editor.putString("username",mEmail);
                        editor.putString("password",mPassword);
                        editor.putInt("checkstate",2);
                        editor.putInt("startid",1);
                        editor.commit();
                    }
                    intent.setClass(LoginActivity.this,CommerceActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    overridePendingTransition(R.transition.in_from_right,R.transition.out_to_left);
                }else{
                    if(remebername.isChecked()){
                        editor.putString("username",mEmail);
                        editor.putInt("checkstate",1);
                        editor.putInt("startid",2);
                        editor.commit();
                    }if(autologin.isChecked()){
                        editor.putString("username",mEmail);
                        editor.putString("password",mPassword);
                        editor.putInt("checkstate",2);
                        editor.putInt("startid",2);
                        editor.commit();
                    }
                    intent.setClass(LoginActivity.this,UserActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    overridePendingTransition(R.transition.in_from_right,R.transition.out_to_left);
                }
            }else if(flag ==0){
                mEmailView.setError("用户不存在");
                mEmailView.requestFocus();
                onCancelled();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                onCancelled();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            mEmailView.setEnabled(true);
            mPasswordView.setEnabled(true);
            loginbutton.setEnabled(true);
            loginbutton.setProgress(0);
        }

    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}


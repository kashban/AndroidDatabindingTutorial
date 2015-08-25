package com.mtag.androiddatabindingtutorial;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivityDatabinding extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    LoginViewModel mLoginViewModel = new LoginViewModel();
    RelativeLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        // Authentication Mode Spinner provisioning and Eventhandler
        Spinner spinner = (Spinner) findViewById(R.id.spAuthMethod);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.auth_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Add Watchers to EditTexts
        EditText edUsername = (EditText) findViewById(R.id.edUsername);
        edUsername.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(String newValue) {
                mLoginViewModel.setUsername(newValue);
                // Reflect changes in textview
                TextView tvUsernameValue = (TextView) findViewById(R.id.tvUsernameValue);
                tvUsernameValue.setText(newValue);
                enableDisableLoginButton();
            }
        });
        
        EditText edPassword = (EditText) findViewById(R.id.edPassword);
        edPassword.addTextChangedListener(new SimpleTextWatcher(){
            @Override
            public void onTextChanged(String newValue) {
                mLoginViewModel.setPassword(newValue);
                // Reflect changes in textview
                TextView tvPasswordValue = (TextView) findViewById(R.id.tvPasswordValue);
                tvPasswordValue.setText(newValue);
                enableDisableLoginButton();
            }
        });

        EditText edDomain = (EditText) findViewById(R.id.edDomain);
        edDomain.addTextChangedListener(new SimpleTextWatcher(){
            @Override
            public void onTextChanged(String newValue) {
                mLoginViewModel.setDomain(newValue);
                // Reflect changes in textview
                TextView tvDomainValue = (TextView) findViewById(R.id.tvDomainValue);
                tvDomainValue.setText(newValue);
                enableDisableLoginButton();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mLoginViewModel.setAuthMethod(LoginViewModel.AUTH_METHOD.values()[position]);
        // Hide / show Domain
        showHideDomain(position == LoginViewModel.AUTH_METHOD.WINDOWS.ordinal());
        enableDisableLoginButton();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mLoginViewModel.setAuthMethod(null);
        showHideDomain(false);
    }

    /**
     * Convenience Method to change the state of the button according to form data validity
     */
    private void enableDisableLoginButton()
    {
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setEnabled(mLoginViewModel.isInputValid());
    }

    /**
     * Convenience method to hide / show the domain input field
     * @param show true: Show Domain input field, false: hide this field
     */
    private void showHideDomain(boolean show)
    {
        TextInputLayout tivDomain = (TextInputLayout) findViewById(R.id.tilDomain);
        TextView tvDomain = (TextView) findViewById(R.id.tvDomain);
        TextView tvDomainValue = (TextView) findViewById(R.id.tvDomainValue);

        if (show)
        {
            tivDomain.setVisibility(View.VISIBLE);
            tvDomain.setVisibility(View.VISIBLE);
            tvDomainValue.setVisibility(View.VISIBLE);
        } else
        {
            tivDomain.setVisibility(View.GONE);
            tvDomain.setVisibility(View.GONE);
            tvDomainValue.setVisibility(View.GONE);
        }
    }

    public void login(View view) {
        showSnackbar(String.format(
                    getString(R.string.msgLogin), mLoginViewModel.getUsername()
                    + "/" + mLoginViewModel.getPassword()
                    + (mLoginViewModel.getAuthMethod() == LoginViewModel.AUTH_METHOD.WINDOWS ? "/" + mLoginViewModel.getDomain() : "")),
                Snackbar.LENGTH_SHORT);
    }

    /**
     * Simple TextWatcher to get notified on EditText changes
     */
    public abstract class SimpleTextWatcher implements TextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            onTextChanged(s.toString());
        }

        public abstract void onTextChanged(String newValue);
    }

    /**
     * Show a Snackbar message
     * @param message message to show
     * @param length One of Snackbar.LENGTH_*
     */
    protected void showSnackbar(String message, int length) {
        Snackbar
                .make(mMainLayout, message, length)
                        //.setAction(R.string.snackbar_action, myOnClickListener)
                .show(); // Don’t forget to show!
    }
}

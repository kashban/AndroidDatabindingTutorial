package com.mtag.androiddatabindingtutorial;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mtag.androiddatabindingtutorial.databinding.ActivityMainDatabindingBinding;

public class MainActivityDatabinding extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    LoginViewModel mLoginViewModel = new LoginViewModel();
    ActivityMainDatabindingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_databinding);
        mBinding.setViewModel(mLoginViewModel);

        // Authentication Mode Spinner provisioning and Eventhandler
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.auth_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        // Bindings to Views automatically generated on those having IDs.
        mBinding.spAuthMethod.setAdapter(adapter);
        mBinding.spAuthMethod.setOnItemSelectedListener(this);

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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mLoginViewModel.setAuthMethod(null);
        showHideDomain(false);
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
     * Show a Snackbar message
     * @param message message to show
     * @param length One of Snackbar.LENGTH_*
     */
    protected void showSnackbar(String message, int length) {
        Snackbar
                .make(mBinding.mainLayout, message, length)
                        //.setAction(R.string.snackbar_action, myOnClickListener)
                .show(); // Don’t forget to show!
    }
}

package com.mtag.androiddatabindingtutorial;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.mtag.androiddatabindingtutorial.databinding.ActivityMainDatabindingBinding;

public class MainActivityDatabinding extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    LoginViewModelDatabinding mLoginViewModelDatabinding = new LoginViewModelDatabinding();
    ActivityMainDatabindingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_databinding);
        mLoginViewModelDatabinding.authMethod.set(LoginViewModelDatabinding.AUTH_METHOD.WINDOWS);
        mBinding.setViewModel(mLoginViewModelDatabinding);

        // Authentication Mode Spinner provisioning and Eventhandler
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
        mLoginViewModelDatabinding.authMethod.set(LoginViewModelDatabinding.AUTH_METHOD.values()[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mLoginViewModelDatabinding.authMethod.set(null);
    }

    public void login(View view) {
        showSnackbar(String.format(
                    getString(R.string.msgLogin), mLoginViewModelDatabinding.username.get()
                    + "/" + mLoginViewModelDatabinding.password.get()
                    + (mLoginViewModelDatabinding.authMethod.get() == LoginViewModelDatabinding.AUTH_METHOD.WINDOWS ? "/" + mLoginViewModelDatabinding.domain.get() : "")),
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

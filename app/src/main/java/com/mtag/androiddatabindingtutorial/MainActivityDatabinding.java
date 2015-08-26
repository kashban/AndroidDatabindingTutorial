package com.mtag.androiddatabindingtutorial;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mtag.androiddatabindingtutorial.databinding.ActivityMainDatabindingBinding;

public class MainActivityDatabinding extends AppCompatActivity {

    LoginViewModelDatabinding mLoginViewModelDatabinding = new LoginViewModelDatabinding();
    ActivityMainDatabindingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_databinding);
        mLoginViewModelDatabinding.authMethod.set(LoginViewModelDatabinding.AUTH_METHOD.WINDOWS);
        mBinding.setViewModel(mLoginViewModelDatabinding);

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

    /**
     * Show a Snackbar message
     * @param message message to show
     * @param length One of Snackbar.LENGTH_*
     */
    public static void showSnackbar(View rootView, String message, int length) {
        Snackbar
                .make(rootView, message, length)
                        //.setAction(R.string.snackbar_action, myOnClickListener)
                .show(); // Don’t forget to show!
    }
}

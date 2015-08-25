package com.mtag.androiddatabindingtutorial;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Model Class for the Login Screen
 * Created by jwahlmann on 25.08.2015.
 */
public class LoginViewModel extends BaseObservable {

    private boolean isInNotifiation = true;

    public static enum AUTH_METHOD {BASIC,WINDOWS};

    private AUTH_METHOD mAuthMethod = AUTH_METHOD.BASIC;
    private String mUsername = "";
    private String mPassword = "";
    private String mDomain = "";

    @Bindable
    public AUTH_METHOD getAuthMethod() {
        return mAuthMethod;
    }

    public void setAuthMethod(AUTH_METHOD mAuthMethod) {
        this.mAuthMethod = mAuthMethod;
    }

    @Bindable
    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
        notifyPropertyChanged(com.mtag.androiddatabindingtutorial.BR.username);
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
        isInNotifiation = true;
        notifyPropertyChanged(com.mtag.androiddatabindingtutorial.BR.password);
    }

    @Bindable
    public String getDomain() {
        return mDomain;
    }

    public void setDomain(String mDomain) {
        this.mDomain = mDomain;
        isInNotifiation = true;
        notifyPropertyChanged(com.mtag.androiddatabindingtutorial.BR.domain);
    }

    /**
     * Quick check if form data is valid
     * @return true: Login possible, false: Data missing
     */
    @Bindable
    public boolean getInputValid()
    {
        return mUsername.length() > 0 && mPassword.length() > 0 && (mAuthMethod == AUTH_METHOD.BASIC || mDomain.length() > 0);
    }

    public void setInputValid(boolean valid)
    {
        // Stub
    }

    // Add Watchers to EditTexts
    @Bindable
    public TextWatcher getOnUsernameChanged(){
         return new SimpleTextWatcher() {
        @Override
        public void onTextChanged(String newValue) {
            if (!newValue.equals(mUsername)) setUsername(newValue);
        }};
    };

    @Bindable
    public TextWatcher getOnPasswordChanged(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(String newValue) {
                if (!isInNotifiation) setPassword(newValue);
                isInNotifiation = false;
            }};
    };

    @Bindable
    public TextWatcher getOnDomainChanged(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(String newValue) {
                if (!isInNotifiation) setDomain(newValue);
                isInNotifiation = false;
            }};
    };

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
}

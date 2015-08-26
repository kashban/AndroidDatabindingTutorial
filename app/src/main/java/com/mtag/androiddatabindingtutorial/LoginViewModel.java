package com.mtag.androiddatabindingtutorial;

/**
 * Model Class for the Login Screen
 * Created by jwahlmann on 25.08.2015.
 */
public class LoginViewModel{


    public static enum AUTH_METHOD {BASIC,WINDOWS};

    private AUTH_METHOD mAuthMethod = AUTH_METHOD.BASIC;
    private String mUsername = "";
    private String mPassword = "";
    private String mDomain = "";

    public AUTH_METHOD getAuthMethod() {
        return mAuthMethod;
    }

    public void setAuthMethod(AUTH_METHOD mAuthMethod) {
        this.mAuthMethod = mAuthMethod;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getDomain() {
        return mDomain;
    }

    public void setDomain(String mDomain) {
        this.mDomain = mDomain;
    }

    /**
     * Quick check if form data is valid
     * @return true: Login possible, false: Data missing
     */
    public boolean getInputValid()
    {
        return mUsername.length() > 0 && mPassword.length() > 0 && (mAuthMethod == AUTH_METHOD.BASIC || mDomain.length() > 0);
    }

}

package com.mtag.androiddatabindingtutorial;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Model Class for the Login Screen
 * Created by jwahlmann on 25.08.2015.
 */
public class LoginViewModelDatabinding extends BaseObservable {

    public static enum AUTH_METHOD {BASIC, WINDOWS};

    public static final int BASIC = 0;
    public static final int WINDOWS = 1;

    // TODO: For Databinding

    public BindableString username = new BindableString();
    public BindableString password = new BindableString();
    public BindableString domain = new BindableString();

    public final ObservableField<AUTH_METHOD> authMethod = new ObservableField<>();
    public final ObservableBoolean inputValid = new ObservableBoolean();

    /**
     * Quick check if form data is valid
     *
     * @return true: Login possible, false: Data missing
     */
    @Bindable
    public boolean getInputValid() {
        return !username.isEmpty() && !password.isEmpty() && (authMethod.get() == AUTH_METHOD.BASIC || !domain.isEmpty());
    }

    public void setInputValid(boolean valid) {
        // Stub
    }

    @BindingConversion
    public static String convertBindableToString(
            BindableString bindableString) {
        return bindableString.get();
    }

    @BindingAdapter({"app:binding"})
    public static void bindEditText(EditText view,
                                    final BindableString bindableString) {
        if (view.getTag(R.id.bound) == null) {
            view.setTag(R.id.bound, true);
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    bindableString.set(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    /**
     * XML Setter for Spinner selection
     * @param spinner spinner to set selection to
     * @param position Position to select
     */
    @BindingAdapter("app:selection")
    public static void setSelection(Spinner spinner, int position) {
        spinner.setSelection(position);
    }
}

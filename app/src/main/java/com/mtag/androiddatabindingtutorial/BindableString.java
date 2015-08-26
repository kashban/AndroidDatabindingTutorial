package com.mtag.androiddatabindingtutorial;

import android.databinding.BaseObservable;

public class BindableString extends BaseObservable {
    private String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!value.equals(this.value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}
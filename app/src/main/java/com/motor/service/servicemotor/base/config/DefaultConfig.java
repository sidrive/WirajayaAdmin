package com.motor.service.servicemotor.base.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ikun on 28/12/17.
 */

public class DefaultConfig {
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NEW_JOB_RETRY_COUNT = "new_post_retry_count";
    private static final String KEY_API_URL = "api_url";

    private final SharedPreferences mSharedPreferences;
    public DefaultConfig(Context context) {
        mSharedPreferences = context.getSharedPreferences("default_config", Context.MODE_PRIVATE);
    }

    public long getUserId() {
        return mSharedPreferences.getLong(KEY_USER_ID, 1);
    }

    public void setUserId(long userId) {
        mSharedPreferences.edit().putLong(KEY_USER_ID, userId).apply();
    }

    public int getNewPostRetryCount() {
        return mSharedPreferences.getInt(KEY_NEW_JOB_RETRY_COUNT, 20);
    }

    public void setNewPostRetryCount(int count) {
        mSharedPreferences.edit().putInt(KEY_NEW_JOB_RETRY_COUNT, count).apply();
    }

    public String getApiUrl() {
        return mSharedPreferences.getString(KEY_API_URL, "https://lokal101.com/api/v1/");
    }

    public void setApiUrl(String url) {
        mSharedPreferences.edit().putString(KEY_API_URL, url).apply();
    }
}

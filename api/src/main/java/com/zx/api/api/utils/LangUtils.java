package com.zx.api.api.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import java.util.Locale;

/**
 * Name: LangUtils
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-14 16:33
 */
public class LangUtils {
    public static final int FOLLOW_SYSTEM = 0;
    public static final int SIMPLE_CHINESE = 1;
    public static final int TRADITION_CHINESE = 2;
    public static final int ENGLISH = 3;

    private static Locale getCurrentLang(int userLang) {
        switch (userLang) {
            case FOLLOW_SYSTEM:
                return Locale.getDefault();
            case SIMPLE_CHINESE:
                return Locale.SIMPLIFIED_CHINESE;
            case TRADITION_CHINESE:
                return Locale.TRADITIONAL_CHINESE;
            case ENGLISH:
                return Locale.ENGLISH;
            default:
                return Locale.SIMPLIFIED_CHINESE;
        }
    }

    public static Context getAttachBaseContext(Context context, int lang) {
        //Android 7.0之后需要用另一种方式更改res语言,即配置进context中
        Log.d("pigdreams", "配置语言...默认locale=" + Locale.getDefault() + ";用户设置的为=" + getCurrentLang(lang));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, lang);
        } else {
            //7.0之前的更新语言资源方式
            changeResLanguage(context, lang);
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, int lang) {
        Resources resources = context.getResources();
        Locale locale = getCurrentLang(lang);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    private static void changeResLanguage(Context context, int lang) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getCurrentLang(lang);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

}

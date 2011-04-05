package ru.gelin.android.weather.notification.skin.builtin;

import static ru.gelin.android.weather.notification.AbstractWeatherLayout.formatTemp;
import static ru.gelin.android.weather.notification.skin.builtin.PreferenceKeys.NOTIFICATION_ICON_STYLE;
import static ru.gelin.android.weather.notification.skin.builtin.PreferenceKeys.NOTIFICATION_ICON_STYLE_DEFAULT;
import static ru.gelin.android.weather.notification.skin.builtin.PreferenceKeys.NOTIFICATION_TEXT_STYLE;
import static ru.gelin.android.weather.notification.skin.builtin.PreferenceKeys.NOTIFICATION_TEXT_STYLE_DEFAULT;
import static ru.gelin.android.weather.notification.skin.builtin.PreferenceKeys.TEMP_UNIT;
import static ru.gelin.android.weather.notification.skin.builtin.PreferenceKeys.TEMP_UNIT_DEFAULT;
import ru.gelin.android.weather.Temperature;
import ru.gelin.android.weather.UnitSystem;
import ru.gelin.android.weather.Weather;
import ru.gelin.android.weather.WeatherCondition;
import ru.gelin.android.weather.notification.R;
import ru.gelin.android.weather.notification.skin.WeatherNotificationReceiver;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

/**
 *  Weather notification receiver built into basic application.
 */
public class BuiltinWeatherNotificationReceiver extends
        WeatherNotificationReceiver {

    /** Notification ID */
    static final int ID = 1;
    /** Icon level shift relative to temp value */
    static final int ICON_LEVEL_SHIFT = 100;
    
    @Override
    protected void cancel(Context context) {
        getNotificationManager(context).cancel(ID);
    }

    @Override
    protected void notify(Context context, Weather weather) {
        SharedPreferences prefs =
            PreferenceManager.getDefaultSharedPreferences(context);
    
        TemperatureUnit unit = TemperatureUnit.valueOf(prefs.getString(
            TEMP_UNIT, TEMP_UNIT_DEFAULT));
        UnitSystem mainUnit = unit.getUnitSystem();
        NotificationStyle iconStyle = NotificationStyle.valueOf(prefs.getString(
            NOTIFICATION_ICON_STYLE, NOTIFICATION_ICON_STYLE_DEFAULT));
        NotificationStyle textStyle = NotificationStyle.valueOf(prefs.getString(
                NOTIFICATION_TEXT_STYLE, NOTIFICATION_TEXT_STYLE_DEFAULT));

        Notification notification = new Notification();
        
        notification.icon = iconStyle.getIconRes();
        
        if (weather.isEmpty() || weather.getConditions().size() <= 0) {
            notification.tickerText = context.getString(R.string.unknown_weather);
        } else {
            //http://code.google.com/p/android/issues/detail?id=6560
            //adding a hundred
            notification.iconLevel = weather.getConditions().get(0).
                    getTemperature(mainUnit).getCurrent() + ICON_LEVEL_SHIFT;
            notification.tickerText = formatTicker(context, weather, unit);
        }
        //this.iconLevel = 223;//debug

        notification.when = weather.getTime().getTime();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        
        notification.contentView = new RemoteViews(context.getPackageName(), 
                textStyle.getLayoutRes());
        RemoteWeatherLayout layout = new RemoteWeatherLayout(context, notification.contentView);
        layout.bind(weather);
        
        notification.contentIntent = getMainActivityPendingIntent(context);
        
        getNotificationManager(context).notify(ID, notification);
    }
    
    String formatTicker(Context context, Weather weather, TemperatureUnit unit) {
        WeatherCondition condition = weather.getConditions().get(0);
        Temperature tempC = condition.getTemperature(UnitSystem.SI);
        Temperature tempF = condition.getTemperature(UnitSystem.US);
        return context.getString(R.string.notification_ticker,
                weather.getLocation().getText(),
                formatTemp(tempC.getCurrent(), tempF.getCurrent(), unit));
    }

}

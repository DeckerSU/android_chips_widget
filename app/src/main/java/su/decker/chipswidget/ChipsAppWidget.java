package su.decker.chipswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class ChipsAppWidget extends AppWidgetProvider {

    private static final String SYNC_CLICKED    = "chipswidget_update_action";
    private static final String WAITING_MESSAGE = "Loading ...";
    private static final String LOG_TAG = "chipswidget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        CharSequence updateTime = currentDateandTime;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.chips_app_widget);

        CurrencyData cd = new CurrencyData();

        ParseTask pt = new ParseTask(context, appWidgetManager, appWidgetId, views, cd);
        pt.execute();

        //CharSequence rateText = pt.getLastPriceStr();
        CharSequence fromCurrencyText = "CHIPS";
        CharSequence toCurrencyText = "BTC";

        // flagBmp = new BitmapDrawable(this.context.getResources(), BitmapFactory.decodeByteArray(this.flag, 0, this.flag.length));
        Bitmap flag_from_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.chips_flag);
        Bitmap flag_to_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.btc_flag);

        views.setImageViewBitmap(R.id.flag_from, flag_from_icon);
        views.setImageViewBitmap(R.id.flag_to, flag_to_icon);
        views.setTextViewText(R.id.rate, WAITING_MESSAGE+" ["+appWidgetId+"]");
        views.setTextViewText(R.id.code_from, fromCurrencyText);
        views.setTextViewText(R.id.code_to, toCurrencyText);
        views.setTextColor(R.id.updated, -1);

        views.setTextColor(R.id.rate, ContextCompat.getColor(context.getApplicationContext(), R.color.widget_text));
        views.setViewVisibility(R.id.please_update, 8);
        views.setTextViewText(R.id.updated, updateTime);

        Intent intent = new Intent(context, ChipsAppWidget.class);
        intent.setAction(SYNC_CLICKED+String.valueOf(appWidgetId));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.rate, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        // https://stackoverflow.com/questions/15551679/how-to-know-clicked-widget-id
        // https://bitbucket.org/jholetzeck/minicallwidget-android/src/8e6c325f299775674ec530abb64ee39302cf89ff/src/de/holetzeck/minicallwidget/MiniCallWidget.java?at=default&fileviewer=file-view-default
        // lines 97 & 169

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        CharSequence updateTime = currentDateandTime;

        if (intent != null) {
            String action = intent.getAction();
            if (action.startsWith(SYNC_CLICKED)) {
                int widgetId = Integer.parseInt(action.substring(SYNC_CLICKED.length()));
                Log.d(LOG_TAG, "Clicked ... ["+widgetId+"]");



                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

                RemoteViews remoteViews;
                ComponentName watchWidget;
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.chips_app_widget);

                CurrencyData cd = new CurrencyData();

                ParseTask pt = new ParseTask(context, appWidgetManager, widgetId, remoteViews, cd);
                pt.execute();

                watchWidget = new ComponentName(context, ChipsAppWidget.class);
                //remoteViews.setTextViewText(R.id.rate, WAITING_MESSAGE+" ("+widgetId+")");
                remoteViews.setTextViewText(R.id.rate, WAITING_MESSAGE);
                remoteViews.setTextViewText(R.id.updated, updateTime);

                /*
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(watchWidget);
                for (int appWidgetId : appWidgetIds) {
                ParseTask pt = new ParseTask(context, appWidgetManager, appWidgetId, remoteViews);
                pt.execute();
                } */

                // appWidgetManager.updateAppWidget(watchWidget, remoteViews);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


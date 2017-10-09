package su.decker.chipswidget;

/**
 * Created by decker
 */
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.AsyncTask;

import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParseTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private AppWidgetManager manager;
    private int id;
    private RemoteViews views;
    private CurrencyData cd;


    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    public String resultJson = "";
    private static final String LOG_TAG = "chipswidget";
    private Double LastPrice = 0.0;

    public String getLastPriceStr() {
        return String.format("%.8f", LastPrice);
    }

    ParseTask(Context context, AppWidgetManager appWidgetManager, int appWidgetId, RemoteViews views, CurrencyData cd) {
        this.context = context;
        this.manager = appWidgetManager;
        this.id = appWidgetId;
        this.views = views;
        this.cd = cd;

    }

    @Override
    protected String doInBackground(Void... params) {
        // получаем данные с внешнего ресурса
        try {
            /*
            http://androiddocs.ru/parsing-json-poluchaem-i-razbiraem-json-s-vneshnego-resursa/
            https://api.coinmarketcap.com/v1/ticker/chips/?convert=USD
            http://jsoneditoronline.org/?url=https%3A%2F%2Fapi.coinmarketcap.com%2Fv1%2Fticker%2Fchips%2F%3Fconvert%3DUSD
             */


            URL url = new URL("https://api.coinmarketcap.com/v1/ticker/chips/?convert=USD");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);

        Log.d(LOG_TAG, strJson);

        // JSONObject dataJsonObj = null;

        String secondName = "";

        try {
                //dataJsonObj = new JSONObject(strJson);
                //boolean Success = dataJsonObj.getBoolean("Success");
                //Log.d(LOG_TAG, "Success: " + Success);
                //if (Success) {
                //JSONObject Data = dataJsonObj.getJSONObject("Data");
                //JSONObject Data = dataJsonObj.getJSONObject("sigt_btc");
                //LastPrice = Data.getDouble("LastPrice");
                //LastPrice = Data.getDouble("last");

                /* Example:
                JSONArray jsonarray = new JSONArray(jsonStr);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String name = jsonobject.getString("name");
                    String url = jsonobject.getString("url");
                } */

                JSONArray jsonarray = new JSONArray(strJson);
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                LastPrice = jsonobject.getDouble("price_btc");

                Log.d(LOG_TAG, "LastPrice: "+String.format("%.8f", LastPrice));

                cd.setLastPrice(LastPrice);



                this.views.setTextViewText(R.id.rate, String.format("%.8f", LastPrice));
                this.manager.updateAppWidget(this.id, this.views);

            //}

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
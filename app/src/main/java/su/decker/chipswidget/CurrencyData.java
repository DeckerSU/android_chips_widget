package su.decker.chipswidget;

/**
 * Created by decker on 18.08.17.
 */

// класс, описывающий валютную пару на Cryptopia

     /*
            http://androiddocs.ru/parsing-json-poluchaem-i-razbiraem-json-s-vneshnego-resursa/
            https://www.cryptopia.co.nz/api/GetMarket/SIGT_BTC
            http://jsoneditoronline.org/?url=https%3A%2F%2Fwww.cryptopia.co.nz%2Fapi%2FGetMarket%2FSIGT_BTC
     */

public class CurrencyData {
    private int id; // внутренний id (может и не понадобиться)
    private int TradePairId;
    private String Label;
    private double AskPrice;
    private double BidPrice;
    private double Low;
    private double High;
    private double Volume;
    private double LastPrice;
    private double BuyVolume;
    private double SellVolume;
    private double Change;
    private double Open;
    private double Close;
    private double BaseVolume;
    private double BuyBaseVolume;
    private double SellBaseVolume;



    public double getLastPrice() {
        return LastPrice;
    }

    public void setLastPrice(double lastPrice) {
        LastPrice = lastPrice;
    }
}

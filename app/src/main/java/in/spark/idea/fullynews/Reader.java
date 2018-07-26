package in.spark.idea.fullynews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Reader extends AppCompatActivity {

    String url = "";
    private WebView mainWebContent;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4283164631469341~4786268306");

        url = getIntent().getExtras().getString("link");

        AdView mTopAdView = (AdView) findViewById(R.id.topBanner);
        AdView mBottomAdView = (AdView) findViewById(R.id.bottomBanner);

        AdRequest adTopRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        AdRequest adBottomRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();


        mInterstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId(getString(R.string.interestial_reader_ad_unit_id));

        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });


        mainWebContent = (WebView) findViewById(R.id.mainweb);
        mainWebContent.getSettings().setLoadWithOverviewMode(true);
        mainWebContent.getSettings().setUseWideViewPort(true);
        mainWebContent.getSettings().setJavaScriptEnabled(true);
        mainWebContent.getSettings().setBuiltInZoomControls(true);
        mainWebContent.getSettings().setCacheMode(0);
        mainWebContent.getSettings().setSupportZoom(true);
        mainWebContent.clearCache(true);
        mainWebContent.loadUrl(url);

        mTopAdView.loadAd(adTopRequest);
        mBottomAdView.loadAd(adBottomRequest);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isNetworkAvailable()){
                    showInterstitial();
                }
            }
        }, 5000);


        mainWebContent.setWebViewClient(new MyWebViewClient());
        mainWebContent.setWebChromeClient(new MyWebChromeClient());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void requestNewInterstitial() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    public void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


}

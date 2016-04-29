package com.example.gabrielbl.androidstudyjam;

import android.net.Uri;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by gabrielbl on 25/04/16.
 */
public class MyAppWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // Render in WebView only URL that contains 'google.com'
        if(Uri.parse(url).getHost().contains("cinesercla.com")) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }
}

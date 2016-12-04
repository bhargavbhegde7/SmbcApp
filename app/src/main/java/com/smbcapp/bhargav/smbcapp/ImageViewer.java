package com.smbcapp.bhargav.smbcapp;

import android.app.ProgressDialog;
import android.content.Context;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sound.bytes.asynchdownloader.HTMLDataHandler;
import com.sound.bytes.asynchdownloader.HTMLDownloader;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class ImageViewer extends ActionBarActivity {

    private final int latestComicId = 4315;
    private int currentId;
    HTMLDataHandler handler;
    ProgressDialog pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        final WebView webView = (WebView)findViewById(R.id.MyView);
        pdLoading = new ProgressDialog(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setBuiltInZoomControls(true);

        pdLoading.setMessage("\tLoading...");
        pdLoading.show();
        handler = new HTMLDataHandler() {
            @Override
            public void onHTMLDownloadCompleted(Object result, Context mainAppCtxt) {
                Elements elements = ((Document)result).getElementById("cc-comicbody").getElementsByTag("img");
                String src = elements.attr("src");

                webView.loadUrl(src);

                webView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        pdLoading.dismiss();
                        Toast.makeText(getApplicationContext(), String.valueOf(currentId), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        currentId = latestComicId;
        goToComic(currentId);
    }

    public void goToComic(int comicId)
    {
        pdLoading.show();
        new HTMLDownloader(handler, getApplicationContext()).execute("http://www.smbc-comics.com/index.php?id="+ comicId);
    }

    public void goToPrev(View view)
    {
        if(currentId >1) {
            currentId--;
            goToComic(currentId);
        }
        else
        {
            Toast.makeText(this, "Cannot go back", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToNext(View view)
    {
        if(currentId <latestComicId) {
            currentId++;
            goToComic(currentId);
        }
        else
        {
            Toast.makeText(this, "Cannot go further", Toast.LENGTH_SHORT).show();
        }
    }

    public int getRandomId()
    {
        int randomId = (int)(Math.random()*9000)+1000;
        if(randomId>latestComicId)
            return getRandomId();
        else
            return randomId;
    }

    public void goToRandom(View view)
    {
        currentId = getRandomId();
        goToComic(currentId);
    }
}

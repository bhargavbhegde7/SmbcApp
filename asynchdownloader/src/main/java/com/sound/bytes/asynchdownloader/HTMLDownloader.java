package com.sound.bytes.asynchdownloader;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by goodbytes on 12/3/2016.
 */
public class HTMLDownloader extends AsyncTask<String, Void, Document> {
    HTMLDataHandler handler;
    Context appCtxt;

    public HTMLDownloader(HTMLDataHandler handlerFromMainActivity, Context mainAppCtxt){
        handler = handlerFromMainActivity;
        appCtxt = mainAppCtxt;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Document doInBackground(String... params) {

        Document document = null;
        try {
            document = Jsoup.connect(params[0]).get();
        } catch (IOException e) {
            System.out.println("doInBackGround exception :" + e.getMessage());
        }

        return document;
    }

    @Override
    protected void onPostExecute(Document result) {
        super.onPostExecute(result);
        handler.onHTMLDownloadCompleted(result,appCtxt);
    }
}

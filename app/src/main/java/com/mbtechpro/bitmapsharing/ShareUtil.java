package com.mbtechpro.bitmapsharing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.content.FileProvider;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareUtil {

    public static void getSharingIntent(Context context, String mShareTitle,String mShareUrl) {

        if (mShareTitle == null) {
            mShareTitle = "";
        }
        if (mShareUrl == null) {
            mShareUrl = "";
        }
        Intent sharingIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        String shareBody = mShareTitle
                + ": " + mShareUrl;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                mShareTitle);
        sharingIntent
                .putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TITLE,
                mShareTitle);
        Intent intent = Intent.createChooser(sharingIntent, "Share Via");

        context.startActivity(intent);
    }


    public static void bitmapSharing(Context context) {
        // Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#ff00ff"));
        textPaint.setTextSize(30);

        String text = "Hellow";
        int textWidth = 200;

        StaticLayout mTextLayout = new StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER,
                1.0f, 0.0f, false);

        // Create bitmap and canvas to draw to
        Bitmap txtBitmap = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(txtBitmap);

        // Draw background
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        c.drawPaint(paint);

        // Draw text
        c.save();
        c.translate(0, 0);
        mTextLayout.draw(c);
        c.restore();


        Bitmap imgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.nature);


        Bitmap bmOverlay = Bitmap.createBitmap(imgBitmap.getWidth(), imgBitmap.getHeight(), imgBitmap.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(imgBitmap, new Matrix(), null);
        canvas.drawBitmap(txtBitmap, 0, imgBitmap.getHeight()-100, null);


        // Save this bitmap to a file.
        File cache = context.getExternalCacheDir();
        File sharefile = new File(cache, "Shared.png");
        Log.d("share file type is", sharefile.getAbsolutePath());
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bmOverlay.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            Log.e("ERROR", String.valueOf(e.getMessage()));

        }


        // Now send it out to share
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context,
                context.getResources().getString(R.string.file_provider_authority), sharefile));
        context.startActivity(Intent.createChooser(share,
                "Share This Image with"));
    }

}

package com.davidmarian_buzatu.remlock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.davidmarian_buzatu.remlock.MainActivity;
import com.davidmarian_buzatu.remlock.calendar.CalendarManager;
import com.davidmarian_buzatu.remlock.parser.Parser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class ScreenshotBroadcastReceiver extends BroadcastReceiver {
    public static final String SCREENSHOT_PATH = "SCREENSHOT_PATH";
    public static final int SCREENSHOT_ID = 999;
    public static final String STOP_SERVICE = "STOP_SERVICE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(STOP_SERVICE)) {
            context.stopService(new Intent(context, ScreenshotService.class));
        } else {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.get(SCREENSHOT_PATH) != null) {
                File newScreenshot = new File(bundle.getString(SCREENSHOT_PATH));
                if (newScreenshot.exists()) {
                    Bitmap bitmap = getBitmap(newScreenshot.getPath());
                    if(bitmap != null ) {
                        analyzeImage(context, bitmap);
                    }
                }
            }
        }
    }

    public Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void analyzeImage(final Context context, Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient();
        Task<Text> res = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text text) {
                int cnt = 0;
                String date = null, time = null, location = null;
                StringBuilder description = new StringBuilder();
                boolean nextLocation = false;
                for (Text.TextBlock block : text.getTextBlocks()) {
                    if(cnt == 0) {
                        ++cnt;
                        continue;
                    }
                    for (Text.Line line : block.getLines()) {
                        String lineText = line.getText().trim();
                        String[] subSplit = new String[0];
                        if(lineText.contains("Add")) {
                            nextLocation = true;
                            continue;
                        }
                        if(location == null && nextLocation) {
                            location = lineText;
                            continue;
                        } else if(nextLocation) {
                            description.append(lineText).append("\n");
                        }
                        if(lineText.startsWith("O")) {
                            lineText = lineText.substring(1);
                        }
                        if(lineText.contains("from")) {
                            subSplit = lineText.split("from");
                        } else if(lineText.contains("to")) {
                            subSplit = lineText.split("to");
                        }

                        if(subSplit.length > 0) {
                            for(String sPrime: subSplit) {
                                if(date == null && Parser.isValidDate(sPrime)) {
                                    date = sPrime.trim();
                                }
                                if(time == null && Parser.isValidTime(sPrime)) {
                                    time = sPrime;
                                }
                            }
                        } else {
                            if(date == null && Parser.isValidDate(lineText)) {
                                date = lineText;
                            }
                            if(time == null && Parser.isValidTime(lineText)) {
                                time = lineText;
                            }
                        }
                    }
                }
                CalendarManager.addToCalendar(context, location, location, description.toString(), Parser.getDateInMillis(date) + Parser.getTimeInMillis(time));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to parse screenshot", Toast.LENGTH_LONG).show();
            }
        });

    }
}

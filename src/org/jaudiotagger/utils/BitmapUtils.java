package org.jaudiotagger.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Maxim Becker on 07.02.15.
 */
public class BitmapUtils {

    public static Bitmap decodeByteArray(byte[] data) {
        return decodeByteArray(data, 0, data.length);
    }

    public static Bitmap decodeByteArray(byte[] data, int offset, int length) {
        return BitmapFactory.decodeByteArray(data, offset, length);
    }

    public static Bitmap decodeInputStream(InputStream stream) {
        return BitmapFactory.decodeStream(stream);
    }

    public static Bitmap decodeFile(File file) throws FileNotFoundException {
        return BitmapFactory.decodeStream(new FileInputStream(file));
    }

    public static byte[] getBytes(Bitmap bitmap) {
        int byteCount = bitmap.getByteCount();
        ByteBuffer buffer = ByteBuffer.allocate(byteCount);
        bitmap.copyPixelsToBuffer(buffer);
        buffer.rewind();

        byte[] data = new byte[byteCount];
        buffer.get(data);
        return data;
    }

    public static Bitmap scaleBitmap(Bitmap srcBitmap, int newSize) {
        int w = srcBitmap.getWidth();
        int h = srcBitmap.getHeight();

        // Determine the scaling required to get desired result.
        float scaleW = newSize / (float) w;
        float scaleH = newSize / (float) h;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleW, scaleH);

        return Bitmap.createBitmap(srcBitmap, 0, 0, w, h, matrix, false);
    }

}

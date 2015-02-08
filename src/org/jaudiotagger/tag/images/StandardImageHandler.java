package org.jaudiotagger.tag.images;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 Image Handling used when running on standard JVM
 */
public class StandardImageHandler implements ImageHandler
{
    private static StandardImageHandler instance;

    public static StandardImageHandler getInstanceOf()
    {
        if (instance==null)
        {
            instance = new StandardImageHandler();
        }
        return instance;
    }

    private StandardImageHandler()
    {

    }

    /**
     * Resize the image until the total size require to store the image is less than maxsize
     * @param artwork
     * @param maxSize
     * @throws IOException
     */
    public void reduceQuality(Artwork artwork, int maxSize) throws IOException
    {
        while(artwork.getBinaryData().length > maxSize)
        {
            Bitmap srcImage = (Bitmap)artwork.getImage();
            int w = srcImage.getWidth();
            int newSize = w /2;
            makeSmaller(artwork,newSize);
        }
    }
     /**
     * Resize image using Java 2D
      * @param artwork
      * @param size
      * @throws java.io.IOException
      */
    public void makeSmaller(Artwork artwork, int size) throws IOException
    {
        throw new UnsupportedOperationException("StandardImageHandler not supported on android");
    }

    public boolean isMimeTypeWritable(String mimeType)
    {
        throw new UnsupportedOperationException("StandardImageHandler not supported on android");
    }
    /**
     *  Write buffered image as required format
     *
     * @param artwork
     * @return
     * @throws IOException
     */
    public void writeImage(Artwork artwork) throws IOException
    {
        throw new UnsupportedOperationException("StandardImageHandler not supported on android");
    }

    /**
     *
     * @param artwork
     * @return
     * @throws IOException
     */
    public void writeImageAsPng(Artwork artwork) throws IOException
    {
        throw new UnsupportedOperationException("StandardImageHandler not supported on android");
    }

    /**
     * Show read formats
     *
     * On Windows supports png/jpeg/bmp/gif
     */
    public void showReadFormats()
    {
        throw new UnsupportedOperationException("StandardImageHandler not supported on android");
    }

    /**
     * Show write formats
     *
     * On Windows supports png/jpeg/bmp
     */
    public void showWriteFormats()
    {
        throw new UnsupportedOperationException("StandardImageHandler not supported on android");
    }
}

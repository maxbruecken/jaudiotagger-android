package org.jaudiotagger.tag.images;

import java.io.IOException;

/**
 * Image Handler
 */
public interface ImageHandler
{
    public void reduceQuality(Artwork artwork, int maxSize) throws IOException;
    public void makeSmaller(Artwork artwork, int size) throws IOException;
    public boolean isMimeTypeWritable(String mimeType);
    public void writeImage(Artwork artwork) throws IOException;
    public void writeImageAsPng(Artwork artwork) throws IOException;
    public void showReadFormats();
    public void showWriteFormats();
}

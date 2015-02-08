package org.jaudiotagger.tag.images;

import android.graphics.Bitmap;

import org.jaudiotagger.audio.flac.metadatablock.MetadataBlockDataPicture;
import org.jaudiotagger.tag.id3.valuepair.ImageFormats;
import org.jaudiotagger.tag.reference.PictureTypes;
import org.jaudiotagger.utils.BitmapUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Represents artwork in a format independent way
 */
public class AndroidArtwork extends AbstractArtwork<AndroidArtwork>
{
    private byte[]          binaryData;
    private String          mimeType="";
    private String          description="";
    private boolean         isLinked=false;
    private String          imageUrl="";
    private int             pictureType=-1;
    private int             width;
    private int             height;

    public AndroidArtwork()
    {
        super(AndroidArtwork.class);
    }

    /**
     * Should be called when you wish to prime the artwork for saving
     *
     * @return
     */
    public boolean setImageFromData()
    {
        try
        {
            Bitmap image = (Bitmap)getImage();
            setWidth(image.getWidth());
            setHeight(image.getHeight());
        }
        catch(IOException ioe)
        {
            return false;
        }
        return true;
    }

    public Object getImage() throws IOException
    {
        return BitmapUtils.decodeByteArray(getBinaryData());
    }

    /**
     * Create Artwork from File
     *
     * @param file
     * @throws java.io.IOException
     */
    public void setFromFile(File file)  throws IOException
    {
        RandomAccessFile imageFile = new RandomAccessFile(file, "r");
        byte[] imagedata = new byte[(int) imageFile.length()];
        imageFile.read(imagedata);
        imageFile.close();

        setBinaryData(imagedata);
        setMimeType(ImageFormats.getMimeTypeForBinarySignature(imagedata));
        setDescription("");
        setPictureType(PictureTypes.DEFAULT_ID);
    }

    /**
     * Create Artwork from File
     *
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static AndroidArtwork createArtworkFromFile(File file)  throws IOException
    {
        AndroidArtwork artwork = new AndroidArtwork();
        artwork.setFromFile(file);
        return artwork;
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static AndroidArtwork createLinkedArtworkFromURL(String url)  throws IOException
    {
        AndroidArtwork artwork = new AndroidArtwork();
        artwork.setLinkedFromURL(url);
        return artwork;
    }

    /**
     * Create artwork from Flac block
     *
     * @param coverArt
     * @return
     */
    public static AndroidArtwork createArtworkFromMetadataBlockDataPicture(MetadataBlockDataPicture coverArt)
    {
        AndroidArtwork artwork = new AndroidArtwork();
        artwork.setFromMetadataBlockDataPicture(coverArt);
        return artwork;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }
}

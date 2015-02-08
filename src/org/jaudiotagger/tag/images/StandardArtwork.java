package org.jaudiotagger.tag.images;

import org.jaudiotagger.audio.flac.metadatablock.MetadataBlockDataPicture;

import java.io.File;
import java.io.IOException;

/**
 * Represents artwork in a format independent way
 */
public class StandardArtwork extends AbstractArtwork {

    public StandardArtwork()
    {
        super(StandardArtwork.class);
    }

    /**
     * Should be called when you wish to prime the artwork for saving
     *
     * @return
     */
    public boolean setImageFromData()
    {
        throw new UnsupportedOperationException("StandardArtwork not supported on android");
    }

    public Object getImage() throws IOException
    {
        throw new UnsupportedOperationException("StandardArtwork not supported on android");
    }

    /**
     * Create Artwork from File
     *
     * @param file
     * @throws java.io.IOException
     */
    public void setFromFile(File file)  throws IOException
    {
        throw new UnsupportedOperationException("StandardArtwork not supported on android");
    }

    /**
     * Create Artwork from File
     *
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static StandardArtwork createArtworkFromFile(File file)  throws IOException
    {
        StandardArtwork artwork = new StandardArtwork();
        artwork.setFromFile(file);
        return artwork;
    }

    public static StandardArtwork createLinkedArtworkFromURL(String url)  throws IOException
    {
        StandardArtwork artwork = new StandardArtwork();
        artwork.setLinkedFromURL(url);
        return artwork;
    }

    /**
     * Create artwork from Flac block
     *
     * @param coverArt
     * @return
     */
    public static StandardArtwork createArtworkFromMetadataBlockDataPicture(MetadataBlockDataPicture coverArt)
    {
        StandardArtwork artwork = new StandardArtwork();
        artwork.setFromMetadataBlockDataPicture(coverArt);
        return artwork;
    }

}

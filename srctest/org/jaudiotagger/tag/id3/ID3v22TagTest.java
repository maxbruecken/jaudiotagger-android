package org.jaudiotagger.tag.id3;

import static org.junit.Assert.*;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.id3.framebody.AbstractFrameBodyTextInfo;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTALB;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTCON;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTDRC;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTIT2;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTPE1;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTRCK;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 *
 */
public class ID3v22TagTest {

    /////////////////////////////////////////////////////////////////////////
    // TestCase classes to override
    /////////////////////////////////////////////////////////////////////////

    /**
        *
        */
    @Before
    public void setUp()
       {
           TagOptionSingleton.getInstance().setToDefault();
       }

       /**
        *
        */
       @After
       public void tearDown()
       {
       }


    /////////////////////////////////////////////////////////////////////////
    // Tests
    /////////////////////////////////////////////////////////////////////////


    @Test
    public void testCreateIDv22Tag()
    {
        ID3v22Tag v2Tag = new ID3v22Tag();
        Assert.assertEquals((byte) 2, v2Tag.getRelease());
        Assert.assertEquals((byte) 2, v2Tag.getMajorVersion());
        Assert.assertEquals((byte) 0, v2Tag.getRevision());
    }

    @Test
    public void testCreateID3v22FromID3v11()
    {
        ID3v11Tag v11Tag = ID3v11TagTest.getInitialisedTag();
        ID3v22Tag v2Tag = new ID3v22Tag(v11Tag);
        Assert.assertNotNull(v11Tag);
        Assert.assertNotNull(v2Tag);
        Assert.assertEquals(ID3v11TagTest.ARTIST, ((FrameBodyTPE1) ((ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_ARTIST)).getBody()).getText());
        Assert.assertEquals(ID3v11TagTest.ALBUM, ((FrameBodyTALB) ((ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_ALBUM)).getBody()).getText());
        Assert.assertEquals(ID3v11TagTest.COMMENT, ((FrameBodyCOMM) ((ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_COMMENT)).getBody()).getText());
        Assert.assertEquals(ID3v11TagTest.TITLE, ((FrameBodyTIT2) ((ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_TITLE)).getBody()).getText());
        Assert.assertEquals(ID3v11TagTest.TRACK_VALUE, String.valueOf(((FrameBodyTRCK) ((ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_TRACK)).getBody()).getTrackNo()));
        Assert.assertTrue(((FrameBodyTCON) ((ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_GENRE)).getBody()).getText().endsWith(ID3v11TagTest.GENRE_VAL));

        //TODO:Note confusingly V22 YEAR Frame shave v2 identifier but use TDRC behind the scenes, is confusing
        Assert.assertEquals(ID3v11TagTest.YEAR, ((FrameBodyTDRC) ((ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_TYER)).getBody()).getText());

        Assert.assertEquals((byte) 2, v2Tag.getRelease());
        Assert.assertEquals((byte) 2, v2Tag.getMajorVersion());
        Assert.assertEquals((byte) 0, v2Tag.getRevision());
    }

    @Test
    public void testCreateIDv22TagAndSave()
    {
        Exception exception = null;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3");
            MP3File mp3File = new MP3File(testFile);
            ID3v22Tag v2Tag = new ID3v22Tag();
            v2Tag.setField(FieldKey.TITLE,"fred");
            v2Tag.setField(FieldKey.ARTIST,"artist");
            v2Tag.setField(FieldKey.ALBUM,"album");

            Assert.assertEquals((byte) 2, v2Tag.getRelease());
            Assert.assertEquals((byte) 2, v2Tag.getMajorVersion());
            Assert.assertEquals((byte) 0, v2Tag.getRevision());
            mp3File.setID3v2Tag(v2Tag);
            mp3File.save();

            //Read using new Interface
            AudioFile v22File = AudioFileIO.read(testFile);
            Assert.assertEquals("fred", v22File.getTag().getFirst(FieldKey.TITLE));
            Assert.assertEquals("artist", v22File.getTag().getFirst(FieldKey.ARTIST));
            Assert.assertEquals("album", v22File.getTag().getFirst(FieldKey.ALBUM));

            //Read using old Interface
            mp3File = new MP3File(testFile);
            v2Tag = (ID3v22Tag) mp3File.getID3v2Tag();
            ID3v22Frame frame = (ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_TITLE);
            Assert.assertEquals("fred", ((AbstractFrameBodyTextInfo) frame.getBody()).getText());

        }
        catch (Exception e)
        {
            exception = e;
        }
        Assert.assertNull(exception);
    }

    @Test
    public void testv22TagWithUnneccessaryTrailingNulls()
    {
        File orig = new File("testdata", "test24.mp3");
        if (!orig.isFile())
        {
            return;
        }

        Exception exception = null;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("test24.mp3");
            AudioFile af = AudioFileIO.read(testFile);
            MP3File m = (MP3File) af;

            //Read using new Interface getFirst method with key
            Assert.assertEquals("*Listen to images:*", "*" + af.getTag().getFirst(FieldKey.TITLE) + ":*");
            Assert.assertEquals("Clean:", af.getTag().getFirst(FieldKey.ALBUM) + ":");
            Assert.assertEquals("Cosmo Vitelli:", af.getTag().getFirst(FieldKey.ARTIST) + ":");
            Assert.assertEquals("Electronica/Dance:", af.getTag().getFirst(FieldKey.GENRE) + ":");
            Assert.assertEquals("2003:", af.getTag().getFirst(FieldKey.YEAR) + ":");


            //Read using new Interface getFirst method with String
            Assert.assertEquals("Listen to images:", af.getTag().getFirst(ID3v22Frames.FRAME_ID_V2_TITLE) + ":");
            Assert.assertEquals("Clean:", af.getTag().getFirst(ID3v22Frames.FRAME_ID_V2_ALBUM) + ":");
            Assert.assertEquals("Cosmo Vitelli:", af.getTag().getFirst(ID3v22Frames.FRAME_ID_V2_ARTIST) + ":");
            Assert.assertEquals("Electronica/Dance:", af.getTag().getFirst(ID3v22Frames.FRAME_ID_V2_GENRE) + ":");
            Assert.assertEquals("2003:", af.getTag().getFirst(ID3v22Frames.FRAME_ID_V2_TYER) + ":");
            Assert.assertEquals("1:", af.getTag().getFirst(ID3v22Frames.FRAME_ID_V2_TRACK) + ":");

            //Read using new Interface getFirst methods for common fields
            Assert.assertEquals("Listen to images:", af.getTag().getFirst(FieldKey.TITLE) + ":");
            Assert.assertEquals("Cosmo Vitelli:", af.getTag().getFirst(FieldKey.ARTIST) + ":");
            Assert.assertEquals("Clean:", af.getTag().getFirst(FieldKey.ALBUM) + ":");
            Assert.assertEquals("Electronica/Dance:", af.getTag().getFirst(FieldKey.GENRE) + ":");
            Assert.assertEquals("2003:", af.getTag().getFirst(FieldKey.YEAR) + ":");

            //Read using old Interface
            ID3v22Tag v2Tag = (ID3v22Tag) m.getID3v2Tag();
            ID3v22Frame frame = (ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_TITLE);
            Assert.assertEquals("Listen to images:", ((AbstractFrameBodyTextInfo) frame.getBody()).getText() + ":");
            frame = (ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_ARTIST);
            Assert.assertEquals("Cosmo Vitelli:", ((AbstractFrameBodyTextInfo) frame.getBody()).getText() + ":");
            frame = (ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_ALBUM);
            Assert.assertEquals("Clean:", ((AbstractFrameBodyTextInfo) frame.getBody()).getText() + ":");
            frame = (ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_GENRE);
            Assert.assertEquals("Electronica/Dance:", ((AbstractFrameBodyTextInfo) frame.getBody()).getText() + ":");
            frame = (ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_TYER);
            Assert.assertEquals("2003:", ((AbstractFrameBodyTextInfo) frame.getBody()).getText() + ":");
            frame = (ID3v22Frame) v2Tag.getFrame(ID3v22Frames.FRAME_ID_V2_TRACK);
            Assert.assertEquals("01/11:", ((FrameBodyTRCK) frame.getBody()).getText() + ":");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            exception = e;
        }
        Assert.assertNull(exception);
    }

     @Test
     public void testDeleteFields() throws Exception
    {
        File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3");
        MP3File mp3File = new MP3File(testFile);
        ID3v22Tag v2Tag = new ID3v22Tag();
        mp3File.setID3v2Tag(v2Tag);
        mp3File.save();

        //Delete using generic key
        AudioFile f = AudioFileIO.read(testFile);
        List<TagField> tagFields = f.getTag().getFields(FieldKey.ALBUM_ARTIST_SORT);
        Assert.assertEquals(0, tagFields.size());
        f.getTag().addField(FieldKey.ALBUM_ARTIST_SORT,"artist1");
        tagFields = f.getTag().getFields(FieldKey.ALBUM_ARTIST_SORT);
        Assert.assertEquals(1, tagFields.size());
        f.getTag().deleteField(FieldKey.ALBUM_ARTIST_SORT);
        f.commit();

        //Delete using flac id
        f = AudioFileIO.read(testFile);
        tagFields = f.getTag().getFields(FieldKey.ALBUM_ARTIST_SORT);
        Assert.assertEquals(0, tagFields.size());
        f.getTag().addField(FieldKey.ALBUM_ARTIST_SORT,"artist1");
        tagFields = f.getTag().getFields(FieldKey.ALBUM_ARTIST_SORT);
        Assert.assertEquals(1, tagFields.size());
        f.getTag().deleteField("TS2");
        tagFields = f.getTag().getFields(FieldKey.ALBUM_ARTIST_SORT);
        Assert.assertEquals(0, tagFields.size());
        f.commit();

        f = AudioFileIO.read(testFile);
        tagFields = f.getTag().getFields(FieldKey.ALBUM_ARTIST_SORT);
        Assert.assertEquals(0, tagFields.size());
    }

    public void testWriteMultipleGenresToID3v22TagUsingDefault() throws Exception
    {
        File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3");
        MP3File file = null;
        file = new MP3File(testFile);
        assertNull(file.getID3v1Tag());
        file.setTag(new ID3v22Tag());
        assertNotNull(file.getTag());
        file.getTag().deleteField(FieldKey.GENRE);
        file.getTag().addField(FieldKey.GENRE,"Genre1");
        file.getTag().addField(FieldKey.GENRE,"Genre2");
        file.commit();
        file = new MP3File(testFile);
        assertEquals("Genre1",file.getTag().getFirst(FieldKey.GENRE));
        assertEquals("Genre1",file.getTag().getValue(FieldKey.GENRE, 0));
        assertEquals("Genre2",file.getTag().getValue(FieldKey.GENRE, 1));

        TagOptionSingleton.getInstance().setWriteMp3GenresAsText(false);
        file.getTag().deleteField(FieldKey.GENRE);
        file.getTag().addField(FieldKey.GENRE,"Death Metal");
        file.getTag().addField(FieldKey.GENRE,"(23)");
        assertEquals("Death Metal",file.getTag().getFirst(FieldKey.GENRE));
        assertEquals("Death Metal",file.getTag().getValue(FieldKey.GENRE, 0));
        assertEquals("Pranks",file.getTag().getValue(FieldKey.GENRE, 1));
        file.commit();
        file = new MP3File(testFile);
        assertEquals("Death Metal",file.getTag().getFirst(FieldKey.GENRE));
        assertEquals("Death Metal",file.getTag().getValue(FieldKey.GENRE, 0));
        assertEquals("Pranks",file.getTag().getValue(FieldKey.GENRE, 1));

        TagOptionSingleton.getInstance().setWriteMp3GenresAsText(true);
        file.getTag().deleteField(FieldKey.GENRE);
        file.getTag().addField(FieldKey.GENRE,"Death Metal");
        file.getTag().addField(FieldKey.GENRE,"23");
        assertEquals("Death Metal",file.getTag().getFirst(FieldKey.GENRE));
        assertEquals("Death Metal",file.getTag().getValue(FieldKey.GENRE, 0));
        assertEquals("Pranks",file.getTag().getValue(FieldKey.GENRE, 1));
        file.commit();
        file = new MP3File(testFile);
        assertEquals("Death Metal",file.getTag().getFirst(FieldKey.GENRE));
        assertEquals("Death Metal",file.getTag().getValue(FieldKey.GENRE, 0));
        assertEquals("Pranks",file.getTag().getValue(FieldKey.GENRE, 1));
    }
}

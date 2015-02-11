package org.jaudiotagger.tag.flac;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.flac.FlacInfoReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * basic Flac tests
 */
public class FlacReadTest {
    /**
     * Read Flac File
     */
    @Test
    public void testReadTwoChannelFile()
    {
        Exception exceptionCaught = null;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("test2.flac", new File("test2read.flac"));
            AudioFile f = AudioFileIO.read(testFile);

            Assert.assertEquals("192", f.getAudioHeader().getBitRate());
            Assert.assertEquals("FLAC 16 bits", f.getAudioHeader().getEncodingType());
            Assert.assertEquals("2", f.getAudioHeader().getChannels());
            Assert.assertEquals("44100", f.getAudioHeader().getSampleRate());
            Assert.assertEquals(5, f.getAudioHeader().getTrackLength());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            exceptionCaught = e;
        }
        Assert.assertNull(exceptionCaught);
    }

    /**
     * Read Flac File
     */
    @Test
    public void testReadSingleChannelFile()
    {
        Exception exceptionCaught = null;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("test3.flac", new File("test3read.flac"));
            AudioFile f = AudioFileIO.read(testFile);

            Assert.assertEquals("FLAC 8 bits", f.getAudioHeader().getEncodingType());
            Assert.assertEquals("1", f.getAudioHeader().getChannels());
            Assert.assertEquals("16000", f.getAudioHeader().getSampleRate());
            Assert.assertEquals(0, f.getAudioHeader().getTrackLength());
            Assert.assertEquals("47", f.getAudioHeader().getBitRate());       //is this correct value
        }                                           
        catch (Exception e)
        {
            e.printStackTrace();
            exceptionCaught = e;
        }
        Assert.assertNull(exceptionCaught);
    }


    /**
     * Test can identify file that isnt flac
     */
    @Test
    public void testNotFlac()
    {
        Exception exceptionCaught = null;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3", new File("testV1noFlac.flac"));
            AudioFile f = AudioFileIO.read(testFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            exceptionCaught = e;
        }
        Assert.assertTrue(exceptionCaught instanceof CannotReadException);
    }

    /**
     * Reading file that contains cuesheet
     */
    @Test
    public void testReadCueSheet()
    {
        Exception exceptionCaught = null;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("test3.flac");
            AudioFile f = AudioFileIO.read(testFile);
            FlacInfoReader infoReader = new FlacInfoReader();
            Assert.assertEquals(5, infoReader.countMetaBlocks(f.getFile()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            exceptionCaught = e;
        }
        Assert.assertNull(exceptionCaught);
    }


    /**
     * test read flac file with preceding ID3 header
     */
    @Test
    public void testReadFileWithId3Header()
    {
        Exception exceptionCaught = null;
        try
        {
            File orig = new File("testdata", "test22.flac");
            if (!orig.isFile())
            {
                System.out.println("Test cannot be run because test file not available");
                return;
            }
            File testFile = AbstractTestCase.copyAudioToTmp("test22.flac", new File("testreadFlacWithId3.flac"));
            AudioFile f = AudioFileIO.read(testFile);
            FlacInfoReader infoReader = new FlacInfoReader();
            Assert.assertEquals(4, infoReader.countMetaBlocks(f.getFile()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            exceptionCaught = e;
        }
        Assert.assertNull(exceptionCaught);
    }


    /**
     * test read flac file with no header
     */
    @Test
    public void testReadFileWithOnlyStreamInfoHeader()
    {
        Exception exceptionCaught = null;
        try
        {
            File orig = new File("testdata", "test102.flac");
            if (!orig.isFile())
            {
                System.out.println("Test cannot be run because test file not available");
                return;
            }
            File testFile = AbstractTestCase.copyAudioToTmp("test102.flac", new File("test102.flac"));
            AudioFile f = AudioFileIO.read(testFile);
            FlacInfoReader infoReader = new FlacInfoReader();
            Assert.assertEquals(1, infoReader.countMetaBlocks(f.getFile()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            exceptionCaught = e;
        }
        Assert.assertNull(exceptionCaught);
    }
}

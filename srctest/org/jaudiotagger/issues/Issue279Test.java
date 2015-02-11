package org.jaudiotagger.issues;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * ID3 Tag Specific flags
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class Issue279Test extends AbstractTestCase
{

    /**
     * Test write to ogg, cant find parent setup header
     */
    @Test
    public void testWriteToOgg() throws Exception {
        File orig = new File("testdata", "test55.ogg");
        if (!orig.isFile())
        {
            System.err.println("Unable to test file - not available");
            return;
        }

        File testFile = AbstractTestCase.copyAudioToTmp("test55.ogg");

        //Read File okay
        AudioFile af = AudioFileIO.read(testFile);
        System.out.println(af.getTag().toString());


        af.getTag().setField(FieldKey.ALBUM,"FRED");
        af.commit();
        af = AudioFileIO.read(testFile);
        System.out.println(af.getTag().toString());
        assertEquals("FRED", af.getTag().getFirst(FieldKey.ALBUM));
    }


}

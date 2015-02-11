package org.jaudiotagger.issues;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.flac.FlacTag;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.junit.Assert.assertNotNull;

/**
 * Test
 */
public class Issue468Test extends AbstractTestCase
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReadFlac() throws Exception
    {
        thrown.expect(FieldDataInvalidException.class);
        thrown.expectMessage("ImageData cannot be null");

        File testFile = AbstractTestCase.copyAudioToTmp("test.flac");
        AudioFile af = AudioFileIO.read(testFile);
        assertNotNull(af.getTag());
        FlacTag tag = (FlacTag)af.getTag();
        tag.setField( tag.createArtworkField(null, 1, "","", 100, 200, 128, 1));
        af.commit();
    }
}

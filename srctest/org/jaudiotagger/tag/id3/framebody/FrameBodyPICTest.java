package org.jaudiotagger.tag.id3.framebody;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.tag.id3.ID3v22Frames;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test PICFrameBody
 */
public class FrameBodyPICTest extends AbstractTestCase
{
    public static String DESCRIPTION = "ImageTestv22";

    public static FrameBodyPIC getInitialisedBody()
    {
        FrameBodyPIC fb = new FrameBodyPIC();
        fb.setDescription(FrameBodyPICTest.DESCRIPTION);
        return fb;
    }


    @Test
    public void testCreateFrameBody()
    {
        Exception exceptionCaught = null;
        FrameBodyPIC fb = null;
        try
        {
            fb = new FrameBodyPIC();
        }
        catch (Exception e)
        {
            exceptionCaught = e;
        }

        assertNull(exceptionCaught);
        assertEquals(ID3v22Frames.FRAME_ID_V2_ATTACHED_PICTURE, fb.getIdentifier());
        assertTrue(fb.getDescription() == null);

    }

    @Test
    public void testCreateFrameBodyEmptyConstructor()
    {

        Exception exceptionCaught = null;
        FrameBodyPIC fb = null;
        try
        {
            fb = new FrameBodyPIC();
            fb.setDescription(FrameBodyPICTest.DESCRIPTION);
        }
        catch (Exception e)
        {
            exceptionCaught = e;
        }

        assertNull(exceptionCaught);
        assertEquals(ID3v22Frames.FRAME_ID_V2_ATTACHED_PICTURE, fb.getIdentifier());
        assertEquals(FrameBodyPICTest.DESCRIPTION, fb.getDescription());


    }


}

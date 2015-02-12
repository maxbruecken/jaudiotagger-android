package org.jaudiotagger.tag.id3.framebody;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test APICFrameBody
 */
public class FrameBodyAPICTest extends AbstractTestCase
{
    public static String DESCRIPTION = "ImageTest";

    public static FrameBodyAPIC getInitialisedBody()
    {
        FrameBodyAPIC fb = new FrameBodyAPIC();
        fb.setDescription(DESCRIPTION);
        return fb;
    }


    @Test
    public void testCreateFrameBody()
    {
        Exception exceptionCaught = null;
        FrameBodyAPIC fb = null;
        try
        {
            fb = new FrameBodyAPIC();
        }
        catch (Exception e)
        {
            exceptionCaught = e;
        }

        assertNull(exceptionCaught);
        assertEquals(ID3v24Frames.FRAME_ID_ATTACHED_PICTURE, fb.getIdentifier());
        assertTrue(fb.getDescription() == null);

    }

    @Test
    public void testCreateFrameBodyEmptyConstructor()
    {

        Exception exceptionCaught = null;
        FrameBodyAPIC fb = null;
        try
        {
            fb = new FrameBodyAPIC();
            fb.setDescription(DESCRIPTION);
        }
        catch (Exception e)
        {
            exceptionCaught = e;
        }

        assertNull(exceptionCaught);
        assertEquals(ID3v24Frames.FRAME_ID_ATTACHED_PICTURE, fb.getIdentifier());
        assertEquals(DESCRIPTION, fb.getDescription());


    }


}

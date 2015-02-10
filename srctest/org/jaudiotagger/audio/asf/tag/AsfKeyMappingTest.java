package org.jaudiotagger.audio.asf.tag;

import junit.framework.TestCase;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.asf.AsfTag;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christian Laireiter
 *
 */
public class AsfKeyMappingTest {

    /**
     * This method tests whether each {@link org.jaudiotagger.tag.FieldKey} is mapped
     * to an {@link org.jaudiotagger.tag.asf.AsfFieldKey}.<br>
     */
    @Test
    public void testTagFieldKeyMappingComplete()
    {
        Exception exceptionCaught=null;
        Tag tag = new AsfTag();
        try
        {
            for (FieldKey curr : FieldKey.values())
            {
                tag.getFields(curr);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            exceptionCaught=e;
        }
        Assert.assertNull(exceptionCaught);
    }
}

package org.jaudiotagger.audio.generic;

import java.io.File;
import java.util.logging.Logger;

/**
 * Outputs permissions to try and identify why we dont have permissions to read/write file
 */
public class Permissions
{
    public static Logger logger = Logger.getLogger("org.jaudiotagger.audio.generic");

    /**
     * Display Permissions
     *
     * @param path
     * @return
     */
    public static String displayPermissions(File path)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot display permissions on android");
        return sb.toString();
    }
}

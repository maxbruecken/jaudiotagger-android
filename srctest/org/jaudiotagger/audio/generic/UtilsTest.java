package org.jaudiotagger.audio.generic;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;


public class UtilsTest {

    @Test
    public void testCopy () {
        byte[] src = new byte[] { 0, 1, 2, 3, 4, 5};
        byte[] dst = new byte[8];
        Utils.copy (src, dst, 2);
        Assert.assertTrue(dst[2] == 0 && dst[3] == 1 && dst[4] == 2 &&
                dst[5] == 3 && dst[6] == 4 && dst[7] == 5);
    }
    
    @Test
    public void testGetExtension () {
        Assert.assertEquals("jpeg", Utils.getExtension(new File("_12XYZ.jpeg")));
    }
    
    @Test
    public void testReadUInt16 () {
        try {
            byte[] maxUnsignedBuf = { (byte) 0XFF, (byte) 0XFF };
            ByteArrayInputStream ins = new ByteArrayInputStream (maxUnsignedBuf);
            DataInputStream dis = new DataInputStream (ins);
            int val = Utils.readUint16(dis);
            Assert.assertEquals(val, 0XFFFF);

            byte[] smallIntBuf = { (byte) 0X01, (byte) 0X10 };
            ins = new ByteArrayInputStream (smallIntBuf);
            dis = new DataInputStream (ins);
            val = Utils.readUint16(dis);
            Assert.assertEquals(val, 0X0110);
        }
        catch (IOException e) {
            Assert.fail("IOException in testReadUInt16");  // huh?
        }
    }

    @Test
    public void testReadInt16 () {
        try {
            byte[] maxUnsignedBuf = { (byte) 0XFF, (byte) 0XFF };
            ByteArrayInputStream ins = new ByteArrayInputStream (maxUnsignedBuf);
            DataInputStream dis = new DataInputStream (ins);
            int val = Utils.readInt16(dis);
            Assert.assertEquals(val, -1);

            byte[] smallIntBuf = { (byte) 0X01, (byte) 0X10 };
            ins = new ByteArrayInputStream (smallIntBuf);
            dis = new DataInputStream (ins);
            val = Utils.readInt16(dis);
            Assert.assertEquals(val, 0X0110);
        }
        catch (IOException e) {
            Assert.fail("IOException in testReadInt16");  // huh?
        }
    }

    @Test
    public void testReadUInt32 () {
        try {
            byte[] maxUnsignedBuf = { (byte) 0XFF, (byte) 0XFF, (byte) 0XFF, (byte) 0XFF };
            ByteArrayInputStream ins = new ByteArrayInputStream (maxUnsignedBuf);
            DataInputStream dis = new DataInputStream (ins);
            long val = Utils.readUint32(dis);
            Assert.assertEquals(val, 0XFFFFFFFFL);

            byte[] smallIntBuf = { (byte) 0X03, (byte) 0XFF, (byte) 0X01, (byte) 0X12 };
            ins = new ByteArrayInputStream (smallIntBuf);
            dis = new DataInputStream (ins);
            val = Utils.readUint32(dis);
            Assert.assertEquals(val, 0X03FF0112);
        }
        catch (IOException e) {
            Assert.fail("IOException in testReadUInt32");  // huh?
        }
    }
    
    @Test
    public void testReadString () {
        try {
            byte [] strBuf = { (byte) 'A', (byte) '=', (byte) '1', (byte) '+', (byte) '7' };
            ByteArrayInputStream ins = new ByteArrayInputStream (strBuf);
            DataInputStream dis = new DataInputStream (ins);
            String s = Utils.readString(dis,  5);
            Assert.assertEquals("A=1+7", s);
        }
        catch (IOException e) {
            Assert.fail("IOException in testReadString");  // huh?
        }
    }
    
    @Test
    public void testGetSizeLEInt32 () {
        byte[] bytes = Utils.getSizeLEInt32(0X010203F4);
        Assert.assertEquals(bytes.length, 4);
        // need to AND with FF to avoid sign extension of byte
        Assert.assertEquals(bytes[0] & 0XFF, 0XF4);
        Assert.assertEquals(bytes[1] & 0XFF, 0X03);
        Assert.assertEquals(bytes[2] & 0XFF, 0X02);
        Assert.assertEquals(bytes[3] & 0XFF, 0X01);
    }

    @Test
    public void testGetSizeBEInt32 () {
        byte[] bytes = Utils.getSizeBEInt32(0X010203F4);
        Assert.assertEquals(bytes.length, 4);
        // need to AND with FF to avoid sign extension of byte
        Assert.assertEquals(bytes[3] & 0XFF, 0XF4);
        Assert.assertEquals(bytes[2] & 0XFF, 0X03);
        Assert.assertEquals(bytes[1] & 0XFF, 0X02);
        Assert.assertEquals(bytes[0] & 0XFF, 0X01);
    }

    @Test
    public void testGetSizeBEInt16 () {
        byte[] bytes = Utils.getSizeBEInt16((short) 0X0182);
        Assert.assertEquals(bytes.length, 2);
        // need to AND with FF to avoid sign extension of byte
        Assert.assertEquals(bytes[1] & 0XFF, 0X82);
        Assert.assertEquals(bytes[0] & 0XFF, 0X01);
    }

    @Test
    public void testgetLongLE () {
        ByteBuffer bb = ByteBuffer.allocate(4);
        byte[] bytes = new byte[] {0X32, 0X01, (byte) 0XF0, 0X18};
        bb.put(bytes);
        long val = Utils.getLongLE (bb, 0, 3);
        Assert.assertEquals(0X18F00132L, val);
    }

    @Test
    public void testgetLongBE () {
        ByteBuffer bb = ByteBuffer.allocate(4);
        byte[] bytes = new byte[] {0X32, 0X01, (byte) 0XF0, 0X18};
        bb.put(bytes);
        long val = Utils.getLongBE (bb, 0, 3);
        Assert.assertEquals(0X3201F018L, val);
        
        bytes = new byte[] {0X01, 0X02, 0X03, 0X04, 0X05, 0X06, 0X07, 0X08};
        bb = ByteBuffer.allocate(8);
        bb.put(bytes);
        val = Utils.getLongBE (bb, 0, 7);
        Assert.assertEquals(0X0102030405060708L, val);
    }
    
    @Test
    public void testGetIntLE () {
        byte[] bytes = new byte[] {(byte) 0XFF, 0X01, 0X11, 0X21};
        int val = Utils.getIntLE (bytes);
        Assert.assertEquals(0X211101FF, val);
        
        bytes = new byte[] {0, (byte) 0XFF, 0X01, 0X11, 0X31, 0};
        val = Utils.getIntLE (bytes, 1, 4);
        Assert.assertEquals(0X311101FF, val);
    }

    @Test
    public void testGetIntBE () {
        // For some reason there's no getIntBE (byte[])
        byte[] bytes = new byte[] {0, (byte) 0X7F, 0X01, 0X11, 0X31, 0};
        int val = Utils.getIntBE (bytes, 1, 4);
        Assert.assertEquals(0X7F011131, val);
        
        // But there is one with a ByteBuffer, a start, and an end
        ByteBuffer bb = ByteBuffer.allocate(5);
        bytes = new byte[] {0, 0X32, (byte) 0X80, 0X70, 0X18};
        bb.put(bytes);
        val = Utils.getIntBE(bb, 1, 4);
        Assert.assertEquals(0X32807018, val);
    }

}

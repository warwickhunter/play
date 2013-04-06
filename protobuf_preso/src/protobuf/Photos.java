/**
 * Copyright Warwick Hunter 2013. All rights reserved.
 */
package protobuf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.TimeZone;

import au.id.wh.photos.PhotosV1.Album;
import au.id.wh.photos.PhotosV1.Picture;
import au.id.wh.photos.PhotosV1.Privacy;

import com.google.protobuf.ByteString;

/**
 * Demonstration of how to use Google Protocol Buffers.
 * 
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date   2013-04-06
 */
public class Photos {
    
    private static int IMAGE_EXCERPT_SIZE = 20; // Just to keep the output nice and brief
    
    public static void main(String[] args) {
        save();
        load();
    }
    
    /**
     * Create a photo album and save it to disc.
     */
    private static void save() {
        try {
            Picture.Builder picture = Picture.newBuilder();
            picture.setDescription("War Duck statue in Modena");
            picture.setLatitude(44.6500f);
            picture.setLongitude(10.9167f);
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            picture.setTimestamp(String.format("%1$tF %1$tT", now));
            
            FileChannel image = FileChannel.open(Paths.get("images/statue.jpg"), StandardOpenOption.READ);
            ByteBuffer imageData = ByteBuffer.allocate(IMAGE_EXCERPT_SIZE);
            image.read(imageData);
            imageData.flip();
            picture.setImage(ByteString.copyFrom(imageData));
            picture.setImageMimeType("image/jpg");
            
            Album.Builder album = Album.newBuilder();
            album.setName("Warwick in Modena");
            album.addPicture(picture);
            album.setPrivacy(Privacy.PUBLIC);
            
            FileOutputStream out = new FileOutputStream("album.dat");
            album.build().writeTo(out);
            out.close();
            
            long protobufSize = new File("album.proto").length();
            System.out.printf("protobuf size=%d, image size=%d, the rest=%d%n", 
                              protobufSize, IMAGE_EXCERPT_SIZE, 
                              protobufSize - IMAGE_EXCERPT_SIZE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the photo album and print the contents
     */
    private static void load() {
        try {
            FileInputStream in = new FileInputStream("album.dat");
            Album album = Album.parseFrom(in);
            in.close();
            System.out.printf("album {%n%s}%n", album);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

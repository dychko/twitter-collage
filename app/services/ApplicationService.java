package services;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ApplicationService {

    private static final String COLLAGE_FILE = "collage.png";

    public static void combineImages(List<String> avatarUrls, int avaWidth, int avaHeight, int numHorizontal) {
        try {
            int numAvatars = avatarUrls.size();
            int collageWidth = avaWidth * numHorizontal;
            int collageHeight = avaHeight * (int) Math.ceil((double) numAvatars / numHorizontal);
            BufferedImage collageImage0 = new BufferedImage(collageWidth, collageHeight, BufferedImage.TYPE_INT_ARGB);
            ImageIO.write(collageImage0, "png", new File("public/images", COLLAGE_FILE));
            for (int i = 0; i < numAvatars; i++) {
                int x = (i % numHorizontal) * avaWidth;
                int y = (i / numHorizontal) * avaHeight;
                BufferedImage collageImage = ImageIO.read(new File("public/images", COLLAGE_FILE));
                URL avatarUrl = new URL(avatarUrls.get(i));
                BufferedImage avatarImage = ImageIO.read(avatarUrl);
                Graphics2D g2 = collageImage.createGraphics();
                Color color = g2.getColor();
                g2.setPaint(Color.WHITE);
                g2.fillRect(x, y, x + avaWidth, y + avaHeight);
                g2.setColor(color);
                g2.drawImage(avatarImage, null, x, y);
                g2.dispose();
                ImageIO.write(collageImage, "png", new File("public/images", COLLAGE_FILE));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

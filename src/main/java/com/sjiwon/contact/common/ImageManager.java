package com.sjiwon.contact.common;

import javax.swing.*;
import java.awt.*;

public class ImageManager {
    private static final String STATIC_PATH = "src/main/resources/images/static/";

    public static ImageIcon getStaticImage(final String name, final int width, final int height) {
        final Image instance = new ImageIcon(STATIC_PATH + name)
                .getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(instance);
    }
}

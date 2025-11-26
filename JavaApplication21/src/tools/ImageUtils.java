/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

/**
 *
 * @author Admin
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageUtils {

    private static final Map<String, ImageIcon> cache = new HashMap<>();

    public static ImageIcon loadPngIcon(String path, int width, int height) {
        String cacheKey = path + "_" + width + "_" + height;

        // Kiểm tra cache
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        try {
            InputStream stream = ImageUtils.class.getResourceAsStream(path);
            if (stream == null) {
                System.err.println("Image not found: " + path);
                return null;
            }

            BufferedImage originalImage = ImageIO.read(stream);
            stream.close();

            // Resize với chất lượng cao
            BufferedImage resizedImage = resizeImage(originalImage, width, height);
            ImageIcon icon = new ImageIcon(resizedImage);

            // Lưu vào cache
            cache.put(cacheKey, icon);

            return icon;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        // 1. Đảm bảo kiểu ảnh phù hợp nhất cho PNG có alpha
        BufferedImage temp = new BufferedImage(
                original.getWidth(), original.getHeight(),
                BufferedImage.TYPE_INT_ARGB_PRE);  // quan trọng: ARGB_PRE
        Graphics2D g2 = temp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.drawImage(original, 0, 0, null);
        g2.dispose();

        // 2. Scale dần theo bước (rất quan trọng để giữ nét khi giảm kích thước lớn)
        int w = temp.getWidth();
        int h = temp.getHeight();
        BufferedImage result = temp;

        while (w > targetWidth * 2 || h > targetHeight * 2) {
            w = Math.max(targetWidth, w / 2);
            h = Math.max(targetHeight, h / 2);

            BufferedImage intermediate = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D g = intermediate.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(result, 0, 0, w, h, null);
            g.dispose();

            result = intermediate;
        }

        // Bước cuối cùng
        BufferedImage finalImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = finalImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.drawImage(result, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        return finalImage;
    }

    // Clear cache nếu cần
    public static void clearCache() {
        cache.clear();
    }
}

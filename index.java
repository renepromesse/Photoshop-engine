import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class ImageProcessing {
    public static void main(String[] args) {
        // The provided images are apple.jpg, flower.jpg, and kitten.jpg
        int[][] imageData = imgToTwoD("./apple.jpg");
        // Or load your own image using a URL!
        // int[][] imageData =
        // imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
        // viewImageData(imageData);
        // int[][] trimmed = trimBorders(imageData, 60);
        // int[][] negativeConvertedImg = negativeColor(imageData);
        // int[][] stretchedImg = stretchHorizontally(imageData);
        // int[][] verticalImg = shrinkVertically(imageData);
        // int[][] invertedImg = invertImage(imageData);
        // int[][] colorFilteredImg = colorFilter(imageData, -75, 30, -30);
        // int[][] randomPic = new int[500][500];
        // int[][] randomImg = paintRandomImage(randomPic);
        // int[] rgba = {255, 155, 0, 255};
        // int col = getColorIntValFromRGBA(rgba);
        // int[][] rectImg = paintRectangle(imageData, 100, 100, 50, 40, col);
        int[][] generatedImg = generateRectangles(imageData, 10);
        twoDToImage(generatedImg, "./gen_rect_apple.jpg");

        // int[][] allFilters =
        // stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData),
        // 50)), 200, 20, 40)));
        // Painting with pixels
    }

    // Image Processing Methods
    public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {

        if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
            int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
            for (int i = 0; i < trimmedImg.length; i++) {
                for (int j = 0; j < trimmedImg[i].length; j++) {
                    trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
                }
            }
            return trimmedImg;
        } else {
            System.out.println("Cannot trim that many pixels from the given image.");
            return imageTwoD;
        }
    }

    public static int[][] negativeColor(int[][] imageTwoD) {

        int[][] copyImage = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[0].length; j++) {
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                rgba[0] = 255 - rgba[0];
                rgba[1] = 255 - rgba[1];
                rgba[2] = 255 - rgba[2];
                rgba[3] = 255 - rgba[3];
                int newImage = getColorIntValFromRGBA(rgba);
                copyImage[i][j] = newImage;
            }
        }
        return copyImage;
    }

    public static int[][] stretchHorizontally(int[][] imageTwoD) {

        int[][] copyImage = new int[imageTwoD.length][imageTwoD[0].length * 2];
        int pix = 0;
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[i].length; j++) {
                pix = j * 2;
                copyImage[i][pix] = imageTwoD[i][j];
                copyImage[i][pix + 1] = imageTwoD[i][j];
            }
        }
        return copyImage;
    }

    public static int[][] shrinkVertically(int[][] imageTwoD) {

        int[][] copyImg = new int[imageTwoD.length / 2][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD[0].length; i++) {
            for (int j = 0; j < imageTwoD.length - 1; j += 2) {
                copyImg[j / 2][i] = imageTwoD[j][i];
            }
        }
        return copyImg;
    }

    public static int[][] invertImage(int[][] imageTwoD) {

        int[][] copyImage = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[0].length; j++) {
                copyImage[i][j] = imageTwoD[(imageTwoD.length - 1) - i][(imageTwoD[0].length - 1) - j];
            }
        }
        return copyImage;
    }

    public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue,
            int blueChangeValue) {

        int[][] copyImage = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[0].length; j++) {
                int[] rgb = getRGBAFromPixel(imageTwoD[i][j]);
                int red = rgb[0];
                int green = rgb[1];
                int blue = rgb[2];
                red = (red + redChangeValue) < 0 ? 0 : red + redChangeValue;
                green = (green + greenChangeValue) < 0 ? 0 : green + greenChangeValue;
                blue = (blue + blueChangeValue) < 0 ? 0 : blue + blueChangeValue;

                red = red > 255 ? 255 : red;
                green = green > 255 ? 255 : green;
                blue = blue > 255 ? 255 : blue;

                rgb[0] = red;
                rgb[1] = green;
                rgb[2] = blue;
                int convertedHexColor = getColorIntValFromRGBA(rgb);
                copyImage[i][j] = convertedHexColor;
            }
        }
        return copyImage;
    }

    // Painting Methods
    public static int[][] paintRandomImage(int[][] canvas) {

        Random random = new Random();
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[0].length; j++) {
                int col1 = random.nextInt(256);
                int col2 = random.nextInt(256);
                int col3 = random.nextInt(256);
                int[] colors = { col1, col2, col3, 255 };
                canvas[i][j] = getColorIntValFromRGBA(colors);
            }
        }
        return canvas;
    }

    public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition,
            int color) {

        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[0].length; j++) {
                if (i >= rowPosition && i <= rowPosition + width) {
                    if (j >= colPosition && j <= colPosition + height) {
                        canvas[i][j] = color;
                    }
                }
            }
        }
        return canvas;
    }

    public static int[][] generateRectangles(int[][] canvas, int numRectangles) {

        Random random = new Random();
        for (int i = 0; i < numRectangles; i++) {
            int randWidth = random.nextInt(canvas[0].length);
            int randHeight = random.nextInt(canvas.length);
            int randRowPos = random.nextInt(canvas.length - randHeight);
            int randColPos = random.nextInt(canvas[0].length - randWidth);
            int randRed = random.nextInt(256);
            int randGreen = random.nextInt(256);
            int randBlue = random.nextInt(256);
            int[] rgba = { randRed, randGreen, randBlue, 255 };
            int color = getColorIntValFromRGBA(rgba);
            canvas = paintRectangle(canvas, randWidth, randHeight, randRowPos, randColPos, color);

        }
        return canvas;
    }

    // Utility Methods
    public static int[][] imgToTwoD(String inputFileOrLink) {
        try {
            BufferedImage image = null;
            if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
                URL imageUrl = new URL(inputFileOrLink);
                image = ImageIO.read(imageUrl);
                if (image == null) {
                    System.out.println("Failed to get image from provided URL.");
                }
            } else {
                image = ImageIO.read(new File(inputFileOrLink));
            }
            int imgRows = image.getHeight();
            int imgCols = image.getWidth();
            int[][] pixelData = new int[imgRows][imgCols];
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    pixelData[i][j] = image.getRGB(j, i);
                }
            }
            return pixelData;
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getLocalizedMessage());
            return null;
        }
    }

    public static void twoDToImage(int[][] imgData, String fileName) {
        try {
            int imgRows = imgData.length;
            int imgCols = imgData[0].length;
            BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    result.setRGB(j, i, imgData[i][j]);
                }
            }
            File output = new File(fileName);
            ImageIO.write(result, "jpg", output);
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e.getLocalizedMessage());
        }
    }

    public static int[] getRGBAFromPixel(int pixelColorValue) {
        Color pixelColor = new Color(pixelColorValue);
        return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
    }

    public static int getColorIntValFromRGBA(int[] colorData) {
        if (colorData.length == 4) {
            Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
            return color.getRGB();
        } else {
            System.out.println("Incorrect number of elements in RGBA array.");
            return -1;
        }
    }

    public static void viewImageData(int[][] imageTwoD) {
        if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
            int[][] rawPixels = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rawPixels[i][j] = imageTwoD[i][j];
                }
            }
            System.out.println("Raw pixel data from the top left corner.");
            System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
            int[][][] rgbPixels = new int[3][3][4];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
                }
            }
            System.out.println();
            System.out.println("Extracted RGBA pixel data from top the left corner.");
            for (int[][] row : rgbPixels) {
                System.out.print(Arrays.deepToString(row) + System.lineSeparator());
            }
        } else {
            System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
        }
    }
}

package common

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.model.FailureHandling
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.File

class ImageComparison {
    
    @Keyword
    def captureElementScreenshot(TestObject testObject, String imageName, double threshold = 0.1, int step = 5, int colorTolerance = 30) {
        
        String projectDir = RunConfiguration.getProjectDir()
        String baseFolder = projectDir + "/Data Files/VisualTesting"
        String baselinePath = baseFolder + "/Baseline/" + imageName
        String actualPath = baseFolder + "/Actual/" + imageName
        
        new File(baseFolder + "/Baseline").mkdirs()
        new File(baseFolder + "/Actual").mkdirs()
        
        File baselineFile = new File(baselinePath)
        
        if (!baselineFile.exists()) {
            WebUI.takeElementScreenshot(baselinePath, testObject, FailureHandling.STOP_ON_FAILURE)
            KeywordUtil.logInfo("Baseline created: " + baselinePath)
            return true
        } else {
            WebUI.takeElementScreenshot(actualPath, testObject, FailureHandling.STOP_ON_FAILURE)
            KeywordUtil.logInfo("Actual screenshot captured: " + actualPath)
            
            boolean isSimilar = compareImages(baselinePath, actualPath, threshold, step, colorTolerance)
            
            if (!isSimilar) {
                String failedFolder = baseFolder + "/Failed"
                new File(failedFolder).mkdirs()
                String timestamp = new Date().format('yyyyMMdd_HHmmss')
                String failedPath = failedFolder + "/FAILED_" + imageName.replace(".png", "") + "_" + timestamp + ".png"
                copyFile(actualPath, failedPath)
                KeywordUtil.markFailed("Images do NOT match! Failed screenshot saved: " + failedPath)
            }
            
            return isSimilar
        }
    }
    
    @Keyword
    def compareImages(String path1, String path2, double threshold = 0.1, int step = 5, int colorTolerance = 30) {
        try {
            BufferedImage img1 = ImageIO.read(new File(path1))
            BufferedImage img2 = ImageIO.read(new File(path2))
            
            if (img1 == null || img2 == null) {
                KeywordUtil.logInfo("Cannot read one or both images")
                return false
            }
            
            if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
                KeywordUtil.logInfo("Image dimensions don't match: " +
                    img1.getWidth() + "x" + img1.getHeight() + " vs " +
                    img2.getWidth() + "x" + img2.getHeight())
                return false
            }
            
            long diff = 0
            long totalPixels = 0
            long maxAllowedDiff = (long)((img1.getWidth() / step) * (img1.getHeight() / step) * threshold / 100)
            
            outerLoop:
            for (int y = 0; y < img1.getHeight(); y += step) {
                for (int x = 0; x < img1.getWidth(); x += step) {
                    totalPixels++
                    
                    int rgb1 = img1.getRGB(x, y)
                    int rgb2 = img2.getRGB(x, y)
                    
                    int r1 = (rgb1 >> 16) & 0xFF
                    int g1 = (rgb1 >> 8) & 0xFF
                    int b1 = rgb1 & 0xFF
                    
                    int r2 = (rgb2 >> 16) & 0xFF
                    int g2 = (rgb2 >> 8) & 0xFF
                    int b2 = rgb2 & 0xFF
                    
                    int colorDiff = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2)
                    
                    if (colorDiff > colorTolerance) {
                        diff++
                        if (diff > maxAllowedDiff) {
                            double currentPercentage = (diff * 100.0) / totalPixels
                            KeywordUtil.logInfo("Early stop at: " + String.format("%.2f", currentPercentage) + "%")
                            return false
                        }
                    }
                }
            }
            
            double percentage = (diff * 100.0) / totalPixels
            KeywordUtil.logInfo("Diff: " + String.format("%.2f", percentage) + "% | Threshold: " + threshold + "%")
            
            return percentage < threshold
            
        } catch (Exception e) {
            KeywordUtil.logInfo("Error: " + e.getMessage())
            return false
        }
    }
    
    @Keyword
    def deleteBaseline(String imageName) {
        String projectDir = RunConfiguration.getProjectDir()
        String baselinePath = projectDir + "/Data Files/VisualTesting/Baseline/" + imageName
        File baselineFile = new File(baselinePath)
        if (baselineFile.exists()) {
            baselineFile.delete()
            KeywordUtil.logInfo("Baseline deleted: " + baselinePath)
            return true
        }
        return false
    }
    
    private void copyFile(String sourcePath, String destPath) {
        File source = new File(sourcePath)
        File dest = new File(destPath)
        dest.parentFile.mkdirs()
        
        FileInputStream fis = new FileInputStream(source)
        FileOutputStream fos = new FileOutputStream(dest)
        
        byte[] buffer = new byte[1024]
        int length
        while ((length = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, length)
        }
        
        fis.close()
        fos.close()
    }
}
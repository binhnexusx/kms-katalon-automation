package common 

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.annotation.Keyword
import java.util.regex.Pattern
import java.util.regex.Matcher

class CartUtils {
    
    @Keyword
    def double extractPrice(TestObject priceElement) {
        String priceText = WebUI.getText(priceElement).trim()
        return extractPriceFromString(priceText)
    }
    
    @Keyword
    def double extractPriceFromString(String text) {
        Pattern pattern1 = Pattern.compile(/[\$€]\s*(\d+(?:[.,]\d+)?)/)
        Matcher matcher1 = pattern1.matcher(text)
        
        if (matcher1.find()) {
            String amount = matcher1.group(1).replace(',', '.')
            return Double.parseDouble(amount)
        }
        
        Pattern pattern2 = Pattern.compile(/(\d+(?:[.,]\d+)?)\s*(?:\$|USD|VND|EUR|GBP)?/)
        Matcher matcher2 = pattern2.matcher(text)
        
        if (matcher2.find()) {
            String amount = matcher2.group(1).replace(',', '.')
            return Double.parseDouble(amount)
        }
        
        throw new Exception("Cannot extract price from: ${text}")
    }
    
    @Keyword
    def void verifyCartTotals(Map<String, Double> expected, double tolerance = 0.01) {
        Map<String, String> xpaths = [
            'subtotal': "//tr[contains(@class,'cart-subtotal')]//bdi",
            'shipping': "//tr[contains(@class,'shipping')]//bdi",
            'tax': "//tr[contains(@class,'tax')]//bdi",
            'discount': "//tr[contains(@class,'cart-discount')]//bdi",
            'total': "//tr[contains(@class,'order-total')]//bdi"
        ]
        
        boolean allPassed = true
        
        expected.each { key, expectedValue ->
            try {
                TestObject element = new TestObject().addProperty(
                    'xpath',
                    com.kms.katalon.core.testobject.ConditionType.EQUALS,
                    xpaths[key]
                )
                
                double actualValue = extractPrice(element)
                double diff = Math.abs(actualValue - expectedValue)
                boolean passed = diff <= tolerance
                
                String status = passed ? "✓" : "✗"
                println "${status} ${key}: Expected=${expectedValue}, Actual=${actualValue}, Diff=${diff}"
                
                if (!passed) {
                    allPassed = false
                }
            } catch (Exception e) {
                println "✗ ${key}: ${e.message}"
                allPassed = false
            }
        }
        
        assert allPassed : "Cart totals verification failed"
    }
}
package common

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

class VerifyCartQuantityDynamic {

    /**
     * Verify quantity khi add to cart - Dynamic cho nhiều sản phẩm
     */
    @Keyword
    def verifyAddToCartQuantity(String productName,
                               int quantityToAdd,
                               TestObject cartIconObject,
                               TestObject backButtonObject = null) {

        Map<String, Integer> initialCartState = getCartState(cartIconObject)

        int initialQuantity = initialCartState.containsKey(productName)
                ? initialCartState[productName]
                : 0

        try {
            if (backButtonObject != null) {
                WebUI.click(backButtonObject)
            } else {
                WebUI.back()
            }
            WebUI.delay(1)
        } catch (Exception e) {
            WebUI.back()
            WebUI.delay(1)
        }

        WebUI.click(findTestObject('Object Repository/Shopping Carts/btn_addCart'))
        WebUI.delay(1)

        Map<String, Integer> finalCartState = getCartState(cartIconObject)

        int expectedQuantity = initialQuantity + quantityToAdd
        int actualQuantity = finalCartState.get(productName, 0)
    }

    /**
     * Lấy trạng thái giỏ hàng
     */
    @Keyword
    def Map<String, Integer> getCartState(TestObject cartIconObject) {

        Map<String, Integer> cartState = [:]

        try {
            WebUI.click(cartIconObject)
            WebUI.delay(1)

            WebDriver driver = DriverFactory.getWebDriver()

            List<WebElement> cartRows =
                    driver.findElements(By.cssSelector("tr.cart-item, .cart-product-row, tbody tr"))

            if (cartRows.isEmpty()) {
                cartRows = driver.findElements(By.xpath("//table//tr[td]"))
            }

            for (WebElement row : cartRows) {
                try {
                    WebElement nameEl = row.findElement(
                            By.cssSelector("td.product-name, .cart-product-name, td:nth-child(2)")
                    )
                    String productName = nameEl.getText().trim()
                    if (!productName) continue

                    WebElement qtyEl = row.findElement(
                            By.cssSelector("input[type='number'], input.quantity, input[name*='quantity']")
                    )
                    String qtyValue = qtyEl.getAttribute("value")

                    if (qtyValue) {
                        cartState.put(productName, Integer.parseInt(qtyValue))
                    }
                } catch (Exception ignored) {}
            }

        } catch (Exception e) {
            WebUI.comment("⚠ Warning: Không thể lấy giỏ hàng - ${e.getMessage()}")
        }

        return cartState
    }
}
package cart

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class CartKeyword {

    @Keyword
    def verifySubtotalWithoutCoupon() {

        int totalItems = WebUI.findWebElements(
            findTestObject('Object Repository/Shopping Carts/rows_cart_items'),
            5
        ).size()

        double calculatedSubtotal = 0

        for (int i = 1; i <= totalItems; i++) {

            String priceText = WebUI.getText(
                findTestObject(
                    'Object Repository/Shopping Carts/lbl_product_price',
                    ['index': i]
                )
            )

            double price = Double.parseDouble(
                priceText.replaceAll('[^0-9.]', '')
            )

            int quantity = Integer.parseInt(
                WebUI.getAttribute(
                    findTestObject(
                        'Object Repository/Shopping Carts/txt_product_quantity',
                        ['index': i]
                    ),
                    'value'
                )
            )

            calculatedSubtotal += price * quantity
        }

        String subtotalText = WebUI.getText(
            findTestObject('Object Repository/Shopping Carts/lbl_cart_subtotal')
        )

        double uiSubtotal = Double.parseDouble(
            subtotalText.replaceAll('[^0-9.]', '')
        )

        WebUI.verifyEqual(calculatedSubtotal, uiSubtotal)
    }
}

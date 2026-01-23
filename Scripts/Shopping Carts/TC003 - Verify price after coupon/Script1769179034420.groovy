import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import cart.CartKeyword 

WebUI.setText(
    findTestObject('Object Repository/Shopping Carts/txt_cuponcode'),
    GlobalVariable.coupon
)

WebUI.click(
    findTestObject('Object Repository/Shopping Carts/btn_submit cuponcode')
)

boolean isCouponError = WebUI.verifyElementPresent(
    findTestObject('Object Repository/Shopping Carts/lbl_coupon_error'),
    3,
    FailureHandling.OPTIONAL
)

if (isCouponError) {

    WebUI.verifyMatch(
        WebUI.getText(
            findTestObject('Object Repository/Shopping Carts/lbl_coupon_error')
        ).trim(),
        'This coupon has expired.',
        false
    )

    CustomKeywords.'cart.CartKeyword.verifySubtotalWithoutCoupon'()
}

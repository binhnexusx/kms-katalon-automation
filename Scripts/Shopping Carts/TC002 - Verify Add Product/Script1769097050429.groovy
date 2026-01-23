import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.Random

WebUI.click(findTestObject('Object Repository/Shopping Carts/Login/href_Shop'))

Random rand = new Random()
int randomIndex = rand.nextInt(5) + 1

TestObject randomProduct = new TestObject()
randomProduct.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "(//ul[contains(@class,'products')]//a[contains(@class,'woocommerce-LoopProduct-link')])[${randomIndex}]"
)

WebUI.click(randomProduct)

String productName = WebUI.getText(
    findTestObject('Object Repository/Shopping Carts/h1_title product name')
).trim()

int randomQuantity = rand.nextInt(5) + 1

WebUI.clearText(findTestObject('Object Repository/Shopping Carts/input_quantity'))
WebUI.setText(
    findTestObject('Object Repository/Shopping Carts/input_quantity'),
    randomQuantity.toString()
)

WebUI.click(findTestObject('Object Repository/Shopping Carts/btn_addCart'))
WebUI.click(findTestObject('Object Repository/Shopping Carts/btn_viewCart'))

TestObject cartQtyObj = findTestObject(
    'Object Repository/Shopping Carts/txt_cart_quantity_by_name',
    ['productName': productName]
)

int actualQty = Integer.parseInt(
    WebUI.getAttribute(cartQtyObj, 'value')
)

assert actualQty >= randomQuantity

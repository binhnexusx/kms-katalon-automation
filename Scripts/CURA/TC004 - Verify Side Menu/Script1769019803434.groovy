import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.click(findTestObject('Object Repository/CURA/btn_Icon_Menu'))
WebUI.click(findTestObject('Object Repository/CURA//lnk_Home'))
WebUI.verifyElementPresent(findTestObject('Object Repository/CURA/h1_Header'), 3)

WebUI.click(findTestObject('Object Repository/CURA/btn_Icon_Menu'))
WebUI.click(findTestObject('Object Repository/CURA/lnk_History'))
WebUI.verifyElementText(findTestObject('Object Repository/CURA/h2_History_Header'), 'History')

WebUI.click(findTestObject('Object Repository/CURA/btn_Icon_Menu'))
WebUI.click(findTestObject('Object Repository/CURA/lnk_Profile'))
WebUI.verifyElementText(findTestObject('Object Repository/CURA/h2_Profile_Header'), 'Profile')

WebUI.click(findTestObject('Object Repository/CURA/btn_Icon_Menu'))
WebUI.click(findTestObject('Object Repository/CURA/lnk_Logout'))

WebUI.verifyElementPresent(findTestObject('Object Repository/CURA/h1_Header'), 3)
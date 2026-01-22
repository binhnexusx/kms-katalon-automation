package common

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.annotation.Keyword


public class LoginKeyword {
	
	@Keyword
	def login() {
		WebUI.navigateToUrl(GlobalVariable.baseURL)
		WebUI.click(findTestObject('Object Repository/CURA/btn_MakeAppointment'))
		WebUI.verifyElementPresent(findTestObject('Object Repository/CURA/form_LoginForm'), 10)
		
		String username = WebUI.getAttribute(findTestObject('Object Repository/CURA/lbl_Username'), 'value')
		WebUI.setText(findTestObject('Object Repository/CURA/txt_Username'), username)
		
		String password = WebUI.getAttribute(findTestObject('Object Repository/CURA/lbl_Password'), 'value')
		WebUI.setText(findTestObject('Object Repository/CURA/txt_Password'), password)
		
		WebUI.click(findTestObject('Object Repository/CURA/btn_Login'))
		WebUI.verifyElementPresent(findTestObject('Object Repository/CURA/h2_MakeAppointment'), 10)
	}
	
	@Keyword
	def loginWithCredentials(String username, String password) {
		WebUI.navigateToUrl(GlobalVariable.baseURL)
		WebUI.click(findTestObject('Object Repository/CURA/btn_MakeAppointment'))
		WebUI.verifyElementPresent(findTestObject('Object Repository/CURA/form_LoginForm'), 10)
		
		WebUI.setText(findTestObject('Object Repository/CURA/txt_Username'), username)
		WebUI.setText(findTestObject('Object Repository/CURA/txt_Password'), password)
		
		WebUI.click(findTestObject('Object Repository/CURA/btn_Login'))
		WebUI.verifyElementPresent(findTestObject('Object Repository/CURA/h2_MakeAppointment'), 10)
	}
}
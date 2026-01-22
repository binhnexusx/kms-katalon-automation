import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import common.DateHelper

String facility = 'Hongkong CURA Healthcare Center'
String program = 'Medicare'
String comment = 'Booking test by binhhoangdev - IT Student' 

WebUI.selectOptionByValue(findTestObject('Object Repository/CURA/ddl_Facility'), facility, false)

WebUI.check(findTestObject('Object Repository/CURA/chk_Hospital_Readmission'))
WebUI.check(findTestObject('Object Repository/CURA/chk_Medicare'))

DateHelper dateHelper = new DateHelper()
String expectedDate = dateHelper.setVisitDatePlus30()

WebUI.setText(findTestObject('Object Repository/CURA/txa_Comment'), comment)
WebUI.click(findTestObject('Object Repository/CURA/btn_BookAppointment'))
WebUI.verifyElementPresent(findTestObject('Object Repository/CURA/h2_Appointment_Confirmation'), 5)
WebUI.verifyElementText(findTestObject('Object Repository/CURA/h2_Appointment_Confirmation'), 'Appointment Confirmation')

WebUI.verifyElementText(findTestObject('Object Repository/CURA/lbl_Facility'), facility)

WebUI.verifyElementText(findTestObject('Object Repository/CURA/lbl_HospitalReadmission'), 'Yes')
WebUI.verifyElementText(findTestObject('Object Repository/CURA/lbl_Program'), program)
WebUI.verifyElementText(findTestObject('Object Repository/CURA/lbl_VisitDate'), expectedDate)
WebUI.verifyElementText(findTestObject('Object Repository/CURA/lbl_Comment'), comment)

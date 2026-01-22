package common

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.LocalDate
import java.time.format.DateTimeFormatter

public class DateHelper {

    @Keyword
    def String setVisitDatePlus30() {

        LocalDate futureDate = LocalDate.now().plusDays(30)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        String formattedDate = futureDate.format(formatter)

        WebUI.setText(
            findTestObject('Object Repository/CURA/txt_Visit_Date'),
            formattedDate
        )
        return formattedDate
    }
}
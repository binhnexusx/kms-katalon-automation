import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class TestSuiteListener {

    @BeforeTestCase
    def beforeTestCase(TestCaseContext testCaseContext) {
        try {
            DriverFactory.getWebDriver()
        } catch (Exception e) {
            WebUI.openBrowser('')
            WebUI.maximizeWindow()
        }
    }

    @AfterTestCase
    def afterTestCase(TestCaseContext testCaseContext) {
    }

    @AfterTestSuite
    def afterTestSuite(TestSuiteContext testSuiteContext) {
        WebUI.closeBrowser()
    }
}
package api

import org.apache.commons.io.IOUtils
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Configuration
import play.api.http.Status.OK
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.ExecutionContextExecutor

class InformationControllerTest extends PlaySpec with GuiceOneAppPerSuite {
  implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.global

  private val informationController = app.injector.instanceOf[InformationController]
  private val config = app.injector.instanceOf[Configuration]

  "InformationController" should {

    "return application information" in {
      // Given
      val request = FakeRequest(GET, "/api/information")
      val expectedContent = IOUtils.toString(getClass.getResourceAsStream("/info.json.json"), "UTF-8")

      // When
      val result = informationController.info apply request

      // Then
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe expectedContent
    }
  }
}

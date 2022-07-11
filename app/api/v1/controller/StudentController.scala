package api.v1.controller

import api.v1.model.Student
import api.v1.service.StudentService
import play.api.mvc._
import play.api.routing.SimpleRouter

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class StudentController @Inject() (
    val cc: ControllerComponents,
    studentService: StudentService
)(implicit ec: ExecutionContext)
    extends ApiController[Student](cc, studentService)
    with SimpleRouter {}

package api.v1.controller

import api.v1.model.Student
import api.v1.service.StudentService
import api.v1.validation.StudentValidation
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class StudentController @Inject() (
    cc: ControllerComponents,
    validation: StudentValidation,
    service: StudentService
)(implicit ec: ExecutionContext)
    extends ApiController[Student](cc, validation, service) {}
object StudentController
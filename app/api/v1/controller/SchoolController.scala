package api.v1.controller

import api.v1.model.School
import api.v1.service.SchoolService
import api.v1.validation.SchoolValidation
import play.api.mvc._
import play.api.routing.SimpleRouter

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class SchoolController @Inject() (
    cc: ControllerComponents,
    validation: SchoolValidation,
    service: SchoolService
)(implicit ec: ExecutionContext)
    extends ApiController[School](cc, validation, service)
    with SimpleRouter {}

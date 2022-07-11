package api.v1.controller

import api.v1.model.School
import api.v1.service.SchoolService
import play.api.mvc._
import play.api.routing.SimpleRouter

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class SchoolController @Inject() (
    val cc: ControllerComponents,
    schoolService: SchoolService
)(implicit ec: ExecutionContext)
    extends ApiController[School](cc, schoolService)
    with SimpleRouter {}

package api.v1.validation

import api.v1.model.Student
import play.api.data.Forms._
import play.api.data._

import javax.inject.Inject

class StudentValidation @Inject() extends ModelValidation[Student] {
  override val createForm: Form[Student] = Form(
    mapping(
      "id" -> optional(number),
      "email" -> email,
      "schoolId" -> optional(number)
    )(Student.apply)(Student.unapply)
  )

  override val updateForm: Form[Student] = Form(
    mapping(
      "id" -> optional(number),
      "email" -> email,
      "schoolId" -> optional(number).verifying("error.required", _.isDefined)
    )(Student.apply)(Student.unapply)
  )
}

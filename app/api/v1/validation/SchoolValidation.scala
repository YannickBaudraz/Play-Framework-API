package api.v1.validation

import api.v1.model.School
import play.api.data.Forms._
import play.api.data._
import play.api.data.validation.Constraints.pattern

class SchoolValidation extends ModelValidation[School] {
  private val urlRegex = Seq(
    """^[-a-zA-Z\d@:%._+~#=]{1,256}\.[a-zA-Z\d()]{1,6}\b[-a-zA-Z\d()@:%_+.~#?&/=]*$""".r,
    """^https?://(?:www\.)?[-a-zA-Z\d@:%._+~#=]{1,256}\.[a-zA-Z\d()]{1,6}\b[-a-zA-Z\d()@:%_+.~#?&/=]*$""".r
  ).mkString("|").r

  override val createForm: Form[School] = Form(
    mapping(
      "id" -> optional(number),
      "name" -> nonEmptyText,
      "phone" -> optional(text),
      "email" -> optional(email),
      "website" -> optional(text.verifying(pattern(urlRegex, error = "Invalid URL")))
    )(School.apply)(School.unapply)
  )
  override val updateForm: Form[School] = createForm
}

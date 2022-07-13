package api.v1.validation

import api.v1.model.BaseModel
import play.api.data.Form

trait ModelValidation[Model <: BaseModel] {
  val createForm: Form[Model]
  val updateForm: Form[Model]
}

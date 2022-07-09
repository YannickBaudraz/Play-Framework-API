package api.v1.model

case class School(
    id: Option[Int] = None,
    name: String,
    phone: Option[String],
    email: Option[String],
    website: Option[String],
)

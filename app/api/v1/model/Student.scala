package api.v1.model

case class Student(
    id: Option[Int] = None,
    email: String,
    schoolId: Option[Int] = None
)

case class StudentWithSchool(
    id: Option[Int] = None,
    email: String,
    school: Option[School] = None
)

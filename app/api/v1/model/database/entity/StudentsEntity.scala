package api.v1.model.database.entity

import api.v1.model.Student
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

class StudentsEntity(tag: Tag) extends Table[Student](tag, "students") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def email: Rep[String] = column[String]("email")

  def * : ProvenShape[Student] = (id.?, email) <> (Student.tupled, Student.unapply)
}

package api.v1.model.database.entity

import api.v1.model.School
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

class SchoolEntity(tag: Tag) extends Table[School](tag, "schools") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name: Rep[String] = column[String]("name", O.Unique)
  def phone: Rep[Option[String]] = column[Option[String]]("phone", O.Unique)
  def email: Rep[Option[String]] = column[Option[String]]("email", O.Unique)
  def website: Rep[Option[String]] = column[Option[String]]("website", O.Unique)

  def * : ProvenShape[School] = (id.?, name, phone, email, website) <> (School.tupled, School.unapply)
}

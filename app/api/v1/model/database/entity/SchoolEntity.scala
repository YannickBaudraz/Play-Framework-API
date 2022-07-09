package api.v1.model.database.entity

import api.v1.model.School
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

class SchoolEntity(tag: Tag) extends Table[School](tag, "schools") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name: Rep[String] = column[String]("name")
  def phone: Rep[Option[String]] = column[Option[String]]("phone")
  def email: Rep[Option[String]] = column[Option[String]]("email")
  def website: Rep[Option[String]] = column[Option[String]]("website")

  def * : ProvenShape[School] = (id.?, name, phone, email, website) <> (School.tupled, School.unapply)
}

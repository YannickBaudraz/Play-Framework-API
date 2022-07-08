package api.v1.model.database.dao

import api.v1.model.Student
import api.v1.model.database.entity.StudentsEntity
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StudentDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit val ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  private val Students = TableQuery[StudentsEntity]

  def all(): Future[Seq[Student]] = db.run(Students.sortBy(_.id).result)

  def find(id: Int): Future[Option[Student]] = db.run(Students.filter(_.id === id).result.headOption)

  def create(student: Student): Future[Int] = db.run(Students returning Students.map(_.id) += student)

  def update(student: Student): Future[Student] =
    db.run(Students.filter(_.id === student.id).update(student)).map(_ => student)

  def delete(id: Int): Future[Int] = db.run(Students.filter(_.id === id).delete)
}
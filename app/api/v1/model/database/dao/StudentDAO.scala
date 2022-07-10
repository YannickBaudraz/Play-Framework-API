package api.v1.model.database.dao

import api.v1.model.database.entity.{SchoolEntity, StudentEntity}
import api.v1.model.service.ConverterService
import api.v1.model.{School, Student, StudentWithSchool}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StudentDAO @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider,
    converter: ConverterService
)(implicit val ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  private val Students = TableQuery[StudentEntity]

  def all(): Future[Seq[Student]] =
    db.run(Students.sortBy(_.id).result)

  def find(id: Int): Future[Student] =
    db.run(Students.filter(_.id === id).result.headOption)
      .map {
        case Some(student) => student
        case None => handleNoElement(id)
      }

  def findWithSchool(id: Int): Future[StudentWithSchool] =
    db.run(Students.filter(_.id === id).withSchool.result.headOption)
      .map {
        case Some(tuple2) => converter.toStudentWithSchool(tuple2)
        case None => handleNoElement(id)
      }

  def create(student: Student): Future[Student] =
    db.run(Students returning Students.map(_.id) += student)
      .flatMap(id => find(id))

  def update(student: Student): Future[Student] =
    db.run(Students.filter(_.id === student.id.get).update(student))
      .flatMap(_ => find(student.id.get))

  def delete(id: Int): Future[Nothing] =
    db.run(Students.filter(_.id === id).delete)
      .map { case 0 => handleNoElement(id) }

  private def handleNoElement(id: Int) = {
    throw new NoSuchElementException(s"Student with id $id not found")
  }

  implicit class StudentExtensions[C[_]](q: Query[StudentEntity, Student, C]) {
    type WithSchoolQuery = Query[(StudentEntity, Rep[Option[SchoolEntity]]), (Student, Option[School]), C]
    def withSchool: WithSchoolQuery = q.joinLeft(TableQuery[SchoolEntity]).on(_.schoolId === _.id)
  }
}

package api.v1.database.dao

import api.v1.database.entity.SchoolEntity
import api.v1.model.School
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SchoolDAO @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit val ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  private val Schools = TableQuery[SchoolEntity]

  def all(): Future[Seq[School]] =
    db.run(Schools.sortBy(_.id).result)

  def find(id: Int): Future[School] =
    db.run(Schools.filter(_.id === id).result.headOption)
      .map(withErrorIfNoElement(id))

  def create(school: School): Future[School] =
    db.run(Schools returning Schools.map(_.id) += school)
      .flatMap(find)

  def update(school: School): Future[School] =
    db.run(Schools.filter(_.id === school.id) update school)
      .flatMap(_ => find(school.id.get))

  def delete(id: Int): Future[Unit] =
    db.run(Schools.filter(_.id === id).delete)
      .map(rowsAffected => if (rowsAffected == 0) handleNoElement(id))

  private def withErrorIfNoElement(id: Int): Option[School] => School = {
    case Some(school) => school
    case None         => handleNoElement(id)
  }

  private def handleNoElement(id: Int) = {
    throw new NoSuchElementException(s"School with id $id not found")
  }
}

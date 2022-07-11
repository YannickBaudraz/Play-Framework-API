package api.v1.service

import api.v1.database.dao.SchoolDAO
import api.v1.model.School

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SchoolService @Inject() (dao: SchoolDAO)(implicit ec: ExecutionContext) {

  def list(): Future[Seq[School]] =
    dao.all()

  def get(id: Int): Future[School] =
    dao.find(id)

  def create(school: School): Future[School] =
    dao.create(school)

  def update(school: School): Future[School] =
    dao.update(school)

  def delete(id: Int): Future[Unit] =
    dao.delete(id)
}

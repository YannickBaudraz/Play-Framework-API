package api.v1.service

import api.v1.database.dao.SchoolDAO
import api.v1.model.{BaseModel, School}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SchoolService @Inject() (dao: SchoolDAO)(implicit ec: ExecutionContext) extends ModelService {

  def list(): Future[Seq[School]] =
    dao.all()

  def get(id: Int): Future[School] =
    dao.find(id)

  def create(school: BaseModel): Future[BaseModel] =
    dao.create(school.asInstanceOf[School])

  def update(school: BaseModel): Future[BaseModel] =
    dao.update(school.asInstanceOf[School])

  def delete(id: Int): Future[Unit] =
    dao.delete(id)
}

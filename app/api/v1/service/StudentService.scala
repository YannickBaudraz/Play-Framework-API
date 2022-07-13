package api.v1.service

import api.v1.database.dao.StudentDAO
import api.v1.model.{BaseModel, Student, StudentWithSchool}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StudentService @Inject() (dao: StudentDAO)(implicit ec: ExecutionContext) extends ModelService {

  def list(): Future[Seq[Student]] =
    dao.all()

  def get(id: Int): Future[StudentWithSchool] =
    dao.findWithSchool(id)

  def create(student: BaseModel): Future[Student] =
    dao.create(student.asInstanceOf[Student])

  def update(student: BaseModel): Future[Student] =
    dao.update(student.asInstanceOf[Student])

  def delete(id: Int): Future[Unit] =
    dao.delete(id)
}

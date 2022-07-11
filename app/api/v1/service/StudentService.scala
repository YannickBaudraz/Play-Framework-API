package api.v1.service

import api.v1.database.dao.StudentDAO
import api.v1.model.{Student, StudentWithSchool}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StudentService @Inject() (dao: StudentDAO)(implicit ec: ExecutionContext) {

  def list(): Future[Seq[Student]] =
    dao.all()

  def get(id: Int): Future[StudentWithSchool] =
    dao.findWithSchool(id)

  def create(student: Student): Future[Student] =
    dao.create(student)

  def update(student: Student): Future[Student] =
    dao.update(student)

  def delete(id: Int): Future[Unit] =
    dao.delete(id)
}

package api.v1.model.service

import api.v1.model.Student
import api.v1.model.database.dao.StudentDAO
import api.v1.model.dto.StudentDTO

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StudentService @Inject() (dao: StudentDAO)(implicit ec: ExecutionContext) {

  type FutureIntOrStudent = Either[Future[Int], Future[Student]]

  def list(): Future[Seq[Student]] = dao.all()

  def get(id: Int): Future[Student] = {
    dao.find(id).map {
      case Some(student) => student
      case None          => throw new NoSuchElementException(s"Student with id $id not found")
    }
  }

  def create(dto: StudentDTO): Future[Student] = dao.create(toStudent(dto, None)).map(id => toStudent(dto, Some(id)))

  def update(id: Int, dto: StudentDTO): Future[Student] = get(id).flatMap(_ => dao.update(toStudent(dto, Some(id))))

  def delete(id: Int): Future[Int] = get(id).flatMap(_ => dao.delete(id))

  private def toStudent(dto: StudentDTO, id: Option[Int]) = Student(id, dto.email)
}

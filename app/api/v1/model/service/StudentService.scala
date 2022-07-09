package api.v1.model.service

import api.v1.model.{School, Student, StudentWithSchool}
import api.v1.model.database.dao.StudentDAO
import api.v1.model.dto.StudentReqDTO

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StudentService @Inject() (dao: StudentDAO, converter: ConverterService)(implicit ec: ExecutionContext) {

  def list(): Future[Seq[Student]] = dao.all()

  def get(id: Int): Future[Option[StudentWithSchool]] = dao.findWithSchool(id)

  def create(dto: StudentReqDTO): Future[Student] = dao.create(converter.toStudent(dto, None))

  def update(id: Int, dto: StudentReqDTO): Future[Student] = dao.update(converter.toStudent(dto, Some(id)))

  def delete(id: Int): Future[Int] = dao.delete(id)
}

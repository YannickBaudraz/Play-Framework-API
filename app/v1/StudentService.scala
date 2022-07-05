package v1

import javax.inject.Inject

class StudentService @Inject() (repository: StudentRepository) {

  def getAll: List[Student] = repository.list

  def getById(id: Int): Option[Student] = repository.find(id)

  def create(dto: StudentDTO): Unit = repository.create(toStudent(dto))

  def update(id: Int, dto: StudentDTO): Unit = {
    val student = toStudent(dto).copy(id = id)
    repository.update(student)
  }

  def delete(id: Int): Unit = repository.delete(id)

  private def toStudent(dto: StudentDTO) = Student(email = dto.email)
}

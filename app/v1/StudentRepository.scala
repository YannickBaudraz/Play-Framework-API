package v1

import scala.collection.mutable.ListBuffer

class StudentRepository {
  val studentDao = new ListBuffer[Student]
  studentDao += Student(1, "johndoe@gmail.com")
  studentDao += Student(2, "janedote@gmail.com")

  def list: List[Student] = studentDao.toList

  def find(id: Int): Option[Student] = studentDao.find(_.id == id)

  def create(student: Student): Unit =
    studentDao += student.copy(id = studentDao.map(_.id).max + 1)

  def update(student: Student): Unit = {
    val index = studentDao.indexWhere(_.id == student.id)
    if (index < 0) throw new NoSuchElementException("Student not found")
    studentDao.update(index, student)
  }

  def delete(id: Int): Unit = {
    val index = studentDao.indexWhere(_.id == id)
    if (index < 0) throw new NoSuchElementException("Student not found")
    studentDao.remove(index)
  }
}

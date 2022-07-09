package api.v1.model.service

import api.v1.model.{School, Student, StudentWithSchool}
import api.v1.model.dto.StudentReqDTO

class ConverterService {
  def toStudent(dto: StudentReqDTO, id: Option[Int]): Student = Student(id, dto.email, None)

  def toStudentWithSchool(student: Option[(Student, Option[School])]): Option[StudentWithSchool] =
    student.map(student => StudentWithSchool(student._1.id, student._1.email, student._2))
}

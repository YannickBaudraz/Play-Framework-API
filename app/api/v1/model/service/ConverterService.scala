package api.v1.model.service

import api.v1.model.dto.StudentReqDTO
import api.v1.model.{School, Student, StudentWithSchool}

class ConverterService {

  def toStudent(dto: StudentReqDTO, id: Option[Int]): Student =
    Student(id, dto.email, dto.schoolId)

  def toStudentWithSchool(student: (Student, Option[School])): StudentWithSchool =
    StudentWithSchool(student._1.id, student._1.email, student._2)
}

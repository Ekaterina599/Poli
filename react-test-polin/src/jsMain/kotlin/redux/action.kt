import csstype.Color
import data.*
import redux.RAction

open class CourseAction: RAction
open class StudentAction: RAction
open class ModeAction: RAction
open class ColorAction: RAction

class MarkStudent(
    val courseId: CourseId,
    val studentId: StudentId
) : CourseAction()

class AddStudent(
    val student: Student
): StudentAction()

class AddCourse(
    val course: Course
): CourseAction()

class AddStudentToCourse(
    val courseId: CourseId,
    val studentId: StudentId
): CourseAction()

class AddGrade(
    val courseId: CourseId ,
    val studentId: StudentId,
    val grade: Grade
): CourseAction()

class RemoveStudent(
    val courseId: CourseId,
    val studentId: StudentId
):CourseAction()

class AddColor(
    val index: Int,
    val color: String
): ColorAction()

class ChangeMode() : ModeAction()

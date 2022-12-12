package redux

import csstype.Color
import data.*

typealias CourseState = Array<Course>
typealias StudentState = Array<Student>
typealias ModeState = Mode
typealias GradeState = Array<Grade>
typealias ColorState = Array<String>

class AppState(
    val courses: CourseState = emptyArray(),
    val students: StudentState = emptyArray(),
    val mode: Mode = Mode.Full,
    val grade: GradeState = emptyArray(),
    val color: ColorState = Array(5){"#0000ff"}
) {
    fun getStudentsById(studentIds: Array<StudentId>) =
        studentIds.mapNotNull { studentId ->
            students.firstOrNull {
                it.id == studentId
            }
        }

    fun getCoursesById(courseIds: Array<CourseId>) =
        courseIds.mapNotNull { courseId ->
            courses.firstOrNull {
                it.id == courseId
            }
        }

    fun getCourses(student: Student): List<Pair<Course, Boolean>> =
        courses
            .filter { it.students.contains(student.id) }
            .map {
                it to it.marked[it.students.indexOf(student.id)]
            }

    fun getCoursesGrade(student: Student): List<Pair<Course,Grade>> =
        courses
            .filter { it.students.contains(student.id) }
            .map {
                it to it.grades[it.students.indexOf(student.id)]
            }
}

fun testState() =
    AppState(
        courseList.map { it }.toTypedArray(),
        studentList.map { it }.toTypedArray(),
    )
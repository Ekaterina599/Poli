package component

import AddGrade
import AddStudentToCourse
import MarkStudent
import data.StudentId
import kotlinx.js.Record
import kotlinx.js.get
import org.w3c.dom.HTMLSelectElement
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.details
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.summary
import react.redux.useDispatch
import react.redux.useSelector
import react.router.useParams
import react.useRef
import redux.AppState
import redux.RAction

val CStudent = FC<Props>("Student") {
    val params: Record<String, String> = useParams()
    val studentId = params["studentId"] as StudentId
    val student = useSelector { state: AppState -> state.getStudentsById(arrayOf(studentId)) }.first()
    val courses = useSelector { state: AppState -> state.getCourses(student) }
    val grades =  useSelector { state: AppState -> state.getCoursesGrade(student) }
    val allCourses = useSelector { state: AppState -> state.courses }
    val candidates = allCourses.toList() - courses.mapTo(hashSetOf()){it.first}
    val dispatch = useDispatch<RAction, Unit>()

    val addCourseSelectRef = useRef<HTMLSelectElement>()
    div {
        h3 { +student.id }
        details {
            summary { +"Add Course" }
            select {
                ref = addCourseSelectRef
                candidates.forEach {
                    option { +it.id }
                }
            }
            button {
                +"Add"
                onClick = {
                    addCourseSelectRef.current?.value?.let {
                        dispatch(AddStudentToCourse(it, studentId))
                    }
                }
            }
        }
        CCourseList {
            this.courses = courses.map { it.first }.toTypedArray()
            this.marked = courses.map { it.second }.toTypedArray()
            this.clickId = { dispatch(MarkStudent(it, studentId)) }
            this.studId = student.id
            this.gradeList = grades.map { it.second }.toTypedArray()
            this.gradeSet = { courseId, studentId, it -> dispatch(AddGrade(courseId, studentId, it)) }
        }
    }
}

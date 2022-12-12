package component

import AddGrade
import AddStudentToCourse
import MarkStudent
import RemoveStudent
import data.CourseId
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

val CCourse = FC<Props>("Course") {
    val params: Record<String, String> = useParams()
    val courseId = params["courseId"] as CourseId
    val course = useSelector { state: AppState -> state.getCoursesById(arrayOf(courseId)) }.first()
    val students = useSelector { state: AppState -> state.getStudentsById(course.students) }
    val allStudents = useSelector { state: AppState -> state.students }
    val candidates = allStudents.toList() - students.toSet()
    val dispatch = useDispatch<RAction, Unit>()

    val removeStudentSelectRef = useRef<HTMLSelectElement>()
    val addStudentSelectRef = useRef<HTMLSelectElement>()
    div {
        h3 { +course.name }
        details {
            summary { +"Add Student" }
            select {
                ref = addStudentSelectRef
                candidates.forEach {
                    option { +it.id }
                }
            }
            button {
                +"Add"
                onClick = {
                    addStudentSelectRef.current?.value?.let {
                        dispatch(AddStudentToCourse(courseId, it))
                    }
                }
            }
        }
        details {
            id = "RemoveStudentCourse"
            summary { +"Remove Student Course" }
            select {
                ref = removeStudentSelectRef
                students.forEach {
                    option {
                        +it.id
                        id = "RemoveStudent${it.id}"
                    }
                }
            }
            button {
                +"Remove"
                id = "RemoveButton"
                onClick = {
                    removeStudentSelectRef.current?.value?.let { student ->
                        val studentFind = students.find {it.id == student}!!
                        dispatch(RemoveStudent(courseId, studentFind.id))
                    }
                }

            }
        }
        CStudentList {
            this.students = students.toTypedArray()
            this.marked = course.marked
            this.clickId = { dispatch(MarkStudent(courseId, it)) }
            this.gradesList = course.grades
            this.gradeSet = { studentId, it -> dispatch(AddGrade(courseId,studentId, it)) }

        }
    }
}


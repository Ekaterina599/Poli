package component

import csstype.*
import data.CourseId
import data.Grade
import data.Student
import data.StudentId
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr

external interface StudentListProps : Props {
    var students: Array<Student>
    var marked: Array<Boolean>
    var clickId: (StudentId) -> Unit
    var gradesList: Array<Grade>
    var gradeSet: (StudentId, Grade) -> Unit
}

val CStudentList = FC<StudentListProps>("StudentList") { props ->
    props.students.forEachIndexed { index, student ->
        tr {
            td {
                css {
                    background = NamedColor.lightpink
                    padding = Padding(vertical = 10.px, horizontal = 10.px)
                    border = Border(width = 2.px, style = LineStyle.solid)
                }
                CStudentItem { this.student = student }
                if (props.marked[index])
                    css {
                        fontWeight = FontWeight.bold
                    }
                onClick = { props.clickId(student.id) }
            }
            td {
                css {
                    padding = Padding(vertical = 10.px, horizontal = 10.px)
                    border = Border(width = 2.px, style = LineStyle.solid)
                }
                CGrade {
                    grade = props.gradesList[index]
                    setGrade = { props.gradeSet(student.id, it) }
                }
            }
        }
    }
    tr {
        label { +"Средняя оценка: ${props.gradesList.average().toInt()}" }
    }
}
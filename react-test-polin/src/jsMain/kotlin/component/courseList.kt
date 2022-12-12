package component

import csstype.*
import data.Course
import data.CourseId
import data.Grade
import data.StudentId
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr

external interface CourseListProps : Props {
    var courses: Array<Course>
    var marked: Array<Boolean>
    var clickId: (CourseId) -> Unit


    var studId: StudentId
    var gradeList: Array <Grade>
    var gradeSet: (CourseId, StudentId, Grade) -> Unit
}

val CCourseList = FC<CourseListProps>("CourseList") { props ->
    props.courses.forEachIndexed { index, course ->
        tr {
            td {
                css {
                    background = NamedColor.lightpink
                    padding = Padding(vertical = 10.px, horizontal = 10.px)
                    border = Border(width = 2.px, style = LineStyle.solid)
                }
                +course.id
                if (props.marked[index])
                    css { fontWeight = FontWeight.bold }
                onClick = { props.clickId(course.id) }
            }
            td {
                css {
                    padding = Padding(vertical = 10.px, horizontal = 10.px)
                    border = Border(width = 2.px, style = LineStyle.solid)
                }
                CGrade {
                    grade = props.gradeList[index]
                    setGrade = { props.gradeSet(course.id, props.studId, it) }
                }
            }
        }
    }
    tr {
        ReactHTML.label { +"Средняя оценка: ${props.gradeList.average().toInt()}" }
    }
}
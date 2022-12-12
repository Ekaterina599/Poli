package component

import csstype.*
import data.Grade
import react.FC
import react.Props
import emotion.react.css
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.redux.useSelector
import react.useContext
import redux.AppState

external interface GradeProps : Props {
    var grade: Grade
    var setGrade: (Grade) -> Unit
}


val CGrade =FC<GradeProps>("Grade") { props ->
    val color = useSelector { state: AppState -> state.color }
    val list = listOf(1,2,3,4,5)
    div {
        list.mapIndexed { index, it ->
            input {
                this.checked = false
                id = "CheckedGrade ${it}"
                value = "$it"
                if (it == props.grade) {
                    this.checked = true
                    css {
                        boxShadow =
                            BoxShadow(
                                2.px, 2.px,
                                Color(color[props.grade - 1])
                            )
                    }
                }
                type = InputType.checkbox
                onChange = { props.setGrade(it.target.let { (value as String).toInt() }) }
            }
        }
    }
}